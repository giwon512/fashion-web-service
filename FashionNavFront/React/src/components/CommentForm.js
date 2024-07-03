import React, { useState } from 'react';
import api from '../api';
import './CommentForm.css';

const CommentForm = ({ postId, onCommentCreated }) => {
    const [content, setContent] = useState('');

    const handleSubmit = async (e) => {
        e.preventDefault();
        const comment = {
            content,
            postId,
        };

        try {
            await api.post(`/posts/${postId}/comments`, comment);
            setContent('');
            onCommentCreated();
        } catch (error) {
            console.error('Failed to create comment', error);
            alert('Failed to create comment');
        }
    };

    return (
        <form onSubmit={handleSubmit} className="comment-form">
            <textarea
                value={content}
                onChange={(e) => setContent(e.target.value)}
                required
                placeholder="댓글을 입력하세요"
            />
            <button type="submit">댓글 작성</button>
        </form>
    );
};

export default CommentForm;
