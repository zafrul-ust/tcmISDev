<%@ page language="java" %>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>

<html:html locale="true">
<head>
  <title>Haas TCM Inventory Groups</title>
  <link rel="STYLESHEET" type="text/css" href="css/global.css">
</head>

<body>

<h3>Inventory Group List</h3>

<logic:present name="InvGroupBeans">

<script>
function chargeSelect(id) 
{
   opener.document.AddLineForm.addInvGrp.value = id;
   window.close();
   // alert(val);
}
</script>

  <TABLE  BORDER="0" BGCOLOR="#a2a2a2" CELLSPACING=1 CELLPADDING=2 CLASS="moveup">
  <c:set var="rowCount" value='${0}'/>
      <TR align="center">
        <td class="thheading" onmouseover='className="thheading3"' onmouseout='className="thheading"'>Inventory Group</td>
        <td class="thheading" onmouseover='className="thheading3"' onmouseout='className="thheading"'>Inventory Group Name</td>
      </TR>    
  <c:forEach items="${InvGroupBeans}" var="line">
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
        <TD CLASS='<c:out value="${colorClass}"/>' onmouseover='className="yellowl"' onmouseout='className="<c:out value="${colorClass}"/>"' onclick='chargeSelect("<c:out value="${line.inventoryGroup}"/>");' HEIGHT="38"><c:out value="${line.inventoryGroup}"/></TD>
        <TD CLASS='<c:out value="${colorClass}"/>' onmouseover='className="yellowl"' onmouseout='className="<c:out value="${colorClass}"/>"' onclick='chargeSelect("<c:out value="${line.inventoryGroup}"/>");' HEIGHT="38"><c:out value="${line.inventoryGroupName}"/></TD>    
    </TR>
   </c:forEach>
  </TABLE>
      
</logic:present>          

</body>

</html:html>
