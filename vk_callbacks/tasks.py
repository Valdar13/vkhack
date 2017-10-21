from celery.task import Task
from auction import Auction


class MessageProcessor(Task):

    def run(self, message_id):
        self.auction = Auction()
        from vk_callbacks.models import Message
        message = Message.objects.get(pk=message_id)
        if message.type == 'message_new':
            self.process_object(message.object)

    def process_object(self, data):
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
