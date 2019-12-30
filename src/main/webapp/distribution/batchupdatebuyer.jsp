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
<link rel="shortcut icon" href="/images/buttons/tcmIS.ico"></link>

<%@ include file="/common/locale.jsp" %>
<!--Use this tag to get the correct CSS class.
This looks at what the user's preffered font size and which application he is viewing to set the correct CSS. -->
<tcmis:gridFontSizeCss />
<%-- Add any other stylesheets you need for the page here --%>

<script type="text/javascript" src="/js/common/formchek.js"></script>
<script type="text/javascript" src="/js/common/commonutil.js"></script>
<!-- This handles all the resizing of the page and frames -->
<%--NEW--%>
<script type="text/javascript" src="/js/common/grid/nosearchresize.js"></script>
<!-- This handels which key press events are disabeled on this page -->
<script type="text/javascript" src="/js/common/disableKeys.js"></script>

<!-- This handels the menu style and what happens to the right click on the whole page -->
<script type="text/javascript" src="/js/menu/milonic_src.js"></script>
<script type="text/javascript" src="/js/menu/mmenudom.js"></script>
<script type="text/javascript" src="/js/menu/mainmenudata.js"></script>
<script type="text/javascript" src="/js/menu/contextmenu.js"></script>
<%@ include file="/common/rightclickmenudata.jsp" %>
<%@ include file="/common/opshubig.jsp" %>

<!-- For Calendar support -->
<script type="text/javascript" src="/js/calendar/newcalendar.js"></script>
<script type="text/javascript" src="/js/calendar/AnchorPosition.js"></script>
<script type="text/javascript" src="/js/calendar/PopupWindow.js"></script>
<script type="text/javascript" src="/js/calendar/calendarval.js"></script>

<!-- Add any other javascript you need for the page here -->
<script type="text/javascript" src="/js/common/noSearchStandardGridmain.js"></script>
<script type="text/javascript" src="/js/common/opshubig.js"></script>
<script type="text/javascript" src="/js/distribution/batchupdatebuyer.js"></script>

<!-- These are for the Grid, uncomment if you are going to use the grid -->
<%--NEW - dhtmlX grid files--%>
<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxcommon.js"></script>
<script type="text/javascript" src="/dhtmlxWindows/codebase/dhtmlxwindows.js"></script>
    
<title>
<fmt:message key="label.batchupdate"/>
</title>


<script language="JavaScript" type="text/javascript">
<!--
var messagesData = new Array();
messagesData={alert:"<fmt:message key="label.alert"/>",and:"<fmt:message key="label.and"/>",
errors:"<fmt:message key="label.errors"/>",
update:"<fmt:message key="label.update"/>",
validvalues:"<fmt:message key="label.validvalues"/>",
submitOnlyOnce:"<fmt:message key="label.submitOnlyOnce"/>",
legalCompanyNameRequired:"<fmt:message key="error.legalcompanyname.required"/>",
countryRequired:"<fmt:message key="error.country.required"/>",
addressRequired:"<fmt:message key="error.address.required"/>",
cityRequired:"<fmt:message key="error.city.required"/>",
stateRequired:"<fmt:message key="error.state.required"/>",
zipRequired:"<fmt:message key="label.postalcode"/>",
shipToNameRequired:"<fmt:message key="label.shipto"/> <fmt:message key="label.name"/>",
pleaseselect:"<fmt:message key="label.pleaseselect"/>", 
fulladdressnozip:"<fmt:message key="label.fulladdressnozip"/>",
fulladdressnocity:"<fmt:message key="label.fulladdressnocity"/>",
all:"<fmt:message key="label.all"/>",
missinginventorygroup:"<fmt:message key="label.missinginventorygroup"/>",
itemid:"<fmt:message key="label.itemid"/>",
eitheritemorsupplier:"<fmt:message key="label.eitheritemorsupplier"/>", 
choosesupplierb4choosecontact:"<fmt:message key="label.choosesupplierb4choosecontact"/>",
inputnewvalues:"<fmt:message key="label.inputnewvalues"/>",
supplier:"<fmt:message key="label.supplier"/>"
};

defaults.ops.nodefault = true;
//defaults.hub.nodefault = true;
//defaults.inv.nodefault = true;

defaults.ops.callback = checkPermission;

var OpsEntityPermissionArray = new Array();
<c:set var="counter" value='${0}'/>
<c:forEach var="opsEntityBean" items="${personnelBean.permissionBean.userGroupMemberOpsEntityBeanCollection}" varStatus="status">
  <c:if test="${(status.current.userGroupId  == 'supplierPriceList')}">
    OpsEntityPermissionArray["<c:out value="${counter}"/>"] = "<c:out value="${status.current.opsEntityId}" escapeXml="false"/>";
    <c:set var="counter" value='${counter + 1}'/>    
  </c:if>
</c:forEach>
// -->
</script>
</head>

<body bgcolor="#ffffff"  onload="showError();setOps();$('opsEntityId').value='${param.opsEntityId}';opsChanged();$('hub').value='${param.hub}';hubChanged();if('${param.inventoryGroup}' != 'All')$('inventoryGroup').value='${param.inventoryGroup}';" onunload="closeAllchildren();try{opener.closeTransitWin();}catch(ex){}" >

<tcmis:form action="/batchupdatebuyer.do" onsubmit="return submitFrameOnlyOnce();">

<div class="interface" id="mainPage" style="">

<!-- Error Messages Begins -->

<div id="errorMessagesAreaBody" style="display:none;">
${tcmISError}
${rowCount}
</div>
<!-- Error Messages Ends -->

<script type="text/javascript">
<!--
/*Check if there is any error messages to show and set the variable you use in javascript to true or false.*/
<c:choose>
   <c:when test="${ empty tcmISError && done != 'Y'}">
    showErrorMessage = false;
   </c:when>
   <c:otherwise>
    showErrorMessage = true;
   </c:otherwise>
</c:choose>

//-->
</script>

<!-- Result Frame Begins -->
<div id="resultFrameDiv" style="margin: 4px 4px 0px 4px;">

<%--NEw -Transit Page Starts --%>
<div id="transitPage" style="display: none;" class="optionTitleBoldCenter">
  <br/><br/><br/><fmt:message key="label.pleasewait"/>
  <br/><br/><br/><img src="/images/rel_interstitial_loading.gif" align="middle"/>
</div>
<!-- Transit Page Ends -->

<div id="resultGridDiv" style="display:">
<!-- Search results start -->
<!-- If you want your results section to span only 50% set this to 50% and your main table will be 100% -->
<table id="resultsMaskTable" width="100%" border="0" cellpadding="0" cellspacing="0">
<tr><td>
<div class="roundcont contentContainer">
<div class="roundright">
  <div class="roundtop">
    <div class="roundtopright"> <img src="/images/rndBoxes/borderTL.gif" alt="" width="15" height="15" class="corner" style="display: none" /></div>
  </div>
  <div class="roundContent">
    <div class="boxhead"> <%-- boxhead Begins --%>
     
    </div> <%-- boxhead Ends --%>

<div class="dataContent">
<fieldset>
<legend>&nbsp;<fmt:message key="label.batchupdate"/>&nbsp;</legend>
<table id="searchTable" width="100%" border="0" cellpadding="0" cellspacing="0" class="tableSearch">

<tr>
      <td width="10%" class="optionTitleBoldRight" nowrap><fmt:message key="label.operatingentity"/>:</td>
      <td width="10%">
         <select name="opsEntityId" id="opsEntityId" class="selectBox" onChange>
         </select>
      </td>
    </tr>
    <tr>
      <td width="10%" class="optionTitleBoldRight"><fmt:message key="label.hub"/>:</td>
      <td width="10%">
         <select name="hub" id="hub" class="selectBox">
         </select>
      </td>
    </tr>
    <tr>
      <td width="10%" class="optionTitleBoldRight" nowrap><fmt:message key="label.inventorygroup"/>:&nbsp;</td>
      <td width="10%" nowrap>
       <select name="inventoryGroup" id="inventoryGroup" class="selectBox">
       </select>
       <input type="checkbox" name="allInventoryGroups" id="allInventoryGroups" value="Y"><fmt:message key="label.includeallinventorygroup"/></input>
      </td>
    </tr>
 
<tr>    
    <td width="10%" class="optionTitleBoldCenter">
		<img src="/images/rightarrow.png" onmouseover=style.cursor="hand" onclick="transferInventoryGroup('inventoryGroup','inventoryGroups');"/>
		<br/><br/>
		<button id="deleteBtn" style="display:none" class="smallBtns" onmouseover="this.className='smallBtns smallBtnsOver'" onMouseout="this.className='smallBtns'"
        	name="None" value=""  OnClick="inventoryDelete($('inventoryGroups').options);return false;"><fmt:message key="label.delete"/> </button>
		<input class="smallBtns" onmouseover="this.className='smallBtns smallBtnsOver'" onmouseout="this.className='smallBtns'" name="clearsupplierlike" value="<fmt:message key="label.clear"/>" onclick="clearList('inventoryGroups')" type="button"/>
	</td>
	<td width="10%" class="optionTitleBoldLeft" colspan="2">
		<select name="inventoryGroups" id="inventoryGroups" class="selectBox" size="5" multiple >
			<option value="xxblankxx">
				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			<option>
		<select>
		<input name="inventoryGroupList" id="inventoryGroupList" type="hidden" value=""/>
	</td>
</tr>

		<tr>
				<td class="optionTitleBoldRight" nowrap="nowrap">
					<fmt:message key="label.shipto" />:
				</td>
				<td class="optionTitleBoldLeft" nowrap="nowrap">
					<input name="shipToLocationName" id="shipToLocationName" type="text" maxlength="20" size="20" class="invGreyInputText" readonly="readonly" value="All"/>                                                           
                    <input name="shipToLocationId" id="shipToLocationId" type="hidden" value="All"/>
                    <input name="shipToCompanyId" id="shipToCompanyId" type="hidden" value="All"/>
                    <input class="lookupBtn" onmouseover="this.className='lookupBtn lookupBtnOver'" onmouseout="this.className='lookupBtn'" name="searchsupplierlike" value="..." onclick="getShipToId()" type="button"/>
                    <input class="smallBtns" onmouseover="this.className='smallBtns smallBtnsOver'" onmouseout="this.className='smallBtns'" name="clearsupplierlike" value="<fmt:message key="label.clear"/>" onclick="clearShipToId()" type="button"/>
                               				
				</td>
		</tr>
		<tr>
				<td class="optionTitleBoldRight" nowrap="nowrap">
					<fmt:message key="label.item" />:
				</td>
				<td class="optionTitleBoldLeft" nowrap="nowrap">
					<input name="itemDesc" id="itemDesc" type="text" maxlength="20" size="20" value="${param.itemDesc}" class="invGreyInputText" readonly="readonly"/>                                                           
                    <input name="itemId" id="itemId" type="hidden" value="${param.itemId}"/>                                    
                    <input class="lookupBtn" onmouseover="this.className='lookupBtn lookupBtnOver'" onmouseout="this.className='lookupBtn'" name="searchsupplierlike" value="..." onclick="getItem()" type="button"/>
                    <input class="smallBtns" onmouseover="this.className='smallBtns smallBtnsOver'" onmouseout="this.className='smallBtns'" name="clearsupplierlike" value="<fmt:message key="label.clear"/>" onclick="clearItem()" type="button"/>
				</td>
		</tr>

	<tr>
			<td class="optionTitleBoldRight" nowrap="nowrap">
					<fmt:message key="label.supplier" />:
			</td>
			<td class="optionTitleBoldLeft" nowrap="nowrap">
					<input name="supplierName" id="supplierName" type="text" maxlength="20" size="20" class="invGreyInputText" readonly="readonly" value="${param.supplierName}"/>                                                           
                    <input name="supplier" id="supplier" type="hidden" value="${param.supplier}"/>                                    
                    <input class="lookupBtn" onmouseover="this.className='lookupBtn lookupBtnOver'" onmouseout="this.className='lookupBtn'" name="searchsupplierlike" value="..." onclick="getSuppliers()" type="button"/>
                    <input class="smallBtns" onmouseover="this.className='smallBtns smallBtnsOver'" onmouseout="this.className='smallBtns'" name="clearsupplierlike" value="<fmt:message key="label.clear"/>" onclick="clearSuppliers()" type="button"/>                                				
			</td>
	</tr>
</table>
<c:if test="${requestScope['org.apache.struts.action.ERROR'] != null}">
<div class="spacerY">&nbsp;
<div id="searchErrorMessagesArea" class="errorMessages">
<html:errors/>
</div>
</div>
</c:if>
</fieldset>
&nbsp;
<table id="searchTable" width="100%" border="0" cellpadding="0" cellspacing="0" class="tableSearch">
	<tr>
			<td class="optionTitleBoldRight" nowrap="nowrap">
					<fmt:message key="label.newbuyer" />:
			</td>
			<td class="optionTitleBoldLeft" nowrap="nowrap">
					<input name="buyerName" id="buyerName" type="text" maxlength="20" size="20" class="invGreyInputText" readonly="readonly" value="${param.buyerName}"/>                                                           
                    <input name="buyer" id="buyer" type="hidden" value="${param.buyer}"/>                                    
                    <input class="lookupBtn" onmouseover="this.className='lookupBtn lookupBtnOver'" onmouseout="this.className='lookupBtn'" name="searchsupplierlike" value="..." onclick="getBuyer()" type="button"/>
                    <input class="smallBtns" onmouseover="this.className='smallBtns smallBtnsOver'" onmouseout="this.className='smallBtns'" name="clearsupplierlike" value="<fmt:message key="label.clear"/>" onclick="clearBuyer()" type="button"/> 
                    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;                              				
			</td>
	</tr>
	<tr>
			<td class="optionTitleBoldRight" nowrap="nowrap">
				<fmt:message key="label.carrier"/>:
			</td>
			<td class="optionTitleBoldLeft" nowrap="nowrap">
				<input  type="text" name="carrierName" id="carrierName" value="" size="20" class="invGreyInputText" readonly="readonly" />
      			<input name="carrier" id="carrier" type="hidden" value=""/>
      			<input type="button" class="lookupBtn" onmouseover="this.className='lookupBtn lookupBtnOver'" onmouseout="this.className='lookupBtn'" name="selectedCarrier" value="..." align="right" onClick="searchCarrier()"/>
        		<button class="smallBtns" onmouseover="this.className='smallBtns smallBtnsOver'" onMouseout="this.className='smallBtns'" name="None" value=""  OnClick="clearCarrier();return false;"><fmt:message key="label.clear"/> </button>
			</td>
	</tr>
	<tr>
			<td class="optionTitleBoldRight" nowrap="nowrap">
				<fmt:message key="label.criticalordercarrier"/>:
			</td>
			<td class="optionTitleBoldLeft" nowrap="nowrap">
				<input  type="text" name="criticalOrderCarrierName" id="criticalOrderCarrierName" value="" size="20" class="invGreyInputText" readonly="readonly" />
      			<input name="criticalOrderCarrier" id="criticalOrderCarrier" type="hidden" value=""/>
      			<input type="button" class="lookupBtn" onmouseover="this.className='lookupBtn lookupBtnOver'" onmouseout="this.className='lookupBtn'" name="selectedCriticalOrderCarrier" value="..." align="right" onClick="searchCriticalOrderCarrier()"/>
        		<button class="smallBtns" onmouseover="this.className='smallBtns smallBtnsOver'" onMouseout="this.className='smallBtns'" name="None" value=""  OnClick="clearCriticalOrderCarrier();return false;"><fmt:message key="label.clear"/> </button>
			</td>
	</tr>
	<tr>
			<td class="optionTitleBoldRight" nowrap="nowrap">
				<fmt:message key="label.suppliercontact"/>:
			</td>
			<td class="optionTitleBoldLeft" nowrap="nowrap">
				<input type="text" name="supplierContactName" id="supplierContactName" value="" size="20" class="invGreyInputText" readonly="readonly" />
      			<input name="supplierContactId" id="supplierContactId" type="hidden" value=""/>
      			<input type="button" class="lookupBtn" onmouseover="this.className='lookupBtn lookupBtnOver'" onmouseout="this.className='lookupBtn'" name="selectedCriticalOrderCarrier" value="..." align="right" onClick="getSupplierContact()"/>
        		<button class="smallBtns" onmouseover="this.className='smallBtns smallBtnsOver'" onMouseout="this.className='smallBtns'" name="None" value=""  OnClick="clearSupplierContact();return false;"><fmt:message key="label.clear"/> </button>
			</td>
	</tr>
	<tr>
			<td class="optionTitleBoldRight" nowrap="nowrap">
				<fmt:message key="label.remainingShelfLife"/>:
			</td>
			<td class="optionTitleBoldLeft" nowrap="nowrap">
				<input type="text" name="remainingShelfLifePercent" id="remainingShelfLifePercent" value="" size="5" class="inputBox" /> %
			</td>
	</tr>
	<tr>
			<td class="optionTitleBoldRight" nowrap="nowrap">
				<fmt:message key="label.pricingType"/>:
			</td>
			<td class="optionTitleBoldLeft" nowrap="nowrap">
				<select name="priceType" id="priceType" class="selectBox">
					<option value=""><fmt:message key="label.pleaseselect"/></option>
					<option value="CONTRACT"><fmt:message key="label.contract"/></option>
					<option value="QUOTE"><fmt:message key="label.quote"/></option>
				</select>
			</td>
	</tr>
<c:if test="${'Y' == param.supplyPathPermission}">
	<tr>
			<td class="optionTitleBoldRight" nowrap="nowrap">
				<fmt:message key="label.supplypath"/>:
			</td>
			<td class="optionTitleBoldLeft" nowrap="nowrap">
				<select name="supplyPath" id="supplyPath" class="selectBox">
					<option value=""><fmt:message key="label.pleaseselect"/></option>
					<option value="Purchaser">Purchaser</option>
					<option value="Wbuy">WBuy</option>
					<option value="Ibuy">IBuy</option>
					<option value="Xbuy">XBuy</option>
				</select>
			</td>
	</tr>
</c:if>
	<tr>
			<td class="optionTitleBoldRight" nowrap="nowrap">
				<fmt:message key="peiproject.label.startdate"/>:
			</td>
			<td class="optionTitleBoldLeft" nowrap="nowrap">
				<input class="inputBox pointer" readonly type="text" name="startDate" id="startDate" value="" onClick="return getCalendar(this);"
		        maxlength="10" size="9"/>
			</td>
	</tr>
	<tr>
			<td class="optionTitleBoldRight" nowrap="nowrap">
				<fmt:message key="label.unitprice"/>:
			</td>
			<td class="optionTitleBoldLeft" nowrap="nowrap">
				<c:set var="selectedCurrencyId" value='USD'/>
				<tcmis:isCNServer>
					<c:set var="selectedCurrencyId" value='CNY'/>
				</tcmis:isCNServer>  
				<select name="currencyId" id="currencyId" class="selectBox">
					<c:forEach var="cbean" items="${vvCurrencyBeanCollection}" varStatus="status">
					<option value="${cbean.currencyId}" <c:if test="${selectedCurrencyId == cbean.currencyId}">selected</c:if>>${cbean.currencyId}</option>
					</c:forEach>
				</select>&nbsp;
				<input type="text" name="unitPrice" id="unitPrice" value="" size="5" class="inputBox" />
			</td>
	</tr>
	<tr>
	<td width="15%" colspan="2" class="optionTitleBoldCenter">
		<input name="okBtn" type="button" class="inputBtns" value="<fmt:message key="label.ok(done)"/>" id="okBtn" onmouseover="this.className='inputBtns inputBtnsOver'" onMouseout="this.className='inputBtns'"
          		 onclick= "doBatchUpdateBuyer();"/>
        <input name="cancelBtn" type="button" class="inputBtns" value="<fmt:message key="label.cancel"/>" id="cancelBtn" onmouseover="this.className='inputBtns inputBtnsOver'" onMouseout="this.className='inputBtns'"
          		 onclick= "window.close();"/>
	</td>

	</tr>
</table>


<!--  result page section start -->
<div id="beanCollDiv" style="height:400px;display: none;" ></div>
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

<!-- Hidden element start -->
<div id="hiddenElements" style="display: none;">
<input type="hidden" name="uAction" id="uAction" value="${param.uAction}"/>
<input type="hidden" name="totalLines" id="totalLines" value="${dataCount}"/>
<input type="hidden" name="done" id="done" value="${done}"/>
<input name="startSearchTime" id="startSearchTime" type="hidden" value="${startSearchTime}"/>
<input name="endSearchTime" id="endSearchTime" type="hidden" value="${endSearchTime}"/>
</div>
<!-- Hidden elements end -->

</div> <!-- close of interface -->

</tcmis:form>

<!-- You can build your error messages below. Similar divs would have to be built to show any other messages in a inline pop-up.-->
<!-- Error Messages Begins -->
<div id="errorMessagesArea" class="errorMessages" style="display: none;overflow: auto;">

</div>
<c:if test="${applicationPermissionAll == 'Y'}">
 <script type="text/javascript">
 <!--
  showUpdateLinks = true;
 //-->
 </script>
</c:if>
</body>
</html:html>