var fgImage = null;
var bgImage = null;
var compositeImage = null;

function uploadFG(){
  var canvas1 = document.getElementById("canvas1");
  var fileInput = document.getElementById("fg-file_input");
  fgImage = new SimpleImage(fileInput);
  fgImage.drawTo(canvas1);
}

function uploadBG(){
  var canvas2 = document.getElementById("canvas2");
  var fileInput = document.getElementById("bg-file_input");
  bgImage = new SimpleImage(fileInput);
  bgImage.drawTo(canvas2);
}

function clearCanvases(){
  var canvas1 = document.getElementById("canvas1");
  var canvas2 = document.getElementById("canvas2");
  var ctx1 = canvas1.getContext("2d");
  var ctx2 = canvas2.getContext("2d");
  ctx1.clearRect(0,0,canvas1.width,canvas1.height);
  ctx2.clearRect(0,0,canvas2.width,canvas2.height);
}

function createComposite(){
  if(fgImage == null || !fgImage.complete()){
    alert("Foreground not loaded");
    return;
  } if(bgImage == null || !bgImage.complete()){
    alert("Background not loaded");
    return;
  } 
  clearCanvases();
  compositeImage = new SimpleImage(fgImage.getWidth(),fgImage.getHeight());
  for(var pixel of fgImage.values()){
    var x = pixel.getX();
    var y = pixel.getY();
    var greenThreshold = pixel.getRed() + pixel.getBlue();
    if(pixel.getGreen() > greenThreshold){
      var bgPixel = bgImage.getPixel(x,y);
      compositeImage.setPixel(x,y,bgPixel);
    } else{
      compositeImage.setPixel(x,y,pixel);
    }
  }
  var canvas1 = document.getElementById("canvas1");
  compositeImage.drawTo(canvas1);
}