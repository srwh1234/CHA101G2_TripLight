let camera = document.getElementById('camera');
let img = document.querySelector('.card-body img');

// <!-- 左邊會員照片+導覽列 -->
// ============================上傳大頭照=====================================
camera.addEventListener('click', function () {
  const input = document.createElement('input');
  input.type = 'file';

  input.addEventListener("change", function () {
    const file = this.files[0];
    const reader = new FileReader();

    reader.addEventListener("load", function () {
      sessionStorage.setItem('uploadedPhoto', reader.result);
      img.src = reader.result;
    });
    if (file) {
      reader.readAsDataURL(file);
    }
  });
  input.click();
});
// <!-- 右邊會員資料--票券收藏 -->
//已完成訂單的假資料
const valid = {
  url: "https://www.kkday.com/zh-tw/product/142053-thredbo-resort-ski-snowboard-day-tour-from-haymarket-parramatta-australia",
  imgUrl: "https://scitechvista.nat.gov.tw/FileDownload/Article/20230330152155448044257.jpg",
  title: "票券收藏1",
  summary: "簡介簡介簡介簡介簡介簡介簡介簡介簡介",
  realPrice: 400,
};
//未完成訂單的假資料
const valid2 = {
  url: "https://www.kkday.com/zh-tw/product/115643-limited-time-offer-2-4-meals-hsinchu-camping-xiong-glamping-taiwan",
  imgUrl: "https://scitechvista.nat.gov.tw/FileDownload/Article/20230330152155448044257.jpg",
  title: "票券收藏2",
  summary: "簡介簡介簡介簡介簡介簡介簡介簡介簡介簡介簡介簡介簡介簡介簡介簡介簡介簡介簡介簡介簡介簡介簡介簡介簡介簡介簡介簡介簡介簡介簡介簡介簡介簡介簡介簡介簡介簡介簡介簡介簡介簡介簡介簡介簡介簡介簡介簡介簡介簡介簡介簡介簡介簡介簡介簡介簡介簡介簡介簡介簡介簡介簡介簡介簡介簡介簡介簡介簡介簡介簡介簡介簡介簡介簡介簡介簡介簡介簡介簡介簡介簡介簡介簡介簡介簡介簡介簡介簡介簡介",
  realPrice: 1200,
};
//簡介超過超過100個字以"..."取代
$(function () {
  var len = 100;
  $(".Number").each(function (i) {
    if ($(this).text().length > len) {
      $(this).attr("title", $(this).text());
      var text = $(this).text().substring(0, len - 1) + "...";
      $(this).text(text);
    }
  });
});

//存進sessionStorage
sessionStorage.setItem('ticket_favorite', JSON.stringify(valid2));
sessionStorage.setItem('ticket_favorite', JSON.stringify(valid));
//一載入頁面就出現"
const orderIncomplete = JSON.parse(sessionStorage.getItem('ticket_favorite'));
// $("#orderselectdiv").append(generateTicketItem(orderIncomplete));

$(".nav-item")
  .eq(0)
  .on("click", function () {
    $(".tab-pane").eq(0).addClass("show active"); // 顯示
    // 呼叫這個顯示行程卡片列表
    getAiFavorite();
  });

function getAiFavorite() {
  $.ajax({
    url: "/getAiFavorite/" + memberId,
    method: "GET",
    dataType: "json",
    success: function (aiFavorite) {
      console.log("接收資料");
      console.log(aiFavorite);

      for (let i = 0; i < aiFavorite.length; i++) {
        $(".tab-pane").eq(0).find("#orderselect")
          .after(function generateTicketItem(order) {
            return `
                <div class="ticket_item_class">
                  <a href=${order.url} class="orderurl">
                    <div class="item_img_class">
                      <img src=${order.imgUrl} class="item_img">
                    </div>
                    <div class="item_commend_class">
                    <button type="button" class="remove_btn" aria-label="Close"  style=" border: 1px solid rgb(180, 174, 174)">
                      <span aria-hidden="true">&times;移除收藏</span>
                    </button>
                    </div>
                    <div class="item_content">
                      <h1 class="item_title">${order.title}</h1>
                      <div class="box">
                         <p class="Number">${order.summary}</p>
                      </div>
                      <div>
                        <p class="price">TWD</p>
                        <p class="realPrice">${order.realPrice}</p>
                      </div>
                    </div>
                  </a>
                </div>
              `;
          })
      }
    },

  });
}


// <!-- 右邊會員資料--旅遊團訂單 -->
//未完成訂單的假資料
const valid4 = {
  url: "https://www.kkday.com/zh-tw/product/115643-limited-time-offer-2-4-meals-hsinchu-camping-xiong-glamping-taiwan",
  imgUrl: "https://image.kkday.com/v2/image/get/h_650%2Cc_fit/s1.kkday.com/product_115643/20230515091041_Tx0bo/jpg",
  title: "未完成旅遊團test",
  Number: "#sdfh",
  realPrice: 5678,
};
//存進sessionStorage
// sessionStorage.setItem('test_group_done', JSON.stringify(valid3));
sessionStorage.setItem('test_group_incomplete', JSON.stringify(valid4));

//一載入頁面在"未完成訂單上"
const test_group_incomplete = JSON.parse(sessionStorage.getItem('test_group_incomplete'));
$("#group_orderselectdiv").append(generateGroupOrderItem(test_group_incomplete));


function generateGroupOrderItem(groupOrder) {
  return `
      <div class="ticket_item_class">
        <a href=${groupOrder.url} class="orderurl">
          <div class="item_img_class">
            <img src=${groupOrder.imgUrl} class="item_img">
          </div>
          <div class="item_commend_class">
          <button type="button" class="remove_btn" aria-label="Close"  style=" border: 1px solid rgb(180, 174, 174)">
          <span aria-hidden="true">&times;移除收藏</span>
        </button>
          </div>
          <div class="item_content">
            <h1 class="item_title">${groupOrder.title}</h1>
            <div>
              <p class="serialNumber">訂單編號：</p>
              <p class="Number">${groupOrder.Number}</p>
            </div>
            <div>
              <p class="price">TWD</p>
              <p class="realPrice">${groupOrder.realPrice}</p>
            </div>
          </div>
        </a>
      </div>
      `;
}
//移除資料
const remove_btn = document.querySelector(".remove_btn");

remove_btn.addEventListener("click", function () {
  document.querySelector(".orderurl").removeAttribute("href");
  document.querySelector(".ticket_item_class").remove();
  // document.querySelector(".ticket_item_class").remove();
});

// =================================================================
// 設定點擊AI行程規劃
$(".nav-item")
  .eq(3) // 選第四個
  .on("click", function () {
    $(".tab-pane").eq(3).addClass("show active"); // 顯示
    // 呼叫這個顯示行程卡片列表
    getAiFavorite();
  });

// 接收後台AI行程資料
console.log(memberId);
function getAiFavorite() {
  $.ajax({
    url: "/getAiFavorite/" + memberId,
    method: "GET",
    dataType: "json",
    success: function (aiFavorite) {
      console.log("接收資料");
      console.log(aiFavorite);

      for (let i = 0; i < aiFavorite.length; i++) {
        $(".tab-pane").eq(3).find("#group_orderselect")
          .after(`<div class="group_order_item_class">
          <div class="card-header">
            <div class="card-top">
              <h5 class="text-center">
                  <h5>${aiFavorite[i].destination}${aiFavorite[i].travelDays}日遊</h5>
              </h5>
            </div>
          </div>
          <div class="card-body card-down">
            <p>
              <i class="fa-solid fa-person"></i>
              人數：${aiFavorite[i].people}
            </p>
            <p>
              <i class="fa-solid fa-dollar-sign"></i>
              預算範圍：${aiFavorite[i].budgetRange}
            </p>
            <p>
              <i class="fa-brands fa-fly"></i>
              旅遊風格：${aiFavorite[i].preferredStyle}
           </p>
           <p>
            <i class="fa-solid fa-location-dot"></i> 路線連結：<a
             href="${aiFavorite[i].route}"
             >${aiFavorite[i].destination}${aiFavorite[i].travelDays}日遊路線連結</a
            >
          </p>
            <p class="ai_description"><i class="fa-solid fa-file-lines"></i> 行程內容：<br>${aiFavorite[i].planningDescription}</p>
          </div>
        </div>`);
      }
    },
    error: function (error) {
      console.log(error);
    },
  });
}


// 設定卡片展開與關閉
$(document).on("click", ".group_order_item_class", function () {
  var container = $(this);
  var content = container.find(".card-body");

  if (container.hasClass("expanded")) {
    // 容器已展開，收起内容並恢復原始高度
    container.removeClass("expanded");
    content.addClass("card-down");

    // 計算原始高度
    var originalHeight = container.data("originalHeight");

    // 恢復原始高度
    container.animate({ height: originalHeight }, 500);
  } else {
    // 容器未展開，展開内容並增加高度
    container.addClass("expanded");
    content.removeClass("card-down");

    // 計算完整高度
    var fullHeight = content.outerHeight() + 100;

    // 保存原始高度
    container.data("originalHeight", container.height());

    // 增加高度以適應內容
    container.animate({ height: fullHeight }, 500);
  }
});

