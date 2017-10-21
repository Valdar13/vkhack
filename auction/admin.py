from django.contrib import admin
from auction.models import Product


@admin.register(Product)
class ProductAdmin(admin.ModelAdmin):
    pass
