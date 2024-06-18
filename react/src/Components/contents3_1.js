import React, { useState, useEffect } from 'react';
import axios from 'axios';
import './contents3.css';
import ContentTitle from './ContentTitle';
import ImgAsset from '../public';

export default function Content3_1() {
	const newsItems = [
        {
            id: 1,
            title: "탑텐, 세계적 뮤지션과 만남! 유니버설뮤직과 협업 ‘밋 더 뮤직’ 티셔츠 런칭",
            content: "세계적인 뮤지션 롤링스톤즈, 건즈 앤 로지스, 밥 말리 앨범 재킷 이미지 티셔츠에 유니버설뮤직과 협업 ‘밋 더 뮤직’ 티셔츠 런칭 유니버설뮤직과 협업 ‘밋 더 뮤직’ 티셔츠 런칭",
        },
        {
            id: 2,
            title: "두 번째 뉴스 제목두 번째 뉴스 제목두 번째 뉴스 제목두 번째 뉴스 제목두 번째 뉴스 제목",
            content: "두 번째 뉴스 내용두 번째 뉴스 제목두부김치 먹고싶당 번두 번째 뉴스 제목두 번째 뉴스 제목째 뉴스 제목두 번째 뉴스 제목두 번째 뉴스 제목",
        },
		{
            id: 1,
            title: "탑텐, 세계적 뮤지션과 만남! 유니버설뮤직과 협업 ‘밋 더 뮤직’ 티셔츠 런칭",
            content: "세계적인 뮤지션 롤링스톤즈, 건즈 앤 로지스, 밥 말리 앨범 재킷 이미지 티셔츠에 유니버설뮤직과 협업 ‘밋 더 뮤직’ 티셔츠 런칭 유니버설뮤직과 협업 ‘밋 더 뮤직’ 티셔츠 런칭",
        },
		{
            id: 1,
            title: "탑텐, 세계적 뮤지션과 만남! 유니버설뮤직과 협업 ‘밋 더 뮤직’ 티셔츠 런칭",
            content: "세계적인 뮤지션 롤링스톤즈, 건즈 앤 로지스, 밥 말리 앨범 재킷 이미지 티셔츠에 유니버설뮤직과 협업 ‘밋 더 뮤직’ 티셔츠 런칭 유니버설뮤직과 협업 ‘밋 더 뮤직’ 티셔츠 런칭",
        },
		{
            id: 1,
            title: "탑텐, 세계적 뮤지션과 만남! 유니버설뮤직과 협업 ‘밋 더 뮤직’ 티셔츠 런칭",
            content: "세계적인 뮤지션 롤링스톤즈, 건즈 앤 로지스, 밥 말리 앨범 재킷 이미지 티셔츠에 유니버설뮤직과 협업 ‘밋 더 뮤직’ 티셔츠 런칭 유니버설뮤직과 협업 ‘밋 더 뮤직’ 티셔츠 런칭",
        },
		{
            id: 1,
            title: "탑텐, 세계적 뮤지션과 만남! 유니버설뮤직과 협업 ‘밋 더 뮤직’ 티셔츠 런칭",
            content: "세계적인 뮤지션 롤링스톤즈, 건즈 앤 로지스, 밥 말리 앨범 재킷 이미지 티셔츠에 유니버설뮤직과 협업 ‘밋 더 뮤직’ 티셔츠 런칭 유니버설뮤직과 협업 ‘밋 더 뮤직’ 티셔츠 런칭",
        },
		{
            id: 1,
            title: "탑텐, 세계적 뮤지션과 만남! 유니버설뮤직과 협업 ‘밋 더 뮤직’ 티셔츠 런칭",
            content: "세계적인 뮤지션 롤링스톤즈, 건즈 앤 로지스, 밥 말리 앨범 재킷 이미지 티셔츠에 유니버설뮤직과 협업 ‘밋 더 뮤직’ 티셔츠 런칭 유니버설뮤직과 협업 ‘밋 더 뮤직’ 티셔츠 런칭",
        },
		{
            id: 1,
            title: "탑텐, 세계적 뮤지션과 만남! 유니버설뮤직과 협업 ‘밋 더 뮤직’ 티셔츠 런칭",
            content: "세계적인 뮤지션 롤링스톤즈, 건즈 앤 로지스, 밥 말리 앨범 재킷 이미지 티셔츠에 유니버설뮤직과 협업 ‘밋 더 뮤직’ 티셔츠 런칭 유니버설뮤직과 협업 ‘밋 더 뮤직’ 티셔츠 런칭",
        },
        // 나머지 뉴스 아이템들도 추가할 수 있음
    ];
    // const [newsItems, setNewsItems] = useState([]);

    // useEffect(() => {
    //     // Axios를 이용하여 서버에서 뉴스 아이템을 가져옵니다.
    //     axios.get('https://api.example.com/news')  // 실제 사용할 API 엔드포인트로 대체해야 합니다.
    //         .then(response => {
    //             setNewsItems(response.data);  // API 응답에서 뉴스 아이템 데이터를 가져와 state에 설정합니다.
    //         })
    //         .catch(error => {
    //             console.error('뉴스를 가져오는 중 에러 발생:', error);
    //         });
    // }, []); // 빈 배열은 useEffect가 한 번만 실행되도록 합니다.

    // // 뉴스 아이템들을 화면에 렌더링합니다.
    return (
        <div className='contents3_1'>
            <ContentTitle title="CELEBRITY NEWS" moreLink="more >" />
			
			<div className='con3_1'>
                {newsItems.slice(0, 8).map((item) => (
                    <div key={item.id} className='con3_1_item'>
                        <div className='con3_1_wrap'>
                            <div className='con3_1_img'></div>
                            <div className='con3_1_txt'>
                                <span className='con3_1_title'>{item.title}</span>
                                <span className='con3_1_txt1'>{item.content}</span>
                            </div>
                        </div>
                    </div>
                ))}
            </div>

        </div>
    );
}
