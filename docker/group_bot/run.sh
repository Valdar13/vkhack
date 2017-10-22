#!/bin/sh
set -e -x
echo ""
echo ""

APP='vk_auction'
pip3 install -r requirements.txt
python manage.py migrate
python manage.py collectstatic --no-input

gunicorn -w 1 -b :8000 "${APP}".wsgi:application
