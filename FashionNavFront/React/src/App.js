import React, { useState, useEffect } from "react";
import { Route, Routes, Navigate, useNavigate } from "react-router-dom";
import HomePage from "./components/HomePage";
import NewsList from "./components/NewsList";
import NewsDetail from "./components/NewsDetail";
import Login from "./components/Login";
import SignUp from "./components/SignUp";
import SubmitSurvey from "./components/SubmitSurvey";
import MyPage from "./components/MyPage";
import EditProfile from "./components/EditProfile"; // EditProfile 컴포넌트 추가
import Admin from "./components/admin/Admin";
import ManageSurveys from "./components/admin/ManageSurveys";
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
import SavedContent from "./components/SavedContent";
import NoticeBoardPage from "./components/boards/NoticeBoardPage";
import FAQBoardPage from "./components/boards/FAQBoardPage";
import EventBoardPage from "./components/boards/EventBoardPage";
import FreeBoardPage from "./components/boards/FreeBoardPage";
import DataBoardPage from "./components/boards/DataBoardPage";
import PostDetail from "./components/boards/PostDetail";
import ReplyForm from "./components/boards/ReplyForm";

const App = () => {
  const [isLoggedIn, setIsLoggedIn] = useState(false);
  const [isAdmin, setIsAdmin] = useState(false);
  const [loading, setLoading] = useState(true); // 로딩 상태 추가
  const navigate = useNavigate();

  useEffect(() => {
    const checkAuthStatus = async () => {
      const token = localStorage.getItem("token");
      console.log("Token from localStorage:", token);
      if (token) {
        try {
          setIsLoggedIn(true);
          const response = await api.get("/users/me");
          console.log("API response:", response.data);
          const user = response.data.body;
          if (user && user.role) {
            setIsAdmin(user.role === "ROLE_ADMIN");
          } else {
            console.error("User data is invalid", user);
            setIsLoggedIn(false);
            setIsAdmin(false);
          }
        } catch (error) {
          console.error("Failed to fetch user data", error);
          setIsLoggedIn(false);
          setIsAdmin(false);
        }
      }
      setLoading(false); // 로딩 완료
    };

    checkAuthStatus();
  }, []);

  const handleLogin = async () => {
    const token = localStorage.getItem("token");
    console.log("Token from localStorage (login):", token);
    if (token) {
      try {
        setIsLoggedIn(true);
        const response = await api.get("/users/me");
        console.log("API response (login):", response.data);
        const user = response.data.body;
        if (user && user.role) {
          setIsAdmin(user.role === "ROLE_ADMIN");
        } else {
          console.error("User data is invalid", user);
          setIsLoggedIn(false);
          setIsAdmin(false);
        }
      } catch (error) {
        console.error("Failed to fetch user data on login", error);
        setIsLoggedIn(false);
        setIsAdmin(false);
      }
    }
  };

  const handleLogout = () => {
    localStorage.removeItem("token");
    setIsLoggedIn(false);
    setIsAdmin(false);
    navigate("/");
  };

  if (loading) {
    return <div>Loading...</div>; // 로딩 중일 때 표시할 내용
  }

  console.log('App - isLoggedIn:', isLoggedIn);
  console.log('App - isAdmin:', isAdmin);

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
          <Route path="/edit-profile" element={<PrivateRoute isAuthenticated={isLoggedIn}><EditProfile /></PrivateRoute>} />
          <Route path="/mypage/saved-content" element={<PrivateRoute isAuthenticated={isLoggedIn}><SavedContent /></PrivateRoute>} />
          <Route path="/join" element={<Join />} />
          <Route path="/join-agree" element={<JoinAgree />} />
          <Route path="/admin/*" element={<AdminRoute isAuthenticated={isLoggedIn} isAdmin={isAdmin}><Admin /></AdminRoute>} />
          <Route path="/admin/surveys" element={<AdminRoute isAuthenticated={isLoggedIn} isAdmin={isAdmin}><ManageSurveys /></AdminRoute>} />
          <Route path="/add-survey" element={<AddSurvey />} />
          <Route path="/survey-management" element={<PrivateRoute isAuthenticated={isLoggedIn}><UserSurveyManagement /></PrivateRoute>} />
          <Route path="/edit-survey/:surveyId" element={<EditSurvey />} />
          <Route path="/all-news" element={<AllNews />} />
          <Route path="/search" element={<SearchResults />} />
          <Route path="/boards/notice" element={<NoticeBoardPage />} />
          <Route path="/boards/faq" element={<FAQBoardPage />} />
          <Route path="/boards/event" element={<EventBoardPage />} />
          <Route path="/boards/free" element={<FreeBoardPage />} />
          <Route path="/boards/data" element={<DataBoardPage />} />
          <Route path="/posts/:postId" element={<PostDetail />} />
          <Route path="/posts/:postId/replies/write" element={<ReplyForm />} />
        </Routes>
      </GoogleOAuthProvider>
  );
};

export default App;
