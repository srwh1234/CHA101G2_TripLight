
function getRating() {
	const id=1;
	axios.get("/rating/"+id)
		.then((response) => {
			var totalOrderElement = document.getElementById('total_order');
			var progressBarElement = document.querySelector('.progress-bar');

			// 將後端返回的資料指派給 total_order 變數
			var totalOrder = response.data;

			// 更新 total_order 元素的內容

			if (totalOrder < 10) {
				totalOrderElement.innerText = totalOrder;
			} else if (totalOrder >= 10 && totalOrder <= 20) {
				totalOrderElement.innerText = 20 - totalOrder;
			} else if (totalOrder >= 20) {
				document.getElementById("rating_div_p").innerText = "恭喜成為最高等會員"
			}

			// 計算進度條的寬度

			if (totalOrder < 10) {
				progressWidth = ((10 - totalOrder) / 10) * 100;
			} else if (totalOrder >= 10 && totalOrder <= 20) {
				progressWidth = ((totalOrder - 10) / 10) * 100;
			} else if (totalOrder >= 20) {
				progressWidth = 100;
			}

			// 設定進度條的寬度樣式
			progressBarElement.style.width = progressWidth + "%";
			progressBarElement.setAttribute('aria-valuenow', progressWidth);
			//會員名稱
			const rate = response.data;
			const rate_name = document.querySelector("#rate_name")
			if (rate < 10) {
				rate_name.innerText = '一般會員';
			} else if (rate >= 10 && rate < 20) {
				rate_name.innerText = '白金會員';
			} else {
				rate_name.innerText = '鑽石會員';
			}
		});
}

// 調用 getRating 函式
getRating();
