var selectedrow=null;
var showRighClickMenu=false;
var showDocumentImageClickMenu=false;

function init() { /*This is to initialize the YUI*/
 this.cfg = new YAHOO.util.Config(this);
 if (this.isSecure)
 {
  this.imageRoot = YAHOO.widget.Module.IMG_ROOT_SSL;
 }
}

/*The reason for this is to show the error messages from the main page*/
function showErrorMessages()
{
  parent.showErrorMessages();
}

function mySearchOnLoad()
{
  setSearchFrameSize();
    var action = document.getElementById("action");
    action.value = 'result';
    document.genericForm.target='resultFrame';
    parent.showPleaseWait();
    var a = window.setTimeout("document.genericForm.submit();",500);
}

function myOnload()
{
   setResultFrameSize();
}

function generateExcel() {
    var action = document.getElementById("action");
    action.value = 'createExcel';
    openWinGenericViewFile('/tcmIS/common/loadingfile.jsp','_MrAllocationReportGenerateExcel','650','600','yes');
    document.genericForm.target='_MrAllocationReportGenerateExcel';
    var a = window.setTimeout("document.genericForm.submit();",500);
}

function catchdetailsevent(rowid,actualRowCount)
{
	 var selectedRow = document.getElementById("rowId"+rowid+"");
	 var selectedRowClass = document.getElementById("colorClass"+rowid+"");
	 selectedRow.className = "selected"+selectedRowClass.value+"";

	 try
	 {
      var grandChildRowsSize = document.getElementById("grandChildRowsSize"+rowid+"0");
      for(k=1;k<grandChildRowsSize.value;k++)
      {
       //alert("grandchildRowId"+rowid+"0"+(k)+"");
	    var grnadChildRowId = document.getElementById("grnadChildRowId"+rowid+"0"+(k)+"");
	    grnadChildRowId.className = "selected"+selectedRowClass.value+"";
       }
    }
    catch (ex)
    {

    }

	 var childRowsSize = document.getElementById("childRowsSize"+rowid+"");
    for(j=1;j<childRowsSize.value;j++)
    {
	   //alert("childRowId"+rowid+""+(j)+"");
	   var selectedchildRow = document.getElementById("childRowId"+rowid+""+(j)+"");
	   selectedchildRow.className = "selected"+selectedRowClass.value+"";

	   var grandChildRowsSize = document.getElementById("grandChildRowsSize"+rowid+""+(j)+"");
      for(k=1;k<grandChildRowsSize.value;k++)
      {
      //alert("grandchildRowId"+rowid+""+(j)+""+(k)+"");
	   var grnadChildRowId = document.getElementById("grnadChildRowId"+rowid+""+(j)+""+(k)+"");
	   grnadChildRowId.className = "selected"+selectedRowClass.value+"";

      }
    }

	 if (selectedrow != null && rowid != selectedrow)
	 {
		var previouslySelectedRow = document.getElementById("rowId"+selectedrow+"");
	   var previouslySelectedRowClass = document.getElementById("colorClass"+selectedrow+"");
	   previouslySelectedRow.className = ""+previouslySelectedRowClass.value+"";

      try
	   {
       var previouslygrandChildRowsSize = document.getElementById("grandChildRowsSize"+selectedrow+"0");
       for(k=1;k<previouslygrandChildRowsSize.value;k++)
       {
        //alert("grandchildRowId"+rowid+"0"+(k)+"");
	     var previouslygrnadChildRowId = document.getElementById("grnadChildRowId"+selectedrow+"0"+(k)+"");
	     previouslygrnadChildRowId.className = ""+previouslySelectedRowClass.value+"";
        }
      }
      catch (ex)
      {

      }

		//alert(previouslySelectedRowClass.value);
      var previouslychildRowsSize = document.getElementById("childRowsSize"+selectedrow+"");
      for(j=1;j<previouslychildRowsSize.value;j++)
      {
	    //alert("childRowId"+selectedrow+""+(j)+"");
	    var previouslyselectedchildRow = document.getElementById("childRowId"+selectedrow+""+(j)+"");
	    previouslyselectedchildRow.className = ""+previouslySelectedRowClass.value+"";

	    var previouslygrandChildRowsSize = document.getElementById("grandChildRowsSize"+selectedrow+""+(j)+"");
       for(k=1;k<previouslygrandChildRowsSize.value;k++)
       {
       //alert("grandchildRowId"+selectedrow+""+(j)+""+(k)+"");
	    var previouslygrnadChildRowId = document.getElementById("grnadChildRowId"+selectedrow+""+(j)+""+(k)+"");
	    previouslygrnadChildRowId.className = ""+previouslySelectedRowClass.value+"";
       }
     }
	 }

	if (showRighClickMenu) {
		showRighClickMenu = false;
		vitems = new Array();
		if ($v("showDisplayCerts"+actualRowCount) == "Y") {
			document.getElementById("selectedDocumentId").value = document.getElementById("receiptDocument"+actualRowCount).value;
			
			vitems[vitems.length] = "text="+messagesData.displaycerts+";url=javascript:showReceiptDocument();";
		}
		
		if ($v("showDisplayMSDS"+actualRowCount) == "Y") {
			document.getElementById("selectedRefType").value = document.getElementById("refType"+actualRowCount).value;
			document.getElementById("selectedRefNumber").value = document.getElementById("refNumber"+actualRowCount).value;
            document.getElementById("selectedLineItem").value = document.getElementById("lineItem"+actualRowCount).value;    
			vitems[vitems.length] = "text="+messagesData.displaymsds+";url=javascript:showMSDS();";
		}
//alert("selectedShipmentId"+$v("shipmentId"+actualRowCount)+"     prNumber"+$v("prNumber"));

		if ($("shipmentId"+actualRowCount) != null && $v("shipmentId"+actualRowCount).length > 0) {
			$("selectedShipmentId").value = $v("shipmentId"+actualRowCount);
			vitems[vitems.length] = "text="+messagesData.displaypackinglist+";url=javascript:showPackList();";
		}
				
		if ($v("showDisplayInvoice"+actualRowCount) == "Y") {
			$("selectedShipmentId").value = $v("shipmentId"+actualRowCount);

			vitems[vitems.length] = "text="+messagesData.displayinvoice+";url=javascript:showInvoice();";
		}
		if (vitems.length > 0 ) {
			replaceMenu('showAll',vitems); 
			toggleContextMenu('showAll');
		}
	}else if (showDocumentImageClickMenu) {
		showDocumentImageClickMenu = false;
		var tmp = '';
		var documentIdArray = documentId[actualRowCount];
		if(documentIdArray != null) {
			//auto open if ONLY one document
			if(documentIdArray.length == 1) {
				parent.showDocument(documentIdArray[0]);
			}else {
				var documentNameArray = documentName[actualRowCount];
				for (var i = 0; i < documentIdArray.length; i++) {
					tmp += '<a href="#" onclick="showDocument(\''+documentIdArray[i]+'\')">'+documentNameArray[i]+'</a><br>';
				}
				parent.document.getElementById("showDocumentMenuAreaBody").innerHTML = tmp;
				parent.showDocumentMenu();
			}
		}
	}

	 selectedrow =rowid;
}

function replaceMenu(menuname,menus){
	  var i = mm_returnMenuItemCount(menuname);

	  for(;i> 1; i-- )
			mm_deleteItem(menuname,i);

	  for( i = 0; i < menus.length; ){
 		var str = menus[i];

 		i++;
		mm_insertItem(menuname,i,str);
		// delete original first item.
    	if( i == 1 ) mm_deleteItem(menuname,1);
      }
}


function showReceiptDocumentMenu(rowid,actualRowCount)
{
	showRighClickMenu = true;
	catchdetailsevent(rowid,actualRowCount);
}

function showDocumentImageMenu(rowid,actualRowCount)
{
	showDocumentImageClickMenu = true;
	catchdetailsevent(rowid,actualRowCount);
}

function showReceiptDocument()
{
	var loc = "/HaasReports/report/displaymergedpdfs.do?type=certs&receiptId="+$v("selectedDocumentId");
    openWinGeneric(loc,"DisplayCerts"+$v("selectedDocumentId"),"800","600","yes","50","50","20","20","yes");
}

function showMSDS()
{
	var loc = "/HaasReports/report/displaymergedpdfs.do?type=msds&refType="+encodeURIComponent($v("selectedRefType"))+
              "&refNumber="+$v("selectedRefNumber")+"&prNumber="+$v("prNumber")+"&lineItem="+$v("selectedLineItem");
    openWinGeneric(loc,"DisplayMSDS","800","600","yes","50","50","20","20","yes");
}

function showPackList()
{//alert("prNumber="+$v("prNumber")+"   shipmentId="+$v("selectedShipmentId"));
	var loc = "/HaasReports/report/displaymergedpdfs.do?type=packList&prNumber="+$v("prNumber")+"&shipmentId="+$v("selectedShipmentId");
    openWinGeneric(loc,"DisplayPackList","800","600","yes","50","50","20","20","yes");
}

function showInvoice()
{
	var loc = "/HaasReports/report/displaymergedpdfs.do?type=invoice&prNumber="+$v("prNumber")+"&shipmentId="+$v("selectedShipmentId");
    openWinGeneric(loc,"DisplayInvoice","800","600","yes","50","50","20","20","yes");
}
