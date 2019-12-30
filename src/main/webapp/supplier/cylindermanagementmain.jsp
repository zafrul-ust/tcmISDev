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
<meta http-equiv="content-type" content="text/html; charset=UTF-8">
<meta http-equiv="expires" content="-1">
<link rel="shortcut icon" href="https://www.tcmis.com/images/buttons/tcmIS.ico"></link>
<%@ include file="/common/locale.jsp" %>
<!--Use this tag to get the correct CSS class.
This looks at what the user's preffered font size and which application he is viewing to set the correct CSS. -->
<tcmis:gridFontSizeCss />
<%-- Add any other stylesheets you need for the page here --%>

<script type="text/javascript" src="/js/common/formchek.js"></script>
<script type="text/javascript" src="/js/common/commonutil.js"></script>
<!-- This handles all the resizing of the page and frames -->
<script type="text/javascript" src="/js/common/grid/iframegridresizemain.js"></script>
<!-- This handels which key press events are disabeled on this page -->
<script type="text/javascript" src="/js/common/disableKeys.js"></script>

<!-- For Calendar support -->
<script type="text/javascript" src="/js/calendar/newcalendar.js"></script>
<script type="text/javascript" src="/js/calendar/AnchorPosition.js"></script>
<script type="text/javascript" src="/js/calendar/PopupWindow.js"></script>

<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxcommon.js"></script>
<script type="text/javascript" src="/dhtmlxLayout/codebase/dhtmlxlayout.js"></script>
<script type="text/javascript" src="/dhtmlxWindows/codebase/dhtmlxwindows.js"></script>

<script type="text/javascript" src="/js/common/standardGridmain.js"></script>
<!-- Add any other javascript you need for the page here -->
<script type="text/javascript" src="/js/supplier/cylindermanagementmain.js"></script>

<title>
<fmt:message key="cylinderManagement"/>
</title>

<script language="JavaScript" type="text/javascript">
<!--
//add all the javascript messages here, this for internationalization of client side javascript messages

var windowCloseOnEsc = true;

var messagesData = new Array();
messagesData={alert:"<fmt:message key="label.alert"/>",and:"<fmt:message key="label.and"/>",
validvalues:"<fmt:message key="label.validvalues"/>",
submitOnlyOnce:"<fmt:message key="label.submitOnlyOnce"/>",
searchInput:"<fmt:message key="label.inputSearchText"/>",
allSuppliers:"<fmt:message key="label.allsuppliers"/>",
allLocations:"<fmt:message key="label.alllocations"/>",
errors:"<fmt:message key="label.errors"/>",
selectSupplier:"<fmt:message key="label.selectasupplier"/>",
itemInteger:"<fmt:message key="error.item.integer"/>"};

<c:set var="userSupplierLocationColl" value='${supplierLocationCollection}'/>
<bean:size id="supplierSize" name="userSupplierLocationColl"/>
var altSupplier = new Array(
   {
        id:'',
        name:'<fmt:message key="label.allsuppliers"/>'
   },
   <c:forEach var="myBean" items="${supplierLocationCollection}" varStatus="status">
   <c:if test="${!status.first}">,</c:if>
    {
        id:'<tcmis:jsReplace value="${myBean.supplier}"/>',
        name:'<tcmis:jsReplace value="${myBean.supplierName}"/>'
    }
</c:forEach>
);

var altLocation = new Array();
<c:forEach var="myBean" items="${supplierLocationCollection}" varStatus="status">
    <c:set var="currentSupplier" value='${myBean.supplier}'/>
    <c:set var="locationListCollection" value='${myBean.locationList}'/>
    <bean:size id="locationSize" name="locationListCollection"/>

    altLocation["<tcmis:jsReplace value="${currentSupplier}" />"] = new Array(
        {
            id: '',
            name: '<fmt:message key="label.alllocations"/>'
        },
    <c:forEach var="locationBean" items="${locationListCollection}" varStatus="status1">
        <c:if test="${!status1.first}">,</c:if>
        {
            id: '<tcmis:jsReplace value="${locationBean.locationId}"/>',
            name:'<tcmis:jsReplace value="${locationBean.locationDesc}"/>'
        }
    </c:forEach>
    );
</c:forEach>

// -->
</script>
</head>

<body bgcolor="#ffffff"  onLoad="loadLayoutWin('','cylinderManagement');setSupplier();" onresize="resizeFrames()" >

<div class="interface" id="mainPage" style="">

<!-- Search Frame Begins -->
<div id="searchFrameDiv">
<tcmis:form action="/cylindermanagementresults.do" onsubmit="return submitSearchOnlyOnce();" target="resultFrame">
<div class="contentArea">

<!-- Search Option Begins -->
<table id="searchMaskTable" width="100%" border="0" cellpadding="0" cellspacing="0">
<tr><td>
<div class="roundcont filterContainer">
 <div class="roundright">
   <div class="roundtop">
     <div class="roundtopright"> <img src="/images/rndBoxes/borderTL_filter.gif" alt="" width="15" height="10" class="corner_filter" style="display: none" /></div>
   </div>
   <div class="roundContent">
    <!-- Insert all the search option within this div -->
    <!-- Search Option Table Start -->
    <table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableSearch">
        <tr>
         <td width="10%" class="optionTitleBoldRight">
            <fmt:message key="label.supplier"/>:
         </td>
         <td width="70%" class="optionTitleLeft">
            <select name="supplier" id="supplier" class="selectBox">
            </select>
         </td>
        </tr>

        <tr>
         <td width="10%" class="optionTitleBoldRight">
            <fmt:message key="label.location"/>:
         </td>
         <td width="70%" class="optionTitleLeft">
            <select name="locationId" id="locationId" class="selectBox">
            </select>
         </td>
        </tr>

        <tr>
            <td width="20%" class="optionTitleBoldRight" nowrap><fmt:message key="label.cylinderstatus" />:</td>
            <td width="70%" class="optionTitleLeft">
                  <select name="cylinderStatus" id="cylinderStatus" class="selectBox" >
                    <option value=""><fmt:message key='label.allstatuses'/></option>
                    <c:forEach var="bean" items="${cylinderStatusColl}" varStatus="cylinderStatus">
                        <option value="<c:out value="${bean.cylinderStatus}"/>"><c:out value="${bean.cylinderStatus}"/></option>
                    </c:forEach>
                </select>
            </td>
        </tr>

        <tr>
         <td width="10%" class="optionTitleBoldRight">
            <fmt:message key="label.search"/>:
         </td>
         <td width="70%" class="optionTitleLeft">
            <html:select property="searchField" styleId="searchField" styleClass="selectBox">
                <html:option value="serial_number" key="label.serialnumber"/>
                <html:option value="manufacturer_id_no||' '||manufacturer_name" key="label.manufacturer"/>
                <html:option value="vendor_part_no" key="label.nsn8120"/>
            </html:select>
            <html:select property="searchMode" styleId="searchMode" styleClass="selectBox">
                <html:option value="contains" key="label.contain"/>
                <html:option value="is" key="label.is"/>
            </html:select>
            <input class="inputBox" type="text" name="searchArgument" id="searchArgument" value="" size="15">
         </td>
        </tr>
        <tr>
            <td width="10%" class="optionTitleLeft" colspan="2">
                <input onClick="submitSearch()" type="button" class="inputBtns" onmouseover="this.className='inputBtns inputBtnsOver'" onmouseout="this.className='inputBtns'" value="<fmt:message key="label.search"/>" name="search" id="search">&nbsp;
                <input type="button" class="inputBtns" onmouseover="this.className='inputBtns inputBtnsOver'" onmouseout="this.className='inputBtns'" value="<fmt:message key="label.createexcelfile"/>" name="createExl" id="createExl" onclick="createExcel()">&nbsp;
                <input onClick="submitAddCylinder()" type="button" class="inputBtns" onmouseover="this.className='inputBtns inputBtnsOver'" onmouseout="this.className='inputBtns'" value="<fmt:message key="label.addcylinder"/>" name="addCylinder" id="addCylinder">
            </td>
        </tr>
    </table>
   <!-- Search Option Table end -->
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
<!-- Build this section only if there is an error message to display.
     For the search section, we show the error messages within its frame
-->
<c:if test="${requestScope['org.apache.struts.action.ERROR'] != null}">
    <div class="spacerY">&nbsp;
<div id="searchErrorMessagesArea" class="errorMessages">
            <html:errors/>
        </div>
    </div>
</c:if>
<!-- Error Messages Ends -->

<!-- Hidden element start -->
<div id="hiddenElements" style="display: none;">
   <input type="hidden" name="supplierName" id="supplierName" value=""/>">
   <input type="hidden" name="locationDesc" id="locationDesc" value=""/>">
   <input type="hidden" name="searchFieldName" id="searchFieldName" value=""/>">
   <input type="hidden" name="searchModeName" id="searchModeName" value=""/>">
   <input type="hidden" name="userAction" id="userAction" value="">
   <input name="startSearchTime" id="startSearchTime" type="hidden" value="" />
</div>
</div>
<!-- Hidden elements end -->
</tcmis:form>
</div>
<!-- close of contentArea -->
</div>
<!-- Search Frame Ends -->



<!-- Result Frame Begins -->
<div id="resultFrameDiv" style="margin: 4px 4px 0px 4px;">
<!-- Transit Page Begins -->
<div id="transitPage" style="display: none;" class="optionTitleBoldCenter">
  <br/><br/><br/><fmt:message key="label.pleasewait"/>
  <br/><br/><br/><img src="/images/rel_interstitial_loading.gif" align="middle"/>
</div>
<!-- Transit Page Ends -->
<div id="resultGridDiv" style="display: none;"> 
<!-- Search results start -->
<!-- If you want your results section to span only 50% set this to 50% and your main table will be 100% -->
<table id="resultsMaskTable" width="100%" border="0" cellpadding="0" cellspacing="0">
<tr> 
    <td>
<div class="roundcont contentContainer">
<div class="roundright">
  <div class="roundtop">
    <div class="roundtopright"> <img src="/images/rndBoxes/borderTL.gif" alt="" width="15" height="15" class="corner" style="display: none" /></div>
  </div>
  <div class="roundContent">
   <div class="boxhead">
	  <div id="mainUpdateLinks" style="display: none">
	    <div id="updateResultLink" style="display: none">
            <%-- <a href="#" onclick="call('submitMainAdd'); return false;"><fmt:message key="label.add"/></a>
            | <a href="#" onclick="call('submitMainUpdate'); return false;"><fmt:message key="label.update"/></a>
            | <a href="#" onclick="call('submitMainPrintBarcode'); return false;"><fmt:message key="label.printbarcode"/></a> --%>
        </div>
      </div> <%-- mainUpdateLinks Ends --%>
   </div> <%-- boxhead Ends --%>

    <div class="dataContent">
     <iframe scrolling="no" id="resultFrame" name="resultFrame" width="100%" height="300" frameborder="0" marginwidth="0" src="/blank.html"></iframe>
   </div>
    
     
   </div>
   <%-- result count and time --%>
   <div id="footer" class="messageBar"></div>
  </div>
  <div class="roundbottom">
    <div class="roundbottomright"> <img src="/images/rndBoxes/borderBL.gif" alt="" width="15" height="15" class="corner" style="display: none" /> </div>
  </div>
</div>
</div>
</td></tr>
</table>
</div>  
</div><!-- Result Frame Ends -->

<!-- You can build your error messages below. Similar divs would have to be built to show any other messages in a yui pop-up.-->
<!-- Error Messages Begins -->
<div id="errorMessagesArea" class="errorMessages" style="display: none;overflow: auto;">

</div>
</body>
</html:html>