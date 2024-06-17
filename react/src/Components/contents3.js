import React from 'react'
import './contents3.css'
import ContentTitle from './ContentTitle';
import ImgAsset from '../public'

export default function Contents3 () {
	return (
		<div className='contents3'>
			<div className='con3_1'>
				<ContentTitle title="CELEBRITY NEWS" moreLink="more >" />

				<div className=''>
					<div className='con3_1_img'></div>
					<div className='con3_1_txt'>
						<span className='con3_1_title'>탑텐, 세계적 뮤지션과 만남! 유니버설뮤직과 협업 ‘밋 더 뮤직’ 티셔츠 런칭</span>
						<span className='con3_1_txt1'>세계적인 뮤지션 롤링스톤즈, 건즈 앤 로지스, 밥 말리 앨범 재킷 이미지 티셔츠에...</span>
						<span className='con3_1_txt2'>더보기</span>
					</div>
				</div>
			</div>

			<div className='con3_2'>
				<ContentTitle title="CELEBRITY NEWS" moreLink="more >" />
				<div className='con3_2_img'>
					<img className='' src = {ImgAsset.contents3__20240530_4263611} />
				</div>
			</div>

			<div className='con3_3'>
				<ContentTitle title="CELEBRITY NEWS" moreLink="more >" />
				<div className='con3_3_1'>
					<div className='Rectangle333'>
						<img className='con3_3_1_img' src = {ImgAsset.contents3_images1} />
					</div>
					{/* 마우스올릴때 나타날효과 넣을지말지고민 */}
					<div className='hover'>
						<div className='Rectangle339'/>
						<div className='Rectangle340'/>
						<div className='Rectangle341'/>
					</div>
				</div>
			</div>

			<div className='con3_4'>
				<ContentTitle title="CELEBRITY NEWS" moreLink="more >" />

				{/* 게시판 어떻게 넣징 */}
				<div className='con3_4_1'>
					<span className='con3_4_1_title'>오늘의 명언</span>
					<p className='con3_4_1_txt'>오늘의 명언</p>
					<p className='con3_4_1_date'>날짜나 아이콘</p>
				</div>
			</div>
			
		</div>
	)
}