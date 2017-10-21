from django.conf.urls import url
from vk_callbacks.views import callback_post


urlpatterns = [
    url(r'', callback_post, name='callback_post'),
]
