<%@ page language="java" %>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>

<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>

<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>



<html:html lang="true">

<head>

  <title>Haas TCM Problem/Rejected Orders</title>

  <link rel="STYLESHEET" type="text/css" href="/css/global.css">

</head>



<body>



<table width="100%">

  <tr>

     <%-- Banner --%>

     <td width="90%"><img src="/images/tcmtitlegif.gif"></td>

     <td class="pagetitle" align="right">Problems/Rejections</td>

  </tr>

  <tr>

     <td class="heading"><b>Web DBuy</b></td>

     <td class="headingr2" align="right" width="10%"><a class="tbar" href="orderList.jsp">Order&nbsp;List</a>&nbsp;&nbsp;&nbsp;&nbsp;<a class="tbar" href="logout.do">Log&nbsp;Off</a></td>

  </tr>         

</table>



<logic:present name="PersonnelBean">

    <table width='100%'>

    <tr>

      <td>

         <bean:write name="PersonnelBean" property="firstName"/>&nbsp;<bean:write name="PersonnelBean" property="lastName"/>

      </td>

    </tr>   

    </table>

</logic:present>



<br>



<c:if test="${! empty ProbRejectBeans}">

<form name="formSortData" action="sortview.do">

  <input type="hidden" name="sortfield" value="">

  <input type="hidden" name="path" value="Problem">

</form>



<script>

function sortView(field) {

  document.formSortData.sortfield.value = field;

  document.formSortData.submit();

}

</script>



  <TABLE  BORDER="0" BGCOLOR="#a2a2a2" CELLSPACING=1 CELLPADDING=2 CLASS="moveup">

  <c:set var="rowCount" value='${0}'/>

  <c:forEach items="${ProbRejectBeans}" var="problem">

    <c:set var="rowCount" value='${rowCount+1}'/>

    <c:choose>

      <c:when test="${rowCount % 2 == 0}" >

        <c:set var="colorClass" value='blue'/>

      </c:when>

      <c:otherwise>

        <c:set var="colorClass" value='white'/>

      </c:otherwise>

    </c:choose>

    <c:if test="${rowCount % 10 == 1}">              

    <tr>

      <td class="heading" onmouseover='className="heading3"' onmouseout='className="heading"' onclick="sortView('dbuy_status')">Status</td>

      <td class="heading" onmouseover='className="heading3"' onmouseout='className="heading"' onclick="sortView('radian_po')">PO Num.</td>

      <td class="heading" onmouseover='className="heading3"' onmouseout='className="heading"' onclick="sortView('supplier_name')">Supplier</td>

      <td class="heading" onmouseover='className="heading3"' onmouseout='className="heading"' onclick="sortView('ship_to_location_id')">Ship To</td>

      <td class="heading" onmouseover='className="heading3"' onmouseout='className="heading"' onclick="sortView('item_id')">Item</td>

      <td class="heading" onmouseover='className="heading3"' onmouseout='className="heading"' onclick="sortView('quantity')">Qty</td>

      <td class="heading" onmouseover='className="heading3"' onmouseout='className="heading"' onclick="sortView('unit_price')">Price (EA)</td>

      <td class="heading" onmouseover='className="heading3"' onmouseout='className="heading"' onclick="sortView('promised_date')">Need Date</td>

      <td class="heading" onmouseover='className="heading3"' onmouseout='className="heading"' onclick="sortView('carrier')">Carrier</td>

      <td class="heading" onmouseover='className="heading3"' onmouseout='className="heading"' onclick="sortView('comments')">Comments</td>

      <td class="heading" onmouseover='className="heading3"' onmouseout='className="heading"' onclick="sortView('status_date')">Date Rejected</td>

      <td class="heading" onmouseover='className="heading3"' onmouseout='className="heading"' onclick="sortView('dbuy_user_id')">User</td>

    </tr>

    </c:if>

    

    <tr>

        <TD CLASS='${colorClass}' HEIGHT="38">${problem.dbuyStatus}</TD>

        <TD CLASS='${colorClass}' HEIGHT="38">

          <c:choose>

             <c:when test="${problem.dbuyStatus eq 'REJECTED'}">

                ${problem.radianPo}

             </c:when>

             <c:otherwise>

                <a href='purchaseorder.do?po=${problem.radianPo}'>${problem.radianPo}</a>

             </c:otherwise>

          </c:choose>

        </TD>

        <TD CLASS='${colorClass}' HEIGHT="38">${problem.supplierName}</TD>

        <TD CLASS='${colorClass}' HEIGHT="38">${problem.shipToLocationId}</TD>

        <TD CLASS='${colorClass}' HEIGHT="38">${problem.itemId}</TD>

        <TD CLASS='${colorClass}' HEIGHT="38">${problem.quantity}</TD>

        <TD CLASS='${colorClass}' HEIGHT="38">${problem.unitPrice}</TD>

        <TD CLASS='${colorClass}' HEIGHT="38">

           <fmt:formatDate var="fmtPromiseDate" value="${problem.promisedDate}" pattern="MM/dd/yyyy"/>

           ${fmtPromiseDate}

        </TD>

        <TD CLASS='${colorClass}' HEIGHT="38">${problem.carrier}</TD>

        <TD CLASS='${colorClass}' HEIGHT="38">${problem.comments}</TD>

        <TD CLASS='${colorClass}' HEIGHT="38">

           <fmt:formatDate var="fmtStatusDate" value="${problem.statusDate}" pattern="MM/dd/yyyy"/>

          ${fmtStatusDate}

        </TD>

        <TD CLASS='${colorClass}' HEIGHT="38">${problem.dbuyUserId}</TD>    

    </tr>

  </c:forEach>    

  </TABLE>

  

</c:if>          



</body>



</html:html>

