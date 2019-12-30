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
<%-- this version based on template.jsp --%>

<head>
<meta http-equiv="content-type" content="text/html; charset=UTF-8">
<meta http-equiv="expires" content="-1">
<link rel="shortcut icon" href="https://www.tcmis.com/images/buttons/tcmIS.ico"></link>

<%--Use this tag to get the correct CSS class.
This looks at what the user's preffered font size and which application he is viewing to set the correct CSS. --%>
<tcmis:fontSizeCss />
<%-- CSS for YUI --%>
<%--
<link rel="stylesheet" type="text/css" href="/yui/build/container/assets/container.css" />
--%>
<%-- Add any other stylesheets you need for the page here --%>

<%--
<link rel="stylesheet" type="text/css" href="/css/dhtmlXGridHaas.css"></link>
--%>
<%@ include file="/common/locale.jsp" %>
    
<script type="text/javascript" src="/js/common/formchek.js"></script>
<script type="text/javascript" src="/js/common/commonutil.js"></script>
<%-- This handles which key press events are disabeled on this page --%>
<script type="text/javascript" src="/js/common/disableKeys.js"></script>

<%-- This handles the menu style and what happens to the right click on the whole page --%>
<script type="text/javascript" src="/js/menu/milonic_src.js"></script>
<script type="text/javascript" src="/js/menu/mmenudom.js"></script>
<script type="text/javascript" src="/js/menu/mainmenudata.js"></script>
<script type="text/javascript" src="/js/menu/contextmenu.js"></script>

<script type="text/javascript" src="/js/catalog/ordertracking.js"></script>

<c:set var="lineItem" value='${param.lineItem}'/>
<title>
<c:choose>
  <c:when test="${!empty lineItem}" >
    <fmt:message key="mrallocationreport.label.mrlinetitle"/>
  </c:when>
  <c:otherwise>
   <fmt:message key="mrallocationreport.label.title"/>
  </c:otherwise>
</c:choose>
</title>

</head>

<c:set var="doexcelpopup" value='${doexcelpopup}'/>
<c:choose>
  <c:when test="${empty doexcelpopup || doexcelpopup == null}" >
    <body bgcolor="#FFFFFF" text="#000000">
  </c:when>
  <c:otherwise>
   <body bgcolor="#FFFFFF" text="#000000" onLoad="doexcelpopup()">
  </c:otherwise>
</c:choose>

<script language="JavaScript" type="text/javascript">

<%-- add all the javascript messages here, this for internationalization of client side javascript messages --%>
var messagesData = new Array();
messagesData={alert:"<fmt:message key="label.alert"/>",and:"<fmt:message key="label.and"/>",
submitOnlyOnce:"<fmt:message key="label.submitOnlyOnce"/>"};
</script>
</head>

<body bgcolor="#ffffff">

 <div id="transitPage" class="optionTitleBoldCenter" style="display: none;">
  <br><br><br><fmt:message key="label.pleasewait"/>
  <br><br><br><img src="/images/rel_interstitial_loading.gif" align="middle">
 </div>
 <div class="interface" id="mainPage">

<%-- Top Navigation- we will not display this section after sometime, when we move to the new menu structure --%>
<div class="topNavigation" id="topNavigation">
<table width="100%" border="0" cellpadding="0" cellspacing="0">
<tr>
<td width="200">
<img src="/images/tcmtitlegif.gif" align="left">
</td>
<td>
<c:choose>
  <c:when test="${!empty lineItem}" >
    <img src="/images/mrlineallocationreport.gif" border=0 align="right">
  </c:when>
  <c:otherwise>
   <img src="/images/mrallocationreport.gif" border=0 align="right">
  </c:otherwise>
</c:choose>
</td>
</tr>
</table>

<table width="100%" border="0" cellpadding="0" cellspacing="0">
<tr><td width="70%" class="headingl">
<c:choose>
  <c:when test="${!empty lineItem}" >
    <fmt:message key="mrallocationreport.label.mrlinetitle"/>
  </c:when>
  <c:otherwise>
   <fmt:message key="mrallocationreport.label.title"/>
  </c:otherwise>
</c:choose>
</td>
<td width="30%" class="headingr">
</td>
</tr>
</table>
</div>

<div class="contentArea">

<%-- Search Option Begins --%>
<table id="searchMaskTable" width="800" border="0" cellpadding="0" cellspacing="0">
<tr><td>
<div class="roundcont filterContainer">
 <div class="roundright">
   <div class="roundtop">
     <div class="roundtopright"> <img src="/images/rndBoxes/borderTL_filter.gif" alt="" width="15" height="10" class="corner_filter" style="display: none" /></div>
   </div>
   <div class="roundContent">
    <%-- Insert all the search option within this div --%>

	<%-- START OF CONTROL-SECTION TABLE derived from old-mrar --%>

<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableSearch">

<tr class="optionTitleBoldCenter">
<td width="5%" class="optionTitleBoldRight">
<fmt:message key="label.facility"/>:
</td>

<td width="10%" class="optionTitleLeft">
<c:out value='${orderTrackDetailHeaderViewBean.facilityId}'/>
</td>

<td width="10%" class="optionTitleBoldRight">
<fmt:message key="label.mrnumber"/>:
</td>

<td width="10%" class="optionTitleLeft">
<c:out value='${orderTrackDetailHeaderViewBean.prNumber}'/>
</td>

<td width="5%" class="optionTitleBoldRight">
<fmt:message key="label.submitdate"/>:
</td>

<td width="10%" class="optionTitleLeft">
<fmt:formatDate var="formattedsubmittedDate" value="${orderTrackDetailHeaderViewBean.submittedDate}" pattern="${dateTimeFormatPattern}"/>
<c:out value='${formattedsubmittedDate}'/>
</td>

</tr>
<tr class="optionTitleBoldCenter">

<td width="5%" class="optionTitleBoldRight">
<fmt:message key="label.requestor"/>:
</td>

<td width="10%" class="optionTitleLeft">
<c:out value='${orderTrackDetailHeaderViewBean.requestorName}'/>
</td>

<td width="10%" class="optionTitleBoldRight">
<fmt:message key="mrallocationreport.label.financeapprover"/>:
</td>

<td width="10%" class="optionTitleLeft">
<c:out value='${orderTrackDetailHeaderViewBean.financeApproverName}'/>
</td>

<td width="5%" class="optionTitleBoldRight">
<fmt:message key="mrallocationreport.label.releaser"/>:
</td>

<td width="10%" class="optionTitleLeft">
<c:out value='${orderTrackDetailHeaderViewBean.releaserName}'/>
</td>

</tr>

<tr class="optionTitleBoldCenter">
<tcmis:form action="/mrallocationreport.do" onsubmit="return submitOnlyOnce();">
<td colspan="3" width="10%" class="optionTitleLeft">
<html:submit property="buttonCreateExcel" styleId="buttonCreateExcel" styleClass="inputBtns" onmouseover="this.className='inputBtns inputBtnsOver'" onmouseout="this.className='inputBtns'" >
     <fmt:message key="label.createexcelfile"/>
</html:submit>

<input type="hidden" name="mrNumber" id="mrNumber" value="<c:out value="${param.mrNumber}"/>" >
<input type="hidden" name="lineItem" id="lineItem" value="<c:out value="${param.lineItem}"/>" >
</td>

<td colspan="3" width="10%" class="optionTitleLeft">
<html:button property="buttonCreateExcel" styleId="buttonCreateExcel" styleClass="inputBtns" onmouseover="this.className='inputBtns inputBtnsOver'" onmouseout="this.className='inputBtns'"
		onclick= "return closeWindow()">
     <fmt:message key="label.close"/>
</html:button>
</td>
</tcmis:form>
</tr>
</table>
						<%-- END OF CONTROL-SECTION TABLE from old-mrar %>

   <%-- End search options --%>
   </div>
   <div class="roundbottom">
     <div class="roundbottomright"> <img src="/images/rndBoxes/borderBL.gif" alt="" width="15" height="15" class="corner" style="display: none" /> </div>
   </div>
 </div>
</div>
</td></tr>
</table>
<%-- Search Option Ends --%>

<div class="spacerY">&nbsp;</div>

<%-- Error Messages Begins --%>
<div id="errorMessagesArea" class="errorMessages">
<html:errors/>
</div>
<%-- Error Messages Ends --%>
<%-- * * * * * * RESULTS SECTION * * * * * *  --%>

<c:if test="${pkgOrderTrackWebPrOrderTrackDetailBeanCollection != null}" >
<%-- Search results start --%>
<%-- If you want your results section to span only 50% set this to 50% and your main table will be 100% --%>
<table id="resultsMaskTable" width="100%" border="0" cellpadding="0" cellspacing="0">
<tr><td>
<div class="roundcont contentContainer">
<div class="roundright">
  <div class="roundtop">
    <div class="roundtopright"> <img src="/images/rndBoxes/borderTL.gif" alt="" width="15" height="15" class="corner" style="display: none" /></div>
  </div>
  <div class="roundContent">
    <%-- TEMPLATE EXAMPLE CODE: <div class="boxhead"> <a href="#" onclick="doSomeThing(); return false;"><fmt:message key="label.createexcelfile"/></a></div> --%>
    <div class="dataContent">
    <table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableResults">
    <c:set var="colorClass" value=''/>
    <c:set var="dataCount" value='${0}'/>
												<%-- * * * * * * START OF RESULTS FOR-EACH BLOCK * * * * * * --%>

<%-- START of OUTERMOST FOR-EACH --%>

<c:forEach var="pkgOrderTrackWebPrOrderTrackDetailBean" items="${pkgOrderTrackWebPrOrderTrackDetailBeanCollection}" varStatus="status">
<c:set var="dataCount" value='${dataCount+1}'/>

<c:if test="${status.index % 10 == 0}">
<tr>
<th width="2%"><fmt:message key="mrallocationreport.label.mrline"/></th>
<th width="5%"><fmt:message key="label.customerpo"/></th>
<th width="5%"><fmt:message key="mrallocationreport.label.useapprover"/></th>
<th width="5%"><fmt:message key="mrallocationreport.label.releasedate"/></th>
<th width="5%"><fmt:message key="label.workarea"/></th>
<th width="5%"><fmt:message key="label.partnumber"/></th>
<th width="5%"><fmt:message key="label.type"/></th>
<th width="7%"><fmt:message key="label.packaging"/></th>
<th width="5%"><fmt:message key="label.deliverytype"/></th>
<th width="5%"><fmt:message key="label.needed"/></th>
<th width="5%"><fmt:message key="label.orderedqty"/></th>
<th width="5%"><fmt:message key="label.allocatedqty"/></th>
<th width="5%"><fmt:message key="label.status"/></th>
<th width="5%"><fmt:message key="mrallocationreport.label.ref"/></th>
<th width="5%"><fmt:message key="label.projecteddeliverydate"/></th>
<th width="5%"><fmt:message key="mrallocationreport.label.lot"/></th>
<th width="5%"><fmt:message key="label.expdate"/></th>
<th width="7%"><fmt:message key="label.notes"/></th>
</tr>
</c:if>

<%--<c:set var="currentLine"  value='${status.current.lineItem}'/>
<c:if test="${status.index > 0 && currentLine != previousLine}">--%>
<%--<c:if test="${dataCount > 1}">
<TR align="center">
<TD COLSPAN="17" HEIGHT="1" BGCOLOR="#000000"></TD>
</TR>
</c:if>
--%>
<%--<c:set var="previousLine"  value='${status.current.lineItem}'/>--%>
<c:set var="needDateCollection"  value='${status.current.needDateCollection}'/>
<bean:size id="listSize" name="needDateCollection"/>
<c:set var="mainRowSpan" value='${status.current.rowSpan}' />

<c:choose>
  <c:when test="${dataCount % 2 == 0}" >
   <c:set var="colorClass" value=''/>
  </c:when>
  <c:otherwise>
   <c:set var="colorClass" value='alt'/>
  </c:otherwise>
</c:choose>

<%--<TR align="center" CLASS="<c:out value="${colorClass}"/>">--%>

<tr class="<c:out value="${colorClass}"/>" id="rowId<c:out value="${status.index}"/>" onmouseup="catchdetailsevent('<c:out value="${status.index}"/>')">

<input type="hidden" name="colorClass<c:out value="${status.index}"/>" id="colorClass<c:out value="${status.index}"/>" value="<c:out value="${colorClass}"/>" >
<input type="hidden" name="childRowsSize<c:out value="${status.index}"/>" id="childRowsSize<c:out value="${status.index}"/>" value="<c:out value="${listSize}"/>" >

  <td width="2%" rowspan="<c:out value="${mainRowSpan}"/>"><c:out value="${status.current.lineItem}"/></td>
  <td width="5%" rowspan="<c:out value="${mainRowSpan}"/>"><c:out value="${status.current.poNumber}"/></td>
  <td width="5%" rowspan="<c:out value="${mainRowSpan}"/>"><c:out value="${status.current.useApproverName}"/></td>
  <fmt:formatDate var="formattedReleaseDate" value="${status.current.releaseDate}" pattern="${dateTimeFormatPattern}"/>
  <td width="5%" rowspan="<c:out value="${mainRowSpan}"/>"><c:out value="${formattedReleaseDate}"/></td>
  <td width="5%" rowspan="<c:out value="${mainRowSpan}"/>"><c:out value="${status.current.applicationDesc}"/></td>
  <td width="5%" rowspan="<c:out value="${mainRowSpan}"/>"><c:out value="${status.current.facPartNo}"/></td>
  <td width="5%" rowspan="<c:out value="${mainRowSpan}"/>"><c:out value="${status.current.itemType}"/></td>
  <td width="7%" rowspan="<c:out value="${mainRowSpan}"/>"><c:out value="${status.current.packaging}"/></td>
  <td width="5%" rowspan="<c:out value="${mainRowSpan}"/>"><c:out value="${status.current.deliveryType}"/></td>

<%-- START of MIDDLE FOR-EACH --%>
 <c:forEach var="pkgOrderTrackWebNeededBean" items="${needDateCollection}" varStatus="status1">
  <c:if test="${status1.index > 0 && listSize > 1 }">
   <tr class="<c:out value="${colorClass}"/>" id="childRowId<c:out value="${status.index}"/><c:out value="${status1.index}"/>" onmouseup="catchdetailsevent('<c:out value="${status.index}"/>')">
  </c:if>

   <c:set var="allocQtyCollection"  value='${status1.current.allocationCollection}'/>
   <bean:size id="allocationSize" name="allocQtyCollection"/>
   <input type="hidden" name="grandChildRowsSize<c:out value="${status.index}"/><c:out value="${status1.index}"/>"
   						  id="grandChildRowsSize<c:out value="${status.index}"/><c:out value="${status1.index}"/>"
   			value="<c:out value="${allocationSize}"/>" >

  <td width="5%" rowspan="<c:out value="${allocationSize}"/>"><c:out value="${status1.current.requiredDatetime}"/></td>
  <td width="5%" rowspan="<c:out value="${allocationSize}"/>"><c:out value="${status1.current.orderQuantity}"/></td>

<%-- START of INNERMOST FOR-EACH --%>

    <c:forEach var="PkgOrderTrackWebAllocationBean" items="${allocQtyCollection}" varStatus="status2">
    <c:if test="${status2.index > 0 && allocationSize > 1 }">
     <tr class="<c:out value="${colorClass}"/>" id="grnadChildRowId<c:out value="${status.index}"/><c:out value="${status1.index}"/><c:out value="${status2.index}"/>" onmouseup="catchdetailsevent('<c:out value="${status.index}"/>')">
    </c:if>

     <td width="5%"><c:out value="${status2.current.allocatedQuantity}"/></td>
     <td width="5%"><c:out value="${status2.current.status}"/></td>
     <td width="5%"><c:out value="${status2.current.ref}"/></td>
     <td width="5%"><c:out value="${status2.current.allocationReferenceDate}"/></td>
     <td width="5%"><c:out value="${status2.current.mfgLot}"/></td>
      <fmt:formatDate var="formattedexpireDate" value="${status2.current.expireDate}" pattern="${dateFormatPattern}"/>
     <td width="5%"><c:out value="${formattedexpireDate}"/></td>

       <c:set var="notes" value='${status2.current.notes}'/>
        <c:choose>
        <c:when test="${empty notes || notes == ' '}" >
        <td width="7%" id="lineNotesTd<c:out value="${status.index}"/>">&nbsp;
        </c:when>
        <c:otherwise>
          <td width="7%" id="lineNotesTd<c:out value="${status.index}"/>" onMouseOver=style.cursor="hand" onmouseup="showLineNotes('<c:out value="${status.index}"/><c:out value="${status1.index}"/><c:out value="${status2.index}"/>')">
          <span id="lineNoteslinkDiv<c:out value="${status.index}"/><c:out value="${status1.index}"/><c:out value="${status2.index}"/>">
          <div id="lineNoteslink<c:out value="${status.index}"/><c:out value="${status1.index}"/><c:out value="${status2.index}"/>">+</div>
          <div id="lineNotes<c:out value="${status.index}"/><c:out value="${status1.index}"/><c:out value="${status2.index}"/>" class="displaynone" onMouseOver=style.cursor="hand">
            <c:out value="${status2.current.notes}"/>
          </div>
          </span>
          <input type="hidden" name="notesVisible<c:out value="${status.index}"/><c:out value="${status1.index}"/><c:out value="${status2.index}"/>"
          						 id="notesVisible<c:out value="${status.index}"/><c:out value="${status1.index}"/><c:out value="${status2.index}"/>"
          						   value="No" >
        </c:otherwise>
       </c:choose>
     </td>

     <c:if test="${status2.index > 0 || allocationSize ==1 }">
      </tr>
     </c:if>
   </c:forEach>	<%-- END of INNERMOST FOR-EACH --%>

  <c:if test="${status1.index > 0 || listSize ==1 }">
   </tr>
  </c:if>
 </c:forEach>	<%-- END of MIDDLE FOR-EACH --%>


</c:forEach>	<%-- END of OUTERMOST FOR-EACH --%>

			<%-- * * * * * * END OF RESULTS FOR-EACH BLOCK * * * * * * --%>

   </table>
									<%-- * * * * * * WRAP-UP of SEARCH RESULTS SECTION * * * * * *  --%>


   <%-- If the collection is empty say no data found --%>
	<c:if test="${dataCount == 0}">

    <table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableNoData">
     <tr>
      <td width="100%">
       <fmt:message key="main.nodatafound"/>
      </td>
      <%-- <td width="100%"> HAHAHA </td> --%>

     </tr>
    </table>
   </c:if>

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

</c:if>								<%-- * * * * * * END OF SEARCH ESULTS SECTION * * * * * *  --%>

<%-- Hidden element start --%>
 <div id="hiddenElements" style="display: none;"></div>
<%-- Hidden elements end --%>

</div> <%-- close of contentArea --%>

<%-- Footer message start --%>
 <div class="messageBar">&nbsp;</div>
<%-- Footer message end --%>

</div> <%-- close of interface --%>

</body>
</html:html>