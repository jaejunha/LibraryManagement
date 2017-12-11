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
		s = Seat.objects.filter(AreaZone=a,no=seat[1])
		if s.count()>0:
			s.update(use=1)
			s.update(time=900)
		else:	
			Seat(AreaZone = a, no=seat[1],use=1, time=900).save()
	else:	
		s = Seat.objects.filter(AreaZone=a,no=seat[1])
		if s.count()>0:
			s.update(use=0)
			s.update(time=0)
		else:	
			Seat(AreaZone = a, no=seat[1],use=0, time=0).save()
	
	print seat+'is used'
	return render(request,'library/print.html')

def checkSeat(request):

	seat = request.GET.get('seat','')
	a = Area.objects.get(zone=seat[0])
	s = Seat.objects.filter(AreaZone=a,no=seat[1])
	use = 0

	if s.count()>0:
		use = s[0].use

	return render(request,'library/print.html',{"response":use})

def setTimer(request):
	seat = request.GET.get('seat','')
	remain = request.GET.get('remain','')
	a = Area.objects.get(zone=seat[0])
	s = Seat.objects.filter(AreaZone=a,no=seat[1])
	if s.count()>0:
		s.update(use=1)
		s.update(time=int(remain))
	else:	
		Seat(AreaZone = a, no=seat[1], use=1, time=int(remain)).save()

	print 'remain: '+remain
	return render(request,'library/print.html')

def getTimer(request):
	seat = request.GET.get('seat','')
	a = Area.objects.get(zone=seat[0])
	s = Seat.objects.filter(AreaZone=a,no=seat[1])
	remain = s[0].time
	
	return render(request,'library/print.html',{"response":remain})

def init(request):
	seat = request.GET.get('seat','')
	print seat
	a = Area.objects.get(zone=seat[0])
	Seat.objects.filter(AreaZone=a,no=seat[1]).delete()
	return render(request,'library/print.html')

def test(request):
	"""
	a = Area.objects.get(zone='A')
	Seat(AreaZone = a, no='1', use=1, time=400).save()
	Seat(AreaZone = a, no='2', use=1, time=400).save()
	Seat(AreaZone = a, no='3', use=1, time=400).save()
	Seat(AreaZone = a, no='4', use=1, time=400).save()
	Seat(AreaZone = a, no='6', use=1, time=400).save()
	Seat(AreaZone = a, no='7', use=1, time=400).save()
	Seat(AreaZone = a, no='9', use=1, time=400).save()
	Seat(AreaZone = a, no='10', use=1, time=400).save()
	Seat(AreaZone = a, no='11', use=1, time=400).save()

	a = Area.objects.get(zone='B')
	Seat(AreaZone = a, no='0', use=1, time=400).save()
	Seat(AreaZone = a, no='3', use=1, time=400).save()
	Seat(AreaZone = a, no='4', use=1, time=400).save()
	Seat(AreaZone = a, no='5', use=1, time=400).save()
	Seat(AreaZone = a, no='6', use=1, time=400).save()
	Seat(AreaZone = a, no='7', use=1, time=400).save()
	Seat(AreaZone = a, no='8', use=1, time=400).save()
	Seat(AreaZone = a, no='9', use=1, time=400).save()
	Seat(AreaZone = a, no='10', use=1, time=400).save()

	a = Area.objects.get(zone='C')
	Seat(AreaZone = a, no='1', use=1, time=400).save()
	Seat(AreaZone = a, no='2', use=1, time=400).save()
	Seat(AreaZone = a, no='3', use=1, time=400).save()
	Seat(AreaZone = a, no='4', use=1, time=400).save()
	Seat(AreaZone = a, no='5', use=1, time=400).save()
	Seat(AreaZone = a, no='6', use=1, time=400).save()
	Seat(AreaZone = a, no='7', use=1, time=400).save()
	Seat(AreaZone = a, no='8', use=1, time=400).save()
	Seat(AreaZone = a, no='9', use=1, time=400).save()
	Seat(AreaZone = a, no='11', use=1, time=400).save()

	a = Area.objects.get(zone='D')
	Seat(AreaZone = a, no='1', use=1, time=400).save()
	Seat(AreaZone = a, no='3', use=1, time=400).save()
	Seat(AreaZone = a, no='5', use=1, time=400).save()
	Seat(AreaZone = a, no='6', use=1, time=400).save()
	Seat(AreaZone = a, no='9', use=1, time=400).save()
	Seat(AreaZone = a, no='10', use=1, time=400).save()

	a = Area.objects.get(zone='E')
	Seat(AreaZone = a, no='1', use=1, time=400).save()
	Seat(AreaZone = a, no='2', use=1, time=400).save()
	Seat(AreaZone = a, no='3', use=1, time=400).save()
	Seat(AreaZone = a, no='4', use=1, time=400).save()
	Seat(AreaZone = a, no='6', use=1, time=400).save()
	Seat(AreaZone = a, no='7', use=1, time=400).save()
	Seat(AreaZone = a, no='9', use=1, time=400).save()
	Seat(AreaZone = a, no='10', use=1, time=400).save()
	Seat(AreaZone = a, no='11', use=1, time=400).save()

	a = Area.objects.get(zone='F')
	Seat(AreaZone = a, no='0', use=1, time=400).save()
	Seat(AreaZone = a, no='3', use=1, time=400).save()
	Seat(AreaZone = a, no='4', use=1, time=400).save()
	Seat(AreaZone = a, no='5', use=1, time=400).save()
	Seat(AreaZone = a, no='6', use=1, time=400).save()
	Seat(AreaZone = a, no='7', use=1, time=400).save()
	Seat(AreaZone = a, no='8', use=1, time=400).save()
	Seat(AreaZone = a, no='9', use=1, time=400).save()
	Seat(AreaZone = a, no='10', use=1, time=400).save()

	a = Area.objects.get(zone='G')
	Seat(AreaZone = a, no='1', use=1, time=400).save()
	Seat(AreaZone = a, no='2', use=1, time=400).save()
	Seat(AreaZone = a, no='3', use=1, time=400).save()
	Seat(AreaZone = a, no='4', use=1, time=400).save()
	Seat(AreaZone = a, no='5', use=1, time=400).save()
	Seat(AreaZone = a, no='6', use=1, time=400).save()
	Seat(AreaZone = a, no='7', use=1, time=400).save()
	Seat(AreaZone = a, no='8', use=1, time=400).save()
	Seat(AreaZone = a, no='9', use=1, time=400).save()
	Seat(AreaZone = a, no='11', use=1, time=400).save()

	a = Area.objects.get(zone='H')
	Seat(AreaZone = a, no='1', use=1, time=400).save()
	Seat(AreaZone = a, no='2', use=1, time=400).save()
	Seat(AreaZone = a, no='3', use=1, time=400).save()
	Seat(AreaZone = a, no='4', use=1, time=400).save()
	Seat(AreaZone = a, no='6', use=1, time=400).save()
	Seat(AreaZone = a, no='7', use=1, time=400).save()
	Seat(AreaZone = a, no='9', use=1, time=400).save()
	Seat(AreaZone = a, no='10', use=1, time=400).save()
	Seat(AreaZone = a, no='11', use=1, time=400).save()

	a = Area.objects.get(zone='I')
	Seat(AreaZone = a, no='0', use=1, time=400).save()
	Seat(AreaZone = a, no='3', use=1, time=400).save()
	Seat(AreaZone = a, no='4', use=1, time=400).save()
	Seat(AreaZone = a, no='5', use=1, time=400).save()
	Seat(AreaZone = a, no='6', use=1, time=400).save()
	Seat(AreaZone = a, no='7', use=1, time=400).save()
	Seat(AreaZone = a, no='9', use=1, time=400).save()
	Seat(AreaZone = a, no='10', use=1, time=400).save()

	
	A{0,1,1,1,1,0,1,1,0,1,1,1}
	B{1,0,0,1,1,1,1,1,1,1,1,0}
	C{0,1,1,1,1,1,1,1,1,1,0,1},
	D{0,1,0,1,0,1,1,0,0,1,1,0}
	E{0,1,1,1,1,0,1,1,0,1,1,1}
	F{1,0,0,1,1,1,1,1,1,1,1,0},
	G{0,1,1,1,1,1,1,1,1,1,0,1}
	H{0,1,1,1,1,0,1,1,0,1,1,1}
	I{1,0,0,1,1,1,1,1,1,1,1,0}};
	"""

	return render(request,'library/print.html')