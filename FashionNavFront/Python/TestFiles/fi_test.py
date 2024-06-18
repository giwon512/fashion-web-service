import requests
from bs4 import BeautifulSoup

url = "https://www.fi.co.kr/main/view.asp?idx=82569"
res = requests.get(url)
res.encoding = 'cp949'

soup = BeautifulSoup(res.text, "lxml")

body = soup.find("div", class_="d_cont").div
children_list = [child for child in body.children if child != '\n']
for child in children_list:
    if child.name =="table":
        print("=======================================")
        img = "<img src=" + child.img["src"] + " />"
        print("image tag :", img)
        print(child.find("span").get_text().strip())
        print("=======================================")
    else:
        print(child.get_text().strip())