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
<meta http-equiv="content-type" content="text/html; charset=UTF-8"/>
<meta http-equiv="expires" content="-1"/>
<link rel="shortcut icon" href="https://www.tcmis.com/images/buttons/tcmIS.ico"></link>

<%@ include file="/common/locale.jsp" %>
<%@ include file="/common/opshubig.jsp" %>

<!--Use this tag to get the correct CSS class.
This looks at what the user's preffered font size and which application he is viewing to set the correct CSS. -->
<tcmis:gridFontSizeCss />
<%-- Add any other stylesheets you need for the page here --%>

<script type="text/javascript" src="/js/common/formchek.js"></script>
<script type="text/javascript" src="/js/common/commonutil.js"></script>
<!-- This handles all the resizing of the page and frames -->
<%--NEW--%>
<script type="text/javascript" src="/js/common/grid/iframegridresizemain.js"></script>
<!-- This handels which key press events are disabeled on this page -->
<script type="text/javascript" src="/js/common/disableKeys.js"></script>

<!-- This handels the menu style and what happens to the right click on the whole page -->
<script type="text/javascript" src="/js/menu/milonic_src.js"></script>
<script type="text/javascript" src="/js/menu/mmenudom.js"></script>
<script type="text/javascript" src="/js/menu/mainmenudata.js"></script>
<script type="text/javascript" src="/js/menu/contextmenu.js"></script>
<%@ include file="/common/rightclickmenudata.jsp" %>

<!-- For Calendar support -->
<script type="text/javascript" src="/js/calendar/newcalendar.js"></script>
<script type="text/javascript" src="/js/calendar/AnchorPosition.js"></script>
<script type="text/javascript" src="/js/calendar/PopupWindow.js"></script>

<!-- Add any other javascript you need for the page here -->
<script type="text/javascript" src="/js/common/standardGridmain.js"></script>
<script type="text/javascript" src="/js/common/opshubig.js"></script>
<script type="text/javascript" src="/js/distribution/customerreturntracking.js"></script>

<!-- These are for the Grid, uncomment if you are going to use the grid -->
<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxcommon.js"></script>
<script type="text/javascript" src="/dhtmlxLayout/codebase/dhtmlxlayout.js"></script>
<script type="text/javascript" src="/dhtmlxWindows/codebase/dhtmlxwindows.js"></script>

<script type="text/javascript" src="/js/jquery/jquery-1.6.4.js"></script>
<title>
<fmt:message key="label.customerreturntracking"/>
</title>

<script language="JavaScript" type="text/javascript">
<!--
//add all the javascript messages here, this for internationalization of client side javascript messages
var messagesData = new Array();
messagesData={alert:"<fmt:message key="label.alert"/>",and:"<fmt:message key="label.and"/>",all:"<fmt:message key="label.all"/>",
showlegend:"<fmt:message key="label.showlegend"/>",errors:"<fmt:message key="label.errors"/>",
validvalues:"<fmt:message key="label.validvalues"/>",
materialrequest:"<fmt:message key="label.materialrequest"/>",
days:"<fmt:message key="label.days"/>",
submitOnlyOnce:"<fmt:message key="label.submitOnlyOnce"/>",
rma:"<fmt:message key="label.rma"/>",
itemInteger:"<fmt:message key="error.item.integer"/>"};

defaults.ops.nodefault = true;
defaults.hub.nodefault = true;
defaults.inv.nodefault = true;

var searchMyArr = new Array(
		{value:'contains', text: '<fmt:message key="label.contain"/>'}
		,{value:'is', text: '<fmt:message key="label.is"/>'}
		,{value:'startsWith', text: '<fmt:message key="label.startswith"/>'}
		,{value:'endsWith', text: '<fmt:message key="label.endswith"/>'}
		);

function checkAutoSearch() {
	if ('<tcmis:jsReplace value="${param.uAction}"/>' == 'autoSearch') {
		var searchValue = '<tcmis:jsReplace value="${param.opsEntityId}"/>';
		if (!isWhitespace(searchValue)) {
			$('opsEntityId').value = searchValue;
			opsChanged();
		}

		searchValue = '<tcmis:jsReplace value="${param.hub}"/>';
		if (!isWhitespace(searchValue)) {
			$('hub').value = searchValue;
			hubChanged();
		}

		searchValue = '<tcmis:jsReplace value="${param.inventoryGroup}"/>';
		if (!isWhitespace(searchValue)) {
			$('inventoryGroup').value = searchValue;
		}

		searchValue = '<tcmis:jsReplace value="${param.searchArgument}"/>';
		if (!isWhitespace(searchValue)) {
			$('searchField').value = '<tcmis:jsReplace value="${param.searchField}"/>';
			$('searchMode').value = '<tcmis:jsReplace value="${param.searchMode}"/>';
			$('searchArgument').value = searchValue;
		}
		$('rmaStatus').value = '<tcmis:jsReplace value="${param.rmaStatus}"/>';

		var isHideSearchPanel = '<tcmis:jsReplace value="${param.isHideSearchPanel}"/>';
		if (isHideSearchPanel == 'true') {
			try {
				j$(".dhtmlxPolyInfoBar").eq(0).children()[4].click();
			} catch (e) {}
		}

		submitSearchForm();
	}
}
// -->
</script>
</head>

<body bgcolor="#ffffff" onload="loadLayoutWin('','customerReturnTracking');setOps();checkAutoSearch();" onresize="resizeFrames()">

<div class="interface" id="mainPage" style="">

<!-- Search Frame Begins -->
<div id="searchFrameDiv">
<%--NEW - removed the search frame and copied the search section here--%>
<div class="contentArea">
<tcmis:form action="/customerreturntrackingresults.do" onsubmit="return submitSearchOnlyOnce();" target="resultFrame">
<!-- Search Option Begins -->
<table id="searchMaskTable" width="100%" border="0" cellpadding="0" cellspacing="0">
<tr><td>
<div class="roundcont filterContainer">
 <div class="roundright">
   <div class="roundtop">
     <div class="roundtopright"> <img src="/images/rndBoxes/borderTL_filter.gif" alt="" width="15" height="10" class="corner_filter" style="display: none" /></div>
   </div>
   <div class="roundContent">
    <!-- Insert all the search option within this div -->
    <table width="400" id="searchTable" border="0" cellpadding="0" cellspacing="0" class="tableSearch">
    <tr>
      <td width="10%" class="optionTitleBoldRight" nowrap><fmt:message key="label.operatingentity"/>:</td>
      <td width="10%">
         <select name="opsEntityId" id="opsEntityId" class="selectBox">
         </select>
      </td>
      <td width="70%" class="optionTitleBoldLeft" nowrap>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
        <fmt:message key="label.search"/>:&nbsp;
        <select id="searchField" name="searchField"  class="selectBox" onchange="changeSearchTypeOptions(this.value)" >
          <option value="prNumber"><fmt:message key="label.materialrequest"/></option>
          <option value="customerName"><fmt:message key="label.customer"/></option>
          <option value="poNumber"><fmt:message key="label.customerpo"/></option>
          <option value="customerRmaId"><fmt:message key="label.rma"/></option>

        </select>
        <select name="searchMode" id="searchMode" class="selectBox">
            <option value="is" selected="selected"><fmt:message key="label.is"/></option>
            <option value="startsWith"><fmt:message key="label.startswith"/></option>
          </select>
        <input class="inputBox" type="text" name="searchArgument" id="searchArgument" value='<c:out value="${param.searchString1}"/>' size="25" />
      </td>
    </tr>
    <tr>
      <td width="10%" class="optionTitleBoldRight"><fmt:message key="label.hub"/>:</td>
      <td width="10%">
         <select name="hub" id="hub" class="selectBox">
         </select>
      </td>
      <td width="10%" class="optionTitleBoldLeft" nowrap>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
        <fmt:message key="label.status"/>:&nbsp;
      	<select id="rmaStatus" name="rmaStatus"  class="selectBox">
      	  <option value=""><fmt:message key="label.all"/></option>
          <option value="Draft"><fmt:message key="label.draft"/></option>
          <option value="Pending Approval" selected><fmt:message key="label.pendingapproval"/></option>
          <option value="Approved"><fmt:message key="label.approved"/></option>
          <option value="Rejected"><fmt:message key="label.rejected"/></option>
          <option value="Partial Return"><fmt:message key="label.partialreturn"/></option>
          <option value="Completed"><fmt:message key="label.completed"/></option>
          <option value="Problem"><fmt:message key="label.problem"/></option>
        </select>
      </td>
    </tr>
    <tr>
      <td width="10%" class="optionTitleBoldRight" nowrap><fmt:message key="label.inventorygroup"/>:&nbsp;</td>
      <td width="10%">
       <select name="inventoryGroup" id="inventoryGroup" class="selectBox">
       </select>
      </td>
      <td width="10%" class="optionTitleBoldLeft" nowrap>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
        <input type="checkbox" name="searchOption" id="searchOption" onclick="setValue('2');" value="2"/>&nbsp;<fmt:message key="label.allrequestswithin"/>&nbsp;
		<input class="inputBox" type="text" name="days" id="days" value="30" size="5" />&nbsp;<fmt:message key="label.days"/>
      </td>
    </tr>
    <tr>
     <td colspan="4" width="100%" class="optionTitleBoldLeft">
          <input name="submitSearch" type="button" class="inputBtns" value="<fmt:message key="label.search"/>" id="submitSearch" onmouseover="this.className='inputBtns inputBtnsOver'" onmouseout="this.className='inputBtns'"
          		 onclick= "return submitSearchForm()"/>
          <input name="xslButton" type="button" class="inputBtns" value="<fmt:message key="label.createExcel"/>" id="xslButton" onmouseover="this.className='inputBtns inputBtnsOver'" onmouseout="this.className='inputBtns'"
          		 onclick= "return createXSL()"/>
      </td>
    </tr>
    </table>

   <!-- End search options -->
   </div>
   <div class="roundbottom">
     <div class="roundbottomright"> <img src="/images/rndBoxes/borderBL.gif" alt="" width="15" height="15" class="corner" style="display: none" /> </div>
   </div>
 </div>
</div>
</td></tr>
</table>
<!-- Search Option Ends -->

<!-- Error Messages Begins -->
<!-- Build this section only if there is an error message to display.
     For the search section, we show the error messages within its frame
-->
<c:if test="${requestScope['org.apache.struts.action.ERROR'] != null}">
<div class="spacerY">&nbsp;
<div id="searchErrorMessagesArea" class="errorMessages">
<html:errors/>
</div>
</div>
</c:if>
<!-- Error Messages Ends -->

<!-- Hidden element start -->
<div id="hiddenElements" style="display: none;">
<input type="hidden" name="uAction" id="uAction" value=""/>
<input name="startSearchTime" id="startSearchTime" type="hidden" value="" />
<input name="searchHeight" id="searchHeight" type="hidden" value="133">
</div>
<!-- Hidden elements end -->

<!-- Error Messages Ends -->
</tcmis:form>
</div> <!-- close of contentArea -->

</div>
<!-- Search Frame Ends -->

<!-- Result Frame Begins -->
<div id="resultFrameDiv" style="margin: 4px 4px 0px 4px;">

<%--NEw -Transit Page Starts --%>
<div id="transitPage" style="display: none;" class="optionTitleBoldCenter">
  <br><br><br><fmt:message key="label.pleasewait"/>
  <br><br><br><img src="/images/rel_interstitial_loading.gif" align="middle">
</div>
<!-- Transit Page Ends -->

<div id="resultGridDiv" style="display: none;">
<!-- Search results start -->
<!-- If you want your results section to span only 50% set this to 50% or 800 pixels. Make sure to set resizeGridWithWindow to false. -->
<table id="resultsMaskTable" width="100%" border="0" cellpadding="0" cellspacing="0">
<tr><td>
<div class="roundcont contentContainer">
<div class="roundright">
  <div class="roundtop">
    <div class="roundtopright"> <img src="/images/rndBoxes/borderTL.gif" alt="" width="15" height="15" class="corner" style="display: none" /></div>
  </div>
  <div class="roundContent">
    <div class="boxhead"> <%-- boxhead Begins --%>
     <%-- Place all update links in divs that can be turned on or off depending on the search criteria that is selected
          Do not remove mainUpdateLinks div, even if you don't have any links. You can remove content inside the div but not the div itself.
      --%>
      <div id="mainUpdateLinks" style="display: none"> <%-- mainUpdateLinks Begins --%>
      <span id="updateResultLink" style="display: none">
          <span id="selectedRow">&nbsp;</span>

      </span>
     </div> <%-- mainUpdateLinks Ends --%>
    </div> <%-- boxhead Ends --%>

    <div class="dataContent">
     <iframe scrolling="no" id="resultFrame" name="resultFrame" width="100%" height="300" frameborder="0" marginwidth="0" src="/blank.html"></iframe>
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

</div> <!-- close of interface -->

<!-- You can build your error messages below. Similar divs would have to be built to show any other messages in a inline pop-up.-->
<!-- Error Messages Begins -->
<div id="errorMessagesArea" class="errorMessages" style="display: none;overflow: auto;">

</div>

</body>
</html:html>
