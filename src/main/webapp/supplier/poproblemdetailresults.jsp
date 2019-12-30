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
<script type="text/javascript" src="/js/supplier/poproblemdetailresults.js"></script>

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

<title><fmt:message key="label.haas.problem.rejected"/></title>

<script language="JavaScript" type="text/javascript">
<!--
//add all the javascript messages here, this for internationalization of client side javascript messages
var messagesData = new Array();
messagesData={alert:"<fmt:message key="label.alert"/>",and:"<fmt:message key="label.and"/>",
            validvalues:"<fmt:message key="label.validvalues"/>",
            recordFound:"<fmt:message key="label.recordFound"/>",
            searchDuration:"<fmt:message key="label.searchDuration"/>",
            minutes:"<fmt:message key="label.minutes"/>",
            seconds:"<fmt:message key="label.seconds"/>",
            submitOnlyOnce:"<fmt:message key="label.submitOnlyOnce"/>"};
// -->

showUpdateLinks = true;
</script>
</head>

<body bgcolor="#ffffff" onload="myOnload()">

<tcmis:form action="/poproblemdetailresults.do" onsubmit="return submitFrameOnlyOnce();">
<%--<c:set var="dateFormatPattern"><fmt:message key="java.dateformat"/></c:set>
<c:set var="dateFormatLabel"><fmt:message key="jsp.dateformat"/></c:set>
<c:set var="datetimeFormatPattern"><fmt:message key="jsp.datetimeformat"/></c:set>--%>

<!-- Check if the user has permissions and needs to see the update links,set the variable you use in javascript to true.
     The default value of showUpdateLinks is false.
-->

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

<%--<c:if test="${ProbRejectBeans != null}" >--%>
<!-- Search results start -->
<c:set var="colorClass" value=''/>
<c:set var="dataCount" value='${0}'/>
<c:set var="adminCount" value='${0}'/>

 <!-- If you want your results section to span only 50% set this to 50% and your main table will be 100% -->

   <c:if test="${!empty ProbRejectBeans}">
            <table width="100%" class="tableResults" id="resultsPageTable" border="0" cellpadding="0" cellspacing="0">
                <c:set var="colorClass" value=''/>
                <c:set var="dataCount" value='${0}'/>
               <c:forEach var="ProbRejectBeans" items="${ProbRejectBeans}"
                           varStatus="viewBean">

                    <c:if test="${viewBean.index % 10 == 0}">
                        <!-- Need to print the header every 10 rows-->
                        <tr>                          
                          <th><fmt:message key="label.status"/></th>
					      <th><fmt:message key="label.po.num"/></th>
					      <th><fmt:message key="label.supplier"/></th>
					      <th><fmt:message key="label.shipto"/></th>
					      <th><fmt:message key="label.item"/></th>
					      <th><fmt:message key="label.qty"/></th>
					      <th><fmt:message key="label.price.each"/></th>
					      <th><fmt:message key="label.needdate"/></th>
					      <th><fmt:message key="label.carrier"/></th>
					      <th><fmt:message key="label.comments"/></th>
					      <th><fmt:message key="label.date.rejected"/></th>
					      <th><fmt:message key="label.user"/></th>
                        </tr>
                    </c:if>
                    <c:choose>
                        <c:when test="${viewBean.index % 2 == 0}">
                            <c:set var="colorClass" value=''/>
                        </c:when>
                        <c:otherwise>
                            <c:set var="colorClass" value='alt'/>
                        </c:otherwise>
                    </c:choose>

                    <tr onmouseup="highlightRow('${viewBean.index}')"class="${colorClass}" id="rowId${viewBean.index}">
                       <input name="colorClass${viewBean.index}"id="colorClass${viewBean.index}" value="${colorClass}" type="hidden" >
                         <td><c:out value="${viewBean.current.dbuyStatus}"/></td>
				        <td>
				           <c:out value="${viewBean.current.radianPo}"/>				          
				        </td>
				        <td><c:out value="${viewBean.current.supplierName}"/></td>
				        <td><c:out value="${viewBean.current.shipToLocationId}"/></td>
				        <td><c:out value="${viewBean.current.itemId}"/></td>
				        <td><c:out value="${viewBean.current.quantity}"/></td>
				        <td><c:out value="${viewBean.current.unitPrice}"/></td>
				        <td>
				           <fmt:formatDate var="fmtPromiseDate" value="${viewBean.current.promisedDate}" pattern="MM/dd/yyyy"/>
				           <c:out value="${fmtPromiseDate}"/>
				        </td>
				        <td><c:out value="${viewBean.current.carrier}"/></td>
				        <td><c:out value="${viewBean.current.comments}"/></td>
				        <td>
				           <fmt:formatDate var="fmtStatusDate" value="${viewBean.current.statusDate}" pattern="MM/dd/yyyy"/>
				          <c:out value="${fmtStatusDate}"/>
				        </td>
				        <td><c:out value="${viewBean.current.dbuyUserId}"/></td> 
                    </tr>
                    <c:set var="dataCount" value='${dataCount+1}'/>
                </c:forEach>
            </table>
        </c:if>
   <!-- If the collection is empty say no data found -->
   <c:if test="${empty ProbRejectBeans}" >

    <table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableNoData" id="resultsPageTable">
     <tr>
      <td width="100%">
       <fmt:message key="main.nodatafound"/>
      </td>
     </tr>
    </table>
   </c:if>

<!-- Search results end -->
<%--</c:if> --%>



<!-- Hidden element start -->
<div id="hiddenElements" style="display: none;">

<input name="totalLines" id="totalLines" value="${dataCount}" type="hidden"/>
<input name="action" id="action" value="" type="hidden"/>
<input name="radianPo" id="radianPo" value="${param.radianPo}" type="hidden"/>
<input name="poLine" id="poLine" value="${param.elementId2}" type="hidden"/>
<input name="itemId" id="itemId" value="${param.elementId3}" type="hidden"/>     
<!-- Store all search criteria in hidden elements, need this to requery the database after updates -->

 </div>
<!-- Hidden elements end -->

</div> <!-- close of backGroundContent -->
</div> <!-- close of interface -->

</tcmis:form>
</body>
</html:html>