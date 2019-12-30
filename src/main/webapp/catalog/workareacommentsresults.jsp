<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
	"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="/WEB-INF/tcmis.tld" prefix="tcmis" %>

<html>
<head>
<meta http-equiv="content-type" content="text/html; charset=UTF-8">
<meta http-equiv="expires" content="-1">
<link rel="shortcut icon" href="https://www.tcmis.com/images/buttons/tcmIS.ico"></link>

<%--This is used to define the date format that will be used on the page.--%>
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
--%>

<!-- Add any other javascript you need for the page here -->
<script type="text/javascript" src="/js/catalog/workareacomments.js"></script>

<script language="JavaScript" type="text/javascript">
<!--
//add all the javascript messages here, this for internationalization of client side javascript messages
var messagesData = new Array();
messagesData={alert:"<fmt:message key="label.alert"/>",and:"<fmt:message key="label.and"/>",recordFound:"<fmt:message key="label.recordFound"/>",
searchDuration:"<fmt:message key="label.searchDuration"/>",minutes:"<fmt:message key="label.minutes"/>",seconds:"<fmt:message key="label.seconds"/>",
validvalues:"<fmt:message key="label.validvalues"/>",submitOnlyOnce:"<fmt:message key="label.submitOnlyOnce"/>", itemInteger:"<fmt:message key="error.item.integer"/>"};


function editcommentevent(rowid)
{
/*
	var editcommentspopup = new PopupWindow("editcommentsdiv");
	editcommentspopup.setSize(400,430);
	editcommentspopup.autoHide();
   	editcommentspopup.offsetX = -400;
	editcommentspopup.offsetY = 25;
*/
	var commentElements ="<B>Edit Comments:</B><BR><FORM name='edit_comment_form'><TEXTAREA name=\"txtcomments\" id=\"txtcomments\" rows=\"10\" cols=\"30\">"+document.getElementById("comments"+rowid+"").value+"</TEXTAREA>";
	commentElements +="<TABLE BORDER=\"0\" CELLSPACING=\"0\" CELLPADDING=\"3\" WIDTH=\"100%\">";
	commentElements +="<TR VALIGN=\"MIDDLE\">";
	commentElements +="<TD CLASS=\"announce\" HEIGHT=\"35\" WIDTH=\"10%\" ALIGN=\"LEFT\">";
	commentElements +="<input type=\"button\" name=\"buttonTransfer\" value=\"Ok\" onclick=\"window.frames['resultFrame'].sendComments("+rowid+",document.getElementById('txtcomments').value); showErrorMessagesWin.hide();\" onmouseover=\"className='SUBMITHOVER'\" onmouseout=\"className='SUBMIT'\" class=\"SUBMIT\">";
	commentElements +="</TD></TR></TABLE></FORM>";
	document.getElementById('errorMessagesAreaBody').innerHTML =  commentElements;
	document.getElementById('txtcomments').innerHTML = document.getElementById('comment' + rowid).innerHTML;
	
	parent.document.getElementById('errorMessagesAreaTitle').innerHTML = "Edit Comment";
	parent.showErrorMessages();
//	editcommentspopup.populate(commentElements);
//   editcommentspopup.showPopup("anchor"+rowid+"");
}

function sendComments(row_id,value) {
 document.getElementById('comment' + row_id).innerHTML = value;
 document.getElementById('comments' + row_id).value = value;
 document.getElementById('updateStatus' + row_id).value = "changed"; 
}

function submitUpdate() {
	var now = new Date();
	parent.frames['searchFrame'].document.getElementById("startSearchTime").value = now.getTime();
   	parent.showPleaseWait();
	document.genericForm.submit();
}

//set this if you want to result table with FIXED width
//parent.resultWidthResize=false;
// -->
</script>
</head>

<body bgcolor="#ffffff" onload="standardResultOnLoad()">

<tcmis:form action="/workareacommentsresults.do" onsubmit="return submitFrameOnlyOnce();">

<!-- Check if the user has permissions and needs to see the update links,set the variable you use in javascript to true.
     The default value of showUpdateLinks is false.
-->
 <script type="text/javascript">
 <!--
  showUpdateLinks = true;
 //-->
 </script>

<!-- You can build your error messages below. But we want to trigger the YUI pop-up from the main page.
So this is just used to feed the YUI pop-up in the main page.
Similar divs would have to be built to show any other messages in a pop-up.-->
<!-- Error Messages Begins -->
<div id="errorMessagesAreaBody" style="display:none;">
  ${tcmISError}<br/>
  <c:forEach items="${tcmISErrors}" varStatus="status">
  	${status.current}<br/>
  </c:forEach>
</div>

<script type="text/javascript">
<!--
/*YAHOO.namespace("example.aqua");
YAHOO.util.Event.addListener(window, "load", init);*/

/*Check if there is any error messages to show and set the variable you use in javascript to true or false.*/
<c:choose>
   <c:when test="${empty tcmISErrors and empty tcmISError}">
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

<c:if test="${workAreaCommentsBeanCollection != null}" >
<!-- Search results start -->

<c:set var="colorClass" value=''/>
<c:set var="dataCount" value='${0}'/>
<c:set var="deleteComment" value=''/>

 <!-- If you want your results section to span only 50% set this to 50% and your main table will be 100% -->
 <c:if test="${!empty workAreaCommentsBeanCollection}" >

    <table width="100%" class="tableResults" id="resultsPageTable" border="0" cellpadding="0" cellspacing="0">
    <c:forEach var="pageNameViewBean" items="${workAreaCommentsBeanCollection}" varStatus="status">

    <c:if test="${status.index % 10 == 0}">
    <!-- Need to print the header every 10 rows-->
    <tr>
<th width="5%" ></th>
<%-- 
<th width="2%" ><fmt:message key="label.edit"/></th>
--%>
<th width="5%" ><fmt:message key="workareacomments.label.by"/></th>
<th width="5%" ><fmt:message key="label.date"/></th>
<th width="5%"><fmt:message key="label.comments"/></th>
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

   <!-- Check if the user has permissions to update the specific facility/inventory group etc.
        We need to cehck again as the result set might have more that one facility/Inv Group etc.
        One of showing readonly version to the user is to store a variable and check that variable, instead
        of checking the tag multiple times.
   -->
   <tr class="${colorClass}" id="commentrowId${status.index}" onmouseup="catchcommentevent('${status.index}')">
   <c:set var="dataCount" value='${dataCount+1}'/>

  <input type="hidden" name="colorClass${status.index}" id="colorClass${status.index}" value="${colorClass}" />
  <input type="hidden" name="bean[${status.index}].commentId" value="${status.current.commentId}" />

  <input type="hidden" name="bean[${status.index}].updateStatus" id="updateStatus${status.index}" value="" />

  
  <td width="5%" id="delete${status.index}"">
   <tcmis:facilityPermission indicator="true" userGroupId="Administrator" facilityId="${param.facilityId}">
     <c:set var="deleteComment" value='Yes'/>
   </tcmis:facilityPermission>

   <tcmis:facilityPermission indicator="true" userGroupId="SuperUser">
     <c:set var="deleteComment" value='Yes'/>
   </tcmis:facilityPermission>
   
   <c:if test="${personnelBean.personnelId == status.current.enteredBy}" >
    <c:set var="deleteComment" value='Yes'/>
   </c:if>

   <c:if test="${ empty status.current.commentId }"> 
		<input type="checkbox" name="bean[${status.index}].deleteCheckBox" id="deleteCheckBox${status.index}" value="New"><b><fmt:message key="label.new"/></b>
   </c:if>
   <c:if test="${! empty status.current.commentId }"> 
   <c:choose>
    <c:when test="${deleteComment == 'Yes'}">
     	<input type="checkbox" name="bean[${status.index}].deleteCheckBox" id="deleteCheckBox${status.index}" value="${status.current.commentId}"><b><fmt:message key="label.delete"/></b>
    </c:when>
    <c:otherwise>
    &nbsp;
    </c:otherwise>
   </c:choose>
   </c:if>
  </td>

<%-- 
  <td width="2%" align="center"><a href="#" onClick="editcommentevent('${status.index}');return false;" name="anchor${status.index}" id="anchor${status.index}">&diams;</a></td>
  --%>
  <td width="5%" id="enteredBy${status.index}">${status.current.fullName}</td>
  <td width="5%" id="date${status.index}">
  <fmt:formatDate var="formattedEnteredDate" pattern="${dateFormatPattern}" value="${status.current.dateEntered}"/>
	${formattedEnteredDate}
  </td>
<td width="5%" id="comment${status.index}">
<textarea name="bean[${status.index}].comments" id="comments${status.index}" rows="5" cols="40" class="inputBox">${status.current.comments}</textarea>
</td>
   </tr>


   </c:forEach>
   </table>
   </c:if>
   <!-- If the collection is empty say no data found -->
   <c:if test="${empty workAreaCommentsBeanCollection}" >

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
<input name="totalLines" id="totalLines" value="<c:out value="${dataCount}"/>" type="hidden">
<input type="hidden" name="catPartNo" value="${param.catPartNo}"/>
<input type="hidden" name="partGroupNo" value="${param.partGroupNo}"/>
<input type="hidden" name="applicationId" value="${param.applicationId}"/>
<input type="hidden" name="facilityId" value="${param.facilityId}"/>
<input type="hidden" name="catalogId" value="${param.catalogId}"/>
<input type="hidden" name="submitUpdate" value="submitUpdate"/>


<%-- can use on read-only pages only. Store all search criteria in hidden elements, need this to requery the database after updates
Do not use <tcmis:saveRequestParameter/> on pages with updates
The purpose of this tag was to save the search criteria on the result page.
When there is a form in the result section and gets submitted to the server there is no difference between
the search parameters and the result parameters. This causes duplicates of the result section parameters.
This can potentially cause lot of mix-ups.
 --%>
<%--<tcmis:saveRequestParameter/>--%>

<%--This is the minimum height of the result section you want to display--%>
<input name="minHeight" id="minHeight" type="hidden" value="0"/>
 </div>
<!-- Hidden elements end -->

</div> <!-- close of backGroundContent -->
</div> <!-- close of interface -->

</tcmis:form>
</body>
</html>