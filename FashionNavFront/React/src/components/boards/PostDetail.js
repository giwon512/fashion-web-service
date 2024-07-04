import React, { useState, useEffect } from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import api from '../../api';
import CommentForm from '../CommentForm';
import './PostDetail.css';

const PostDetail = () => {
    const { postId } = useParams();
    const [post, setPost] = useState(null);
    const [comments, setComments] = useState([]);
    const [userId, setUserId] = useState(null);
    const [isEditingPost, setIsEditingPost] = useState(false);
    const [editPostTitle, setEditPostTitle] = useState('');
    const [editPostContent, setEditPostContent] = useState('');
    const [replyCommentId, setReplyCommentId] = useState(null);
    const [replyContent, setReplyContent] = useState('');
    const [files, setFiles] = useState([]);
    const navigate = useNavigate();

    useEffect(() => {
        const fetchUserData = async () => {
            try {
                const response = await api.get('/users/me');
                setUserId(response.data.body.userId);
            } catch (error) {
                console.error('Failed to fetch user data', error);
            }
        };

        fetchUserData();
        fetchPost();
        fetchComments();
        fetchFiles();
    }, [postId]);

    const fetchPost = async () => {
        try {
            const response = await api.get(`/posts/${postId}`);
            setPost(response.data);
            setEditPostTitle(response.data.title);
            setEditPostContent(response.data.content);
        } catch (error) {
            console.error('Failed to fetch post', error);
        }
    };

    const fetchComments = async () => {
        try {
            const response = await api.get(`/posts/${postId}/comments`);
            setComments(response.data);
        } catch (error) {
            console.error('Failed to fetch comments', error);
        }
    };

    const fetchFiles = async () => {
        try {
            const response = await api.get(`/posts/${postId}/files`);
            setFiles(response.data);
        } catch (error) {
            console.error('Failed to fetch files', error);
        }
    };

    const handleDeletePost = async () => {
        try {
            await api.delete(`/posts/${postId}`);
            navigate(`/boards/${post.boardType}`); // 게시물 삭제 후 해당 게시판으로 이동
        } catch (error) {
            console.error('Failed to delete post', error);
            alert('Failed to delete post');
        }
    };

    const handleDeleteComment = async (commentId) => {
        try {
            await api.delete(`/posts/${postId}/comments/${commentId}`);
            fetchComments(); // 댓글 삭제 후 목록 갱신
        } catch (error) {
            console.error('Failed to delete comment', error);
            alert('Failed to delete comment');
        }
    };

    const handleDeleteFile = async (fileId) => {
        try {
            await api.delete(`/posts/${postId}/files/${fileId}`);
            fetchFiles(); // 파일 삭제 후 목록 갱신
        } catch (error) {
            console.error('Failed to delete file', error);
            alert('Failed to delete file');
        }
    };

    const handleEditPost = () => {
        setIsEditingPost(true);
    };

    const handleUpdatePost = async () => {
        try {
            const updatedPost = {
                ...post,
                title: editPostTitle,
                content: editPostContent,
            };
            await api.put(`/posts/${postId}`, updatedPost);
            setIsEditingPost(false);
            fetchPost(); // 게시물 업데이트 후 다시 가져오기
        } catch (error) {
            console.error('Failed to update post', error);
            alert('Failed to update post');
        }
    };

    const handleCancelEditPost = () => {
        setIsEditingPost(false);
        setEditPostTitle(post.title);
        setEditPostContent(post.content);
    };

    const handleReplyComment = (commentId) => {
        setReplyCommentId(commentId);
    };

    const handleCreateCommentReply = async () => {
        try {
            const newCommentReply = {
                content: replyContent,
                parentCommentId: replyCommentId,
                postId: postId, // Ensure postId is set
            };
            await api.post(`/posts/${postId}/comments`, newCommentReply);
            setReplyCommentId(null);
            setReplyContent('');
            fetchComments(); // 댓글 생성 후 목록 갱신
        } catch (error) {
            console.error('Failed to create comment reply', error);
            alert('Failed to create comment reply');
        }
    };

    const handleCancelCommentReply = () => {
        setReplyCommentId(null);
        setReplyContent('');
    };

    const handleFileUpload = async (e) => {
        const file = e.target.files[0];
        if (file) {
            const formData = new FormData();
            formData.append('file', file);

            try {
                await api.post(`/posts/${postId}/files`, formData, {
                    headers: {
                        'Content-Type': 'multipart/form-data',
                    },
                });
                fetchFiles(); // 파일 업로드 후 목록 갱신
            } catch (error) {
                console.error('Failed to upload file', error);
                alert('Failed to upload file');
            }
        }
    };

    const handleWriteReply = () => {
        navigate(`/posts/${postId}/replies/write`);
    };

    const handleNavigateBack = () => {
        if (post && post.boardType) {
            switch (post.boardType) {
                case 'notice':
                    navigate('/boards/notice');
                    break;
                case 'faq':
                    navigate('/boards/faq');
                    break;
                case 'event':
                    navigate('/boards/event');
                    break;
                case 'free':
                    navigate('/boards/free');
                    break;
                case 'data':
                    navigate('/boards/data');
                    break;
                default:
                    navigate('/');
                    break;
            }
        } else {
            navigate('/');
        }
    };

    const renderComments = (comments, level = 0) => {
        return comments.map(comment => (
            <div key={comment.commentId} className={`comment-item level-${level}`}>
                {replyCommentId === comment.commentId ? (
                    <div>
                        <textarea
                            value={replyContent}
                            onChange={(e) => setReplyContent(e.target.value)}
                        />
                        <button onClick={handleCreateCommentReply}>Save</button>
                        <button onClick={handleCancelCommentReply}>Cancel</button>
                    </div>
                ) : (
                    <div>
                        <p>{comment.content}</p>
                        <div className="comment-meta">
                            <p>작성자: {comment.userName}</p>
                            <p>작성일: {new Date(comment.createdAt).toLocaleString()}</p>
                        </div>
                        <div className="comment-actions">
                            <button onClick={() => handleReplyComment(comment.commentId)}>대댓글</button>
                            {comment.userId === userId && (
                                <button onClick={() => handleDeleteComment(comment.commentId)}>삭제</button>
                            )}
                        </div>
                    </div>
                )}
                {comment.replies && comment.replies.length > 0 && renderComments(comment.replies, level + 1)}
            </div>
        ));
    };

    return (
        <div className="post-detail-container">
            {post && (
                <>
                    {isEditingPost ? (
                        <div className="post-edit-form">
                            <input
                                type="text"
                                value={editPostTitle}
                                onChange={(e) => setEditPostTitle(e.target.value)}
                                className="edit-title-input"
                            />
                            <textarea
                                value={editPostContent}
                                onChange={(e) => setEditPostContent(e.target.value)}
                                className="edit-content-textarea"
                            />
                            <div className="post-actions">
                                <button onClick={handleUpdatePost}>Save</button>
                                <button onClick={handleCancelEditPost}>Cancel</button>
                            </div>
                            <div className="file-list">
                                <h3>첨부파일</h3>
                                <input type="file" onChange={handleFileUpload} />
                                {files.map(file => (
                                    <div key={file.fileId} className="file-item">
                                        <a href={`http://localhost:8080/api/posts/files/download/${file.fileId}`} target="_blank" rel="noopener noreferrer">{file.fileName}</a>
                                        {file.userId === userId && (
                                            <button onClick={() => handleDeleteFile(file.fileId)}>삭제</button>
                                        )}
                                    </div>
                                ))}
                            </div>
                        </div>
                    ) : (
                        <div className="post-header">
                            <h1 className="post-title">{post.title}</h1>
                            <p className="post-content">{post.content}</p>
                            <div className="post-meta">
                                <p>작성자: {post.userName}</p>
                                <p>작성일: {new Date(post.createdAt).toLocaleString()}</p>
                            </div>
                            {post.userId === userId && (
                                <div className="post-actions">
                                    <button onClick={handleEditPost}>수정</button>
                                    <button onClick={handleDeletePost}>삭제</button>
                                </div>
                            )}
                            <button onClick={handleWriteReply}>답글</button>
                            <div className="file-list">
                                <h3>첨부파일</h3>
                                {files.map(file => (
                                    <div key={file.fileId} className="file-item">
                                        <a href={`http://localhost:8080/api/posts/files/download/${file.fileId}`} target="_blank" rel="noopener noreferrer">{file.fileName}</a>
                                    </div>
                                ))}
                            </div>
                        </div>
                    )}
                    <CommentForm postId={postId} onCommentCreated={fetchComments} />
                    <div className="comment-section">
                        {renderComments(comments)}
                    </div>
                    <button onClick={handleNavigateBack}>목록으로</button>
                </>
            )}
        </div>
    );
};

export default PostDetail;
