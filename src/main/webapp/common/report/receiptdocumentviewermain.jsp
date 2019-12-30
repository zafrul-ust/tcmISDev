<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
	"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<%@ taglib uri="/WEB-INF/struts-nested.tld" prefix="nested" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/tcmis.tld" prefix="tcmis" %>

<html:html lang="true">
<head>
<meta http-equiv="content-type" content="text/html; charset=UTF-8">
<meta http-equiv="expires" content="-1">
<link rel="shortcut icon" href="https://www.tcmis.com/images/buttons/tcmIS.ico"> </link>

<%@ include file="/common/locale.jsp" %>
<!--Use this tag to get the correct CSS class.
This looks at what the user's preffered font size and which application he is viewing to set the correct CSS. -->
<tcmis:gridFontSizeCss />
<%-- Add any other stylesheets you need for the page here --%>

<link rel="stylesheet" type="text/css" href="/css/clientpages.css"></link>
<style type="text/css">
html {
  height: 100%;
  max-height:100%;
  margin-bottom: 1px;
  overflow: hidden;
}
</style>

<script type="text/javascript" src="/js/common/formchek.js"></script>
<script type="text/javascript" src="/js/common/commonutil.js"></script>
<!-- This handles all the resizing of the page and frames -->
<script type="text/javascript" src="/js/common/grid/iframegridresizemain.js"></script>
<!-- This handels which key press events are disabeled on this page -->
<script type="text/javascript" src="/js/common/disableKeys.js"></script>

<!-- This handels the menu style and what happens to the right click on the whole page -->
<script type="text/javascript" src="/js/menu/milonic_src.js"></script>
<script type="text/javascript" src="/js/menu/mmenudom.js"></script>
<script type="text/javascript" src="/js/menu/mainmenudata.js"></script>
<script type="text/javascript" src="/js/menu/contextmenu.js"></script>
<%@ include file="/common/rightclickmenudata.jsp" %>

<!-- For Calendar support -->
<script type="text/javascript" src="/js/calendar/newcalendar.js"></script>
<script type="text/javascript" src="/js/calendar/AnchorPosition.js"></script>
<script type="text/javascript" src="/js/calendar/PopupWindow.js"></script>

<!-- Add any other javascript you need for the page here -->
<script type="text/javascript" src="/js/common/standardGridmain.js"></script>
<script type="text/javascript" src="/js/common/report/receiptdocumentviewermain.js"></script>

<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxcommon.js"></script>
<script type="text/javascript" src="/dhtmlxLayout/codebase/dhtmlxlayout.js"></script>
<script type="text/javascript" src="/dhtmlxWindows/codebase/dhtmlxwindows.js"></script>

    
<title>
<fmt:message key="receiptdocumentviewer.title"/>
</title>
<script language="JavaScript" type="text/javascript">
<!--
//add all the javascript messages here, this for internationalization of client side javascript messages
var messagesData = new Array();
messagesData={alert:"<fmt:message key="label.alert"/>",
and:"<fmt:message key="label.and"/>",all:"<fmt:message key="label.all"/>",
validvalues:"<fmt:message key="label.validvalues"/>",
missingSearchText:"<fmt:message key="receiptdocumentviewer.searchmessage"/>",
submitOnlyOnce:"<fmt:message key="label.submitOnlyOnce"/>", 
itemInteger:"<fmt:message key="error.item.integer"/>",
errors:"<fmt:message key="label.errors"/>",
pleasewait:"<fmt:message key="label.pleasewait"/>",
pleaseselect:"<fmt:message key="label.pleaseselect"/>",
accountsysteminputdialog:"<fmt:message key="label.catalogaccountsysIdInput"/>",
myFacilities:"<fmt:message key="label.myfacilities"/>",
myWorkAreas:"<fmt:message key="label.myworkareas"/>",
poLine:"<fmt:message key="label.poline"/>"
};

var altFacility = new Array(
    <c:forEach var="myWorkareaFacilityViewBean" items="${myWorkareaFacilityViewBeanCollection}" varStatus="status">
        <c:if test="${!status.first}">,</c:if>
        {
            value:'<tcmis:jsReplace value="${myWorkareaFacilityViewBean.facilityId}"/>',
            text:'<tcmis:jsReplace value="${myWorkareaFacilityViewBean.facilityName}"/>'
        }
    </c:forEach>
);

var altApplication = new Array();
<c:forEach var="myWorkareaFacilityViewBean" items="${myWorkareaFacilityViewBeanCollection}" varStatus="status">
    <c:set var="applicationCount" value='${0}'/>
    altApplication['<tcmis:jsReplace value="${status.current.facilityId}"/>'] = new Array(
        <c:forEach items="${status.current.applicationBeanCollection}" varStatus="status1">
            <c:if test="${status1.current.status == 'A'}">
                <c:if test="${applicationCount > 0}">,</c:if>
                {
                    value:'<tcmis:jsReplace value="${status1.current.application}"/>',
                    text:'<tcmis:jsReplace value="${status1.current.applicationDesc}"/>'
                }
                <c:set var="applicationCount" value='${applicationCount+1}'/>
            </c:if>
        </c:forEach>
    );
</c:forEach>

var poRequiredFacsMap = {};

<c:forEach var="poRequiredFacsBean" items="${poRequiredFacs}" varStatus="poRequiredFacsStatus">
	poRequiredFacsMap['${poRequiredFacsBean.facilityId}'] = '${poRequiredFacsBean.poRequired}';
</c:forEach>

var altFacilityGroup = new Array(
    <c:forEach var="facilityGroupBean" items="${myFacilityGroupFacilityCollection}" varStatus="status">
    <c:if test="${!status.first}">,</c:if>
    {
        value:'<tcmis:jsReplace value="${facilityGroupBean.facilityGroupId}"/>',
        text:'<tcmis:jsReplace value="${facilityGroupBean.facilityGroupDescription}"/>'
    }
    </c:forEach>
);

var altFacilityGroupFacility = new Array();
<c:forEach var="facilityGroupBean" items="${myFacilityGroupFacilityCollection}" varStatus="status">
<c:set var="facilityCount" value='${0}'/>
altFacilityGroupFacility['<tcmis:jsReplace value="${status.current.facilityGroupId}"/>'] = new Array(
    <c:forEach items="${status.current.facilityList}" varStatus="status1">
        <c:if test="${facilityCount > 0}">,</c:if>
        {
            value:'<tcmis:jsReplace value="${status1.current.facilityId}"/>',
            text:'<tcmis:jsReplace value="${status1.current.facilityName}"/>'
        }
        <c:set var="facilityCount" value='${facilityCount+1}'/>
    </c:forEach>
);
</c:forEach>

// -->
 </script>
</head>

<!--call the loadLayoutWin() to set the sizes of the search section and initiate the layout. 
If you dont want to use the layout set javascript variable useLayout=false;-->
<body bgcolor="#ffffff" onLoad="loadLayoutWin('','deliveries');loadDropdown();" onresize="resizeFrames()">

<div class="interface" id="mainPage" style="">

<!-- Top Navigation- we will not display this section after sometime, when we move to the new menu structure -->
<div class="topNavigation" id="topNavigation">
<table width="100%" border="0" cellpadding="0" cellspacing="0">
<tr>
<td width="200">
<img src="/images/tcmtitlegif.gif" align="left">
</td>
<td>
<img src="/images/tcmistcmis32.gif" align="right">
</td>
</tr>
</table>

</div>
<!-- end of Top Navigation -->

<!-- Search Frame Begins -->
<div id="searchFrameDiv">

<div class="contentArea">
<tcmis:form action="/receiptdocumentviewerresults.do" onsubmit="return submitSearchOnlyOnce();" target="resultFrame">
<!-- Search Option Begins -->
<table id="searchMaskTable" width="600" border="0" cellpadding="0" cellspacing="0">
    <tr>
        <td>
            <div class="roundcont filterContainer">
                <div class="roundright">
                    <div class="roundtop">
                        <div class="roundtopright"><img src="/images/rndBoxes/borderTL_filter.gif" alt="" width="15" height="10" class="corner_filter" style="display: none"/></div>
                    </div>
                    <div class="roundContent">
                     <!-- Insert all the search option within this div -->
            <table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableSearch">
                <tr>
                    <c:choose>
                        <c:when test="${!empty myFacilityGroupFacilityCollection}">
                            <td width="30%" name="facilityGroupIdLabel" id="facilityGroupIdLabel" class="optionTitleBoldRight" nowrap>
                                <fmt:message key="label.facilitygroup"/>:
                            </td>
                            <td width="70%" class="optionTitleBoldLeft" nowrap>
                                <select name="facilityGroupIdSel" id="facilityGroupIdSel" class="selectBox" onchange="facilityGroupIdSelChanged()">
                                </select>
                                <input name="facilityGroupName" id="facilityGroupName" value="" class="inputBox"  style="display: none" readonly="readonly" size="33"/>
                                <input type="hidden" name="facilityGroupId" id="facilityGroupId" value="" />
                                <input name="facilityGroupMultiSel" id="facilityGroupMultiSel" type="button" class="listButton" onmouseover="this.className='listButton listButtonOvr'" onMouseout="this.className='listButton'" value="" onClick="popMultiSel('facilityGroupId');" />
                                &nbsp;&nbsp;&nbsp;&nbsp;
                                <span id="facilityGroupIdSpan"></span>
                                <fmt:message key="label.facility"/>:&nbsp;
                                <select name="facilityIdSel" id="facilityIdSel" class="selectBox" onchange="facilityIdSelChanged()">
                                </select>
                                <input name="facilityName" id="facilityName" value="" class="inputBox"  style="display: none" readonly="readonly" size="33"/>
                                <input type="hidden" name="facilityId" id="facilityId" value="" />
                                <input name="facilityMultiSel" id="facilityMultiSel" type="button" class="listButton" onmouseover="this.className='listButton listButtonOvr'" onMouseout="this.className='listButton'" value="" onClick="popMultiSel('facilityId');" />
                                <span id="facilityIdSpan"></span>
                            </td>
                        </c:when>
                        <c:otherwise>
                            <td width="30%" class="optionTitleBoldRight">
                                <fmt:message key="label.facility"/>:
                            </td>
                            <td width="70%" class="optionTitleBoldLeft">
                                <select name="facilityIdSel" id="facilityIdSel" class="selectBox" onchange="facilityIdSelChanged()">
                                </select>
                                <input type="hidden" name="facilityId" id="facilityId" value="" />
                            </td>
                        </c:otherwise>
                    </c:choose>
                </tr>

                <tr>
                    <td width="30%" class="optionTitleBoldRight" nowrap>
                    <fmt:message key="label.workarea"/>:
                    </td>
                    <td width="70%" class="optionTitleBoldLeft">
                        <select name="applicationId" id="applicationId" class="selectBox" >
                        </select>
                    </td>
                </tr>
                <tr>
                    <!-- search by -->
                    <td width="20%" class="optionTitleBoldRight" nowrap>
                        <fmt:message key="label.deliveredbetween"/>:&nbsp;
                    </td>
                    <td width="80%" class="optionTitleBoldLeft" nowrap>
                        <input class="inputBox pointer" readonly type="text" name="deliveryFromDate" id="deliveryFromDate" value="" onClick="return getCalendar(document.genericForm.deliveryFromDate,null,document.genericForm.deliveryToDate);" maxlength="10" size="9"/>
                        <fmt:message key="label.and"/>&nbsp;
                        <input class="inputBox pointer" readonly type="text" name="deliveryToDate" id="deliveryToDate" value="" onClick="return getCalendar(document.genericForm.deliveryToDate,document.genericForm.deliveryFromDate);" maxlength="10" size="9"/>
                    </td>
                </tr>
  
                <tr>
                  <!-- search by -->
                  <td width="20%" class="optionTitleBoldRight" nowrap>
                    <fmt:message key="label.searchby"/>:&nbsp;
                  </td>
                  <td width="80%" class="optionTitleBoldLeft" nowrap>
                    <html:select property="searchBy" styleClass="selectBox" styleId="searchBy">
                    <html:option value="receiptId" key="label.receipt"/>
                    <html:option value="mfgLot" key="label.lot"/>
                    <html:option value="mrLine" key="label.mrline"/>
                    <html:option value="facPartNo" key="label.partnumber"/>
                    <html:option value="partDescription" key="label.partdesc"/>
                    <html:option value="itemId" key="label.item"/>
                    <html:option value="itemDesc" key="label.itemdesc"/>
                    </html:select>
                    <!-- search type -->
                    <html:select property="searchType" styleClass="selectBox" styleId="searchType">
                    <html:option value="LIKE" key="label.contain"/>
                    <html:option value="STARTS_WITH" key="label.startswith"/>
                    <html:option value="ENDS_WITH" key="label.endswith"/>
                    <html:option value="=" key="label.is"/>
                    </html:select>
                    <input name="searchText" id="searchText" type="text" class="inputBox" value="<c:out value="${param.searchText}"/>">
                 </td>
               </tr>
                <tr>
                    <td width="20%" class="optionTitleBoldRight" nowrap>
                        <fmt:message key="label.expiredatebetween"/>:&nbsp;
                    </td>
                    <td width="80%" class="optionTitleBoldLeft" nowrap>
                        <input class="inputBox pointer" readonly type="text" name="expireFromDate" id="expireFromDate" value="" onClick="return getCalendar(document.genericForm.expireFromDate,null,document.genericForm.expireToDate);" maxlength="10" size="9"/>
                        <fmt:message key="label.and"/>&nbsp;
                        <input class="inputBox pointer" readonly type="text" name="expireToDate" id="expireToDate" value="" onClick="return getCalendar(document.genericForm.expireToDate,document.genericForm.expireFromDate);" maxlength="10" size="9"/>
                    </td>
                </tr>
               <tr>
                   <td width="20%" class="optionTitleBoldRight" nowrap><fmt:message key="label.groupby"/>:</td>
                   <td class="optionTitleBoldLeft" nowrap>
                      <input name="groupBy" id="mr" type="radio" class="radioBtns" value="mr" checked>
                        <fmt:message key="label.mr"/>
                      <input name="groupBy" id="lot" type="radio" class="radioBtns" value="lot">
                        <fmt:message key="label.lot"/>
                   </td>
               </tr>
               <tr>
                 <td colspan="2">
                    <!-- search button -->
                    <input name="submitSearch" type="button" class="inputBtns" value="<fmt:message key="label.search"/>" id="submitSearch" onmouseover="this.className='inputBtns inputBtnsOver'" onmouseout="this.className='inputBtns'" onclick= "submitForm()"/>
                    <input name="createExcel" id="createExcel" type="button" value="<fmt:message key="label.createexcelfile"/>" onclick="generateExcel();" class="inputBtns" onmouseover="this.className='inputBtns inputBtnsOver'" onmouseout="this.className='inputBtns'" />
                  </td>
               </tr>
            </table>
   <!-- End search options -->
            </div>
            <div class="roundbottom">
            <div class="roundbottomright"><img src="/images/rndBoxes/borderBL.gif" alt="" width="15" height="15" class="corner" style="display: none"/></div>
            </div>
            </div>
            </div>
        </td>
    </tr>
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
<input name="uAction" id="uAction" type="hidden" value="">
<input name="startSearchTime" id="startSearchTime" type="hidden"value="">
<input type="hidden" name="personnelLoggedIn" id="personnelLoggedIn" value='<tcmis:jsReplace value="${sessionScope.personnelBean.personnelId}"/>'/>
<input type="hidden" name="myDefaultFacilityId" id="myDefaultFacilityId" value='<tcmis:jsReplace value="${personnelBean.myDefaultFacilityId}"/>'/>
<input type="hidden" name="companyId" id="companyId" value='<tcmis:jsReplace value="${personnelBean.companyId}"/>'/>
<input name="poRequired" id="poRequired" type="hidden"value="">
<input name="deliveriesCostData" id="deliveriesCostData" value="" type="hidden">
<input name="deliveriesCustomerData" id="deliveriesCustomerData" value="" type="hidden">
<input name="maxData" id="maxData" type="hidden" value="2000"/>
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
  <br><br><br><fmt:message key="label.pleasewait"/>
  <br><br><br><img src="/images/rel_interstitial_loading.gif" align="middle">
</div>
<!-- Transit Page Ends -->
<div id="resultGridDiv" style="display: none;"> 
<!-- Search results start -->
<!-- If you want your results section to span only 50% set this to 50% or 800 pixels. Make sure to set resizeGridWithWindow to false. -->
<table id="resultsMaskTable" width="100%" border="0" cellpadding="0" cellspacing="0">
<tr> 
    <td>
<div class="roundcont contentContainer">
<div class="roundright">
  <div class="roundtop">
    <div class="roundtopright"> <img src="/images/rndBoxes/borderTL.gif" alt="" width="15" height="15" class="corner" style="display: none" /></div>
  </div>
  <div class="roundContent">
    <div class="boxhead"> <%-- boxhead Begins --%>
     <%-- Place all update links in divs that can be turned on or off depending on the search criteria that is selected
          Do not remove mainUpdateLinks div, even if you don't have any links. You can remove content inside the div but not the div itself.
      --%>
     <div id="mainUpdateLinks" style="display: none"> <%-- mainUpdateLinks Begins --%>
      <span id="updateResultLink" style="display: none"><a href="#" onclick="call('updateExpedite');"><fmt:message key="label.update"/></a></span>         
     </div> <%-- mainUpdateLinks Ends --%>
    </div> <%-- boxhead Ends --%>

    <div class="dataContent"> 
     <iframe  scrolling="no"  id="resultFrame" name="resultFrame" width="100%" height="" frameborder="0" marginwidth="0" src="/blank.html"></iframe>
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

</div> <!-- close of interface -->
    <!-- You can build your error messages below. Similar divs would have to be built to show any other messages in a yui pop-up.-->
<!-- Error Messages Begins -->
<div id="errorMessagesArea" class="errorMessages" style="display: none;overflow: auto;">
</div>

<!-- Input Dialog Window Begins -->
<div id="accountSysDailogWin" class="errorMessages" style="display: none;overflow: auto;">
<table width="100%" border="0" cellpadding="2" cellspacing="1">
<tr><td>&nbsp;</td></tr>
<tr>
<td align="center"  width="100%">
<select name="accountSystemSelectBox" id="accountSystemSelectBox" class="selectBox"> 
</select>
</td>
</tr>
<tr><td>&nbsp;</td></tr>
<tr>
<td align="center" width="100%">
<input name="accountSysOk"  id="accountSysOk"  type="button" value="<fmt:message key="label.ok"/>" class="smallBtns" onmouseover="this.className='smallBtns smallBtnsOver'" onmouseout="this.className='smallBtns'" onClick="accountSysOkClose();"/>
</td>
</tr>
</table>
</div>

<!-- Input Transit Window Begins -->
<div id="transitDailogWin" class="errorMessages" style="display: none;overflow: auto;">
	<table width="100%" border="0" cellpadding="2" cellspacing="1">
		<tr><td>&nbsp;</td></tr>
		<tr><td>&nbsp;</td></tr>
		<tr><td>&nbsp;</td></tr>
		<tr>
			<td align="center" id="transitLabel">
			</td>
		</tr>
		<tr>
			<td align="center">
				<img src="/images/rel_interstitial_loading.gif" align="middle">
			</td>
		</tr>
	</table>
</div>

</body>
</html:html>