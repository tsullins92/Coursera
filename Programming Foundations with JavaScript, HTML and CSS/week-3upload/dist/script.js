var image;
var greyImage;

function upload(){
  var canvas1 = document.getElementById("canvas1");
  var fileInput = document.getElementById("file_input1");
  image = new SimpleImage(fileInput);
  greyImage = new SimpleImage(fileInput);
  image.drawTo(canvas1);
}

function makeGrey(){
  for(var pix of greyImage.values()){
    var greyValue = (pix.getRed() + pix.getGreen() + pix.getBlue())/3;
    pix.setRed(greyValue);
    pix.setGreen(greyValue);
    pix.setBlue(greyValue);
  }
  var canvas1 = document.getElementById("canvas1");
  var canvas2 = document.getElementById("canvas2");
  greyImage.drawTo(canvas1);
  greyImage.drawTo(canvas2);
}

function revertImage(){
  var canvas1 = document.getElementById("canvas1");
  image.drawTo(canvas1);
}