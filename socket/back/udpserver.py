import socket
import sys
import pymysql

conn = pymysql.connect(host='localhost', user='root', password='root',db='testdb',charset='utf8')

curs = conn.cursor()

sock = socket.socket(socket.AF_INET, socket.SOCK_DGRAM)
print("socket")
sock.bind(('',9999))
print("bind")
while True:
    data = sock.recvfrom(200)
    real_data = data[0]
    data_list = real_data.split(",")
    print("receive")
    print(data_list)

    
    sql = 'INSERT INTO test3 (date,time,latitude,longtitude) VALUES (%s,%s,%s,%s)'
    curs.execute(sql,(data_list[0], data_list[1], data_list[2], data_list[3]))
    conn.commit()


conn.close()

