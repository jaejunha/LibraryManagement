import requests
import time

co2_url = 'http://localhost:7575/rosemary/ae-edu0/cnt-co2/latest'
moter_url = 'http://localhost:7575/rosemary/ae-edu0/cnt-serial'
co2_headers = {'nmtype': 'long', 'Accept':'application/xml', 'X-M2M-Origin':'SRosemary', 'X-M2M-RI':'12345'}
moter_headers = headers = {'Content-Type': 'application/vnd.onem2m-res+xml;ty=4', 'Accept':'application/xml', 'X-M2M-Origin':'SRosemary', 'X-M2M-RI':'12345'}
moter_data = '<?xml version="1.0" encoding="UTF-8"?><m2m:cin xmlns:m2m="http://www.onem2m.org/xml/protocols" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"><cnf>text</cnf><con>m:90</con></m2m:cin>'
while True:
        response = requests.get(co2_url, headers = co2_headers)
        content = response.text[response.text.find('<content>')+9:].split("<")[0]
        print content

        if content > 2000:
                response = requests.post(moter_url, headers = moter_headers, data=moter_data)
        time.sleep(30*60)
