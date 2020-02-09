#! /usr/bin/python
# -*- coding: utf-8 -*-
import pymysql
from socket import *
from select import *
import sys
from time import ctime


conn = pymysql.connect(host='localhost', user='root', password='root',db='testdb',charset='utf8')

curs = conn.cursor()


i = 0
HOST = ''
PORT = 8080
BUFSIZE = 1024
ADDR = (HOST,PORT)

serverSocket = socket(AF_INET, SOCK_STREAM)
serverSocket.bind(ADDR)
print('bind')
serverSocket.listen(100)
print('listen')
clientSocket, addr_info = serverSocket.accept()
print('accept')

while True:

    data = clientSocket.recv(1024)

  
           
        

    if not data: break

    if i==0:
        text1 = data
        i=i+1

    if i>0:
        text2 =data
    
    clientSocket.sendall(data)
    sql = 'INSERT INTO test2 (data1, data2) VALUES (%s, %s)'
    curs.execute(sql, (text1, text2))
    conn.commit()



conn.close()   






clientSocket.close()
serverSocket.close()
print('close')
