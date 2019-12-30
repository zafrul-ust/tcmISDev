<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
	"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

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


<!-- Add any other javascript you need for the page here -->
<script type="text/javascript" src="/js/hub/freightinvoicingresults.js"></script>

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
<!--<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_hmenu.js"></script>-->
<%--Uncomment the below if your grid has rwospans >1--%>
<!--<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_rowspan.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/rowspan_cell_patch.js"></script>
-->
<%--This has the custom cells we built, hcal - the internationalized calendar which we use
    hlink- this is for any links you want tp provide in the grid, the URL/function to call
    can be attached based on a even (rowselect etc)
--%>
<script type="text/javascript" src="/dhtmlxGrid/codebase/excells/dhtmlxgrid_excell_customized.js"></script>
<%--This file has our custom sorting and other utility methos for the grid.--%>
<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxcommon_haas.js"></script>

<script type="text/javascript" src="/dhtmlxLayout/codebase/dhtmlxlayout.js"></script>
<script type="text/javascript" src="/dhtmlxWindows/codebase/dhtmlxwindows.js"></script>

<title>
<fmt:message key="label.freightinvoicing"/>
</title>

<script language="JavaScript" type="text/javascript">
<!--
//add all the javascript messages here, this for internationalization of client side javascript messages
var messagesData = new Array();
messagesData={alert:"<fmt:message key="label.alert"/>",
recordFound:"<fmt:message key="label.recordFound"/>",
searchDuration:"<fmt:message key="label.searchDuration"/>",
minutes:"<fmt:message key="label.minutes"/>",
seconds:"<fmt:message key="label.seconds"/>",
pleasewait:"<fmt:message key="label.pleasewaitmenu"/>",
noRowSelected:"<fmt:message key="error.norowselected"/>",
pleaseSelectRowForUpdate:"<fmt:message key="label.pleaseselectarowforupdate"/>",
maxlength:"<fmt:message key="errors.maxlength"/>",
comments:"<fmt:message key="label.comments"/>",
unSavedChanges:"<fmt:message key="label.unsavedchanges"/>",
reprocessConfirmation:"<fmt:message key="label.freightinvoice.confirmation"/>"
};

// arrays for connected dropdowns.

var orderType = [
    {value:'INBOUND', text: 'INBOUND'},
    {value:'OUTBOUND', text: 'OUTBOUND'}

];    

var status = new Array(
	<c:forEach var="bean" items="${freightInvoiceStatusColl}" varStatus="status">
		<c:if test="${status.current.key != 'PROCESSED'}">
		   <c:if test="${status.index > 0}">,</c:if>
		    {value:'${status.current.key}',text:'<fmt:message key="${status.current.value}"/>'}
		</c:if>
	</c:forEach>
);

// result grid config

var config = [
{ columnId:"permission",
  columnName:''
},
{ columnId:"ok",
  columnName:'<fmt:message key="label.ok"/>',
  width:3,
  type:"hch",
  sorting:"haasHch",
  align:"center",
  submit:true
}
,
{ columnId:"invoiceNumber",
  columnName:'<fmt:message key="label.invoicenumber"/>',
  type:"hed",
  size:20,
  width:12,
  submit:true
}
,
{ columnId:"invoicedDate",
  columnName:'<fmt:message key="label.invoicedate"/>',
  width:8,
  submit:true
}
,
{ columnId:"orderNumber",
  columnName:'<fmt:message key="label.ordernumber"/>',
  type:"hed",
  size:20,
  width:12,
  submit:true
}
,
{ columnId:"glCode",
  columnName:'<fmt:message key="label.glcode"/>',
  type:"hed",
  size: 20,
  width:12,
  submit:true
}
,
{ columnId:"carrier",
  columnName:'<fmt:message key="label.carrier"/>',
  width:16,
  submit:true
}
,
{ columnId:"trackingNumber",
  columnName:'<fmt:message key="label.trackingnumber"/>',
  width:16,
  submit:true
}
,
{ columnId:"orderType",
  columnName:'<fmt:message key="label.ordertype"/>',
  type:"hcoro",
  width:8,
  submit:true
}
,
{ columnId:"transportationMode",
  columnName:'<fmt:message key="label.transportationmode"/>',
  width:6,
  submit:true
}
,
{ columnId:"currencyId"}
,
{ columnId:"loadId",
  columnName:'<fmt:message key="label.loadid"/>',
  width:6,
  submit:true
}
,
{ columnId:"loadLine",
  columnName:'<fmt:message key="label.loadline"/>',
  width:6,
  submit:true
}
,
{ columnId:"status",
  columnName:'<fmt:message key="label.status"/>',
  type:"hcoro",
  width:10,
  submit:true
}
,
{ columnId:"errorDetail",
  columnName:'<fmt:message key="label.errordetail"/>',
  width:25,
  submit:true, 
  tooltip: 'Y',
  submit:true
},
{ columnId:"comments",
  columnName:'<fmt:message key="label.comments"/>',
  type:"hed",
  size: 65,
  width:33,
  onChange:limitCommentLength,
  tooltip: 'Y',
  submit:true
},
{ columnId:"freightInvoiceStageId"}
];

var gridConfig = {
	       divName:'freightInvoiceStageBean', // the div id to contain the grid.
	       beanData:'jsonMainData', // the data variable name for jsonparse, as in grid.parse( beanData ,"json" )
	       beanGrid:'beanGrid', // the grid's name, as in beanGrid.attachEvent...
	       config:config, // the column config var name, as in var config = { [ columnId:..,columnName...
	       rowSpan:false, // true: this page has rowSpan > 1 or not, disables smartrendering & sorting
	       backgroundRender: true,
	       submitDefault:true, // the fields in grid are defaulted to be submitted or not.
	       variableHeight: true
 };

// -->
</script>
</head>


<body bgcolor="#ffffff" onload="myOnLoad();">
<tcmis:form action="/freightinvoicingresults.do" onsubmit="return submitFrameOnlyOnce();">
<c:set var="reprocessPermission" value="${false}"/>
<tcmis:opsEntityPermission indicator="true" userGroupId="TransManager" opsEntityId="${bean.opsEntityId}">
	<c:set var="reprocessPermission" value="${true}"/>
</tcmis:opsEntityPermission>
<div class="interface" id="resultsPage">
<div class="backGroundContent">
<c:set var="dataCount" value='${0}'/>

<div id="freightInvoiceStageBean" style="width:100%;height:400px;"></div>	
<c:choose>
	<c:when test="${!empty freightInvoiceColl}" >
		<!-- Search results start -->
		<script type="text/javascript">
		<!--
		/*This is to keep track of whether to show any update links.
		If the user has any update permisions for any row then we show update links.*/
		
		<c:set var="showUpdateLink" value='N'/>
		
		/*Storing the data to be displayed in a JSON object array.*/
		var jsonMainData = new Array();
			var jsonMainData = {
			rows:[
				<c:forEach var="freightInvoiceStageBean" items="${freightInvoiceColl}" varStatus="status">
					<c:if test="${status.index > 0}">,
					</c:if>
					<c:set var="currentPermission" value='N'/>
					<c:if test="${status.current.status != 'PROCESSED'}">
						<c:set var="currentPermission" value='Y'/>
						<c:set var="showUpdateLink" value='Y'/>
					</c:if>			  
		
					{ id:${status.index +1},				
					  data:[ '${currentPermission}',
						 	'',
						 	'<tcmis:jsReplace value="${status.current.invoiceNumber}" processMultiLines="false" />',
						 	'<fmt:formatDate value="${status.current.invoiceDate}" pattern="${dateFormatPattern}"/>',
						 	'<tcmis:jsReplace value="${status.current.orderNumber}" processMultiLines="false" />',
						 	'<tcmis:jsReplace value="${status.current.glCode}" processMultiLines="false" />',
						 	'<tcmis:jsReplace value="${status.current.carrier}" processMultiLines="false" />',
						 	'<tcmis:jsReplace value="${status.current.trackingNumber}" processMultiLines="false" />',
						 	'<tcmis:jsReplace value="${status.current.orderType}" processMultiLines="false" />',
						 	'<tcmis:jsReplace value="${status.current.transportationMode}" processMultiLines="false" />',
						 	'${status.current.currencyId}',
						 	'${status.current.loadId}',
						 	'${status.current.loadLine}',
						 	'<tcmis:jsReplace value="${status.current.status}" processMultiLines="false" />',
						 	'<tcmis:jsReplace value="${status.current.errorDetail}" processMultiLines="true" />', 
						 	'<tcmis:jsReplace value="${status.current.comments}" processMultiLines="true" />',
						 	'${status.current.freightInvoiceStageId}'
						 	]}
					 <c:set var="dataCount" value='${dataCount+1}'/>
				 </c:forEach>
			]};
		//-->
		</script>
		
		</div> <!-- end of grid div. -->
	</c:when>
	<c:otherwise >
		<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableNoData" id="resultsPageTable">
		 <tr>
		  <td width="100%">
		   <fmt:message key="main.nodatafound"/>
		  </td>
		 </tr>
		</table>
	</c:otherwise>
</c:choose>

<div id="reprocessDailogWin" style="display: none;overflow: auto;">
	<div class="contentArea">
		<div class="roundcont filterContainer">
			<div class="roundright">
				<div class="roundtop">
					<div class="roundtopright"><img src="/images/rndBoxes/borderTL_filter.gif" alt="" width="15" height="10" class="corner_filter" style="display: none" /></div>
				</div>
				<div class="roundContent">
					<h4 class="errorMessages"><fmt:message key="label.freightinvoice.confirmation"/></h4>
					<table width=100% border="0" cellpadding="4" cellspacing="2">
				    	<tr>
				    		<td class="optionTitleBoldRight" width="1%" nowrap><fmt:message key="label.loadid" />:</td>
				    		<td>
				    			<select name="loadId" id="loadId" class="selectBox">
				    				<c:forEach var="loadId" items="${loadIdList}">
					    				<option value="${loadId}">${loadId}</option>
									</c:forEach>
								</select>
							</td>
						</tr>
						<tr>
						    <td class="optionTitleBoldRight" width="1%" nowrap><fmt:message key="label.freightforwarder" />:</td>
							<td>
								<select name="radianCarrier" id="radianCarrier" class="selectBox">
					    			<c:forEach var="carrier" items="${radianCarrierList}" varStatus="status">
					    				<option value="${carrier}">${carrier}</option>
									</c:forEach>
								</select>
				    		</td>
						</tr>
						<tr>
							<td colspan="2" align="center">
					  			<input name="reprocessSubmit" id="reprocessSubmit" type="submit" value="<fmt:message key="label.ok"/>" 
					  				   class="inputBtns" onmouseover="this.className='inputBtns inputBtnsOver'" onmouseout="this.className='inputBtns'" 
					  				   onclick="submitReprocessInvoiceLines();">
					  			<input name="reprocessCancel" id="reprocessCancel" type="button" value="<fmt:message key="label.cancel"/>" 
					  				   class="inputBtns"  onmouseover="this.className='inputBtns inputBtnsOver'" onmouseout="this.className='inputBtns'" 
					  				   onclick="doReprocessCancel();">
							</td>
				    	</tr>
					</table>
				</div>
				<div class="roundbottom">
					<div class="roundbottomright"><img src="/images/rndBoxes/borderBL.gif" alt="" width="15" height="15" class="corner" style="display: none" /></div>
				</div>
			</div>
		</div>
	</div>
</div>

<div id="hiddenElements" style="display: none;">
<input name="totalLines" id="totalLines" value="${dataCount}" type="hidden"/>
<input name="uAction" id="uAction" value="" type="hidden"/>
<input name="minHeight" id="minHeight" type="hidden" value="50"/>
<input name="searchField" id="searchField" value='<tcmis:jsReplace value="${param.searchField}"/>' type="hidden"/>
<input name="searchMode" id="searchMode" value='<tcmis:jsReplace value="${param.searchMode}"/>' type="hidden"/>
<input name="searchArgument" id="searchArgument" value='<tcmis:jsReplace value="${param.searchArgument}"/>' type="hidden"/>
<input name="reprocessLoadId" id="reprocessLoadId" value="" type="hidden"/>
<input name="reprocessCarrier" id="reprocessCarrier" value="" type="hidden"/>
<input name="reprocessCurrencyId" id="reprocessCurrencyId" value="" type="hidden"/>
<c:forEach items="${paramValues.freightInvoiceStatus}" varStatus="status">
	<input type="hidden" name="freightInvoiceStatus" id="freightInvoiceStatus${status.index}" value="${status.current}" />
</c:forEach>
</div>

</div> <!-- close of interface -->

</tcmis:form>

<!-- You can build your error messages below. But we want to trigger the pop-up from the main page.
So this is just used to feed the pop-up in the main page.
Similar divs would have to be built to show any other messages in a pop-up.-->
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

showUpdateLinks = true;
<c:if test="${reprocessPermission}">
	reprocessPermission = true;
</c:if>
//-->
</script>

</body>
</html:html>