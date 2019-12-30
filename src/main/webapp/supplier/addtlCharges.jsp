<%@ page language="java" %>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>

<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>

<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>



<html:html lang="true">

<head>

  <title>Haas TCM Additional Charges</title>

  <link rel="STYLESHEET" type="text/css" href="/css/global.css">

</head>



<body>



<h3>Additional Charge List</h3>



<logic:present name="AddtlChargeBeans">



<script>

function chargeSelect(id,desc) 

{

   opener.document.AddLineForm.addItemID.value = id;

   opener.document.AddLineForm.addDescription.value = desc;

   window.close();

   // alert(val);

}

</script>



  <TABLE  BORDER="0" BGCOLOR="#a2a2a2" CELLSPACING=1 CELLPADDING=2 CLASS="moveup">

  <c:set var="rowCount" value='${0}'/>

      <TR align="center">

        <td class="thheading" onmouseover='className="thheading3"' onmouseout='className="thheading"'>Item ID</td>

        <td class="thheading" onmouseover='className="thheading3"' onmouseout='className="thheading"'>Description</td>

      </TR>    

  <c:forEach items="${AddtlChargeBeans}" var="line">

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

        <TD CLASS='${colorClass}' onmouseover='className="yellowl"' onmouseout='className="${colorClass}"' onclick='chargeSelect("${line.itemId}","${line.itemDesc}");' HEIGHT="38">${line.itemId}</TD>

        <TD CLASS='${colorClass}' onmouseover='className="yellowl"' onmouseout='className="${colorClass}"' onclick='chargeSelect("${line.itemId}","${line.itemDesc}");' HEIGHT="38">${line.itemDesc}</TD>    

    </TR>

   </c:forEach>

  </TABLE>

      

</logic:present>          



</body>



</html:html>

