String.prototype.trim = function()
{
// skip leading and trailing whitespace
// and return everything in between
  return this.replace(/^\s*(\b.*\b|)\s*$/, "$1");
}

function changeAllLabelQuantity(rowCount) {
 var allLabelQuantity = document.getElementById("allLabelQuantity").value;
 var rowCount = document.getElementById("Total").value;
 if (allLabelQuantity.trim().length > 0)
 {
  for(var i=0; i<rowCount; i++) {
    var noOfLabels = document.getElementById("nooflabels" + (i+1));
    noOfLabels.value = allLabelQuantity;
  }
 }
}
