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

<%@ include file="/common/locale.jsp" %>

<!--Use this tag to get the correct CSS class.
This looks at what the user's preffered font size and which application he is viewing to set the correct CSS. -->
<tcmis:gridFontSizeCss />

<link rel="stylesheet" type="text/css" href="/css/clientpages.css"></link>
<style type="text/css">
html {
  height: 100%;
  max-height:100%;
  margin-bottom: 1px;
  overflow: hidden;
}
</style>


<%-- Add any other stylesheets you need for the page here --%>

<script type="text/javascript" src="/js/common/formchek.js"></script>
<script type="text/javascript" src="/js/common/commonutil.js"></script>
<!-- This handles all the resizing of the page and frames -->
<%--NEW--%>
<script type="text/javascript" src="/js/common/grid/iframegridresizemain.js"></script>
<!-- This handels which key press events are disabeled on this page -->
<script type="text/javascript" src="/js/common/disableKeys.js"></script>

<!-- This handels the menu style and what happens to the right click on the whole page -->
<script type="text/javascript" src="/js/menu/milonic_src.js"></script>
<script type="text/javascript" src="/js/menu/mmenudom.js"></script>
<script type="text/javascript" src="/js/menu/mainmenudata.js"></script>
<script type="text/javascript" src="/js/menu/contextmenu.js"></script>
<%@ include file="/common/rightclickmenudata.jsp" %>

<script language="JavaScript" type="text/javascript">
<!--

//hasAdmin eq true
var messagesData = new Array();
messagesData={alert:"<fmt:message key="label.alert"/>",and:"<fmt:message key="label.and"/>",
validvalues:"<fmt:message key="label.validvalues"/>",submitOnlyOnce:"<fmt:message key="label.submitOnlyOnce"/>",
approverlabel:"<fmt:message key="label.use.approvers"/>" +":"
};
//-->
</script>
<script language="JavaScript" type="text/javascript">
<!--
<c:set var="dataCount" value='0'/>
//var jsonMainData = {
var jsonMainData = new Array();
var jsonMainData = {
rows:[
	<c:forEach var="bean" items="${approverCollection}" varStatus="status">
			<c:if test="${status.index > 0}">,</c:if>
			{ id:${status.index +1},
			 data:[
				  '${bean.mrLine}',
				  '<tcmis:jsReplace value="${bean.approvalType}"/>',
				  '${bean.approvalStatus}',
				  '<tcmis:jsReplace value="${bean.approverName}"/>',
                  '<tcmis:jsReplace value="${bean.approvalEmail}"/>',
                  '<tcmis:jsReplace value="${bean.approvalPhone}"/>',
                  '<fmt:formatDate value="${bean.approvalDate}" pattern="${dateFormatPattern}"/>',
				  '<tcmis:jsReplace value="${bean.approvalComment}" processMultiLines="true"/>'
			  ]
			}
 	<c:set var="dataCount" value='${dataCount+1}'/>
 	</c:forEach>
]};

parent.showApproverListWin(jsonMainData,messagesData.approverlabel );
// -->
</script>
</head>
<body>
</body>
</html:html>