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

<!-- Add any other stylesheets you need for the page here -->

<script src="/js/common/formchek.js" language="JavaScript"></script>
<script src="/js/common/commonutil.js" language="JavaScript"></script>
<script src="/js/common/searchiframeresize.js" language="JavaScript"></script>
<!-- This handels which key press events are disabeled on this page -->
<script src="/js/common/disableKeys.js" language="JavaScript"></script>
<!-- This handels the menu style and what happens to the right click on the whole page -->
<script type="text/javascript" src="/js/menu/milonic_src.js"></script>
<script type="text/javascript" src="/js/menu/mmenudom.js"></script>
<script type="text/javascript" src="/js/menu/mainmenudata.js"></script>
<script type="text/javascript" src="/js/menu/contextmenu.js"></script>
<%@ include file="/common/rightclickmenudata.jsp" %>

<script src="/js/common/misc/searchpersonnel.js" language="JavaScript"></script>

<title>
<fmt:message key="searchpersonnel.label.title"/>
</title>

<script language="JavaScript" type="text/javascript">
<!--
<c:set var="workareaFacilityColl" value='${dropDownDataCollection}'/>
<bean:size id="companySize" name="workareaFacilityColl"/>
var altCompanyId = new Array(
<c:forEach var="myWorkareaFacilityViewBean" items="${dropDownDataCollection}" varStatus="status">
 <c:choose>
   <c:when test="${status.index == 0 && companySize > 1}">
     "My Companies","<c:out value="${status.current.companyId}" escapeXml="false"/>"
   </c:when>
   <c:otherwise>
     <c:choose>
       <c:when test="${status.index == 0}">
         "<c:out value="${status.current.companyId}" escapeXml="false"/>"
       </c:when>
       <c:otherwise>
         ,"<c:out value="${status.current.companyId}" escapeXml="false"/>"
       </c:otherwise>
     </c:choose>
   </c:otherwise>
  </c:choose>
</c:forEach>
);

var altCompanyName = new Array(
<c:forEach var="myWorkareaFacilityViewBean" items="${dropDownDataCollection}" varStatus="status">
 <c:choose>
   <c:when test="${status.index == 0 && companySize > 1}">
     "<fmt:message key="label.mycompanies"/>","<c:out value="${status.current.companyName}" escapeXml="false"/>"
   </c:when>
   <c:otherwise>
     <c:choose>
       <c:when test="${status.index == 0}">
         "<c:out value="${status.current.companyName}" escapeXml="false"/>"
       </c:when>
       <c:otherwise>
         ,"<c:out value="${status.current.companyName}" escapeXml="false"/>"
       </c:otherwise>
     </c:choose>
   </c:otherwise>
  </c:choose>
</c:forEach>
);
// -->
</script>

<script language="JavaScript" type="text/javascript">
<!--
//add all the javascript messages here, this for internationalization of client side javascript messages
var messagesData = new Array();
messagesData={alert:"<fmt:message key="label.alert"/>",and:"<fmt:message key="label.and"/>",
recordFound:"<fmt:message key="label.recordFound"/>",
searchDuration:"<fmt:message key="label.searchDuration"/>",
minutes:"<fmt:message key="label.minutes"/>",
seconds:"<fmt:message key="label.seconds"/>",
submitOnlyOnce:"<fmt:message key="label.submitOnlyOnce"/>"};
// -->
</script>

<%-- set height dynamically --%>
<c:set var="displayView" value='${displayView}'/>
<script language="JavaScript" type="text/javascript">
<!--
	var myHeight = 83;
	<c:if test="${companySize > 1}">
    	myHeight = 110;
	</c:if>

	<c:if test="${displayView == 'displayCompanyFacility'}">
   	myHeight = 133;
	</c:if>
// -->
</script>
<%-- set height dynamically END --%>	

</head>


<body bgcolor="#ffffff" onload="parent.document.getElementById('searchFrame').height=myHeight;mySearchOnLoad();">

<tcmis:form action="/searchpersonnelresults.do" onsubmit="return submitFrameOnlyOnce();" target="resultFrame">

<div class="interface" id="searchMainPage">

<div class="contentArea">

<!-- Search Option Begins -->
<table id="searchMaskTable" width="450" border="0" cellpadding="0" cellspacing="0">
<tr><td>
<div class="roundcont filterContainer">
 <div class="roundright">
   <div class="roundtop">
     <div class="roundtopright"> <img src="/images/rndBoxes/borderTL_filter.gif" alt="" width="15" height="10" class="corner_filter" style="display: none" /></div>
   </div>
   <div class="roundContent">
    <!-- Insert all the search option within this div -->
  <table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableSearch">
    <%-- row 1 --%>
	  <c:set var="displayView" value='${displayView}'/>
	  <c:if test="${displayView == 'displayCompany'}">
			<tr>
				<td width="30%" class="optionTitleBoldRight"><fmt:message key="label.company"/>:</td>
				<td width="50%" colspan="2">
					 <c:set var="workareaFacilityColl" value='${dropDownDataCollection}'/>
					 <bean:size id="companySize" name="workareaFacilityColl"/>
					 <c:set var="selectedCompanyId" value='${companyId}'/>
					 <select name="companyId" id="companyId" class="selectBox">
					 <c:choose>
						 <c:when test="${companySize == 0}">
							 <option value="My Companies"><fmt:message key="label.mycompanies"/></option>
						 </c:when>
						 <c:otherwise>
							 <c:if test="${companySize > 1}">
								 <option value="My Companies" selected><fmt:message key="label.mycompanies"/></option>
								 <c:set var="selectedCompanyId" value="mycompanies"/>  <%-- set it to something so I can test it selectedCompanyId is empty below --%>
							 </c:if>

							 <c:forEach var="myWorkareaFacilityViewBean" items="${dropDownDataCollection}" varStatus="status">
								 <c:set var="currentCompanyId" value='${status.current.companyId}'/>
									 <c:if test="${empty selectedCompanyId}" >
										 <c:set var="selectedCompanyId" value='${status.current.companyId}'/>
									 </c:if>
									 <option value="<c:out value="${currentCompanyId}"/>"><c:out value="${status.current.companyName}"/></option>
							 </c:forEach>
						 </c:otherwise>
					 </c:choose>
					 </select>
				</td>
			</tr>
	  </c:if>

	  <c:if test="${displayView == 'displayCompanyFacility'}">
			<tr>
				<td width="30%" class="optionTitleBoldRight"><fmt:message key="label.company"/>:</td>
				<td width="50%" colspan="2"><c:out value="${companyName}"/></td>
			</tr>
		  <tr>
				<td width="30%" class="optionTitleBoldRight"><fmt:message key="label.facility"/>:</td>
				<td width="50%" colspan="2"><c:out value="${facilityName}"/></td>
			</tr>

		  <%-- hidden values --%>
		  <input name="companyId" id="companyId" type="hidden" value="<c:out value="${companyId}"/>">
		  <input name="facilityId" id="facilityId" type="hidden" value="<c:out value="${facilityId}"/>">
		</c:if>

	  <c:if test="${displayView == 'hideCompanyFacility'}">
		  <%-- hidden values --%>
		  <input name="companyId" id="companyId" type="hidden" value="<c:out value="${companyId}"/>">
		</c:if>	    

    <%-- row 2 --%>
    <tr>
      <td width="30%"  class="optionTitleBoldRight">
        <fmt:message key="label.search"/>:
      </td>
      <td width="50%"  class="optionTitleLeft">
        <input type="text" class="inputBox" name="searchText" id="searchText" value="<c:out value="${param.searchText}"/>" size="30" onkeypress="return onKeyPress()">
      </td>
      <td width="30%"  class="optionTitleLeft">
        <input type="checkbox" class="radioBtns" name="status" id="status" value="ACTIVE" checked><fmt:message key="label.activeOnly"/>
      </td>
    </tr>
    <%-- row 3 --%>
    <tr>
      <td width="30%"  colspan="3" class="optionTitleLeft">
        <html:submit property="submitSearch" styleClass="inputBtns" onmouseover="this.className='inputBtns inputBtnsOver'" onmouseout="this.className='inputBtns'" onclick= "return submitSearchForm()">
        <fmt:message key="label.search"/>
        </html:submit>
      </td>
    </tr>
  </table>
  <!-- End search options -->

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
<!-- Build this section only if there is an error message to display -->
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
<input name="action" id="action" type="hidden" value="">
<input name="displayElementId" id="displayElementId" type="hidden" value="<c:out value="${param.displayElementId}"/>">
<input name="valueElementId" id="valueElementId" type="hidden"value="<c:out value="${param.valueElementId}"/>">
<input name="startSearchTime" id="startSearchTime" type="hidden"value="">
</div>
<!-- Hidden elements end -->

</div> <!-- close of interface -->

</tcmis:form>
</body>
</html:html>