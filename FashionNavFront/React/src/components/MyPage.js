import React, { useState, useEffect } from 'react';
import { Link } from 'react-router-dom';
import api from '../api';
import './MyPage.css';

const MyPage = () => {
    const [user, setUser] = useState(null);

    useEffect(() => {
        api
            .get('/users/me')
            .then((response) => {
                setUser(response.data.body);
            })
            .catch((error) => {
                console.error('Failed to fetch user info', error);
            });
    }, []);

    return (
        <div className="mypage-container">
            <div className="profile-section">
                <div className="profile-image"></div>
                <div className="username">{user?.name || 'userName'}</div>
            </div>
            <div className="menu-section">
                <ul>
                    <li><Link to="/edit-profile">내 정보 관리</Link></li>
                    <li>favorite</li>
                    <li><Link to="/mypage/saved-content">저장한 콘텐츠</Link></li>
                    <li><Link to="/mypage/my-comments">내가 쓴 댓글</Link></li>
                    <li>
                        <Link to="/survey-management">설문 관리</Link>
                    </li>
                </ul>
            </div>
        </div>
    );
};

export default MyPage;