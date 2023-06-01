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
// ==========================右邊會員資料--票券訂單 =====================================

//填入假資料
const dataObj = {};
const valid = [
  {
    url: "http://google.com.tw",
    imgUrl: "https://scitechvista.nat.gov.tw/FileDownload/Article/20230330152155448044257.jpg",
    title: "票券收藏1",
    summary: "簡介簡介簡介簡介簡介簡介簡介簡介簡介簡介簡介簡介簡介簡介介簡介簡介簡介介簡介簡介簡介簡介簡介簡介簡介v簡介簡介簡介簡介簡介簡介簡介簡介簡介簡介簡介簡介簡介簡介簡介簡介簡介簡介簡介簡介簡介簡介簡介簡介簡介簡介簡介簡介簡介簡介簡介簡介簡介簡介簡介簡介簡介簡介簡介簡介簡介簡介簡介簡介簡介簡介簡介簡介簡介簡介簡介簡介簡介簡介",
    realPrice: 400,
  },
  {
    url: "https://www.kkday.com/zh-tw/product/115643-limited-time-offer-2-4-meals-hsinchu-camping-xiong-glamping-taiwan",
    imgUrl: "https://scitechvista.nat.gov.tw/FileDownload/Article/20230330152155448044257.jpg",
    title: "票券收藏2",
    summary: "簡介",
    realPrice: 1200,
  }
];

for (let i = 0; i < valid.length; i++) {
  Object.assign(dataObj, valid[i]);
  $(".orderselectclass").append(generateTicket(valid[i]));
}
function generateTicket(t_favorite) {
  return `
  <div class="ticket_item_class">
    <a href="${t_favorite.url}", class="orderurl">
        <div class="item_img_class">
            <img src="${t_favorite.imgUrl}" , class="item_img">
        </div>      
        <div class="item_content">
            <h1 class="item_title">${t_favorite.title}</h1>
            <div class="box">
                <p class="Number"> ${t_favorite.summary}  </p>
            </div>
            <div>
                <p class="price">TWD</p>
                <p class="realPrice">${t_favorite.realPrice}</p>
            </div>
        </div>
    </a>
    <div class="item_commend_class">
      <button type="button" class="remove_btn" aria-label="Close"
          style=" border: 1px solid rgb(180, 174, 174)">
          <span aria-hidden="true">&times;移除收藏</span>
      </button>
    </div>
  </div>
      `;
}
//移除背景圖                     
if (Object.keys(dataObj).length !== 0) {
  $(".no_comment_div").first().toggleClass("-out")
}
//移除收藏
$(document).on("click", ".remove_btn", function (e) {
  $(this).closest(".ticket_item_class").remove();
  var ticketItems = document.querySelectorAll('#orderselectdiv .ticket_item_class');
  if (ticketItems.length !== 0) {
    $(".no_comment_div").first().toggleClass("-out", true);
  } else {
    $(".no_comment_div").first().toggleClass("-out", false);
  }
});
// ==========================右邊會員資料--旅遊團收藏 =====================================
// 處理第一個分頁內容跑到別的分頁
$(".nav-item").eq(1).on("click", function () {
  $("#group_order .ticket_item_class").remove();
});
const g_dataObj = {};
const g_valid = [{
  url: "https://www.kkday.com/zh-tw/product/115643-limited-time-offer-2-4-meals-hsinchu-camping-xiong-glamping-taiwan",
  imgUrl: "https://image.kkday.com/v2/image/get/h_650%2Cc_fit/s1.kkday.com/product_115643/20230515091041_Tx0bo/jpg",
  title: "旅遊團test",
  summary: "testtesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttest",
  realPrice: 5678,
}];
//填入假資料
for (let i = 0; i < g_valid.length; i++) {
  Object.assign(g_dataObj, g_valid[i]);
  $("#group_orderselectdiv").append(generateGroup(g_valid[i]));
}
function generateGroup(g_favorite) {
  return `
  <div class="group_item_class">
   <a href="${g_favorite.url}" , class="orderurl">
      <div class="item_img_class">
          <img src="${g_favorite.imgUrl}" class="item_img">
      </div>
        <div class="item_content">
          <h1 class="item_title">${g_favorite.title}</h1>
          <div class="box">
              <p class="Number">${g_favorite.summary}</p>
          </div>
          <div>
              <p class="price">TWD</p>
              <p class="realPrice">${g_favorite.realPrice}</p>
          </div>
      </div>
    </a>
    <div class="item_commend_class">
      <button type="button" class="remove_btn" aria-label="Close"
        style=" border: 1px solid rgb(180, 174, 174)">
        <span aria-hidden="true">&times;移除收藏</span>
      </button>
    </div>
  </div>
          `;
}

//移除背景圖                     
if (Object.keys(g_dataObj).length !== 0) {
  $(".no_comment_div").eq(1).toggleClass("-out")
}
//移除收藏
$(document).on("click", ".remove_btn", function (e) {
  $(this).closest(".group_item_class").remove();
  var groupItems = document.querySelectorAll(".group_item_class");
  if (groupItems.length !== 0) {
    $(".no_comment_div").eq(1).toggleClass("-out", true);
  } else {
    $(".no_comment_div").eq(1).toggleClass("-out", false);
  }
});
// ==========================右邊會員資料--文章收藏 =====================================
// 處理第一個分頁內容跑到別的分頁
$(".nav-item").eq(2).on("click", function () {
  $("#article_order .ticket_item_class").remove();
});

const a_dataObj = {};
const a_valid = [{
  url: "http://google.com.tw",
  imgUrl: "https://image.kkday.com/v2/image/get/h_650%2Cc_fit/s1.kkday.com/product_115643/20230515091041_Tx0bo/jpg",
  title: "文章文章",
  summary: "3333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333",
  realPrice: "2020 / 02 / 02",
}];
// 填入假資料
for (let i = 0; i < a_valid.length; i++) {
  Object.assign(a_dataObj, a_valid[i]);
  $("#article_orderselectdiv").append(generateArticle(a_valid[i]));
}
function generateArticle(a_favorite) {
  return `
  <div class="article_item_class">
    <a href="${a_favorite.url}" , class="orderurl">
      <div class="item_img_class">
          <img src="${a_favorite.imgUrl}"class="item_img">
      </div>
      <div class="item_content">
        <h1 class="item_title">${a_favorite.title}</h1>
        <div class="box">
            <p class="Number">${a_favorite.summary} </p>
        </div>
        <div>
            <p class="realPrice">${a_favorite.realPrice}</p>
        </div>
      </div>
    </a>
      <div class="item_commend_class">
        <button type="button" class="remove_btn" aria-label="Close"
            style=" border: 1px solid rgb(180, 174, 174)">
            <span aria-hidden="true">&times;移除收藏</span>
        </button>
    </div>
  </div>
   `;
}
//移除背景圖     

if (Object.keys(a_dataObj).length !== 0) {
  $(".no_comment_div").eq(2).toggleClass("-out")
}
// //移除收藏
$(document).on("click", ".remove_btn", function (e) {
  $(this).closest(".article_item_class").remove();

  var articleItems = document.querySelectorAll(".article_item_class");
  if (articleItems.length !== 0) {
    $(".no_comment_div").eq(2).toggleClass("-out", true);
  } else {
    $(".no_comment_div").eq(2).toggleClass("-out", false);
  }
});

// =================================================================
// 處理第一個分頁內容跑到別的分頁
$(".nav-item").eq(3).on("click", function () {
  $("#AI_order .ticket_item_class").remove();
});
// 設定點擊AI行程規劃
$(".nav-item")
  .eq(3) // 選第四個
  .on("click", function () {
    $(".tab-pane").eq(3).addClass("show active"); // 顯示
    // 呼叫這個顯示行程卡片列表
    getAiFavorite();
  });

// 接收後台AI行程資料
// console.log(memberId);
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
//背景圖
var GOItems = document.querySelectorAll(".group_order_item_class");
if (GOItems.length !== 0) {
  $(".no_comment_div").eq(3).toggleClass("-out", true);
} else {
  $(".no_comment_div").eq(3).toggleClass("-out", false);
}
// =====================================================================
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

