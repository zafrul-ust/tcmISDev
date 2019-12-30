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
<script type="text/javascript" src="/js/catalog/partnotes.js"></script>

<!-- These are for the Grid, uncomment if you are going to use the grid -->
<%--
<script type="text/javascript" src="/js/dhtmlxGrid/dhtmlXCommon.js"></script>
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
validvalues:"<fmt:message key="label.validvalues"/>",submitOnlyOnce:"<fmt:message key="label.submitOnlyOnce"/>", itemInteger:"<fmt:message key="error.item.integer"/>"};

//set this if you want to result table with FIXED width
parent.resultWidthResize=true;
// -->
</script>
</head>

<body bgcolor="#ffffff" onload="myResultOnload()">

<tcmis:form action="/partnotesresults.do" onsubmit="return submitFrameOnlyOnce();">

<!-- Check if the user has permissions and needs to see the update links,set the variable you use in javascript to true.
     The default value of showUpdateLinks is false.
-->
<%-- for example code, disable the permission 
	<tcmis:inventoryGroupPermission indicator="true" userGroupId="Receiving">
--%>
 <script type="text/javascript">
 <!--
  showUpdateLinks = true;
 //-->
 </script>
<%--
	</tcmis:inventoryGroupPermission>
--%>
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

<c:if test="${partNotesColl != null}" >
<!-- Search results start -->

<c:set var="colorClass" value=''/>
<c:set var="dataCount" value='${0}'/>

 <!-- If you want your results section to span only 50% set this to 50% and your main table will be 100% -->
 <c:if test="${!empty partNotesColl}" >
    <table width="100%" class="tableResults" id="resultsPageTable" border="0" cellpadding="0" cellspacing="0">
    <c:forEach var="note" items="${partNotesColl}" varStatus="status">
    <!-- Need to print the header every 10 rows-->
    <c:if test="${status.index % 10 == 0}">
    <tr align="center">
      <th width="2%"><fmt:message key="label.ok"/></th>
      <th width="5%"><fmt:message key="label.date"/></th>
      <th width="5%"><fmt:message key="label.user"/></th>
      <th width="5%"><fmt:message key="label.catalog"/></th>
      <th width="5%"><fmt:message key="label.partnum"/></th>
      <th width="25%"><fmt:message key="label.comments"/></th>
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

    <c:if test="${status.index == 0}">
   <tr align="center" id='rowId${status.index}'>
     <td width="2%" class="optionTitleBoldCenter">
     	<input name="CatPartCommentBean[0].ok" id="ok_0" value="new" type="hidden"/><fmt:message key="label.new"/>
     	<input name="CatPartCommentBean[0].catalogId" id="catalogId_0" value="${param.catalogId}" type="hidden"/>
     	<input name="CatPartCommentBean[0].companyId" id="companyId_0" value="${param.companyId}" type="hidden"/>
     	<input name="CatPartCommentBean[0].catalogCompanyId" id="catalogCompanyId_0" value="${param.catalogCompanyId}" type="hidden"/>
     	<input name="CatPartCommentBean[0].catPartNo" id="catPartNo_0" value="${param.catPartNo}" type="hidden"/>
     	<input name="CatPartCommentBean[0].enteredBy" id="enteredBy_0" value="${personnelBean.personnelId}" type="hidden"/>
	 </td>
     <td width="5%">
        <c:set var="todayDate">
			<tcmis:getDateTag numberOfDaysFromToday="0"/>
		</c:set>
        ${todayDate}
     </td>
     <td width="5%">${personnelBean.personnelId}</td>
     <td width="5%">${param.catalogId}</td>
     <td width="5%">${param.catPartNo}</td>
     <td width="25%" class="alignLeft">
     	<textarea name="CatPartCommentBean[0].comments"  id="comments_0" cols="40" rows="3" class="inputBox"></textarea>
	 </td>
   </tr>

   <c:set var="dataCount" value='${dataCount+1}'/>
	</c:if>

   <tr align="center" class="${colorClass}" id='rowId${status.index}'>
     <td width="2%">
     	<input name="CatPartCommentBean[${dataCount}].ok" id="ok_${dataCount}" value="ok" type="checkbox"/>
     	<input name="CatPartCommentBean[${dataCount}].catalogId" id="catalogId_${dataCount}" value="${note.catalogId}" type="hidden"/>
     	<input name="CatPartCommentBean[${dataCount}].catPartNo" id="catPartNo_${dataCount}" value="${note.catPartNo}" type="hidden"/>
     	<input name="CatPartCommentBean[${dataCount}].companyId" id="companyId_${dataCount}" value="${param.companyId}" type="hidden"/>
     	<input name="CatPartCommentBean[${dataCount}].catalogCompanyId" id="catalogCompanyId_${dataCount}" value="${param.catalogCompanyId}" type="hidden"/>
     	<input name="CatPartCommentBean[${dataCount}].enteredBy" id="enteredBy_${dataCount}" value="${note.enteredBy}" type="hidden"/>
     	<input name="CatPartCommentBean[${dataCount}].commentId" id="commentId_${dataCount}" value="${note.commentId}" type="hidden"/>
	 </td>
     <td width="5%">
        <fmt:formatDate var="fmtTxnDate" value="${note.dateEntered}" pattern="MM/dd/yyyy kk:mm"/>
        <c:out value="${fmtTxnDate}"/>
     </td>
     <td width="5%">${note.enteredBy}</td>
     <td width="5%">${note.catalogId}</td>
     <td width="5%">${note.catPartNo}</td>
     <td width="5%" class="alignLeft">
     	<textarea name="CatPartCommentBean[${dataCount}].comments"  id="comments_${dataCount}" cols="40" rows="5" class="inputBox">${note.comments}</textarea>
	 </td>
   </tr>

   <c:set var="dataCount" value='${dataCount+1}'/>

   </c:forEach>

   </table>
   </c:if>
   <!-- If the collection is empty say no data found -->
   <c:if test="${empty partNotesColl}" >
   <!-- If the collection is empty say no data found -->
    <table width="100%" class="tableResults" id="resultsPageTable" border="0" cellpadding="0" cellspacing="0">
    <!-- Need to print the header every 10 rows-->
    <tr align="center">
      <th width="2%"><fmt:message key="label.ok"/></th>
      <th width="5%"><fmt:message key="label.date"/></th>
      <th width="5%"><fmt:message key="label.user"/></th>
      <th width="5%"><fmt:message key="label.catalog"/></th>
      <th width="5%"><fmt:message key="label.partnum"/></th>
      <th width="25%"><fmt:message key="label.comments"/></th>
    </tr>
      <c:set var="colorClass" value='alt'/>

   <tr align="center" id='rowId${status.index}'>
     <td width="2%" class="optionTitleBoldCenter">
     	<input name="CatPartCommentBean[0].ok" id="ok_0" value="new" type="hidden"/><fmt:message key="label.new"/>
     	<input name="CatPartCommentBean[0].catalogId" id="catalogId_0" value="${param.catalogId}" type="hidden"/>
     	<input name="CatPartCommentBean[0].catPartNo" id="catPartNo_0" value="${param.catPartNo}" type="hidden"/>
     	<input name="CatPartCommentBean[0].enteredBy" id="enteredBy_0" value="${personnelBean.personnelId}" type="hidden"/>
     	<input name="CatPartCommentBean[0].companyId" id="companyId_0" value="${param.companyId}" type="hidden"/>
     	<input name="CatPartCommentBean[0].catalogCompanyId" id="catalogCompanyId_0" value="${param.catalogCompanyId}" type="hidden"/>
	 </td>
     <td width="5%">
        <c:set var="todayDate">
			<tcmis:getDateTag numberOfDaysFromToday="0"/>
		</c:set>
        ${todayDate}
     </td>
     <td width="5%">${personnelBean.personnelId}</td>
     <td width="5%">${param.catalogId}</td>
     <td width="5%">${param.catPartNo}</td>
     <td width="25%" class="alignLeft">
     	<textarea name="CatPartCommentBean[0].comments" id="comments_0" cols="40" rows="3" class="inputBox"></textarea>
	 </td>
   </tr>
   <c:set var="dataCount" value='${dataCount+1}'/>
   </table>
   
   </c:if>


<!-- Search results end -->
</c:if>

<!-- Hidden element start -->
<div id="hiddenElements" style="display: none;">
<input name="catalogId" id="catalogId" value="${param.catalogId}" type="hidden"/>
<input name="catPartNo" id="catPartNo" value="${param.catPartNo}" type="hidden"/>
<input name="uAction" id="uAction" value="update" type="hidden"/>
<input name="totalLines" id="totalLines" value="${dataCount}" type="hidden">
<input name="companyId" id="companyId" value="${param.companyId}" type="hidden"/>
<input name="catalogCompanyId" id="catalogCompanyId" value="${param.catalogCompanyId}" type="hidden"/>

<!-- can use on read-only pages only. Store all search criteria in hidden elements, need this to requery the database after updates -->

 </div>
<!-- Hidden elements end -->

</div> <!-- close of backGroundContent -->
</div> <!-- close of interface -->

</tcmis:form>
</body>
</html:html>