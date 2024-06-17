import React, { useState } from 'react';
import './slide.css';
import ImgAsset from '../public';

const Slide = () => {
    const [activeSlideIndex, setActiveSlideIndex] = useState(0);

    const slides = [
        {
            title: 'LOREM IPSUM STYLE',
            subtitle: 'LOREM 슬라이드 배너 STYLE',
            description: 'is placeholder text commonly used in the',
            vector1: ImgAsset.arrow_left,
            vector2: ImgAsset.arrow_right
        },
        {
            title: 'Another Slide Title',
            subtitle: 'Another Slide Subtitle',
            description: 'Another slide description text.',
            vector1: ImgAsset.another_slide_Vector1,
            vector2: ImgAsset.another_slide_Vector2
        },
        {
            title: 'Yet Another Slide',
            subtitle: 'Subtitle for Yet Another Slide',
            description: 'Description for the third slide.',
            vector1: ImgAsset.yet_another_slide_Vector1,
            vector2: ImgAsset.yet_another_slide_Vector2
        }
    ];

    const goToNextSlide = () => {
        setActiveSlideIndex((prevIndex) => (prevIndex === slides.length - 1 ? 0 : prevIndex + 1));
    };

    const goToPrevSlide = () => {
        setActiveSlideIndex((prevIndex) => (prevIndex === 0 ? slides.length - 1 : prevIndex - 1));
    };

    return (
        <div className='slide-container'>
            <div className='slide'>
                {/* 슬라이드 텍스트 */}
                <div className='slide-content'>
                    <span className='LOREMIPSUMSTYLE'>{slides[activeSlideIndex].title}</span>
                    <span className='LOREMSTYLE'>{slides[activeSlideIndex].subtitle}</span>
                    <span className='isplaceholdertextcommonlyusedinthe'>{slides[activeSlideIndex].description}</span>
                </div>

                {/* 슬라이드 배너와 화살표 */}
                <div className='slide_wrap'>
                    <div className='Group29'>
                        {/* 좌우 화살표 */}
                        <img className='Vector1' src={slides[activeSlideIndex].vector1} alt='Previous Slide' onClick={goToPrevSlide} />
                        <img className='Vector2' src={slides[activeSlideIndex].vector2} alt='Next Slide' onClick={goToNextSlide} />
                    </div>

                    {/* Learn more 버튼 */}
                    <div className='learnmore_box'>
                        <span className='Learnmore'>Learn more</span>
                    </div>
                </div>
            </div>
        </div>
    );
};

export default Slide;