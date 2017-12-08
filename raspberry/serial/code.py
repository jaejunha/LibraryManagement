import sys
import serial

port = "/dev/ttyACM0"
ser = serial.Serial(port, 9600)
ser.flushInput()

com = sys.argv[1]

print 'get '+com+' command'
ser.write(com)
