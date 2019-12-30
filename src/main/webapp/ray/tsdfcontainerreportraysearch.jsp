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

<%-- For Calendar support --%>
<script src="/js/calendar/newcalendar.js" language="JavaScript"></script>
<script src="/js/calendar/AnchorPosition.js" language="JavaScript"></script>
<script src="/js/calendar/PopupWindow.js" language="JavaScript"></script>

<script src="/js/common/waste/tsdfcontainerreport.js" language="JavaScript"></script>

<title>
<fmt:message key="tsdfcontainerreport.title"/>
</title>

<script language="JavaScript" type="text/javascript">
<%-- assumption here is that value and label are the same --%>
<!--

var altTsdfFacilityId = new Array(
<c:forEach var="wasteTsdfReportDropdownOvBean" items="${wasteTsdfReportDropdownOvBeanCollection}" varStatus="status">
 <c:choose>
   <c:when test="${status.index > 0}">
    ,"<c:out value="${status.current.tsdfFacilityId}"/>"
   </c:when>
   <c:otherwise>
    "<c:out value="${status.current.tsdfFacilityId}"/>"
   </c:otherwise>
  </c:choose>
</c:forEach>
);

var altTsdfFacilityName = new Array();
<c:forEach var="wasteTsdfReportDropdownOvBean" items="${wasteTsdfReportDropdownOvBeanCollection}" varStatus="status">
   altTsdfFacilityName["<c:out value="${status.current.tsdfFacilityId}" escapeXml="false"/>"] = "<c:out value="${status.current.tsdfFacilityName}" escapeXml="false"/>";
</c:forEach>


var altVendorId = new Array();
var altVendorName = new Array();
<c:forEach var="wasteTsdfReportDropdownOvBean" items="${wasteTsdfReportDropdownOvBeanCollection}" varStatus="status">
  <c:set var="currentTsdfFacilityId" value='${status.current.tsdfFacilityId}'/>
  <c:set var="toVendorListCollection" value='${status.current.toVendorList}'/>

  altVendorId["<c:out value="${currentTsdfFacilityId}"/>"] = new Array(""
  <c:forEach var="toVendorObjBean" items="${toVendorListCollection}" varStatus="status1">
        ,"<c:out value="${status1.current.vendorId}"/>"
  </c:forEach>
  );

  altVendorName["<c:out value="${currentTsdfFacilityId}"/>"] = new Array("<fmt:message key="label.all"/>"
  <c:forEach var="toVendorObjBean" items="${toVendorListCollection}" varStatus="status1">
        ,"<c:out value="${status1.current.vendorName}" escapeXml="false"/>"
  </c:forEach>
  );
</c:forEach>

var altGenCompanyId = new Array();
var altGenCompanyName = new Array();
<c:forEach var="wasteTsdfReportDropdownOvBean" items="${wasteTsdfReportDropdownOvBeanCollection}" varStatus="status">
  <c:set var="currentTsdfFacilityId" value='${status.current.tsdfFacilityId}'/>
  <c:set var="toWasteGenerationCompanyListCollection" value='${status.current.wasteGenerationCompanyList}'/>

  altGenCompanyId["<c:out value="${currentTsdfFacilityId}"/>"] = new Array(""
  <c:forEach var="wasteGenerationCompanyObjBean" items="${toWasteGenerationCompanyListCollection}" varStatus="status1">
        ,"<c:out value="${status1.current.companyId}"/>"
  </c:forEach>
  );

  altGenCompanyName["<c:out value="${currentTsdfFacilityId}"/>"] = new Array("<fmt:message key="label.all"/>"
  <c:forEach var="wasteGenerationCompanyObjBean" items="${toWasteGenerationCompanyListCollection}" varStatus="status1">
        ,"<c:out value="${status1.current.companyName}" escapeXml="false"/>"
  </c:forEach>
  );
</c:forEach>

var altGenFacilityId = new Array();
var altGenFacilityName = new Array();
<c:forEach var="wasteTsdfReportDropdownOvBean" items="${wasteTsdfReportDropdownOvBeanCollection}" varStatus="status">
  <c:set var="currentTsdfFacilityId" value='${status.current.tsdfFacilityId}'/>
  <c:set var="wasteGenerationCompanyListCollection" value='${status.current.wasteGenerationCompanyList}'/>
  <c:forEach var="wasteGenerationCompanyObjBean" items="${wasteGenerationCompanyListCollection}" varStatus="status1">
    <c:set var="currentGenerationCompanyId" value='${status1.current.companyId}'/>
    <c:set var="genFacilityListCollection" value='${status1.current.genFacilityList}'/>

    altGenFacilityId["<c:out value="${currentGenerationCompanyId}"/>"] = new Array(""
    <c:forEach var="genFacilityObjBean" items="${genFacilityListCollection}" varStatus="status2">
        ,"<c:out value="${status2.current.facilityId}"/>"
    </c:forEach>
    );

    altGenFacilityName["<c:out value="${currentGenerationCompanyId}"/>"] = new Array("<fmt:message key="label.all"/>"
    <c:forEach var="genFacilityObjBean" items="${genFacilityListCollection}" varStatus="status2">
        ,"<c:out value="${status2.current.facilityName}" escapeXml="false"/>"
    </c:forEach>
    );
  </c:forEach>
</c:forEach>

var altGenerationPointId = new Array();
var altGenerationPointName = new Array();
<c:forEach var="wasteTsdfReportDropdownOvBean" items="${wasteTsdfReportDropdownOvBeanCollection}" varStatus="status">
  <c:set var="currentTsdfFacilityId" value='${status.current.tsdfFacilityId}'/>
  <c:set var="wasteGenerationCompanyListCollection" value='${status.current.wasteGenerationCompanyList}'/>
  <c:forEach var="wasteGenerationCompanyObjBean" items="${wasteGenerationCompanyListCollection}" varStatus="status1">
    <c:set var="currentGenerationCompanyId" value='${status1.current.companyId}'/>
    <c:set var="genFacilityListCollection" value='${status1.current.genFacilityList}'/>
    <c:forEach var="genFacilityObjBean" items="${genFacilityListCollection}" varStatus="status2">
      <c:set var="currentGenFacilityId" value='${status2.current.facilityId}'/>
      <c:set var="wasteLocationCollection" value='${status2.current.wasteLocationList}'/>

      altGenerationPointId["<c:out value="${currentGenFacilityId}"/>"] = new Array(""
      <c:forEach var="wasteLocationObjBean" items="${wasteLocationCollection}" varStatus="status3">
        ,"<c:out value="${status3.current.refColumn}"/>"
      </c:forEach>
      );

      altGenerationPointName["<c:out value="${currentGenFacilityId}"/>"] = new Array("<fmt:message key="label.all"/>"
      <c:forEach var="wasteLocationObjBean" items="${wasteLocationCollection}" varStatus="status3">
        ,"<c:out value="${status3.current.locationGroupWithLocation}" escapeXml="false"/>"
      </c:forEach>
      );
    </c:forEach>
  </c:forEach>
</c:forEach>

// -->
</script>


<script language="JavaScript" type="text/javascript">
   //add all the javascript messages here, this for internationalization of client side javascript messages
   var messagesData = new Array();
   messagesData={alert:"<fmt:message key="label.alert"/>",
        submitOnlyOnce:"<fmt:message key="label.submitOnlyOnce"/>",
        tsdfContainerIdIsNotInteger:"<fmt:message key="error.tsdfcontainerid.integer"/>",
        genContainerIdIsNotInteger:"<fmt:message key="error.gencontainerid.integer"/>",
        tsdfShipDateFormat:"<fmt:message key="error.tsdfshipdateformat"/>",
        genShipDateFormat:"<fmt:message key="error.genshipdateformat"/>"};
</script>
</head>

<body bgcolor="#ffffff" onload="setSearchFrameSize();">


<tcmis:form action="/tsdfcontainerreport.do" onsubmit="return submitFrameOnlyOnce();" target="resultFrame">

<div class="interface" id="searchMainPage">

<div class="contentArea">

<!-- Search Option Begins -->
<%@ include file="/common/waste/tsdfcontainerreportsearch.jsp" %>
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

</div>
<!-- Hidden elements end -->

</div> <!-- close of interface -->

</tcmis:form>
</body>
</html:html>

