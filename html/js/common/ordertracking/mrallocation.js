windowCloseOnEsc = true;
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
		if (document.getElementById("receiptDocument"+actualRowCount) != null) {
			if (document.getElementById("receiptDocument"+actualRowCount).value.length != 0) {
				document.getElementById("selectedDocumentId").value = document.getElementById("receiptDocument"+actualRowCount).value
				//first delete existing menu items
				//menu item starts with 0 (zero)
				var menuItemCount = mm_returnMenuItemCount('showReceiptDocument');
				if (menuItemCount > 0) {
					//NOTE: cannot remove all items, have to leave at least one item
					for (var i = 1; i < menuItemCount; i++) {
						mm_deleteItem('showReceiptDocument',1);    //always delete from top until no more items, BUT note delete top is 1 not 0 (zero)
					}
				}
				var documentIdArray = documentId[actualRowCount];
				var documentNameArray = documentName[actualRowCount];
				//edit the item left behind
				var jsUrl = "parent.showDocument("+documentIdArray[0].receiptId+","+documentIdArray[0].documentId+",'"+documentIdArray[0].documentUrl+"')";
				var menuText = 'text='+documentNameArray[0]+';url=javascript:'+jsUrl+';';
				mm_editItem('showReceiptDocument',0,menuText);
				//now add items to menu
				//adding item start with 0 (zero)
				//but since i cannot delete all items, starting new menu will starts with 1
				for (var j = 1; j < documentIdArray.length; j++) {
					var jsUrl = "parent.showDocument("+documentIdArray[j].receiptId+","+documentIdArray[j].documentId+",'"+documentIdArray[j].documentUrl+"')";
					var menuText = 'text='+documentNameArray[j]+';url=javascript:'+jsUrl+';';
					mm_insertItem('showReceiptDocument',j,menuText);
				}

				toggleContextMenu('showAll');
			}
		}

	}else if (showDocumentImageClickMenu) {
		showDocumentImageClickMenu = false;
		var tmp = '';
		var documentIdArray = documentId[actualRowCount];
		if(documentIdArray != null) {
			//auto open if ONLY one document
			if(documentIdArray.length == 1) {
				parent.showDocument(documentIdArray[0].receiptId, documentIdArray[0].documentId, "'"+documentIdArray[0].documentUrl+"'");
			}else {
				var documentNameArray = documentName[actualRowCount];
				for (var i = 0; i < documentIdArray.length; i++) {
					tmp += '<a href="#" onclick="showDocument('+
							documentIdArray[i].receiptId+','+
							documentIdArray[i].documentId+','+
                            "'"+documentIdArray[i].documentUrl+"'"+
                           ')">'+documentNameArray[i]+'</a><br>';
				}
				parent.document.getElementById("showDocumentMenuAreaBody").innerHTML = tmp;
				parent.showDocumentMenu();
			}
		}
	}

	 selectedrow =rowid;
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

/*
function showReceiptDocument()
{
	//alert("receipt document clicked: "+document.getElementById("selectedDocumentId").value);
   var loc = "showreceiptdocument.do?documentId="+document.getElementById("selectedDocumentId").value;
   openWinGeneric(loc,"showReceiptDocument22","800","500","yes","80","80")
}
*/