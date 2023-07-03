//找到會員id
let id = 0;
if (sessionStorage.getItem("test-login")) {
	id = JSON.parse(sessionStorage.getItem("test-login")).memberId;
} else {
	id = null;
}
var ticketOrder = 0;
function getTicket() {
	return axios.get("/rating/" + id)
		.then((response) => {
			// 將後端返回的資料指派給 ticketOrder 變數
			ticketOrder = response.data;
		});
}
let tripOrder = 0;
function getTrip() {
	return axios.get("/rating2/" + id)
		.then((response) => {
			// 將後端返回的資料指派給 tripOrder 變數
			tripOrder = response.data;
		});
}
//等後端資料都拿回來後，再開始做以下判斷
Promise.all([getTicket(), getTrip()]).then(() => {
	var totalOrderElement = document.getElementById('total_order');
	var progressBarElement = document.querySelector('.progress-bar');
	let totalOrder = ticketOrder + tripOrder;
	if (totalOrder < 10) {
		totalOrderElement.innerText = 10 - totalOrder;
	} else if (totalOrder >= 10 && totalOrder <= 20) {
		totalOrderElement.innerText = 20 - totalOrder;
	} else if (totalOrder >= 20) {
		document.getElementById("rating_div_p").innerText = "恭喜成為最高等會員"
	}

	// 計算進度條的寬度

	if (totalOrder < 10) {
		progressWidth = ((totalOrder) / 10) * 100;
	} else if (totalOrder >= 10 && totalOrder <= 20) {
		progressWidth = ((totalOrder - 10) / 10) * 100;
	} else if (totalOrder >= 20) {
		progressWidth = 100;
	}

	// 設定進度條的寬度樣式
	progressBarElement.style.width = progressWidth + "%";
	progressBarElement.setAttribute('aria-valuenow', progressWidth);
	//會員名稱
	const rate_name = document.querySelector("#rate_name")
	let memberGrade = 0;
	if (totalOrder < 10) {
		rate_name.innerText = '一般會員';
	} else if (totalOrder >= 10 && totalOrder < 20) {
		rate_name.innerText = '白金會員';
		memberGrade = 1;
		$.ajax({
			url: "/updateRating",
			method: "POST",
			data: {
				id: id,
				memberGrade: memberGrade
			},
			success: (e) => {
				console.log(e)
			},
			error: (error) => {
				console.log(error);
			}
		})
	} else {
		rate_name.innerText = '鑽石會員';
		memberGrade = 2;
		$.ajax({
			url: "/updateRating",
			method: "POST",
			data: {
				id: id,
				memberGrade: memberGrade
			},
			success: (e) => {
				console.log(e)
			},
			error: (error) => {
				console.log(error);
			}
		})
	}
})

// ============================存會員分級回資料庫=====================================

// ============================上傳大頭照=====================================
let camera = document.getElementById('camera');
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
		var memberPic = sessionStorage.getItem('uploadedPhoto');
		var formData = new FormData();
		formData.append('memberPic', file);

		$.ajax({
			url: "/m_saveImg/" + id,
			method: "POST",
			data: formData,
			processData: false,
			contentType: false,
			success: (response) => {
				console.log(response);
				console.log("儲存成功");
			},
			error: (error) => {
				console.log(error);
				console.log("儲存失敗");
			}
		});
	});
	input.click();
});
//顯示大頭照
$(document).ready(function () {
	$.ajax({
		url: "/img/members/" + id,
		method: "GET",
		success: function (response) {
			const imgUrl =  this.url;
			$('.rounded-circle').attr('src', imgUrl);
			console.log("圖片載入成功");
		},
		error: function (error) {
			console.log("圖片載入失敗");
		}
	});
});