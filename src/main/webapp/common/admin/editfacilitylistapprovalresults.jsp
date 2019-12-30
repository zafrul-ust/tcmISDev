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
<script type="text/javascript" src="/js/calendar/newcalendar.js"></script>
<script type="text/javascript" src="/js/calendar/AnchorPosition.js"></script>
<script type="text/javascript" src="/js/calendar/PopupWindow.js"></script>


<!-- Add any other javascript you need for the page here -->
<script type="text/javascript" src="/js/common/admin/editfacilitylistapprovalresults.js"></script>


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
	<fmt:message key="label.editfacilitylistapproval"/> (${param.facilityName})
</title>

<script language="JavaScript" type="text/javascript">
<!--
//add all the javascript messages here, this for internationalization of client side javascript messages
var messagesData = new Array();
messagesData={alert:"<fmt:message key="label.alert"/>",and:"<fmt:message key="label.and"/>",
validvalues:"<fmt:message key="label.validvalues"/>",
submitOnlyOnce:"<fmt:message key="label.submitOnlyOnce"/>",
setAtLeastOneLimit:"<fmt:message key="label.setatleastonelimit"/>",
mrLimitWrongFormat:"<fmt:message key="label.mrlimitwrongformat"/>",
ytdLimitWrongFormat:"<fmt:message key="label.ytdlimitwrongformat"/>",
periodLimitWrongFormat:"<fmt:message key="label.periodlimitwrongformat"/>",
missingPeriodDays:"<fmt:message key="label.missingperioddays"/>",
line:"<fmt:message key="label.line"/>",	
noDefault:"<fmt:message key="label.cannotRemoveDefault"/>"};
// -->
</script>
</head>

<body bgcolor="#ffffff" onLoad="myResultOnload()">

<tcmis:form action="/editfacilitylistapprovalresults.do" onsubmit="return submitOnlyOnce();">

<!-- Check if the user has permissions and needs to see the update links,set the variable you use in javascript to true.
     The default value of showUpdateLinks is false.
-->
<c:set var="isAdmin" value=""/>
<tcmis:facilityPermission indicator="true" userGroupId="ListApprovalAdmin" facilityId="${facilityId}" companyId="${companyId}">
 <script type="text/javascript">
 <!--
  showUpdateLinks = true;
  <c:set var="isAdmin" value="Yes"/>
 //-->
 </script>
</tcmis:facilityPermission>

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
			  
<c:if test="${facilityListSelectionViewBeanCollection != null}" >
<!-- Search results start -->

<c:set var="colorClass" value=''/>
<c:set var="dataCount" value='${0}'/>
<c:set var="adminCount" value='${0}'/>

 <!-- If you want your results section to span only 50% set this to 50% and your main table will be 100% -->
 <c:if test="${!empty facilityListSelectionViewBeanCollection}" >

    <table width="100%" class="tableResults" id="resultsPageTable" border="0" cellpadding="0" cellspacing="0">
    <c:set var="colorClass" value=''/>
    <c:set var="dataCount" value='${0}'/>
	<c:set var="adminCount" value='${0}'/>

	<c:forEach var="UserFacilityAdminViewBean" items="${facilityListSelectionViewBeanCollection}" varStatus="status">

	<c:if test="${status.index % 10 == 0}">
    <!-- Need to print the header every 10 rows-->
    <tr>
	    <th width="15%"><fmt:message key="label.list"/></th>
    <th width="2%">
    	    <fmt:message key="label.active"/>
		 	<%--
			  <c:if test="${empty firstLine}">
    	    <br/>
    		<input type="checkbox" name="ok${status.index}" id="ok${status.index}" onClick="checkAll(${status.index})" style="display:none"/>
		    <c:set var="firstLine" value='No'/>
    		</c:if>
    		--%>
    </th>
    	<th width="15%"><fmt:message key="label.mrlimit"/></th>
		<%-- <th width="5%"><fmt:message key="label.mrthreshold"/></th> --%>
		 <th width="29%"><fmt:message key="label.ytdlimit"/></th>
		<%-- <th width="5%"><fmt:message key="label.ytdthreshold"/></th> --%>
		<th width="29%"><fmt:message key="label.periodlimit"/></th>
		<%-- <th width="5%"><fmt:message key="label.periodthreshold"/></th> --%>
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

	<%-- hidden variables --%>
	<input type="hidden" name="listApproverInputBean[${status.index}].modified" id="modified${status.index}" value=""/>
	<input type="hidden" name="listApproverInputBean[${status.index}].companyId" id="companyId${status.index}" value="${status.current.companyId}"/>
	<input type="hidden" name="listApproverInputBean[${status.index}].facilityId" id="facilityId{status.index}" value="${status.current.facilityId}"/>
	<input type="hidden" name="listApproverInputBean[${status.index}].listId" id="listId${status.index}" value="${status.current.listId}"/>
	<c:choose>
		<c:when test="${isAdmin == 'Yes'}">


			<tr class="${colorClass}">
     			<td width="15%">
     				${status.current.listName}
     			</td>
     			<td width="2%">
					<c:set var="statusChecked" value=''/>
					<c:if test="${status.current.status == 'A'}">
						<c:set var="statusChecked" value='checked'/>	
					</c:if>
					<input type="checkbox" name="listApproverInputBean[${status.index}].status" id="status${status.index}" ${statusChecked} onClick="changeActive(${status.index})" />
	  			</td>
	 			<td width="15%">
					<input class="inputBox" type="text" name="listApproverInputBean[${status.index}].mrLimit" id="mrLimit${status.index}" value="${status.current.mrLimit}" size="5" maxlength="30" onClick="changeStatus(${status.index})" >
					<select name="listApproverInputBean[${status.index}].mrLimitUnit" id="mrLimitUnit${status.index}" class="selectBox" onchange="changeStatus(${status.index})" >
						<c:forEach var="sizeUnitConversionBean" items="${sizeUnitConversionCollection}" varStatus="status2">
							<c:set var="selectedVal" value=''/>
							<c:set var="currentUnit" value="${status.current.mrLimitUnit}"/>
							<c:if test="${currentUnit == null || empty currentUnit}">
								<c:set var="currentUnit" value='lb'/>
							</c:if>
							<c:if test="${currentUnit == status2.current.fromUnit}">
								<c:set var="selectedVal" value='selected'/>
							</c:if>
							<option ${selectedVal} value="<c:out value="${status2.current.fromUnit}"/>"><c:out value="${status2.current.fromUnit}" escapeXml="false"/></option>
						</c:forEach>
					 </select>
				</td>
				  <%--
				  <td width="5%">
					<c:choose>
						<c:when test="${status.current.allowListThresholdTest == 'N'}">
							&nbsp;
						</c:when>
						<c:otherwise>
							<c:set var="statusChecked" value=''/>
							<c:if test="${status.current.mrLimitByThreshold == 'Y'}">
								<c:set var="statusChecked" value='checked'/>
							</c:if>
							<input type="checkbox" name="listApproverInputBean[${status.index}].mrThreshold" id="mrThreshold${status.index}" ${statusChecked} onClick="changeStatus(${status.index})" />
						</c:otherwise>
					</c:choose>
	  			</td>
	  			--%>
	 			<td width="29%">
					<input class="inputBox" type="text" name="listApproverInputBean[${status.index}].ytdLimit" id="ytdLimit${status.index}" value="${status.current.ytdLimit}" size="5" maxlength="30" onClick="changeStatus(${status.index})" />
					<select name="listApproverInputBean[${status.index}].ytdLimitUnit" id="ytdLimitUnit${status.index}" class="selectBox" onchange="changeStatus(${status.index})" >
						<c:forEach var="sizeUnitConversionBean" items="${sizeUnitConversionCollection}" varStatus="status2">
							<c:set var="selectedVal" value=''/>
							<c:set var="currentUnit" value="${status.current.ytdLimitUnit}"/>
							<c:if test="${currentUnit == null || empty currentUnit}">
								<c:set var="currentUnit" value='lb'/>
							</c:if>
							<c:if test="${currentUnit == status2.current.fromUnit}">
								<c:set var="selectedVal" value='selected'/>
							</c:if>
							<option ${selectedVal} value="<c:out value="${status2.current.fromUnit}"/>"><c:out value="${status2.current.fromUnit}" escapeXml="false"/></option>
						</c:forEach>
					 </select>
					 &nbsp;<fmt:message key="label.ytdstarting"/>&nbsp;
					 <select name="listApproverInputBean[${status.index}].ytdStartMm" id="ytdStartMm${status.index}" class="selectBox" onchange="changeStatus(${status.index})" >
					   <c:set var="selectedVal" value=''/>
						<c:if test="${status.current.ytdStartMm == '01'}">
							<c:set var="selectedVal" value='selected'/>
						</c:if>
						<option ${selectedVal} value="01"><fmt:message key="label.jan"/></option>
						<c:set var="selectedVal" value=''/>
						<c:if test="${status.current.ytdStartMm == '02'}">
							<c:set var="selectedVal" value='selected'/>
						</c:if>
						<option ${selectedVal} value="02"><fmt:message key="label.feb"/></option>
						<c:set var="selectedVal" value=''/>
						<c:if test="${status.current.ytdStartMm == '03'}">
							<c:set var="selectedVal" value='selected'/>
						</c:if>
						<option ${selectedVal} value="03"><fmt:message key="label.mar"/></option>
						<c:set var="selectedVal" value=''/>
						<c:if test="${status.current.ytdStartMm == '04'}">
							<c:set var="selectedVal" value='selected'/>
						</c:if>
						<option ${selectedVal} value="04"><fmt:message key="label.apr"/></option>
						<c:set var="selectedVal" value=''/>
						<c:if test="${status.current.ytdStartMm == '05'}">
							<c:set var="selectedVal" value='selected'/>
						</c:if>
						<option ${selectedVal} value="05"><fmt:message key="label.may"/></option>
						<c:set var="selectedVal" value=''/>
						<c:if test="${status.current.ytdStartMm == '06'}">
							<c:set var="selectedVal" value='selected'/>
						</c:if>
						<option ${selectedVal} value="06"><fmt:message key="label.jun"/></option>
						<c:set var="selectedVal" value=''/>
						<c:if test="${status.current.ytdStartMm == '07'}">
							<c:set var="selectedVal" value='selected'/>
						</c:if>
						<option ${selectedVal} value="07"><fmt:message key="label.jul"/></option>
						<c:set var="selectedVal" value=''/>
						<c:if test="${status.current.ytdStartMm == '08'}">
							<c:set var="selectedVal" value='selected'/>
						</c:if>
						<option ${selectedVal} value="08"><fmt:message key="label.aug"/></option>
						<c:set var="selectedVal" value=''/>
						<c:if test="${status.current.ytdStartMm == '09'}">
							<c:set var="selectedVal" value='selected'/>
						</c:if>
						<option ${selectedVal} value="09"><fmt:message key="label.sep"/></option>
						<c:set var="selectedVal" value=''/>
						<c:if test="${status.current.ytdStartMm == '10'}">
							<c:set var="selectedVal" value='selected'/>
						</c:if>
						<option ${selectedVal} value="10"><fmt:message key="label.oct"/></option>
						<c:set var="selectedVal" value=''/>
						<c:if test="${status.current.ytdStartMm == '11'}">
							<c:set var="selectedVal" value='selected'/>
						</c:if>
						<option ${selectedVal} value="11"><fmt:message key="label.nov"/></option>
						<c:set var="selectedVal" value=''/>
						<c:if test="${status.current.ytdStartMm == '12'}">
							<c:set var="selectedVal" value='selected'/>
						</c:if>
						<option ${selectedVal} value="12"><fmt:message key="label.dec"/></option>
					 </select>
				</td>
				  <%--
				  <td width="5%">
					<c:choose>
						<c:when test="${status.current.allowListThresholdTest == 'N'}">
							&nbsp;
						</c:when>
						<c:otherwise>
							<c:set var="statusChecked" value=''/>
							<c:if test="${status.current.ytdLimitByThreshold == 'Y'}">
								<c:set var="statusChecked" value='checked'/>
							</c:if>
							<input type="checkbox" name="listApproverInputBean[${status.index}].ytdThreshold" id="ytdThreshold${status.index}" ${statusChecked} onClick="changeStatus(${status.index})" />
						</c:otherwise>
					</c:choose>
	  			</td>
	  			--%>
	 			<td width="29%">
     				<input class="inputBox" type="text" name="listApproverInputBean[${status.index}].periodLimit" id="periodLimit${status.index}" value="${status.current.periodLimit}" size="5" maxlength="30" onClick="changeStatus(${status.index})" />
					<select name="listApproverInputBean[${status.index}].periodLimitUnit" id="periodLimitUnit${status.index}" class="selectBox" onchange="changeStatus(${status.index})">
						<c:forEach var="sizeUnitConversionBean" items="${sizeUnitConversionCollection}" varStatus="status2">
							<c:set var="selectedVal" value=''/>
							<c:set var="currentUnit" value="${status.current.periodLimitUnit}"/>
							<c:if test="${currentUnit == null || empty currentUnit}">
								<c:set var="currentUnit" value='lb'/>
							</c:if>
							<c:if test="${currentUnit == status2.current.fromUnit}">
								<c:set var="selectedVal" value='selected'/>
							</c:if>
							<option ${selectedVal} value="<c:out value="${status2.current.fromUnit}"/>"><c:out value="${status2.current.fromUnit}" escapeXml="false"/></option>
						</c:forEach>
					 </select>
					 &nbsp;<fmt:message key="label.per"/>
					 <input class="inputBox" type="text" name="listApproverInputBean[${status.index}].periodDays" id="periodDays${status.index}" value="${status.current.periodDays}" size="5" maxlength="30" onClick="changeStatus(${status.index})" />
					 &nbsp;<fmt:message key="label.days"/>
				</td>
				<%--
				  <td width="5%">
					<c:choose>
						<c:when test="${status.current.allowListThresholdTest == 'N'}">
							&nbsp;
						</c:when>
						<c:otherwise>
							<c:set var="statusChecked" value=''/>
							<c:if test="${status.current.periodLimitByThreshold == 'Y'}">
								<c:set var="statusChecked" value='checked'/>
							</c:if>
							<input type="checkbox" name="listApproverInputBean[${status.index}].periodThreshold" id="periodThreshold${status.index}" ${statusChecked} onClick="changeStatus(${status.index})" />
						</c:otherwise>
					</c:choose>
	  			</td>
	  			--%>
			</tr>
		</c:when>
		<c:otherwise>
			<tr class="${colorClass}">
     			<td width="15%">
     				${status.current.listName}
     			</td>
     			<td width="5%">
     				${status.current.status}
	  			</td>
				<td width="10%">
     				${status.current.mrLimit}&nbsp;${status.current.mrLimitUnit}
	  			</td>
				<%--
				<td width="5%">
     				${status.current.mrLimitByThreshold}
	  			</td>
	  			--%>
				<td width="10%">
     				${status.current.ytdLimit}&nbsp;${status.current.ytdLimitUnit}&nbsp;${status.current.ytdStartMm}
	  			</td>
				<%--
				<td width="5%">
     				${status.current.ytdLimitByThreshold}
	  			</td>
	  			--%>
				<td width="10%">
     				${status.current.periodLimit}&nbsp;${status.current.periodLimitUnit}&nbsp;${status.current.periodDays}
	  			</td>
				<%--
				<td width="5%">
     				${status.current.periodLimitByThreshold}
	  			</td>
	  			--%>
			</tr>
		</c:otherwise>
	</c:choose>

   <c:set var="dataCount" value='${dataCount+1}'/>
   </c:forEach>
  </table>
   </c:if>
   <!-- If the collection is empty say no data found -->
   <c:if test="${empty facilityListSelectionViewBeanCollection}" >

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
 <div id="hiddenElements" style="display: none;"></div>
<input type="hidden" name="action" id="action" value=""/>
<input type="hidden" name="companyId" id="companyId" value="${param.companyId}" />
<input type="hidden" name="facilityId" id="facilityId" value="${param.facilityId}" />
<input type="hidden" name="facilityName" id="facilityName" value="${param.facilityName}" />
<input type="hidden" name="totalLines" id="totalLines" value="${dataCount}" >

<!-- Store all search criteria in hidden elements, need this to requery the database after updates -->

 </div>
<!-- Hidden elements end -->

</div> <!-- close of backGroundContent -->
</div> <!-- close of interface -->

</tcmis:form>
</body>
</html:html>