import pymysql


def dbConnect():
    conn = pymysql.connect(host='svc.sel5.cloudtype.app', port=30052, user='fashiondbuser', password='fashiondbuser',
                       db='fashiondb', charset='utf8')
    return conn

def search_data(conn):
    cur = conn.cursor()
    # sql = 'SELECT * FROM Raw_News'
    sql = "select img_content from test_image"
    cur.execute(sql)
    results = cur.fetchall()
    # for i in range(len(results)):
    #     print(results[i])
    return results

def insert_data(conn, news):
    cur = conn.cursor()
    sql = "insert into Raw_News(title, content, published_date, subtitle) \
        values(%s, %s, SYSDATE(), %s)"
    cur.execute(sql, (news.title, news.content, news.subtitle))
    conn.commit()

def insert_image(conn, imageData, newsId):
    cur = conn.cursor()
    sql = "insert into images(img_content, news_id) values(%s, %s)"
    cur.execute(sql, (imageData, newsId))
    conn.commit()
    
def test_insert_data(conn, news):
    cur = conn.cursor()
    sql = "insert into Test_News(title, content, published_date) \
        values(%s, %s, SYSDATE())"
    cur.execute(sql, (news.title, news.content))
    conn.commit()
    
def test_insert_image(conn, imageData, newsId):
    cur = conn.cursor()
    sql = "insert into test_image(img_content, news_id) values(%s, %s)"
    cur.execute(sql, (imageData, newsId))
    conn.commit()
    
def test_select_id(conn):
    cur = conn.cursor()
    sql = "select news_id from Raw_News order by published_date desc limit 1"
    cur.execute(sql)
    result = cur.fetchall()
    return result[0]

def delete_data(conn, news_id):
    cur = conn.cursor()
    sql = "delete from Raw_News where news_id={}".format(news_id)
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
    # conn = dbConnect()
    # title = "역대급 폭염 예고! ‘스트라이프 vs 민소매’ 올 여름 골프 필드룩 뭐 입지?"
    # content = "contents body"
    # img_src = "<img src=\"https://www.fashionn.com/files/board/2024/image/o_1hvqpb989fnk9or1\" />"
    # news_obj = Raw_News(title, "subtitle", content, "link", img_src)
    # news_obj.content_parsing()
    # news_obj.title_parsing()
    # news_obj.img_url_parsing()
    # insert_data(conn, news_obj)
    print("It didn't called as module")

if __name__=="__main__":
    main()