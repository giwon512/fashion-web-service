import React from 'react'
import {Link} from 'react-router-dom'

export default function HomePage () {
    return (
	<div>
		<Link to='/header'><div>header</div></Link>
		<Link to='/slide'><div>slide</div></Link>
		<Link to='/contents1'><div>contents1</div></Link>
		<Link to='/contents2'><div>contents2</div></Link>
		<Link to='/contents3'><div>contents3</div></Link>
		<Link to='/footer'><div>footer</div></Link>
		<Link to='/news_detail_page'><div>news_detail_page</div></Link>
		<Link to='/newsPage'><div>news_page</div></Link>
	</div>
	)
}