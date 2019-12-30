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
<SCRIPT SRC="/js/ordertracking.js" LANGUAGE="JavaScript"></SCRIPT>

<script type="text/javascript" src="/js/common/commonutil.js"></script>	
<SCRIPT SRC="/js/common/clientinventory/inventorydetail.js" LANGUAGE="JavaScript"></SCRIPT>

<!-- This handels the menu style and what happens to the right click on the whole page -->
<script type="text/javascript" src="/js/menu/milonic_src.js"></script>
<script type="text/javascript" src="/js/menu/mmenudom.js"></script>
<script type="text/javascript" src="/js/menu/mainmenudata.js"></script>
<script type="text/javascript" src="/js/menu/contextmenu.js"></script>
<script type="text/javascript" src="/js/menu/mm_menueditapi.js"></script>	
<%@ include file="/common/rightclickmenudata.jsp" %>

<%@ include file="/common/locale.jsp" %>	

<title>
<fmt:message key="inventorydetail.label.title"/>: <c:out value="${param.catPartNo}"/>
</title>

<script LANGUAGE="JavaScript" TYPE="text/javascript">
<!--
with(milonic=new menuname("showAll")){
 top="offset=2"
 style = contextStyle;
 margin=3
 //aI("text=<fmt:message key="label.print"/>;url=javascript:window.print();");	
 aI("showmenu=showReceiptDocument;text=<fmt:message key="label.receivingdocument"/>;");
}

with(milonic=new menuname("showReceiptDocument")){
	style=submenuStyle;
	itemheight=17;
	//has to have at least one menu item, this is a place holder.
	//we will dynamically add items in javascript
	aI("text=test;");
}

drawMenus();

<%-- initialize for later use --%>
var documentId = new Array();
var documentName = new Array();

// -->
</script>

</HEAD>

<body BGCOLOR="#FFFFFF" TEXT="#000000" onmouseup="toggleContextMenuToNormal()">

<DIV ID="TRANSITPAGE" STYLE="display: none;">
<P><BR><BR><BR></P>
<center>
 <fmt:message key="label.pleasewait"/>
</center>
</DIV>

<DIV ID="MAINPAGE" STYLE="">

<TABLE BORDER="0" CELLSPACING="0" CELLPADDING="0" WIDTH="100%">

<tr>
	<td>
		<a href="#" onclick="createExcel(); return false;"><fmt:message key="label.createexcel"/></a>
	</td>
</tr>
<tr><td>&nbsp;</td></tr>

<TR><TD WIDTH="70%" ALIGN="LEFT" HEIGHT="22" CLASS="heading">
<B><fmt:message key="inventorydetail.label.title"/>: <c:out value="${param.catPartNo}"/></B>
</TD>
<TD WIDTH="30%" ALIGN="RIGHT" HEIGHT="22" CLASS="headingr">
</TD>
</TR>
</TABLE>

<BR>

<c:set var="colorClass" value=''/>
<c:set var="dataCount" value='${0}'/>

<c:forEach var="pkgInventoryDetailWebPrInventoryDetailBean" items="${pkgInventoryDetailWebPrInventoryDetailBeanCollection}" varStatus="status">
<c:set var="itemDescriptionBeanCollection" value='${status.current.itemDescription}'/>
<c:set var="onHandMaterialBeanCollection" value='${status.current.onHandMaterial}'/>
<c:set var="inSupplyChainBeanCollection" value='${status.current.inSupplyChain}'/>
</c:forEach>

<B><fmt:message key="label.itemdescription"/>:</B>
<TABLE  BORDER="0" BGCOLOR="#a2a2a2" CELLSPACING=1 CELLPADDING=2 WIDTH="100%">
<c:forEach var="ItemBean" items="${itemDescriptionBeanCollection}" varStatus="status">

<c:if test="${status.index % 10 == 0}">
<c:set var="dataCount" value='${dataCount+1}'/>
<TR align="center">
<TH width="10%" CLASS="results"><fmt:message key="label.item"/></TH>
<TH width="90%" CLASS="results"><fmt:message key="label.description"/></TH>
</TR>
</c:if>

<c:choose>
  <c:when test="${status.index % 2 == 0}" >
   <c:set var="colorClass" value='CLASS=blue'/>
  </c:when>
  <c:otherwise>
   <c:set var="colorClass" value='CLASS=white'/>
  </c:otherwise>
</c:choose>

<TR align="center">
  <TD <c:out value="${colorClass}"/> width="10%"><c:out value="${status.current.itemId}"/></B></TD>
  <TD <c:out value="${colorClass}"/> width="90%"><c:out value="${status.current.itemDesc}"/></TD>
</TR>
</c:forEach>

<c:if test="${dataCount == 0}">
<TD width="100%" CLASS="white">
<fmt:message key="main.nodatafound"/>
</TD>
</c:if>
</TABLE>

<BR>
<c:set var="showReportUsage" value="${tcmis:isCompanyFeatureReleased(personnelBean,'DisplayChargeNoOwnerSeqment', 'ALL',param.companyId)}"/>
<c:set var="colorClass" value=''/>
<c:set var="dataCount" value='${0}'/>
<B><fmt:message key="inventorydetail.label.onhandmaterial"/>:</B>
<TABLE  BORDER="0" BGCOLOR="#a2a2a2" CELLSPACING=1 CELLPADDING=2 WIDTH="100%">
<c:forEach var="inventoryDetailOnHandMaterialBean" items="${onHandMaterialBeanCollection}" varStatus="status">

<c:if test="${status.index % 10 == 0}">
<c:set var="dataCount" value='${dataCount+1}'/>
<TR align="center">
<TH width="10%" CLASS="results"><fmt:message key="label.item"/></TH>
<TH width="10%" CLASS="results"><fmt:message key="label.status"/></TH>
<TH width="15%" CLASS="results"><fmt:message key="label.inventorygroup"/></TH>
<TH width="10%" CLASS="results"><fmt:message key="label.qty"/></TH>
<TH width="15%" CLASS="results"><fmt:message key="label.lot"/></TH>
<c:if test="${showReportUsage}">
	<TH width="10%" CLASS="results"><fmt:message key="label.owner"/></TH>
	<TH width="15%" CLASS="results"><fmt:message key="label.program"/></TH>
    <TH width="15%" CLASS="results"><fmt:message key="label.trace"/></TH>
</c:if>
<TH width="10%" CLASS="results"><fmt:message key="label.expdate"/></TH>
<TH width="10%" CLASS="results"><fmt:message key="label.qualitynote"/></TH>
<TH width="10%" CLASS="results"><fmt:message key="inventorydetail.label.readytoship"/></TH>
</TR>
</c:if>

<c:choose>
  <c:when test="${status.index % 2 == 0}" >
   <c:set var="colorClass" value='CLASS=blue'/>
  </c:when>
  <c:otherwise>
   <c:set var="colorClass" value='CLASS=white'/>
  </c:otherwise>
</c:choose>

<fmt:formatDate var="expireDate" value="${status.current.expireDate}" pattern="${dateFormatPattern}"/>
<fmt:formatDate var="expireYear" value="${status.current.expireDate}" pattern="yyyy"/>
<c:if test="${expireYear == '3000'}">
	<c:set var="expireDate"><fmt:message key="label.indefinite"/></c:set>
</c:if>

<c:choose>
	<c:when test="${!empty status.current.receiptDocumentColl}">
		<%-- creating javascript array so we can build submenu dynamically --%>
		<script language="JavaScript" type="text/javascript">
		<!--
			documentId[<c:out value="${status.index}"/>] = new Array(
			 <c:forEach var="receiptDocumentBean" items="${status.current.receiptDocumentColl}" varStatus="status2">
			 	<c:if test="${status2.index > 0}">,</c:if>
				{ receiptId: "<c:out value="${status2.current.receiptId}"/>",
				  documentId: "<c:out value="${status2.current.documentId}"/>"
				}
			 </c:forEach>
			 );
	
		  documentName[<c:out value="${status.index}"/>] = new Array(
			 <c:forEach var="receiptDocumentBean" items="${status.current.receiptDocumentColl}" varStatus="status2">
				<fmt:formatDate var="formattedDocumentDate" value="${status2.current.documentDate}" pattern="${dateFormatPattern}"/>
				<c:choose>
				  <c:when test="${status2.index == 0}">
					 "<c:out value="${status2.current.documentTypeDesc} ${status2.current.documentName} ${formattedDocumentDate}" escapeXml="false"/>"
				  </c:when>
				  <c:otherwise>
					 ,"<c:out value="${status2.current.documentTypeDesc} ${status2.current.documentName} ${formattedDocumentDate}" escapeXml="false"/>"
				  </c:otherwise>
				</c:choose>
			 </c:forEach>
			 );
		// -->
		</script>
		<TR align="center" onmouseup="showReceiptDocumentMenu('<c:out value="${status.index}"/>')">
	</c:when>
	<c:otherwise>
		<TR align="center">
	</c:otherwise>
</c:choose>
  <TD <c:out value="${colorClass}"/> width="5%"><c:out value="${status.current.itemId}"/></TD>
  <TD <c:out value="${colorClass}"/> width="5%"><c:out value="${status.current.lotStatus}"/></TD>
  <TD <c:out value="${colorClass}"/> width="5%"><c:out value="${status.current.inventoryGroup}"/></TD>
  <TD <c:out value="${colorClass}"/> width="5%"><c:out value="${status.current.quantity}"/></TD>
  <TD <c:out value="${colorClass}"/> width="5%"><c:out value="${status.current.mfgLot}"/></TD>
  <c:if test="${showReportUsage}">
		<TD <c:out value="${colorClass}"/> width="5%"><c:out value="${status.current.ownerSegmentId}"/></TD>
 		<TD <c:out value="${colorClass}"/> width="5%"><c:out value="${status.current.programId}"/></TD>
        <TD <c:out value="${colorClass}"/> width="5%"><c:out value="${status.current.traceId}"/></TD>
  </c:if>
  <TD <c:out value="${colorClass}"/> width="5%"><c:out value="${expireDate}"/></TD>
  <TD <c:out value="${colorClass}"/> width="5%"><c:out value="${status.current.qualityTrackingNumber}"/></TD>
  <TD <c:out value="${colorClass}"/> width="5%"><fmt:formatDate pattern="${dateFormatPattern}" value="${status.current.readyToShipDate}"/></TD>
  
  <input name="receiptId${status.index}" id="receiptId${status.index}" value="${status.current.receiptId}" type="hidden"/>
  
  
</TR>
</c:forEach>

<c:if test="${dataCount == 0}">
<TD width="100%" CLASS="white">
<fmt:message key="main.nodatafound"/>
</TD>
</c:if>
</TABLE>

<BR>
<c:set var="colorClass" value=''/>
<c:set var="dataCount" value='${0}'/>
<B><fmt:message key="inventorydetail.label.insupplychain"/>:</B>
<TABLE  BORDER="0" BGCOLOR="#a2a2a2" CELLSPACING=1 CELLPADDING=2 WIDTH="100%">
<c:forEach var="InventoryDetailInSupplyChainBean" items="${inSupplyChainBeanCollection}" varStatus="status">

<c:if test="${status.index % 10 == 0}">
<c:set var="dataCount" value='${dataCount+1}'/>
<TR align="center">
<TH width="10%" CLASS="results"><fmt:message key="label.item"/></TH>
<TH width="10%" CLASS="results"><fmt:message key="label.status"/></TH>
<TH width="15%" CLASS="results"><fmt:message key="label.inventorygroup"/></TH>
<TH width="10%" CLASS="results"><fmt:message key="label.qty"/></TH>
<TH width="10%" CLASS="results"><fmt:message key="inventorydetail.label.ref"/></TH>
<TH width="15%" CLASS="results"><fmt:message key="inventorydetail.label.readytoship"/></TH>
<TH width="30%" CLASS="results"><fmt:message key="label.notes"/></TH>
</TR>
</c:if>

<c:choose>
  <c:when test="${status.index % 2 == 0}" >
   <c:set var="colorClass" value='CLASS=blue'/>
  </c:when>
  <c:otherwise>
   <c:set var="colorClass" value='CLASS=white'/>
  </c:otherwise>
</c:choose>

<TR align="center">
  <TD <c:out value="${colorClass}"/> width="5%"><c:out value="${status.current.itemId}"/></TD>
  <TD <c:out value="${colorClass}"/> width="5%"><c:out value="${status.current.status}"/></TD>
  <TD <c:out value="${colorClass}"/> width="5%"><c:out value="${status.current.inventoryGroup}"/></TD>
  <TD <c:out value="${colorClass}"/> width="5%"><c:out value="${status.current.quantity}"/></TD>
  <TD <c:out value="${colorClass}"/> width="5%"><c:out value="${status.current.reference}"/></TD>
  <TD <c:out value="${colorClass}"/> width="5%"><fmt:formatDate pattern="${dateFormatPattern}" value="${status.current.readyToShipDate}"/></TD>
  <TD <c:out value="${colorClass}"/> width="5%"><c:out value="${status.current.notes}"/></TD>
</TR>
</c:forEach>

<c:if test="${dataCount == 0}">
<TD width="100%" CLASS="white">
<fmt:message key="main.nodatafound"/>
</TD>
</c:if>
</TABLE>

</DIV>

<!-- Hidden element start -->
<div id="hiddenElements" style="display: none;">
<input name="uAction" id="uAction" value="" type="hidden"/>
<input name="inventoryGroup" id="inventoryGroup" type="hidden" value="${param.inventoryGroup}">
<input name="catPartNo" id="catPartNo" type="hidden" value="${param.catPartNo}">
<input name="partGroupNo" id="partGroupNo" type="hidden" value="${param.partGroupNo}">
<input name="catalogId" id="catalogId" type="hidden" value="${param.catalogId}">
<input name="catalogCompanyId" id="catalogCompanyId" type="hidden" value="${param.catalogCompanyId}">
<input type="hidden" name="secureDocViewer" id="secureDocViewer" value='${tcmis:isCompanyFeatureReleased(personnelBean,'SecureDocViewer','',personnelBean.companyId)}'/>
<input type="hidden" name="companyId" id="companyId" value='${personnelBean.companyId}'/>
</div>
<!-- Hidden elements end -->

</BODY>
</html:html>
