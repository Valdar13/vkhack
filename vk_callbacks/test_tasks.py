 # -*- coding: utf-8 -*-
from unittest.mock import patch
from django.test import TestCase
from vk_callbacks.tasks import MessageProcessor



class TestMessageProcessor(TestCase):

    @patch('auction.auction.Auction.new_product')
    def test_new_product(self, new_product):
        obj = MessageProcessor().process_object('market_comment_new',
                                                {"id":1,
                                                 "from_id":123,
                                                 "date":1508599919,
                                                 "text":"#AuctionStart-1000-50-5",
                                                 "item_id":1131189})
        new_product.assert_called_once_with(product_id=1131189,
                                            initial_bid=1000,
                                            step=50,
                                            duration=5)

    #@patch('auction.auction.Auction.new_bid')
    @patch('auction.auction.Auction.tell')
    def test_new_bid(self, tell):#new_bid):
        from auction import Auction
        product = Auction().new_product(product_id=189,
                                        initial_bid=1000,
                                        step=50,
                                        duration=5)
        obj = MessageProcessor().process_object('market_comment_new',
                                                {"id": 3,
                                                 "date": 1508607991,
                                                 "text": "#bid 1000",
                                                 "from_id": 213,
                                                 "item_id": 189,
                                                 "market_owner_id": -155
                                                })
        self.assertEqual(obj.amount, 1000)

        obj = MessageProcessor().process_object('message_new', {"id": 47,
                                                                "out": 0,
                                                                "body": "#bid 2000",
                                                                "user_id": 213
        })
        self.assertEqual(obj.amount, 2000)

        obj = MessageProcessor().process_object(
            'message_new',
            {"id":6, "user_id":213, "body":"PRIVET",
             "attachments":[{"type":"link","link":{
                 "url":"https:\/\/m.vk.com\/landings\/moneysend","title":"3000 ?4??4??1?.",
                 "caption":"Денежный перевод",}}],
             "group_id":155403696}
        )
        self.assertEqual(obj.amount, 3000)

        obj = MessageProcessor().process_object('market_comment_new',
                                                {"id": 3,
                                                 "date": 1508607991,
                                                 "text": "#bid 123",
                                                 "from_id": 213,
                                                 "item_id": 189,
                                                 "market_owner_id": -155
                                                })
        self.assertFalse(obj)
        obj = MessageProcessor().process_object('market_comment_new',
                                                {"id": 3,
                                                 "date": 1508607991,
                                                 "text": "#bid 123000",
                                                 "from_id": 213,
                                                 "item_id": 189,
                                                 "market_owner_id": -155
                                                })
        self.assertTrue(obj)
