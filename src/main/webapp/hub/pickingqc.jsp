<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
	"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html;charset=UTF-8" language="java"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%@ taglib uri="/WEB-INF/struts-nested.tld" prefix="nested" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/tcmis.tld" prefix="tcmis" %>
<html:html lang="true">
<head>
<meta http-equiv="content-type" content="text/html; charset=iso-8859-1">
<meta http-equiv="expires" content="-1">
<link rel="shortcut icon" href="https://www.tcmis.com/images/buttons/tcmIS.ico"></link>

<tcmis:fontSizeCss />
<%-- Add any other stylesheets you need for the page here --%>
<script src="/js/common/formchek.js" language="JavaScript"></script>
<script src="/js/common/commonutil.js" language="JavaScript"></script>

<%-- For Calendar support --%>
<%--
<script src="/js/calendar/newcalendar.js" language="JavaScript"></script>
<script src="/js/calendar/AnchorPosition.js" language="JavaScript"></script>
<script src="/js/calendar/PopupWindow.js" language="JavaScript"></script>
--%>
<!-- This handles the menu style and what happens to the right click on the whole page -->
<script type="text/javascript" src="/js/menu/milonic_src.js"></script>
<script type="text/javascript" src="/js/menu/mmenudom.js"></script>
<script type="text/javascript" src="/js/menu/mainmenudata.js"></script>
<script type="text/javascript" src="/js/menu/contextmenu.js"></script>
<!-- This handles which key press events are disabeled on this page -->
<script src="/js/common/disableKeys.js" language="JavaScript"></script>

<%-- Add any other javascript you need for the page here --%>
<script src="/js/hub/allocationanalysis.js" language="JavaScript"></script>
<script src="/js/hub/pickingqc.js" language="JavaScript"></script>

<script language="JavaScript" type="text/javascript">

var altHubId = new Array(
<c:forEach var="hubInventoryGroupFacOvBean" items="${hubInventoryGroupFacOvBeanCollection}" varStatus="status">
 <c:choose>
   <c:when test="${status.index > 0}">
    ,"<c:out value="${status.current.branchPlant}"/>"
   </c:when>
   <c:otherwise>
    "<c:out value="${status.current.branchPlant}"/>"
   </c:otherwise>
  </c:choose>
</c:forEach>
);

var altInventoryGroup = new Array();
var altInventoryGroupName = new Array();
<c:forEach var="hubInventoryGroupFacOvBean" items="${hubInventoryGroupFacOvBeanCollection}" varStatus="status">
  <c:set var="currentHub" value='${status.current.branchPlant}'/>
  <c:set var="inventoryGroupCollection" value='${status.current.inventoryGroupCollection}'/>

  altInventoryGroup["<c:out value="${currentHub}"/>"] = new Array(""
  <c:forEach var="inventoryGroupBean" items="${inventoryGroupCollection}" varStatus="status1">
        ,"<c:out value="${status1.current.inventoryGroup}"/>"
  </c:forEach>
  );

  altInventoryGroupName["<c:out value="${currentHub}"/>"] = new Array("<fmt:message key="label.all"/>"
  <c:forEach var="inventoryGroupBean" items="${inventoryGroupCollection}" varStatus="status1">
        ,"<c:out value="${status1.current.inventoryGroup}"/>"
  </c:forEach>
  );
</c:forEach>

var altFacilityId = new Array();
var altFacilityName = new Array();
<c:forEach var="hubInventoryGroupFacOvBean" items="${hubInventoryGroupFacOvBeanCollection}" varStatus="status">
  <c:set var="currentHub" value='${status.current.branchPlant}'/>
  <c:set var="inventoryGroupCollection" value='${status.current.inventoryGroupCollection}'/>
  <c:forEach var="inventoryGroupBean" items="${inventoryGroupCollection}" varStatus="status1">
    <c:set var="currentInventoryGroup" value='${status1.current.inventoryGroup}'/>
    <c:set var="facilityBeanCollection" value='${status1.current.facilities}'/>

      altFacilityId["<c:out value="${currentInventoryGroup}"/>"] = new Array(""
      <c:forEach var="facilityBean" items="${facilityBeanCollection}" varStatus="status2">
        ,"<c:out value="${status2.current.facilityId}"/>"
      </c:forEach>
      );

      altFacilityName["<c:out value="${currentInventoryGroup}"/>"] = new Array("<fmt:message key="label.all"/>"
      <c:forEach var="facilityBean" items="${facilityBeanCollection}" varStatus="status2">
        ,"<c:out value="${status2.current.facilityName}" escapeXml="false"/>"
      </c:forEach>
      );

  </c:forEach>
</c:forEach>

</script>

<%-- These are for the Grid, uncomment if you are going to use the grid --%>
<%--<script src="/js/dhtmlxGrid/dhtmlXCommon.js"></script>
<script src="/js/dhtmlxGrid/dhtmlXGridHaas.js"></script>
<script src="/js/dhtmlxGrid/dhtmlXGridCell.js"></script>
<script src="/js/dhtmlxGrid/dhtmlXGrid_excell_mro.js"></script>
--%>

<%-- This is for the YUI, uncomment if you will use this --%>
<%--<script type="text/javascript" src="/yui/build/yahoo/yahoo.js" ></script>
<script type="text/javascript" src="/yui/build/event/event.js" ></script>
<script type="text/javascript" src="/yui/build/dom/dom.js" ></script>
<script type="text/javascript" src="/yui/build/animation/animation.js" ></script>
<script type="text/javascript" src="/yui/build/container/container.js"></script>
<script type="text/javascript" src="/js/common/waitDialog.js"></script>
<script type="text/javascript" src="/yui/build/dragdrop/dragdrop.js" ></script>--%>

<title>
<fmt:message key="pickingqc.title"/>
</title>

<script language="JavaScript" type="text/javascript">

//add all the javascript messages here, this for internationalization of client side javascript messages
var messagesData = new Array();
messagesData={     alert:"<fmt:message key="label.alert"/>",
                     and:"<fmt:message key="label.and"/>",
          submitOnlyOnce:"<fmt:message key="label.submitOnlyOnce"/>",
         mrNumberInteger:"<fmt:message key="error.mrnumber.integer"/>",
       picklistIdInteger:"<fmt:message key="error.picklistid.integer"/>",
          pickQtyInteger:"<fmt:message key="error.quantity.integer"/>",
           difftPickQty1:"<fmt:message key="pickingqc.confirm.diffpickqty1"/>",
           difftPickQty2:"<fmt:message key="pickingqc.confirm.diffpickqty2"/>",
           difftPickQty3:"<fmt:message key="pickingqc.confirm.diffpickqty3"/>",
         picklistConfirm:"<fmt:message key="picklistpicking.confirm.generate"/>",
        reversePickTitle:"<fmt:message key="reversepick.title"/>",
       viewReceiptsTitle:"<fmt:message key="pickingqc.viewaddreceipts"/>"};          

<c:if test="${picklistColl != null}" >
   <bean:size id="picklistSize" name="picklistColl"/>
   var TOTAL_ROWS = <c:out value="${picklistSize}"/>;
</c:if>
<c:if test="${empty picklistColl}">
   var TOTAL_ROWS=0;
</c:if>
</script>
</head>

<body bgcolor="#ffffff">


<tcmis:form action="/pickingqc.do" onsubmit="return submitOnlyOnce();">

 <div id="transitPage" class="optionTitleBoldCenter" style="display: none;text-align:center;">
  <br><br><br><fmt:message key="label.pleasewait"/>
 </div>
 <div class="interface" id="mainPage">

<%-- Top Navigation- we will not display this section after sometime, when we move to the new menu structure --%>
<div class="topNavigation" id="topNavigation">
<table width="100%" border="0" cellpadding="0" cellspacing="0">
<tr valign="top">
<td width="200">
<img src="/images/tcmtitlegif.gif" align="left">
</td>
<td align="right">
<img src="/images/tcmistcmis32.gif" align="right">
</td>
</tr>
</table>

<table width="100%" border="0" cellpadding="0" cellspacing="0">
<tr><td width="70%" class="headingl">
<fmt:message key="pickingqc.title"/>
</td>
<td width="30%" class="headingr">
<html:link style="color:#FFFFFF" forward="home">
 <fmt:message key="label.home"/>
</html:link>
</td>
</tr>
</table>
</div>
<div class="contentArea">
<!-- Search Option Begins -->
<table id="searchMaskTable" width="100%" border="0" cellpadding="0" cellspacing="0" class="tableSearch">
<tr><td>

<div class="roundcont filterContainer">
 <div class="roundright">
   <div class="roundtop">
     <div class="roundtopright"> <img src="/images/rndBoxes/borderTL_filter.gif" alt="" width="15" height="10" class="corner_filter" style="display: none" /></div>
   </div>
   <div class="roundContent">
    <!-- Insert all the search option within this div -->
    <table border="0" cellpadding="0" cellspacing="0" class="tableSearch">
      <tr>
        <td width="10%" class="optionTitleBoldRight"><fmt:message key="label.hub"/>:</td>
        <td width="20%">
         <c:set var="selectedHub" value='${param.hub}'/>
          <select name="hub" id="hub" class="selectBox" onchange="hubChanged();">
          <c:forEach var="hubInventoryGroupFacOvBean" items="${hubInventoryGroupFacOvBeanCollection}" varStatus="status">
            <c:set var="currentHub" value='${status.current.branchPlant}'/>
            <c:choose>
              <c:when test="${empty selectedHub}" >
                <c:set var="selectedHub" value='${status.current.branchPlant}'/>
                <c:set var="inventoryGroupCollection" value='${status.current.inventoryGroupCollection}'/>
              </c:when>
              <c:when test="${currentHub == selectedHub}" >
                <c:set var="inventoryGroupCollection" value='${status.current.inventoryGroupCollection}'/>
              </c:when>
            </c:choose>
            <c:choose>
              <c:when test="${currentHub == selectedHub}">
                <option value='<c:out value="${currentHub}"/>' selected><c:out value="${status.current.hubName}"/></option>
              </c:when>
              <c:otherwise>
                <option value='<c:out value="${currentHub}"/>'><c:out value="${status.current.hubName}"/></option>
              </c:otherwise>
            </c:choose>
          </c:forEach>
          </select></td>
        <td width="10%" class="optionTitleBoldRight"><fmt:message key="label.mr"/>:</td>
        <td width="20%">
          <input name="prNumber" id="prNumber" type="text" class="inputBox" value='<c:out value="${param.prNumber}"/>' size="8">
        </td>
      </tr>
      <tr>
        <td width="10%" class="optionTitleBoldRight"><fmt:message key="label.inventorygroup"/>:</td>
        <td width="20%">
          <c:set var="selectedInventoryGroup" value='${param.inventoryGroup}'/>
          <select name="inventoryGroup" id="inventoryGroup" class="selectBox" onchange="inventoryGroupChanged();">
            <option value=""><fmt:message key="label.all"/></option>
            <c:forEach var="inventoryGroupFacilityObjBean" items="${inventoryGroupCollection}" varStatus="status">
              <c:set var="currentInventoryGroup" value='${status.current.inventoryGroup}'/>
              <c:choose>
                <c:when test="${empty selectedInventoryGroup}" >
                  <c:set var="selectedInventoryGroup" value=''/>
                  <c:set var="facilityBeanCollection" value=''/>
                </c:when>
                <c:when test="${selectedInventoryGroup == currentInventoryGroup}" >
                  <c:set var="facilityBeanCollection" value='${status.current.facilities}'/>
                </c:when>
              </c:choose>
              <c:choose>
                <c:when test="${currentInventoryGroup == selectedInventoryGroup}">
                  <option value='<c:out value="${currentInventoryGroup}"/>' selected><c:out value="${status.current.inventoryGroupName}"/></option>
                </c:when>
                <c:otherwise>
                  <option value='<c:out value="${currentInventoryGroup}"/>'><c:out value="${status.current.inventoryGroupName}"/></option>
                </c:otherwise>
              </c:choose>
            </c:forEach>
          </select>
        </td>
        <td width="10%" class="optionTitleBoldRight"><fmt:message key="label.picklistid"/>:</td>
        <td width="20%">
          <input name="picklistId" id="picklistId" type="text" class="inputBox" value='<c:out value="${param.picklistId}"/>' size="8">
        </td>
      </tr>
      <tr>
        <td width="10%" class="optionTitleBoldRight"><fmt:message key="label.facility"/>:</td>
        <td width="20%">
          <c:set var="selectedFacilityId" value='${param.facilityId}'/>
          <select name="facilityId" id="facilityId" class="selectBox">
            <option value=""><fmt:message key="label.all"/></option>
            <c:forEach var="facilityBean" items="${facilityBeanCollection}" varStatus="status">
              <c:set var="currentFacilityId" value='${status.current.facilityId}'/>
              <c:choose>
                <c:when test="${empty selectedFacilityId}" >
                  <c:set var="selectedFacilityId" value=''/>
                </c:when>
              </c:choose>
              <c:choose>
                <c:when test="${currentFacilityId == selectedFacilityId}">
                  <option value='<c:out value="${currentFacilityId}"/>' selected><c:out value="${status.current.facilityId}"/></option>
                </c:when>
                <c:otherwise>
                  <option value='<c:out value="${currentFacilityId}"/>'><c:out value="${status.current.facilityId}"/></option>
                </c:otherwise>
              </c:choose>
            </c:forEach>
          </select></td>
        <td width="10%">&nbsp;</td>
        <td width="20%">&nbsp;</td>
      </tr>
      <tr>
        <td width="10%" class="optionTitleBoldRight">
           <input name="submitSearch" id="submitSearch" type="submit" class="inputBtns" value='<fmt:message key="label.search"/>' onmouseover="this.className='inputBtns inputBtnsOver'" onmouseout="this.className='inputBtns'" onclick="return validatePickingForm();">
        </td>
        <td width="20%">&nbsp;</td>
        <td width="10%">&nbsp;</td>
        <td width="20%">&nbsp;</td>
      </tr>
    </table>
    <input type=hidden name="confirm" id="confirm" value="">
   <!-- End search options -->
   </div>
   <div class="roundbottom">
     <div class="roundbottomright"> <img src="/images/rndBoxes/borderBL.gif" alt="" width="15" height="15" class="corner" style="display: none" /> </div>
   </div>
 </div>
</div>
</td></tr>
</table>
<!-- Search Option Ends -->

<!-- Error Messages Begins -->
<div class="spacerY">&nbsp;
<div id="errorMessagesArea" class="errorMessages" style="display:none;">
<html:errors/>
</div>
</div>
<!-- Error Messages Ends -->

<c:if test="${picklistColl != null}" >
<!-- Search results start -->
<table id="resultsMaskTable" width="100%" border="0" cellpadding="0" cellspacing="0">
<tr><td>
<div class="roundcont contentContainer">
<div class="roundright">
  <div class="roundtop">
    <div class="roundtopright"> <img src="/images/rndBoxes/borderTL.gif" alt="" width="15" height="15" class="corner" style="display: none" /></div>
  </div>
  <div class="roundContent">
    <div class="boxhead">
      <c:if test="${! empty picklistColl}" >
        <a href="#" onclick="showpickingpagelegend(); return false;"><fmt:message key="label.showlegend"/></a> | 
        <a href="#" onclick="doConfirm(); return false;"><fmt:message key="button.confirm"/></a> | 
        <a href="#" onclick="doPrintBolShort(); return false;"><fmt:message key="picklistreprint.printbolshort"/></a> | 
        <a href="#" onclick="doPrintBolLong(); return false;"><fmt:message key="picklistreprint.printbollong"/></a> | 
        <a href="#" onclick="doPrintbox(); return false;"><fmt:message key="picklistreprint.printboxlabels"/></a> | 
        <a href="#" onclick="doRecevingLables(); return false;"><fmt:message key="pickingqc.receivinglabels"/></a> | 
        <a href="#" onclick="doReprintLables(); return false;"><fmt:message key="pickingqc.reprintlabels"/></a> | 
        <a href="#" onclick="doDeliveryTicket(); return false;"><fmt:message key="pickingqc.deliveryticket"/></a> 
      </c:if>
    </div>
    <div class="dataContent">
    <table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableResults">
    <c:set var="colorClass" value=''/>
    <c:set var="legendColorClass" value=''/>
    <c:set var="dataCount" value='${0}'/>
    <c:forEach var="pickBean" items="${picklistColl}" varStatus="status">
    <c:if test="${status.index % 10 == 0}">
    <tr>
      <th width="2%"><fmt:message key="label.print"/><br><input type="checkbox" name='printTitle<c:out value="${status.index}"/>' onclick="checkall(this,'genericForm','print','printTitle');"></th>
      <th width="5%"><fmt:message key="label.picklistid"/></th>
      <th width="5%"><fmt:message key="label.facility"/></th>
      <th width="5%"><fmt:message key="label.workarea"/></th>
      <th width="5%"><fmt:message key="label.deliverypoint"/></th>
      <th width="5%"><fmt:message key="label.shipto"/></th>
      <th width="5%"><fmt:message key="label.requestor"/></th>
      <th width="5%"><fmt:message key="label.mrline"/></th>
      <th width="2%"><fmt:message key="label.closemr"/></th>
      <th width="3%"><fmt:message key="label.mr"/> <fmt:message key="label.notes"/></th>
      <th width="5%"><fmt:message key="label.partnumber"/></th>
      <th width="4%"><fmt:message key="label.type"/></th>
      <th width="4%"><fmt:message key="label.item"/></th>    
      <th width="9%"><fmt:message key="label.partdescription"/></th>
      <th width="5%"><fmt:message key="label.packaging"/></th>
      <th width="4%"><fmt:message key="label.bin"/></th>
      <th width="4%"><fmt:message key="label.receiptid"/></th>
      <th width="4%"><fmt:message key="label.mfglot"/></th>
      <th width="4%"><fmt:message key="label.expdate"/></th>
      <th width="4%"><fmt:message key="label.mrline"/></th>
      <th width="2%"><fmt:message key="pickingqc.pickqty"/></th>
      <th width="5%"><fmt:message key="pickingqc.qtypicked"/></th>
    </tr>   
    </c:if>
    <c:choose>
      <c:when test="${status.index % 2 == 0}">
        <c:set var="colorClass" value=''/>
      </c:when>
      <c:otherwise>
        <c:set var="colorClass" value="alt"/>
      </c:otherwise>
    </c:choose>              
    <c:if test='${pickBean.critical=="Y" || pickBean.critical=="y"}'>
     <c:set var="colorClass" value='red'/>
    </c:if>
    <c:if test='${pickBean.critical=="S" || pickBean.critical=="s"}'>
     <c:set var="colorClass" value='pink'/>
    </c:if>
    <c:if test='${pickBean.hazmatIdMissing=="MISSING"}'>
     <c:set var="colorClass" value='orange'/>
    </c:if>    
    <tr class='<c:out value="${colorClass}"/>' align="center">   
      <c:set var="firstItemRow" value="${true}"/>
      <td width="2%" rowspan='<c:out value="${pickBean.itemRowspan}"/>'>
        <input name='print<c:out value="${status.index}"/>' id='print<c:out value="${status.index}"/>' value='<c:out value="${status.index}"/>' type="checkbox">
      </td>
      <td width="5%" rowspan='<c:out value="${pickBean.itemRowspan}"/>'><c:out value="${pickBean.picklistId}"/>&nbsp;</td>
      <td width="5%" rowspan='<c:out value="${pickBean.itemRowspan}"/>'><c:out value="${pickBean.facilityId}"/>&nbsp;</td>
      <td width="5%" rowspan='<c:out value="${pickBean.itemRowspan}"/>'><c:out value="${pickBean.application}"/>&nbsp;</td>
      <td width="5%" rowspan='<c:out value="${pickBean.itemRowspan}"/>'><c:out value="${pickBean.deliveryPoint}"/>&nbsp;</td>
      <td width="5%" rowspan='<c:out value="${pickBean.itemRowspan}"/>'><c:out value="${pickBean.shipToLocationId}"/>&nbsp;</td>
      <td width="5%" rowspan='<c:out value="${pickBean.itemRowspan}"/>'><c:out value="${pickBean.requestor}"/>&nbsp;</td>
      <td width="5%" rowspan='<c:out value="${pickBean.itemRowspan}"/>'>
         <a href="javascript:reversePick('<c:out value="${pickBean.prNumber}"/>','<c:out value="${pickBean.lineItem}"/>','<c:out value="${pickBean.picklistId}"/>');"><c:out value="${pickBean.mrLine}"/></a>&nbsp;
      </td>
      <td width="2%" rowspan='<c:out value="${pickBean.itemRowspan}"/>'>
        <c:if test='${pickBean.cabinetReplenishment=="Y" && empty pickBean.qcDate}'>
          <input name='picklistBean[<c:out value="${dataCount}"/>].closeMr' id='picklistBean[<c:out value="${dataCount}"/>].closeMr' value='<c:out value="${dataCount}"/>' type="checkbox">
        </c:if>&nbsp;                    
      </td>
      <td width="3%" rowspan='<c:out value="${pickBean.itemRowspan}"/>'>        
        <c:if test='${! empty pickBean.mrNotes}'>
          <span id='spanMrNote<c:out value="${status.index}"/>' onclick="showNotes('MrNote<c:out value="${status.index}"/>');">
          <p id='pgphMrNote<c:out value="${status.index}"/>'><i>+</i></p>
          <div id='divMrNote<c:out value="${status.index}"/>' style='display:none' onmouseover='style.cursor="hand"'>
            <c:out value="${pickBean.mrNotes}"/>
          </div>
          </span>
        </c:if>&nbsp;          
      </td>
      <td width="5%" rowspan='<c:out value="${pickBean.itemRowspan}"/>'><c:out value="${pickBean.catPartNo}"/>&nbsp;</td>
      <td width="4%" rowspan='<c:out value="${pickBean.itemRowspan}"/>'><c:out value="${pickBean.deliveryType}"/>&nbsp;</td>     
    <c:forEach var="itemBean" items="${pickBean.items}">
     <c:if test='${! firstItemRow}'>
   <tr class='<c:out value="${colorClass}"/>' align="center">
     </c:if>         
      <td width="4%" rowspan='<c:out value="${itemBean.receiptRowspan}"/>'>
         <a href='/tcmis/Hub/dotshipname?item_id=<c:out value="${itemBean.itemId}"/>'><c:out value="${itemBean.itemId}"/></a>&nbsp;
      </td>
      <td width="9%" rowspan='<c:out value="${itemBean.receiptRowspan}"/>'><c:out value="${itemBean.partDescription}"/>&nbsp;</td>          
      <c:set var="firstReceiptRow" value="${true}"/>
    <c:forEach var="receiptBean" items="${itemBean.receipts}">
     <c:if test='${receiptBean.pickable != "Y" && receiptBean.pickable != "y"}'>
       <c:set var="colorClass" value='yellow'/>
     </c:if>
     <c:if test='${! firstReceiptRow}'>
   <tr class='<c:out value="${colorClass}"/>' align="center">
     </c:if>     
      <td width="5%"><c:out value="${receiptBean.packaging}"/>&nbsp;</td>
      <td width="4%"><c:out value="${receiptBean.bin}"/>&nbsp;</td>
      <td width="4%">
        <c:out value="${receiptBean.receiptId}"/>&nbsp;<br>
        <c:choose>
           <c:when test='${receiptBean.receiptDocumentAvailable=="y" || receiptBean.receiptDocumentAvailable=="Y"}'>
             <img src="/images/buttons/document.gif" alt='<fmt:message key="pickingqc.addviewreceipts"/>' onclick="showProjectDocuments('<c:out value="${receiptBean.receiptId}"/>','<c:out value="${pickBean.inventoryGroup}"/>');" onmouseoever="style.cursor='hand'">
           </c:when>
           <c:otherwise>
             <img src="/images/buttons/plus.gif" alt='<fmt:message key="pickingqc.addreceipts"/>' onclick="showProjectDocuments('<c:out value="${receiptBean.receiptId}"/>','<c:out value="${pickBean.inventoryGroup}"/>');" onmouseoever="style.cursor='hand'"> 
           </c:otherwise>
        </c:choose>
      </td>
      <td width="4%"><c:out value="${receiptBean.mfgLot}"/>&nbsp;</td>
      <td width="4%">        
        <fmt:formatDate var="fmtExpireDate" value="${receiptBean.expireDate}" pattern="MM/dd/yyyy"/>
        <c:choose>
          <c:when test='${fmtExpireDate=="01/01/3000"}'>
             <fmt:message key="label.indefinite"/>&nbsp;
          </c:when>
          <c:otherwise>
             <c:out value="${fmtExpireDate}"/>&nbsp;  
          </c:otherwise>
        </c:choose>
      </td>
      <td width="5%"><c:out value="${pickBean.mrLine}"/>&nbsp;</td>
      <td width="2%"><c:out value="${receiptBean.picklistQuantity}"/>&nbsp;</td>
      <td width="5%">
        <c:choose>        
          <c:when test='${(receiptBean.pickable != "Y" && receiptBean.pickable != "y") || (pickBean.hazmatIdMissing=="MISSING") || (! empty pickBean.qcDate) }'>
             <input type="hidden" name='picklistBean[<c:out value="${dataCount}"/>].quantityPicked' id='picklistBean[<c:out value="${dataCount}"/>].quantityPicked' value='<c:out value="${receiptBean.quantityPicked}"/>'>
             <c:out value="${receiptBean.quantityPicked}"/>         
          </c:when>
          <c:otherwise>
            <input type="text" name='picklistBean[<c:out value="${dataCount}"/>].quantityPicked' id='picklistBean[<c:out value="${dataCount}"/>].quantityPicked' value='<c:out value="${receiptBean.quantityPicked}"/>' class="radioBtns" size=3 onchange="pickQtyChg(this,'<c:out value="${receiptBean.picklistQuantity}"/>','<c:out value="${receiptBean.nonintegerReceiving}"/>');">
          </c:otherwise>
        </c:choose>
      </td>          
      <fmt:formatDate var="fmtNeedDate" value="${pickBean.needDate}" pattern="MM/dd/yyyy"/>
      <input type="hidden" name='picklistBean[<c:out value="${dataCount}"/>].picklistId' id='picklistBean[<c:out value="${dataCount}"/>].picklistId' value='<c:out value="${pickBean.picklistId}"/>'>
      <input type="hidden" name='picklistBean[<c:out value="${dataCount}"/>].companyId' id='picklistBean[<c:out value="${dataCount}"/>].companyId' value='<c:out value="${pickBean.companyId}"/>'>
      <input type="hidden" name='picklistBean[<c:out value="${dataCount}"/>].prNumber' id='picklistBean[<c:out value="${dataCount}"/>].prNumber' value='<c:out value="${pickBean.prNumber}"/>'>
      <input type="hidden" name='picklistBean[<c:out value="${dataCount}"/>].lineItem' id='picklistBean[<c:out value="${dataCount}"/>].lineItem' value='<c:out value="${pickBean.lineItem}"/>'>
      <input type="hidden" name='picklistBean[<c:out value="${dataCount}"/>].needDate' id='picklistBean[<c:out value="${dataCount}"/>].needDate' value='<c:out value="${fmtNeedDate}"/>'>
      <input type="hidden" name='picklistBean[<c:out value="${dataCount}"/>].hub' id='picklistBean[<c:out value="${dataCount}"/>].hub' value='<c:out value="${pickBean.hub}"/>'>
      <input type="hidden" name='picklistBean[<c:out value="${dataCount}"/>].receiptId' id='picklistBean[<c:out value="${dataCount}"/>].receiptId' value='<c:out value="${receiptBean.receiptId}"/>'>
      <input type="hidden" name='picklistBean[<c:out value="${dataCount}"/>].cabinetReplenishment' id='picklistBean[<c:out value="${dataCount}"/>].cabinetReplenishment' value='<c:out value="${pickBean.cabinetReplenishment}"/>'>
      <input type="hidden" name='picklistBean[<c:out value="${dataCount}"/>].picklistQuantity' id='picklistBean[<c:out value="${dataCount}"/>].picklistQuantity' value='<c:out value="${receiptBean.picklistQuantity}"/>'>
      <input type="hidden" name='picklistBean[<c:out value="${dataCount}"/>].group' id='picklistBean[<c:out value="${dataCount}"/>].group' value='<c:out value="${pickBean.picklistId}"/>-<c:out value="${pickBean.mrLine}"/>'>
      <c:set var="dataCount" value='${dataCount+1}'/> 
    </tr>
    <c:set var="firstReceiptRow" value="${false}"/>
    </c:forEach>
    <c:set var="firstItemRow" value="${false}"/> 
    </c:forEach>
   </tr>
   </c:forEach>
   </table>
   <script> var PICK_ROWS = <c:out value="${dataCount}"/>; </script>
   <!-- If the collection is empty say no data found -->
   <c:if test="${empty picklistColl}" >
    <table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableNoData">
     <tr>
      <td width="100%">
       <fmt:message key="main.nodatafound"/>
      </td>
     </tr>
    </table>
   </c:if>
  </div>
  </div>
  <div class="roundbottom">
    <div class="roundbottomright"> <img src="/images/rndBoxes/borderBL.gif" alt="" width="15" height="15" class="corner" style="display: none" /> </div>
  </div>
</div>
</div>
</div>
</td></tr>
</table>
<!-- Search results end -->
</c:if>

<!-- Footer message start -->
 <div class="messageBar">&nbsp;</div>
<!-- Footer message end -->

<!-- Hidden element start -->
 <div id="hiddenElements" style="display: none;">
   <input name="action" id="action" type="hidden" value="createExcel">
 </div>
<!-- Hidden elements end -->

</div> <!-- close of content area -->
</div> <!-- close of interface -->
</tcmis:form>
</body>
</html:html>
