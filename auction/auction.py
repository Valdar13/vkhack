# -*- coding: utf-8 -*-
import vk_api

from django.conf import settings


class Auction():

    def __init__(self, token=None):
        self.vk = vk_api.VkApi(token=token or settings.VK_GROUP_ACCESS_TOKEN)

    def get_greeting(self, product, user_id):
        return 'Лот доступен, высылайте деньги.'

    def product_chat_request(self, product_id, user_id):
        self.vk.method('messages.send', {
            'user_id': user_id,
            'message': self.get_greeting(product_id, user_id)})
