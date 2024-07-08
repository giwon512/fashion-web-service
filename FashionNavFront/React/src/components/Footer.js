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
                        <Link to="/news/best" className="FASHION_footer">NEWS</Link>
                        <ul>
                            <li><Link to="/news/best">BEST NEWS</Link></li>
                            <li><Link to="/news/brand">BRAND NEWS</Link></li>
                            <li><Link to="/news/celeb">CELEB NEWS</Link></li>
                            <li><Link to="/news/trend">TREND NEWS</Link></li>
                        </ul>
                    </div>
                    <div className='sitemap-menu'>
                        <Link to="/boards/free" className="FASHION_footer">COMMUNITY</Link>
                        <ul>
                            <li><Link to="/boards/notice">NOTICE BOARD</Link></li>
                            <li><Link to="/boards/faq">FAQ BOARD</Link></li>
                            <li><Link to="/boards/data">DATA BOARD</Link></li>
                            <li><Link to="/boards/event">EVENT BOARD</Link></li>
                            <li><Link to="/boards/free">FREE BOARD</Link></li>
                        </ul>
                    </div>
                    <div className='sitemap-menu'>
                        <Link to="/stylePage" className="FASHION_footer">STYLE</Link>
                        
                    </div>
                    <div className='sitemap-menu'>
                        <Link to="/itemPage" className="FASHION_footer">ITEM</Link>
                        
                    </div>
                </div>
            </div>
            
        </div>
    )
}