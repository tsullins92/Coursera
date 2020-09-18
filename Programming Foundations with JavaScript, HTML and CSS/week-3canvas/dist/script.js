function doLime(){
  var canvas = document.getElementById("canvas1");
  canvas.style.backgroundColor = "lime";
  var ctx = canvas.getContext("2d");
  ctx.clearRect(0,0, canvas.width, canvas.height);
}
function doYellow(){
  var canvas = document.getElementById("canvas1");
  canvas.style.backgroundColor = "white";
  var ctx = canvas.getContext("2d");
  ctx.fillStyle = "yellow";
  ctx.fillRect(10,10,40,40);
  ctx.fillRect(60,10,40,40);
  ctx.fillStyle = "black";
  ctx.font = "30px Arial";
  ctx.fillText("Hello",10,80);
}

function doColor(){
  var canvas1 = document.getElementById("canvas1");
  var colorInput = document.getElementById("color_input1");
  var color = colorInput.value;
  canvas1.style.backgroundColor = color;
}

function doSquare(){
  var canvas1 = document.getElementById("canvas1");
  var sizeInput = document.getElementById("range_input1");
  var size = sizeInput.value;
  var ctx = canvas1.getContext("2d");
  ctx.fillStyle = "yellow";
  ctx.clearRect(0,0, canvas1.width, canvas1.height);
  var sq1X = 10;
  var sq1Y = 10;
  ctx.fillRect(10,10,size,size);
  ctx.fillRect(sq1X+100,sq1Y+100,size,size);
}