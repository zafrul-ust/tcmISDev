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
<script type="text/javascript" src="/js/reconciliation.js"></script>

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
<fmt:message key="label.reconciliation"/>
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
<%--<tcmis:form action="/reconciliation.do" onsubmit="return submitFrameOnlyOnce();" target="resultFrame">--%>

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
    <table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableSearch">
      <tr>
 		<td width="10%" class="optionTitleBoldRight">
  			<fmt:message key="label.hub"/>:
 		</td>
		<td width="10%" class="optionTitleLeft">      
 			<select name="hubId" id="hubId" class="selectBox">  <%-- onChange="RefreshPage(this)" --%>
				<option value="2122">DANA Hub</option>
				<option value="2119">Silicon Valley Hub</option>
 			</select>
 		</td>    
 		<td width="10%" class="optionTitleBoldRight">
  			<fmt:message key="label.countdate"/>:
 		</td>
		<td width="10%" class="optionTitleLeft" colspan="6">      
 			<select name="countDate" id="countDate" class="selectBox">  <%-- OnChange="hubchanged(document.receiving.HubName)" --%>
				<option selected value="18 May 2003 16:51">18 May 2003 16:51</option>
				<option value="18 May 2003 16:50">18 May 2003 16:50</option>
				<option value="29 Apr 2003 06:58">29 Apr 2003 06:58</option>
				<option value="29 Apr 2003 06:41">29 Apr 2003 06:41</option>
				<option value="28 Apr 2003 12:53">28 Apr 2003 12:53</option>
 			</select>
 			
 		</td>    
 	  </tr>  
 	  
      <tr>
 		<td width="10%" class="optionTitleBoldRight">
  			<fmt:message key="label.sortby"/>:
 		</td>
		<td width="10%" class="optionTitleLeft">      
 			
 			<html:select property="sortBy" styleId="sortBy" styleClass="selectBox">
				<html:option selected="selected" value="1"><fmt:message key="label.bin"/></html:option>
				<html:option value="2"><fmt:message key="label.itemid"/></html:option>
  			</html:select>		
 			 			
 		</td>    

 		<td width="10%" class="optionTitleBoldRight">
  			<fmt:message key="label.show"/>:
 		</td>
		<td width="10%" class="optionTitleLeft">      
 			<html:select property="reportType" styleId="reportType" styleClass="selectBox"> 				
				<html:option selected="selected" value="All"><fmt:message key="label.all"/></html:option>
				<html:option  value="Items"><fmt:message key="label.itemstoreconcile"/></html:option>
				<html:option  value="Receipts"><fmt:message key="label.receiptstoreconcile"/></html:option>
				<html:option  value="Item"><fmt:message key="label.item"/></html:option>
				<html:option  value="80per"><fmt:message key="label.80pctinventoryvalue"/></html:option>
  			</html:select>		
 		</td>    

 		<td width="10%" class="optionTitleBoldRight">
  			<fmt:message key="label.search"/>:
 		</td>
		<td width="10%" class="optionTitleLeft">      
 			<html:select property="searchField" styleId="searchField" styleClass="selectBox"> 							
				<html:option  selected="selected" value="field1">field 1</html:option>
				<html:option  value="field2">field 2</html:option>			
  			</html:select>		
 		</td>    
		<td width="10%" class="optionTitleLeft">      
 			<html:select property="searchMode" styleId="searchMode" styleClass="selectBox"> 							 					
				<html:option  selected="selected" value="is"><fmt:message key="label.is"/></html:option>
				<html:option   value="contains"><fmt:message key="label.contains"/></html:option> 			
 			</html:select>
 		</td>    
 		<td width="5%" class="optionTitleBoldRight">
  			<fmt:message key="label.for"/>
 		</td>
		<td width="25%" class="optionTitleLeft" colspan="2">       		
   			<input class="inputBox" type="text" name="searchArgument" id="searchArgument" size="20">
 		</td>
 	  </tr>  
 	  
 	  <tr class="alignLeft">   
        <td width="10%" class="optionTitleLeft" > 		
			<input name="startNewCount" id="startNewCount" type="button" value="<fmt:message key="label.startnewcount"/>" class="inputBtns" onmouseover="this.className='inputBtns inputBtnsOver'" onmouseout="this.className='inputBtns'">
 		</td>      	     
        <td width="10%" class="optionTitleLeft"> 		
			<input name="search" id="search" type="submit" value="<fmt:message key="label.search"/>" class="inputBtns" onmouseover="this.className='inputBtns inputBtnsOver'" onmouseout="this.className='inputBtns'">
 		</td>      
        <td width="10%" class="optionTitleLeft"> 		
			<input name="createPdf" id="createPdf" type="button" value="<fmt:message key="label.createpdf"/>" class="inputBtns" onmouseover="this.className='inputBtns inputBtnsOver'" onmouseout="this.className='inputBtns'">
 		</td>      
        <td width="10%" class="optionTitleLeft"> 		
			<input name="createXls" id="createXls" type="button" value="<fmt:message key="label.createxls"/>" class="inputBtns" onmouseover="this.className='inputBtns inputBtnsOver'" onmouseout="this.className='inputBtns'">
 		</td>      
        <td width="60%" class="optionTitleLeft" colspan="5"> 		
			<input name="update" id="update" type="button" value="<fmt:message key="label.update"/>" class="inputBtns" onmouseover="this.className='inputBtns inputBtnsOver'" onmouseout="this.className='inputBtns'">
 		</td>      
 	  </tr>  
 	   	   
    </table> 		
    
    <%-- Search Option Table end --%>
    
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