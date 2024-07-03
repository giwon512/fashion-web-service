import React, { useState, useEffect } from 'react';
import { useNavigate, useLocation } from 'react-router-dom';
import api from '../api';
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
        setError('');

        try {
            const response = await api.post('/users/authenticate', { email, password });
            const { accessToken, refreshToken } = response.data.body;
            console.log('AccessToken:', accessToken);
            console.log('RefreshToken:', refreshToken);
            localStorage.setItem('token', accessToken);
            localStorage.setItem('refreshToken', refreshToken);
            onLogin();
            navigate(from);
        } catch (error) {
            console.error('Error during login', error);
            setError('Login failed. Please check your credentials and try again.');
        }
    };

    const handleSignup = () => {
        navigate('/signup');
    };

    const handleFindIdPw = () => {
        // 구현 필요시 추가
    };

    const handleGoogleSuccess = async (response) => {
        console.log('Login Success:', response);

        try {
            const res = await fetch('http://localhost:8080/api/users/oauth2/google', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify({ token: response.credential }),
            });

            const data = await res.json();
            console.log('Parsed JSON:', data);

            if (data.accessToken && data.refreshToken) {
                console.log('AccessToken:', data.accessToken);
                console.log('RefreshToken:', data.refreshToken);
                localStorage.setItem('token', data.accessToken);
                localStorage.setItem('refreshToken', data.refreshToken);
                onLogin();
                navigate('/');
            } else {
                console.error('Login failed, no access token in response:', data);
            }
        } catch (error) {
            console.error('Error:', error);
        }
    };

    const handleGoogleError = (error) => {
        console.log('Login Failed:', error);
    };

    return (
        <div className="login-container">
            <h2 className="login-title">로그인</h2>
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
                {error && <p className="login-error">{error}</p>}
                <button type="submit" className="login-button">로그인</button>
            </form>
            <div className="login-footer">
                <button className="signup-button" onClick={handleSignup}>회원가입</button>
                <button className="login-find-idpw-button" onClick={handleFindIdPw}>아이디/비밀번호찾기</button>
            </div>
            <div className="login-social-login">
                <h3>또는</h3>
                <GoogleLogin onSuccess={handleGoogleSuccess} onError={handleGoogleError} />
            </div>
        </div>
    );
};

export default Login;
