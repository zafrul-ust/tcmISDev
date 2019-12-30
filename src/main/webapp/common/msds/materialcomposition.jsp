<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
	"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html;charset=UTF-8" language="java"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>

<html:html lang="true">
<head>

<tcmis:fontSizeCss />
<!-- CSS for YUI -->
<link rel="stylesheet" type="text/css" href="/yui/build/container/assets/container.css" />
<script LANGUAGE = "JavaScript">
<!--

// -->
</script>
</head>
<body>
<TABLE BORDER="0" CELLPADDING="5" align="center" WIDTH="85%" >
	<tr>
		<th width="15%" align="center" bgcolor="#000066">
			<FONT FACE="Arial" SIZE="2" COLOR="#ffffff"><B><fmt:message key="label.casno"/></B></FONT>
		</th>
	  <c:if test="${showCustomerChemicalId == 'Y'}">
		<th width="10%" align="center" bgcolor="#000066">
			<FONT FACE="Arial" SIZE="2" COLOR="#ffffff"><B><fmt:message key="label.customerchemicalid"/></B></FONT>
		</th>
	  </c:if>	
		<th width="55%" align="center" bgcolor="#000066">
			<FONT FACE="Arial" SIZE="2" COLOR="#ffffff"><B><fmt:message key="label.name"/></B></FONT>
		</th>
		<th width="10%" align="center" bgcolor="#000066">
			<FONT FACE="Arial" SIZE="2" COLOR="#ffffff"><B><fmt:message key="label.lowerpercent"/></B></FONT>
		</th>
		<th width="10%" align="center" bgcolor="#000066">
			<FONT FACE="Arial" SIZE="2" COLOR="#ffffff"><B><fmt:message key="label.upperpercent"/></B></FONT>
		</th>
		<th width="10%" align="center" bgcolor="#000066">
			<FONT FACE="Arial" SIZE="2" COLOR="#ffffff"><B><fmt:message key="label.avepercent"/></B></FONT>
		</th>
		<th width="10%" align="center" bgcolor="#000066">
			<FONT FACE="Arial" SIZE="2" COLOR="#ffffff"><B><fmt:message key="label.trace"/></B></FONT>
		</th>
	</tr>

<c:set var="rowCount" value="0" />
<c:forEach var="compositionBean" items="${compositionColl}" varStatus="status">
	<tr>
	<c:choose>
	   <c:when test="${rowCount % 2 == 0}" >
	    <td width="15%" align="center"><FONT FACE="Arial" SIZE="2">${compositionBean.casNumber}</FONT></td>
	    <c:if test="${showCustomerChemicalId == 'Y'}">
			<td width="10%" align="center"><FONT FACE="Arial" SIZE="2">${compositionBean.companyChemicalId}</FONT></td>
		</c:if>	
		<td width="55%" align="left"><FONT FACE="Arial" SIZE="2">${compositionBean.chemicalId}</FONT></td>
		<td width="10%" align="center"><FONT FACE="Arial" SIZE="2">${compositionBean.percentLower}</FONT></td>
		<td width="10%" align="center"><FONT FACE="Arial" SIZE="2">${compositionBean.percentUpper}</FONT></td>
		<td width="10%" align="center"><FONT FACE="Arial" SIZE="2">${compositionBean.percent}</FONT></td>
		<td width="10%" align="center"><FONT FACE="Arial" SIZE="2"><c:if test ="${compositionBean.trace=='Y'}"><fmt:message key="label.yes"/></c:if></FONT></td>
	   </c:when>
	   <c:otherwise>
	    <td width="15%" align="center" BGCOLOR="#E6E8FA"><FONT FACE="Arial" SIZE="2">${compositionBean.casNumber}</FONT></td>
	    <c:if test="${showCustomerChemicalId == 'Y'}">
			<td width="10%" align="center" BGCOLOR="#E6E8FA"><FONT FACE="Arial" SIZE="2">${compositionBean.companyChemicalId}</FONT></td>
		</c:if>	
		<td width="55%" align="left" BGCOLOR="#E6E8FA"><FONT FACE="Arial" SIZE="2">${compositionBean.chemicalId}</FONT></td>
		<td width="10%" align="center" BGCOLOR="#E6E8FA"><FONT FACE="Arial" SIZE="2">${compositionBean.percentLower}</FONT></td>
		<td width="10%" align="center" BGCOLOR="#E6E8FA"><FONT FACE="Arial" SIZE="2">${compositionBean.percentUpper}</FONT></td>
		<td width="10%" align="center" BGCOLOR="#E6E8FA"><FONT FACE="Arial" SIZE="2">${compositionBean.percent}</FONT></td>
		<td width="10%" align="center" BGCOLOR="#E6E8FA"><FONT FACE="Arial" SIZE="2"><c:if test ="${compositionBean.trace=='Y'}"><fmt:message key="label.yes"/></c:if></FONT></td>
	   </c:otherwise>
	</c:choose>
	<c:set var="rowCount" value="${rowCount+1}" />
	</tr>
</c:forEach>
</TABLE>
<BR><BR>
      <center><fmt:message key="msg.avgepaguid"/><BR>
      <center>
      <TABLE BORDER="1" CELLPADDING="5" WIDTH="70%" >
	      <TR><TD>
	      	<fmt:message key="msds.msg71"/>
	      	<fmt:message key="msds.msg72"/>
	      	<fmt:message key="msds.msg73"/>
	      	<fmt:message key="msds.msg74"/>
	      	<fmt:message key="msds.msg75"/>
	      	<fmt:message key="msds.msg76"/>
	      	<fmt:message key="msds.msg77"/>
	      	<fmt:message key="msds.msg78"/>
	      	<fmt:message key="msds.msg79"/>
	      	<fmt:message key="msds.msg80"/>
	      	<fmt:message key="msds.msg81"/>
	      	<fmt:message key="msds.msg82"/>
	      	<fmt:message key="msds.msg83"/>
	      	<fmt:message key="msds.msg84"/>
	      </TD></TR>
      </table>
      </center>
</body>
</html:html>


