from django.contrib import admin
from auction.models import Bid, Product


@admin.register(Bid)
class BidAdmin(admin.ModelAdmin):
    list_display = ['vk_user_id', 'amount']


@admin.register(Product)
class ProductAdmin(admin.ModelAdmin):
    pass
