import React, { useState } from 'react';
import { BrowserRouter as Router, Route, Routes } from 'react-router-dom';

import './App.css';
import Header from './Components/header';
import MainPage from './Components/MainPage';
import NewsPage from './Components/newsPage';
import NewsDetailPage from './Components/newsDetailPage';
import Login from './Components/login';
import SignUp from './Components/signUp';
import Survey from './Components/survey';


const App = () => {
    const [isLoggedIn, setIsLoggedIn] = useState(!!localStorage.getItem('token'));
    return (
        
            <div className='app'>
                <Router>
                    
                    <Routes>
                        <Route path="/" element={<MainPage />} />
                        <Route path="/newsPage" element={<NewsPage />} />
                        <Route path="/newsPage/newsDetailPage" element={<NewsDetailPage />} />
                        <Route path="/login" element={<Login />} />
                        <Route path="/signUp" element={<SignUp />} />
                        <Route path="/survey" element={<Survey/>}/>
                    </Routes>
                </Router>
            </div>
    
    );
}

export default App;
