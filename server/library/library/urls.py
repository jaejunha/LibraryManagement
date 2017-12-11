"""library URL Configuration

The `urlpatterns` list routes URLs to views. For more information please see:
    https://docs.djangoproject.com/en/1.11/topics/http/urls/
Examples:
Function views
    1. Add an import:  from my_app import views
    2. Add a URL to urlpatterns:  url(r'^$', views.home, name='home')
Class-based views
    1. Add an import:  from other_app.views import Home
    2. Add a URL to urlpatterns:  url(r'^$', Home.as_view(), name='home')
Including another URLconf
    1. Import the include() function: from django.conf.urls import url, include
    2. Add a URL to urlpatterns:  url(r'^blog/', include('blog.urls'))
"""
from django.conf.urls import url
from django.contrib import admin
from . import views

urlpatterns = [
    url(r'^postSerial', views.postSerial, name='postSerial'),
    url(r'^getCO2', views.getCO2, name='getCO2'),
    url(r'^getUW', views.getUW, name='getUW'),
    url(r'^getIR', views.getIR, name='getIR'),
    url(r'^useSeat', views.useSeat, name='useSeat'),
    url(r'^checkSeat', views.checkSeat, name='checkSeat'),
    url(r'^setTimer', views.setTimer, name='setTimer'),
    url(r'^init', views.init, name='init'),
    url(r'^test', views.test, name='test'),
]
