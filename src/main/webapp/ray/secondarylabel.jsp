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
<meta http-equiv="content-type" content="text/html; charset=iso-8859-1">
<meta http-equiv="expires" content="-1">
<link rel="shortcut icon" href="https://www.tcmis.com/images/buttons/tcmIS.ico"></link>

<!--Use this tag to get the correct CSS class.
This looks at what the user's preffered font size and which application he is viewing to set the correct CSS. -->
<tcmis:fontSizeCss />
<%-- Add any other stylesheets you need for the page here --%>


<%--
<link rel="stylesheet" type="text/css" href="/css/dhtmlXGridHaas.css"></link>
<link rel="stylesheet" type="text/css" href="/yui/build/container/assets/container.css" />
--%>

<script SRC="/js/common/formchek.js" language="JavaScript"></script>
<script SRC="/js/common/commonutil.js" language="JavaScript"></script>
<script src="/js/ray/catalog.js" language="JavaScript"></script>

<%-- For Calendar support --%>
<%--
<script src="/js/calendar/newcalendar.js" language="JavaScript"></script>
<script src="/js/calendar/AnchorPosition.js" language="JavaScript"></script>
<script src="/js/calendar/PopupWindow.js" language="JavaScript"></script>
--%>
<!-- This handels the menu style and what happens to the right click on the whole page -->
<script type="text/javascript" src="/js/menu/milonic_src.js"></script>
<script type="text/javascript" src="/js/menu/mmenudom.js"></script>
<script type="text/javascript" src="/js/menu/mainmenudata.js"></script>
<script type="text/javascript" src="/js/menu/contextmenu.js"></script>

<%-- Add any other javascript you need for the page here --%>


<%-- These are for the Grid, uncomment if you are going to use the grid --%>
<%--<script src="/js/dhtmlxGrid/dhtmlXCommon.js"></script>
<script src="/js/dhtmlxGrid/dhtmlXGridHaas.js"></script>
<script src="/js/dhtmlxGrid/dhtmlXGridCell.js"></script>
<script src="/js/dhtmlxGrid/dhtmlXGrid_excell_mro.js"></script>
--%>

<%-- This is for the YUI, uncomment if you will use this --%>
<%--<script type="text/javascript" src="/yui/build/yahoo/yahoo.js" ></script>
<script type="text/javascript" src="/yui/build/event/event.js" ></script>
<script type="text/javascript" src="/yui/build/dom/dom.js" ></script>
<script type="text/javascript" src="/yui/build/animation/animation.js" ></script>
<script type="text/javascript" src="/yui/build/container/container.js"></script>
<script type="text/javascript" src="/js/common/waitDialog.js"></script>
<script type="text/javascript" src="/yui/build/dragdrop/dragdrop.js" ></script>--%>


<title>
<fmt:message key="label.printlabels"/>:
</title>

<script language="JavaScript" type="text/javascript">
<!--
//add all the javascript messages here, this for internationalization of client side javascript messages
var messagesData = new Array();
messagesData={alert:"<fmt:message key="label.alert"/>",pleaseselect:"<fmt:message key="label.pleaseselect"/>",and:"<fmt:message key="label.and"/>",
validquantity:"<fmt:message key="labels.label.validquantity"/>",submitOnlyOnce:"<fmt:message key="label.submitOnlyOnce"/>"};
// -->
</script>


<c:set var="buttonPrint" value='${param.buttonPrint}'/>
<c:set var="filePath" value='${sessionScope.filePath}'/>
<c:set var="doexcelpopup" value='${doexcelpopup}'/>
<c:choose>
  <c:when test="${!empty doexcelpopup && !empty buttonPrint}" >
    <meta http-equiv="Refresh" content="0; url=<c:out value="${filePath}" />">
    </head>
      <body bgcolor="#ffffff">
      <div class="optionTitleBoldCenter" style="display: none;">
      <img src="/images/tcmintro.gif"  border=1 align="middle"><br><br><br>
      Printing Labels<br><br>
      Please Wait ...<br>
      </div>
  </c:when>
  <c:otherwise>
  </head>
  <body bgcolor="#ffffff">

  <tcmis:form action="/label.do" onsubmit="return submitOnlyOnce();">

 <div id="transitPage" class="optionTitleBoldCenter" style="display: none;">
  <br><br><br><fmt:message key="label.pleasewait"/>
 </div>
 <div class="interface" id="mainPage">


<div class="contentArea">

<!-- Search Option Begins -->
<table id="searchMaskTable" width="600" border="0" cellpadding="0" cellspacing="0">
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

<td width="5%" class="optionTitleRight"><input id="option1" type="radio" name="paperSize" value="31" checked class="radioBtns"></td>
<td width="5%" class="optionTitleLeft"><fmt:message key="labels.label.size31"/></td>
<td width="5%" class="optionTitleRight"><input id="option2" type="radio" name="paperSize" value="425" class="radioBtns"></td>
<td width="5%" class="optionTitleLeft"><fmt:message key="labels.label.size425"/></td>

<td width="10%" class="optionTitleBoldRight">
<html:submit property="buttonPrint" styleId="buttonPrint" styleClass="inputBtns" onmouseover="this.className='inputBtns inputBtnsOver'" onmouseout="this.className='inputBtns'">
     <fmt:message key="label.printlabels"/>
</html:submit>
</td>

<td width="70%" class="optionTitleBoldLeft">
<html:button property="close" styleId="close" styleClass="inputBtns" onmouseover="this.className='inputBtns inputBtnsOver'" onmouseout="this.className='inputBtns'" onclick= "closeWindow();">
     <fmt:message key="label.close"/>
</html:button>
</td>
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

<div class="spacerY">&nbsp;

<c:if test="${catPartHazardViewBeanCollection != null}" >
<!-- Search results start -->
<!-- If you want your results section to span only 50% set this to 50% and your mail table will be 100% -->
<table id="resultsMaskTable" width="600" border="0" cellpadding="0" cellspacing="0">
<tr><td>
<div class="roundcont contentContainer">
<div class="roundright">
  <div class="roundtop">
    <div class="roundtopright"> <img src="/images/rndBoxes/borderTL.gif" alt="" width="15" height="15" class="corner" style="display: none" /></div>
  </div>
  <div class="roundContent">
    <div class="boxhead">
    <%--<a href="#" onclick="sendLabelZpl(); return false;"><fmt:message key="label.printlabels"/></a>--%>
   </div>
    <div class="dataContent">
    <table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableResults">


<c:set var="colorClass" value=''/>
<c:set var="dataCount" value='${0}'/>

<c:forEach var="catPartHazardViewBean" items="${catPartHazardViewBeanCollection}" varStatus="status">
<c:set var="dataCount" value='${dataCount+1}'/>
<c:if test="${status.index % 10 == 0}">

<tr align="center">
<th width="5%" CLASS="results"><fmt:message key="label.partnumber"/></th>
<th width="15%" CLASS="results"><fmt:message key="label.description"/></th>
<th width="2%" CLASS="results"><fmt:message key="label.labelqty"/></th>
</tr>
</c:if>

<c:choose>
  <c:when test="${status.index % 2 == 0}" >
   <c:set var="colorClass" value='alt'/>
  </c:when>
  <c:otherwise>
   <c:set var="colorClass" value=''/>
  </c:otherwise>
</c:choose>

<tr class="<c:out value="${colorClass}"/>">
 <input type="hidden" name="catPartHazardViewBean[<c:out value="${status.index}"/>].catPartNo" id="catPartNo<c:out value="${status.index}"/>" value="<c:out value="${status.current.catPartNo}"/>" >
 <input type="hidden" name="catPartHazardViewBean[<c:out value="${status.index}"/>].catalogId" id="catalogId<c:out value="${status.index}"/>" value="<c:out value="${status.current.catalogId}"/>" >
 <input type="hidden" name="catPartHazardViewBean[<c:out value="${status.index}"/>].ok" id="ok<c:out value="${status.index}"/>" value="<c:out value="${status.index}"/>" >
 <input type="hidden" name="catPartHazardViewBean[<c:out value="${status.index}"/>].partShortName" id="partShortName<c:out value="${status.index}"/>" value="<c:out value="${status.current.partShortName}"/>" >
 <input type="hidden" name="catPartHazardViewBean[<c:out value="${status.index}"/>].signalWord" id="signalWord<c:out value="${status.index}"/>" value="<c:out value="${status.current.signalWord}"/>" >
 <input type="hidden" name="catPartHazardViewBean[<c:out value="${status.index}"/>].targetOrgan" id="targetOrgan<c:out value="${status.index}"/>" value="<c:out value="${status.current.targetOrgan}"/>" >
 <input type="hidden" name="catPartHazardViewBean[<c:out value="${status.index}"/>].flammability" id="flammability<c:out value="${status.index}"/>" value="<c:out value="${status.current.flammability}"/>" >
 <input type="hidden" name="catPartHazardViewBean[<c:out value="${status.index}"/>].health" id="health<c:out value="${status.index}"/>" value="<c:out value="${status.current.health}"/>" >
 <input type="hidden" name="catPartHazardViewBean[<c:out value="${status.index}"/>].specificHazard" id="specificHazard<c:out value="${status.index}"/>" value="<c:out value="${status.current.specificHazard}"/>" >
 <input type="hidden" name="catPartHazardViewBean[<c:out value="${status.index}"/>].reactivity" id="reactivity<c:out value="${status.index}"/>" value="<c:out value="${status.current.reactivity}"/>" >

 <td width="5%"><c:out value="${status.current.catPartNo}"/></td>
 <td width="15%"><c:out value="${status.current.materialDesc}"/></td>
 <td width="2%">
 <input type="text" name="catPartHazardViewBean[<c:out value="${status.index}"/>].labelQty" id="labelQty<c:out value="${status.index}"/>" value="<c:out value="${status.current.labelQty}"/>" size="2" onchange="checkLabelQuantity(<c:out value="${status.index}"/>)" class="inputBox">
 </td>
</tr>
</c:forEach>
</table>

   <!-- If the collection is empty say no data found -->
   <c:if test="${empty catPartHazardViewBeanCollection}" >
    <table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableNoData">
     <tr>
      <td width="100%">
       <fmt:message key="main.nodatafound"/>
      </td>
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
</td></tr>
</table>
<!-- Search results end -->
</c:if>

<!-- Footer message start -->
 <div class="messageBar">&nbsp;</div>
<!-- Footer message end -->

<!-- Hidden element start -->
 <div id="hiddenElements" style="display: none;">   
   <input type="hidden" name="inventoryGroup" id="inventoryGroup" value="${inventoryGroup}" >    
 </div>
<!-- Hidden elements end -->

</div> <!-- close of contentArea -->
</div> <!-- close of interface -->

</tcmis:form>
</div>
</c:otherwise>
</c:choose>

</body>
</html:html>
