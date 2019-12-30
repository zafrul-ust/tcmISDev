<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
	"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>

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

<link rel="stylesheet" type="text/css" href="/yui/build/container/assets/container.css" />

<!-- Add any other stylesheets you need for the page here -->

<%--
<link rel="stylesheet" type="text/css" href="/css/dhtmlXGridHaas.css"></link>
--%>

<script type="text/javascript" src="/js/common/formchek.js"></script>
<script type="text/javascript" src="/js/common/commonutil.js"></script>
<!-- This handels which key press events are disabeled on this page -->
<script type="text/javascript" src="/js/common/disableKeys.js"></script>

<!-- This handels the menu style and what happens to the right click on the whole page -->
<script type="text/javascript" src="/js/menu/milonic_src.js"></script>
<script type="text/javascript" src="/js/menu/mmenudom.js"></script>
<script type="text/javascript" src="/js/menu/mainmenudata.js"></script>
<script type="text/javascript" src="/js/menu/contextmenu.js"></script>

<!-- For Calendar support -->

<script type="text/javascript" src="/js/calendar/newcalendar.js"></script>
<script type="text/javascript" src="/js/calendar/AnchorPosition.js"></script>
<script type="text/javascript" src="/js/calendar/PopupWindow.js"></script>


<!-- Add any other javascript you need for the page here -->

<script type="text/javascript" src="/clientscripts/calendar.js"></script>


<!-- These are for the Grid, uncomment if you are going to use the grid -->
<%--<script type="text/javascript" src="/js/dhtmlxGrid/dhtmlXCommon.js"></script>
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
<fmt:message key="label.editinvoice"/>
</title>

<script language="JavaScript" type="text/javascript">
<!--
//add all the javascript messages here, this for internationalization of client side javascript messages
var messagesData = new Array();
messagesData={alert:"<fmt:message key="label.alert"/>",and:"<fmt:message key="label.and"/>",
submitOnlyOnce:"<fmt:message key="label.submitOnlyOnce"/>"};
// -->
</script>
</head>

<body bgcolor="#ffffff">

<!--Uncomment for production-->
<%--<tcmis:form action="/editinvoice.do" onsubmit="return submitOnlyOnce();">--%>

<!-- Transit Page Begins -->
<div id="transitPage" class="optionTitleBoldCenter" style="display: none;">
 <br><br><br><fmt:message key="label.pleasewait"/>
 <br><br><br><img src="/images/rel_interstitial_loading.gif" align="middle">
</div>
<!-- Transit Page Ends -->

<div class="interface" id="mainPage"> <!-- start of interface-->

<div class="contentArea">  <!-- start of contentArea-->

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
    <!-- Search Option Table Begins -->
    
    <table width="100%" border="1" cellpadding="0" cellspacing="0" class="tableSearch">
      <tr>
 		<td width="20%" class="optionTitleBoldRight">
  			<fmt:message key="label.supplier"/>:
 		</td>
		<td width="40%" class="optionTitleLeft">      
 			<select name="supplierId" id="supplierId" class="selectBox">
    			<option value="10004750" >Central Transport</option>
    			<option value="78444" selected>International Products Corp</option> 		
 			</select>
 		</td>    
 		<td width="10%" class="optionTitleBoldRight">
  			<fmt:message key="label.currency"/>:
 		</td>
		<td width="30%" class="optionTitleLeft">      
 			<select name="currency" id="currency" class="selectBox">
    			<option value="ARS">ARS</option>
    			<option value="CAD">CAD</option> 		
    			<option value="CHF">CHF</option>
    			<tcmis:isCNServer><option selected value="CNY">CNY</option></tcmis:isCNServer>		
    			<option value="EUR">EUR</option> 
    			<tcmis:isCNServer indicator="false"><option selected value="USD">USD</option></tcmis:isCNServer>
 			</select>
 		</td>    
 	  </tr>  
 		 		
 	  <tr>  
 		<td width="20%" class="optionTitleBoldRight">
  			<fmt:message key="label.invoice"/>:
 		</td>
		<td width="40%" class="optionTitleLeft" >       		
   			<input class="inputBox" type="text" name="invoice" id="invoice" size="20" maxlength="30" value="<c:out value="${status.current.invoice}"/>">
 		</td>

 		<td width="10%" class="optionTitleBoldRight">
  			<fmt:message key="label.invoicedate"/>:
 		</td>
		<td width="30%" class="optionTitleLeft" >       		
   			<input class="inputBox" type="text" name="invoiceDate" id="invoiceDate" size="10" maxlength="10">
   			<a href="javascript: void(0);" id="invoicedatelink" onclick="return getCalendar(document.SupplierLike.invoicedate);">&diams;</a>
 		</td>
 	  </tr>  

 	  <tr>   		
 		<td width="20%" class="optionTitleBoldRight">
  			<fmt:message key="label.refinvoice"/>:
 		</td>
		<td width="40%" class="optionTitleLeft" >       		
   			<input class="inputBox" type="text" name="refInvoice" id="refInvoice" size="20" maxlength="30" value="<c:out value="${status.current.refInvoice}"/>">
 		</td> 	  
 	  
 		<td width="10%" class="optionTitleBoldRight">
  			<fmt:message key="label.datereceived"/>:
 		</td>
		<td width="30%" class="optionTitleLeft" >       		
   			<input class="inputBox" type="text" name="dateReceived" id="dateReceived" size="10" maxlength="10">
   			<a href="javascript: void(0);" id="invdatereceivedlink" onclick="return getCalendar(document.SupplierLike.invdatereceived);">&diams;</a>
 		</td>
 	  </tr>  

 	  <tr>   		
 		<td width="20%" class="optionTitleBoldRight">
  			<fmt:message key="label.remitto"/>:
 		</td>
 		<td width="40%" class="optionTitleLeft"><c:out value="${status.current.remitToLine1}"/></td>
 		
 		<td width="10%" class="optionTitleBoldRight">
  			<fmt:message key="label.invamt"/>:
 		</td>
 		
		<td width="30%" class="optionTitleLeft" >       		
   			<input class="inputBox" type="text" name="invoiceAmt" id="invoiceAmt" size="20" maxlength="30" value="<c:out value="${status.current.invoiceAmt}"/>">
 		</td> 	   		
 	  </tr>  

 	  <tr >      
        <td width="20%" class="optionTitleRight" > 		
			<input name="searchButton" id="searchButton" type="submit" value="<fmt:message key="label.search"/>" class="inputBtns" onmouseover="this.className='inputBtns inputBtnsOver'" onmouseout="this.className='inputBtns'">
 		</td>       
 		<td width="40%" class="optionTitleLeft"><c:out value="${status.current.remitToLine2}"/></td>
 		
 		<td width="10%" class="optionTitleBoldRight">
  			<fmt:message key="label.refused"/>:
 		</td>
		<td width="30%" class="optionTitleLeft" >       		
   			<input class="inputBox" type="text" name="refused" id="refused" size="20" maxlength="30" value="<c:out value="${status.current.refused}"/>">
 		</td> 	   		
 	  </tr>  

 	  <tr >      
        <td width="20%" class="optionTitleRight" ></td> 		
 		       
 		<td width="40%" class="optionTitleBoldRight"></td>
 		
 		<td width="10%" class="optionTitleBoldRight">
  			<fmt:message key="label.netamt"/>:
 		</td>
		<td width="30%" class="optionTitleLeft" ><c:out value="${status.current.netAmt}"/></td>       		 		
 	  </tr> 
 	   
 	  <tr >       	  
        <td width="20%" class="optionTitleRight" ></td> 		 		       
 		<td width="40%" class="optionTitleLeft"><c:out value="${status.current.remitToLine3}"/></td>

 		<td width="10%" class="optionTitleBoldRight">
  			<fmt:message key="label.terms"/>:
 		</td>
		<td width="30%" class="optionTitleLeft">      
 			<select name="terms" id="terms" class="selectBox">
 			</select>
 		</td>    
 	  </tr>  

    </table>

    <!-- Search Option Table Ends -->
   </div>
   <div class="roundbottom">
     <div class="roundbottomright"> <img src="/images/rndBoxes/borderBL.gif" alt="" width="15" height="15" class="corner" style="display: none" /> </div>
   </div>
 </div>
</div>
</td></tr>
</table>
<!-- Search Option Ends -->

<div class="spacerY">&nbsp;</div>

<!-- Error Messages Begins -->
<div id="errorMessagesArea" class="errorMessages">
<html:errors/>
</div>
<!-- Error Messages Ends -->

<!--Uncomment for production-->
<%--<c:if test="${editinvoiceViewBeanCollection != null}" >--%>

<!-- Search results start -->
<!-- If you want your results section to span only 50% set this to 50% and your main table will be 100% -->
<%--
<table id="resultsMaskTable" width="100%" border="0" cellpadding="0" cellspacing="0">
<tr><td>
<div class="roundcont contentContainer">
<div class="roundright">
  <div class="roundtop">
    <div class="roundtopright"> <img src="/images/rndBoxes/borderTL.gif" alt="" width="15" height="15" class="corner" style="display: none" /></div>
  </div>
  <div class="roundContent">
    <div class="boxhead"> <a href="#" onclick="doSomeThing(); return false;"><fmt:message key="label.createexcelfile"/></a></div>
    <div class="dataContent">
    <table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableResults">
    <c:set var="colorClass" value=''/>
    <c:set var="dataCount" value='${0}'/>

    <!--Uncomment for production-->
    <% --<c:forEach var="editinvoiceViewBean" items="${editinvoiceViewBeanCollection}" varStatus="status">-- %>

    <!--Uncomment for production-->
    <% --<c:if test="${status.index % 10 == 0}">-- %>
    <!-- Need to print the header every 10 rows-->
    <tr>
    <th width="5%"><fmt:message key="label.example1"/></th>
    <th width="5%"><fmt:message key="label.example2"/></th>
    </tr>
    <!--Uncomment for production-->
    <% --</c:if>-- %>

    <!--Uncomment for production-->
    <% --<c:choose>
     <c:when test="${status.index % 2 == 0}" >
      <c:set var="colorClass" value=''/>
     </c:when>
     <c:otherwise>
      <c:set var="colorClass" value='alt'/>
     </c:otherwise>
    </c:choose>-- %>

    <tr class="<c:out value="${colorClass}"/>">
     <td width="5%"><c:out value="${status.current.exampleColumn1}"/></td>
     <td width="5%"></td>
   </tr>

   <c:set var="dataCount" value='${dataCount+1}'/>
   <!--Uncomment for production-->
   <% --</c:forEach>-- %>
   </table>
   <!-- If the collection is empty say no data found -->

   <!--Uncomment for production-->
   <% --<c:if test="${empty editinvoiceViewBeanCollection}" >-- %>
    <table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableNoData">
     <tr>
      <td width="100%">
       <fmt:message key="main.nodatafound"/>
      </td>
     </tr>
    </table>
   <!--Uncomment for production-->
   <% --</c:if>-- %>

  </div>
  </div>
  <div class="roundbottom">
    <div class="roundbottomright"> <img src="/images/rndBoxes/borderBL.gif" alt="" width="15" height="15" class="corner" style="display: none" /> </div>
  </div>
</div>
</div>
</div>
</td></tr>
</table>

--%>
<!-- Search results end -->
<!--Uncomment for production-->
<%--</c:if>--%>

<!-- Hidden element start -->
 <div id="hiddenElements" style="display: none;"></div>
<!-- Hidden elements end -->

</div> <!-- close of contentArea -->

<!-- Footer message start -->
 <div class="messageBar">&nbsp;</div>
<!-- Footer message end -->

</div> <!-- close of interface -->

<%--</tcmis:form>--%>
</body>
</html:html>