<%@ page language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>

<html:html lang="true">
<head>
  <title>Haas TCM DLA Orders </title>
  <LINK REL="stylesheet" TYPE="text/css" HREF="/css/global.css"></LINK>
  <script src="/js/util.js"></script>
</head>

<body>

  <table width="100%">
    <tr>
       <%-- Banner --%>
       <td width="90%"><img src="/images/tcmtitlegif.gif"></td>
       <td class="pagetitle" align="right" colspan='2'>VMI</td>
    </tr>
    <tr>
       <td class="heading"><b>VMI Stock-Out</b></td>
     </tr>         
  </table>

  <c:set var="vmicount" value="0"/>
 
  <TABLE  BORDER="0" BGCOLOR="#a2a2a2" CELLSPACING=1 CELLPADDING=2 WIDTH="100%" CLASS="moveup">
    <TR align="center">
      <td class="thheading" HEIGHT="32">NSN</td>
      <td class="thheading" HEIGHT="32">Quantity</td>
      <td class="thheading" HEIGHT="32">CC</td>
      <td class="thheading" HEIGHT="32">Date</td>
      <td class="thheading" HEIGHT="32">Doc Num</td>
      <td class="thheading" HEIGHT="32">Type</td>
      <td class="thheading" HEIGHT="32">DOC ID</td>
      <td class="thheading" HEIGHT="32">Line</td>
    </TR>    
 
    <c:set var="colorClass" value='white'/>
   
  <c:if  test="${empty vmiBeanCollection}">
    <tr><td colspan=7>No VMI records found.</td></tr>
  </c:if>
 
  <c:if test="${!empty vmiBeanCollection}">
    
    <c:forEach var="vmi" items="${vmiBeanCollection}" varStatus="status">
    
      <c:choose>
        <c:when test="${status.index % 2 == 0}" >
          <c:set var="colorClass" value='white'/>
        </c:when>
        <c:otherwise>
          <c:set var="colorClass" value='blue'/>
        </c:otherwise>
      </c:choose>

    <TR>
         <TD CLASS='<c:out value="${colorClass}"/>' HEIGHT="32"><c:out value="${vmi.catPartNo}"/></TD>
         <TD CLASS='<c:out value="${colorClass}"/>' HEIGHT="32"><c:out value="${vmi.quantity}"/></TD>
         <TD CLASS='<c:out value="${colorClass}"/>' HEIGHT="32"><c:out value="${vmi.supplyConditionCode}"/></TD>
         <TD CLASS='<c:out value="${colorClass}"/>' HEIGHT="32"><c:out value="${vmi.dateSent}"/></TD>
         <TD CLASS='<c:out value="${colorClass}"/>' HEIGHT="32"><c:out value="${vmi.transactionRefNum}"/><c:out value="${vmi.transactionNumSuffix}"/></TD>
         <TD CLASS='<c:out value="${colorClass}"/>' HEIGHT="32"><c:out value="${vmi.fmTransactionType}"/></TD>
         <TD CLASS='<c:out value="${colorClass}"/>' HEIGHT="32"><c:out value="${vmi.docIdCode}"/></TD>
         <TD CLASS='<c:out value="${colorClass}"/>' HEIGHT="32"><c:out value="${vmi.transactionRefLineNum}"/></TD>
         
         <c:set var="vmicount" value="${vmicount + vmi.quantity}"/>
    </TR>
    </c:forEach>       
   </c:if>
  </TABLE>
  <br><br>
  Total: <c:out value="${vmicount}"/>
  <br><br>
  
  <logic:present name="personnelBean">

    <c:set var="editQuantity" value="${false}"/>
    <tcmis:facilityPermission indicator="true" userGroupId="EditQuantity" facilityId="USGOV">
      <c:set var="editQuantity" value="${true}"/>
    </tcmis:facilityPermission>
  
  <logic:present name="vmiOrderBean">
  
  <c:if test='${editQuantity}'>  
  
  <table>
  <tr>
    <td>&nbsp;</td>
  </tr>
  <form name="DenyOrderForm" action="denyorder.do">
  <tr>
     <td>
     <span id="denyorderspan" onclick="expand('DenyOrderBlock');" style="texareaannounce"><img src="/images/plus.jpg" name="DenyAdd" id="1">&nbsp;Click here to deny order.</span>
     <div id="DenyOrderBlock" style="display:none">
     <br>
     <table>
       <tr>
         <td class="bluer">Customer PO:</td>
         <td><c:out value="${vmiOrderBean.customerPO}"/></td>
       </tr>
       <tr>
         <td class="bluer">Line:</td>
         <td><c:out value="${vmiOrderBean.poLine}"/></td>
       </tr>
       <tr>
         <td class="bluer">Requested quantity:</td>
         <td><c:out value="${vmiOrderBean.orderQty}"/></td>
       </tr>         
       <tr>
         <td class="bluer">Denial quantity:</td>
         <td><input type="text" size="3" name="denyQuantity" style="HEADER" value="0"></td>
       </tr>
       <tr>
         <td colspan=2 class="blue"><input type="button" name="Deny" value="Deny" class="SUBMIT" onclick='sendDenial();'></td>
         <input type='hidden' name='companyId' value='<c:out value="${vmiOrderBean.companyId}"/>'>
         <input type='hidden' name='transactionType' value='<c:out value="${vmiOrderBean.transactionType}"/>'>
         <input type='hidden' name='lineSeq' value='<c:out value="${vmiOrderBean.lineSeq}"/>'>
         <input type='hidden' name='changeOrderSeq' value='<c:out value="${vmiOrderBean.changeOrderSeq}"/>'>
         <input type='hidden' name='customerPO' value='<c:out value="${vmiOrderBean.customerPO}"/>'>
         <input type='hidden' name='poLine' value='<c:out value="${vmiOrderBean.poLine}"/>'>
       </tr>
     </table>
     </div>
     </td>
  </tr>
  </form>
  <script>
   function sendDenial() {
   
     var denialQty = document.DenyOrderForm.denyQuantity.value;
     
     if (denialQty<=0) {
       alert("Denial quantity cannot be zero!");
       return false;
     }
     
     if ( <c:out value="${vmiOrderBean.orderQty}"/> <= <c:out value="${vmicount}"/> ) {
       alert("You cannot deny an order when there is enough VMI stock left to fulfill the order!");
       return false;
     }
     
     if ( denialQty > <c:out value="${vmiOrderBean.orderQty}"/>) {
       alert("You cannot deny more quantity that was requested!");
       return false;
     }
   
     document.DenyOrderForm.submit();
   
   }
  </script>
  </table>
  
  </c:if>
  
  </logic:present>
  
  </logic:present>
  
</body>
</html:html>
