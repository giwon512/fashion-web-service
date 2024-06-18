import React from 'react'
import './contents3.css'
import ContentTitle from './ContentTitle';

export default function Content3_4 () {
	return (
		<div className='contents3_'>

			<div className='contents3_4'>
				<ContentTitle title="COMMUNITY" moreLink="more >" />

				<div className='con3_4'>
					
					{/* 게시판 어떻게 넣징 */}
					<div className='con3_4_wrap'>
						<span className='con3_4_1_title'>오늘의 명언</span>
						<p className='con3_4_1_txt'>오늘의 명언</p>
						<p className='con3_4_1_date'>날짜나 아이콘</p>
					</div>
					{/* 자리만잡음ㅁ 아래주석 수정해서 쓰기 */}
				</div>
			</div>

		</div>
	)
}



// import React, { useState, useEffect } from 'react';
// import './contents3.css';
// import ContentTitle from './ContentTitle';
// import axios from 'axios';

// export default function Content3_4() {
//     const [boardData, setBoardData] = useState(null); // 게시판 데이터 상태 설정

//     useEffect(() => {
//         axios.get('https://api.example.com/board') // 실제 API 주소로 변경해야 함
//             .then(response => {
//                 setBoardData(response.data); // 받은 데이터를 상태에 설정
//             })
//             .catch(error => {
//                 console.error('게시판 데이터를 가져오는 중 에러 발생:', error);
//             });
//     }, []); // 빈 배열은 컴포넌트가 처음 마운트될 때 한 번만 호출

//     return (
//         <div className='contents3_'>
//             <div className='contents3_4'>
//                 <ContentTitle title="COMMUNITY" moreLink="more >" />
//                 <div className='con3_4'>
//                     {/* 게시판 UI */}
//                     {boardData && (
//                         <div className='con3_4_wrap'>
//                             <span className='con3_4_1_title'>{boardData.title}</span>
//                             <p className='con3_4_1_txt'>{boardData.content}</p>
//                             <p className='con3_4_1_date'>{boardData.date}</p>
//                         </div>
//                     )}
//                 </div>
//             </div>
//         </div>
//     );
// }

