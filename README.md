# fashion-web-service

## 프로젝트명
패션 포털 웹 서비스 시스템 개발

## 프로젝트 목적
- 최신 패션 트렌드에 대한 정보를 보여주는 사이트는 많으나 정작 나에 대한 맞춤 트렌드를 보여주는 사이트를 찾기 어려움
- Crawling을 통하여 여러 사이트에 산개하여 있는 패션 트렌드 데이터들을 수집하여 분류체계 수립
- 분류한 데이터를 기반으로 사용자에 맞는 패션 트렌드를 추천할 수 있는 웹 서비스 개발

## 💾 프로젝트 상세 내용 PPT
https://github.com/giwon512/fashion-web-service/blob/main/%ED%94%84%EB%A1%9C%EC%A0%9D%ED%8A%B8-%EC%B5%9C%EC%A2%85.pptx

# 📚 활용된 개발 언어 및 환경

<img src="https://github.com/user-attachments/assets/14929a47-6501-4f24-a604-ed455f6ead68" />

# 🗂️ 프로젝트 구조
🗂️FashionNav - Spring Boot를 활용한 백엔드 로직

🗂️FashionNavFront - React를 활용한 프론트엔드 로직 및 크롤링을 위한 파이썬 코드

🗂️FashionTrend - 프로젝트 초기에 JSP로 제작한 목업 홈페이지 코드

# 📚 API 명세
회원 관리를 위한 User API - https://giwon512.github.io/project/userAPI/

컨텐츠 관리를 위한 User API - https://giwon512.github.io/project/newsAPI/

메인 서비스 로직을 위한 User API - https://giwon512.github.io/project/surveyAPI/

# 🗂️ 서비스 사이트맵 구조
<img src="https://github.com/user-attachments/assets/886b8c5c-8fe5-4ce5-93e7-196d42799692" />

## 프로젝트 수행내용
- Crawling을 이용하여 데이터 수집 및 분류체계 구성
- Spring boot 기반의 Web Project 구축
- MariaDB를 주DB로 사용
- Sprint Security를 사용하여 login 인증 처리

## 추후 목표
- 각 사용자의 뉴스 클릭 히스토리를 분석하여 그가 좋아할 것으로 예측되는 뉴스를 추천해주는 시스템 개발
