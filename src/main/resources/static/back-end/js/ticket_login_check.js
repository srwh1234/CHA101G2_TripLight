
/**
 * 取得員工id 如果沒有就必須先進行登入
 * 
 * @returns 沒有資料回傳0 有資料回傳員工編號
 */
function getEmpolyeeId() {
    // const item = JSON.parse(sessionStorage.getItem('test-login'));
    // if (!item || !item.memberId) {
    //     $('#login-container').addClass('active-popup');
    //     return 0;
    // }
    // return item.memberId;
    return 1;
}

/**
 * 不填瀏覽器會自行補上目前位置的前面路徑
 * @returns 協定+網域名稱
 */
function getDomainName() {
    //return 'http://localhost:8080';
    return '';
}