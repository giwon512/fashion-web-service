import React, { useState } from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import api from '../../api';
import './ReplyForm.css';

const ReplyForm = () => {
    const { postId } = useParams();
    const [title, setTitle] = useState('');
    const [content, setContent] = useState('');
    const navigate = useNavigate();

    const handleSubmit = async (e) => {
        e.preventDefault();
        try {
            await api.post(`/posts/${postId}/replies`, { title, content }, {
                headers: {
                    'Content-Type': 'application/json',
                }
            });
            navigate(`/posts/${postId}`);
        } catch (error) {
            console.error('Failed to create reply', error);
            alert('Failed to create reply');
        }
    };

    return (
        <div className="reply-form-container">
            <form onSubmit={handleSubmit} className="reply-form">
                <h2>Write a Reply</h2>
                <div className="form-group">
                    <label htmlFor="title">Title</label>
                    <input
                        type="text"
                        id="title"
                        value={title}
                        onChange={(e) => setTitle(e.target.value)}
                        placeholder="Enter the title"
                        required
                    />
                </div>
                <div className="form-group">
                    <label htmlFor="content">Content</label>
                    <textarea
                        id="content"
                        value={content}
                        onChange={(e) => setContent(e.target.value)}
                        placeholder="Enter your reply"
                        required
                    />
                </div>
                <div className="form-actions">
                    <button type="submit" className="submit-button">Submit</button>
                    <button type="button" className="cancel-button" onClick={() => navigate(`/posts/${postId}`)}>Cancel</button>
                </div>
            </form>
        </div>
    );
};

export default ReplyForm;
