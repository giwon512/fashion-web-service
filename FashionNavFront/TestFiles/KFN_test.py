from selenium import webdriver
from selenium.webdriver.common.by import By
from selenium.webdriver.support.ui import WebDriverWait
from selenium.webdriver.support import expected_conditions as EC
from selenium.webdriver.common.keys import Keys
import time
from selenium.webdriver.chrome.options import Options
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
    for i in range(1, 2):
        url = "https://www.koreafashionnews.com/sub_read.html?uid=2142&section=sc1&section2="
        browser = create_browser(url)
        try:
            content_result = ""
            contents_list_container = WebDriverWait(browser, 10).until(EC.presence_of_element_located((By.ID, "textinput")))
            contents_list = contents_list_container.find_elements(By.XPATH, "./*")
            for contents in contents_list:
                try:
                    imgNode = contents.find_element(By.TAG_NAME, "img")
                    img = "<img src=\"" + imgNode.get_attribute("src") + "\" />"
                    content_result += img + '\n'
                except NoSuchElementException:
                    content_result += contents.text + '\n'
            print(content_result)
                
        finally:
            browser.quit()
            

if __name__ == "__main__":
    scrape_news()