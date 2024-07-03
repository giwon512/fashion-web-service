import React, { useState, useEffect } from 'react';
import { Link, useNavigate } from 'react-router-dom';
import api from '../../api';
import PostForm from './PostForm';
import './Board.css';

const BoardPage = ({ boardType }) => {
    const [posts, setPosts] = useState([]);
    const [page, setPage] = useState(1);
    const [totalPages, setTotalPages] = useState(1);
    const [isWriting, setIsWriting] = useState(false);
    const navigate = useNavigate();

    useEffect(() => {
        fetchPosts();
    }, [boardType, page]);

    const fetchPosts = async () => {
        try {
            const response = await api.get(`/posts`, {
                params: {
                    boardType,
                    page,
                    size: 10, // 페이지당 표시할 게시물 수
                },
            });
            setPosts(response.data.posts);
            setTotalPages(response.data.totalPages);
        } catch (error) {
            console.error('Failed to fetch posts', error);
        }
    };

    const handlePostCreated = () => {
        setIsWriting(false);
        fetchPosts(); // 글 작성 후 게시물 목록 갱신
    };

    const handlePageChange = (newPage) => {
        if (newPage >= 1 && newPage <= totalPages) {
            setPage(newPage);
        }
    };

    return (
        <div className="board-page">
            <h1>{boardType} Board</h1>
            {isWriting ? (
                <PostForm boardType={boardType} onPostCreated={handlePostCreated} />
            ) : (
                <>
                    <button onClick={() => setIsWriting(true)}>글쓰기</button>
                    <table className="posts-table">
                        <thead>
                        <tr>
                            <th>제목</th>
                            <th>작성자</th>
                            <th>작성일</th>
                        </tr>
                        </thead>
                        <tbody>
                        {posts.map(post => (
                            <React.Fragment key={post.postId}>
                                <tr>
                                    <td>
                                        <Link to={`/posts/${post.postId}`}>{post.title}</Link>
                                    </td>
                                    <td>{post.userName}</td>
                                    <td>{new Date(post.createdAt).toLocaleString()}</td>
                                </tr>
                                {post.replies && post.replies.map(reply => (
                                    <tr key={reply.postId} className="reply-row">
                                        <td className="reply-title">
                                            ㄴ <Link to={`/posts/${reply.postId}`}>{reply.title}</Link>
                                        </td>
                                        <td>{reply.userName}</td>
                                        <td>{new Date(reply.createdAt).toLocaleString()}</td>
                                    </tr>
                                ))}
                            </React.Fragment>
                        ))}
                        </tbody>
                    </table>
                    <div className="pagination">
                        <button onClick={() => handlePageChange(page - 1)} disabled={page === 1}>
                            Previous
                        </button>
                        <span>Page {page} of {totalPages}</span>
                        <button onClick={() => handlePageChange(page + 1)} disabled={page === totalPages}>
                            Next
                        </button>
                    </div>
                </>
            )}
        </div>
    );
};

export default BoardPage;
