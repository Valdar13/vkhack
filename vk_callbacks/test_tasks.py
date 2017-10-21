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
