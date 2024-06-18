import requests
from bs4 import BeautifulSoup, Tag

url = "https://www.fashionn.com/board/read_new.php?table=1004&number=51545&page=1&sel=&search=&sel_cat="
res = requests.get(url)
res.raise_for_status()

soup = BeautifulSoup(res.text, "lxml")

content_result = ""
content_body = soup.find("div", {"class":"view_body"})
# 뉴스 본문의 문단이 div 또는 p 태그로 나뉘어 있음
content_list = [content for content in content_body.contents if content != '\n']

for content in content_list:
    # HTML을 BeautifulSoup으로 파싱하고 .contents를 사용하여 자식 요소들을 가져오면, 태그 요소는 Tag 객체로, 텍스트 요소는 NavigableString 객체로 나타난다.
    # 그런데 <p><br/></p>태그는 단순 텍스트 노드로 인식하기 때문에 Tag 객체가 아닌 NavigableString 객체가 되어버리고, NavigableString은 .img와 같은 속성을 사용할 수 없다.
    # 따라서 다음 함수를 통해 태그 객체가 아닌 경우를 예외처리
    if not isinstance(content, Tag):
        continue
    # 사진이 있는 문단의 경우 사진과 설명이 p태그로 한 번 더 분류되어 있음
    if content.img:
        p_list = content.find_all("p")
        for elem in p_list:
            if elem.img:
                img = "<img src=\"https://www.fashionn.com" + elem.img["src"] + "\" />"
                content_result += img + '\n'
            else:
                content_result += elem.get_text() + '\n'
    else:
        content_result += content.get_text() + '\n'
print(content_result)