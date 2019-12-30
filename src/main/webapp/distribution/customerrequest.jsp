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
<%--NEW--%>
<script type="text/javascript" src="/js/common/grid/iframegridresizemain.js"></script>
<!-- This handels which key press events are disabeled on this page -->
<script type="text/javascript" src="/js/common/disableKeys.js"></script>

<!-- This handels the menu style and what happens to the right click on the whole page -->
<script type="text/javascript" src="/js/menu/milonic_src.js"></script>
<script type="text/javascript" src="/js/menu/mmenudom.js"></script>
<script type="text/javascript" src="/js/menu/mainmenudata.js"></script>
<script type="text/javascript" src="/js/menu/contextmenu.js"></script>
<%@ include file="/common/rightclickmenudata.jsp" %>

<%-- For Calendar support --%>

<script src="/js/calendar/newcalendar.js" language="JavaScript"></script>
<script src="/js/calendar/AnchorPosition.js" language="JavaScript"></script>
<script src="/js/calendar/PopupWindow.js" language="JavaScript"></script>

<!-- Add any other javascript you need for the page here -->
<script type="text/javascript" src="/js/common/standardGridmain.js"></script>
<script type="text/javascript" src="/js/distribution/customerrequest.js"></script>

<!-- These are for the Grid, uncomment if you are going to use the grid -->
<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxcommon.js"></script>
<script type="text/javascript" src="/dhtmlxLayout/codebase/dhtmlxlayout.js"></script>
<script type="text/javascript" src="/dhtmlxWindows/codebase/dhtmlxwindows.js"></script>

<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxgrid.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxgridcell.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_form.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_srnd.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_json.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/excells/dhtmlxgrid_excell_customized.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxcommon_haas.js"></script>


<title>
</title>

<%--TODO Add option to search for requests waiting for my approval.--%>

<script language="JavaScript" type="text/javascript">
<!--
//add all the javascript messages here, this for internationalization of client side javascript messages
var messagesData = new Array();
messagesData={alert:"<fmt:message key="label.alert"/>",and:"<fmt:message key="label.and"/>",
customerrequestdetail:"<fmt:message key="customerrequestdetail.title"/>",
newcustomerrequest:"<fmt:message key="customerrequestdetail.title"/>",
submitOnlyOnce:"<fmt:message key="label.submitOnlyOnce"/>"};
// -->
</script>
</head>

<body bgcolor="#ffffff" onload="loadLayoutWin('','customerRequest');" onresize="resizeFrames()">

<div class="interface" id="mainPage" style="">

<!-- Search Frame Begins -->
<div id="searchFrameDiv">
<%--NEW - removed the search frame and copied the search section here--%>
<div class="contentArea">
<tcmis:form action="/customerrequest.do" onsubmit="return submitSearchOnlyOnce();" target="resultFrame">
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
    <table width="600" border="0" cellpadding="0" cellspacing="0" class="tableSearch">
      <tr>
        <td width="10%" class="optionTitleBoldRight"><fmt:message key="label.status"/>:</td>
        <td width="15%">
<c:set var="selectedStatus" value='${param.status}'/>
<c:if test="${empty selectedStatus}">
	<c:set var="selectedStatus" value='Pending Financial Approval'/> 
</c:if>
          <html:select property="status" styleClass="selectBox" styleId="status" value="${selectedStatus}">
          <html:option value="" key="label.all"/>
          <html:option value="Draft" key="label.draft"/>
          <html:option value="Pending New Company Approval" key="label.pendingnewcompanyapproval"/>
          <html:option value="Pending Financial Approval" key="label.pendingfinancialapproval"/>
          <html:option value="Approved" key="label.approved"/>
          <html:option value="Created" key="label.created"/>
          <html:option value="Rejected" key="label.rejected"/>
        </html:select>
       </td>
		<td width="5%" class="optionTitleBoldRight">
		    <fmt:message key="label.search"/>:&nbsp;
		</td>
		<td width="5%" class="optionTitleBoldLeft" nowrap>
			<select class="selectBox" name="searchField" id="searchField">
						<option selected value="customerName"><fmt:message key="label.customer"/></option>
						<option value="requesterName"><fmt:message key="label.requestor"/></option>
						<option value="countryAbbrev"><fmt:message key="label.country"/></option>
						<option value="sapCustomerCode"><fmt:message key="label.sapcustomercode"/></option>
			</select>
			<select name="searchMode" id="searchMode" class="selectBox">
				<option value="is"><fmt:message key="label.is"/></option>
				<option value="contains" selected><fmt:message  key="label.contains"/></option>
	            <option value="startsWith"><fmt:message  key="label.startswith"/></option>
    	        <option value="endsWith"><fmt:message  key="label.endswith"/></option>
			</select>
		</td>
		<td width="5%" class="optionTitleBoldLeft">
			<input class="inputBox" type="text" name="searchArgument" id="searchArgument" value="" size="20">
		</td>
      </tr>
      <tr>
      <td/>
      <td/>
        <td width="10%" class="optionTitleBoldRight" nowrap><fmt:message key="label.requestdate"/>:</td>
      <td width="10%" colspan="4" nowrap>
           <fmt:message key="label.from"/>&nbsp;
          <input name="dorfrom" id="dorfrom" type="text" class="inputBox pointer" size="10" readonly="readonly" onClick="getCalendar(document.genericForm.dorfrom,null,document.genericForm.dorto);" />
          <fmt:message key="label.to"/>&nbsp;
            <input name="dorto" id="dorto" type="text" class="inputBox pointer" size="10" readonly="readonly" onClick="getCalendar(document.genericForm.dorto,document.genericForm.dorfrom,null,null,null);"/>            
        </td>
      
      </tr>
      <tr>
      <td width="10%" colspan="4" nowrap>
          <input name="submitSearch" type="submit" class="inputBtns" value="<fmt:message key="label.search"/>" id="submitSearch" onmouseover="this.className='inputBtns inputBtnsOver'" onMouseout="this.className='inputBtns'"
          		 onclick= "return submitSearchForm()">
          <input name="xslButton" type="button" class="inputBtns" value="<fmt:message key="label.createExcel"/>" id="xslButton" onmouseover="this.className='inputBtns inputBtnsOver'" onMouseout="this.className='inputBtns'"
          		 onclick= "return createXSL()"/>
<c:set var="newPermission" value="false"/>
<tcmis:facilityPermission indicator="true" userGroupId="NewSuppApprover">
  <c:set var="newPermission" value="true"/>
</tcmis:facilityPermission>
<tcmis:facilityPermission indicator="true" userGroupId="Sourcing">
  <c:set var="newPermission" value="true"/>
</tcmis:facilityPermission>
<tcmis:facilityPermission indicator="true" userGroupId="newSupplier">
  <c:set var="newPermission" value="true"/>
</tcmis:facilityPermission>                    
<c:if test="${newPermission == 'true'}">
<input name="submitNew" id="submitNew" type="button" value="<fmt:message key="label.newcustomer"/>" class="inputBtns"  onmouseover="this.className='inputBtns inputBtnsOver'" onmouseout="this.className='inputBtns'"
 onclick="subnew()">
</c:if>
</td>
      </tr>
    </table>
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


<!-- Hidden element start -->
 <div id="hiddenElements" style="display: none;">
<input type="hidden" name="uAction" id="uAction" value="createXSL"/>
<input name="startSearchTime" id="startSearchTime" type="hidden" value="" />
<input type="hidden" name="customerRequestId" id="customerRequestId">
 </div>
<!-- Hidden elements end -->

</tcmis:form>
</div> <!-- close of contentArea -->

</div>
<!-- Search Frame Ends -->

<!-- Result Frame Begins -->
<div id="resultFrameDiv" style="margin: 4px 4px 0px 4px;">

<%--NEw -Transit Page Starts --%>
<div id="transitPage" style="display: none;" class="optionTitleBoldCenter">
  <br><br><br><fmt:message key="label.pleasewait"/>
  <br><br><br><img src="/images/rel_interstitial_loading.gif" align="middle">
</div>
<!-- Transit Page Ends -->

<div id="resultGridDiv" style="display: none;">
<!-- Search results start -->
<!-- If you want your results section to span only 50% set this to 50% or 800 pixels. Make sure to set resizeGridWithWindow to false. -->
<table id="resultsMaskTable" width="100%" border="0" cellpadding="0" cellspacing="0">
<tr><td>
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
<span id="showlegendLink">
<%--         <a href="javascript:simpleCallToServerWithGrid('inventoryreportresults.do?uAction=loaddata')"><fmt:message key="label.lotstatuslegend"/></a>
--%>
      </span>&nbsp;
      <%-- 
            <span id="updateResultLink" style="display: none">
        <tcmis:permission indicator="true" userGroupId="transactions">
         <a href="#" onclick="submitUpdate(); return false;"><fmt:message key="label.update"/></a>
        </tcmis:permission>
      </span> --%>
     </div> <%-- mainUpdateLinks Ends --%>
    </div> <%-- boxhead Ends --%>

    <div class="dataContent">
     <iframe scrolling="no" id="resultFrame" name="resultFrame" width="100%" height="300" frameborder="0" marginwidth="0" src="/blank.html"></iframe>
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

<!-- You can build your error messages below. Similar divs would have to be built to show any other messages in a inline pop-up.-->
<!-- Error Messages Begins -->
<div id="errorMessagesArea" class="errorMessages" style="display: none;overflow: auto;">

</div>

</body>
</html:html>

