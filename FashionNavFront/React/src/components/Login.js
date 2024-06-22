import React, { useState, useEffect } from 'react';
import { useNavigate, useLocation } from 'react-router-dom';
import api from '../api'; // api 모듈 import
import { GoogleLogin } from '@react-oauth/google';
import './Login.css';

const Login = ({ onLogin }) => {
    const [email, setEmail] = useState('');
    const [password, setPassword] = useState('');
    const [rememberMe, setRememberMe] = useState(false);
    const [error, setError] = useState('');
    const navigate = useNavigate();
    const location = useLocation();
    const from = location.state?.from?.pathname || "/";

    useEffect(() => {
        const token = localStorage.getItem('token');
        if (token) {
            navigate(from);
        }
    }, [navigate, from]);

    const handleLogin = async (e) => {
        e.preventDefault();
        setError(''); // 기존 에러 메시지 초기화

        try {
            const response = await api.post('/users/authenticate', { email, password });
            const { accessToken, refreshToken } = response.data.body;
            localStorage.setItem('token', accessToken);
            localStorage.setItem('refreshToken', refreshToken);
            onLogin(); // 로그인 성공 후 콜백 함수 호출
            navigate(from); // 원래 목적지로 이동
        } catch (error) {
            console.error('Error during login', error);
            setError('Login failed. Please check your credentials and try again.');
        }
    };

    const handleSignup = () => {
        navigate('/signup'); // 회원가입 페이지로 이동
    };

    const handleFindIdPw = () => {
        // 아이디/비밀번호 찾기 페이지로 이동 로직 (필요시 구현)
    };

    const handleGoogleSuccess = (response) => {
        console.log('Login Success:', response);

        fetch('http://localhost:8080/api/users/oauth2/google', {
            method: 'POST',  // POST 요청으로 수정
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify({ token: response.credential }),  // body에 데이터 포함
        })
            .then((res) => {
                console.log('Response status:', res.status);
                console.log('Response headers:', res.headers);
                return res.text();  // Change to text for debugging
            })
            .then((data) => {
                try {
                    console.log('Response text:', data);
                    const jsonData = JSON.parse(data);  // 수동으로 JSON 파싱
                    console.log('Parsed JSON:', jsonData);
                    if (jsonData.accessToken) {  // jsonData.body.accessToken에서 jsonData.accessToken로 변경
                        localStorage.setItem("token", jsonData.accessToken);
                        onLogin();
                        if (jsonData.isFirstLogin) {  // 첫 로그인 여부 확인
                            navigate('/survey');  // 설문조사 페이지로 이동
                        } else {
                            navigate('/'); // 홈 페이지로 이동
                        }
                    } else {
                        console.error('Login failed, no access token in response:', jsonData);
                    }
                } catch (error) {
                    console.error('Error parsing JSON:', error);
                    console.log('Response text:', data);
                }
            })
            .catch((error) => {
                console.error('Error:', error);
            });
    };

    const handleGoogleError = (error) => {
        console.log('Login Failed:', error);
    };

    return (
        <div className="login-container">
            <form className="login-form" onSubmit={handleLogin}>
                <div className="login-input-group">
                    <label htmlFor="email">아이디</label>
                    <input
                        type="text"
                        id="email"
                        value={email}
                        onChange={(e) => setEmail(e.target.value)}
                        required
                    />
                </div>
                <div className="login-input-group">
                    <label htmlFor="password">비밀번호</label>
                    <input
                        type="password"
                        id="password"
                        value={password}
                        onChange={(e) => setPassword(e.target.value)}
                        required
                    />
                </div>
                <div className="login-remember-me">
                    <input
                        type="checkbox"
                        id="rememberMe"
                        checked={rememberMe}
                        onChange={(e) => setRememberMe(e.target.checked)}
                    />
                    <label htmlFor="rememberMe">아이디 저장</label>
                </div>
                {error && <p className="error">{error}</p>}
                <button type="submit" className="login-button">로그인</button>
            </form>
            <div className="login-footer">
                <button className="signup-button" onClick={handleSignup}>회원가입</button>
                <button className="find-idpw-button" onClick={handleFindIdPw}>아이디/비밀번호찾기</button>
            </div>
            <div className="social-login">
                <h3>또는</h3>
                <GoogleLogin onSuccess={handleGoogleSuccess} onError={handleGoogleError} />
            </div>
        </div>
    );
};

export default Login;
