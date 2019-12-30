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

<script src="/js/common/waste/tsdfwastereceiving.js" language="JavaScript"></script>

<title>
<fmt:message key="tsdfwastereceiving.title"/>
</title>

<script language="JavaScript" type="text/javascript">
<%-- assumption here is that value and label are the same --%>
<!--

var altEpaId = new Array(
<c:forEach var="wasteTsdfSourceViewBean" items="${wasteTsdfSourceViewBeanCollection}" varStatus="status">
 <c:choose>
   <c:when test="${status.index > 0}">
    ,"<c:out value="${status.current.tsdfEpaId}"/>"
   </c:when>
   <c:otherwise>
    "<c:out value="${status.current.tsdfEpaId}"/>"
   </c:otherwise>
  </c:choose>
</c:forEach>
);

var altGeneratorCompanyId = new Array();
var altGeneratorCompanyName = new Array();
<c:forEach var="wasteTsdfSourceViewBean" items="${wasteTsdfSourceViewBeanCollection}" varStatus="status">
  <c:set var="currentEpaId" value='${status.current.tsdfEpaId}'/>
  <c:set var="generatorCompanyCollection" value='${status.current.generatorCompanyIdBeanCollection}'/>

  altGeneratorCompanyId["<c:out value="${currentEpaId}"/>"] = new Array(""
  <c:forEach var="generatorCompanyBean" items="${generatorCompanyCollection}" varStatus="status1">
        ,"<c:out value="${status1.current.generatorCompanyId}"/>"
  </c:forEach>
  );

  altGeneratorCompanyName["<c:out value="${currentEpaId}"/>"] = new Array("<fmt:message key="label.all"/>"
  <c:forEach var="generatorCompanyBean" items="${generatorCompanyCollection}" varStatus="status1">
        ,"<c:out value="${status1.current.generatorCompanyId}"/>"
  </c:forEach>
  );
</c:forEach>

var altFacilityId = new Array();
var altFacilityName = new Array();
<c:forEach var="wasteTsdfSourceViewBean" items="${wasteTsdfSourceViewBeanCollection}" varStatus="status">
  <c:set var="currentEpaId" value='${status.current.tsdfEpaId}'/>
  <c:set var="generatorCompanyCollection" value='${status.current.generatorCompanyIdBeanCollection}'/>
  <c:forEach var="generatorCompanyBean" items="${generatorCompanyCollection}" varStatus="status1">
    <c:set var="currentCompanyId" value='${status1.current.generatorCompanyId}'/>
    <c:set var="facilityBeanCollection" value='${status1.current.tsdfFacilityIdForGeneratorBeanCollection}'/>

      altFacilityId["<c:out value="${currentCompanyId}"/>"] = new Array(""
      <c:forEach var="facilityBean" items="${facilityBeanCollection}" varStatus="status2">
        ,"<c:out value="${status2.current.generatorFacilityStorageLocationId}"/>"
      </c:forEach>
      );

      altFacilityName["<c:out value="${currentCompanyId}"/>"] = new Array("<fmt:message key="label.all"/>"
      <c:forEach var="facilityBean" items="${facilityBeanCollection}" varStatus="status2">
        ,"<c:out value="${status2.current.generatorFacilityStorageLocation}"/>"
      </c:forEach>
      );

  </c:forEach>
</c:forEach>

// -->
</script>

<script language="JavaScript" type="text/javascript">
<!--
//add all the javascript messages here, this for internationalization of client side javascript messages
var messagesData = new Array();
messagesData={alert:"<fmt:message key="label.alert"/>",and:"<fmt:message key="label.and"/>",
submitOnlyOnce:"<fmt:message key="label.submitOnlyOnce"/>"};
// -->
</script>
</head>

<body bgcolor="#ffffff" onload="setSearchFrameSize();">


<tcmis:form action="/tsdfwastereceiving.do" onsubmit="return submitFrameOnlyOnce();" target="resultFrame">

<div class="interface" id="searchMainPage">

<div class="contentArea">

<!-- Search Option Begins -->
<%@ include file="/common/waste/tsdfwastereceivingsearch.jsp" %>
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
</div>
<!-- Hidden elements end -->

</div> <!-- close of interface -->

</tcmis:form>
</body>
</html:html>

