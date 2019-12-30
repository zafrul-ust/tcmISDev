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

<%@ include file="/common/locale.jsp" %>

<tcmis:fontSizeCss />
<%-- Add any other stylesheets you need for the page here --%>


<%--
<link rel="stylesheet" type="text/css" href="/css/dhtmlXGridHaas.css"></link>
<link rel="stylesheet" type="text/css" href="/yui/build/container/assets/container.css" />
--%>
<!-- This handels the menu style and what happens to the right click on the whole page -->
<script type="text/javascript" src="/js/menu/milonic_src.js"></script>
<script type="text/javascript" src="/js/menu/mmenudom.js"></script>
<script type="text/javascript" src="/js/menu/mainmenudata.js"></script>
<script type="text/javascript" src="/js/menu/contextmenu.js"></script>
<script src="/js/common/disableKeys.js" language="JavaScript"></script>

<script src="/js/common/formchek.js" language="JavaScript"></script>
<script src="/js/common/commonutil.js" language="JavaScript"></script>

<%-- For Calendar support --%>
<%--
<script src="/js/calendar/newcalendar.js" language="JavaScript"></script>
<script src="/js/calendar/AnchorPosition.js" language="JavaScript"></script>
<script src="/js/calendar/PopupWindow.js" language="JavaScript"></script>
--%>

<%-- Add any other javascript you need for the page here --%>
<script src="/js/hub/cabinetlevel.js" language="JavaScript"></script>

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
<fmt:message key="cabinetlevel.title"/>
</title>

<script language="JavaScript" type="text/javascript">
<!--
//add all the javascript messages here, this for internationalization of client side javascript messages
var messagesData = new Array();
messagesData={alert:"<fmt:message key="label.alert"/>",and:"<fmt:message key="label.and"/>",
submitOnlyOnce:"<fmt:message key="label.submitOnlyOnce"/>", reorderPointInteger:"<fmt:message key="error.reorderpoint.integer"/>",
stockingLevelInteger:"<fmt:message key="error.stockinglevel.integer"/>",leadTimeDaysInteger:"<fmt:message key="error.leadtimedays.integer"/>",
reorderPointRequired:"<fmt:message key="error.reorderpoint.required"/>",
stockingLevelRequired:"<fmt:message key="error.stockinglevel.required"/>",leadTimeDaysRequired:"<fmt:message key="error.leadtimedays.required"/>",
reorderPointLessThanStockingLevel:"<fmt:message key="error.reorderpoint.lessthanstockinglevel"/>",
reorderPointGreaterThanZero:"<fmt:message key="label.reorderpointgreaterthanzero"/>",	
noChange:"<fmt:message key="error.nochange"/>"};
// -->
</script>

</head>

<body bgcolor="#ffffff">
<tcmis:form action="/cabinetlevel.do" onsubmit="return submitOnlyOnce();">

 <div id="transitPage" class="optionTitleBoldCenter" style="display: none;">
  <br><br><br><fmt:message key="label.pleasewait"/>
 </div>
 <div class="interface" id="mainPage">

<%-- Top Navigation- we will not display this section after sometime, when we move to the new menu structure --%>
<!--
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
<fmt:message key="cabinetlevel.title"/>
</td>
<td width="30%" class="headingr">
<html:link style="color:#FFFFFF" forward="home">
 <fmt:message key="label.home"/>
</html:link>
</td>
</tr>
</table>
</div>
-->
<div class="contentArea">

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
<input name="companyId" id="companyId" type="hidden" value="<c:out value="${param.companyId}"/>">
<input name="catalogId" id="catalogId" type="hidden" value="<c:out value="${param.catalogId}"/>">
<input name="partGroupNo" id="partGroupNo" type="hidden" value="<c:out value="${param.partGroupNo}"/>">
<input name="catPartNo" id="catPartNo" type="hidden" value="<c:out value="${param.catPartNo}"/>">
<input name="facilityId" id="facilityId" type="hidden" value="<c:out value="${param.facilityId}" escapeXml="false"/>">
<input name="status" id="status" type="hidden" value="<c:out value="${param.status}"/>">
<input name="branchPlant" id="branchPlant" type="hidden" value="<c:out value="${param.branchPlant}"/>">
<input name="cabinetId" id="cabinetId" type="hidden" value="<c:out value="${param.cabinetId}"/>">
<input name="binId" id="binId" type="hidden" value="<c:out value="${param.binId}"/>">		
<input name="uAction" id="uAction" type="hidden">
    <table width="600" border="0" cellpadding="0" cellspacing="0" class="tableSearch">
      <tr>
        <td width="20%" class="optionTitleBoldRight"><fmt:message key="label.itemid"/>:&nbsp;</td>
        <td width="80%" class="optionTitleLeft"><c:out value="${param.itemId}"/></td>
<input name="itemId" id="itemId" type="hidden" value="<c:out value="${param.itemId}"/>">
      </tr>
      <tr>
        <td width="20%" class="optionTitleBoldRight"><fmt:message key="label.hub"/>:&nbsp;</td>
        <td width="80%" class="optionTitleLeft"><c:out value="${param.hubName}"/></td>
<input name="hubName" id="hubName" type="hidden" value="<c:out value="${param.hubName}"/>">
      </tr>
      <tr>
        <td width="20%" class="optionTitleBoldRight"><fmt:message key="label.facility"/>:&nbsp;</td>
        <td width="80%" class="optionTitleLeft"><c:out value="${param.facilityName}" escapeXml="false"/></td>
<input name="facilityName" id="facilityName" type="hidden" value="<c:out value="${param.facilityName}" escapeXml="false"/>">
      </tr>
      <tr>
        <td width="20%" class="optionTitleBoldRight"><fmt:message key="label.workarea"/>:&nbsp;</td>
        <td width="80%" class="optionTitleLeft"><c:out value="${param.application}"/></td>
<input name="application" id="application" type="hidden" value="<c:out value="${param.application}"/>">
      </tr>
      <tr>
        <td width="20%" class="optionTitleBoldRight"><fmt:message key="label.cabinet"/>:&nbsp;</td>
        <td width="80%" class="optionTitleLeft"><c:out value="${param.cabinetName}"/></td>
<input name="cabinetName" id="cabinetName" type="hidden" value="<c:out value="${param.cabinetName}"/>">
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
<div class="spacerY">&nbsp;
<div id="errorMessagesArea" class="errorMessages">
<html:errors/>
<c:if test="${param.stockingLevel != null && param.reorderPoint != null && param.leadTimeDays != null && requestScope['org.apache.struts.action.ERROR'] == null}">
<div id="successMessageArea" class="successMessages">
  <fmt:message key="cabinetlevel.label.successfulupdate"/>
</div>
</c:if>
</div>
</div>
<!-- Error Messages Ends -->

<c:if test="${cabinetPartLevelViewBeanCollection != null}" >
<!-- Search results start -->
<!-- If you want your results section to span only 50% set this to 50% and your mail table will be 100% -->
<table id="resultsMaskTable" width="100%" border="0" cellpadding="0" cellspacing="0">
<tr><td>
<div class="roundcont contentContainer">
<div class="roundright">
  <div class="roundtop">
    <div class="roundtopright"> <img src="/images/rndBoxes/borderTL.gif" alt="" width="15" height="15" class="corner" style="display: none" /></div>
  </div>
  <div class="roundContent">
    <div class="boxhead">
            <input name="action" id="action" type="hidden">
 <tcmis:facilityPermission indicator="true" userGroupId="CabinetMgmt" facilityId="${param.hubName}">
<a href="#" onclick="submitUpdate();"><fmt:message key="label.update"/></a>
 </tcmis:facilityPermission>
    </div>
    <div class="dataContent">
    <table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableResults">
    <c:set var="colorClass" value=''/>
    <c:set var="dataCount" value='${0}'/>

    <c:forEach var="cabinetPartLevelViewBean" items="${cabinetPartLevelViewBeanCollection}" varStatus="status">
    <c:set var="dataCount" value='${dataCount+1}'/>

    <c:if test="${status.index == 0}">
     <input name="orderingApplication" id="orderingApplication" type="hidden" value="<c:out value="${status.current.application}"/>">
	  <input name="catalogCompanyId" id="catalogCompanyId" type="hidden" value="<c:out value="${status.current.catalogCompanyId}"/>">	 
	 </c:if>

    <c:if test="${status.index % 10 == 0}">
    <!-- Need to print the header every 10 rows-->
    <tr>
    <th ><fmt:message key="label.description"/></th>
    <th ><fmt:message key="label.containersize"/></th>
    <th ><fmt:message key="label.partnumber"/></th>
    <th ><fmt:message key="label.reorderpoint"/></th>
    <th ><fmt:message key="label.stockinglevel"/></th>
    <th ><fmt:message key="label.leadtimeindays"/></th>
    <th ><fmt:message key="label.remarks"/></th>
    </tr>
    </c:if>

    <c:choose>
     <c:when test="${status.index % 2 == 0}" >
      <c:set var="colorClass" value=''/>
     </c:when>
     <c:otherwise>
      <c:set var="colorClass" value='alt'/>
     </c:otherwise>
    </c:choose>

    <tr class="<c:out value="${colorClass}"/>" id="rowId<c:out value="${status.index}"/>">
     <td><c:out value="${status.current.materialDesc}"/>&nbsp;</td>
     <td><c:out value="${status.current.packaging}"/>&nbsp;</td>
     <td><c:out value="${status.current.catPartNo}"/>&nbsp;</td>
     <td>
 <tcmis:facilityPermission indicator="true" userGroupId="CabinetMgmt" facilityId="${param.hubName}">
<input name="oldReorderPoint" id="oldReorderPoint" type="hidden" value="<c:out value="${status.current.reorderPoint}"/>">
<input name="reorderPoint" id="reorderPoint" type="text" class="inputBox" value="<c:out value="${status.current.reorderPoint}"/>" size="5" maxlength="5">
 </tcmis:facilityPermission>
 <tcmis:facilityPermission indicator="false" userGroupId="CabinetMgmt" facilityId="${param.hubName}">
       <c:out value="${status.current.reorderPoint}"/>
 </tcmis:facilityPermission>
     &nbsp;</td>
     <td>
 <tcmis:facilityPermission indicator="true" userGroupId="CabinetMgmt" facilityId="${param.hubName}">
<input name="oldStockingLevel" id="oldStockingLevel" type="hidden" value="<c:out value="${status.current.stockingLevel}"/>">
<input name="stockingLevel" id="stockingLevel" type="text" class="inputBox" value="<c:out value="${status.current.stockingLevel}"/>" size="5" maxlength="5">
 </tcmis:facilityPermission>
 <tcmis:facilityPermission indicator="false" userGroupId="CabinetMgmt" facilityId="${param.hubName}">
       <c:out value="${status.current.stockingLevel}"/>
 </tcmis:facilityPermission>
     &nbsp;</td>
     <td>
 <tcmis:facilityPermission indicator="true" userGroupId="CabinetMgmt" facilityId="${param.hubName}">
<input name="oldLeadTimeDays" id="oldLeadTimeDays" type="hidden" value="<c:out value="${status.current.leadTimeDays}"/>">
<input name="leadTimeDays" id="leadTimeDays" type="text" class="inputBox" value="<c:out value="${status.current.leadTimeDays}"/>" size="5" maxlength="5">
 </tcmis:facilityPermission>
 <tcmis:facilityPermission indicator="false" userGroupId="CabinetMgmt" facilityId="${param.hubName}">
       <c:out value="${status.current.leadTimeDays}"/>
 </tcmis:facilityPermission>
     &nbsp;</td>
     <td>
 <tcmis:facilityPermission indicator="true" userGroupId="CabinetMgmt" facilityId="${param.hubName}">
<input name="remarks" id="remarks" type="text" class="inputBox" value="" size="10" maxlength="200">
 </tcmis:facilityPermission>
&nbsp;
     </td>
   </tr>
   <c:set var="dataCount" value='${dataCount+1}'/>
   </c:forEach>
   </table>
  </div>
  </div>
  <div class="roundbottom">
    <div class="roundbottomright"> <img src="/images/rndBoxes/borderBL.gif" alt="" width="15" height="15" class="corner" style="display: none" /> </div>
  </div>
</div>
</div>
</td></tr>
<tr height="10"><td>&nbsp;</td></tr>
<tr>
<td>
<c:if test="${!empty cabinetPartLevelViewBeanCollection}" >
<div class="roundcont contentContainer">
<div class="roundright">
  <div class="roundtop">
    <div class="roundtopright"> <img src="/images/rndBoxes/borderTL.gif" alt="" width="15" height="15" class="corner" style="display: none" /></div>
  </div>
  <div class="roundContent">
  <div class="dataContent">
    <table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableResults">

    <c:set var="colorClass" value=''/>
    <c:set var="dataCount" value='${0}'/>

    <c:forEach var="cabinetPartLevelLogViewBean" items="${cabinetPartLevelLogViewBeanCollection}" varStatus="status">
    <c:set var="dataCount" value='${dataCount+1}'/>

    <c:if test="${status.index % 10 == 0}">
    <!-- Need to print the header every 10 rows-->
    <tr>
    <th colspan="10" ><fmt:message key="label.changehistory"/></th>
    </tr>
    <tr>
    <th ><fmt:message key="label.partnumber"/></th>
    <th ><fmt:message key="cabinetlevel.label.changedon"/></th>
    <th ><fmt:message key="cabinetlevel.label.changedby"/></th>
    <th ><fmt:message key="cabinetlevel.label.oldstockinglevel"/></th>
    <th ><fmt:message key="cabinetlevel.label.newstockinglevel"/></th>
    <th ><fmt:message key="cabinetlevel.label.oldreorderpoint"/></th>
    <th ><fmt:message key="cabinetlevel.label.newreorderpoint"/></th>
    <th ><fmt:message key="cabinetlevel.label.oldleadtimeindays"/></th>
    <th ><fmt:message key="cabinetlevel.label.newleadtimeindays"/></th>
    <th ><fmt:message key="label.remarks"/></th>
    </tr>
    </c:if>

    <c:choose>
     <c:when test="${status.index % 2 == 0}" >
      <c:set var="colorClass" value=''/>
     </c:when>
     <c:otherwise>
      <c:set var="colorClass" value='alt'/>
     </c:otherwise>
    </c:choose>

    <tr class="<c:out value="${colorClass}"/>" id="rowId<c:out value="${status.index}"/>">
     <td><c:out value="${status.current.catPartNo}"/>&nbsp;</td>
     <td><fmt:formatDate pattern="${dateTimeFormatPattern}" value="${status.current.dateModified}"/>&nbsp;</td>
     <td><c:out value="${status.current.modifiedByName}"/>&nbsp;</td>
     <td><c:out value="${status.current.oldStockingLevel}"/>&nbsp;</td>
     <td><c:out value="${status.current.newStockingLevel}"/>&nbsp;</td>
     <td><c:out value="${status.current.oldReorderPoint}"/>&nbsp;</td>
     <td><c:out value="${status.current.newReorderPoint}"/>&nbsp;</td>
     <td><c:out value="${status.current.oldLeadTimeDays}"/>&nbsp;</td>
     <td><c:out value="${status.current.newLeadTimeDays}"/>&nbsp;</td>
     <td><c:out value="${status.current.remarks}"/>&nbsp;</td>
   </tr>
   <c:set var="dataCount" value='${dataCount+1}'/>
   </c:forEach>
   </table>
   <!-- If the collection is empty say no data found -->
   <c:if test="${empty cabinetPartLevelViewBeanCollection  || dataCount ==0}" >

    <table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableNoData">
    <tr>
        <th><fmt:message key="label.changehistory"/></th>
    </tr>

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
</c:if>
</td></tr>
</table>
<!-- Search results end -->
</c:if>

<!-- Footer message start -->
 <div class="messageBar">&nbsp;</div>
<!-- Footer message end -->

<!-- Hidden element start -->
 <div id="hiddenElements" style="display: none;"></div>
<!-- Hidden elements end -->

</div> <!-- close of contentArea -->
</div> <!-- close of interface -->

</tcmis:form>
</body>
</html:html>

