// 提取表單資訊
const form = document.getElementById("myForm");

// 下拉式多選表單設計

let sightseeingValues; // 偏好景點
let activitiesValues; // 偏好活動

$(".select-input").blur(function () {
  sightseeingValues = "";
  $("input[name='sightseeing']:checked").each(function () {
    sightseeingValues += $(this).val() + "、";
  });
  sightseeingValues = sightseeingValues.slice(0, -1); // 刪掉最後一個逗號，返回字符串中從 0 開始直到倒數第一個字符
  $(this).val(sightseeingValues);
});

$(".select-input2").blur(function () {
  activitiesValues = "";
  $("input[name='activities']:checked").each(function () {
    activitiesValues += $(this).val() + "、";
  });
  activitiesValues = activitiesValues.slice(0, -1); // 刪掉最後一個逗號，返回字符串中從 0 開始直到倒數第一個字符
  $(this).val(activitiesValues);
});

form.addEventListener("submit", async function (event) {
  event.preventDefault(); // 防止表單提交

  // 獲取表單元素的值
  const destination = document.getElementById("destination").value; // 目的地
  const travelDays = document.getElementById("travelDays").value; // 旅遊天數
  const budgetRange = document.getElementById("budgetRange").value; // 預算
  let message = document.getElementById("message").value; // 訊息
  const people = document.getElementById("people").value; // 人數
  const preferredStyle = document.getElementById("travelStyle").value; // 旅遊風格

  // // 控制額外需求字數
  if (message.length > 100) {
    message = message.slice(0, 100);
  }

  // 取得地點
  // 将变量存储在sessionStorage中
  sessionStorage.setItem("destination", destination);

  const send = `
1.目的地:${destination}
2.旅行天數:${travelDays}
3.人數:${people}
4.預算:${budgetRange}台幣
5.偏好旅遊風格:${preferredStyle}
6.偏好景點:${sightseeingValues}
7.偏好活動:${activitiesValues}
8.其他需求：${message}`;
  console.log(send);

  // 存入sessionStorage
  sessionStorage.setItem("formdata", send);

  //   將資料傳至後台;
  $.ajax({
    url: "/processVariable",
    type: "POST",
    data: { variable: send },
    // 當請求成功時，將伺服器返回的response印出到console中
    success: function (response) {
      console.log(response);
    },
    // 當請求失敗時，將錯誤訊息印出到console中
    error: function (xhr) {
      console.log(xhr.responseText);
    },
  });

  window.location.href = "ai_planning_results.html";
});

// 獲取 session ID
$.ajax({
  url: "/getSessionId",
  type: "GET",
  success: function (response) {
    var sessionId = response;
    console.log("Session ID: " + sessionId);

    sessionStorage.setItem("sessionId", sessionId);
    // 在这里进行后续操作，使用sessionId发送请求或进行其他逻辑处理
  },
  error: function (error) {
    console.log("Error retrieving Session ID: " + error);
  },
});
