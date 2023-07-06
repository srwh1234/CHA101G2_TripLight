let img = document.querySelector(".card-body img");
//找會員id
let theId = 0;
if (sessionStorage.getItem("test-login")) {
	theId = JSON.parse(sessionStorage.getItem("test-login")).memberId;
} else {
	theId = null;
}
// <!-- 左邊會員照片+導覽列 -->
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
// ==========================右邊會員資料--票券訂單 =====================================
//找到會員id
let memberId = 0;
if (sessionStorage.getItem("test-login")) {
	memberId = JSON.parse(sessionStorage.getItem("test-login")).memberId;
} else {
	memberId = null;
}

// 設定點擊票券
$(window).on("load", function () {
	$(".tab-pane").eq(0).addClass("show active"); // 顯示
	generateTicket();
});
function generateTicket() {
	$.ajax({
		url: "/tickets/" + memberId,
		method: "GET",
		dataType: "json",
		success: function (t_favorite) {
			if (!$(".tab-pane").eq(0).find("#orderselect").next().hasClass("ticket_item_class")) {
				for (let i = 0; i < t_favorite.length; i++) {
					$(".tab-pane").eq(0).find("#orderselect")
						.after(`<div class="ticket_item_class">
						<a href="/front-end/tickets_detail.html?id=${t_favorite[i].ticketId}" class="orderurl">                 
                 <div class="item_img_class">
                  <img src="${t_favorite[i].imgUrl}" class="item_img">  
                 </div>
                   <div class="item_content">
                     <h1 class="item_title">${t_favorite[i].name}</h1>
                     <div class="box">
                         <p class="Number dynamic-text">${t_favorite[i].description}</p>
                     </div>
                     <div id="allPrice">
                         <p class="price">TWD</p>
                         <p class="realPrice">${t_favorite[i].price}</p>
                     </div>
                 </div>
               </a>
               <div class="item_commend_class">
                  <i class="fa-solid fa-heart heart remove_btn"></i>  
               </div>
             </div>
                `);
				}
			}
		},
		error: function (error) {
			console.log(error);
		},
	});
}

//移除收藏
$(document).on("click", ".remove_btn", function (e) {
	$(this).closest(".ticket_item_class").remove();
	//	var ticketItems = document.querySelectorAll(".ticket_item_class");
	var ticketId = $(this).closest(".ticket_item_class").find(".orderurl").attr("href").split("=")[1];

	// 連接後端刪除=========================================
	$.ajax({
		method: "POST",
		url: "/removeTicket",
		data: {
			memberId: memberId,
			ticketId: ticketId,
		},
		success: function (response) {
		},
		error: function (error) {
			console.error(error);
		},
	});
});
// ==========================右邊會員資料--旅遊團收藏 =====================================
// 處理第一個分頁內容跑到別的分頁
$(".nav-item")
	.eq(1)
	.on("click", function () {
		$("#group_order .ticket_item_class").remove();
	});
//=========================================
// 設定點擊旅遊團
$(".nav-item")
	.eq(1)
	.on("click", function () {
		$(".tab-pane").eq(1).addClass("show active");
		generateGroup();
	});
function generateGroup() {
	$.ajax({
		url: "/groups/" + memberId,
		method: "GET",
		dataType: "json",
		success: function (g_favorite) {
			if (!$(".tab-pane").eq(1).find("#group_orderselect").next().hasClass("group_item_class")) {
				for (let i = 0; i < g_favorite.length; i++) {
					$(".tab-pane").eq(1).find("#group_orderselect")
						.after(`<div class="group_item_class">
						<a href="/front-end/itinerary_page.html?tripId=${g_favorite[i].tripId}" class="orderurl">	                
                 <div class="item_img_class">
                 	<img src="${g_favorite[i].imgUrl}" class="item_img"> 
                 </div>
                   <div class="item_content">
                     <h1 class="item_title">${g_favorite[i].tripName}</h1>
                     <div class="box">
                         <p class="Number dynamic-text">${g_favorite[i].tripContent}</p>
                     </div>
                     <div id="allPrice2">
                         <p class="price">成人 TWD </p>
                         <p class="realPrice">${g_favorite[i].priceAdult}</p>
                     </div>
                     <div id="allPrice2">
                         <p class="price">小孩 TWD </p>
                         <p class="realPrice">${g_favorite[i].priceChild}</p>
                     </div>
                 </div>
				 </a>
               <div class="item_commend_class">
                  <i class="fa-solid fa-heart heart remove_btn2"></i>  
               </div>
             </div>
                `);
					//移除收藏
					$(document).on("click", ".remove_btn2", function (e) {
						console.log("remove")
						$(this).closest(".group_item_class").remove();
						let tripId = g_favorite[i].tripId;
						console.log(tripId);
						// 連接後端刪除=========================================
						$.ajax({
							method: "POST",
							url: "/removeTrip",
							data: {
								memberId: memberId,
								tripId: tripId,
							},
							success: function (response) {
								console.log("成功移除")
							},
							error: function (error) {
								console.error(error);
							},
						});
					});
				}
			}
		},
		error: function (error) {
			console.log(error);
		},
	});
}


// ==========================右邊會員資料--AI收藏 =====================================
// 處理第一個分頁內容跑到別的分頁
$(".nav-item")
	.eq(2)
	.on("click", function () {
		$("#AI_order .ticket_item_class").remove();
	});
// 設定點擊AI行程規劃
$(".nav-item")
	.eq(2) // 選第四個
	.on("click", function () {
		$(".tab-pane").eq(3).addClass("show active"); // 顯示
		// 呼叫這個顯示行程卡片列表
		getAiFavorite();
	});

// 接收後台AI行程資料
function getAiFavorite() {
	$.ajax({
		url: "/aiFavorite",
		method: "GET",
		dataType: "json",
		success: function (aiFavorite) {
			console.log("接收資料");
			console.log(aiFavorite);
			if (
				!$(".tab-pane")
					.eq(2)
					.find("#group_orderselect")
					.next()
					.hasClass("group_order_item_class")
			) {
				for (let i = 0; i < aiFavorite.length; i++) {
					$(".tab-pane").eq(2).find("#group_orderselect")
						.after(`<div class="group_order_item_class">
            <div class="card-header">
              <div class="card-top">
                <h5 class="text-center">
                  <h5>
                    ${aiFavorite[i].destination}${aiFavorite[i].travelDays}日遊
                  </h5>
                </h5>
              </div>
            </div>
            <div class="card-body card-down">
              <p>
                <i class="fa-solid fa-person"></i>
                人數：${aiFavorite[i].people}
              </p>
              <p>
                <i class="fa-solid fa-dollar-sign"></i>
                預算範圍：${aiFavorite[i].budgetRange}
              </p>
              <p>
                <i class="fa-brands fa-fly"></i>
                旅遊風格：${aiFavorite[i].preferredStyle}
              </p>
              <p>
                <i class="fa-solid fa-location-dot"></i> 路線連結：<a
                  href="${aiFavorite[i].route}"
                  >${aiFavorite[i].destination}${aiFavorite[i].travelDays}日遊路線連結</a
                >
              </p>
              <p class="ai_description"><i class="fa-solid fa-file-lines"></i> 行程內容：<br />${aiFavorite[i].planningDescription}</p>
              <div class="item_commend_class">
                <i class="fa-solid fa-heart heart remove_btn4"></i>
              </div>
            </div>
            <div class="aiFavoriteId">
              ${aiFavorite[i].aiFavoriteId}
            </div>
          </div>`);
				}
			}
		},
		error: function (error) {
			console.log(error);
		},
	});
}

// 設定刪除按鈕
$(document).on("click", ".remove_btn4", function (e) {
	$(this).closest(".group_order_item_class").remove();
	var groupItems = document.querySelectorAll(".group_order_item_class");

	let aiFavoriteId = $(this)
		.closest(".group_order_item_class")
		.find(".aiFavoriteId")
		.text();
	console.log(aiFavoriteId);

	// 連接後端刪除=========================================
	$.ajax({
		type: "DELETE",
		url: "/aiFavorite/" + aiFavoriteId,
		success: function (response) {
			console.log(response);
		},
		error: function (error) {
			console.error(error);
		},
	});
	// 連接後端刪除=========================================


});


// 設定卡片展開與關閉
$(document).on("click", ".group_order_item_class", function () {
	var container = $(this);
	var content = container.find(".card-body");

	if (container.hasClass("expanded")) {
		// 容器已展開，收起内容並恢復原始高度
		container.removeClass("expanded");
		content.addClass("card-down");

		// 計算原始高度
		var originalHeight = container.data("originalHeight");

		// 恢復原始高度
		container.animate({ height: originalHeight }, 500);
	} else {
		// 容器未展開，展開内容並增加高度
		container.addClass("expanded");
		content.removeClass("card-down");

		// 計算完整高度
		var fullHeight = content.outerHeight() + 100;

		// 保存原始高度
		container.data("originalHeight", container.height());

		// 增加高度以適應內容
		container.animate({ height: fullHeight }, 500);
	}
});

// =====================================================================
