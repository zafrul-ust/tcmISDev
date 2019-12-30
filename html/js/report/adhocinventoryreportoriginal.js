function showBaseFields(type) {
    openWinGeneric("basefielddescription.do?type="+type,"basefielddescription","600","400","yes","80","80");
}

function getListSearch()
{
    var listID = document.getElementById("listOption");
    var selecelemet = listID.selectedIndex;
    var listName = listID.options[selecelemet].text;
    listName = listName.replace(/&/gi, "%26");
    listName = listName.replace(/#/gi, "%23");
    listName = listName.replace(/\+/gi, "%2b");
    openWinGeneric("chemicallistsearch.do?Action=New&listId="+listID.value+"&listName="+listName,"chemicallistsearch","600","400","yes","80","80");
}

function getCasSearch()
{
    openWinGeneric("chemicalcassearch.do?Action=New","chemicalcassearch","600","400","yes","80","80");
}

function loadForm() {
	showFacilityOptions();
  //check if a template is opened or saved
  if(document.getElementById("templateName").value.length > 0) {
	 var bar = document.getElementById("bar");
    var foo = document.getElementById("foo");
	 removeReportFieldFromBaseField(bar,"Yes");
    removeReportFieldFromBaseField(foo,"No");
	 //removing space holder
	 var chemicalFieldList = document.getElementById("chemicalFieldList");
	 var reportFieldList = document.getElementById("reportFieldList");
	 removeInitialMessage(chemicalFieldList);
	 removeInitialMessage(reportFieldList);

  } else {
    document.adHocInventoryReportForm.reportGenerationType[0].checked = true;
  }

}
function showFacilityOptions() {
	var facilityArray = altFacilityId;
	var facilityNameArray = altFacilityName;

	var selectedFacilityIndex = 0;
	if(facilityArray != null) {
		var count = 0;
		if(facilityArray.length > 1) {
	   	    setOption(count++,messagesData.myFacilities,"All", "facilityId");
		}
		for (var i=0; i < facilityArray.length; i++) {
			setOption(i+count,facilityNameArray[i],facilityArray[i], "facilityId");
			//selected facility
			if ($v("templateFacilityId") != null) {
				if ($v("templateFacilityId").length > 0) {
					if (facilityArray[i] == $v("templateFacilityId")) {
						selectedFacilityIndex = i+count;
					}
				}
			} //end of if selected facility is not null
		}
	}else {
		setOption(0,messagesData.myFacilities,"All", "facilityId");
	}

	$("facilityId").selectedIndex = selectedFacilityIndex;
	facilityChanged();
}

function facilityChanged() {
	var facilityO = document.getElementById("facilityId");
	var areaO = document.getElementById("areaId");
	var selectedFacility = facilityO.value;

	for (var i = areaO.length; i >= 0;i--) {
		areaO[i] = null;
	}

	showAreaOptions(selectedFacility);
}

function showAreaOptions(selectedFacility) {
	var areaArray = altAreaId[selectedFacility];
	var areaNameArray = altAreaName[selectedFacility];

	var selectedAreaIdIndex = 0;
	if (areaArray != null) {
		var count = 0;
		if (areaArray.length > 1 || areaArray.length == 0) {
			setArea(count++,messagesData.all,"");
		}
		for (var i=0; i < areaArray.length; i++) {
			setArea(i+count,areaNameArray[i],areaArray[i]);
			//selected reporting Entity
			if ($v("templateAreaId") != null) {
				if ($v("templateAreaId").length > 0) {
					if (areaArray[i] == $v("templateAreaId")) {
						selectedAreaIdIndex = i+count;
					}
				}
			} //end of if selected reporting Entity is not null
		}
	}else {
		setArea(0,messagesData.all,"");
	}
	$("areaId").selectedIndex = selectedAreaIdIndex;
	areaChanged();
}

function setArea(href,text,id) {
	var optionName = new Option(text, id, false, false)
	var areaO = document.getElementById("areaId");
	areaO[href] = optionName;
}

function areaChanged() {
	var buildingIdO = document.getElementById("buildingId");
	for (var i = buildingIdO.length; i >= 0;i--) {
		buildingIdO[i] = null;
	}

	showBuildingOptions($v("facilityId")+$v("areaId"));
}

function showBuildingOptions(selectedFacility) {
	var buildingIdArray = altBuildingId[selectedFacility];
	var buildingNameArray = altBuildingName[selectedFacility];

	var selectedBuildingIdIndex = 0;
	if (buildingIdArray != null) {
		var count = 0;
		if (buildingIdArray.length > 1 || buildingIdArray.length == 0) {
			setBuilding(count++,messagesData.all,"");
		}
		for (var i=0; i < buildingIdArray.length; i++) {
			setBuilding(i+count,buildingNameArray[i],buildingIdArray[i]);
			//selected building
			if ($v("templateBuildingId") != null) {
				if ($v("templateBuildingId").length > 0) {
					if (buildingIdArray[i] == $v("templateBuildingId")) {
						selectedBuildingIdIndex = i+count;
					}
				}
			} //end of if selected building is not null
		}
	}else {
		setBuilding(0,messagesData.all,"");
	}
	$("buildingId").selectedIndex = selectedBuildingIdIndex;
    buildingChanged();
}

function setBuilding(href,text,id) {
  var optionName = new Option(text, id, false, false)
  var buildingIdO = document.getElementById("buildingId");
  buildingIdO[href] = optionName;
}

function buildingChanged() {
	var floorIdO = document.getElementById("floorId");
	for (var i = floorIdO.length; i >= 0;i--) {
		floorIdO[i] = null;
	}
	showFloorOptions($v("facilityId")+$v("areaId")+$v("buildingId"));
}

function showFloorOptions(selectedFacility) {
    var floorIdArray = altFloorId[selectedFacility];
	var floorDescriptionArray = altFloorName[selectedFacility];

	var selectedFloorIdIndex = 0;
	if (floorIdArray != null) {
		var count = 0;
		if (floorIdArray.length > 1 || floorIdArray.length == 0) {
			setFloor(count++,messagesData.all,"");
		}
		for (var i=0; i < floorIdArray.length; i++) {
			setFloor(i+count,floorDescriptionArray[i],floorIdArray[i]);
			//selected room
			if ($v("templateFloorId") != null) {
				if ($v("templateFloorId").length > 0) {
					if (floorIdArray[i] == $v("templateFloorId")) {
						selectedFloorIdIndex = i+count;
					}
				}
			} //end of if selected room is not null
		}
	}else {
		setFloor(0,messagesData.all,"");
	}
	$("floorId").selectedIndex = selectedFloorIdIndex;
	floorChanged();
}

function setFloor(href,text,id) {
  var optionName = new Option(text, id, false, false)
  var floorIdO = document.getElementById("floorId");
  floorIdO[href] = optionName;
}

function floorChanged() {
	var roomIdO = document.getElementById("roomId");
	for (var i = roomIdO.length; i >= 0;i--) {
		roomIdO[i] = null;
	}
	showRoomOptions($v("facilityId")+$v("areaId")+$v("buildingId")+$v("floorId"));
}

function showRoomOptions(selectedFacility) {
    var roomIdArray = altRoomId[selectedFacility];
	var roomNameArray = altRoomName[selectedFacility];

	var selectedRoomIdIndex = 0;
	if (roomIdArray != null) {
		var count = 0;
		if (roomIdArray.length > 1 || roomIdArray.length == 0) {
			setRoom(count++,messagesData.all,"");
		}
		for (var i=0; i < roomIdArray.length; i++) {
			setRoom(i+count,roomNameArray[i],roomIdArray[i]);
			//selected room
			if ($v("templateRoomId") != null) {
				if ($v("templateRoomId").length > 0) {
					if (roomIdArray[i] == $v("templateRoomId")) {
						selectedRoomIdIndex = i+count;
					}
				}
			} //end of if selected room is not null
		}
	}else {
		setRoom(0,messagesData.all,"");
	}
	$("roomId").selectedIndex = selectedRoomIdIndex;
}

function setRoom(href,text,id) {
  var optionName = new Option(text, id, false, false)
  var roomIdO = document.getElementById("roomId");
  roomIdO[href] = optionName;
}

function addItem(obj,value,text) {
     index = obj.length;
     obj.options[index]=new Option(text,value);
     obj.options[index].selected = true;
}

function removeItem(obj) {
     if(obj.length <= 0)
          return;
     var index = obj.selectedIndex;
     if(index != -1)
          obj.options[index]=null;
}

function transferItem(objFrom,objTo) {
     if(objFrom.length <= 0) {
        alert(messagesData.thereisnoitemtomove);
     }else {
        //make at least one item is selected from table
        var count = 0;
        for (j=0;j<objFrom.length;j++) {
           if(objFrom.options[j].selected){
              count++;
           }
        }
        if (count > 0) {
			  //removing space holder from drop down
			  if (objTo.options[0].value == "xxblankxx") {
				 objTo.options[0] = null;
			  }
			  //don't move space holder
			  if (objFrom.options[0].value == "xxblankxx") {
				  return;
			  }
			  for(i=0;i<objFrom.length;i++) {
             if(objFrom.options[i].selected){
                 //alert('remove:'+objFrom.options[i].text);
                 addItem(objTo,objFrom.options[i].value,objFrom.options[i].text);
                 removeItem(objFrom,i);
                 i--;
              }
           }
			  //adding space holder to drop down
			  if (objFrom.length == 0){
				  var tmpBlank = "                                    ";
				  addItem(objFrom,"xxblankxx",tmpBlank);
			  }
		  }else {
            alert(messagesData.selectanitemthatyouwanttomove);
        }
     }
}


//This function is called by the buttons generated above and is used to move the selected item in the listbox up or down.
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

function generateReportAudit() {
   var groupByOptionList = document.getElementById("groupByOptionList");
   selectAllItems(groupByOptionList);
   var groupByList = document.getElementById("groupByList");
   selectAllItems(groupByList);
}

function generateAdHocReportAudit() {
	var facilityId = document.getElementById("facilityId");
	document.getElementById("facilityName").value = facilityId[facilityId.selectedIndex].text;

    if ($v("beginDateJsp").length == 0) {
      alert(messagesData.begindaterequired);
      return;
    }
    if ($v("endDateJsp").length == 0) {
      alert(messagesData.enddaterequired);
      return;
    }

    var reportFieldList = document.getElementById("reportFieldList");
   selectAllItems(reportFieldList);
   if(reportFieldList.length <= 0 || reportFieldList.options[0].value == "xxblankxx") {
      alert(messagesData.youmustselectreportfield);
      return;
   }

   var chemicalFieldList = document.getElementById("chemicalFieldList");
   selectAllItems(chemicalFieldList);

   if(document.adHocInventoryReportForm.reportGenerationType[1].checked==true) {
     var reportName = document.getElementById("reportName");
     if(reportName == null || reportName.value == "") {
        alert(messagesData.pleaseenterreportname);
        return;
     }
   }

  var submitValue = document.getElementById("submitValue");
  submitValue.value = "submit";
  var randomNumber = Math.floor(Math.random()*1001);
  var myAction = document.adHocInventoryReportForm.action;
  document.adHocInventoryReportForm.action = myAction.substring(0,myAction.length-3) + randomNumber + ".do";
  openWinGenericViewFile("/tcmIS/common/generatingreport.jsp","myWin"+randomNumber,"800","600","yes","80","80");
  document.adHocInventoryReportForm.target='myWin'+randomNumber;
  addHiddenVariable(document.adHocInventoryReportForm, 'alternateDb', 'Report');
  setTimeout("document.adHocInventoryReportForm.submit()",300);
}

function selectAllItems(obj) {
   for(i=0;i<obj.length;i++) {
      obj.options[i].selected=true;
   }
}

function saveTemplateAudit() {
   var reportFieldList = document.getElementById("reportFieldList");
   selectAllItems(reportFieldList);
   if(reportFieldList.length <= 0) {
      alert(messagesData.youmustselectreportfield);
      return;
   }

  document.adHocInventoryReportForm.target = '';
  openWinGeneric("savetemplate.do?reportType=inventory&templateId="+$v("templateId"),"savetemplate","600","400","yes","80","80");
}

function openTemplateAudit() {
  document.adHocInventoryReportForm.target = '';
  openWinGeneric("opentemplate.do?reportType=inventory","opentemplate","600","400","yes","80","80");
}

function subAdHocForm() {
    var reportFieldList = document.getElementById("reportFieldList");
    selectAllItems(reportFieldList);
    var chemicalFieldList = document.getElementById("chemicalFieldList");
    selectAllItems(chemicalFieldList);
    document.adHocInventoryReportForm.target='';
    document.adHocInventoryReportForm.submit();
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
	document.adHocInventoryReportForm.target = '';
	document.getElementById("submitValue").value = "clearTemplate";
}

function publishTemplateAudit() {
   var reportFieldList = document.getElementById("reportFieldList");
   selectAllItems(reportFieldList);
   if(reportFieldList.length <= 0) {
      alert(messagesData.youmustselectreportfield);
      return;
   }
   document.adHocInventoryReportForm.target='';
	var tmpTemplateName = $v("globalizationLabelLetter")+$v("templateId")+"-"+$v("templateName");
	openWinGeneric("publishtemplate.do?action=publish&reportType=inventory&calledFrom=adhocInventory&templateId="+$v("templateId")+"&templateName="+tmpTemplateName
		            ,"publishTemplate","650","275","yes","80","80");
}

function unpublishTemplateAudit() {
   var reportFieldList = document.getElementById("reportFieldList");
   selectAllItems(reportFieldList);
   if(reportFieldList.length <= 0) {
      alert(messagesData.youmustselectreportfield);
      return;
   }
   document.adHocInventoryReportForm.target='';
	var tmpTemplateName = $v("globalizationLabelLetter")+$v("templateId")+"-"+$v("templateName");
	openWinGeneric("publishtemplate.do?action=unpublish&reportType=inventory&calledFrom=adhocInventory&templateId="+$v("templateId")+"&templateName="+tmpTemplateName
		            ,"publishTemplate","600","360","yes","80","80");
}

