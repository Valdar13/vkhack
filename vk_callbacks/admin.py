from django.contrib import admin
from vk_callbacks.models import Message


@admin.register(Message)
class MessageAdmin(admin.ModelAdmin):
    list_display = ['created', 'type']
