import React, { useState, useEffect } from 'react';
import { Link, useNavigate } from 'react-router-dom'; // useNavigate 추가
import api from '../api';
import './SavedContent.css';

const SavedContent = () => {
    const [savedPages, setSavedPages] = useState([]);
    const [loading, setLoading] = useState(true); // 로딩 상태 추가
    const [error, setError] = useState(null); // 에러 상태 추가
    const navigate = useNavigate(); // useNavigate 훅 사용

    useEffect(() => {
        const fetchSavedPages = async () => {
            try {
                const response = await api.get('/page/archive');
                console.log('Response:', response); // 응답 데이터 확인
                if (response.data && response.data.body) {
                    setSavedPages(response.data.body);
                } else {
                    console.error('No data body found');
                    setError('저장된 페이지를 불러오는 데 실패했습니다.');
                }
            } catch (error) {
                console.error('저장된 페이지를 불러오는 데 실패했습니다:', error);
                setError('저장된 페이지를 불러오는 데 실패했습니다.');
            } finally {
                setLoading(false); // 로딩 상태 업데이트
            }
        };

        fetchSavedPages();
    }, []);

    if (loading) {
        return <div>Loading...</div>; // 로딩 상태 표시
    }

    if (error) {
        return <div>{error}</div>; // 에러 메시지 표시
    }

    return (
        <div className="saved-content-container">
            <h3>저장된 콘텐츠</h3>
            {savedPages.length === 0 ? (
                <p>저장된 콘텐츠가 없습니다.</p>
            ) : (
                <ul>
                    {savedPages.map(page => (
                        <li key={page.newsId}>
                            <Link to={`/news/details/${page.newsId}`}>{page.title}</Link>
                        </li>
                    ))}
                </ul>
            )}
            <button className="mypage-button" onClick={() => navigate('/mypage')}>
                마이페이지로 이동
            </button>
        </div>
    );
};

export default SavedContent;