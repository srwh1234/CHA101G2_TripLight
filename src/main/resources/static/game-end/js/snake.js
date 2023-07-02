let snakeArray = []; /*初始化蛇关节的数组*/
let isPause = false; /*游戏是否暂停：未暂停*/
let snakeSize = 5; /*蛇的初始长度*/
let direct = "right"; /*蛇初始方向：向右*/
let user = JSON.parse(sessionStorage.getItem("user"));
let speed = 70 + user.level; /*蛇移动初始速度：35+等級*/ // 數字越低越快
let score, timer, board, bean; /*游戏初始分数显示区，定时器，面板，豆*/
let trueScore;
let snakeheadColor = "red";
// 如果等級大於10等，可使用技能
let isShiftPressed = true;
if (user.level >= 10) {
  isShiftPressed = false;
}

// 設定蛇頭顏色
let colorPicker = document.getElementById("colorPicker");
let skinPicker = document.getElementById("skinPicker");

// 如果等級大於15等，可以改顏色
if (user.level < 15) {
  colorPicker.style.display = "none";
  colorPicker.parentNode.style.display = "none";
}
// 如果等級大於20等，可以改skin
if (user.level < 20) {
  skinPicker.style.display = "none";
  skinPicker.parentNode.style.display = "none";
}

//獲取音樂
let eatBeanAudio = document.getElementById("eatBean");
let slowDownAudio = document.getElementById("slowDown");

// 獲取按鈕
let pikachuButton = document.getElementById("pikachuButton");

// 初始化(){
onload = () => {
  // 真實數字，避免被竄改
  trueScore = 0;
  //     初始化游戏面板和游戏分数显示区
  board = document.querySelector("#board");
  score = document.querySelector("#score");
  //   畫面置中
  board.scrollIntoView({ behavior: "smooth", block: "center" });
  //     造蛇()
  createSnake();
  //     造豆()
  createBean();
  //     监听键盘()
  keyListener();
};

// 造蛇(){
function createSnake() {
  //     循环蛇初始化长度次{
  for (let i = 0; i < snakeSize; i++) {
    //         创造蛇的新关节，每个关节都是一个div
    let snake = document.createElement("div");
    // 蛇头变红
    if (i === 0) {
      // 預設skin
      snake.style.backgroundColor = snakeheadColor;

      // 檢查紀錄的skin
      // 检查本地存储中是否存在之前的选择
      if (localStorage.getItem("selectedSkin")) {
        let previousSkin = localStorage.getItem("selectedSkin");
        skinPicker.value = previousSkin;
        snake.style.backgroundImage = `url('img/${previousSkin}.png')`;
        snake.style.backgroundSize = "cover";
        snake.style.backgroundColor = "transparent";
      }
      // 檢查紀錄的color
      if (localStorage.getItem("selectedColor")) {
        let previousColor = localStorage.getItem("selectedColor");
        snake.style.backgroundColor = previousColor;
        snake.style.backgroundImage = "";
      }

      // 監聽color
      colorPicker.addEventListener("change", function () {
        let selectedColor = colorPicker.value;
        snake.style.backgroundColor = selectedColor;
        snake.style.backgroundImage = "";
        console.log("Selected color: " + selectedColor);

        // 儲存至本地
        localStorage.removeItem("selectedSkin");
        localStorage.setItem("selectedColor", selectedColor);
      });

      // 監聽skin
      skinPicker.addEventListener("change", function () {
        let selectedSkin = skinPicker.value;
        snake.style.backgroundImage = `url('img/${selectedSkin}.png')`;
        snake.style.backgroundSize = "cover";
        snake.style.backgroundColor = "transparent";

        // 将当前选择保存到本地存储中
        localStorage.setItem("selectedSkin", selectedSkin);
        localStorage.removeItem("selectedColor");
      });
    }
    //         蛇的新关节推入数组
    snakeArray.push(snake);
    //         蛇的新关节的左距离为上一个蛇关节左侧
    snake["style"]["left"] = (snakeSize - i - 1) * 20 + "px";
    //         蛇的新关节展示在面板上
    board.appendChild(snake);
  }
}

// 造豆(){
function createBean() {
  //     if(存在旧豆){
  if (bean) {
    //     从游戏面板上删除旧豆
    board.removeChild(bean);
  }
  //     创建新豆，每个豆都是一个span
  bean = document.createElement("span");
  let x = null,
    y = null;
  //     调用随机坐标()，为新豆生成出生坐标
  randomXY();

  //     随机坐标(){
  function randomXY() {
    //         面板宽度1000除以20（豆子宽20px），等分成500份
    //         乘以一个随机数并取整，得出一个0-500的整数
    //         乘以20得到一个0-1000范围内的20的整数倍，即横坐标
    //         纵坐标同理
    x = parseInt("" + Math.random() * (1000 / 20)) * 20;
    y = parseInt("" + Math.random() * (500 / 20)) * 20;

    //         遍历蛇关节数组{
    for (let i = 0; i < snakeArray.length; i++) {
      //             if(和当前豆的坐标冲突){
      if (snakeArray[i]["offsetLeft"] === x) {
        if (snakeArray[i]["offsetTop"] === y) {
          //                 随机坐标();
          randomXY();
          break;
        }
      }
    }
  }

  //     为新豆赋值横纵坐标
  bean["style"]["left"] = x + "px";
  bean["style"]["top"] = y + "px";
  //     将新豆追加到面板中
  board.appendChild(bean);
}

// 添加一個布爾變量（例如canChangeDirection）來標識是否可以改變方向。當蛇的方向改變時，將其設置為false，並在蛇進行下一次移動之前將其設置回true。
let canChangeDirection = true;
function keyListener() {
  document.onkeydown = (event) => {
    let oEvent = event || window.event;
    switch (oEvent.keyCode) {
      case 65:
      case 74:
        // 按下 "a" 鍵或 "j" 鍵：當方向不為右，方向改為左
        if (direct !== "right" && canChangeDirection) {
          direct = "left";
          canChangeDirection = false;
        }
        break;
      case 87:
      case 73:
        // 按下 "w" 鍵或 "i" 鍵：當方向不為下，方向改為上
        if (direct !== "down" && canChangeDirection) {
          direct = "up";
          canChangeDirection = false;
        }
        break;
      case 68:
      case 76:
        // 按下 "d" 鍵或 "l" 鍵：當方向不為左，方向改為右
        if (direct !== "left" && canChangeDirection) {
          direct = "right";
          canChangeDirection = false;
        }
        break;
      case 83:
      case 75:
        // 按下 "s" 鍵或 "k" 鍵：當方向不為上，方向改為下
        if (direct !== "up" && canChangeDirection) {
          direct = "down";
          canChangeDirection = false;
        }
        break;
      case 32:
        // 按了空格鍵：暫停和開始遊戲效果切換
        if (!isPause) {
          pause();
        } else {
          start();
        }
        isPause = !isPause;
        break;
      case 16:
        // 按了 Shift 鍵：速度下降 2 秒，然後回到原本的速度
        changeSpeed();
        break;
    }
  };
}

// 游戏开始(){
function start() {
  // 判斷還有沒有錢
  if (user.money < 50) {
    Swal.fire({
      title: "You don't have enough money",
      timer: 1500,
      background: "rgba(255, 255, 255, .7)",
    }).then(() => {
      setTimeout(() => {
        // 過一段時間自動刷新頁面
        location.reload();
      }, 500);
    });
  }

  // 清除舊定時器
  clearInterval(timer);
  // 開啟新定時器
  timer = setInterval(gameLoop, speed);
}

function gameLoop() {
  // 蛇的移動邏輯
  move();
  // 撞到自己的檢查邏輯

  // 設定條件 等級大等於20開啟自我碰撞
  if (user.level >= 20) {
    isHit();
  }
  // 吃到豆子的檢查邏輯
  isEat();

  // 判斷是否直接往回走的邏輯
  canChangeDirection = true;
}

// 在需要的時候更改速度
function changeSpeed() {
  if (!isShiftPressed) {
    playslowDownMusic();
    isShiftPressed = true;
    clearInterval(timer);
    speed += 200;
    timer = setInterval(gameLoop, speed);
    setTimeout(() => {
      clearInterval(timer);
      speed -= 200;
      timer = setInterval(gameLoop, speed);
      isShiftPressed = false;
    }, 2000);
  }
}

// 蛇移动(){
function move() {
  //     获取蛇头左距离和上距离
  let hLeft = snakeArray[0].offsetLeft;
  let hTop = snakeArray[0].offsetTop;
  //     判断当前蛇的移动方向{
  switch (direct) {
    case "left":
      //         if(对应方向上出界){
      if (hLeft <= 0) {
        //             游戏结束()
        gameover();
        return;
      }
      //         蛇身移动()
      snakeBodyMove();
      //         蛇头移动
      snakeArray[0]["style"]["left"] = hLeft - 20 + "px";
      break;
    case "up":
      if (hTop <= 0) {
        gameover();
        return;
      }
      snakeBodyMove();
      snakeArray[0]["style"]["top"] = hTop - 20 + "px";
      break;
    case "right":
      if (hLeft >= 1000 - 20) {
        gameover();
        return;
      }
      snakeBodyMove();
      snakeArray[0]["style"]["left"] = hLeft + 20 + "px";
      break;
    case "down":
      if (hTop >= 500 - 20) {
        gameover();
        return;
      }
      snakeBodyMove();
      snakeArray[0]["style"]["top"] = hTop + 20 + "px";
      break;
  }

  //     蛇身移动(){
  function snakeBodyMove() {
    //         循环所有蛇身{
    for (let i = snakeArray.length - 1; i > 0; i--) {
      //             后面的关节横向顶替前面的关节
      snakeArray[i]["style"]["left"] = snakeArray[i - 1]["style"]["left"];
      //             后面的关节纵向顶替前面的关节
      snakeArray[i]["style"]["top"] = snakeArray[i - 1]["style"]["top"];
    }
  }
}

/*判断本次移动是否撞到自己*/
function isHit() {
  //     遍历所有蛇身{
  for (let i = 1, j = snakeArray.length; i < j; i++) {
    //         if(蛇头坐标与某个蛇身关节坐标冲突){
    if (snakeArray[0].offsetLeft === snakeArray[i].offsetLeft) {
      if (snakeArray[0].offsetTop === snakeArray[i].offsetTop) {
        //             结束游戏()
        gameover();
        break;
      }
    }
  }
}

// 吃豆子(){
function isEat() {
  //     if(蛇头坐标和当前豆的坐标一致){
  if (snakeArray[0].offsetLeft === bean.offsetLeft) {
    if (snakeArray[0].offsetTop === bean.offsetTop) {
      //         分数++
      trueScore++;
      score.innerText = parseInt(score.innerText) + 1;
      //         速度++
      clearInterval(timer);
      speed -= 2;
      timer = setInterval(gameLoop, speed);
      // 音效
      playEatBeanMusic();
      //         创建一个新的蛇关节
      let snake = document.createElement("div");
      //         新蛇关节的出生坐标就是被吃掉豆子的坐标
      snake["style"]["left"] = bean["style"]["left"];
      snake["style"]["top"] = bean["style"]["top"];
      //         新蛇关节加入到蛇的数组中
      snakeArray.push(snake);
      //         新蛇关节展示在游戏面板中
      board.appendChild(snake);
      //         造豆()
      createBean();
    }
  }
}

// 游戏结束(){
function gameover() {
  // 清空定時器
  clearInterval(timer);
  saveScore();

  Swal.fire({
    title: `Game Over
Your Score is ${trueScore}`,
    timer: 3000,
    background: "rgba(255, 255, 255, .7)",
  }).then(() => {
    setTimeout(() => {
      location.reload();
    }, 500);
  });
}

// 游戏暂停(){
function pause() {
  //     清空定时器
  clearInterval(timer);
}

// 游戏重置(){
function reset() {
  //     刷新页面
  location.reload();
}

// 紀錄分數
function saveScore() {
  // 將 user 由json 轉物件
  let user = JSON.parse(sessionStorage.getItem("user"));
  let thisScore = parseInt(score.innerText);

  if (user.maxScore < trueScore) {
    user.maxScore = trueScore;
  }
  user.money += trueScore * 20 - 50; // 增加money
  // 將 user 由物件 轉json
  let userString = JSON.stringify(user);
  sessionStorage.setItem("user", userString); // 將使用者資訊存入sessionStorage

  $.ajax({
    url: "/users/score",
    method: "PUT",
    data: { score: trueScore },
    success: function (response) {
      console.log(response);
    },
  });
}

// 播放音乐
function playEatBeanMusic() {
  eatBeanAudio.play();
}
function playslowDownMusic() {
  slowDownAudio.play();
}

// 自訂頭像
function pikachu(snake) {
  snake.style.backgroundImage = "url('img/pikachu.png')";
  snake.style.backgroundSize = "cover";
  snake.style.backgroundColor = "transparent";
}
