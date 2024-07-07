// src/components/CategoryNews.js
import React, { useEffect, useState } from 'react';
import { Link } from 'react-router-dom';
import api from '../api';
import './CategoryNews.css';

const CategoryNews = ({ category, isLoggedIn }) => {
    const [newsList, setNewsList] = useState([]);
    const [imgList, setImgList] = useState([]);
    const [isLoading, setIsLoading] = useState(true);

    useEffect(() => {
        const getNews = async () => {
            if(!isLoggedIn){
                await api.get(`/top3?category=${category}`)
                    .then(response => setNewsList(response.data[category]))
                    .catch(error => console.error('Error fetching category news:', error));
            }
            else{
                await api.get(`/top3/prefer?category=${category}`)
                    .then(response => setNewsList(response.data))
                    .catch(error => console.error('Error fetching category news:', error));
            }
        }

        getNews();
    }, [category]);

    useEffect(() => {
        const getImages = async () => {
            let imgContent = []
            for (const news of newsList) {
                await api.get(`/top3/${news.newsId}`)
                    .then(response => imgContent.push(response.data))
                    .catch(error => console.error('Error fetching news images', error));
            }
            setImgList(imgContent);
            setIsLoading(false);
        }

        getImages();
    }, [newsList]);

    if(isLoading) {
        return <h2>Loading...</h2>
    }

    return (
        <div className="category-news">
            <div className="category-header">
                <h2>{category.toUpperCase()} NEWS</h2>
                <Link to={`/news/${category}`} className="more-link">MORE &gt;&gt;</Link>
            </div>
            <div className="news-items">
                {newsList.length != 0 && newsList.map((news, index) => (
                    <div key={news.newsId} className="news-item">
                        <Link to={`/news/details/${news.newsId}`}>
                            <div className="news-image">
                                <img src={"data:image/jpg;base64,"+imgList[index]} alt={news.title} />
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