let data = "";

// 取得目的地資料
// let destination = sessionStorage.getItem("destination");

let request = null; // 声明一个全局变量用于存储定时器的 ID

function getArray() {
  $.ajax({
    url: "/longitude/" + memberId,
    method: "GET",
    dataType: "json",
    success: function (locations) {
      // locations為收到的緯度資料陣列
      console.log("發起請求：請求緯度資料");
      // 遍歷locations 並將字串轉為number 生成新陣列data
      console.log(locations);
      const data = locations.map((location) => {
        return {
          lat: location.latitude,
          lng: location.longitude,
        };
      });

      sessionStorage.setItem("LatitudeArr", data);
      console.log(data);
      window.initMap(data);
      // 可以在這裡更新前端畫面，顯示陣列資料
      // 呼叫顯示location_icon
      $("#location-icon").show();
    },
    error: function (xhr, status, error) {
      console.error(error);

      // 可以在這裡顯示錯誤訊息給使用者
    },
  });
}
// 初始化地圖

try {
  function initMap(data) {
    // 初始化地圖：台北經緯度
    const map = new google.maps.Map(document.getElementById("map"), {
      zoom: 8,
      center: { lat: 25.033968, lng: 121.564468 },
    });

    // Get driving directions from Taipei to Kaohsiung via Tainan
    const directionsService = new google.maps.DirectionsService();
    const directionsRenderer = new google.maps.DirectionsRenderer();
    directionsRenderer.setMap(map);

    // let locations=[];

    // 交通方式:設定走路，設定行車有些地方會去不了
    let travelMode = "WALKING";
    // 加入地點
    // const locations = data;
    const waypoints = [];
    // 第一個或最後一個不要加入
    for (let i = 1; i < data.length - 1; i++) {
      waypoints.push({
        location: data[i],
        stopover: true,
      });
    }
    // 設定起始點與目的地
    const request = {
      origin: data[0], //
      destination: data[data.length - 1],
      // 交通方式
      travelMode: google.maps.TravelMode[travelMode],
      // 中途經過地點
      waypoints,
    };

    directionsService.route(request, (result, status) => {
      if (status === google.maps.DirectionsStatus.OK) {
        directionsRenderer.setDirections(result);
      }
    });
  }

  window.initMap = initMap;
} catch (error) {}

//TODO:  連接googlemap地圖 功能

let locationTitle = "";
let url = "https://www.google.com/maps/dir/";
function getLocations() {
  $.ajax({
    url: "/locations/" + memberId,
    method: "GET",
    dataType: "json",
    success: function (locations2) {
      locationTitle = locations2;
      // 取得locationTitle資料
      console.log(locationTitle);

      sessionStorage.setItem("locationArray", locationTitle);

      // 在這裡放入url
      for (let i = 0; i < locationTitle.length; i++) {
        url += encodeURIComponent(locationTitle[i]) + "/";
      }
      sessionStorage.setItem("url", url);
      console.log(url);
    },
    error: function (xhr, status, error) {
      // 發生錯誤
      console.error(error); // 印出錯誤訊息

      // 可以在這裡顯示錯誤訊息給使用者
    },
  });
}

// 點擊地圖icon
$(".location-icon2").on("click", function (e) {
  e.preventDefault();
  $(this).css("animation-play-state", "paused");
  window.open(url, "_blank");
});
