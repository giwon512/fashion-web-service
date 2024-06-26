import React, { useState, useEffect } from "react";
import { Link, useNavigate, useLocation } from "react-router-dom";
import ReactQuill from "react-quill";
import "react-quill/dist/quill.snow.css";
import api from "../../api";
import "./Admin.css";

const ManageNews = () => {
    const location = useLocation();
    const queryParams = new URLSearchParams(location.search);
    const editNewsId = queryParams.get('editNewsId');
    const page = queryParams.get('page');
    const navigate = useNavigate();

    const [allNewsList, setAllNewsList] = useState([]);
    const [rawNewsList, setRawNewsList] = useState([]);
    const [filteredNewsList, setFilteredNewsList] = useState([]);
    const [selectedNews, setSelectedNews] = useState(null);
    const [content, setContent] = useState("");
    const [currentPage, setCurrentPage] = useState(page ? parseInt(page, 10) : 1);
    const [totalPages, setTotalPages] = useState(1);
    const [searchTerm, setSearchTerm] = useState(""); // 검색어 상태 추가
    const [isLoading, setIsLoading] = useState(false); // 로딩 상태 추가

    const categories = ["celeb", "brand", "trend"]; // 카테고리 목록

    useEffect(() => {
        fetchAllNews();
        fetchRawNews(currentPage);
    }, [currentPage]);

    useEffect(() => {
        if (editNewsId) {
            api.get(`/raw-news/${editNewsId}`)
                .then(response => {
                    setSelectedNews(response.data);
                    setContent(response.data.content);
                    const newsIndex = rawNewsList.findIndex(news => news.newsId === parseInt(editNewsId));
                    if (newsIndex !== -1) {
                        const pageIndex = Math.floor(newsIndex / 10) + 1;
                        setCurrentPage(pageIndex);
                    }
                })
                .catch(error => console.error('Error fetching news:', error));
        }
    }, [editNewsId, rawNewsList]);

    useEffect(() => {
        handleSearch(); // 검색어 상태가 변경될 때마다 필터링된 뉴스 항목 업데이트
    }, [searchTerm, allNewsList]);

    const fetchAllNews = async () => {
        try {
            const response = await api.get(`/raw-news?pageNum=1&pageSize=1000`);
            setAllNewsList(response.data.content);
        } catch (error) {
            console.error("Error fetching all news:", error);
        }
    };

    const fetchRawNews = async (page) => {
        setIsLoading(true); // 로딩 시작
        try {
            const response = await api.get(`/raw-news?pageNum=${page}&pageSize=10`);
            setRawNewsList(response.data.content);
            setTotalPages(response.data.totalPages);
        } catch (error) {
            console.error("Error fetching raw news:", error);
        }
        setIsLoading(false); // 로딩 종료
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
                const editor = document.querySelector('.ql-editor');
                const updatedContent = editor.innerHTML;
                await api.put(`/raw-news/${selectedNews.newsId}`, {
                    ...selectedNews,
                    content: updatedContent,
                });
                // Immediately update state
                setRawNewsList(prevList => prevList.map(news =>
                    news.newsId === selectedNews.newsId ? { ...selectedNews, content: updatedContent } : news
                ));
                setSelectedNews(null);
                fetchAllNews(); // Fetch all news again to update filtered list
            } catch (error) {
                console.error("Error updating news:", error);
            }
        }
    };

    const handleDeleteNews = async (newsId) => {
        try {
            await api.delete(`/raw-news/${newsId}`);
            setRawNewsList(prevList => prevList.filter(news => news.newsId !== newsId));
            fetchAllNews(); // Fetch all news again to update filtered list
        } catch (error) {
            console.error("Error deleting news:", error);
        }
    };

    const handlePageChange = (newPage) => {
        setCurrentPage(newPage);
        navigate(`/admin/news?page=${newPage}`);
    };

    const handleSearch = () => {
        let filtered = allNewsList;

        if (searchTerm) {
            filtered = allNewsList.filter(news =>
                (news.title && news.title.toLowerCase().includes(searchTerm.toLowerCase())) ||
                (news.category && news.category.toLowerCase().includes(searchTerm.toLowerCase()))
            );
        }

        setFilteredNewsList(filtered.slice((currentPage - 1) * 10, currentPage * 10));
        setTotalPages(Math.ceil(filtered.length / 10));
    };

    const handleSearchChange = (e) => {
        setSearchTerm(e.target.value);
        setCurrentPage(1); // 검색어가 변경될 때 페이지를 1로 리셋
    };

    const handleAutoAlign = () => {
        const editor = document.querySelector('.ql-editor');
        if (editor) {
            editor.querySelectorAll('img').forEach(img => {
                img.style.display = 'block';
                img.style.margin = '0 auto';
                img.style.maxWidth = '100%';
            });
            editor.querySelectorAll('p').forEach(p => {
                p.style.margin = '10px 0';
            });
        }
    };

    const quillModules = {
        toolbar: [
            [{ header: [false] }], // 헤더 옵션을 제거
            ["bold", "italic", "underline", "strike", "blockquote"],
            [{ list: "ordered" }, { list: "bullet" }],
            ["link", "image"],
            ["clean"],
        ],
    };

    const quillFormats = [
        "bold",
        "italic",
        "underline",
        "strike",
        "blockquote",
        "list",
        "bullet",
        "link",
        "image",
    ];

    return (
        <div className="admin-section">
            <h2>Manage Raw News</h2>
            <input
                type="text"
                placeholder="Search..."
                value={searchTerm}
                onChange={handleSearchChange}
                className="admin-search-input"
            />
            {isLoading ? (
                <div>Loading...</div> // 로딩 중일 때 표시할 내용
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
                        {filteredNewsList.map((news) => (
                            <tr key={news.newsId}>
                                <td><Link to={`/news/details/${news.newsId}`}>{news.title}</Link></td>
                                <td>{news.category}</td>
                                <td>{new Date(news.publishedDate).toLocaleDateString()}</td>
                                <td><Link to={`/news/details/${news.newsId}`}>{`/news/details/${news.newsId}`}</Link></td>
                                <td>
                                    <button className="admin-button" onClick={() => handleEditNews(news)}>Edit</button>
                                    <button className="admin-button" onClick={() => handleDeleteNews(news.newsId)}>Delete</button>
                                </td>
                            </tr>
                        ))}
                        </tbody>
                    </table>
                    <div className="admin-pagination">
                        {Array.from({ length: totalPages }, (_, index) => (
                            <button
                                key={index}
                                className={`admin-button ${index + 1 === currentPage ? "admin-active" : ""}`}
                                onClick={() => handlePageChange(index + 1)}
                            >
                                {index + 1}
                            </button>
                        ))}
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
                            value={content}
                            onChange={handleContentChange}
                            modules={quillModules}
                            formats={quillFormats}
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
        </div>
    );
};

export default ManageNews;
