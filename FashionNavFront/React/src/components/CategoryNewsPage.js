// src/components/CategoryNewsPage.js
import React, { useEffect, useState } from 'react';
import { useParams, Link } from 'react-router-dom';
import api from '../api';
import './CategoryNewsPage.css';

const CategoryNewsPage = () => {
    const { category } = useParams();
    const [newsList, setNewsList] = useState([]);
    const [currentPage, setCurrentPage] = useState(1);
    const [totalPages, setTotalPages] = useState(1);
    const pageSize = 10; // 한 페이지에 보여줄 뉴스 항목 수

    useEffect(() => {
        fetchNews(currentPage);
    }, [category, currentPage]);

    const fetchNews = async (page) => {
        try {
            const response = await api.get(`/news/category/${category}?pageNum=${page}&pageSize=${pageSize}`);
            const data = response.data;
            if (data && data.content) {
                setNewsList(data.content);
                setCurrentPage(data.currentPage);
                setTotalPages(data.totalPages);
                console.log(totalPages);
            } else {
                setNewsList([]);
            }
        } catch (error) {
            console.error('Error fetching category news:', error);
            setNewsList([]);
        }
    };

    const handlePageChange = (page) => {
        setCurrentPage(page);
    };

    return (
        <div className="category-news-page">
            <h2 className="cnp-page-title">{category.toUpperCase()} News</h2>
            <div className="cnp-news-items">
                {newsList.length > 0 ? (
                    newsList.map(news => (
                        <Link to={`/news/details/${news.newsId}`} key={news.newsId} className="cnp-news-item-link">
                            <div className="cnp-news-item">
                                <img src={news.imageUrl} alt={news.title} className="cnp-news-image" />
                                <div className="cnp-news-text">
                                    <h3 className="cnp-news-title">{news.title}</h3>
                                    <p className="cnp-news-summary">{news.summary}</p>
                                    <span className="cnp-news-date">{new Date(news.publishedDate).toLocaleDateString()}</span>
                                </div>
                            </div>
                        </Link>
                    ))
                ) : (
                    <p>No news available in this category.</p>
                )}
            </div>
            <div className="pagination">
                {Array.from({ length: totalPages }, (_, index) => (
                    <button
                        key={index}
                        className={index + 1 === currentPage ? 'active' : ''}
                        onClick={() => handlePageChange(index + 1)}
                    >
                        {index + 1}
                    </button>
                ))}
            </div>
        </div>
    );
};

export default CategoryNewsPage;
