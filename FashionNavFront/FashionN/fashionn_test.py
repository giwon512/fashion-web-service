import requests
from bs4 import BeautifulSoup

url = "https://www.fashionn.com/board/read_new.php?table=1004&number=51545&page=1&sel=&search=&sel_cat="
res = requests.get(url)
res.raise_for_status()

soup = BeautifulSoup(res.text, "lxml")

content_body = soup.find("div", {"class":"view_body"})
content_list = content_body.find_all("div")
for content in content_list:
    if content.img:
        img = "<img src=https://www.fashionn.com" + content.img["src"] + " />"