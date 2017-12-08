# -*- coding: utf-8 -*-

from django.shortcuts import render
#from models import *
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

def test(request):
	return render(request,'library/print.html')