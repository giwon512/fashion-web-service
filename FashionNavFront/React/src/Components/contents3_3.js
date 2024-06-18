import React, { useState, useEffect } from 'react';
import axios from 'axios';
import './contents3.css';
import ContentTitle from './ContentTitle';
import ImgAsset from '../public';

export default function Content3_3() {

    // const [imageURLs, setImageURLs] = useState([]);

    // useEffect(() => {
    //     // 백엔드에서 이미지 URL을 가져오는 API 호출
    //     axios.get('https://api.example.com/images') // 바꿔야됨
    //         .then(response => {
    //             setImageURLs(response.data); // 이미지 URL 목록을 state에 설정
    //         })
    //         .catch(error => {
    //             console.error('이미지를 가져오기 실패ㅜ:', error);
    //         });
    // }, []); // 빈 배열은 한 번만 실행

    return (
        <div className='contents3_'>

            <div className='contents3_3'>
                <ContentTitle title="CELEBRITY NEWS" moreLink="more >" />

                {/* <div className='con3_3'>
                    {imageURLs.slice(0, 4).map((imageUrl, index) => (
                        <div key={index} className='con3_3_1'>
                            <div className='con3_3_img'>
                                <img className='con3_3_img_1' src={imageUrl} alt={`Celebrity News ${index + 1}`} />
								{/* 
								호버넣을지고민
								<div className='hover'>
									<div className='Rectangle339'/>
									<div className='Rectangle340'/>
									<div className='Rectangle341'/>
								</div> */}
							{/* </div>
                        </div>
                    ))}
                </div> */} 



					{/* 레이아웃용 네모빔 나중에 이거 지우고 나머지 주석 풀어쓰기*/}
					<div className='con3_3'>
						
							<div className='con3_3_1'>
								<div className='con3_3_img'>
									<img className='con3_3_img_1' />
									{/* 
									호버넣을지고민
									<div className='hover'>
										<div className='Rectangle339'/>
										<div className='Rectangle340'/>
										<div className='Rectangle341'/>
									</div> */}
								</div>
							</div>
							<div className='con3_3_1'>
								<div className='con3_3_img'>
									<img className='con3_3_img_1' />
									{/* 
									호버넣을지고민
									<div className='hover'>
										<div className='Rectangle339'/>
										<div className='Rectangle340'/>
										<div className='Rectangle341'/>
									</div> */}
								</div>
							</div>
							<div className='con3_3_1'>
								<div className='con3_3_img'>
									<img className='con3_3_img_1' />
									{/* 
									호버넣을지고민
									<div className='hover'>
										<div className='Rectangle339'/>
										<div className='Rectangle340'/>
										<div className='Rectangle341'/>
									</div> */}
								</div>
							</div>
							<div className='con3_3_1'>
								<div className='con3_3_img'>
									<img className='con3_3_img_1' />
									{/* 
									호버넣을지고민
									<div className='hover'>
										<div className='Rectangle339'/>
										<div className='Rectangle340'/>
										<div className='Rectangle341'/>
									</div> */}
								</div>
							</div>
					</div>
                



            </div>
        </div>
    );
}
