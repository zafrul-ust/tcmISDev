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
<script type="text/javascript" src="/js/accountspayable.js"></script>

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
<fmt:message key="label.accountspayable"/>
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
<%--<tcmis:form action="/accountspayable.do" onsubmit="return submitFrameOnlyOnce();" target="resultFrame">--%>

<div class="interface" id="searchMainPage"> <!-- Start of interface-->

<div class="contentArea"> <!-- Start of contentArea-->

<!-- Search Option Begins -->
<table id="searchMaskTable" width="1200" border="1" cellpadding="0" cellspacing="0">
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
      <tr>
 		<td width="8%" class="optionTitleBoldRight">
  			<fmt:message key="label.po"/>:
 		</td>
 		
		<td width="20%" class="optionTitleLeft">      
			<input class="INVALIDTEXT" type="text" name="po" id="po" value="770430" >  <%-- onChange="invalidatePo()" > --%>
			<input name="searchForPO" id="searchForPO" type="button" value="..." class="lookupBtn" onmouseover="this.className='lookupBtn lookupBtnOver'" onmouseout="this.className='lookupBtn'"> <%-- onclick="getPO(this.form,0)" --%>

 		</td>    
 		<td width="8%" class="optionTitleBoldRight">
			<a href="javascript:showinvoicesummary()"><fmt:message key="label.invoice"/>:</a>
 		</td>    
 
		<td width="48%" class="optionTitleLeft" colspan="2">      
 			<select name="invoices" id="invoices" class="selectBox">		<%-- ONCHANGE="getInvoiceData()" --%>
				<option   value="06080335">06080335</option>
				<option   value="086-488900-2">086-488900-2</option>

 			</select>

		<%-- <td width="20%" class="optionTitleLeft" colspan="2">       		
   			<input class="inputBox" type="text" name="searchArgument" id="searchArgument" size="20">
 		</td> --%>

			<input name="newInvoice" id="newInvoice" type="button" value="<fmt:message key="label.new"/>" class="inputBtns" onmouseover="this.className='inputBtns inputBtnsOver'" onmouseout="this.className='inputBtns'">

			<input name="editInvoice" id="editInvoice" type="button" value="<fmt:message key="label.edit"/>" class="inputBtns" onmouseover="this.className='inputBtns inputBtnsOver'" onmouseout="this.className='inputBtns'">
 		</td>      
 		<td width="8%" ></td>
 		<td width="8%" class="optionTitleBoldRight">
  			<fmt:message key="label.datesentforpayment"/>:
 		</td>
       	<td width="8%" ><c:out value="${status.current.dateSentForPayment}"/></td>
 		
 	  </tr>  

      <tr>
 		<td width="8%" class="optionTitleBoldRight">
  			<fmt:message key="label.terms"/>:
 		</td>
 		<td width="20%" id="poneterms" class="optionTitleLeft"><c:out value="${status.current.poNetTerms}"/></td>
 		
 		<td width="8%" class="optionTitleBoldRight">
  			<fmt:message key="label.consigned"/>: 
 		</td>
 		<td width="20%" id="consignedpo" class="optionTitleLeft"><c:out value="${status.current.consignedPO}"/></td>
 		
 		<td width="8%" class="optionTitleBoldRight">
  			<fmt:message key="label.homecompany"/>:
 		</td>
 		<td width="20%" id="homecompany" class="optionTitleLeft"><c:out value="${status.current.homeCompany}"/></td>
 		
 		<td width="8%" class="optionTitleBoldRight">
  			<fmt:message key="label.currency"/>:
 		</td>
      	<td width="8%" class="optionTitleLeft"><c:out value="${status.current.currency}"/></td>		
 		<%--
		<td width="10%" class="optionTitleLeft">      
			<input class="invisibleClass" type="text" name="currency" id="currency" value="" readonly>  
 		</td> --%>   
 	  </tr>  
 	  
      <tr>
 		<td width="8%" class="optionTitleBoldRight">
  			<fmt:message key="label.supplier"/>:
 		</td>
 		<td width="20%" name="supplierline1" id="supplierline1" class="alignLeft"><c:out value="${status.current.supplierLine1}"/></td>
 		
 		<td width="8%" class="optionTitleBoldRight">
  			<fmt:message key="label.remitto"/>:
 		</td>
 		<td width="20%" name="shiptoline1" id="shiptoline1" class="alignLeft"><c:out value="${status.current.shipToLine1}"/></td>
 		
 		<td width="8%" class="optionTitleBoldRight">
  			<fmt:message key="label.invoicedate"/>:
 		</td>
		<td width="20%" class="alignLeft"><c:out value="${status.current.invoiceDate}"/>      
			<%-- <input class="invisibleClass" type="text" name="invoicedate" id="invoicedate" value="" readonly>  --%>
 		</td>    
 		
 		<td width="8%" class="optionTitleBoldRight">
  			<fmt:message key="label.invamt"/>:
 		</td>
      	<td width="8%" ><c:out value="${status.current.invAmt}"/></td>		
 		<%--
		<td width="10%" class="alignLeft">      
			<input class="invisibleClass" type="text" name="invamount" id="invamount" value="" readonly>  
 		</td>    --%>

 	  </tr>  
 	  
      <tr>
       	<td width="8%" ></td>
      
      	<td width="20%"><c:out value="${status.current.supplierLine2}"/></td>
        <td width="8%" class="optionTitleCenter"> 		
			<input name="Submit" id="Submit" type="submit" value="<fmt:message key="label.search"/>" class="inputBtns" onmouseover="this.className='inputBtns inputBtnsOver'" onmouseout="this.className='inputBtns'">
 		</td>      
      <td width="20%"><c:out value="${status.current.shipToLine2}"/></td>
 		<td width="8%" class="optionTitleBoldRight">
  			<fmt:message key="label.datereceived"/>:
 		</td>
		<td width="20%" class="alignLeft"><c:out value="${status.current.dateReceived}"/>      
			<%-- <input class="invisibleClass" type="text" name="invdatereceived" id="invdatereceived" value="" readonly>  --%>
 		</td>    
 		
 		<td width="8%" class="optionTitleBoldRight">
  			<fmt:message key="label.refused"/>:
 		</td>
      	<td width="8%" ><c:out value="${status.current.refusedAmt}"/></td>		
 		<%--
		<td width="10%" class="alignLeft">      
			<input class="invisibleClass" type="text" name="refused" id="refused" value="" readonly>  
 		</td>    --%>
 		
 	  </tr>  

      <tr>
       	<td width="8%" ></td>
      
       	<td width="20%" ><c:out value="${status.current.supplierLine3}"/></td>
       	<td width="8%" ></td>
       	
       	<td width="20%" ><c:out value="${status.current.shipToLine3}"/></td>
      	
 		<td width="8%" class="optionTitleBoldRight">
  			<fmt:message key="label.refinvoice"/>:
 		</td>
       	<td width="20%" ><c:out value="${status.current.refInvoice}"/></td>
 		
 		<td width="8%" class="optionTitleBoldRight">
  			<fmt:message key="label.netamt"/>:
 		</td>
      	<td width="8%" ><c:out value="${status.current.netAmt}"/></td>		
 	  </tr>  
 	  

      <tr>
       	<td width="56%" colspan="4"></td>
      
 		<td width="8%" class="optionTitleBoldRight">
  			<fmt:message key="label.status"/>:
 		</td>
        <td width="20%" ><c:out value="${status.current.status}"/></td>
 		
 		<td width="8%" class="optionTitleBoldRight">
  			<fmt:message key="label.invterms"/>:
 		</td>
        <td width="8%" ><c:out value="${status.current.invTerms}"/></td>
 		
 	  </tr>  

      <tr>
 		<td width="8%" class="optionTitleBoldRight">
  			<fmt:message key="label.phone"/>:
 		</td>
        <td width="20%" ><c:out value="${status.current.phone}"/></td>
        <td width="28%" colspan="2"></td>
 		
 		<td width="8%" class="optionTitleBoldRight">
  			<fmt:message key="label.approvedby"/>:
 		</td>
        <td width="20%" ><c:out value="${status.current.approvedby}"/></td>
 		<td width="8%" class="optionTitleBoldRight">
  			<fmt:message key="label.qcedby"/>:
 		</td>
        <td width="8%" ><c:out value="${status.current.qcedBy}"/></td>
 		
 	  </tr>  

      <tr>
 		<td width="8%" class="optionTitleBoldRight">
  			<fmt:message key="label.ordertakenby"/>:
 		</td>
        <td width="20%" ><c:out value="${status.current.orderTakenBy}"/><br><c:out value="${status.current.orderTakenByPhone}"/></td>
 		
 		<td width="8%" class="optionTitleBoldRight">
  			<fmt:message key="label.comments"/>:
 		</td>
       	<td width="64%" colspan="5"><c:out value="${status.current.comments}"/></td>
 		
 	  </tr>  


      <tr>
 		<td width="8%" class="optionTitleBoldRight">
  			<fmt:message key="label.buyer"/>:
 		</td>
         <td width="20%" ><c:out value="${status.current.buyer}"/>Watson, Jen<br><c:out value="${status.current.buyerPhone}"/></td>
 		
 		<td width="8%" class="optionTitleBoldRight">
  			<fmt:message key="label.invoiceurl"/>:
 		</td>
       	<td width="64%" colspan="5"><c:out value="${status.current.invoiceUrl}"/></td>
 		
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