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

    def product_chat_request(self, product_id, user_id):
        self.vk.method('messages.send', {
            'user_id': user_id,
            'message': self.get_greeting(product_id, user_id)})
