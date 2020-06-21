$(function () {

  var shadow = $("#shadow")
  var wrap = $("#wrap");
  var smallImgBox = $('#wrap .smallImg');
  var smallImg = $('#wrap .smallImg img');
  var bigImgBox = $('#wrap .bigImg');
  var bigImg = $('#wrap .bigImg img');

  smallImgBox.mouseover(function () {
    shadow.css('display', 'block');
    bigImgBox.css('display', 'block');
  });
  smallImgBox.mouseout(function () {
    shadow.css('display', 'none');
    bigImgBox.css('display', 'none');
  });

  smallImgBox.mousemove(function (ev) {

    var ev = ev || window.event;
    var x = ev.pageX - $('#div_box').offset().left - smallImgBox.position().left - wrap.position().left;
    var y = ev.pageY - $('#div_box').offset().top - smallImgBox.position().top - wrap.position().top;

    var l = x - shadow.width() / 2;
    var t = y - shadow.height() / 2;
    if (l <= 0) {
      l = 0;
    } else if (l >= smallImgBox.width() - shadow.width()) {
      l = smallImgBox.width() - shadow.width();
    }
    if (t <= 0) {
      t = 0;
    } else if (t >= smallImgBox.height() - shadow.height()) {
      t = smallImgBox.height() - shadow.height();
    }
    shadow.css('left', l + "px");
    shadow.css('top', t + "px");

    var radioX = x / (smallImgBox.width() - shadow.width()) >= 1 ? 1 : x / (smallImgBox.width() - shadow.width());
    var radioY = y / (smallImgBox.height() - shadow.height()) >= 1 ? 1 : y / (smallImgBox.height() - shadow.height());

    bigImg.css('left', -radioX * (bigImg.width() - bigImgBox.width()) + "px");
    bigImg.css('top', -radioY * (bigImg.height() - bigImgBox.height()) + "px");

  });
});