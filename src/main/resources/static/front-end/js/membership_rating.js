//進度條
// 獲取 total_order 元素
var totalOrderElement = document.getElementById('total_order');

// 獲取 progress-bar 元素
var progressBarElement = document.querySelector('.progress-bar');

// 獲取 total_order 的數字值
var totalOrder = parseInt(totalOrderElement.innerText);

// 計算進度條的寬度
var progressWidth = (totalOrder / 10) * 100;

// 設定進度條的寬度樣式
progressBarElement.style.width = progressWidth + '%';
progressBarElement.setAttribute('aria-valuenow', progressWidth);
