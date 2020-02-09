import socket

sock = socket.socket(socket.AF_INET, socket.SOCK_DGRAM)

sock.sendto("Hello".encode(),('192.168.63.28',4210))
