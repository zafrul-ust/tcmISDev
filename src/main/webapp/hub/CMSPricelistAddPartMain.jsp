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

<tcmis:gridFontSizeCss />

<script type="text/javascript" src="/js/common/formchek.js"></script>
<script type="text/javascript" src="/js/common/commonutil.js"></script>
<script type="text/javascript" src="/js/common/grid/iframegridresizemain.js"></script>
<script type="text/javascript" src="/js/common/disableKeys.js"></script>

<script type="text/javascript" src="/js/menu/milonic_src.js"></script>
<script type="text/javascript" src="/js/menu/mmenudom.js"></script>
<script type="text/javascript" src="/js/menu/mainmenudata.js"></script>
<script type="text/javascript" src="/js/menu/contextmenu.js"></script>
<%@ include file="/common/rightclickmenudata.jsp" %>

<!-- Add any other javascript you need for the page here -->
<script type="text/javascript" src="/js/common/standardGridmain.js"></script>

<title>
<fmt:message key="label.addpart" />
</title>

<script language="JavaScript" type="text/javascript">
windowCloseOnEsc = true;
//add all the javascript messages here, this for internationalization of client side javascript messages
var messagesData = new Array();
messagesData = {
	alert:"<fmt:message key="label.alert"/>",
	and:"<fmt:message key="label.and"/>",
	all:"<fmt:message key="label.all"/>",
	showlegend:"<fmt:message key="label.showlegend"/>",
	errors:"<fmt:message key="label.errors"/>",
	validvalues:"<fmt:message key="label.validvalues"/>",
	submitOnlyOnce:"<fmt:message key="label.submitOnlyOnce"/>",
	itemInteger:"<fmt:message key="error.item.integer"/>"
	};
useLayout = false;

function mySubmitSearchForm() {
	var now = new Date();
    document.getElementById("startSearchTime").value = now.getTime();
   	document.genericForm.target='resultFrame';
   	$('uAction').value = 'searchAddPart';
   	showPleaseWait();
    document.genericForm.submit(); 
  return false;
}

</script>
</head>

<body bgcolor="#ffffff" onload="loadLayoutWin('','CMSPricelistAddPart');" onresize="resizeFrames()" onunload="try{opener.closeTransitWin();}catch(ex){}">

<div class="interface" id="mainPage" style="">

<!-- Search Frame Begins -->
<div id="searchFrameDiv">
<%--NEW - removed the search frame and copied the search section here--%>
<div class="contentArea">
<tcmis:form action="/CMSPricelist.do" onsubmit="return submitSearchOnlyOnce();" target="resultFrame">
<!-- Search Option Begins -->
<table id="searchMaskTable" width="400" border="0" cellpadding="0" cellspacing="0">
<tr><td>
<div class="roundcont filterContainer">
 <div class="roundright">
   <div class="roundtop">
     <div class="roundtopright"> <img src="/images/rndBoxes/borderTL_filter.gif" alt="" width="15" height="10" class="corner_filter" style="display: none" /></div>
   </div>
   <div class="roundContent">
    <!-- Insert all the search option within this div -->
<table width="100%" id="searchTable" border="0" cellpadding="0" cellspacing="0" class="tableSearch">
	<tr>
		<td width="20%" class="optionTitleBoldRight">
			<fmt:message key="label.catalog"/>:
		</td>
		<td width="40%" class="optionTitleLeft">
			<select name="catalogId" class="selectBox" id="catalogId"><c:forEach var="catalog" items="${companyCatalogs}" varStatus="status">
				<option value="${catalog.catalogId}" <c:if test="${catalog.catalogId == param.catalogId}">selected</c:if>>${catalog.catalogDesc}</option>
			</c:forEach></select>
			
		</td>
		<td width="5%"/>
		<td width="10%"/>
	</tr>
	<tr>
		<td width="20%" class="optionTitleBoldRight">
			<fmt:message key="label.search"/>:
		</td>
		<td width="40%" class="optionTitleLeft">
			<input type="text" name="searchArgument" id="searchArgument" value="${param.searchArgument}" size="40" class="inputBox" onkeypress="return onKeyPress()"/>
		</td>
		<td width="5%"/>
		<td width="10%"/>
	</tr>
	<tr>
		<td>
          <input name="submitSearch" type="button" class="inputBtns" value="<fmt:message key="label.search"/>" id="submitSearch" onmouseover="this.className='inputBtns inputBtnsOver'" onMouseout="this.className='inputBtns'"
          		 onclick="mySubmitSearchForm();return false;">
		</td>
	</tr>
</table>
    <!-- Search Option Table end -->
   </div>
   <div class="roundbottom">
     <div class="roundbottomright"> <img src="/images/rndBoxes/borderBL.gif" alt="" width="15" height="15" class="corner" style="display: none" /> </div>
   </div>
 </div>
</div>
</td></tr>
</table>
<!-- Search Option Ends -->


<!-- Hidden element start -->
<div id="hiddenElements" style="display: none;">
<input type="text" name="uAction" id="uAction" value="searchAddPart"/>
<input type="text" name="companyId" id="companyID" value="<tcmis:jsReplace value="${param.companyId}" processMultiLines="true" />"/>
<input name="startSearchTime" id="startSearchTime" type="hidden" value="" />
</div>
<!-- Hidden elements end -->

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
      </span>&nbsp;
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