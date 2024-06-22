import React, { useState } from "react";
import api from "../api";
import { useNavigate } from "react-router-dom";
import "./SignUp.css"; // 스타일 파일을 import

const SignUp = () => {
  const [name, setName] = useState("");
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [confirmPassword, setConfirmPassword] = useState("");
  const [error, setError] = useState("");
  const navigate = useNavigate();

  const handleSubmit = async (e) => {
    e.preventDefault();
    if (password !== confirmPassword) {
      setError("Passwords do not match");
      return;
    }

    const registerRequest = {
      name: name,
      email: email,
      password: password,
    };

    try {
      // 회원가입 요청
      await api.post("/users/register", registerRequest);
      console.log("Registration successful");

      // 로그인 요청
      const loginRequest = {
        email: email,
        password: password,
      };

      const response = await api.post("/users/authenticate", loginRequest);
      const accessToken = response.data.body?.accessToken;
      const refreshToken = response.data.body?.refreshToken;
      if (accessToken && refreshToken) {
        localStorage.setItem("token", accessToken);
        localStorage.setItem("refreshToken", refreshToken);
        navigate("/submit-survey"); // 설문조사 페이지로 이동
      } else {
        setError("Token not found in response");
      }
    } catch (error) {
      console.error("Error during registration or login", error);
      setError("Registration or login failed");
    }
  };

  return (
      <div className="signup-container">
        <form onSubmit={handleSubmit} className="signup-form">
          <h1 className="signup-title">회원가입</h1>
          {error && <p className="error">{error}</p>}
          <div className="form-group">
            <label>이름</label>
            <input
                type="text"
                value={name}
                onChange={(e) => setName(e.target.value)}
                required
                className="signup-input"
            />
          </div>
          <div className="form-group">
            <label>이메일</label>
            <input
                type="email"
                value={email}
                onChange={(e) => setEmail(e.target.value)}
                required
                className="signup-input"
            />
          </div>
          <div className="form-group">
            <label>비밀번호</label>
            <input
                type="password"
                value={password}
                onChange={(e) => setPassword(e.target.value)}
                required
                className="signup-input"
            />
          </div>
          <div className="form-group">
            <label>비밀번호 확인</label>
            <input
                type="password"
                value={confirmPassword}
                onChange={(e) => setConfirmPassword(e.target.value)}
                required
                className="signup-input"
            />
          </div>
          <button type="submit" className="signup-button">회원가입</button>
        </form>
      </div>
  );
};

export default SignUp;
