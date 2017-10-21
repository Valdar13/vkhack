import logging

from rest_framework.decorators import api_view, renderer_classes
from rest_framework.renderers import StaticHTMLRenderer
from rest_framework.response import Response
from rest_framework.status import HTTP_200_OK

from vk_callbacks.serializers import CallbackSerializer
from vk_callbacks.tasks import process_message


logger = logging.getLogger(__name__)


@api_view(['POST'])
@renderer_classes((StaticHTMLRenderer,))
def callback_post(request):
    try:
        stage = 'parsing'
        serializer = CallbackSerializer(data=request.data)
        serializer.is_valid(raise_exception=True)
        stage = 'saving'
        message = serializer.save()
        stage = 'processing'
        process_message(message.pk)
    except Exception:
        logger.exception("Error {} callback".format(stage))
    return Response(status=HTTP_200_OK, data='ok')
