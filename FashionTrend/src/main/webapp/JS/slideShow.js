window.onload = () => {
    const wrapper = document.querySelector('.wrapper');
    const slideShow = document.querySelector('.slideShow');
    const showBox = document.querySelectorAll('.showBox');
    const arrow = document.querySelector('.arrow');

    let showBoxWidth = showBox[0].clientWidth;
    let sliderWidth = showBoxWidth * showBox.length;
    slideShow.style.width = `${sliderWidth}px`;

    let currentIdx = 0;
    let translate = 0;

    const moveRight = () => {
        //현재가 마지막 슬라이드가 아니라면
        if (currentIdx !== showBox.length - 1) {
            translate -= showBoxWidth;
            slideShow.style.transform = `translateX(${translate}px)`;
            currentIdx += 1;
        }
        //현재가 마지막 슬라이드인 경우 첫 슬라이드로 이동
        else {
            currentIdx = 0;
            translate = 0;
            slideShow.style.transform = `translateX(${translate}px)`;
        }
    }

    const moveLeft = () => {
        //현재가 첫 슬라이드가 아니라면
        if (currentIdx !== 0){
            translate += showBoxWidth;
            slideShow.style.transform = `translateX(${translate}px)`;
            currentIdx -= 1;
        }
        //현재가 첫 슬라이드인 경우 마지막 슬라이드로 이동
        else {
            currentIdx = showBox.length - 1;
            translate = currentIdx * showBoxWidth * -1;
            slideShow.style.transform = `translateX(${translate}px)`;
        }
    }

    arrow.addEventListener('click', ev => {
        ev.preventDefault();
        if(ev.target.className === 'next') {
            moveRight();
        } else if(ev.target.className === 'prev') {
            moveLeft();
        }
    }, false);

    setInterval(() => {
        moveRight();
    }, 4000);

    window.addEventListener('resize', () => {
        // 창 크기 변경이 일어나면 슬라이드 쇼 크기 조정
        const width = window.innerWidth;

        showBoxWidth = width;
        sliderWidth = showBoxWidth * showBox.length;
        slideShow.style.width = `${sliderWidth}px`;
        currentIdx = 0;
        translate = 0;
        slideShow.style.transform = `translateX(${translate}px)`;
    }, false);
};