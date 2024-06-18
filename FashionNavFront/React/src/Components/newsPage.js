import React from 'react';
import { useNavigate } from 'react-router-dom';
import './news_page.css';
import Header from './header';
import ImgAsset from '../public'
import RightImg from './RightImg';

export default function NewsPage () {
	const navigate = useNavigate(); // useNavigate 훅 사용

    const handleNewsClick = () => {
        navigate('/newsPage/newsDetailPage'); // 상세 페이지로 이동
    };

	return (
		<div className='newsPage'>
			<Header></Header>

			{/* 메뉴 경로? */}
			<div className='Group29'> {/* width 100% */}
				<div className='submenu_wrap'> {/* width 1150px */}
					<span className='BESTNEWS'>BEST NEWS</span> {/* 현재위치 */}
					<div>
						<span className='HOMENEWSBSET'>HOME    /   NEWS   /   BSET</span> {/* 현재위치경로 */}
					</div>
				</div>
			</div>
				
			<div className='newsList' onClick={handleNewsClick}>
				<div className='newsList_img'>
					<img className='_101' src = {ImgAsset.news_detail_page__101} />
				</div>
				<div>
					<span className='_'>'디커' 모델 변우석, 훈훈한 남친짤 대방출</span>
					<span className='__7'>에프앤에프(대표 김창수)에서 전개하는 프리미엄 라이프스타일 아웃도어 브랜드 ‘디스커버리 익스페디션’이 브랜드 앰버서더이자 대세 배우 변우석과 마케팅 커뮤니케이션을 전개하며 긍정적인 효과를 내고 있다.</span>
					<span className='newsList_date'>2024.06.14</span>
				</div>
			</div>
			{/*   */}
			
			{/* 오른쪽광고? 이미지 영역 */}
			<div className='Group30'>
				<div className='right'/>
			</div>

			<RightImg></RightImg>
		</div>
	)
}