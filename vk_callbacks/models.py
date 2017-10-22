from django.contrib.postgres.fields import JSONField
from django.db import models
from model_utils.models import TimeStampedModel


class Message(TimeStampedModel):

    type = models.CharField(max_length=50)
    object = JSONField()

    def __str__(self):
        return str(self.created)
