import React from 'react'
import './contents3.css'
import ContentTitle from './ContentTitle';
import ImgAsset from '../public'

export default function Content3_4 () {
	return (
		<div className='con3_4'>
			<ContentTitle title="CELEBRITY NEWS" moreLink="more >" />

			{/* 게시판 어떻게 넣징 */}
			<div className='con3_4_1'>
				<span className='con3_4_1_title'>오늘의 명언</span>
				<p className='con3_4_1_txt'>오늘의 명언</p>
				<p className='con3_4_1_date'>날짜나 아이콘</p>
			</div>
			
		</div>
	)
}