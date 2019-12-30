<%@ page language="java" %>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>

<html:html locale="true">
<head>
  <title>Haas TCM Carriers</title>
  <link rel="STYLESHEET" type="text/css" href="css/global.css">
</head>

<body>

<h3>Carrier List</h3>

<logic:present name="CarrierBeans">

<script>
function chargeSelect(id) 
{
   opener.document.AddLineForm.addCarrier.value = id;
   window.close();
   // alert(val);
}
</script>

  <TABLE  BORDER="0" BGCOLOR="#a2a2a2" CELLSPACING=1 CELLPADDING=2 CLASS="moveup">
  <c:set var="rowCount" value='${0}'/>
      <TR align="center">
        <td class="thheading" onmouseover='className="thheading3"' onmouseout='className="thheading"'>Carrier Code</td>
        <td class="thheading" onmouseover='className="thheading3"' onmouseout='className="thheading"'>Carrier Name</td>
        <td class="thheading" onmouseover='className="thheading3"' onmouseout='className="thheading"'>Hub</td>
        <td class="thheading" onmouseover='className="thheading3"' onmouseout='className="thheading"'>Carrier Method</td>
      </TR>    
  <c:forEach items="${CarrierBeans}" var="line">
    <c:set var="rowCount" value='${rowCount+1}'/>
    <c:choose>
      <c:when test="${rowCount % 2 == 0}" >
        <c:set var="colorClass" value='blue'/>
      </c:when>
      <c:otherwise>
        <c:set var="colorClass" value='white'/>
      </c:otherwise>
    </c:choose>
    
    <TR>
        <TD CLASS='<c:out value="${colorClass}"/>' onmouseover='className="yellowl"' onmouseout='className="<c:out value="${colorClass}"/>"' onclick='chargeSelect("<c:out value="${line.carrierCode}"/>");' HEIGHT="38"><c:out value="${line.carrierCode}"/></TD>
        <TD CLASS='<c:out value="${colorClass}"/>' onmouseover='className="yellowl"' onmouseout='className="<c:out value="${colorClass}"/>"' onclick='chargeSelect("<c:out value="${line.carrierCode}"/>");' HEIGHT="38"><c:out value="${line.carrierName}"/></TD>    
        <TD CLASS='<c:out value="${colorClass}"/>' onmouseover='className="yellowl"' onmouseout='className="<c:out value="${colorClass}"/>"' onclick='chargeSelect("<c:out value="${line.carrierCode}"/>");' HEIGHT="38"><c:out value="${line.hub}"/></TD>    
        <TD CLASS='<c:out value="${colorClass}"/>' onmouseover='className="yellowl"' onmouseout='className="<c:out value="${colorClass}"/>"' onclick='chargeSelect("<c:out value="${line.carrierCode}"/>");' HEIGHT="38"><c:out value="${line.carrierMethod}"/></TD>    
    </TR>
   </c:forEach>
  </TABLE>
      
</logic:present>          

</body>

</html:html>
