<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
	"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html;charset=UTF-8" language="java"%>

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
<%-- Add any other stylesheets you need for the page here --%>

<script type="text/javascript" src="/js/common/formchek.js"></script>
<script type="text/javascript" src="/js/common/commonutil.js"></script>
<!-- This handles all the resizing of the page and frames -->
<script type="text/javascript" src="/js/common/grid/nosearchresize.js"></script>
<!-- This handels which key press events are disabeled on this page -->
<script type="text/javascript" src="/js/common/disableKeys.js"></script>

<!-- This handels the menu style and what happens to the right click on the whole page -->
<script type="text/javascript" src="/js/menu/milonic_src.js"></script>
<script type="text/javascript" src="/js/menu/mmenudom.js"></script>
<script type="text/javascript" src="/js/menu/mainmenudata.js"></script>
<script type="text/javascript" src="/js/menu/contextmenu.js"></script>
<%@ include file="/common/rightclickmenudata.jsp" %>

<!-- Add any other javascript you need for the page here -->
<script type="text/javascript" src="/js/common/noSearchStandardGridmain.js"></script>
<script type="text/javascript" src="/js/hub/shipmenthistorydetails.js"></script>

<!-- These are for the Grid-->
<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxcommon.js"></script>
<script type="text/javascript" src="/dhtmlxWindows/codebase/dhtmlxwindows.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxgrid.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxgridcell.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_form.js"></script>

<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_json.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/excells/dhtmlxgrid_excell_customized.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxcommon_haas.js"></script>

<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_srndRowSpan.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_rowspan.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/rowspan_cell_patch.js"></script>


<title>
<fmt:message key="label.shipmenthistorydetails"/> : ${param.shipmentId}
</title>

<script language="JavaScript" type="text/javascript">
//add all the javascript messages here, this for internationalization of client side javascript messages
	var messagesData = new Array();
	messagesData={alert:"<fmt:message key="label.alert"/>",and:"<fmt:message key="label.and"/>",
	recordFound:"<fmt:message key="label.recordFound"/>",searchDuration:"<fmt:message key="label.searchDuration"/>",
	minutes:"<fmt:message key="label.minutes"/>",seconds:"<fmt:message key="label.seconds"/>",
	validvalues:"<fmt:message key="label.validvalues"/>",submitOnlyOnce:"<fmt:message key="label.submitOnlyOnce"/>",
	errors:"<fmt:message key="label.errors"/>",itemInteger:"<fmt:message key="error.item.integer"/>"};
	
	var gridConfig = {
		divName:'shipmentHistoryDetailsViewBean', // the div id to contain the grid.
		beanData:'jsonMainData',     // the data variable name for jsonparse, as in grid.parse( beanData ,"json" )
		beanGrid:'beanGrid',     // the grid's name, as in beanGrid.attachEvent...
		config:'config',	     // the column config var name, as in var config = { [ columnId:..,columnName...
		rowSpan:'true',			 // this page has rowSpan > 1 or not.
		noSmartRender: true,
		submitDefault:false,    // the fields in grid are defaulted to be submitted or not.
		onRowSelect:selectRow,   // the onRowSelect event function to be attached, as in beanGrid.attachEvent("onRowSelect",selectRow)
		onRightClick:rightMouseClick,  // the onRightClick event function to be attached, as in beanGrid.attachEvent("onRightClick",selectRow)
	//	onBeforeSorting:_onBeforeSorting
		singleClickEdit:true
	};
	
var lastSelectedRow = -1;
var lastSelectedClass;
function selectRow(rowId, colId) { 
	if (lastSelectedRow != -1 && mrRowspanMap[lastSelectedRow - 1] != 1) {
		deselectRow(getParentRowId(lastSelectedRow));
	}
	
	if (mrRowspanMap[rowId - 1] != 1) {
		selectRowspan(getParentRowId(rowId));
	}
	
	lastSelectedRow = rowId;
}

function rightMouseClick (rowId, colId) {
	selectRow(rowId, colId)	

	var inventoryGroup = '${param.inventoryGroup}';

	if('Dallas LM Co-Producer' == inventoryGroup || 'FTW LM Co-Producer' == inventoryGroup)
		toggleContextMenu('showLineCharge');
	else
		toggleContextMenu('contextMenu');  
}
	
with ( milonic=new menuname("showLineCharge") ) {
 top="offset=2";
 style=submenuStyle;
 itemheight=17;
// style = contextStyle;
// margin=3;
 aI( "text=<fmt:message key="label.linecharges"/> ;url=javascript:addLineCharges();" );
}

drawMenus();

<c:set var="shipmenthistory"><fmt:message key="shipmenthistory.title"/></c:set>
function getcurpath (){
	return encodeURIComponent('<tcmis:jsReplace value="${shipmenthistory}"/>');
}
	
function addLineCharges() { 
//      showTransitWin('<fmt:message key="label.linecharges"/>');
      loc = "/tcmIS/distribution/addchargeline.do?orderType=MR"+"&status=&prNumber="+cellValue(lastSelectedRow,"prNumber")+
	  		"&companyId="+'${param.companyId}'+
	  		"&lineItem="+cellValue(lastSelectedRow,"lineItem")+
	  		"&curpath="+getcurpath()+
            "&opsEntityId="+'${param.opsEntityId}'+
            "&currencyId=USD";
	  var winId= 'addHeaderCharge'+cellValue(lastSelectedRow,"prNumber");
	  openWinGeneric(loc,winId.replace('.','a'),"820","400","yes","50","50","20","20","no");
}	
	/*This array defines the grid and properties about the columns in the grid.
	* more explanation of the grid config can be found in /dhtmlxGrid/codebase/dhtmlxcommon_haas.js initGridWithConfig()
	* */
	/* At this point there is only page permission protection. */
function _waitsimpleUpdate() {
	document.genericForm.target = '_self';
	setTimeout('_simpleUpdate()',100);
}

function setAllRef()
{
  var colName = 'shippingReference';
  var val = $v('refAll');
  var rowsNum = haasGrid.getRowsNum();
  var result;

  rowsNum = rowsNum*1;
  renderAllRows();
  
  for (var p = 1 ; p < (rowsNum+1) ; p ++)
  {
	try 
	{
	  haasGrid.cells(p, haasGrid.getColIndexById(colName)).setValue(val);
	}
	catch (ex1)
	{}
  }
  return true;
}
	
	<c:set var="itemid"><fmt:message key="label.itemid"/></c:set>
	var config = [
			{columnId:"permission" },
	      	{ columnId:"prNumber", columnName:'<fmt:message key="label.mr"/>', width:8, sorting:'na' , submit:true},
	      	{ columnId:"poNumber", columnName:'<fmt:message key="label.ponumber"/>', tooltip:"Y"},
	      	{ columnId:"customerName" <%--columnName:'<fmt:message key="label.customer"/>', tooltip:"Y", width:10--%>},
	      	{ columnId:"shipTo" <%--columnName:'<fmt:message key="label.shipto"/>', tooltip:"Y", width:10--%>},
	      	{ columnId:"requester", columnName:'<fmt:message key="label.requestor"/>', tooltip:"Y", width:10, sorting:'na'},
	      	{ columnId:"customerServiceRep", columnName:'<fmt:message key="label.csr"/>', tooltip:"Y", width:10, sorting:'na'},
	      	{ columnId:"lineItem", columnName:'<fmt:message key="label.lineitem"/>', width:4, sorting:'na' , submit:true},
	      	{ columnId:"shippingReference",
	      		columnName:'<fmt:message key="label.shippingreferenece"/> <br> <input class="inputBox" name="refAll" id="refAll" type="text" size=9 onChange="setAllRef();"/>',
		      	type:'hed', width:10, sorting:'na', align:'center',
		      	submit:true
		    },
	      	{ columnId:"partNumber", columnName:'<fmt:message key="label.partnumber"/>', width:10, sorting:'na'},
	      	{ columnId:"itemId", columnName:'<tcmis:jsReplace value="${itemid}"/>', width:5, sorting:'na'},
	      	{ columnId:"itemDesc", columnName:'<fmt:message key="label.itemdesc"/>', tooltip:"Y", width:15, sorting:'na'},
	      	{ columnId:"receipt", columnName:'<fmt:message key="label.receipt"/>', width:5, sorting:'na'},
	      	{ columnId:"quantityShipped", columnName:'<fmt:message key="label.quantityshipped"/>', width:5, sorting:'na'},
	      	{ columnId:"mfgLot", columnName:'<fmt:message key="label.mfglot"/>', width:10, sorting:'na'},
	      	{ columnId:"expireDate", columnName:'<fmt:message key="label.expiredate"/>', width:8, sorting:'na'},
	      	{ columnId:"qcDate", columnName:'<fmt:message key="label.qcdate"/>', sorting:'na', width:12},
	      	{ columnId:"qcBy", columnName:'<fmt:message key="label.qcedby"/>', tooltip:"Y", width:10, sorting:'na'},
	      	{ columnId:"shipConfirmDate", columnName:'<fmt:message key="label.shipconfirmdate"/>', sorting:'na', width:12},
	      	{ columnId:"shipConfirmBy", columnName:'<fmt:message key="label.shipconfirmby"/>', width:10, sorting:'na'},
	      	{ columnId:"pickListId", columnName:'<fmt:message key="label.picklistid"/>', width:5, sorting:'na'},
	      	{ columnId:"oldShippingReference", submit:true }
	      ];

	var mrRowspanMap;
	var itemRowspanMap;
	var lineRowspanMap;
	function stopP() {
		alert('to here');
		try { stopPleaseWait();//$('transitPage').style["display"] = ""; 
		} catch(ex){}
	}
 </script>
</head>

<body bgcolor="#ffffff"  onload="popupOnLoadWithGrid(gridConfig);setTimeout('resultOnLoad()',100);" onresize="resizeFrames()">

<tcmis:form action="/shipmenthistorydetails.do" onsubmit="return submitFrameOnlyOnce();" >

<div class="interface" id="resultsPage">
<!-- Error Messages Begins -->
	<div id="errorMessagesAreaBody" style="display:none;">
	  ${tcmISError}<br/>
	  <c:forEach items="${tcmISErrors}" varStatus="status">
	  	${status.current}<br/>
	  </c:forEach>
	</div>

	<script type="text/javascript">
	<!--
	/*Check if there is any error messages to show and set the variable you use in javascript to true or false.*/
	<c:choose>
	   <c:when test="${empty tcmISErrors and empty tcmISError}">
	    showErrorMessage = false;
	   </c:when>
	   <c:otherwise>
	    showErrorMessage = true;
	   </c:otherwise>
	</c:choose>
	//-->
	</script>
<!-- Error Messages Ends -->

<!-- Result Frame Begins -->
<div id="resultFrameDiv" style="margin: 4px 4px 0px 4px;">
<!-- Transit Page Begins -->
	<div id="transitPage" style="display: none;" class="optionTitleBoldCenter">
	  <br><br><br><fmt:message key="label.pleasewait"/>
	  <br><br><br><img src="/images/rel_interstitial_loading.gif" align="middle">
	</div>
<!-- Transit Page Ends -->
<div id="resultGridDiv" style="display: none;">
<!-- results start -->
<!-- If you want your results section to span only 50% set this to 50% or 800 pixels. Make sure to set resizeGridWithWindow to false. -->
<table id="resultsMaskTable" width="100%" border="0" cellpadding="0" cellspacing="0">
<tr>
<td>
<div class="roundcont contentContainer">
<div class="roundright">
  <div class="roundtop">
    <div class="roundtopright"> <img src="/images/rndBoxes/borderTL.gif" alt="" width="15" height="15" class="corner" style="display: none" /></div>
  </div>
  <div class="roundContent">
    <div class="boxhead"> <%-- boxhead Begins --%>
     <%-- Place all update links in divs that can be turned on or off depending on the search criteria that is selected
          Do not remove mainUpdateLinks div, even if you don't have any links. You can remove content inside the div but not the div itself.
          Please keep the <span></span> on the same line this will avoid extra virtical space.
      --%>
     <%-- Use this case if you only have one update link to minimize extra line.  Notice this uses "div" instead of "span" --%>
	  <div id="mainUpdateLinks" > <%-- mainUpdateLinks Begins --%>
      <div id="updateResultLink" >
		 <a href="#" onclick="_waitsimpleUpdate()"><fmt:message key="label.update"/></a>   |
         <a href="#" onclick="return createXSL();"><fmt:message key="label.createExcel"/></a>
      </div>
     </div> <%-- mainUpdateLinks Ends --%>
     <%-- END OF OR --%>
	</div> <%-- boxhead Ends --%>

<div class="dataContent">
     <!--Give the div name that holds the grid the same name as your viewbean or dynabean you want for updates-->
 <div id="shipmentHistoryDetailsViewBean" style="width:100%;height:600px;" style="display: none;"></div>
<!-- Search results start -->
<c:if test="${shipmentHistoryDetailsViewBeanColl != null}">
	<script type="text/javascript">
	/*This is to keep track of whether to show any update links.
	If the user has any update permisions for any row then we show update links.*/
	<c:set var="showUpdateLink" value='Y'/>
	<c:set var="dataCount" value='${0}'/>
	<c:if test="${!empty shipmentHistoryDetailsViewBeanColl}" >
		/*Storing the data to be displayed in a JSON object array.*/
		//var jsonMainData = new Array();
		var jsonMainData = {
			rows:[
			<c:forEach var="shipmentHistoryDetailsViewBean" items="${shipmentHistoryDetailsViewBeanColl}" varStatus="status">
				<c:if test="${status.index > 0}">,</c:if>
				<fmt:formatDate var="fmtExpireDate" value="${status.current.expireDate}" pattern="${dateFormatPattern}"/>
				<c:set var="expireDateTime" value="${status.current.expireDate.time}"/>
				<c:set var="qcDateTime" value="${status.current.qcDate.time}"/>
				<c:set var="shipConfirmDate" value="${status.current.shipConfirmDate.time}"/>
				<fmt:formatDate var="fmtQcDate" value="${status.current.qcDate}" pattern="${dateTimeFormatPattern}"/>
				<fmt:formatDate var="fmtshipConfirmDate" value="${status.current.shipConfirmDate}" pattern="${dateTimeFormatPattern}"/>
				<tcmis:jsReplace var="addressLine1" value="${status.current.addressLine1}"  processMultiLines="true"/>
				<tcmis:jsReplace var="addressLine2" value="${status.current.addressLine2}"  processMultiLines="true"/>
				<tcmis:jsReplace var="addressLine3" value="${status.current.addressLine3}"  processMultiLines="true"/>
				<tcmis:jsReplace var="addressLine4" value="${status.current.addressLine4}"  processMultiLines="true"/>
				<tcmis:jsReplace var="addressLine5" value="${status.current.addressLine5}"  processMultiLines="true"/>
				
				<%-- The row ID needs to start with 1 per their design. --%>
				{	id:${status.index +1},
					data:[
						'Y',
						'${status.current.prNumber}',
						'<tcmis:jsReplace value="${status.current.poNumber}"/>',
						'<tcmis:jsReplace value="${status.current.customerName}"/>',
						'${addressLine1} ${addressLine2} ${addressLine3} ${addressLine4} ${addressLine5}',
						'<tcmis:jsReplace value="${status.current.requester}"/>',
						'<tcmis:jsReplace value="${status.current.customerServiceRep}"/>',
						'${status.current.lineItem}',
						'<tcmis:jsReplace value="${status.current.shippingReference}"/>',
						'<tcmis:jsReplace value="${status.current.facpartNo}"/>',
						'${status.current.itemId}',
						'<tcmis:jsReplace value="${status.current.itemDesc}"/>',
						'${status.current.receiptId}',
						'${status.current.quantityShipped}',
						'<tcmis:jsReplace value="${status.current.mfgLot}"/>',
						'${fmtExpireDate}',
						'${fmtQcDate}',
						'<tcmis:jsReplace value="${status.current.qcBy}"/>',
						'${fmtshipConfirmDate}',
						'<tcmis:jsReplace value="${status.current.shipConfirmBy}"/>',
						'${status.current.pickListId}',
						'<tcmis:jsReplace value="${status.current.shippingReference}"/>'
					]
				}				
				<c:if test="${status.current.mrRowspan > 0}"><c:set var="dataCount" value='${dataCount+1}'/></c:if>
			</c:forEach>
			]};

			mrRowspanMap = new Array(${dataCount});
		 	itemRowspanMap = new Array(${dataCount});
		 	receiptRowspanMap = new Array(${dataCount});
			
			
			<c:forEach var="line" items="${shipmentHistoryDetailsViewBeanColl}" varStatus="status">
				mrRowspanMap[${status.index}] = ${line.mrRowspan}; 
				itemRowspanMap[${status.index}] = ${line.itemRowspan}; 
				receiptRowspanMap[${status.index}] = ${line.receiptRowspan}; 
			</c:forEach>
	</c:if>
</script>

<!-- If the collection is empty say no data found -->
	<c:if test="${empty shipmentHistoryDetailsViewBeanColl}" >
	<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableNoData" id="resultsPageTable">
	 <tr>
	  <td width="100%">
	   <fmt:message key="main.nodatafound"/>
	  </td>
	 </tr>
	</table>
	</c:if>

</c:if>
<!-- Search results end -->
</div>
   <%-- result count and time --%>
   <div id="footer" class="messageBar"></div>
</div>

  <div class="roundbottom">
    <div class="roundbottomright"> <img src="/images/rndBoxes/borderBL.gif" alt="" width="15" height="15" class="corner" style="display: none" /> </div>
  </div>
</div>
</div>
</td>
</tr>
</table>
<!-- results end -->
</div>
</div>
<!-- Result Frame Ends -->

<!-- Hidden element start -->
<div id="hiddenElements" style="display: none;">
	<input name="totalLines" id="totalLines" value="${dataCount}" type="hidden"/>
	<input name="startSearchTime" id="startSearchTime" type="hidden" value="${startSearchTime}">
	<input name="endSearchTime" id="endSearchTime" type="hidden" value="${endSearchTime}">
	<input name="minHeight" id="minHeight" type="hidden" value="100">
	<input type="hidden" name="uAction" id="uAction" value="createExcel"/>
	<input type="shipmentId" name="shipmentId" id="shipmentId" value="${param.shipmentId}"/>
</div>
<!-- Hidden elements end -->

</div> <!-- close of interface -->

<!-- If the user has permissions and needs to see the update links,set the variable showUpdateLinks javascript to true.
     The default value of showUpdateLinks is false.
-->
<c:if test="${showUpdateLink == 'Y'}">
    <script type="text/javascript">
        showUpdateLinks = true;
    </script>
</c:if>

</tcmis:form>

<!-- You can build your error messages below.
     Similar divs would have to be built to show any other messages in a inline pop-up.-->
<!-- Error Messages Begins -->
<div id="errorMessagesArea" class="errorMessages" style="display: none;overflow: auto;">
</div>

</body>
</html:html>