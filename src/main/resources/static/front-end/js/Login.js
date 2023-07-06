const container = document.querySelector(" #login-container ");
const loginLink = document.querySelector(".login-link");
const registerLink = document.querySelector(".register-link");
const loginbtn = document.getElementById("login");
const forgetPwd = document.querySelector("#forgetPwd")
//清除登入/註冊屬性
loginbtn.href = "";

//點擊註冊
registerLink.addEventListener("click", () => {
	container.classList.add("active");
});
//點擊登入
loginLink.addEventListener("click", () => {
	container.classList.remove("active");
});

//點擊"登入/註冊" 跳出登入小視窗
function loginin(e) {
	e.preventDefault();
	container.classList.add("active-popup");
}
loginbtn.addEventListener("click", loginin);

//關閉登入小視窗
const closeIcon = document.querySelector(".close-icon");
closeIcon.addEventListener("click", (e) => {
	container.classList.remove("active-popup");
});

//更改註冊html屬性
$("#register_account").attr("type", "text");
//================================登入=====================================
// 取得按鈕
const login33 = document.querySelectorAll(".main-btn")[0];
//按下login
login33.addEventListener("click", function (e) {
	e.preventDefault();
	container.classList.remove("active-popup");
	container.classList.add("active");
	//================================獲得後端資料=====================================
	let memberEmail = $("#email").val();
	let memberPassword = $("#password").val();

	$.ajax({
		type: "POST",
		url: "/login",
		data: {
			email: memberEmail,
			password: memberPassword,
		},
		success: function (response) {
			if (response === 0) {
				Swal.fire({
					icon: "error",
					title: "登入失敗",
					text: "帳號/密碼錯誤",
					showConfirmButton: false,
					timer: 1500,
				});
				// 清空欄位
				$("#email").val("");
				$("#password").val("");
			} else if (response === -1) {
				Swal.fire({
					icon: "error",
					title: "登入失敗",
					text: "您已被停權，請聯絡客服處理",
					showConfirmButton: false,
					timer: 1500,
				});
				// 清空欄位
				$("#email").val("");
				$("#password").val("");
			} else {

				// 清空欄位
				$("#email").val("");
				$("#password").val("");
				//資料存入sessionStorage
				const valid = {
					email: memberEmail,
					memberId: response,
				};
				sessionStorage.setItem("test-login", JSON.stringify(valid));
				// loginbtn.innerHTML = ` <i class="fa-regular fa-lightbulb" style="color: #dcdfe5;">Hello</i>`;
				// container.classList.remove("active-popup");
				loginbtn.removeEventListener("click", loginin);

				Swal.fire({
					icon: "success",
					title: "登入成功",
					showConfirmButton: false,
					timer: 1500,
				}).then(() => {
					location.reload();
				});
			}
		},
		error: function (xhr) {
			console.log(xhr.responseText);

		},
	});
});

//================================註冊=====================================
// 取得註冊按鈕=====================================================
function validateEmail(email) {
	const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
	return emailRegex.test(email);
}
function validatePassword(password) {
	const goodPassword = /^.{8,}$/;
	return goodPassword.test(password);
}
const register_btn = $(".register").find(".main-btn");
register_btn.on("click", (e) => {
	e.preventDefault();
	container.classList.remove("active-popup");
	let memberEmail = $("#register_email").val();
	let memberPassword = $("#register_password").val();
	let memberNameFirst = $("#register_account").val();
	if (!validateEmail(memberEmail)) {
		Swal.fire({
			icon: "error",
			title: "註冊失敗",
			text: "請輸入有效的電子郵件地址",
			showConfirmButton: false,
			timer: 1500,
		});
		return;
	}
	if (!validatePassword(memberPassword)) {
		Swal.fire({
			icon: "error",
			title: "註冊失敗",
			text: "密碼至少需要 8 個字元",
			showConfirmButton: false,
			timer: 1500,
		});
		return;
	}
	if (memberNameFirst.trim() === "") {
		Swal.fire({
			icon: "error",
			title: "註冊失敗",
			text: "請輸入有效的使用者名稱",
			showConfirmButton: false,
			timer: 1500,
		});
		return;
	}
	$.ajax({
		type: "POST",
		url: "/register",
		data: {
			account: memberNameFirst,
			email: memberEmail,
			password: memberPassword,
		},
		success: function (response) {
			console.log(response);
			if (response) {
				// 儲存成功動畫
				Swal.fire({
					icon: "success",
					title: "註冊成功",
					showConfirmButton: false,
					timer: 1500,
				});
				// 清空欄位
				$("#register_email").val("");
				$("#register_password").val("");
				$("#register_account").val("");
			} else {
				Swal.fire({
					icon: "error",
					title: "註冊失敗",
					text: "該電子郵件已經被註冊過",
					showConfirmButton: false,
					timer: 1500,
				});
			}
		},
		error: function (xhr) {
			console.log(xhr.responseText);
		},
	});
});
// 取得註冊按鈕=====================================================
//找到會員id
let Id = 0;
if (sessionStorage.getItem("test-login")) {
	Id = JSON.parse(sessionStorage.getItem("test-login")).memberId;
} else {
	Id = null;
}
//拿會員的名字，更改登入鍵 =>燈泡+名字
window.addEventListener("load", function () {
	const valid = JSON.parse(sessionStorage.getItem("test-login"));
	if (valid) {
		$.ajax({
			url: "/getName/" + Id,
			method: "GET",
			dataType: "text",
			success: (response) => {
				response = $.trim(response);
				if (response !== "") {
					document.querySelector(
						"#login"
					).innerHTML = ` <i class="fa-regular fa-lightbulb" style="color: #dcdfe5;"> Hi ${response}</i>`;
				} else {
					document.querySelector(
						"#login"
					).innerHTML = ` <i class="fa-regular fa-lightbulb" style="color: #dcdfe5;">會員您好</i>`;
				}
			}, error: (error) => {
				console.log(error)
			}
		})


		loginbtn.addEventListener("click", function (e) {
			if (valid) {
				e.preventDefault;
				container.classList.remove("active-popup");
				loginbtn.removeEventListener("click", loginin);
				document.querySelector(".member").classList.toggle("-on");
			}
		});
	}
});

//=======================================================
// 右上導覽列訂單管理
$("#order_li").on("mouseover", function () {
	$(".order_list").addClass("-on");
	$(".order_content").addClass("-on");
});
$("#order_li").on("mouseout", function () {
	$(".order_list").removeClass("-on");
	$(".order_content").removeClass("-on");
});
//=======================================================
// 左列會員訂單管理

$("#order_li2").on("mouseover", function () {
	$("#order_list2, #order_content1, #order_content2").addClass("-on");
});
$("#order_li2").on("mouseout", function () {
	$("#order_list2, #order_content1, #order_content2").removeClass("-on");
});

//登出
const logout_li = document.querySelector("#logout_li");

$(logout_li).on("click", function (e) {
	e.preventDefault();

	$.ajax({
		url: "/logout",
		method: "GET",
		success: function (response) {
			if (response) {
				document.querySelector("#login").innerHTML = ` 登入/註冊`;
				document.querySelector(".member").classList.toggle("-on");
				sessionStorage.clear();
				window.location.assign("index.html");
			}
		},
		error: function (xhr) {
			console.log("失敗");
		},
	});
});

document.addEventListener("DOMContentLoaded", function () {
	const urlParams = new URLSearchParams(window.location.search);
	if (urlParams.has("1")) {
		$("#login-container").addClass("active-popup");
	}
});
// 忘記密碼=====================================================
$("#forgetPwd").on("click", function () {
	container.classList.remove("active-popup");
	Swal.fire({
		title: '忘記密碼',
		text: '請輸入電子信箱',
		input: 'email',
		inputPlaceholder: '請輸入電子信箱',
		showCancelButton: true,
		confirmButtonText: '發送驗證信',
		cancelButtonText: '取消',
	}).then(function (result) {
		if (result.isConfirmed) {
			let forgetPwdEmail = result.value;
			$.ajax({
				url: '/sendMail',  // 指定後端的郵件發送端點
				method: 'POST',     // 使用POST方法
				data: {
					forgetPwdEmail: forgetPwdEmail  // 直接傳遞對象
				},
				success: function (response) {
					console.log('郵件傳送成功!');
					Swal.fire({
						icon: "success",
						title: "驗證信發送成功",
						showConfirmButton: false,
					}).then(() => {
						window.location.href = "/front-end/index.html";
					})
				},
				error: function (xhr, status, error) {
					console.log('郵件傳送失敗!');
					console.log(error);
					Swal.fire({
						icon: "error",
						title: "驗證信發送失敗",
						text: "請聯絡客服",
						showConfirmButton: false,
					}).then(() => {
						window.location.href = "/front-end/index.html?1";
					})
				}
			})
		}
		else {
			container.classList.add("active-popup");
		}
	})
});

// 姐~這邊借我放一下  購物車抖動效果
if (sessionStorage.getItem('ShakeCart')) {
	$('img[src$="shoppingCar.svg"]').addClass('shake-animation');
}
$('img[src$="shoppingCar.svg"]').on('click', () => sessionStorage.removeItem('ShakeCart'));


// 註冊密碼提示=====================================================
$("#register_password").on("focus", (e) => {
	if ($("#register_password").val().length < 8) {
		$("#pwdHint").addClass("-on");
	} else {
		$("#pwdHint").removeClass("-on");

	}
})
$("#register_password").on("blur", (e) => {
	if ($("#register_password").val().length < 8) {
		$("#pwdHint").addClass("-on");
	} else {
		$("#pwdHint").removeClass("-on");

	}
})