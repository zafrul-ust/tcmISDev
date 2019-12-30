function selectIG() {

  var igSelect = document.getElementById("igArray");
  var inventoryGroup = igSelect.value;

  if (window.parent.opener.updateInventoryGroup) {
     window.parent.opener.updateInventoryGroup(inventoryGroup);
  }

  // close the parent window (since this is a sub window of a sub window)
  window.parent.opener.close();
  window.close();
}