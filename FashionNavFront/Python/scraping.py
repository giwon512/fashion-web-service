import CrawlingFIles.fashionn_crawling as FN
import CrawlingFIles.fi_crawling as FI
import CrawlingFIles.KFN_crawling as KFN

def main():
    FN.scrape_news()
    FI.scrape_news()
    KFN.scrape_news()

if __name__ == "__main__":
    main()