<%@ page language="java" %>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ page import="com.tcmis.internal.hub.beans.EdiOrderErrorViewBean,
                 com.tcmis.internal.hub.beans.EdiShiptoMappingViewBean" %>

<%@ taglib uri="/WEB-INF/struts-nested.tld" prefix="nested" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/tcmis.tld" prefix="tcmis" %>
<%--<tcmis:loggedIn indicator="true" forwardPage="/hub/Home.do"/>--%>
<html:html lang="true">
<head>
  <META HTTP-EQUIV="Content-Type" CONTENT="text/html; charset=iso-8859-1">
  <META HTTP-EQUIV="Pragma" CONTENT="no-cache">
  <META HTTP-EQUIV="Expires" CONTENT="-1">
  <LINK REL="SHORTCUT ICON" HREF="https://www.tcmis.com/images/buttons/tcmIS.ico"></LINK>
  <tcmis:fontSizeCss />
  <!-- This handles the menu style and what happens to the right click on the whole page -->
  <script type="text/javascript" src="/js/menu/milonic_src.js"></script>
  <script type="text/javascript" src="/js/menu/mmenudom.js"></script>
  <script type="text/javascript" src="/js/menu/mainmenudata.js"></script>
  <script type="text/javascript" src="/js/menu/contextmenu.js"></script>
  <!-- This handles which key press events are disabeled on this page -->
  <script src="/js/common/disableKeys.js" language="JavaScript"></script>
  <SCRIPT SRC="/js/edisupport.js" LANGUAGE="JavaScript"></SCRIPT>
  <script type="text/javascript" src="/js/common/commonutil.js"></script>
  <title>
    <fmt:message key="ediorderstatus.label.title"/>
  </title>
</head>

<body bgcolor="#ffffff">
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
<fmt:message key="ediorderstatus.label.title"/>
</td>
<td width="30%" class="headingr">
<html:link style="color:#FFFFFF" forward="home">
 <fmt:message key="label.home"/>
</html:link>
</td>
</tr>
</table>
</div>

    <logic:present name="personnelBean">

    <c:set var="editShipTo" value="${false}"/>
    <tcmis:facilityPermission indicator="true" userGroupId="EditShipTo" facilityId="${CompanyIdBean}">
      <c:set var="editShipTo" value="${true}"/>
    </tcmis:facilityPermission>

    <c:set var="editQuantity" value="${false}"/>
    <tcmis:facilityPermission indicator="true" userGroupId="EditQuantity" facilityId="${CompanyIdBean}">
      <c:set var="editQuantity" value="${true}"/>
    </tcmis:facilityPermission>

    <c:set var="editPrice" value="${false}"/>
    <tcmis:facilityPermission indicator="true" userGroupId="EditPrice" facilityId="${CompanyIdBean}">
      <c:set var="editPrice" value="${true}"/>
    </tcmis:facilityPermission>

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
    <TABLE BORDER="0" CELLSPACING="0" CELLPADDING="3" WIDTH="60%" CLASS="moveup">
    <TR VALIGN="LEFT">
    <form name="companyForm" action="edistatus.do" onSubmit="return SubmitOnlyOnce();">
      <TD HEIGHT="35" ALIGN="RIGHT" CLASS="announce">
        <B><fmt:message key="label.company"/>:</B>&nbsp;
      </TD>
      <TD HEIGHT="35" ALIGN="LEFT" CLASS="announce">
        <select name="edicompany">
          <option value="BA" <logic:equal name="CompanyIdBean" value="BA">SELECTED</logic:equal>>BA</option>  
          <option value="FEC" <logic:equal name="CompanyIdBean" value="FEC">SELECTED</logic:equal>>FEC</option>
          <option value="IAI" <logic:equal name="CompanyIdBean" value="IAI">SELECTED</logic:equal>>IAI</option>
          <option value="SWA" <logic:equal name="CompanyIdBean" value="SWA">SELECTED</logic:equal>>SWA</option>
          <option value="RAYTHEON" <logic:equal name="CompanyIdBean" value="RAYTHEON">SELECTED</logic:equal>>RAYTHEON</option>
          <option value="MILLER" <logic:equal name="CompanyIdBean" value="MILLER">SELECTED</logic:equal>>MILLER</option>
          <option value="USGOV" <logic:equal name="CompanyIdBean" value="USGOV">SELECTED</logic:equal>>USGOV</option>
          <option value="KILFROST" <logic:equal name="CompanyIdBean" value="KILFROST">SELECTED</logic:equal>>KILFROST</option>
          <option value="DETROIT_DIESEL" <logic:equal name="CompanyIdBean" value="DETROIT_DIESEL">SELECTED</logic:equal>>DETROIT DIESEL</option>
          <option value="UTC" <logic:equal name="CompanyIdBean" value="UTC">SELECTED</logic:equal>>UTC</option>
          <option value="MERCK" <logic:equal name="CompanyIdBean" value="MERCK">SELECTED</logic:equal>>MERCK</option>
        </select>
      </TD>
      <TD CLASS="announce" HEIGHT="35" WIDTH="15%" ALIGN="LEFT">
        <html:submit property="submitSearch" styleClass="SUBMIT" onmouseover="className='SUBMITHOVER'" onmouseout="className='SUBMIT'">
          <fmt:message key="button.find"/>
        </html:submit>
      </TD>
    </form>

    <form name="searchForm" action="edisearch.do" onSubmit="return SubmitOnlyOnce();">
      <TD HEIGHT="35" ALIGN="RIGHT" CLASS="announce">
        <select name="searchfield">
          <option value="customerPoNo" <logic:equal name="SearchFieldBean" value="customerPoNo">SELECTED</logic:equal>>Customer PO</option>
          <option value="catPartNoOnOrder" <logic:equal name="SearchFieldBean" value="catPartNoOnOrder">SELECTED</logic:equal>>Part Number</option>
          <option value="manufacturerPartNum" <logic:equal name="SearchFieldBean" value="manufacturerPartNum">SELECTED</logic:equal>>Mfr. Part Num</option>
          <option value="errorDetail" <logic:equal name="SearchFieldBean" value="errorDetail">SELECTED</logic:equal>>Error</option>
          <option value="shiptoPartyId" <logic:equal name="SearchFieldBean" value="shiptoPartyId">SELECTED</logic:equal>>Ship To</option>
          <option value="facilityId" <logic:equal name="SearchFieldBean" value="facilityId">SELECTED</logic:equal>>Facility ID</option>
        </select>
      </TD>
      <TD HEIGHT="35" ALIGN="LEFT">
        <select name="operator">
          <option value="CONTAINS" <logic:equal name="SearchOperatorBean" value="CONTAINS">SELECTED</logic:equal>>CONTAINS</option>
          <option value="IS" <logic:equal name="SearchOperatorBean" value="IS">SELECTED</logic:equal>>IS</option>
          <option value="IS NOT" <logic:equal name="SearchOperatorBean" value="IS NOT">SELECTED</logic:equal>>IS NOT</option>
        </select>
      </TD>
      <TD HEIGHT="35" ALIGN="LEFT">
        <INPUT TYPE="text" NAME="searchvalue" class="inputBox" value='<logic:present name="SearchValueBean"><bean:write name="SearchValueBean"/></logic:present>'>
        <input type="hidden" name="companyid" value='<bean:write name="CompanyIdBean"/>'>
      </TD>
      <TD CLASS="announce" HEIGHT="35" ALIGN="LEFT">
        <html:submit property="submitSearch" styleClass="SUBMIT" onmouseover="className='SUBMITHOVER'" onmouseout="className='SUBMIT'">
          <fmt:message key="label.search"/>
        </html:submit>
      </TD>
    </form>
    </tr>
    </TABLE>
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

    <logic:present name="errorviewBeanCollection">
      <bean:size id="errorbeansSize" name="errorviewBeanCollection"/>
      <script>
        // for use with the check-all javascript code
        var TOTAL_ROWS=<c:out value="${errorbeansSize}"/>;
      </script>

      <form name="formIgnoreLine" action="ignoreedi.do" onSubmit="return SubmitOnlyOnce();">
      <input type="hidden" name="company_id" value="">
      <input type="hidden" name="customer_po_no" value="">
      <input type="hidden" name="customer_po_line_no" value="">
      <input type="hidden" name="load_id" value="">
      <input type="hidden" name="load_line" value="">
      </form>

      <form name="formSortData" action="sortview.do" onSubmit="return SubmitOnlyOnce();">
      <input type="hidden" name="sortfield" value="">
      </form>

  <script>
   var altShipToList = new Array(
   <c:forEach var="stlBean" items="${ShipToListBean}" varStatus="status">
<c:if test="${status.index!=0}">,</c:if>{id:'<c:out value="${stlBean.shiptoPartyId}"/>',firstletter:'<c:out value="${stlBean.shiptoFirstLetter}"/>'}
   </c:forEach>
   );
  </script>

<div class="spacerY">&nbsp;</div>

      <form name="ordstat" action="resetstatus.do" method="POST" onSubmit="return SubmitOnlyOnce();">
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
      <span align="right">
        <B><fmt:message key="ediorderstatus.label.rowcount"/>:</B>&nbsp;<c:out value="${errorbeansSize}"/>
        <c:set var="dataCount" value='${errorbeansSize}'/>      
      </span>
      <c:if test="${! empty errorviewBeanCollection}" >
        | <a href="#" onclick="return resetSelections();"><fmt:message key="ediorderstatus.button.reset"/></a> 
      </c:if>
    </div>
    <div class="dataContent">
    <table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableResults">
      <c:set var="rowCount" value='${0}'/>
      <c:forEach items="${errorviewBeanCollection}" var="line">
        <c:set var="rowCount" value='${rowCount+1}'/>
        <c:choose>
          <c:when test="${rowCount % 2 == 0}" >
            <c:set var="colorClass" value=''/>
          </c:when>
          <c:otherwise>
            <c:set var="colorClass" value='alt'/>
          </c:otherwise>
        </c:choose>
        <c:if test="${rowCount % 10 == 1}">
          <TR align="center">
            <th>Reset
              <center><input type="checkbox" name='reset_<c:out value="${rowCount}"/>' onclick="checkall(this,'ordstat','reset_sel_','reset_header');" value="on"></center>
            </th>
            <th onclick="sortView('date_issued')">Order Received</th>
            <th onclick="sortView('customer_po_no')">PO</th>
            <th onclick="sortView('customer_po_line_no_trim')">Line</th>
            <th onclick="sortView('cat_part_no_on_order')">Part Num</th>
            <th onclick="sortView('manufacturer_part_num')">Mfg Part Num</th>
            <c:if test='${CompanyIdBean=="USGOV"}'>
            <th onclick="sortView('facility_id')">Facility</th>
            </c:if>
            <th onclick="sortView('ordered_qty')">Quantity</th>
            <th onclick="sortView('ordered_uom')">UOM</th>
            <th onclick="sortView('unit_price_on_order')">Unit Price</th>
            <th onclick="sortView('currency_id')">Currency</th>
            <th onclick="sortView('shipto_party_id')">Ship To</th>
            <c:if test='${CompanyIdBean=="USGOV"}'>
            <th>Addr Change Request ID</th>
            <th onclick="sortView('address_change_type')">Addr Change Type</th>            
            </c:if>                       
            <th onclick="sortView('requested_delivery_date')">Need Date</th>
            <th onclick="sortView('buyer_name_on_po')">Requestor</th>
            <th onclick="sortView('current_order_status')">Status</th>
            <th onclick="sortView('error_detail')">Error(s)</th>
            <th onclick="sortView('catalog_uos')">TCM UOS</th>
            <th onclick="sortView('catalog_uos')">UOS per EA</th>
            <th onclick="sortView('mr_qty')">MR Qty</th>
            <th onclick="sortView('packaging')">TCM Packaging</th>
            <th onclick="sortView('customer_po_line_note')">Line Note</th>
            <th onclick="sortView('customer_po_note')">PO Note</th>
            <th onclick="sortView('transaction_type')">Order Type</th>
            <th onclick="sortView('company_id')">Company</th>
            <c:if test='${CompanyIdBean == "USGOV"}'>
            <th>Contract Owner</th>
            <th>Ship To DoDAAC</th>
            <th>Ship To Address</th>
            <th>Mark For DoDAAC</th>
            <th>Mark For Address</th>
            </c:if>
          </TR>
        </c:if>
        <TR CLASS='<c:out value="${colorClass}"/>'>
          <TD HEIGHT="38">
            <center><input type='checkbox' name='reset_sel_<c:out value="${rowCount}"/>' value='<c:out value="${rowCount}"/>'></center>
          </TD>
          <TD HEIGHT="38">
            <fmt:formatDate var="fmtIssueDate" value="${line.dateIssued}" pattern="MM/dd/yyyy"/>
            <c:out value="${fmtIssueDate}"/>
          </TD>
          <TD HEIGHT="38" >
             <c:out value="${line.customerPoNo}"/>
             <c:if test='${CompanyIdBean=="USGOV"}'>
                <c:if test='${line.facilityId=="DLA Gases"}'>
                   <c:if test='${line.customerPoLineNoTrim=="1"}'>
                      <br>
                     <c:choose>
                       <c:when test="${personnelBean.personnelId == '19375' || personnelBean.personnelId == '86030' || personnelBean.personnelId == '15583' || personnelBean.personnelId == '15143' || personnelBean.personnelId == '86405' || personnelBean.personnelId == '17654'}">
                        (<a title="<fmt:message key="label.cancelorder"/>" href="#" onclick="cancelOrder('${line.transactionRefNum}');">${line.transactionRefNum}</a>)
                       </c:when>
                       <c:otherwise>
                        (${line.transactionRefNum})
                       </c:otherwise>
                    </c:choose>
                      <%--(<a href='https://www.tcmis.com/tcmIS/haas/dlagasordertrackingresults.do?userAction=cancelOrder&docNumWithSuffix=<c:out value="${line.transactionRefNum}"/>' title='Cancel Order'><c:out value="${line.transactionRefNum}"/></a>)--%>
                   </c:if>
                </c:if>
             </c:if>
          </TD>
          <TD HEIGHT="38"><c:out value="${line.customerPoLineNoTrim}"/></TD>
          <TD HEIGHT="38">
            <c:if test='${editShipTo}'>
              <c:if test='${empty line.multiplePart}'>
                 <c:out value="${line.catPartNoOnOrder}"/>
              </c:if>
              <c:if test='${! empty line.multiplePart}'>
                 <a href='chgpart.do?partnum=<c:out value="${line.catPartNoOrig}"/>&rownum=<c:out value="${rowCount}"/>&qty=<c:out value="${line.orderedQty}"/>&company=<c:out value="${line.companyId}"/>&fac=<c:out value="${line.catalogId}"/>' target='_blank'>
                   <span id='catpartspan_<c:out value="${rowCount}"/>'><c:out value="${line.catPartNoOnOrder}"/></span>
                 </a>
              </c:if>
            </c:if>
            <c:if test='${editShipTo==false}'>
             <c:out value="${line.catPartNoOnOrder}"/>
            </c:if>
            <c:if test='${CompanyIdBean=="USGOV"}'>              
              <c:if test='${line.facilityId=="DLA Gases" && line.customerPoLineNoTrim!="1"}'>                            
              <br>
              <center>
                <a href='https://www.tcmis.com/tcmIS/supplier/vmistockout.do?catPartNo=<c:out value="${line.catPartNoOnOrder}"/>&companyId=<c:out value="${line.companyId}"/>&transactionType=<c:out value="${line.transactionType}"/>&lineSeq=<c:out value="${line.lineSequence}"/>&changeOrderSeq=<c:out value="${line.changeOrderSequence}"/>&customerPO=<c:out value="${line.customerPoNo}"/>&poLine=<c:out value="${line.customerPoLineNoTrim}"/>&orderQty=<c:out value="${line.orderedQty}"/>&unitOfSale=<c:out value="${line.orderedUom}"/>' target='_blank'>(VMI)</a>
              </center>
              </c:if>
            </c:if>
            <input type='hidden' name='catpartno_<c:out value="${rowCount}"/>' value='<c:out value="${line.catPartNoOnOrder}"/>'>
          </TD>
          <TD HEIGHT="38"><c:out value="${line.manufacturerPartNum}"/></TD>
          <c:if test='${CompanyIdBean=="USGOV"}'>
          <TD HEIGHT="38"><c:out value="${line.facilityId}"/></TD>
          </c:if>
          <TD HEIGHT="38">
            <c:if test='${empty line.multiplePart}'>
              <c:if test='${editQuantity}'>
                <input type='text' name='ordered_qty_<c:out value="${rowCount}"/>' value='<c:out value="${line.orderedQty}"/>' size=8 class="radioBtns">
              </c:if>
              <c:if test='${editQuantity==false}'>
                <c:out value="${line.orderedQty}"/>
                <input type='hidden' name='ordered_qty_<c:out value="${rowCount}"/>' value='<c:out value="${line.orderedQty}"/>'>
              </c:if>
            </c:if>
            <c:if test='${! empty line.multiplePart}'>
              <c:out value="${line.orderedQty}"/>
              <input type='hidden' name='ordered_qty_<c:out value="${rowCount}"/>' value='<c:out value="${line.orderedQty}"/>'>
            </c:if>
          </TD>
          <TD HEIGHT="38">
            <c:if test='${empty line.multiplePart}'>
              <c:if test='${editQuantity}'>
                <select name='ordered_uom_<c:out value="${rowCount}"/>'>
                  <option value='<c:out value="${line.orderedUom}"/>' SELECTED><c:out value="${line.orderedUom}"/></option>
                  <c:forEach var="validUOS" items="${validUOSBean}">
                    <option value='<c:out value="${validUOS.unitOfSale}"/>'><c:out value="${validUOS.unitOfSale}"/></option>
                  </c:forEach>
                </select>
              </c:if>
              <c:if test='${editQuantity==false}'>
                  <c:out value="${line.orderedUom}"/>
                  <input type='hidden' name='ordered_uom_<c:out value="${rowCount}"/>' value='<c:out value="${line.orderedUom}"/>'>
              </c:if>
            </c:if>
            <c:if test='${! empty line.multiplePart}'>
              <c:out value="${line.orderedUom}"/>
              <input type='hidden' name='ordered_uom_<c:out value="${rowCount}"/>' value='<c:out value="${line.orderedUom}"/>'>
            </c:if>
          </TD>
          <TD HEIGHT="38">
            <c:choose>
              <c:when test='${editPrice}'>
                <input type='input' name='unit_price_<c:out value="${rowCount}"/>' value='<c:out value="${line.unitPriceOnOrder}"/>' size=10>
              </c:when>
              <c:otherwise>
                 <c:out value="${line.unitPriceOnOrder}"/>
                 <input type="hidden" name='unit_price_<c:out value="${rowCount}"/>' value='<c:out value="${line.unitPriceOrig}"/>'>
              </c:otherwise>
            </c:choose>
          </TD>
          <TD CLASS='<c:out value="${colorClass}"/>' HEIGHT="38">
          <c:choose>
           <c:when test='${editPrice}'>
             <c:choose>
               <c:when test='${line.currencyId=="USD"}'>
                 <c:out value="${line.currencyId}"/>
                 <input type="hidden" name='currency_id_<c:out value="${rowCount}"/>' value='<c:out value="${line.currencyId}"/>'>
               </c:when>
               <c:when test='${line.currencyId=="CNY"}'>
                 <c:out value="${line.currencyId}"/>
                 <input type="hidden" name='currency_id_<c:out value="${rowCount}"/>' value='<c:out value="${line.currencyId}"/>'>
               </c:when>
               <c:otherwise>
                 <select name='currency_id_<c:out value="${rowCount}"/>'>
                    <option value='<c:out value="${line.currencyId}"/>' SELECTED><c:out value="${line.currencyId}"/></option>
                    <tcmis:isCNServer indicator="false"><option value='USD'>USD</option></tcmis:isCNServer>
                    <tcmis:isCNServer><option value='CNY'>CNY</option></tcmis:isCNServer>
                 </select>
               </c:otherwise>
             </c:choose>
           </c:when>
           <c:otherwise>
             <c:out value="${line.currencyId}"/>
             <input type="hidden" name='currency_id_<c:out value="${rowCount}"/>' value='<c:out value="${line.currencyId}"/>'>
           </c:otherwise>
           </c:choose>
          </TD>
          <TD HEIGHT="38">
            <c:if test='${editShipTo && (CompanyIdBean!="USGOV")}'>
              <select name='shipto_party_id_<c:out value="${rowCount}"/>' id='shipto_party_id_<c:out value="${rowCount}"/>' onclick="populateShipToList('<c:out value="${CompanyIdBean}"/>','shipto_party_id_<c:out value="${rowCount}"/>','<c:out value="${line.shiptoFirstLetter}"/>','<c:out value="${line.shiptoPartyId}"/>');">
                <option value='<c:out value="${line.shiptoPartyId}"/>' SELECTED><c:out value="${line.shiptoPartyId}"/></option>
              </select>
            </c:if>
            <c:if test='${editShipTo==false || (CompanyIdBean=="USGOV")}'>
              <c:out value="${line.shiptoPartyId}"/>
              <input type='hidden' name='shipto_party_id_<c:out value="${rowCount}"/>' value='<c:out value="${line.shiptoPartyId}"/>'>
            </c:if>
          </TD>
          <c:if test='${CompanyIdBean=="USGOV"}'>
          <td CLASS='<c:out value="${colorClass}"/>' HEIGHT="38">
              <c:if test='${line.addressChangeAllowed=="Y"}'>
              <a href='javascript:openAddressChangeRequest("${line.addressChangeRequestId}","${line.addressChangeType}")'><c:out value="${line.addressChangeRequestId}"/></a>
              </c:if>
          </td>
          <td CLASS='<c:out value="${colorClass}"/>' HEIGHT="38">
              <c:choose>
                <c:when test='${line.addressChangeType=="dpms" && line.sentToDpms=="N"}'>
                  Verification
                </c:when>
                <c:otherwise>
                  <c:out value="${line.addressChangeType}"/>
                </c:otherwise>
              </c:choose>
          </td>            
          </c:if>                                
          <TD HEIGHT="38">
            <fmt:formatDate var="fmtReqDate" value="${line.requestedDeliveryDate}" pattern="MM/dd/yyyy"/>
            <c:out value="${fmtReqDate}"/>
          </TD>
          <TD HEIGHT="38"><c:out value="${line.buyerNameOnPo}"/></TD>
          <TD HEIGHT="38">
              <c:out value="${line.currentOrderStatus}"/>
              <c:if test='${editPrice && CompanyIdBean=="MILLER" && line.unitPriceOnOrder==".0100"}'>
                <br>
                <input type='button' value='<fmt:message key="button.ignore"/>' class="SUBMIT" onmouseover="className='SUBMITHOVER'" onmouseout="className='SUBMIT'" onClick="ignoreErrorLine('<c:out value="${CompanyIdBean}"/>','<c:out value="${line.customerPoNo}"/>','<c:out value="${line.customerPoLineNo}"/>','<c:out value="${line.loadId}"/>','<c:out value="${line.loadLine}"/>','<fmt:message key="label.ignore.question"/>');" >
              </c:if>
          </TD>
          <TD CLASS='red' HEIGHT="38"><c:out value="${line.errorDetail}"/></TD>
          <TD HEIGHT="38"><c:out value="${line.catalogUos}"/></TD>
          <TD HEIGHT="38"><c:out value="${line.uosPerPackaging}"/></TD>
          <TD HEIGHT="38"><c:out value="${line.mrQty}"/></TD>
          <TD HEIGHT="38"><c:out value="${line.packaging}"/></TD>
          <TD HEIGHT="38">
            <c:if test='${! empty line.customerPoLineNote}'>
              <span id='span_customer_po_line_note_<c:out value="${rowCount}"/>' onclick='showNotes("customer_po_line_note_<c:out value="${rowCount}"/>");'>
              <p id='pgph_customer_po_line_note_<c:out value="${rowCount}"/>'><i>+</i></p>
              <div id='div_customer_po_line_note_<c:out value="${rowCount}"/>' style='display:none' onmouseover='style.cursor="hand"'>
                <c:out value="${line.customerPoLineNote}"/>
              </div>
              </span>
            </c:if>
          </TD>
          <TD HEIGHT="38">
            <c:if test='${! empty line.customerPoNote}'>
              <span id='span_customer_po_note_<c:out value="${rowCount}"/>' onclick='showNotes("customer_po_note_<c:out value="${rowCount}"/>");'>
              <p id='pgph_customer_po_note_<c:out value="${rowCount}"/>'><i>+</i></p>
              <div id='div_customer_po_note_<c:out value="${rowCount}"/>' style='display:none' onmouseover='style.cursor="hand"'>
                <c:out value="${line.customerPoNote}"/>
              </div>
              </span>
            </c:if>
          </TD>
          <TD HEIGHT="38"><c:out value="${line.transactionTypeDisplay}"/></TD>
          <TD HEIGHT="38"><c:out value="${line.companyId}"/></TD>
          <c:if test='${CompanyIdBean == "USGOV"}'>
          <td HEIGHT="38" title="<c:out value="${line.contractOwner}"/>"><c:out value="${line.contractOwner}"/></td>
          <td HEIGHT="38"><c:out value="${line.shipToDodaac}"/></td>
          <td HEIGHT="38"><c:out value="${line.shipToAddress}"/></td>
          <td HEIGHT="38"><c:out value="${line.markForDodaac}"/></td>
          <td HEIGHT="38"><c:out value="${line.markForAddress}"/></td>
          </c:if>
          <input type='hidden' name='customer_po_no_<c:out value="${rowCount}"/>' value='<c:out value="${line.customerPoNo}"/>'>
          <input type='hidden' name='company_id_<c:out value="${rowCount}"/>' value='<c:out value="${line.companyId}"/>'>
          <input type='hidden' name='transaction_type_<c:out value="${rowCount}"/>' value='<c:out value="${line.transactionType}"/>'>
          <input type='hidden' name='line_sequence_<c:out value="${rowCount}"/>' value='<c:out value="${line.lineSequence}"/>'>
          <input type='hidden' name='change_order_sequence_<c:out value="${rowCount}"/>' value='<c:out value="${line.changeOrderSequence}"/>'>
          <input type='hidden' name='customer_po_line_no_<c:out value="${rowCount}"/>' value='<c:out value="${line.customerPoLineNo}"/>'>
          <input type='hidden' name='load_id_<c:out value="${rowCount}"/>' value='<c:out value="${line.loadId}"/>'>
          <input type='hidden' name='load_line_<c:out value="${rowCount}"/>' value='<c:out value="${line.loadLine}"/>'>
          <input type='hidden' name='unit_price_orig_<c:out value="${rowCount}"/>' value='<c:out value="${line.unitPriceOrig}"/>'>
        </TR>
      </c:forEach>
      </table>
   <!-- If the collection is empty say no data found -->
   <c:if test="${empty errorviewBeanCollection}" >
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
</div> <!-- close of content area -->
</td></tr>
</table>
<!-- Search results end -->
</div> 
      </form>
    </logic:present>
    </logic:present>
    <logic:notPresent name="personnelBean">
      You must log in to access this page.
      <href src="/login.do">Login</href>
    </logic:notPresent>
    <br>
  </DIV>
</body>
</html:html>
