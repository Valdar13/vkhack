import re
from celery.task import Task
from auction import Auction
from auction.models import Chat


class MessageProcessor(Task):

    @property
    def auction(self):
        if not getattr(self, '_auction', None):
            self._auction = Auction()
        return self._auction

    def run(self, message_id):
        from vk_callbacks.models import Message
        message = Message.objects.get(pk=message_id)
        if message.type in ('message_new', 'market_comment_new'):
            self.process_object(message.type, message.object)

    def process_object(self, message_type, data):
        product_id = data.get('item_id')
        if message_type == 'market_comment_new':
            # comment format is: #AuctionStart-1000-50-5
            match = re.search('#AuctionStart-(\d+)-(\d+)-(\d+)', data['text'])
            if match:
                groups = match.groups()
                return self.auction.new_product(product_id=product_id,
                                                initial_bid=int(groups[0]),
                                                step=int(groups[1]),
                                                duration=int(groups[2]))
            match = re.search('#AuctionEnd', data['text'])
            if match:
                groups = match.groups()
                return self.auction.end_product(product_id=product_id)
            else:
                match = re.search('#bid (\d+)', data['text'])
                if match:
                    groups = match.groups()
                    return self.auction.new_bid(product_id=data['item_id'],
                                                user_id=data['from_id'],
                                                amount=int(groups[0]))
        elif message_type == 'message_new':
            try:
                attachments = data['attachments']
            except KeyError:
                pass
            else:
                for attachment in attachments:
                    if attachment['type'] == 'link' and (
                            'product' in attachment['link']):
                        return self.product_chat_request(
                            attachment['link']['url'], data)
                    if attachment['type'] == 'link' and (
                            attachment['link']['url'] == "https:\/\/m.vk.com\/landings\/moneysend"):
                        match = re.search('(\d+)', attachment['link']['title'])
                        groups = match.groups()
                        if match:
                            last_chat = Chat.objects.filter(vk_user_id=data['user_id']).last()
                            if last_chat:
                                return self.auction.new_bid(product_id=last_chat.product.vk_product_id,
                                                            user_id=data['user_id'],
                                                            amount=int(groups[0]))
            try:
                body = data['body']
            except KeyError:
                pass
            else:
                match = re.search('#bid (\d+)', data['body'])
                if match:
                    groups = match.groups()
                    last_chat = Chat.objects.filter(vk_user_id=data['user_id']).last()
                    if last_chat:
                        return self.auction.new_bid(product_id=last_chat.product.vk_product_id,
                                                    user_id=data['user_id'],
                                                    amount=int(groups[0]))

    def product_chat_request(self, url, data):
        match = re.search('https://m.vk.com/product-\d+_(\d+)', url)
        if match:
            groups = match.groups()
            self.auction.product_chat_request(product_id=int(groups[0]),
                                              user_id=data['user_id'])


def process_message(message_id):
    MessageProcessor().run(message_id=message_id)
