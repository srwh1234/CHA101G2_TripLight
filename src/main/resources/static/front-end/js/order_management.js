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
// <!-- 右邊會員資料--票券訂單 -->
//已完成訂單的假資料
const valid = {
  url: "https://www.kkday.com/zh-tw/product/142053-thredbo-resort-ski-snowboard-day-tour-from-haymarket-parramatta-australia",
  imgUrl: "https://blog.asiayo.com/wp-content/uploads/67817447_xl-1.jpg",
  title: "滑雪場門票",
  Number: "#0012",
  realPrice: 400,
};
//未完成訂單的假資料
const valid2 = {
  url: "https://www.kkday.com/zh-tw/product/115643-limited-time-offer-2-4-meals-hsinchu-camping-xiong-glamping-taiwan",
  imgUrl: "https://image.kkday.com/v2/image/get/h_650%2Cc_fit/s1.kkday.com/product_115643/20230515091041_Tx0bo/jpg",
  title: "露營",
  Number: "#A4001",
  realPrice: 1200,
};
//存進sessionStorage
sessionStorage.setItem('test_order_done', JSON.stringify(valid));
sessionStorage.setItem('test_order_imcomplete', JSON.stringify(valid2));
//一載入頁面在"未完成訂單上"
const orderIncomplete = JSON.parse(sessionStorage.getItem('test_order_imcomplete'));
$("#orderselectdiv").append(generateTicketItem(orderIncomplete));
//"未完成訂單"和"已完成訂單"切換
$("#orderselect").on("change", function (e) {
  $(".ticket_item_class").remove();
  var selectedOption = $(this).val();
  var newTicketItem = "";

  if (selectedOption === "已完成訂單") {
    const orderDone = JSON.parse(sessionStorage.getItem('test_order_done'));
    newTicketItem = generateTicketItem(orderDone);
  }
  else if (selectedOption === "未完成訂單") {
    const orderIncomplete = JSON.parse(sessionStorage.getItem('test_order_imcomplete'));
    newTicketItem = generateTicketItem(orderIncomplete);
  }
  if (newTicketItem !== "") {
    $(this).after(newTicketItem);
    sessionStorage.setItem("ticketAdded", "false");
  }
});
function generateTicketItem(order) {
  return `
      <div class="ticket_item_class">
        <a href=${order.url} class="orderurl">
          <div class="item_img_class">
            <img src=${order.imgUrl} class="item_img">
          </div>
          <div class="item_commend_class">
            <a href="#"><i class="fa-solid fa-pen-to-square">我要評論</i></a>
          </div>
          <div class="item_content">
            <h1 class="item_title">${order.title}</h1>
            <div>
              <p class="serialNumber">訂單編號：</p>
              <p class="Number">${order.Number}</p>
            </div>
            <div>
              <p class="price">TWD</p>
              <p class="realPrice">${order.realPrice}</p>
            </div>
          </div>
        </a>
      </div>
    `;
}


// <!-- 右邊會員資料--旅遊團訂單 -->
//已完成訂單的假資料
const valid3 = {
  url: "https://www.kkday.com/zh-tw/product/142053-thredbo-resort-ski-snowboard-day-tour-from-haymarket-parramatta-australia",
  imgUrl: "https://blog.asiayo.com/wp-content/uploads/67817447_xl-1.jpg",
  title: "已完成旅遊團TEST",
  Number: "#0012",
  realPrice: 1234,
};
//未完成訂單的假資料
const valid4 = {
  url: "https://www.kkday.com/zh-tw/product/115643-limited-time-offer-2-4-meals-hsinchu-camping-xiong-glamping-taiwan",
  imgUrl: "https://image.kkday.com/v2/image/get/h_650%2Cc_fit/s1.kkday.com/product_115643/20230515091041_Tx0bo/jpg",
  title: "未完成旅遊團test",
  Number: "#sdfh",
  realPrice: 5678,
};
//存進sessionStorage
sessionStorage.setItem('test_group_done', JSON.stringify(valid3));
sessionStorage.setItem('test_group_incomplete', JSON.stringify(valid4));

//一載入頁面在"未完成訂單上"
const test_group_incomplete = JSON.parse(sessionStorage.getItem('test_group_incomplete'));
$("#group_orderselectdiv").append(generateGroupOrderItem(test_group_incomplete));

//"未完成訂單"和"已完成訂單"切換（旅遊團訂單）
$("#group_orderselect").on("change", function (e) {
  $(".group_order_item_class").remove();
  var selectedOption = $(this).val();
  var newGroupOrderItem = "";

  if (selectedOption === "已完成訂單") {
    const groupOrderDone = JSON.parse(sessionStorage.getItem('test_group_done'));
    newGroupOrderItem = generateGroupOrderItem(groupOrderDone);
  } else if (selectedOption === "未完成訂單") {
    const groupOrderIncomplete = JSON.parse(sessionStorage.getItem('test_group_incomplete'));
    newGroupOrderItem = generateGroupOrderItem(groupOrderIncomplete);
  }

  if (newGroupOrderItem !== "") {
    $("#group_orderselectdiv").append(newGroupOrderItem);
    sessionStorage.setItem("groupOrderAdded", "false");
  }
});
function generateGroupOrderItem(groupOrder) {
  return `
      <div class="group_order_item_class">
        <a href=${groupOrder.url} class="group_order_url">
          <div class="item_img_class">
            <img src=${groupOrder.imgUrl} class="item_img">
          </div>
          <div class="item_commend_class">
            <a href="#"><i class="fa-solid fa-pen-to-square">我要評論</i></a>
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

