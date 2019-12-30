function validateSearchForm() {  
  if(!isInteger(document.genericForm.itemId.value.trim(), true)) {
    alert(messagesData.itemIdInteger);
    return false;
  }
  if(!isInteger(document.genericForm.receiptId.value.trim(), true)) {
    alert(messagesData.receiptIdInteger);
    return false;
  }
  if(!isInteger(document.genericForm.mrNumber.value.trim(), true)) {
    alert(messagesData.prNumberInteger);
    return false;
  }
  return true;
}

function validateReceive() {

  var okName = 'document.genericForm.ok';
  var qtyName = 'document.genericForm.quantity';
  var qtyRetName = 'document.genericForm.quantityReturned';
  var dorName = 'docuemtn.genericForm.dor';

  for (i=0;i<returnsBeanSize;i++) {
    okCheckbox = eval(okName + i);
    if (okCheckbox.checked) {
      qtyInputbox = eval(qtyName + i);
      qtyRetInputbox = eval(qtyRetName + i);
      dorInputbox = eval(dorName + i);
      if (!isInteger(qtyRetInputbox.value.trim(),true)) {
        alert(messagesData.qtyRetInteger);
        return false;
      }
      if (!checkdate(dorInputbox)) {
        alert(messagesData.dorDate);
        return false;
      }
      // check to see the Quantity Returned < Quantity
      if (parseInt(qtyRetInputbox.value.trim()) > parseInt(qtyInputbox.value)) {
        alert(messagesData.qtyReturnNotLess);
        return false;
      }
    }
  }

  return true;
}

function showVvHubBins(itemId,branchPlant,rowNumber)
{
  var loc = "/tcmIS/hub/showhubbin.do?branchPlant="+branchPlant+"&userAction=showBins&rowNumber="+rowNumber+"&itemId=";
  loc = loc + itemId;
  openWinGeneric(loc,"showVvHubBins","300","150","no","80","80");
}

function addOptionItem(rowNumber,value,text,selectedRow) {
     obj = document.getElementById("bin"+rowNumber+"")
     var index = obj.length;
     obj.options[index]=new Option(text,value);
     if (selectedRow)
     {
        obj.options[index].selected = true;
     }
}

