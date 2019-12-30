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

<!--Use this tag to get the correct CSS class.
This looks at what the user's preffered font size and which application he is viewing to set the correct CSS. -->
<tcmis:fontSizeCss />
<!-- CSS for YUI -->
<%--
<link rel="stylesheet" type="text/css" href="/yui/build/container/assets/container.css" />
--%>
<!-- Add any other stylesheets you need for the page here -->

<%--
<link rel="stylesheet" type="text/css" href="/css/dhtmlXGridHaas.css"></link>
--%>

<script type="text/javascript" src="/js/common/formchek.js"></script>
<script type="text/javascript" src="/js/common/commonutil.js"></script>
<script type="text/javascript" src="/js/common/searchiframeresize.js"></script>
<!-- This handels which key press events are disabeled on this page -->
<script type="text/javascript" src="/js/common/disableKeys.js"></script>

<!-- This handels the menu style and what happens to the right click on the whole page -->
<script type="text/javascript" src="/js/menu/milonic_src.js"></script>
<script type="text/javascript" src="/js/menu/mmenudom.js"></script>
<script type="text/javascript" src="/js/menu/mainmenudata.js"></script>
<script type="text/javascript" src="/js/menu/contextmenu.js"></script>
<%@ include file="/common/rightclickmenudata.jsp" %>

<!-- For Calendar support -->
<%--
<script type="text/javascript" src="/js/calendar/newcalendar.js"></script>
<script type="text/javascript" src="/js/calendar/AnchorPosition.js"></script>
<script type="text/javascript" src="/js/calendar/PopupWindow.js"></script>
--%>


<!-- Add any other javascript you need for the page here -->
<script type="text/javascript" src="/js/repackaging.js"></script>

<!-- These are for the Grid, uncomment if you are going to use the grid -->
<%--<script src="/js/dhtmlxGrid/dhtmlXCommon.js"></script>
<script type="text/javascript" src="/js/dhtmlxGrid/dhtmlXGridHaas.js"></script>
<script type="text/javascript" src="/js/dhtmlxGrid/dhtmlXGridCell.js"></script>
<script type="text/javascript" src="/js/dhtmlxGrid/dhtmlXGrid_excell_mro.js"></script>
--%>

<!-- This is for the YUI, uncomment if you will use this -->
<%--<script type="text/javascript" src="/yui/build/yahoo/yahoo.js" ></script>
<script type="text/javascript" src="/yui/build/event/event.js" ></script>
<script type="text/javascript" src="/yui/build/dom/dom.js" ></script>
<script type="text/javascript" src="/yui/build/animation/animation.js" ></script>
<script type="text/javascript" src="/yui/build/container/container.js"></script>
<script type="text/javascript" src="/js/common/waitDialog.js"></script>
<script type="text/javascript" src="/yui/build/dragdrop/dragdrop.js" ></script>--%>

<title>
<fmt:message key="label.repackaging"/>
</title>

<script language="JavaScript" type="text/javascript">
<!--
//add all the javascript messages here, this for internationalization of client side javascript messages
var messagesData = new Array();
messagesData={alert:"<fmt:message key="label.alert"/>",and:"<fmt:message key="label.and"/>",
validvalues:"<fmt:message key="label.validvalues"/>",submitOnlyOnce:"<fmt:message key="label.submitOnlyOnce"/>"};
// -->
</script>
</head>

<body bgcolor="#ffffff" onload="setSearchFrameSize();">

<!--Uncomment for production-->
<%--<tcmis:form action="/repackaging.do" onsubmit="return submitFrameOnlyOnce();" target="resultFrame">--%>

<div class="interface" id="searchMainPage"> <!-- Start of interface-->

<div class="contentArea"> <!-- Start of contentArea-->

<!-- Search Option Begins -->
<table id="searchMaskTable" width="1000" border="0" cellpadding="0" cellspacing="0">
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
 		<td width="20%" class="optionTitleBoldRight">
  			<fmt:message key="label.hub"/>:
 		</td>
		<td width="20%" class="optionTitleLeft">      
 			<select name="hubId" id="hubId" class="selectBox">  <%-- OnChange="hubchanged(document.receiving.HubName)" --%>
				<option value="2122">DANA Hub</option>
				<option value="2119">Silicon Valley Hub</option>
 			</select>
 		</td>    
 		
 		<td width="20%" class="optionTitleBoldRight">
  			<fmt:message key="label.search"/>:
 		</td>
		<td width="10%" class="optionTitleLeft">     
 			<html:select property="searchField" styleId="searchField" styleClass="selectBox"> 							 			
				<html:option  value="ITEM_DESCRIPTION"><fmt:message key="label.itemdesc"/></html:option>
				<html:option  value="ITEM_ID"><fmt:message key="label.itemid"/></html:option>
				<html:option  value="MR_NUMBER"><fmt:message key="label.mr"/></html:option>
				<html:option  value="RADIAN_PO" selected="selected" ><fmt:message key="label.po"/></html:option>
				<html:option  value="REQUESTOR_NAME"><fmt:message key="label.requestor"/></html:option>
 			</html:select>		 
 		</td>    
		<td width="10%" class="optionTitleLeft">     
 			<html:select property="searchMode" styleId="searchMode" styleClass="selectBox"> 							 					
				<html:option  value="is" selected="selected" ><fmt:message key="label.is"/></html:option>
				<html:option  value="contains"><fmt:message key="label.contains"/></html:option> 			
 			</html:select>		 
 		</td>    
 		<td width="5%" class="optionTitleBoldRight">
  			<fmt:message key="label.for"/>
 		</td>
		<td width="20%" class="optionTitleLeft" colspan="2">       		
   			<input class="inputBox" type="text" name="searchArgument" id="searchArgument" size="20">
 		</td>
 	  </tr>  
 	  
 	  <tr class="alignLeft">      
 		<td width="20%" class="optionTitleBoldRight">
  			<fmt:message key="label.invgrp"/>:
 		</td>
		<td width="20%" class="optionTitleLeft">      
 			<select name="inventoryGrp" id="inventoryGrp" class="selectBox">  <%-- OnChange="hubchanged(document.receiving.HubName)" --%>
				<option  value="Salem"> Salem</option>
				<option  value="Salem BAE"> Salem BAE</option>
 			</select>
 		</td>    
 		
 		<td width="20%" class="optionTitleBoldRight">
  			<fmt:message key="label.atprocessgenlabelsas"/>:&nbsp;
 		</td>
        <td width="10%" class="optionTitleBoldLeft"> 		
			<input name="paperSize" id="paperSize" type="radio" value="31" class="radioBtns" checked>3''/1''&nbsp; <%-- ONCLICK="assignPaper('31')" --%>
 		</td>      
        <td width="10%" class="optionTitleBoldLeft"> 		
			<input name="paperSize" id="paperSize" type="radio" value="811" class="radioBtns" >8.5''/11''&nbsp; <%-- ONCLICK="assignPaper('811')" --%>
 		</td>      
 		 		
        <td width="5%" class="optionTitleBoldRight"> 		
			<input name="printKitLabels" id="printKitLabels" type="checkbox" value="Yes" class="radioBtns" >
 		</td>      
 		<td width="20%" class="optionTitleBoldLeft">
  			<fmt:message key="label.skipkitcaseqtylabels"/>
 		</td> 		
 	  </tr>  
		
 	  <tr class="alignLeft">      
        <td width="20%" class="optionTitleLeft"> 		
			<input name="search" id="search" type="submit" value="<fmt:message key="label.search"/>" class="inputBtns" onmouseover="this.className='inputBtns inputBtnsOver'" onmouseout="this.className='inputBtns'">
 		</td>      
        <td width="20%" class="optionTitleLeft"> 		
			<input name="process" id="process" type="button" value="<fmt:message key="label.process"/>" class="inputBtns" onmouseover="this.className='inputBtns inputBtnsOver'" onmouseout="this.className='inputBtns'">
 		</td>      
        <td width="20%" class="optionTitleLeft"> 		
			<input name="undotap" id="undotap" type="button" value="<fmt:message key="label.undotap"/>" class="inputBtns" onmouseover="this.className='inputBtns inputBtnsOver'" onmouseout="this.className='inputBtns'">
 		</td>      
        <td width="15%" class="optionTitleLeft" colspan="4"> 		
			<input name="forceRepackaging" id="forceRepackaging" type="button" value="<fmt:message key="label.forcerepackaging"/>" class="inputBtns" onmouseover="this.className='inputBtns inputBtnsOver'" onmouseout="this.className='inputBtns'">
 		</td>      
 	  </tr>  
 	  
    </table>

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

</div> <!-- close of contentArea -->

<!-- Hidden element start -->
<div id="hiddenElements" style="display: none;">

</div>
<!-- Hidden elements end -->

</div> <!-- close of interface -->

<!--Uncomment for production-->
<%--</tcmis:form>--%>
</body>
</html:html>