# -*- coding: utf-8 -*-
import vk_api

from django.conf import settings
from django.utils import timezone
from auction.wall_service import WallService


class Auction():
    """Auction logic
    """

    def __init__(self, token=None):
        self.vk = vk_api.VkApi(token=token or settings.VK_GROUP_ACCESS_TOKEN)

    def get_greeting(self, product, user_id):
        if not product:
            return 'Лот не доступен для ставок.'
        if not product.is_expired:
            seconds = (product.end_time - timezone.now()).total_seconds()
            minutes_remain = int(seconds / 60)
            msg = ("Лот доступен для ставок. "
                   "Начальная цена: {initial}. Шаг: {step}. "
                   "Минут до окончания: {minutes}.".format(
                       initial=product.initial_bid,
                       step=product.step,
                       minutes=minutes_remain

                   )
            )
            if product.best_bid:
                msg += " Текущая ставка: {}.".format(product.best_bid)
        else:
            msg = "К сожалению, лот не доступен для ставок. Аукцион закончен."
        return msg

    def new_product(self, product_id, initial_bid, step, duration):
        from auction.models import Product
        end_time = timezone.now() + timezone.timedelta(minutes=duration)
        obj, created = Product.objects.get_or_create(status=Product.STATUS.open,
                                                     initial_bid=initial_bid,
                                                     vk_product_id=product_id,
                                                     step=step,
                                                     duration=duration,
                                                     end_time=end_time)
        WallService().send('start', [
            ('product_id', product_id),
            ('price', initial_bid),
            ('step', step),
            ('local_id', obj.pk)])
        return obj

    def end_product(self, product_id):
        from auction.models import Product
        product = Product.objects.exclude(status=Product.STATUS.closed)\
                                 .get(vk_product_id=product_id)
        product.status = Product.STATUS.closed
        product.save()
        best_bid = product.best_bid
        msg = "Аукцион закончен."
        if best_bid:
            msg += " Победившая ставка: {} рублей.".format(best_bid.amount)
        for vk_user in product.vk_users:
            if vk_user == product.winner_vk_user:
                self.tell(vk_user, "Аукцион закончен. Ваша ставка победила!")
            else:
                self.tell(vk_user, msg)
        WallService().send('close', [
            ('product_id', product_id),
            ('user_id', best_bid and best_bid.vk_user_id),
            ('bid', best_bid and best_bid.amount or 0),
            ('local_id', product.pk)
        ])
        return product

    def new_bid(self, product_id, user_id, amount):
        from auction.models import Product, Bid
        try:
            product = Product.objects.exclude(status=Product.STATUS.closed)\
                                     .get(vk_product_id=product_id)
        except Product.DoesNotExist:
            self.tell(user_id, 'В данный момент аукцион не идёт.')
        else:
            if product.is_expired:
                self.tell(user_id,
                          'К сожалению, Ваша ставка не может быть принята. '
                          'Аукцион уже закончился.')
                return
            best_bid = product.best_bid
            if (not best_bid and amount >= product.initial_bid) or (
                    best_bid and amount >= best_bid.amount + product.step):
                bid = Bid.objects.create(product=product,
                                         vk_user_id=user_id,
                                         amount=amount)
                self.tell(user_id, "Ваша ставка в {} рублей принята!".format(amount))
                if best_bid:
                    self.tell(best_bid.vk_user_id, "Ваша ставка ({} рублей) "
                              "была перебита новой ставкой: {} рублей.".format(
                                  best_bid.amount, amount))
                WallService().send('update', [
                    ('product_id', product.pk),
                    ('user_id', user_id),
                    ('bid', amount),
                    ('local_id', product.pk)
                ])
                return bid
            else:
                self.tell(user_id, "Не удалось перебить ставку: {}".format(
                    best_bid and best_bid.amount or product.initial_bid))

    def product_chat_request(self, product_id, user_id):
        from auction.models import Chat, Product
        product = Product.objects.filter(vk_product_id=product_id).first()
        self.tell(user_id, self.get_greeting(product, user_id))
        if product:
            Chat.objects.create(product=product, vk_user_id=user_id)

    def tell(self, user_id, message):
        self.vk.method('messages.send', {
            'user_id': user_id,
            'message': message
        })
