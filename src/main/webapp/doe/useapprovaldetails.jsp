<%@ page contentType="text/html;charset=UTF-8" language="java"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%@ taglib uri="/WEB-INF/struts-nested.tld" prefix="nested" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/tcmis.tld" prefix="tcmis" %>

<script type="text/javascript">
<!--
var jsData = new Array();

<c:set var="selectedFacilityId" value='${param.facilityId}'/>

<c:forEach var="facAppUserGrpOvBean" items="${facAppUserGrpOvBeanColl}" varStatus="status">
 <c:set var="currentFacilityId" value='${status.current.facilityId}'/>
  <c:choose>
   <c:when test="${currentFacilityId == selectedFacilityId}" >
    <c:set var="facilityDockObjBeanJspCollection" value='${status.current.facDockVar}'/>
   </c:when>
  </c:choose>
</c:forEach>

<c:set var="dockLocationIdCount" value='${0}'/>
var dockLocationId = new Array(
<c:forEach var="facilityDockObjBean" items="${facilityDockObjBeanJspCollection}" varStatus="dockLocationIdStatus">
  <c:choose>
   <c:when test="${dockLocationIdCount > 0}">
    ,"<c:out value="${dockLocationIdStatus.current.dockLocationId}"/>"
   </c:when>
   <c:otherwise>
    "<c:out value="${dockLocationIdStatus.current.dockLocationId}"/>"
   </c:otherwise>
  </c:choose>
  <c:set var="dockLocationIdCount" value='${dockLocationIdCount+1}'/>
 </c:forEach>
  );

<c:set var="dockLocationIdCount" value='${0}'/>
var dockDesc = new Array(
<c:forEach var="facilityDockObjBean" items="${facilityDockObjBeanJspCollection}" varStatus="dockLocationIdStatus">
  <c:choose>
   <c:when test="${dockLocationIdCount > 0}">
    ,"<c:out value="${dockLocationIdStatus.current.dockDesc}"/>"
   </c:when>
   <c:otherwise>
    "<c:out value="${dockLocationIdStatus.current.dockDesc}"/>"
   </c:otherwise>
  </c:choose>
  <c:set var="dockLocationIdCount" value='${dockLocationIdCount+1}'/>
 </c:forEach>
  );


<c:set var="useApprovalPermission" value=''/>
<tcmis:facilityPermission indicator="true" userGroupId="useapprovalupdate" facilityId="${selectedFacilityId}">
  <c:set var="useApprovalPermission" value='Yes'/>
</tcmis:facilityPermission>
window.parent.useApprovalPermission = "<c:out value="${useApprovalPermission}"/>";

<c:set var="useApprovalStatusPermission" value=''/>
<tcmis:facilityPermission indicator="true" userGroupId="useapprovalstatusupdate" facilityId="${selectedFacilityId}">
  <c:set var="useApprovalStatusPermission" value='Yes'/>
</tcmis:facilityPermission>
window.parent.useApprovalStatusPermission = "<c:out value="${useApprovalStatusPermission}"/>";

<c:set var="managedWorkArea" value='${managedWorkArea}'/>
window.parent.isItmanagedWorkArea = "<c:out value="${managedWorkArea}"/>";

var headerString = "<fmt:message key="label.catalog"/>,<fmt:message key="label.partnumber"/>,";
headerString +="<fmt:message key="label.partdescription"/>,<fmt:message key="useapprovalstatus.label.approvedstatus"/>,";
headerString +="<fmt:message key="useapprovalstatus.label.limit1"/>,<fmt:message key="useapprovalstatus.label.limit2"/>,";
headerString +="<fmt:message key="useapprovalstatus.label.processdesc"/>,<fmt:message key="label.active"/>,<fmt:message key="useapprovalstatus.label.limit1"/>,";
headerString +="<fmt:message key="useapprovalstatus.label.limit2"/>,<fmt:message key="useapprovalstatus.label.processdesc"/>,";
headerString +="<fmt:message key="label.orderqty"/>,<fmt:message key="useapprovalstatus.label.packaging"/>,";
headerString +="<fmt:message key="useapprovalstatus.label.estimatedcovereage"/>,<fmt:message key="label.orderqtytype"/>,";
headerString +="<fmt:message key="useapprovalstatus.label.customershiptocode"/>,";
headerString +="<fmt:message key="useapprovalstatus.label.tcmisdock"/>,<fmt:message key="useapprovalstatus.label.customerdeliverto"/>,";
headerString +="<fmt:message key="useapprovalstatus.label.tcmisdeliverto"/>,<fmt:message key="useapprovalstatus.label.requestor"/>";
window.parent.headerString = headerString;

<%--<BR><INPUT TYPE=\"checkbox\" name=\"makeAllActive\" ID=\"makeAllActive\" value=\"Yes\" onclick=\"makeAllRowsActive()\">&nbsp;All--%>

<c:set var="dataCount" value='${0}'/>
<c:forEach var="useApprovalStatusViewBean" items="${useApprovalStatusViewBeanCollection}" varStatus="status">
<c:set var="limitQuantityPeriod1" value='${status.current.limitQuantityPeriod1}'/>
<c:set var="limitQuantityPeriod2" value='${status.current.limitQuantityPeriod2}'/>
<c:set var="mwLimitQuantityPeriod1" value='${status.current.mwLimitQuantityPeriod1}'/>
<c:set var="mwLimitQuantityPeriod2" value='${status.current.mwLimitQuantityPeriod2}'/>
<c:set var="mwEstimateOrderQuantityPrd" value='${status.current.mwEstimateOrderQuantityPrd}'/>
<c:set var="mwApprovalId" value='${status.current.mwApprovalId}'/>

jsData[<c:out value="${status.index}"/>] = {facPartNo:"<c:out value="${status.current.facPartNo}"/>",approvalStatus:"<c:out value="${status.current.approvalStatus}"/>",
catalogId:"<c:out value="${status.current.catalogId}"/>",partDescription:" <tcmis:jsReplace var="notes" value="${status.current.partDescription}" processMultiLines="true" />",
<c:if test="${dataCount == 0}" >
facilityId:"<c:out value="${status.current.facilityId}"/>",application:"<c:out value="${status.current.application}"/>",userGroupId:"<c:out value="${status.current.userGroupId}"/>",
</c:if>
limitQuantityPeriod1:"<c:out value="${limitQuantityPeriod1}"/>",daysPeriod1:"<c:out value="${status.current.daysPeriod1}"/>",
limitQuantityPeriod2:"<c:out value="${limitQuantityPeriod2}"/>",daysPeriod2:"<c:out value="${status.current.daysPeriod2}"/>",
<c:choose>
   <c:when test="${empty limitQuantityPeriod1}">
    limitQuantityPeriod1Display:"",
   </c:when>
   <c:otherwise>
    limitQuantityPeriod1Display:"<c:out value="${status.current.limitQuantityPeriod1}"/> <fmt:message key="useapprovalstatus.label.every"/> <c:out value="${status.current.daysPeriod1}"/> <fmt:message key="label.days"/>",
   </c:otherwise>
</c:choose>
<c:choose>
<c:when test="${empty limitQuantityPeriod2}">
  limitQuantityPeriod2Display:"",
 </c:when>
 <c:otherwise>
   limitQuantityPeriod2Display:"<c:out value="${status.current.limitQuantityPeriod2}"/> <fmt:message key="useapprovalstatus.label.every"/> <c:out value="${status.current.daysPeriod2}"/> <fmt:message key="label.days"/>",
  </c:otherwise>
</c:choose>
packaging:"<c:out value="${status.current.packaging}"/>",customerDeliverTo:"<c:out value="${status.current.customerDeliverTo}"/>",
dockLocationId:"<c:out value="${status.current.dockLocationId}"/>",deliveryPointBarcode:"<c:out value="${status.current.deliveryPointBarcode}"/>",
dockDeliveryPoint:"<c:out value="${status.current.dockDeliveryPoint}"/>",barcodeRequester:"<c:out value="${status.current.barcodeRequester}"/>",
barcodeRequesterName:"<c:out value="${status.current.barcodeRequesterName}"/>",processDesc:"<c:out value="${status.current.processDesc}"/>",
mwEstimateOrderQuantityPrd:"<c:out value="${mwEstimateOrderQuantityPrd}"/>",mwDaysPeriod1:"<c:out value="${status.current.mwDaysPeriod1}"/>",
<c:choose>
   <c:when test="${empty mwEstimateOrderQuantityPrd}">
    mwEstimateOrderQuantityPrdDisplay:"",
   </c:when>
   <c:otherwise>
    mwEstimateOrderQuantityPrdDisplay:"<c:out value="${status.current.mwEstimateOrderQuantityPrd}"/> <fmt:message key="label.days"/>",
 </c:otherwise>
</c:choose>
mwLimitQuantityPeriod1:"<c:out value="${mwLimitQuantityPeriod1}"/>",mwDaysPeriod2:"<c:out value="${status.current.mwDaysPeriod2}"/>",
mwLimitQuantityPeriod2:"<c:out value="${mwLimitQuantityPeriod2}"/>",mwOrderQuantityRule:"<c:out value="${status.current.mwOrderQuantityRule}"/>",
<c:choose>
   <c:when test="${empty mwLimitQuantityPeriod1}">
    mwLimitQuantityPeriod1Display:"",
   </c:when>
   <c:otherwise>
    mwLimitQuantityPeriod1Display:"<c:out value="${status.current.mwLimitQuantityPeriod1}"/> <fmt:message key="useapprovalstatus.label.every"/> <c:out value="${status.current.mwDaysPeriod1}"/> <fmt:message key="label.days"/>",
 </c:otherwise>
</c:choose>
<c:choose>
<c:when test="${empty mwLimitQuantityPeriod2}">
  mwLimitQuantityPeriod2Display:"",
 </c:when>
 <c:otherwise>
   mwLimitQuantityPeriod2Display:"<c:out value="${status.current.mwLimitQuantityPeriod2}"/> <fmt:message key="useapprovalstatus.label.every"/> <c:out value="${status.current.mwDaysPeriod2}"/> <fmt:message key="label.days"/>",
</c:otherwise>
</c:choose>
<c:choose>
<c:when test="${empty mwApprovalId}">
  active:"<img src=/images/item_chk0.gif border=0 alt=Active align=>",
 </c:when>
 <c:otherwise>
   active:"<img src=/images/item_chk1.gif border=0 alt=Active align=>",
</c:otherwise>
</c:choose>
mwApprovalId:"<c:out value="${mwApprovalId}"/>",mwProcessDesc:"<c:out value="${status.current.mwProcessDesc}"/>",mwOrderQuantity:"<c:out value="${status.current.mwOrderQuantity}"/>"
,catalogCompanyId:"<c:out value="${status.current.catalogCompanyId}"/>",companyId:"<c:out value="${status.current.companyId}"/>"
};

<c:set var="dataCount" value='${dataCount+1}'/>
</c:forEach>

window.parent.totalRows = <c:out value="${dataCount}"/>;
window.parent.workAreaManagementData = jsData;
window.parent.dockLocationId = dockLocationId;
window.parent.dockDesc = dockDesc;
window.parent.lastSearchText = "<c:out value="${param.searchText}"/>";
window.parent.lastShowActiveOnly = "<c:out value="${param.showActiveOnly}"/>";
window.parent.lastShowOnlyWithLimits = "<c:out value="${param.showOnlyWithLimits}"/>";

//window.parent.clearGridCount = 0;
window.parent.endingRowNumber = 0;
window.parent.rowCount = 0;
window.parent.inputChangedCount=0;
window.parent.buildFirstSetRows();
//-->
</script>

<%--
<c:set var="selectedApprovalStatus" value='${status.current.approvalStatus}'/>

<INPUT TYPE="hidden" name="useApprovalStatusViewBean[<c:out value="${dataCount}"/>].facPartNo" ID="facPartNo<c:out value="${dataCount}"/>" value="<c:out value="${status.current.facPartNo}"/>">
<INPUT TYPE="hidden" name="useApprovalStatusViewBean[<c:out value="${dataCount}"/>].itemId" ID="itemId<c:out value="${dataCount}"/>" value="<c:out value="${status.current.itemId}"/>">
<INPUT TYPE="hidden" name="useApprovalStatusViewBean[<c:out value="${dataCount}"/>].rowNumber" ID="rowNumber<c:out value="${dataCount}"/>" value="<c:out value="${dataCount}"/>">

<c:choose>
<c:when test="${useApprovalPermission == 'Yes' && selectedApprovalStatus == 'approved'}">
<TR align="center">
<TD <c:out value="${colorClass}"/> width="2%">
<c:if test="${status.current.ok != null}" >
 <INPUT TYPE="checkbox" name="useApprovalStatusViewBean[<c:out value="${dataCount}"/>].ok" ID="ok<c:out value="${dataCount}"/>" value="<c:out value="${dataCount}"/>" checked onclick="checkInput('<c:out value="${dataCount}"/>')">
</c:if>
<c:if test="${status.current.ok == null}" >
 <INPUT TYPE="checkbox" name="useApprovalStatusViewBean[<c:out value="${dataCount}"/>].ok" ID="ok<c:out value="${dataCount}"/>" value="<c:out value="${dataCount}"/>" onclick="checkInput('<c:out value="${dataCount}"/>')">
</c:if>
</TD>

<TD <c:out value="${colorClass}"/> width="5%"><c:out value="${status.current.userGroupId}"/></TD>
<TD <c:out value="${colorClass}"/> width="5%"><c:out value="${status.current.catalogId}"/></TD>
<TD <c:out value="${colorClass}"/> width="5%"><c:out value="${status.current.facPartNo}"/></TD>
<TD <c:out value="${colorClass}"/> width="10%"><c:out value="${status.current.partDescription}"/></TD>

<TD <c:out value="${colorClass}"/> width="3%">
<INPUT TYPE="text" name="useApprovalStatusViewBean[<c:out value="${dataCount}"/>].limitQuantityPeriod1" ID="limitQuantityPeriod1<c:out value="${dataCount}"/>" value="<c:out value="${status.current.limitQuantityPeriod1}"/>" size="2" maxlength="10" Class="DETAILS">
 <fmt:message key="useapprovalstatus.label.per"/>
<INPUT TYPE="text" name="useApprovalStatusViewBean[<c:out value="${dataCount}"/>].daysPeriod1" ID="daysPeriod1<c:out value="${dataCount}"/>" value="<c:out value="${status.current.daysPeriod1}"/>" size="2" maxlength="10" Class="DETAILS">
 <fmt:message key="useapprovalstatus.label.days"/>
</TD>

<TD <c:out value="${colorClass}"/> width="3%">
<INPUT TYPE="text" name="useApprovalStatusViewBean[<c:out value="${dataCount}"/>].limitQuantityPeriod2" ID="limitQuantityPeriod2<c:out value="${dataCount}"/>" value="<c:out value="${status.current.limitQuantityPeriod2}"/>" size="2" maxlength="10" Class="DETAILS">
 <fmt:message key="useapprovalstatus.label.per"/>
<INPUT TYPE="text" name="useApprovalStatusViewBean[<c:out value="${dataCount}"/>].daysPeriod2" ID="daysPeriod2<c:out value="${dataCount}"/>" value="<c:out value="${status.current.daysPeriod2}"/>" size="2" maxlength="10" Class="DETAILS">
 <fmt:message key="useapprovalstatus.label.days"/>
</TD>
<TD <c:out value="${colorClass}"/> width="5%">
<INPUT TYPE="text" name="useApprovalStatusViewBean[<c:out value="${dataCount}"/>].orderQuantity" ID="orderQuantity<c:out value="${dataCount}"/>" value="<c:out value="${status.current.orderQuantity}"/>" size="4" maxlength="10" Class="DETAILS">
</TD>
<TD <c:out value="${colorClass}"/> width="5%"><c:out value="${status.current.packaging}"/></TD>
<TD <c:out value="${colorClass}"/> width="5%">
<INPUT TYPE="text" name="useApprovalStatusViewBean[<c:out value="${dataCount}"/>].estimateOrderQuantityPeriod" ID="estimateOrderQuantityPeriod<c:out value="${dataCount}"/>" value="<c:out value="${status.current.estimateOrderQuantityPeriod}"/>" size="4" maxlength="10" Class="DETAILS">
  <fmt:message key="useapprovalstatus.label.days"/>
</TD>
<TD <c:out value="${colorClass}"/> width="5%">
<c:set var="selectedOrderQuantityRule" value='${status.current.orderQuantityRule}'/>
<select name="useApprovalStatusViewBean[<c:out value="${dataCount}"/>].orderQuantityRule" ID="orderQuantityRule<c:out value="${dataCount}"/>">
  <option value=""><fmt:message key="label.pleaseselect"/></option>
  <c:forEach var="vvUseApprovalOrderQtyRuleBean" items="${vvUseApprovalOrderQtyRuleBeanColl}" varStatus="statusPriority">
  <c:set var="currentOrderQuantityRule" value='${statusPriority.current.orderQuantityRule}'/>

  <c:choose>
   <c:when test="${currentOrderQuantityRule == selectedOrderQuantityRule}">
    <option value="<c:out value="${currentOrderQuantityRule}"/>" selected><c:out value="${statusPriority.current.orderQuantityRuleDesc}"/></option>
   </c:when>
   <c:otherwise>
    <option value="<c:out value="${currentOrderQuantityRule}"/>"><c:out value="${statusPriority.current.orderQuantityRuleDesc}"/></option>
   </c:otherwise>
  </c:choose>
  </c:forEach>
</select>
</TD>

<c:choose>
<c:when test="${useApprovalStatusPermission == 'Yes'}">
<TD <c:out value="${colorClass}"/> width="5%">
<select name="useApprovalStatusViewBean[<c:out value="${dataCount}"/>].approvalStatus">
<c:choose>
<c:when test="${selectedApprovalStatus == 'approved'}" >
  <option value="approved" selected>Approved</option>
  <option value="banned">Banned</option>
  <option value="unapproved">Unapproved</option>
</c:when>
<c:when test="${selectedApprovalStatus == 'unapproved'}" >
  <option value="banned">Banned</option>
  <option value="unapproved" selected>Unapproved</option>
</c:when>
<c:when test="${selectedApprovalStatus == 'banned'}" >
  <option value="banned" selected>Banned</option>

</c:when>
</c:choose>
</TD>
</c:when>
<c:otherwise>
<TD <c:out value="${colorClass}"/> width="5%"><c:out value="${status.current.approvalStatus}"/></TD>
</c:otherwise>
</c:choose>

<TD <c:out value="${colorClass}"/> width="5%">
<INPUT TYPE="text" name="useApprovalStatusViewBean[<c:out value="${dataCount}"/>].customerDeliverTo" ID="customerDeliverTo<c:out value="${dataCount}"/>" value="<c:out value="${status.current.customerDeliverTo}"/>" size="8" maxlength="10" Class="DETAILS">
</TD>
<TD <c:out value="${colorClass}"/> width="5%">
<c:set var="selectedDockLocationId" value='${status.current.dockLocationId}'/>
<select name="useApprovalStatusViewBean[<c:out value="${dataCount}"/>].dockLocationId" ID="dockLocationId<c:out value="${dataCount}"/>" onchange="dockLocationIdChanged(<c:out value="${dataCount}"/>)">
<c:if test="${empty selectedDockLocationId}" >
<option value=""><fmt:message key="label.pleaseselect"/></option>
</c:if>
  <c:forEach var="facilityDockObjBean" items="${facilityDockObjBeanJspCollection}" varStatus="dockLocationIdStatus">
  <c:set var="currentDockLocationId" value='${dockLocationIdStatus.current.dockLocationId}'/>

  <c:choose>
   <c:when test="${empty selectedDockLocationId}" >

   </c:when>
   <c:when test="${currentDockLocationId == selectedDockLocationId}" >
    <option value="<c:out value="${currentDockLocationId}"/>" selected><c:out value="${dockLocationIdStatus.current.dockDesc}"/></option>
    <c:set var="deliveryPointObjBeanJspCollection" value='${dockLocationIdStatus.current.deliveryPointVar}'/>
   </c:when>
  </c:choose>

  <c:choose>
   <c:when test="${empty selectedDockLocationId}">
    <option value="<c:out value="${currentDockLocationId}"/>"><c:out value="${dockLocationIdStatus.current.dockDesc}"/></option>
   </c:when>
  </c:choose>
  </c:forEach>
</select>
</TD>


<TD <c:out value="${colorClass}"/> width="5%">
<INPUT TYPE="text" name="useApprovalStatusViewBean[<c:out value="${dataCount}"/>].deliveryPointBarcode" ID="deliveryPointBarcode<c:out value="${dataCount}"/>" value="<c:out value="${status.current.deliveryPointBarcode}"/>" size="8" maxlength="10" Class="DETAILS">
</TD>

<TD <c:out value="${colorClass}"/> width="5%">
<c:set var="selectedDockDeliveryPoint" value='${status.current.dockDeliveryPoint}'/>
<c:if test="${empty selectedDockLocationId}" >
 <c:set var="deliveryPointObjBeanJspCollection" value=''/>
</c:if>

<select name="useApprovalStatusViewBean[<c:out value="${dataCount}"/>].dockDeliveryPoint" ID="dockDeliveryPoint<c:out value="${dataCount}"/>">
  <option value=""><fmt:message key="label.pleaseselect"/></option>
  <c:forEach var="deliveryPointObjBean" items="${deliveryPointObjBeanJspCollection}" varStatus="dockDeliveryPointStatus">
  <c:set var="currentDockDeliveryPoint" value='${dockDeliveryPointStatus.current.deliveryPoint}'/>
  <c:choose>
   <c:when test="${currentDockDeliveryPoint == selectedDockDeliveryPoint}">
    <option value="<c:out value="${currentDockDeliveryPoint}"/>" selected><c:out value="${dockDeliveryPointStatus.current.deliveryPointDesc}"/></option>
   </c:when>
   <c:otherwise>
    <option value="<c:out value="${currentDockDeliveryPoint}"/>"><c:out value="${dockDeliveryPointStatus.current.deliveryPointDesc}"/></option>
   </c:otherwise>
  </c:choose>
  </c:forEach>
</select>
</TD>

<TD <c:out value="${colorClass}"/> width="5%">
<INPUT TYPE="hidden" name="useApprovalStatusViewBean[<c:out value="${dataCount}"/>].barcodeRequester" ID="barcodeRequester<c:out value="${dataCount}"/>" value="<c:out value="${status.current.barcodeRequester}"/>">
<INPUT TYPE="text" NAME="useApprovalStatusViewBean[<c:out value="${dataCount}"/>].barcodeRequesterName" ID="barcodeRequesterName<c:out value="${dataCount}"/>" value="<c:out value="${status.current.barcodeRequesterName}"/>" size="12" <c:out value="${invisibleColorClass}"/> readonly>
<INPUT CLASS="SUBMIT" onmouseover="className='SUBMITHOVER'" onMouseout="className='SUBMIT'" name="searchpersonnellike" value="..." OnClick="getpersonnelID(<c:out value="${dataCount}"/>)" type="button">
</TD>
</TR>
</c:when>
<c:otherwise>
<TR align="center">
<TD <c:out value="${colorClass}"/> width="2%">
<c:choose>
 <c:when test="${useApprovalPermission == 'Yes'}">
 <c:if test="${status.current.ok != null}" >
  <INPUT TYPE="checkbox" name="useApprovalStatusViewBean[<c:out value="${dataCount}"/>].ok" ID="ok<c:out value="${dataCount}"/>" value="<c:out value="${dataCount}"/>" checked onclick="checkInput(<c:out value="${dataCount}"/>)">
 </c:if>
 <c:if test="${status.current.ok == null}" >
  <INPUT TYPE="checkbox" name="useApprovalStatusViewBean[<c:out value="${dataCount}"/>].ok" ID="ok<c:out value="${dataCount}"/>" value="<c:out value="${dataCount}"/>" onclick="checkInput(<c:out value="${dataCount}"/>)">
 </c:if>
 </c:when>
 <c:otherwise>
 &nbsp;
 </c:otherwise>
</c:choose>
</TD>
<TD <c:out value="${colorClass}"/> width="5%"><c:out value="${status.current.userGroupId}"/></TD>
<TD <c:out value="${colorClass}"/> width="5%"><c:out value="${status.current.catalogId}"/></TD>
<TD <c:out value="${colorClass}"/> width="8%"><c:out value="${status.current.facPartNo}"/></TD>
<TD <c:out value="${colorClass}"/> width="15%"><c:out value="${status.current.partDescription}"/></TD>
<TD <c:out value="${colorClass}"/> width="3%">
<c:set var="limitQuantityPeriod1" value='${status.current.limitQuantityPeriod1}'/>
<c:if test="${!empty limitQuantityPeriod1}">
<c:out value="${status.current.limitQuantityPeriod1}"/> <fmt:message key="useapprovalstatus.label.per"/> <c:out value="${status.current.daysPeriod1}"/> <fmt:message key="useapprovalstatus.label.days"/>
</c:if>
</TD>
<TD <c:out value="${colorClass}"/> width="3%">
<c:set var="limitQuantityPeriod2" value='${status.current.limitQuantityPeriod2}'/>
<c:if test="${!empty limitQuantityPeriod2}">
<c:out value="${status.current.limitQuantityPeriod2}"/> <fmt:message key="useapprovalstatus.label.per"/> <c:out value="${status.current.daysPeriod2}"/> <fmt:message key="useapprovalstatus.label.days"/>
</c:if>
</TD>
<TD <c:out value="${colorClass}"/> width="5%"><c:out value="${status.current.orderQuantity}"/></TD>
<TD <c:out value="${colorClass}"/> width="5%"><c:out value="${status.current.packaging}"/></TD>
<TD <c:out value="${colorClass}"/> width="5%">
<c:set var="estimateOrderQuantityPeriod" value='${status.current.estimateOrderQuantityPeriod}'/>
<c:if test="${!empty estimateOrderQuantityPeriod}">
<c:out value="${status.current.estimateOrderQuantityPeriod}"/> <fmt:message key="useapprovalstatus.label.days"/>
</c:if>
</TD>
<TD <c:out value="${colorClass}"/> width="5%"><c:out value="${status.current.orderQuantityRule}"/></TD>
<c:choose>
<c:when test="${useApprovalStatusPermission == 'Yes'}">
<TD <c:out value="${colorClass}"/> width="5%">
<select name="useApprovalStatusViewBean[<c:out value="${dataCount}"/>].approvalStatus">
<c:choose>
<c:when test="${selectedApprovalStatus == 'approved'}" >
  <option value="approved" selected>Approved</option>
  <option value="banned">Banned</option>
  <option value="unapproved">Unapproved</option>
</c:when>
<c:when test="${selectedApprovalStatus == 'unapproved'}" >
  <option value="banned">Banned</option>
  <option value="unapproved" selected>Unapproved</option>
</c:when>
<c:when test="${selectedApprovalStatus == 'banned'}" >
  <option value="banned" selected>Banned</option>
 </c:when>
</c:choose>
</TD>
</c:when>
<c:otherwise>
<TD <c:out value="${colorClass}"/> width="5%"><c:out value="${status.current.approvalStatus}"/></TD>
</c:otherwise>
</c:choose>
<TD <c:out value="${colorClass}"/> width="5%"><c:out value="${status.current.customerDeliverTo}"/></TD>
<TD <c:out value="${colorClass}"/> width="5%"><c:out value="${status.current.dockLocationId}"/></TD>
<TD <c:out value="${colorClass}"/> width="5%"><c:out value="${status.current.deliveryPointBarcode}"/></TD>
<TD <c:out value="${colorClass}"/> width="5%"><c:out value="${status.current.dockDeliveryPoint}"/></TD>
<TD <c:out value="${colorClass}"/> width="5%"><c:out value="${status.current.barcodeRequesterName}"/></TD>
</TR>
</c:otherwise>
</c:choose>

<c:set var="dataCount" value='${dataCount+1}'/>
</c:forEach>--%>


