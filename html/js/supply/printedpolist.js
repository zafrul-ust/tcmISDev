function checkSearchPo() 
{
   var searchPo = document.getElementById("searchText");
   if ( !(isInteger(searchPo.value.trim()))) {
      alert(messagesData.enterSearchArgument);
      return false;
   }else {
      return true;
   }
}


function submitSearchForm()
{
 var isValidSearchForm = checkSearchPo();
  if(isValidSearchForm) 
  {
   parent.showPleaseWait();
   return true;
  }
  else
  {
    return false;
  }
}

function showErrorMessages()
{
  parent.showErrorMessages();
}
