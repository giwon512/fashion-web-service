import React, { useState } from 'react';
import api from '../../api';
import './Board.css';

const PostForm = ({ boardType, onPostCreated }) => {
    const [title, setTitle] = useState('');
    const [content, setContent] = useState('');
    const [file, setFile] = useState(null);

    const handleSubmit = async (e) => {
        e.preventDefault();
        const post = {
            title,
            content,
            boardType,
        };

        const formData = new FormData();
        formData.append('post', new Blob([JSON.stringify(post)], { type: 'application/json' }));

        if (file) {
            formData.append('file', file);
        }

        try {
            await api.post('/posts', formData, {
                headers: {
                    'Content-Type': 'multipart/form-data',
                },
            });
            alert('Post created successfully');
            setTitle('');
            setContent('');
            setFile(null);
            onPostCreated(); // 글 작성 후 콜백 호출
        } catch (error) {
            console.error('Failed to create post', error);
            alert('Failed to create post');
        }
    };

    return (
        <form onSubmit={handleSubmit} className="post-form">
            <label>Title</label>
            <input type="text" value={title} onChange={(e) => setTitle(e.target.value)} required />
            <label>Content</label>
            <textarea value={content} onChange={(e) => setContent(e.target.value)} required />
            <label>File</label>
            <input type="file" onChange={(e) => setFile(e.target.files[0])} />
            <button type="submit">Submit</button>
        </form>
    );
};

export default PostForm;
