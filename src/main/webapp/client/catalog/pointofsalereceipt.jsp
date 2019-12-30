<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
	"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html;charset=UTF-8" language="java"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%@ taglib uri="/WEB-INF/tcmis.tld" prefix="tcmis" %>

<html>
<head>
<meta http-equiv="content-type" content="text/html; charset=UTF-8"/>
<meta http-equiv="expires" content="-1"/>
<link rel="shortcut icon" href="/images/buttons/tcmIS.ico"></link>

<%@ include file="/common/locale.jsp" %>
<tcmis:gridFontSizeCss />

<script type="text/javascript" src="/js/common/formchek.js"></script>
<script type="text/javascript" src="/js/common/commonutil.js"></script>
<script type="text/javascript" src="/js/common/grid/nosearchresize.js"></script>
<script type="text/javascript" src="/js/common/disableKeys.js"></script>

<script type="text/javascript" src="/js/menu/milonic_src.js"></script>
<script type="text/javascript" src="/js/menu/mmenudom.js"></script>
<script type="text/javascript" src="/js/menu/mainmenudata.js"></script>
<script type="text/javascript" src="/js/menu/contextmenu.js"></script>
<%@ include file="/common/rightclickmenudata.jsp" %>


<script type="text/javascript" src="/js/common/noSearchStandardGridmain.js"></script>

<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxcommon.js"></script>
<script type="text/javascript" src="/dhtmlxWindows/codebase/dhtmlxwindows.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxgrid.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxgridcell.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_form.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_srnd.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_json.js"></script>

<script type="text/javascript" src="/dhtmlxGrid/codebase/excells/dhtmlxgrid_excell_customized.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxcommon_haas.js"></script>

<title>
<fmt:message key="label.customerreceipt"/>
</title>

<script language="JavaScript" type="text/javascript">
<!--
//add all the javascript messages here, this for internationalization of client side javascript messages
var messagesData = new Array();
messagesData={alert:"<fmt:message key="label.alert"/>",
and:"<fmt:message key="label.and"/>",
recordFound:"<fmt:message key="label.recordFound"/>",
searchDuration:"<fmt:message key="label.searchDuration"/>",
minutes:"<fmt:message key="label.minutes"/>",
seconds:"<fmt:message key="label.seconds"/>",
validvalues:"<fmt:message key="label.validvalues"/>",
newBin:"<fmt:message key="label.newbin"/>",	
submitOnlyOnce:"<fmt:message key="label.submitOnlyOnce"/>"};

windowCloseOnEsc = true;
window.onresize = resizeFrames;

function printScreen() {
	var loc = "/HaasReports/report/printConfigurableReport.do?pr_number="+$v("prNumber")+"&rpt_ReportBeanId=posReceiptReportDefinition";
	openWinGenericViewFile(loc,"showPointOfSaleReceiptPrint","520","500","yes");
}

function closeWindow() {
	window.close();
}

function closeParentTab() {
    opener.parent.parent.closeTabx('pointofsalepickingscreen'+$v("prNumber"));    
}

// -->
</script>
</head>

<body bgcolor="#ffffff"  onload="popupOnLoadWithGrid();" onunload="closeParentTab()">
<tcmis:form action="/pointofsale.do" onsubmit="return submitFrameOnlyOnce();">
<div class="interface" id="mainPage" style="">

<!-- Error Messages Begins -->
<div id="errorMessagesAreaBody" style="display:none;">
  ${tcmISError}<br/>
  <c:forEach items="${tcmISErrors}" varStatus="status">
  	${status.current}<br/>
  </c:forEach>
</div>
<!-- Error Messages Ends -->

<script type="text/javascript">
<!--
/*Check if there is any error messages to show and set the variable you use in javascript to true or false.*/
    showErrorMessage = false;
<c:if test="${!empty tcmISError}">
	showErrorMessage = true;
</c:if>
<c:forEach var="errorMsg" items="${tcmISError}">
	<c:if test="${!empty errorMsg}">
		showErrorMessage = true;
	</c:if>
</c:forEach>

//-->
</script>

<!-- Result Frame Begins -->
<div id="resultFrameDiv" style="margin: 4px 4px 0px 4px;">

<%--NEw -Transit Page Starts --%>
<div id="transitPage" style="display: none;" class="optionTitleBoldCenter">
  <br/><br/><br/><fmt:message key="label.pleasewait"/>
  <br/><br/><br/><img src="/images/rel_interstitial_loading.gif" align="middle"/>
</div>
<!-- Transit Page Ends -->

<!-- Search results start -->
<!-- If you want your results section to span only 50% set this to 50% and your main table will be 100% -->
<div id="resultGridDiv" style="display: none;">

<table id="resultsMaskTable" width="100%" border="0" cellpadding="0" cellspacing="0">
<tr><td>
<div class="roundcont contentContainer">
<div class="roundright">
  <div class="roundtop">
    <div class="roundtopright"> <img src="/images/rndBoxes/borderTL.gif" alt="" width="15" height="15" class="corner" style="display: none" /></div>
  </div>
  <div class="roundContent">
    <div class="boxhead"> <%-- boxhead Begins --%>
      <div id="mainUpdateLinks" style="display: none"> <%-- mainUpdateLinks Begins --%>
      <span id="updateResultLink" style="display: none">
			<a href="#" onclick="printScreen(); return false;"><fmt:message key="label.print"/></a>
			&nbsp;|&nbsp;
			<a href="#" onclick="closeWindow(); return false;"><fmt:message key="label.close"/></a>
	  </span>
       &nbsp;
			
		<span id="headerData">
			 <%-- header info --%>
			<table class="tableSearch" border="0">
				 <tr>
					<td class="optionTitleBoldRight">
						<fmt:message key="label.hub"/>:&nbsp;
					</td>
					<td class="optionTitleBoldLeft">
						${pointOfSaleReceiptColl[0].hubName}
					</td>
					<td class="optionTitleBoldRight">
						<fmt:message key="label.customer"/>:&nbsp;
					</td>
					<td class="optionTitleBoldLeft">
						${pointOfSaleReceiptColl[0].requestorName}
					</td>
				 </tr>
				 <tr>
					<td class="optionTitleBoldRight">
						<fmt:message key="label.pos"/>:&nbsp;
					</td>
					<td class="optionTitleBoldLeft">
						${pointOfSaleReceiptColl[0].inventoryGroupName}
					</td>
					<td class="optionTitleBoldRight">
						<fmt:message key="label.facility"/>:&nbsp;
					</td>
					<td class="optionTitleBoldLeft">
						${pointOfSaleReceiptColl[0].facilityName}
					</td>
				 </tr>
				 <tr>
					<td class="optionTitleBoldRight">
						<fmt:message key="label.clerk"/>:&nbsp;
					</td>
					<td class="optionTitleBoldLeft">
						${pointOfSaleReceiptColl[0].clerkName}
					</td>
					<td class="optionTitleBoldRight">
						<fmt:message key="label.date"/>:&nbsp;
					</td>
					<td class="optionTitleBoldLeft">
						${pointOfSaleReceiptColl[0].receiptDate}
					</td>
				 </tr>
			</table>
		</span>


		</div> <%-- mainUpdateLinks Ends --%>
    </div> <%-- boxhead Ends --%>

<div class="dataContent">

<c:set var="dataCount" value='0'/>
<!--  result page section start -->
<c:set var="beanColl" value='${pointOfSaleReceiptColl}'/>
<c:if test="${!empty beanColl}" >
	<div id="dataGridDiv" style="height:100px;display: none;" ></div>
	<script type="text/javascript">
	<!--
	_gridConfig.submitDefault = true;
	_gridConfig.divName = 'dataGridDiv';
	_gridConfig.rowSpan = false;
	_gridConfig.beanGrid = 'ordersGrid';
	<%--NEW - storing the data to be displayed in the grid in a JSON.--%>
	var jsonData = {
		rows:[
	<c:forEach var="bean" items="${beanColl}" varStatus="status">
		<c:if test="${status.index > 0}">,</c:if>
		<tcmis:jsReplace var="appDesc" value="${bean.applicationDesc}"  processMultiLines="false"/>
		{id:${status.index },
				 data:[
						 'N',
						 '${bean.prNumber}-${bean.lineItem}',
						 '${appDesc}',
						 '${bean.catPartNo}',
					    '${bean.itemId}',
					    '${bean.quantity}'
	 	]}
		 <c:set var="dataCount" value='${dataCount+1}'/>
	</c:forEach>
	]};

	var config = [
		{
			columnId:"permission"
		},
	  {
		columnId:"mrLine",
		columnName:'<fmt:message key="label.mrline"/>',
		width:5
	  },
	  {
		columnId:"applicationDesc",
		columnName:'<fmt:message key="label.workarea"/>',
		 width:15
	  },
	  {
		columnId:"catPartNo",
		columnName:'<fmt:message key="label.partnumber"/>',
		width:10
	  },
	  {
		columnId:"itemId",
		columnName:'<fmt:message key="label.itemid"/>',
		width:5
	  },
	  {
		columnId:"quantity",
		columnName:'<fmt:message key="label.quantity"/>',
		width:5
	  }
	  ];
	// -->
	</script>
</c:if>

<!-- If the collection is empty say no data found -->
<c:if test="${empty beanColl}" >
<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableNoData" id="resultsPageTable">
 <tr>
  <td>
   <fmt:message key="main.nodatafound"/>
  </td>
 </tr>
</table>
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
</td></tr>
</table>
</div>

</div><!-- Result Frame Ends -->

<!-- Hidden element start -->
<div id="hiddenElements" style="display: none;">
<input name="totalLines" id="totalLines" value="${dataCount}" type="hidden"/>
<input name="uAction" id="uAction" value="" type="hidden"/>
<!--This sets the start time for time elapesed.-->
<input name="startSearchTime" id="startSearchTime" type="hidden" value="${startSearchTime}"/>
<input name="endSearchTime" id="endSearchTime" type="hidden" value="${endSearchTime}"/>
<input name="prNumber" id="prNumber" type="hidden" value="${param.prNumber}"/>
<input name="minHeight" id="minHeight" type="hidden" value="100"/>

</div>
<!-- Hidden elements end -->

</div> <!-- close of interface -->
</tcmis:form>
<!-- You can build your error messages below. Similar divs would have to be built to show any other messages in a inline pop-up.-->
<!-- Error Messages Begins -->
<div id="errorMessagesArea" class="errorMessages" style="display: none;overflow: auto;">

</div>

<script type="text/javascript">
<!--
  showUpdateLinks = true;
//-->
</script>

</body>
</html>
