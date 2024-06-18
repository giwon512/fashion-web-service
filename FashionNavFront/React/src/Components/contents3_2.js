import React from 'react'
import './contents3.css'
import ContentTitle from './ContentTitle';
import ImgAsset from '../public'

export default function Content3_2 () {
	return (
		<div className='contents3_'>

			<div className='con3_2'>
				<ContentTitle title="CELEBRITY NEWS" moreLink="more >" />

				<div className='con3_2_img'>
					<img className='' src = {ImgAsset.contents3__20240530_4263611} />
				</div>
				
			</div>
			
		</div>
	)
}