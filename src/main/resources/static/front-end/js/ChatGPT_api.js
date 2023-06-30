// 建立事件連線
const memberId = sessionStorage.getItem("sessionId");
const sseSource = new EventSource(`/sse/${memberId}`);
const output = document.querySelector("#output");
// 監聽後端是否有傳訊息來前台
sseSource.addEventListener("message", function (event) {
  if (event.data !== null) {
    // 根据 sessionId 提取特定的值
    let value = event.data;
    sessionStorage.setItem("PlanningResults", value.replace(/\\n/g, "\n"));
  }

  // 將結果顯示在文字框
  output.innerHTML = sessionStorage.getItem("PlanningResults");
});

let text = $("#output").val();
// 檢測value的變動 沒變就執行
let initialLength = text.length;

// 定時檢查output長度是否有變化
const intervalId = setInterval(() => {
  text = $("#output").val();
  if (text.length !== initialLength) {
    initialLength = text.length; // 重置初始長度
  } else {
    clearInterval(intervalId); // 停止定時器執行

    // 關閉 SSE 連線
    sseSource.close();
    getLocations(); //獲得地點資訊

    getArray(); // 獲得緯度資料

    // getLocationData(); // 獲得所有地點資料

    // 顯示下標
    $(".storage-planning a").css("display", "block");
    // 設定文字框可更改
    $("#output").removeAttr("readonly");
  }
}, 1000); // 每秒检查一次

// 點擊儲存行程按鈕，燈箱，執行資料庫儲存
$("#storage-planning").on("click", function (e) {
  e.preventDefault();

  // 取得 textarea 與 url 資料
  let text = $("textarea").val();
  let url = sessionStorage.getItem("url");
  let formdata = sessionStorage.getItem("formData");
  let locationData = sessionStorage.getItem("locationsData");

  console.log("url" + url);
  console.log(text);
  // 將url 與 文字框內容回傳至後端, 以及表單資料formdata
  $.ajax({
    url: "/aiFavorite/" + memberId,
    type: "POST",
    data: {
      resultData: text,
      resultUrl: url,
    },
    // 當請求成功時，將伺服器返回的response印出到console中
    success: function (response) {
      if (response) {
        // 儲存成功動畫
        Swal.fire({
          icon: "success",
          title: "儲存成功",
          showConfirmButton: false,
          timer: 1500,
        });
      } else {
        Swal.fire({
          icon: "error",
          title: "儲存失敗",
          showConfirmButton: false,
          timer: 1500,
        });
      }
    },
    // 當請求失敗時，將錯誤訊息印出到console中
    error: function (xhr) {
      console.log(xhr.responseText);
      Swal.fire({
        icon: "error",
        title: "儲存失敗",
        showConfirmButton: false,
        timer: 1500,
      });
    },
  });
});
// 心跳機制
// 在前端定期發送心跳請求，表示使用者仍然在線
// 定期發送心跳請求
setInterval(function () {
  // 發送心跳請求到後端
  // 使用Ajax
  $.ajax({
    url: "/heartbeat/" + memberId, // 心跳請求的後端路徑
    method: "POST",
    data: { memberId: memberId },
    success: function (response) {
      // 心跳請求成功回調
      console.log(memberId + "心跳請求成功");
    },
    error: function (xhr, status, error) {
      // 心跳請求失敗回調
      console.error("心跳請求失敗:", error);
    },
  });
}, 30000); // 每隔30秒發送一次心跳請求

// 離開網頁
$(window).on("beforeunload", function (event) {
  // 发送AJAX请求

  $.ajax({
    url: "/notifyBackend/" + memberId, // 后端接收消息的URL
    method: "POST",
    data: { memberId: memberId },
    success: function (response) {
      console.log(memberId + "滾蛋了");
    },
    error: function (xhr, status, error) {
      console.error("失敗了幹:", error);
    },
  });
});

// 接收後台套裝行程資料
getPackages();
function getPackages() {
  $.ajax({
    url: "/trips/" + memberId,
    method: "GET",
    dataType: "json",
    success: function (packages) {
      console.log("接收資料");
      console.log(packages);

      for (let i = 0; i < packages.length; i++) {
        $(".recommendTrip").append(`<div class="col-lg-3 col-sm-6 mb-3">
        <div class="image-tour position-relative">
          <a href="itinerary_page.html?tripId=${packages[i].tripId}"
            ><img src=${packages[i].tripImage} alt="" class="card-img"
          /></a>
          <p><span class="fa fa-tags"></span> <span>${packages[i].priceAdult}起</span></p>
        </div>
        <div class="package-info">
          <h6 class="mt-1">
            <span class="fa fa-map-marker mr-2"></span> ${packages[i].city}
          </h6>
          <h5 class="my-2">${packages[i].tripName}</h5>
          <p class="">${packages[i].tripContent}</p>
          <ul class="listing mt-3">
            <li>
              Duration :
              <span>${packages[i].tripDay} Days</span>
            </li>
          </ul>
        </div>
        </div>`);
      }
    },
    error: function (error) {
      console.log(error);
    },
  });
}

// 接收後台票券資料
getTickets();
function getTickets() {
  $.ajax({
    url: "/ai/tickets/" + memberId,
    method: "GET",
    dataType: "json",
    success: function (Tickets) {
      console.log("接收資料");
      console.log(Tickets);

      for (let i = 0; i < Tickets.length; i++) {
        $(".recommendTrip").append(`<div class="col-lg-3 col-sm-6 mb-3">
        <div class="image-tour position-relative">
          <a href="tickets_detail.html?id=${Tickets[i].ticketId}"
            ><img src=${Tickets[i].image} alt="" class="card-img"
          /></a>
          <p><span class="fa fa-tags"></span> <span>${Tickets[i].price}起</span></p>
        </div>
        <div class="package-info">
          <h6 class="mt-1">
            <span class="fa fa-map-marker mr-2"></span> ${Tickets[i].city}
          </h6>
          <h5 class="my-2">${Tickets[i].name}</h5>
          <p class="">${Tickets[i].description}</p>
          <ul class="listing mt-3">
            <li>
              <span>票券</span>
            </li>
          </ul>
        </div>
        </div>`);
      }
    },
    error: function (error) {
      console.log(error);
    },
  });
}

// let package = {
//   priceAdult: 100, // packages.price
//   city: "綠島", // packages.city
//   tripName: "綠島五日遊", // packages.
//   tripDescription: "跟著經驗豐富的導遊探索薄荷島，在處女島海灘放鬆身心",
//   tripDay: 5,
//   imageSrc: "images/p1.jpg",
// };
