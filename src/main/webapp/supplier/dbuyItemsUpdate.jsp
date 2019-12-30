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
    <fmt:message key="dbuyitems.label.title"/>
  </title>
</head>

<body>

  <DIV ID="TRANSITPAGE" STYLE="display: none;">
    <P><BR><BR><BR></P>
    <center>
      <fmt:message key="label.pleasewait"/>
    </center>
  </DIV>

  <DIV ID="MAINPAGE" STYLE="">
    <TABLE BORDER=0 WIDTH="100%" CLASS="moveupmore">
    <TR VALIGN="TOP">
      <TD WIDTH="200">
        <img src="/images/tcmtitlegif.gif" border=0 align="left">
      </TD>
      <TD ALIGN="right">
        <img src="/images/tcmistcmis32.gif" border=0 align="right">
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
      <TD><B><fmt:message key="label.supplier"/></B></TD>
      <TD><B><fmt:message key="label.inventorygroup"/></B></TD>
      <TD>&nbsp;<B><fmt:message key="label.itemid"/></B></TD>
      <TD></TD>
    </TR>
    <TR VALIGN="MIDDLE">
    <form name="searchForm" action="contract_x.do" onSubmit="return SubmitOnlyOnce();">
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
        <INPUT CLASS="HEADER" TYPE="text" NAME="itemid" value='<logic-el:present name="SearchItemBean"><c:out value="${SearchItemBean}"/></logic-el:present>'>
      </TD>
      <TD WIDTH="5%" HEIGHT="35" ALIGN="LEFT" CLASS="announce">
        <INPUT CLASS="HEADER" TYPE="checkbox" NAME="status" value="statusok" checked>"Active" items only
      </TD>
      <TD CLASS="announce" HEIGHT="35" WIDTH="5%" ALIGN="LEFT">
        <html-el:submit property="submitSearch" styleClass="SUBMIT" onmouseover="className='SUBMITHOVER'" onmouseout="className='SUBMIT'" onclick= "return search()">
          <fmt:message key="button.search"/>
        </html-el:submit>   
      </TD>
    </form>
    </tr>
    </TABLE>
    
    <BR>
    
    <form name="formSortData" action="sortcontract.do">
      <input type="hidden" name="sortfield" value="">
      <input type="hidden" name="path" value="Order">
    </form>

    <script>
    function sortView(field) {
      document.formSortData.sortfield.value = field;
      document.formSortData.submit();
    }
    </script>

    <form name="formUpdatePrice" action="viewprice.do" target='_blank'>
      <input type=hidden name="contract_id" value="">
      <input type=hidden name="cat_part_no" value="">
    </form>
    
    <script>
    function updatePricing(contract_id, cat_part_no) {
       document.formUpdatePrice.contract_id.value = contract_id;
       document.formUpdatePrice.cat_part_no.value = cat_part_no;
       document.formUpdatePrice.submit();
    }
    </script>
    
    <form name="formNewItem" action="newitem.do" target='_blank'>
      <input type=hidden name="contract_id" value="">
      <input type=hidden name="cat_part_no" value="">
    </form>
    
    <script>
    function newItem(contract_id, cat_part_no) {
       document.formNewItem.contract_id.value = contract_id;
       document.formNewItem.cat_part_no.value = cat_part_no;
       document.formNewItem.submit();
    }
    </script>
    
    <script>
    function updateContract(item_id,contract_id,inv_group,shipto_company,shipto_loc,priority,supplier,carrier,buyer,sourcer,terms,fob,supp_part,supp_contact,shelf_life,crit_carrier,client_part,client_po,round_to,multiple,consign,lead,origin,transit,comments,pricing,supply_path,status,fazer) {
      document.UpdateLineForm.addItemId.value = item_id;
      document.UpdateLineForm.addContractId.value = contract_id;
      document.UpdateLineForm.addInvGrp.value = inv_group;
      document.UpdateLineForm.addShiptoCompId.value = shipto_company;
      document.UpdateLineForm.addShiptoLocId.value = shipto_loc;
      document.UpdateLineForm.addPriority.value = priority;
      document.UpdateLineForm.addSupplier.value = supplier;
      document.UpdateLineForm.addCarrier.value = carrier;
      document.UpdateLineForm.addBuyer.value = buyer;
      document.UpdateLineForm.addSourcer.value = sourcer;
      document.UpdateLineForm.addTerms.value = terms;
      document.UpdateLineForm.addFOB.value = fob;
      document.UpdateLineForm.addSupplierPart.value = supp_part;
      document.UpdateLineForm.addSupplierContact.value = supp_contact;
      document.UpdateLineForm.addShelfLife.value = shelf_life;
      document.UpdateLineForm.addCritCarrier.value = crit_carrier;
      document.UpdateLineForm.addClientPart.value = client_part;
      document.UpdateLineForm.addClientPO.value = client_po;
      document.UpdateLineForm.addRoundTo.value = round_to;
      document.UpdateLineForm.addMultiple.value = multiple;
      document.UpdateLineForm.addConsign.value = consign;
      document.UpdateLineForm.addLeadTime.value = lead;
      document.UpdateLineForm.addShipOrigin.value = origin;
      document.UpdateLineForm.addTransitTime.value = transit;      
      document.UpdateLineForm.addContractComment.value = comments;      
      document.UpdateLineForm.addPriceType.value = pricing;      
      document.UpdateLineForm.addSupplyPath.value = supply_path;      
      document.UpdateLineForm.addStatus.value = status;
      document.UpdateLineForm.prevPriority.value = priority;
      var x = fazer;
      
      document.UpdateLineForm.action.value = 'Update';
      document.UpdateLineForm.addType.value = 'UPDATE';
      expand('AddLineBlock');
      window.scroll(1,2000);
      // document.UpdateLineForm.submit();            
    }
    
    function newContract() {
      document.UpdateLineForm.addItemId.value = '';
      document.UpdateLineForm.addContractId.value = '';
      document.UpdateLineForm.addInvGrp.value = '';
      document.UpdateLineForm.addShiptoCompId.value = '';
      document.UpdateLineForm.addShiptoLocId.value = '';
      document.UpdateLineForm.addPriority.value = '';
      document.UpdateLineForm.addSupplier.value = '';
      document.UpdateLineForm.addCarrier.value = '';
      document.UpdateLineForm.addBuyer.value = '';
      document.UpdateLineForm.addSourcer.value = '';
      document.UpdateLineForm.addTerms.value = '';
      document.UpdateLineForm.addFOB.value = '';
      document.UpdateLineForm.addSupplierPart.value = '';
      document.UpdateLineForm.addSupplierContact.value = '';
      document.UpdateLineForm.addShelfLife.value = '';
      document.UpdateLineForm.addCritCarrier.value = '';
      document.UpdateLineForm.addClientPart.value = '';
      document.UpdateLineForm.addClientPO.value = '';
      document.UpdateLineForm.addRoundTo.value = '';
      document.UpdateLineForm.addMultiple.value = '';
      document.UpdateLineForm.addConsign.value = '';
      document.UpdateLineForm.addLeadTime.value = '';
      document.UpdateLineForm.addShipOrigin.value = '';
      document.UpdateLineForm.addTransitTime.value = '';      
      document.UpdateLineForm.addContractComment.value = '';      
      document.UpdateLineForm.addPriceType.value = '';      
      document.UpdateLineForm.addSupplyPath.value = '';
      document.UpdateLineForm.addStatus.value= '';
      
      document.UpdateLineForm.action.value = 'Add';
      document.UpdateLineForm.addType.value = 'NEW';
    }
    
    function chooseInventoryGroup() {
      document.ChooseInvGroupForm.submit();
    }
    
    function chooseCarrier() {
        // check to make sure inventory group is filled out
      if (document.UpdateLineForm.addInvGrp.value == '') {
         alert('please select an inventory group first.');
      } else {
         document.ChooseCarrierForm.invgrp.value = document.UpdateLineForm.addInvGrp.value;
         document.ChooseCarrierForm.submit();
      }
    }
    
    </script>
    
    <TABLE  BORDER="0" BGCOLOR="#a2a2a2" CELLSPACING=1 CELLPADDING=2 WIDTH="100%" CLASS="moveup">
      <TR align="center">
        <td class="thheading" onmouseover='className="thheading3"' onmouseout='className="thheading"' onclick="sortView('item_id')">Item ID</td>
        <td class="thheading" onmouseover='className="thheading3"' onmouseout='className="thheading"' onclick="sortView('inventory_group')">Inventory Group</td>
        <td class="thheading" onmouseover='className="thheading3"' onmouseout='className="thheading"' onclick="sortView('ship_to_company_id')">ShipTo Company ID</td>
        <td class="thheading" onmouseover='className="thheading3"' onmouseout='className="thheading"' onclick="sortView('ship_to_location_id')">ShipTo Location ID</td>
        <td class="thheading" onmouseover='className="thheading3"' onmouseout='className="thheading"' onclick="sortView('priority')">Priority</td>
        <td class="thheading">Supplier</td>
        <td class="thheading">Carrier</td>
        <td class="thheading">Buyer</td>
        <td class="thheading">Sourcer</td>
        <td class="thheading">Payment Terms</td>
        <td class="thheading">Freight On Board</td>
        <td class="thheading">Supplier Part No.</td>
        <td class="thheading">Supplier Contact ID</td>
        <td class="thheading">Remaining Shelf Life %</td>
        <td class="thheading">Critical Order Carrier</td>
        <td class="thheading">Ref Client Part No.</td>
        <td class="thheading">Ref Client PO No.</td>
        <td class="thheading">Round To Multiple</td>
        <td class="thheading">Multiple Of</td>
        <td class="thheading">Consignment</td>
        <td class="thheading">Lead Time Days</td>
        <td class="thheading">Default Shipment Origin State</td>
        <td class="thheading">Transit Time In Days</td>
        <td class="thheading">Contract Reference Comments</td>
        <td class="thheading">Pricing Type</td>
        <td class="thheading">Supply Path</td>
        <td class="thheading">Status</td>
        <td class="thheading">Update</td>
        <td class="thheading">Pricing</td>
        <td class="thheading">New</td>
      </TR>    

      <form name='formUpdateContract' action='updatecontract.do'>
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
        <TD CLASS='<c:out value="${colorClass}"/>' HEIGHT="38"><c:out value="${item.supplierPartNo}"/></TD>
        <TD CLASS='<c:out value="${colorClass}"/>' HEIGHT="38"><c:out value="${item.supplierContactId}"/></TD>
        <TD CLASS='<c:out value="${colorClass}"/>' HEIGHT="38"><c:out value="${item.remainingShelfLifePercent}"/></TD>
        <TD CLASS='<c:out value="${colorClass}"/>' HEIGHT="38"><c:out value="${item.criticalOrderCarrier}"/></TD>
        <TD CLASS='<c:out value="${colorClass}"/>' HEIGHT="38"><c:out value="${item.refClientPartNo}"/></TD>
        <TD CLASS='<c:out value="${colorClass}"/>' HEIGHT="38"><c:out value="${item.refClientPoNo}"/></TD>
        <TD CLASS='<c:out value="${colorClass}"/>' HEIGHT="38"><c:out value="${item.roundToMultiple}"/></TD>
        <TD CLASS='<c:out value="${colorClass}"/>' HEIGHT="38"><c:out value="${item.multipleOf}"/></TD>        
        <TD CLASS='<c:out value="${colorClass}"/>' HEIGHT="38"><c:out value="${item.consignment}"/></TD>
        <TD CLASS='<c:out value="${colorClass}"/>' HEIGHT="38"><c:out value="${item.leadTimeDays}"/></TD>
        <TD CLASS='<c:out value="${colorClass}"/>' HEIGHT="38"><c:out value="${item.defaultShipmentOriginState}"/></TD>
        <TD CLASS='<c:out value="${colorClass}"/>' HEIGHT="38"><c:out value="${item.transitTimeInDays}"/></TD>
        <TD CLASS='<c:out value="${colorClass}"/>' HEIGHT="38"><c:out value="${item.contractReferenceComments}"/></TD>
        <TD CLASS='<c:out value="${colorClass}"/>' HEIGHT="38"><c:out value="${item.pricingType}"/></TD>        
        <TD CLASS='<c:out value="${colorClass}"/>' HEIGHT="38"><c:out value="${item.supplyPath}"/></TD> 
        <TD CLASS='<c:out value="${colorClass}"/>' HEIGHT="38"><c:out value="${item.status}"/></TD>
        <TD CLASS='<c:out value="${colorClass}"/>' HEIGHT="38">
            <input type=button name="Edit" value="Edit" 
                   onclick="updateContract('<c:out value="${item.itemId}"/>',
                                           '<c:out value="${item.dbuyContractId}"/>',
                                           '<c:out value="${item.inventoryGroup}"/>',
                                           '<c:out value="${item.shipToCompanyId}"/>',
                                           '<c:out value="${item.shipToLocationId}"/>',
                                           '<c:out value="${item.priority}"/>',
                                           '<c:out value="${item.supplier}"/>',
                                           '<c:out value="${item.carrier}"/>',
                                           '<c:out value="${item.buyer}"/>',
                                           '<c:out value="${item.sourcer}"/>',
                                           '<c:out value="${item.paymentTerms}"/>',
                                           '<c:out value="${item.freightOnBoard}"/>',
                                           '<c:out value="${item.supplierPartNo}"/>',
                                           '<c:out value="${item.supplierContactId}"/>',
                                           '<c:out value="${item.remainingShelfLifePercent}"/>',
                                           '<c:out value="${item.criticalOrderCarrier}"/>',
                                           '<c:out value="${item.refClientPartNo}"/>',
                                           '<c:out value="${item.refClientPoNo}"/>',
                                           '<c:out value="${item.roundToMultiple}"/>',
                                           '<c:out value="${item.multipleOf}"/>',
                                           '<c:out value="${item.consignment}"/>',
                                           '<c:out value="${item.leadTimeDays}"/>',
                                           '<c:out value="${item.defaultShipmentOriginState}"/>',
                                           '<c:out value="${item.transitTimeInDays}"/>',
                                           '<c:out value="${item.contractReferenceComments}"/>',
                                           '<c:out value="${item.pricingType}"/>',
                                           '<c:out value="${item.supplyPath}"/>',
                                           '<c:out value="${item.status}"/>');">
        </TD>
        <TD CLASS='<c:out value="${colorClass}"/>' HEIGHT="38">
           <input type='button' name='Pricing' value='Pricing' onclick="updatePricing('<c:out value="${item.dbuyContractId}"/>','<c:out value="${item.itemId}"/>');">
        </TD>
        <TD CLASS='<c:out value="${colorClass}"/>' HEIGHT="38">
           <input type='button' name='New' value='New' onclick="newItem('<c:out value="${item.dbuyContractId}"/>','<c:out value="${item.itemId}"/>');">
        </TD>
      </TR>
      </c:forEach>   
      </form>
   </TABLE>
    
  </DIV>

  <br>
  <table>
  <tr>
    <td>&nbsp;</td>
  </tr>
  <tr>
     <td>
     <span id="newlinespan" onclick="expand('AddLineBlock');" style="texareaannounce"><img src="/images/plus.jpg" name="LineAdd" id="1">&nbsp;Update line</span>
     <div id="AddLineBlock" style="display:none">
     <!-- input type=button name="new" value="New" onclick='newContract();'-->
     <table>
       <tr>
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
        <td class="thheading">Supplier Part No.</td>
        <td class="thheading">Supplier Contact ID</td>
        <td class="thheading">Remaining Shelf Life %</td>
        <td class="thheading">Critical Order Carrier</td>
        <td class="thheading">Ref Client Part No.</td>
        <td class="thheading">Ref Client PO No.</td>
        <td class="thheading">Round To Multiple</td>
        <td class="thheading">Multiple Of</td>
        <td class="thheading">Consignment</td>
        <td class="thheading">Lead Time Days</td>
        <td class="thheading">Default Shipment Origin State</td>
        <td class="thheading">Transit Time In Days</td>
        <td class="thheading">Contract Reference Comments</td>
        <td class="thheading">Pricing Type</td>
        <td class="thheading">Supply Path</td>
        <td class="thheading">Status</td>
        <!-- td class="thheading">&nbsp;</td -->
       </tr>
  <form name="UpdateLineForm" action="updateline.do" method="POST">
       <tr>
         <td colspan='26'>Update</td>
       </tr>
       <tr>
         <td class="blue"><input type='text' size='10' name='addItemId' style='HEADER' value='' READONLY></td>         
         <td class="blue"><input type="text" size="10" name="addInvGrp" style="HEADER" value="" READONLY></td>     
         <td class="blue"><input type="text" size="10" name="addShiptoCompId" style="HEADER" value="" READONLY></td>
         <td class="blue"><input type="text" size="10" name="addShiptoLocId" style="HEADER" value=""></td>
         <td class="blue"><input type="text" size="10" name="addPriority" style="HEADER" value=""></td>     
         <td class="blue"><input type="text" size="10" name="addSupplier" style="HEADER" value=""></td>     
         <td class="blue"><input type="text" size="10" name="addCarrier" style="HEADER" value=""></td>     
         <td class="blue"><input type="text" size="10" name="addBuyer" style="HEADER" value=""></td>     
         <td class="blue"><input type="text" size="10" name="addSourcer" style="HEADER" value=""></td>     
         <td class="blue"><input type="text" size="10" name="addTerms" style="HEADER" value=""></td>     
         <td class="blue"><input type="text" size="10" name="addFOB" style="HEADER" value=""></td>     
         <td class="blue"><input type="text" size="10" name="addSupplierPart" style="HEADER" value=""></td>     
         <td class="blue"><input type="text" size="10" name="addSupplierContact" style="HEADER" value=""></td>     
         <td class="blue"><input type="text" size="10" name="addShelfLife" style="HEADER" value=""></td>     
         <td class="blue"><input type="text" size="10" name="addCritCarrier" style="HEADER" value=""></td>     
         <td class="blue"><input type="text" size="10" name="addClientPart" style="HEADER" value=""></td>     
         <td class="blue"><input type="text" size="10" name="addClientPO" style="HEADER" value=""></td>     
         <td class="blue"><input type="text" size="10" name="addRoundTo" style="HEADER" value=""></td>     
         <td class="blue"><input type="text" size="10" name="addMultiple" style="HEADER" value=""></td>     
         <td class="blue"><input type="text" size="10" name="addConsign" style="HEADER" value=""></td>     
         <td class="blue"><input type="text" size="10" name="addLeadTime" style="HEADER" value=""></td>     
         <td class="blue"><input type="text" size="10" name="addShipOrigin" style="HEADER" value=""></td>     
         <td class="blue"><input type="text" size="10" name="addTransitTime" style="HEADER" value=""></td>     
         <td class="blue"><input type="text" size="10" name="addContractComment" style="HEADER" value=""></td>         
         <td class="blue"><input type="text" size="10" name="addPriceType" style="HEADER" value=""></td>     
         <td class="blue"><input type="text" size="10" name="addSupplyPath" style="HEADER" value=""></td>  
         <td class="blue"><input type="text" size="10" name="addStatus" style="HEADER" value=""></td>   
         <!-- td class="blue"><input type="button" name="action" value="Add" class="SUBMIT" onclick='addChargeLine();'></td -->
         <input type=hidden name="addContractId" value="">
         <input type=hidden name="addType" value="EDIT">
         <input type=hidden name="prevPriority" value="">
       </tr>
       <tr>
         <td>
           <input type=submit value='Submit Changes'>
         </td>
       </tr>
   </form>
     </table>
     </div>
     </td>
  </tr>
  </table>

</body>
</html:html>
