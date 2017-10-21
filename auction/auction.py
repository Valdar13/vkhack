# -*- coding: utf-8 -*-
import vk_api

from django.conf import settings
from django.utils import timezone


class Auction():

    def __init__(self, token=None):
        self.vk = vk_api.VkApi(token=token or settings.VK_GROUP_ACCESS_TOKEN)

    def get_greeting(self, product, user_id):
        return 'Лот доступен, высылайте деньги.'

    def new_product(self, product_id, initial_bid, step, duration):
        from auction.models import Product
        end_time = timezone.now() + timezone.timedelta(minutes=duration)
        return Product.objects.create(status=Product.STATUS.open,
                                      initial_bid=initial_bid,
                                      vk_product_id=product_id,
                                      step=step,
                                      duration=duration,
                                      end_time=end_time
        )

    def new_bid(self, product_id, user_id, amount):
        from auction.models import Product, Bid
        try:
            product = Product.objects.get(vk_product_id=product_id)
        except Product.DoesNotExist:
            self.tell(user_id, 'В данный момент аукцион не идёт.')
        else:
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
                return bid
            else:
                self.tell(user_id, "Не удалось перебить ставку: {}".format(
                    best_bid and best_bid.amount or product.initial_bid))

    def product_chat_request(self, product_id, user_id):
        self.tell(user_id, self.get_greeting(product_id, user_id))

    def tell(self, user_id, message):
        self.vk.method('messages.send', {
            'user_id': user_id,
            'message': message
        })
