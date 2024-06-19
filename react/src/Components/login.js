import React, { useState } from 'react';
import styles from './login.module.css'; // CSS 모듈 import
import { useNavigate } from 'react-router-dom';
import api from '../api';

const Login = ({setIsLoggedIn}) => {
  const [username, setUsername] = useState('');
  const [password, setPassword] = useState('');
  const [error, setError] = useState('');
  const navigate = useNavigate();

  const handleUsernameChange = (event) => {
    setUsername(event.target.value);
  };

  const handlePasswordChange = (event) => {
    setPassword(event.target.value);
  };

  const handleSubmit = async (event) => {
    event.preventDefault();
    // 여기서 로그인 처리를 구현할 수 있습니다
    try {
      const response = await api.post('/login', { username, password });
      localStorage.setItem('token', response.data.token);
      setIsLoggedIn(true);
      navigate('/');
    } catch (error) {
      setError('Invalid username or password');
    }
    // 예를 들어 실제로는 서버에 로그인 요청을 보낼 수 있습니다
  };

  return (
    <div className={styles.login}>
      <h2>로그인</h2>
      <form className={styles.form} onSubmit={handleSubmit}>
        <div className={styles.formGroup}>
          <label className={styles.label}>아이디:</label>
          <input
            type="text"
            className={styles.input}
            value={username}
            onChange={handleUsernameChange}
          />
        </div>
        <div className={styles.formGroup}>
          <label className={styles.label}>비밀번호:</label>
          <input
            type="password"
            className={styles.input}
            value={password}
            onChange={handlePasswordChange}
          />
        </div>
        <button type="submit" className={styles.button}>로그인</button>
      </form>

      {/* 회원가입 및 아이디/비밀번호 찾기 버튼 */}
      <div className={styles.additionalLinks}>
        <a href="/signup" className={styles.link}>회원가입</a>
        <a href="/forgot-password" className={styles.link}>아이디/비밀번호 찾기</a>
      </div>
    </div>
  );
};

export default Login;
