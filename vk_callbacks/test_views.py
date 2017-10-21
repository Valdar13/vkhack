from django.core.urlresolvers import reverse

from rest_framework.test import APITestCase
from rest_framework.status import HTTP_200_OK

from vk_callbacks.models import Message


class TestPostCallbackView(APITestCase):

    def setUp(self):
        self.url = reverse('callback_post')

    def test_200_returned(self):
        with self.subTest('empty post'):
            response = self.client.post(self.url)
            self.assertEqual(response.status_code, HTTP_200_OK)
        with self.subTest('trash post'):
            response = self.client.post(self.url, {'some': 'trash',
                                                   'object': 'trash'})
            self.assertEqual(response.status_code, HTTP_200_OK)

    def test_post_callback_creates_message(self):
        with self.subTest('object is not created for empty callback'):
            self.client.post(self.url)
            self.assertEqual(Message.objects.count(), 0)
        with self.subTest('object created for valid callback'):
            self.client.post(self.url, {
                'type': 'message_new',
                'object': {'date': 1508520788},
            })
            message = Message.objects.get()
            self.assertEqual(message.type, 'message_new')
            self.assertEqual(message.object['date'], 1508520788)
