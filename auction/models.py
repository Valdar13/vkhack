# -*- coding: utf-8 -*-
from __future__ import unicode_literals

from django.db import models
from model_utils import Choices
from model_utils.models import StatusModel, TimeStampedModel


class Product(StatusModel, TimeStampedModel):

    STATUS = Choices('open', 'closed')
    vk_group_id = models.CharField(max_length=100)
    vk_product_id = models.CharField(max_length=100)

    @property
    def winner(self):
        raise NotImplementedError()


class Bid(TimeStampedModel):

    product = models.ForeignKey(Product)
    vk_user_id = models.CharField(max_length=100)
    amount = models.PositiveIntegerField()
