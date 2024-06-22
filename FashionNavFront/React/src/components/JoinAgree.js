// src/components/JoinAgree.js
import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import './JoinAgree.css';

const JoinAgree = () => {
    const [isAgreed, setIsAgreed] = useState(false);
    const navigate = useNavigate();

    const handleAgree = () => {
        if (isAgreed) {
            navigate('/signup');
        }
    };

    return (
        <div className="join-agree-container">
            <h1>이용약관</h1>
            <div className="terms-container">
                <p>여기에 이용약관 내용이 들어갑니다...</p>
            </div>
            <div className="agree-checkbox">
                <input
                    type="checkbox"
                    id="agree"
                    checked={isAgreed}
                    onChange={(e) => setIsAgreed(e.target.checked)}
                />
                <label htmlFor="agree">이용약관에 동의합니다.</label>
            </div>
            <button onClick={handleAgree} disabled={!isAgreed} className="agree-button">
                동의하고 회원가입
            </button>
        </div>
    );
};

export default JoinAgree;
