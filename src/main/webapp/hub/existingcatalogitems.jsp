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
<%--<tcmis:fontSizeCss currentCss="clientpages.css"/>--%>
<tcmis:gridFontSizeCss />
<link rel="stylesheet" type="text/css" href="/css/clientpages.css"></link>

<!-- CSS for YUI -->
<%--
<link rel="stylesheet" type="text/css" href="/yui/build/container/assets/container.css" />
--%>
<!-- Add any other stylesheets you need for the page here -->
<%@ include file="/common/locale.jsp" %>

<%--
<link rel="stylesheet" type="text/css" href="/css/dhtmlXGridHaas.css"></link>
--%>

<script src="/js/common/formchek.js" language="JavaScript"></script>
<script src="/js/common/commonutil.js" language="JavaScript"></script>
<!-- This handels which key press events are disabeled on this page -->
<script src="/js/common/disableKeys.js" language="JavaScript"></script>

<!-- This handels the menu style and what happens to the right click on the whole page -->
<script type="text/javascript" src="/js/menu/milonic_src.js"></script>
<script type="text/javascript" src="/js/menu/mmenudom.js"></script>
<script type="text/javascript" src="/js/menu/mainmenudata.js"></script>
<script type="text/javascript" src="/js/menu/contextmenu.js"></script>
<%@ include file="/common/rightclickmenudata.jsp" %>

<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxcommon.js"></script>		
<script type="text/javascript" src="/dhtmlxLayout/codebase/dhtmlxlayout.js"></script>
<script type="text/javascript" src="/dhtmlxWindows/codebase/dhtmlxwindows.js"></script>

<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxgrid.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxgridcell.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_form.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_srnd.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_json.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/excells/dhtmlxgrid_excell_customized.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxcommon_haas.js"></script>


<SCRIPT SRC="/js/ordertracking.js" LANGUAGE="JavaScript"></SCRIPT>

<title>
<fmt:message key="existingcatalogitems.label.title"/>&nbsp;<fmt:message key="label.catalog"/>:&nbsp<c:out value='${param.catalogId}'/>
&nbsp;<fmt:message key="label.and"/>&nbsp;<fmt:message key="label.partnumber"/>:&nbsp;<c:out value='${param.catPartNo}'/>
</title>
</HEAD>

<script language="JavaScript" type="text/javascript">
<!--
var windowCloseOnEsc = true;
//-->
</script>

<body BGCOLOR="#FFFFFF" TEXT="#000000">

<!-- Transit Page Begins -->
<div id="transitPage" style="display: none;" class="optionTitleBoldCenter">
	<br><br><br><fmt:message key="label.pleasewait"/>
	<br><br><br><img src="/images/rel_interstitial_loading.gif" align="middle">
</div>
<!-- Transit Page Ends -->


<div class="interface" id="mainPage" style="">
<!-- open contentArea -->
<div class="contentArea">
	<table id="resultsMaskTable" width="98%" border="0" cellpadding="0" cellspacing="0">
		<tr> 
			<td>
				<div class="roundcont contentContainer">
					<div class="roundright">
						<div class="roundtop">
							<div class="roundtopright">
								<img src="/images/rndBoxes/borderTL.gif" alt="" width="15" height="15" class="corner" style="display: none" />
							</div>
						</div>
						<div class="roundContent">
							<div class="boxhead"> <%-- boxhead Begins --%>
								<fmt:message key="existingcatalogitems.label.title"/> 
								<fmt:message key="label.catalog"/>: ${catalogId}
								&nbsp;&nbsp;&nbsp;&nbsp;
								<fmt:message key="label.partnumber"/>: ${catPartNo}
							</div>

							<c:set var="dataCount" value='${0}'/>
							
							<table BORDER="0" width="95%" CELLPADDING="1" CELLSPACING="1">
								<tr align="center">
								<th width="5%" CLASS="results"><fmt:message key="label.item"/></th>
								<th width="5%" CLASS="results"><fmt:message key="label.partnumber"/></th>
								<th width="3%" CLASS="results"><fmt:message key="label.partgroupnumber"/></th>
								<th width="2%" CLASS="results"><fmt:message key="label.status"/></th>
								<th width="2%" CLASS="results"><fmt:message key="label.priority"/></th>
								<th width="5%" CLASS="results"><fmt:message key="label.pkg"/></th>
								<th width="15%" CLASS="results"><fmt:message key="label.description"/></th>
								</tr>
							</table>
							<TABLE BORDER="0" CELLSPACING="1" CELLPADDING="3" WIDTH="95%" ID="line_table">
								
								<c:forEach var="existingCatalogViewBean" items="${existingCatalogItemsBeanCollection}" varStatus="status">
								<c:set var="dataCount" value='${dataCount+1}'/>
								
								<c:choose>
								  <c:when test="${status.index % 2 == 0}" >
								   <c:set var="colorClass" value='CLASS=ev_haas'/>
								  </c:when>
								  <c:otherwise>
								   <c:set var="colorClass" value='CLASS=odd_haas'/>
								  </c:otherwise>
								</c:choose>
								
								<TR align="center">
								  <TD <c:out value="${colorClass}"/> width="5%"><c:out value="${status.current.itemId}"/></TD>
								  <TD <c:out value="${colorClass}"/> width="5%"><c:out value="${status.current.catPartNo}"/></TD>
								  <TD <c:out value="${colorClass}"/> width="3%"><c:out value="${status.current.partGroupNo}"/></TD>
								  <TD <c:out value="${colorClass}"/> width="2%"><c:out value="${status.current.status}"/></TD>
								  <TD <c:out value="${colorClass}"/> width="2%"><c:out value="${status.current.priority}"/></TD>
								  <TD <c:out value="${colorClass}"/> width="5%"><c:out value="${status.current.kitPackaging}"/></TD>
								  <TD <c:out value="${colorClass}"/> width="15%"><c:out value="${status.current.itemDesc}"/></TD>
								</TR>
								
								</c:forEach>
								<c:if test="${dataCount == 0}">
								<TD width="100%" 'CLASS=white'>
								<fmt:message key="main.nodatafound"/>
								</TD>
								</c:if>
								
							</TABLE>
							
							<div id="footer" class="messageBar"></div>
						</div>
						<div class="roundbottom">
							<div class="roundbottomright">
								<img src="/images/rndBoxes/borderBL.gif" alt="" width="15" height="15" class="corner" style="display: none" />
							</div>
						</div>
					</div>
				</div>
			</td>
		</tr>
	</table>

</div>
<!-- close of contentArea -->

<%-- <TABLE BORDER=0 WIDTH=100% CLASS="moveupmore">
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
<TR><TD WIDTH="100%" ALIGN="LEFT" HEIGHT="22" CLASS="heading">
<B><fmt:message key="existingcatalogitems.label.title"/>&nbsp;<fmt:message key="label.catalog"/>:&nbsp<c:out value='${param.catalogId}'/>
&nbsp<fmt:message key="label.partnumber"/>&nbsp;<c:out value='${param.catPartNo}'/></B>
</TD>
</TR>
</TABLE>
<BR><BR>
<c:set var="colorClass" value=''/>
<c:set var="dataCount" value='${0}'/>

<TABLE BORDER="0" width="98%" CELLPADDING="1" CELLSPACING="1">
<TR align="center">
<TH width="5%" CLASS="results"><fmt:message key="label.item"/></TH>
<TH width="5%" CLASS="results"><fmt:message key="label.partnumber"/></TH>
<TH width="3%" CLASS="results"><fmt:message key="label.partgroupnumber"/></TH>
<TH width="2%" CLASS="results"><fmt:message key="label.status"/></TH>
<TH width="2%" CLASS="results"><fmt:message key="label.priority"/></TH>
<TH width="5%" CLASS="results"><fmt:message key="label.pkg"/></TH>
<TH width="15%" CLASS="results"><fmt:message key="label.description"/></TH>
</TR>
</TABLE>

<TABLE CLASS="columnar" WIDTH="100%" CLASS="moveup">
<TBODY>
<TR>
<TD VALIGN="TOP">
<DIV ID="orderdetail" CLASS="scroll_column250">
<TABLE BORDER="0" CELLSPACING="1" CELLPADDING="3" WIDTH="100%" ID="line_table" CLASS="moveup">

<c:forEach var="existingCatalogViewBean" items="${existingCatalogItemsBeanCollection}" varStatus="status">
<c:set var="dataCount" value='${dataCount+1}'/>

<c:choose>
  <c:when test="${status.index % 2 == 0}" >
   <c:set var="colorClass" value='CLASS=blue'/>
  </c:when>
  <c:otherwise>
   <c:set var="colorClass" value='CLASS=white'/>
  </c:otherwise>
</c:choose>

<TR align="center">
  <TD <c:out value="${colorClass}"/> width="4%"><c:out value="${status.current.itemId}"/></TD>
  <TD <c:out value="${colorClass}"/> width="5%"><c:out value="${status.current.catPartNo}"/></TD>
  <TD <c:out value="${colorClass}"/> width="3%"><c:out value="${status.current.partGroupNo}"/></TD>
  <TD <c:out value="${colorClass}"/> width="2%"><c:out value="${status.current.status}"/></TD>
  <TD <c:out value="${colorClass}"/> width="2%"><c:out value="${status.current.priority}"/></TD>
  <TD <c:out value="${colorClass}"/> width="5%"><c:out value="${status.current.kitPackaging}"/></TD>
  <TD <c:out value="${colorClass}"/> width="15%"><c:out value="${status.current.itemDesc}"/></TD>
</TR>

</c:forEach>
<c:if test="${dataCount == 0}">
<TD width="100%" 'CLASS=white'>
<fmt:message key="main.nodatafound"/>
</TD>
</c:if>

</TABLE>
</DIV>
</TD>
</TR>
</TBODY>
</TABLE> --%>
<BR>
<div style="text-align:center">
<input type="submit" class="inputBtns" onmouseover="this.className='inputBtns inputBtnsOver'" onmouseout="this.className='inputBtns'" onclick="return cancel()" value="<fmt:message key="label.close"/>"/>
</div>
</div>
</body>
</html:html>
