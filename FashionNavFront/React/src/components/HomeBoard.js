import React, { useState, useEffect } from 'react';
import { Link, useNavigate } from 'react-router-dom';
import api from '../api';
import './HomeBoard.css';


const HomeBoard = ({ boardType }) => {
    const [posts, setPosts] = useState([]);

    useEffect(() => {
        fetchPosts();
    }, []);

    const fetchPosts = async () => {
        try {
            const response = await api.get(`/posts`, {
                params: {
                    boardType,
                    page : 1,
                    size: 6,
                },
            });
            setPosts(response.data.posts);
        } catch (error) {
            console.error('Failed to fetch posts', error);
        }
    };

    const truncateTitle = (title, maxLength) => {
        if (title.length > maxLength) {
            return title.substring(0, maxLength) + '...';
        }
        return title;
    };

    return (
        <div className='home-board'>
            <hr className="top-divider" /> {/* 구분선 */}
            <div className="section-header">
                {boardType === 'free' &&
                    <>
                        <h3>자유게시판</h3>
                        <Link to="/boards/free" className="more-link">MORE>></Link>
                    </> 
                }
                {boardType === 'event' &&
                    <>
                        <h3>이벤트게시판</h3>
                        <Link to="/boards/event" className="more-link">MORE>></Link>
                    </> 
                }
            </div>
            <table className='board-table'>
            {posts.map(post => (
                <tr>
                    <td>
                        {/* 제목이 20글자 넘는 경우 뒤 내용은 ...으로 처리 */}
                        <Link to={`/posts/${post.postId}`} className='title'>{truncateTitle(post.title, 20)}</Link><br />
                        <span>{post.userName}</span>
                    </td>
                </tr>
            ))}
            </table>
        </div>
    )
}

export default HomeBoard;