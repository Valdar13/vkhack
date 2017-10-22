from django.conf.urls import include, url
from django.contrib import admin
import vk_callbacks.urls as vk_callbacks_urls

urlpatterns = [
    url(r'^admin/', admin.site.urls),
    url(r'^', include(vk_callbacks_urls),)
]
