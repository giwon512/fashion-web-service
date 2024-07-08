import React, { useEffect, useState } from 'react';
import { Link } from 'react-router-dom';
import api from '../api';
import './LatestArticles.css';

const LatestArticles = () => {
    const [articles, setArticles] = useState([]);
    const [imgList, setImgList] = useState({});

    useEffect(() => {
        api.get(`/processed-news?pageNum=1&pageSize=9`)
            .then(response => {
                const newsData = response.data.content;
                if (Array.isArray(newsData)) {
                    setArticles(newsData);
                    setImgList(response.data.imgContent);
                } else {
                    console.error('Invalid data format:', newsData);
                }
            })
            .catch(error => console.error('Error fetching latest articles:', error));
    }, []);

    return (
        <div className="latest-articles-section">
            <hr className="top-divider" /> {/* 구분선 */}
            <div className="section-header">
                <h2>Latest Articles</h2>
                <Link to="/all-news" className="more-link">MORE>></Link>
            </div>
            <div className="articles-container">
                {articles.map(article => (
                    <Link to={`/news/details/${article.newsId}`} key={article.newsId} className="article-link">
                        <div className="article-card">
                            <img src={"data:image/jpg;base64,"+ imgList[article.newsId]} alt={article.title} className="article-image" />
                            <div className="article-content">
                                <h3>{article.title}</h3>
                                <p>{article.summary}</p>
                                <span className="article-date">{article.date}</span>
                            </div>
                        </div>
                    </Link>
                ))}
            </div>
            <hr className="articles-divider" />
        </div>
    );
};

export default LatestArticles;
