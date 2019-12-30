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
<META HTTP-EQUIV="Content-Type" CONTENT="text/html; charset=iso-8859-1">
<META HTTP-EQUIV="Pragma" CONTENT="no-cache">
<META HTTP-EQUIV="Expires" CONTENT="-1">
<LINK REL="SHORTCUT ICON" HREF="https://www.tcmis.com/images/buttons/tcmIS.ico"></LINK>
<LINK REL="stylesheet" TYPE="text/css" HREF="/css/clientpages.css"></LINK>
<SCRIPT SRC="/js/inventory.js" LANGUAGE="JavaScript"></SCRIPT>
<LINK REL="stylesheet" TYPE="text/css" HREF="/css/rightclick.css"></LINK>
<SCRIPT SRC="/js/rightclick.js" LANGUAGE="JavaScript"></SCRIPT>
<SCRIPT SRC="/js/common/formchek.js" LANGUAGE="JavaScript"></SCRIPT>

<title>
<fmt:message key="label.inventory"/>
</title>

<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript">
<!--
var altFacilityId = new Array(
<c:forEach var="facilityIgViewOvBean" items="${facilityIgViewOvBeanCollection}" varStatus="status">
 <c:choose>
   <c:when test="${status.index > 0}">
    ,"<c:out value="${status.current.facilityId}"/>"
   </c:when>
   <c:otherwise>
    "<c:out value="${status.current.facilityId}"/>"
   </c:otherwise>
  </c:choose>
</c:forEach>
);

var altFacilityName = new Array(
<c:forEach var="facilityIgViewOvBean" items="${facilityIgViewOvBeanCollection}" varStatus="status">
 <c:choose>
   <c:when test="${status.index > 0}">
    ,"<c:out value="${status.current.facilityName}"/>"
   </c:when>
   <c:otherwise>
    "<c:out value="${status.current.facilityName}"/>"
   </c:otherwise>
  </c:choose>
</c:forEach>
);

var altInventoryGroup = new Array();
var altInventoryGroupName = new Array();
<c:forEach var="facilityIgViewOvBean" items="${facilityIgViewOvBeanCollection}" varStatus="status">
  <c:set var="currentFacility" value='${status.current.facilityId}'/>
  <c:set var="inventoryGroups" value='${status.current.inventoryGroups}'/>

  altInventoryGroup["<c:out value="${currentFacility}"/>"] = new Array(
  <c:forEach var="inventoryGroupDefinitionOvBean" items="${inventoryGroups}" varStatus="status1">
   <c:choose>
   <c:when test="${status1.index > 0}">
    ,"<c:out value="${status1.current.inventoryGroup}"/>"
   </c:when>
   <c:otherwise>
    "<c:out value="${status1.current.inventoryGroup}"/>"
   </c:otherwise>
  </c:choose>
  </c:forEach>
  );

  altInventoryGroupName["<c:out value="${currentFacility}"/>"] = new Array(
  <c:forEach var="inventoryGroupDefinitionOvBean" items="${inventoryGroups}" varStatus="status1">
   <c:choose>
   <c:when test="${status1.index > 0}">
    ,"<c:out value="${status1.current.inventoryGroupName}"/>"
   </c:when>
   <c:otherwise>
    "<c:out value="${status1.current.inventoryGroupName}"/>"
   </c:otherwise>
  </c:choose>
  </c:forEach>
  );
</c:forEach>

var menuskin = "skin1"; // skin0, or skin1
var display_url = 0; // Show URLs in status bar?
// -->
</SCRIPT>
</HEAD>

<c:set var="doexcelpopup" value='${doexcelpopup}'/>
<c:set var="buttonCreateExcel" value='${param.buttonCreateExcel}'/>
<c:choose>
  <c:when test="${!empty doexcelpopup && !empty buttonCreateExcel}" >
    <BODY BGCOLOR="#FFFFFF" TEXT="#000000" LINK="#FFFFFF" ALINK="" VLINK="#FFFF66" onLoad="doexcelpopup()">
  </c:when>
  <c:otherwise>
   <BODY BGCOLOR="#FFFFFF" TEXT="#000000" LINK="#FFFFFF" ALINK="" VLINK="#FFFF66">
  </c:otherwise>
</c:choose>

<TABLE BORDER=0 WIDTH=100% >
<TR VALIGN="TOP">
<TD WIDTH="200">
<img src="/images/tcmtitlegif.gif" border=0 align="left">
</TD>
<TD ALIGN="right">
<img src="/images/inventory.gif" border=0 align="right">
</TD>
</TR>
</Table>

<%@ include file="title.jsp" %>

<DIV ID="TRANSITPAGE" STYLE="display: none;">
<P><BR><BR><BR></P><center><font face=Arial SIZE=2><B><fmt:message key="label.pleasewait"/></B></FONT></center>
</DIV>

<DIV ID="MAINPAGE" STYLE="">
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript">
<!--
var rowId  =  document.getElementById("MAINPAGE");
rowId.attachEvent('onmouseup',function () {
        makeRightClickNormal();});
// -->
</SCRIPT>

<tcmis:form action="/inventory.do" onsubmit="return SubmitOnlyOnce();">

<TABLE BORDER="0" CELLSPACING="0" CELLPADDING="3" WIDTH="100%" CLASS="moveup">
<TR>

<TD WIDTH="8%" ALIGN="RIGHT" CLASS="announce">
<B><fmt:message key="label.searchtext"/>:</B>&nbsp;
</TD>

<TD WIDTH="20%">
<INPUT CLASS="HEADER" TYPE="text" NAME="searchText" value="<c:out value="${param.searchText}"/>" maxlength="20" size="30">
</TD>

<%--<TD WIDTH="10%">
<html:checkbox property="noOor"/>
<B><fmt:message key="inventory.label.donotshowoor"/></B>
</TD>

<TD WIDTH="13%" ALIGN="LEFT" ROWSPAN="2" CLASS="announce">
<html:checkbox property="onHand"/>
<B><fmt:message key="inventory.label.onhand"/></B>
</TD>
--%>

<TD WIDTH="10%" ALIGN="RIGHT" CLASS="announce">
<B><fmt:message key="inventory.label.expireswithin"/></B>&nbsp;
</TD>

<TD WIDTH="10%" ALIGN="LEFT">
<INPUT CLASS="HEADER" TYPE="text" NAME="expiresWithin" value="<c:out value="${param.expiresWithin}"/>" onChange="checkexpiresWithin()" maxlength="4" size="3">
<fmt:message key="label.days"/>
</TD>

<TD WIDTH="10%" CLASS="announce">
&nbsp;
</TD>

</TR>
<TR>

<TD WIDTH="8%" ALIGN="RIGHT" CLASS="announce">
<B><fmt:message key="label.facility"/></B>&nbsp;
</TD>
<TD  ALIGN="LEFT" WIDTH="20%">
<c:set var="selectedFacility" value='${param.facilityId}'/>
<select name="facilityId" onchange="facilityChanged()">
  <c:forEach var="facilityIgViewOvBean" items="${facilityIgViewOvBeanCollection}" varStatus="status">
    <c:set var="currentFacility" value='${status.current.facilityId}'/>
    <c:choose>
      <c:when test="${empty selectedFacility}" >
        <c:set var="selectedFacility" value=""/>
        <c:set var="inventoryGroupNameOvBeanCollection" value='${status.current.inventoryGroups}'/>
      </c:when>
      <c:when test="${currentFacility == selectedFacility}" >
        <c:set var="inventoryGroupNameOvBeanCollection" value='${status.current.inventoryGroups}'/>
      </c:when>
    </c:choose>

    <c:choose>
      <c:when test="${currentFacility == selectedFacility}">
        <option value="<c:out value="${currentFacility}"/>" selected><c:out value="${status.current.facilityName}"/></option>
      </c:when>
      <c:otherwise>
        <option value="<c:out value="${currentFacility}"/>"><c:out value="${status.current.facilityName}"/></option>
      </c:otherwise>
    </c:choose>
  </c:forEach>
</select>
</TD>

<TD WIDTH="15%" ALIGN="RIGHT" CLASS="announce">
<B><fmt:message key="inventory.label.expiresafter"/></B>
</TD>

<TD WIDTH="10%" ALIGN="LEFT" CLASS="announce">
<INPUT CLASS="HEADER" TYPE="text" NAME="expiresAfter" value="<c:out value="${param.expiresAfter}"/>" onChange="checkexpiresAfter()" maxlength="4" size="3">
<fmt:message key="label.days"/>
</TD>

<TD WIDTH="10%" ALIGN="LEFT" CLASS="announce">
<html:submit property="submitSearch" styleClass="SUBMIT" onmouseover="className='SUBMITHOVER'" onmouseout="className='SUBMIT'">
     <fmt:message key="label.search"/>
</html:submit>
</TD>

</TR>
<TR>

<TD WIDTH="8%" ALIGN="RIGHT" CLASS="announce">
<B><fmt:message key="label.inventory"/></B>&nbsp;
</TD>
<TD ALIGN="LEFT" WIDTH="20%">
<select name="inventory">
<c:set var="selectedInventory" value='${param.inventory}'/>
  <c:forEach var="inventoryGroupNameOvBean" items="${inventoryGroupNameOvBeanCollection}" varStatus="status">
    <c:set var="currentSelectedInventory" value='${status.current.inventoryGroup}'/>
    <c:choose>
      <c:when test="${selectedInventory == currentSelectedInventory}">
        <option value="<c:out value="${currentSelectedInventory}"/>" selected><c:out value="${status.current.inventoryGroupName}"/></option>
      </c:when>
      <c:otherwise>
        <option value="<c:out value="${currentSelectedInventory}"/>"><c:out value="${status.current.inventoryGroupName}"/></option>
      </c:otherwise>
    </c:choose>
  </c:forEach>
</select>
</TD>

<%--<TD WIDTH="13%" ALIGN="LEFT" CLASS="announce">
<html:checkbox property="onOrder"/>
<B><fmt:message key="label.onorder"/></B>
--%>

</TD>
<TD WIDTH="15%" ALIGN="RIGHT" CLASS="announce">
<B><fmt:message key="inventory.label.arriveswithin"/></B>
</TD>

<TD WIDTH="10%" CLASS="announce">
<INPUT CLASS="HEADER" TYPE="text" NAME="arrivesWithin" value="<c:out value="${param.arrivesWithin}"/>" onChange="checkarrivesWithin()" maxlength="4" size="3">
<fmt:message key="label.days"/>
</TD>

<TD WIDTH="10%" ALIGN="LEFT" CLASS="announce">
<html:submit property="buttonCreateExcel" styleClass="SUBMIT" onmouseover="className='SUBMITHOVER'" onmouseout="className='SUBMIT'">
     <fmt:message key="label.createexcelfile"/>
</html:submit>
</TD>

</TR>
</TABLE>

<TABLE BORDER="0" CELLSPACING=1 CELLPADDING=0 WIDTH="100%" CLASS="moveup">
<TR>
<TD width="10%" CLASS="announce">
</TD>
<TD width="30%" CLASS="announce">
<html:errors/>
<c:set var="submitSearch" value='${param.submitSearch}'/>
 <c:if test="${empty submitSearch && empty buttonCreateExcel}">
  <I><fmt:message key="hub.proceed"/></I>
  <c:set var="showResults" value='showSearchResults'/>
 </c:if>
<BR>
</TD>
</TR>
</TABLE>

</DIV>
<DIV ID="RESULTSPAGE" STYLE="">

<TABLE BORDER="0" BGCOLOR="#a2a2a2" CELLSPACING="1" CELLPADDING="2" WIDTH="100%">
<c:set var="colorClass" value=''/>
<c:set var="dataCount" value='${0}'/>

<c:forEach var="pkgInventoryDetailWebPrInventoryBean" items="${pkgInventoryDetailWebPrInventoryBeanCollection}" varStatus="status">

<c:if test="${status.index % 10 == 0}">
<c:set var="dataCount" value='${dataCount+1}'/>

<TR align="center">
<TH width="5%" CLASS="results" height="38"><fmt:message key="label.catalog"/></TH>
<TH width="5%" CLASS="results" height="38"><fmt:message key="label.part"/></TH>
<TH width="15%" CLASS="results" height="38"><fmt:message key="label.description"/></TH>
<TH width="5%" CLASS="results" height="38"><fmt:message key="label.type"/></TH>
<TH width="5%" CLASS="results" height="38"><fmt:message key="inventory.label.setpoint"/><BR><fmt:message key="inventory.label.setpointlabel"/></TH>
<TH width="5%" CLASS="results" height="38"><fmt:message key="label.invengroup"/></TH>
<TH width="8%" CLASS="results" height="38"><fmt:message key="inventory.label.inventoryuom"/></TH>
<%--<TH width="5%" CLASS="results" height="38"><fmt:message key="inventory.label.numperpart"/></TH>--%>
<TH width="5%" CLASS="results" height="38"><fmt:message key="label.available"/><BR><fmt:message key="inventory.label.lastcounted"/></TH>
<TH width="5%" CLASS="results" height="38"><fmt:message key="label.held"/></TH>
<TH width="5%" CLASS="results" height="38"><fmt:message key="label.onorder"/></TH>
<TH width="5%" CLASS="results" height="38"><fmt:message key="label.inpurchasing"/></TH>
<TH width="5%" CLASS="results" height="38"><fmt:message key="label.item"/></TH>
<TH width="10%" CLASS="results" height="38"><fmt:message key="inventory.label.componentdescription"/></TH>
<TH width="5%" CLASS="results" height="38"><fmt:message key="inventory.label.packaging"/></TH>
<TH width="5%" CLASS="results" height="38"><fmt:message key="label.manufacturer"/></TH>
<TH width="10%" CLASS="results" height="38"><fmt:message key="label.mfgpartno"/></TH>
</tr>
</c:if>

<c:choose>
  <c:when test="${status.index % 2 == 0}" >
   <c:set var="colorClass" value='blue'/>
  </c:when>
  <c:otherwise>
   <c:set var="colorClass" value='white'/>
  </c:otherwise>
</c:choose>

<TR align="center" CLASS="<c:out value="${colorClass}"/>" ID="rowId<c:out value="${status.index}"/>">
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript">
<!--
var rowId  =  document.getElementById("rowId<c:out value="${status.index}"/>");
rowId.attachEvent('onmouseup',function () {
        catchevent('<c:out value="${status.index}"/>');});
// -->
</SCRIPT>

<c:set var="itemId" value='${status.current.itemId}'/>
<c:set var="inventoryGroup" value='${status.current.inventoryGroup}'/>
<c:set var="catalogId" value='${status.current.catalogId}'/>
<c:set var="catPartNo" value='${status.current.catPartNo}'/>
<c:set var="partGroupNo" value='${status.current.partGroupNo}'/>

<c:set var="itemCollection"  value='${status.current.itemCollection}'/>
<bean:size id="listSize" name="itemCollection"/>
<c:set var="mainRowSpan" value='${status.current.rowSpan}' />

<INPUT TYPE="hidden" NAME="colorClass<c:out value="${status.index}"/>" value="<c:out value="${colorClass}"/>" >
<INPUT TYPE="hidden" NAME="childRowsSize<c:out value="${status.index}"/>" value="<c:out value="${listSize}"/>" >
<INPUT TYPE="hidden" NAME="itemId<c:out value="${status.index}"/>" value="<c:out value="${itemId}"/>" >
<INPUT TYPE="hidden" NAME="inventoryGroup<c:out value="${status.index}"/>" value="<c:out value="${inventoryGroup}"/>" >
<INPUT TYPE="hidden" NAME="inventoryGroupName<c:out value="${status.index}"/>" value="<c:out value='${status.current.inventoryGroupName}'/>" >
<INPUT TYPE="hidden" NAME="catalogId<c:out value="${status.index}"/>" value="<c:out value="${catalogId}"/>" >
<INPUT TYPE="hidden" NAME="catPartNo<c:out value="${status.index}"/>" value="<c:out value="${catPartNo}"/>" >
<INPUT TYPE="hidden" NAME="partGroupNo<c:out value="${status.index}"/>" value="<c:out value="${partGroupNo}"/>" >
<INPUT TYPE="hidden" NAME="issueGeneration<c:out value="${status.index}"/>" value="<c:out value='${status.current.issueGeneration}'/>" >

<TD width="5%" rowspan="<c:out value="${mainRowSpan}"/>"><c:out value="${catalogId}"/></TD>
<TD width="5%" rowspan="<c:out value="${mainRowSpan}"/>"><c:out value="${catPartNo}"/></TD>
<TD width="15%" rowspan="<c:out value="${mainRowSpan}"/>"><c:out value="${status.current.partDescription}"/></TD>
<TD width="5%" rowspan="<c:out value="${mainRowSpan}"/>"><c:out value="${status.current.stockingMethod}"/></TD>
<TD width="5%" rowspan="<c:out value="${mainRowSpan}"/>"><c:out value="${status.current.setPoints}"/>/<c:out value="${status.current.reorderQuantity}"/></TD>
<TD width="5%" rowspan="<c:out value="${mainRowSpan}"/>"><c:out value="${status.current.inventoryGroupName}"/></TD>
<TD width="8%" rowspan="<c:out value="${mainRowSpan}"/>"><c:out value="${status.current.itemPackaging}"/></TD>

<TD width="5%" rowspan="<c:out value="${mainRowSpan}"/>"><fmt:formatNumber maxFractionDigits="2" minFractionDigits="0"><c:out value="${status.current.qtyAvailable}"/></fmt:formatNumber>
  <c:if test="${!empty status.current.lastCountDate}">
   <fmt:formatDate var="formattedLastCountDate" value="${status.current.lastCountDate}" pattern="MM/dd/yyyy"/>
   <BR>(<c:out value="${formattedLastCountDate}"/>)
  </c:if>
</TD>
<TD width="5%" rowspan="<c:out value="${mainRowSpan}"/>"><fmt:formatNumber maxFractionDigits="2" minFractionDigits="0"><c:out value="${status.current.qtyHeld}"/></fmt:formatNumber></TD>
<TD width="5%" rowspan="<c:out value="${mainRowSpan}"/>"><fmt:formatNumber maxFractionDigits="2" minFractionDigits="0"><c:out value="${status.current.qtyOnOrder}"/></fmt:formatNumber></TD>
<TD width="5%" rowspan="<c:out value="${mainRowSpan}"/>"><fmt:formatNumber maxFractionDigits="2" minFractionDigits="0"><c:out value="${status.current.qtyInPurchasing}"/></fmt:formatNumber></TD>

<c:forEach var="pkgInventoryDetailItemBean" items="${itemCollection}" varStatus="status1">
  <c:if test="${status1.index > 0 && listSize > 1 }">
   <TR align="center" CLASS="<c:out value="${colorClass}"/>" ID="childRowId<c:out value="${status.index}"/><c:out value="${status1.index}"/>">
   <SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript">
   <!--
    var childRowId  =  document.getElementById("childRowId<c:out value="${status.index}"/><c:out value="${status1.index}"/>");
     childRowId.attachEvent('onmouseup',function () {
      catchevent('<c:out value="${status.index}"/>');});
    // -->
    </SCRIPT>
  </c:if>

  <c:set var="componentCollection"  value='${status1.current.componentCollection}'/>
  <bean:size id="componentSize" name="componentCollection"/>
  <INPUT TYPE="hidden" NAME="grandChildRowsSize<c:out value="${status.index}"/><c:out value="${status1.index}"/>" value="<c:out value="${componentSize}"/>" >

  <%--<TD width="5%" rowspan="<c:out value="${componentSize}"/>"><c:out value="${status1.current.itemsPerPart}"/></TD>--%>
  <TD width="5%" rowspan="<c:out value="${componentSize}"/>"><c:out value="${status1.current.itemId}"/></TD>

  <c:forEach var="pkgInventoryDetailComponentBean" items="${componentCollection}" varStatus="status2">
    <c:if test="${status2.index > 0 && componentSize > 1 }">
     <TR align="center" CLASS="<c:out value="${colorClass}"/>" ID="grnadChildRowId<c:out value="${status.index}"/><c:out value="${status1.index}"/><c:out value="${status2.index}"/>">
     <SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript">
     <!--
     var childRowId  =  document.getElementById("grnadChildRowId<c:out value="${status.index}"/><c:out value="${status1.index}"/><c:out value="${status2.index}"/>");
     childRowId.attachEvent('onmouseup',function () {
      catchevent('<c:out value="${status.index}"/>');});
     // -->
     </SCRIPT>
    </c:if>

    <TD width="5%"><c:out value="${status2.current.materialDesc}"/></TD>
    <TD width="5%"><c:out value="${status2.current.packaging}"/></TD>
    <TD width="5%"><c:out value="${status2.current.mfgDesc}"/></TD>
    <TD width="10%"><c:out value="${status2.current.mfgPartNo}"/></TD>

    <c:if test="${status2.index > 0 || componentSize ==1 }">
    </TR>
    </c:if>
  </c:forEach>

  <c:if test="${status1.index > 0 || listSize ==1 }">
   </TR>
  </c:if>
 </c:forEach>

</c:forEach>
</table>

<c:if test="${empty pkgInventoryDetailWebPrInventoryBeanCollection}" >
<c:if test="${pkgInventoryDetailWebPrInventoryBeanCollection != null}" >
<TABLE  BORDER="0" CELLSPACING=0 CELLPADDING=0 WIDTH="100%" CLASS="moveup">
<tr>
<TD HEIGHT="25" WIDTH="100%" VALIGN="MIDDLE" BGCOLOR="#a2a2a2">
<fmt:message key="main.nodatafound"/>
</TD>
</tr>
</TABLE>
</c:if>
</c:if>

</tcmis:form>
</DIV>

<DIV id="ie5menu" class="skin0" onMouseover="highlightie5()" onMouseout="lowlightie5()" onClick="jumptoie5();">
<DIV class="menuitems" url="javascript:showInventoryDetails();">Inventory Detail<BR></DIV>
<HR>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Plots
<DIV class="menuitems" url="javascript:showInventoryPlot(2);">&nbsp;&nbsp;Inventory - 2 months</DIV>
<DIV class="menuitems" url="javascript:showInventoryPlot(12);">&nbsp;&nbsp;Inventory - 1 year</DIV>
<DIV class="menuitems" url="javascript:showMrLeadTimePlot();">&nbsp;&nbsp;MR Lead Time</DIV>
<DIV class="menuitems" url="javascript:showSupplyLeadTimePlot();">&nbsp;&nbsp;Supply Lead Time</DIV>
<DIV class="menuitems" url="javascript:showIssues();">&nbsp;&nbsp;Issues</DIV>
<DIV class="menuitems" url="javascript:showReceipts();">&nbsp;&nbsp;Receipts</DIV>
</DIV>

</body>
</html:html>
