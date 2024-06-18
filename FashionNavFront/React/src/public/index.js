
        const tryRequire = (path) => {
        	try {
        	const image = require(`${path}`);
        	return image
        	} catch (err) {
        	return false
        	}
        };

        export default {
        
	questionMark: require('./questionMark.png'),
	contents3_image6: tryRequire('./contents3_image6.png') || require('./questionMark.png'),
	news_page_image4: tryRequire('./news_page_image4.png') || require('./questionMark.png'),
	slide_Vector1: tryRequire('./slide_Vector1.png') || require('./questionMark.png'),
	news_page_image5: tryRequire('./news_page_image5.png') || require('./questionMark.png'),
	slide_Vector2: tryRequire('./slide_Vector2.png') || require('./questionMark.png'),
	news_detail_page__101: tryRequire('./news_detail_page__101.png') || require('./questionMark.png'),
	header_Vector: tryRequire('./header_Vector.png') || require('./questionMark.png'),
	header_Vector_1: tryRequire('./header_Vector_1.png') || require('./questionMark.png'),
	contents3__20240530_4263611: tryRequire('./contents3__20240530_4263611.png') || require('./questionMark.png'),
	contents3_images1: tryRequire('./contents3_images1.png') || require('./questionMark.png'),
}