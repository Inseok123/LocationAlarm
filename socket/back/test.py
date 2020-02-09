import pymysql

conn = pymysql.connect(host='localhost', user='root', password='root',db='testdb',charset='utf8')

curs = conn.cursor()
curs.execute("select * from person;")
rows = curs.fetchall()

print(rows)
