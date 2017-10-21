import re
from celery.task import Task
from auction import Auction


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
            else:
                return
        try:
            attachments = data['attachments']
        except KeyError:
            pass
        else:
            for attachment in attachments:
                if attachment['type'] == 'link' and (
                        'product' in attachment['link']):
                    return self.product_chat_request(data)

    def product_chat_request(self, data):
        self.auction.product_chat_request(product_id=None,
                                          user_id=data['user_id'])


def process_message(message_id):
    MessageProcessor().run(message_id=message_id)
