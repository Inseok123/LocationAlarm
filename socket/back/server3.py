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

    text1 = clientSocket.recv(1024)
    if i==0: break
    clientSocket.sendall(text1)
    i=i+1
clientSocket.close()
clientSocket, addr_info = serverSocket.accept()
while True:

    text2 = clientSocket.recv(1024)
    if i==1: break
    clientSocket.sendall(text2)
    i=i-1
           
        

    

   
sql = 'INSERT INTO test2 (data1, data2) VALUES (%s, %s)'
curs.execute(sql, (text1, text2))
conn.commit()
conn.close()   






clientSocket.close()
serverSocket.close()
print('close')
