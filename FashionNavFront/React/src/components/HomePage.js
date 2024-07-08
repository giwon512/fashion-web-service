// src/components/HomePage.js
import React from 'react';
import BannerSlider from './Slider';
import CategoryNews from './CategoryNews';

import './HomePage.css';
import LatestArticles from "./LatestArticles";
import HomeBoard from './HomeBoard';

const HomePage = ({ isLoggedIn }) => {
    const categories = ['celeb', 'brand', 'trend'];

    return (
        <div className="home-page">
            <BannerSlider />
            {categories.map(category => (
                <CategoryNews key={category} category={category} isLoggedIn={isLoggedIn}/>
            ))}
            <div className='home-page-bottom'>
                <LatestArticles className="homepage-articles"/> {/* Add LatestNews component here */}
                <div className='homepage-board'>
                    <HomeBoard boardType={'event'}/>
                    <HomeBoard boardType={'free'}/>
                </div>
            </div>
            
            
        </div>
    );
};

export default HomePage;
