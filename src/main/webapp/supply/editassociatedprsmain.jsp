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
<meta http-equiv="content-type" content="text/html; charset=UTF-8"/>
<meta http-equiv="expires" content="-1"/>
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

<!-- Add any other javascript you need for the page here -->
<script type="text/javascript" src="/js/common/standardGridmain.js"></script>
<script type="text/javascript" src="/js/supply/editassociatedprsmain.js"></script>

<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxcommon.js"></script>
<script type="text/javascript" src="/dhtmlxLayout/codebase/dhtmlxlayout.js"></script>
<script type="text/javascript" src="/dhtmlxWindows/codebase/dhtmlxwindows.js"></script>

<title>
<fmt:message key="label.purchaseordersupplier"/>
</title>

<script language="JavaScript" type="text/javascript">
<!--
//add all the javascript messages here, this for internationalization of client side javascript messages
var statusFlag="${statusFlag}";

var messagesData = new Array();
messagesData={alert:"<fmt:message key="label.alert"/>",and:"<fmt:message key="label.and"/>",
validvalues:"<fmt:message key="label.validvalues"/>",
submitOnlyOnce:"<fmt:message key="label.submitOnlyOnce"/>", 
searchInput:"<fmt:message key="label.inputSearchText"/>",
newsupplierrequest:"<fmt:message key="newsupplierrequest.title"/>",    
itemInteger:"<fmt:message key="error.item.integer"/>"};
// -->
</script>
</head>

<body bgcolor="#ffffff"  onload="loadLayoutWin('','editassociatedPRs');if ($v('fromPo') === 'Y' && submitSearchForm()) document.genericForm.submit();" >

<div class="interface" id="mainPage" style="">

<!-- Search Frame Begins -->
<div id="searchFrameDiv">
<!-- start of contentArea -->
<div class="contentArea">
<tcmis:form action="/editassociatedprsresults.do" onsubmit="return submitSearchForm();" target="resultFrame">

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
        <td class="optionTitleBoldRight" nowrap><fmt:message key="label.customerpo"/>:</td>
        <td>
          <input type="text" class="inputBox" name="raytheonPo" id="raytheonPo"  onchange="raytheonPoChanged()"/>          
        </td> 
        <td class="optionTitleBoldRight" nowrap><fmt:message key="label.buyer"/>:</td>
        <td>         
	        <html:select property="buyerId" styleClass="selectBox" styleId="buyerId" value="${param.buyerId}">
	          <html:option value="0" key="label.allbuyers"/>	         
	          <html:options collection="buyerBeanCollection" property="personnelId" labelProperty="lastName"/>
	        </html:select>
        </td>
        <td class="optionTitleBoldRight" nowrap><fmt:message key="label.supplypath"/>:</td>
        <td>         
	        <html:select property="supplyPath" styleClass="selectBox" styleId="supplyPath">
	          <html:option value="ALL" key="label.all"/>
	          <html:option value="Dbuy" key="label.dbuy"/>
	          <html:option value="Purchaser" key="label.purchasing"/>
	          <html:option value="Xbuy" key="label.xbuy"/> 
	          <html:option value="Wbuy" key="label.wbuy"/>
	          <html:option value="Ibuy" key="label.ibuy"/>
	        </html:select>
        </td>
        <td width="340" rowspan="2">         
        <html:select property="companyIdArray" styleClass="selectBox" styleId="companyIdArray" size="4" multiple="true">
          <html:option value="ALL" key="label.all" />
          <html:options collection="companyBeanCollection" property="companyId" labelProperty="companyName"/>
        </html:select>
       </td>             
      </tr>      
      <tr>
        <td class="optionTitleBoldRight" nowrap><fmt:message key="label.shiptolocationid"/>:</td>
        <td>
          <input type="text" name="shipToLocationId" id="shipToLocationId" class="inputBox" value="${param.shipToLocationId}"/>          
        </td>        
        <td class="optionTitleBoldRight" nowrap><fmt:message key="label.mr"/>:</td>
        <td>
          <input type="text" name="mrNumber" id="mrNumber" class="inputBox"/>          
        </td>
        <td colspan="2" class="optionTitleBoldRight"><fmt:message key="label.company"/>:</td> 
      </tr>
      <tr>       
        <td class="optionTitleBoldRight"><fmt:message key="label.sortby"/>:</td>
        <td>         
	        <html:select property="sortBy" styleClass="selectBox" styleId="sortBy">
	          <html:options collection="vvBuypageSortBeanCollection" property="sortId" labelProperty="sortDesc"/>
	        </html:select>
        </td>
        <td class="optionTitleBoldRight"><fmt:message key="label.search"/>:</td>
        <td nowrap colspan="4">         
	        <html:select property="searchWhat" styleClass="selectBox" styleId="searchWhat">
	          <%--<html:option value="RAYTHEON_PO" key="label.customerpo"/>--%>
	          <html:option value="ITEM_ID" key="label.itemid"/>
	          <html:option value="LAST_SUPPLIER" key="label.lastsupplier"/>
	          <html:option value="MFG_ID" key="label.manufacturer"/>
	          <%--<html:option value="MR_NUMBER" key="label.mr"/>--%>
	          <html:option value="PART_ID" key="label.partnumber"/>
	          <html:option value="RADIAN_PO" key="label.po"/>
	          <html:option value="PR_NUMBER" key="label.pr"/>
	        </html:select>
	        <html:select property="searchType" styleClass="selectBox" styleId="searchType">
	          <html:option value="IS" key="label.is"/>
	          <html:option value="CONTAINS" key="label.contain"/>
	          <html:option value="START_WITH" key="label.startswith"/>
	          <html:option value="END_WITH" key="label.endswith"/>
	        </html:select>
            <input name="searchText" id="searchText" type="text" class="inputBox"/>
        </td>
      </tr>
      <tr>
	      <td colspan="7">
	        <input name="submitSearch" id="submitSearch" type="submit" value="<fmt:message key="label.search"/>" class="inputBtns" onmouseover="this.className='inputBtns inputBtnsOver'" onmouseout="this.className='inputBtns'" onclick="return submitSearchForm();"/>	        
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
<input name="uAction" id="uAction" type="hidden" value=""/>
<input name="startSearchTime" id="startSearchTime" type="hidden"value=""/>
<input type="hidden" name="popUp" id="popUp" value="<tcmis:jsReplace value="${param.popUp}"/>"/>
<input type="hidden" name="fromPo" id="fromPo" value="<tcmis:jsReplace value="${param.fromPo}"/>"/>
<input type="hidden" name="itemId" id="itemId" value="<tcmis:jsReplace value="${param.itemId}"/>"/>
<input type="hidden" name="inventoryGroup" id="inventoryGroup" value="<tcmis:jsReplace value="${param.inventoryGroup}"/>"/>
<input type="hidden" name="radianPo" id="radianPo" value="<tcmis:jsReplace value="${param.radianPo}"/>"/>
<input type="hidden" name="buyerId" id="buyerId" value="<tcmis:jsReplace value="${param.buyerId}"/>"/>
<input type="hidden" name="currencyId" id="currencyId" value="<tcmis:jsReplace value="${param.currencyId}"/>"/>
<input type="hidden" name="attachedPr" id="attachedPr" value="<tcmis:jsReplace value="${param.attachedPr}"/>"/>
<input type="hidden" name="userAction" id="userAction" value="<tcmis:jsReplace value="${param.userAction}"/>"/>
<input type="hidden" name="currencyId" id="currencyId" value="<tcmis:jsReplace value="${param.currencyId}"/>"/>
<input name="searchHeight" id="searchHeight" type="hidden" value="108"/>
</div>
<!-- Hidden elements end -->

<!-- Search Frame Ends -->
</tcmis:form>
</div> <!-- close of contentArea -->
</div> <!-- close of searchFrameDiv -->

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
    <div class="boxhead"> <%-- boxhead Begins --%>
     <%-- Place all update links in divs that can be turned on or off depending on the search criteria that is selected
          Do not remove mainUpdateLinks div, even if you don't have any links. You can remove content inside the div but not the div itself.
      --%>        
    <div id="mainUpdateLinks" style="display: none" > <%-- mainUpdateLinks Begins --%>     
     <span id="updateResultLink">
     	<a href="#" onclick="call('updateBuyOrdersToPO');"><fmt:message key="label.update"/></a>
     	<a href="#" onclick="cancelAndReturn();"><fmt:message key="label.cancel"/></a>
     </span> 
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


</body>
</html:html>