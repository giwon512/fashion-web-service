// SignUp.js

import React, { useState } from 'react';
import { Link, useNavigate } from 'react-router-dom';
import './signUp.css'; // SignUp.css 파일 import 추가
import api from '../api';

const SignUp = () => {
    const [userId, setUserId] = useState('');
    const [password, setPassword] = useState('');
    const [confirmPassword, setConfirmPassword] = useState('');
    const [name, setName] = useState('');
    const [year, setYear] = useState('');
    const [month, setMonth] = useState('');
    const [day, setDay] = useState('');
    const [gender, setGender] = useState('');
    const [email, setEmail] = useState('');
    const [phoneNumber, setPhoneNumber] = useState('');
    const [message, setMessage] = useState('');
    const navigate = useNavigate();

    const handleUserIdChange = (event) => {
        setUserId(event.target.value);
    };

    const handlePasswordChange = (event) => {
        setPassword(event.target.value);
    };

    const handleConfirmPasswordChange = (event) => {
        setConfirmPassword(event.target.value);
    };

    const handleNameChange = (event) => {
        setName(event.target.value);
    };

    const handleYearChange = (event) => {
        setYear(event.target.value);
    };

    const handleMonthChange = (event) => {
        setMonth(event.target.value);
    };

    const handleDayChange = (event) => {
        setDay(event.target.value);
    };

    const handleGenderChange = (event) => {
        setGender(event.target.value);
    };

    const handleEmailChange = (event) => {
        setEmail(event.target.value);
    };

    const handlePhoneNumberChange = (event) => {
        setPhoneNumber(event.target.value);
    };

    const handleSubmit = async (event) => {
        event.preventDefault();
        // 여기서 실제로 회원가입 처리를 구현할 수 있습니다
        try {
            await api.post('/signup', {userId, password });
            setMessage('회원가입이 완료되었습니다!');
          } catch (error) {
            setMessage('회원가입에 실패했습니다.');
          }
        // 예를 들어 실제로는 서버에 회원가입 요청을 보낼 수 있습니다

        navigate('/survey');
    };

    // 년도 옵션 생성
    const yearOptions = [];
    for (let year = 1960; year <= 2024; year++) {
        yearOptions.push(
            <option key={year} value={year}>
                {year}년
            </option>
        );
    }

    // 월 옵션 생성
    const monthOptions = [];
    for (let month = 1; month <= 12; month++) {
        monthOptions.push(
            <option key={month} value={month < 10 ? `0${month}` : `${month}`}>
                {month}월
            </option>
        );
    }

    // 일 옵션 생성
    const dayOptions = [];
    for (let day = 1; day <= 31; day++) {
        dayOptions.push(
            <option key={day} value={day < 10 ? `0${day}` : `${day}`}>
                {day}일
            </option>
        );
    }

    return (
        <div className="signup">
            <h2>회원 가입</h2>
            <form onSubmit={handleSubmit}>
                <div className="form-group">
                    <label>아이디:</label>
                    <input type="text" value={userId} onChange={handleUserIdChange} required />
                </div>
                <div className="form-group">
                    <label>이메일:</label>
                    <input type="email" value={email} onChange={handleEmailChange} required />
                </div>
                <div className="form-group">
                    <label>비밀번호:</label>
                    <input type="password" value={password} onChange={handlePasswordChange} required />
                </div>
                <div className="form-group">
                    <label>비밀번호 확인:</label>
                    <input type="password" value={confirmPassword} onChange={handleConfirmPasswordChange} required />
                </div>
                <div className="form-group">
                    <label>이름:</label>
                    <input type="text" value={name} onChange={handleNameChange} required />
                </div>
                <div className="birthdate-group">
                    <label>생년월일:</label>
                    <div className="birthdate-inputs">
                        <select value={year} onChange={handleYearChange} required>
                            <option value="">연도</option>
                            {yearOptions}
                        </select>
                        <select value={month} onChange={handleMonthChange} required>
                            <option value="">월</option>
                            {monthOptions}
                        </select>
                        <select value={day} onChange={handleDayChange} required>
                            <option value="">일</option>
                            {dayOptions}
                        </select>
                    </div>
                </div>
                <div className="form-group">
                    <label>성별:</label>
                    <select value={gender} onChange={handleGenderChange} required>
                        <option value="">선택</option>
                        <option value="male">남성</option>
                        <option value="female">여성</option>
                        <option value="other">기타</option>
                    </select>
                </div>
                <div className="form-group">
                    <label>전화번호:</label>
                    <input type="tel" value={phoneNumber} onChange={handlePhoneNumberChange} required />
                </div>
                <button type="submit">가입하기</button>
            </form>
            <p>이미 회원이신가요? <Link to="/login">로그인 하러가기</Link></p>
        </div>
    );
};

export default SignUp;
