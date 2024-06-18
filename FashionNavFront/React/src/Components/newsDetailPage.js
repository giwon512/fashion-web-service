import React from 'react'
import './news_detail_page.css'
import ImgAsset from '../public'
import Header from './header'
import RightImg from './RightImg';
export default function NewsDetailPage () {
	return (
		<div className='news_detail_page'>

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

			<div className='news_detail_page_wrap'>
				<span className='news_detail_page_title'>'디커' 모델 변우석, 훈훈한 남친짤 대방출<br/></span>
				<span className='news_detail_page_date'>2024-06-07 김우현 기자 whk@fi.co.kr</span>
				<span className='news_detail_page_subTitle'>아웃도어 '디스커버리'와 배우 변우석의 시너지 효과 빵빵</span>
				<div>
					<img className='news_detail_page_mainImg' src = {ImgAsset.news_detail_page__101} />
					<span className='news_detail_page_mainImg_title'>디스커버리 익스페디션의 변우석 ‘프레시벤트 컬렉션’ 광고 스케치 필름 컷</span>
				</div>
				

				<span className='news_detail_page_txt'>에프앤에프(대표 김창수)에서 전개하는 프리미엄 라이프스타일 아웃도어 브랜드 ‘디스커버리 익스페디션’이 브랜드 앰버서더이자 대세 배우 변우석과 마케팅 커뮤니케이션을 전개하며 긍정적인 효과를 내고 있다.<br/><br/>디스커버리 익스페디션은 최근 드라마 &lt;선재 업고 튀어&gt;를 통해 최고의 인기를 구가하고 있는 변우석과 ’24 썸머 프레시벤트 컬렉션’ 화보를 온라인에 공개하고 화보 콘텐츠의 좋아요 수 1만 건을 넘겼다. 이는 같은 브랜드에서 일반 패션모델과 협업한 화보 콘텐츠의 평균 좋아요 수를 10배 이상을 초과하는 수치다.<br/><br/>최근에는 디스커버리 공식 유튜브와 인스타그램을 통해 공개된 화보 촬영 시 제작한 스케치 필름도 공개하며 변우석에 대한 폭발적인 반응을 브랜드에 대한 호감도로 이어가고 있다. 해당 영상은 인스타그램 공개 이틀 만에 조회수 17만 뷰를 넘으며 디스커버리와 변우석의 만남에 대한 화제성을 다시 한번 입증하고 있다.<br/><br/>특히 공개된 필름에는 뛰어난 접촉냉감 기능성을 갖춘 ‘프레시벤트 컬렉션’ 제품을 착용한 변우석이 아웃도어 활동을 청량하게 즐기는 모습이 담겨 있어 드라마 종영 이후 변우석의 새로운 모습을 기대하던 팬들의 기대감을 충족시킨다는 평이다.</span>
			</div>
			
			<RightImg></RightImg>
			
		</div>
	)
}