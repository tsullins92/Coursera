var image = null;
var windowImage = null;
var gsImage = null;
var redImage = null;
var canvas = document.getElementById("canvas1");

function uploadImage(){
  var fileInput = document.getElementById("file_input");
  image = new SimpleImage(fileInput); 
  windowImage = new SimpleImage(fileInput); 
  gsImage = new SimpleImage(fileInput); 
  redImage = new SimpleImage(fileInput); 
  image.drawTo(canvas);
}

function resetImage(){
  if(imageIsLoaded(image)){
    uploadImage();
  }
}

function doGreyscale(){
  if(imageIsLoaded(gsImage)){
    filterGrey();
    gsImage.drawTo(canvas);
  } 
}

function filterGrey(){
  for(var pixel of gsImage.values()){
    var greyValue = (pixel.getRed()+pixel.getGreen()+pixel.getBlue())/3;
    pixel.setRed(greyValue);
    pixel.setGreen(greyValue);
    pixel.setBlue(greyValue);
  }
}

function doRed(){
  if(imageIsLoaded(redImage)){
    filterRed();
    redImage.drawTo(canvas);
  } 
}

function filterRed(){
  for(var pixel of redImage.values()){
    var avg = (pixel.getRed()+pixel.getGreen()+pixel.getBlue())/3;
    if(avg < 128){
      pixel.setRed(2*avg);
      pixel.setGreen(0);
      pixel.setBlue(0);
    } else{
      pixel.setRed(255);
      pixel.setGreen((2*avg)-255);
      pixel.setBlue((2*avg)-255);
    }
  }
}

function doWindow(){
  if(imageIsLoaded(windowImage)){
    filterWindow();
    windowImage.drawTo(canvas);
  } 
}

function filterWindow(){
  var sqWidth = windowImage.width/5;
  var sqHeight = windowImage.height/2;
  var thickness = 2;
  for(var pixel of windowImage.values()){
    var x = pixel.getX();
    var y = pixel.getY();
    if(x<thickness*2||
       ((x>=sqWidth-thickness)&&(x<sqWidth+thickness))||
       ((x>=sqWidth*2-thickness)&&(x<sqWidth*2+thickness))||
       ((x>=sqWidth*3-thickness)&&(x<sqWidth*3+thickness))||
       ((x>=sqWidth*4-thickness)&&(x<sqWidth*4+thickness))||
       ((x>=sqWidth*5-thickness*2)&&(x<sqWidth*5))){
      pixel.setRed(0);
      pixel.setGreen(0);
      pixel.setBlue(0);
    } else if(y<thickness*2||
       ((y>=sqHeight-thickness)&&(y<sqHeight+thickness))||
       ((y>=sqHeight*2-thickness*2)&&(y<sqHeight*2))){
      pixel.setRed(0);
      pixel.setGreen(0);
      pixel.setBlue(0);
    } 
  }
}

function imageIsLoaded(testImage){
  if(testImage == null || !testImage.complete()){
    alert("Image not loaded");
    return false;
  }
  return true;
}