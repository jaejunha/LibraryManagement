import time
import httplib
import sqlite3

headers = {}
remain = 900
using = 0

con_web = httplib.HTTPConnection("localhost:8888")
con_web.request("GET", "/init?seat=A0","",headers)
con_web.getresponse()
print('server is running')
while True:
		time.sleep(0.5)
 		con_web.request("GET", "/getUW","",headers)
 		ir = con_web.getresponse().read().split("&gt;")[3].split("&lt;")[0]
 		con_web.request("GET", "/getIR","",headers)
 		uw = con_web.getresponse().read().split("&gt;")[3].split("&lt;")[0]
		try:
			if int(uw) <= 25:
				if using == 0:
					con_web.request("GET", "/useSeat?seat=A0&use=O","",headers)
					con_web.getresponse()
					con_web.request("GET", "/postSerial?con=r","",headers)
					con_web.getresponse()
					print 'use seat'
					remain = 900
					using = 1
					print 'remain: '+str(remain)
			if using == 1:
				if int(ir) >= 1:
					remain = 900
					con_web.request("GET", "/setTimer?seat=A0&remain="+str(remain),"",headers)
					con_web.getresponse()
					print 'remain: '+str(remain)
				elif int(uw) > 25 or int(ir) == 0:
					remain -= 1
					con_web.request("GET", "/setTimer?seat=A0&remain="+str(remain),"",headers)
					con_web.getresponse()
					print 'remain: '+str(remain)
					if remain == 0:
						con_web.request("GET", "/useSeat?seat=A0&use=X","",headers)
						con_web.getresponse()
						con_web.request("GET", "/postSerial?con=g","",headers)
						con_web.getresponse()
						using = 0
						print 'empty'
		except:
			pass
 		print 'uw: '+uw+'ir: '+ir