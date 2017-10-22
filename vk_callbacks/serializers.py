from rest_framework import serializers
from vk_callbacks.models import Message


class CallbackSerializer(serializers.ModelSerializer):

    class Meta:
        model = Message
        fields = ['type', 'object']
