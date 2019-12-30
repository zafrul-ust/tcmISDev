var dhxWins = null;

function loadForm() {
	showFacilityGroupOptions();
	preSelectSavedValuesOnload = true;

  if(document.getElementById("templateName").value.length > 0) {
	 var chemicalFieldList = document.getElementById("chemicalFieldList");
//	 var reportFieldList = document.getElementById("reportFieldList");
//	 removeInitialMessage(chemicalFieldList);
//	 removeInitialMessage(reportFieldList);
//	  if(chemicalFieldList.options.length > 0 && chemicalFieldList.options[0].value != 'xxblankxx')
//		  $('reportListDeleteBtn').style['display'] = "";
//	  else
//		  $('reportListDeleteBtn').style['display'] = "none";

  } else {
    document.adHocMaterialMatrixReportForm.reportGenerationType[0].checked = true;
  }

  setApprOrAttFac();
}

function showMaterialCategory(facilityId)
{
	if(matCatFacIds['ALL'] == 'Y')
		$('matCat').style['display'] = '';
	else if (matCatFacIds[facilityId] == 'Y')
		$('matCat').style['display'] = '';
	else
		{
			$('matCat').style['display'] = 'none';
			$('materialCategoryId').value = '';
			$('materialCategoryName').value = '';
		}
}

//This function is called by the buttons generated above and is used to move the selected item in the listbox up or down.
/*
function move(objMove,bDir) {
   if(objMove.length <= 0) {
      alert(messagesData.thereisnoitemtomove);
      return;
   }
   var idx = objMove.selectedIndex
   if (idx==-1)
      alert(messagesData.youmustfirstselectitemtoreorder)
   else {
      var nxidx = idx+( bDir? -1 : 1)
      if (nxidx<0) nxidx=objMove.length-1
      if (nxidx>=objMove.length) nxidx=0
      var oldVal = objMove[idx].value
      var oldText = objMove[idx].text
      objMove[idx].value = objMove[nxidx].value
      objMove[idx].text = objMove[nxidx].text
      objMove[nxidx].value = oldVal
      objMove[nxidx].text = oldText
      objMove.selectedIndex = nxidx
   }
}
*/

function generateReportAudit() {
   var groupByOptionList = document.getElementById("groupByOptionList");
   selectAllItems(groupByOptionList);
   var groupByList = document.getElementById("groupByList");
   selectAllItems(groupByList);
}

function generateAdHocReportAudit() {

//    var reportFieldList = document.getElementById("reportFieldList");
//   selectAllItems(reportFieldList);
//   if(reportFieldList.length <= 0 || reportFieldList.options[0].value == "xxblankxx") {
//      alert(messagesData.youmustselectreportfield);
//      return;
//   }
   
   if(!validateReportFieldsGrid()) {
       alert(messagesData.youmustselectreportfield);
	   return;
   }
	
   var chemicalFieldList = document.getElementById("chemicalFieldList");
   selectAllItems(chemicalFieldList);

   if(document.adHocMaterialMatrixReportForm.reportGenerationType[1].checked==true) {
     var reportName = document.getElementById("reportName");
     if(reportName == null || reportName.value == "") {
        alert(messagesData.pleaseenterreportname);
        return;
     }
   }
   
  if (!validateListFormatAndContraint())
		  return;
	   
  if(!validateMsdsSearch())
		  return;

  if(!validateGrid())
	  return;
  else
  {
	  	$('gridSubmit').value = gridSubmit;
	  	$('gridDesc').value = gridDesc;
	  	$('listFormat').value= listFormat;
	  	$('chemicalFieldListId').value = chemicalFieldListId;
	  }
/*
   if($('list').checked && listBeanGrid.getRowsNum() > 0 && $('chemicalFieldList').options.length > 0 && !gridListReportListCompare())
	  {
	  	if(confirm("Some selections in the Report Fields Report List are not in the List constraints. Are you sure you want to continue?"))
	  		remDiffFieldListOptions();

	    else
	    	return;
	  }
*/

  var submitValue = document.getElementById("submitValue");
  submitValue.value = "submit";
  var randomNumber = Math.floor(Math.random()*1001);
  var myAction = document.adHocMaterialMatrixReportForm.action;
  document.adHocMaterialMatrixReportForm.action = myAction.substring(0,myAction.length-3) + randomNumber + ".do";
  openWinGenericViewFile("/tcmIS/common/generatingreport.jsp","myWin"+randomNumber,"800","600","yes","80","80");
  document.adHocMaterialMatrixReportForm.target='myWin'+randomNumber;
  addHiddenVariable(document.adHocMaterialMatrixReportForm, 'alternateDb', 'Report');
  setTimeout("document.adHocMaterialMatrixReportForm.submit()",300);
}

var notInListGrid = new Array();
function remDiffFieldListOptions()
{
	var chemicalFieldList = $('chemicalFieldList').options;
	for(var i = notInListGrid.length - 1; i > -1; i--)
		chemicalFieldList[notInListGrid[i]] = null;
}
/*
function gridListReportListCompare()
{
	 notInListGrid = new Array();
	 var notInListGridCount = 0;
	 var chemicalFieldList = $('chemicalFieldList').options;
	 var foundAll = true;
	 for(var i = 0; i < chemicalFieldList.length ;i++)
		 {
		 	var foundLatest = false;
			 for(var j = 0; j < listBeanGrid.getRowsNum();j++)
			 {
				 var pos = listBeanGrid.getRowId(j);
				 if(chemicalFieldList[i].value == gridCellValue(listBeanGrid,pos,'listId'))
				 	foundLatest = true;
			 }
			if(foundLatest == false)
				{
					foundAll = false;
					notInListGrid[notInListGridCount++] = i;
				}
		 }
	 return foundAll;
}
*/
function selectAllItems(obj) {
	/*
   for(i=0;i<obj.length;i++) {
      obj.options[i].selected=true;
   }
   */
}

function saveTemplateAudit() {
   if(!validateMsdsSearch()) return false;

   if(!validateReportFieldsGrid()) {
      alert(messagesData.youmustselectreportfield);
      return false;
   }

  document.adHocMaterialMatrixReportForm.target = '';
  openWinGeneric("savetemplate.do?reportType=materialmatrix&templateId="+$v("templateId"),"savetemplate","600","400","yes","80","80");
}

function openTemplateAudit() {
  document.adHocMaterialMatrixReportForm.target = '';
  openWinGeneric("opentemplate.do?reportType=materialmatrix","opentemplate","600","400","yes","80","80");
}

function removeInitialMessage(obj) {
  if (obj.length == 1 && obj.options[0].value == "xxblankxx") {
     //do nothing
	  //leaving the space holder alone
  }else {
	  for(i=0;i<obj.length;i++) {
		 if(obj.options[i].value == '' || obj.options[i].value == "xxblankxx") {
			obj.options[i] = null;
		 }
	  }
  }
}

function removeReportFieldFromBaseField(objFrom,saveOld) {

	  if(objFrom.length > 0) {
        //make at least one item is selected from table
        var count = 0;
        for (j=0;j<objFrom.length;j++) {
           if(objFrom.options[j].selected){
              count++;
           }
        }
        if (count > 0) {
			  var oldidarr = new Array();
			  var oldnamearr = new Array();
			  for(i=0;i<objFrom.length;i++) {
              if(objFrom.options[i].selected){
					  if (saveOld == "Yes") {
						  oldidarr[oldidarr.length] = objFrom.options[i].value;
					  	  oldnamearr[oldnamearr.length] = objFrom.options[i].text;
					  }
					  removeItem(objFrom,i);
                 i--;
              }
           }
		  }
     }
}

function clearTemplate()
{
	document.adHocMaterialMatrixReportForm.target = '';
	document.getElementById("submitValue").value = "clearTemplate";
}

function publishTemplateAudit() {
    /*
   var reportFieldList = document.getElementById("reportFieldList");
   selectAllItems(reportFieldList);
   if(reportFieldList.length <= 0) {
      alert(messagesData.youmustselectreportfield);
      return;
   }*/
   document.adHocMaterialMatrixReportForm.target='';
	var tmpTemplateName = $v("globalizationLabelLetter")+$v("templateId")+"-"+$v("templateName");
	openWinGeneric("publishtemplate.do?action=publish&reportType=materialmatrix&calledFrom=adhocMaterialMatrix&templateId="+$v("templateId")+"&templateName="+tmpTemplateName
		            ,"publishTemplate","650","275","yes","80","80");
}

function unpublishTemplateAudit() {
    /*
   var reportFieldList = document.getElementById("reportFieldList");
   selectAllItems(reportFieldList);
   if(reportFieldList.length <= 0) {
      alert(messagesData.youmustselectreportfield);
      return;
   }*/
   document.adHocMaterialMatrixReportForm.target='';
	var tmpTemplateName = $v("globalizationLabelLetter")+$v("templateId")+"-"+$v("templateName");
	openWinGeneric("publishtemplate.do?action=unpublish&reportType=materialmatrix&calledFrom=adhocMaterialMatrix&templateId="+$v("templateId")+"&templateName="+tmpTemplateName
		            ,"publishTemplate","600","360","yes","80","80");
}
/*
function popReportBaseFields(reportType) {
	var baseFieldIdString = '';
	var obj = $('reportFieldList');
	if(obj.length > 0) {
		for ( var i=0; i < obj.length; i++) {
			if(baseFieldIdString == '')
				baseFieldIdString = obj.options[i].value;
			else
				baseFieldIdString += "|"+obj.options[i].value;
		}
	}

	var loc = "reportbasefields.do?reportType="+reportType+"&baseFieldIdString="+baseFieldIdString;
	children[children.length] = openWinGeneric(loc,'ReportBaseFields',"720","550","yes","50","50","20","20","no");
}
*/
//This function is called by the buttons generated above and is used to move the selected item in the listbox up or down.
/*
function moveReportFields(bDir) {
   var objMove = $('reportFieldList');

   if(objMove.length <= 0) {
      alert(messagesData.thereisnoitemtomove);
      return;
   }
   var idx = objMove.selectedIndex;
   if (idx==-1)
      alert(messagesData.youmustfirstselectitemtoreorder);
   else {
      var nxidx = idx+( bDir? -1 : 1)
      if (nxidx<0) nxidx=objMove.length-1
      if (nxidx>=objMove.length) nxidx=0
      var oldVal = objMove[idx].value
      var oldText = objMove[idx].text
      objMove[idx].value = objMove[nxidx].value
      objMove[idx].text = objMove[nxidx].text
      objMove[nxidx].value = oldVal
      objMove[nxidx].text = oldText
      objMove.selectedIndex = nxidx
   }
}
*/

function setFields(fieldsString) {
    /*
    var fieldArray=fieldsString.split("|");

	// Set up the hidden report fields
	var obj = $('reportFieldList');
   	for (var i = obj.length; i >= 0;i--)
     	obj[i] = null;

  	for ( var i=0; i < fieldArray.length; i++) {
  		var fieldInfo = fieldArray[i].split(":");
	    setOption(i,fieldInfo[1],fieldInfo[0],'reportFieldList');
	}
	*/
}


function retReportList(selected)
{
	var chemicalFieldList = document.getElementById('chemicalFieldList');
/*	for ( var i = ops.length - 1; i >= 0; i--) {
		ops[i] = null;
	}  */
	if(chemicalFieldList.options[0].value == 'xxblankxx')
		chemicalFieldList[0] = null;

	var count = chemicalFieldList.length;
	for (var i = 0; i < selected.length; i++) {
		setOption(count+i, selected[i].listName, selected[i].listId, "chemicalFieldList");
	}
	  if(chemicalFieldList.options.length > 0)
		  $('reportListDeleteBtn').style['display'] = "";
	  else
		  $('reportListDeleteBtn').style['display'] = "none";
}

var gridSubmit = '';
var gridDesc = '';
var listFormat = '';
var baseFieldId = '';
var baseFieldName = '';
var baseDescription = '';
var chemicalFieldListId = '';
function subAdHocForm()
{
	var validVals = true;
	var validReportFieldsVals = true;
	
	validVals = validateGrid();
	validReportFieldsVals = validateReportFieldsGrid();
	if(validVals && validReportFieldsVals)
	{
		try{
			var target = document.all.item("TRANSITPAGE");
			target.style["display"] = "";
			var target1 = document.all.item("MAINPAGE");
			target1.style["display"] = "none";
		}catch (ex){
		}
		//var reportFieldList = document.getElementById("reportFieldList");
		//selectAllItems(reportFieldList);
		document.adHocMaterialMatrixReportForm.target='';
		$('gridSubmit').value = gridSubmit;
		$('gridDesc').value = gridDesc;
		$('listFormat').value= listFormat;
		$('baseFieldId').value = baseFieldId;
		$('baseFieldName').value = baseFieldName;
		$('baseDescription').value = baseDescription;
		$('chemicalFieldListId').value = chemicalFieldListId;
		document.adHocMaterialMatrixReportForm.submit();
	}
	else
		alert('Please enter valid values in the grid');
}
/*
function reportListDelete(rList)
{
	for(var i =  rList.length - 1 ; i > -1; i--)
		if(rList[i].selected)
			rList[i] = null;
	 var chemicalFieldList = $('chemicalFieldList').options;
	 if(chemicalFieldList.length == 0)
	 {
		  $('reportListDeleteBtn').style['display'] = "none";
		  setOption(0, '', 'xxblankxx', "chemicalFieldList");
		  chemicalFieldList[0].innerHTML = '&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp';
	 }
}
*/
var fieldListSelected = new Array();
/*
function reportListEdit()
{
	if(listBeanGrid.getRowsNum() > 0)
	{
		 var listString = '';
		 var obj = $('chemicalFieldList');
		 if(obj.length > 0) {
			for ( var i=0; i < obj.length; i++) {
				if(listString == '')
					listString = obj.options[i].value;
				else
					listString += "|"+obj.options[i].value;
			}
		 }

		 $('reportListDeleteBtn').style['display'] = "none";
		 setOption(0, '', 'xxblankxx', "chemicalFieldList");
		 var chemicalFieldList = $('chemicalFieldList').options;
		 chemicalFieldList[0].innerHTML = '&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp';
		 for(var i = chemicalFieldList.length - 1; i > 0;i--)
				chemicalFieldList[i] = null;
		fieldListSelected = new Array();
		fieldListSelected = {rows:[]};
	 for(var i = 0; i < listBeanGrid.getRowsNum();i++)
	 {
		//var pos = listBeanGrid.getRowId(i);
		var listName = gridCellValue(listBeanGrid,i,'listName');
		var listId = gridCellValue(listBeanGrid,i,'listId');
		fieldListSelected.rows.push({id:i,
				 data:['Y',
					   false,
					   listName,
					   listId
					  ]});
	 }
	 children[children.length] = openWinGeneric("adhocinventoryreport.do?uAction=editFieldListPreSelect&listString="+listString,"edit_field_list","800","500","yes");
	}
	else
	{
		 listSelected = new Array();
		 var chemicalFieldList = $('chemicalFieldList').options;
		 for(var i = 0; i < chemicalFieldList.length ;i++)
		 	listSelected[i] = chemicalFieldList[i].value;

		 children[children.length] = openWinGeneric("adhocinventoryreport.do?reportType=MaterialMatrix&uAction=editChemListLoad&callerId=reportList","edit_field_list","800","500","yes");
	}
}
*/
var children = new Array();

function closeAllchildren() {
	try {
		for ( var n = 0; n < children.length; n++) {
			try {
				children[n].closeAllchildren();
			}
			catch (ex) {
			}
			children[n].close();
		}
	}
	catch (ex) {
	}
	children = new Array();
}

function showApprOrAttFac(id) {
    //var listFormat0 = document.getElementById("listFormat");
    if(id == 'attachedtofacility') {
        $('approved').checked = false;
        $('usedOnly').checked = false;
        document.getElementById("beginDateJsp").disabled=true;
		document.getElementById("endDateJsp").disabled=true;
        $('reportCriteria').value = 'FACILITY';
        //listFormat0.remove(3);
    }else if(id == 'approved') {
        $('attachedtofacility').checked = false;
        $('usedOnly').checked = false;
        document.getElementById("beginDateJsp").disabled=true;
		document.getElementById("endDateJsp").disabled=true;
        $('reportCriteria').value = 'APPROVED';
        //listFormat0.remove(3);
    }else {
        $('approved').checked = false;
        $('attachedtofacility').checked = false;
        $('reportCriteria').value = 'USED';
        document.getElementById("beginDateJsp").disabled="";
		document.getElementById("endDateJsp").disabled="";
        //if(listFormat0.length == 3) {
        //  var optionName = new Option("lbs", "lbs", false, false)
        //  listFormat0.add(optionName);
        //}
    }
}

function setApprOrAttFac() {
    //var listFormat0 = document.getElementById("listFormat");
    if ($('approved').checked) {
        $('reportCriteria').value = 'APPROVED';
        document.getElementById("beginDateJsp").disabled=true;
		document.getElementById("endDateJsp").disabled=true;
       // listFormat0.remove(3);
    }else if ($('attachedtofacility').checked){
        $('reportCriteria').value = 'FACILITY';
        document.getElementById("beginDateJsp").disabled=true;
		document.getElementById("endDateJsp").disabled=true;
        //listFormat0.remove(3);
    }else {
        $('usedOnly').checked = true;
        $('reportCriteria').value = 'USED';
        document.getElementById("beginDateJsp").disabled="";
		document.getElementById("endDateJsp").disabled="";
        //if(listFormat0 != null && listFormat0.length == 3) {
        //  var optionName = new Option("lbs", "lbs", false, false)
          //listFormat0.add(optionName);
        //}
    }
}

function getMatCat()
{
	 children[children.length] = openWinGeneric("materialcategorysearch.do?facilityId="+ $v('facilityId')+"&vals="+$v('materialCategoryId'),"mat_cat_search","800","500","yes");
}

function setMatCat(idString,nameString)
{
	$('materialCategoryId').value = idString;
	var materialCategoryName = $('materialCategoryName');
	materialCategoryName.value = nameString;
	materialCategoryName.title = nameString;
}

//this function will intialize dhtmlXWindow if it's null
function initializeDhxWins() {
	if (dhxWins == null) {
		dhxWins = new dhtmlXWindows();
		dhxWins.setImagePath("/dhtmlxWindows/codebase/imgs/");
	}
}

function closeTransitWin() {
	if (dhxWins != null) {
		if (dhxWins.window("transitDailogWin")) {
			dhxWins.window("transitDailogWin").setModal(false);
			dhxWins.window("transitDailogWin").hide();
		}
	}
}

function showTransitWin(message) {
	if (message != null && message.length > 0) {
		$("transitLabel").innerHTML = message;
	}else {
		$("transitLabel").innerHTML = messagesData.pleasewait;
	}

	var transitDailogWin = document.getElementById("transitDailogWin");
	transitDailogWin.style.display="";

	initializeDhxWins();
	if (!dhxWins.window("transitDailogWin")) {
		// create window first time
		transitWin = dhxWins.createWindow("transitDailogWin",0,0, 400, 200);
		transitWin.setText("");
		transitWin.clearIcon();  // hide icon
		transitWin.denyResize(); // deny resizing
		transitWin.denyPark();   // deny parking

		transitWin.attachObject("transitDailogWin");
		//transitWin.attachEvent("onClose", function(inputWin){inputWin.hide();});
		transitWin.center();
		// setting window position as default x,y position doesn't seem to work, also hidding buttons.
		transitWin.setPosition(328, 131);
		transitWin.button("minmax1").hide();
		transitWin.button("park").hide();
		transitWin.button("close").hide();
		transitWin.setModal(true);
	}else{
		// just show
		transitWin.setModal(true);
		dhxWins.window("transitDailogWin").show();
	}
}



/*
function reportCriteriaClicked() {
    var listFormat0 = document.getElementById("listFormat");
	 var showPartsInInventory = document.getElementById("showPartsInInventory").value;
	 if(document.adHocMaterialMatrixReportForm.reportCriteria[0].checked == true) {
		 document.getElementById("partNumberCriteria").disabled="";
		 document.getElementById("partNumber").disabled="";
		 document.getElementById("beginDateJsp").disabled=true;
		 document.getElementById("endDateJsp").disabled=true;
		 if (showPartsInInventory == 'true') {
		 	document.getElementById("inventoryDate").disabled=true;
		 }
		 listFormat0.remove(3);
	 }else if(document.adHocMaterialMatrixReportForm.reportCriteria[1].checked == true) {
		 document.getElementById("partNumberCriteria").disabled=true;
		 document.getElementById("partNumber").disabled=true;
		 document.getElementById("beginDateJsp").disabled="";
		 document.getElementById("endDateJsp").disabled="";
		 if (showPartsInInventory == 'true') {
		 	document.getElementById("inventoryDate").disabled=true;
		 }
		if(listFormat0.length == 3) {
          var optionName = new Option("lbs", "lbs", false, false)
          listFormat0.add(optionName);
      }
	 }else if(document.adHocMaterialMatrixReportForm.reportCriteria[2].checked == true) {
		 document.getElementById("partNumberCriteria").disabled=true;
		 document.getElementById("partNumber").disabled=true;
		 document.getElementById("beginDateJsp").disabled=true;
		 document.getElementById("endDateJsp").disabled=true;
		 if (showPartsInInventory == 'true') {
		 	document.getElementById("inventoryDate").disabled=true;
		 }
		 listFormat0.remove(3);
	 }else {
		 document.getElementById("partNumberCriteria").disabled=true;
		 document.getElementById("partNumber").disabled=true;
		 document.getElementById("beginDateJsp").disabled=true;
		 document.getElementById("endDateJsp").disabled=true;
		 if (showPartsInInventory == 'true') {
		 	document.getElementById("inventoryDate").disabled="";
		 }
		 listFormat0.remove(3);
    }
}
 */