$(document).ready(function () {




// 抬頭




// 燈箱按鈕
// 頁面上的按鈕
    $("#btn_modal").on("click", function () {
        alert('ttt');
        $("#lightbox").removeClass("none");
    });

// modal 中的半透明黑色區域
    $("#lightbox").on("click", function () {
        $("#lightbox").addClass("none");
    });

// 點擊 lightbox 中的白色區域，不會關掉 modal
    $("#lightbox > article").on("click", function (e) {
        e.stopPropagation();
    });


});





