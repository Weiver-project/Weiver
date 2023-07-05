document.addEventListener('DOMContentLoaded', function() {
    var swiperOptions = {
      slidesPerView: 'auto',
      spaceBetween: 5,
      navigation: {
        nextEl: '.swiper-button-next',
        prevEl: '.swiper-button-prev',
      },
      scrollbar: {
        el: '.swiper-scrollbar',
        hide: true,
      },
      observer: true,
      observeParents: true
    };
  
    var swiperCasting = new Swiper('.casting .swiper-container', swiperOptions);
    var swiperCasting = new Swiper('.youtube-api .swiper-container', swiperOptions);
  
    swiperCasting.init();  // Swiper 인스턴스 초기화
  });
  