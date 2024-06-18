import React from 'react';
import Header from './header.js';
import Slide from './slide.js';
import Content1 from './contents1.js';
import Content2 from './contents2.js';
import Content3 from './contents3.js';
import Footer from './footer.js';

const MainPage = () => {
    return (
        <div className='app'>
          <Header />
          <Slide />
          <Content1 />
          <Content1 />
          <Content3 />
          <Footer />
        </div>
      );
};

export default MainPage;