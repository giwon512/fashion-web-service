import requests
from bs4 import BeautifulSoup

url = "https://www.fashionn.com/board/read_new.php?table=1004&number=51545&page=1&sel=&search=&sel_cat="
res = requests.get(url)
res.raise_for_status()

soup = BeautifulSoup(res.text, "lxml")

content_body = soup.find("div", {"class":"view_body"})
content_list = content_body.find_all("div")
for index, content in enumerate(content_list):
    print("{} : {}".format(index, content.get_text()))