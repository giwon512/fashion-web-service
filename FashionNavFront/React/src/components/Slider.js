import React, { useState, useEffect } from 'react';
import Slider from 'react-slick';
import api from '../api';
import 'slick-carousel/slick/slick.css';
import 'slick-carousel/slick/slick-theme.css';
import './Slider.css';

const CustomPrevArrow = (props) => {
    const { className, style, onClick } = props;
    return (
        <div
            className={`${className} custom-prev-arrow`}
            style={{ ...style }}
            onClick={onClick}
        >
            ‹
        </div>
    );
};

const CustomNextArrow = (props) => {
    const { className, style, onClick } = props;
    return (
        <div
            className={`${className} custom-next-arrow`}
            style={{ ...style }}
            onClick={onClick}
        >
            ›
        </div>
    );
};

const CustomSlider = () => {
    const [banners, setBanners] = useState([]);

    useEffect(() => {
        const fetchBanners = async () => {
            try {
                const response = await api.get('/banners');
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
        arrows: true,
        prevArrow: <CustomPrevArrow />,
        nextArrow: <CustomNextArrow />
    };

    const handleBannerClick = (url) => {
        if (url) {
            window.location.href = url;
        }
    };

    return (
        <div className="slider-container">
            <Slider {...settings}>
                {banners.map((banner) => (
                    <div key={banner.bannerId} className="slide">
                        <img src={"data:image/jpg;base64," + banner.imageUrl} alt={banner.title} className="slide-image" />
                        <div className="slide-caption">
                            <h1
                                className="clickable"
                                onClick={() => handleBannerClick(banner.url)}
                            >
                                {banner.title}
                            </h1>
                            <div
                                className="clickable"
                                onClick={() => handleBannerClick(banner.url)}
                            >
                                <h2>{banner.description}</h2>
                            </div>
                        </div>
                    </div>
                ))}
            </Slider>
        </div>
    );
};

export default CustomSlider;
