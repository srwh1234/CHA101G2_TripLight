// import { result } from "./ChatGPT_api.js";

let data = "";
// 取得目的地資料
// let destination = sessionStorage.getItem("destination");

function getArray() {
  $.ajax({
    url: "/getArray",
    method: "GET",
    dataType: "json",
    success: function (locations) {
      // 檢查是否符合預期
      if (locations.length === 0) {
        // 因為locations不為0, 所以直接沿用舊的
        // 未獲得參數，再次發送請求
        setTimeout(getArray, 1000); // 每隔 1 秒發送請求
      } else {
        // 資料處理  轉成 number
        data = locations.map((location) => {
          return {
            lat: Number(location.lat),
            lng: Number(location.lng),
          };
        });

        // 取得data資料
        console.log(data);
        window.initMap(data);
        // 可以在這裡更新前端畫面，顯示陣列資料
        // 呼叫顯示location_icon
        $("#location-icon").show();
      }
    },
    error: function (xhr, status, error) {
      // 發生錯誤
      console.error(error); // 印出錯誤訊息

      // 可以在這裡顯示錯誤訊息給使用者
    },
  });
}

// 初始呼叫
getArray();

// 呼叫另一個 獲得地點標題
getLocations();

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
function getLocations() {
  $.ajax({
    url: "/getLocations",
    method: "GET",
    dataType: "json",
    success: function (locations2) {
      if (locations2.length === 0) {
        // 因為locations不為0, 所以直接沿用舊的
        // 未獲得參數，再次發送請求
        setTimeout(getLocations, 1000); // 每隔 1 秒發送請求
      } else {
        locationTitle = locations2;

        // 取得locationTitle資料
        console.log(locationTitle);
      }
    },
    error: function (xhr, status, error) {
      // 發生錯誤
      console.error(error); // 印出錯誤訊息

      // 可以在這裡顯示錯誤訊息給使用者
    },
  });
}

let addresses = "";
$(".location-icon2").on("click", function (e) {
  e.preventDefault();
  $(this).css("animation-play-state", "paused");

  addresses = locationTitle; //TODO:要加冒號
  let url = "https://www.google.com/maps/dir/";


  for (var i = 0; i < addresses.length; i++) {
    url += encodeURIComponent(addresses[i]) + "/";
  }

  console.log(url);
  window.open(url, "_blank");
});
