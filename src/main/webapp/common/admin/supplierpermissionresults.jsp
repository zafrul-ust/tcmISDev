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
<script type="text/javascript" src="/js/common/admin/supplierpermissionresults.js"></script>

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
<fmt:message key="supplierpermission.title"/>
</title>

<script language="JavaScript" type="text/javascript">
<!--
//add all the javascript messages here, this for internationalization of client side javascript messages
var messagesData = new Array();
messagesData={alert:"<fmt:message key="label.alert"/>",
	and:"<fmt:message key="label.and"/>",
	validvalues:"<fmt:message key="label.validvalues"/>",
	submitOnlyOnce:"<fmt:message key="label.submitOnlyOnce"/>",
	itemInteger:"<fmt:message key="error.item.integer"/>"};
// -->
</script>
</head>

<body bgcolor="#ffffff" onload="myResultOnload()">

<tcmis:form action="/supplierpermissionresults.do" onsubmit="return submitFrameOnlyOnce();">

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

<c:if test="${supplierDetailBeanCollection != null}" >
<!-- Search results start -->

<c:set var="colorClass" value=''/>
<c:set var="dataCount" value='${0}'/>
<c:set var="userGroupCount" value='${0}'/>
<c:set var="isAdmin" value=''/>

 <!-- If you want your results section to span only 50% set this to 50% and your main table will be 100% -->
 <c:if test="${!empty supplierDetailBeanCollection}" >

    <table width="100%" class="tableResults" id="resultsPageTable" border="0" cellpadding="0" cellspacing="0">
    <c:forEach var="userSupplierUgAdminOvBean" items="${supplierDetailBeanCollection}" varStatus="status">

    <c:if test="${status.index % 10 == 0}">
      <!-- Need to print the header every 10 rows-->
      <tr>
        <th width="5%"><fmt:message key="label.supplierid"/></th>
        <th width="5%"><fmt:message key="label.supplier"/></th>
	      <c:forEach var="userSupplierUgHeaderViewBean" items="${supplierHeaderBeanCollection}" varStatus="status1">
					<th width="5%"><fmt:message key="${status1.current.userGroupId}"/></th>
		    </c:forEach>
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
        <td width="5%" class="alignLeft"><c:out value="${status.current.supplier}"/></td>
	    <td width="5%" class="alignLeft"><c:out value="${status.current.supplierName}"/></td>
	    <c:set var="userAccess" value='${status.current.userAccess}'/>
			<c:set var="userGroupCollection" value='${status.current.userGroupAccess}'/>
	    <c:forEach var="userGroupAccessViewBean" items="${userGroupCollection}" varStatus="status2">
		    <input name="supplierPermissionInputBean[<c:out value="${userGroupCount}"/>].modified" id="modified<c:out value="${userGroupCount}"/>" type="hidden" value="">
		    <input name="supplierPermissionInputBean[<c:out value="${userGroupCount}"/>].companyId" id="companyId<c:out value="${userGroupCount}"/>" type="hidden" value="<c:out value="${status.current.companyId}"/>">
		    <input name="supplierPermissionInputBean[<c:out value="${userGroupCount}"/>].userId" id="userId<c:out value="${userGroupCount}"/>" type="hidden" value="<c:out value="${status.current.userId}"/>">
		    <input name="supplierPermissionInputBean[<c:out value="${userGroupCount}"/>].personnelId" id="personnelId<c:out value="${userGroupCount}"/>" type="hidden" value="<c:out value="${status.current.personnelId}"/>">
			<input name="supplierPermissionInputBean[<c:out value="${userGroupCount}"/>].supplier" id="supplier<c:out value="${userGroupCount}"/>" type="hidden" value="<c:out value="${status.current.supplier}"/>">
		    <input name="supplierPermissionInputBean[<c:out value="${userGroupCount}"/>].userGroupId" id="userGroupId<c:out value="${userGroupCount}"/>" type="hidden" value="<c:out value="${status2.current.userGroupId}"/>">
				<%-- check to see if the user group is a place holder --%>
		    <c:set var="tmpUserGroupId" value='${status2.current.userGroupId}'/>
		    <c:choose>
			    <c:when test="${tmpUserGroupId == 'xxDummyXX'}">
				    <td width="5%" class="optionTitleCenter"></td>
			    </c:when>
			    <c:otherwise>
				    
						<c:set var="userGroupAccess" value='${status2.current.userGroupAccess}'/>
						<c:choose>
							<c:when test="${userAccess == 'View'}">
								<c:choose>
									<c:when test="${userGroupAccess == '0'}">
										<td width="5%" class="optionTitleCenter"><input type="checkbox" class="" name="supplierPermissionInputBean[<c:out value="${userGroupCount}"/>].checked" id="checked<c:out value="${userGroupCount}"/>" value="" disabled="true"></td>
									</c:when>
									<c:otherwise>
										<input type="hidden" class="" name="supplierPermissionInputBean[<c:out value="${userGroupCount}"/>].checked" id="checked<c:out value="${userGroupCount}"/>" value="checked">
										<td width="5%" class="optionTitleCenter"><input type="checkbox" class="" name="checkedDisplay<c:out value="${userGroupCount}"/>" id="checkedDisplay<c:out value="${userGroupCount}"/>" value="" checked disabled="true"></td>
									</c:otherwise>
								</c:choose>
							</c:when>
							<c:otherwise>
								<c:set var="isAdmin" value='Yes'/>
								<c:choose>
									<c:when test="${userGroupAccess == '0'}">
										<input type="hidden" class="" name="supplierPermissionInputBean[<c:out value="${userGroupCount}"/>].checked" id="checked<c:out value="${userGroupCount}"/>" value="">
										<td width="5%" class="optionTitleCenter"><input type="checkbox" class="" name="checkedDisplay<c:out value="${userGroupCount}"/>" id="checkedDisplay<c:out value="${userGroupCount}"/>" value="" onclick="dataChanged('<c:out value="${userGroupCount}"/>')"></td>
									</c:when>
									<c:otherwise>
										<input type="hidden" class="" name="supplierPermissionInputBean[<c:out value="${userGroupCount}"/>].checked" id="checked<c:out value="${userGroupCount}"/>" value="checked">
										<td width="5%" class="optionTitleCenter"><input type="checkbox" class="" name="checkedDisplay<c:out value="${userGroupCount}"/>" id="checkedDisplay<c:out value="${userGroupCount}"/>" value="" checked onclick="dataChanged('<c:out value="${userGroupCount}"/>')"></td>
									</c:otherwise>
								</c:choose>
							</c:otherwise>
						</c:choose>

				  </c:otherwise>
		    </c:choose>

        <c:set var="userGroupCount" value='${userGroupCount+1}'/>
		  </c:forEach>
   </tr>

   <c:set var="dataCount" value='${dataCount+1}'/>

   </c:forEach>
   </table>
   </c:if>
   <!-- If the collection is empty say no data found -->
   <c:if test="${empty supplierDetailBeanCollection}" >

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
<input name="action" id="action" value="" type="hidden">
<input name="totalLines" id="totalLines" value="<c:out value="${dataCount}"/>" type="hidden">
<input name="personnelId" id="personnelId" value="${personnelId}" type="hidden">
<input name="companyId" id="companyId" value="${companyId}" type="hidden">
<input name="supplier" id="supplier" value="${supplierId}" type="hidden">
<!-- Store all search criteria in hidden elements, need this to requery the database after updates -->

 </div>
<!-- Hidden elements end -->

<!-- Check if the user has permissions and needs to see the update links,set the variable you use in javascript to true.
     The default value of showUpdateLinks is false.
-->
<script type="text/javascript">
<!--
  <c:choose>
   <c:when test="${isAdmin == 'Yes'}" >
       showUpdateLinks = true;
   </c:when>
   <c:otherwise>
       showUpdateLinks = false;
   </c:otherwise>
  </c:choose>
//-->
</script>

</div> <!-- close of backGroundContent -->
</div> <!-- close of interface -->

</tcmis:form>
</body>
</html:html>