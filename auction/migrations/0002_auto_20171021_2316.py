# -*- coding: utf-8 -*-
# Generated by Django 1.11.6 on 2017-10-21 23:16
from __future__ import unicode_literals

from django.db import migrations, models


class Migration(migrations.Migration):

    dependencies = [
        ('auction', '0001_initial'),
    ]

    operations = [
        migrations.AlterField(
            model_name='product',
            name='vk_product_id',
            field=models.CharField(max_length=100, unique=True),
        ),
    ]
