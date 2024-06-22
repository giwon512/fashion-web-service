import React, { useState, useEffect } from "react";
import { Link, useParams, useLocation, useNavigate } from "react-router-dom";
import ReactQuill from "react-quill";
import "react-quill/dist/quill.snow.css";
import api from "../api";
import "./Admin.css";

const Admin = () => {
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
    const [bannerList, setBannerList] = useState([]);
    const [selectedBanner, setSelectedBanner] = useState(null);
    const [currentPage, setCurrentPage] = useState(page ? parseInt(page, 10) : 1);
    const [totalPages, setTotalPages] = useState(1);
    const [searchTerm, setSearchTerm] = useState(""); // 검색어 상태 추가
    const [isLoading, setIsLoading] = useState(false); // 로딩 상태 추가

    useEffect(() => {
        fetchAllNews();
        fetchRawNews(currentPage);
        fetchBanners();
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

    const fetchBanners = async () => {
        try {
            const response = await api.get("/banners");
            setBannerList(response.data);
        } catch (error) {
            console.error("Error fetching banners:", error);
        }
    };

    const handleEditNews = (news) => {
        setSelectedNews(news);
        setContent(news.content);
    };

    const handleEditBanner = (banner) => {
        setSelectedBanner(banner);
    };

    const handleContentChange = (value) => {
        setContent(value);
    };

    const handleSaveNews = async () => {
        if (selectedNews) {
            try {
                await api.put(`/raw-news/${selectedNews.newsId}`, {
                    ...selectedNews,
                    content,
                });
                // Immediately update state
                setRawNewsList(prevList => prevList.map(news =>
                    news.newsId === selectedNews.newsId ? { ...selectedNews, content } : news
                ));
                setSelectedNews(null);
                fetchAllNews(); // Fetch all news again to update filtered list
            } catch (error) {
                console.error("Error updating news:", error);
            }
        }
    };

    const handleSaveBanner = async () => {
        if (selectedBanner) {
            try {
                await api.put(`/banners/${selectedBanner.bannerId}`, selectedBanner);
                fetchBanners();
                setSelectedBanner(null);
            } catch (error) {
                console.error("Error updating banner:", error);
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
        navigate(`/admin?page=${newPage}`);
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

    const quillModules = {
        toolbar: [
            [{ header: "1" }, { header: "2" }, { header: "3" }, { font: [] }],
            [{ size: [] }],
            ["bold", "italic", "underline", "strike", "blockquote"],
            [{ list: "ordered" }, { list: "bullet" }],
            ["link", "image"],
            ["clean"],
        ],
    };

    const quillFormats = [
        "header",
        "font",
        "size",
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
        <div className="admin-container">
            <h1>Admin Panel</h1>

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
                            <input
                                type="text"
                                value={selectedNews.category}
                                onChange={(e) =>
                                    setSelectedNews({ ...selectedNews, category: e.target.value })
                                }
                            />
                        </label>
                        <button className="admin-button" onClick={handleSaveNews}>Save</button>
                        <button className="admin-button" onClick={() => setSelectedNews(null)}>Cancel</button>
                    </div>
                )}
            </div>

            <div className="admin-section">
                <h2>Manage Banners</h2>
                <table className="admin-banner-table">
                    <thead>
                    <tr>
                        <th>Title</th>
                        <th>Created Date</th>
                        <th>Actions</th>
                    </tr>
                    </thead>
                    <tbody>
                    {bannerList.map((banner) => (
                        <tr key={banner.bannerId}>
                            <td>{banner.title}</td>
                            <td>{new Date(banner.createdDate).toLocaleDateString()}</td>
                            <td>
                                <button className="admin-button" onClick={() => handleEditBanner(banner)}>Edit</button>
                            </td>
                        </tr>
                    ))}
                    </tbody>
                </table>
                {selectedBanner && (
                    <div className="admin-editor-container">
                        <h2>Edit Banner</h2>
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
                                value={selectedBanner.url}
                                onChange={(e) =>
                                    setSelectedBanner({ ...selectedBanner, url: e.target.value })
                                }
                            />
                        </label>
                        <label>
                            Description:
                            <ReactQuill
                                value={selectedBanner.description}
                                onChange={(value) =>
                                    setSelectedBanner({ ...selectedBanner, description: value })
                                }
                                modules={quillModules}
                                formats={quillFormats}
                            />
                        </label>
                        <button className="admin-button" onClick={handleSaveBanner}>Save</button>
                        <button className="admin-button" onClick={() => setSelectedBanner(null)}>Cancel</button>
                    </div>
                )}
            </div>
        </div>
    );
};

export default Admin;
