from selenium import webdriver
from selenium.webdriver.common.by import By
from selenium.webdriver.support.ui import WebDriverWait
from selenium.webdriver.support import expected_conditions as EC
from selenium.webdriver.common.keys import Keys
import time
from selenium.webdriver.chrome.options import Options

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
    for i in range(1, 2):
        url = "https://www.koreafashionnews.com/sub.html?page={}&section=sc1&section2=".format(i)
        browser = create_browser(url)
        try:
            news_list = WebDriverWait(browser, 10).until(EC.presence_of_all_elements_located((By.CSS_SELECTOR, "body > div:nth-child(1) > table:nth-child(3) > tbody > tr > td:nth-child(2) > table")))
            for idx, news in enumerate(news_list):
                news_list = WebDriverWait(browser, 10).until(EC.presence_of_all_elements_located((By.CSS_SELECTOR, "body > div:nth-child(1) > table:nth-child(3) > tbody > tr > td:nth-child(2) > table")))
                # 0번째, 11번째 테이블은 뉴스 기사 정보가 아니다.
                if idx is 0 or idx is 11:
                    continue
                img_src = news.find_element(By.TAG_NAME, "img").get_attribute("src")
                link = news.find_element(By.TAG_NAME, "a").get_attribute("href")
                print("img :", img_src) # 대표 이미지
                print("link :", link) # 링크
                td_list = news.find_elements(By.CSS_SELECTOR, "tbody > tr:nth-child(2) > td:nth-child(2) > table > tbody > tr")
                title = td_list[0].text
                desc = td_list[1].text
                print(title) # 제목
                print(desc) # 부제목
                print()
                content_result = ""
                # 해당 뉴스 세부 페이지로 이동
                news.click()
                time.sleep(2)
                # 세부 페이지에서 원래 페이지로 복귀
                browser.back()
                print(content_result) # 본문 내용
                print("//////////////////////////////////////////")
                
        finally:
            print(browser.quit())
            

if __name__ == "__main__":
    scrape_news()
    
    
    KFN_crawling.py 파일은 제대로 동작하는데, KFN_test.py파일은 selenium.common.exceptions.StaleElementReferenceException: Message: stale element reference: stale element not found 에러가 발생해. 아무리봐도 두 코드의 로직이 같은데 왜 이런 다른 결과가 발생하는지 분석해서 자세히 설명해줘