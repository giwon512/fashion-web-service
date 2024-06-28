import requests
from bs4 import BeautifulSoup
from database import News, insert_data, dbConnect, test_insert_data, test_insert_image, test_select_id
import base64

def create_soup(url):
    res = requests.get(url)
    res.raise_for_status()
    res.encoding = 'cp949'
    soup = BeautifulSoup(res.text, "lxml")
    return soup

def scrape_news():
    
    print("[FI news scraping]")
    # 1, 2페이지에서 뉴스 가져오기(16개)
    for i in range(1, 3):
        url = "https://www.fi.co.kr/main/list.asp?SectionStr=News&SectionSub=&page={}".format(i)
        soup = create_soup(url)

        # 각각의 뉴스 리스트로 가져오기
        news_list_container = soup.find("ul", {"class":"gallery_list"})
        news_list = news_list_container.find_all("li")

        for news in news_list:

            # 링크 정보 / 제목 / 부제목 출력
            link = "https://www.fi.co.kr/main/" + news.find("a")["href"]
            # print("link : " + link) # 링크
            soup = create_soup(link)
            head = soup.find("div", {"class":"d_head"})
            title = head.find("h3", {"class":"d_title"}).get_text().strip()
            desc = head.find("p", {"class":"d_title_p"}).get_text().strip()
            # print(title) # 제목
            # print(desc) # 부제목
            
            # 추후에 대표이미지 따로 다운로드
            # 본문 내용 출력 / 이미지는 태그 형식으로 출력 / 대표 이미지 출력
            img_src = None
            content_result = ""
            body = soup.find("div", class_="d_cont").div
            children_list = [child for child in body.children if child != '\n']
            for child in children_list:
                if child.name =="table" or child.name =="p":
                    if child.img:
                        if not img_src:
                            # print("find first image")
                            img_src = child.img["src"]
                        img = "<img src=\"" + child.img["src"] + "\" />"
                        content_result += img + '\n'
                    if child.span:
                        content_result += child.find("span").get_text().strip() + '\n'
                else:
                    content_result += child.get_text().strip() + '\n'
            # print("대표 이미지 : ", img_src) # 대표 이미지
            # print()
            # print("본문 내용")
            # print(content_result) # 본문 내용
            # print()
            
            # db에 크롤링한 결과 삽입
            news_obj = News(title, desc, content_result, link, img_src)
            
            #이미지 다운로드
            img_res = requests.get(img_src)
            img_data = img_res.content

            #base64 인코딩
            encoded_image = base64.b64encode(img_data).decode('utf-8')
            
            conn = dbConnect()
            insert_data(conn, news_obj)
            newsId = test_select_id(conn)
            test_insert_image(conn, encoded_image, newsId)


if __name__ == "__main__":
    scrape_news()