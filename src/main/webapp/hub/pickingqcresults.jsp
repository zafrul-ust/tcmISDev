
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%@ taglib uri="/WEB-INF/struts-nested.tld" prefix="nested" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/tcmis.tld" prefix="tcmis" %>

<html:html lang="true">
<head>
    <meta http-equiv="content-type" content="text/html; charset=UTF-8">
    <meta http-equiv="expires" content="-1">
    <link rel="shortcut icon" href="https://www.tcmis.com/images/buttons/tcmIS.ico"></link>
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
<%@ include file="/common/rightclickmenudata.jsp" %>

<!-- These are for the Grid-->
<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxcommon.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxgrid.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxgridcell.js"></script>
<%--This is for HTML form integration.--%>
<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_form.js"></script>
<%--This is for smart rendering.--%>
<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_srnd.js"></script>
<%--This is to suppoert loading by JSON.--%>
<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_json.js"></script>    
<%--Uncomment below if you are providing header menu to switch columns on/off--%>
<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_hmenu.js"></script>
<%--Uncomment the below if your grid has rwospans >1--%>
<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_rowspan.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/rowspan_cell_patch.js"></script>

<%--This has the custom cells we built, hcal - the internationalized calendar which we use
    hlink- this is for any links you want tp provide in the grid, the URL/function to call
    can be attached based on a even (rowselect etc)
--%>
<script type="text/javascript" src="/dhtmlxGrid/codebase/excells/dhtmlxgrid_excell_customized.js"></script>
<%--This file has our custom sorting and other utility methos for the grid.--%>    
<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxcommon_haas.js"></script>
<script type="text/javascript" src="/js/hub/pickingqcresults.js"></script>


<title>
    Update Example
</title>

<script language="JavaScript" type="text/javascript">
<!--
//add all the javascript messages here, this for internationalization of client side javascript messages
var messagesData = new Array();
messagesData = {alert:"<fmt:message key="label.alert"/>",and:"<fmt:message key="label.and"/>",
            validvalues:"<fmt:message key="label.validvalues"/>",
            submitOnlyOnce:"<fmt:message key="label.submitOnlyOnce"/>",
            recordFound:"<fmt:message key="label.recordFound"/>",
            searchDuration:"<fmt:message key="label.searchDuration"/>",
            minutes:"<fmt:message key="label.minutes"/>",
            seconds:"<fmt:message key="label.seconds"/>",
            mustBeAnInteger:"<fmt:message key="errors.integer"/>",                    
            qPick:"<fmt:message key="pickingqc.qtypicked"/>",
            pleasemakeselection:"<fmt:message key="label.pleasemakeselection"/>",
            diffQty1:"<fmt:message key="pickingqc.confirm.diffpickqty1"/>",
            diffQty2:"<fmt:message key="pickingqc.confirm.diffpickqty2"/>",
            diffQty3:"<fmt:message key="pickingqc.confirm.diffpickqty3"/>",
            qtypickedlessthanavailableqty:"<fmt:message key="label.qtypickedlessthanavailableqty"/>"
            };

with(milonic=new menuname("rightClickMenu")){
    top="offset=2"
    style = contextStyle;
    margin=3
    	aI("text=<fmt:message key="label.reversepick"/>;url=javascript:reversePick();");
 		aI("text=<fmt:message key="pickingqc.viewaddreceipts"/>;url=javascript:showProjectDocuments();");
 		aI("text=<fmt:message key="label.shippinginformation"/>;url=javascript:showShippingInfo();");
 		aI("text=<fmt:message key="pickingqc.receivinglabels"/>;url=javascript:doRecevingLabels();");
}

with(milonic=new menuname("rightClickMenuSplit")){
    top="offset=2"
    style = contextStyle;
    margin=3
 		aI("text=<fmt:message key="pickingqc.viewaddreceipts"/>;url=javascript:showProjectDocuments();");
 		aI("text=<fmt:message key="label.shippinginformation"/>;url=javascript:showShippingInfo();");
 		aI("text=<fmt:message key="pickingqc.receivinglabels"/>;url=javascript:doRecevingLabels();");
}

with(milonic=new menuname("rightClickMenuWmsHub")){
    top="offset=2"
    style = contextStyle;
    margin=3
    aI("text=<fmt:message key="pickingqc.viewaddreceipts"/>;url=javascript:showProjectDocuments();");
    aI("text=<fmt:message key="label.shippinginformation"/>;url=javascript:showShippingInfo();");
}

drawMenus();

//Allow different permissions for different columns
var multiplePermissions = true;

//Build up the array for the columns which use different permissions
var permissionColumns = new Array();
permissionColumns = {
        "quantityPicked" : true
};
var permissionArray = new Array();
var rowSpanMap = new Array();
var rowSpanClassMap = new Array();	
var rowSpanCols = [1,2,3,4,5,6,7,8,9,10,11,12,13,14,15];	
var rowSpanLvl2Map = new Array();
var lineMap4 = new Array();        
var config = [
  {
  	columnId:"permission"
  },
  {
	 columnId:"quantityPickedPermission"
  },
  {
  	columnId:"print",
  	columnName:'<fmt:message key="label.print"/>',
  	type:'hchstatus',  // checkbox:The value is string "true" if checked
    align:'center',
    //onChange:callClickOk, // onCheck only works for hch, but we don't use hch for Haas
    width:3
  },
  {
  	columnId:"plPicklistId",
  	columnName:'<fmt:message key="label.picklistid"/>',
    sorting:'int',
  	align:'center',
    width:5
  },
  {
  	columnId:"facilityName",
  	columnName:'<fmt:message key="label.facility"/>',
  	align:'center',
	tooltip:'Y',
    width:4
  },
  {
  	columnId:"workAreaDesc",
  	columnName:'<fmt:message key="label.workareadescription"/>',
  	align:'center',
  	tooltip:'Y',
    width:5
  },
  {
  	columnId:"deliveryPoint",
  	columnName:'<fmt:message key="label.deliverypoint"/>',
  	align:'center',
    width:4
  },
  {
  	columnId:"shipToLocationDesc",
  	columnName:'<fmt:message key="label.shipto"/>',
  	align:'center',
	tooltip:'Y',
  	width:7
  },
  {
  	columnId:"requestor",
  	columnName:'<fmt:message key="label.requestor"/>',
  	align:'center',
	tooltip:'Y',
  	width:5
  },
  {
  	columnId:"mrLine",
  	columnName:'<fmt:message key="label.mrline"/>',
  	align:'center',
  	width:4
  },
  {
  	columnId:"mrNotes",
  	columnName:'<fmt:message key="label.mrnotes"/>',
    align:'center',
  	tooltip:"Y",
  	width:4
  },
  {
  	columnId:"partNumber",
  	columnName:'<fmt:message key="label.partnumber"/>',
    align:'center',
	tooltip:'Y',
  	width:6     
  },
  {
  	columnId:"type",
  	columnName:'<fmt:message key="label.type"/>',
    align:'center',
  	width:3   
  },
  {
  	columnId:"item",
  	columnName:'<fmt:message key="label.item"/>',
    align:'center',
    sorting:'int',
  	width:4    
  },
  {
  	columnId:"partDescription",
  	columnName:'<fmt:message key="label.partdescription"/>',
    align:'center',
  	tooltip:"Y",
  	width:20        
  },
  {
  	columnId:"closeMrDisplay",
  	columnName:'<fmt:message key="label.closemr"/>',
    align:'center',
  	width:3       
  },
  {
  	columnId:"packaging",
  	columnName:'<fmt:message key="label.packaging"/>',
    align:'center',
  	tooltip:"Y",
  	width:9        
  },
  {
  	columnId:"bin",
  	columnName:'<fmt:message key="label.bin"/>',
    align:'center',
  	width:5        
  },
  {
	columnId:"receiptNum",
	columnName:'<fmt:message key="label.receiptid"/>',
  	align:'center',
	width:7,
	submit:false
  },
  {
	columnId:"mfgLot",
	columnName:'<fmt:message key="label.mfglot"/>',
  	align:'center',
	tooltip:'Y',
	width:6       
   },
   {
 	columnId:"expDate",
 	columnName:'<fmt:message key="label.expdate"/>',
   	align:'center',
    hiddenSortingColumn:'hiddenExpDateTime',
    sorting:'int',
 	width:6        
  },
  {
	 columnId:"mrLine",
  	columnName:'<fmt:message key="label.mrline"/>',
    align:'center',
  	width:6        
  },
  {
  	columnId:"picklistQuantity",
  	columnName:'<fmt:message key="pickingqc.pickqty"/>',
    align:'center',
    sorting:'int',
  	width:2       
  },
  {
  	columnId:"quantityPicked",
  	columnName:'<fmt:message key="pickingqc.qtypicked"/>',
  	type:'hed',    // input field
    sorting:'int',
  	align:'right',
  	//onChange:validationForConfirm,
  	width:5, // the width of this cell
  	size:4,  // the size of the input field
    maxlength:5 // the maxlength of the input field       
  },
  {
	  	columnId:"prNumber"
  },
  {
	  	columnId:"lineItem"
  },
  {
	  	columnId:"inventoryGroup"
  },
  {
	  	columnId:"receiptId"
 },
 {
	  	columnId:"cabinetReplenishment"
},
{ 
	  columnId:"hiddenExpDateTime",
	  sorting:'int'
},
{ 
	  columnId:"pickable"
},
{ 
	  columnId:"mrCompletePickable"
},
{ 
	  columnId:"hub"
},
{ 
	  columnId:"needDate"
},
{ 
	  columnId:"companyId"
},
{ 
	  columnId:"ok"
},
{ 
	  columnId:"picklistId"
},
{ 
	  columnId:"application"
},
{ 
	  columnId:"pickable"
},
{ 
	  columnId:"customerPartNo"
},
{ 
	  columnId:"reportingEntityName"
},
{ 
	  columnId:"endUser"
},
{ 
	  columnId:"applicationDesc"
},
{ 
	  columnId:"issueId"
},
{ 
	  columnId:"ownerSegmentId"
},
{ 
	  columnId:"allocateByChargeNumber1"
},
{ 
	  columnId:"allocateByChargeNumber2"
},
{ 
	  columnId:"allocateByChargeNumber3"
},
{ 
	  columnId:"allocateByChargeNumber4"
},
{ 
	  columnId:"receiptSpecNameList"
},
{ 
	  columnId:"receiptSpecVersion"
},
{ 
	  columnId:"releaseNumber"
},
{
  	columnId:"shipTo"
},
{
  	columnId:"facility"
},
{
  	columnId:"closeMr"
}
];

var gridConfig = {
	       divName:'PicklistViewBean', // the div id to contain the grid. ALSO the var name for the submitted data
	       beanData:'jsonMainData', // the data variable name for jsonparse, as in grid.parse( beanData ,"json" )
	       beanGrid:'mygrid', // the grid's name, for later reference/usage
	       config:config, // the column config var name, as in var config = { [ columnId:..,columnName...
	       rowSpan: true, // true: this page has rowSpan > 1 or not, disables smartrendering & sorting
	       submitDefault: true, // the fields in grid are defaulted to be submitted or not.
	       noSmartRender: false, // Explicitly enable smartrender by setting this to false for rowspans
		   variableHeight:false,
	       onRowSelect: selectRow,
	       onRightClick:selectRightclick, // the onRightClick event function to be attached, as in beanGrid.attachEvent("onRightClick",selectRow)
	       selectChild: 2, // Double select single row
	       onAfterHaasRenderRow:colorSpan
    }; 
function doPrintBolShort() {
	   var checked = countChecked();
	   if (checked==0) {
	      alert(messagesData.pleasemakeselection);
	      return false;
	   }
	   else {
		   openWinGeneric('/tcmIS/common/loadingfile.jsp', 'printBolShortddd','800','600','yes',"80","80");
		   var labelForm = document.getElementById("LabelPrintForm");
		   clearLabelForm(labelForm);
		   addNewFormElement(labelForm, "userAction", 'printBolShort');
		   document.LabelPrintForm.target = 'printBolShortddd';
		   getSelected(labelForm);	
		   labelForm.submit();	
		   setTimeout('window.status="";',5000);
	   }
	}

function clearLabelForm(labelForm)
{
	if ( labelForm.hasChildNodes() ) {
		while ( labelForm.childNodes.length >= 1 ) {
			labelForm.removeChild( labelForm.firstChild );       
		} 
	}
}

function getSelected(labelForm){
	var rowsNum = mygrid.getRowsNum();	
	var count = 0;
	for(var p = 1 ; p <= rowsNum ; p++) {
		var cid = "print"+p;
			if($(cid) != null && !$(cid).disabled && $(cid).checked) {
				addNewFormElement(labelForm, "PicklistViewBean[" + count + "].ok", cellValue(p, "print"));
				addNewFormElement(labelForm, "PicklistViewBean[" + count + "].prNumber", cellValue(p, "prNumber"));
				addNewFormElement(labelForm, "PicklistViewBean[" + count + "].lineItem", cellValue(p, "lineItem"));
				addNewFormElement(labelForm, "PicklistViewBean[" + count + "].picklistId", cellValue(p, "picklistId"));
				addNewFormElement(labelForm, "PicklistViewBean[" + count + "].mrLine", cellValue(p, "mrLine"));
				addNewFormElement(labelForm, "PicklistViewBean[" + count + "].itemId", cellValue(p, "item"));
				addNewFormElement(labelForm, "PicklistViewBean[" + count + "].receiptId", cellValue(p, "receiptId"));
	            addNewFormElement(labelForm, "PicklistViewBean[" + count + "].facilityId", cellValue(p, "facility"));
	            addNewFormElement(labelForm, "PicklistViewBean[" + count + "].application", cellValue(p, "application")); 
	            addNewFormElement(labelForm, "PicklistViewBean[" + count + "].catPartNo", cellValue(p, "partNumber"));
	            addNewFormElement(labelForm, "PicklistViewBean[" + count + "].customerPartNo", cellValue(p, "customerPartNo"));
	            addNewFormElement(labelForm, "PicklistViewBean[" + count + "].reportingEntityName", cellValue(p, "reportingEntityName"));
	            addNewFormElement(labelForm, "PicklistViewBean[" + count + "].applicationDesc", cellValue(p, "applicationDesc"));
	            addNewFormElement(labelForm, "PicklistViewBean[" + count + "].requestor", cellValue(p, "requestor"));
	            addNewFormElement(labelForm, "PicklistViewBean[" + count + "].hub", cellValue(p, "hub"));
	            addNewFormElement(labelForm, "PicklistViewBean[" + count + "].inventoryGroup", cellValue(p, "inventoryGroup"));
	            addNewFormElement(labelForm, "PicklistViewBean[" + count + "].partDescription", cellValue(p, "partDescription"));
	            addNewFormElement(labelForm, "PicklistViewBean[" + count + "].picklistQuantity", cellValue(p, "picklistQuantity"));
	            addNewFormElement(labelForm, "PicklistViewBean[" + count + "].shipToLocationId", cellValue(p, "shipTo"));
	            addNewFormElement(labelForm, "PicklistViewBean[" + count + "].packaging", cellValue(p, "packaging"));
	            addNewFormElement(labelForm, "PicklistViewBean[" + count + "].companyId", cellValue(p, "companyId"));
	            addNewFormElement(labelForm, "PicklistViewBean[" + count + "].endUser", cellValue(p, "endUser"));
	            addNewFormElement(labelForm, "PicklistViewBean[" + count + "].mfgLot", cellValue(p, "mfgLot"));
	            addNewFormElement(labelForm, "PicklistViewBean[" + count + "].issueId", cellValue(p, "issueId"));
	            addNewFormElement(labelForm, "PicklistViewBean[" + count + "].ownerSegmentId", cellValue(p, "ownerSegmentId"));
	            addNewFormElement(labelForm, "PicklistViewBean[" + count + "].allocateByChargeNumber1", cellValue(p, "allocateByChargeNumber1"));
	            addNewFormElement(labelForm, "PicklistViewBean[" + count + "].allocateByChargeNumber2", cellValue(p, "allocateByChargeNumber2"));
	            addNewFormElement(labelForm, "PicklistViewBean[" + count + "].allocateByChargeNumber3", cellValue(p, "allocateByChargeNumber3"));
	            addNewFormElement(labelForm, "PicklistViewBean[" + count + "].allocateByChargeNumber4", cellValue(p, "allocateByChargeNumber4"));
	            addNewFormElement(labelForm, "PicklistViewBean[" + count + "].receiptSpecNameList", cellValue(p, "receiptSpecNameList"));
	            addNewFormElement(labelForm, "PicklistViewBean[" + count + "].receiptSpecVersion", cellValue(p, "receiptSpecVersion"));
	            addNewFormElement(labelForm, "PicklistViewBean[" + count + "].releaseNumber", cellValue(p, "releaseNumber"));
		            
	            var qp = cellValue(p, "quantityPicked");
	            if(qp == "" || qp == "&nbsp;")
	            	addNewFormElement(labelForm, "PicklistViewBean[" + count + "].quantityPicked", "0");
	            else
		            addNewFormElement(labelForm, "PicklistViewBean[" + count + "].quantityPicked", cellValue(p, "quantityPicked"));
	            	addNewFormElement(labelForm, "PicklistViewBean[" + count + "].expireDate", cellValue(p, "expDate"));
	            	addNewFormElement(labelForm, "PicklistViewBean[" + count + "].pickable", cellValue(p, "pickable"));
	        	count++;
			}
			else
			{
			   var j=p;
				 while($("print" + (j)) == null)
					 j--;
					   if($("print" + (j)).checked)
					   {
							addNewFormElement(labelForm, "PicklistViewBean[" + count + "].ok", cellValue(p, "print"));
							addNewFormElement(labelForm, "PicklistViewBean[" + count + "].prNumber", cellValue(p, "prNumber"));
							addNewFormElement(labelForm, "PicklistViewBean[" + count + "].lineItem", cellValue(p, "lineItem"));
							addNewFormElement(labelForm, "PicklistViewBean[" + count + "].picklistId", cellValue(p, "picklistId"));
							addNewFormElement(labelForm, "PicklistViewBean[" + count + "].mrLine", cellValue(p, "mrLine"));
							addNewFormElement(labelForm, "PicklistViewBean[" + count + "].itemId", cellValue(p, "item"));
							addNewFormElement(labelForm, "PicklistViewBean[" + count + "].receiptId", cellValue(p, "receiptId"));
				            addNewFormElement(labelForm, "PicklistViewBean[" + count + "].facilityId", cellValue(p, "facility"));
				            addNewFormElement(labelForm, "PicklistViewBean[" + count + "].application", cellValue(p, "application")); 
				            addNewFormElement(labelForm, "PicklistViewBean[" + count + "].catPartNo", cellValue(p, "partNumber"));
				           		var qp = cellValue(p, "quantityPicked");
				            if(qp == "" || qp == "&nbsp;")
				            	addNewFormElement(labelForm, "PicklistViewBean[" + count + "].quantityPicked", "0");
				            else
					            addNewFormElement(labelForm, "PicklistViewBean[" + count + "].quantityPicked", cellValue(p, "quantityPicked"));
				            addNewFormElement(labelForm, "PicklistViewBean[" + count + "].expireDate", cellValue(p, "expDate"));
				            addNewFormElement(labelForm, "PicklistViewBean[" + count + "].pickable", cellValue(p, "pickable"));
				            addNewFormElement(labelForm, "PicklistViewBean[" + count + "].customerPartNo", cellValue(p, "customerPartNo"));
				            addNewFormElement(labelForm, "PicklistViewBean[" + count + "].reportingEntityName", cellValue(p, "reportingEntityName"));
				            addNewFormElement(labelForm, "PicklistViewBean[" + count + "].applicationDesc", cellValue(p, "applicationDesc"));
				            addNewFormElement(labelForm, "PicklistViewBean[" + count + "].requestor", cellValue(p, "requestor"));
				            addNewFormElement(labelForm, "PicklistViewBean[" + count + "].hub", cellValue(p, "hub"));
				            addNewFormElement(labelForm, "PicklistViewBean[" + count + "].inventoryGroup", cellValue(p, "inventoryGroup"));
				            addNewFormElement(labelForm, "PicklistViewBean[" + count + "].partDescription", cellValue(p, "partDescription"));
				            addNewFormElement(labelForm, "PicklistViewBean[" + count + "].picklistQuantity", cellValue(p, "picklistQuantity"));
				            addNewFormElement(labelForm, "PicklistViewBean[" + count + "].shipToLocationId", cellValue(p, "shipTo"));
				            addNewFormElement(labelForm, "PicklistViewBean[" + count + "].packaging", cellValue(p, "packaging"));
				            addNewFormElement(labelForm, "PicklistViewBean[" + count + "].companyId", cellValue(p, "companyId"));
				            addNewFormElement(labelForm, "PicklistViewBean[" + count + "].endUser", cellValue(p, "endUser"));
				            addNewFormElement(labelForm, "PicklistViewBean[" + count + "].mfgLot", cellValue(p, "mfgLot"));
				            addNewFormElement(labelForm, "PicklistViewBean[" + count + "].issueId", cellValue(p, "issueId"));
				            addNewFormElement(labelForm, "PicklistViewBean[" + count + "].ownerSegmentId", cellValue(p, "ownerSegmentId"));
				            addNewFormElement(labelForm, "PicklistViewBean[" + count + "].allocateByChargeNumber1", cellValue(p, "allocateByChargeNumber1"));
				            addNewFormElement(labelForm, "PicklistViewBean[" + count + "].allocateByChargeNumber2", cellValue(p, "allocateByChargeNumber2"));
				            addNewFormElement(labelForm, "PicklistViewBean[" + count + "].allocateByChargeNumber3", cellValue(p, "allocateByChargeNumber3"));
				            addNewFormElement(labelForm, "PicklistViewBean[" + count + "].allocateByChargeNumber4", cellValue(p, "allocateByChargeNumber4"));
				            addNewFormElement(labelForm, "PicklistViewBean[" + count + "].receiptSpecNameList", cellValue(p, "receiptSpecNameList"));
				            addNewFormElement(labelForm, "PicklistViewBean[" + count + "].receiptSpecVersion", cellValue(p, "receiptSpecVersion"));
				            addNewFormElement(labelForm, "PicklistViewBean[" + count + "].releaseNumber", cellValue(p, "releaseNumber"));

							count++;	 
					   }
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

function doPrintBolLong() {
	   var checked = countChecked();
	   if (checked==0) {
	      alert(messagesData.pleasemakeselection);
	      return false;
	   }
	   else {
	   	   openWinGeneric('/tcmIS/common/loadingfile.jsp', 'printBolLongddd','800','600','yes',"80","80");
		   var labelForm = document.getElementById("LabelPrintForm");
		   clearLabelForm(labelForm);
		   addNewFormElement(labelForm, "userAction", 'printBolLong');
		   document.LabelPrintForm.target = 'printBolLongddd';
		   getSelected(labelForm);	
		   labelForm.submit();	
		   setTimeout('window.status="";',5000);
	    }
	}

function doPrintBoxLabels() {
	   var checked = countChecked();
	   if (checked==0) {
	      alert(messagesData.pleasemakeselection);
	      return false;
	   }
	   else {
	    	openWinGeneric('/tcmIS/common/loadingfile.jsp', 'printBoxLabelsddd','800','600','yes',"80","80");
		   var labelForm = document.getElementById("LabelPrintForm");
		   clearLabelForm(labelForm);
		   addNewFormElement(labelForm, "userAction", 'printBoxLabels');
		   document.LabelPrintForm.target = 'printBoxLabelsddd';
		   getSelected(labelForm);	
		   labelForm.submit();	
		   setTimeout('window.status="";',5000);
	    }
	}

function doReprintLables(){
	   var checked = countChecked();
	   if (checked==0) {
	      alert(messagesData.pleasemakeselection);
	      return false;
	   }
	   else {
	    	openWinGeneric('/tcmIS/common/loadingfile.jsp', 'rePrintLabels','800','600','yes',"80","80");
		   var labelForm = document.getElementById("LabelPrintForm");
		   clearLabelForm(labelForm);
		   addNewFormElement(labelForm, "userAction", 'rePrintLabels');
		   addNewFormElement(labelForm, "PicklistViewBean[0].materialRequestOriginCount", $("materialRequestOriginCount").value);
		   document.LabelPrintForm.target = 'rePrintLabels';
		   getSelected(labelForm);	
		   labelForm.submit();	
		   setTimeout('window.status="";',5000);
	    }
}

function doDeliveryTicket(){
	   var checked = countChecked();
	   if (checked==0) {
	      alert(messagesData.pleasemakeselection);
	      return false;
	   }
	   else {

	       openWinGeneric('/tcmIS/common/loadingfile.jsp', 'deliveryTicket','800','600','yes',"80","80");
		   var labelForm = document.getElementById("LabelPrintForm");
		   clearLabelForm(labelForm);
		   addNewFormElement(labelForm, "userAction", 'generatedelvtkt');
		   document.LabelPrintForm.target = 'deliveryTicket';
		   getSelected(labelForm);	
		   labelForm.submit();	
		   setTimeout('window.status="";',5000);
	    }
}



function doPrintExitLabels() {
	   var checked = countChecked();
	   if (checked==0) {
	      alert(messagesData.pleasemakeselection);
	      return false;
	   }
	   else {
		   openWinGeneric('/tcmIS/common/loadingfile.jsp', 'printExitLabels','800','600','yes',"80","80");
		   var labelForm = document.getElementById("LabelPrintForm");
		   clearLabelForm(labelForm);
		   addNewFormElement(labelForm, "userAction", 'printExitLabels');
		   addNewFormElement(labelForm, "PicklistViewBean[0].materialRequestOriginCount", $("materialRequestOriginCount").value);
		   document.LabelPrintForm.target = 'printExitLabels';
		   getSelected(labelForm);	
		   labelForm.submit();	
		   setTimeout('window.status="";',5000);
	    }
	}

function countChecked() {
	var totallines = document.getElementById("totalLines").value;
	var totalChecked = 0;
	for ( var p = 1; p <= totallines; p++ ) {
		if (haasGrid.haasRowIsRendered(p)) {
			var checkbox = document.getElementById("print" + p);
			if (checkbox != null && checkbox.checked == true) {
				totalChecked++;
			}
		}
	}
	return totalChecked;
}

function colorSpan(rowId)
{
	//renderAllRows();
	 for(var i=0;i<lineMap4.length;i++)
		if(rowId == lineMap4[i])
			haasGrid.rowsAr[lineMap4[i]+1].className = "grid_yellow";
}

function setUp()
{
    if ($v("isWmsHub") == 'Y') {
        showUpdateLinks = false;
        displayActions("none");
    }else {
        displayActions("");
        for (var p = 0; p < permissionArray.length; p++) {
            if (permissionArray[p] != null) {
                var spanSize = parseInt(permissionArray[p]);
                for (var i = 0; i < spanSize; i++)
                    jsonMainData.rows[p + i].data[1] = 'N';
            }
        }
        var mroCount = document.getElementById("materialRequestOriginCount").value;
        if (mroCount != "") {
            mroCount = parseInt(mroCount);
            if (mroCount > 0)

                parent.document.getElementById("doExitLablesLink").style["display"] = "";
        }
    }
}

function displayActions(displayStyle) {
    parent.document.getElementById("doPrintBolShortLink").style["display"] = displayStyle;
    parent.document.getElementById("doPrintBolLongLink").style["display"] = displayStyle;
    parent.document.getElementById("doPrintDelieveryLabelLink").style["display"] = displayStyle;
    parent.document.getElementById("doReprintLablesLink").style["display"] = displayStyle;
    parent.document.getElementById("doRDeliveryTicketLink").style["display"] = displayStyle;
    parent.document.getElementById("doExitLablesLink").style["display"] = displayStyle;
}
 
// -->
</script>

</head>

<body bgcolor="#ffffff" onload="setUp();resultOnLoadWithGrid(gridConfig);">
<tcmis:form action="/pickingqcresults.do" onsubmit="return submitFrameOnlyOnce();">
<div id="errorMessagesAreaBody" style="display:none;">
    <html:errors/>
    ${tcmISError}
    <c:forEach items="${tcmISErrors}" varStatus="status">
  	${status.current}<br/>
    </c:forEach>        
</div>

<script type="text/javascript">
   <c:choose>
    <c:when test="${requestScope['org.apache.struts.action.ERROR'] == null && empty tcmISError && empty tcmISErrors }">
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

<div id="PicklistViewBean" style="width: 100%; height: 600px;" style="display: none;"></div>

<script type="text/javascript">
<!--
<c:set var="showUpdateLink" value='N'/>
<c:set var="dataCount" value='${0}'/>
<c:set var="materialRequestOriginCount" value="0"/>

<c:set var="confirmPermission" value='N'/>
<tcmis:facilityPermission indicator="true" userGroupId="Picking QC" facilityId="${param.selectedHubName}">
<c:set var="confirmPermission" value='Y'/>
</tcmis:facilityPermission>

<c:if test="${!empty picklistColl}" >  
var jsonMainData = new Array();
var jsonMainData = {
rows:[
<c:forEach var="topBean" items="${picklistColl}" varStatus="topStatus">
<c:forEach var="itemBean" items="${topBean.items}" varStatus="itemStatus">
<c:forEach var="line" items="${itemBean.receipts}" varStatus="status">
<c:set var="lineCount" value='${lineCount+1}'/> 
<c:if test="${lineCount > 1}">,</c:if>
<tcmis:jsReplace var="partDesc" value="${itemBean.partDescription}" processMultiLines="true" />
<tcmis:jsReplace var="mrNotes" value="${topBean.mrNotes}" processMultiLines="true" />
<tcmis:jsReplace var="mfgLot" value="${line.mfgLot}" processMultiLines="true" />
<tcmis:jsReplace var="facilityName" value="${topBean.facilityName}" processMultiLines="false" />
<tcmis:jsReplace var="shipToLocationDesc" value="${topBean.shipToLocationDesc}" processMultiLines="false" />
<fmt:formatDate var="expireDate" value="${line.expireDate}" pattern="${dateFormatPattern}"/>
<fmt:formatDate var="needDate" value="${topBean.needDate}" pattern="${dateFormatPattern}"/>
<c:set var="receiptId">${line.receiptId} <img src="/images/buttons/document.gif"/></c:set>
<c:if test="${line.receiptDocumentAvailable == 'N'}"><c:set var="receiptId" value="${line.receiptId}"/></c:if>
<c:set var="picklistId" value='PL ${topBean.picklistId}'/>
<c:if test="${line.materialRequestOrigin == 'Order Entry'}"><c:set var="materialRequestOriginCount" value="${materialRequestOriginCount + 1}"/></c:if>

<c:set var="waDesc" value="${topBean.deliveryPoint}"/>
<c:if test="${topBean.deliveryPoint != topBean.applicationDesc}"><c:set var="waDesc" value="${topBean.applicationDesc}"/></c:if>

<c:set var="qPPermission" value="Y"/>
<c:if test="${confirmPermission == 'N' || line.qcDate != null || line.pickable != 'Y'
              || line.mrCompletePickable != 'Y' || line.hazmatIdMissing == 'MISSING'
              || param.isWmsHub == 'Y'}">
    <c:set var="qPPermission" value="N"/>
</c:if>

<c:set var="rowPermission" value="Y"/>
<c:if test="${param.isWmsHub == 'Y'}">
    <c:set var="rowPermission" value="N"/>
</c:if>

<c:set var="closeMr" value=""/>
<c:if test="${line.qcDate == null && topBean.cabinetReplenishment != '' && param.isWmsHub == 'N'}">
    <c:set var="checked" value=""/>
    <c:if test="${topBean.cabinetReplenishment != ''}"><c:set var="checked" value="checked"/></c:if>
    <c:set var="closeMr" value='<input type="checkbox" value="" onClick="return setMrClose(this,${status.index})" name="closeMr${status.index}" id="closeMr${status.index}">'/>
</c:if>
 

/*The row ID needs to start with 1 per their design.*/
{ id:${lineCount},
<c:if test="${topBean.critical eq 'y'}">'class':"grid_red",</c:if>
<c:if test="${topBean.critical eq 's'}">'class':"grid_pink",</c:if>
 data:['${rowPermission}',
  '${qPPermission}',
  false,
  '${picklistId}',
  '${facilityName}',
  '${waDesc}',
  '${topBean.deliveryPoint}',
  '${shipToLocationDesc}',
  '<tcmis:jsReplace value="${topBean.requestor}" processMultiLines="true" />',
  '${topBean.mrLine}',
  '${mrNotes}',
  '${topBean.catPartNo}',
  '${topBean.stockingMethod}',
  '${itemBean.itemId}',
  '${partDesc}',
  '${closeMr}',
  '<tcmis:jsReplace value="${line.packaging}" processMultiLines="true" />',
  '${line.bin}',
  '${receiptId}',
  '${mfgLot}',
  '${expireDate}',
  '${topBean.mrLine}',
  '${line.picklistQuantity}',
  '${line.quantityPicked}',
  '${topBean.prNumber}',
  '${topBean.lineItem}',
  '${line.inventoryGroup}',
  '${line.receiptId}',
  '${topBean.cabinetReplenishment}',
  '${line.expireDate.time}',
  '${line.pickable}',
  '${line.mrCompletePickable}',
  '${topBean.hub}',
  '${needDate}',
  '${topBean.companyId}',
  false,
  '${topBean.picklistId}',
  '<tcmis:jsReplace value="${itemBean.application}" processMultiLines="true" />',
  '${itemBean.pickable}',
  '<tcmis:jsReplace value="${line.customerPartNo}" processMultiLines="true" />',
  '${line.reportingEntityName}',
  '<tcmis:jsReplace value="${line.endUser}" processMultiLines="true" />',
  '${waDesc}',
  '${topBean.issueId}',
  '${line.ownerSegmentId}',
  '${line.allocateByChargeNumber1}',
  '${line.allocateByChargeNumber2}',
  '${line.allocateByChargeNumber3}',
  '${line.allocateByChargeNumber4}',
  '${line.receiptSpecNameList}',
  '${line.receiptSpecVersion}',
  '${line.releaseNumber}',
  '${topBean.facilityId}',
  '${topBean.shipToLocationId}'
  ]}  
 <c:set var="dataCount" value='${dataCount+1}'/> 
</c:forEach>
</c:forEach>
</c:forEach>
]};
</c:if>

//-->  
</script>

<!-- If the collection is empty say no data found -->
<c:if test="${empty picklistColl}">
  <table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableNoData" id="resultsPageTable">
    <tr>
       <td width="100%">
          <fmt:message key="main.nodatafound"/>
       </td>
    </tr>
  </table>
</c:if>

        
<!-- Search results end -->

<!-- Hidden element start --> 
<div id="hiddenElements" style="display: none;">    			
	<input name="totalLines" id="totalLines" value="<c:out value="${dataCount}"/>" type="hidden">
    <input name="userAction" id="userAction" type="hidden" value='<tcmis:jsReplace value="${param.userAction}"/>'/>
    <input name="minHeight" id="minHeight" type="hidden" value="100">
    <input name="hub" id="hub" type="hidden" value='<tcmis:jsReplace value="${param.hub}"/>'/>
    <input name="prNumber" id="prNumber" type="hidden" value='<tcmis:jsReplace value="${param.prNumber}"/>'/>
    <input name="facilityId" id="facilityId" type="hidden" value='<tcmis:jsReplace value="${param.facilityId}"/>'/>
    <input name="picklistId"  id="picklistId" type="hidden" value='<tcmis:jsReplace value="${param.picklistId}"/>'/>
     <input name="materialRequestOriginCount"  id="materialRequestOriginCount" type="hidden" value="${materialRequestOriginCount}">
     <input name="opsEntityId"  id="opsEntityId" type="hidden" value='<tcmis:jsReplace value="${param.opsEntityId}"/>'/>
     <input name="inventoryGroup"  id="inventoryGroup" type="hidden" value='<tcmis:jsReplace value="${param.inventoryGroup}"/>'/>
     <input type="hidden" name="personnelCompanyId"  id="personnelCompanyId" value="${personnelBean.companyId}"/>
     <input type="hidden" name="isWmsHub"  id="isWmsHub" value='<tcmis:jsReplace value="${param.isWmsHub}"/>'/>
</div>
<!-- Hidden elements end -->

</div>
<!-- close of backGroundContent -->
</div>
<!-- close of interface -->
<script type="text/javascript">
showUpdateLinks = false;
<c:if test="${confirmPermission == 'Y'}">

        <!--
        showUpdateLinks = true;
        //-->

</c:if>
</script>

</tcmis:form>
			<%-- determining rowspan --%>
				<c:set var="lineCount" value='0' />
				<c:set var="colorCount" value='0' />
				<c:forEach var="topBean" items="${picklistColl}" varStatus="topStatus">
					<c:forEach var="itemBean" items="${topBean.items}" varStatus="itemStatus">
						<c:set var="rowspan" value="${itemBean.receiptRowspan}" />
						<c:forEach var="line" items="${itemBean.receipts}" varStatus="lineStatus">
						<script language="JavaScript" type="text/javascript">	
							<c:if test="${line.pickable eq 'N' or line.mrCompletePickable eq 'N'}">
				
							    lineMap4[${colorCount}]= ${lineCount};
						 		<c:set var="colorCount" value='${colorCount+1}'/>
							</c:if>
							<c:choose>
								<c:when test="${lineStatus.first}">
									rowSpanMap[${lineCount}]= ${rowspan};
									<c:if test="${topBean.qcDate != null}">permissionArray[${lineCount}] = ${rowspan};</c:if>
									<c:set var="prevLineItem" value="${line.prNumber-line.lineItem}"/>
									<c:set var="prevLineIndex" value="${lineCount}"/>
									rowSpanLvl2Map[${lineCount}] = 1;
								</c:when>
								<c:otherwise>
									rowSpanMap[${lineCount}]= 0;
									<c:choose>
										<c:when test="${prevLineItem == line.prNumber-line.lineItem}">
											rowSpanLvl2Map[${lineCount}] = 0;
											rowSpanLvl2Map[${prevLineIndex}]++;
										</c:when>
										<c:otherwise>
											<c:set var="prevLineItem" value="${line.prNumber-line.lineItem}"/>
											<c:set var="prevLineIndex" value="${lineCount}"/>
											rowSpanLvl2Map[${lineCount}]=1;
										</c:otherwise>
									</c:choose>
								</c:otherwise>
							</c:choose>
							rowSpanClassMap[${lineCount}]= ${topStatus.count % 2}; // lineCount == ${lineCount}, status.count == ${status.count}		
							<c:set var="lineCount" value='${lineCount+1}'/>
							</script>
						</c:forEach>
					</c:forEach>
					</c:forEach>
					
<c:choose>		
	<c:when test="${personnelBean.companyId == 'Radian'}">
		<form name="LabelPrintForm" id="LabelPrintForm" method="post" action="/tcmIS/hub/pickingqcresults.do">
		    <input name="target" id="target" value="" type="hidden">
		</form>   
	</c:when>
	<c:otherwise>			
		<form name="LabelPrintForm" id="LabelPrintForm" method="post" action="pickingqcresults.do">
		    <input name="target" id="target" value="" type="hidden">
		</form>     
	</c:otherwise>
</c:choose>

</body>
</html:html>