# -*- coding: utf-8 -*-
from __future__ import unicode_literals

from django.db import models
from django.utils import timezone
from model_utils import Choices
from model_utils.models import StatusModel, TimeStampedModel


class Product(StatusModel, TimeStampedModel):

    STATUS = Choices('open', 'closed')
    vk_product_id = models.CharField(max_length=100, unique=True)
    duration = models.PositiveIntegerField()
    initial_bid = models.PositiveIntegerField()
    step = models.PositiveIntegerField()
    end_time = models.DateTimeField()

    @property
    def best_bid(self):
        return self.bid_set.order_by('-amount').first()

    @property
    def is_expired(self):
        return self.end_time <= timezone.now()

    @property
    def winner_vk_user(self):
        return self.status == self.STATUS.closed and self.best_bid.vk_user_id

    @property
    def vk_users(self):
        return self.bid_set.values_list('vk_user_id', flat=True).distinct()

    def __str__(self):
        return str(self.vk_product_id)


class Bid(TimeStampedModel):

    product = models.ForeignKey(Product)
    vk_user_id = models.CharField(max_length=100)
    amount = models.PositiveIntegerField()

    def __str__(self):
        return str(self.amount)


class Chat(TimeStampedModel):

    product = models.ForeignKey(Product)
    vk_user_id = models.CharField(max_length=100)
