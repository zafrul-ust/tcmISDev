// This works only for popup windows and IE. Close the window after clicking Esc key
var windowCloseOnEsc = true; 

function submitSearchForm()
{
 /*Make sure to not set the target of the form to anything other than resultFrame*/
  var now = new Date();
  document.getElementById("startSearchTime").value = now.getTime();

  if(validateSearchForm()) { 
   $('uAction').value = 'search';
   document.genericForm.target='resultFrame';
   showPleaseWait();
   return true;
  }
  else
  {
    return false;
  }
}


  function validateSearchForm() {
	if ($v("searchArgument").length == 0) {
		alert(messagesData.missingSearchArgument);
		return false;
	}
	if($v("searchArgument").length > 0 && $v("searchField") == "itemId" && !isInteger($v("searchArgument"),true)) {
		alert(messagesData.itemInteger);
		return false;
	}
	return true;
}
  
  function addItemSynonym(){
		var loc = "newitemsynonym.do?uAction=new" +
		"&catalog_company_id=" + $v("catalogCompanyId")+
		"&catalog_id=" + $v("catalogid");
		var winId= 'newitemsynonym';
		 openWinGeneric(loc,winId.replace('.','a'),"600","250","yes","50","50","20","20","no");
		return ;
			
	}  
  
  

	
  function createXSL(){
	  var flag = true;//validateForm();
	  if(flag) {
		$('uAction').value = 'createExcel';
	    openWinGenericViewFile('/tcmIS/common/loadingfile.jsp','_Catalog_Synonym_Excel','650','600','yes');
	    document.genericForm.target='_Catalog_Synonym_Excel';
	    var a = window.setTimeout("document.genericForm.submit();",50);
	  }
	}  