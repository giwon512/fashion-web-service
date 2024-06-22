// src/components/CategoryNews.js
import React, { useEffect, useState } from 'react';
import { Link } from 'react-router-dom';
import api from '../api';
import './CategoryNews.css';

const CategoryNews = ({ category }) => {
    const [newsList, setNewsList] = useState([]);

    useEffect(() => {
        api.get(`/top3?category=${category}`)
            .then(response => setNewsList(response.data[category]))
            .catch(error => console.error('Error fetching category news:', error));
    }, [category]);

    return (
        <div className="category-news">
            <div className="category-header">
                <h2>{category.toUpperCase()} NEWS</h2>
                <Link to={`/news/${category}`} className="more-link">MORE &gt;&gt;</Link>
            </div>
            <div className="news-items">
                {newsList.map(news => (
                    <div key={news.newsId} className="news-item">
                        <Link to={`/news/details/${news.newsId}`}>
                            <div className="news-image">
                                <img src={news.imageUrl} alt={news.title} />
                            </div>
                            <div className="news-text">
                                <h3>{news.title}</h3>
                            </div>
                        </Link>
                    </div>
                ))}
            </div>
        </div>
    );
};

export default CategoryNews;
