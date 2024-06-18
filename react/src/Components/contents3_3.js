import React from 'react'
import './contents3.css'
import ContentTitle from './ContentTitle';
import ImgAsset from '../public'

export default function Content3_3 () {
	return (
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
	)
}