var selectedRowId=null;

function init() {
this.cfg = new YAHOO.util.Config(this);
if (this.isSecure)
{
 this.imageRoot = YAHOO.widget.Module.IMG_ROOT_SSL;
}

/*Yui pop-ups need to be initialized onLoad to make them work correctly.
I they are not initialized onLoad they tend to act weird*/
legendWin = new YAHOO.widget.Panel("showLegendArea", { width:"300px", fixedcenter: true, constraintoviewport: true, underlay:"none", close:true, visible:false, draggable:true, modal:false } );
legendWin.render();
}

function showCatalogSpecLegend()
{
var showLegendArea = document.getElementById("showLegendArea");
showLegendArea.style.display="";

legendWin.show();
}

/*The reason for this is to show the error messages from the main page*/
function showErrorMessages()
{
  parent.showErrorMessages();
}

function myOnload()
{
   displaySearchDuration();
 setResultFrameSize();
 /*Dont show any update links if the user does not have permissions.
 Remove this section if you don't have any links on the main page*/
 if (!showUpdateLinks)
 {
  parent.document.getElementById("updateResultLink").style["display"] = "none";
 }
 else
 {
  parent.document.getElementById("updateResultLink").style["display"] = "";
 }
}

function submitSearchForm()
{

 /*Make sure to not set the target of the form to anything other than resultFrame*/
 var now = new Date();
    document.getElementById("startSearchTime").value = now.getTime();   
 var flag = validateForm();
  if(flag) {
   var action = document.getElementById("action");
   action.value = '';
   document.genericForm.target='resultFrame';
   parent.showPleaseWait();
   return true;
  }
  else
  {
    return false;
  }
}
/*
function submitUpdate() {
  var flag = validateUpdateForm();
  if(flag) {
    var action = document.getElementById("action");
    action.value = 'update';
    submitOnlyOnce();
    document.genericForm.submit();
  }
  return flag;
}
*/
function validateForm() {
 // document.genericForm.target=''

  return true;
}
/*
function validateUpdateForm() {
  return true;
}
*/
function generateExcel() {
    var action = document.getElementById("action");
    action.value = 'createExcel';
    openWinGenericViewFile('/tcmIS/common/loadingfile.jsp','_CatalogSpecGenerateExcel','650','600','yes');
    document.genericForm.target='_CatalogSpecGenerateExcel';
    var a = window.setTimeout("document.genericForm.submit();",500);
}

function selectRow(rowId)
{
   //alert("rowId  "+rowId+"");
   var selectedRow = document.getElementById("rowId"+rowId+"");
	 var selectedRowClass = document.getElementById("colorClass"+rowId+"");
   var deleteRowCheckBox = document.getElementById("delete"+rowId+"");
   if (deleteRowCheckBox && !deleteRowCheckBox.checked)
   {
	  selectedRow.className = "selected"+selectedRowClass.value+"";
   }

	 if (selectedRowId != null && rowId != selectedRowId)
	 {
		var previouslySelectedRow = document.getElementById("rowId"+selectedRowId+"");
	  var previouslySelectedRowClass = document.getElementById("colorClass"+selectedRowId+"");
    var previouslydeleteRowCheckBox = document.getElementById("delete"+selectedRowId+"");

    if (previouslydeleteRowCheckBox && !previouslydeleteRowCheckBox.checked)
    {
	   previouslySelectedRow.className = ""+previouslySelectedRowClass.value+"";
    }
	 }
	 selectedRowId =rowId;

   var onLine = document.getElementById("onLine"+selectedRowId+"");
   var content = document.getElementById("content"+selectedRowId+"");
   var newRow = document.getElementById("newRow"+selectedRowId+"");

   /*Depending on the different conditions I set the right click menu to have different options*/
   /*if (onLine.value == "Y" && content.value.trim().length > 0)
   {
	  toggleContextMenu('catalogSpecLink');
   }*/
   if (newRow && newRow.value == "true")
   {
	  toggleContextMenu('catalogSpecNew');
   }
   else
   {
	  toggleContextMenu('catalogSpec');
   }
}

function viewSpec(rowId) {
    var tmpUrl = '';
    if ($v("secureDocViewer") == 'true')
        tmpUrl = '/DocViewer/client/';
    openWinGeneric(tmpUrl + 'docViewer.do?uAction=viewSpec' +
				'&spec=' + encodeURIComponent($v('specId'  + rowId)) +
				'&companyId=' + $v('companyId') +
				'&catalogId=' + encodeURIComponent($v('catalogId'  + rowId)) +
				'&catpartno=' + encodeURIComponent($v('catPartNo'  + rowId))
				,"ViewSpec","800","800",'yes' );
	selectRow(rowId);
}

function viewPartHistory() {
  var historySpecId = document.getElementById('historySpecId');
  historySpecId.value = '';

  var partNumber = document.getElementById('catPartNo' + selectedRowId);
  var historyPartNumber = document.getElementById('historyCatPartNo');
  historyPartNumber.value = partNumber.value;
  var catalogId = document.getElementById('catalogId' + selectedRowId);
  var historyCatalogId = document.getElementById('historyCatalogId');
  historyCatalogId.value = catalogId.value;
  //alert("#"+ rowNumber + " " +specId.value + " " + partNumber.value + " " + catalogId.value);
  var action = document.getElementById("action");
  action.value = 'history';
  openWinGeneric('/tcmIS/common/loadingpleasewait.jsp','_CatalogPartSpecHistory','650','600','yes');
  document.genericForm.target='_CatalogPartSpecHistory';
  var a = window.setTimeout("document.genericForm.submit();",500);
}

function viewSpecHistory() {
  var specId = document.getElementById('specId' + selectedRowId);
  var historySpecId = document.getElementById('historySpecId');
  historySpecId.value = specId.value;

  var partNumber = document.getElementById('catPartNo' + selectedRowId);
  var historyPartNumber = document.getElementById('historyCatPartNo');
  historyPartNumber.value = partNumber.value;
  var catalogId = document.getElementById('catalogId' + selectedRowId);
  var historyCatalogId = document.getElementById('historyCatalogId');
  historyCatalogId.value = catalogId.value;
//alert("#"+ rowNumber + " " +specId.value + " " + partNumber.value + " " + catalogId.value);
    var action = document.getElementById("action");
    action.value = 'history';
    openWinGeneric('/tcmIS/common/loadingpleasewait.jsp','_CatalogSpecHistory','650','600','yes');
    document.genericForm.target='_CatalogSpecHistory';
    var a = window.setTimeout("document.genericForm.submit();",500);
}

function addRow() {
  var lastSelectedRow = document.getElementById("rowId"+selectedRowId+"");
  //insert after the selected row
  var rowPosition = lastSelectedRow.rowIndex*1 + 1;
  var totalLines = document.getElementById('totalLines');
  //alert("add row "+rowPosition+"  totalLines  "+totalLines.value+"");
  var x=document.getElementById('resultsPageTable').insertRow(rowPosition);
  x.id = "rowId" + totalLines.value;
  x.className= "yellow";

  var insertedRow = document.getElementById("rowId"+totalLines.value+"");
  var cell0=insertedRow.insertCell(0);
  var cell1=insertedRow.insertCell(1);
  var cell2=insertedRow.insertCell(2);
  var cell3=insertedRow.insertCell(3);
  var cell4=insertedRow.insertCell(4);
  var cell5=insertedRow.insertCell(5);
  var cell6=insertedRow.insertCell(6);

  var catalogId = document.getElementById('catalogId' + selectedRowId)
  var catPartNo = document.getElementById('catPartNo' + selectedRowId);

  cell0.innerHTML= getCatalogCell(catalogId.value, totalLines.value);
  cell1.innerHTML= getPartNumberCell(catPartNo.value, totalLines.value);
  cell2.innerHTML= getSpecificationDropDown(totalLines.value, "rowId" + totalLines.value);
  cell3.innerHTML= getCocCheckbox(totalLines.value);
  cell4.innerHTML= getCoaCheckbox(totalLines.value);
  cell5.innerHTML= getItarCell(totalLines.value);
  cell6.innerHTML= getDeleteCell(totalLines.value);
  //need to hide coc and coa if spec is no specification
  specChanged(totalLines.value);

  if (insertedRow.addEventListener) /*For non IE*/
  {
		insertedRow.addEventListener("mouseup", selectNewRow, true)
	}
	else if (insertedRow.attachEvent) /*For IE*/
  {
		insertedRow.attachEvent("onmouseup",selectNewRow);
	}

  totalLines.value = Math.round(totalLines.value) + 1;
  parent.resizeFrame("resultFrame",10);
}

function selectNewRow(event)
{
 var targ;
  if (!event)
    var event = window.event;
  if (event.target)
    targ = event.target;
  else if (event.srcElement)
    targ = event.srcElement;
  if (targ.nodeType == 3) // defeat Safari bug
    targ = targ.parentNode;

 newRowSelected =targ.parentNode;
 var tempId = newRowSelected.id;
 if (tempId.trim().length > 0) /*Need to select the row only when it is selected the first time*/
 {
  var newRowSelectedId = tempId.replace(/rowId/g," ");
  //alert("tempId  "+tempId+" Row id  "+newRowSelectedId*1);
  if (newRowSelectedId*1 >= 0 ) /*This is for Mozilla*/
  {
   selectRow(newRowSelectedId*1);
  }
 }
}

function getCatalogCell(catalogId, rowNumber) {
 var catalogCellHTML = "<input name=\"catalogSpecInputBean[" + rowNumber + "].newRow\" id=\"newRow" + rowNumber + "\" type=\"hidden\" value=\"true\">";
 catalogCellHTML += "<input name=\"colorClass" + rowNumber + "\" id=\"colorClass" + rowNumber + "\" type=\"hidden\" value=\"yellow\">";
 catalogCellHTML += "<input name=\"catalogSpecInputBean[" + rowNumber + "].oldCoc\" id=\"oldCoc" + rowNumber + "\" type=\"hidden\" value=\"\">";
 catalogCellHTML += "<input name=\"catalogSpecInputBean[" + rowNumber + "].oldCoa\" id=\"oldCoa" + rowNumber + "\" type=\"hidden\" value=\"\">";
// catalogCellHTML += "<input name=\"catalogSpecInputBean[" + rowNumber + "].onLine\" id=\"onLine" + rowNumber + "\" type=\"hidden\" value=\"\">";
// catalogCellHTML += "<input name=\"catalogSpecInputBean[" + rowNumber + "].content\" id=\"content" + rowNumber + "\" type=\"hidden\" value=\"\">";
 catalogCellHTML += "<input name=\"catalogSpecInputBean[" + rowNumber + "].catalogId\" id=\"catalogId" + rowNumber + "\" type=\"hidden\" value=\"" + catalogId + "\">";
 catalogCellHTML += catalogId;

 return catalogCellHTML;
}

function getPartNumberCell(partNumber, rowNumber) {
  return "<input name=\"catalogSpecInputBean[" + rowNumber + "].catPartNo\" id=\"catPartNo" + rowNumber + "\" type=\"hidden\" value=\"" + partNumber + "\">" + partNumber;
}

function getSpecificationDropDown(rowNumber, rowId) {
var specDropDownHTML= "<select name=\"catalogSpecInputBean[" + rowNumber + "].specId\" class=\"selectBox\" id=\"specId" + rowNumber + "\" onchange=\"specChanged('" + rowNumber + "');\">";
specDropDownHTML += "<option value=\"No Specification\">"+messagesData.nospecification+"</option>";
specDropDownHTML += "<option value=\"Std Mfg Cert\">"+messagesData.stdmfgcert+"</option></select>";

return specDropDownHTML;
}

function getCocCheckbox(rowNumber) {
  return "<input name=\"catalogSpecInputBean[" + rowNumber + "].coc\" id=\"coc" + rowNumber + "\" type=\"checkbox\" value=\"Y\">";
}

function getCoaCheckbox(rowNumber) {
  return "<input name=\"catalogSpecInputBean[" + rowNumber + "].coa\" id=\"coa" + rowNumber + "\" type=\"checkbox\" value=\"Y\">";
}

function getItarCheckbox(rowNumber) {
	  return "<input name=\"catalogSpecInputBean[" + rowNumber + "].itar\" id=\"itar" + rowNumber + "\" type=\"checkbox\">";
}

/*function getDeleteCheckbox(rowNumber) {
  return "<input name=\"catalogSpecInputBean[" + rowNumber + "].delete\" id=\"delete" + rowNumber + "\" type=\"checkbox\" value=\"Y\">";
}*/

function getDeleteCell(rowNumber) {
  return "<input name=\"catalogSpecInputBean[" + rowNumber + "].delete\" id=\"delete" + rowNumber + "\" type=\"checkbox\" onclick=\"deleteSpec("+rowNumber+")\" value=\"Y\">";
  //return "<a href=\"javascript:deleteRow('" + rowId + "')\">Remove</a>"
}

function deleteRow(rowId) {
 var selectedDeleteRow = document.getElementById("rowId"+rowId+"");
 selectedDeleteRow.style["display"] = "none";

 var deleteRowCheckBox = document.getElementById("delete"+rowId+"");
 deleteRowCheckBox.checked=true;
}

function deleteSpec(rowId) {
 var deleteRowCheckBox = document.getElementById("delete"+rowId+"");
 var selectedDeleteRow = document.getElementById("rowId"+rowId+"");
 var selectedDeleteRowClass = document.getElementById("colorClass"+rowId+"");
 if (deleteRowCheckBox.checked)
 {
  selectedDeleteRow.className = "black";
 }
 else
 {
  selectedDeleteRow.className = "selected"+selectedDeleteRowClass.value+"";
 }
}

function specChanged(rowNumber) {
  var cocCheckbox = document.getElementById('coc' + rowNumber);
  var coaCheckbox = document.getElementById('coa' + rowNumber);
  var itarCheckbox = document.getElementById('itar' + rowNumber);
  var spec = document.getElementById('specId' + rowNumber);
  if(spec.value == 'No Specification') {
    cocCheckbox.style.visibility = "hidden";
    coaCheckbox.style.visibility = "hidden";
    itarCheckbox.style.visibility = "hidden";
  }
  else {
    cocCheckbox.style.visibility = "visible";
    coaCheckbox.style.visibility = "visible";
    itarCheckbox.style.visibility = "visible";
  }
}


/*function deleteRow(rowId) {
  var x=document.getElementById('resultTable').rows;
  if(x != null) {
    for(var i=1; i < x.length; i++) {
      if(x[i].id == rowId) {
        document.getElementById('resultTable').deleteRow(x[i].rowIndex);
      }
    }
  }
}*/

/*
function catchMouseEvent2(event,row, permission) {
  var menuName="";
  var targ;
  if (!event)
    var event = window.event;
  if (event.target)
    targ = event.target;
  else if (event.srcElement)
    targ = event.srcElement;
  if (targ.nodeType == 3) // defeat Safari bug
    targ = targ.parentNode;

  if(permission == 'true' || (targ != null && targ.id == 'part') || (targ != null && targ.id == 'spec')) {
    if(targ != null && targ.id == 'part') {
      selectedCell = 'part';
      if(permission == 'true') {
        menuName = 'catalogSpecMenuPartHistoryAdd';
      }
      else {
        menuName = 'catalogSpecMenuPartHistory';
      }
    }
    else if(targ != null && targ.id == 'spec') {
      selectedCell = 'spec';
      if(permission == 'true') {
        menuName = 'catalogSpecMenuSpecHistoryAdd';
      }
      else {
        menuName = 'catalogSpecMenuSpecHistory';
      }
    }
    else if(permission == 'true'){
      menuName = 'catalogSpecMenuAdd';
    }
//alert(menuName);
    var myRow = row;
    selectedRowIdNumber = myRow.substring(5);
    var mySelectedRow = document.getElementById(myRow);
    var selectedRowClass = document.getElementById("colorClass"+selectedRowIdNumber+"");
    mySelectedRow.className = "selected"+selectedRowClass.value+"";
    if (mySelectedRow != null && selectedRow != mySelectedRow) {
      var previouslySelectedRow = document.getElementById(myRow);
      var previouslySelectedRowClass = document.getElementById("colorClass"+selectedRowIdNumber+"");
      previouslySelectedRow.className = ""+previouslySelectedRowClass.value+"";
    }
    selectedRow = mySelectedRow;
    selectedRowIndex = mySelectedRow.rowIndex;

    if(event.button==2 || event.button==3){
      _gm=getMenuByName(menuName)
      if(_gm!=null)popup(menuName,1)
        return false
    }
    else{
      closeAllMenus();
    }
  }
}
*/
/*
function closeMenu() {
  closeAllMenus();
}
*/

/*
function viewHistory() {
  var rowNumber = Math.round(selectedRowIndex) - 1;
  if(selectedCell == 'spec') {
    var specId = document.getElementById('specId' + rowNumber);
    var historySpecId = document.getElementById('historySpecId');
    historySpecId.value = specId.value;
  }
  else {
    var historySpecId = document.getElementById('historySpecId');
    historySpecId.value = '';
  }
  var partNumber = document.getElementById('catPartNo' + rowNumber);
  var historyPartNumber = document.getElementById('historyCatPartNo');
  historyPartNumber.value = partNumber.value;
  var catalogId = document.getElementById('catalogId' + rowNumber);
  var historyCatalogId = document.getElementById('historyCatalogId');
  historyCatalogId.value = catalogId.value;
//alert("#"+ rowNumber + " " +specId.value + " " + partNumber.value + " " + catalogId.value);
    var action = document.getElementById("action");
    action.value = 'history';
    openWinGeneric('/tcmIS/common/loadingpleasewait.jsp','_CatalogSpecHistory','650','600','yes');
    document.genericForm.target='_CatalogSpecHistory';
    var a = window.setTimeout("document.genericForm.submit();",500);
}
*/
/*function myOnLoad(initialRowCount) {
  //alert(initialRowCount);
  rowCount = initialRowCount;
}*/
