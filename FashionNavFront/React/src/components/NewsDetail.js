import React, { useEffect, useState } from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import api from '../api';
import './NewsDetail.css';

const NewsDetail = ({ isAdmin }) => {
    const { newsId } = useParams();
    const [news, setNews] = useState(null);
    const [allNewsList, setAllNewsList] = useState([]);
    const [comments, setComments] = useState([]);
    const [newComment, setNewComment] = useState("");
    const [isBookmarked, setIsBookmarked] = useState(false);
    const [currentUser, setCurrentUser] = useState(null); // 현재 사용자 정보 저장
    const [showComments, setShowComments] = useState(false); // 댓글 창 표시 상태
    const navigate = useNavigate();

    useEffect(() => {
        const fetchNewsDetail = async () => {
            try {
                const response = await api.get(`/news/detail/${newsId}`);
                setNews(response.data);

                const bookmarkResponse = await api.get('/page/archive');
                const bookmarkedNews = bookmarkResponse.data.body;
                setIsBookmarked(bookmarkedNews.some(bookmarked => bookmarked.newsId === parseInt(newsId)));

                const userResponse = await api.get('/users/me');
                setCurrentUser(userResponse.data.body); // 현재 사용자 정보 설정
            } catch (error) {
                console.error('Error fetching news detail:', error);
            }
        };

        const fetchComments = async () => {
            try {
                const response = await api.get(`/news-comments/news/${newsId}`);
                setComments(response.data);
            } catch (error) {
                console.error('Error fetching comments:', error);
            }
        };

        fetchNewsDetail();
        fetchComments();
    }, [newsId]);

    useEffect(() => {
        api.get('/processed-news?pageNum=1&pageSize=1000')
            .then(response => setAllNewsList(response.data.content))
            .catch(error => console.error('Error fetching all news:', error));
    }, []);

    const handlePreviousClick = () => {
        const currentIndex = allNewsList.findIndex(news => news.newsId === parseInt(newsId));
        if (currentIndex > 0) {
            navigate(`/news/details/${allNewsList[currentIndex - 1].newsId}`);
        }
    };

    const handleNextClick = () => {
        const currentIndex = allNewsList.findIndex(news => news.newsId === parseInt(newsId));
        if (currentIndex < allNewsList.length - 1) {
            navigate(`/news/details/${allNewsList[currentIndex + 1].newsId}`);
        }
    };

    const handleListClick = () => {
        const category = news?.category;
        if (category) {
            navigate(`/news/${category}`);
        } else {
            navigate('/');
        }
    };

    //수정된 부분
    const handleEditClick = () => {
        const currentPage = Math.ceil((allNewsList.findIndex(news => news.newsId === parseInt(newsId)) + 1) / 10);
        navigate(`/admin/processed-news?editNewsId=${newsId}&page=${currentPage}`);
    };

    const handleBookmarkToggle = async () => {
        try {
            if (isBookmarked) {
                await api.delete(`/page/archive/${newsId}`);
                alert('북마크가 삭제되었습니다.');
            } else {
                await api.post(`/page/archivesave/${newsId}`);
                alert('북마크가 추가되었습니다.');
            }
            setIsBookmarked(!isBookmarked);
        } catch (error) {
            console.error('북마크 토글 실패:', error);
            alert('북마크 토글에 실패했습니다.');
        }
    };

    const handleCommentSubmit = async (e) => {
        e.preventDefault();
        try {
            await api.post('/news-comments', { newsId, content: newComment });
            setNewComment(""); // Clear the input field
            // Force re-fetching the comments to update the list
            const response = await api.get(`/news-comments/news/${newsId}`);
            setComments(response.data);
        } catch (error) {
            console.error('Error submitting comment:', error);
        }
    };

    const handleDeleteComment = async (commentId) => {
        try {
            await api.delete(`/news-comments/${commentId}`);
            setComments(comments.filter(comment => comment.commentId !== commentId));
        } catch (error) {
            console.error('Error deleting comment:', error);
        }
    };

    if (!news) {
        return <div>Loading...</div>;
    }

    return (
        <div className="news-detail-container">
            <div className="news-detail">
                {isAdmin && (
                    <div className="edit-button-container">
                        <button className="edit-button" onClick={handleEditClick}>Edit</button>
                    </div>
                )}
                <button className="bookmark-button" onClick={handleBookmarkToggle}>
                    {isBookmarked ? '북마크 해제' : '북마크'}
                </button>
                <h2>{news.title}</h2>
                <div dangerouslySetInnerHTML={{ __html: news.content }} />
                <div className="navigation-buttons">
                    <button
                        className="previous-button"
                        onClick={handlePreviousClick}
                        disabled={allNewsList.findIndex(news => news.newsId === parseInt(newsId)) === 0}
                    >
                        이전
                    </button>
                    <button
                        className="list-button"
                        onClick={handleListClick}
                    >
                        목록
                    </button>
                    <button
                        className="next-button"
                        onClick={handleNextClick}
                        disabled={allNewsList.findIndex(news => news.newsId === parseInt(newsId)) === allNewsList.length - 1}
                    >
                        다음
                    </button>
                </div>

                <div className="comments-section">
                    <h3 onClick={() => setShowComments(!showComments)}>
                        Comments ({comments.length})
                    </h3>
                    {showComments && (
                        <>
                            <form onSubmit={handleCommentSubmit}>
                                <textarea
                                    value={newComment}
                                    onChange={(e) => setNewComment(e.target.value)}
                                    placeholder="Add a comment..."
                                    required
                                />
                                <button type="submit">Submit</button>
                            </form>
                            <div className="comments-list">
                                {comments.map(comment => (
                                    <div key={comment.commentId} className="comment">
                                        <p>{comment.content}</p>
                                        <small>{new Date(comment.createdAt).toLocaleString()}</small>
                                        {currentUser && comment.userId === currentUser.userId && (
                                            <button
                                                className="delete-button"
                                                onClick={() => handleDeleteComment(comment.commentId)}
                                            >
                                                삭제
                                            </button>
                                        )}
                                    </div>
                                ))}
                            </div>
                        </>
                    )}
                </div>
            </div>
        </div>
    );
};

export default NewsDetail;