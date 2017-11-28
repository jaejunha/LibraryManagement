# -*- coding: utf-8 -*-

from django.shortcuts import render
#from models import *
import sys  

#setting utf-8
reload(sys)  
sys.setdefaultencoding('utf-8')

def test(request):
	context = {'response':'hello world'}
	return render(request,'library/print.html',context)