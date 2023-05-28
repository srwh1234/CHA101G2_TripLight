// 建立事件連線
const sseSource = new EventSource("/sse");
const output = document.querySelector("#output");
// 監聽後端是否有傳訊息來前台
sseSource.addEventListener("message", function (event) {
  // 將訊息顯示在網頁上，因為被後端處理過 "\n" 會變 "\\n"，所以前端要改回來
  output.innerHTML = event.data.replace(/\\n/g, "\n");
});

let text = $("#output").val();
// 檢測value的變動 沒變就執行
let initialLength = text.length;

// 定時檢查output長度是否有變化
setInterval(() => {
  text = $("#output").val();
  if (text.length !== initialLength) {
    initialLength = text.length; // 重置初始长度
  } else {
    // 顯示下標
    $(".storage-planning a").css("display", "block");
    // 設定文字框可更改
    $("#output").removeAttr("readonly");
  }
}, 1000); // 每秒检查一次
