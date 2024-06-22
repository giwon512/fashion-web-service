import React, { useEffect, useState } from 'react';
import { useParams, useNavigate, useLocation } from 'react-router-dom';
import api from '../api';
import './NewsDetail.css';

const NewsDetail = ({ isAdmin }) => {
    const { newsId } = useParams();
    const [news, setNews] = useState(null);
    const [allNewsList, setAllNewsList] = useState([]);
    const navigate = useNavigate();

    useEffect(() => {
        api.get(`/news/detail/${newsId}`)
            .then(response => setNews(response.data))
            .catch(error => console.error('Error fetching news detail:', error));
    }, [newsId]);

    useEffect(() => {
        api.get('/raw-news?pageNum=1&pageSize=1000') // 큰 페이지 사이즈로 모든 뉴스 가져오기
            .then(response => setAllNewsList(response.data.content))
            .catch(error => console.error('Error fetching all news:', error));
    }, []);

    const handlePreviousClick = () => {
        const currentIndex = allNewsList.findIndex(news => news.newsId === parseInt(newsId));
        if (currentIndex > 0) {
            navigate(`/news/details/${allNewsList[currentIndex - 1].newsId}`);
        }
    };

    const handleNextClick = () => {
        const currentIndex = allNewsList.findIndex(news => news.newsId === parseInt(newsId));
        if (currentIndex < allNewsList.length - 1) {
            navigate(`/news/details/${allNewsList[currentIndex + 1].newsId}`);
        }
    };

    const handleListClick = () => {
        const category = news?.category;
        if (category) {
            navigate(`/news/${category}`);
        } else {
            navigate('/'); // 카테고리가 지정되지 않은 경우 메인 페이지로 이동
        }
    };

    const handleEditClick = () => {
        const currentPage = Math.ceil((allNewsList.findIndex(news => news.newsId === parseInt(newsId)) + 1) / 10);
        navigate(`/admin?editNewsId=${newsId}&page=${currentPage}`);
    };

    if (!news) {
        return <div>Loading...</div>;
    }

    return (
        <div className="news-detail-container">
            <div className="news-detail">
                {isAdmin && (
                    <div className="edit-button-container">
                        <button className="edit-button" onClick={handleEditClick}>Edit</button>
                    </div>
                )}
                <h2>{news.title}</h2>
                <div dangerouslySetInnerHTML={{ __html: news.content }} />
                <div className="navigation-buttons">
                    <button
                        className="previous-button"
                        onClick={handlePreviousClick}
                        disabled={allNewsList.findIndex(news => news.newsId === parseInt(newsId)) === 0}
                    >
                        이전
                    </button>
                    <button
                        className="list-button"
                        onClick={handleListClick}
                    >
                        목록
                    </button>
                    <button
                        className="next-button"
                        onClick={handleNextClick}
                        disabled={allNewsList.findIndex(news => news.newsId === parseInt(newsId)) === allNewsList.length - 1}
                    >
                        다음
                    </button>
                </div>
            </div>
        </div>
    );
};

export default NewsDetail;
