import React, { useState } from 'react';
import { Link, useNavigate } from 'react-router-dom';
import './Join.css';

const Join = () => {
    const [isAgreed, setIsAgreed] = useState(false);
    const [showWarning, setShowWarning] = useState(false);
    const navigate = useNavigate();

    const handleAgree = () => {
        if (isAgreed) {
            navigate('/signup');
        } else {
            setShowWarning(true);
        }
    };

    return (
        <div className="join-container">
            <p>FASHION NAV 에 오신 것을 환영합니다. <br/>회원가입을 하시면 더 많은 혜택을 누리실 수 있습니다.</p>
            {/* <h2>이용약관</h2> */}
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
            <div className="button-container">
                <button onClick={handleAgree} className="join-button">
                    동의하고 회원가입
                </button>
                <Link to="/" className="cancel-button">취소</Link>
            </div>
            {showWarning && (
                <p className="warning-message">이용 약관에 동의해야 회원가입할 수 있습니다.</p>
            )}
        </div>
    );
};

export default Join;