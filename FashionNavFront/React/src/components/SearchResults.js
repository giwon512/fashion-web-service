import React, { useState, useEffect } from 'react';
import { useNavigate, useLocation, Link } from 'react-router-dom';
import axios from 'axios';
import './SearchResults.css';

const api = axios.create({
  baseURL: 'http://192.168.0.124:8080', // 실제 서버 주소로 변경
});

api.interceptors.request.use(config => {
  const token = localStorage.getItem('token'); // 토큰을 로컬 스토리지에서 가져옴
  if (token) {
    config.headers['Authorization'] = `Bearer ${token}`;
  }
  return config;
}, error => {
  return Promise.reject(error);
});

const SearchResults = () => {
  const [results, setResults] = useState([]);
  const [isLoading, setIsLoading] = useState(false);
  const [error, setError] = useState(null);
  const [totalPages, setTotalPages] = useState(1);
  const [searchType, setSearchType] = useState('title');

  const navigate = useNavigate();
  const location = useLocation();

  const searchParams = new URLSearchParams(location.search);
  const keyword = searchParams.get('keyword') || '';
  const page = parseInt(searchParams.get('page') || '1', 10);
  const size = parseInt(searchParams.get('size') || '10', 10);

  useEffect(() => {
    setIsLoading(true);
    setError(null);

    const fetchResults = async () => {
      try {
        const response = await api.get('/api/search', { params: { keyword, page, size, type: searchType } });
        setResults(response.data.content);
        setTotalPages(response.data.totalPages);
      } catch (error) {
        console.error('Error fetching search results:', error);
        setError('검색 결과를 불러오는 데 실패했습니다.');
      } finally {
        setIsLoading(false);
      }
    };

    fetchResults();
  }, [keyword, page, size, searchType]);

  const handleSearch = (event) => {
    event.preventDefault();
    const searchKeyword = event.target.keyword.value;
    navigate(`/search?keyword=${searchKeyword}&page=1&size=${size}&type=${searchType}`);
  };

  const handlePageChange = (newPage) => {
    navigate(`/search?keyword=${keyword}&page=${newPage}&size=${size}&type=${searchType}`);
  };

  if (isLoading) return <div>로딩 중...</div>;
  if (error) return <div>{error}</div>;

  return (
      <div className="search-container">
        <div className="search-form">
          <form onSubmit={handleSearch}>
            <input name="keyword" defaultValue={keyword} placeholder="검색어를 입력하세요" />
            <select name="searchType" value={searchType} onChange={(e) => setSearchType(e.target.value)}>
              <option value="title">제목 검색</option>
              <option value="content">내용 검색</option>
            </select>
            <button type="submit">검색</button>
          </form>
        </div>

        <div className="search-results">
          <h2>{keyword ? `"${keyword}" 검색 결과` : '전체 뉴스'}</h2>

          <table className="results-table">
            <thead>
            <tr>
              <th>번호</th>
              <th>게시판 분류</th>
              <th>제목</th>
              <th>등록일</th>
            </tr>
            </thead>
            <tbody>
            {results.map((result, index) => (
                <tr key={result.newsId}>
                  <td>{index + 1 + ((page - 1) * size)}</td>
                  <td>{result.category}</td>
                  <td>
                    <Link to={`/news/details/${result.newsId}`}>
                      {result.title}
                    </Link>
                  </td>
                  <td>{new Date(result.publishedDate).toLocaleDateString()}</td>
                </tr>
            ))}
            </tbody>
          </table>
        </div>

        <div className="pagination">
          {page > 1 && (
              <button onClick={() => handlePageChange(page - 1)}>
                &lt; 이전
              </button>
          )}
          {Array.from({ length: totalPages }, (_, index) => (
              <button
                  key={index}
                  className={index + 1 === page ? 'active' : ''}
                  onClick={() => handlePageChange(index + 1)}
              >
                {index + 1}
              </button>
          ))}
          {page < totalPages && (
              <button onClick={() => handlePageChange(page + 1)}>
                다음 &gt;
              </button>
          )}
        </div>
      </div>
  );
};

export default SearchResults;
