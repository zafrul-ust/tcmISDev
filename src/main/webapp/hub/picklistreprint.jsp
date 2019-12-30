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

<!-- This handles the menu style and what happens to the right click on the whole page -->
<script type="text/javascript" src="/js/menu/milonic_src.js"></script>
<script type="text/javascript" src="/js/menu/mmenudom.js"></script>
<script type="text/javascript" src="/js/menu/mainmenudata.js"></script>
<script type="text/javascript" src="/js/menu/contextmenu.js"></script>
<!-- This handles which key press events are disabeled on this page -->
<script src="/js/common/disableKeys.js" language="JavaScript"></script>

<script src="/js/common/formchek.js" language="JavaScript"></script>
<script src="/js/common/commonutil.js" language="JavaScript"></script>

<%-- Add any other javascript you need for the page here --%>
<script src="/js/hub/picklistpicking.js" language="JavaScript"></script>

<title>
<fmt:message key="picklistreprint.title"/>
</title>

<script language="JavaScript" type="text/javascript">
//add all the javascript messages here, this for internationalization of client side javascript messages
var messagesData = new Array();
messagesData={     alert:"<fmt:message key="label.alert"/>",
                     and:"<fmt:message key="label.and"/>",
          submitOnlyOnce:"<fmt:message key="label.submitOnlyOnce"/>",
          daySpanInteger:"<fmt:message key="error.dayspan.integer"/>",
           itemMrInteger:"<fmt:message key="error.itemmr.integer"/>"};

</script>
</head>

<body bgcolor="#ffffff">


<tcmis:form action="/picklistreprint.do" onsubmit="return submitOnlyOnce();">

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
<fmt:message key="picklistreprint.title"/>
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
    <table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableSearch">
      <tr>
        <td width="10%" class="optionTitleBoldRight"><fmt:message key="label.picklistno"/>:</td>
        <td width="10%" class="optionTitleBoldLeft"><c:out value="${picklistId}"/></td>
        <td colspan=4>&nbsp;</td>
      </tr>
 <tcmis:inventoryGroupPermission indicator="false" userGroupId="CusReturns" facilityId="${status.current.hubName}">            
      <tr>
        <td width="10%" class="optionTitleBoldRight">
           <input name="returnPicking" id="returnPicking" type="submit" class="inputBtns" value='<fmt:message key="picklistreprint.returntopicking"/>' onmouseover="this.className='inputBtns inputBtnsOver'" onmouseout="this.className='inputBtns'">
        </td>
        <td width="10%" class="optionTitleBoldRight">
           <input name="printBOLShort" id="printBOLShort" type="button" class="inputBtns" value='<fmt:message key="picklistreprint.printbolshort"/>' onmouseover="this.className='inputBtns inputBtnsOver'" onmouseout="this.className='inputBtns'" onclick="doPrintBolShort();">
        </td>
        <td width="10%" class="optionTitleBoldRight">
           <input name="printBOXLabels" id="printBOXLabels" type="button" class="inputBtns" value='<fmt:message key="picklistreprint.printboxlabels"/>' onmouseover="this.className='inputBtns inputBtnsOver'" onmouseout="this.className='inputBtns'" onclick="doPrintbox();">
        </td>
        <td width="10%" class="optionTitleBoldRight">
           <input name="printCons" id="printCons" type="button" class="inputBtns" value='<fmt:message key="picklistreprint.printcons"/>' onmouseover="this.className='inputBtns inputBtnsOver'" onmouseout="this.className='inputBtns'" onclick="doPrintCons();">
        </td>
        <td width="10%" class="optionTitleBoldRight">
           <input name="printBOLLong" id="printBOLLong" type="button" class="inputBtns" value='<fmt:message key="picklistreprint.printbollong"/>' onmouseover="this.className='inputBtns inputBtnsOver'" onmouseout="this.className='inputBtns'" onclick="doPrintBolLong();">
        </td>
        <td width="10%" class="optionTitleBoldRight">
           <input name="reprintPicklist" id="reprintPicklist" type="button" class="inputBtns" value='<fmt:message key="picklistreprint.reprintpicklist"/>' onmouseover="this.className='inputBtns inputBtnsOver'" onmouseout="this.className='inputBtns'" onclick="return validateForm();">
        </td>      
      </tr>      
 </tcmis:inventoryGroupPermission>      
    </table>
    <input type="hidden" name='picklistId' id='picklistId' value='<c:out value="${picklistId}"/>'>
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

<c:if test="${picklistPrintColl != null}" >
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
      <a href="#" onclick="showpickingpagelegend(); return false;"><fmt:message key="label.showlegend"/></a> 
    </div>
    <div class="dataContent">
    <table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableResults">
    <c:set var="colorClass" value=''/>
    <c:set var="legendColorClass" value=''/>
    <c:set var="dataCount" value='${0}'/>

    <c:forEach var="pickBean" items="${picklistPrintColl}" varStatus="status">
    <!-- Need to print the header every 10 rows-->
    <c:if test="${status.index % 10 == 0}">
    <tr>
    <th width="5%"><fmt:message key="label.facility"/></th>
    <th width="5%"><fmt:message key="label.workarea"/></th>
    <th width="5%"><fmt:message key="label.deliverypoint"/></th>
    <th width="5%"><fmt:message key="label.shipto"/></th>
    <th width="5%"><fmt:message key="label.requestor"/></th>
    <th width="5%"><fmt:message key="label.mrline"/></th>
    <th width="5%"><fmt:message key="label.partnumber"/></th>
    <th width="4%"><fmt:message key="label.type"/></th>
    <th width="15%"><fmt:message key="label.partdescription"/></th>
    <th width="5%"><fmt:message key="label.packaging"/></th>
    <th width="4%"><fmt:message key="label.quantity"/></th>
    <th width="5%"><fmt:message key="label.needed"/></th>
    <th width="3%"><fmt:message key="label.mr"/> <fmt:message key="label.notes"/></th>
    </tr>
    </c:if>

    <c:choose>
      <c:when test='${status.current.critical == "Y"}'>
       <c:set var="colorClass" value='red'/>
      </c:when>
      <c:when test='${status.current.critical == "S"}'>
       <c:set var="colorClass" value='pink'/>
      </c:when>
      <c:when test="${status.index % 2 == 0}">
       <c:set var="colorClass" value=''/>
      </c:when>
      <c:otherwise>
       <c:set var="colorClass" value="alt"/>
      </c:otherwise>
    </c:choose>       

   <tr class='<c:out value="${colorClass}"/>' align="center">
     <td width="5%"><c:out value="${status.current.facilityId}"/>&nbsp;</td>
     <td width="5%"><c:out value="${status.current.application}"/>&nbsp;</td>
     <td width="5%"><c:out value="${status.current.deliveryPoint}"/>&nbsp;</td>
     <td width="5%"><c:out value="${status.current.shipToLocationId}"/>&nbsp;</td>
     <td width="5%"><c:out value="${status.current.requestor}"/>&nbsp;</td>
     <td width="5%"><c:out value="${status.current.prNumber}"/>-<c:out value="${status.current.lineItem}"/>&nbsp;</td>
     <td width="5%"><c:out value="${status.current.catPartNo}"/>&nbsp;</td>
     <td width="4%"><c:out value="${status.current.deliveryType}"/>&nbsp;</td>
     <td width="15%"><c:out value="${status.current.partDescription}"/>&nbsp;</td>
     <td width="5%"><c:out value="${status.current.packaging}"/>&nbsp;</td>
     <td width="4%"><c:out value="${status.current.pickQty}"/>&nbsp;</td>
     <td width="5%">
        <c:out value="${status.current.needDatePrefix}"/>         
        <fmt:formatDate var="fmtNeedDate" value="${status.current.needDate}" pattern="MM/dd/yyyy"/>
        <c:out value="${fmtNeedDate}"/>&nbsp;        
     </td>
     <td width="3%">        
        <c:if test='${! empty status.current.mrNotes}'>
          <span id='spanMrNote<c:out value="${status.index}"/>' onclick='showNotes("MrNote<c:out value="${status.index}"/>");'>
          <p id='pgphMrNote<c:out value="${status.index}"/>'><i>+</i></p>
          <div id='divMrNote<c:out value="${status.index}"/>' style='display:none' onmouseover='style.cursor="hand"'>
            <c:out value="${status.current.mrNotes}"/>
          </div>
          </span>
        </c:if>
        &nbsp;          
     </td>
   </tr>
   </c:forEach>
   </c:forEach>
   </c:forEach>
   </table>
   <!-- If the collection is empty say no data found -->
   <c:if test="${empty picklistPrintColl}" >
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
