 var dhxWins = null;
 var children = new Array(); 
 var tabSec1DataJson = new Array();
 var tabSec2DataJson = new Array();
 var selectedTabId="";
 var dhxWins = null;
 windowCloseOnEsc = true;
 var isLoadOnDemand = false;
 var isValidCoo = false;
 var fieldSeparator = "!@#$%";
 
 function openTab(tabId,tabURL,tabName,tabIcon,noScrolling)
 {
 	var foundExistingDiv = false;
 	var tabNum = 1;
 	var tabDataJson;
 	if (tabId == '0' || tabId == '1' || tabId == '3' || tabId == '4' || tabId == '5' || tabId == '9')
 		tabDataJson = tabSec1DataJson;
 	else
 		tabDataJson = tabSec2DataJson;
 	for (var i=0; i<tabDataJson.length; i++)
 	{
 		if (tabDataJson[i].status == "open")
 	 	tabNum = tabNum + 1;

 		if (tabDataJson[i].tabId == tabId) {
 			foundExistingDiv = true;
 			togglePage(tabId);
 		}
 	}

 	if (!foundExistingDiv) {
 		if (tabId == '0' || tabId == '1' || tabId == '3' || tabId == '4' || tabId == '5' || tabId == '9')
 			list = document.getElementById("firstTabList");
 		else
 			list = document.getElementById("secondTabList");
 		tabIndex = tabDataJson.length;
 		/*Store the pages that are open in an array so that I don't open more than one frame for the same page*/
 		tabDataJson[tabIndex]={tabId:""+tabId+"",status:"open"};

 		if (tabIcon.length ==0)
 		{
 		  tabIcon = "/images/spacer14.gif"; //this is to maintain equal heights for all tabs. the heigt of the icon image has to be 14 piexels
 		}
 		var list;

 		var newNode = document.createElement("li");
 		newNode.id = tabId +"li";
 		var newNodeInnerHTML ="<a href=\"#\" id=\""+tabId+"Link\" class=\"selectedTab\" onclick=\"togglePage('"+tabId+"'); return false;\">";
 		newNodeInnerHTML +="<span id=\""+tabId+"LinkSpan\" class=\"selectedSpanTab\"><img src=\""+tabIcon+"\" class=\"iconImage\">"+tabName;
 		newNodeInnerHTML +="<br class=\"brNoHeight\"><img src=\"/images/minwidth.gif\" width=\""+(tabName.length*2)+"\" height=\"0\"></span></a>";
 		newNode.innerHTML = newNodeInnerHTML;
 		list.appendChild(newNode);
 		
 		togglePage(tabId);
 	}
 }

 function findPos(obj) {
     var curtop = 0;
     if (obj.offsetParent) {
         do {
             curtop += obj.offsetTop;
         } while (obj = obj.offsetParent);
     return [curtop];
     }
 }

 function togglePage(tabId) {
     //toggle page only if the page passed is not the selected tab
 	if (selectedTabId != tabId) {
 		if (tabId == '0' || tabId == '1' || tabId == '3' || tabId == '4' || tabId == '5' || tabId == '9')
 		{
 				
 				tabDataJson = tabSec1DataJson;
 				if (tabId == '1')
 				{
 					$("docUpdateLink").style["display"] = "none";
 					setFooter(document.getElementById("footer1"),"catalogTotalLines");
 					$("footer1").style["display"] = "";
 				}
 				else if(tabId == '3')
 	 			{
 	 				
 	 				$("docUpdateLink").style["display"] = "";
 	 			    setFooter(document.getElementById("footer1"),"inboundDocTotalLines");
 	 			    $("footer1").style["display"] = "";
 	 			       
 	 			}
 	 			else if(tabId == '5')
 	 			{
 	 				$("docUpdateLink").style["display"] = "none";
 	 			    setFooter(document.getElementById("footer1"),"receiptDocTotalLines");
 	 			    $("footer1").style["display"] = "";
 	 			}
 				else if (tabId == '9')
 				{
 					$("docUpdateLink").style["display"] = "none";
 					setFooter(document.getElementById("footer1"),"containerLabelTotalLines");
 					$("footer1").style["display"] = "";
 				}
 				else
 					{
 						$("footer1").style["display"] = "none";
 		 				$("docUpdateLink").style["display"] = "none";
 					}
 				//window.scrollTo(0, 0);
 				//$('get').focus();
 			
 		}
 		else
 		{
 		
 			tabDataJson = tabSec2DataJson;
 			
			if (tabId == '2')
			{				
				setFooter(document.getElementById("footer2"),"msdsTotalLines");
				$("footer2").style["display"] = "";
			}
			else if (tabId == '6')
			{
				 if(isLoadOnDemand && ($('docType').value == '' || ($('docType').value.toUpperCase() != "IT" && $('docType').value.toUpperCase() != "IA"))
						 &&  typeof (flowDownGrid) == 'undefined')
				 {
					 var url = "flowdowncall.do";
					 if ($v("personnelCompanyId") == 'Radian') 
						  url = "/tcmIS/hub/" + url;
					 
					 j$.ajax({
				           type: "POST",
				           url: url,
					   	   data: {
						   hub: $v('sourceHub'),
						   shipToCompanyId: $v('shipToCompanyId'),
						   itemId:$v('itemId'),
						   radianPo: $v('radianPo'),
						   poLine: $v('poLine'),
						   amendment: $v('amendment')
						  },
				           success: initFlowDownGrid
				         });
					 window.document.getElementById("flowDownBeanTransitPage").style["display"] = "";
				 }
				 else
				 {
					 setFooter(document.getElementById("footer2"),"flowDownTotalLines");
				 	 $("footer2").style["display"] = "";
				 }
			}
			else if (tabId == '7')
			{
				 if(isLoadOnDemand && ($('docType').value == '' || ($('docType').value.toUpperCase() != "IT" && $('docType').value.toUpperCase() != "IA"))
						 &&  typeof (specGrid) == 'undefined')
				 {
					 var url = "speccall.do";
					 if ($v("personnelCompanyId") == 'Radian') 
						  url = "/tcmIS/hub/" + url;
					 
					 j$.ajax({
				           type: "POST",
				           url: url,
					   	   data: {hub: $v('sourceHub'),
						   shipToCompanyId: $v('shipToCompanyId'),
						   itemId:$v('itemId'),
						   radianPo: $v('radianPo'),
						   poLine: $v('poLine'),
						   amendment: $v('amendment'),
						   inventoryGroup:$v('inventoryGroup')
						  },
				           success: initSpecGrid
				         });
					 window.document.getElementById("specBeanTransitPage").style["display"] = "";
				 }
				 else
				 {
					 setFooter(document.getElementById("footer2"),"specTotalLines");
					 $("footer2").style["display"] = "";
				 }
					
			}
			else if (tabId == '8')
			{
				 if(isLoadOnDemand && ($('docType').value == '' || ($('docType').value.toUpperCase() != "IT" && $('docType').value.toUpperCase() != "IA"))
						 &&  typeof (associatedPRsGrid) == 'undefined')
				 {
					 var url = "assocprcall.do";
					 if ($v("personnelCompanyId") == 'Radian') 
						  url = "/tcmIS/hub/" + url;
					 
					 j$.ajax({
				           type: "POST",
				           url: url,
				     	   data: {
							   itemId:$v('itemId'),
							   radianPo: $v('radianPo'),
							   inventoryGroup:$v('inventoryGroup')
							  },
				           success: initPrGrid
				         });
					 window.document.getElementById("associatedPRsTransitPage").style["display"] = "";
				}
				 else
				 {
					 setFooter(document.getElementById("footer2"),"associatedPRsTotalLines");
					 $("footer2").style["display"] = "";
				 }
			}
 			else
 			{
 				$("footer2").style["display"] = "none";
 			}

 			//setTimeout("window.scrollTo(0, document.body.scrollHeight)",10);
 				
 		}
        for (var i=0; i<tabDataJson.length; i++) {
 			 var tabLink =  document.getElementById(tabDataJson[i].tabId+"Link");
 			 var tabLinkSpan =  document.getElementById(tabDataJson[i].tabId+"LinkSpan");
 			 if (tabDataJson[i].tabId == tabId && tabDataJson[i].status == "open")
 			 {
 				setVisible(tabDataJson[i].tabId, true);
 				tabLink.className = "selectedTab";
 				tabLink.style["display"] = "";

 				tabLinkSpan.className = "selectedSpanTab";
 				tabLinkSpan.style["display"] = "";
 				selectedTabId = tabId;
 			 }else {
 				setVisible(tabDataJson[i].tabId, false);
 				tabLink.className = "tabLeftSide";

 				tabLinkSpan.className = "tabRightSide";
 			 }
  		}
       /* if(tabId == '5')
				gridRenderAllRows(receiptDocGrid);*/
     }
 	else {
 		var tabLink =  document.getElementById(selectedTabId+"Link");
 		tabLink.style["display"] = "";
 		var tabLinkSpan =  document.getElementById(selectedTabId+"LinkSpan");
 		tabLinkSpan.style["display"] = "";
 	
 		setVisible(tabId, true);
 		}
 }

 function setVisible(tabId, yesno)
 {
    try
    {
        var target =  document.getElementById("tab" + tabId);
        if (yesno)
        {
         //alert("Here setVisible true  "+tabId+"");
         target.style["display"] = "";

        }
        else
        {
          //alert("Here setVisible false  "+tabId+"");
          target.style["display"] = "none";
        }
    }
    catch (ex)
    {
      alert("Here exception in setVisible");
    }
 }
var imgForDeleteName;
var imgForDeleteId;
 function handleRightClick(e)
 {
	 if(e.button === 2)
	 {
		if(e.srcElement.tagName.toLowerCase() == 'img')
		{
			imgForDelete = e.srcElement.src;
			imgForDeleteId = e.srcElement.parentElement.parentElement.id;
			toggleContextMenu('deleteImgMenu');
			return;
		}
		var ele = e.srcElement.parentElement.className;
		if( ele.indexOf('_haas') == -1)
			toggleContextMenu('contextMenu');
	 }
 }
 
 function deleteImg()
 {
	showTransitWinUpdate(messagesData.pleasewait);
	  var src = imgForDelete.substring(imgForDelete.lastIndexOf("/"), imgForDelete.length);
	  var url = "receivingqcchecklist.do";
		 if ($v("personnelCompanyId") == 'Radian') 
			  url = "/tcmIS/hub/" + url;
		 
	 j$.ajax({
         type: "POST",
         url: url,
		data: {documentUrl: src,
		 			  documentId: imgForDeleteId,
		 			  receiptId:$v('receiptId'),
		 			  userAction: "deleteImg"
		 			  },
         success: deleteImgReturn
       });
 }
 
 function deleteImgReturn(msg)
 {
	 stopShowingWait();
	 if(msg != "OK")
		 alert(msg);
	 else
		 {
		 	var tbl = $('imagesTbl');
		 	for(var i = 0; i < tbl.rows.length;i++)
		 		if(tbl.rows[0].cells[i].id == imgForDeleteId)
		 		{
		 			tbl.rows[0].deleteCell(i);
		 			tbl.rows[1].deleteCell(i);
		 			break;
		 		}
		 		
		 }
 }
 
 function myOnLoad()
 {	 
	 	 
	 var el = document.body;
	 if (el.addEventListener){
		  el.addEventListener('mousedown', handleRightClick, false);
	 } else if (el.attachEvent){
		  el.attachEvent('onmousedown', handleRightClick);
	 }
	 
	 stopShowingWait();
	 initGridWithGlobal(componentGridConfig);
 	 initGridWithGlobal(inboundDocGridConfig);
 	 initGridWithGlobal(receiptDocGridConfig);
 	 inboundDocGrid.obj.ondblclick=function(e){
 		var selected = inboundDocGrid.getSelectedRowId();
 		if(gridCellValue(inboundDocGrid,selected,'originalDocumentName') != '')
 			doF6();
 		if (!this.grid.wasDblClicked(e||window.event)) 
			return false; 
		if (this.grid._dclE) 
			this.grid.editCell(e||window.event);  
		(e||event).cancelBubble=true;
		if (_isOpera) return false; //block context menu for Opera 9+
	 };
	 
	 receiptDocGrid.obj.ondblclick=function(e){
	 		doF6();
	 		if (!this.grid.wasDblClicked(e||window.event)) 
				return false; 
			if (this.grid._dclE) 
				this.grid.editCell(e||window.event);  
			(e||event).cancelBubble=true;
			if (_isOpera) return false; //block context menu for Opera 9+
		 };
		 	 
 	 //displayNoSearchSecDurationLocal();
 	 
 	 if (typeof( extraReduction ) != 'undefined')
 	 	setResultSize(extraReduction);
 	 else
 	 	setResultSize();

	 openTab('0','',messagesData.notes,'','');
 	 openTab('1','',messagesData.catalog,'','');
	 openTab('2','',messagesData.msds,'','');
 	 openTab('3','',messagesData.inbounddocuments,'','');
 	 openTab('4','',messagesData.images,'','');
 	 openTab('5','',messagesData.receiptdocuments,'','');
 	 openTab('6','',messagesData.orderedflowdowns,'','');
	 openTab('7','',messagesData.orderedspecs,'','');
	 openTab('8','',messagesData.associatedPRs,'','');
	 openTab('9','',messagesData.labelHistory,'','');

 	 togglePage(0);
 	 togglePage(2);
 	 isLoadOnDemand = true;
	 window.document.getElementById("msdsTransitPage").style["display"] = "";
	 window.document.getElementById("catalogBeanTransitPage").style["display"] = "";
	 
	 var url = "msdscall.do";
	 if ($v("personnelCompanyId") == 'Radian') 
		  url = "/tcmIS/hub/" + url;
	 
 	 j$.ajax({
 	     type: "POST",
 	     url: url,
 		   data: {
 			   itemId:$v('itemId')
 			  },
 	     success: initMsdsGrid
 	  });	
 	 
 	 url = "catalogrqccall.do";
	 if ($v("personnelCompanyId") == 'Radian') 
		  url = "/tcmIS/hub/" + url;
 	 
 	 j$.ajax({
 	     type: "POST",
 	     url: url,
 		   data: {
 			   itemId:$v('itemId'),
 			   inventoryGroup:$v('inventoryGroup'),
 			   hub:$v('sourceHub')
 			  },
 	     success: initCatalogGrid
 	  });
 	 
 	 url = "labelhistorycall.do";
	 if ($v("personnelCompanyId") == 'Radian') 
		  url = "/tcmIS/hub/" + url;
	 
 	 j$.ajax({
 	     type: "POST",
 	     url: url,
 		   data: {
 		 	   search:$v('receiptId')
 			  },
 	     success: initLabelHistoryGrid
 	  });
	
	//autocomplete to show suggestions for valid country abbrevs
	j$().ready(function() {
		// do initial check when page is first loaded. Assuming the value is either empty string or a valid country's abbrev.
		validateCoo($v("countryOfOrigin"));
		
		function log(event, data, formatted) {
			validateCoo(formatted.split(fieldSeparator)[0]);
		} 

		j$("#countryOfOrigin").autocomplete("/tcmIS/hub/receivingqcchecklist.do",{
			extraParams : {
				userAction : function() {
					return "getSimilarCountry";
				},
				fieldSeparator: function() {
					return fieldSeparator;
				}
			},
			width: 200,
			cacheLength:0, //if cache is allow, when user manually enters one of the previous suggestions, the suggestion list will show the entered suggestion 
			scrollHeight: 150,
			parse: function (data) {
				var parsed = [];
				
				if (isWhitespace(data)) {
					validateCoo();
				} else {
					var rows = data.split("\n");
					for (var i=0; i < rows.length; i++) {
						var row = rows[i].trim();
						if (row) {
							parsed[parsed.length] = {
								data: row,
								value: row,
								result: row.split(fieldSeparator)[0]
							};
						}
					}
				}
				
				return parsed;
			},
			formatItem: function(data, i, n, value) {
				var values = value.split(fieldSeparator);
				
				//since the check is for existence, only needs to do for the first option. If not an exact match then user has not
				//entered valid value
				if (i == 1)
					validateCoo(values[0]);
				
				return  values[0] + " (" + values[1] + ")";
			},
			formatResult: function(data) {
				return  data[0].split(fieldSeparator)[0];
			}
		});
		
		j$('#countryOfOrigin').keyup(function () {
			//when user delete the whole input string in one go, we want to return the text box to normal state
			if (isWhitespace($v("countryOfOrigin"))) {
				validateCoo();
			} else {
				//trim in case value contains space
				$("countryOfOrigin").value = $("countryOfOrigin").value.trim(); 
			}
		});

		j$("#countryOfOrigin").result(log).next().click(function() {
			j$(this).prev().search();
		});
	});
	
	//Following functionalities are not applicable to JDA due to technical constraints
	if ($v("isWmsInventoryGroup") == "true") {
		var notApplicableFeatures = [
			"binnedCheckboxDiv",
			"reverseReceiptLink",
			"revertImageLink",
			"binDropdownLabelDiv",
			"bin",
			"addBin0",
			"addShippingSampleLink"
		];
		for (var featureNum = 0; featureNum < notApplicableFeatures.length; featureNum++) {
			try {
				$(notApplicableFeatures[featureNum]).style.display = "none";
			} catch (e) {}
		}
	}
 }

 var minutes = 0;
 var seconds = 0;

 function displayNoSearchSecDurationLocal() {
        var startSearchTime = document.getElementById("startSearchTime");
        var now = new Date();
        var endSearchTime = now.getTime();

        //the duration is in milliseconds
        var searchDuration = (endSearchTime*1)-(startSearchTime.value*1);
        if (searchDuration > (1000*60)) {   //calculating minutes
          minutes = Math.round((searchDuration / (1000*60)));
          var remainder = searchDuration % (1000*60);
          if (remainder > 0) {   //calculating seconds
            seconds = Math.round(remainder / 1000);
          }
        }else {  //calculating seconds
          seconds = Math.round(searchDuration / 1000);
        }	      
 	}

 function setFooter(footer,whichGrid)
 {
 	/*if (minutes != 0) {
     footer.innerHTML = messagesData.recordFound+": "+($v(whichGrid)-1)+" -- "+messagesData.searchDuration+": "+minutes+" "+messagesData.minutes+" "+seconds+" "+messagesData.seconds;
   }else {
     footer.innerHTML = messagesData.recordFound+": "+($v(whichGrid)-1)+" -- "+messagesData.searchDuration+": "+seconds+" "+messagesData.seconds;
   }*/
   footer.innerHTML = messagesData.recordFound+": "+($v(whichGrid)-1);
 }
 
 function doNothing()
 {
	 toggleContextMenu('contextMenu');
 }

 function onRightclick(rowId, cellId) {
	toggleContextMenu('rightClickMenu');
 }
 
 function onInboundRightclick(rowId, cellId) {
	 if(gridCellValue(inboundDocGrid,rowId,'originalDocumentName') != '')
		 toggleContextMenu('rightClickMenu');
	 else if(gridCellValue(inboundDocGrid,rowId,"isAddDocRow") == 'Y')
		 toggleContextMenu('deleteRightClickMenu'); 
	 else
		 toggleContextMenu('contextMenu');
		 
 }


 function doF6() {
	var tmpUrl = "";
	if ($v("secureDocViewer") == 'true')
        tmpUrl = "/DocViewer/client/";
 	if (selectedTabId == '3' && inboundDocGrid.getRowsNum() > 0) {
        var inboundShipmentDocumentId = gridCellValue(inboundDocGrid,1,'inboundShipmentDocumentId');
        var inboundShipmentId = gridCellValue(inboundDocGrid,1,'inboundShipmentId');
        //by default display the first row unless user selected a specific row
        var selected = inboundDocGrid.getSelectedRowId();
 		if(selected != null) {
 			inboundShipmentDocumentId = gridCellValue(inboundDocGrid,selected,'inboundShipmentDocumentId');
            inboundShipmentId = gridCellValue(inboundDocGrid,selected,'inboundShipmentId');
 		}
        children[children.length] = openWinGeneric(tmpUrl+"receiptdocviewer.do?uAction=viewReceiptDoc&calledFrom=inboundDoc&inboundShipmentDocumentId="+inboundShipmentDocumentId+"&companyId="+$v("companyId")+
                                                   "&inboundShipmentId="+inboundShipmentId+"&receiptId="+$v("receiptId"),'selectedDocument' + new Date().getTime(), '1000', '950', 'yes', '80', '80');
     }else if (selectedTabId == '5' && receiptDocGrid.getRowsNum() > 0) {
        var docId = gridCellValue(receiptDocGrid,1,'documentId');
        var receiptId = gridCellValue(receiptDocGrid,1,'receiptId');
        var selected = receiptDocGrid.getSelectedRowId();
        if(selected != null){
            docId = gridCellValue(receiptDocGrid,selected,'documentId');
            receiptId = gridCellValue(receiptDocGrid,selected,'receiptId');
        }
        children[children.length] = openWinGeneric(tmpUrl+"receiptdocviewer.do?uAction=viewReceiptDoc&documentId="+docId+"&receiptId="+receiptId+"&companyId="+$v("companyId"),'selectedDocument' + new Date().getTime(), '1000', '950', 'yes', '80', '80');
    }
 }

 function onSpecRightClickMenu(rowId, cellId) {

	 if(gridCellValue(specGrid,rowId,"content") != '' )
		toggleContextMenu('specRightClickMenu');
 	 else
 		 toggleContextMenu('contextMenu');
 }
 
 function doF8()
 {
		var selected = specGrid.getSelectedRowId();
		var tmpUrl = "../haas/";
		if ($v("secureDocViewer") == 'true')
	        tmpUrl = "/DocViewer/client/";
 		if(selected != null)
 			children[children.length] = openWinGeneric(tmpUrl+'docViewer.do?uAction=viewSpec' +
 		               '&opsEntityId='+$v('opsEntityId')+
 	                   '&inventoryGroup='+encodeURIComponent($v('inventoryGroup'))+
                       '&specLibrary='+encodeURIComponent(gridCellValue(specGrid,selected,"specLibrary"))+
                        '&spec='+ encodeURIComponent(gridCellValue(specGrid,selected,"specId"))+
                        '&companyId='+$v("companyId")
 	                   ,"Spec" + new Date().getTime(),"800","600",'yes' );
 		else
 			children[children.length] = openWinGeneric(tmpUrl+'docViewer.do?uAction=viewSpec' +
 		               '&opsEntityId='+$v('opsEntityId')+
 	                   '&inventoryGroup='+encodeURIComponent($v('inventoryGroup'))+
                       '&specLibrary='+encodeURIComponent(gridCellValue(specGrid,1,"specLibrary"))+
                        '&spec='+ encodeURIComponent(gridCellValue(specGrid,1,"specId"))+
                        '&companyId='+$v("companyId")
 	                   ,"Spec" + new Date().getTime(),"800","600",'yes' );		
 }

function initializeDhxWins() {
	if (dhxWins == null) {
		dhxWins = new dhtmlXWindows();
		dhxWins.setImagePath("/dhtmlxWindows/codebase/imgs/");
	}
}

function showTransitWinUpdate(messageText)
{
	
	var transitDailogWin = document.getElementById("transitDailogWinUpdate");
	transitDailogWin.innerHTML = '<br><br><br>'+messagesData.pleasewait+'<br><br><br><img src="/images/rel_interstitial_loading.gif" align="middle">';
	transitDailogWin.style['display']="";

	initializeDhxWins();
	if (!dhxWins.window("transitDailogWin")) {
		// create window first time
		transitWin = dhxWins.createWindow("transitDailogWin",0,0, 400, 200);
		transitWin.setText(messageText);
		transitWin.clearIcon();  // hide icon
		transitWin.denyResize(); // deny resizing
		transitWin.denyPark();   // deny parking

		transitWin.attachObject("transitDailogWinUpdate");
		//transitWin.attachEvent("onClose", function(inputWin){inputWin.hide();});
		transitWin.center();
		// setting window position as default x,y position doesn't seem to work, also hidding buttons.
		//transitWin.setPosition(328, 131);
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

function addDoc()
{	var rId = new Date();
	var newId = inboundDocGrid.getRowsNum();
	var newData = new Array();
	var cntr = 0;

	newData[cntr++] = 'Y';
	newData[cntr++] = 'Y';
	newData[cntr++] = '';
	newData[cntr++] = '';
	newData[cntr++] = 'N'; 
	newData[cntr++] = '';
	newData[cntr++] = 'N'; 
	newData[cntr++] = '';
	newData[cntr++] = 'N'; 
	newData[cntr++] = '';
	newData[cntr++] = '';
	newData[cntr++] = '';
	newData[cntr++] = '';
	newData[cntr++] = '';
	newData[cntr++] = '';
	newData[cntr++] = '';
	newData[cntr++] = '';
	newData[cntr++] = '';
	newData[cntr++] = 'Y';
	newData[cntr++] = '';
	newData[cntr++] = '';
	inboundDocGrid.addRow(newId+1,newData,newId);
	inboundDocGrid.selectRowById(newId);	
}

	
function updateDocs() {
	
	if(validate())
	{
		showTransitWinUpdate(messagesData.pleasewait);
		var docForm = document.getElementById("docForm");
		cleardocForm(docForm);
	
		var rowsNum = inboundDocGrid.getRowsNum();
		var rowActuallyAddedIfPermission = 0;
		// This reflects the rowId we put in the JSON data 
		for ( var rowpos = 0; rowpos < rowsNum; rowpos++) {
			var rowId = inboundDocGrid.getRowId(rowpos);
			if(gridCellValue(inboundDocGrid,rowId,'permission') == 'Y')
				{
					var isAddDocRow = gridCellValue(inboundDocGrid,rowId,'isAddDocRow');
					addNewFormElement(docForm, "inboundDocBean[" + rowActuallyAddedIfPermission + "].isAddDocRow", isAddDocRow);
					
					if(isAddDocRow == 'N')
					{
						addNewFormElement(docForm, "inboundDocBean[" + rowActuallyAddedIfPermission + "].documentName", gridCellValue(inboundDocGrid,rowId,'documentName')); 
						addNewFormElement(docForm, "inboundDocBean[" + rowActuallyAddedIfPermission + "].documentType", gridCellValue(inboundDocGrid,rowId,'documentType'));
						addNewFormElement(docForm, "inboundDocBean[" + rowActuallyAddedIfPermission + "].companyId", gridCellValue(inboundDocGrid,rowId,'companyId'));
						addNewFormElement(docForm, "inboundDocBean[" + rowActuallyAddedIfPermission + "].fileName", gridCellValue(inboundDocGrid,rowId,'fileName'));
						addNewFormElement(docForm, "inboundDocBean[" + rowActuallyAddedIfPermission + "].receiptId", gridCellValue(inboundDocGrid,rowId,'receiptId'));
						addNewFormElement(docForm, "inboundDocBean[" + rowActuallyAddedIfPermission + "].documentDate", gridCellValue(inboundDocGrid,rowId,'documentDate'));
						addNewFormElement(docForm, "inboundDocBean[" + rowActuallyAddedIfPermission + "].msdsRevisionDate", gridCellValue(inboundDocGrid,rowId,'msdsRevisionDate'));
					}
					else
						{
							addNewFormElement(docForm, "inboundDocBean[" + rowActuallyAddedIfPermission + "].inboundShipmentDocumentId", gridCellValue(inboundDocGrid,rowId,'inboundShipmentDocumentId'));
							addNewFormElement(docForm, "inboundDocBean[" + rowActuallyAddedIfPermission + "].inboundShipmentId", $v("inboundShipmentId"));
						}
					rowActuallyAddedIfPermission++;
				}
		}
		
		addNewFormElement(docForm, "userAction", "updateDocs");
		addNewFormElement(docForm, "TOKEN_NAME", $v('TOKEN_NAME'));
		addNewFormElement(docForm, $v('TOKEN_NAME'), $v('TOKEN_NAME'));
		addNewFormElement(docForm, "search", $v('receiptId')); 
		
		var url = "receivingqcchecklist.do";
		 if ($v("personnelCompanyId") == 'Radian') 
			  url = "/tcmIS/hub/" + url;
		 
		 j$.ajax({
	           type: "POST",
	           url: url,
	           data: j$("#docForm").serialize(), // serializes the form's elements.
	           success: updateDocReturn
	         });
	}
}

function updateDocReturn(res)
{
	var results = jQuery.parseJSON(res);

	 if(results.Message != null && results.Message != undefined) 
		 alert(messagesData.updateerr);
	else
	{
		 if(results.DocToReceiptResults != null && results.DocToReceiptResults != undefined)
			 {
				var rowsNum = receiptDocGrid.getRowsNum();
				// This reflects the rowId we put in the JSON data 
				for ( var rowpos = 0; rowpos <= rowsNum; rowpos++) {
					receiptDocGrid.deleteRow(rowpos);
				}
				
			  	var r = results.DocToReceiptResults;
				for ( var rowpos = 0; rowpos < r.length; rowpos++) {
					
					var newData = new Array();
					var cntr = 0;
					newData[cntr++] = 'N'; // Permission
					newData[cntr++] = r[rowpos].documentType; //
					newData[cntr++] = r[rowpos].companyId; // 
					newData[cntr++] = r[rowpos].documentName; //
					var date = r[rowpos].documentDate;
					newData[cntr++] = (date != undefined ? date.substring(0,date.indexOf(' ')):""); // 
					newData[cntr++] = r[rowpos].documentUrl; // 
					newData[cntr++] = r[rowpos].documentId; // 
					newData[cntr++] = r[rowpos].receiptId;
					
					var ind = receiptDocGrid.getRowsNum();
					var rowid = ind*1+1;
					receiptDocGrid.addRow(rowid, newData);				
				}
				$('receiptDocTotalLines').value = r.length + 1;
				
			 }
		 if(results.AddedToShipIdResults != null && results.AddedToShipIdResults != undefined)
			 {
				var rowsNum = inboundDocGrid.getRowsNum();

				//Refresh the newly added row's data
				//if row is added through page, it does not have a file associated with it yet, so disable all editable options
				for ( var rowId = 1; rowId <= rowsNum; rowId++) {
					if(gridCellValue(inboundDocGrid,rowId,'isAddDocRow') == 'Y')
					{
						var oldRowPostion = inboundDocGrid.getRowIndex(rowId);
						var newData = new Array();
						var cntr = 0;
						
						newData[cntr++] = 'N';
						newData[cntr++] = '';
						newData[cntr++] = gridCellValue(inboundDocGrid,rowId,'inboundShipmentDocumentId');
						for(var i = 0; i < 16 ;i++)
								newData[cntr++] = '';
						
						inboundDocGrid.deleteRow(rowId);
						inboundDocGrid.addRow(rowId, newData, oldRowPostion);
						setGridCellValue(inboundDocGrid,rowId,'isAddDocRow', 'N');
					}
				}
			 }
			 
	}

	 stopShowingWait();
}

function deleteInboundRow()
{
	inboundDocGrid.deleteRow(inboundDocGrid.getSelectedRowId());
}


function cleardocForm(docForm)
{
	if ( docForm.hasChildNodes() ) {
		while ( docForm.childNodes.length >= 1 ) {
			docForm.removeChild(docForm.firstChild);  
		}
	}
}


//helper function to add elements to the form
function addNewFormElement(inputForm, elementName, elementValue){
	var input = document.createElement("input");
	input.setAttribute("type", "hidden");
	input.setAttribute("name", elementName);
	input.setAttribute("value", elementValue);
	inputForm.appendChild(input);
}

function validate() {
	var rowsNum = inboundDocGrid.getRowsNum();
	var oneRowUpdated = false;
	var warnDocsWithNoNamesOnce = true;
	var docsWithNoTypes = '';
	// This reflects the rowId we put in the JSON data
	for (var rowpos = 1; rowpos <= rowsNum; rowpos++) {
		if (gridCellValue(inboundDocGrid, rowpos, "isAddDocRow") == 'Y' || !isEmpty(gridCellValue(inboundDocGrid, rowpos, "documentName")) || !isEmpty(gridCellValue(inboundDocGrid, rowpos, "documentType")))
			oneRowUpdated = true;
		else
			continue;

		if (warnDocsWithNoNamesOnce && isEmpty(gridCellValue(inboundDocGrid, rowpos, "documentName")) && !isEmpty(gridCellValue(inboundDocGrid, rowpos, "documentType"))) {
			var noNameCheck = confirm(messagesData.docswithnonames);
			if (!noNameCheck)
				return false;
			else
				warnDocsWithNoNamesOnce = false;
		} else if (!isEmpty(gridCellValue(inboundDocGrid, rowpos, "documentName")) && isEmpty(gridCellValue(inboundDocGrid, rowpos, "documentType")))
			docsWithNoTypes += gridCellValue(inboundDocGrid, rowpos, "documentName") + "\n";

		var inboundShipmentDocumentId = gridCellValue(inboundDocGrid, rowpos, "inboundShipmentDocumentId");
		if (inboundShipmentDocumentId != null && typeof (inboundShipmentDocumentId) != 'undefined' && !isNonnegativeInteger(inboundShipmentDocumentId)) {
			alert(messagesData.enterDocIds);
			return false;
		}
	}

	if (!oneRowUpdated) {
		alert(messagesData.entryvalues);
		return false;
	} else if (!isEmpty(docsWithNoTypes)) {
		alert(messagesData.docswithnotype + '\n' + docsWithNoTypes);
		return false;
	}

	return true;
}
	
	function showCatMenu()
	{
		toggleContextMenu('catalogRightClickMenu');
	}
	
	
	function qualitySummary() {
		var selectedRowId = catalogGrid.getSelectedRowId();
		children[children.length] = openWinGeneric('qualitysummary.do?catPartNo='+ encodeURIComponent(gridCellValue(catalogGrid,selectedRowId,"catPartNo") ) +
		               '&catalogId='+encodeURIComponent(gridCellValue(catalogGrid,selectedRowId,"catalogId")) +
		               '&catalogCompanyId='+encodeURIComponent(gridCellValue(catalogGrid,selectedRowId,"catalogCompanyId"))+
	                   '&partGroupNo='+ gridCellValue(catalogGrid,selectedRowId,"partGroupNo")+
	                   '&inventoryGroup='+ encodeURIComponent(gridCellValue(catalogGrid,selectedRowId,"inventoryGroup"))+
	                   '&catalogDesc='+encodeURIComponent(gridCellValue(catalogGrid,selectedRowId,"catalogDesc")) +
	                   '&opsEntityId='+ $v("opsEntityId")
	                   ,"QualitySummary","800","600",'yes' );
		}
	
	function closeAllchildren() 
	{ 
		if ($v("userAction") != "updateDocs") {
			try {
				for(var n=0;n<children.length;n++) {
					children[n].close(); 
				}
			} catch(ex)
			{
				alert(ex);
			}
		} 
	} 
	
	function documentTypeChanged(rowid)
	{
		var documentName = $('documentName'+rowid);
		var documentType = $('documentType'+rowid);
		
		var docForm = document.getElementById("docForm");
		cleardocForm(docForm);
		addNewFormElement(docForm, "userAction", "checkAlreadyAssociated");
		addNewFormElement(docForm, "receiptId", $v('receiptId')); 
		addNewFormElement(docForm, "documentId", gridCellValue(inboundDocGrid,rowid,'inboundShipmentDocumentId')); 
		addNewFormElement(docForm, "documentUrl", gridCellValue(inboundDocGrid,rowid,'fileName')); 
		
		var url = "receivingqcchecklist.do";
		 if ($v("personnelCompanyId") == 'Radian') 
			  url = "/tcmIS/hub/" + url;
		 
		 j$.ajax({
	           type: "POST",
	           url: url,
	           data: j$("#docForm").serialize(), // serializes the form's elements.
	           success: checkAlreadyAssociatedReturn
	         });

		if(documentType.value != ''){
			if(documentType.value == 'MSDS'){
				//addMSDSRevisionDate();

				var loc = "newrevisiondate.do";
				showWait(formatMessage(messagesData.waitingFor, messagesData.revisionDate));
				children[children.length] = openWinGeneric(loc,"newrevisiondate","300","200","yes","300","500","20","20","no");
				
			}
			else {
				showTransitWinUpdate(messagesData.pleasewait);
			}
				
			
			if(documentName.value == '')
				documentName.value= documentType.options[documentType.selectedIndex].text;
		}
	}

	function checkAlreadyAssociatedReturn (res){
		var results = jQuery.parseJSON(res);
		stopShowingWait();
		if (results.associated) {
			alert(results.msg);
		}
	}

	function confirmReceivingQcChecklistReport()
	{
		var rdocDoubleCheck = true;
		if (receiptDocGrid.getRowsNum() == 0) {
			rdocDoubleCheck = confirm(messagesData.noreceiptdocs);
		}
		if(rdocDoubleCheck && checkChemicalReceivingQcInput())
		{
			showTransitWinUpdate('Confirming Checklist');
			document.genericForm.target = ''; // Form name "genericForm" comes from struts config file
												// $('formVariableName') is a utility function that does a document.getElementById('variableName')
											// $v('formVariableName') does the same with document.getElementById('variableName').value
			
			$('userAction').value = 'confirmChecklist';
			
			if (componentGrid != null) {
				// This function prepares the grid dat for submitting top the server
				componentGrid.parentFormOnSubmit();
			}		
			
			// Reset search time for update
			var now = new Date();
			var startSearchTime = document.getElementById("startSearchTime");
			startSearchTime.value = now.getTime();
			
			document.genericForm.submit(); // Submit the form
		}
	}
	
	function myGetCalendarE(rowid)
	{
		return getCalendar($('expireDateDisplay'+rowid),null,null,document.genericForm.todayoneyear,null,'Y');
	}
	
	function setExpDateChanged(rowid) {
		// set auto-calculate to No, will reset to Yes if this was called from the auto-calculate method
		$("calculateQvrExpDate").value = "N";
		var setDate = $v('expireDateDisplay'+rowid);
		if(setDate == 'Indefinite')
			setDate = "01-" + month_abbrev_Locale_Java[pageLocale][0] + "-3000";
		componentGrid.cellById(rowid + 1, componentGrid.getColIndexById("expireDate")).setValue(setDate);
		checkExpireDate();
	}
	
	
	function myGetCalendarMFG(rowid)
	{
		return getCalendar($('mfgLabelExpireDateDisplay'+rowid),null,null,document.genericForm.todayoneyear,null,'Y');
	}
	
	function setMFGExpDateChanged(rowid) {
		var setDate = $v('mfgLabelExpireDateDisplay'+rowid);
		if(setDate == 'Indefinite')
			setDate = "01-" + month_abbrev_Locale_Java[pageLocale][0] + "-3000";
		componentGrid.cellById(rowid + 1, componentGrid.getColIndexById("mfgLabelExpireDate")).setValue(setDate);
		checkExpireDate();
	}
	
	function myGetCalendarDOP(rowid)
	{
		return getCalendar($('dateOfRepackagingDisplay'+rowid),document.genericForm.dateOfManufacture,null,null,document.genericForm.dateOfReceipt,null,'Y');
	}
	
	function setDOPChanged(rowid) {
		var setDate = $v('dateOfRepackagingDisplay'+rowid);
		componentGrid.cellById(rowid + 1, componentGrid.getColIndexById("dateOfRepackaging")).setValue(setDate);
	}
	
	
	function checkReceiptStatus() {
        //the reason for this is that a part can be incomoing lab test required and Quality Control Item
        var tmpVal = true;
        if ($("incomingTesting").checked)
            tmpVal = lotStatusChangedOk();

        if (tmpVal) {
            var lotStatus = document.getElementById("lotStatus");
            var origLotStatus = document.getElementById("origLotStatus" );
            var qualityControlItem = $v("qualityControlItem");
            if (lotStatus.value.length == 0 && qualityControlItem != 'N') {
                var selecelemet = lotStatus.selectedIndex;
                var testelementtext = lotStatus.options[selecelemet].text;
                if (origLotStatus == null || origLotStatus.value != testelementtext) {
                    alert(messagesData.nopermissiontochangestatus + " "
                            + testelementtext + ".")
                }

                i = 0;
                while (i < lotStatus.length) {
                    var elementtext = lotStatus.options[i].text;

                    if (elementtext == origLotStatus.value) {
                        lotStatus.selectedIndex = i;
                        break;
                    }
                    i += 1;
                }
            } else {
                if (lotStatus.value == "Customer Purchase"
                        || lotStatus.value == "Write Off Requested"
                        || lotStatus.value == "3PL Purchase") {
                    loc = "terminalstatusrootcause.do?lotStatus=";
                    if ($v("personnelCompanyId") == 'Radian') 
          			  loc = "/tcmIS/hub/" + loc;
                    loc = loc + lotStatus.value + "&rowNumber=";
                    try {
                        children[children.length] = openWinGeneric(loc,
                                "terminal_root_cause", "500", "300", "no");
                    } catch (ex) {
                        openWinGeneric(loc, "terminal_root_cause", "500", "300", "no");
                    }
                } else {
                    lotStatusRootCause = document.getElementById("lotStatusRootCause");
                    lotStatusRootCause.value = "";

                    responsibleCompanyId = document.getElementById("responsibleCompanyId");
                    responsibleCompanyId.value = "";

                    lotStatusRootCauseNotes = document.getElementById("lotStatusRootCauseNotes" );
                    lotStatusRootCauseNotes.value = "";
                }
            }
        } //end of if lotStatusChangeOk
    } //end of method


function lotStatusChangedOk() {
    var result = true;
    var lotStatus = document.getElementById("lotStatus");
    var selectedLotStatus = $v("lotStatus");
    var origLotStatus = $v("origLotStatus" );
    for (var i = 0; i < pickableArr.length; i++) {
        if (selectedLotStatus == pickableArr[i] && $v("labTestComplete") == 'N') {
            alert(messagesData.noCompletedIncomingTest);
            result = false;
            break;
        }
    }

    if (!result) {
        //reset lot status to original
        var j = 0
        while (j < lotStatus.length) {
            var elementtext = lotStatus.options[j].text;
            if (elementtext == origLotStatus) {
                lotStatus.selectedIndex = j;
                break;
            }
            j += 1;
        }
    }
    return result;
} //end of method


    function checkChemicalReceivingQcInput() {
		var receiptId = document.getElementById("receiptId" + "");
		var errorMessage = messagesData.forreceipt + " " + receiptId.value + " "
				+ messagesData.validvalues + " \n\n";
		var errorCount = 0;
		var warnCount = 0;
		var expireError = false;

			var shelfLifeBasis = $v("receiptShelfLifeBasis" );
			var supplierShipDate = dateToIntString($v("vendorShipDate"));
			var dateOfReceipt = dateToIntString($v("dateOfReceipt"));
			var dateOfManufacture = dateToIntString($v("dateOfManufacture"));
			if ($v("vendorShipDate" + "").length > 0
					&& (supplierShipDate > dateOfReceipt || supplierShipDate < dateOfManufacture)) {
				errorMessage = errorMessage + " " + messagesData.actsupshpdate
						+ "\n";
				errorCount = 1;
			}

			 if (shelfLifeBasis == "M" && $v("dateOfManufacture").length <= 0) {
				errorMessage = errorMessage + " " + messagesData.dom + "\n";
				errorCount = 1;
			}
			else if (shelfLifeBasis == "S" && $v("dateOfShipment").length <= 0) {
				errorMessage = errorMessage + " " + messagesData.dos + "\n";
				errorCount = 1;
			}
			else if (shelfLifeBasis == "R" && $v("dateOfReceipt").length <= 0) {
				errorMessage = errorMessage + " " + messagesData.dor + "\n";
				errorCount = 1;
			}
			
			
			var dateOfShipment = dateToIntString($v("dateOfShipment" ));
			if ($v("dateOfManufacture").length > 0
					&& $v("dateOfShipment" + "").length > 0
					&& dateOfManufacture > dateOfShipment) {
				errorMessage = errorMessage + " " + messagesData.shipbeforemanufacture + "\n";
				errorCount = 1;
			}

			var qaStatement = $v("qaStatement" + "");
			if (qaStatement != null && qaStatement != "" && qaStatement != "1"
					&& qaStatement != "2") {
				errorMessage = errorMessage + " " + messagesData.qastatement + "\n";
				$("qaStatement" + "").value = "";
				errorCount = 1;
			}

			try {
				var lotStatusValue = document.getElementById("lotStatus" + "").value;
			} catch (ex) {
				lotStatusValue = "";
			}

				try {
					var lotStatus = document.getElementById("lotStatus" + "");
				 	var qualityControlItem = $v("qualityControlItem" + "");
					if (lotStatus.value.trim() == "Incoming") {
						errorMessage = errorMessage + " " + messagesData.lotstatus
								+ " " + messagesData.cannotbe + " "
								+ messagesData.incoming + ". \n";
						errorCount = 1;
					} else if (lotStatus.value.length == 0 && qualityControlItem != 'N') {
						var selecelemet = lotStatus.selectedIndex;
						var testelementtext = lotStatus.options[selecelemet].text;

						errorMessage = errorMessage + " "
								+ messagesData.nopermissionstoqcstatus + " "
								+ testelementtext + ".";
						errorCount = 1;
					}
				} catch (ex) {
				}
				
				
				var rowsNum = componentGrid.getRowsNum();
				for ( var rowpos = 0; rowpos < rowsNum; rowpos++) {
					var rowId = componentGrid.getRowId(rowpos);
					var expireDateString = gridCellValue(componentGrid,rowId,'expireDate');
					if (expireDateString.trim().length == 0) {
						errorMessage = errorMessage + " " + messagesData.expiredate + "\n";
						errorCount = 1;
						expireDateString.value = "";
						expireDateError = true;
					}
					else if(dateToIntString(expireDateString) < dateToIntString($v("minimumExpireDate")))
					{	
							if(!confirm(messagesData.expdatelessthanminexpdatecontinuewithconfirm.replace(/[{]0[}]/g,receiptId.value)))
								return false;	
					}
					
					var mfgLot = gridCellValue(componentGrid,rowId,'mfgLot');
					if (mfgLot.trim().length == 0) {
						errorMessage = errorMessage + " " + messagesData.mfglot + ". \n";
						errorCount = 1;
					}
				}
		
		if (!isValidCoo) {
			errorMessage += messagesData.cooInvalid;
			errorCount = 1;
		}
		
		if (errorCount > 0) {
			alert(errorMessage);
			return false;
		}
		
		
		if(receiptDocGrid.getRowsNum() == 0 && !confirm(messagesData.noreceiptdocs))
			return false;
				
			
		return true;
	}
	
function changeMlItem() {

					var loc = "receivingitemsearchmain.do?receipts="
							+ $v('receiptId') + "";
					
					if ($v("personnelCompanyId") == 'Radian') 
						  loc = "/tcmIS/hub/" + loc;
					
					var hubNumber = document.getElementById("sourceHub").value;
					loc = loc + "&hubNumber=" + hubNumber;
					loc = loc + "&listItemId=" + $("itemId").value;
					loc = loc + "&inventoryGroup="
							+ encodeURIComponent($("inventoryGroup").value);
					loc = loc + "&catPartNo=" + $("catPartNo").value;
					loc = loc + "&catalog=" + $("catalogId").value;
					loc = loc + "&catalogCompanyId="
							+ $("catalogCompanyId").value;

					try {
						children[children.length] = openWinGeneric(loc,
								"changeItem", "800", "400", "yes", "80", "80");
					} catch (ex) {
						openWinGeneric(loc, "changeItem", "800", "400", "yes",
								"80", "80");
					}
		}



function submitGenLabel(actionElementName) {

	showTransitWinUpdate(messagesData.pleasewait);
		var actionElement = document
				.getElementById("userAction");
		actionElement.value = "submitPrint";
		
		var submitDocumentLabelsPrint = document
		.getElementById("submitPrint");
		submitDocumentLabelsPrint.value = "submitPrint";
		
		if (componentGrid != null) {
			// This function prepares the grid dat for submitting top the server
			componentGrid.parentFormOnSubmit();
		}		
		
		document.genericForm.submit();
}


function printContainerLabels() {
	var loc = "printcontainerlabels.do?labelReceipts=" + $v('receiptId') + "";	
	loc = loc + "&paperSize=31";
	loc = loc + "&hubNumber=" + $v('hub');
	
	if ($v("personnelCompanyId") == 'Radian') 
		  loc = "/tcmIS/hub/" + loc;
	
	var skipKitLabels = document.getElementById("skipKitLabels");
	if (skipKitLabels.checked) {
		loc = loc + "&skipKitLabels=Yes";
	}
	else {
		loc = loc + "&skipKitLabels=No";
	}
	try {
		parent.children[parent.children.length] = openWinGeneric(loc, "printContainerLabels11", "800", "500", "yes", "80", "80");
	}
	catch (ex) {
		openWinGeneric(loc, "printContainerLabels11", "800", "500", "yes", "80", "80");
	}
}

function printRecDocumentLabels() {

	var loc = "printcontainerlabels.do?labelReceipts=" + $v('receiptId') + "";
	loc = loc + "&paperSize=receiptDocument";
	loc = loc + "&hubNumber=" + $v('hub');
	
	if ($v("personnelCompanyId") == 'Radian') 
		  loc = "/tcmIS/hub/" + loc;
	
	var skipKitLabels = document.getElementById("skipKitLabels");
	if (skipKitLabels.checked) {
		loc = loc + "&skipKitLabels=Yes";
	}
	else {
		loc = loc + "&skipKitLabels=No";
	}
	try {
		children[children.length] = openWinGeneric(loc, "printContainerLabels11", "800", "500", "yes", "80", "80");
	}
	catch (ex) {
		openWinGeneric(loc, "printRecDocumentLabels11", "800", "500", "yes", "80", "80");
	}
}

function printReceiptDetailLabels() {
	var loc = "printcontainerlabels.do?labelReceipts=" + $v('receiptId') + "";
	loc = loc + "&paperSize=receiptDetail";
	loc = loc + "&hubNumber=" + $v('hub');
	
	if ($v("personnelCompanyId") == 'Radian') 
		  loc = "/tcmIS/hub/" + loc;
	
	var skipKitLabels = document.getElementById("skipKitLabels");
    loc = loc + "&skipKitLabels=Yes";
	try {
		children[children.length] = openWinGeneric(loc, "printContainerLabels11", "800", "500", "yes", "80", "80");
	}
	catch (ex) {
		openWinGeneric(loc, "printRecDocumentLabels11", "800", "500", "yes", "80", "80");
	}
}


function doPrintrelabel()
{
   var receiptId = $v('receiptId');
   var reLabelUrl = "/tcmIS/Hub/reprintnoinvenlabels?session=Active&generate_bols=yes";
   var paperSize  =  "";
   reLabelUrl += "&boldetails=" + paperSize ;
   reLabelUrl += "&receiptId=" + receiptId ;
   reLabelUrl += "&HubNoforlabel=" + + $v('hub');

   openWinGeneric(reLabelUrl,"Generate_relabels","640","600","yes")
}

function selectPrintType()
{

	updateExpirationDates();
	
	switch($v('printType'))
	{
		case"RL":
			printReceivingBoxLabels(); 
			break;
		case"GL":
			printContainerLabels(); 
			break;
		case "DL":
			 printRecDocumentLabels(); 
			break;
		case "RDL":
			printReceiptDetailLabels();
			break;
		case "EL":
			doPrintrelabel();
			break;
		case "RTK":
			printRTKLabel();
		default:

	}
}

function updateExpirationDates() {
	var rowsNum = componentGrid.getRowsNum();
	for ( var rowpos = 0; rowpos < rowsNum; rowpos++) {
		var rowId = componentGrid.getRowId(rowpos);
		var docForm = document.getElementById("docForm");
		cleardocForm(docForm);
		addNewFormElement(docForm, "userAction", "updateExpireDate");
		addNewFormElement(docForm, "TOKEN_NAME", $v('TOKEN_NAME'));
		addNewFormElement(docForm, $v('TOKEN_NAME'), $v('TOKEN_NAME'));
		addNewFormElement(docForm, "receiptId", $v('receiptId'));
		addNewFormElement(docForm, "componentId", gridCellValue(componentGrid,rowId,'componentId'));
		addNewFormElement(docForm, "expireDate", gridCellValue(componentGrid,rowId,'expireDate'));
		var url = "receivingqcchecklist.do";
		 if ($v("personnelCompanyId") == 'Radian') 
			  url = "/tcmIS/hub/" + url;
		 
		 j$.ajax({
	           type: "POST",
	           async: false,
	           url: url,
	           data: j$("#docForm").serialize()
	         });	
	}
}

function printReceivingBoxLabels() {
		var loc = "printreceiptboxlabels.do?labelReceipts="
				+ $v('receiptId') + "";
		loc = loc + "&paperSize=64";
		
		if ($v("personnelCompanyId") == 'Radian') 
			  loc = "/tcmIS/hub/" + loc;
		
		try {
			children[children.length] = openWinGeneric(loc, "printBinLabels11",
					"800", "500", "yes", "80", "80");
		} catch (ex) {
			openWinGeneric(loc, "printBinLabels11", "800", "500", "yes", "80",
					"80");
		}
}

function printReceivingQcDocLabels(hubNumber) {
	var loc = "receivingqclabels.do?";
	//var paperSize = document.getElementById("paperSize").value;
	loc = loc + "paperSize=receiptDocument";
	loc = loc + "&hubNumber=" + hubNumber;
	
	if ($v("personnelCompanyId") == 'Radian') 
		  loc = "/tcmIS/hub/" + loc;
	
	if( $v('groupReceiptDoc') == "Y" ) {
			loc += "&groupReceiptDoc=Y";
			groupReceiptDoc = false;
			$('groupReceiptDoc').value = "";
	}
	
	if (hubNumber == "2106") {
		printNormandaleQCLabels();
	} else {
		loc = loc + "&hubNumber=" + hubNumber;
		printQCLabels(loc);
	}
}

function printReceivingQcReceiptLabels(hubNumber) {
	var loc = "receivingqclabels.do?";
	loc = loc + "paperSize=receiptDetail";
	loc = loc + "&hubNumber=" + hubNumber;
	
	if ($v("personnelCompanyId") == 'Radian') 
		  loc = "/tcmIS/hub/" + loc;
	
	if (hubNumber == "2106") {
		printNormandaleQCLabels();
	} else {
		loc = loc + "&hubNumber=" + hubNumber;
		printQCLabels(loc);
	}
}

function printReceivingQcLabels(hubNumber) {
	var loc = "receivingqclabels.do?";
	//var paperSize = document.getElementById("paperSize").value;
	loc = loc + "paperSize=31"; //+ paperSize;
	loc = loc + "&hubNumber=" + hubNumber;
	
	if ($v("personnelCompanyId") == 'Radian') 
		  loc = "/tcmIS/hub/" + loc;
	
	if (hubNumber == "2106") {
		printNormandaleQCLabels();
	} else {
		loc = loc + "&hubNumber=" + hubNumber;
		printQCLabels(loc);
	}
}

function printQCLabels(loc) {
	var skipKitLabels = document.getElementById("skipKitLabels");
	if (skipKitLabels.checked) {
		loc = loc + "&skipKitLabels=Yes";
	} else {
		loc = loc + "&skipKitLabels=No";
	}
	try {
		children[children.length] = openWinGeneric(loc,
				"printReceivingQcLabels", "800", "500", "yes", "80", "80");
	} catch (ex) {
		openWinGeneric(loc, "printReceivingQcLabels", "800", "500", "yes",
				"80", "80");
	}
}

/*
 * Function used to print the QC labels for hub=2106 (Normandale), as the
 * printing is delegated to /HaasReports.
 */
function printNormandaleQCLabels() {

		var loc = "/HaasReports/report/printSeagateLabel.do?pr_receipts_ids="
				+ $v('receiptId');
		openWinGeneric(loc, "PrintSeagateLabel", "800", "600", "yes", "50",
				"50", "20", "20", "yes");
	return;
}

function receiptSpecs() {
	var receiptId = $v("receiptId");
	var loc = "receiptspec.do?receiptId=" + receiptId;
	
	if ($v("personnelCompanyId") == 'Radian') 
		  loc = "/tcmIS/distribution/" + loc;
	
	children[children.length] = openWinGeneric(loc, "receiptSpecs", "800",
			"400", "yes", "50", "50", "20", "20", "no");
}

function showRadianPo() {
	  var radianPo = document.getElementById("radianPo").value;

	  loc = "/tcmIS/supply/purchaseorder.do?po=" + radianPo + "&Action=searchlike&subUserAction=po";
	  try {
	 	children[children.length] = openWinGeneric(loc,"showradianPo","800","600","yes","50","50","yes");
	 } catch (ex){
	 	openWinGeneric(loc,"showradianPo","800","600","yes","50","50","yes");
	 }
}

function revertStatus()
{

	if($v('internalReceiptNotes') != '')
	{
		if(confirm(messagesData.revertstatusconfirm))
			{
				showTransitWinUpdate(messagesData.revertingImage);
				document.genericForm.target = ''; // Form name "genericForm" comes from struts config file
				$('userAction').value = 'revertStatus';		// $('formVariableName') is a utility function that does a document.getElementById('variableName')
									// $v('formVariableName') does the same with document.getElementById('variableName').value
				
				// Reset search time for update
				var now = new Date();
				var startSearchTime = document.getElementById("startSearchTime");
				startSearchTime.value = now.getTime();	
				
				document.genericForm.submit(); // Submit the form
			}
	}
	else
		alert(messagesData.pleaseaddnotesforthereverting);
}



function showVvHubBins() {
	var itemId = $v("itemId");

	var loc = "showhubbin.do?branchPlant=" + $v('hub')
			+ "&userAction=showBins&rowNumber=0&itemId=";
	loc = loc + itemId.value;
	
	if ($v("personnelCompanyId") == 'Radian') 
		  loc = "/tcmIS/hub/" + loc;
	
	try {
		children[children.length] = openWinGeneric(loc, "showVvHubBins", "300",
				"150", "no", "80", "80");
	} catch (ex) {
		openWinGeneric(loc, "showVvHubBins", "300", "150", "no", "80", "80");
	}

}

function getBin(bin,notUsed,notUsed1)
{
	var binSel = $('bin');
	var len = binSel.options.length;
	setOption(len, bin, bin, "bin");
	binSel.selectedIndex = len;
}

function unitLabelPartNumber() {
	var unitLabelPrinted = document.getElementById("unitLabelPrinted" + "");
	if (unitLabelPrinted.checked) {
		var itemId = document.getElementById("itemId" + "");
		var inventoryGroup = document.getElementById("inventoryGroup" + "");

		loc = "unitlabelpartnumber.do?&rowNumber=";
		loc = loc + "&itemId=" + itemId.value;
		loc = loc + "&hub=" + $v('hub');
		loc = loc + "&inventoryGroup=" + $v('inventoryGroup');
		
		if ($v("personnelCompanyId") == 'Radian') 
			  loc = "/tcmIS/hub/" + loc;
		
		try {
			children[children.length] = openWinGeneric(loc,
					"terminal_root_cause", "500", "300", "no");
		} catch (ex) {
			openWinGeneric(loc, "terminal_root_cause", "500", "300", "no");
		}
	}
}

function msdsRCMenu()
{
	 toggleContextMenu('msdsRCMenu');
}

function openMsds()
{
	var rowid = msdsGrid.getSelectedRowId();
	children[children.length] = openWinGeneric(gridCellValue(msdsGrid,rowid,'content'), "showMSDS", "800",
				"600", "no", "80", "80");

}

function flowDownMenu()
{
	var selected = flowDownGrid.getSelectedRowId();
	if(gridCellValue(flowDownGrid,selected,'onLine') == 'Y')
		toggleContextMenu('flowDownMenu');
	else
		 toggleContextMenu('contextMenu');
}

function openFlowDown()
{
		var selected = flowDownGrid.getSelectedRowId();
		if(selected != null) 			
			children[children.length] = openWinGeneric(gridCellValue(flowDownGrid,selected,'content'),'flowDown' + new Date().getTime(), '1000', '950', 'yes', '80', '80');
		else
			children[children.length] = openWinGeneric(gridCellValue(flowDownGrid,1,'content'),'flowDown' + new Date().getTime(), '1000', '950', 'yes', '80', '80');
}


function startMARStest()
{	
	var loc = "/tcmIS/haas/testrequestform.do?uAction=create&receiptId="+$v('receiptId');
	
	try {
		parent.parent.openIFrame("testrequest" , loc, ""+messagesData.marsdetail+" "+ receiptId + "","","N");
	}
	catch (ex) {
		children[children.length] = openWinGeneric(loc, "testrequest" , "900", "600", "yes", "80", "80", "yes");
	}
   
}

function showMarsDetail () {
    var tabId = "testRequestDetail_" + $v("testRequestId");
	var queryString = "?uAction=search&testRequestId=" + $v("testRequestId");
	var destination = "/tcmIS/haas/testrequestform.do" + queryString;
	try {
        parent.parent.openIFrame(tabId,destination, messagesData.marsdetail + " " + $v("testRequestId"),'','N');
    }catch (ex) {
		windowName = "testRequestDetail_" +  $v("testRequestId");
		children[children.length] = openWinGeneric(destination, windowName.replace('.', 'a'), "900", "600", "yes", "80", "80", "yes");
	}
} //end of method

function showPreviousReceivedQc() {

	loc = "showreceivedreceipts.do?itemId=" + $v('itemId') + "&hub="
			+ $v('sourceHub') + "&approved=Y";

	if ($v("personnelCompanyId") == 'Radian') 
		  loc = "/tcmIS/hub/" + loc;
	
	try {
		children[children.length] = openWinGeneric(loc, "Previous_Receiving",
				"1200", "600", "yes")
	} catch (ex) {
		openWinGeneric(loc, "Previous_Receiving", "800", "600", "yes")
	}
}

function reverseReceipt() {

	var receiptId = $('receiptId');
	if (receiptId.value.trim().length > 0) {
		loc = "showreversereceipt.do?openerPage=QVR&receiptId=";
		loc = loc + receiptId.value;
		
		if ($v("personnelCompanyId") == 'Radian') 
			  loc = "/tcmIS/hub/" + loc;
		
		try {
			children[children.length] = openWinGeneric(loc,
					"Reverse_Receiving", "300", "150", "no")
		} catch (ex) {
			openWinGeneric(loc, "Reverse_Receiving", "300", "150", "no")
		}
		return true;
	}
	return false;
}

function handleReverseReturn()
{
	if($v('openerPage') != '' )
	{
		opener.document.getElementById("uAction").value = 'search'; 
		opener.parent.showPleaseWait();
		opener.document.genericForm.submit();	
	    window.close();
	    return true;
	}
	return false;
	
}

function printChecklistReport()
{
		showTransitWinUpdate('Printing Checklist');
		document.genericForm.target = ''; // Form name "genericForm" comes from struts config file
											// $('formVariableName') is a utility function that does a document.getElementById('variableName')
										// $v('formVariableName') does the same with document.getElementById('variableName').value
		
		$('userAction').value = 'printChecklist';
		
		if (componentGrid != null) {
			// This function prepares the grid dat for submitting top the server
			componentGrid.parentFormOnSubmit();
		}		
		
		// Reset search time for update
		var now = new Date();
		var startSearchTime = document.getElementById("startSearchTime");
		startSearchTime.value = now.getTime();	
		
		document.genericForm.submit(); // Submit the form
}


function openOldReceivingInformation() {
	var loc = "https://www.tcmis.com/radweb/rec_lookup.html";
	try {
		children[children.length] = openWinGeneric(loc,
				"Lookup_Receiving_Info", "800", "600");
	} catch (ex) {
		if(ex.message!="Unable to get property 'focus' of undefined or null reference")
			openWinGeneric(loc, "Lookup_Receiving_Info", "800", "600")
	}
}


function openNewReceivingInformation() {
	var loc = "https://www.tcmis.com/radweb/rec_info.html";
	try {
		children[children.length] = openWinGeneric(loc,
				"Enter_Receiving_Info", "800", "600", "no");
	} catch (ex) {
		if(ex.message!="Unable to get property 'focus' of undefined or null reference")
			openWinGeneric(loc, "Enter_Receiving_Info", "800", "600")
	}
}

function submitSearchForm()
{
	window.location.reload();
}

function initSpecGrid(res)
{
	var results = jQuery.parseJSON(res);
	if(results.Message != null && results.Message != undefined) 
		 alert(results.Message);
	else
	{
		var results = jQuery.parseJSON(res);
	  	var r = results.specRes;
		var addedCount = 0;
		for(var i = 0; i < r.length; i++)	{
			var color = '';
			switch(checkType(r[i].color))
			{
				case 1:
					color = "grid_green";
				break;
				case 2:
					color = "grid_yellow";
				break;
				case 3:
					color = "grid_orange";
				break;
				case 4:
					color = "grid_red";
				break;
				case 5:
					color = "grid_purple";
				break;
				default:
					color = "grid_white";
				break;
			}
			specJsonData.rows[addedCount] =
	                {id:++addedCount,
					'class':color,
	                 data:['N',
	                       checkType(r[i].savedCoc),
	                       checkType(r[i].savedCoa),
	                       (typeof(r[i].content) != 'undefined'?'<img src="/images/buttons/document.gif"/>':'') + escapeHtml(checkType(r[i].specIdDisplay)) ,
	                       escapeHtml(checkType(r[i].specLibraryDesc)),
	                       checkType(r[i].currentCocRequirement),
	                       checkType(r[i].currentCoaRequirement),
	                       checkType(r[i].cocReqAtSave),
	                       checkType(r[i].coaReqAtSave),
	                       checkType(r[i].content),
	                       checkType(r[i].specId),
	                       checkType(r[i].specLibrary)
	                      ]
	                }
		}
	 	initGridWithGlobal(specGridConfig);
	 	$("specTotalLines").value = addedCount+1;
	 	setFooter(document.getElementById("footer2"),"specTotalLines");
	 	specGrid.obj.ondblclick=function(e){
			var selected = specGrid.getSelectedRowId();
			if(gridCellValue(specGrid,selected,"content") != '' )
			 		doF8();
	 		if (!this.grid.wasDblClicked(e||window.event)) 
				return false; 
			if (this.grid._dclE) 
				this.grid.editCell(e||window.event);  
			(e||event).cancelBubble=true;
			if (_isOpera) return false; //block context menu for Opera 9+
		 };
		
	}
}


function initPrGrid(res)
{
	var results = jQuery.parseJSON(res);

	if(results.Message != null && results.Message != undefined) 
		 alert(results.Message);
	else
	{
	  	var r = results.assocPrs;
		var addedCount = 0;
		for(var i = 0; i < r.length; i++)	{
			associatedPRsJsonData.rows[addedCount] =
	                {id:++addedCount,
	                 data:['N',
	                       checkType(r[i].prNumber),
	                       checkType(r[i].mrNumber) + " - " + checkType(r[i].mrLineItem),
	                       checkType(r[i].partId),
	                       checkType(r[i].specList),
	                       checkType(r[i].itemType),
	                       escapeHtml(checkType(r[i].purchasingNote)),
	                       escapeHtml(checkType(r[i].notes)),
	                       checkType(r[i].requestorLastName) + ", " + checkType(r[i].requestorFirstName)  + " / " + checkType(r[i].phone) + " / " + checkType(r[i].email),
	                       checkType(r[i].csrName),
	                       checkType(r[i].mrQuantity),
	                       checkType(r[i].orderQuantity),
	                       checkType(r[i].sizeUnit),
	                       checkType(r[i].needDate),
	                       checkType(r[i].facilityId),
	                       checkType(r[i].shipToDeliveryPoint)
	                      ]		
	                }
		}
		 initGridWithGlobal(associatedPRsGridConfig);
		 $("associatedPRsTotalLines").value = addedCount+1;
		 setFooter(document.getElementById("footer2"),"associatedPRsTotalLines");
	}
}

function initMsdsGrid(res)
{
	var results = jQuery.parseJSON(res);

	if(results.Message != null && results.Message != undefined) 
		 alert(results.Message);
	else
	{
	  	var r = results.msds;
		var addedCount = 0;
		for(var i = 0; i < r.length; i++)	{
			msdsJsonData.rows[addedCount] =
	                {id:++addedCount,
	                 data:['N',
	                       escapeHtml(checkType(r[i].description)),
	                       checkType(r[i].manufacturer),
	                       checkType(r[i].revisionDate),
	                       checkType(r[i].content)
	                      ]		
	                }
		}
		 initGridWithGlobal(msdsGridConfig);
		 $("msdsTotalLines").value = addedCount+1;
		 setFooter(document.getElementById("footer2"),"msdsTotalLines");
	}
}

function initFlowDownGrid(res)
{
	var results = jQuery.parseJSON(res);
	if(results.Message != null && results.Message != undefined) 
		 alert(results.Message);
	else
	{
	  	var r = results.flowDowns;
		var addedCount = 0;
		for(var i = 0; i < r.length; i++)	
		{
			if(checkType(r[i].ok).toLowerCase() == 'y')
			{
				var color = '';
				switch(checkType(r[i].color))
				{
					case 1:
						color = "grid_green";
					break;
					case 2:
						color = "grid_yellow";
					break;
					case 3:
						color = "grid_orange";
					break;
					case 4:
						color = "grid_red";
					break;
					case 5:
						color = "grid_purple";
					break;
					default:
						color = "grid_white";
					break;
				}
				flowDownJsonData.rows[addedCount] =
		                {id:++addedCount,
						'class':color,
		                 data:['N',
		                       (checkType(r[i].onLine).toLowerCase() == 'y' ? '<img src="/images/buttons/document.gif"/>':'') + checkType(r[i].flowDown),
		                       checkType(r[i].revisionDate),
		                       escapeHtml(checkType(r[i].flowDownDesc)),
		                       checkType(r[i].catalogId),
		                       checkType(r[i].companyId),
		                       checkType(r[i].flowDownType),
		                       checkType(r[i].onLine),
		                       checkType(r[i].content)
		                      ]
		                }
			}
		}
	 	initGridWithGlobal(flowDownGridConfig);
		flowDownGrid.obj.ondblclick=function(e){
			 	var selected = flowDownGrid.getSelectedRowId();
				if(gridCellValue(flowDownGrid,selected,'onLine') == 'Y')
					openFlowDown();
		 		if (!this.grid.wasDblClicked(e||window.event)) 
					return false; 
				if (this.grid._dclE) 
					this.grid.editCell(e||window.event);  
				(e||event).cancelBubble=true;
				if (_isOpera) return false; //block context menu for Opera 9+
		 };
	 	$("flowDownTotalLines").value = addedCount+1;
	 	setFooter(document.getElementById("footer2"),"flowDownTotalLines");
	}
}

function initCatalogGrid(res)
{
	var results = jQuery.parseJSON(res);

	if(results.Message != null && results.Message != undefined) 
		 alert(results.Message);
	else
	{
	  	var r = results.catalog;
		var addedCount = 0;
		for(var i = 0; i < r.length; i++)	{
			var shelfLifeBasisDisplay = '';
					
			switch(checkType(r[i].shelfLifeBasis))
			{
				case 'R':
					shelfLifeBasisDisplay = messagesData.dor;
				break;
				case 'M':
					shelfLifeBasisDisplay = messagesData.dom;
				break;
				case 'S':
					shelfLifeBasisDisplay = messagesData.manufacturerdos;
				break;
				default:
					shelfLifeBasisDisplay = checkType(r[i].shelfLifeBasis);
				break;
			}
			
			catalogJsonData.rows[addedCount] =
	                {id:++addedCount,
	                 data:['N',
	                       checkType(r[i].companyShortName) + "-" + checkType(r[i].catalogId),
	                       checkType(r[i].catPartNo),
	                       checkType(r[i].stocked),
	                       checkType(r[i].shelfLifeDays),
	                       (checkType(r[i].shelfLifeDays)==-1?messagesData.indefinite:checkType(r[i].shelfLifeDays)) + ' @ ' + checkType(r[i].storageTemp) + " " + shelfLifeBasisDisplay,
	                       shelfLifeBasisDisplay,
	                       checkType(r[i].specList),
	                       checkType(r[i].pwa300),
	                       checkType(r[i].companyId),
	                       checkType(r[i].catalogCompanyId),
	                       checkType(r[i].partGroupNo),
	                       checkType(r[i].inventoryGroup),
	                       (checkType(r[i].reorderPoint) == '' && checkType(r[i].stockingLevel) == '' && checkType(r[i].reorderQuantity) == '' ? '': checkType(r[i].reorderPoint) + " / " + checkType(r[i].stockingLevel) + " / " + checkType(r[i].reorderQuantity)),
	                       checkType(r[i].incomingTesting),
	                       (checkType(r[i].totalRecertsAllowed)==''?checkType(r[i].maxRecertNumber):r[i].totalRecertsAllowed),
	                       checkType(r[i].recertInstructions),
	                       checkType(r[i].customerPartRevision),
	                       checkType(r[i].catalogId),
	                       checkType(r[i].catalogDesc)
	                      ]		
	                }
		}
		 initGridWithGlobal(catalogGridConfig);
		 $("catalogTotalLines").value = addedCount+1;
		 setFooter(document.getElementById("footer2"),"catalogTotalLines");
		 if ($v("calculateQvrExpDate") == "Y") {
			var expDate = gridCellValue(componentGrid,1,'expireDate');
			var indefDate = "01-"+month_abbrev_Locale_Java[pageLocale][0]+"-3000";
			if (expDate == null || expDate == indefDate) {
				calculateExpireDate(0);
			}
			else {
				$("calculateQvrExpDate").value = "N";
			}
		 }
	}

}



function initLabelHistoryGrid(res)
{
	var results = jQuery.parseJSON(res);

	if(results.Message != null && results.Message != undefined) 
		 alert(results.Message);
	else
	{
		
		var rowSpanCount = 0;
		var rowSpanStart;
		var rowSpanCount;
		var prevSpanCount;
		var previousKey;
		var currentKey;
		
	  	var r = results.labelHistory;
		var addedCount = 0;
		for(var i = 0; i < r.length; i++)
		{
				
			containerLabelJsonData.rows[addedCount] =
            {id:++addedCount,
             data:['N',
                   checkType(r[i].receiptId),
                   checkType(r[i].containerLabelId),
                   escapeHtml(checkType(r[i].materialDesc)),
                   checkType(r[i].mfgLot),
                   (checkType(r[i].expireDate)=='01-Jan-3000'?messagesData.indefinite:checkType(r[i].expireDate)),
                   checkType(r[i].lotStatus),
                   checkType(r[i].partId),
                   checkType(r[i].partNumbers),
                   checkType(r[i].compMfgLot),
                   checkType(r[i].compDateInserted),
                   checkType(r[i].compExpireDate),
                   checkType(r[i].specDetail),
                   checkType(r[i].specList),
                   checkType(r[i].vendorShipDate),
                   checkType(r[i].dateOfReceipt),
                   checkType(r[i].dateOfManufacture),
                   checkType(r[i].dateOfShipment),  
                   (checkType(r[i].mfgLabelExpireDate)=='01-Jan-3000'?messagesData.indefinite:checkType(r[i].mfgLabelExpireDate)),
                   checkType(r[i].storageTemp),
                   checkType(r[i].recertNumber),
                   checkType(r[i].certifiedByName),
                   checkType(r[i].certificationDate),
                   checkType(r[i].qaStatement),
                   checkType(r[i].qualityTrackingNumber),
                   checkType(r[i].quantityPrinted),
                   checkType(r[i].lastIdPrinted),
                   checkType(r[i].printUserName),
                   checkType(r[i].dateInserted),
                   checkType(r[i].labelColor)
                  ]		
            }	
			
			currentKey = checkType(r[i].containerLabelId) + "|" + checkType(r[i].manageKitsAsSingleUnit);
			if(i == 0)
			{
				var rowSpanStart = 0;
				var rowSpanCount = 1;
				var prevSpanCount = rowSpanCount;
				rowSpanMap[0] = 1;
				rowSpanClassMap[0] = 1;
			}
			else if(currentKey == previousKey)
			{
				rowSpanMap[rowSpanStart]++;
				rowSpanMap[i] = 0;
				rowSpanClassMap[i] = rowSpanCount % 2;
			}
			else
			{
				rowSpanCount = rowSpanCount + 1;
				rowSpanStart = i;
				rowSpanMap[i] = 1;
				rowSpanClassMap[i] = rowSpanCount % 2;
			}
			previousKey = currentKey;
		}
	 	 rowSpanCols = [1,4,7,13,14,15,16,17,19,20,21,22,27,28];
	 	 initGridWithGlobal(containerLabelGridConfig);
		 $("containerLabelTotalLines").value = addedCount+1;
		 setFooter(document.getElementById("footer1"),"containerLabelTotalLines");
	}
}


function checkType(obj)
{
	if(typeof (obj) != 'undefined')
		return obj;
	else
		return '';
}

var entityMap = {
	    "&": "&amp;",
	    "<": "&lt;",
	    ">": "&gt;",
	    '"': '&quot;',
	    "'": '&#39;',
	    "/": '&#x2F;'
	  };

function escapeHtml(string) {
    return String(string).replace(/[&<>"'\/]/g, function (s) {
      return entityMap[s];
    });
}

function addMSDSRevisionDate() {
	var inputDailogWin = document.getElementById("msdsNewRevisionDateDialogWin");
	inputDailogWin.style.display="";

	initializeDhxWins();
	inputWin = dhxWins.createWindow("showMessageDialog",0,0, 200, 150);
	inputWin.setText(messagesData.pleaseentervalue);
	inputWin.clearIcon();  // hide icon
	inputWin.denyResize(); // deny resizing
	inputWin.denyPark();   // deny parking
	inputWin.attachObject("msdsNewRevisionDateDialogWin");
	inputWin.attachEvent("onClose", function(inputWin){inputWin.hide();});
	inputWin.center();
	// setting window position as default x,y position doesn't seem to work, also hidding buttons.
	inputWin.setPosition(350, 500);
	inputWin.button("close").hide();
	inputWin.button("minmax1").hide();
	inputWin.button("park").hide();
	inputWin.setModal(true);

}

function closeMSDSNewRevisionDateMessageWin () {
	var rowId = inboundDocGrid.getSelectedRowId();
	var date = $('msdsNewRevDate').value;
	
	if(date == null || date.length < 1){
		// revision date is required in order to associate a MSDS with this receipt
		alert(messagesData.revDateRequired);
	}
	else {			
		gridCell(inboundDocGrid,rowId,'msdsRevisionDate').setValue(date);
		
		dhxWins.window("showMessageDialog").setModal(false);	
		dhxWins.window("showMessageDialog").hide();
	}
}

function cancelMSDSNewRevisionDateWin() {
	var rowId = inboundDocGrid.getSelectedRowId();
	// reset document type drop down
	$('documentType'+rowId).value = '';
	
	dhxWins.window("showMessageDialog").setModal(false);	
	dhxWins.window("showMessageDialog").hide();
}

function showWait(message){
	$("transitLabel").innerHTML = message;
	
	var transitDailogWin = $("transitDailogWinBody");
	transitDailogWin.style.display="";

	if (dhxWins == null) {
		dhxWins = new dhtmlXWindows();
		dhxWins.setImagePath("/dhtmlxWindows/codebase/imgs/");
	}

	if (!dhxWins.window("transitDailogWinBody")) {
		// create window first time
		transitWin = dhxWins.createWindow("transitDailogWinBody",0,0, 400, 200);
		transitWin.setText("");
		transitWin.clearIcon();  // hide icon
		transitWin.denyResize(); // deny resizing
		transitWin.denyPark();   // deny parking

		transitWin.attachObject("transitDailogWinBody");
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
		dhxWins.window("transitDailogWinBody").show();
	}
}

function stopShowingWait() {
	if (dhxWins != null) {
		if (dhxWins.window("transitDailogWinBody")) {
			dhxWins.window("transitDailogWinBody").setModal(false);
			dhxWins.window("transitDailogWinBody").hide();
		}
		if (dhxWins.window("transitDailogWin")) {
			dhxWins.window("transitDailogWin").setModal(false);
			dhxWins.window("transitDailogWin").hide();
		}
	}
	return true;
}

function setNewRevisionDate(date){
	var rowId = inboundDocGrid.getSelectedRowId();
	
	if(date == null || date.length < 1){
		// revision date is required in order to associate a MSDS with this receipt
		alert(messagesData.revDateRequired);
		$('documentType'+rowId).value = '';
	}
	else if (date == 'Cancel'){
		// reset document type drop down
		$('documentType'+rowId).value = '';
	}
	else {			
		gridCell(inboundDocGrid,rowId,'msdsRevisionDate').setValue(date);
	}
}

function getWorkArea()
{
   loc = "/tcmIS/haas/singleselectresults.do?typeOfData=workArea&companyId="+encodeURIComponent($v("ownerCompanyId"))+"&facilityId="+encodeURIComponent($v("facilityRestriction"));
   openWinGeneric(loc,"singleSelectWorkArea","600","450","yes","50","50","no");
   showWait();
}

function singleSelectReturn(application,applicationDesc) {
    $("workAreaRestriction").value = application;
    $("applicationDesc").value = applicationDesc;
    stopShowingWait();
}

function editShelfLife() {
	var selectedRowId = catalogGrid.getSelectedRowId();
	var itemId = $v("itemId");
	var inventoryGroup = encodeURIComponent($v("inventoryGroup"));
	var catPartNo = encodeURIComponent(gridCellValue(catalogGrid,selectedRowId,"catPartNo"));
	var hub = $v("hub");
	
	children[children.length] = openWinGeneric(
			'catalogrqceditshelflife.do?catPartNo='+catPartNo+'&itemId='+itemId+'&hub='+hub+'&inventoryGroup='+inventoryGroup+'&caller=CIG'
                   ,"EditShelfLife","500","360",'yes' );
}

function updateShelfLifeStorageTemp(shelfLifeStorageTemp) {
	var concatSlst = "";
	
	var basisDates = {
			"S":parseDate($v("dateOfShipment")),
			"M":parseDate($v("dateOfManufacture")),
			"R":parseDate($v("dateOfReceipt"))
	};

	var daysForExp = 0;
	var basisForExp = 0;
	var minExpDate = -1;
	for (x in shelfLifeStorageTemp) {
		var slst = shelfLifeStorageTemp[x];
		var days = slst.shelfLifeDays;
		if (days > -1) {
			var temp = slst.storageTemp;
			var basis = slst.shelfLifeBasis;
			var basisDisp = slst.shelfLifeBasisDisplay;
			
			if (concatSlst.length > 0) {
				concatSlst = concatSlst+"; ";
			}
			concatSlst = concatSlst + days+" @ "+temp+" "+basisDisp;
			if (basisDates[basis]) {
				var expDateCalc = basisDates[basis].valueOf()+(days*24*60*60*1000);
			}
			
			if (minExpDate == -1 || expDateCalc < minExpDate) {
				minExpDate = expDateCalc;
				daysForExp = days;
				basisForExp = basis;
			}
		}
	}
	
	for (i = 1; i <= catalogGrid.getRowsNum();i++) {
		setGridCellValue(catalogGrid,i,"shelfLifeDays", daysForExp);
		setGridCellValue(catalogGrid,i,"shelfLifeStorageTemp", concatSlst);
		setGridCellValue(catalogGrid,i,"shelfLifeBasis", basisForExp);
	}
	
	try {
		if ($("calculateQvrExpDate") != null && $v("calculateQvrExpDate") == "Y") {
			if (minExpDate == -1) {
				$("expireDateDisplay0").value = "Indefinite";
			}
			else {
				var expDate = new Date(minExpDate);
				var expDateMonth = month_abbrev_Locale_Java[pageLocale][expDate.getMonth()];
				$("expireDateDisplay0").value = padDate(expDate.getDate())+"-"+expDateMonth+"-"+expDate.getFullYear();
			}
			recalcMinExpDate();
			setExpDateChanged(0);
		}
	}
	catch(e) {
		$("expireDateDisplay0").value = "Indefinite";
		setExpDateChanged(0);
	}
}

function parseDate(dateStr) {
	var dateVals = dateStr.split("-");
	
	var day = dateVals[0];
	var month = 0;
	var x = 1;
	for (;x<=month_abbrev_Locale_Java[pageLocale].length;x++) {
		if (month_abbrev_Locale_Java[pageLocale][x-1] === dateVals[1]) {
			month = x;
			break;
		}
	}
	var year = dateVals[2];
	
	var calcDate = false;
	if (month > 0) {
		calcDate = Date.parse(month+"/"+day+"/"+year);
	}
	return calcDate;
}

function recalculateExpireDate(rowIdx) {
	if ($v("calculateQvrExpDate") == "Y") {
		calculateExpireDate(rowIdx);
	}
}

function calculateExpireDate(rowIdx) {
	var expDateVal = -1;
	
	var totalRows = catalogGrid.getRowsNum();
	for (var rowId = 1; rowId <= totalRows; rowId++) {
		var dor = $v("dateOfReceipt");
		var dos = $v("dateOfShipment");
		var dom = $v("dateOfManufacture");
		var basis = gridCellValue(catalogGrid,rowId,"shelfLifeBasis");
		var shelfLifeDays = gridCellValue(catalogGrid, rowId,"shelfLifeDays");
		if (shelfLifeDays > -1) {
		
			if (basis == "DOS" && dosDate !== "") {
				var dosDate = parseDate(dos);
				var dosExpDate = dosDate.valueOf()+(shelfLifeDays*24*60*60*1000);
				if (dosDate && (expDateVal == -1 || expDateVal > dosExpDate)) {
					expDateVal = dosExpDate;
				}
			}
			else if (basis == "DOR" && dorDate !== "") {
				var dorDate = parseDate(dor);
				var dorExpDate = dorDate.valueOf()+(shelfLifeDays*24*60*60*1000);
				if (dorDate && (expDateVal == -1 || expDateVal > dorExpDate)) {
					expDateVal = dorExpDate;
				}
			}
			else if (basis == "DOM" && domDate !== "") {
				var domDate = parseDate(dom);
				var domExpDate = domDate.valueOf()+(shelfLifeDays*24*60*60*1000);
				if (domDate && (expDateVal == -1 || expDateVal > domExpDate)) {
					expDateVal = domExpDate;
				}
			}
		}
	}
	
	try {
		if (expDateVal == -1) {
			$("expireDateDisplay"+rowIdx).value = "Indefinite";
		}
		else {
			var expDate = new Date(expDateVal);
			var expDateMonth = month_abbrev_Locale_Java[pageLocale][expDate.getMonth()];
			$("expireDateDisplay"+rowIdx).value = padDate(expDate.getDate())+"-"+expDateMonth+"-"+expDate.getFullYear();
		}
		setExpDateChanged(rowIdx);
	}
	catch(e) {
		$("expireDateDisplay0").value = "Indefinite";
		setExpDateChanged(0);
	}
	
	recalcMinExpDate();
	$("calculateQvrExpDate").value = "Y";
}

function recalcMinExpDate() {
	j$.getJSON("receivingqcchecklist.do?userAction=recalcMinExpDate&receiptId="+$v("receiptId"))
		.done(function(response) { $("minExpDate").innerHTML = response.minExpDate; 
									$("minimumExpireDate").value = response.minExpDate; })
		.fail(function(jqXHR, status, error) { alert(messagesData.recalculateminexpdateerror); })
		.always(function() { return true; });
}

function padDate(dateVal) {
	var dateStr = "";
	if (dateVal < 10) {
		dateStr = "0"+dateVal;
	}
	else {
		dateStr = ""+dateVal;
	}
	return dateStr;
}

function warnForExpireDate(rowIdx) {
	if ($v("calculateQvrExpDate") == "Y") {
		var expDate = gridCellValue(componentGrid,1,'expireDate');
		var indefDate = "01-"+month_abbrev_Locale_Java[pageLocale][0]+"-3000";
		if (expDate == null || expDate == indefDate ||
				confirm(messagesData.calculatedvalue)) {
			return myGetCalendarE(rowIdx);
		}
		else {
			return false;
		}
	}
	else {
		return myGetCalendarE(rowIdx);
	}
}

function checkExpireDate() {
	var message = "";
	
	for (var rowNum = 1; rowNum <= componentGrid.getRowsNum(); rowNum++) {
		var curShelfLifeDays = gridCellValue(componentGrid, rowNum, "shelfLifeDays");
		if (curShelfLifeDays != "-1" && curShelfLifeDays != "") {
			var materialDesc = gridCellValue(componentGrid, rowNum, "materialDesc").length > 0 ? gridCellValue(componentGrid, rowNum, "materialDesc") + " " : "";
			if (gridCellValue(componentGrid, rowNum, "mfgLabelExpireDate") == '01-Jan-3000')
				message += "\n" + messagesData.infiniteExpireDateWarning.replace("{0} ", materialDesc).replace("{1}", curShelfLifeDays).replace("{2}", messagesData.labelexpiredate);
			if (gridCellValue(componentGrid, rowNum, "expireDate") == '01-Jan-3000')
				message += "\n" + messagesData.infiniteExpireDateWarning.replace("{0} ", materialDesc).replace("{1}", curShelfLifeDays).replace("{2}", messagesData.expiredate);
		}
	}
	
	if (message.length > 0)
		alert(message.substring(1)); //remove '\n' at the beginning of the string
}

function addShippingSample() {
	var receiptId = $v("receiptId");
	var loc = "shippingsample.do?receiptId=" + receiptId;
	loc += "&hub=" + $v("hub");
	
	if ($v("personnelCompanyId") == 'Radian') 
		loc = "/tcmIS/hub/" + loc;
	openWinGeneric(loc, "addShippingSample" + receiptId, "350", "200", "yes", "80", "80");
}

function printRTKLabel() {
	var itemId =  $v("itemId");
	var labelQuantity = $v("quantityReceived");

	var reportLoc = "printrtklabels.do"
		+ "?itemId="+itemId
		+ "&labelQuantity="+labelQuantity;
	
	if ($v("personnelCompanyId") == 'Radian') 
		reportLoc = "/tcmIS/hub/" + reportLoc;
	openWinGeneric(reportLoc,"printRTKLabels","300","200","yes","200","200");
}

function validateCoo(firstOption) {
	//the input CoO is invalid when:
	//1. CoO is required but user hasn't entered anything
	//OR
	//2. The input value is different from the first suggestion
	if ((isWhitespace($v("countryOfOrigin")) && $v("countryOfOriginReqdAtQc") == "Y")
			|| (!isWhitespace($v("countryOfOrigin")) && 
					(firstOption == null
							|| (firstOption.toLowerCase() != $v("countryOfOrigin").toLowerCase())))) {
		$("countryOfOrigin").className = "inputBox red";
		isValidCoo = false;
	} else {
		$("countryOfOrigin").value = firstOption ? firstOption : "";
		$("countryOfOrigin").className = "inputBox";
		isValidCoo = true;
	}
}