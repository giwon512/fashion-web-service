import React, { useState, useEffect } from 'react';
import './contents1.css';
import ContentTitle from './ContentTitle.js';
import axios from 'axios';

export default function Contents1() {
    const [imageUrl, setImageUrl] = useState('');
    const [articleTitle, setArticleTitle] = useState('');

    useEffect(() => {
        const fetchData = async () => {
            try {
                // 이미지 URL을 가져오는 API 호출 (예시에서는 jsonplaceholder.typicode.com 사용)
                const imageResponse = await axios.get('');
                setImageUrl(imageResponse.data.url); // 이미지 URL 설정

                // 기사 제목을 가져오는 API 호출 (예시에서는 jsonplaceholder.typicode.com 사용)
                const articleResponse = await axios.get('');
                setArticleTitle(articleResponse.data.title); // 기사 제목 설정
            } catch (error) {
                console.error('Error fetching data:', error);
            }
        };

        fetchData();
    }, []);

    return (
		<div className='Content_wrap'>
			<div className='content1'/>

			{/* content title */}
			<ContentTitle title="CELEBRITY NEWS" moreLink="more >" />

			{/* 뉴스 한칸 */}
			<div className='con1_wrap'>

				<div className='con1_1'>
					{/* 이미지 */}
					<div className='con1_img'>
						{imageUrl && <img src={imageUrl} alt="Celebrity" />}
					</div>

					{/* 기사 제목 */}
					<div className='con1_1_txt'>
						<span className='_4_5'>{articleTitle}</span>
                        {/* <Link to={`/news/${articleTitle}`}>{articleTitle}</Link> */}
					</div>
				</div>



                <div className='con1_1'>
					{/* 이미지 */}
					<div className='con1_img'>
						{imageUrl && <img src={imageUrl} alt="Celebrity" />}
					</div>

					{/* 기사 제목 */}
					<div className='con1_1_txt'>
						<span className='_4_5'>{articleTitle}</span>
                        {/* <Link to={`/news/${articleTitle}`}>{articleTitle}</Link> */}
					</div>
				</div>

                
                <div className='con1_1'>
					{/* 이미지 */}
					<div className='con1_img'>
						{imageUrl && <img src={imageUrl} alt="Celebrity" />}
					</div>

					{/* 기사 제목 */}
					<div className='con1_1_txt'>
						<span className='_4_5'>{articleTitle}</span>
                        {/* <Link to={`/news/${articleTitle}`}>{articleTitle}</Link> */}
					</div>
				</div>
                
			</div>
		</div>
        
        
    );
}


// import React, { useState, useEffect } from 'react';
// import './contents1.css';
// import ContentTitle from './ContentTitle.js';
// import axios from 'axios';

// const Contents1 = () => {
//     const [articles, setArticles] = useState([]);

//     useEffect(() => {
//         const fetchData = async () => {
//             try {
//                 // 이미지 URL을 가져오는 API 호출 (예시에서는 jsonplaceholder.typicode.com 사용)
//                 const imageResponse = await axios.get('https://jsonplaceholder.typicode.com/photos/1');
//                 const imageUrl = imageResponse.data.url; // 이미지 URL 설정

//                 // 기사 제목을 가져오는 API 호출 (예시에서는 jsonplaceholder.typicode.com 사용)
//                 const articleResponse = await axios.get('https://jsonplaceholder.typicode.com/posts/1');
//                 const articleTitle = articleResponse.data.title; // 기사 제목 설정

//                 // 상태 업데이트
//                 setArticles([{ imageUrl, articleTitle }, { imageUrl, articleTitle }, { imageUrl, articleTitle }]);
//             } catch (error) {
//                 console.error('Error fetching data:', error);
//             }
//         };

//         fetchData();
//     }, []);

//     return (
//         <div className='Content_wrap'>
//             <div className='content1'/>

//             {/* content title */}
//             <ContentTitle title="연예 뉴스" moreLink="더 보기 >" />

//             {/* 뉴스 한칸 */}
//             <div className='con1_wrap'>
//                 {articles.map((article, index) => (
//                     <div key={index} className='con1_1'>
//                         {/* 이미지 */}
//                         <div className='con1_img'>
//                             <img src={article.imageUrl} alt="Celebrity" />
//                         </div>

//                         {/* 기사 제목 */}
//                         <div className='con1_1_txt'>
//                             <span className='_4_5'>{article.articleTitle}</span>
//                             {/* <Link to={`/news/${article.articleTitle}`}>{article.articleTitle}</Link> */}
//                         </div>
//                     </div>
//                 ))}
//             </div>
//         </div>
//     );
// }

// export default Contents1;