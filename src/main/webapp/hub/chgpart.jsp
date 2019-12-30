<%@ page language="java" %>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>

<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>

<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>



<html:html lang="true">

<head>

  <title>Haas TCM Parts</title>

  <link rel="STYLESHEET" type="text/css" href="css/global.css">

  <SCRIPT SRC="/js/edisupport.js" LANGUAGE="JavaScript"></SCRIPT>

</head>



<body onload='resizeWindow();'>



<h3>Part List</h3>



<script>

function chargeSelect(id) 

{

   var span_block = "catpartspan_<c:out value="${RowNum}"/>";

   opener.document.ordstat.catpartno_<c:out value="${RowNum}"/>.value = id;

   opener.document.ordstat.reset_sel_<c:out value="${RowNum}"/>.checked = true;

   opener.document.getElementById(span_block).innerHTML = id;      

   window.close();

   // alert(val);

}

</script>



  <TABLE  BORDER="0" BGCOLOR="#a2a2a2" CELLSPACING=1 CELLPADDING=2 CLASS="moveup">

  <c:set var="rowCount" value='${0}'/>

      <TR align="center">

        <td class="thheading">Part No</td>

        <td class="thheading">Description</td>

        <td class="thheading">Catalog UOS</td>

        <td class="thheading">UOS per pkg</td>

        <td class="thheading">Item ID</td>

        <td class="thheading">Haas Packaging</td>

        <td class="thheading">MR qty</td>

        <td class="thheading">Item Desc</td>

      </TR>    

  <c:forEach items="${partBeanCollection}" var="line">

    <c:set var="rowCount" value='${rowCount+1}'/>

    <c:choose>

      <c:when test="${rowCount % 2 == 0}" >

        <c:set var="colorClass" value='blue'/>

      </c:when>

      <c:otherwise>

        <c:set var="colorClass" value='white'/>

      </c:otherwise>

    </c:choose>

    

    <TR CLASS='<c:out value="${colorClass}"/>' onmouseover='className="yellow"' onmouseout='className="<c:out value="${colorClass}"/>"' onclick='chargeSelect("<c:out value="${line.catPartNo}"/>");'>

        <TD HEIGHT="38"><c:out value="${line.catPartNo}"/></TD>

        <TD HEIGHT="38"><c:out value="${line.partDescription}"/></TD>    

        <TD HEIGHT="38"><c:out value="${line.catalogUos}"/></TD>    

        <TD HEIGHT="38"><c:out value="${line.uosPerPackaging}"/></TD>    

        <TD HEIGHT="38"><c:out value="${line.itemId}"/></TD>    

        <TD HEIGHT="38"><c:out value="${line.tcmPackaging}"/></TD>    

        <TD HEIGHT="38"><c:out value="${line.mrQty}"/></TD> 

        <TD HEIGHT="38"><c:out value="${line.itemDesc}"/></TD>

    </TR>

   </c:forEach>

  </TABLE>



<script>

function resizeWindow() 

{

   var maxheight = 600;

   var height = 200 * <c:out value="${rowCount}"/> ;

   if (height > maxheight) {

     height = maxheight;

   }

   self.resizeTo(800,height);  

}

</script>

         



</body>



</html:html>

