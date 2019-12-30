<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
	"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

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

<%@ include file="/common/locale.jsp" %>
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
<script type="text/javascript" src="/js/common/resultiframeresize.js"></script>
<!-- This handels which key press events are disabeled on this page -->
<script src="/js/common/disableKeys.js"></script>

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
<script type="text/javascript" src="/js/hub/packshipconfirmparcel.js"></script>

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
<fmt:message key="shipconfirm.title"/>
</title>

<script LANGUAGE="JavaScript" TYPE="text/javascript">
<!--
with(milonic=new menuname("showPutShipmentOnPallet")){
 top="offset=2"
 style = contextStyle;
 margin=3
 aI("text=<fmt:message key="label.putshipmentonpallet"/>;url=javascript:showPutShipmentOnPallet();");
}

drawMenus();
// -->
</script>

<script language="JavaScript" type="text/javascript">
<!--
//add all the javascript messages here, this for internationalization of client side javascript messages
var messagesData = new Array();
messagesData={
	alert:"<fmt:message key="label.alert"/>",
	and:"<fmt:message key="label.and"/>",
	recordFound:"<fmt:message key="label.recordFound"/>",
	searchDuration:"<fmt:message key="label.searchDuration"/>",
	minutes:"<fmt:message key="label.minutes"/>",
	seconds:"<fmt:message key="label.seconds"/>",
	validvalues:"<fmt:message key="label.validvalues"/>",
	submitOnlyOnce:"<fmt:message key="label.submitOnlyOnce"/>",
	missingSerialNumber:"<fmt:message key="label.missingserialnumber"/>",
	mrLine:"<fmt:message key="label.mrline"/>",
	sendToWawfInspection:"<fmt:message key="label.sendtowawfinspection"/>",
	itemInteger:"<fmt:message key="error.item.integer"/>"};
// -->
</script>
</head>

<body bgcolor="#ffffff" onload="myResultOnload();displaySearchDuration();">

<tcmis:form action="/packshipconfirmresults.do" onsubmit="return submitFrameOnlyOnce();">

<!-- Check if the user has permissions and needs to see the update links,set the variable you use in javascript to true.
     The default value of showUpdateLinks is false.
-->
<%--<tcmis:facilityPermission indicator="true" userGroupId="shipConfirm" facilityId="${param.sourceHub}">--%>
 <script type="text/javascript">
 <!--
  showUpdateLinks = true;
	showShipConfirmLinks = true;
	showReprintPalletMslResultLink = false;
 //-->
 </script>
<%--</tcmis:facilityPermission>--%>

<!-- You can build your error messages below. But we want to trigger the YUI pop-up from the main page.
So this is just used to feed the YUI pop-up in the main page.
Similar divs would have to be built to show any other messages in a pop-up.-->
<!-- Error Messages Begins -->
<div id="errorMessagesAreaBody" style="display:none;">
 <html:errors/>
	<logic:present name="errorBean" scope="request">
		<bean:write name="errorBean" property="cause"/>
	</logic:present>
</div>

<script type="text/javascript">
<!--
/*YAHOO.namespace("example.aqua");
YAHOO.util.Event.addListener(window, "load", init);*/

/*Check if there is any error messages to show and set the variable you use in javascript to true or false.*/
<c:choose>
   <c:when test="${requestScope['org.apache.struts.action.ERROR'] == null and empty requestScope['errorBean']}">
    showErrorMessage = false;
   </c:when>
   <c:otherwise>
    showErrorMessage = true;
   </c:otherwise>
</c:choose>
//-->
</script>
<!-- Error Messages Ends -->

<div class="interface" id="resultsPage">
<div class="backGroundContent">

<c:if test="${parcelViewBeanCollection != null}" >
<!-- Search results start -->

<c:set var="colorClass" value=''/>
<c:set var="dataCount" value='${0}'/>
<c:set var="showWAWFInsRequestLink" value='N'/>
<c:set var="showPrintHazardousGoodsManifest" value='N'/>

 <!-- If you want your results section to span only 50% set this to 50% and your main table will be 100% -->
 <c:if test="${!empty parcelViewBeanCollection}" >

    <table width="100%" class="tableResults" id="resultsPageTable" border="0" cellpadding="0" cellspacing="0">
    <c:forEach var="parcelViewBean" items="${parcelViewBeanCollection}" varStatus="status">

			<c:if test="${status.index % 10 == 0}">
			<!-- Need to print the header every 10 rows-->
			<tr>
				<th width="2%"><fmt:message key="label.update"/></th>
                <th width="5%"><fmt:message key="label.facility"/></th>
                <th width="10%"><fmt:message key="label.carrier"/></th>
				<th width="10%"><fmt:message key="label.mrline"/></th>
				<th width="2%"><fmt:message key="label.hazardous"/></th>
				<th width="10%"><fmt:message key="label.dot"/></th>
				<th width="10%"><fmt:message key="label.leadtrackingnumber"/></th>
				<th width="10%"><fmt:message key="label.boxid"/></th>
				<th width="10%"><fmt:message key="label.boxtrackingnumber"/></th>
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


		  <c:set var="packingGroupCollection"  value='${status.current.packingGroupBox}'/>
		  <bean:size id="listSize" name="packingGroupCollection"/>
		  <c:set var="mainRowSpan" value='${listSize}' />

		  <%-- hidden value --%>
		  <input type="hidden" name="packShipConfirmInputBean[<c:out value="${dataCount}"/>].facilityId" id="facilityId<c:out value="${dataCount}"/>" value="${status.current.facilityId}"/>
		  <input type="hidden" name="packShipConfirmInputBean[<c:out value="${dataCount}"/>].originInspectionRequired" id="originInspectionRequired<c:out value="${dataCount}"/>" value="${status.current.originInspectionRequired}"/>
		  <input type="hidden" name="packShipConfirmInputBean[<c:out value="${dataCount}"/>].trackSerialNumber" id="trackSerialNumber<c:out value="${dataCount}"/>" value="${status.current.trackSerialNumber}"/>
		  <input type="hidden" name="packShipConfirmInputBean[<c:out value="${dataCount}"/>].missingSerialNumber" id="missingSerialNumber<c:out value="${dataCount}"/>" value="${status.current.missingSerialNumber}"/>
		  <input type="hidden" name="packShipConfirmInputBean[<c:out value="${dataCount}"/>].mrLine" id="mrLine<c:out value="${dataCount}"/>" value="${status.current.prNumber}-${status.current.lineItem}"/>

	      <c:choose>
		    <c:when test="${listSize == 0}">
				<tr class="<c:out value="${colorClass}"/>" id="rowId<c:out value="${status.index}"/>" onmouseup="selectRow('<c:out value="${status.index}"/>')">
					<!-- hidden columns -->
					<input type="hidden" name="colorClass<c:out value="${status.index}"/>" id="colorClass<c:out value="${status.index}"/>" value="<c:out value="${colorClass}"/>" >
					<input type="hidden" name="childRowsSize<c:out value="${status.index}"/>" id="childRowsSize<c:out value="${status.index}"/>" value="<c:out value="${listSize}"/>" >
					<input type="hidden" name="packShipConfirmInputBean[<c:out value="${dataCount}"/>].selected" id="selected<c:out value="${dataCount}"/>" value=""/>
				    <input type="hidden" name="packShipConfirmInputBean[<c:out value="${dataCount}"/>].leadTrackingNumber" id="leadTrackingNumber<c:out value="${dataCount}"/>" value="<c:out value="${status.current.trackingNumber}"/>"/>
				    <input type="hidden" name="packShipConfirmInputBean[<c:out value="${dataCount}"/>].packingGroupId" id="packingGroupId<c:out value="${dataCount}"/>" value="<c:out value="${status.current.packingGroupId}"/>"/>
				    <input type="hidden" name="packShipConfirmInputBean[<c:out value="${dataCount}"/>].boxId" id="boxId<c:out value="${dataCount}"/>" value=""/>
				    <input type="hidden" name="packShipConfirmInputBean[<c:out value="${dataCount}"/>].boxTrackingNumber" id="boxTrackingNumber<c:out value="${dataCount}"/>" value=""/>
					<input type="hidden" name="packShipConfirmInputBean[<c:out value="${dataCount}"/>].carrierCode" id="carrierCode<c:out value="${dataCount}"/>" value="<c:out value="${status.current.carrierCode}"/>"/>
                    <%--
					<input type="hidden" name="packShipConfirmInputBean[<c:out value="${dataCount}"/>].facilityId" id="facilityId<c:out value="${dataCount}"/>" value="<c:out value="${status.current.facilityId}"/>"/>
                    --%>
                    <input type="hidden" name="packShipConfirmInputBean[<c:out value="${dataCount}"/>].dot" id="dot<c:out value="${dataCount}"/>" value="<c:out value="${status.current.dot}"/>"/>
                    <input type="text" name="packShipConfirmInputBean[<c:out value="${dataCount}"/>].hazardous" id="hazardous<c:out value="${dataCount}"/>" value="<c:out value="${status.current.hazardous}"/>"/>
                    <input type="hidden" name="packShipConfirmInputBean[<c:out value="${dataCount}"/>].fedexParcelHazardousDocId" id="fedexParcelHazardousDocId<c:out value="${dataCount}"/>" value="<c:out value="${status.current.fedexParcelHazardousDocId}"/>"/>
                    <td width="2%"></td>
                    <td width="5%" align="left"><c:out value="${status.current.facilityId}"/></td>
                    <td width="10%"><c:out value="${status.current.carrierName}"/></td>
					<td width="10%"><c:out value="${status.current.prNumber}"/>-<c:out value="${status.current.lineItem}"/></td>
					<td width="2%"><c:out value="${status.current.hazardous}"/></td>
					<td width="10%"><c:out value="${status.current.dot}"/></td>
					<td width="2%"><c:out value="${status.current.trackingNumber}"/></td>
			      	<td width="10%"></td>
			      	<td width="10%"></td>
			    </tr>
				<c:set var="dataCount" value='${dataCount+1}'/>
		    </c:when>
		    <c:otherwise>
				<tr class="<c:out value="${colorClass}"/>" id="rowId<c:out value="${status.index}"/>" onmouseup="selectRow('<c:out value="${status.index}"/>')">
						<!-- hidden columns -->
					<input type="hidden" name="colorClass<c:out value="${status.index}"/>" id="colorClass<c:out value="${status.index}"/>" value="<c:out value="${colorClass}"/>" >
					<input type="hidden" name="childRowsSize<c:out value="${status.index}"/>" id="childRowsSize<c:out value="${status.index}"/>" value="<c:out value="${listSize}"/>" >
						
				   <c:if test="${fn:toUpperCase(status.current.facilityId) == 'DLA GASES' && status.current.originInspectionRequired == 'Y'}" >
					<c:set var="showWAWFInsRequestLink" value='Y'/>
				   </c:if>
				   <c:if test="${fn:toUpperCase(status.current.carrierName) == 'FEDEX GROUND' && status.current.hazardous == 'Y'}" >
					<c:set var="showPrintHazardousGoodsManifest" value='Y'/>
				   </c:if>

				   <td align="center" width="2%" rowspan="<c:out value="${mainRowSpan}"/>"><input name="packShipConfirmInputBean[<c:out value="${dataCount}"/>].selected" id="selected<c:out value="${dataCount}"/>" type="checkbox" value="<c:out value="${dataCount}"/>"></td>

			    
			      <input type="hidden" name="packShipConfirmInputBean[<c:out value="${dataCount}"/>].carrierCode" id="carrierCode<c:out value="${dataCount}"/>" value="<c:out value="${status.current.carrierCode}"/>"/>
                  <td width="5%" align="left" rowspan="<c:out value="${mainRowSpan}"/>"><c:out value="${status.current.facilityId}"/></td>
                  <td width="10%" rowspan="<c:out value="${mainRowSpan}"/>"><c:out value="${status.current.carrierName}"/></td>


				  <td width="10%" rowspan="<c:out value="${mainRowSpan}"/>"><c:out value="${status.current.prNumber}"/>-<c:out value="${status.current.lineItem}"/></td>
				  <td width="2%" rowspan="<c:out value="${mainRowSpan}"/>"><c:out value="${status.current.hazardous}"/></td>
				  <td width="10%" rowspan="<c:out value="${mainRowSpan}"/>"><c:out value="${status.current.dot}"/></td>
				  <td width="10%" rowspan="<c:out value="${mainRowSpan}"/>"><input name="packShipConfirmInputBean[<c:out value="${dataCount}"/>].leadTrackingNumber" id="leadTrackingNumber<c:out value="${dataCount}"/>" type="text" class="inputBox" value="<c:out value="${status.current.trackingNumber}"/>" size="30"></td>
				  <%-- START of CHILD FOR-EACH --%>
				  <c:forEach var="boxBean" items="${status.current.packingGroupBox}" varStatus="status1">
					<c:if test="${status1.index > 0 && listSize > 1 }">
						<tr class="<c:out value="${colorClass}"/>" id="childRowId<c:out value="${status.index}"/><c:out value="${status1.index}"/>" onmouseup="selectRow('<c:out value="${status.index}"/>')">
					</c:if>
					<td width="10%"><c:out value="${status1.current.boxId}"/></td>
					<td width="10%"><input name="packShipConfirmInputBean[<c:out value="${dataCount}"/>].boxTrackingNumber" id="boxTrackingNumber<c:out value="${dataCount}"/>" type="text" class="inputBox" value="<c:out value="${status1.current.trackingNumber}"/>" size="30"></td>
					<%-- hidden value --%>
					<input type="hidden" name="packShipConfirmInputBean[<c:out value="${dataCount}"/>].boxId" id="boxId<c:out value="${dataCount}"/>" value="<c:out value="${status1.current.boxId}"/>"/>
					<input type="hidden" name="packShipConfirmInputBean[<c:out value="${dataCount}"/>].packingGroupId" id="packingGroupId<c:out value="${dataCount}"/>" value="<c:out value="${status.current.packingGroupId}"/>"/>
					<input type="hidden" name="packShipConfirmInputBean[<c:out value="${dataCount}"/>].hazardous" id="hazardous<c:out value="${dataCount}"/>" value="<c:out value="${status.current.hazardous}"/>"/>
					<input type="hidden" name="packShipConfirmInputBean[<c:out value="${dataCount}"/>].fedexParcelHazardousDocId" id="fedexParcelHazardousDocId<c:out value="${dataCount}"/>" value="<c:out value="${status.current.fedexParcelHazardousDocId}"/>"/>
					</tr>
					<c:set var="dataCount" value='${dataCount+1}'/>
				  </c:forEach>
		    </c:otherwise>
	    </c:choose>

   </c:forEach>
   </table>
   </c:if>

   <c:if test="${showWAWFInsRequestLink == 'Y'}" >
<script language="JavaScript" type="text/javascript">
<!--
	showWAWFInsRequestLink = true;
// -->    	
</script>	   
   </c:if>  
   
   <c:if test="${showPrintHazardousGoodsManifest == 'Y'}" >
<script language="JavaScript" type="text/javascript">
<!--
	showPrintHazardousGoodsManifest = true;
// -->    	
</script>	   
   </c:if>
      
   <!-- If the collection is empty say no data found -->
   <c:if test="${empty parcelViewBeanCollection}" >

    <table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableNoData" id="resultsPageTable">
     <tr>
      <td width="100%">
       <fmt:message key="main.nodatafound"/>
      </td>
     </tr>
    </table>
   </c:if>

<!-- Search results end -->
</c:if>

<!-- Hidden element start -->
<div id="hiddenElements" style="display: none;">
<input name="totalLines" id="totalLines" value="${dataCount}" type="hidden">
<input name="transportationMode" id="transportationMode" value='<tcmis:jsReplace value="${param.transportationMode}"/>' type="hidden">
<input name="sourceHub" id="sourceHub" value='<tcmis:jsReplace value="${param.sourceHub}"/>' type="hidden">
<input name="action" id="action" value='<tcmis:jsReplace value="${param.action}"/>' type="hidden">
<input name="FedexParcelHazDocId" id="FedexParcelHazDocId" value="${FedexParcelHazDocId}" type="hidden">
<input name="popUp" id="popUp" value="${popUp}" type="hidden">
<!-- Store all search criteria in hidden elements, need this to requery the database after updates -->
<%--<tcmis:saveRequestParameter/> --%>

 </div>
<!-- Hidden elements end -->

</div> <!-- close of backGroundContent -->
</div> <!-- close of interface -->

</tcmis:form>
</body>
</html:html>