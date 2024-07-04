import React, { useEffect, useState } from 'react';
import api from '../api';
import './AllNews.css';
import { Link } from 'react-router-dom';

const AllNews = () => {
    const [newsList, setNewsList] = useState([]);
    const [currentPage, setCurrentPage] = useState(1);
    const [totalPages, setTotalPages] = useState(1);

    useEffect(() => {
        fetchNews(currentPage);
    }, [currentPage]);

    const fetchNews = async (page) => {
        try {
            const response = await api.get(`/processed-news?pageNum=${page}&pageSize=10`);
            setNewsList(response.data.content);
            setTotalPages(response.data.totalPages);
        } catch (error) {
            console.error('Error fetching news:', error);
        }
    };

    const renderPaginationButtons = () => {
        const buttons = [];
        const maxButtons = 5; // 한 번에 표시할 최대 버튼 수

        let startPage = Math.max(1, currentPage - Math.floor(maxButtons / 2));
        let endPage = Math.min(totalPages, startPage + maxButtons - 1);

        if (endPage - startPage + 1 < maxButtons) {
            startPage = Math.max(1, endPage - maxButtons + 1);
        }

        // 이전 페이지 버튼
        buttons.push(
            <button
                key="prev"
                onClick={() => setCurrentPage(prev => Math.max(1, prev - 1))}
                disabled={currentPage === 1}
            >
                Previous
            </button>
        );

        // 페이지 번호 버튼
        for (let i = startPage; i <= endPage; i++) {
            buttons.push(
                <button
                    key={i}
                    className={i === currentPage ? 'active' : ''}
                    onClick={() => setCurrentPage(i)}
                >
                    {i}
                </button>
            );
        }

        // 다음 페이지 버튼
        buttons.push(
            <button
                key="next"
                onClick={() => setCurrentPage(prev => Math.min(totalPages, prev + 1))}
                disabled={currentPage === totalPages}
            >
                Next
            </button>
        );

        return buttons;
    };

    return (
        <div className="all-news-section">
            <h2>All News</h2>
            <div className="news-container">
                {newsList.map((news) => (
                    <Link to={`/news/details/${news.newsId}`} key={news.newsId} className="news-link">
                        <div className="news-card">
                            <img src={news.imageUrl} alt={news.title} className="news-image" />
                            <div className="news-content">
                                <h3>{news.title}</h3>
                                <p>{news.summary}</p>
                                <span className="news-date">{news.date}</span>
                            </div>
                        </div>
                    </Link>
                ))}
            </div>
            <div className="pagination">
                {renderPaginationButtons()}
            </div>
        </div>
    );
};

export default AllNews;