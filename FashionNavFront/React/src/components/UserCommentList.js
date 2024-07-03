import React, { useEffect, useState } from 'react';
import { Link } from 'react-router-dom';
import './UserComment.css';
import api from '../api';

const UserCommentList = () => {
  const [comments, setComments] = useState([]);
  const [userName, setUserName] = useState('');
  const [pageTotal, setPageTotal] = useState(0);
  const [currentPage, setCurrentPage] = useState(1);
  const [pageSize] = useState(10);

  useEffect(() => {
    fetchComments(currentPage, pageSize);
  }, [currentPage, pageSize]);

  const fetchComments = async (page, size) => {
    try {
      const response = await api.get(`/user/comment`, {
        params: {
          sizeNum: page,
          pageSize: size,
        },
      });
      const data = response.data.body;
      setComments(data.userComment);
      setUserName(data.userName);
      setPageTotal(data.pageTotal);
    } catch (error) {
      console.error('댓글을 불러오는 중 오류 발생:', error);
    }
  };

  const handlePageChange = (newPage) => {
    setCurrentPage(newPage);
  };

  return (
    <div className="user-comment-list">
      <h2>작성한 댓글 목록</h2>
      <h3>{userName} 님의 댓글</h3>
      <table>
        <thead>
          <tr>
            <th>댓글 내용</th>
            <th>작성일</th>
            <th>작성 위치</th>
          </tr>
        </thead>
        <tbody>
          {comments.map((comment) => (
            <tr key={comment.commentId}>
              <td>
                {comment.commentType === 'Post' ? (
                  <Link to={`/posts/${comment.postId}`}>{comment.content}</Link>
                ) : (
                  <Link to={`/news/details/${comment.newsId}`}>{comment.content}</Link>
                )}
              </td>
              <td>{comment.createdAt.split('T')[0]}</td>
              <td>{comment.commentType === 'Post' ? comment.postTitle : comment.newsTitle}</td>
            </tr>
          ))}
        </tbody>
      </table>
      <div className="pagination">
        {[...Array(pageTotal)].map((_, index) => (
          <button
            key={index}
            onClick={() => handlePageChange(index + 1)}
            disabled={currentPage === index + 1}
          >
            {index + 1}
          </button>
        ))}
      </div>
    </div>
  );
};

export default UserCommentList;