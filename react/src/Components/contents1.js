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
			<div>
				<div className='con1_1'>

					{/* 이미지 */}
					<div className='con1_img'>
						{imageUrl && <img src={imageUrl} alt="Celebrity" />}
					</div>

					{/* 기사 제목 */}
					<div className='con1_1_txt'>
						<span className='_4_5'>{articleTitle}</span>
					</div>
					
				</div>
			</div>
		</div>
        
    );
}
