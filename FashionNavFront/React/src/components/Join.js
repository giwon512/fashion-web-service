// src/components/Join.js
import React from 'react';
import { Link } from 'react-router-dom';
import './Join.css';

const Join = () => {
    return (
        <div className="join-container">
            <h1>회원가입</h1>
            <p>Fashionn에 오신 것을 환영합니다. 회원가입을 하시면 더 많은 혜택을 누리실 수 있습니다.</p>
            <Link to="/join-agree" className="join-button">회원가입</Link>
        </div>
    );
};

export default Join;
