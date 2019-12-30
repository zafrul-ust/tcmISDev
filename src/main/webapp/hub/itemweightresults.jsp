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
<script type="text/javascript" src="/js/hub/itemweight.js"></script>

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

<script language="JavaScript" type="text/javascript">
<!--
//add all the javascript messages here, this for internationalization of client side javascript messages
var messagesData = new Array();
messagesData={alert:"<fmt:message key="label.alert"/>",and:"<fmt:message key="label.and"/>",
validvalues:"<fmt:message key="label.validvalues"/>",submitOnlyOnce:"<fmt:message key="label.submitOnlyOnce"/>", itemInteger:"<fmt:message key="error.item.integer"/>",
cannotbeempty:"<fmt:message key="label.musthavevalueinthisfield"/>",
mustbeanumber:"<fmt:message key="label.mustbeanumberinthisfield"/>",
ui:"<fmt:message key="label.unitofissue"/>",
grossWeightLbs:"<fmt:message key="label.grossweightlbs"/>",
cubicFeet:"<fmt:message key="label.cubicfeet"/>",
containersPerUi:"<fmt:message key="label.containerperunit"/>",
maxNsnPerBox:"<fmt:message key="label.maxnsnperbox"/>",
maxNsnPerPallet:"<fmt:message key="label.maxnsnperpallet"/>",
externalContainer:"<fmt:message key="label.externalcontainer"/>"
};

//set this if you want to result table with FIXED width
//parent.resultWidthResize=false;
// -->
</script>
</head>

<body bgcolor="#ffffff" onload="myResultOnload()">

<tcmis:form action="/itemweightresults.do" onsubmit="return submitFrameOnlyOnce();">

<!-- Check if the user has permissions and needs to see the update links,set the variable you use in javascript to true.
     The default value of showUpdateLinks is false.
-->
<tcmis:inventoryGroupPermission indicator="true" userGroupId="Receiving">
 <script type="text/javascript">
 <!--
  showUpdateLinks = true;
 //-->
 </script>
</tcmis:inventoryGroupPermission>

<!-- You can build your error messages below. But we want to trigger the YUI pop-up from the main page.
So this is just used to feed the YUI pop-up in the main page.
Similar divs would have to be built to show any other messages in a pop-up.-->
<!-- Error Messages Begins -->
<div id="errorMessagesAreaBody" style="display:none;">
 <html:errors/>
 ${tcmISError}
</div>

<script type="text/javascript">
<!--
/*YAHOO.namespace("example.aqua");
YAHOO.util.Event.addListener(window, "load", init);*/

/*Check if there is any error messages to show and set the variable you use in javascript to true or false.*/
<c:choose>
   <c:when test="${requestScope['org.apache.struts.action.ERROR'] == null and empty requestScope['tcmISError']}">
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

<c:if test="${itemWeightCollection != null}" >
<!-- Search results start -->

<c:set var="colorClass" value=''/>
<c:set var="dataCount" value='${0}'/>

 <!-- If you want your results section to span only 50% set this to 50% and your main table will be 100% -->
 <c:if test="${!empty itemWeightCollection}" >

    <table width="100%" class="tableResults" id="resultsPageTable" border="0" cellpadding="0" cellspacing="0">
    <c:forEach var="pageNameViewBean" items="${itemWeightCollection}" varStatus="status">

    <c:if test="${status.index % 10 == 0}">
    <!-- Need to print the header every 10 rows-->
    <tr>
	    <th width="2%"><fmt:message key="label.ok"/></th>
	    <th width="5%"><fmt:message key="label.nsn"/></th>
	    <th width="5%"><fmt:message key="label.unitofissue"/></th>
	    <th width="5%"><fmt:message key="label.grossweightlbs"/></th>
	    <th width="5%"><fmt:message key="label.cubicfeet"/></th>
	    <%--<th width="5%"><fmt:message key="label.containerperunit"/></th>--%>
	    <th width="5%"><fmt:message key="label.maxnsnperbox"/></th>
	    <th width="5%"><fmt:message key="label.maxnsnperpallet"/></th>
	    <th width="5%"><fmt:message key="label.externalcontainer"/></th>
	    <th width="5%"><fmt:message key="label.verifiedby"/></th>
	    <th width="5%"><fmt:message key="label.verified"/> <fmt:message key="label.date"/> (<fmt:message key="label.mmddyyyy"/>)</th>
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
<%--
	<c:if test="${!empty status.current.verifiedBy}">
    <tr class="<c:out value="${colorClass}"/>" id="rowId<c:out value="${status.index}"/>">
      	<td class="optionTitleRight">
		</td>
      	<td class="optionTitleRight">
           <input type="hidden" name="PolchemNsnVerificationBean[${dataCount}].nsn" id="nsn_${dataCount}" value="${status.current.nsn}"/>
		   ${status.current.nsn}
		</td>
		<td>
            ${status.current.ui}
		</td>
		<td>
            ${status.current.grossWeightLbs}
		</td>
		<td>
            ${status.current.cubicFeet}
		</td>
--%><%--		<td>
            ${status.current.containersPerUi}
		</td>--%><%--
		<td>
            ${status.current.maxNsnPerBox}
		</td>
		<td>
            ${status.current.maxNsnPerPallet}
		</td>
		<td>
            ${status.current.externalContainer}
		</td>
		<td>
            ${status.current.verifiedBy}
		</td>
		<td>
		<fmt:formatDate var="vDate" value="${status.current.verifiedDate}" pattern="MM/dd/yyyy"/>
            ${vDate}
		</td>
	  </tr>
	</c:if>--%>

	<%--<c:if test="${empty status.current.verifiedBy}">--%>
    <tr class="<c:out value="${colorClass}"/>" id="rowId<c:out value="${status.index}"/>">
      	<td class="optionTitleRight">
           <input type="checkbox" class="radioBtn" name="PolchemNsnVerificationBean[${dataCount}].ok" id="ok_${dataCount}" value="ok" onclick="okClick(${dataCount})" />
		</td>
      	<td class="optionTitleRight">
           <input type="hidden" name="PolchemNsnVerificationBean[${dataCount}].nsn" id="nsn_${dataCount}" value="${status.current.nsn}"/>
		   ${status.current.nsn}
		</td>
		<td>
            <input name="PolchemNsnVerificationBean[${dataCount}].ui" id="ui_${dataCount}" type="text" class="inputBox" maxlength="30" size="3" value="${status.current.ui}" />
		</td>
		<td>
            <input name="PolchemNsnVerificationBean[${dataCount}].grossWeightLbs" id="grossWeightLbs_${dataCount}" type="text" class="inputBox" maxlength="30" size="8" value="${status.current.grossWeightLbs}" />
		</td>
		<td>
            <input name="PolchemNsnVerificationBean[${dataCount}].cubicFeet" id="cubicFeet_${dataCount}" type="text" class="inputBox" maxlength="30" size="8" value="${status.current.cubicFeet}" />
		</td>
<%--		<td>
            <input name="PolchemNsnVerificationBean[${dataCount}].containersPerUi" id="containersPerUi_${dataCount}" type="text" class="inputBox" maxlength="30" size="3" value="${status.current.containersPerUi}" />
		</td>--%>
		<td>
            <input name="PolchemNsnVerificationBean[${dataCount}].maxNsnPerBox" id="maxNsnPerBox_${dataCount}" type="text" class="inputBox" maxlength="30" size="3" value="${status.current.maxNsnPerBox}" />
		</td>
		<td>
            <input name="PolchemNsnVerificationBean[${dataCount}].maxNsnPerPallet" id="maxNsnPerPallet_${dataCount}" type="text" class="inputBox" maxlength="30" size="8" value="${status.current.maxNsnPerPallet}" />
		</td>
		<td>
            <input name="PolchemNsnVerificationBean[${dataCount}].externalContainer" id="externalContainer_${dataCount}" type="text" class="inputBox" maxlength="1" size="1" value="${status.current.externalContainer}" />
		</td>
		<td>
            <input name="PolchemNsnVerificationBean[${dataCount}].verifiedBy" id="verifiedBy_${dataCount}" type="text" class="inputBox" maxlength="30" size="8" value="${status.current.verifiedBy}" />
		</td>
		<td>
		<fmt:formatDate var="vDate" value="${status.current.verifiedDate}" pattern="MM/dd/yyyy"/>
            ${vDate}
		</td>
	  </tr>
	<%--</c:if>--%>

   <c:set var="dataCount" value='${dataCount+1}'/>

   </c:forEach>
   </table>
   </c:if>
   <!-- If the collection is empty say no data found -->
   <c:if test="${empty itemWeightCollection}" >

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
<input name="nsn" id="nsn" value="${param.nsn}" type="hidden"/>
<input name="action" id="action" value="update" type="hidden"/>
<input name="totalLines" id="totalLines" value="<c:out value="${dataCount}"/>" type="hidden">

<!-- can use on read-only pages only. Store all search criteria in hidden elements, need this to requery the database after updates -->

<input name="minHeight" id="minHeight" type="hidden" value="0">
 </div>
<!-- Hidden elements end -->

</div> <!-- close of backGroundContent -->
</div> <!-- close of interface -->

</tcmis:form>
</body>
</html:html>