sudo service mosquitto stop
mosquitto -v &
cd /home/pi/Edu/rosemary-v2.1.14
node rosemary &
cd /home/pi/Edu/thyme-v1.6.5
node thyme &
cd /home/pi/Edu/serial
node app &
cd /home/pi/Edu/thyme_tas_co2
node app