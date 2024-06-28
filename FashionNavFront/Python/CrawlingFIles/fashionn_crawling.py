import requests
from database import News, insert_data, dbConnect, test_insert_data, test_insert_image, test_select_id
from bs4 import BeautifulSoup, Tag
import base64

def create_soup(url):
    res = requests.get(url)
    res.raise_for_status()
    soup = BeautifulSoup(res.text, "lxml")
    return soup


def scrape_news():
    
    print("[FashionN News scraping]")
    #기본 뉴스 페이지에서 1페이지의 뉴스를 긁어옴(15개)
    for i in range(1, 2):
        url = "https://www.fashionn.com/board/list_new.php?page={}&table=1004".format(i)
        soup = create_soup(url)
        news_list_container = soup.find("ul", {"class" : "list_type_list01"})
        news_list = news_list_container.find_all("li")
        for news in news_list:
            title = news.dd.find("a")["title"]
            link = "https://www.fashionn.com/board/" + news.dd.find("a")["href"]
            desc = news.dd.get_text()
            img_src = "<img src=\"https://www.fashionn.com" + news.img["src"] + "\" />"
            # print(title) # 제목
            # print(link) # 링크
            # print(desc) # 부제목
            # print(img_src) # 대표 이미지
            
            # 추후에 이미지 따로 다운로드 받을 예정
            # 이미지 태그 / 이미지 설명 / 본문 내용을 실제 게시글의 순서에 맞게 출력
            # print("본문 내용")
            soup = create_soup(link)
            content_result = ""
            content_body = soup.find("div", {"class":"view_body"})
            # 뉴스 본문의 문단이 div 또는 p 태그로 나뉘어 있음
            content_list = [content for content in content_body.contents if content != '\n']

            for content in content_list:
                # HTML을 BeautifulSoup으로 파싱하고 .contents를 사용하여 자식 요소들을 가져오면, 태그 요소는 Tag 객체로, 텍스트 요소는 NavigableString 객체로 나타난다.
                # 그런데 <p><br/></p>태그는 단순 텍스트 노드로 인식하기 때문에 Tag 객체가 아닌 NavigableString 객체가 되어버리고, NavigableString은 .img와 같은 속성을 사용할 수 없다.
                # 따라서 isinstance 함수를 통해 태그 객체가 아닌 경우를 예외처리
                # 사진이 있는 문단의 경우 사진과 설명이 p태그로 한 번 더 분류되어 있음
                if isinstance(content, Tag) and content.img:
                    p_list = content.find_all("p")
                    for elem in p_list:
                        if elem.img:
                            img = "<img src=\"https://www.fashionn.com" + elem.img["src"] + "\" />"
                            content_result += img + '\n'
                        else:
                            content_result += elem.get_text() + '\n'
                else:
                    content_result += content.get_text() + '\n'
            # print(content_result) # 본문 내용
            
            # db에 크롤링한 결과 삽입
            news_obj = News(title, desc, content_result, link, img_src)
            
            #이미지 다운로드
            img_res = requests.get("https://www.fashionn.com" + news.img["src"])
            img_data = img_res.content

            #base64 인코딩
            encoded_image = base64.b64encode(img_data).decode('utf-8')
            
            conn = dbConnect()
            insert_data(conn, news_obj)
            newsId = test_select_id(conn)
            test_insert_image(conn, encoded_image, newsId)
            
            # print("////////////////////////////////////////////////////")




if __name__ == "__main__":
    scrape_news()