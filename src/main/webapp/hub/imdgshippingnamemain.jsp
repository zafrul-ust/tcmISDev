<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
	"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<%@ taglib uri="/WEB-INF/struts-nested.tld" prefix="nested"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/tcmis.tld" prefix="tcmis"%>

<html:html lang="true">
<head>
	<meta http-equiv="content-type" content="text/html; charset=UTF-8">
	<meta http-equiv="expires" content="-1">
	<link rel="shortcut icon" href="https://www.tcmis.com/images/buttons/tcmIS.ico"></link>

	<%@ include file="/common/locale.jsp"%>
	<!--Use this tag to get the correct CSS class.
		This looks at what the user's preffered font size and which application he is viewing to set the correct CSS. -->
	<tcmis:gridFontSizeCss /> 
	
	<%-- Add any other stylesheets you need for the page here --%> 
	<script type="text/javascript" src="/js/common/formchek.js"></script> 
	<script type="text/javascript" src="/js/common/commonutil.js"></script> 

	<!-- This handles all the resizing of the page and frames --> 
	<script type="text/javascript" src="/js/common/grid/iframegridresizemain.js"></script> 
	
	<!-- This handles which key press events are disabled on this page -->
	<script type="text/javascript" src="/js/common/disableKeys.js"></script> 
	
	<!-- This handels the menu style and what happens to the right click on the whole page --> 
	<script type="text/javascript" src="/js/menu/milonic_src.js"></script>
	<script type="text/javascript" src="/js/menu/mmenudom.js"></script> 
	<script type="text/javascript" src="/js/menu/mainmenudata.js"></script> 
	<script type="text/javascript" src="/js/menu/contextmenu.js"></script> 
	
	<%@ include file="/common/rightclickmenudata.jsp"%> 
	
	<!-- For Calendar support --> 
	<script type="text/javascript" src="/js/calendar/newcalendar.js"></script> 
	<script type="text/javascript" src="/js/calendar/AnchorPosition.js"></script> 
	<script type="text/javascript" src="/js/calendar/PopupWindow.js"></script> 
	
	<!-- Add any other javascript you need for the page here --> 
	<script type="text/javascript"src="/js/common/standardGridmain.js"></script> 
	<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxcommon.js"></script> 
	<script type="text/javascript" src="/dhtmlxLayout/codebase/dhtmlxlayout.js"></script> 
	<script type="text/javascript" src="/dhtmlxWindows/codebase/dhtmlxwindows.js"></script>

<!-- Add any other javascript you need for the page here -->
<script type="text/javascript" src="/js/hub/imdgshippingnamemain.js"></script>

<title>
<fmt:message key="label.imdgshippingnames"/>
</title>

<script language="JavaScript" type="text/javascript">
<!--
//add all the javascript messages here, this for internationalization of client side javascript messages
var returnSelectedValue=null;
var returnSelectedId=null;
var valueElementId="${valueElementId}";
var displayElementId="${displayElementId}";

var imdgId = null; 
var unNumber = null; 
var description = null;
var classOrDivision = null;
var subsidiaryRisk = null;
var packingGroup = null;
var specialProvision = null;
var limitedQuantity = null;
var packingInstruction = null;
var specialPackingProvision = null;
var ibcSpecialProvision = null;
var imoTankInstruction= null;
var unTankAndBulkContInstr = null;
var tankSpecialProvision = null;
var ems = null;
var stowageAndSegregation = null;
var property = null;
var observation = null;
var star = null;
var state = null;
var shippingNameCount = null;
var properShippingName = null;



var messagesData = new Array();
messagesData={alert:"<fmt:message key="label.alert"/>",and:"<fmt:message key="label.and"/>",
validvalues:"<fmt:message key="label.validvalues"/>",submitOnlyOnce:"<fmt:message key="label.submitOnlyOnce"/>", itemInteger:"<fmt:message key="error.item.integer"/>",
missingSearchText:"<fmt:message key="receiptdocumentviewer.searchmessage"/>",    pleasewait:"<fmt:message key="label.pleasewait"/>",showlegend:"<fmt:message key="label.showlegend"/>"};
// -->
</script>
</head>

<body bgcolor="#ffffff" onload="loadLayoutWin('','imdgShippingInfo');mainOnload();try{document.getElementById('searchArgument').focus()}catch(ex){}" onunload="try{opener.closeTransitWin();}catch(ex){}" onresize="resizeFrames()">

<div class="interface" id="mainPage" style="">

<!-- Search Frame Begins -->
<div id="searchFrameDiv">

<div class="contentArea"> <!-- Start of contentArea-->
<tcmis:form action="/imdgshippingnameresults.do" onsubmit="return submitFrameOnlyOnce();" target="resultFrame">
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
    <!-- Search Option Table Start -->
<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableSearch">
	<tr>
		<td width="15%" class="optionTitleBoldRight">
			<fmt:message key="label.searchtext"/>:
		</td>
		<td width="85%" class="optionTitleLeft">
			<input type="text" name="searchArgument" id="searchArgument" value="${param.searchText}" size="40" class="inputBox"/>
		</td>
	</tr>
	<tr>
		<td width="15%" class="optionTitleBoldRight">
			<input type="submit" class="inputBtns" name="submitSearch" id="submitSearch" onmouseover="this.className='inputBtns inputBtnsOver'" onmouseout="this.className='inputBtns'" value="<fmt:message key="label.search"/>" 
				   onclick="document.getElementById('selectedRow').innerHTML='';return submitSearchForm();" />
		</td>
		<td width="85%"/>
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


<!-- Error Messages Begins -->
<!-- Build this section only if there is an error message to display.
     For the search section, we show the error messages within its frame
-->
<c:if test="${requestScope['org.apache.struts.action.ERROR'] != null}">
<div class="spacerY">&nbsp;
<div id="errorMessagesArea" class="errorMessages">
<html:errors/>
</div>
</div>
</c:if>
<!-- Error Messages Ends -->
<!-- Hidden element start -->
<div id="hiddenElements" style="display: none;">
<input name="startSearchTime" id="startSearchTime" type="hidden" value=""> <!-- needed if this page will show on application.do -->
<input type="hidden" name="uAction" id="uAction" value="search"/>
<!-- To get the correct height value to insert, insert showSearchHeight = true; anywhere in the JavaScript array section in main.jsp.  --> 
<input name="searchHeight" id="searchHeight" type="hidden" value="214">	
<input name="searchType" id="searchType" type="hidden" value="${param.searchType}" />
<input name="noUse" id="noUse" value=""/>	

</div>
<!-- Hidden elements end -->
</tcmis:form>
</div> <!-- close of contentArea -->


</div>

<!-- Search Frame Ends -->

<div class="spacerY">&nbsp;</div>

<%--NEw -Transit Page Starts --%>
<div id="transitPage" style="display: none;" class="optionTitleBoldCenter">
  <br><br><br><fmt:message key="label.pleasewait"/>
  <br><br><br><img src="/images/rel_interstitial_loading.gif" align="middle">
</div>
<!-- Transit Page Ends -->

<div id="transitDailogWin" class="errorMessages" style="display: none;overflow: auto;">
	<table width="100%" border="0" cellpadding="2" cellspacing="1">
		<tr><td>&nbsp;</td></tr>
		<tr><td>&nbsp;</td></tr>
		<tr><td>&nbsp;</td></tr>
		<tr>
			<td align="center" id="transitLabel">
			</td>
		</tr>
		<tr>
			<td align="center">
				<img src="/images/rel_interstitial_loading.gif" align="middle">
			</td>
		</tr>
	</table>
</div>	

<!-- Result Frame Begins -->
<div id="resultFrameDiv" style="margin: 4px 4px 0px 4px;">
<div id="resultGridDiv" style="display: none;">
<!-- Search results start -->
<!-- If you want your results section to span only 50% set this to 50% and your main table will be 100% -->
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
      <div id="mainUpdateLinks"> <%--  --%>
             <span id="showlegendLink">
        <a href="javascript:showshippingpagelegend()"><fmt:message key="label.showlegend"/></a>
      </span>
         <span id="selectedRow">&nbsp;</span>
      </div> <%-- mainUpdateLinks Ends --%>
    </div> <%-- boxhead Ends --%>

    <div class="dataContent">
     <iframe id="resultFrame" name="resultFrame" width="100%" height="250" frameborder="0" marginwidth="0" src="/blank.html"></iframe>
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
</div><!-- Result Frame Ends -->
</div>
<!-- Can not have footer message because of resizing issues with iframe -->
<%--
<!-- Footer message start -->
<div id="footer" class="messageBar">545</div>
<!-- Footer message end -->
--%>



</div> <!-- close of interface -->

<!-- You can build your error messages below. Similar divs would have to be built to show any other messages in a yui pop-up.-->
<!-- Error Messages Begins -->
<div id="errorMessagesArea" class="errorMessages" style="display:none;z-index:5;">
<div id="errorMessagesAreaTitle" class="hd"><fmt:message key="label.errors"/></div>
<div id="errorMessagesAreaBody" class="bd">

</div>
</div>
<%-- show legend Begins --%>
<div id="showLegendArea" style="display: none;overflow: auto;">
  <table width=100% class="tableResults" border="0" cellpadding="0" cellspacing="0">
    <tr><td width="100px" class="black legendText">&nbsp;</td><td class="legendText"><fmt:message key="label.noshippingname"/></td></tr>
    <tr><td width="100px" class="red legendText">&nbsp;</td><td class="legendText"><fmt:message key="label.forbidden"/></td></tr>
  </table>
</div>
<%-- show legend Ends --%>
</body>
</html:html>