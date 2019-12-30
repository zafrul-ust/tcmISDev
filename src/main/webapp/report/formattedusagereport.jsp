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

<link rel="stylesheet" type="text/css" href="/css/haasGlobal.css"></link>
<%-- Add any other stylesheets you need for the page here --%>


<%--
<link rel="stylesheet" type="text/css" href="/css/dhtmlXGridHaas.css"></link>
<link rel="stylesheet" type="text/css" href="/yui/build/container/assets/container.css" />
--%>

<script src="/js/common/formchek.js" language="JavaScript"></script>
<script src="/js/common/commonutil.js" language="JavaScript"></script>

<%-- For Calendar support --%>

<script src="/js/calendar/newcalendar.js" language="JavaScript"></script>
<script src="/js/calendar/AnchorPosition.js" language="JavaScript"></script>
<script src="/js/calendar/PopupWindow.js" language="JavaScript"></script>


<%-- Add any other javascript you need for the page here --%>
<script src="/js/report/formattedreport.js" language="JavaScript"></script>
<script language="JavaScript" type="text/javascript">
<!--
var altFacilityId = new Array(
   <c:forEach var="facAppReportViewBean" items="${facAppReportViewBeanCollection}" varStatus="status">
   <c:choose>
   <c:when test="${status.index > 0}">
    ,"<c:out value="${status.current.facilityId}"/>"
   </c:when>
   <c:otherwise>
    "<c:out value="${status.current.facilityId}"/>"
   </c:otherwise>
  </c:choose>
</c:forEach>
);

var altFacilityName = new Array(
<c:forEach var="facAppReportViewBean" items="${facAppReportViewBeanCollection}" varStatus="status">
 <c:choose>
   <c:when test="${status.index > 0}">
    ,"<c:out value="${status.current.facilityName}"/>"
   </c:when>
   <c:otherwise>
    "<c:out value="${status.current.facilityName}"/>"
   </c:otherwise>
  </c:choose>
</c:forEach>
);
<%--
//now do reporting entity
var altReportingEntity = new Array();
<c:forEach var="facAppReportViewBean" items="${facAppReportViewBeanCollection}" varStatus="status">
<c:set var="currentFacility" value='${status.current.facilityId}'/>
<c:set var="reportingEntityBeanCollection" value='${status.current.reportingEntityBeanCollection}'/>

<c:set var="reportingEntityCount" value='${0}'/>
altReportingEntity["<c:out value="${currentFacility}"/>"] = new Array(
 <c:forEach var="facLocAppBean" items="${reportingEntityBeanCollection}" varStatus="status1">
 <c:if test="${status1.index == 0}">
   "","myreportingentities",
 </c:if>

 <c:set var="reportingEntityCount" value='${reportingEntityCount+1}'/>
 <c:choose>
   <c:when test="${reportingEntityCount > 1}">
    ,"<c:out value="${status1.current.reportingEntityId}"/>"
   </c:when>
   <c:otherwise>
    "<c:out value="${status1.current.reportingEntityId}"/>"
   </c:otherwise>
  </c:choose>

 </c:forEach>
);
 </c:forEach>

var altReportingEntityDesc = new Array();
<c:forEach var="facAppReportViewBean" items="${facAppReportViewBeanCollection}" varStatus="status">
<c:set var="currentFacility" value='${status.current.facilityId}'/>
<c:set var="reportingEntityBeanCollection" value='${status.current.reportingEntityBeanCollection}'/>

<c:set var="reportingEntityCount" value='${0}'/>
altReportingEntityDesc["<c:out value="${currentFacility}"/>"] = new Array(
 <c:forEach var="facLocAppBean" items="${reportingEntityBeanCollection}" varStatus="status1">
 <c:if test="${status1.index == 0}">
   "<fmt:message key="label.all"/>","<fmt:message key="label.myreportingentities"/>",
 </c:if>

 <c:set var="reportingEntityCount" value='${reportingEntityCount+1}'/>
 <c:choose>
   <c:when test="${reportingEntityCount > 1}">
    ,"<c:out value="${status1.current.reportingEntityDescription}"/>"
   </c:when>
   <c:otherwise>
    "<c:out value="${status1.current.reportingEntityDescription}"/>"
   </c:otherwise>
  </c:choose>

 </c:forEach>
);
 </c:forEach>

--%>
//now do applications
var altApplication = new Array();
<c:forEach var="facAppReportViewBean" items="${facAppReportViewBeanCollection}" varStatus="status">
<c:set var="currentFacility" value='${status.current.facilityId}'/>
<%--
<c:set var="reportingEntityBeanCollection" value='${status.current.reportingEntityBeanCollection}'/>

<c:forEach var="facAppReportViewBean1" items="${reportingEntityBeanCollection}" varStatus="status1">
<c:set var="currentReportingEntity" value='${status1.current.reportingEntityId}'/>
--%>
<c:set var="applicationBeanCollection" value='${status.current.applicationBeanCollection}'/>

<c:set var="applicationCount" value='${0}'/>
altApplication["<c:out value="${currentFacility}"/>"] = new Array(
 <c:forEach var="facLocAppBean" items="${applicationBeanCollection}" varStatus="status2">
<%--
 <c:if test="${status2.index == 0}">
   "","myworkareas",
 </c:if>
--%>
 <c:set var="applicationCount" value='${applicationCount+1}'/>
 <c:choose>
   <c:when test="${applicationCount > 1}">
    ,"<c:out value="${status2.current.application}"/>"
   </c:when>
   <c:otherwise>
    "<c:out value="${status2.current.application}"/>"
   </c:otherwise>
  </c:choose>

 </c:forEach>
);
<%--
 </c:forEach>
--%>
 </c:forEach>

var altApplicationDesc = new Array();
<c:forEach var="facAppReportViewBean" items="${facAppReportViewBeanCollection}" varStatus="status">
<c:set var="currentFacility" value='${status.current.facilityId}'/>
<%--
<c:set var="reportingEntityBeanCollection" value='${status.current.reportingEntityBeanCollection}'/>

<c:forEach var="facAppReportViewBean1" items="${reportingEntityBeanCollection}" varStatus="status1">
<c:set var="currentReportingEntity" value='${status1.current.reportingEntityId}'/>
--%>
<c:set var="applicationBeanCollection" value='${status.current.applicationBeanCollection}'/>

<c:set var="applicationCount" value='${0}'/>
altApplicationDesc["<c:out value="${currentFacility}"/>"] = new Array(
 <c:forEach var="facLocAppBean" items="${applicationBeanCollection}" varStatus="status2">
<%--
 <c:if test="${status2.index == 0}">
   "<fmt:message key="label.all"/>","<fmt:message key="label.myworkareas"/>",
 </c:if>
--%>
 <c:set var="applicationCount" value='${applicationCount+1}'/>
 <c:choose>
   <c:when test="${applicationCount > 1}">
    ,"<c:out value="${status2.current.applicationDesc}"/>"
   </c:when>
   <c:otherwise>
    "<c:out value="${status2.current.applicationDesc}"/>"
   </c:otherwise>
  </c:choose>

 </c:forEach>
);
<%--
 </c:forEach>
--%>
 </c:forEach>

var altDock = new Array();
<c:forEach var="facAppDockDpViewBean" items="${facAppDockDpViewBeanCollection}" varStatus="status">
<c:set var="currentFacility" value='${status.current.facilityId}'/>
<c:set var="dockBeanCollection" value='${status.current.dockBeanCollection}'/>

<c:set var="dockCount" value='${0}'/>
altDock["<c:out value="${currentFacility}"/>"] = new Array(
 <c:forEach var="facLocAppBean" items="${dockBeanCollection}" varStatus="status1">
<%--
 <c:if test="${status1.index == 0}">
   "","mydocks",
 </c:if>
--%>
 <c:set var="dockCount" value='${dockCount+1}'/>
 <c:choose>
   <c:when test="${dockCount > 1}">
    ,"<c:out value="${status1.current.dockLocationId}"/>"
   </c:when>
   <c:otherwise>
    "<c:out value="${status1.current.dockLocationId}"/>"
   </c:otherwise>
  </c:choose>

 </c:forEach>
);
 </c:forEach>

var altDockDesc = new Array();
<c:forEach var="facAppDockDpViewBean" items="${facAppDockDpViewBeanCollection}" varStatus="status">
<c:set var="currentFacility" value='${status.current.facilityId}'/>
<c:set var="dockBeanCollection" value='${status.current.dockBeanCollection}'/>

<c:set var="dockCount" value='${0}'/>
altDockDesc["<c:out value="${currentFacility}"/>"] = new Array(
 <c:forEach var="facLocAppBean" items="${dockBeanCollection}" varStatus="status1">
<%--
 <c:if test="${status1.index == 0}">
   "<fmt:message key="label.all"/>","<fmt:message key="label.mydocks"/>",
 </c:if>
--%>
 <c:set var="dockCount" value='${dockCount+1}'/>
 <c:choose>
   <c:when test="${dockCount > 1}">
    ,"<c:out value="${status1.current.dockDesc}"/>"
   </c:when>
   <c:otherwise>
    "<c:out value="${status1.current.dockDesc}"/>"
   </c:otherwise>
  </c:choose>

 </c:forEach>
);
 </c:forEach>

var altDeliveryPoint = new Array();
<c:forEach var="facAppDockDpViewBean" items="${facAppDockDpViewBeanCollection}" varStatus="status">
<c:set var="currentFacility" value='${status.current.facilityId}'/>
<c:set var="dockBeanCollection" value='${status.current.dockBeanCollection}'/>

<c:forEach var="facAppDockDpViewBean" items="${dockBeanCollection}" varStatus="status1">
<c:set var="currentDock" value='${status1.current.dockLocationId}'/>
<c:set var="deliveryPointBeanCollection" value='${status1.current.deliveryPointBeanCollection}'/>

<c:set var="deliveryPointCount" value='${0}'/>
altDeliveryPoint["<c:out value="${currentDock}"/>"] = new Array(
 <c:forEach var="facAppDockDpViewBean2" items="${deliveryPointBeanCollection}" varStatus="status2">
<%--
 <c:if test="${status2.index == 0}">
   "","mydeliverypoints",
 </c:if>
--%>
 <c:set var="deliveryPointCount" value='${deliveryPointCount+1}'/>
 <c:choose>
   <c:when test="${deliveryPointCount > 1}">
    ,"<c:out value="${status2.current.deliveryPoint}"/>"
   </c:when>
   <c:otherwise>
    "<c:out value="${status2.current.deliveryPoint}"/>"
   </c:otherwise>
  </c:choose>

 </c:forEach>
);
 </c:forEach>
 </c:forEach>

var altDeliveryPointDesc = new Array();
<c:forEach var="facAppDockDpViewBean" items="${facAppDockDpViewBeanCollection}" varStatus="status">
<c:set var="currentFacility" value='${status.current.facilityId}'/>
<c:set var="dockBeanCollection" value='${status.current.dockBeanCollection}'/>

<c:forEach var="facAppDockDpViewBean" items="${dockBeanCollection}" varStatus="status1">
<c:set var="currentDock" value='${status1.current.dockLocationId}'/>
<c:set var="deliveryPointBeanCollection" value='${status1.current.deliveryPointBeanCollection}'/>

<c:set var="deliveryPointCount" value='${0}'/>
altDeliveryPointDesc["<c:out value="${currentDock}"/>"] = new Array(
 <c:forEach var="facAppDockDpViewBean2" items="${deliveryPointBeanCollection}" varStatus="status2">
<%--
 <c:if test="${status2.index == 0}">
   "<fmt:message key="label.all"/>","<fmt:message key="label.mydeliverypoints"/>",
 </c:if>
--%>
 <c:set var="deliveryPointCount" value='${deliveryPointCount+1}'/>
 <c:choose>
   <c:when test="${deliveryPointCount > 1}">
    ,"<c:out value="${status2.current.deliveryPointDesc}"/>"
   </c:when>
   <c:otherwise>
    "<c:out value="${status2.current.deliveryPointDesc}"/>"
   </c:otherwise>
  </c:choose>

 </c:forEach>
);
 </c:forEach>
 </c:forEach>
// -->
</script>

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
<fmt:message key="adhocusagereport.title"/>
</title>

<script language="JavaScript" type="text/javascript">
<!--
//add all the javascript messages here, this for internationalization of client side javascript messages
var messagesData = new Array();
messagesData={alert:"<fmt:message key="label.alert"/>",and:"<fmt:message key="label.and"/>",
submitOnlyOnce:"<fmt:message key="label.submitOnlyOnce"/>",
youmustentercasnumber:"<fmt:message key="label.youmustentercasnumber"/>",
pleaseenterreportname:"<fmt:message key="label.pleaseenterreportname"/>",
daterequired:"<fmt:message key="label.daterequired"/>",
begindaterequired:"<fmt:message key="label.begindaterequired"/>",
enddaterequired:"<fmt:message key="label.enddaterequired"/>"};
// -->
</script>
</head>

<body bgcolor="#ffffff" onload="onFormattedUsageLoad();">
<c:if test="org.apache.struts.action.MESSAGE == null">
  <div class="errorMessages">
    ERROR:  Application resources not loaded
  </div>
</c:if>

<tcmis:form action="/formattedusagereport.do" onsubmit="return submitOnlyOnce();">

 <div id="transitPage" class="optionTitleBoldCenter" style="display: none;">
  <br><br><br><fmt:message key="label.pleasewait"/>
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
<img src="/images/tcmistcmis32.gif" align="right">
</td>
</tr>
</table>

<table width="100%" border="0" cellpadding="0" cellspacing="0">
<tr><td width="70%" class="headingl">
<fmt:message key="formattedusagereport.title"/>
</td>
<td width="30%" class="headingr">
<html:link style="color:#FFFFFF" forward="home">
 <fmt:message key="label.home"/>
</html:link>
</td>
</tr>
</table>
</div>

<div class="contentArea">

<!-- Search Option Begins -->
<table id="searchMaskTable" width="800" border="0" cellpadding="0" cellspacing="0">
<tr><td>
<div class="roundcont filterContainer">
 <div class="roundright">
   <div class="roundtop">
     <div class="roundtopright"> <img src="/images/rndBoxes/borderTL_filter.gif" alt="" width="15" height="10" class="corner_filter" style="display: none" /></div>
   </div>
   <div class="roundContent">
    <!-- Insert all the search option within this div -->
    <table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableSearch">
<tr><td class="optionTitleBoldCenter" colspan="2"><fmt:message key="formattedusagereport.title"/></td></tr>

   <tr>
      <td class="optionTitleBoldLeft">
         <html:radio styleClass="radioBtns" styleId="reportType" property="reportType" value="list"/>
         <fmt:message key="label.list"/>
</td>

<td class="optionTitleLeft">
         <html:select property="chemicalListName" styleClass="selectBox" styleId="chemicalListName">
         <html:options collection="listOptionBeanCollection" name="ListBean" labelProperty="listName" property="listId"/>
         </html:select>
         <html:button
            property="listSearch" styleClass="inputBtns" onmouseover="this.className='inputBtns inputBtnsOver'" onmouseout="this.className='inputBtns'" onclick="getListSearch()">
            <fmt:message key="adhocusagereport.button.viewlist"/>
         </html:button>
      </td>
   </tr>

   <tr>
      <td class="optionTitleBoldLeft">
         <html:radio styleClass="radioBtns" styleId="reportType" property="reportType" value="singleChemical"/>
         <fmt:message key="adhocusagereport.radio.singleChemical"/>
</td>
<td class="optionTitleBoldLeft">
         <fmt:message key="adhocusagereport.label.casNumber"/>
<html:text property="casNumber" styleClass="inputBox" styleId="casNumber"/>
         <html:button
            property="casSearch" styleClass="inputBtns" onmouseover="this.className='inputBtns inputBtnsOver'" onmouseout="this.className='inputBtns'" onclick="getCasSearch()">
            <fmt:message key="button.search"/>
         </html:button>
      </td>
   </tr>
   <tr>
       <td class="optionTitleBoldLeft" colspan="2">
          <html:radio styleClass="radioBtns" styleId="reportType" property="reportType" value="all"/>
          <fmt:message key="label.all"/>
       </td>
   </tr>

</table>



    <table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableSearch">

<tr>
<td class="optionTitleBoldRight" width="15%">
   <fmt:message key="label.facility"/>:
</td>

<td class="optionTitleLeft">
<html:select property="facilityId" onchange="facilityChanged()" styleClass="selectBox" styleId="facilityId">
<html:options collection="facAppReportViewBeanCollection" name="FacAppReportViewBean" labelProperty="facilityName" property="facilityId"/>
</html:select>
</td>
<td class="optionTitleBoldRight" width="10%"><fmt:message key="label.begindate"/>:</td>
<td class="optionTitleLeft">
 <html:text property="beginDate" styleClass="inputBox" styleId="beginDate"/> <a href="javascript: void(0);" ID="linkbeginDate" onClick="return getCalendar(document.genericForm.beginDate);" STYLE="color:#0000ff;cursor:pointer;text-decoration:underline">&diams;</a>
</td>
</tr>
<%--
<tr>
<td class="optionTitleBoldRight"  width="15%">
   <fmt:message key="adhocusagereport.label.reportingentity"/>:
</td>
<td class="optionTitleLeft" width="15%">
      <html:select property="reportingEntity" onchange="reportingEntityChanged()" styleClass="selectBox" styleId="reportingEntity">

  <html:option value="All" key="label.all"/>
  <html:option value="myreportingentities" key="label.myreportingentities"/>

  <c:if test="${reportingEntityCollection == null}">
    <c:set var="reColl" value='${facAppReportViewBeanCollection[0].reportingEntityBeanCollection}'/>
  </c:if>
  <c:if test="${!empty reportingEntityCollection}">
    <c:set var="reColl" value='${reportingEntityCollection}'/>
  </c:if>
    <html:options collection="reColl" labelProperty="reportingEntityDescription" property="reportingEntityId"/>

      </html:select>
</td>

<td class="optionTitleBoldRight" width="10%"><fmt:message key="label.begindate"/>:</td>
<td class="optionTitleLeft">
 <html:text property="beginDate" styleClass="inputBox" styleId="beginDate"/> <a href="javascript: void(0);" ID="linkbeginDate" onClick="return getCalendar(document.genericForm.beginDate);" STYLE="color:#0000ff;cursor:pointer;text-decoration:underline">&diams;</a>
</td>

</tr>
--%>
<tr>
<td class="optionTitleBoldRight" width="10%">
   <fmt:message key="label.workarea"/>:
</td>
<td class="optionTitleLeft" width="15%">
<html:select property="application" styleClass="selectBox" styleId="application">
<%--
  <html:option value="All" key="label.all"/>
  <html:option value="myworkareas" key="label.myworkareas"/>
--%>
    <c:if test="${applicationCollection == null}">
    <c:set var="applicationCollection" value='${facAppReportViewBeanCollection[0].applicationBeanCollection}'/>
  </c:if>
<c:if test="${!empty applicationCollection}">
<html:options collection="applicationCollection" labelProperty="applicationDesc" property="application"/>
</c:if>
</html:select>
</td>
<td class="optionTitleBoldRight" width="15%"><fmt:message key="label.enddate"/></td>
<td>
   <html:text property="endDate" styleClass="inputBox" styleId="endDate"/><a href="javascript: void(0);" ID="linkendDate" onClick="return getCalendar(document.genericForm.endDate);" STYLE="color:#0000ff;cursor:pointer;text-decoration:underline">&diams;</a>
</td>

</tr>


<tr>

   <td class="optionTitleBoldRight">
      <fmt:message key="label.orderby"/>
   </td>


   <td colspan="3" class="optionTitleBoldLeft">
<html:select property="orderBy" styleClass="selectBox" styleId="orderBy">
    <html:option value="PART_NUM"><fmt:message key="label.partnumber"/></html:option>
    <html:option value="WORK_AREA"><fmt:message key="label.workarea"/></html:option>
      </html:select>
   </td>

</tr>

<tr>
<html:hidden property="id" styleId="id"/>
<html:hidden property="templateName" styleId="templateName"/>
<html:hidden property="submitValue" styleId="submitValue"/>
   <td class="optionTitleLeft" colspan="4">
      <html:button property="submitReport" styleId="submitReport" styleClass="inputBtns" onmouseover="this.className='inputBtns inputBtnsOver'" onmouseout="this.className='inputBtns'" onclick="generateUsageReportAudit()">
     <fmt:message key="button.generatereport"/>
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

<div class="spacerY">&nbsp;</div>

<!-- Error Messages Begins -->
<div id="errorMessagesArea" class="errorMessages">
<html:errors/>
</div>
<!-- Error Messages Ends -->



<!-- Hidden element start -->
 <div id="hiddenElements" style="display: none;"></div>
<!-- Hidden elements end -->

</div> <!-- close of contentArea -->

<!-- Footer message start -->
 <div class="messageBar">&nbsp;</div>
<!-- Footer message end -->

</div> <!-- close of interface -->

</tcmis:form>
</body>
</html:html>
