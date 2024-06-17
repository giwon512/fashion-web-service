import requests
from bs4 import BeautifulSoup

def create_soup(url):
    res = requests.get(url)
    res.raise_for_status()
    res.encoding = 'cp949'
    soup = BeautifulSoup(res.text, "lxml")
    return soup

def scrape_news():

    # 1, 2페이지에서 뉴스 가져오기
    for i in range(1, 3):
        print("[FI news scraping]")
        url = "https://www.fi.co.kr/main/list.asp?SectionStr=News&SectionSub=&page={}".format(i)
        soup = create_soup(url)

        # 각각의 뉴스 리스트로 가져오기
        news_list_container = soup.find("ul", {"class":"gallery_list"})
        news_list = news_list_container.find_all("li")

        for news in news_list:

            # 링크 정보 / 제목 / 부제목 출력
            link = "https://www.fi.co.kr/main/" + news.find("a")["href"]
            print("link : " + link)
            soup = create_soup(link)
            head = soup.find("div", {"class":"d_head"})
            title = head.find("h3", {"class":"d_title"}).get_text().strip()
            desc = head.find("p", {"class":"d_title_p"}).get_text().strip()
            print(title)
            print(desc)
            
            # 추후에 대표이미지 따로 다운로드
            # 본문 내용 출력 / 이미지는 태그 형식으로 출력 / 대표 이미지 출력
            img_src = None
            body = soup.find("div", class_="d_cont").div
            children_list = [child for child in body.children if child != '\n']
            for child in children_list:
                if child.name =="table":
                    print("=======================================")
                    if child.img:
                        if not img_src:
                            img_src = child.img["src"]
                        img = "<img src=\"" + child.img["src"] + "\" />"
                        print("image tag :", img)
                    if child.span:
                        print(child.find("span").get_text().strip())
                    print("=======================================")
                else:
                    print(child.get_text().strip())
            print("대표 이미지 : ", img_src)
            print()


if __name__ == "__main__":
    scrape_news()