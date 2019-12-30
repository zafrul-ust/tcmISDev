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
<script type="text/javascript" src="/js/hub/packshipconfirmltltl.js"></script>

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
with(milonic=new menuname("showPalletDetail")){
 top="offset=2"
 style = contextStyle;
 margin=3
 aI("text=<fmt:message key="label.showpalletdetail"/>;url=javascript:showPalletDetail();");
}

drawMenus();
// -->
</script>

<script language="JavaScript" type="text/javascript">
<!--
//add all the javascript messages here, this for internationalization of client side javascript messages
var messagesData = new Array();
messagesData={alert:"<fmt:message key="label.alert"/>",and:"<fmt:message key="label.and"/>",
	validvalues:"<fmt:message key="label.validvalues"/>",submitOnlyOnce:"<fmt:message key="label.submitOnlyOnce"/>",
	recordFound:"<fmt:message key="label.recordFound"/>",
	searchDuration:"<fmt:message key="label.searchDuration"/>",
	minutes:"<fmt:message key="label.minutes"/>",
	seconds:"<fmt:message key="label.seconds"/>",
	missingSerialNumber:"<fmt:message key="label.missingserialnumber"/>",
	trackingNumber:"<fmt:message key="label.trackingnumber"/>",
	confirmShipment:"<fmt:message key="label.confirmshipment"/>",
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

 <script type="text/javascript">
  	showUpdateLinks = true;
	showShipConfirmLinks = true;
	showReprintPalletMslResultLink = false;
 </script>

<!-- You can build your error messages below. But we want to trigger the YUI pop-up from the main page.
So this is just used to feed the YUI pop-up in the main page.
Similar divs would have to be built to show any other messages in a pop-up.-->
<!-- Error Messages Begins -->
<div id="errorMessagesAreaBody" style="display:none;">
  <html:errors/>
    <c:forEach items="${tcmISErrors}" varStatus="status">
  	${status.current}<br/>
    </c:forEach>
    
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
   <c:when test="${requestScope['org.apache.struts.action.ERROR'] == null and empty requestScope['errorBean'] and empty requestScope['tcmISErrors']}">
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

<c:if test="${ltltlViewBeanCollection != null}" >
<!-- Search results start -->

<c:set var="colorClass" value=''/>
<c:set var="dataCount" value='${0}'/>

 <!-- If you want your results section to span only 50% set this to 50% and your main table will be 100% -->
 <c:if test="${!empty ltltlViewBeanCollection}" >

    <table width="100%" class="tableResults" id="resultsPageTable" border="0" cellpadding="0" cellspacing="0">
    	<c:forEach var="ltlltViewBean" items="${ltltlViewBeanCollection}" varStatus="status">

			<c:if test="${status.index % 10 == 0}">
			<!-- Need to print the header every 10 rows-->
			<tr>
                <th width="5%"><fmt:message key="label.facility"/></th>
                <th width="10%"><fmt:message key="label.carrier"/></th>
                <th width="1%"><fmt:message key="label.ok"/></th>
				<th width="5%"><fmt:message key="label.trackingnumber"/></th>
				<th width="10%"><fmt:message key="label.consolidationno"/></th>
				<th width="10%"><fmt:message key="label.palletid"/></th>
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

			<tr align="center" class="<c:out value="${colorClass}"/>" id="rowId<c:out value="${status.index}"/>" onmouseup="selectRow('<c:out value="${status.index}"/>')">
				<td width="5%" align="left"><c:out value="${status.current.facilityId}"/></td>
                <td width="10%" align="left">
			    	<c:set var="selectedCarrierCode" value='${status.current.carrierCode}'/>
          			${status.current.carrierName}
          			<input name="packShipConfirmInputBean[<c:out value="${status.index}"/>].carrierCode" id="carrierCode<c:out value="${status.index}"/>" type="hidden" class="inputBox" value="<c:out value="${selectedCarrierCode}"/>">
          			<input name="packShipConfirmInputBean[<c:out value="${status.index}"/>].facilityId" id="facilityId<c:out value="${status.index}"/>" type="hidden" class="inputBox" value="<c:out value="${status.current.facilityId}"/>">
				</td>
				
				<td align="center" width="1%" rowspan="${mainRowSpan}">
					<c:choose>
						<c:when test="${fn:toUpperCase(status.current.facilityId) == 'DLA GASES' && status.current.originInspectionRequired == 'Y'}" >
							<input name="packShipConfirmInputBean[<c:out value="${status.index}"/>].selected" id="selected<c:out value="${status.index}"/>" type="checkbox" onclick="checkSerialNumber(${status.index})" value="<c:out value="${status.index}"/>">
							<c:set var="showWAWFInsRequestLink" value='Y'/>
						</c:when>
						<c:otherwise>
							&nbsp;
						</c:otherwise>
					</c:choose>
				</td>
				<td width="10%" rowspan="<c:out value="${mainRowSpan}"/>"><input name="packShipConfirmInputBean[<c:out value="${status.index}"/>].leadTrackingNumber" id="leadTrackingNumber<c:out value="${status.index}"/>" type="text" class="inputBox" value="<c:out value="${status.current.trackingNumber}"/>" size="30"></td>
			  	<input name="packShipConfirmInputBean[${status.index}].oldLeadTrackingNumber" id="oldLeadTrackingNumbe${status.index}" value="${status.current.trackingNumber}" type="hidden">
			 	<td width="10%" align="left"><a href="#" onclick="return showMrLines('${status.current.consolidationNumber}')"><c:out value="${status.current.consolidationNumber}"/></a></td>
			 	<td width="10%" align="left"><c:out value="${status.current.palletId}"/></td>
			 	<input type="hidden" name="packShipConfirmInputBean[<c:out value="${status.index}"/>].palletId" id="palletId<c:out value="${status.index}"/>" value="<c:out value="${status.current.palletId}"/>"/>
			</tr>
			<!-- hidden columns -->
		 	<input type="hidden" name="colorClass<c:out value="${status.index}"/>" id="colorClass<c:out value="${status.index}"/>" value="<c:out value="${colorClass}"/>" >
			<input type="hidden" name="packShipConfirmInputBean[<c:out value="${status.index}"/>].trackSerialNumber" id="trackSerialNumber<c:out value="${status.index}"/>" value="${status.current.trackSerialNumber}"/>
			<input type="hidden" name="packShipConfirmInputBean[<c:out value="${status.index}"/>].missingSerialNumber" id="missingSerialNumber<c:out value="${status.index}"/>" value="${status.current.missingSerialNumber}"/>

			<c:set var="dataCount" value='${dataCount+1}'/>
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
   
   <!-- If the collection is empty say no data found -->
   <c:if test="${empty ltltlViewBeanCollection}" >

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
<input name="action" id="action" value="" type="hidden">
<!-- Store all search criteria in hidden elements, need this to requery the database after updates -->
<%--<tcmis:saveRequestParameter/>--%>

 </div>
<!-- Hidden elements end -->

</div> <!-- close of backGroundContent -->
</div> <!-- close of interface -->

</tcmis:form>
</body>
</html:html>