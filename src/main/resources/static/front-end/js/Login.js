const container = document.querySelector(" #login-container ");
const loginLink = document.querySelector(".login-link");
const registerLink = document.querySelector(".register-link");
const loginbtn = document.getElementById("login");
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

// 取得按鈕
const the_main_btn = document.querySelector('.main-btn');
//按下login
the_main_btn.addEventListener('click', function () {
  //取得值 (幫他加一個id應該更好取得)
  const valid = {
    account: document.querySelector("#email").value,
    password: document.querySelector("#password").value,
  }

  //先暫存
  sessionStorage.setItem('test-login', JSON.stringify(valid));
  loginbtn.innerHTML = ` <i class="fa-regular fa-lightbulb" style="color: #dcdfe5;"></i>`;

})


window.addEventListener('load', function () {
  const valid = JSON.parse(sessionStorage.getItem('test-login'));
  if (valid) {
    document.querySelector('#login').innerHTML = ` <i class="fa-regular fa-lightbulb" style="color: #dcdfe5;"></i>`;

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
// 右上導覽列訂單管理
$("#order_li").on("mouseover", function () {
  $(".order_list").addClass("-on");
  $(".order_content").addClass("-on");

});
$("#order_li").on("mouseout", function () {
  $(".order_list").removeClass("-on");
  $(".order_content").removeClass("-on");
})

// 左列會員訂單管理

$("#order_li2 a").on("click", function (e) {
  e.preventDefault();
});
$("#order_li2").on("mouseover", function () {
  $("#order_list2, #order_content1, #order_content2").addClass("-on");
});
$("#order_li2").on("mouseout", function () {
  $("#order_list2, #order_content1, #order_content2").removeClass("-on");
})

