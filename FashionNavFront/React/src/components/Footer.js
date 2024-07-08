import React from 'react'
import { Link } from 'react-router-dom'
import './Footer.css'
export default function Footer () {
    return (
        <div className='footer'>
            <div className='footer-content'>
                <div className="header_logo">
                    <Link to="/" className="FASHION_footer">FASHION NAV</Link>
                </div>
                <div className='sitemap'>
                    <div className='sitemap-menu'>
                        <div className="FASHION_footer">NEWS</div>
                        <ul>
                            <li><Link to="/news/best">BEST NEWS</Link></li>
                            <li><Link to="/news/brand">BRAND NEWS</Link></li>
                            <li><Link to="/news/celeb">CELEB NEWS</Link></li>
                            <li><Link to="/news/trend">TREND NEWS</Link></li>
                        </ul>
                    </div>
                    <div className='sitemap-menu'>
                        <div className="FASHION_footer">STYLE</div>
                        <ul>
                            <li><Link to="/style_1">Style 1</Link></li>
                            <li><Link to="/style_2">Style 2</Link></li>
                            <li><Link to="/style_3">Style 3</Link></li>
                            <li><Link to="/style_4">Style 4</Link></li>
                        </ul>
                    </div>
                    <div className='sitemap-menu'>
                        <div className="FASHION_footer">ITEM</div>
                        <ul>
                            <li><Link to="/item_1">Item 1</Link></li>
                            <li><Link to="/item_2">Item 2</Link></li>
                            <li><Link to="/item_3">Item 3</Link></li>
                            <li><Link to="/item_4">Item 4</Link></li>
                        </ul>
                    </div>
                    <div className='sitemap-menu'>
                        <div className="FASHION_footer">COMMUNITY</div>
                        <ul>
                        <li><Link to="/boards/notice">Notice Board</Link></li>
                        <li><Link to="/boards/faq">FAQ Board</Link></li>
                        <li><Link to="/boards/data">Data Board</Link></li>
                        <li><Link to="/boards/event">Event Board</Link></li>
                        <li><Link to="/boards/free">Free Board</Link></li>
                        </ul>
                    </div>
                </div>
            </div>
            
        </div>
    )
}