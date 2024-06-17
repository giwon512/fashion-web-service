from selenium import webdriver
from selenium.webdriver.common.by import By
from selenium.webdriver.support.ui import WebDriverWait
from selenium.webdriver.support import expected_conditions as EC
from selenium.webdriver.chrome.options import Options
import time
from selenium.common.exceptions import NoSuchElementException

def create_browser(url):
    # 브라우저 꺼짐 방지 옵션
    chrome_options = Options()
    chrome_options.add_experimental_option("detach", True)
    # 브라우저 실행
    browser = webdriver.Chrome(options=chrome_options)
    browser.get(url)
    return browser

def scrape_news():
    print("[KFN news scraping]")
    # 1페이지에서 뉴스 10개 가져옴
    for i in range(1, 3):
        url = "https://www.koreafashionnews.com/sub.html?page={}&section=sc1&section2=".format(i)
        browser = create_browser(url)
        try:
            news_list = WebDriverWait(browser, 10).until(EC.presence_of_all_elements_located((By.CSS_SELECTOR, "body > div:nth-child(1) > table:nth-child(3) > tbody > tr > td:nth-child(2) > table")))
            for idx in range(len(news_list)):
                # 뉴스 목록 다시 로드 (페이지가 변경되었을 수 있으므로)
                news_list = WebDriverWait(browser, 10).until(EC.presence_of_all_elements_located((By.CSS_SELECTOR, "body > div:nth-child(1) > table:nth-child(3) > tbody > tr > td:nth-child(2) > table")))
                news = news_list[idx]
                # 0번째, 11번째 테이블은 뉴스 기사 정보가 아니다.
                if idx == 0 or idx == 11:
                    continue
                img_src = news.find_element(By.TAG_NAME, "img").get_attribute("src")
                link = news.find_element(By.TAG_NAME, "a").get_attribute("href")
                print("img :", img_src)  # 대표 이미지
                print("link :", link)  # 링크
                td_list = news.find_elements(By.CSS_SELECTOR, "tbody > tr:nth-child(2) > td:nth-child(2) > table > tbody > tr")
                title = td_list[0].text
                desc = td_list[1].text
                print(title)  # 제목
                print(desc)  # 부제목
                print()
                # 해당 뉴스 세부 페이지로 이동
                news.find_element(By.TAG_NAME, "a").click()
                # time.sleep(2)
                # 세부 페이지에서 기사 내용 크롤링
                content_result = ""
                contents_list_container = WebDriverWait(browser, 10).until(EC.presence_of_element_located((By.ID, "textinput")))
                # xpath로 자식 노드를 모두 가져옴
                contents_list = contents_list_container.find_elements(By.XPATH, "./*")
                for contents in contents_list:
                    try:
                        #이미지 태그가 없는 경우 에러를 발생시키고, 텍스트 값을 결과에 더해준다.
                        imgNode = contents.find_element(By.TAG_NAME, "img")
                        img = "<img src=\"" + imgNode.get_attribute("src") + "\" />"
                        content_result += img + '\n'
                    except NoSuchElementException:
                        content_result += contents.text + '\n'
                print(content_result)  # 본문 내용
                # 다시 목록 페이지로 복귀
                browser.back()
                # time.sleep(2)
                print("//////////////////////////////////////////")
                print()
        finally:
            browser.quit()

if __name__ == "__main__":
    scrape_news()
