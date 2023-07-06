let img = document.querySelector('.card-body img');
let lastName = document.querySelector('#inputLastName');
let firstName = document.querySelector('#inputFirstName');
let IdNumber = document.querySelector('#inputIdNumber');
let birthday = document.querySelector('#inputBD');
let phoneNumber = document.querySelector('#inputPhoneNumber');
let gender = document.querySelector('#inputGender');
//let city = document.querySelector('#city');
//let dist = document.getElementById('dist');
let address = document.querySelector('#inputAddress');
let email = document.querySelector('#inputEmail');
let saveBtn = document.querySelector('#saveData');
// <!-- 左邊會員照片+導覽列 -->
//找到會員id
let theId = 0;
if (sessionStorage.getItem("test-login")) {
	theId = JSON.parse(sessionStorage.getItem("test-login")).memberId;
} else {
	theId = null;
}
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
			url: "/m_saveImg/" + theId,
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
		url: "/img/members/" + theId,
		method: "GET",
		success: function (response) {
			const imgUrl = this.url;
			$('.rounded-circle').attr('src', imgUrl);
			console.log("圖片載入成功");
		},
		error: function (error) {
			console.log("圖片載入失敗");
		}
	});
});
// <!-- 右邊會員資料--基本資料 -->

// ============================地址=====================================
const dist_data = {
	'臺北市': [
		'中正區', '大同區', '中山區', '萬華區', '信義區', '松山區', '大安區', '南港區', '北投區', '內湖區', '士林區', '文山區'
	],
	'新北市': [
		'板橋區', '新莊區', '泰山區', '林口區', '淡水區', '金山區', '八里區', '萬里區', '石門區', '三芝區', '瑞芳區', '汐止區', '平溪區', '貢寮區', '雙溪區', '深坑區', '石碇區', '新店區', '坪林區', '烏來區', '中和區', '永和區', '土城區', '三峽區', '樹林區', '鶯歌區', '三重區', '蘆洲區', '五股區'
	],
	'基隆市': [
		'仁愛區', '中正區', '信義區', '中山區', '安樂區', '暖暖區', '七堵區'
	],
	'桃園市': [
		'桃園區', '中壢區', '平鎮區', '八德區', '楊梅區', '蘆竹區', '龜山區', '龍潭區', '大溪區', '大園區', '觀音區', '新屋區', '復興區'
	],
	'新竹縣': [
		'竹北市', '竹東鎮', '新埔鎮', '關西鎮', '峨眉鄉', '寶山鄉', '北埔鄉', '橫山鄉', '芎林鄉', '湖口鄉', '新豐鄉', '尖石鄉', '五峰鄉'
	],
	'新竹市': [
		'東區', '北區', '香山區'
	],
	'苗栗縣': [
		'苗栗市', '通霄鎮', '苑裡鎮', '竹南鎮', '頭份鎮', '後龍鎮', '卓蘭鎮', '西湖鄉', '頭屋鄉', '公館鄉', '銅鑼鄉', '三義鄉', '造橋鄉', '三灣鄉', '南庄鄉', '大湖鄉', '獅潭鄉', '泰安鄉'
	],
	'臺中市': [
		'中區', '東區', '南區', '西區', '北區', '北屯區', '西屯區', '南屯區', '太平區', '大里區', '霧峰區', '烏日區', '豐原區', '后里區', '東勢區', '石岡區', '新社區', '和平區', '神岡區', '潭子區', '大雅區', '大肚區', '龍井區', '沙鹿區', '梧棲區', '清水區', '大甲區', '外埔區', '大安區'
	],
	'南投縣': [
		'南投市', '埔里鎮', '草屯鎮', '竹山鎮', '集集鎮', '名間鄉', '鹿谷鄉', '中寮鄉', '魚池鄉', '國姓鄉', '水里鄉', '信義鄉', '仁愛鄉'
	],
	'彰化縣': [
		'彰化市', '員林鎮', '和美鎮', '鹿港鎮', '溪湖鎮', '二林鎮', '田中鎮', '北斗鎮', '花壇鄉', '芬園鄉', '大村鄉', '永靖鄉', '伸港鄉', '線西鄉', '福興鄉', '秀水鄉', '埔心鄉', '埔鹽鄉', '大城鄉', '芳苑鄉', '竹塘鄉', '社頭鄉', '二水鄉', '田尾鄉', '埤頭鄉', '溪州鄉'
	],
	'雲林縣': [
		'斗六市', '斗南鎮', '虎尾鎮', '西螺鎮', '土庫鎮', '北港鎮', '莿桐鄉', '林內鄉', '古坑鄉', '大埤鄉', '崙背鄉', '二崙鄉', '麥寮鄉', '臺西鄉', '東勢鄉', '褒忠鄉', '四湖鄉', '口湖鄉', '水林鄉', '元長鄉'
	],
	'嘉義縣': [
		'太保市', '朴子市', '布袋鎮', '大林鎮', '民雄鄉', '溪口鄉', '新港鄉', '六腳鄉', '東石鄉', '義竹鄉', '鹿草鄉', '水上鄉', '中埔鄉', '竹崎鄉', '梅山鄉', '番路鄉', '大埔鄉', '阿里山鄉'
	],
	'嘉義市': [
		'東區', '西區'
	],
	'臺南市': [
		'中西區', '東區', '南區', '北區', '安平區', '安南區', '永康區', '歸仁區', '新化區', '左鎮區', '玉井區', '楠西區', '南化區', '仁德區', '關廟區', '龍崎區', '官田區', '麻豆區', '佳里區', '西港區', '七股區', '將軍區', '學甲區', '北門區', '新營區', '後壁區', '白河區', '東山區', '六甲區', '下營區', '柳營區', '鹽水區', '善化區', '大內區', '山上區', '新市區', '安定區'
	],
	'高雄市': [
		'楠梓區', '左營區', '鼓山區', '三民區', '鹽埕區', '前金區', '新興區', '苓雅區', '前鎮區', '小港區', '旗津區', '鳳山區', '大寮區', '鳥松區', '林園區', '仁武區', '大樹區', '大社區', '岡山區', '路竹區', '橋頭區', '梓官區', '彌陀區', '永安區', '燕巢區', '田寮區', '阿蓮區', '茄萣區', '湖內區', '旗山區', '美濃區', '內門區', '杉林區', '甲仙區', '六龜區', '茂林區', '桃源區', '那瑪夏區'
	],
	'屏東縣': [
		'屏東市', '潮州鎮', '東港鎮', '恆春鎮', '萬丹鄉', '長治鄉', '麟洛鄉', '九如鄉', '里港鄉', '鹽埔鄉', '高樹鄉', '萬巒鄉', '內埔鄉', '竹田鄉', '新埤鄉', '枋寮鄉', '新園鄉', '崁頂鄉', '林邊鄉', '南州鄉', '佳冬鄉', '琉球鄉', '車城鄉', '滿州鄉', '枋山鄉', '霧台鄉', '瑪家鄉', '泰武鄉', '來義鄉', '春日鄉', '獅子鄉', '牡丹鄉', '三地門鄉'
	],
	'宜蘭縣': [
		'宜蘭市', '羅東鎮', '蘇澳鎮', '頭城鎮', '礁溪鄉', '壯圍鄉', '員山鄉', '冬山鄉', '五結鄉', '三星鄉', '大同鄉', '南澳鄉'
	],
	'花蓮縣': [
		'花蓮市', '鳳林鎮', '玉里鎮', '新城鄉', '吉安鄉', '壽豐鄉', '秀林鄉', '光復鄉', '豐濱鄉', '瑞穗鄉', '萬榮鄉', '富里鄉', '卓溪鄉'
	],
	'臺東縣': [
		'臺東市', '成功鎮', '關山鎮', '長濱鄉', '海端鄉', '池上鄉', '東河鄉', '鹿野鄉', '延平鄉', '卑南鄉', '金峰鄉', '大武鄉', '達仁鄉', '綠島鄉', '蘭嶼鄉', '太麻里鄉'
	],
	'澎湖縣': [
		'馬公市', '湖西鄉', '白沙鄉', '西嶼鄉', '望安鄉', '七美鄉'
	],
	'金門縣': [
		'金城鎮', '金湖鎮', '金沙鎮', '金寧鄉', '烈嶼鄉', '烏坵鄉'
	],
	'連江縣': [
		'南竿鄉', '北竿鄉', '莒光鄉', '東引鄉'
	]
}

$("#city").change((e) => {
	$('#dist').empty(); // Clear the dropdown
	const distarr = dist_data[e.target.value];
	for (let text of distarr) {
		$('#dist').append(`<option>${text}</option>`);
	}
});


// ============================生日=====================================
$(function () {
	$("#inputBD").datepicker();

	$("#saveData").on("click", function () {
		getData();
	});
});

// <!-- 右邊會員資料--帳號安全 -->
// ============================會員資料儲存=====================================
function getData() {
	const data = {
		memberNameLast: $("#inputLastName").val(),
		memberNameFirst: $("#inputFirstName").val(),
		memberIdCard: $("#inputIdNumber").val(),
		memberBirth: $("#inputBD").datepicker("getDate"),
		memberPhone: $("#inputPhoneNumber").val(),
		memberGender: $("#inputGender").val(),
		memberCity: $("#city").val(),
		memberDist: $("#dist").val(),
		memberAddress: $("#inputAddress").val(),
		memberEmail: $("#inputEmail").val()
	}
	// 檢查資料是否有空值
	const hasEmptyValue = Object.values(data).some(value => value === "");
	if (hasEmptyValue || $('#inputLastName').val().trim() === "" || $('#inputFirstName').val().trim() === "" || $('#inputIdNumber').val().trim() === "" || $("#inputPhoneNumber").val().trim() === "" || $("#inputAddress").val().trim() === "") {
		Swal.fire({
			icon: "error",
			title: "儲存失敗",
			text: "請輸入完整資料",
			showConfirmButton: false,
			timer: 1500,
		});
	} else {
		$.ajax({
			url: "/member/" + theId,
			type: "POST",
			data: JSON.stringify(data),
			contentType: "application/json",
			success: function (response) {

			},
			error: function (error) {
				console.error(error);
			}
		});
	}
}
$("#saveData").click(function () {
	getData();
	Swal.fire({
		icon: "success",
		title: "資料儲存成功",
		showConfirmButton: false,
		timer: 1500,
	});
})


// 從資料庫中獲取並設定使用者資料到 input
$(document).ready(function () {
	$.ajax({
		url: "/memberdetail/" + theId,
		method: "GET",
		dataType: "json",
		success: function (response) {
			let member = response;

			$('#inputLastName').val(member.memberNameLast);
			$('#inputFirstName').val(member.memberNameFirst);
			$('#inputIdNumber').val(member.memberIdCard);
			// $('#inputBD').val(member.memberBirth);
			$('#inputPhoneNumber').val(member.memberPhone);
			$('#inputGender').val(member.memberGender);
			$('#city').val(member.memberCity);
			$('#dist').val(member.memberDist);
			$('#inputAddress').val(member.memberAddress);
			$('#inputEmail').val(member.memberEmail);

			if ($('#city').val() !== null) {
				$('#city').trigger('change');
			} else {
			}

			// 從資料庫獲取的日期換格式
			const databaseDate = member.memberBirth;
			if (databaseDate === null) {
				$('#inputBD').val(databaseDate);
			} else {
				const date = new Date(databaseDate);

				const year = date.getFullYear();
				const month = ('0' + (date.getMonth() + 1)).slice(-2);
				const day = ('0' + date.getDate()).slice(-2);

				// 格式化日期為 "mm/dd/yyyy" 的格式
				const formattedDate = `${month}/${day}/${year}`;
				$('#inputBD').val(formattedDate);

			}

		},
		error: function (error) {
			console.log(error);
			console.log(error.response);

		}
	});
});
// ============================會員修改密碼=====================================
//換新密碼
function changePwd() {
	let memberPassword1 = $("#editPassword").val();
	let memberPassword = $("#confirmPassword").val();
	if (memberPassword1.length < 8) {
		Swal.fire({
			icon: "error",
			title: "密碼不得小於八碼",
			showConfirmButton: false,
			timer: 1500,
		});
		$("#editPassword").val("")
		$("#confirmPassword").val("")
	} else if (memberPassword1 !== memberPassword) {
		Swal.fire({
			icon: "error",
			title: "新密碼錯誤",
			text: "請重複確認密碼",
			showConfirmButton: false,
			timer: 1500,
		});
		$("#editPassword").val("")
		$("#confirmPassword").val("")
	} else {
		$.ajax({
			url: "/changePwd",
			method: "POST",
			data: {
				id: theId,
				password: memberPassword
			},
			success: (response) => {
				$("#editPassword").val("")
				$("#confirmPassword").val("")
				Swal.fire({
					icon: "success",
					title: "密碼更新成功",
					showConfirmButton: false,
					timer: 1500,
				});
			},
			error: function (error) {
				console.log(error);
				console.log(error.response);
			}
		})
	}
}
//確認舊密碼正確	
function getOldPwd() {
	let memberPassword = $("#currentPassword").val();
	$.ajax({
		url: "/pwd/" + theId,
		method: "POST",
		data: {
			memberPassword: memberPassword,
		},
		success: (response) => {
			if (response === false) {
				Swal.fire({
					icon: "error",
					title: "舊密碼錯誤",
					text: "請輸入正確舊密碼",
					showConfirmButton: false,
					timer: 1500,
				});
				// 清空欄位
				$("#currentPassword").val("");
				return;

			} else {
				console.log("true")
				$("#currentPassword").val("");
			}
		},
		error: (error) => {
			console.log(error)
		}
	})
}

$("#saveData2").click((e) => {
	getOldPwd();
	changePwd();

})

//button for test
//$("#saveData").prepend(`<button id="testbtn">test</button>`);
//$("#testbtn").on("click", function() {
//	// 填充相應值
//	$("#inputLastName").val("葉");
//
//	$("#inputIdNumber").val("a");
//	$("#inputBD").val("06/17/2022");
//	$("#inputPhoneNumber").val("a");
//	$("#inputGender").val("女");
//	$("#city").val("臺北市");
//	$("#dist").val("中正區");
//	$("#inputAddress").val("中正路100號");
//	$("#inputEmail").val("aaa@gmail.com");
//});
