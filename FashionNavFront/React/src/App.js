import React, { useEffect, useState } from "react";
import { Route, Routes, Navigate, useNavigate } from "react-router-dom";
import HomePage from "./components/HomePage";
import NewsList from "./components/NewsList";
import NewsDetail from "./components/NewsDetail";
import Login from "./components/Login";
import SignUp from "./components/SignUp";
import SubmitSurvey from "./components/SubmitSurvey";
import MyPage from "./components/MyPage";
import Admin from "./components/admin/Admin";
import Header from "./components/Header";
import { PrivateRoute, AdminRoute } from "./components/PrivateRoute";
import api from "./api";
import UserSurveyManagement from "./components/UserSurveyManagement";
import EditSurvey from "./components/EditSurvey";
import CategoryNewsPage from "./components/CategoryNewsPage";
import AllNews from "./components/AllNews";
import { GoogleOAuthProvider } from '@react-oauth/google';
import JoinAgree from "./components/JoinAgree";
import Join from "./components/Join";
import AddSurvey from "./components/AddSurvey";
import SearchResults from "./components/SearchResults";

import UserCommentList from "./components/UserCommentList"; // Import UserCommentList
import './components/UserComment.css'; // Import UserCommentList CSS


const App = () => {
  const [isLoggedIn, setIsLoggedIn] = useState(false);
  const [isAdmin, setIsAdmin] = useState(false);
  const [isLoading, setIsLoading] = useState(true);
  const navigate = useNavigate();


  useEffect(() => {
    const initialize = async () => {
      const token = localStorage.getItem("token");
      if (token) {
        setIsLoggedIn(true);
        await api.get("/users/me").then((response) => {
          const user = response.data.body;
          setIsAdmin(user.role === "ROLE_ADMIN");
        });
      }
      setIsLoading(false);
    }
    
    initialize();
  }, []);

  const handleLogin = async () => {
    const token = localStorage.getItem("token");
    if (token) {
      setIsLoggedIn(true);
      const response = await api.get("/users/me");
      const user = response.data.body;
      setIsAdmin(user.role === "ROLE_ADMIN");
    }
  };

  const handleLogout = () => {
    localStorage.removeItem("token");
    setIsLoggedIn(false);
    setIsAdmin(false);
    navigate("/");
  };

  if(isLoading) {
    return <h1>page loading...</h1>
  }

  return (
      <GoogleOAuthProvider clientId="123145919395-6b789bgir2efl0o2r83vjvhicshs3avi.apps.googleusercontent.com">
        <Header isLoggedIn={isLoggedIn} isAdmin={isAdmin} onLogout={handleLogout} />
        <Routes>
          <Route path="/" element={<HomePage />} />
          <Route path="/news" element={<NewsList />} />
          <Route path="/news/:category" element={<CategoryNewsPage />} />
          <Route path="/news/details/:newsId" element={<NewsDetail isAdmin={isAdmin} />} />
          <Route path="/login" element={isLoggedIn ? <Navigate to="/" /> : <Login onLogin={handleLogin} />} />
          <Route path="/signup" element={<SignUp />} />
          <Route path="/submit-survey" element={<SubmitSurvey onLogin={handleLogin} />} />
          <Route path="/mypage" element={<MyPage />} />
          <Route path="/join" element={<Join />} />
          <Route path="/join-agree" element={<JoinAgree />} />
          <Route path="/admin/*" element={<AdminRoute isAuthenticated={isLoggedIn} isAdmin={isAdmin}><Admin /></AdminRoute>} />
          <Route path="/add-survey" element={<AddSurvey />} />
          <Route path="/survey-management" element={<PrivateRoute isAuthenticated={isLoggedIn}><UserSurveyManagement /></PrivateRoute>} />
          <Route path="/edit-survey/:surveyId" element={<EditSurvey />} />
          <Route path="/all-news" element={<AllNews />} />
          <Route path="/search" element={<SearchResults />} /> {/* 추가 */}

          <Route path="/mypage/my-comments" element={<PrivateRoute isAuthenticated={isLoggedIn}><UserCommentList /></PrivateRoute>} />
          <Route path="/news/details/:newsId" element={<NewsDetail isAdmin={isAdmin} />} />
        </Routes>
      </GoogleOAuthProvider>
  );
};

export default App;
