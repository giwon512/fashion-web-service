import React from 'react';
import { BrowserRouter as Router, Route, Routes } from 'react-router-dom';

import './App.css';
import MainPage from './Components/MainPage';
import NewsPage from './Components/newsPage';
import NewsDetailPage from './Components/newsDetailPage';

const App = () => {
    return (
        <Router>
            <div className='app'>
                <Routes>
                    <Route path="/" element={<MainPage />} />
                    <Route path="/newsPage" element={<NewsPage />} />
                    <Route path="/newsPage/newsDetailPage" element={<NewsDetailPage />} />
                </Routes>
            </div>
        </Router>
    );
}

export default App;
