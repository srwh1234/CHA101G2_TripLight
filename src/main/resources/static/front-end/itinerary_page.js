$(document).ready(function () {

    // ===== 取得元素 =====
    // 兒童按鈕
    var minus_child = $("#minus_child");
    var plus_child = $("#plus_child");
    var count_child = $("#count_child");

    // 大人按鈕
    var minus_adult = $("#minus_adult");
    var plus_adult = $("#plus_adult");
    var count_adult = $("#count_adult");

    // 總金額
    var amount = $("#amount");
    var amountValue = $("#amount").attr("value");
    // 價格
    var price_child = $("#price_child").attr("value");
    var price_adult = $("#price_adult").attr("value");
    console.log(price_child);


    // ===== 兒童按鈕 =====

    // 減少數量, 金額
    minus_child.on("click", function () {
        var currentQty = count_child.val();

        if (currentQty > 1) {
            currentQty--;
            count_child.val(currentQty);
            // 金額增減

            amountValue = price_child * currentQty;
            amount.html(`${amountValue} 元`);
        }
    })

    // 增加數量, 金額
    plus_child.on("click", function () {
        // alert(price_child);
        var currentQty = count_child.val();
        currentQty++;
        count_child.val(currentQty);
        // 金額增減
        amountValue = price_child * currentQty;
        amount.html(`${amountValue} 元`);
    })

    // ===== 大人按鈕 =====
    // 減少數量, 金額
    minus_adult.on("click", function () {

        var currentQty = count_adult.val();

        if (currentQty > 1) {
            currentQty--;
            count_adult.val(currentQty);
            // 金額增減
            amountValue = price_adult * currentQty;
            amount.html(`${amountValue} 元`);
        }
    })

    // 增加數量, 金額
    plus_adult.on("click", function () {
        var currentQty = count_adult.val();
        currentQty++;
        count_adult.val(currentQty);
        // 金額增減
        amountValue = price_adult * currentQty;
        amount.html(`${amountValue} 元`);

    })


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





