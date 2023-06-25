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
		$.ajax({
			url: "/uploadImage/" + theId,
			method: "POST",
			data: {
				memberPic: memberPic,
			},
			success: (response) => {
				console.log("儲存成功")
			}, error: (error) => {
				console.log(error);
			}
		})
	});
	input.click();
});
//顯示大頭照
$(document).ready(function () {
	$.ajax({
		url: "/getPic/" + theId,
		method: "GET",
		dataType: "text",
		success: function (response) {
			let memberPic = response;
			$('.rounded-circle').attr('src', memberPic);

		},
		error: function (error) {
			console.log(error);
			console.log(error.response);

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
						<a href="http://localhost:8080/front-end/tickets_detail.html?id=${t_favorite[i].ticketId}" class="orderurl">                 
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
//移除背景圖
if ($(".tab-pane").eq(0).find("#orderselect").find(".ticket_item_class")) {
	$(".no_comment_div").first().addClass("-out");
} else {
	$(".no_comment_div").first().removeClass("-out");
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
				<a href="http://localhost:8080/front-end/tickets_detail.html?id=${g_favorite[i].tripId}" class="orderurl"> 	                
                 <div class="item_img_class">
                 	<img src="${g_favorite[i].imgUrl}" class="item_img"> 
                 </div>
                   <div class="item_content">
                     <h1 class="item_title">${g_favorite[i].tripName}</h1>
                     <div class="box">
                         <p class="Number dynamic-text">${g_favorite[i].tripDescription}</p>
                     </div>
                     <div id="allPrice">
                         <p class="price">成人 TWD </p>
                         <p class="realPrice">${g_favorite[i].priceAdult}</p>
                     </div>
                     <div id="allPrice">
                         <p class="price">小孩 TWD </p>
                         <p class="realPrice">${g_favorite[i].priceChild}</p>
                     </div>
                 </div>
               </a>
               <div class="item_commend_class">
                  <i class="fa-solid fa-heart heart remove_btn2"></i>  
               </div>
             </div>
                `
						);
					let tripId = g_favorite[i].tripId;
					console.log(tripId);
					sessionStorage.setItem("favoriteTrip", tripId)
				}
			}
		},
		error: function (error) {
			console.log(error);
		},
	});
}
//移除背景圖
if ($(".tab-pane").eq(1).find("#group_orderselect").find(".group_item_class")) {
	$(".no_comment_div").eq(1).addClass("-out");
} else {
	$(".no_comment_div").eq(1).removeClass("-out");
}


//移除收藏
//沒有tripId
$(document).on("click", ".remove_btn2", function (e) {
	console.log("remove")
	$(this).closest(".group_item_class").remove();
	//	var groupItems = document.querySelectorAll(".group_item_class");
	//var tripId = $(this).closest(".group_item_class").find(".orderurl").attr("href").split("=")[1];
	let tripId = $(this).closest(".group_item_class").data("tripid");
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
		},
		error: function (error) {
			console.error(error);
		},
	});
});

// //移除背景圖
// if (Object.keys(g_dataObj).length !== 0) {
// 	$(".no_comment_div").eq(1).toggleClass("-out");
// }
// //移除收藏
// $(document).on("click", ".remove_btn", function(e) {
// 	$(this).closest(".group_item_class").remove();
// 	var groupItems = document.querySelectorAll(".group_item_class");
// 	if (groupItems.length !== 0) {
// 		$(".no_comment_div").eq(1).toggleClass("-out", true);
// 	} else {
// 		$(".no_comment_div").eq(1).toggleClass("-out", false);
// 	}
// });
// ==========================右邊會員資料--文章收藏 =====================================
// 處理第一個分頁內容跑到別的分頁
$(".nav-item")
	.eq(2)
	.on("click", function () {
		$("#article_order .ticket_item_class").remove();
	});

const a_dataObj = {};
const a_valid = [
	{
		url: "http://google.com.tw",
		imgUrl:
			"https://image.kkday.com/v2/image/get/h_650%2Cc_fit/s1.kkday.com/product_115643/20230515091041_Tx0bo/jpg",
		title: "文章文章",
		summary:
			"3333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333",
		realPrice: "2020 / 02 / 02",
	},
];
// 填入假資料
for (let i = 0; i < a_valid.length; i++) {
	Object.assign(a_dataObj, a_valid[i]);
	$("#article_orderselectdiv").append(generateArticle(a_valid[i]));
}
function generateArticle(a_favorite) {
	return `
  <div class="article_item_class">
    <a href="${a_favorite.url}" , class="orderurl">
      <div class="item_img_class">
          <img src="${a_favorite.imgUrl}"class="item_img">
      </div>
      <div class="item_content">
        <h1 class="item_title">${a_favorite.title}</h1>
        <div class="box">
            <p class="Number">${a_favorite.summary} </p>
        </div>
        <div>
            <p class="realPrice">${a_favorite.realPrice}</p>
        </div>
      </div>
    </a>
      <div class="item_commend_class">
        <i class="fa-solid fa-heart heart remove_btn"></i>  
    </div>
  </div>
   `;
}
//移除背景圖

if (Object.keys(a_dataObj).length !== 0) {
	$(".no_comment_div").eq(2).toggleClass("-out");
}
// //移除收藏
$(document).on("click", ".remove_btn", function (e) {
	$(this).closest(".article_item_class").remove();

	var articleItems = document.querySelectorAll(".article_item_class");
	if (articleItems.length !== 0) {
		$(".no_comment_div").eq(2).toggleClass("-out", true);
	} else {
		$(".no_comment_div").eq(2).toggleClass("-out", false);
	}
});

// =================================================================
// 處理第一個分頁內容跑到別的分頁
$(".nav-item")
	.eq(3)
	.on("click", function () {
		$("#AI_order .ticket_item_class").remove();
	});
// 設定點擊AI行程規劃
$(".nav-item")
	.eq(3) // 選第四個
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
					.eq(3)
					.find("#group_orderselect")
					.next()
					.hasClass("group_order_item_class")
			) {
				for (let i = 0; i < aiFavorite.length; i++) {
					$(".tab-pane").eq(3).find("#group_orderselect")
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
                <i class="fa-solid fa-heart heart remove_btn"></i>
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
$(document).on("click", ".remove_btn", function (e) {
	$(this).closest(".group_order_item_class").remove();
	var groupItems = document.querySelectorAll(".group_order_item_class");

	let aiFavoriteId = $(this)
		.closest(".group_order_item_class")
		.find(".aiFavoriteId")
		.text();
	console.log(aiFavoriteId);

	// 連接後端刪除=========================================
	//	$.ajax({
	//		type: "DELETE",
	//		url: "/aiFavorite/" + aiFavoriteId,
	//		success: function(response) {
	//			console.log(response);
	//		},
	//		error: function(error) {
	//			console.error(error);
	//		},
	//	});
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
//背景圖
var GOItems = document.querySelectorAll(".group_order_item_class");
if (GOItems.length !== 0) {
	$(".no_comment_div").eq(3).toggleClass("-out", true);
} else {
	$(".no_comment_div").eq(3).toggleClass("-out", false);
}
// =====================================================================
