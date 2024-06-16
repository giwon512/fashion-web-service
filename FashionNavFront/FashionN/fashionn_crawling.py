import requests
from bs4 import BeautifulSoup

def create_soup(url):
    res = requests.get(url)
    res.raise_for_status()
    soup = BeautifulSoup(res.text, "lxml", from_encoding="utf8")
    return soup


def scrape_news():
    print("[FashionN News scraping]")
    #기본 뉴스 페이지에서 1, 2페이지의 뉴스를 긁어옴(30개)
    for i in range(1, 3):
        url = "https://www.fashionn.com/board/list_new.php?page={}&table=1004".format(i)
        soup = create_soup(url)
        news_list_container = soup.find("ul", {"class" : "list_type_list01"})
        news_list = news_list_container.find_all("li")
        for news in news_list:
            title = news.dd.find("a")["title"]
            link = "https://www.fashionn.com/board/" + news.dd.find("a")["href"]
            desc = news.dd.get_text()
            print(title)
            print(link)
            print(desc)
            print("본문 내용")
            soup = create_soup(link)
            content = soup.find("div", {"class":"view_body"}).get_text()
            print(content)
            print("=================================================")




if __name__ == "__main__":
    scrape_news()