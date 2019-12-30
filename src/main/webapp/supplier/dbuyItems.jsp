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
    
    <TABLE BORDER="0" CELLSPACING="0" CELLPADDING="3" WIDTH="100%" CLASS="moveup">
    <TR>
      <TD><B><fmt:message key="dbuyitems.label.supplier"/></B></TD>
      <TD><B><fmt:message key="dbuyitems.label.inventorygroup"/></B></TD>
      <TD>&nbsp;<B><fmt:message key="dbuyitems.label.itemid"/></B></TD>
      <TD></TD>
    </TR>
    <TR VALIGN="MIDDLE">
    <form name="searchForm" action="contract.do" onSubmit="return SubmitOnlyOnce();">
      <input type="hidden" name="fromform" value="yes">
      <TD WIDTH="5%" HEIGHT="35" ALIGN="LEFT" CLASS="announce">
        <select name="supplierid">
           <option value=''>All</option>
         <c:forEach items="${SupplierBeanCollection}" var="supplier">        
           <option value='<c:out value="${supplier.supplier}"/>' <c:if test="${supplier.supplier == SearchSupplierBean}">SELECTED</c:if>><c:out value="${supplier.supplierName}"/></option>           
         </c:forEach>
        </select>
      </TD>
      <TD WIDTH="5%" HEIGHT="35" ALIGN="LEFT" CLASS="announce">
        <select name="inventorygroup">
           <option value=''>All</option>
         <c:forEach items="${InventoryGroupBeanCollection}" var="ig">        
           <option value='<c:out value="${ig.inventoryGroup}"/>' <c:if test="${ig.inventoryGroup == SearchInventoryGroupBean}">SELECTED</c:if>><c:out value="${ig.inventoryGroup}"/></option>           
         </c:forEach>
        </select>
      </TD>
      <TD WIDTH="5%" HEIGHT="35" ALIGN="LEFT" CLASS="announce">
        <INPUT CLASS="HEADER" TYPE="text" NAME="itemid" value='<logic:present name="SearchItemBean"><bean:write name="SearchItemBean"/></logic:present>'>
      </TD>
      <TD CLASS="announce" HEIGHT="35" WIDTH="5%" ALIGN="LEFT">
        <html-el:submit property="submitSearch" styleClass="SUBMIT" onmouseover="className='SUBMITHOVER'" onmouseout="className='SUBMIT'" onclick= "return search()">
          <fmt:message key="dbuyitems.button.search"/>
        </html-el:submit>   
      </TD>
    </form>
    </tr>
    </TABLE>
    
    <BR>

    <TABLE  BORDER="0" BGCOLOR="#a2a2a2" CELLSPACING=1 CELLPADDING=2 WIDTH="100%" CLASS="moveup">
      <TR align="center">
        <td class="thheading">Item ID</td>
        <td class="thheading">Inventory Group</td>
        <td class="thheading">ShipTo Company ID</td>
        <td class="thheading">ShipTo Location ID</td>
        <td class="thheading">Priority</td>
        <td class="thheading">Supplier</td>
        <td class="thheading">Carrier</td>
        <td class="thheading">Buyer</td>
        <td class="thheading">Sourcer</td>
        <td class="thheading">Payment Terms</td>
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
        <td class="thheading">Contract Reference Comments</td>
        <td class="thheading">Loading Comments</td>
        <td class="thheading">Pricing Type</td>
        <td class="thheading">Supply Path</td>
      </TR>    

      <c:set var="rowCount" value='${0}'/>
      <c:forEach items="${DbuyItemBeans}" var="item">
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
        <TD CLASS='<c:out value="${colorClass}"/>' HEIGHT="38"><c:out value="${item.itemId}"/></TD>
        <TD CLASS='<c:out value="${colorClass}"/>' HEIGHT="38"><c:out value="${item.inventoryGroup}"/></TD>
        <TD CLASS='<c:out value="${colorClass}"/>' HEIGHT="38"><c:out value="${item.shipToCompanyId}"/></TD>
        <TD CLASS='<c:out value="${colorClass}"/>' HEIGHT="38"><c:out value="${item.shipToLocationId}"/></TD>
        <TD CLASS='<c:out value="${colorClass}"/>' HEIGHT="38"><c:out value="${item.priority}"/></TD>        
        <TD CLASS='<c:out value="${colorClass}"/>' HEIGHT="38"><c:out value="${item.supplier}"/></TD>
        <TD CLASS='<c:out value="${colorClass}"/>' HEIGHT="38"><c:out value="${item.carrier}"/></TD>
        <TD CLASS='<c:out value="${colorClass}"/>' HEIGHT="38"><c:out value="${item.buyer}"/></TD>
        <TD CLASS='<c:out value="${colorClass}"/>' HEIGHT="38"><c:out value="${item.sourcer}"/></TD>
        <TD CLASS='<c:out value="${colorClass}"/>' HEIGHT="38"><c:out value="${item.paymentTerms}"/></TD>
        <TD CLASS='<c:out value="${colorClass}"/>' HEIGHT="38"><c:out value="${item.freightOnBoard}"/></TD>
        <TD CLASS='<c:out value="${colorClass}"/>' HEIGHT="38"><c:out value="${item.multipleOf}"/></TD>
        <TD CLASS='<c:out value="${colorClass}"/>' HEIGHT="38"><c:out value="${item.supplierPartNo}"/></TD>
        <TD CLASS='<c:out value="${colorClass}"/>' HEIGHT="38"><c:out value="${item.supplierContactId}"/></TD>
        <TD CLASS='<c:out value="${colorClass}"/>' HEIGHT="38"><c:out value="${item.remainingShelfLifePercent}"/></TD>
        <TD CLASS='<c:out value="${colorClass}"/>' HEIGHT="38"><c:out value="${item.criticalOrderCarrier}"/></TD>
        <TD CLASS='<c:out value="${colorClass}"/>' HEIGHT="38"><c:out value="${item.refClientPartNo}"/></TD>
        <TD CLASS='<c:out value="${colorClass}"/>' HEIGHT="38"><c:out value="${item.refClientPoNo}"/></TD>
        <TD CLASS='<c:out value="${colorClass}"/>' HEIGHT="38"><c:out value="${item.roundToMultiple}"/></TD>
        <TD CLASS='<c:out value="${colorClass}"/>' HEIGHT="38"><c:out value="${item.consignment}"/></TD>
        <TD CLASS='<c:out value="${colorClass}"/>' HEIGHT="38"><c:out value="${item.leadTimeDays}"/></TD>
        <TD CLASS='<c:out value="${colorClass}"/>' HEIGHT="38"><c:out value="${item.defaultShipmentOriginState}"/></TD>
        <TD CLASS='<c:out value="${colorClass}"/>' HEIGHT="38"><c:out value="${item.transitTimeInDays}"/></TD>
        <TD CLASS='<c:out value="${colorClass}"/>' HEIGHT="38"><c:out value="${item.contractReferenceComments}"/></TD>
        <TD CLASS='<c:out value="${colorClass}"/>' HEIGHT="38"><c:out value="${item.loadingComments}"/></TD>
        <TD CLASS='<c:out value="${colorClass}"/>' HEIGHT="38"><c:out value="${item.pricingType}"/></TD>        
        <TD CLASS='<c:out value="${colorClass}"/>' HEIGHT="38"><c:out value="${item.supplyPath}"/></TD>        
      </TR>
      </c:forEach>   
   </TABLE>
    
  </DIV>

</body>
</html>
