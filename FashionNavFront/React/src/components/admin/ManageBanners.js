import React, { useState, useEffect } from "react";
import ReactQuill from "react-quill";
import "react-quill/dist/quill.snow.css";
import api from "../../api";
import "./Admin.css";

const ManageBanners = () => {
    const [bannerList, setBannerList] = useState([]);
    const [selectedBanner, setSelectedBanner] = useState(null);
    const [isLoading, setIsLoading] = useState(false); // 로딩 상태 추가

    useEffect(() => {
        fetchBanners();
    }, []);

    const fetchBanners = async () => {
        setIsLoading(true); // 로딩 시작
        try {
            const response = await api.get("/banners");
            setBannerList(response.data);
        } catch (error) {
            console.error("Error fetching banners:", error);
        }
        setIsLoading(false); // 로딩 종료
    };

    const handleEditBanner = (banner) => {
        setSelectedBanner(banner);
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

    const handleContentChange = (value) => {
        setSelectedBanner((prevBanner) => ({
            ...prevBanner,
            description: value,
        }));
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
        <div className="admin-section">
            <h2>Manage Banners</h2>
            {isLoading ? (
                <div>Loading...</div> // 로딩 중일 때 표시할 내용
            ) : (
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
            )}
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
                            onChange={handleContentChange}
                            modules={quillModules}
                            formats={quillFormats}
                        />
                  a  </label>
                    <button className="admin-button" onClick={handleSaveBanner}>Save</button>
                    <button className="admin-button" onClick={() => setSelectedBanner(null)}>Cancel</button>
                </div>
            )}
        </div>
    );
};

export default ManageBanners;
