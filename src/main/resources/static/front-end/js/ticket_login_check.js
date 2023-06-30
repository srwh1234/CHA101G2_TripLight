
/**
 * 取得會員id 如果沒有就必須先進行登入
 * 
 * @returns 沒有資料回傳0 有資料回傳會員編號
 */
function getMemberId(show = true) {

    if (isLiveServer()) {
        return 1;
    }

    const item = JSON.parse(sessionStorage.getItem('test-login'));
    if (!item || !item.memberId) {
        if (show) {
            Swal.fire({ icon: 'info', title: '請登入會員', });
        }
        return 0;
    }
    return item.memberId;
}

/**
 * 不填瀏覽器會自行補上目前位置的前面路徑
 * @returns 協定+網域名稱
 */
function getDomainName() {
    if (isLiveServer()) {
        return 'http://localhost:8080';
    }   
    return '';
}

/**
 * 測試用
 */
function isLiveServer() {
    return window.location.origin === 'http://127.0.0.1:5500';
}