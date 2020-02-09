#! /usr/bin/python
# -*- coding: utf-8 -*-
import pymysql
from socket import *
from select import *
import sys
from time import ctime


conn = pymysql.connect(host='localhost', user='root', password='root',db='testdb',charset='utf8')

curs = conn.cursor()



HOST = ''
PORT = 9999
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
    
        sql = 'INSERT INTO test1 (data) VALUES (%s)'
        curs.execute(sql, (data))
        conn.commit()
    
        if not data: break
  
    
        clientSocket.sendall(data)

conn.close()





clientSocket.close()
serverSocket.close()
print('close')
