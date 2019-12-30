function $(a) {
	return document.getElementById(a);
}

function regExcape(str) {
// if more special cases, need more lines.
return str.replace(new RegExp("[([]","g"),"[$&]");
}

var submitCount=0;
var updatecount=0;
function submitPoOnlyOnce()
{
   if (submitCount == 0)
   {
        submitCount++;
        try
        {
         //Turns off the main page and shows the transitions page
         var transitPage = document.getElementById("transitPage");
         transitPage.style["display"] = "";

         var mainPage = document.getElementById("mainPage");
         mainPage.style["display"] = "none";
         /*This is for IE, fomr some reason the table background is visible*/
         var resultsMaskTable = document.getElementById("resultsMaskTable");
         resultsMaskTable.style["display"] = "none";
        }
        catch (ex)
        {
        }
        return true;
    }
    else
    {
        alert("This form has already been submitted.\n Please Wait for Results.\n Thanks!");
        return false;
    }
}

 function addMaterialLine() {
 	 var maxCount = getMaxCount();  
 	 var table = document.getElementById("materialTable");
	 var num = table.rows.length;
	 	 			
 	 var cells = table.rows[1].cells;
 	 var newcells = new Array();
 	 var row = table.insertRow(num);
   var universalReplace = "poLineDetailViewBean["; // for those inputs

    for( ii = 0 ; ii < cells.length; ii++) {
 	 var inner = cells[ii].innerHTML;
 	 inner=inner.replace(/unitPrice0/gi,"unitPrice"+num);
 	 inner=inner.replace(/vendorShipDate0/gi,"vendorShipDate"+num);
 	 inner=inner.replace(/promisedDate0/gi,"promisedDate"+num);
 	 inner=inner.replace(/poLine0/gi,"poLine"+num);
 	 inner=inner.replace(/quantity0/gi,"quantity"+num);
 	 inner=inner.replace(/extendedPrice0/gi,"extendedPrice"+num);
   inner=inner.replace(/expediteComments0/gi,"expediteComments"+num);
   inner=inner.replace(/radianPo0/gi,"radianPo"+num);
   inner=inner.replace(/amendment0/gi,"amendment"+num);
   inner=inner.replace(/itemId0/gi,"itemId"+num);
   inner=inner.replace(/needDate0/gi,"needDate"+num);
   inner=inner.replace(/mfgPartNo0/gi,"mfgPartNo"+num);
   inner=inner.replace(/supplierPartNo0/gi,"supplierPartNo"+num);
   inner=inner.replace(/dpasRating0/gi,"dpasRating"+num);
   inner=inner.replace(/poLineNote0/gi,"poLineNote"+num);
   inner=inner.replace(/ammendmentcomments0/gi,"ammendmentcomments"+num);
   inner=inner.replace(/remainingShelfLifePercent0/gi,"remainingShelfLifePercent"+num);
   inner=inner.replace(/currencyId0/gi,"currencyId"+num);
   inner=inner.replace(/supplier0/gi,"supplier"+num);
   inner=inner.replace(/shipFromLocationId0/gi,"shipFromLocationId"+num);
   inner=inner.replace(/supplierSalesOrderNo0/gi,"supplierSalesOrderNo"+num);
   inner=inner.replace(/quantityReceived0/gi,"quantityReceived"+num);
   inner=inner.replace(/quantityVouchered0/gi,"quantityVouchered"+num);
   inner=inner.replace(/quantityReturned0/gi,"quantityReturned"+num);
   inner=inner.replace(/lineChangeStatus0/gi,"lineChangeStatus"+num);
   inner=inner.replace(/poLineStatus0/gi,"poLineStatus"+num);
   inner=inner.replace(/expediteNoteChangeStatus0/gi,"expediteNoteChangeStatus"+num);
   inner=inner.replace(/poLineStatusSpan0/gi,"poLineStatusSpan"+num);
   inner=inner.replace(/deliveryComments0/gi,"deliveryComments"+num);
   inner=inner.replace(/backOrderCategory0/gi,"backOrderCategory"+num);
   //inner=inner.replace(/setExpediteNoteCatChanged(0/gi,"setExpediteNoteCatChanged("+num);

   newcells[ii] = cells[ii].cloneNode(false);
 	 var cell =	row.insertCell(ii);
   if(	universalReplace != null && universalReplace != "" )
			inner = inner.replace(new RegExp( regExcape(universalReplace)+0,"g"),universalReplace+num);
    //alert(inner);
    cell.innerHTML = inner;
   }

     document.getElementById('quantity'+num).value= '0' ;
	   var lineCount = 1000 + maxCount;
     document.getElementById('label_poLine'+num).innerHTML = lineCount;
     document.getElementById('poLine'+num).value = lineCount;

     var reset = $("unitPrice"+num).options;
     for (i = reset.length; i> 0;i--) {
		  reset[i] = null;
	   }

	   for (var i=0; i < validUnitPrice.length; i++)
     {
      if( validUnitPrice[i] != null ) {
			setOption(i,validUnitPrice[i],validUnitPrice[i],"unitPrice"+num);
		 }
     }
     document.getElementById('extendedPrice'+num).innerHTML = "";
     document.getElementById('expediteComments'+num).value = "";
     document.getElementById('amendment'+num).value = "0";
     document.getElementById('lineChangeStatus'+num).value = "addLine";
     document.getElementById('poLineStatus'+num).value = "Draft";
     document.getElementById('poLineStatusSpan'+num).innerHTML = "Draft";
     document.getElementById('backOrderCategory'+num).disabled = true;
}
 
 function validatePoForm()
 {   
   var table = document.getElementById("materialTable");
	 var num = table.rows.length;
   var sumLineQty = 0;
  
   for (var i=0; i < num*1; i++)
   {    
     lineqty = document.getElementById("quantity"+i+"");
     if( lineqty != null ) {
     sumLineQty = sumLineQty + lineqty.value*1;
     //alert(lineqty.value*1);
     }
   }

   var totalLineQty = document.getElementById("totalLineQty");
   if (totalLineQty.value != sumLineQty)
   {
    alert(""+messagesData.totalQtyErrorMsg+" "+totalLineQty.value+"");
    return false;
   }
   else
   {
     return true;
   }
 }

 function confirmPO() 
 {
	if( !validatePoForm() ) return;

	var userAction = document.getElementById("action");
	userAction.value = 'confirm';
    submitPoOnlyOnce();
    var totalLines = document.getElementById("totalLines");
    for (var i=0; i < totalLines.value*1; i++)
    {
     document.getElementById("shipFromLocationId"+i+"").disabled = false;
     document.getElementById("unitPrice"+i+"").disabled = false;
    }
    document.genericForm.submit();
 }

 function updatePO()
 {
	var userAction = document.getElementById("action");
	userAction.value = 'updateExpeditenotes';
    submitPoOnlyOnce();
    var totalLines = document.getElementById("totalLines");
    for (var i=0; i < totalLines.value*1; i++)
    {
     document.getElementById("shipFromLocationId"+i+"").disabled = false;
     document.getElementById("unitPrice"+i+"").disabled = false;
    }
    document.genericForm.submit();
 }

function getMaxCount() {
	 var maxCount = 0;
 	 var table = document.getElementById("materialTable");
	 var num = table.rows.length;
	 for(ii = 0 ; ii <= num; ii++ ) 
	 	if( document.getElementById('poLine'+ ii) != null ) {
	 		var intval = parseInt( document.getElementById('poLine'+ ii).value );
	 		if( intval > maxCount )
	 			maxCount = intval;
	 	}

	return maxCount;
}

function showNotes(fieldid)
{
    var section = 'div' + fieldid;
    var pgphBlock = 'pgph' + fieldid;
    var current = (document.getElementById(section).style.display == 'block') ? 'none' : 'block';
    document.getElementById(section).style.display = current;
    document.getElementById(pgphBlock).innerHTML = (current == 'block') ? '<i>-</i>' : '<i>+</i>';
}

function setLineChanged(selectedRow)
{
    lineChangeStatus = document.getElementById("lineChangeStatus"+selectedRow+"");
	  lineChangeStatus.value = "Yes";

    document.getElementById('poLineStatusSpan'+selectedRow).innerHTML = "Unconfirmed";      
}

function setExpediteNoteChanged(selectedRow,changeLine)
{
    expediteNoteChangeStatus = document.getElementById("expediteNoteChangeStatus"+selectedRow+"");
	  expediteNoteChangeStatus.value = "Yes";

    if (changeLine == "Yes")
    {      
     lineChangeStatus = document.getElementById("lineChangeStatus"+selectedRow+"");
	   lineChangeStatus.value = "Yes";
    }
}

function setExpediteNoteCatChanged(selectedRow,changeLine)
{
  backOrderCategory = document.getElementById("backOrderCategory"+selectedRow+"");
  if (backOrderCategory.value.trim().length > 0)
  {
    expediteComments = document.getElementById("expediteComments"+selectedRow+"");
    var currentcomments = expediteComments.value
    expediteComments.value = backOrderCategory.value + "\n" + currentcomments;

    expediteNoteChangeStatus = document.getElementById("expediteNoteChangeStatus"+selectedRow+"");
	  expediteNoteChangeStatus.value = "Yes";

    if (changeLine == "Yes")
    {
     lineChangeStatus = document.getElementById("lineChangeStatus"+selectedRow+"");
	   lineChangeStatus.value = "Yes";
    }
  }
}

function showUpdateLinks()
{
  totalQtyReceived = document.getElementById("totalQtyReceived");
  totalLineQty = document.getElementById("totalLineQty");
  dbuyLockStatus = document.getElementById("dbuyLockStatus");
  javaScriptUpdatePermission = document.getElementById("javaScriptUpdatePermission");
  javaScriptConfirmPermission = document.getElementById("javaScriptConfirmPermission");  
  everConfirmed = document.getElementById("everConfirmed");
  var totalLines = document.getElementById("totalLines");

  if ( (totalLineQty.value == totalQtyReceived.value) || (dbuyLockStatus.value == 'Yes') )
  {
     document.getElementById("poConfirmLink").style["display"] = "none";
     document.getElementById("addLineLink").style["display"] = "none";
     if (everConfirmed.value == 'Yes' && javaScriptUpdatePermission.value == 'Yes')
     {
     document.getElementById("poUpdateLink").style["display"] = "";
     //var table = document.getElementById("materialTable");
	 //var num = table.rows.length;
     for (var i=0; i < totalLines.value*1; i++)
     {
      document.getElementById("quantity"+i+"").readOnly = true;
      document.getElementById("shipFromLocationId"+i+"").disabled = true;
      document.getElementById("unitPrice"+i+"").disabled = true;
     }
     }
     else
     {
       document.getElementById("poUpdateLink").style["display"] = "none";
     }
  }
  else if (javaScriptConfirmPermission.value != 'Yes')
  {
    document.getElementById("poConfirmLink").style["display"] = "none";
    document.getElementById("addLineLink").style["display"] = "none";

    if (javaScriptUpdatePermission.value == 'Yes')
    {
     document.getElementById("poUpdateLink").style["display"] = "";
     //var table = document.getElementById("materialTable");
	 //var num = table.rows.length;
     for (var i=0; i < totalLines.value*1; i++)
     {
      document.getElementById("quantity"+i+"").readOnly = true;
      document.getElementById("shipFromLocationId"+i+"").disabled = true;
      document.getElementById("unitPrice"+i+"").disabled = true;
     }
    }
  }
  else if (javaScriptUpdatePermission.value != 'Yes')
  {
    document.getElementById("poUpdateLink").style["display"] = "none";
  }   
}

function checkQuantity(selectedRow)
{
 lineqty = document.getElementById("quantity"+selectedRow+"");
 oldQuantity = document.getElementById("oldQuantity"+selectedRow+"");
 quantityReceived = document.getElementById("quantityReceived"+selectedRow+"");

 if ( !(isInteger(lineqty.value)) )
 {
   alert(messagesData.selectNumber);
   lineqty.value = oldQuantity.value;
 }
 else if (lineqty.value*1 < quantityReceived.value*1)
 {
   alert(messagesData.qtyReceivedErrorMsg);
   lineqty.value = oldQuantity.value;
 }
 else
 {
  setLineChanged(selectedRow);
 }
}