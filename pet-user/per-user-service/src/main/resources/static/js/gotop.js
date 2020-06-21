$(function () {
  var top = $('#top');
  var cart = $('#cart');
  // $(window).scroll(function () {
  //   if ($(window).scrollTop() > 100) {
  //     top.fadeIn(1500);
  //   } else {
  //     top.fadeOut(1500);
  //   }
  // });

  //当点击跳转链接后，回到页面顶部位置

  top.click(function () {
    $('body,html').animate({
      scrollTop: 0
    }, 1000);
    return false;
  });
});