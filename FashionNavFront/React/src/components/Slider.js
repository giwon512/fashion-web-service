import React, { useState, useEffect } from 'react';
import Slider from 'react-slick';
import api from '../api'; // API 호출을 위한 모듈
import 'slick-carousel/slick/slick.css';
import 'slick-carousel/slick/slick-theme.css';
import './Slider.css'; // 커스텀 CSS 파일

const CustomSlider = () => {
    const [banners, setBanners] = useState([]);

    useEffect(() => {
        const fetchBanners = async () => {
            try {
                const response = await api.get('/banners'); // 배너 데이터를 가져오는 API 엔드포인트
                setBanners(response.data);
            } catch (error) {
                console.error('Error fetching banners:', error);
            }
        };
        fetchBanners();
    }, []);

    const settings = {
        dots: true,
        infinite: true,
        speed: 500,
        slidesToShow: 1,
        slidesToScroll: 1,
        autoplay: true,
        autoplaySpeed: 3000,
    };

    return (
        <div className="slider-container">
            <Slider {...settings}>
                {banners.map((banner) => (
                    <div key={banner.bannerId} className="slide">
                        <img src={banner.imageUrl} alt={banner.title} className="slide-image" />
                        <div className="slide-caption">
                            <h2>{banner.title}</h2>
                        </div>
                    </div>
                ))}
            </Slider>
        </div>
    );
};

export default CustomSlider;
