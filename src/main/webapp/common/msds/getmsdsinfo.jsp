<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
	"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html;charset=UTF-8" language="java"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%@ include file="/common/locale.jsp" %>

<script type="text/javascript" src="/js/common/commonutil.js"></script>

<html:html lang="true">
<head>
</head>
<body>
<span id="newSideViewSpan">		
<CENTER><B><fmt:message key="label.nfpa"/></B>
<c:choose>
	<c:when test="${empty sideViewInfo.flammability && empty sideViewInfo.health && empty sideViewInfo.reactivity && empty sideViewInfo.specificHazard}">
		<BR><B><fmt:message key="label.notlisted"/></B><BR>
	</c:when>
	<c:otherwise>
		<div class="nfpadiamond">
                                 <div class="fire">
                                 	${sideViewInfo.flammability}
                                 </div>
                                 <div class="health">
                                    ${sideViewInfo.health}
                                 </div>
                                 <div class="reactivity">
									${sideViewInfo.reactivity}
                                 </div>
                                 <div class="special" style='font-size: 20px;'>
                                    ${sideViewInfo.specificHazard}
                                 </div>
                              </div>
 <%--                       
		<table background="${hostUrl}/images/buttons/nfpa.gif" width="80px" height="82px" cellpadding=0 cellspacing=0 border=0 STYLE="background-repeat: no-repeat;">
		<tr><td valign="center" align="center"><FONT size="1" face=Arial COLOR="#ffffff"><p><BR>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		<B><FONT size=2>${sideViewInfo.flammability}</FONT>
		</b>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<BR>&nbsp;<B><FONT size=2>${sideViewInfo.health}</FONT>
		</b>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<FONT COLOR="#000000"><B>
		<FONT size=2>${sideViewInfo.reactivity}</FONT></b>&nbsp;<BR>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<B>
		<FONT size=2>${sideViewInfo.specificHazard}</FONT></b>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<BR><BR>
		</td>
		</tr>
		</table>   --%>      
	</c:otherwise>
</c:choose>

<BR><B><fmt:message key="label.hmis"/></B>

<BR>
<c:choose>
	<c:when test="${ empty sideViewInfo.hmisHealth && empty sideViewInfo.hmisFlammability && empty sideViewInfo.hmisReactivity && empty sideViewInfo.ppe}">
	    <BR><B><fmt:message key="label.notlisted"/></B><BR>
	</c:when>
	<c:otherwise>
		<div class="hmis">
                                 <div class="health">
                                    ${sideViewInfo.hmisHealth}
                                 </div>
                                 <div class="fire">
                                    ${sideViewInfo.hmisFlammability}
                                 </div>
                                 <div class="reactivity">
                                    ${sideViewInfo.hmisReactivity}
                                 </div>
                                 <div class="personalprotection">
                                    ${sideViewInfo.personalProtection}
                                 </div>
        </div>
 <%--                             
		<TABLE height="63px" cellSpacing=0 cellPadding=0 width="60px" border=0>
		<TR><TD BGCOLOR=#0000ff vAlign=TOP height="15" align="center"><FONT face=Arial color="#ffffff" size="2"><B>${sideViewInfo.hmisHealth}</B></FONT></TD></TR>
		<TR><TD BGCOLOR=#ff0000 vAlign=TOP height="15" align="center"><FONT face=Arial color="#ffffff" size="2"><B>${sideViewInfo.hmisFlammability}</B></FONT></TD></TR>
		<TR><TD BGCOLOR=#ffff00 vAlign=TOP height="15" align="center"><FONT face=Arial color="#000000" size="2"><B>${sideViewInfo.hmisReactivity}</B></FONT></TD></TR>
		<TR><TD BGCOLOR=#ffffff vAlign=TOP height="15" align="center"><FONT face=Arial color="#000000" size="2"><B>${sideViewInfo.ppe}</B></FONT></TD></TR>
	  </TABLE>  --%>  
	</c:otherwise>
</c:choose>
<BR>
<%-- 
<span id="openMSDSSpan"><a href="#" onClick="openMSDS()"><fmt:message key="label.openmsds"/></a></span>
</span>
--%> 

<span id="openMSDSSpan"><A HREF="#" ID="printlinkie" onClick="openMSDS()" STYLE="color:#0000ff;cursor:hand;"><U><fmt:message key="label.openmsds"/></U></A>
</span>

<script LANGUAGE = "JavaScript">
<!--
<fmt:formatDate var="fmtRevisionDate" value="${titleInfo.revisionDate}" pattern="${dateFormatPattern}"/>
<c:set var="localeDisplay" value=""/>
<c:if test="${titleInfo.localeDisplay != 'English' && !empty titleInfo.localeDisplay}">
    <c:set var="localeDisplay" value=" (${titleInfo.localeDisplay})"/>
</c:if>
parent.changeTitleInfo('${titleInfo.tradeName}','${fmtRevisionDate}${localeDisplay}','${titleInfo.mfgDesc}','${titleInfo.content}','${notice}','${revisionMeetsCompanyStandard}');

if('${titleInfo.content}'.endsWith(".txt") || '${titleInfo.content}'.endsWith(".html")) 
	$("openMSDSSpan").style.display="";
else
	$("openMSDSSpan").style.display="none"
	
parent.$("lowerSideViewSpan").innerHTML = $("newSideViewSpan").innerHTML;

// -->
</script>
</body>
</html:html>


