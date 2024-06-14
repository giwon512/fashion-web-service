import React, { useState } from 'react';
import './header.css';
import { Link } from 'react-router-dom';

const Header = () => {
    const [isNewsDropdownVisible, setIsNewsDropdownVisible] = useState(false);
    const [isStyleDropdownVisible, setIsStyleDropdownVisible] = useState(false);
    const [isItemDropdownVisible, setIsItemDropdownVisible] = useState(false);
    const [isCommunityDropdownVisible, setIsCommunityDropdownVisible] = useState(false);

    const handleMouseEnter = (menu) => {
        switch (menu) {
            case 'news':
                setIsNewsDropdownVisible(true);
                break;
            case 'style':
                setIsStyleDropdownVisible(true);
                break;
            case 'item':
                setIsItemDropdownVisible(true);
                break;
            case 'community':
                setIsCommunityDropdownVisible(true);
                break;
            default:
                break;
        }
    };

    const handleMouseLeave = (menu) => {
        switch (menu) {
            case 'news':
                setIsNewsDropdownVisible(false);
                break;
            case 'style':
                setIsStyleDropdownVisible(false);
                break;
            case 'item':
                setIsItemDropdownVisible(false);
                break;
            case 'community':
                setIsCommunityDropdownVisible(false);
                break;
            default:
                break;
        }
    };

    return (
        <header className='header'>
            <div className='header_topbar'>
                <div className='header_top'>
                    <span className='header_top_txt'>Lorem Ipsum FASHION</span>
                </div>
            </div>

            <div className='header_logo'>
                <div className='header_logo_div'>
                    <span className='FASHION'>FASHION(로고로 대체)</span>
                </div>
            </div>

            <div className='header_menu'>
                <div className='header_mainMenu'>
                    <div className='header_mainmenu'>
                        <ul>
                            <li
                                onMouseEnter={() => handleMouseEnter('news')}
                                onMouseLeave={() => handleMouseLeave('news')}
                            >
                                <Link to="/newsPage">NEWS</Link>
                                {isNewsDropdownVisible && (
                                    <ul className='dropdown-menu'>
                                        <li><Link to="/news_best">BEST NEWS</Link></li>
                                        <li><Link to="/news_brand">BRAND NEWS</Link></li>
                                        <li><Link to="/news_celeb">CELEB NEWS</Link></li>
                                        <li><Link to="/news_trend">TREND NEWS</Link></li>
                                    </ul>
                                )}
                            </li>
                            <li
                                onMouseEnter={() => handleMouseEnter('style')}
                                onMouseLeave={() => handleMouseLeave('style')}
                            >
                                <Link to="/stylePage">STYLE</Link>
                                {isStyleDropdownVisible && (
                                    <ul className='dropdown-menu'>
                                        <li><Link to="/style_1">Style 1</Link></li>
                                        <li><Link to="/style_2">Style 2</Link></li>
                                        <li><Link to="/style_3">Style 3</Link></li>
                                        <li><Link to="/style_4">Style 4</Link></li>
                                    </ul>
                                )}
                            </li>
                            <li
                                onMouseEnter={() => handleMouseEnter('item')}
                                onMouseLeave={() => handleMouseLeave('item')}
                            >
                                <Link to="/itemPage">ITEM</Link>
                                {isItemDropdownVisible && (
                                    <ul className='dropdown-menu'>
                                        <li><Link to="/item_1">Item 1</Link></li>
                                        <li><Link to="/item_2">Item 2</Link></li>
                                        <li><Link to="/item_3">Item 3</Link></li>
                                        <li><Link to="/item_4">Item 4</Link></li>
                                    </ul>
                                )}
                            </li>
                            <li
                                onMouseEnter={() => handleMouseEnter('community')}
                                onMouseLeave={() => handleMouseLeave('community')}
                            >
                                <Link to="/communityPage">COMMUNITY</Link>
                                {isCommunityDropdownVisible && (
                                    <ul className='dropdown-menu'>
                                        <li><Link to="/community_1">Community 1</Link></li>
                                        <li><Link to="/community_2">Community 2</Link></li>
                                        <li><Link to="/community_3">Community 3</Link></li>
                                        <li><Link to="/community_4">Community 4</Link></li>
                                    </ul>
                                )}
                            </li>
                        </ul>
                    </div>
                </div>
                
                <div className='header_rightmenu'>
                    <span className='LOGIN'>LOGIN</span>
                    <span className='JOIN'> JOIN</span>
                    <div className='_icon'>
                        <span>돋보기 아이콘</span>
                    </div>
                </div>
            </div>
        </header>
    );
}

export default Header;
