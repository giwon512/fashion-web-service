import React, { useState, useEffect } from 'react';
import { Link } from 'react-router-dom';
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
                    page: 1,
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
            <div className="section-header">
                {boardType === 'free' &&
                    <>
                        <h3>FREE BOARD</h3>
                        <Link to="/boards/free" className="more-link">MORE>></Link>
                    </>
                }
                {boardType === 'event' &&
                    <>
                        <h3>EVENT BOARD</h3>
                        <Link to="/boards/event" className="more-link">MORE>></Link>
                    </>
                }
            </div>
            <table className='board-table'>
                <tbody>
                    {posts.map(post => (
                        <tr key={post.postId}>
                            <td>
                                <Link to={`/posts/${post.postId}`} className='title'>{truncateTitle(post.title, 20)}</Link><br />
                                <span>{post.userName}</span>
                                <span>{new Date(post.createdAt).toLocaleDateString()}</span>
                            </td>
                        </tr>
                    ))}
                </tbody>
            </table>
        </div>
    );
}

export default HomeBoard;
