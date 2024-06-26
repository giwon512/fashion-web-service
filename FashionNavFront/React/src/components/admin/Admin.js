import React from "react";
import { Routes, Route, Navigate, Link } from "react-router-dom";
import ManageNews from "./ManageNews";
import ManageBanners from "./ManageBanners";
import ManageSurveys from "./ManageSurveys";
import "./Admin.css";
// import styled from 'styled-components';

const Admin = () => {

    return (
        <div className="admin-container">
            <h1>Admin Panel</h1>
            <ul>
                <li><Link to="/admin/news">NEWS</Link></li>
                <li><Link to="/admin/banners">BANNERS</Link></li>
                <li><Link to="/admin/surveys">SURVEYS</Link></li>
            </ul>
            <p>관리자 전용 페이지에 오신 것을 환영합니다. 상단의 메뉴를 사용하여 뉴스와 배너를 관리하세요.</p>
            <Routes>
                <Route path="news" element={<ManageNews />} />
                <Route path="banners" element={<ManageBanners />} />
                <Route path="surveys" element={<ManageSurveys />} />
                <Route path="*" element={<Navigate to="news" />} />
            </Routes>
        </div>
    );
};



export default Admin;
