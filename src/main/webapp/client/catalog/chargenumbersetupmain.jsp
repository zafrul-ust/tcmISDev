<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
	"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html;charset=UTF-8" language="java"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

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

<script type="text/javascript" src="/js/common/standardGridmain.js"></script>
<!-- Add any other javascript you need for the page here -->
<script type="text/javascript" src="/js/client/catalog/chargenumbersetupmain.js"></script>

<!-- These are for the Grid, uncomment if you are going to use the grid -->
<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxcommon.js"></script>
<script type="text/javascript" src="/dhtmlxLayout/codebase/dhtmlxlayout.js"></script>
<script type="text/javascript" src="/dhtmlxWindows/codebase/dhtmlxwindows.js"></script>

<title>
    <fmt:message key="label.chargenumbersetup"/>
</title>

<script language="JavaScript" type="text/javascript">
<!--
//add all the javascript messages here, this for internationalization of client side javascript messages
var messagesData = new Array();
messagesData={alert:"<fmt:message key="label.alert"/>",and:"<fmt:message key="label.and"/>",all:"<fmt:message key="label.all"/>",
showlegend:"<fmt:message key="label.showlegend"/>",errors:"<fmt:message key="label.errors"/>",  
validvalues:"<fmt:message key="label.validvalues"/>",submitOnlyOnce:"<fmt:message key="label.submitOnlyOnce"/>",
pleaseselect:"<fmt:message key="label.pleaseselect"/>",
showlegend:"<fmt:message key="label.showlegend"/>"};

<c:set var="prevFac" value=""/>
<c:set var="prevAccountSysId" value=""/>
<c:set var="prevChargeType" value=""/>
<c:set var="preStatus" value="0"/>
var facilityAccountSysChargeTypeArr = [
	<c:forEach var="fac" items="${prRulesFacColl}" varStatus="status"> 
	  <c:set var="currentAccountSysId" value="${status.current.facilityId}|${status.current.accountSysId}"/>
	  <c:set var="currentChargeType" value="${status.current.facilityId}|${status.current.accountSysId}|${status.current.chargeType}"/>

	  <c:set var="chargeLabel1" value=""/>
	  <c:set var="chargeId1" value=""/>
	  <c:set var="chargeLabel2" value=""/>
	  <c:set var="chargeId2" value=""/>
	  <c:set var="chargeLabel3" value=""/>
	  <c:set var="chargeId3" value=""/>
	  <c:set var="chargeLabel4" value=""/>
	  <c:set var="chargeId4" value=""/>
	  <c:set var="chargeValidation1" value=""/>
	  <c:set var="chargeValidation2" value=""/>
	  <c:set var="chargeValidation3" value=""/>
	  <c:set var="chargeValidation4" value=""/>
	  <c:set var="printChargeDesc1" value=""/>
	  <c:set var="printChargeDesc2" value=""/>
	  <c:set var="printChargeDesc3" value=""/>
	  <c:set var="printChargeDesc4" value=""/>  
	  
	  <c:if test="${fn:toLowerCase(status.current.chargeValid1) == 'y'}">
		  <c:set var="chargeLabel1" value="${status.current.chargeLabel1}"/>
		  <c:set var="chargeId1" value="${status.current.chargeId1}"/>
		  <c:set var="chargeValidation1" value="${status.current.chargeNumberFormat1}"/>
		  <c:set var="printChargeDesc1" value="${status.current.printChargeDesc1}"/>
	  </c:if> 
	  <c:if test="${fn:toLowerCase(status.current.chargeValid2) == 'y'}">
		  <c:set var="chargeLabel2" value="${status.current.chargeLabel2}"/>
		  <c:set var="chargeId2" value="${status.current.chargeId2}"/>
		  <c:set var="chargeValidation2" value="${status.current.chargeNumberFormat2}"/>
		  <c:set var="printChargeDesc2" value="${status.current.printChargeDesc2}"/>
	  </c:if> 
	  <c:if test="${fn:toLowerCase(status.current.chargeValid3) == 'y'}">
		  <c:set var="chargeLabel3" value="${status.current.chargeLabel3}"/>
		  <c:set var="chargeId3" value="${status.current.chargeId3}"/>
		  <c:set var="chargeValidation3" value="${status.current.chargeNumberFormat3}"/>
		  <c:set var="printChargeDesc3" value="${status.current.printChargeDesc3}"/>
	  </c:if> 
	  <c:if test="${fn:toLowerCase(status.current.chargeValid4) == 'y'}">
		  <c:set var="chargeLabel4" value="${status.current.chargeLabel4}"/>
		  <c:set var="chargeId4" value="${status.current.chargeId4}"/>
		  <c:set var="chargeValidation4" value="${status.current.chargeNumberFormat4}"/>
		  <c:set var="printChargeDesc4" value="${status.current.printChargeDesc4}"/>  
	  </c:if> 
	  
	  <c:choose>
	    <c:when test="${prevFac != status.current.facilityId}">
	     <c:if test="${preStatus == 1}">}]}]},</c:if> 
	     <c:if test="${preStatus == 2}">}]}]},</c:if>
	     <c:if test="${preStatus == 3}">}]}]},</c:if>
	   {  id: '${status.current.facilityId}', 
	  	 name: '<tcmis:jsReplace value="${status.current.facilityName}"/>',
	  	 coll:[
		 	{ 
		 		id:'${status.current.accountSysId}', 
		 		name:'<tcmis:jsReplace value="${status.current.accountSysName}"/>',
		 		coll:[
		 		   { 
		 			 id:'${status.current.chargeType}',
		 			 name:<c:if test="${status.current.chargeType == 'i'}">'<fmt:message key="label.indirect"/>',</c:if>
		 			 	  <c:if test="${status.current.chargeType == 'd'}">'<fmt:message key="label.direct"/>',</c:if>
		 			 chargeLabel1:'${chargeLabel1}',
		 	    	 chargeLabel2:'${chargeLabel2}',
		 	    	 chargeLabel3:'${chargeLabel3}',
		 	    	 chargeLabel4:'${chargeLabel4}',
		 			 chargeId1:'${chargeId1}',
		 	    	 chargeId2:'${chargeId2}',
		 	    	 chargeId3:'${chargeId3}',
		 	    	 chargeId4:'${chargeId4}',
		 	   	 	 chargeValidation1:'${chargeValidation1}',
		 	   	 	 chargeValidation2:'${chargeValidation2}',
		 	   	 	 chargeValidation3:'${chargeValidation3}',
		 	   	 	 chargeValidation4:'${chargeValidation4}',
		 	   	 	 printChargeDesc1:'${printChargeDesc1}',
		 	   		 printChargeDesc2:'${printChargeDesc2}',
		 	   		 printChargeDesc3:'${printChargeDesc3}',
		 	   		 printChargeDesc4:'${printChargeDesc4}'
		 	<c:set var="preStatus" value="1"/>
	    </c:when>
	    <c:when test="${prevFac == status.current.facilityId && prevAccountSysId != currentAccountSysId}">
	    		<c:if test="${preStatus == 1}">}]},</c:if>
	    		<c:if test="${preStatus == 2}">}]},</c:if>
	    		<c:if test="${preStatus == 3}">}]},</c:if>
		 	{	id:'${status.current.accountSysId}', 
		 		name:'<tcmis:jsReplace value="${status.current.accountSysName}"/>',
		 		coll:[
		 		   { 
		 			 id:'${status.current.chargeType}',
		 			 name:<c:if test="${status.current.chargeType == 'i'}">'<fmt:message key="label.indirect"/>',</c:if>
		 			 	  <c:if test="${status.current.chargeType == 'd'}">'<fmt:message key="label.direct"/>',</c:if>
		 			 chargeLabel1:'${chargeLabel1}',
		 	    	 chargeLabel2:'${chargeLabel2}',
		 	    	 chargeLabel3:'${chargeLabel3}',
		 	    	 chargeLabel4:'${chargeLabel4}',
		 			 chargeId1:'${chargeId1}',
		 	    	 chargeId2:'${chargeId2}',
		 	    	 chargeId3:'${chargeId3}',
		 	    	 chargeId4:'${chargeId4}',	 
		 	   	 	 chargeValidation1:'${chargeValidation1}',
		 	   	 	 chargeValidation2:'${chargeValidation2}',
		 	   	 	 chargeValidation3:'${chargeValidation3}',
		 	   	 	 chargeValidation4:'${chargeValidation4}',
		 	   	 	 printChargeDesc1:'${printChargeDesc1}',
		 	   		 printChargeDesc2:'${printChargeDesc2}',
		 	   		 printChargeDesc3:'${printChargeDesc3}',
		 	   		 printChargeDesc4:'${printChargeDesc4}'
		    <c:set var="preStatus" value="2"/>
	    </c:when>
	    <c:when test="${prevFac == status.current.facilityId && prevAccountSysId == currentAccountSysId && prevChargeType != currentChargeType}">
	    		<c:if test="${preStatus == 1}">},</c:if>
	    		<c:if test="${preStatus == 2}">},</c:if>
	    		<c:if test="${preStatus == 3}">},</c:if>
		 	{	id:'${status.current.chargeType}',
		 		name:<c:if test="${status.current.chargeType == 'i'}">'<fmt:message key="label.indirect"/>',</c:if>
		 			 	  <c:if test="${status.current.chargeType == 'd'}">'<fmt:message key="label.direct"/>',</c:if>
		 	    chargeLabel1:'${chargeLabel1}' ,
		 	    chargeLabel2:'${chargeLabel2}',
		 	    chargeLabel3:'${chargeLabel3}',
	 	    	chargeLabel4:'${chargeLabel4}',
	 			chargeId1:'${chargeId1}',
	 	    	chargeId2:'${chargeId2}',
	 	    	chargeId3:'${chargeId3}',
	 	    	chargeId4:'${chargeId4}',	 
	 	   	 	 chargeValidation1:'${chargeValidation1}',
	 	   	 	 chargeValidation2:'${chargeValidation2}',
	 	   	 	 chargeValidation3:'${chargeValidation3}',
	 	   	 	 chargeValidation4:'${chargeValidation4}',
	 	   	 	 printChargeDesc1:'${printChargeDesc1}',
	 	   		 printChargeDesc2:'${printChargeDesc2}',
	 	   		 printChargeDesc3:'${printChargeDesc3}',
	 	   		 printChargeDesc4:'${printChargeDesc4}'
		    <c:set var="preStatus" value="3"/>
	    </c:when>
	   </c:choose>   
	   <c:if test="${status.last}">}]}]}</c:if>	
	<c:set var="prevFac" value="${status.current.facilityId}"/>
	<c:set var="prevAccountSysId" value="${currentAccountSysId}"/>
	<c:set var="prevChargeType" value="${currentChargeType}"/>
	</c:forEach>]; 
	
// -->
</script>
</head>
<%-- displayOnly needs to match the pageData in application.jsp if the page will be on the menu --%>
<body bgcolor="#ffffff" onload="loadLayoutWin('','chargeNumberSetup');setDropdowns();" onresize="resizeFrames()">

<div class="interface" id="mainPage" style="">

<!-- Search Frame Begins -->
<div id="searchFrameDiv">
<%--NEW - removed the search frame and copied the search section here--%>
<div class="contentArea">
<tcmis:form action="/chargenumbersetupresults.do" onsubmit="return submitSearchOnlyOnce();" target="resultFrame">
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
    <%--  <table width="700" id="searchTable" border="0" cellpadding="0" cellspacing="0" class="tableSearch"> --%>
    <table width="300" id="searchTable" border="0" cellpadding="0" cellspacing="0" class="tableSearch">
    <%-- Row 1 --%>
    <tr>
      <td nowrap  width="8%" class="optionTitleBoldRight" nowrap><fmt:message key="label.facility"/>:</td>
      <td width="6%" class="optionTitleBoldLeft" nowrap>
         <select name="facilityId" id="facilityId" class="selectBox">
         </select>
      </td>
      <td nowrap  width="8%" class="optionTitleBoldRight" nowrap><span id="label1Span" style="display:none;"/>:</td>
      <td width="6%" class="optionTitleBoldLeft" nowrap>
         <input name="chargeNumber1" id="chargeNumber1" type="text" class="inputBox" style="display:none;" size="15" maxlength="30" onkeypress="return onKeyPress()"/>
      </td>
    </tr>
    <%-- Row 2 --%>
    <tr>
      <td nowrap width="6%"  class="optionTitleBoldRight"><fmt:message key="label.accountsys"/>:</td>
      <td  width="10%" class="optionTitleBoldLeft">
         <select name="accountSysId" id="accountSysId" class="selectBox">
         </select>
      </td>	
      <td nowrap width="6%" class="optionTitleBoldRight" ><span id="label2Span" style="display:none;"/>:</td>
      <td  width="10%" class="optionTitleBoldLeft">
         <input name="chargeNumber2" id="chargeNumber2" type="text" class="inputBox" style="display:none;" size="15" maxlength="30" onkeypress="return onKeyPress()"/>
      </td>	
    </tr>
    <%-- Row 3 --%>
    <tr>
      <td nowrap  width="8%" class="optionTitleBoldRight"><fmt:message key="label.chargetype"/>:</td>
      <td width="14%" class="optionTitleBoldLeft">
       <select name="chargeType" id="chargeType" class="selectBox">
       </select>
      </td>	 
      <td nowrap  width="8%" class="optionTitleBoldRight" ><span id="label3Span" style="display:none;"/>:</td>
      <td width="14%" class="optionTitleBoldLeft" >
       <input name="chargeNumber3" id="chargeNumber3" type="text" class="inputBox" style="display:none;" size="15" maxlength="30" onkeypress="return onKeyPress()"/>
      </td>	               
    </tr>
    <%-- Row 4 --%>
    <tr>    
	  <td  class="optionTitleBoldRight">&nbsp;</td>
      <td nowrap>
          &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
      </td> 
      <td  class="optionTitleBoldRight" ><span id="label4Span" style="display:none;"/>:</td>
      <td nowrap class="optionTitleBoldLeft" >
          <input name="chargeNumber4" id="chargeNumber4" type="text" class="inputBox" style="display:none;" size="15" maxlength="30" onkeypress="return onKeyPress()"/>
      </td>           
    </tr>
    <%-- Row 5 --%>
    <tr>
     <td colspan="2"  class="optionTitleBoldLeft" nowrap>
          <input name="submitSearch" type="submit" class="inputBtns" value="<fmt:message key="label.search"/>" id="submitSearch" onmouseover="this.className='inputBtns inputBtnsOver'" onMouseout="this.className='inputBtns'"
          		 onclick= "return submitSearchForm()"/>
          <input name="xslButton" type="button" class="inputBtns" value="<fmt:message key="label.createExcel"/>" id="xslButton" onmouseover="this.className='inputBtns inputBtnsOver'" onMouseout="this.className='inputBtns'"
          		 onclick= "return createXSL()"/>
      </td>
      <td nowrap>
          &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
      </td>
      <td >&nbsp;</td>
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
  <input type="hidden" name="chargeNumber1Label" id="chargeNumber1Label" value=""/>
  <input type="hidden" name="chargeNumber2Label" id="chargeNumber2Label" value=""/>
  <input type="hidden" name="chargeNumber3Label" id="chargeNumber3Label" value=""/>
  <input type="hidden" name="chargeNumber4Label" id="chargeNumber4Label" value=""/>
  <input type="hidden" name="chargeLabel1" id="chargeLabel1" value=""/>
  <input type="hidden" name="chargeLabel2" id="chargeLabel2" value=""/>
  <input type="hidden" name="chargeLabel3" id="chargeLabel3" value=""/>
  <input type="hidden" name="chargeLabel4" id="chargeLabel4" value=""/>
  <input type="hidden" name="chargeLabel1Alias" id="chargeLabel1Alias" value=""/>
  <input type="hidden" name="chargeLabel2Alias" id="chargeLabel2Alias" value=""/>
  <input type="hidden" name="chargeLabel3Alias" id="chargeLabel3Alias" value=""/>
  <input type="hidden" name="chargeLabel4Alias" id="chargeLabel4Alias" value=""/>
  <input type="hidden" name="myDefaultFacilityId" id="myDefaultFacilityId" value="${personnelBean.myDefaultFacilityId}"/>
  <input type="hidden" name="chargeValidation1" id="chargeValidation1" value=""/>
  <input type="hidden" name="chargeValidation2" id="chargeValidation2" value=""/>
  <input type="hidden" name="chargeValidation3" id="chargeValidation3" value=""/>
  <input type="hidden" name="chargeValidation4" id="chargeValidation4" value=""/>
  <input type="hidden" name="printChargeDesc1" id="printChargeDesc1" value=""/>
  <input type="hidden" name="printChargeDesc2" id="printChargeDesc2" value=""/>
  <input type="hidden" name="printChargeDesc3" id="printChargeDesc3" value=""/>
  <input type="hidden" name="printChargeDesc4" id="printChargeDesc4" value=""/>
  

  <input name="startSearchTime" id="startSearchTime" type="hidden" value="" />
 <!-- needed if this page will show on application.do -->
 <!-- To get the correct height value to insert, insert showSearchHeight = true; anywhere in the JavaScript array section in main.jsp.  -->
  <input name="searchHeight" id="searchHeight" type="hidden" value="214"/>
  <input name="maxData" id="maxData" type="hidden" value="500"/>  
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
  <br/><br/><br/><fmt:message key="label.pleasewait"/>
  <br/><br/><br/><img src="/images/rel_interstitial_loading.gif" align="middle"/>
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
    <div class="boxhead"> 
    <%-- boxhead Begins --%>
     <%-- Place all update links in divs that can be turned on or off depending on the search criteria that is selected
          Do not remove mainUpdateLinks div, even if you don't have any links. You can remove content inside the div but not the div itself.
          Please keep the <span></span> on the same line this will avoid extra virtical space.
      --%>     
		<%-- Use this case if you only have one update link to minimize extra line.  Notice this uses "div" instead of "span" --%>
	  <div id="mainUpdateLinks" style="display: none"> <%-- mainUpdateLinks Begins --%>
		<span id="showlegendLink"><a href="javascript:resultFrame.addNewRow();"><fmt:message key="label.new"/></a></span>
	   	<span id="updateSpan"> | <a href="javascript:resultFrame.updateData();"><fmt:message key="label.update"/></a></span>   
	   	<span id="deleteRowSpan" style="display: none"> | <a href="javascript:resultFrame.deleteRow();"><fmt:message key="label.delete"/></a></span>  
      </div> <%-- mainUpdateLinks Ends --%>
     <%-- END OF OR --%>
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