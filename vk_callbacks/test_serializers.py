 # -*- coding: utf-8 -*-
from django.test import TestCase
from vk_callbacks.serializers import CallbackSerializer


class TestCallbacks(TestCase):

    def test_product_chat_request(self):
        request_data = {
            "type": "message_new",
            "object": {
                "id": 15,
                "date": 1508531265,
                "user_id": 12345,
                "read_state": 0,
                "title": "",
                "body": "Здравствуйте!\nМеня заинтересовал этот товар.",
                "attachments": [
                    {
                        "type": "link",
                        "link": {
                            "url": "https://m.vk.com/product-123_1130101_24d6ce3d38fa8df953?api_view=e1fb57d99496264e40980d0aaeac2e",
                            "title": "ваыва",
                            "caption": "Товар",
                            "description": "ывава ыа ыва ываы ваы",
                            "is_external": 0,
                            "product": {
                                "price": {
                                    "amount": "333300",
                                    "currency": {
                                        "id": 978,
                                        "name": "EUR"
                                    },
                                    "text": "€ 3 333"
                                }
                            },
                            "button": {
                                "title": "Посмотреть",
                                "url": "https://m.vk.com/product-123_1130101_24d6ce3d38fa8df953?api_view=e1fb57d99496264e40980d0aaeac2e"
                            }
                        }
                    }
                ]
            },
            "group_id": 155403696
        }
        serializer = CallbackSerializer(data=request_data)
        serializer.is_valid(raise_exception=True)

        obj = serializer.save()
        self.assertEqual(obj.type, 'message_new')
        self.assertEqual(obj.object['attachments'][0]['link']['caption'],
                         'Товар')
