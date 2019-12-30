<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
        "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%@ taglib uri="/WEB-INF/tcmis.tld" prefix="tcmis" %>

<html>
<head>
  <title>DBuy Contract Pricing</title>
  <link rel="STYLESHEET" type="text/css" href="/css/global.css">
  <SCRIPT SRC="/js/edisupport.js" LANGUAGE="JavaScript"></SCRIPT>
</head>

<body onload='resizeWindow();'>
<h3>Price List</h3>

  <form name="formUpdatePrices" action='updatePrice.do'>
  <input type=submit name='updPrice' value='Update Price(s)'>
  <br>
  <TABLE  BORDER="0" BGCOLOR="#a2a2a2" CELLSPACING=1 CELLPADDING=2 CLASS="moveup">
  <c:set var="rowCount" value='${0}'/>
      <TR align="center">
        <td class="thheading">Select</td>
        <td class="thheading">Part No</td>
        <td class="thheading">End Date</td>
        <td class="thheading">Up To Quantity</td>
        <td class="thheading">Unit Price</td>
        <td class="thheading">New Price</td>
        <td class="thheading">Currency Id</td>
        <td class="thheading">Comments</td>
      </TR>    
  <c:forEach items="${ContractPriceBeans}" var="line">
    <c:set var="rowCount" value='${rowCount+1}'/>
    <c:choose>
      <c:when test="${rowCount % 2 == 0}" >
        <c:set var="colorClass" value='blue'/>
      </c:when>
      <c:otherwise>
        <c:set var="colorClass" value='white'/>
      </c:otherwise>
    </c:choose>
    
    <TR CLASS='<c:out value="${colorClass}"/>'>
        <td HEIGHT="38"><input type=checkbox name='price_sel_<c:out value="${rowCount}"/>'></td>
        <TD HEIGHT="38"><c:out value="${CatPartNoBean}"/></TD>
        <TD HEIGHT="38"><c:out value="${line.endDate}"/></TD>    
        <TD HEIGHT="38"><c:out value="${line.uptoQuantity}"/></TD>    
        <TD HEIGHT="38"><c:out value="${line.unitPrice}"/></TD>    
        <TD HEIGHT="38"><input type=text name='new_price_<c:out value="${rowCount}"/>' value='<c:out value="${line.unitPrice}"/>'></TD>
        <TD HEIGHT="38"><c:out value="${line.currencyId}"/></TD>    
        <TD HEIGHT="38"><c:out value="${line.comments}"/></TD>    
        <input type=hidden name='contract_id_<c:out value="${rowCount}"/>' value='<c:out value="${line.dbuyContractId}"/>'>
    </TR>
   </c:forEach>
  </TABLE>         
  </form>
  
<script>
function resizeWindow() 
{
   var maxheight = 600;
   var height = 250 * <c:out value="${rowCount}"/> ;
   if (height > maxheight) {
     height = maxheight;
   }
   self.resizeTo(800,height);  
}
</script>
           
</body>

</html>
