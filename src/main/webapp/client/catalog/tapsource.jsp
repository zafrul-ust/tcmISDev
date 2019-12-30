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

<script type="text/javascript" src="/js/client/catalog/tapsource.js"></script>	

<title>
<fmt:message key="label.tapsource"/>
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
tapSource:"<fmt:message key="label.tapsource"/>",
newBin:"<fmt:message key="label.newbin"/>",	
submitOnlyOnce:"<fmt:message key="label.submitOnlyOnce"/>"};

windowCloseOnEsc = true;
window.onresize = resizeFrames;

// -->
</script>
</head>

<body bgcolor="#ffffff"  onload="popupOnLoadWithGrid();myOnLoad()">
<tcmis:form action="/tapsource.do" onsubmit="return submitFrameOnlyOnce();">
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

	 <%-- message --%>
<div id="messageDailogWin" class="errorMessages" style="display: none;overflow: auto;">
	<table width="100%" border="0" cellpadding="2" cellspacing="1">
		<tr>
			<td align="center" width="100%">
				<input name="messageText" id="messageText" type="text" class="inputBox" value="" size="30"/>
			</td>
		</tr>
		<tr>
			<td align="center" width="100%">
				<input name="closeMessageB"  id="closeMessageB"  type="button" value="<fmt:message key="label.ok"/>" class="smallBtns" onmouseover="this.className='smallBtns smallBtnsOver'" onmouseout="this.className='smallBtns'" onClick="closeMessageWin();"/>
			   &nbsp;
				<input name="cancelMessageB"  id="cancelMessageB"  type="button" value="<fmt:message key="label.cancel"/>" class="smallBtns" onmouseover="this.className='smallBtns smallBtnsOver'" onmouseout="this.className='smallBtns'" onClick="cancelMessageWin();"/>
			</td>
		</tr>
	</table>
</div>

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
	  	</span>
      &nbsp;
		<span id="headerDataSpan" style="display: none">
			<%-- header info --%>
			<table class="tableSearch" border="0">
				 <tr>
					<td class="optionTitleBoldRight">
						<fmt:message key="label.dispenseditem"/>:&nbsp;
					</td>
					<td class="optionTitleBoldLeft">
						${param.itemId}
					</td>
				 </tr>
				 <tr>
					<td class="optionTitleBoldRight">
						<fmt:message key="label.inventorygroup"/>:&nbsp;
					</td>
					<td class="optionTitleBoldLeft">
						${param.inventoryGroup}
					</td>
				 </tr>
			</table>
		</span>
		</div> <%-- mainUpdateLinks Ends --%>
    </div> <%-- boxhead Ends --%>

<div class="dataContent">

<c:set var="dataCount" value='0'/>
<!--  result page section start -->
<c:set var="beanColl" value='${tappableColl}'/>
<c:if test="${!empty beanColl}" >
	<div id="OrdersBean" style="height:300px;display: none;" ></div>
	<script type="text/javascript">
	<!--
	_gridConfig.submitDefault = true;
	_gridConfig.divName = 'OrdersBean';
	_gridConfig.rowSpan = false;
	_gridConfig.beanGrid = 'ordersGrid';
	_gridConfig.onRowSelect = selectRow;
	<%--NEW - storing the data to be displayed in the grid in a JSON.--%>
	var jsonData = {
		rows:[
	<c:forEach var="bean" items="${beanColl}" varStatus="status">
			<c:if test="${status.index > 0}">,</c:if>
			<c:set var="newBin">
   			<input type="button" class="smallBtns" onmouseover="this.className='smallBtns smallBtnsOver'" onMouseout="this.className='smallBtns'" name="None" value="<fmt:message key="label.new"/>" OnClick="showMessageDialog()"/>
			</c:set>
			<tcmis:jsReplace value="${newBin}" var="newBin"/>																																																																		 
	{
	 id:${status.index },
				 data:[
						 'Y',
						 '${bean.receiptId}',
						 '${bean.bin}',
						 '${bean.bin}',
					    '${newBin}'
	 ]}
		 <c:set var="dataCount" value='${dataCount+1}'/>
		  </c:forEach>
		 ]};

	var config = [
		{
			columnId:"permission"
		},
	  {
		columnId:"receiptId",
		columnName:'<fmt:message key="label.receiptid"/>',
		width:5
	  },
	  {
		columnId:"currentBin",
		columnName:'<fmt:message key="label.currentbin"/>',
		 width:15
	  },
	  {
		columnId:"newBin",
		columnName:'<fmt:message key="label.newbin"/>',
		type:'hcoro',
  	   align:'center',
		width:15
	  },
	  {
		columnId:"createNewBin",
		columnName:' ',
		width:5
	  }
	  ];

var newBin = new  Array(
	<c:forEach var="bean" items="${binColl}" varStatus="status">
   	<c:if test="${status.index > 0}">
    	,
   	</c:if>
		{text:'${status.current.bin}',value:'${status.current.bin}'}
	</c:forEach>
);


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
<input name="itemId" id="itemId" value="${param.itemId}" type="hidden"/>
<input name="inventoryGroup" id="inventoryGroup" value="${param.inventoryGroup}" type="hidden"/>	
<!--This sets the start time for time elapesed.-->
<input name="startSearchTime" id="startSearchTime" type="hidden" value="${startSearchTime}"/>
<input name="endSearchTime" id="endSearchTime" type="hidden" value="${endSearchTime}"/>
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
