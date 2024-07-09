import React, { useState, useEffect } from 'react';
import { useNavigate, useLocation } from 'react-router-dom';
import api from '../api';
import { GoogleLogin } from '@react-oauth/google';
import './Login.css';
import Modal from './Modal'; // 모달 컴포넌트 import

const Login = ({ onLogin }) => {
    const [email, setEmail] = useState('');
    const [password, setPassword] = useState('');
    const [rememberMe, setRememberMe] = useState(false);
    const [error, setError] = useState('');
    const [showFindIdModal, setShowFindIdModal] = useState(false); // 아이디 찾기 모달 상태
    const [showFindPwModal, setShowFindPwModal] = useState(false); // 비밀번호 찾기 모달 상태
    const [step, setStep] = useState(1); // 비밀번호 찾기 단계
    const [name, setName] = useState(''); // 이름 상태
    const [phoneNumber, setPhoneNumber] = useState(''); // 휴대폰 번호 상태
    const [verificationCode, setVerificationCode] = useState(''); // 인증 코드 상태
    const [newPassword, setNewPassword] = useState(''); // 새 비밀번호 상태
    const [findPwEmail, setFindPwEmail] = useState(''); // 비밀번호 찾기용 이메일 상태
    const [findPwName, setFindPwName] = useState(''); // 비밀번호 찾기용 이름 상태

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
            localStorage.setItem('token', accessToken);
            localStorage.setItem('refreshToken', refreshToken);
            onLogin();
            navigate(from);
        } catch (error) {
            setError('Login failed. Please check your credentials and try again.');
        }
    };

    const handleSignup = () => {
        navigate('/signup');
    };

    const handleGoogleSuccess = async (response) => {
        try {
            const res = await fetch('https://port-0-fashion-web-service-lydr4cy5f698c981.sel5.cloudtype.app/api/users/oauth2/google', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify({ token: response.credential }),
            });

            const data = await res.json();

            if (data.accessToken && data.refreshToken) {
                localStorage.setItem('token', data.accessToken);
                localStorage.setItem('refreshToken', data.refreshToken);
                onLogin();

                if (data.newUser) {
                    navigate('/edit-profile'); // 신규 가입자인 경우 edit-profile 페이지로 이동
                } else {
                    navigate(from); // 기존 사용자라면 원래 페이지로 이동
                }
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

    // 아이디 찾기 핸들러
    const handleFindId = async (name, phoneNumber) => {
        try {
            const response = await api.post('/users/find-email', { name, phoneNumber });
            alert(`Your email is: ${response.data}`);
            setShowFindIdModal(false);
            setName(''); // 이름 상태 초기화
            setPhoneNumber(''); // 휴대폰 번호 상태 초기화
        } catch (error) {
            alert('이메일 정보를 찾을 수 없습니다.');
        }
    };

    // 비밀번호 찾기 요청 핸들러
    const handleFindPw = async (email, name) => {
        try {
            await api.post('/users/request-password-reset', { email, name });
            alert('비밀번호 재설정 코드가 이메일로 전송되었습니다.');
            setStep(2); // 비밀번호 재설정 단계로 전환
        } catch (error) {
            alert('비밀번호 재설정을 요청실패');
        }
    };

    // 비밀번호 재설정 핸들러
    const handleResetPw = async (email, code, newPassword) => {
        try {
            await api.post('/users/reset-password', { email, code, newPassword });
            alert('비밀번호가 성공적으로 재설정되었습니다.');
            setShowFindPwModal(false);
            setFindPwEmail(''); // 비밀번호 찾기용 이메일 상태 초기화
            setFindPwName(''); // 비밀번호 찾기용 이름 상태 초기화
            setVerificationCode(''); // 인증 코드 상태 초기화
            setNewPassword(''); // 새 비밀번호 상태 초기화
            setStep(1); // 초기 단계로 재설정
        } catch (error) {
            alert('재설정실패 다시 시도');
        }
    };

    return (
        <div className="login-container">
            <h2 className="login-title">LOGIN</h2>
            <form className="login-form" onSubmit={handleLogin}>
                <div className="login-input-group">
                    <label htmlFor="email">아이디</label>
                    <input
                        type="text"
                        id="email"
                        value={email}
                        onChange={(e) => setEmail(e.target.value)}
                        placeholder="아이디를 입력해 주세요"
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
                        placeholder="비밀번호를 입력해 주세요"
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
                <button className="login-find-idpw-button" onClick={() => setShowFindIdModal(true)}>아이디찾기</button>
                <button className="login-find-idpw-button" onClick={() => {
                    setShowFindPwModal(true);
                    setStep(1); // 비밀번호 찾기 첫 단계로 설정
                }}>비밀번호찾기</button>
            </div>
            <div className="login-social-login">
                <h3>또는</h3>
                <GoogleLogin onSuccess={handleGoogleSuccess} onError={handleGoogleError} />
            </div>
            <div className='modal'>
                {showFindIdModal && (
                    <Modal onClose={() => setShowFindIdModal(false)} >
                        <h2>아이디 찾기</h2>
                        <form onSubmit={(e) => {
                            e.preventDefault();
                            handleFindId(name, phoneNumber);
                        }}>
                            <div>
                                <label>이름</label>
                                <input type="text" 
                                value={name} 
                                onChange={(e) => setName(e.target.value)} 
                                placeholder="이름을 입력해 주세요"
                                required />
                            </div>
                            <div>
                                <label>휴대폰 번호</label>
                                <input type="text" 
                                value={phoneNumber} 
                                onChange={(e) => setPhoneNumber(e.target.value)} 
                                placeholder="휴대폰 번호를 입력해 주세요"
                                required />
                            </div>
                            <button className="modal_button" type="submit">찾기</button>
                        </form>
                    </Modal>
                )}
                {showFindPwModal && (
                    <Modal onClose={() => setShowFindPwModal(false)}>
                        {step === 1 && ( // 첫 단계: 인증 코드 요청
                            <>
                                <h2>비밀번호 찾기</h2>
                                <form onSubmit={(e) => {
                                    e.preventDefault();
                                    handleFindPw(findPwEmail, findPwName);
                                }}>
                                    <div>
                                        <label>이메일</label>
                                        <input type="email" 
                                        value={findPwEmail} 
                                        onChange={(e) => setFindPwEmail(e.target.value)} 
                                        placeholder="이메일을 입력해 주세요"
                                        required />
                                    </div>
                                    <div>
                                        <label>이름</label>
                                        <input type="text" 
                                        value={findPwName} 
                                        onChange={(e) => setFindPwName(e.target.value)} 
                                        placeholder="이름을 입력해 주세요"
                                        required />
                                    </div>
                                    <button className="modal_button" type="submit">인증 코드 요청</button>
                                </form>
                            </>
                        )}
                        {step === 2 && ( // 두 번째 단계: 비밀번호 재설정
                            <>
                                <h2>비밀번호 재설정</h2>
                                <form onSubmit={(e) => {
                                    e.preventDefault();
                                    handleResetPw(findPwEmail, verificationCode, newPassword);
                                }}>
                                    <div>
                                        <label>이메일</label>
                                        <input type="email" value={findPwEmail} onChange={(e) => setFindPwEmail(e.target.value)} required />
                                    </div>
                                    <div>
                                        <label>인증 코드</label>
                                        <input type="text" value={verificationCode} onChange={(e) => setVerificationCode(e.target.value)} required />
                                    </div>
                                    <div>
                                        <label>새 비밀번호</label>
                                        <input type="password" value={newPassword} onChange={(e) => setNewPassword(e.target.value)} required />
                                    </div>
                                    <button className="modal_button" type="submit">재설정</button>
                                </form>
                            </>
                        )}
                    </Modal>
                )}
            </div>
        </div>
    );
};

export default Login;
