
function checkAll(rowCount) {
  var allCheck = document.getElementById("allCheck");
  var check;
  var value;
  if(!allCheck.checked) {
    check = false;
  }
  else {
    check = true;
  }
  for(var i=0; i<rowCount; i++) {
    var checkbox = document.getElementById("checkbox" + i);
    checkbox.checked = check;
    checkbox.value = value;
  }
}
