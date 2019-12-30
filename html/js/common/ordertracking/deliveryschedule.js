var children = new Array();

var selectedRowId=null;
function init() { /*This is to initialize the YUI*/
 this.cfg = new YAHOO.util.Config(this);
 if (this.isSecure)
 {
  this.imageRoot = YAHOO.widget.Module.IMG_ROOT_SSL;
 }
}
/*
function ConfirmClose() {
  //if user click on the X to close window
//  try
  {
//  if (window.event.clientY < 0 && window.event.clientX < 0) {
    var userChangedData = document.getElementById("userChangedData");  // TODO:  make sure we got the right value !!
    if (userChangedData.value != null) {
      if (userChangedData.value == 'y') {
//        event.returnValue = messagesData.dataChanged;
		alert(messagesData.dataChanged);
        return false;
      }
    }
//  }
  }
//  catch (ex)
  {

  }
} //end of method
*/

function showCalendar(index)
{
  var cal4 = new CalendarPopup();
  cal4.setMonthNames('Jan','Feb','Mar','Apr','May','Jun','Jul','Aug','Sep','Oct','Nov','Dec');
  cal4.setDayHeaders('S','M','T','W','T','F','S');
  cal4.setWeekStartDay(0);
  var now = new Date();
  var currentDate = (now.getMonth()+1)+"/"+now.getDate()+"/"+now.getFullYear();
  //disable all dates in the past including today
  cal4.addDisabledDates(null,currentDate);
  //disable all dates in the future
  for (i = 0; i < blackOutDate.length;i++) {
    cal4.addDisabledDates(blackOutDate[i]);
  }
  var inputObj = document.getElementById("requestedDateToDeliver"+index);
  if (inputObj == null || inputObj.value.length == 0 ) {
    cal4.select(inputObj,'linkrequestedDateToDeliver'+index,'MMM dd, yyyy');
  }else {
    cal4.select(inputObj,'linkrequestedDateToDeliver'+index,'MMM dd, yyyy',inputObj.value);
  }
  return false;
}

/*The reason for this is to show the error messages from the main page*/
function showErrorMessages()
{
  parent.showErrorMessages();
}

function myOnload(action)
{
   setResultFrameSize();
    
   totalLines = document.getElementById("totalLines").value;
   var source = document.getElementById("source");
   var requestLineStatus = document.getElementById("requestLineStatus");

   if (requestLineStatus.value == 'Draft')
   {
      parent.document.getElementById("mainUpdateLinks").style["display"] = "";
      if (!showUpdateLinks)
       {
         parent.document.getElementById("updateResultLink").style["display"] = "none";
         parent.document.getElementById("frequencyLink").style["display"] = "none";
       }
       else
       {
         if (action == 'list')
         {
            parent.document.getElementById("updateResultLink").style["display"] = "";
         }
         else
         {
           parent.document.getElementById("updateResultLink").style["display"] = "";
           parent.document.getElementById("deleteUndoLink").style["display"] = "none";
         }
         parent.document.getElementById("frequencyLink").style["display"] = "";
       }

       if (totalLines == 0)
       {
        showAddRow()
       }
   }
   else
   {
       if (!showUpdateLinks)
       {
         parent.document.getElementById("updateResultLink").style["display"] = "none";
         parent.document.getElementById("deleteUndoLink").style["display"] = "none";
       }
       else
       {
          if (action == 'list')
         {
            parent.document.getElementById("updateResultLink").style["display"] = "";
            parent.document.getElementById("deleteUndoLink").style["display"] = "";
         }
         else
         {
             parent.document.getElementById("updateResultLink").style["display"] = "";
             parent.document.getElementById("deleteUndoLink").style["display"] = "none";
         }
       }
   }

   if (source.value == 'materialRequest')
   {
     parent.document.getElementById("returnToMrLink").style["display"] = "";
     var lastAction = document.getElementById("lastAction").value;       
     if (lastAction == 'update' || lastAction == 'updateShowCalendar')
     {
      parent.updateGridQuantity();
     }
   }
   else
   {
       parent.document.getElementById("returnToMrLink").style["display"] = "none";
   }
    
   if (!showCalendarLink)
   {
    parent.document.getElementById("showCalendarLink").style["display"] = "none";
   }
   else
   {
    parent.document.getElementById("showCalendarLink").style["display"] = "";
   }

   if (!showSummaryLink)
   {
    parent.document.getElementById("showSummaryLink").style["display"] = "none";
   }
   else
   {
    parent.document.getElementById("showSummaryLink").style["display"] = "";
   }
   /* don't recaculate schedule qty if in calendar view */
   if (!showSummaryLink) {
     scheduledQtyChanged();
   }
} //end of method

function mySearchOnLoad()
{
  setSearchFrameSize();
    var action = document.getElementById("action");
    action.value = 'result';
    document.genericForm.target='resultFrame';
    parent.showPleaseWait();
    var a = window.setTimeout("document.genericForm.submit();",500);
}

function preUpdateAudit() {
  var result = "";
  var rowNum = document.getElementById('maxRowId');
  for(var i = 0; i < rowNum.value; i++) {
    var rowType = document.getElementById("rowType"+i+"");
    var rowDeleted = document.getElementById("rowDeleted"+i+"");
    if (rowType != null) {
      if (rowType.value == "duplicated") {
        continue;
      }
    }
    if (rowDeleted != null) {
      if (rowDeleted.value == "y") {
        continue;
      }
    }

    var requestedDateToDeliver = document.getElementById("requestedDateToDeliver"+i+"");
    if (requestedDateToDeliver == null) {
      result = "missingData";
      break;
    }else if (requestedDateToDeliver.value.length == 0) {
      result = "missingData";
      break;
    }
    var qty = document.getElementById("requestedQty"+i+"");
    if (qty == null) {
      result = "missingData";
      break;
    }else if (qty.value.length < 1) {
      result = "missingData";
      break;
    }

    //make sure that user does not change qty to less than
    //partial delivered/pick in progress qty
    if (!partialDeliveredAudit(i)) {
      result = "partialDelivered";
      break;
    }

    //make sure that user does not increase qty more than
    //requested qty if the requested date in inside median mr lead time
    if (!medianMrLeadTimeAudit(i)) {
      result = "increaseQtyError";
      break;
    }
  }
  return result;
} //end of method

//returns:
//       true - if everything ok
//       false -if row failed
function partialDeliveredAudit(index) {
  var result = true;
  var status = document.getElementById("status"+index+"");
  if (status.value == "Pick in Progress") {
    var revisedQty = document.getElementById("revisedQuantity"+index+"");
    if (revisedQty.value != null && revisedQty.value.length > 0) {
      var requestedQty = document.getElementById("requestedQty"+index+"");
      var openQty = document.getElementById("openQty"+index+"");
      if ((requestedQty.value*1 - openQty.value*1) > revisedQty.value*1) {
        result = false;
      }
    }
  }
  return result;
}

//returns:
//       true - if everything ok
//       false -if row failed
function medianMrLeadTimeAudit(index) {
  var result = true;
  var revisedQty = document.getElementById("revisedQuantity"+index+"");
  var requestedQty = document.getElementById("requestedQty"+index+"");
  if(revisedQty.value*1 > requestedQty.value*1) {
    var now = new Date();
    //add part lead time to today date
    var medianMrLeadTime = document.getElementById("medianMrLeadTime");
    now.setDate(now.getDate()+(medianMrLeadTime.value*1));
    var requestedDateToDeliverForAudit = document.getElementById("requestedDateToDeliverForAudit"+index+"");
    //date format yyyy-mm-dd hh:mm:ss:ms
    var requestedDate = new Date((requestedDateToDeliverForAudit.value.substring(0,4)*1),((requestedDateToDeliverForAudit.value.substring(5,7)*1)-1),(requestedDateToDeliverForAudit.value.substring(8,10)*1));
    if (now.getTime() > requestedDate.getTime()) {
      result = false;
    }
  }
  return result;
}

//Don't know how to test this function
function draftRevisedQtyChanged(changedIndex,userChangedData) {  
  if(userChangedData != null && userChangedData == 'y')
  	$('userChangedData').value = "y";  

  var rowType = document.getElementById("rowType"+changedIndex+"");
  rowType.value = "revised";

  scheduledQtyChanged();
} //end of method

function revisedQtyChanged(changedIndex,userChangedData) {
//  if(userChangedData != null && userChangedData == 'y')
//  	$('userChangedData').value = "y";
  
  //make sure that user does not change qty to less than
  //partial delivered/pick in progress qty
  if (!partialDeliveredAudit(changedIndex)) {
    alert(messagesData.partialDeliveredQtyError);
  }

  //make sure that user does not increase qty more than
  //requested qty if the requested date in inside median mr lead time
  if (!medianMrLeadTimeAudit(changedIndex)) {
    alert(messagesData.increaseQtyError);
  }

  var userChangedData = document.getElementById("userChangedData");
  userChangedData.value = 'y';
  scheduledQtyChanged();
} //end of method

function scheduledQtyChanged(userChangedData)
{
  if(userChangedData != null && userChangedData == 'y')
  	$('userChangedData').value = "y";
  	
  var x=document.getElementById('maxRowId');
  var y=document.getElementById('totalLines');
  if(x != null) {
    var newQty = 0;
    for(var i=0; i < x.value; i++) {
      var rowType = document.getElementById("rowType"+i+"");
      var rowDeleted = document.getElementById("rowDeleted"+i+"");
      if (rowType != null) {
        if (rowType.value == "duplicated") {
          continue;
        }
      }
      if (rowDeleted != null) {
        if (rowDeleted.value == "y") {
          continue;
        }
      }

      var revisedQty = document.getElementById("revisedQuantity"+i+"");
      if (revisedQty != null) {
        if (revisedQty.value.length > 0) {
          newQty += revisedQty.value*1;
          rowType.value = "revised";
          continue;
        }
      }

      var qty = document.getElementById("requestedQty"+i+"");
      if (qty != null) {
        if (qty.value.length > 0) {
          newQty += qty.value*1;
        }
      }
    }
    var qty = parent.window.frames["searchFrame"].document.getElementById("scheduledQty");
    qty.innerHTML = newQty;
    var scheduledQtyV = parent.window.frames["searchFrame"].document.getElementById("scheduledQtyV");
    scheduledQtyV.value = newQty;
  }
}

function deleteNonDeliveredDate() {
  var dataChanged = false;
  var rowNum = document.getElementById('maxRowId');
  var deletedRow = 0;
  for(var i = 0; i < rowNum.value; i++) {
    var rowType = document.getElementById("rowType"+i+"");
    var rowDeleted = document.getElementById("rowDeleted"+i+"");
    if (rowType != null) {
      if (rowType.value == "duplicated") {
        continue;
      }
    }
    if (rowDeleted != null) {
      if (rowDeleted.value == "y") {
        continue;
      }
    }
    //start deleting row without date_delivered
    var status = document.getElementById("status"+i+"");
    if (status.value == 'Not Issued') {
      selectedRowId = null;
      var displayRowId = document.getElementById("displayRowId"+i+"");
      var selectedDeleteRow = document.getElementById("rowId"+displayRowId.value+"");
      selectedDeleteRow.style["display"] = "none";
      var rowDeleted = document.getElementById("rowDeleted"+i+"");
      rowDeleted.value = "y";
      deletedRow++;
      dataChanged = true;
    }
  } //end of for loop

  if (dataChanged) {
    var totalLines = document.getElementById('totalLines');
    totalLines.value = Math.round(totalLines.value) - deletedRow;
    //setResultFrameSize();
    var userChangedData = window.frames["resultFrame"].document.getElementById("userChangedData");alert("userChangedData"+userChangedData);
    userChangedData.value = 'y';
    scheduledQtyChanged();
  }
} //end of method

function undoDeletedData() {
  var dataChanged = false;
  var rowNum = document.getElementById('maxRowId');
  var undoRow = 0;
  for(var i = 0; i < rowNum.value; i++) {
    var rowDeleted = document.getElementById("rowDeleted"+i+"");
    if (rowDeleted != null) {
      if (rowDeleted.value == "y") {
        selectedRowId = null;
        var displayRowId = document.getElementById("displayRowId"+i+"");
        var selectedDeleteRow = document.getElementById("rowId"+displayRowId.value+"");
        selectedDeleteRow.style["display"] = "";
        rowDeleted.value = "";
        undoRow++;
        dataChanged = true;
      }
    }
  } //end of for loop

  if (dataChanged) {
    var totalLines = document.getElementById('totalLines');
    totalLines.value = Math.round(totalLines.value) + undoRow;
    setResultFrameSize();
    var userChangedData = document.getElementById("userChangedData");
    userChangedData.value = 'y';
    scheduledQtyChanged();
  }
} //end of method

function showRemove()
{
  var selectedDeleteRow = document.getElementById("rowId"+selectedRowId+"");
  selectedDeleteRow.style["display"] = "none";

  var rowDeleted = document.getElementById("rowDeleted"+selectedRowId+"");
  rowDeleted.value = "y";

  selectedRowId = null;

  var totalLines = document.getElementById('totalLines');
  totalLines.value = Math.round(totalLines.value) - 1;

  if (totalLines.value > 0)
  {
  setResultFrameSize();
  }
  else
  {
    myOnload('list');
  }
  var userChangedData = document.getElementById("userChangedData");
  userChangedData.value = 'y';
  scheduledQtyChanged();
}

function selectRow(rowId,flatRowCount)
{
   var selectedRow = document.getElementById("rowId"+rowId+"");
   var selectedRowClass = document.getElementById("colorClass"+rowId+"");
   selectedRow.className = "selected"+selectedRowClass.value+"";
   //select child rows
   var numberOfChild = document.getElementById("numberOfChild"+rowId+"");
   for(i=1; i < numberOfChild.value; i++) {
     var selectedChildRow = document.getElementById("childRowId"+rowId+""+i+"");
     selectedChildRow.className = "selected"+selectedRowClass.value+"";
   }

   if (selectedRowId != null && rowId != selectedRowId)
   {
     var previouslySelectedRow = document.getElementById("rowId"+selectedRowId+"");
     var previouslySelectedRowClass = document.getElementById("colorClass"+selectedRowId+"");
     previouslySelectedRow.className = ""+previouslySelectedRowClass.value+"";
     //unselect child rows
     var numberOfChild = document.getElementById("numberOfChild"+selectedRowId+"");
     for(i=1; i < numberOfChild.value; i++) {
       var previouslySelectedChildRow = document.getElementById("childRowId"+selectedRowId+""+i+"");
       previouslySelectedChildRow.className = ""+previouslySelectedRowClass.value+"";
     }
   }
   selectedRowId =rowId;

   /*Depending on the different conditions I set the right click menu to have different options*/
   var userViewType = document.getElementById("userViewType");
   var status = document.getElementById("status"+flatRowCount+"");
   //users cannot add before or remove, if we already delivered material for row
   if (status.value == 'Not Issued') {
     if (userViewType.value == "user" || userViewType.value == "alternate" || userViewType.value == "editMRQty" || userViewType.value == "approverEditMRQty") {
       toggleContextMenu('showAll');
     }else {
       toggleContextMenu('showEmpty');
     }
   }else {
    var orderedQty = parent.window.frames["searchFrame"].document.getElementById("orderedQty");
    var scheduledQty = parent.window.frames["searchFrame"].document.getElementById("scheduledQty");
     if ((userViewType.value == "user" || userViewType.value == "alternate" || userViewType.value == "editMRQty" || userViewType.value == "approverEditMRQty") &&
          orderedQty.innerHTML.trim() != scheduledQty.innerHTML.trim()) {
       toggleContextMenu('showAdd');
     }else {
       toggleContextMenu('showEmpty');
     }
   }
}  //end of selectRow

/*
function increaseMaxRowIdDisplayRowId() {
  var maxRowId = document.getElementById('maxRowId');
  for (i=selectedRowId+1; i < maxRowId.value*1; i++) {
    var numberOfChild = document.getElementById("numberOfChild"+i+"");
    if (numberOfChild.value*1 == 1) {
      var maxRowIdForDisplayRowId = document.getElementById("maxRowIdForDisplayRowId"+selectedRowId+"0");
      maxRowIdForDisplayRowId.value = maxRowIdForDisplayRowId.value*1+1;
    }else {
      for(i=0; i < numberOfChild.value; i++) {
        var maxRowIdForDisplayRowId = document.getElementById("maxRowIdForDisplayRowId"+selectedRowId+i+"");
        maxRowIdForDisplayRowId.value = maxRowIdForDisplayRowId.value*1+1;
      }
    }
  }
}
*/

function showAddRow() {
  var totalLines = document.getElementById('totalLines');
  var maxRowId = document.getElementById('maxRowId');
  var selectedRowClass = document.getElementById("colorClass"+selectedRowId+"");

  /*insert after the selected row
  var numberOfChild = document.getElementById("numberOfChild"+selectedRowId+"");
  var maxRowIdForDisplayRowId = document.getElementById("maxRowIdForDisplayRowId"+selectedRowId+"0");
  for(i=1; i < numberOfChild.value; i++) {
    maxRowIdForDisplayRowId = document.getElementById("maxRowIdForDisplayRowId"+selectedRowId+i+"");
  }
  alert(selectedRowId+"="+maxRowIdForDisplayRowId.value);
  //var lastSelectedRow = document.getElementById("rowId"+maxRowIdForDisplayRowId.value+"");
  //var rowPosition = lastSelectedRow.rowIndex*1 +1;
  var rowPosition = maxRowIdForDisplayRowId.value*1 +2;
  */
    //inserting row at the end of table
    var rowPosition = maxRowId.value*1 +1;
  var x=document.getElementById('resultsPageTable').insertRow(rowPosition);
  x.id = "rowId" + maxRowId.value;
  x.className= "yellow";
  var requestLineStatus = document.getElementById("requestLineStatus");

  var insertedRow = document.getElementById("rowId"+maxRowId.value+"");
  var cell0=insertedRow.insertCell(0);
  var cell1=insertedRow.insertCell(1);
  if (requestLineStatus.value != 'Draft')
  {
  var cell2=insertedRow.insertCell(2);
  var cell3=insertedRow.insertCell(3);
  var cell4=insertedRow.insertCell(4);
  }
  cell0.innerHTML= getNewDataCell(maxRowId.value);
  cell1.innerHTML= getNewQtyCell(maxRowId.value);
  if (requestLineStatus.value != 'Draft')
  {
  cell2.innerHTML= ""; 
  cell3.innerHTML= "";
  cell4.innerHTML= "";
  }
  if (insertedRow.addEventListener) /*For non IE*/
  {
    insertedRow.addEventListener("mouseup", selectNewRow, true)
  }
  else if (insertedRow.attachEvent) /*For IE*/
  {
   insertedRow.attachEvent("onmouseup",selectNewRow);
  }

  //parent.document.getElementById("showCalendarLink").style["display"] = "none";
  totalLines.value = Math.round(totalLines.value) + 1;
  maxRowId.value = Math.round(maxRowId.value) + 1;
  setResultFrameSize();
//  var userChangedData = document.getElementById("userChangedData");
//  userChangedData.value = 'y';
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
  if (newRowSelectedId*1 >= 0 ) /*This is for Mozilla*/
  {
   selectRow(newRowSelectedId*1,newRowSelectedId*1);
  }
 }
}

/*TODO - make sure dates pop-up is internationalized. Remove diamond hyperlink*/
function getNewDataCell(rowNumber) {
  var cellHTML = "<input name=\"colorClass" + rowNumber + "\" id=\"colorClass" + rowNumber + "\" type=\"hidden\" value=\"yellow\">";
  cellHTML += "<input class=\"inputBox pointer\" readonly type=\"text\" name=\"scheduleDeliveryInputBean[" + rowNumber + "].requestedDateToDeliver\" id=\"requestedDateToDeliver" + rowNumber + "\" value=\"\" onClick=\"return getCalendar(document.genericForm.requestedDateToDeliver"+rowNumber+",null,null,document.getElementById('todaysDate'));\" size=\"10\"/>";
  //cellHTML += "<input name=\"scheduleDeliveryInputBean[" + rowNumber + "].requestedDateToDeliver\" id=\"requestedDateToDeliver" + rowNumber + "\" size=\"8\" maxlenght=\"10\" class=\"inputBox pointer\" readonly value=\"\">";
  //cellHTML += "<a href=\"javascript: void(0);\" class=\"optionTitleBold\" id=\"linkrequestedDateToDeliver"+rowNumber+"\" onclick=\"return showCalendar("+rowNumber+");\">&diams;</a>";
  cellHTML += "<input name=\"scheduleDeliveryInputBean[" + rowNumber + "].rowType\" id=\"rowType" + rowNumber + "\" type=\"hidden\" value=\"new\">";
  cellHTML += "<input name=\"scheduleDeliveryInputBean[" + rowNumber + "].rowDeleted\" id=\"rowDeleted" + rowNumber + "\" type=\"hidden\" value=\"\">";
  cellHTML += "<input name=\"scheduleDeliveryInputBean[" + rowNumber + "].revisedQuantity\" id=\"revisedQuantity" + rowNumber + "\" type=\"hidden\" value=\"\">";
  cellHTML += "<input name=\"scheduleDeliveryInputBean[" + rowNumber + "].refDate\" id=\"refDate" + rowNumber + "\" type=\"hidden\" value=\"\">";
  cellHTML += "<input name=\"scheduleDeliveryInputBean[" + rowNumber + "].refQuantity\" id=\"refQuantity" + rowNumber + "\" type=\"hidden\" value=\"\">";
  cellHTML += "<input name=\"scheduleDeliveryInputBean[" + rowNumber + "].status\" id=\"status" + rowNumber + "\" type=\"hidden\" value=\"Not Issued\">";
  cellHTML += "<input name=\"scheduleDeliveryInputBean[" + rowNumber + "].numberOfChild\" id=\"numberOfChild" + rowNumber + "\" type=\"hidden\" value=\"1\">";
  cellHTML += "<input name=\"scheduleDeliveryInputBean[" + rowNumber + "].openQty\" id=\"openQty" + rowNumber + "\" type=\"hidden\" value=\"\">";
  cellHTML += "<input name=\"scheduleDeliveryInputBean[" + rowNumber + "].displayRowId\" id=\"displayRowId" + rowNumber + "\" type=\"hidden\" value=\""+rowNumber+"\">";
  return cellHTML;
}

function getNewQtyCell(rowNumber) {
  var cellHTML = "<input name=\"scheduleDeliveryInputBean[" + rowNumber + "].requestedQty\" id=\"requestedQty" + rowNumber + "\" size=\"8\" maxlenght=\"10\" class=\"inputBox\" value=\"\" onchange=\"scheduledQtyChanged('y')\">";
  return cellHTML;
}

function generateFrequency()
{
   var companyId  =  document.getElementById("companyId");
   var mrNumber  =  document.getElementById("prNumber");
   var lineItem  =  document.getElementById("lineItem");
   var requestor  =  document.getElementById("requestor");
   var requestLineStatus  =  document.getElementById("requestLineStatus");

   if (confirm(messagesData.frequencyschedulewarn))
   {
    var loc = "mrschedulefreq.do?action=freqSearch&requestLineStatus="+requestLineStatus.value+"&requestor="+requestor.value+"&companyId="+companyId.value+"&prNumber="+mrNumber.value+"&lineItem="+lineItem.value+"";
    children[children.length] =  openWinGeneric(loc,"generateFrequency22","600","300","yes","80","80","no");
   }
}

function closeAllchildren()
{
// You need to add all your submit button vlues here. If not, the window will close by itself right after we hit submit button.
		try {
			for(var n=0;n<children.length;n++) {
                children[n].close();
			}
		} catch(ex)
		{
		}
//Oh, and close self!
		if(!window.closed)
			window.close();
}