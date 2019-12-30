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
<script type="text/javascript" src="/js/common/admin/workareaPermission.js"></script>

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
<fmt:message key="label.facilityPermissionsTitle"/>  ()
</title>

<script language="JavaScript" type="text/javascript">
<!--
//add all the javascript messages here, this for internationalization of client side javascript messages
var messagesData = new Array();
messagesData={alert:"<fmt:message key="label.alert"/>",and:"<fmt:message key="label.and"/>",
validvalues:"<fmt:message key="label.validvalues"/>",submitOnlyOnce:"<fmt:message key="label.submitOnlyOnce"/>",
itemInteger:"<fmt:message key="error.item.integer"/>"
};
// -->
</script>
</head>

<body bgcolor="#ffffff" onload="myOnload()">

<tcmis:form action="/cabinetpermissionresults.do" onsubmit="return submitOnlyOnce();">
<%-- <form name="genericForm" method="post" action="/tcmIS/hub/facilitypermissionresults.do" onsubmit="return submitFrameOnlyOnce();" target="resultFrame">
--%>
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

<div class="interface" id="resultsPage"> <!-- start of interface -->
<div class="backGroundContent"> <!-- start of backGroundContent -->

<c:if test="${ApplicationAdminBeanCollection != null}" >

<c:set var="colorClass" value=''/>
<c:set var="dataCount" value='${0}'/>
<c:set var="detailCount" value='${0}'/>
<c:set var="adminCount" value='${0}'/>
<c:set var="firstLine" value='No'/>
<c:set var="preBase" value="${0}"/>
<c:set var="preCompany" value=""/>
<c:set var="preFacility" value=""/>

 <c:if test="${!empty ApplicationAdminBeanCollection}" >
 
	<c:set var="headerSize" value='0'/>
    <c:forEach var="HeaderBean" items="${workareaPermissionsHeaderBeanCollection}">
		<c:set var="headerSize" value='${headerSize+1}'/>
	</c:forEach>
 <script type="text/javascript">
 <!--
 // including the first access column
  var headerSize = parseInt("${headerSize+1}");
 //-->
 </script>
 
    <table width="100%" class="tableResults" id="resultsPageTable" border="0" cellpadding="0" cellspacing="0">
	<c:forEach var="pageNameViewBean" items="${ApplicationAdminBeanCollection}" varStatus="status">

    <c:if test="${status.index % 10 == 0}">
    <tr>
    <th width="5%"><fmt:message key="label.company"/></th>
    <th width="5%"><fmt:message key="label.facility"/></th>
    <th width="15%"><fmt:message key="label.cabinets"/></th>
    <th width="5%">
    	    <fmt:message key="label.access"/>
   </th>
		 <%-- getting the display name from vvusergroup.properties --%>
	 <fmt:bundle basename="com.tcmis.common.resources.VvUserGroup">
		<c:forEach var="HeaderBean" items="${workareaPermissionsHeaderBeanCollection}">
	    <th width="5%"><fmt:message key="${HeaderBean.userGroupId}"/></th>
   	</c:forEach>
	 </fmt:bundle>
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

	<c:if test="${ preCompany != status.current.companyId or preFacility != status.current.facilityId }">
		<c:if test="${ detailCount != 0 }">
			<input type="hidden" name="top_${preBase}" id="top_${preBase}" value="${detailCount}"/>
		</c:if>
		<c:set var="preBase" value="${detailCount}"/>
	</c:if>
    <c:set var="readonly" value="disabled='disabled'"/>
	<c:if test="${status.current.userAccess == 'Admin' || status.current.userAccess == 'Grant Admin'}">
	 	<c:set var="readonly" value=""/>
	 	<c:set var="adminCount" value='${1}'/>
	</c:if>
	<c:if test="${ status.current.application == 'All' }">
			<c:set var="colorClass" value='green'/>
		    <c:set var="preCompany" value="${status.current.companyId}"/>
		    <c:set var="preFacility" value="${status.current.facilityId}"/>
		    <c:set var="preAccess" value="${status.current.applicationAccess}"/>
		    <c:set var="preUserGroupAccess" value="${status.current.userGroupAccess}"/>
	</c:if>
	<c:if test="${ status.current.application != 'All' }">
		<c:if test="${ preCompany == status.current.companyId and preFacility == status.current.facilityId}">
			<c:if test="${ preAccess == 'Y' }">
			    <c:set var="readonly" value="style='display:none'"/>
<%-- 			    <c:set var="readonly" value="disabled='disabled'"/> --%>
			</c:if>
		</c:if>
	</c:if>

    <tr class="${colorClass}" id="rowId${detailCount}">
    <td width="5%">${status.current.companyId}</td>
    <td width="5%">${status.current.facilityId}</td>
    <td width="15%">${status.current.applicationDesc}</td>
    <td width="5%">
    	<input type="hidden"   name="UserGroupAccessInputBean[${detailCount}].companyId"
    						     id="facilityBean_${detailCount}_companyId"
    						   value="${status.current.companyId}"/>
    	<input type="hidden"   name="UserGroupAccessInputBean[${detailCount}].facilityId"
    						     id="facilityBean_${detailCount}_facilityId"
    						   value="${status.current.facilityId}"/>
    	<input type="hidden"   name="UserGroupAccessInputBean[${detailCount}].user"
    						     id="facilityBean_${detailCount}_application"
    						   value="${status.current.application}"/>
    	<input type="hidden"   name="UserGroupAccessInputBean[${detailCount}].userGroupId" 
                                 id="facilityBean_${detailCount}_userGroupId"
                               value="applicationAccess"/> 
    	<input type="hidden"   name="UserGroupAccessInputBean[${detailCount}].oldApplicationAccess" 
                                 id="old_applicationAccess_${detailCount}" 
                               value="${status.current.applicationAccess}"
                               />
    	<input type="hidden"   name="UserGroupAccessInputBean[${detailCount}].userGroupAccess" 
                                 id="applicationAccess_${detailCount}" 
                               value="${status.current.applicationAccess}"
                               />
        <input type="checkbox" name="_applicationAccess_${detailCount}" id="_applicationAccess_${detailCount}" 
	    		<c:if test="${status.current.applicationAccess == 'Y'}">
	    			checked="checked"
	    		</c:if>
	    		onClick="checkAdmin(${detailCount})"
	    		value="Y" 
	    		${readonly}
	    />
		<input type="hidden" name="base_${detailCount}" id="base_${detailCount}" value="${preBase}"/>
		<c:set var="detailCount" value='${detailCount+1}'/>
    </td>
    <c:forEach var="HeaderBean" items="${workareaPermissionsHeaderBeanCollection}" varStatus="status2">
    <td width="5%">
    	<input type="hidden"   name="UserGroupAccessInputBean[${detailCount}].companyId"
    						     id="facilityBean_${detailCount}_companyId"
    						   value="${status.current.companyId}"/>
    	<input type="hidden"   name="UserGroupAccessInputBean[${detailCount}].facilityId"
    						     id="facilityBean_${detailCount}_facilityId"
    						   value="${status.current.facilityId}"/>
      	<input type="hidden"   name="UserGroupAccessInputBean[${detailCount}].user"
    						     id="facilityBean_${detailCount}_application"
    						   value="${status.current.application}"/>
    	<input type="hidden"   name="UserGroupAccessInputBean[${detailCount}].userGroupId" 
                                 id="facilityBean_${detailCount}_userGroupId"
                               value="${status.current.userGroupAccess[status2.index].userGroupId}"/> 
    	<input type="hidden"   name="UserGroupAccessInputBean[${detailCount}].oldApplicationAccess" 
                                 id="old_applicationAccess_${detailCount}" 
                               value="${status.current.userGroupAccess[status2.index].userGroupAccess}"
                               />
    	<input type="hidden"   name="UserGroupAccessInputBean[${detailCount}].userGroupAccess" 
                                 id="applicationAccess_${detailCount}" 
                               value="${status.current.userGroupAccess[status2.index].userGroupAccess}"
                               />
    <c:set var="adminreadonly" value='style="display: none;"'/>
	<c:if test="${ !(status2.current.userGroupId == 'xxDummyXX') && (status.current.userAccess == 'Grant Admin' || status.current.userAccess == 'Admin') && ( status.current.application == 'All' || ( preCompany == status.current.companyId and preFacility == status.current.facilityId and preUserGroupAccess[status2.index].userGroupAccess == '0' ) ) }">
<%-- 		<c:set var="adminreadonly" value='style="display: none;"'/> --%>
		<c:set var="adminreadonly" value=''/>
	</c:if>
    <c:set var="admincheck" value=""/>
	<c:if test="${status.current.userGroupAccess[status2.index].userGroupAccess == '1'}">
		<c:set var="admincheck" value='checked="checked"'/>
	</c:if>
<%--
		<c:set var="display" value=''/>
 	    <c:if test="${status2.current.userGroupId == 'xxDummyXX'}">
			<c:set var="display" value='style="display: none;"'/>
    	</c:if>
--%>        <input type="checkbox" name="_applicationAccess_${detailCount}" id="_applicationAccess_${detailCount}" 
	    		onClick="checkGroupAdmin(${detailCount})"
	    		${admincheck}
	    		${adminreadonly}
	    		value="1" 
	    />
		<input type="hidden" name="base_${detailCount}" id="base_${detailCount}" value="${preBase}"/>
		<c:set var="detailCount" value='${detailCount+1}'/>
    </td>
	</c:forEach>
    </tr>
   <c:set var="dataCount" value='${dataCount+1}'/>

	</c:forEach>
   </table>
   
  
 <script type="text/javascript">
 <!--
<c:if test="${adminCount > 0}">
  showUpdateLinks = true;
</c:if>
	var total = ${detailCount};
 //-->
 </script>

   </c:if>
   <c:if test="${empty ApplicationAdminBeanCollection}" >

    <table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableNoData" id="resultsPageTable">
     <tr>
      <td width="100%">
       <fmt:message key="main.nodatafound"/>
      </td>
     </tr>
    </table>
   </c:if>
</c:if>

<!-- Hidden element start -->
<div id="hiddenElements" style="display: none;">
<input name="uAction" id="uAction" value="update" type="hidden">
<input name="personnelId" id="personnelId" value="${param.personnelId}" type="hidden">
<input name="userId" id="userId" value="${userId}" type="hidden">
<input type="hidden" name="companyId" id="companyId" value="${param.companyId}"/>
<input type="hidden" name="facilityId" id="facilityId" value="${param.facilityId}"/>
<input type="hidden" name="hide" id="hide" value="${param.hide}"/>
<input name="totalLines" id="totalLines" value="${dataCount}" type="hidden">
<input type="hidden" name="top_${preBase}" id="top_${preBase}" value="${detailCount}"/>

<!-- Store all search criteria in hidden elements, need this to requery the database after updates -->

 </div>
<!-- Hidden elements end -->

</div> <!-- close of backGroundContent -->
</div> <!-- close of interface -->

</tcmis:form>
<script type="text/javascript">
<!--
	parent.document.getElementById('legendLink').style["display"] = ( ${dataCount} > 0 )?"":"none";
//-->
</script>
</body>
</html:html>