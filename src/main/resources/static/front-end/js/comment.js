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
// ==========================右邊會員資料--票券評價 =====================================
//填入假資料
const dataObj = {};
const valid = [
  {
    url: "http://google.com.tw",
    imgUrl: "https://blog.asiayo.com/wp-content/uploads/67817447_xl-1.jpg",
    title: "評價1",
    summary: "簡介簡介簡介簡介簡介簡介簡介簡介簡介簡介簡介簡介簡介簡介介簡介簡介簡介介簡介簡介簡介簡介簡介簡介簡介v簡介簡介簡介簡介簡介簡介簡介簡介簡介簡介簡介簡介簡介簡介簡介簡介簡介簡介簡介簡介簡介簡介簡介簡介簡介簡介簡介簡介簡介簡介簡介簡介簡介簡介簡介簡介簡介簡介簡介簡介簡介簡介簡介簡介簡介簡介簡介簡介簡介簡介簡介簡介簡介簡介",
    realPrice: "2020/02/02",
  },
  {
    url: "http://google.com.tw",
    imgUrl: "https://blog.asiayo.com/wp-content/uploads/67817447_xl-1.jpg",
    title: "評價22",
    summary: "222222222222222222222222222222222222222222222222222222222222222222222222222222簡介簡介簡介簡介簡介",
    realPrice: "2020/02/02",
  }
];
for (let i = 0; i < valid.length; i++) {
  Object.assign(dataObj, valid[i]);
  $(".orderselectclass").append(ticketComment(valid[i]));
}
//移除背景圖 
if (Object.keys(dataObj).length !== 0) {
  $(".no_comment_div").first().toggleClass("-out")
}

function ticketComment(t_comment) {
  return `
  <div class="ticket_item_class">
    <a href="${t_comment.url}" class="orderurl">
      <div class="item_img_class">
          <img src="${t_comment.imgUrl}" class="item_img">
      </div>
        <div class="item_content">
          <h1 class="item_title">${t_comment.title}</h1>
        <div class="box">
          <p class="Number">${t_comment.summary}  </p>
        </div>
        <div class="date_div">
          <p class="date">${t_comment.realPrice}</p>
        </div>
      </div>
      <div class="item_commend_class">
        <i class="fa-solid fa-pen-to-square">我要評論</i>
      </div>
    </a>
  </div>
      `;
}

// <!-- 右邊會員資料--旅遊團訂單 -->
//填入假資料
const g_dataObj = {};
const g_valid = [
  {
    url: "http://google.com.tw",
    imgUrl: "https://blog.asiayo.com/wp-content/uploads/67817447_xl-1.jpg",
    title: "group",
    summary: "簡介簡介簡介簡介簡介簡介簡介簡介簡介簡介簡介簡介簡介簡介介簡介簡介簡介介簡介簡介簡介簡介簡介簡介簡介v簡介簡介簡介簡介簡介簡介簡介簡介簡介簡介簡介簡介簡介簡介簡介簡介簡介簡介簡介簡介簡介簡介簡介簡介簡介簡介簡介簡介簡介簡介簡介簡介簡介簡介簡介簡介簡介簡介簡介簡介簡介簡介簡介簡介簡介簡介簡介簡介簡介簡介簡介簡介簡介簡介",
    realPrice: "2020/02/02",
  },
  {
    url: "http://google.com.tw",
    imgUrl: "https://blog.asiayo.com/wp-content/uploads/67817447_xl-1.jpg",
    title: "grouprurer",
    summary: "222222222222222222222222222222222222222222222222222222222222222222222222222222簡介簡介簡介簡介簡介",
    realPrice: "2020/02/02",
  }
];
for (let i = 0; i < g_valid.length; i++) {
  Object.assign(g_dataObj, g_valid[i]);
  $(".orderselectclass").eq(1).append(groupComment(g_valid[i]));
}
//移除背景圖 
if (Object.keys(g_dataObj).length !== 0) {
  $(".no_comment_div").eq(1).toggleClass("-out")
}
// 處理第一個分頁內容跑到別的分頁
$(".nav-item").eq(1).on("click", function () {
  $("#group_orderselectdiv .ticket_item_class").remove();
});

function groupComment(g_comment) {
  return `
  <div class="group_item_class">
    <a href="${g_comment.url}" class="orderurl">
      <div class="item_img_class">
          <img src="${g_comment.imgUrl}" class="item_img">
      </div>
        <div class="item_content">
          <h1 class="item_title">${g_comment.title}</h1>
        <div class="box">
          <p class="Number">${g_comment.summary}  </p>
        </div>
        <div class="date_div">
          <p class="date">${g_comment.realPrice}</p>
        </div>
      </div>
      <div class="item_commend_class">
        <i class="fa-solid fa-pen-to-square">我要評論</i>
      </div>
    </a>
  </div>
      `;
}

// ======================================================================
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