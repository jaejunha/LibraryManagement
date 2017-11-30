# -*- coding: utf-8 -*-

from django.shortcuts import render
#from models import *
import sys  
import requests

#setting utf-8
reload(sys)  
sys.setdefaultencoding('utf-8')

def postLED(request):

	#sensor <- Thyme <- Rosemary <- Mobius <- Server
	url = 'http://localhost:7579/rosemary/ae-edu0/cnt-led'
	headers = {'Content-Type': 'application/vnd.onem2m-res+xml;ty=4', 'Accept':'application/xml', 'X-M2M-Origin':'SRosemary', 'X-M2M-RI':'12345'}
	data = '<?xml version="1.0" encoding="UTF-8"?><m2m:cin xmlns:m2m="http://www.onem2m.org/xml/protocols" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"><cnf>text</cnf><con>0</con></m2m:cin>'
	requests.post(url, headers=headers, data=data)
	return render(request,'library/print.html')

def getCO2(request):

	#sensor -> Thyme -> Rosemary -> Mobius -> Server
	url = 'http://localhost:7579/rosemary/ae-edu0/cnt-co2/latest'
	headers = {'nmtype': 'long', 'Accept':'application/xml', 'X-M2M-Origin':'SRosemary', 'X-M2M-RI':'12345'}
	response = requests.get(url, headers=headers)
	context = {'response':response.text}
	return render(request,'library/print.html',context)