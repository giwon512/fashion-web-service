import React from 'react';
import './ContentTitle.css';

const ContentTitle = ({ title, moreLink }) => {
    return (
        <div className='con_title'>
            <div className='Group2'>
                <span className='CELEBRITYNEWS_1'>{title}</span>
                <span className='more_1'>{moreLink}</span>
            </div>
            <hr className="line" />
        </div>
    );
};

export default ContentTitle;