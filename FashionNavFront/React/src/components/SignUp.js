import React, { useState } from 'react';
import { Link, useNavigate } from 'react-router-dom';
import './SignUp.css';
import api from '../api';

const SignUp = () => {
	const [password, setPassword] = useState('');
	const [confirmPassword, setConfirmPassword] = useState('');
	const [name, setName] = useState('');
	const [year, setYear] = useState('');
	const [month, setMonth] = useState('');
	const [day, setDay] = useState('');
	const [gender, setGender] = useState('');
	const [email, setEmail] = useState('');
	const [phoneNumber, setPhoneNumber] = useState('');
	const [error, setError] = useState('');
	const navigate = useNavigate();

	const handlePasswordChange = (event) => setPassword(event.target.value);
	const handleConfirmPasswordChange = (event) => setConfirmPassword(event.target.value);
	const handleNameChange = (event) => setName(event.target.value);
	const handleYearChange = (event) => setYear(event.target.value);
	const handleMonthChange = (event) => setMonth(event.target.value);
	const handleDayChange = (event) => setDay(event.target.value);
	const handleGenderChange = (event) => setGender(event.target.value);
	const handleEmailChange = (event) => setEmail(event.target.value);
	const handlePhoneNumberChange = (event) => setPhoneNumber(event.target.value);

	const handleSubmit = async (event) => {
		event.preventDefault();
		if (password !== confirmPassword) {
			setError('비밀번호가 일치하지 않습니다.');
			return;
		}

		const birthdate = `${year}-${month}-${day}`;

		const registerRequest = {
			name: name,
			email: email,
			password: password,
			birthdate: birthdate,
			gender: gender,
			phoneNumber: phoneNumber,
		};

		try {
			await api.post('/users/register', registerRequest);
			console.log('회원가입 성공');

			const loginRequest = {
				email: email,
				password: password,
			};

			const response = await api.post('/users/authenticate', loginRequest);
			const accessToken = response.data.body?.accessToken;
			const refreshToken = response.data.body?.refreshToken;

			if (accessToken && refreshToken) {
				localStorage.setItem('token', accessToken);
				localStorage.setItem('refreshToken', refreshToken);
				navigate('/submit-survey');
			} else {
				setError('토큰이 응답에 없습니다.');
			}
		} catch (error) {
			console.error('회원가입 또는 로그인 중 오류 발생', error);
			setError('회원가입 또는 로그인에 실패하였습니다.');
		}
	};

	const yearOptions = [];
	for (let year = 1960; year <= 2024; year++) {
		yearOptions.push(
			<option key={year} value={year}>
				{year}년
			</option>
		);
	}

	const monthOptions = [];
	for (let month = 1; month <= 12; month++) {
		monthOptions.push(
			<option key={month} value={month < 10 ? `0${month}` : `${month}`}>
				{month}월
			</option>
		);
	}

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
				<div className="form-container-wrapper">
					<div className="form-container">
						<div className="form-group">
							<label>이름</label>
							<input
								type="text"
								value={name}
								onChange={handleNameChange}
								placeholder="이름을 입력해 주세요"
								required
							/>
						</div>
						<div className="form-group">
							<label>이메일</label>
							<input
								type="email"
								value={email}
								onChange={handleEmailChange}
								placeholder="이메일을 입력해 주세요"
								required
							/>
						</div>
						<div className="form-group">
							<label>비밀번호</label>
							<input
								type="password"
								value={password}
								onChange={handlePasswordChange}
								placeholder="비밀번호를 입력해 주세요"
								required
							/>
						</div>
						<div className="form-group">
							<label>비밀번호 확인</label>
							<input
								type="password"
								value={confirmPassword}
								onChange={handleConfirmPasswordChange}
								placeholder="비밀번호를 한 번 더 입력해 주세요"
								required
							/>
						</div>
					</div>
					<div className="form-container">
						<div className="form-group">
							<label>생년월일</label>
							<div className="birthdate-group">
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
						</div>
						<div className="form-group">
							<label>성별</label>
							<select value={gender} onChange={handleGenderChange} required>
								<option value="">선택</option>
								<option value="male">남성</option>
								<option value="female">여성</option>
							</select>
						</div>
						<div className="form-group">
							<label>전화번호</label>
							<input
								type="text"
								value={phoneNumber}
								onChange={handlePhoneNumberChange}
								placeholder="전화번호를 입력해 주세요"
							/>
						</div>
					</div>
				</div>
				<button type="submit" className="signup-button">가입하기</button>
			</form>
			{error && <p className="error">{error}</p>}
			<p>
				이미 회원이신가요? &nbsp;<Link to="/login"> 로그인 하러가기</Link>
			</p>
		</div>
	);
};

export default SignUp;
  