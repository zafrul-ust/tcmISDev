<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
        "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%@ taglib uri="/WEB-INF/tcmis.tld" prefix="tcmis" %>

<html>

<head>
  <META HTTP-EQUIV="Content-Type" CONTENT="text/html; charset=iso-8859-1">
  <META HTTP-EQUIV="Pragma" CONTENT="no-cache">
  <META HTTP-EQUIV="Expires" CONTENT="-1">
  <LINK REL="SHORTCUT ICON" HREF="https://www.tcmis.com/images/buttons/tcmIS.ico"></LINK>
  <LINK REL="stylesheet" TYPE="text/css" HREF="/css/global.css"></LINK>
  <SCRIPT SRC="/js/edisupport.js" LANGUAGE="JavaScript"></SCRIPT>
  
  <SCRIPT SRC="/js/util.js" LANGUAGE="JavaScript"></SCRIPT>
  <title>
    <fmt:message key="dbuyitems.label.title"/>
  </title>
</head>

<body>

  <DIV ID="TRANSITPAGE" STYLE="display: none;">
    <P><BR><BR><BR></P>
    <center>
      <fmt:message key="main.pleasewait"/>
    </center>
  </DIV>

  <DIV ID="MAINPAGE" STYLE="">
    <TABLE BORDER=0 WIDTH="100%" CLASS="moveupmore">
    <TR VALIGN="TOP">
      <TD WIDTH="200">
        <img src="images/tcmtitlegif.gif" border=0 align="left">
      </TD>
      <TD ALIGN="right">
        <img src="images/tcmistcmis32.gif" border=0 align="right">
      </TD>
    </TR>
    </Table>
    <TABLE BORDER="0" CELLSPACING="0" CELLPADDING="0" WIDTH="100%" CLASS="moveup">
      <TR><TD WIDTH="70%" ALIGN="LEFT" HEIGHT="22" CLASS="heading">
        <B><fmt:message key="dbuyitems.label.title"/></B>
      </TD>
        <TD WIDTH="30%" ALIGN="RIGHT" HEIGHT="22" CLASS="headingr">
        </TD> 
      </TR>
    </TABLE>
        
<!-- Error Messages Begins -->
<div class="spacerY">&nbsp;
<div id="errorMessagesArea" class="errorMessages">
  <html-el:errors/>
  <c:if test="${! empty ResultMessage}">
     Item ADD message: <c:out value="${ResultMessage}" />
  </c:if>
  <c:if test="${! empty ErrorMessage}">
     Error Message: <c:out value="${ErrorMessage}" />     
  </c:if>
</div>
</div>
<!-- Error Messages Ends -->
    
    <BR>
    
    <table  BORDER="0" BGCOLOR="#a2a2a2" CELLSPACING=1 CELLPADDING=2 WIDTH="100%" CLASS="moveup">
      <tr align="center">
        <td class="thheading">Item ID</td>
        <td class="thheading">Inventory Group</td>
        <td class="thheading">ShipTo Company ID</td>
        <td class="thheading">ShipTo Location ID</td>
        <td class="thheading">Supplier</td>
        <td class="thheading">Carrier</td>
        <td class="thheading">Buyer</td>
        <td class="thheading">Sourcer</td>
        <td class="thheading">Payment Terms</td>
        <td class="thheading">Contract Type</td>        
        <td class="thheading">Freight On Board</td>
        <td class="thheading">Multiple Of</td>
        <td class="thheading">Supplier Part No.</td>
        <td class="thheading">Supplier Contact ID</td>
        <td class="thheading">Remaining Shelf Life %</td>
        <td class="thheading">Critical Order Carrier</td>
        <td class="thheading">Ref Client Part No.</td>
        <td class="thheading">Ref Client PO No.</td>
        <td class="thheading">Round To Multiple</td>
        <td class="thheading">Consignment</td>
        <td class="thheading">Lead Time Days</td>
        <td class="thheading">Default Shipment Origin State</td>
        <td class="thheading">Transit Time In Days</td>
        <td class="thheading">End Date<br>MM/dd/yyyy</td>
        <td class="thheading">Upto Quantity</td>
        <td class="thheading">Item Unit Price</td>
        <td class="thheading">Add Charge Item Id 1</td>
        <td class="thheading">Add Charge Item Id 1 Unit Price</td>
        <td class="thheading">Add Charge Item Id 2</td>
        <td class="thheading">Add Charge Item Id 2 Unit Price</td>
        <td class="thheading">Insert Source</td>
        <td class="thheading">Currency Id</td>
        <td class="thheading">Supply Path</td>
      </tr>    

      <form name='formUpdateContract' action='addcontractitem.do'>
           
      <c:choose>
        <c:when test="${! empty contractBean}">
      <tr>
         <td class="blue"><input type='text' size='10' name='itemId' style='HEADER' value=''></td>         
         <td class="blue"><input type="text" size="10" name="inventoryGroup" style="HEADER" value='<c:out value="${contractBean.inventoryGroup}"/>'></td>     
         <td class="blue"><input type="text" size="10" name="shipToCompanyId" style="HEADER" value='<c:out value="${contractBean.shipToCompanyId}"/>'></td>
         <td class="blue"><input type="text" size="10" name="shipToLocationId" style="HEADER" value='<c:out value="${contractBean.shipToLocationId}"/>'></td>
         <td class="blue"><input type="text" size="10" name="supplier" style="HEADER" value='<c:out value="${contractBean.supplier}"/>'></td>     
         <td class="blue"><input type="text" size="10" name="carrier" style="HEADER" value='<c:out value="${contractBean.carrier}"/>'></td>     
         <td class="blue"><input type="text" size="10" name="buyer" style="HEADER" value='<c:out value="${contractBean.buyer}"/>'></td>     
         <td class="blue"><input type="text" size="10" name="sourcer" style="HEADER" value='<c:out value="${contractBean.sourcer}"/>'></td>     
         <td class="blue"><input type="text" size="10" name="paymentTerms" style="HEADER" value='<c:out value="${contractBean.paymentTerms}"/>'></td>     
         <td class="blue"><input type="text" size="10" name="contractType" style="HEADER" value='DBUY'></td>     
         <td class="blue"><input type="text" size="10" name="freightOnBoard" style="HEADER" value='<c:out value="${contractBean.freightOnBoard}"/>'></td>     
         <td class="blue"><input type="text" size="10" name="multipleOf" style="HEADER" value='<c:out value="${contractBean.multipleOf}"/>'></td>     
         <td class="blue"><input type="text" size="10" name="supplierPartNo" style="HEADER" value=''></td>     
         <td class="blue"><input type="text" size="10" name="supplierContactId" style="HEADER" value='<c:out value="${contractBean.supplierContactId}"/>'></td>     
         <td class="blue"><input type="text" size="10" name="remainingShelfLifePercent" style="HEADER" value='<c:out value="${contractBean.remainingShelfLifePercent}"/>'></td>     
         <td class="blue"><input type="text" size="10" name="criticalOrderCarrier" style="HEADER" value='<c:out value="${contractBean.criticalOrderCarrier}"/>'></td>     
         <td class="blue"><input type="text" size="10" name="refClientPartNo" style="HEADER" value='<c:out value="${contractBean.refClientPartNo}"/>'></td>     
         <td class="blue"><input type="text" size="10" name="refClientPoNo" style="HEADER" value='<c:out value="${contractBean.refClientPoNo}"/>'></td>     
         <td class="blue"><input type="text" size="10" name="roundToMultiple" style="HEADER" value='<c:out value="${contractBean.roundToMultiple}"/>'></td>     
         <td class="blue"><input type="text" size="10" name="consignment" style="HEADER" value='<c:out value="${contractBean.consignment}"/>'></td>     
         <td class="blue"><input type="text" size="10" name="leadTimeDays" style="HEADER" value='<c:out value="${contractBean.leadTimeDays}"/>'></td>     
         <td class="blue"><input type="text" size="10" name="defaultShipmentOriginState" style="HEADER" value='<c:out value="${contractBean.defaultShipmentOriginState}"/>'></td>     
         <td class="blue"><input type="text" size="10" name="transitTimeInDays" style="HEADER" value='<c:out value="${contractBean.transitTimeInDays}"/>'></td>     
         <td class="blue"><input type="text" size="10" name="endDate" style="HEADER" value=''></td>     
         <td class="blue"><input type="text" size="10" name="uptoQuantity" style="HEADER" value=''></td>     
         <td class="blue"><input type="text" size="10" name="itemUnitPrice" style="HEADER" value=''></td>     
         <td class="blue"><input type="text" size="10" name="addChargeItemId1" style="HEADER" value=''></td>     
         <td class="blue"><input type="text" size="10" name="addChargeItemId1UnitPrice" style="HEADER" value=''></td>     
         <td class="blue"><input type="text" size="10" name="addChargeItemId2" style="HEADER" value=''></td>     
         <td class="blue"><input type="text" size="10" name="addChargeItemId2UnitPrice" style="HEADER" value=''></td>     
         <td class="blue"><input type="text" size="10" name="insertSource" style="HEADER" value='Web'></td>     
         <td class="blue"><input type="text" size="10" name="currencyId" style="HEADER" value='USD'></td>              
         <td class="blue"><input type="text" size="10" name="supplyPath" style="HEADER" value='<c:out value="${contractBean.supplyPath}"/>'></td>     
       </tr>
        </c:when>
        <c:otherwise>
      <tr>
         <td class="blue"><input type='text' size='10' name='itemId' style='HEADER' value=''></td>         
         <td class="blue"><input type="text" size="10" name="inventoryGroup" style="HEADER" value=''></td>     
         <td class="blue"><input type="text" size="10" name="shipToCompanyId" style="HEADER" value=''></td>
         <td class="blue"><input type="text" size="10" name="shipToLocationId" style="HEADER" value=''></td>
         <td class="blue"><input type="text" size="10" name="supplier" style="HEADER" value=''></td>     
         <td class="blue"><input type="text" size="10" name="carrier" style="HEADER" value=''></td>     
         <td class="blue"><input type="text" size="10" name="buyer" style="HEADER" value=''></td>     
         <td class="blue"><input type="text" size="10" name="sourcer" style="HEADER" value=''></td>     
         <td class="blue"><input type="text" size="10" name="paymentTerms" style="HEADER" value=''></td>     
         <td class="blue"><input type="text" size="10" name="contractType" style="HEADER" value='DBUY'></td>     
         <td class="blue"><input type="text" size="10" name="freightOnBoard" style="HEADER" value=''></td>     
         <td class="blue"><input type="text" size="10" name="multipleOf" style="HEADER" value=''></td>     
         <td class="blue"><input type="text" size="10" name="supplierPartNo" style="HEADER" value=''></td>     
         <td class="blue"><input type="text" size="10" name="supplierContactId" style="HEADER" value=''></td>     
         <td class="blue"><input type="text" size="10" name="remainingShelfLifePercent" style="HEADER" value=''></td>     
         <td class="blue"><input type="text" size="10" name="criticalOrderCarrier" style="HEADER" value=''></td>     
         <td class="blue"><input type="text" size="10" name="refClientPartNo" style="HEADER" value=''></td>     
         <td class="blue"><input type="text" size="10" name="refClientPoNo" style="HEADER" value=''></td>     
         <td class="blue"><input type="text" size="10" name="roundToMultiple" style="HEADER" value=''></td>     
         <td class="blue"><input type="text" size="10" name="consignment" style="HEADER" value=''></td>     
         <td class="blue"><input type="text" size="10" name="leadTimeDays" style="HEADER" value=''></td>     
         <td class="blue"><input type="text" size="10" name="defaultShipmentOriginState" style="HEADER" value=''></td>     
         <td class="blue"><input type="text" size="10" name="transitTimeInDays" style="HEADER" value=''></td>     
         <td class="blue"><input type="text" size="10" name="endDate" style="HEADER" value=''></td>     
         <td class="blue"><input type="text" size="10" name="uptoQuantity" style="HEADER" value=''></td>     
         <td class="blue"><input type="text" size="10" name="itemUnitPrice" style="HEADER" value=''></td>     
         <td class="blue"><input type="text" size="10" name="addChargeItemId1" style="HEADER" value=''></td>     
         <td class="blue"><input type="text" size="10" name="addChargeItemId1UnitPrice" style="HEADER" value=''></td>     
         <td class="blue"><input type="text" size="10" name="addChargeItemId2" style="HEADER" value=''></td>     
         <td class="blue"><input type="text" size="10" name="addChargeItemId2UnitPrice" style="HEADER" value=''></td>     
         <td class="blue"><input type="text" size="10" name="insertSource" style="HEADER" value='Web'></td>     
         <td class="blue"><input type="text" size="10" name="currencyId" style="HEADER" value='USD'></td>              
         <td class="blue"><input type="text" size="10" name="supplyPath" style="HEADER" value=''></td>     
       </tr>        
        </c:otherwise>
      </c:choose>              
       <tr>
       <c:choose>
       <c:when test="${empty ResultMessage}">
         <td>
           <input type=submit value='Add Item' name="submitButton" onclick='return confirm("Add this item?");'>
         </td>
       </c:when>
       <c:otherwise>
         <td>
           <input type=button value='OK' name="okButton" onclick='window.close();'>
         </td>       
       </c:otherwise>
       </c:choose>
       </tr>
                    
       </form>
     </table>
     </div>
     </td>
  </tr>
  </table>

</body>
</html>
