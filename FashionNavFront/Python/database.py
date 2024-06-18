import pymysql


def dbconnect():
    conn = pymysql.connect(host='127.0.0.1', user='fashiondbuser', password='fashiondbuser', 
                       db='fashiondb', charset='utf8')
    return conn

def search_data(conn):
    cur = conn.cursor()
    sql = 'SELECT * FROM news'
    cur.execute(sql)
    results = cur.fetchall()
    for i in range(len(results)):
        print(results[i])

# db 수정 전이므로 일단 subtitle, link, img_url 생략
def insert_data(conn, news):
    cur = conn.cursor()
    sql = "insert into news(title, content, published_date) \
        values(%s, %s, SYSDATE())"
    cur.execute(sql, (news.title, news.content))
    conn.commit()
    
def delete_data(conn, news_id):
    cur = conn.cursor()
    sql = "delete from news where news_id={}".format(news_id)
    cur.execute(sql)
    conn.commit()
    
class News():
    def __init__(self, title, subtitle, content, link, img_url):
        self.title = title
        self.subtitle = subtitle
        self.content = content
        self.link = link
        self.img_url = img_url
    
def main():
    # conn = dbconnect()
    # title = "역대급 폭염 예고! ‘스트라이프 vs 민소매’ 올 여름 골프 필드룩 뭐 입지?"
    # content = "contents body"
    # img_src = "<img src=\"https://www.fashionn.com/files/board/2024/image/o_1hvqpb989fnk9or1\" />"
    # news_obj = News(title, "subtitle", content, "link", img_src)
    # news_obj.content_parsing()
    # news_obj.title_parsing()
    # news_obj.img_url_parsing()
    # insert_data(conn, news_obj)
    print("It didn't called as module")
    
if __name__=="__main__":
    main()