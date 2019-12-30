function commentsChanged(rowNumber) {
     changed = document.getElementById("changed"+rowNumber+"");
     changed.value = "yes";
}

function submitUpdate() {
 var update = document.getElementById("update");
 update.value = 'update';
 submitOnlyOnce();
 document.genericForm.submit();
}

function validateSearchForm() {
 var itemId = document.getElementById("itemId");
 if (!isInteger(itemId.value.trim())) {
    alert(messagesData.itemInteger);
    return false;
 }
 return true;
}
