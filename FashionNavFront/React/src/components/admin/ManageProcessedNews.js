import React, { useState, useEffect, useRef } from "react";
import { Link, useNavigate, useLocation } from "react-router-dom";
import ReactQuill from "react-quill";
import "react-quill/dist/quill.snow.css";
import api from "../../api";
import "./Admin.css";

const ManageProcessedNews = () => {
  const location = useLocation();
  const queryParams = new URLSearchParams(location.search);
  const page = queryParams.get('page');
  const editNewsId = queryParams.get('editNewsId');
  const navigate = useNavigate();

  const [processedNewsList, setProcessedNewsList] = useState([]);
  const [selectedNews, setSelectedNews] = useState(null);
  const [content, setContent] = useState("");
  const [currentPage, setCurrentPage] = useState(page ? parseInt(page, 10) : 1);
  const [totalPages, setTotalPages] = useState(1);
  const [isLoading, setIsLoading] = useState(false);
  const [pageGroup, setPageGroup] = useState(0);
  const [selectedBanner, setSelectedBanner] = useState(null);
  const [imgList, setImgList] = useState([]);

  const quillRef = useRef(null);

  useEffect(() => {
    fetchProcessedNews(currentPage);
  }, [currentPage]);

  useEffect(() => {
    if (editNewsId) {
      const newsToEdit = processedNewsList.find(news => news.newsId === parseInt(editNewsId));
      if (newsToEdit) {
        setSelectedNews(newsToEdit);
        setContent(newsToEdit.content);
      }
    }
  }, [editNewsId, processedNewsList]);

  const fetchProcessedNews = async (page) => {
    setIsLoading(true);
    try {
      const response = await api.get(`/processed-news?pageNum=${page}&pageSize=10`);
      setProcessedNewsList(response.data.content);
      setTotalPages(response.data.totalPages);
      setImgList(response.data.imgContent);
    } catch (error) {
      console.error("Error fetching processed news:", error);
    }
    setIsLoading(false);
  };

  const handleEditNews = (news) => {
    setSelectedNews(news);
    setContent(news.content);
  };

  const handleContentChange = (value) => {
    setContent(value);
  };

  const handleCategoryChange = (e) => {
    setSelectedNews({ ...selectedNews, category: e.target.value });
  };

  const handleSaveNews = async () => {
    if (selectedNews) {
      try {
        const editor = quillRef.current.getEditor();
        const updatedContent = editor.root.innerHTML;
        await api.put(`/processed-news/${selectedNews.newsId}`, {
          ...selectedNews,
          content: updatedContent,
        });
        setProcessedNewsList(prevList => prevList.map(news =>
            news.newsId === selectedNews.newsId ? { ...selectedNews, content: updatedContent } : news
        ));
        setSelectedNews(null);
      } catch (error) {
        console.error("Error updating news:", error);
      }
    }
  };

  const handleDeleteNews = async (newsId) => {
    try {
      await api.delete(`/processed-news/${newsId}`);
      setProcessedNewsList(prevList => prevList.filter(news => news.newsId !== newsId));
    } catch (error) {
      console.error("Error deleting news:", error);
    }
  };

  const handlePageChange = (newPage) => {
    setCurrentPage(newPage);
    navigate(`/admin/processed-news?page=${newPage}`);
  };

  const handleAutoAlign = () => {
    const editor = quillRef.current.getEditor();
    if (editor) {
      editor.root.querySelectorAll('img').forEach(img => {
        img.style.display = 'block';
        img.style.margin = '0 auto';
        img.style.maxWidth = '100%';
      });
      editor.root.querySelectorAll('p').forEach(p => {
        p.style.margin = '10px 0';
      });
    }
  };

  const pagesPerGroup = 10;

  const handleNextGroup = () => {
    if ((pageGroup + 1) * pagesPerGroup < totalPages) {
      setPageGroup(pageGroup + 1);
    }
  };

  const handlePrevGroup = () => {
    if (pageGroup > 0) {
      setPageGroup(pageGroup - 1);
    }
  };

  const startPage = pageGroup * pagesPerGroup + 1;
  const endPage = Math.min(startPage + pagesPerGroup - 1, totalPages);

  const categories = ["celeb", "brand", "trend"];

  const handleAddToBanner = (news) => {
    setSelectedBanner({...news, description : news.subtitle, imageUrl : imgList[news.newsId]});
  };

  const handleSaveBanner = async () => {
    if (selectedBanner) {
      try {
        const { content, ...bannerData } = selectedBanner;
        const bannerUrl = `/news/details/${selectedBanner.newsId}`;
        await api.post("/processedbanner", { ...bannerData, url: bannerUrl });
        setSelectedBanner(null);
        alert("News added to banner successfully!");
      } catch (error) {
        console.error("Error adding news to banner:", error);
      }
    }
  };

  return (
      <div className="admin-section">
        <h2>Manage Processed News</h2>
        {isLoading ? (
            <div>Loading...</div>
        ) : (
            <>
              <table className="admin-news-table">
                <thead>
                <tr>
                  <th>Title</th>
                  <th>Category</th>
                  <th>Published Date</th>
                  <th>URL</th>
                  <th>Actions</th>
                </tr>
                </thead>
                <tbody>
                {processedNewsList.map((news) => (
                    <tr key={news.newsId}>
                      <td><Link to={`/news/details/${news.newsId}`}>{news.title}</Link></td>
                      <td>{news.category}</td>
                      <td>{new Date(news.publishedDate).toLocaleDateString()}</td>
                      <td><Link to={`/news/details/${news.newsId}`}>{`/news/details/${news.newsId}`}</Link></td>
                      <td>
                        <button className="admin-button" onClick={() => handleEditNews(news)}>Edit</button>
                        <button className="admin-button" onClick={() => handleDeleteNews(news.newsId)}>Delete</button>
                        <button className="admin-button" onClick={() => handleAddToBanner(news)}>Add to Banner</button>
                      </td>
                    </tr>
                ))}
                </tbody>
              </table>
              <div className="admin-pagination">
                <button
                    className="admin-pagination-button"
                    onClick={handlePrevGroup}
                    disabled={pageGroup === 0}
                >
                  Previous
                </button>
                {Array.from({ length: endPage - startPage + 1 }, (_, index) => (
                    <button
                        key={index}
                        className={`admin-pagination-button ${index + startPage === currentPage ? "admin-pagination-active" : ""}`}
                        onClick={() => handlePageChange(index + startPage)}
                    >
                      {index + startPage}
                    </button>
                ))}
                <button
                    className="admin-pagination-button"
                    onClick={handleNextGroup}
                    disabled={endPage === totalPages}
                >
                  Next
                </button>
              </div>
            </>
        )}
        {selectedNews && (
            <div className="admin-editor-container">
              <h2>Edit News</h2>
              <label>
                Title:
                <input
                    type="text"
                    value={selectedNews.title}
                    onChange={(e) =>
                        setSelectedNews({ ...selectedNews, title: e.target.value })
                    }
                />
              </label>
              <label>
                Content:
                <ReactQuill
                    ref={quillRef}
                    value={content}
                    onChange={handleContentChange}
                />
              </label>
              <label>
                Image URL:
                <input
                    type="text"
                    value={selectedNews.imageUrl}
                    onChange={(e) =>
                        setSelectedNews({ ...selectedNews, imageUrl: e.target.value })
                    }
                />
              </label>
              <label>
                Source:
                <input
                    type="text"
                    value={selectedNews.source}
                    onChange={(e) =>
                        setSelectedNews({ ...selectedNews, source: e.target.value })
                    }
                />
              </label>
              <label>
                Author:
                <input
                    type="text"
                    value={selectedNews.author}
                    onChange={(e) =>
                        setSelectedNews({ ...selectedNews, author: e.target.value })
                    }
                />
              </label>
              <label>
                Category:
                <div className="radio-group">
                  {categories.map(category => (
                      <label key={category} className="radio-label">
                        <input
                            type="radio"
                            value={category}
                            checked={selectedNews.category === category}
                            onChange={handleCategoryChange}
                        />
                        {category}
                      </label>
                  ))}
                </div>
              </label>
              <button className="admin-button" onClick={handleSaveNews}>Save</button>
              <button className="admin-button" onClick={() => setSelectedNews(null)}>Cancel</button>
              <button className="admin-button" onClick={handleAutoAlign}>Auto Align</button> {/* 자동 정렬 버튼 추가 */}
            </div>
        )}
        {selectedBanner && (
            <div className="admin-editor-container">
              <h2>Add News to Banner</h2>
              <label>
                Title:
                <input
                    type="text"
                    value={selectedBanner.title}
                    onChange={(e) =>
                        setSelectedBanner({ ...selectedBanner, title: e.target.value })
                    }
                />
              </label>
              <label>
                Content:
                <ReactQuill
                    value={selectedBanner.content}
                    onChange={(value) =>
                        setSelectedBanner({ ...selectedBanner, content: value })
                    }
                />
              </label>
              <label>
                Image URL:
                <input
                    type="text"
                    value={selectedBanner.imageUrl}
                    onChange={(e) =>
                        setSelectedBanner({ ...selectedBanner, imageUrl: e.target.value })
                    }
                />
              </label>
              <label>
                URL:
                <input
                    type="text"
                    value={`/news/details/${selectedBanner.newsId}`}
                    readOnly
                />
              </label>
              <label>
                Description:
                <input
                    type="text"
                    value={selectedBanner.description}
                    onChange={(e) =>
                        setSelectedBanner({ ...selectedBanner, description: e.target.value })
                    }
                />
              </label>
              <button className="admin-button" onClick={handleSaveBanner}>Save</button>
              <button className="admin-button" onClick={() => setSelectedBanner(null)}>Cancel</button>
            </div>
        )}
      </div>
  );
};

export default ManageProcessedNews;
