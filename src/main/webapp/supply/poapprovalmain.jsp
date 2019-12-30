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
<meta http-equiv="content-type" content="text/html; charset=UTF-8"/>
<meta http-equiv="expires" content="-1"/>
<link rel="shortcut-icon" href="https://www.tcmis.com/images/buttons/tcmIS.ico"></link>
<%-- For Internationalization, copies data from calendarval.js --%>
<%@ include file="/common/locale.jsp" %>
<%@ include file="/common/opshubig.jsp" %>
<tcmis:gridFontSizeCss />
<script type="text/javascript" src="/js/common/formchek.js"></script>
<script type="text/javascript" src="/js/common/commonutil.js"></script>
<script type="text/javascript" src="/js/common/opshubig.js"></script>
<!-- This handles which key press events are disabled on this page -->
<script type="text/javascript" src="/js/common/disableKeys.js"></script>
<script type="text/javascript" src="/js/common/grid/iframegridresizemain.js"></script>
<script type="text/javascript" src="/js/menu/milonic_src.js"></script>
<script type="text/javascript" src="/js/menu/mmenudom.js"></script>
<script type="text/javascript" src="/js/menu/mainmenudata.js"></script>
<script type="text/javascript" src="/js/menu/contextmenu.js"></script>
<script type="text/javascript" src="/js/menu/mm_menueditapi.js"></script>
<%@ include file="/common/rightclickmenudata.jsp" %>
<script type="text/javascript" src="/js/supply/poapprovalmain.js"></script>
<script type="text/javascript" src="/js/common/standardGridmain.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxcommon.js"></script>
<script type="text/javascript" src="/dhtmlxLayout/codebase/dhtmlxlayout.js"></script>
<script type="text/javascript" src="/dhtmlxWindows/codebase/dhtmlxwindows.js"></script>
<script type="text/javascript" src="/js/calendar/newcalendar.js"></script>
<script type="text/javascript" src="/js/calendar/AnchorPosition.js"></script>
<script type="text/javascript" src="/js/calendar/PopupWindow.js"></script>
<script type="text/javascript" src="/js/calendar/calendarval.js"></script>
<title><fmt:message key="poApproval"/></title>
<script language="JavaScript" type="text/javascript">
<!-- 
var messagesData = new Array();
messagesData={alert:"<fmt:message key="label.alert"/>",
and:"<fmt:message key="label.and"/>",
errors:"<fmt:message key="label.errors"/>",    
submitOnlyOnce:"<fmt:message key="label.submitOnlyOnce"/>",
itemInteger:"<fmt:message key="error.item.integer"/>"};

<fmt:message var="haaspo" key="label.haaspo"/>
<fmt:message var="itemid" key="label.itemid"/>
var searchArray = [
    { id:'radianPo', text:'<tcmis:jsReplace value="${haaspo}"/>'},
    { id:'itemId', text:'<tcmis:jsReplace value="${itemid}"/>'}
];

<%-- Settings to allow My Entities, My hubs and My Inventory Group  in the standard pulldowns, used by /js/common/opshubig.js, called by setOps() in onload --%>
defaults.ops.nodefault = true;
defaults.hub.nodefault = false;
defaults.inv.nodefault = false;

function setDropDowns()  
{ 
	setOps();
	setSearchWhat(searchArray);
}
//-->
</script>
</head>
<body onload="loadLayoutWin('','poApproval');setDropDowns();" onresize="resizeFrames()">
<div class="interface" id="mainPage" style="">
<!-- Search Frame Begins -->
<div id="searchFrameDiv">
<!-- open contentArea -->
<div class="contentArea">
<tcmis:form action="poapprovalresults.do" onsubmit="return submitSearchOnlyOnce();" target="resultFrame">
<!-- Search Option Begins -->
<table id="searchMaskTable" width="100%" border="0" cellpadding="0" cellspacing="0">
	<tr>
		<td>
			<div class="roundcont filterContainer">
				<div class="roundright">
					<div class="roundtop">
						<div class="roundtopright">
							<img src="/images/rndBoxes/borderTL_filter.gif" alt="" width="15" height="10" class="corner_filter" style="display: none" />
						</div>
					</div>
					<div class="roundContent">
					<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableSearch">
				      <tr>
				        <td width="10%" class="optionTitleBoldRight" nowrap><fmt:message key="label.operatingentity"/>:</td>
				        <td width="30%" class="optionTitleBoldLeft">
				          <select name="opsEntityId" id="opsEntityId" class="selectBox">
					      </select>
				        </td>
				        <td width="10%" class="optionTitleBoldRight"><fmt:message key="label.buyer"/>:</td>
				        <td width="40%">         
				        <html:select property="buyerId" styleClass="selectBox" styleId="buyerId">
				          <html:option value="0" key="label.allbuyers"/>
				          <html:option value="" key="label.nobuyerassigned"/>
				          <html:options collection="buyerBeanCollection" property="personnelId" labelProperty="lastName"/>
				        </html:select>
				       </td>
				      </tr>
				      <tr>
				        <td width="10%" class="optionTitleBoldRight" nowrap><fmt:message key="label.hub"/>:</td>
				        <td width="30%" class="optionTitleBoldLeft">
				         	<select name="branchPlant" id="hub"  class="selectBox">
					        </select>
				        </td>
				        <td width="10%" class="optionTitleBoldRight"><fmt:message key="label.approver"/>:</td>
				        <td width="40%">
				        <html:select property="approverId" styleClass="selectBox" styleId="approverId" value="${personnelBean.personnelId}">
				          <html:option value="0" key="label.allapprovers"/>
				          <html:option value="" key="label.noapproverassigned"/>
				          <html:options collection="buyerBeanCollection" property="personnelId" labelProperty="lastName"/>
				        </html:select>
				        </td>
				      </tr>
				      <tr>
				        <td width="10%" class="optionTitleBoldRight" nowrap><fmt:message key="label.inventorygroup"/>:</td>
				        <td width="30%">
				          <select name="inventoryGroup" id="inventoryGroup" class="selectBox">
					      </select>
				        </td>
				        <td width="10%" class="optionTitleBoldRight" nowrap><fmt:message key="label.datecreated"/>:</td>
				        <td width="40%">
				         	<label for="createdFromDate" class="optionTitleBoldRight"><fmt:message key="label.from"/></label>
				         	<input type="text" id="createdFromDate" name="createdFromDate" class="inputBox pointer" readonly value="" onclick="return getCalendar(document.genericForm.createdFromDate,null,document.genericForm.createdToDate);" maxlength="10" size="8"/>
				         	<label for="createdToDate" class="optionTitleBoldRight"><fmt:message key="label.to"/></label>
				         	<input type="text" id="createdToDate" name="createdToDate" class="inputBox pointer" readonly value="" onclick="return getCalendar(document.genericForm.createdToDate,document.genericForm.createdFromDate);" maxlength="10" size="8"/>
				        </td>
				      </tr>
				      <tr>
				        <td width="10%" class="optionTitleBoldRight"><fmt:message key="label.supplier"/>:</td>
				        <td width="30%">
					        <input type="text" id="supplier" name="supplier" class="inputBox" readonly/>
					        <input type="button" id="supplierLookupBtn" name="supplierLookupBtn" value="..." class="lookupBtn" onmouseover="this.className='lookupBtn lookupBtnOver'" onmouseout="this.className='lookupBtn'" onclick="lookupSuppliers();"/>
					        <input type="button" id="clearSupplier" name="clearSupplier" class="smallBtns" onmouseover="this.className='smallBtns smallBtnsOver'" onmouseout="this.className='smallBtns'" value="<fmt:message key="label.clear"/>" onclick="clearSupplier();"/>
				        </td>
				        <td width="10%" class="optionTitleBoldRight" nowrap><fmt:message key="label.search"/>:</td>
				        <td width="40%">
				        	<select name="searchWhat" id="searchWhat" class="selectBox">
							</select>
							<html:select property="searchType" styleId="searchType" styleClass="selectBox">
								<html:option value="is" ><fmt:message key="label.is"/></html:option>
								<html:option value="contains"><fmt:message key="label.contains"/></html:option>
								<html:option value="startsWith" key="label.startswith"/>
								<html:option value="endsWith" key="label.endswith"/>
							</html:select>
							<html:text property="searchText" styleId="searchText" size="20" styleClass="inputBox"/>
				        </td>
				      </tr>
				      <tr>
				        <td width="10%" class="optionTitleBoldRight"><fmt:message key="label.status"/>:</td>
				        <td width="80%" colspan="3">
				          <label for="statusAwaitingApproval" class="optionTitleBoldLeft">
				          	<fmt:message var="awaitingApproval" key="label.awaitingapproval"/>
					        <input name="status" id="statusAwaitingApproval" type="radio" class="radioBtns" value="AWAITING APPROVAL"
					        	onclick="selectStatus('${awaitingApproval}')"/>
					        <c:out value="${awaitingApproval}"/>
					      </label>&nbsp;&nbsp;
					      <label for="statusApproved" class="optionTitleBoldLeft">
					      	<fmt:message var="approved" key="label.approved"/>
					        <input name="status" id="statusApproved" type="radio" class="radioBtns" value="APPROVED"
					        	onclick="selectStatus('${approved}')"/>
					        <c:out value="${approved}"/>
					      </label>&nbsp;&nbsp;
					      <label for="statusRejected" class="optionTitleBoldLeft">
					      	<fmt:message var="rejected" key="label.rejected"/>
					        <input name="status" id="statusRejected" type="radio" class="radioBtns" value="REJECTED"
					        	onclick="selectStatus('${rejected}')"/>
					        <c:out value="${rejected}"/>
					      </label>&nbsp;&nbsp;
					      <label for="statusAll" class="optionTitleBoldLeft">
					      	<fmt:message var="all" key="label.all"/>
					        <input name="status" id="statusAll" type="radio" class="radioBtns" checked value="ALL"
					        	onclick="selectStatus('${all}')"/>
					        <c:out value="${all}"/>
					      </label>
				        </td>
				      </tr>
				      <tr>
					      <td colspan="4">
							<input name="submitSearch" id="submitSearch" type="submit" value="<fmt:message key="label.search"/>" class="inputBtns" onmouseover="this.className='inputBtns inputBtnsOver'" onmouseout="this.className='inputBtns'" onclick="return submitSearchForm();"/>
							<input name="buttonCreateExcel" id="buttonCreateExcel" type="submit" class="inputBtns" value="<fmt:message key="label.createexcelfile"/>" onmouseover="this.className='inputBtns inputBtnsOver'" onmouseout="this.className='inputBtns'" onclick="generateExcel(); return false;"/>
						  </td>
				      </tr>
				    </table>
					</div>
					<div class="roundbottom">
						<div class="roundbottomright">
							<img src="/images/rndBoxes/borderBL.gif" alt="" width="15" height="15" class="corner" style="display: none" />
						</div>
					</div>
				</div>
			</div>
		</td>
	</tr>
</table>
<!-- Search Option Ends -->
<!-- Error Messages Begins -->
<!-- Build this section only if there is an error message to display. For the search section, we show the error messages within its frame -->
<c:if test="${requestScope['org.apache.struts.action.ERROR'] != null}">
	<div class="spacerY">&nbsp;
		<div id="searchErrorMessagesArea" class="errorMessages">
			<html:errors />
		</div>
	</div>
</c:if>
<!-- Error Messages Ends -->
<!-- Hidden Element start -->
<div id="hiddenElements" style="display:none">
	<input type="hidden" name="uAction" id="uAction" value=""/>
	<input name="startSearchTime" id="startSearchTime" type="hidden" value="" />
	<input name="searchWhatDesc" id="searchWhatDesc" type="hidden" />
	<input name="searchTypeDesc" id="searchTypeDesc" type="hidden" />
	<input name="supplierName" id="supplierName" type="hidden" />
	<input name="selectedStatus" id="selectedStatus" type="hidden" />
	<input name="selectedBuyer" id="selectedBuyer" type="hidden" />
	<input name="selectedApprover" id="selectedApprover" type="hidden" />
	<input name="opsEntityDesc" id="opsEntityDesc" type="hidden" />
	<input name="hubName" id="hubName" type="hidden" />
	<input name="inventoryGroupDesc" id="inventoryGroupDesc" type="hidden" />
</div>
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
<table id="resultsMaskTable" width="100%" border="0" cellpadding="0" cellspacing="0">
	<tr>
		<td>
			<div class="roundcont contentContainer">
				<div class="roundright">
					<div class="roundtop">
						<div class="roundtopright">
							<img src="/images/rndBoxes/borderTL.gif" alt="" width="15" height="15" class="corner" style="display: none" />
						</div>
					</div>
					<div class="roundContent">
						<%-- boxhead Begins --%>
						<div class="boxhead">
							<%-- Place all update links in divs that can be turned on or off depending on the search criteria that is selected
								Do not remove mainUpdateLinks div, even if you don't have any links. You can remove content inside the div but not the div itself. --%>
							<div id="mainUpdateLinks" style="display: none"> <%-- mainUpdateLinks Begins --%>
								<span id="approveLink" style="display: none"><a href="#" onclick="call('approveCheckedOrders')"><fmt:message key="label.approve"/></a></span>
							</div>
						</div>
						<div class="dataContent"> 
							<iframe  scrolling="no"  id="resultFrame" name="resultFrame" width="100%" height="400" frameborder="0" marginwidth="0" src="/blank.html"></iframe>
						</div>
						<%-- result count and time --%>
						<div id="footer" class="messageBar"></div>
					</div>
					<div class="roundbottom">
						<div class="roundbottomright">
							<img src="/images/rndBoxes/borderBL.gif" alt="" width="15" height="15" class="corner" style="display: none" />
						</div>
					</div>
				</div>
			</div>
		</td>
	</tr>
</table>
<!-- Search results end -->
</div>
</div>
<!-- Result Frame Ends -->
</div>
<!-- close of interface -->
<!-- You can build your error messages below. Similar divs would have to be built to show any other messages in a yui pop-up.-->
<!-- Error Messages Begins -->
<div id="errorMessagesArea" class="errorMessages" style="display:none;z-index:5;">
	<div id="errorMessagesAreaTitle" class="hd"><fmt:message key="label.errors"/></div>
	<div id="errorMessagesAreaBody" class="bd">
		<html:errors/>
	</div>
</div>
<%-- show legend Ends --%>
</body>
</html:html>