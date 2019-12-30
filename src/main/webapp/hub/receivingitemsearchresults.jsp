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

<!--Use this tag to get the correct CSS class.
This looks at what the user's preffered font size and which application he is viewing to set the correct CSS. -->
<tcmis:fontSizeCss />
<!-- CSS for YUI -->

<link rel="stylesheet" type="text/css" href="/yui/build/container/assets/container.css" />
<!-- Add any other stylesheets you need for the page here -->

<script src="/js/common/formchek.js" language="JavaScript"></script>
<script src="/js/common/commonutil.js" language="JavaScript"></script>
<script src="/js/common/resultiframeresize.js" language="JavaScript"></script>
<!-- This handels which key press events are disabeled on this page -->
<script src="/js/common/disableKeys.js" language="JavaScript"></script>
<!-- This handels the menu style and what happens to the right click on the whole page -->
<script type="text/javascript" src="/js/menu/milonic_src.js"></script>
<script type="text/javascript" src="/js/menu/mmenudom.js"></script>
<script type="text/javascript" src="/js/menu/mainmenudata.js"></script>
<script type="text/javascript" src="/js/menu/contextmenu.js"></script>
<%@ include file="/common/rightclickmenudata.jsp" %>

<!-- Add any other javascript you need for the page here -->
<script type="text/javascript" src="/js/hub/receivingitemsearch.js"></script>

<!-- This is for the YUI, uncomment if you will use this -->
<!--<script type="text/javascript" src="/yui/build/yahoo/yahoo.js" ></script>
<script type="text/javascript" src="/yui/build/event/event.js" ></script>
<script type="text/javascript" src="/yui/build/dom/dom.js" ></script>
<script type="text/javascript" src="/yui/build/animation/animation.js" ></script>
<script type="text/javascript" src="/yui/build/container/container.js"></script>
<script type="text/javascript" src="/js/common/waitDialog.js"></script>
<script type="text/javascript" src="/yui/build/dragdrop/dragdrop.js" ></script>
-->

<title>
<fmt:message key="searchpersonnel.label.title"/>
</title>

<script language="JavaScript" type="text/javascript">
<!--
//add all the javascript messages here, this for internationalization of client side javascript messages
var messagesData = new Array();
messagesData={alert:"<fmt:message key="label.alert"/>",and:"<fmt:message key="label.and"/>",
submitOnlyOnce:"<fmt:message key="label.submitOnlyOnce"/>",
selectedItem:"<fmt:message key="label.selecteditem"/>",
recordFound:"<fmt:message key="label.recordFound"/>",
searchDuration:"<fmt:message key="label.searchDuration"/>",
minutes:"<fmt:message key="label.minutes"/>",
seconds:"<fmt:message key="label.seconds"/>",
itemInteger:"<fmt:message key="error.item.integer"/>"};

// -->
</script>
</head>

<body bgcolor="#ffffff" onload="myOnload()">

<tcmis:form action="/receivingitemsearchresults.do" onsubmit="return submitFrameOnlyOnce();">

<!-- Check if the user has permissions and needs to see the update links. Set the variable you use in javascript to true.-->
 <script type="text/javascript">
 <!--
  showUpdateLinks = false;
 //-->
 </script>

<!-- You can build your error messages below. But we want to trigger the YUI pop-up from the main page.
So this is just used to feed the YUI pop-up in the main page.
Similar divs would have to be built to show any other messages in a pop-up.-->
<!-- Error Messages Begins -->
<div id="errorMessagesAreaBody" style="display:none;">
 <html:errors/>
</div>

<script type="text/javascript">
<!--
/*YAHOO.namespace("example.aqua");
YAHOO.util.Event.addListener(window, "load", init);*/

/*Check if there is any error messages to show and set the variable you use in javascript to true or false.*/
<c:choose>
   <c:when test="${requestScope['org.apache.struts.action.ERROR'] == null}">
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

<c:if test="${receivingItemBeanCollection != null}" >
<c:set var="colorClass" value=''/>
<c:set var="rowCount" value='${0}'/>
<!-- Search results start -->
 <c:if test="${!empty receivingItemBeanCollection}" >
    <table width="100%" class="tableResults" id="resultsPageTable" border="0" cellpadding="0" cellspacing="0">
    <c:set var="colorClass" value=''/>
    <c:set var="rowCount" value='${0}'/>
    <bean:size collection="${receivingItemBeanCollection}" id="resultSize"/>
    <c:forEach var="receivintItemBean" items="${receivingItemBeanCollection}" varStatus="status">

    <c:if test="${status.index % 10 == 0}">
       <!-- Need to print the header every 10 rows-->
       <tr>
         <th width="5%"><fmt:message key="label.itemid"/></th>
         <th width="5%"><fmt:message key="label.type"/></th>
         <th width="25%"><fmt:message key="label.description"/></th>
         <th width="25%"><fmt:message key="label.packaging"/></th>
         <%--<th width="5%"><fmt:message key="label.partno"/></th>--%>
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

    <tr class='${colorClass}' onmouseup="selectRow('${status.index}')" style="cursor:pointer;" id='rowId${status.index}'>
      <td width="5%">${status.current.itemId}</td>
      <td width="5%">${status.current.itemType}</td>
      <td width="25%">${status.current.itemDesc}</td>
      <td width="25%">${status.current.packaging}</td>
      <!--<td width="5%"></td>-->
    </tr>

   <!-- hidden columns -->
   <input type="hidden" id="colorClass${status.index}" value="${colorClass}"/>
   <input type="hidden" id="itemId${status.index}" value="${status.current.itemId}" />
   <!-- end of hidden columns -->
   <c:set var="rowCount" value='${rowCount+1}'/>
   </c:forEach>
   </table>
 </c:if>

   <!-- If the collection is empty say no data found -->
   <c:if test="${empty receivingItemBeanCollection}" >
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
  
<input name="totalLines" id="totalLines" value="${rowCount}" type="hidden"/>
<input name="displayElementId" id="displayElementId" type="hidden" value="${param.displayElementId}"/>
<input name="valueElementId" id="valueElementId" type="hidden" value="${param.valueElementId}"/>
<!-- Store all search criteria in hidden elements, need this to requery the database after updates -->
<tcmis:saveRequestParameter/>

 </div>
<!-- Hidden elements end -->

</div> <!-- close of backGroundContent -->
</div> <!-- close of interface -->

</tcmis:form>

</body>
</html:html>