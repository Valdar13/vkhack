# -*- coding: utf-8 -*-
from __future__ import unicode_literals

from django.db import models
from model_utils import Choices
from model_utils.models import StatusModel, TimeStampedModel


class Product(StatusModel, TimeStampedModel):

    STATUS = Choices('open', 'closed')
    # vk_group_id = models.CharField(max_length=100)
    vk_product_id = models.CharField(max_length=100)
    duration = models.PositiveIntegerField()
    initial_bid = models.PositiveIntegerField()
    step = models.PositiveIntegerField()
    end_time = models.DateTimeField()

    @property
    def winner(self):
        raise NotImplementedError()

    def __str__(self):
        return str(self.vk_product_id)


class Bid(TimeStampedModel):

    product = models.ForeignKey(Product)
    vk_user_id = models.CharField(max_length=100)
    amount = models.PositiveIntegerField()
