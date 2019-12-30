<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
	"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html;charset=UTF-8" language="java"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<%@ taglib uri="/WEB-INF/struts-nested.tld" prefix="nested"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/tcmis.tld" prefix="tcmis"%>

<html:html lang="true">
<head>
<meta http-equiv="content-type" content="text/html; charset=UTF-8">
<meta http-equiv="expires" content="-1">
<link rel="shortcut icon"
	href="https://www.tcmis.com/images/buttons/tcmIS.ico"></link>

<%@ include file="/common/locale.jsp"%><!--Use this tag to get the correct CSS class.
This looks at what the user's preffered font size and which application he is viewing to set the correct CSS. -->
<tcmis:gridFontSizeCss /> <%-- Add any other stylesheets you need for the page here --%>

<script type="text/javascript" src="/js/common/formchek.js"></script> <script
	type="text/javascript" src="/js/common/commonutil.js"></script> <!-- This handles all the resizing of the page and frames -->
<%--NEW--%> <script type="text/javascript"
	src="/js/common/grid/iframegridresizemain.js"></script> <!-- This handels which key press events are disabeled on this page -->
<script type="text/javascript" src="/js/common/disableKeys.js"></script>

<!-- This handels the menu style and what happens to the right click on the whole page -->
<script type="text/javascript" src="/js/menu/milonic_src.js"></script> <script
	type="text/javascript" src="/js/menu/mmenudom.js"></script> <script
	type="text/javascript" src="/js/menu/mainmenudata.js"></script> <script
	type="text/javascript" src="/js/menu/contextmenu.js"></script> <%@ include
	file="/common/rightclickmenudata.jsp"%> <!-- For Calendar support -->
<script type="text/javascript" src="/js/calendar/newcalendar.js"></script>
<script type="text/javascript" src="/js/calendar/AnchorPosition.js"></script>
<script type="text/javascript" src="/js/calendar/PopupWindow.js"></script>

<script type="text/javascript" src="/js/common/standardGridmain.js"></script>
<script src="/js/supply/printpolist.js" language="JavaScript"></script>

<!-- These are for the Grid, uncomment if you are going to use the grid -->
<script type="text/javascript"
	src="/dhtmlxGrid/codebase/dhtmlxcommon.js"></script> <script
	type="text/javascript" src="/dhtmlxLayout/codebase/dhtmlxlayout.js"></script>
<script type="text/javascript"
	src="/dhtmlxWindows/codebase/dhtmlxwindows.js"></script>

<title>
<fmt:message key="printpolist.title"/>
</title>

<script language="JavaScript" type="text/javascript">
<!--
//add all the javascript messages here, this for internationalization of client side javascript messages
var messagesData = new Array();
messagesData={alert:"<fmt:message key="label.alert"/>",
and:"<fmt:message key="label.and"/>",
all:"<fmt:message key="label.all"/>",
showlegend:"<fmt:message key="label.showlegend"/>",
errors:"<fmt:message key="label.errors"/>",  
validvalues:"<fmt:message key="label.validvalues"/>",
submitOnlyOnce:"<fmt:message key="label.submitOnlyOnce"/>",
pleaseselect:"<fmt:message key="label.pleaseselect"/>",
operatingentity:"<fmt:message key="label.operatingentity"/>",
showlegend:"<fmt:message key="label.showlegend"/>",
searchInput:"<fmt:message key="error.searchInput.integer"/>"};
// -->
</script>
</head>

<body bgcolor="#ffffff"
	onload="loadLayoutWin('','printedPoList');" onresize="resizeFrames()">

 <div class="interface" id="mainPage">
<div id="searchFrameDiv"><%--NEW - removed the search frame and copied the search section here--%>
<div class="contentArea">

<tcmis:form action="/printpolistresults.do"
	onsubmit="if(submitSearchForm()){return submitSearchOnlyOnce();}else{return false;}" target="resultFrame">

<!-- Search Option Begins -->
<table id="searchMaskTable" width="35%" border="0" cellpadding="0" cellspacing="0">
<tr><td>
<div class="roundcont filterContainer">
 <div class="roundright">
   <div class="roundtop">
     <div class="roundtopright"> <img src="/images/rndBoxes/borderTL.gif" alt="" width="15" height="15" class="corner" style="display: none" /></div>
   </div>
   <div class="roundContent">
    <!-- Insert all the search option within this div -->
    <table width="100%" class="tableSearch">
      <tr>
        <c:set var="selectedType" value='${param.searchType}'/>
          <c:choose>
          <c:when test="${empty selectedType}" >
            <c:set var="selectedType" value="po"/>
          </c:when>
          </c:choose>
      
      	<td class="optionTitleBoldRight"><fmt:message key="label.search" />:</td>
		<td nowrap class="optionTitleBoldLeft"><select name="searchType" class="selectBox" id="searchType">
						<option value="po"><fmt:message key="label.ponumber" /></option>
						<option value="bpo"><fmt:message key="label.bponumber" /></option>
		</select><input name="searchText" id="searchText" type="text" class="inputBox"></input></td>
      </tr>
      <tr>
        <td>&nbsp</td>
        <td><input name="submitSearch" id="submitSearch" type="submit" class="inputBtns" value="<fmt:message key="label.search"/>" onmouseover="this.className='inputBtns inputBtnsOver'" onmouseout="this.className='inputBtns'" onclick= "return submitSearchForm()"></td>
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
	<c:if test="${requestScope['org.apache.struts.action.ERROR'] != null}">
		<div class="spacerY">&nbsp;
		<div id="searchErrorMessagesArea" class="errorMessages"><html:errors />
		</div>
		</div>
	</c:if>
<!-- Error Messages Ends -->

	<!-- Hidden element start -->
	<div id="hiddenElements" style="display: none;"><input
		type="hidden" name="uAction" id="uAction" value="" /> <input
		type="hidden" name="hubName" id="hubName" value="" /> <input
		name="startSearchTime" id="startSearchTime" type="hidden" value="" />
	<!-- needed if this page will show on application.do --> <!-- To get the correct height value to insert, insert showSearchHeight = true; anywhere in the JavaScript array section in main.jsp.  -->
	<input name="searchHeight" id="searchHeight" type="hidden" value="214" />
	</div>
	<!-- Hidden elements end -->
</tcmis:form></div>
</div>
<!-- Search Frame Ends --> <!-- Result Frame Begins -->
<div id="resultFrameDiv" style="margin: 4px 4px 0px 4px;"><%--NEw -Transit Page Starts --%>
		<!-- Transit Page Begins -->
		<div id="transitPage" style="display: none;" class="optionTitleBoldCenter">
			<br><br><br><fmt:message key="label.pleasewait" /> <br><br><br><img src="/images/rel_interstitial_loading.gif" align="middle">
		</div>
		<!-- Transit Page Ends -->

<div id="resultGridDiv" style="display: none;"><!-- Search results start -->
<!-- If you want your results section to span only 50% set this to 50% or 800 pixels. Make sure to set resizeGridWithWindow to false. -->
<table id="resultsMaskTable" width="100%" border="0" cellpadding="0"
	cellspacing="0">
	<tr>
		<td>
		<div class="roundcont contentContainer">
		<div class="roundright">
		<div class="roundtop">
		<div class="roundtopright"><img
			src="/images/rndBoxes/borderTL.gif" alt="" width="15" height="15"
			class="corner" style="display: none" /></div>
		</div>
		<div class="roundContent">
		<div class="boxhead"><%-- boxhead Begins --%> <%-- Place all update links in divs that can be turned on or off depending on the search criteria that is selected
          Do not remove mainUpdateLinks div, even if you don't have any links. You can remove content inside the div but not the div itself.
          Please keep the <span></span> on the same line this will avoid extra virtical space.
      --%> <%-- Use this case if you only have one update link to minimize extra line.  Notice this uses "div" instead of "span" --%>
		<div id="mainUpdateLinks" style="display: none"><%-- mainUpdateLinks Begins --%>
		</div>
		<%-- mainUpdateLinks Ends --%> <%-- END OF OR --%></div>
		<%-- boxhead Ends --%>

		<div class="dataContent">
			<iframe scrolling="no" id="resultFrame"	name="resultFrame" width="100%" height="300" frameborder="0" marginwidth="0" src="/blank.html"></iframe>
		</div>

		<%-- result count and time --%>
		<div id="footer" class="messageBar"></div>

		</div>
		<div class="roundbottom">
		<div class="roundbottomright"><img
			src="/images/rndBoxes/borderBL.gif" alt="" width="15" height="15"
			class="corner" style="display: none" /></div>
		</div>
		</div>
		</div>
		</td>
	</tr>
</table>
</div>
</div>
<!-- Result Frame Ends --></div>
<!-- close of interface -->

<!-- You can build your error messages below. Similar divs would have to be built to show any other messages in a inline pop-up.-->
<!-- Error Messages Begins -->
<div id="errorMessagesArea" class="errorMessages"
	style="display: none; overflow: auto;"></div>


</body>
</html:html>

