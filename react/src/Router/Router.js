import React from 'react';
import { BrowserRouter as Router, Route, Switch } from 'react-router-dom';
import HomePage from './Components/index';
import Header from './Components/header';
import Slide from './Components/slide';
import Contents1 from './Components/contents1';
import Contents2 from './Components/contents2';
import Contents3 from './Components/contents3';
import Footer from './Components/footer';
import News_detail_page from './Components/news_detail_page';
import NewsPage from './Components/newsPage';

const RouterDOM = () => {
	return (
		<Router>
			<Switch>
				<Route exact path="/"><HomePage /></Route>
				<Route exact path="/header"><Header /></Route>
				<Route exact path="/slide"><Slide /></Route>
				<Route exact path="/contents1"><Contents1 /></Route>
				<Route exact path="/contents2"><Contents2 /></Route>
				<Route exact path="/contents3"><Contents3 /></Route>
				<Route exact path="/footer"><Footer /></Route>
				<Route exact path="/news_detail_page"><News_detail_page /></Route>
				<Route exact path="/newsPage"><NewsPage /></Route>
			</Switch>
		</Router>
	);
}
export default RouterDOM;