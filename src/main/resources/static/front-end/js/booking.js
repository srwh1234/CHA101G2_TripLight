// 提取表單資訊
const form = document.getElementById("myForm");

form.addEventListener("submit", async function (event) {
  event.preventDefault(); // 防止表單提交

  // 獲取表單元素的值
  const destination = document.getElementById("destination").value; // 目的地
  const travelDate = document.getElementById("travelDate").value; // 旅遊日期
  const travelDays = document.getElementById("travelDays").value; // 旅遊天數
  const budgetRange = document.getElementById("budgetRange").value; // 預算
  const message = document.getElementById("message").value;


  // 旅遊風格
  const preferredStyle = document.getElementById("travelStyle").value;

  // 旅遊景點
  const sightseeing = document.querySelectorAll(
    'input[name="sightseeing"]:checked'
  );
  const sightseeingValues = [];
  sightseeing.forEach((item) => {
    sightseeingValues.push(item.value);
  });

  // 旅遊活動
  const activities = document.querySelectorAll(
    'input[name="activities"]:checked'
  );
  const activitiesValues = [];
  activities.forEach((item) => {
    activitiesValues.push(item.value);
  });

  // 控制額外需求字數
  if (message.length > 50) {
    message = message.slice(0, 50);
  }

  // 取得地點
  // 将变量存储在sessionStorage中
  sessionStorage.setItem("destination", destination);

  const send = `
1.目的地:${destination}
2.旅行天數:${travelDays}
3.預算:${budgetRange}台幣
4.偏好旅遊風格:${preferredStyle}
5.偏好景點:${sightseeingValues}
6.偏好活動:${activitiesValues}
7.其他需求：${message}`;
  console.log(send);

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
