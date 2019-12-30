//var qtyReturned_hedFunction = "onChange=checkQtyReturned";

	var inputSize= new Array();
	inputSize={"qtyReturned":8,"returnDate":15,"supplierRma":10};

	var maxInputLength = new Array();
	maxInputLength={"qtyReturned":8,"returnDate":15,"supplierRma":10};

function submitSearchForm()
{
 /*Make sure to not set the target of the form to anything other than resultFrame*/
 // var isValidSearchForm = true;
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
//  var errorMessage = messagesData.validvalues + "\n\n";
  var errorCount = 0;

  var srchFld1 = document.getElementById("searchField");
  var srchStr1 = document.getElementById("searchArgument");
  var errorMessage = messagesData.validvalues+"\n\n";
  
  if (srchFld1.value.trim() == 'itemId' &&  srchStr1.value.trim().length != 0 && !isInteger(srchStr1.value.trim()) )
    {
      errorMessage += messagesData.itemid + "\n";
      errorCount = 1;
      srchStr1.value = "";
    }
  
    if (srchFld1.value.trim() == 'radianPo' &&  srchStr1.value.trim().length != 0 && !isFloat(srchStr1.value.trim()) )
    {
      errorMessage += messagesData.radianpo + "\n";
      errorCount = 1;
      srchStr1.value = "";
    }
  
 if (errorCount >0)
 {
    alert(errorMessage);
    return false;
 }
 return true;
}

function resultOnLoad()
{	/*
  var rowsNum = beanGrid.getRowsNum();
  rowsNum = rowsNum*1;

  for (var p = 1 ; p < (rowsNum+1) ; p ++)
  {
  	var permission = '';
  	try{ permission = cellValue(p,"permission");
  	} catch(ex){}
	 if (permission != 'Y') {
	    beanGrid.lockRow(p,true);
	 }
  } 
 */   
}


function checkBoxValidate(rowId,cellInd) {
	var errorCount = 0;
 var errorMessage = messagesData.validvalues+"\n\n";
 
 var okValue=  cellValue(rowId,"ok");
// var columnId = beanGrid.getColumnId(cellInd);

 if( okValue == false ) 
	return true;
  var qtyReturned = cellValue(rowId, "qtyReturned").trim();
  var quantity = cellValue(rowId, "quantity").trim();
  var returnDate = cellValue(rowId, "returnDate").trim();
  var supplierRma = cellValue(rowId, "supplierRma").trim();
  var comments = cellValue(rowId, "comments").trim();

//alert("qtyReturnedtt:"+qtyReturned+"  quantity:"+quantity);
  if (!isPositiveInteger(qtyReturned,false))
   {  
   	   errorMessage = errorMessage +"\n"+ messagesData.qtyreturned;
   	   beanGrid.cells(rowId, beanGrid.getColIndexById("qtyReturned")).setValue("");
	   errorCount = 1;
   }
   else {
   	if(qtyReturned*1 > quantity*1){
   		errorMessage = errorMessage +"\n"+ messagesData.qtyreturned;
   	   beanGrid.cells(rowId, beanGrid.getColIndexById("qtyReturned")).setValue("");
	   errorCount = 1;
	 } 	
   }
   
   if (returnDate.length == 0 ||returnDate == null)
   {  
	   	   errorMessage = errorMessage +"\n"+ messagesData.returndate;
		   errorCount = 1;
   }
   
   if (supplierRma.length == 0 ||supplierRma == null)
   {  
	   	   errorMessage = errorMessage +"\n"+ messagesData.supplierrma;
		   errorCount = 1;
   }
   
   if(comments.length >400)
	  {
		  errorMessage = errorMessage +"\n"+ messagesData.maximumallowedtext;
		  beanGrid.cells(rowId, beanGrid.getColIndexById("comments")).setCTxtValue("","");
		  errorCount = 1;
	  }	 

   if (errorCount > 0)
   {
     alert(errorMessage);
//	 beanGrid.selectRowById(rowId,null,false,false);
	 return false;
   }  
   return true;
}


function createXSL(){
  var flag = true;//validateForm();
  if(flag) {
	$('uAction').value = 'createExcel';
    openWinGenericViewFile('/tcmIS/common/loadingfile.jsp','_NoSalesExcel','650','600','yes');
    document.genericForm.target='_NoSalesExcel';
    var a = window.setTimeout("document.genericForm.submit();",500);
  }
}

function process() {

  if(checkAll()) { 
   $('uAction').value = 'update';
   document.genericForm.target='resultFrame';
   parent.showPleaseWait();
   beanGrid.parentFormOnSubmit();
   document.genericForm.submit();
   return true;
  }
  else
  {
    return false;
  }  
}

function checkAll()
{
  var rowsNum = beanGrid.getRowsNum();

  rowsNum = rowsNum*1;

  for (var p = 1 ; p < (rowsNum+1) ; p ++)
  {
  	 var okCell = document.getElementById("ok"+p);
  	 if(okCell != null) {
	 	if (!checkBoxValidate(p,10)) {
		    beanGrid.selectRowById(p,null,false,false);
  	      	okCell.checked = false;
//		    cell( p,"ok" ).setCheck(false);  // error   in dhtmlxgrid_excell_customerized.js  
	 		return false;
		 }
	 }
  } 
  return true;
  
}

