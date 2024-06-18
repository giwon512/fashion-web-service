import React from 'react';
import { Link } from 'react-router-dom'; // React Router의 Link 컴포넌트 import
import './ContentTitle.css';

const ContentTitle = ({ title, moreLink }) => {
    return (
        <div className='con_title'>
            <div className='Group2'>
                <span className='CELEBRITYNEWS_1'>{title}</span>
                <Link to={moreLink} className='more_1'>more &gt;</Link> 
            </div>
            <hr className="line" />
        </div>
    );
};

export default ContentTitle;
