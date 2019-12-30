<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
        "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%@ taglib uri="/WEB-INF/struts-nested.tld" prefix="nested" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html-el" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic-el" %>
<%@ taglib uri="/WEB-INF/tcmis.tld" prefix="tcmis" %>

<html:html lang="true">
<head>
<meta http-equiv="content-type" content="text/html; charset=UTF-8">
<meta http-equiv="expires" content="-1">
<link rel="shortcut icon" href="https://www.tcmis.com/images/buttons/tcmIS.ico"></link>
  <LINK REL="stylesheet" TYPE="text/css" HREF="/css/global.css"></LINK>
  <SCRIPT SRC="/js/edisupport.js" LANGUAGE="JavaScript"></SCRIPT>

  <SCRIPT SRC="/js/util.js" LANGUAGE="JavaScript"></SCRIPT>
  <title>
    DBuy Contract Pricing
  </title>
</head>

<body onload='resizeWindow();'>
<h3>Price List</h3>   


<c:if test="${UpdatePriceMessage==null}">
  <form name="formUpdatePrices" action='updateprice.do'>
  <input type=submit name='updPrice' value='Update Price'>
  <br>
  <TABLE  BORDER="0" BGCOLOR="#a2a2a2" CELLSPACING=1 CELLPADDING=2 CLASS="moveup">
  <c:set var="rowCount" value='${0}'/>
      <TR align="center">
        <td class="thheading">Part No</td>
        <td class="thheading">End Date</td>
        <td class="thheading">Up To Quantity</td>
        <td class="thheading">Unit Price</td>
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
        <TD HEIGHT="38"><input type=text name='part_no' value='<c:out value="${CatPartNoBean}"/>' READONLY></TD>
        <TD HEIGHT="38">
            <fmt:formatDate var="fmtEndDate" value="${line.endDate}" pattern="MM/dd/yyyy"/>            
            <input type=text name='end_date' value='<c:out value="${fmtEndDate}"/>' READONLY>
        </TD>    
        <TD HEIGHT="38"><input type=text name='up_to_quantity' value='<c:out value="${line.uptoQuantity}"/>' READONLY></TD>    
        <TD HEIGHT="38"><input type=text name='price' value='<c:out value="${line.unitPrice}"/>'></TD>
        <TD HEIGHT="38"><input type=text name='currency_id' value='<c:out value="${line.currencyId}"/>' READONLY></TD>    
        <TD HEIGHT="38"><input type=text name='comments' value='<c:out value="${line.comments}"/>' size=40></TD>    
        <input type=hidden name='contract_id' value='<c:out value="${line.dbuyContractId}"/>'>
    </TR>
   </c:forEach>
  </TABLE>         
  </form>
</c:if>

<c:if test="${UpdatePriceMessage!=null}">
  <center><h4><c:out value="${UpdatePriceMessage}"/></h4></center>
</c:if>

<c:set var="myRowCount" value="1"/>

<c:if test="${! empty rowCount}">
   <c:set var="myRowCount" value="${rowCount}"/>
</c:if>
<script>
function resizeWindow() 
{
   var maxheight = 600;
   var height = 450 * <c:out value="${myRowCount}"/> ;
   if (height > maxheight) {
     height = maxheight;
   }
   self.resizeTo(950,height);  
}
</script>
           
</body>

</html>
