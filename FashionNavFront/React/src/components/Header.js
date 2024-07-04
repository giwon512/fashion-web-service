import React, { useState } from "react";
import { Link, useNavigate } from "react-router-dom";
import "./Header.css";

const Header = ({ isLoggedIn, isAdmin, onLogout }) => {
  const [isNewsDropdownVisible, setIsNewsDropdownVisible] = useState(false);
  const [isStyleDropdownVisible, setIsStyleDropdownVisible] = useState(false);
  const [isItemDropdownVisible, setIsItemDropdownVisible] = useState(false);
  const [isCommunityDropdownVisible, setIsCommunityDropdownVisible] = useState(false);
  const [isAdminDropdownVisible, setIsAdminDropdownVisible] = useState(false);
  const [isSearchVisible, setIsSearchVisible] = useState(false); // 검색 창 표시 여부
  const [searchTerm, setSearchTerm] = useState(''); // 검색어

  const navigate = useNavigate(); // 페이지 이동을 위한 useNavigate 훅

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
      case 'admin':
        setIsAdminDropdownVisible(true);
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
      case 'admin':
        setIsAdminDropdownVisible(false);
        break;
      default:
        break;
    }
  };

  const handleSearchIconClick = () => {
    setIsSearchVisible(!isSearchVisible);
  };

  const handleSearchTermChange = (event) => {
    setSearchTerm(event.target.value);
  };

  const handleSearch = () => {
    if (searchTerm.trim() !== '') {
      navigate(`/search?keyword=${searchTerm}`);
      setSearchTerm('');
      setIsSearchVisible(false);
    }
  };

  return (
    <header className="header">
      <div className="header_topbar">
        <div className="header_top">
          <span className="header_top_txt">Lorem Ipsum FASHION</span>
        </div>
      </div>

      <div className="header_main">
        <div className="header_logo">
          <Link to="/" className="FASHION">FASHION NAV</Link>
        </div>

        <div className="header_menu">
          <div className="header_mainmenu">
            <ul>
              <li
                onMouseEnter={() => handleMouseEnter('news')}
                onMouseLeave={() => handleMouseLeave('news')}
              >
                <Link to="/news/best">NEWS</Link>
                {isNewsDropdownVisible && (
                  <ul className="dropdown-menu">
                    <li><Link to="/news/best">BEST NEWS</Link></li>
                    <li><Link to="/news/brand">BRAND NEWS</Link></li>
                    <li><Link to="/news/celeb">CELEB NEWS</Link></li>
                    <li><Link to="/news/trend">TREND NEWS</Link></li>
                  </ul>
                )}
              </li>
              <li
                onMouseEnter={() => handleMouseEnter('style')}
                onMouseLeave={() => handleMouseLeave('style')}
              >
                <Link to="/stylePage">STYLE</Link>
                {isStyleDropdownVisible && (
                  <ul className="dropdown-menu">
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
                  <ul className="dropdown-menu">
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
                  <ul className="dropdown-menu">
                    <li><Link to="/boards/notice">Notice Board</Link></li>
                    <li><Link to="/boards/faq">FAQ Board</Link></li>
                    <li><Link to="/boards/data">Data Board</Link></li>
                    <li><Link to="/boards/event">Event Board</Link></li>
                    <li><Link to="/boards/free">Free Board</Link></li>
                  </ul>
                )}
              </li>
              {isAdmin && (
                <li
                  onMouseEnter={() => handleMouseEnter('admin')}
                  onMouseLeave={() => handleMouseLeave('admin')}
                >
                  <Link to="/admin">ADMIN</Link>
                  {isAdminDropdownVisible && (
                    <ul className="dropdown-menu">
                      <li><Link to="/admin/news">Manage News</Link></li>
                      <li><Link to="/admin/banners">Manage Banners</Link></li>
                      <li><Link to="/admin/surveys">Manage Surveys</Link></li>
                      <li><Link to="/admin/processed-news">Manage Processed News</Link></li> {/* 새로운 메뉴 추가 */}
                    </ul>
                  )}
                </li>
              )}
            </ul>
          </div>
        </div>

        <div className="header_rightmenu">
          {isLoggedIn ? (
            <>
              <span className="MY_PAGE"><Link to="/mypage">MYPAGE</Link></span>
              <span className="LOGOUT" onClick={onLogout}>LOGOUT</span>
            </>
          ) : (
            <>
              <span className="LOGIN"><Link to="/login">LOGIN</Link></span>
              <span className="JOIN"><Link to="/join">JOIN</Link></span>
            </>
          )}
          <div className="_icon">
            <svg
              onClick={handleSearchIconClick}
              xmlns="http://www.w3.org/2000/svg"
              viewBox="0 0 24 24"
              fill="none"
              stroke="currentColor"
              strokeWidth="2"
              strokeLinecap="round"
              strokeLinejoin="round"
              style={{ width: '24px', height: '24px', cursor: 'pointer' }}
            >
              <circle cx="11" cy="11" r="8" />
              <line x1="21" y1="21" x2="16.65" y2="16.65" />
            </svg>
            {isSearchVisible && (
              <div className="search-input">
                <input
                  type="text"
                  placeholder="검색어를 입력하세요"
                  value={searchTerm}
                  onChange={handleSearchTermChange}
                  onKeyDown={(event) => {
                    if (event.key === 'Enter') {
                      handleSearch();
                    }
                  }}
                />
                <button onClick={handleSearch}>검색</button>
              </div>
            )}
          </div>
        </div>
      </div>
    </header>
  );
};

export default Header;
