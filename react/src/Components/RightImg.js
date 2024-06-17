import React from 'react';

const RightImg = ({ title, moreLink }) => {
    return (
        <div className='right_imgs' style={{width: '1150px',
            height: '400px',
            position: 'relative',
            margin: '0 auto'}}>

            <div style={{
                width:'200px', height:'211px', 
                backgroundColor:'#D9D9D9',
                position: 'absolute',
                top: '0',
                right: '0',
            }}><img></img></div>
        </div>
    );
};

export default RightImg;