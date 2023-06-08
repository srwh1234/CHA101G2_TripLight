
$(document).ready(function () {
    // 監聽超級管理員連結的點擊事件
    $('#userDropdown').on('click', function () {
        // 在超級管理員連結的下一個 sibling 元素中添加登出選單
        $(this).next('.dropdown-menu').html(`
<a class="dropdown-item" href="#" id="logoutLink">
登出
</a>
`);
    });

    // 監聽登出連結的點擊事件
    $(document).on('click', '#logoutLink', function () {
        // 執行登出相關操作

        // 跳轉到 login.html 頁面
        window.location.href = 'login.html';
    });
});

