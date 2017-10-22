from django.conf import settings
import requests
from urllib.parse import urljoin


class WallService():
    """Market operations Java service interface
    """

    def __init__(self, base_url=None):
        self.base_url = base_url or settings.WALL_SERVICE_URL

    def get_url(self, path):
        return urljoin(self.base_url, path)

    def send(self, command, parameters):
        parameters = ["{}={}".format(k, v) for k, v in parameters]
        requests.get(self.get_url(command) + '?' + '&'.join(parameters))


if __name__  == '__main__':
    base_url = 'http://localhost:8080'
