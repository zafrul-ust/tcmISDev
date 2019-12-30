<%@ page language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>

<%@ include file="/common/locale.jsp" %>

<html:html lang="true">
<head>
  <title>Haas TCM DBUY Mismatch</title>
 <LINK REL="stylesheet" TYPE="text/css" HREF="/css/global.css"></LINK>
<!-- This handels which key press events are disabeled on this page -->
<script type="text/javascript" src="/js/common/disableKeys.js"></script> <base> 

<script LANGUAGE="JavaScript" TYPE="text/javascript">
<!--
windowCloseOnEsc = true;
// -->
</script>
</head>

<body>

  <table width="100%">
    <tr>
       <%-- Banner --%>
       <td width="90%"><img src="/images/tcmtitlegif.gif"></td>
       <td class="pagetitle" align="right" colspan='2'>DBuy</td>
    </tr>
    <tr>
       <td class="heading"><b>DBuy Mismatch</b></td>
     </tr>
  </table>

  <table>
    <c:forEach items="${AcknowledgeBeans}" var="preack">
    <tr>
      <td><b>Mismatch comments:</b></td>
      <td>&nbsp;</td>
      <td><c:out value="${preack.mismatchComments}"/></td>
    </tr>
    </c:forEach>
  </table>

  <h2>Received:</h2>
  <TABLE  BORDER="0" BGCOLOR="#a2a2a2" CELLSPACING=1 CELLPADDING=2 WIDTH="100%" CLASS="moveup">
    <TR align="center">
      <td class="thheading">PO Num.</td>
      <td class="thheading">Line</td>
      <td class="thheading">Item ID</td>
      <td class="thheading">Supplier Part #</td>
      <td class="thheading">Item Description</td>
      <td class="thheading">Supplier</td>
      <td class="thheading">Quantity</td>
      <td class="thheading">Price</td>
      <td class="thheading">Promise Date</td>
      <td class="thheading">Dock Date</td>
      <td class="thheading">Ship To Addr 1</td>
      <td class="thheading">Ship To Addr 2</td>
      <td class="thheading">Ship To City</td>
      <td class="thheading">Ship To Zip</td>
      <td class="thheading">Notes</td>
    </TR>

    <c:set var="colorClass" value='green'/>
    <c:forEach items="${AcknowledgeBeans}" var="ack">
    <TR>
      <TD CLASS='<c:out value="${colorClass}"/>' HEIGHT="38"><c:out value="${ack.radianPo}"/></TD>
      <TD CLASS='<c:out value="${colorClass}"/>' HEIGHT="38"><c:out value="${ack.poLine}"/></TD>
      <TD CLASS='<c:out value="${colorClass}"/>' HEIGHT="38"><c:out value="${ack.itemId}"/></TD>
      <TD CLASS='<c:out value="${colorClass}"/>' HEIGHT="38"><c:out value="${ack.supplierPartNo}"/></TD>
      <TD CLASS='<c:out value="${colorClass}"/>' HEIGHT="38"><c:out value="${ack.supplierPartDesc}"/></TD>
      <TD CLASS='<c:out value="${colorClass}"/>' HEIGHT="38"><c:out value="${ack.supplierName}"/></TD>
      <TD CLASS='<c:out value="${colorClass}"/>' HEIGHT="38" align="right"><c:out value="${ack.acceptedQuantity}"/></TD>
      <TD CLASS='<c:out value="${colorClass}"/>' HEIGHT="38" align="right"><c:out value="${ack.acceptedUnitPrice}"/></TD>
      <TD CLASS='red' HEIGHT="38">
        <fmt:formatDate var="fmtPromiseDate" value="${ack.promisedDate}" pattern="${dateFormatPattern}"/>
        <c:out value="${fmtPromiseDate}"/>
      </TD>
      <TD CLASS='red' HEIGHT="38">
        <fmt:formatDate var="fmtDockDate" value="${ack.dockDate}" pattern="${dateFormatPattern}"/>
        <c:out value="${fmtDockDate}"/>
      </TD>
      <TD CLASS='<c:out value="${colorClass}"/>' HEIGHT="38"><c:out value="${ack.shiptoAddressLine1}"/></TD>
      <TD CLASS='<c:out value="${colorClass}"/>' HEIGHT="38"><c:out value="${ack.shiptoAddressLine2}"/></TD>
      <TD CLASS='<c:out value="${colorClass}"/>' HEIGHT="38"><c:out value="${ack.shiptoCity}"/></TD>
      <TD CLASS='<c:out value="${colorClass}"/>' HEIGHT="38"><c:out value="${ack.shiptoZip}"/></TD>
      <TD CLASS='<c:out value="${colorClass}"/>' HEIGHT="38"><c:out value="${ack.poLineNotes}"/></TD>
    </TR>
    </c:forEach>


    <tr>
      <td colspan=14  bgcolor="white"><br><h2>Sent:</h2></td>
    </tr>

    <c:set var="colorClass" value='yellow'/>
    <c:forEach items="${SentBeans}" var="sent">
    <TR>
      <TD CLASS='<c:out value="${colorClass}"/>' HEIGHT="38"><c:out value="${sent.radianPo}"/></TD>
      <TD CLASS='<c:out value="${colorClass}"/>' HEIGHT="38"><c:out value="${sent.poLine}"/></TD>
      <TD CLASS='<c:out value="${colorClass}"/>' HEIGHT="38"><c:out value="${sent.itemId}"/></TD>
      <TD CLASS='<c:out value="${colorClass}"/>' HEIGHT="38">&nbsp;</TD>
      <TD CLASS='<c:out value="${colorClass}"/>' HEIGHT="38"><c:out value="${sent.itemShortDesc}"/></TD>
      <TD CLASS='<c:out value="${colorClass}"/>' HEIGHT="38"><c:out value="${sent.supplierName}"/></TD>
      <TD CLASS='<c:out value="${colorClass}"/>' HEIGHT="38" align="right"><c:out value="${sent.quantity}"/></TD>
      <TD CLASS='<c:out value="${colorClass}"/>' HEIGHT="38" align="right"><c:out value="${sent.unitPrice}"/></TD>
      <TD CLASS='<c:out value="${colorClass}"/>' HEIGHT="38">
      	<fmt:formatDate var="fmtNeedDate" value="${sent.needByDate}" pattern="${dateFormatPattern}"/>
        <c:out value="${fmtNeedDate}"/>
      </TD>
      <TD CLASS='<c:out value="${colorClass}"/>' HEIGHT="38">&nbsp;</TD>
      <TD CLASS='<c:out value="${colorClass}"/>' HEIGHT="38"><c:out value="${sent.shiptoAddressLine1}"/></TD>
      <TD CLASS='<c:out value="${colorClass}"/>' HEIGHT="38"><c:out value="${sent.shiptoAddressLine2}"/></TD>
      <TD CLASS='<c:out value="${colorClass}"/>' HEIGHT="38"><c:out value="${sent.shiptoCity}"/></TD>
      <TD CLASS='<c:out value="${colorClass}"/>' HEIGHT="38"><c:out value="${sent.shiptoZip}"/></TD>
      <TD CLASS='<c:out value="${colorClass}"/>' HEIGHT="38">&nbsp;</TD>
    </TR>
    </c:forEach>

  </TABLE>

</body>
</html:html>
