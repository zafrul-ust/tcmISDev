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
<script type="text/javascript" src="/js/msds.js"></script>

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
<fmt:message key="label.msds"/>
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
<%--<tcmis:form action="/msds.do" onsubmit="return submitFrameOnlyOnce();" target="resultFrame">--%>

<div class="interface" id="searchMainPage"> <!-- Start of interface-->

<div class="contentArea"> <!-- Start of contentArea-->

<!-- Search Option Begins -->
<table id="searchMaskTable" width="1000" border="1" cellpadding="0" cellspacing="0">
<tr><td>
<div class="roundcont filterContainer">
 <div class="roundright">
   <div class="roundtop">
     <div class="roundtopright"> <img src="/images/rndBoxes/borderTL_filter.gif" alt="" width="15" height="10" class="corner_filter" style="display: none" /></div>
   </div>
   <div class="roundContent">
    <!-- Insert all the search option within this div -->
    <!-- Search Option Table Start -->
    
    <table width="100%" border="1" cellpadding="0" cellspacing="0" class="tableSearch">
 	  <tr >    
 		<td width="10%" class="optionTitleBoldRight">
  			<fmt:message key="label.facility"/>
 		</td>
		<td width="20%" class="optionTitleLeft">      
 			<select name="facility" id="facility" class="selectBox" >  
				<option  value="All">All Catalogs</option>
				<option  value="APD%20Shop">APD Shop</option>
				<option  value="Airport%20Plaza">Airport Plaza</option>
				<option  value="Andover">Andover</option>
				<option  value="Bedford">Bedford</option>
				<option  value="Boulder">Boulder</option>
 			</select>
 		</td>    

 		<td width="10%" class="optionTitleBoldRight">
  			<fmt:message key="label.sortby"/>:
 		</td>
		<td width="10%" class="optionTitleLeft">      
 			<html:select property="sortBy" styleId="sortBy" styleClass="selectBox">
				<html:option value="0" selected="selected" ><fmt:message key="label.material"/></html:option>
				<html:option value="1"><fmt:message key="label.partnumber"/></html:option>
				<html:option value="2"><fmt:message key="label.tradename"/></html:option>
				<html:option value="3"><fmt:message key="label.manufacturer"/></html:option>
				<html:option value="4"><fmt:message key="label.catalog"/></html:option>
  			</html:select>		 			
 		</td>    

 		<td width="15%" class="optionTitleBoldRight">
  			<fmt:message key="label.searchargument"/>
 		</td>
		<td width="35%" class="optionTitleLeft" colspan="2">       		
   			<input class="inputBox" type="text" name="searchArgument" id="searchArgument" size="55">
 		</td>
 	  </tr>  
    </table>    

    <table>    
 	  <tr >       	  
        <td width="5%" class="optionTitleBoldRight"> 		
			<input name="searchCatalog" id="searchCatalog" type="checkbox" value="Yes" class="radioBtns" >
 		</td>     
 		<td width="25%" class="optionTitleBoldLeft">
  			<fmt:message key="label.catalog"/>
 		</td>
 	  
        <td width="5%" class="optionTitleBoldRight"> 		
			<input name="searchNonCatalog" id="searchNonCatalog" type="checkbox" value="Yes" class="radioBtns" >
 		</td>     
 		<td width="25%" class="optionTitleBoldLeft">
  			<fmt:message key="label.noncatalog"/>
 		</td>
 		
        <td width="5%" class="optionTitleBoldRight"> 		
			<input name="searchFullDatabase" id="searchFullDatabase" type="checkbox" value="Yes" class="radioBtns" >
 		</td>     
 		<td width="25%" class="optionTitleBoldLeft">
  			<fmt:message key="label.fulldatabase"/>
 		</td>
 	  </tr>      
 	  
 	  <tr class="alignLeft">      
        <td width="10%" class="optionTitleLeft" colspan="4"> 		
			<input name="SearchButton" id="SearchButton" type="submit" value="<fmt:message key="label.search"/>" class="inputBtns" onmouseover="this.className='inputBtns inputBtnsOver'" onmouseout="this.className='inputBtns'">
 		</td>       
 	  </tr>  
 	  
    </table>    

    <table>    
 	  <tr >       	  
        <td ><fmt:message key="label.instructions"/>:</td></tr>
        <tr ><td ><fmt:message key="label.searchargument"/>:<fmt:message key="label.msdssearchinstructions01"/></td></tr>      
        <tr ><td ><fmt:message key="label.facility"/>:<fmt:message key="label.msdssearchinstructions02"/></td></tr>      
        <tr ><td ><fmt:message key="label.sortby"/>:<fmt:message key="label.msdssearchinstructions03"/></td></tr>      
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