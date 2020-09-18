function changeColor(){
  var tDiv1 = document.getElementById("text_div1");
  var tDiv2 = document.getElementById("text_div2");
  tDiv1.className = "azure_back";
  tDiv2.className = "ablue_back";
}

function changeText(){
  var tDiv1 = document.getElementById("text_div1");
  var tDiv2 = document.getElementById("text_div2");
  tDiv1.innerHTML = "<p>Changed Text1</p>";
  tDiv2.innerHTML = "<p>Changed Text2</p>";
}