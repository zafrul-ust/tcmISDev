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
<%--
<link rel="stylesheet" type="text/css" href="/yui/build/container/assets/container.css" />
--%>
<!-- Add any other stylesheets you need for the page here -->

<%--
<link rel="stylesheet" type="text/css" href="/css/dhtmlXGridHaas.css"></link>
--%>

<script type="text/javascript" src="/js/common/formchek.js"></script>
<script type="text/javascript" src="/js/common/commonutil.js"></script>
<script type="text/javascript" src="/js/common/searchiframeresize.js"></script>
<!-- This handels which key press events are disabeled on this page -->
<script type="text/javascript" src="/js/common/disableKeys.js"></script>

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
<script type="text/javascript" src="/js/common/admin/facilityPermission.js"></script>

<!-- These are for the Grid, uncomment if you are going to use the grid -->
<%--<script src="/js/dhtmlxGrid/dhtmlXCommon.js"></script>
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
<fmt:message key="label.facilityPermissionsTitle"/>  (${logonId})
</title>

<script language="JavaScript" type="text/javascript">
<!--
//add all the javascript messages here, this for internationalization of client side javascript messages

<c:set var="companyFacilityColl" value='${UserFacilitySelectOvCollection}'/>
<bean:size id="companySize" name="companyFacilityColl"/>
var altCompanyId = new Array(
	""
<c:forEach var="hubvar" items="${UserFacilitySelectOvCollection}" varStatus="status">
    ,"${status.current.companyId}"
</c:forEach>
);

var altCompanyName = new Array(
	"<fmt:message key="label.mycompanies"/>"
<c:forEach var="hubvar" items="${UserFacilitySelectOvCollection}" varStatus="status">
    ,"${status.current.companyName}"
</c:forEach>
);

var altFacilityId = new Array();
var altFacilityName = new Array();
<c:forEach var="hubvar" items="${UserFacilitySelectOvCollection}" varStatus="status">

altFacilityId["${status.current.companyId}"] = new Array(
	""
  <c:forEach var="invGrp" items="${status.current.facilityList}" varStatus="status1">
    ,"${status1.current.facilityId}"
  </c:forEach>
  );

altFacilityName["${status.current.companyId}"] = new Array(
	"<fmt:message key="label.myfacilities"/>"
  <c:forEach var="invGrp" items="${status.current.facilityList}" varStatus="status1">
    ,"${status1.current.facilityName}"
  </c:forEach>
  );
</c:forEach>

var messagesData = new Array();
messagesData={alert:"<fmt:message key="label.alert"/>",and:"<fmt:message key="label.and"/>",
validvalues:"<fmt:message key="label.validvalues"/>",submitOnlyOnce:"<fmt:message key="label.submitOnlyOnce"/>",
myfacilities:"<fmt:message key="label.myfacilities"/>"};
// -->
</script>

<%-- set height dynamically --%>
<script language="JavaScript" type="text/javascript">
<!--
	var myHeight = 110;    <%-- both displaying company or hidding company has 3 rows --%>
	<c:if test="${companySize > 1}">
    	myHeight = 110;
	</c:if>
// -->
</script>
<%-- set height dynamically END --%>

</head>

<body bgcolor="#ffffff" onload="parent.document.getElementById('searchFrame').height=myHeight;setSearchFrameSize();setCompany()">

<tcmis:form action="/facilitypermissionresults.do" onsubmit="return submitFrameOnlyOnce();" target="resultFrame">

<div class="interface" id="searchMainPage"> <!-- Start of interface-->

<div class="contentArea"> <!-- Start of contentArea-->

<!-- Search Option Begins -->
<table id="searchMaskTable" width="100%" border="0" cellpadding="0" cellspacing="0">
<tr><td>
<div class="roundcont filterContainer">
 <div class="roundright">
   <div class="roundtop">
     <div class="roundtopright"> <img src="/images/rndBoxes/borderTL_filter.gif" alt="" width="15" height="10" class="corner_filter" style="display: none" /></div>
   </div>
   <div class="roundContent">
    <!-- Insert all the search option within this div -->
    <!-- Search Option Table Start -->
    <table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableSearch">
		 <%-- determine whether to display company dropdown --%>
		 	<input type="hidden" name="oldcompanyId" id="oldcompanyId" value=""/>
			<c:set var="companyFacilityColl" value='${UserFacilitySelectOvCollection}'/>
			<bean:size id="companySize" name="companyFacilityColl"/>
			<c:choose>
				<c:when test="${companySize == 1}">
					 <c:forEach var="companyFacilityViewBean" items="${UserFacilitySelectOvCollection}" varStatus="status">
						 <input type="hidden" name="companyId" id="companyId" value="${status.current.companyId}"/>
						 <input type="hidden" name="companyName" id="companyName" value="${status.current.companyName}"/>
					 </c:forEach>
				</c:when>
				<c:otherwise>
					<tr>
						<td width="33%" class="optionTitleBoldRight"><fmt:message key="label.company"/>:</td>
					  	<td width="33%" class="optionTitleLeft">
							 <!-- Use this for dropdowns you build with collections from the database -->
						 	<select name="companyId" id="companyId" class="selectBox" onChange="CompanyChanged()">
						 	</select>
						 	<input type="hidden" name="companyName" id="companyName" value="" />
					 	</td>
						<td class="optionTitleLeft" nowrap="nowrap">
							<input type="checkBox" name="hide" id="hide" value="hide" class="radioBtns" checked="checked"/><fmt:message key="label.showcurrentpermissiononly"/>
						</td>
					</tr>
				</c:otherwise>
			 </c:choose>

      <tr>
        <td width="33%" class="optionTitleBoldRight"><fmt:message key="label.facility"/>:</td>
        <td width="33%" class="optionTitleLeft">
          <!-- Use this for dropdowns you build with collections from the database -->
          <input type="hidden" name="oldfacilityId" id="oldfacilityId" value=""/>
          <select name="facilityId" id="facilityId" class="selectBox">
          </select>
       </td>
	   	<td class="optionTitleLeft">
	   		<input type="button" class="smallBtns" onmouseover="this.className='smallBtns smallBtnsOver'" onmouseout="this.className='smallBtns'" name="editButton" value="<fmt:message key="label.editlist"/>" onclick="editList()" />
		</td>
      </tr>

		<c:if test="${companySize == 1}">
			<tr>
				<td>&nbsp;</td>
				<td class="optionTitleLeft" nowrap="nowrap" colspan="2">
					<input type="checkBox" name="hide" id="hide" value="hide" class="radioBtns" checked="checked"/><fmt:message key="label.showcurrentpermissiononly"/>
				</td>
			</tr>
		</c:if>

		<tr>
      	<td width="33%" class="optionTitleRight">
			<input name="Search" id="Search" type="button" value="<fmt:message key="label.search"/>" class="inputBtns" onmouseover="this.className='inputBtns inputBtnsOver'" onmouseout="this.className='inputBtns'" onClick="search()"/>
		</td>
	   	<td width="66%" class="optionTitleLeft" colspan="2" nowrap>
<%-- 
			<input type="button" name="createXSL" id="createXSL" value="<fmt:message key="label.createExcel"/>" class="inputBtns" onmouseover="this.className='inputBtns inputBtnsOver'" onmouseout="this.className='inputBtns'" onClick="createXLS()"/>
--%>
			<input name="createexcel" id="createexcel" type="button" value="<fmt:message key="label.createexcel"/>" class="inputBtns" onmouseover="this.className='inputBtns inputBtnsOver'" onmouseout="this.className='inputBtns'" onClick="createXSL()"/>
			<input type="button" name="cancel" id="cancel" value="<fmt:message key="label.cancel"/>" class="inputBtns" onmouseover="this.className='inputBtns inputBtnsOver'" onmouseout="this.className='inputBtns'" onClick="parent.window.close()"/>
		</td>
      </tr>
    </table>
    <!-- Search Option Table end -->
   </div>
   <div class="roundbottom">
     <div class="roundbottomright"> <img src="/images/rndBoxes/borderBL.gif" alt="" width="15" height="15" class="corner" style="display: none" /> </div>
   </div>
 </div>
</div>
</td></tr>
</table>
<!-- Search Option Ends -->


<!-- Error Messages Begins -->
<!-- Build this section only if there is an error message to display.
     For the search section, we show the error messages within its frame
-->
<c:if test="${requestScope['org.apache.struts.action.ERROR'] != null}">
<div class="spacerY">&nbsp;
<div id="errorMessagesArea" class="errorMessages">
<html:errors/>
</div>
</div>
</c:if>
<!-- Error Messages Ends -->

</div> <!-- close of contentArea -->

<!-- Hidden element start -->
<div id="hiddenElements" style="display: none;">
<input type="hidden" name="uAction" id="uAction" value="search"/>
<input type="hidden" name="personnelId" id="personnelId" value="${personnelId}" />
<input type="hidden" name="userId" id="userId" value="${userId}" />
<input type="hidden" name="fullName" id="fullName" value="${fullName}" />
</div>
<!-- Hidden elements end -->

</div> <!-- close of interface -->
</tcmis:form>
</body>
</html:html>