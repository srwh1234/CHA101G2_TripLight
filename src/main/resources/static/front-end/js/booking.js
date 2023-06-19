// 提取表單資訊
const form = document.getElementById("myForm");

// 下拉式多選表單設計

let sightseeingValues; // 偏好景點
let activitiesValues; // 偏好活動
let formId;

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

  // 傳送表單資料物件
  let formData = {
    formId: formId, // 每個表單都會有一個ID
    destination: destination,
    travelDays: travelDays,
    people: people,
    budgetRange: budgetRange,
    preferredStyle: preferredStyle,
    sightseeingValues: sightseeingValues,
    activitiesValues: activitiesValues,
    otherDemands: message,
  };
  // 存入 sessionStorage
  sessionStorage.setItem("formData", formData);

  // 傳送物件
  axios
    .post("/formData", formData)
    .then((response) => {
      console.log(response);
    })
    .catch((error) => {
      console.error(error);
    });
  window.location.href = "ai_planning_results.html";
});

// 獲取 session ID
axios
  .get("/sessionId")
  .then((response) => {
    let data = response.data;
    sessionStorage.setItem("sessionId", data.sessionId);
    formId = data.formId;
  })
  .catch((error) => {
    console.error(error);
  });

// $.ajax({
//   url: "/getSessionId",
//   type: "GET",
//   success: function (response) {
//     let data = response.data;

//     let sessionId = response;
//     console.log("Session ID: " + sessionId);

//     sessionStorage.setItem("sessionId", sessionId);
//     // 在这里进行后续操作，使用sessionId发送请求或进行其他逻辑处理
//   },
//   error: function (error) {
//     console.log("Error retrieving Session ID: " + error);
//   },
// });
