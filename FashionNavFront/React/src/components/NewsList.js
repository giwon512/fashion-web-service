import React, { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import api from "../api";
import "./NewsList.css"; // CSS 파일 추가

const NewsList = () => {
  const [newsByCategory, setNewsByCategory] = useState({});
  const [error, setError] = useState("");
  const navigate = useNavigate();

  useEffect(() => {
    api
      .get("/news/top3")
      .then((response) => {
        setNewsByCategory(response.data);
      })
      .catch((error) => {
        console.error("Error fetching news summaries:", error);
        setError("Failed to fetch news summaries");
      });
  }, []);

  const handleNewsClick = (newsId) => {
    navigate(`/news/details/${newsId}`);
  };

  const handleMoreClick = (category) => {
    navigate(`/news/category/${category}`);
  };

  return (
    <div className="news-list">
      {error && <p className="error">{error}</p>}
      {Object.entries(newsByCategory).map(([category, newsList]) => (
        <div key={category} className="news-category">
          <h2>{category}</h2>
          <div className="news-items">
            {newsList.map((news) => (
              <div
                key={news.newsId}
                className="news-item"
                onClick={() => handleNewsClick(news.newsId)}
              >
                <img src={news.imageUrl} className="news-image" />
                <h3 className="news-title">{news.title}</h3>
                <p className="news-content">{news.content}</p>
              </div>
            ))}
          </div>
          <div className="more-link">
            <button
              className="more-button"
              onClick={() => handleMoreClick(category)}
            >
              MORE
            </button>
          </div>
        </div>
      ))}
    </div>
  );
};

export default NewsList;
