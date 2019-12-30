<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
	"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%@ taglib uri="/WEB-INF/struts-nested.tld" prefix="nested" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/tcmis.tld" prefix="tcmis" %>

<html:html lang="true">
<head>
    <meta http-equiv="content-type" content="text/html; charset=UTF-8">
    <meta http-equiv="expires" content="-1">
    <link rel="shortcut icon" href="https://www.tcmis.com/images/buttons/tcmIS.ico"></link>
    <%@ include file="/common/locale.jsp" %>
    <!--Use this tag to get the correct CSS class.
This looks at what the user's preffered font size and which application he is viewing to set the correct CSS. -->
<tcmis:gridFontSizeCss />
<!-- Add any other stylesheets you need for the page here -->

<script type="text/javascript" src="/js/common/formchek.js"></script>
<script type="text/javascript" src="/js/common/commonutil.js"></script>
<%--NEW - grid resize--%>
<script type="text/javascript" src="/js/common/grid/resultiframegridresize.js"></script>
<!-- This handels which key press events are disabeled on this page -->
<script src="/js/common/disableKeys.js"></script>

<!-- This handels the menu style and what happens to the right click on the whole page -->
<script type="text/javascript" src="/js/menu/milonic_src.js"></script>
<script type="text/javascript" src="/js/menu/mmenudom.js"></script>
<script type="text/javascript" src="/js/menu/mainmenudata.js"></script>
<script type="text/javascript" src="/js/menu/contextmenu.js"></script>
<%@ include file="/common/rightclickmenudata.jsp" %>

<!-- For Calendar support 

<script type="text/javascript" src="/js/calendar/newcalendar.js"></script>
<script type="text/javascript" src="/js/calendar/AnchorPosition.js"></script>
<script type="text/javascript" src="/js/calendar/PopupWindow.js"></script>
-->
<!-- Add any other javascript you need for the page here -->
<script type="text/javascript" src="/js/distribution/entitysuppliersearchresults.js"></script>

<!-- These are for the Grid, uncomment if you are going to use the grid -->
<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxcommon.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxgrid.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxgridcell.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_form.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_srnd.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_json.js"></script>
<%--Uncomment below if you are providing header menu to switch columns on/off--%>
<!--<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_hmenu.js"></script>-->
<%--Uncomment the below if your grid has rwospans >1--%>
<!--<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_rowspan.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/rowspan_cell_patch.js"></script>
-->
<%--This has the custom cells we built, hcal - the internationalized calendar which we use
    hlink- this is for any links you want tp provide in the grid, the URL/function to call
    can be attached based on a even (rowselect etc)
--%>
<script type="text/javascript" src="/dhtmlxGrid/codebase/excells/dhtmlxgrid_excell_customized.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxcommon_haas.js"></script>

<title>
<fmt:message key="label.suppliersearch"/>
</title>

    <script LANGUAGE="JavaScript" TYPE="text/javascript">
<!--
    with(milonic=new menuname("suppplierContact")){
         top="offset=2"
         style = contextStyle;
         margin=3         
         aI("text=<fmt:message key="label.suppliercontact"/>;url=javascript:showSupplierContacts();");         
    }
    with(milonic=new menuname("suppplierContactWithModify")){
         top="offset=2"
         style = contextStyle;
         margin=3         
         aI("text=<fmt:message key="label.suppliercontact"/>;url=javascript:showSupplierContacts();");
         aI("text=<fmt:message key="label.modifysupplier"/>;url=javascript:requestModify();");
    }

    with(milonic=new menuname("suppplierContactWithNewOptionActivate")){
         top="offset=2"
         style = contextStyle;
         margin=3         
         aI("text=<fmt:message key="label.suppliercontact"/>;url=javascript:showSupplierContacts();"); 
         aI("text=<fmt:message key="label.requestactivation"/>;url=javascript:requestActivation();");
    }
    drawMenus();
// -->
    </script>

<script language="JavaScript" type="text/javascript">
<!--
//add all the javascript messages here, this for internationalization of client side javascript messages
var messagesData = new Array();
messagesData={alert:"<fmt:message key="label.alert"/>",
and:"<fmt:message key="label.and"/>",
validvalues:"<fmt:message key="label.validvalues"/>",
submitOnlyOnce:"<fmt:message key="label.submitOnlyOnce"/>",
recordFound:"<fmt:message key="label.recordFound"/>",
searchDuration:"<fmt:message key="label.searchDuration"/>",
minutes:"<fmt:message key="label.minutes"/>",
seconds:"<fmt:message key="label.seconds"/>",
itemInteger:"<fmt:message key="error.item.integer"/>",
supplierid:"<fmt:message key="label.supplierid"/>",
level:"<fmt:message key="label.level"/>",
suppliername:"<fmt:message key="label.suppliername"/>",
supplieraddress:"<fmt:message key="label.supplieraddress"/>",
phone:"<fmt:message key="label.phone"/>",
email:"<fmt:message key="label.email"/>",
changecomments:"<fmt:message key="label.changecomments"/>",
fedtaxid:"<fmt:message key="label.fedtaxid"/>",
vatregistration:"<fmt:message key="label.vatregistration"/>",
esupplierid:"<fmt:message key="label.esupplierid"/>",
sapvendorcode:"<fmt:message key="label.sapvendorcode"/>",
accountnumber:"<fmt:message key="label.accountnumber"/>",
newsupplier:"<fmt:message key="label.newsupplier"/>",
status:"<fmt:message key="label.status"/>",
defaultpaymentterms:"<fmt:message key="label.defaultpaymentterms"/>",
paymentterms:"<fmt:message key="label.paymentterms"/>",
comments:"<fmt:message key="label.comments"/>",
ok:"<fmt:message key="label.ok"/>",  
newsupplierrequest:"<fmt:message key="newsupplierrequest.title"/>",
selectedRowMsg:"<fmt:message key="label.selectedsupplier"/>",
paymenttermstatus:"<fmt:message key="label.paymenttermstatus"/>"
};

// -->

</script>
</head>

<body bgcolor="#ffffff" onload="resultOnLoad()">

<tcmis:form action="/entitysuppliersearchresults.do" onsubmit="return submitFrameOnlyOnce();">

<!-- Check if the user has permissions and needs to see the update links,set the variable you use in javascript to true.
     The default value of showUpdateLinks is false. -->
<tcmis:inventoryGroupPermission indicator="true" userGroupId="BuyOrder">
 <script type="text/javascript">
  activateOption = true;
 </script>
</tcmis:inventoryGroupPermission>

<tcmis:facilityPermission indicator="true" userGroupId="newSupplier">
 <script type="text/javascript">
  activateOption = true;
 </script>
</tcmis:facilityPermission>

<tcmis:facilityPermission indicator="true" userGroupId="NewSuppApprover">
 <script type="text/javascript">
  activateOption = true;
 </script>
</tcmis:facilityPermission>


<!-- You can build your error messages below. But we want to trigger the YUI pop-up from the main page.
So this is just used to feed the YUI pop-up in the main page.
Similar divs would have to be built to show any other messages in a pop-up.-->
<!-- Error Messages Begins -->
<div id="errorMessagesAreaBody" style="display:none;">
 <html:errors/>
</div>


<script type="text/javascript">
<!--
/*YAHOO.namespace("example.aqua");
YAHOO.util.Event.addListener(window, "load", init);*/

/*Check if there is any error messages to show and set the variable you use in javascript to true or false.*/
<c:choose>
   <c:when test="${requestScope['org.apache.struts.action.ERROR'] == null}">
    showErrorMessage = false;
   </c:when>
   <c:otherwise>
    showErrorMessage = true;
   </c:otherwise>
</c:choose>
//-->
</script>
<!-- Error Messages Ends -->

<div class="interface" id="resultsPage">
<div class="backGroundContent">

<div id="supplierAddressViewBean" style="width:100%;height:600px;" style="display: none;"></div>
<c:if test="${supplierAddressViewBeanCollection != null}" >
<script type="text/javascript">
<!--
<tcmis:facilityPermission indicator="true" userGroupId="NewSuppApprover">
<c:set var="showUpdateLink" value='Y'/>
<c:set var="showInActiveUpdateLink" value='Y'/>
</tcmis:facilityPermission>

<c:choose>
<c:when test="${ empty displayElementId  &&  empty valueElementId}">     
</c:when>
<c:otherwise>
<c:set var="showUpdateLink" value='N'/> 
</c:otherwise>
</c:choose>    

<c:set var="dataCount" value='${0}'/>
<c:if test="${!empty supplierAddressViewBeanCollection}" >
var jsonMainData = new Array();
var jsonMainData = {
rows:[
<c:forEach var="supplierInfo" items="${supplierAddressViewBeanCollection}" varStatus="status">
<c:if test="${status.index > 0}">,</c:if>
<c:if test="${supplierInfo.status eq 'Inactive'}">
 <c:set var="showInActiveUpdateLink" value='N'/>
</c:if>
<c:if test="${supplierInfo.status eq 'Active'}">
 <c:set var="showInActiveUpdateLink" value='Y'/>
</c:if>

<tcmis:jsReplace var="supplierAddr1" value="${supplierInfo.addressLine1}" processMultiLines="true" />  
<tcmis:jsReplace var="supplierAddr2" value="${supplierInfo.addressLine2}" processMultiLines="true" />  

<tcmis:jsReplace var="supplierName" value="${supplierInfo.supplierName}" processMultiLines="true" />  
<tcmis:jsReplace var="supplierAddrCity" value="${supplierInfo.city}" processMultiLines="true" />  
<tcmis:jsReplace var="supplierAddrState" value="${supplierInfo.stateAbbrev}" processMultiLines="true" />  
<tcmis:jsReplace var="supplierAddrZip" value="${supplierInfo.zip}" processMultiLines="true" />  
<tcmis:jsReplace var="supplierNotes" value="${supplierInfo.supplierNotes}" processMultiLines="true" />
/*The row ID needs to start with 1 per their design.*/ 
{ id:${status.index +1},
	<c:if test="${(supplierInfo.paymentTerms == null) or (supplierInfo.status eq 'Inactive') or (supplierInfo.paymentTermStatus eq 'Inactive') }">'class':"grid_black",</c:if>
 data:['${showInActiveUpdateLink}',
  '<tcmis:jsReplace value="${supplierInfo.supplier}"/>', '<tcmis:jsReplace value="${supplierName}"/>',
  '${supplierAddr1} ${supplierAddr2}  ${supplierAddrCity} ${supplierAddrState}  ${supplierAddrZip} ${supplierInfo.countryAbbrev} ',
  '${status.current.paymentTerms}',
  '${supplierInfo.mainPhone}','<tcmis:jsReplace value="${supplierInfo.email}" />','${supplierNotes}',
  '${supplierInfo.qualificationLevel}','${supplierInfo.status}',  
  <c:if test="${showUpdateLink == 'Y'}"> '',</c:if>
  '${supplierInfo.paymentTerms}','${supplierInfo.paymentTermStatus}',
  '${supplierAddr1}',
  '${supplierAddr2}' ,
  '${supplierAddrCity} ${supplierAddrState}  ${supplierAddrZip} ${supplierInfo.countryAbbrev}'     
 ]}
 <c:set var="dataCount" value='${dataCount+1}'/> 
 </c:forEach>
]};
</c:if>
//--> 
</script><!-- If the collection is empty say no data found -->
   <c:if test="${empty supplierAddressViewBeanCollection}" >

    <table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableNoData" id="resultsPageTable">
     <tr>
      <td width="100%">
       <fmt:message key="main.nodatafound"/>
      </td>
     </tr>
    </table>
   </c:if>

<!-- Search results end -->
</c:if>

<!-- Hidden element start -->
<div id="hiddenElements" style="display: none;">
<input name="totalLines" id="totalLines" value="${dataCount}" type="hidden">
<input name="minHeight" id="minHeight" type="hidden" value="100">
<input name="uAction" id="uAction" type="hidden" value="">
<input name="searchArgument" id="searchArgument" type="hidden" value="${param.searchArgument}">
<input name="activeOnly" id="activeOnly" type="hidden" value="${param.activeOnly}">
<input name="countryAbbrev" id="countryAbbrev" type="hidden" value="${param.countryAbbrev}">
<!-- Store all search criteria in hidden elements, need this to requery the database after updates -->

 </div>
<!-- Hidden elements end -->

</div>
<!-- close of backGroundContent -->
</div>
<!-- close of interface -->
<c:if test="${showUpdateLink == 'Y'}">
    <script type="text/javascript">
        <!--
       	 showUpdateLinks = true;
        
        //-->
    </script>
</c:if>
</tcmis:form>
</body>
</html:html>