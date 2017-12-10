# -*- coding: utf-8 -*-

from django.shortcuts import render
from models import *
import sys  
import requests

#setting utf-8
reload(sys)  
sys.setdefaultencoding('utf-8')

ip = 'localhost'
port = '7579'

def postURL(cnt, con):
	url = 'http://'+ip+':'+port+'/rosemary/ae-edu0/'+cnt
	headers = {'Content-Type': 'application/vnd.onem2m-res+xml;ty=4', 'Accept':'application/xml', 'X-M2M-Origin':'SRosemary', 'X-M2M-RI':'12345'}
	data = '<?xml version="1.0" encoding="UTF-8"?><m2m:cin xmlns:m2m="http://www.onem2m.org/xml/protocols" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"><cnf>text</cnf><con>'+con+'</con></m2m:cin>'
	requests.post(url, headers=headers, data=data)

def getURL(cnt):
	url = 'http://'+ip+':'+port+'/rosemary/ae-edu0/'+cnt+'/latest'
	headers = {'nmtype': 'long', 'Accept':'application/xml', 'X-M2M-Origin':'SRosemary', 'X-M2M-RI':'12345'}
	response = requests.get(url, headers=headers)
	return {'response':response.text}

def postSerial(request):

	#sensor <- Thyme <- Rosemary <- Mobius <- Server
	con = request.GET.get('con',0)
	postURL('cnt-serial',con)
	return render(request,'library/print.html')

def getCO2(request):

	#sensor -> Thyme -> Rosemary -> Mobius -> Server
	context = getURL('cnt-co2')
	return render(request,'library/print.html',context)

def getUW(request):

	#sensor -> Thyme -> Rosemary -> Mobius -> Server
	context = getURL('cnt-uw')
	return render(request,'library/print.html',context)

def getIR(request):

	#sensor -> Thyme -> Rosemary -> Mobius -> Server
	context = getURL('cnt-ir')
	return render(request,'library/print.html',context)

def useSeat(request):

	seat = request.GET.get('seat','')
	a = Area.objects.get(zone=seat[0])
	if request.GET.get('use','') == 'O':
		Seat(AreaZone = a, no=seat[1],use=1, time=900).save()
	else:
		Seat(AreaZone = a, no=seat[1],use=0, time=0).save()
	
	print seat+'is used'
	return render(request,'library/print.html')

def setTimer(request):
	seat = request.GET.get('seat','')
	remain = request.GET.get('remain','')
	a = Area.objects.get(zone=seat[0])
	Seat(AreaZone = a, no=seat[1], use=1, time=int(remain)).save()

	print 'remain: '+remain
	return render(request,'library/print.html')

def test(request):
	return render(request,'library/print.html')