<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
	"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="/WEB-INF/tcmis.tld" prefix="tcmis" %>

<html>
<head>
<meta http-equiv="content-type" content="text/html; charset=UTF-8">
<meta http-equiv="expires" content="-1">
<link rel="shortcut icon" href="/images/buttons/tcmIS.ico"></link>
<%@ include file="/common/locale.jsp" %>
<!--Use this tag to get the correct CSS class.
This looks at what the user's preffered font size and which application he is viewing to set the correct CSS. -->
<tcmis:gridFontSizeCss />
<!-- Add any other stylesheets you need for the page here -->

<script type="text/javascript" src="/js/common/formchek.js"></script>
<script type="text/javascript" src="/js/common/commonutil.js"></script>
<%--NEW - grid resize--%>
<script type="text/javascript" src="/js/common/grid/resultiframegridresize.js"></script>
<!-- This handels which key press events are disabeled on this page -->
<script src="/js/common/disableKeys.js"></script>

<!-- This handels the menu style and what happens to the right click on the whole page -->
<script type="text/javascript" src="/js/menu/milonic_src.js"></script>
<script type="text/javascript" src="/js/menu/mmenudom.js"></script>
<script type="text/javascript" src="/js/menu/mainmenudata.js"></script>
<script type="text/javascript" src="/js/menu/contextmenu.js"></script>
<script type="text/javascript" src="/js/menu/mm_menueditapi.js"></script>
<%@ include file="/common/rightclickmenudata.jsp" %>


<!-- Add any other javascript you need for the page here
<script type="text/javascript" src="/js/client/catalog/itemcatalog.js"></script>
-->

<!-- These are for the Grid, uncomment if you are going to use the grid -->
<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxcommon.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxgrid.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxgridcell.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_form.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_srnd.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_json.js"></script>
<%--Uncomment below if you are providing header menu to switch columns on/off--%>
<!--<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_hmenu.js"></script>-->
<%--Uncomment the below if your grid has rwospans >1--%>
<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_rowspan.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/rowspan_cell_patch.js"></script>
<%--This is to allow copy and paste. works only in IE--%>
<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_selection.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_nxml.js"></script>
<%--This has the custom cells we built, hcal - the internationalized calendar which we use
    hlink- this is for any links you want tp provide in the grid, the URL/function to call
    can be attached based on a even (rowselect etc)
--%>
<script type="text/javascript" src="/dhtmlxGrid/codebase/excells/dhtmlxgrid_excell_customized.js"></script>
<%--Custom sorting.
This custom sorting function implements case insensitive sorting.
--%>
<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxcommon_haas.js"></script>

<script language="JavaScript" type="text/javascript">
<!--
//add all the javascript messages here, this for internationalization of client side javascript messages

var messagesData = new Array();

messagesData={alert:"<fmt:message key="label.alert"/>",and:"<fmt:message key="label.and"/>",
validvalues:"<fmt:message key="label.validvalues"/>",submitOnlyOnce:"<fmt:message key="label.submitOnlyOnce"/>",
itemInteger:"<fmt:message key="error.item.integer"/>",
recordFound:"<fmt:message key="label.recordFound"/>",
searchDuration:"<fmt:message key="label.searchDuration"/>",
minutes:"<fmt:message key="label.minutes"/>",
seconds:"<fmt:message key="label.seconds"/>",
selectedRowMsds:"<fmt:message key="label.selectedmsds"/>",
returnmsds:"<fmt:message key="label.returnmsds"/>",
newitemfrom:"<fmt:message key="label.newitemfrom"/>",
addandcontinuemsds:"<fmt:message key="label.addandcontinuemsds"/>",
addandclosemsds:"<fmt:message key="label.addandclosemsds"/>",
addNewMaterial:"<fmt:message key="label.addnewmaterial"/>",
newMaterial:"<fmt:message key="label.newmaterial"/>",
selectedRowMaterial:"<fmt:message key="label.selectedmaterial"/>",
addandclosematerial:"<fmt:message key="label.addandclosematerial"/>",
addandcontinuematerial:"<fmt:message key="label.addandcontinuematerial"/>",
returnmaterial:"<fmt:message key="label.returnmaterial"/>",
newkitfrom:"<fmt:message key="label.newkitfrom"/>"
};

/*Set this to be false if you don't want the grid width to resize based on window size.*/
var resizeGridWithWindow = true;

var map = null;
var map2 = null;
var lineMap = new Array();
var lineMap2 = new Array();
var lineMap3 = new Array();
var lineIdMap = new Array();
var selectedRowId = null;
if('Y' == '${showFacilityUseCode}')
	var rowSpanCols = [0,1,2];
else
	var rowSpanCols = [0,1];

with ( milonic=new menuname("viewMsdsMenu") ) {
 top="offset=2";
 style=submenuStyle;
 itemheight=17;
 aI( "text=<fmt:message key="label.viewcomponentmsds"/> ;url=javascript:viewMsds();" );
 aI( "text=<fmt:message key="label.showstoragelocations"/> ;url=javascript:showStorageLocationMenu();" );   
}

with ( milonic=new menuname("viewKitMsdsMenu") ) {
 top="offset=2";
 style=submenuStyle;
 itemheight=17;
 aI( "text=<fmt:message key="label.viewkitmsds"/> ;url=javascript:viewKitMsds();" );
}

with ( milonic=new menuname("viewEmptyMenu") ) {
 top="offset=2";
 style=submenuStyle;
 itemheight=17;
 aI( "text=" );
}

drawMenus();

var windowCloseOnEsc = true;

var newMsdsViewer = false;

<tcmis:featureReleased feature="NewMsdsViewer" scope="ALL">
   newMsdsViewer = true;
</tcmis:featureReleased>

function viewMsds() {
	try {
		if(newMsdsViewer)
			parent.children[parent.children.length] = openWinGeneric('viewmsds.do?act=msds'+
                                                  '&materialId='+ cellValue(selectedRowId,"materialId") +
                                                  '&spec=' + // do we need to know spec id?
                                                  '&facility=' +parent.$v("facilityId") +
                                                  '&catpartno='
                                                  ,"ViewMSDS","900","600",'yes' );
        else
        	parent.children[parent.children.length] = openWinGeneric('ViewMsds?act=msds'+
                                                  '&id='+ cellValue(selectedRowId,"materialId") +
                                                  '&spec=' + // do we need to know spec id?
                                                  '&facility=' +parent.$v("facilityId") +
                                                  '&catpartno='
                                                  ,"ViewMSDS","900","600",'yes' );
    } catch(ex) {
		openWinGeneric('viewmsds.do?act=msds'+'&materialId='+ cellValue(selectedRowId,"materialId") +
                        '&showspec=N' +
                        '&spec=' + // do we need to know spec id?
                        '&cl='+cellValue(mygrid.getSelectedRowId(),"companyId")+
                        '&facility='+parent.$v("facilityId")
                        ,"ViewMSDS","900","600",'yes' );
	}
}

function viewKitMsds() {
	var reportLoc = "/HaasReports/report/printConfigurableReport.do"+
                    "?pr_companyId="+cellValue(mygrid.getSelectedRowId(),"companyId")+
                    "&pr_custMsdsDb="+cellValue(mygrid.getSelectedRowId(),"customerMsdsDb")+
					"&pr_custMsdsNo="+cellValue(mygrid.getSelectedRowId(),"customerMixtureNumber")+
					"&rpt_ReportBeanId=MSDSKitReportDefinition";
	try {
		parent.children[parent.children.length] = openWinGeneric(reportLoc,"viewKitMsds","800","550","yes","100","100");
	} catch(ex) {
		openWinGeneric(reportLoc,"viewKitMsds","800","550","yes","100","100");
	}
}

function showStorageLocationMenu() {
	var url = 'storagelocations.do?materialId='+cellValue(selectedRowId,"materialId") +
			'&facilityId=' + encodeURIComponent(parent.$v('facilityId')) +
			'&facilityName=' + encodeURIComponent(parent.$("facilityId").options[parent.$("facilityId").selectedIndex].text) +
			'&application=' +
			'&msdsNo=' + cellValue(selectedRowId,"customerMsdsNumber") +
			'&tradeName=' + encodeURIComponent(cellValue(selectedRowId,"tradeName")) +
			'&mfgDesc=' + encodeURIComponent(cellValue(selectedRowId,"mfgDesc")) +
			'&desc=' + encodeURIComponent(cellValue(selectedRowId,"materialDesc")) ;

	openWinGeneric(url,"storageLocations","500","500",'yes' );
}

function newMaterial() {
	parent.showTransitWin();
	var url = 'catalogaddmsdsrequest.do?uAction=newMaterial&calledFrom=searchMsds&requestId='+parent.$v("requestId");
	parent.children[parent.children.length] = openWinGeneric(url,"catalogAddEditNewMsds",800,350,'yes' );
}
var contAdding = false;
function newApprovalCode(cntAdd) {
	contAdding = cntAdd;
    if (parent.$v("calledFrom") == "catalogAddHmrb") {
        parent.opener.customerMsdsNumberCallback(selectedMsdsNo);
        parent.window.close();
    }else if (parent.$v("calledFrom") == "newCatalogAddMsdsProcess") {
        parent.showTransitWin();
        var url = 'catalogaddmsdsrequest.do?uAction=addMaterial&calledFrom=searchMsds&requestId='+parent.$v("requestId")+
                  '&custMsdsNo='+selectedMsdsNo+'&materialId='+cellValue(selectedRowId,"materialId")+"&continueAddingMSDS="+contAdding;
        parent.children[parent.children.length] = openWinGeneric(url,"catalogAddEditNewMsds",800,350,'yes' );
    }
    
}

function addNewMaterialToList() {
    parent.opener.addNewMaterialToList(contAdding);
    if(!contAdding)
    	parent.window.close();
    else
    {
    	parent.$('msdsCatAddCont').value = 'true';
    	addToMSDSCatAdd = 'true';
    	selectRow();
    	closeTransitWin();
    }
}

function closeTransitWin() {
    parent.closeTransitWin();
}

var addAndContinue = false;
var lineItem = "";
var addToMSDSCatAdd = 'false';
catalogAddEditNewItemWin = null;
function newItemFromMaterial(addCont,customerMixtureNumber,insertCustMixtNo)
{
	parent.showTransitWin();
	addAndContinue = addCont;
	var url = 'catalogaddrequest.do?uAction=addMsds&itemId=&calledFrom=searchGlobalItem&requestId='+parent.$v("requestId")
	+ '&materialId='+cellValue(selectedRowId,"materialId")+'&engEvalFacilityId='+parent.$v("facilityId")+'&lineItem=' + lineItem;//+'&addAndContinue='+addAndContinue;
	if(typeof(customerMixtureNumber) != 'undefined') 
		url += '&customerMixtureNumber='+customerMixtureNumber +'&insertCustMixtNo=' + insertCustMixtNo;
	catalogAddEditNewItemWin = openWinGeneric(url,"catalogAddEditNewItem",900,620,'yes' );
	parent.children[parent.children.length] = catalogAddEditNewItemWin; 
}

function addItemToQPL() {
	addOnLineItem = catalogAddEditNewItemWin.$v('lineItem');
	parent.setAddToLineItem(addOnLineItem);
	lineItem = addOnLineItem;
	if(typeof(mygrid) != 'undefined' && mygrid != null && mygrid.getRowsNum() > 0)
	{
		parent.$("updateResultLink").innerHTML = '<a href="#" onclick="call(\'newMaterialItemCatAdd\'); return false;" title="Create new material(s) to add to the selected QPL line and close">'+messagesData.addNewMaterial+'</a>';
		selectRow();
	}
		
	parent.opener.calledFrom = "searchGlobalItemMsds";
	parent.opener.searchGlobalItemMsdsAddAndContinue = addAndContinue; 
	parent.opener.itemAlreadyInQpl = "";
	parent.opener.itemIdInQpl = "";
	parent.opener.addNewItemToQpl();
	if(!addAndContinue)
	{
		parent.window.close();
		parent.closeOpenerTransitWin = false;
	}
	else
		parent.closeOpenerTransitWin = true;	
}

function newMaterialItemCatAdd()
{
	parent.showTransitWin();
	addAndContinue = false;
	var url = 'catalogaddrequest.do?uAction=newMaterial&calledFrom=searchGlobalItem&requestId='+parent.$v("requestId")+'&engEvalFacilityId='+parent.$v("facilityId")+'&lineItem=' + lineItem;
	catalogAddEditNewItemWin = openWinGeneric(url,"catalogAddEditNewItem",900,620,'yes' );
	parent.children[parent.children.length] = catalogAddEditNewItemWin;
}

var selectedMsdsNo = null;
var selectedColId0 = null;
var selectedEe = null;
function selectRow()
{
    // to show menu directly
   rightClick = false;
   rowId0 = arguments[0];
   colId0 = arguments[1];
   ee     = arguments[2];

   if( ee != null ) {
		if( (ee.button != null && ee.button == 2) || ee.which == 3) {
			rightClick = true;
		}
   }

   if(typeof(rowId0) == 'undefined' && (typeof(selectedRowId) == 'undefined' || selectedRowId == null))
	{
  		rowId0 = 1;
   		colId0 = 1;   
	}
   	else if(typeof(rowId0) == 'undefined')
   	{
   		rowId0 = selectedRowId;
   		colId0 = selectedColId0;
   		ee     =  selectedEe;
   	}
   	else
   	{
    	selectedRowId = rowId0;
    	selectedColId0 = colId0;
    	selectedEe = ee;
   	}

    selectedMsdsNo = cellValue(rowId0,"customerMixtureNumber");
    if (selectedMsdsNo == null || selectedMsdsNo.length == 0) {
        selectedMsdsNo = cellValue(rowId0,"customerMsdsNumber");
    }

    if("Y" != '${fromAdvancedMSDSSearch}') {
        if ((selectedMsdsNo != null && selectedMsdsNo.length > 0) || parent.$v("calledFrom") == "newCatalogAddMsdsProcess") {
        	approvalUseGroupId = $v('approvalUseGroupId');
            if (parent.$v("calledFrom") == "catalogAddHmrb"){
            	selectedRowApprovalCode = cellValue(rowId0,"approvalCode");
            	if(approvalUseGroupId == '' || selectedRowApprovalCode.indexOf(approvalUseGroupId) != -1 || selectedRowApprovalCode.indexOf('AG') != -1) 
                	parent.$("selectedRow").innerHTML = ' | <a href="#" onclick="call(\'newApprovalCode\'); return false;">'+messagesData.selectedRowMsds+' : '+ selectedMsdsNo +'</a>';
                else
                	parent.$("selectedRow").innerHTML = '';	
            }else if (parent.$v("calledFrom") == "newCatalogAddMsdsProcess") {

            	addMsdsNo = selectedMsdsNo;
        		addCustMixNum = cellValue(rowId0,"customerMixtureNumber");
        		addCustMsds = cellValue(rowId0,"customerMsdsNumber");
        		isCustomerMixtureNumber = false;
        		isSingleMat = false;
        		
            	/*if(addCustMixNum == '')
            		addMsdsNo = cellValue(rowId0,"customerMsdsNumber");
            	else if(colId0 < mygrid.getColIndexById("customerMsdsNumber"))
            	{
            		addMsdsNo = addCustMixNum;
        			isCustomerMixtureNumber = true;
            	}
            	else
            		addMsdsNo = addCustMsds;*/

                if(addCustMixNum != '' && colId0 < mygrid.getColIndexById("customerMsdsNumber"))
               	{
               		addMsdsNo = addCustMixNum;
           			isCustomerMixtureNumber = true;
               	}
                else if(addCustMsds != '')
               		addMsdsNo = addCustMsds;
               	else
               	{
               		addMsdsNo = cellValue(rowId0,"materialId");
               		isSingleMat = true;
               	}

            	selectedMsdsNo = addMsdsNo;

            	if(addToMSDSCatAdd === 'true')
            	{
         		 	parent.$("newMatLink").innerHTML=messagesData.addNewMaterial; 
                	if(isSingleMat)
                	{
	                	message1 = messagesData.addandclosematerial+ ': ' + addMsdsNo;
	                	message2 = messagesData.addandcontinuematerial + ': ' + addMsdsNo;
	                	toolTip1 = 'Add Material and close window';
	                	toolTip2 = 'Add Material and continue adding further Materials';
                	}
                	else
                	{
	                	message1 = messagesData.addandclosemsds + ': ' + addMsdsNo;
	                	message2 = messagesData.addandcontinuemsds + ': ' + addMsdsNo;
	                	toolTip1 = 'Add MSDS and close window';
	                	toolTip2 = 'Add MSDS and continue adding further MSDSs';
                	}
            	}
            	else
            	{
                	if(isSingleMat)
                	{
                		message1 = messagesData.selectedRowMaterial + ': '+ addMsdsNo;
	                	message2 = messagesData.newkitfrom + ': ' + addMsdsNo;
	                  	toolTip1 = 'Add Material and close window';
	                  	toolTip2 = 'Create kit from Material, and continue adding further Materials';
                	}
                	else
                	{
	                	message1 = messagesData.returnmsds + ': '+ addMsdsNo;
	                	message2 = messagesData.newkitfrom + ': ' + addMsdsNo;
	                  	toolTip1 = 'Add MSDS and close window';
	                  	toolTip2 = 'Create kit from MSDS, and continue adding further MSDSs';
                	}
            	}
            	
            	if(isCustomerMixtureNumber)
            	{
                	parent.$("selectedRow").innerHTML = ' | <a href="#" onclick="call(\'newApprovalCode(false)\'); return false;" title="'+toolTip1+'">'+message1+'</a>'
                											+ ' | <a href="#" onclick="call(\'newApprovalCode(true)\'); return false;" title="'+toolTip2+'">'+message2+'</a>';
            	}
            	else
            		parent.$("selectedRow").innerHTML = ' | <a href="#" onclick="call(\'newApprovalCode(false)\'); return false;" title="'+toolTip1+'">'+message1+'</a>'
            												  + ' | <a href="#" onclick="call(\'newApprovalCode(true)\'); return false;" title="'+toolTip2+'">'+message2+'</a>';
            }
            else if ($v("resultsOnlyCalledFrom") == "newCatalogAddProcess") {
            		itemAddMsdsNo = selectedMsdsNo;
            		itemAddCustMixNum = cellValue(rowId0,"customerMixtureNumber");
            		itemAddCustMsds = cellValue(rowId0,"customerMsdsNumber");
            		isCustomerMixtureNumber = false;
            		insertCustomerMixtureNumber = true;
            		
                	if(itemAddCustMixNum == '')
                		itemAddMsdsNo = cellValue(rowId0,"customerMsdsNumber");
                	else if(colId0 < mygrid.getColIndexById("customerMsdsNumber"))
                	{
                		itemAddMsdsNo = itemAddCustMixNum;
            			isCustomerMixtureNumber = true;
                	}
                	else
                		itemAddMsdsNo = itemAddCustMsds;

                	if(lineItem != '')
                	{
                    	message1 = messagesData.addandclosemsds + ': ' + itemAddMsdsNo;
                    	message2 = messagesData.addandcontinuemsds + ': ' + itemAddMsdsNo;
                    	toolTip1 = 'Add item from MSDS to the selected QPL line and close';
                    	toolTip2 = 'Add item from MSDS to the selected QPL line and contiue adding MSDSs';
                    	insertCustomerMixtureNumber = false;
                	}
                	else
                	{
                    	message1 = messagesData.returnmsds + ': '+ itemAddMsdsNo;
                    	message2 = messagesData.newitemfrom + ': ' + itemAddMsdsNo;
                      	toolTip1 = 'Create item from MSDS, add to the QPL and close';
                    	toolTip2 = 'Create item from MSDS, add to the QPL and continue adding MSDSs';
                	}
                	
	            	if(isCustomerMixtureNumber)
	            	{
	                	parent.$("materialSelectedRow").innerHTML = ' | <a href="#" onclick="call(\'newItemFromMaterial(false,\\\''+itemAddMsdsNo+'\\\','+insertCustomerMixtureNumber+')\'); return false;" title="'+toolTip1+'">'+message1+'</a>'
	                											+ ' | <a href="#" onclick="call(\'newItemFromMaterial(true,\\\''+itemAddMsdsNo+'\\\',false)\'); return false;" title="'+toolTip2+'">'+message2+'</a>';
	            	}
	            	else
	            		parent.$("materialSelectedRow").innerHTML = ' | <a href="#" onclick="call(\'newItemFromMaterial(false)\'); return false;" title="'+toolTip1+'">'+message1+'</a>'
	            												  + ' | <a href="#" onclick="call(\'newItemFromMaterial(true)\'); return false;" title="'+toolTip2+'">'+message2+'</a>';

            }
            parent.$('selectedRow').style["display"] = "";
        }else {
            selectedMsdsNo = cellValue(rowId0,"materialId");
            if (selectedMsdsNo != null && selectedMsdsNo.length > 0) {
                if (parent.$v("calledFrom") == "catalogAddHmrb") {
                    parent.$("selectedRow").innerHTML = '<a href="#" onclick="call(\'newApprovalCode\'); return false;">'+messagesData.selectedRowMaterial+' : '+ selectedMsdsNo +'</a>';
                }else if (parent.$v("calledFrom") == "newCatalogAddMsdsProcess") {
                    parent.$("selectedRow").innerHTML = ' | <a href="#" onclick="call(\'newApprovalCode\'); return false;">'+messagesData.selectedRowMaterial+' : '+ selectedMsdsNo +'</a>';
                }
                else if ($v("resultsOnlyCalledFrom") == "newCatalogAddProcess") {
                   	if(lineItem != '')
                	{
                    	message1 = messagesData.addandclosematerial + ': ' + selectedMsdsNo;
                    	message2 = messagesData.addandcontinuematerial + ': ' + selectedMsdsNo;
                    	toolTip1 = 'Add item from Material to the selected QPL line and close';
                    	toolTip2 = 'Add item from Material to the selected QPL line and contiue adding';
                    	insertCustomerMixtureNumber = false;
                	}
                	else
                	{
                    	message1 = messagesData.returnmaterial + ': '+ selectedMsdsNo;
                    	message2 = messagesData.newitemfrom + ': ' + selectedMsdsNo;
                      	toolTip1 = 'Create item from Material, add to the QPL and close';
                    	toolTip2 = 'Create item from Material, add to the QPL and continue adding';
                	}

	            	parent.$("materialSelectedRow").innerHTML = ' | <a href="#" onclick="call(\'newItemFromMaterial(false)\'); return false;" title="'+toolTip1+'">'+message1+'</a>'
	            												  + ' | <a href="#" onclick="call(\'newItemFromMaterial(true)\'); return false;" title="'+toolTip2+'">'+message2+'</a>';
                }
                parent.$('selectedRow').style["display"] = "";
            }
        }
    }
	
    if( !rightClick ) return;

    if (cellValue(selectedRowId,"msdsOnLine") == 'Y') {
        if (colId0 < mygrid.getColIndexById("customerMsdsNumber")) {
            if (cellValue(selectedRowId,"customerMixtureNumber").length > 0) {
                toggleContextMenu("viewKitMsdsMenu");
            }else {
                toggleContextMenu("viewEmptyMenu");
            }
        }else {
            toggleContextMenu("viewMsdsMenu");
        }
    }else {
        toggleContextMenu("viewEmptyMenu");
    }
}

var config = [
{
    columnId:"customerMixtureNumber"
    <c:if test="${'Y' == showMSDS}">
        ,
        columnName:'<fmt:message key="label.kitmsds"/>'
    </c:if>
},
{
    columnId:"mixtureDesc",
    <c:if test="${'Y' == showMSDS}">
        columnName:'<fmt:message key="label.kitdesc"/>',
    </c:if>
    width:15,
    tooltip:"Y"
},
{
    columnId:"approvalCode"
    <c:if test="${'Y' == showFacilityUseCode}">
        ,
	    columnName:'<fmt:message key="label.approvalcode"/>'
	</c:if>
},
{
    columnId:"customerMsdsNumber"
    <c:if test="${'Y' == showMSDS}">
        ,
        columnName:'<fmt:message key="label.msds"/>'
    </c:if>  
},
{
    columnId:"materialDesc",
	columnName:'<fmt:message key="label.materialdesc"/>',
	width:30,
    tooltip:"Y"
},
{
    columnId:"mixRatio",
    <c:if test="${'Y' == showMSDS}">
        columnName:'<fmt:message key="label.mixratio"/>',
    </c:if>
    align:"right"
},
{
    columnId:"materialId",
    columnName:'<fmt:message key="label.materialid"/>'
},
{
    columnId:"mfgDesc",
	columnName:'<fmt:message key="label.manufacturer"/>',
    width:15,
    tooltip:"Y"
},
{
    columnId:"tradeName",
	columnName:'<fmt:message key="label.tradename"/>',
	width:30,
    tooltip:"Y"
},
{
    columnId:"msdsOnLine"
},
{
    columnId:"customerMsdsDb"
},
{
    columnId:"companyId"
}
];

var gridConfig = {
		divName:'itemCatalogScreenSearchBean', // the div id to contain the grid.
		beanData:'jsonMainData',     // the data variable name for jsonparse, as in grid.parse( beanData ,"json" )
		beanGrid:'mygrid',     // the grid's name, as in beanGrid.attachEvent...
		config:config,	     // the column config var name, as in var config = { [ columnId:..,columnName...
		rowSpan:true,			 // this page has rowSpan > 1 or not.
		submitDefault:true,    // the fields in grid are defaulted to be submitted or not.
		noSmartRender: false,
		onRowSelect:selectRow,   // the onRowSelect event function to be attached, as in beanGrid.attachEvent("onRowSelect",selectRow)
		onRightClick:selectRow,   // the onRightClick event function to be attached, as in beanGrid.attachEvent("onRightClick",selectRow)
        selectChild:1
	};

   function resultOnLoad() {
   		if("Y" == '${fromAdvancedMSDSSearch}') {
   			if('Y' == '${showFacilityUseCode}')
        		parent.$('mainUpdateLinks').style["display"] = "";
        	else
        		parent.$('mainUpdateLinks').style["display"] = "none";
        }else if (parent.$v("calledFrom") == "catalogAddHmrb") {
            parent.$('mainUpdateLinks').style["display"] = "";
            parent.$("updateResultLink").innerHTML = "";
        }else if (parent.$v("calledFrom") == "newCatalogAddMsdsProcess") {
        	parent.$('selectedRow').style["display"] = "none";
       
        	newMatLink = parent.$("newMatLink");
        	newMatLink.title="Create new material(s) to add and close window";
         	addToMSDSCatAdd = parent.$v('msdsCatAddCont');
        	if(addToMSDSCatAdd === 'true')
        		 parent.$("newMatLink").innerHTML=messagesData.addNewMaterial;
       
        	parent.$('mainUpdateLinks').style["display"] = "";
        }
        else if($v("resultsOnlyCalledFrom") == 'newCatalogAddProcess')
        {
           	parentShowCatalogAddOptions = parent.$('showCatalogAddOptions');
           	parentShowCatalogAddOptions.style["display"] = "none";
           	lineItem = parent.getAddToLineItem();
			if(lineItem != '')
    			parent.$("updateResultLink").innerHTML = '<a href="#" onclick="call(\'newMaterialItemCatAdd\'); return false;" title="Create new material(s) to add to the selected QPL line and close">'+messagesData.addNewMaterial+'</a>';
    		else
           		parent.$('updateResultLink').innerHTML = '<a href="#" onclick="call(\'newMaterialItemCatAdd\'); return false;" title="Create new material(s) to add to the QPL and close">'+messagesData.newMaterial+'</a> ';
           	
        	parent.$('mainUpdateLinks').style["display"] = "";
        	
        }
        
   }

   function resultUnLoad() {
	   if($v("resultsOnlyCalledFrom") == 'newCatalogAddProcess')
	   {
		   parent.$('materialSelectedRow').innerHTML = '';
		   
    	   addOnLineItem = parent.getAddToLineItem();
    	   if(typeof (addOnLineItem) != 'undefined' && addOnLineItem != '')
		    	parent.$('updateResultLink').innerHTML = '<a href="#" onclick="call(\'newMaterial\'); return false;">'+messagesData.addNewMaterial+'</a>';
	   }
   }

// -->
</script>

</head>

<body bgcolor="#ffffff" onload="resultOnLoadWithGrid(gridConfig);setTimeout('resultOnLoad()',100);" onunload="resultUnLoad();">
<tcmis:form action="/searchmsdsresults.do" onsubmit="return submitFrameOnlyOnce();">


<script language="JavaScript" type="text/javascript">
<!--
<c:set var="parCount" value="0"/>

<c:set var="dataCount" value='${0}'/>
<c:set var="prePar" value=""/>

<c:if test="${itemCatalogBeanCollection != null}" >
 <c:if test="${!empty itemCatalogBeanCollection}" >
//  var jsonMainData = new Array();
//  The color stuff is not working at this moment, I will use
//  javascript to fix it.
  var jsonMainData = {
  rows:[
  <c:forEach var="p" items="${itemCatalogBeanCollection}" varStatus="status">
	  <c:if test="${status.index != 0 }">,</c:if>
	<c:set var="curPar" value="${status.current.customerMixtureNumber}"/>
	<c:if test="${!(curPar eq prePar)}">
		<c:set var="parCount" value="${parCount+1}"/>
	</c:if>

    { id:${status.index +1},
		<c:if test="${param.approvalUseGroupId != '' && !fn:contains(p.approvalCode, 'AG') && !fn:contains(p.approvalCode, param.approvalUseGroupId)}">'class':"grid_black",</c:if>
        data:[
                '${p.customerMixtureNumber}',
                '<tcmis:jsReplace value="${p.mixtureDesc}" processMultiLines="true"/>',
                '${p.approvalCode}',
                '${p.customerMsdsNumber}',
                '<tcmis:jsReplace value="${p.materialDesc}" processMultiLines="true"/>',
                '${p.mixRatioAmount} ${p.mixRatioSizeUnit}',
                '${p.materialId}',
			    '<tcmis:jsReplace value="${p.mfgDesc}" processMultiLines="true"/>',
				'<tcmis:jsReplace value="${p.tradeName}" processMultiLines="true"/>',
                '${p.msdsOnLine}',
                '${p.customerMsdsDb}',
                '${p.companyId}'
        ]}
		<c:set var="numberOfKit" value="${status.current.numberOfKitPerMsds}"/>
		<c:if test="${!(numberOfKit eq -1)}">
			<c:set var="dataCount" value='${dataCount+1}'/>
		</c:if>
		<c:set var="prePar" value="${status.current.customerMixtureNumber}"/>

     </c:forEach>
  ]};

  </c:if>
 </c:if>

// -->
</script>

<%-- determining rowspan --%>
<c:set var="itemCount" value='0'/>
<c:forEach var="p" items="${itemCatalogBeanCollection}" varStatus="status">
	<c:set var="numberOfKit" value="${status.current.numberOfKitPerMsds}"/>
	<script language="JavaScript" type="text/javascript">
	<!--
	   lineMap[${status.index}] = ${numberOfKit} ;
		<c:if test="${!(numberOfKit eq -1)}">
	      <c:set var="itemCount" value='${itemCount+1}'/>
			map = new Array();
		</c:if>
	   lineMap3[${status.index}] = ${itemCount} % 2;
	   map[map.length] =  ${status.index} ; lineIdMap[${status.index}] = map;
	// -->
	</script>
</c:forEach>

<!-- Check if the user has permissions and needs to see the update links,set the variable you use in javascript to true.
     The default value of showUpdateLinks is false.
-->

<!-- Check if the user has permissions and needs to see the update links,set the variable you use in javascript to true.
     The default value of showUpdateLinks is false.
-->
<%--
<c:set var="pickingPermission" value=''/>
<tcmis:facilityPermission indicator="true" userGroupId="Picking" >
--%>
 <script type="text/javascript">
 <!--
  showUpdateLinks = true;
 //-->
 </script>
<%--</tcmis:facilityPermission>--%>

<!-- You can build your error messages below. But we want to trigger the YUI pop-up from the main page.
So this is just used to feed the YUI pop-up in the main page.
Similar divs would have to be built to show any other messages in a pop-up.-->
<!-- Error Messages Begins -->
<div id="errorMessagesAreaBody" style="display:none;">
${tcmisError}
</div>

<script type="text/javascript">
<!--
/*YAHOO.namespace("example.aqua");
YAHOO.util.Event.addListener(window, "load", init);*/

/*Check if there is any error messages to show and set the variable you use in javascript to true or false.*/
<c:choose>
   <c:when test="${empty tcmisError}">
    showErrorMessage = false;
   </c:when>
   <c:otherwise>
    showErrorMessage = true;
   </c:otherwise>
</c:choose>
//-->
</script>
<!-- Error Messages Ends -->

<div class="interface" id="resultsPage">
<div class="backGroundContent">

<!-- If the collection is empty say no data found -->
   <c:if test="${empty itemCatalogBeanCollection}" >
    <table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableNoData" id="resultsPageTable">
        <tr>
            <td width="100%">
                <fmt:message key="main.nodatafound"/>
            </td>
        </tr>
    </table>
   </c:if>

<%--NEW - there is no results table anymore--%>
<div id="itemCatalogScreenSearchBean" style="width:100%;%;height:400px;" style="display: none;"></div>


<!-- Hidden element start -->
<div id="hiddenElements" style="display: none;">
<input name="totalLines" id="totalLines" value="${dataCount}" type="hidden"/>
<input name="uAction" id="uAction" value="" type="hidden"/>
<input name="showFacilityUseCode" id="showFacilityUseCode" value="${showFacilityUseCode}" type="hidden"/>
<input name="resultsOnlyCalledFrom" id="resultsOnlyCalledFrom" value="${resultsOnlyCalledFrom}" type="hidden"/>
<input name="approvalUseGroupId" id="approvalUseGroupId" value="${param.approvalUseGroupId}" type="hidden"/>
 </div>
<!-- Hidden elements end -->

</div> <!-- close of backGroundContent -->
</div> <!-- close of interface -->

</tcmis:form>

</body>
</html>
