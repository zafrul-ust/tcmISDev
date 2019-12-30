<%@ page contentType="text/html;charset=UTF-8" language="java"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/tcmis.tld" prefix="tcmis" %>

<%--<tcmis:loggedIn indicator="true" forwardPage="/hub/Home.do"/>--%>
<html:html lang="true">
<HEAD>
<META HTTP-EQUIV="Content-Type" CONTENT="text/html; charset=iso-8859-1">
<META HTTP-EQUIV="Pragma" CONTENT="no-cache">
<META HTTP-EQUIV="Expires" CONTENT="-1">
<LINK REL="SHORTCUT ICON" HREF="https://www.tcmis.com/images/buttons/tcmIS.ico"></LINK>
<LINK REL="stylesheet" TYPE="text/css" HREF="/css/clientpages.css"></LINK>
<SCRIPT SRC="/js/catalog.js" LANGUAGE="JavaScript"></SCRIPT>
<SCRIPT SRC="/js/cal/catalog.js" LANGUAGE="JavaScript"></SCRIPT>
<SCRIPT SRC="/js/calendar/AnchorPosition.js" LANGUAGE="JavaScript"></SCRIPT>
<SCRIPT SRC="/js/calendar/PopupWindow.js" LANGUAGE="JavaScript"></SCRIPT>

<title>
<fmt:message key="partnumbercomments.label.title"/>
</title>
</HEAD>

<body BGCOLOR="#FFFFFF" TEXT="#000000">

<DIV ID="TRANSITPAGE" STYLE="display: none;">
<P><BR><BR><BR></P>
<center>
 <fmt:message key="label.pleasewait"/>
</center>
</DIV>

<DIV ID="MAINPAGE" STYLE="">
<TABLE BORDER=0 WIDTH=100% CLASS="moveupmore">
<TR VALIGN="TOP">
<TD WIDTH="200">
<img src="/images/tcmtitlegif.gif" border=0 align="left">
</TD>
<TD ALIGN="right">
<img src="/images/tcmistcmis32.gif" border=0 align="right">
</TD>
</TR>
</Table>
<TABLE BORDER="0" CELLSPACING="0" CELLPADDING="0" WIDTH="100%" CLASS="moveup">
<TR><TD WIDTH="70%" ALIGN="LEFT" HEIGHT="22" CLASS="heading">
<B><fmt:message key="partnumbercomments.label.title"/></B>
</TD>
<TD WIDTH="30%" ALIGN="RIGHT" HEIGHT="22" CLASS="headingr">
</TD>
</TR>
</TABLE>

<html:form action="/partnumbercomments.do" onsubmit="return SubmitOnlyOnce();">

<TABLE BORDER="0" CELLSPACING="0" CELLPADDING="3" WIDTH="100%" CLASS="moveup">
<TR VALIGN="MIDDLE">

<TD WIDTH="7%" HEIGHT="35" ALIGN="RIGHT" CLASS="announce">
<B><fmt:message key="label.catalogid"/>:</B>&nbsp;
</TD>

<TD CLASS="announce" HEIGHT="35" WIDTH="10%" ALIGN="LEFT">
<c:out value='${param.catalogId}'/>
</TD>
</TR>

<TR VALIGN="MIDDLE">

<TD WIDTH="5%" HEIGHT="35" ALIGN="RIGHT" CLASS="announce">
<B><fmt:message key="label.partnumber"/>:</B>&nbsp;
</TD>

<TD CLASS="announce" HEIGHT="35" WIDTH="5%" ALIGN="LEFT">
<c:out value='${param.catPartNo}'/>
</TD>

</TR>
</TABLE>

<BR>
<c:set var="firstname" value='${sessionScope.personnelBean.firstName}'/>
<c:set var="lastname" value='${sessionScope.personnelBean.lastName}'/>
<INPUT TYPE="hidden" NAME="loginname" value="<c:out value="${firstname}" />&nbsp;<c:out value="${lastname}" />">
<INPUT TYPE="hidden" NAME="catPartNo" value="<c:out value='${param.catPartNo}'/>">
<INPUT TYPE="hidden" NAME="catalogId" value="<c:out value='${param.catalogId}'/>">

<TABLE BORDER="0" CELLSPACING="0" CELLPADDING="3" WIDTH="100%">
<TR VALIGN="MIDDLE">
<TD CLASS="announce" HEIGHT="35" WIDTH="10%" ALIGN="LEFT">
<html:button property="buttonCancel" styleClass="SUBMIT" onmouseover="className='SUBMITHOVER'" onmouseout="className='SUBMIT'" onclick= "cancel()">
     <fmt:message key="label.close"/>
</html:button>
</TD>

<TD CLASS="announce" HEIGHT="35" WIDTH="10%" ALIGN="LEFT">
<html:button property="buttonaddcomments" styleClass="SUBMIT" onmouseover="className='SUBMITHOVER'" onmouseout="className='SUBMIT'" onclick= "addCommentsLine()">
     <fmt:message key="partnumbercomments.button.addcommentline"/>
</html:button>

<TD CLASS="announce" HEIGHT="35" WIDTH="10%" ALIGN="LEFT">
<html:submit property="submitUpdate" styleClass="SUBMIT" onmouseover="className='SUBMITHOVER'" onmouseout="className='SUBMIT'">
     <fmt:message key="button.submit"/>
</html:submit>
</TD>
</TR>
</TABLE>

<c:set var="colorClass" value=''/>
<c:set var="dataCount" value='${0}'/>
<c:set var="deleteComment" value=''/>

<TABLE BORDER="0" width="98%" CELLPADDING="1" CELLSPACING="1">
<TR align="center">
<TH width="27%" CLASS="results"><fmt:message key="label.comments"/></TH>
<TH width="2%" CLASS="results"><fmt:message key="label.edit"/></TH>
<TH width="5%" CLASS="results"><fmt:message key="partnumbercomments.label.by"/></TH>
<TH width="5%" CLASS="results"><fmt:message key="label.date"/></TH>
<TH width="5%" CLASS="results"><fmt:message key="label.delete"/></TH>
</TR>
</TABLE>

<TABLE CLASS="columnar" WIDTH="100%" CLASS="moveup">
<TBODY>
<TR>
<TD VALIGN="TOP">
<DIV ID="orderdetail" CLASS="scroll_column250">
<TABLE BORDER="0" CELLSPACING="1" CELLPADDING="3" WIDTH="100%" ID="line_table" CLASS="moveup">

<c:forEach var="catPartCommentBean" items="${catPartCommentBeanCollection}" varStatus="status">
<c:set var="dataCount" value='${dataCount+1}'/>

<c:choose>
  <c:when test="${status.index % 2 == 0}" >
   <c:set var="colorClass" value='blue'/>
  </c:when>
  <c:otherwise>
   <c:set var="colorClass" value='white'/>
  </c:otherwise>
</c:choose>

<TR align="center" CLASS="<c:out value="${colorClass}"/>" ID="commentrowId<c:out value="${status.index}"/>">
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript">
<!--
var rowId  =  document.getElementById("commentrowId<c:out value="${status.index}"/>");
rowId.attachEvent('onmouseup',function () {
        catchcommentevent('<c:out value="${status.index}"/>');});
// -->
</SCRIPT>
  <INPUT TYPE="hidden" NAME="colorClass<c:out value="${status.index}"/>" value="<c:out value="${colorClass}"/>" >
  <INPUT TYPE="hidden" NAME="commentId" value="<c:out value="${status.current.commentId}"/>" >
  <INPUT TYPE="hidden" NAME="comments" ID="comments<c:out value="${status.index}"/>" value="<c:out value="${status.current.comments}"/>" >
  <INPUT TYPE="hidden" NAME="updateStatus" ID="updateStatus<c:out value="${status.index}"/>" value="" >

  <TD width="25%" ID="comment<c:out value="${status.index}"/>" ><c:out value="${status.current.comments}"/></TD>
  <TD width="2%"><FONT SIZE="4"><A HREF="#" onClick="editcommentevent('<c:out value="${status.index}"/>');return false;" NAME="anchor<c:out value="${status.index}"/>" ID="anchor<c:out value="${status.index}"/>" style="text-decoration:none">
  &diams;</A></FONT></TD>
  <TD width="5%" ID="enteredBy<c:out value="${status.index}"/>"><c:out value="${status.current.fullName}"/></TD>
  <TD width="5%" ID="date<c:out value="${status.index}"/>">
  <fmt:formatDate var="formattedEnteredDate" value="${status.current.dateEntered}" pattern="MM/dd/yyyy"/>
  <c:out value="${formattedEnteredDate}"/>
  </TD>

  <TD width="5%" ID="delete<c:out value="${status.index}"/>">
   <tcmis:facilityPermission indicator="true" userGroupId="Administrator" facilityId="${param.facilityId}">
     <c:set var="deleteComment" value='Yes'/>
   </tcmis:facilityPermission>

   <tcmis:facilityPermission indicator="true" userGroupId="SuperUser">
     <c:set var="deleteComment" value='Yes'/>
   </tcmis:facilityPermission>

   <c:if test="${sessionScope.personnelBean.personnelId == status.current.enteredBy}" >
    <c:set var="deleteComment" value='Yes'/>
   </c:if>

   <c:choose>
    <c:when test="${deleteComment == 'Yes'}">
     <INPUT TYPE="checkbox" NAME="deleteCheckBox" ID="deleteCheckBox<c:out value="${status.index}"/>" value="<c:out value="${status.current.commentId}"/>">
    </c:when>
    <c:otherwise>
    &nbsp;
    </c:otherwise>
   </c:choose>
  </TD>

</TR>
</c:forEach>
</TABLE>
</DIV>
</TD>
</TR>
</TBODY>
</TABLE>
<c:if test="${dataCount == 0}">
<TD width="100%" 'CLASS=white'>
<B><fmt:message key="main.nodatafound"/></B>
</TD>
</c:if>
</html:form>
</DIV>

<DIV ID="editcommentsdiv" STYLE="position:absolute;visibility:hidden;background-color:#CCCCCC;width:200px;height:50px;"></DIV>

</BODY>
</html:html>
