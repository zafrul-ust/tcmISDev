<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
	"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html;charset=UTF-8" language="java"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="/WEB-INF/tcmis.tld" prefix="tcmis" %>

<html>
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
<script type="text/javascript" src="/js/common/report/movementofmaterial.js"></script>

<!-- Add any other javascript you need for the page here -->
<script type="text/javascript" src="/js/common/noSearchStandardGridmain.js"></script>

<!-- These are for the Grid-->
<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxcommon.js"></script>
<script type="text/javascript" src="/dhtmlxWindows/codebase/dhtmlxwindows.js"></script>
<script type="text/javascript" src="/dhtmlxLayout/codebase/dhtmlxlayout.js"></script>	
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
<%--This has the custom cells we built,
    hcal - the internationalized calendar which we use
    hlink - this is for any links you want tp provide in the grid, the URL/function to call
           can be attached based on a event (rowselect etc)
    hed -editable sinlge line text
    hcoro -select drop down
    hch -checkbox
--%>
<script type="text/javascript" src="/dhtmlxGrid/codebase/excells/dhtmlxgrid_excell_customized.js"></script>
<%--Custom sorting.
This custom sorting function implements case insensitive sorting.
--%>
<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxcommon_haas.js"></script>
    
<title>
<fmt:message key="label.movetoanotherworkarea"/>
</title>

<script language="JavaScript" type="text/javascript">
<!--
windowCloseOnEsc = true;

var messagesData = new Array();
messagesData={alert:"<fmt:message key="label.alert"/>",and:"<fmt:message key="label.and"/>",
submitOnlyOnce:"<fmt:message key="label.submitOnlyOnce"/>",
validvalues:"<fmt:message key="label.validvalues"/>",
qty:"<fmt:message key="label.quantity"/>",
missingpo:"<fmt:message key="label.missingpo"/>",
poline:"<fmt:message key="label.poline"/>",	
pleasechooseworkarea:"<fmt:message key="label.pleasechooseworkarea"/>",
missingchargenumber:"<fmt:message key="label.missingchargenumber"/>",
missingpercent:"<fmt:message key="label.missingpercent"/>",
invalidpercent:"<fmt:message key="label.invalidpercent"/>",
errors:"<fmt:message key="label.errors"/>",    
nodatafound:"<fmt:message key="main.nodatafound"/>"
};

var prRulesColl = new Array();
<c:forEach var="prRulesBean" items="${materialRequestData.prRulesColl}" varStatus="status">
	prRulesColl[${status.index}]={
		chargeType:'${prRulesBean.chargeType}',
		poRequired:'${prRulesBean.poRequired}',
	    prAccountRequired:'${prRulesBean.prAccountRequired}',
		chargeDisplay1:'${prRulesBean.chargeDisplay1}',
		chargeDisplay2:'${prRulesBean.chargeDisplay2}',
		chargeDisplay3:'${prRulesBean.chargeDisplay3}',
		chargeDisplay4:'${prRulesBean.chargeDisplay4}',
		chargeLabel1:'${prRulesBean.chargeLabel1}',
		chargeLabel2:'${prRulesBean.chargeLabel2}',
		chargeLabel3:'${prRulesBean.chargeLabel3}',
		chargeLabel4:'${prRulesBean.chargeLabel4}',
		chargeAllowNull1:'${prRulesBean.chargeAllowNull1}',
		chargeAllowNull2:'${prRulesBean.chargeAllowNull2}',
		chargeAllowNull3:'${prRulesBean.chargeAllowNull3}',
		chargeAllowNull4:'${prRulesBean.chargeAllowNull4}',
		unitPriceRequired:'${prRulesBean.unitPriceRequired}',
		poSeqRequired:'${prRulesBean.poSeqRequired}',
		customerRequisition:'${prRulesBean.customerRequisition}',
		chargeAllowEdit1:'${prRulesBean.chargeAllowEdit1}',
		chargeAllowEdit2:'${prRulesBean.chargeAllowEdit2}',
		chargeAllowEdit3:'${prRulesBean.chargeAllowEdit3}',
		chargeAllowEdit4:'${prRulesBean.chargeAllowEdit4}'
	};
</c:forEach>

var currencyColl = new Array();
<c:forEach var="tmpBean" items="${materialRequestData.currencyColl}" varStatus="status">
	currencyColl[${status.index}]={
		currencyId:'${tmpBean.currencyId}',
		currencyDescription:'${tmpBean.currencyDescription}',
	   	exchangeRate:'${tmpBean.exchangeRate}'
	};
</c:forEach>

var deliveryTypeColl = new Array();
<c:forEach var="tmpBean" items="${materialRequestData.deliveryTypeColl}" varStatus="status">
	deliveryTypeColl[${status.index}]={
		deliveryType:'${tmpBean.deliveryType}',
		description:'${tmpBean.description}'
	};
</c:forEach>

var dockDeliveryPointColl = new Array();
var facAccountSysPoForDirectColl = new Array();
var facAccountSysPoForIndirectColl = new Array();
var prAccountColl = new Array();
var chargeNumberForDirectColl = new Array();
var chargeNumberForIndirectColl = new Array();
<c:forEach var="lineItemBean" items="${materialRequestData.lineItemColl}" varStatus="status">
	dockDeliveryPointColl['${lineItemBean.lineItem}']={
		dockColl:[
			<c:forEach var="tmpBean" items="${lineItemBean.dockDeliveryPointColl}" varStatus="status2">
			 	<c:if test="${status2.index != 0 }">,</c:if>
				{
				dockLocationId:'${tmpBean.dockLocationId}',
				dockDesc:'${tmpBean.dockDesc}',
				deliveryPointColl:[
					 <c:forEach var="tmpBean2" items="${tmpBean.deliveryPointColl}" varStatus="status3">
						<c:if test="${status3.index != 0 }">,</c:if>
						{
							deliveryPoint: '<tcmis:jsReplace value="${tmpBean2.deliveryPoint}"/>',
							deliveryPointDesc: '<tcmis:jsReplace value="${tmpBean2.deliveryPointDesc}"/>'
						}
					 </c:forEach>
				]
				}
			</c:forEach>
		]
	};

	facAccountSysPoForDirectColl['${lineItemBean.lineItem}']=[
		<c:forEach var="tmpBean" items="${lineItemBean.facAccountSysPoForDirectColl}" varStatus="status2">
			<c:if test="${status2.index != 0 }">,</c:if>
			{
				poNumber:'${tmpBean.poNumber}'
			}
		</c:forEach>
	];

	facAccountSysPoForIndirectColl['${lineItemBean.lineItem}']=[
		<c:forEach var="tmpBean" items="${lineItemBean.facAccountSysPoForIndirectColl}" varStatus="status2">
			 <c:if test="${status2.index != 0 }">,</c:if>
			 {
			 	poNumber:'${tmpBean.poNumber}'
			 }
		</c:forEach>
	];

	prAccountColl['${lineItemBean.lineItem}']=[
		<c:forEach var="tmpBean" items="${lineItemBean.prAccountColl}" varStatus="status2">
		 	<c:if test="${status2.index != 0 }">,</c:if>
			{
				accountNumber:'${tmpBean.accountNumber}',
		   		accountNumber2:'${tmpBean.accountNumber2}',
				accountNumber3:'${tmpBean.accountNumber3}',
				accountNumber4:'${tmpBean.accountNumber4}',
				percentage:'${tmpBean.percentage}',
				chargeType:'${tmpBean.chargeType}'
			}
		</c:forEach>
	];

	chargeNumberForDirectColl['${lineItemBean.lineItem}']=[
		<c:forEach var="tmpBean" items="${lineItemBean.chargeNumberForDirectColl}" varStatus="status2">
		 	<c:if test="${status2.index != 0 }">,</c:if>
			{
				chargeNumber1:'${tmpBean.chargeNumber1}',
		   		chargeNumber2:'${tmpBean.chargeNumber2}',
				chargeNumber3:'${tmpBean.chargeNumber3}',
				chargeNumber4:'${tmpBean.chargeNumber4}',
				percent:'${tmpBean.percent}'
			}
		</c:forEach>
	];

	chargeNumberForIndirectColl['${lineItemBean.lineItem}']=[
		<c:forEach var="tmpBean" items="${lineItemBean.chargeNumberForIndirectColl}" varStatus="status2">
		 	<c:if test="${status2.index != 0 }">,</c:if>
			{
				chargeNumber1:'${tmpBean.chargeNumber1}',
		   		chargeNumber2:'${tmpBean.chargeNumber2}',
				chargeNumber3:'${tmpBean.chargeNumber3}',
				chargeNumber4:'${tmpBean.chargeNumber4}',
				percent:'${tmpBean.percent}'
			}
		</c:forEach>
	];

</c:forEach>


function closeWindowOrNot() {
	if('Y' == '${done}') {
		if('Y' == '${refreshOpener}') {
            opener.parent.submitForm();
        }
		window.close();
	}	
	else
		myOnLoad();
}

// -->
</script>
</head>

<body bgcolor="#ffffff"  onload="closeWindowOrNot();" onunload="if('workAreaChanged'!= $v('uAction'))opener.parent.closeTransitWin(); if($v('uAction').length == 0 && 'Y' != '${done}') doCancelMovement();" onresize="resizeFrames()">

<tcmis:form action="/movementofmaterial.do" onsubmit="return submitFrameOnlyOnce();">

<div class="interface" id="mainPage" style="">

<!-- Error Messages Begins -->

<div id="errorMessagesAreaBody" style="display:none;">
	<html:errors/>
    ${tcmISError}
    <c:forEach items="${tcmISErrors}" varStatus="status">
  	${status.current}<br/>
    </c:forEach>  
</div>
<!-- Error Messages Ends -->

<script type="text/javascript">
<!--
/*Check if there is any error messages to show and set the variable you use in javascript to true or false.*/
<c:choose>
   <c:when test="${empty tcmISError && empty tcmISErrors }">
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
  <br><br><br><fmt:message key="label.pleasewait"/>
  <br><br><br><img src="/images/rel_interstitial_loading.gif" align="middle">
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

<div class="boxhead">
<a href="#" onclick="processMovement();"><fmt:message key="button.submit"/></a> | 
<a href="#" onclick="cancelMovement();"><fmt:message key="label.cancel"/></a>
</div>
<table border="0">
<tr>
	<td width="2%" class="optionTitleBoldRight" nowrap><fmt:message key="label.part"/>:&nbsp;</span>
	</td>
    <td width="15%" class="optionTitle" nowrap>
    	${param.facPartNo}&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
    	<input type="hidden" name="facPartNo" id="facPartNo" value="${param.facPartNo}">
  	</td>
  	<td width="4%" class="optionTitleBoldRight" nowrap><fmt:message key="label.item"/>:&nbsp;</span>
	</td>
    <td width="15%" class="optionTitle" nowrap>
    	${param.itemId}&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
    	<input type="hidden" name="itemId" id="itemId" value="${param.itemId}">
  	</td>
  	<td width="4%" class="optionTitleBoldRight" nowrap><fmt:message key="label.receipt"/>:&nbsp;</span>
	</td>
    <td width="15%" class="optionTitle" nowrap>
    	${param.receiptId}
    	<input type="hidden" name="receiptId" id="receiptId" value="${param.receiptId}">
  	</td>
</tr>

<tr>
	<td width="2%" class="optionTitleBoldRight" nowrap><fmt:message key="label.fromworkarea"/>:&nbsp;</td>
	<td width="15%" class="optionTitle" colspan="5" >
		${param.applicationDesc}
		<input type="hidden" name="applicationDesc" id="applicationDesc" value="${param.applicationDesc}">
	</td>
</tr>

<tr>
	<td width="2%" class="optionTitleBoldRight" nowrap><fmt:message key="label.toworkarea"/>:&nbsp;</td>
	<td width="15%" class="optionTitle" colspan="5">
		<select id="toApplication" name="toApplication" class="selectBox" onchange="toApplicationChanged()">
<%--			<option value=""><fmt:message key="label.pleaseselect"/></option>  --%>
			<c:forEach var="bean" items="${workAreaColl}" varStatus="status">
  	    		<option value="${status.current.application}" <c:if test='${status.current.application == materialRequestData.lineItemColl[0].application }' >selected</c:if>>${status.current.applicationDesc}</option>
    		</c:forEach>
		</select>
	</td>
</tr>

<tr>
	<td width="2%" class="optionTitleBoldRight" nowrap><fmt:message key="label.quantity"/>:&nbsp;</td>
	<td width="15%" class="optionTitle" colspan="5">
		<input type="text" class="inputBox" name="qty" id="qty" value="" onchange="validateQty();" size="10" maxlength="10"/>
		<input type="hidden" name="deliveredQty" id="deliveredQty" value="${param.deliveredQty}"/>
	</td>
</tr>



			<tr>
				<td width="60%" valign="top" class="optionTitleBoldLeft"  colspan="4" >
					<table id="chargeNumberTable"  width="100%" border="0" cellpadding="0" cellspacing="0">
						<tr>
							<td width="100%" class="optionTitleBoldCenter" colspan="2">
								<span id="chargeTypeSpan" name="chargeTypeSpan">
									<input type="radio" name="chargeType" id="chargeTypeD" onClick="directedCheck();" value="d" class="radioBtns" checked="checked"/><fmt:message key="label.direct"/>
									&nbsp;&nbsp;
									<input type="radio" name="chargeType" id="chargeTypeI" onClick="inDirectedCheck();" value="i" class="radioBtns"/><fmt:message key="label.indirect"/>
								</span>
							</td>
						</tr>
						<tr>
							<td width="100%" class="optionTitleBoldLeft" colspan="2">
								<div id="chargeNumber2ColumnsDivId" style="display: none;" height="180px"></div>
								<div id="chargeNumber3ColumnsDivId" style="display: none;" height="180px"></div>
								<div id="chargeNumber4ColumnsDivId" style="display: none;" height="180px"></div>
								<div id="chargeNumber5ColumnsDivId" style="display: none;" height="180px"></div>
							</td>
						</tr>
					</table>
				</td>
				<td width="40%" valign="top" class="optionTitleBoldLeft" colspan="2">
					<table  width="100%" border="0" cellpadding="3" cellspacing="3">
						<tr>
                            <td>
								&nbsp;
							</td>
							<td >
								&nbsp;
							</td>
                        </tr>
						<tr>
                            <td id="dockLabel" width="10%" class="optionTitleBoldRight" nowrap>
								<fmt:message key="label.dock"/>:&nbsp;
							</td>
							<td id="dockSpan" width="40%" class="optionTitleBoldLeft" >
								<select name="dock" id="dock" class="selectBox" onchange="dockChanged()">
								</select>
							</td>
                        </tr>
						<tr>
                            <td id="delivertoLabel" width="10%" class="optionTitleBoldRight" nowrap>
								&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<fmt:message key="label.deliverto"/>:&nbsp;
							</td>
							<td id="delivertoSpan" width="40%" class="optionTitleBoldLeft">
								<select name="deliverTo" id="deliverTo" class="selectBox">
								</select>
							</td>
                        </tr>
                        <tr>
                            <td width="5%" class="optionTitleBoldLeft">
								&nbsp;
							</td>
                            <td width="5%" class="optionTitleBoldLeft">
								<hr id="lineSpan" width="100px"></hr>
							</td>
						</tr>

                   		<tr>
							<td width="10%" class="optionTitleBoldRight">&nbsp;
								<span id="poLabelSpan" name="poLabelSpan">
									<fmt:message key="label.po"/>:&nbsp;
								</span>
							</td>
							<td width="40%" class="optionTitleBoldLeft" nowrap>
								<span id="poComboSpan" name="poComboSpan">
									<select name="poCombo" id="poCombo" class="selectBox">
									</select>
								</span>
								<span id="poInputSpan" name="poInputSpan">
									<input name="poInput" id="poInput" type="text" class="inputBox" value="${materialRequestData.lineItemColl[0].poNumber}" size="10"/>
								</span>
							 </td>
						</tr>
						<tr>
							<td width="10%" class="optionTitleBoldRight">&nbsp;
								<span id="poLineSpan1" name="poLineSpan1">
									<fmt:message key="label.line"/>:&nbsp;
								</span>
							</td>
							<td width="40%" class="optionTitleBoldLeft" nowrap>
								<span id="poLineSpan2" name="poLineSpan2">
									<input name="poLineInput" id="poLineInput" type="text" class="inputBox" value="" size="10"/>
								</span>
							 </td>
						</tr>

					
					</table>
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


  </div>
  <div class="roundbottom">
    <div class="roundbottomright"> <img src="/images/rndBoxes/borderBL.gif" alt="" width="15" height="15" class="corner" style="display: none" /> </div>
  </div>
</div>
</div>
</td></tr>
</table>
</div>


<!-- Hidden element start -->
<div id="hiddenElements" style="display: none;">
<input type="hidden" name="lineItem" id="lineItem" value="1">
<input type="hidden" name="uAction" id="uAction" value="">
<input type="hidden" name="fromPrNumber" id="fromPrNumber" value="${param.fromPrNumber}">
<input type="hidden" name="fromLineItem" id="fromLineItem" value="${param.fromLineItem}">
<input type="hidden" name="materialId" id="materialId" value="${param.materialId}">
<input type="hidden" name="catalogId" id="catalogId" value="${param.catalogId}">
<input type="hidden" name="catalogCompanyId" id="catalogCompanyId" value="${param.catalogCompanyId}">
<input type="hidden" name="prNumber" id="prNumber" value="${materialRequestData.prNumber}">
<input name="companyId" id="companyId" type="hidden" value="${materialRequestData.companyId}">
<input name="facilityId" id="facilityId" type="hidden" value="${materialRequestData.facilityId}">
<input name="accountSysId" id="accountSysId" type="hidden" value="${materialRequestData.accountSysId}">
<input name="containsFillUpBulk" id="containsFillUpBulk" type="hidden" value="${materialRequestData.containsFillUpBulk}">
<input name="currencyId" id="currencyId" type="hidden" value="${materialRequestData.currencyId}">
<input name="shipToCompanyId" id="shipToCompanyId" type="hidden" value="${materialRequestData.shipToCompanyId}">
<input name="addressLine1" id="addressLine1" type="hidden" value="${materialRequestData.addressLine1}">
<input name="addressLine2" id="addressLine2" type="hidden" value="${materialRequestData.addressLine2}">
<input name="addressLine3" id="addressLine3" type="hidden" value="${materialRequestData.addressLine3}">
<input name="city" id="city" type="hidden" value="${materialRequestData.city}">
<input name="stateAbbrev" id="stateAbbrev" type="hidden" value="${materialRequestData.stateAbbrev}">
<input name="zip" id="zip" type="hidden" value="${materialRequestData.zip}">
<input name="countryAbbrev" id="countryAbbrev" type="hidden" value="${materialRequestData.countryAbbrev}">
<input name="allowOneTimeShipTo" id="allowOneTimeShipTo" type="hidden" value="${materialRequestData.allowOneTimeShipTo}">
<input name="updatable" id="updatable" type="hidden" value="${materialRequestData.updatable}">
<input name="oneTimeShipToChanged" id="oneTimeShipToChanged" type="hidden" value="N">
<input name="canEditMr" id="canEditMr" type="hidden" value="${materialRequestData.canEditMr}">
<input name="canEditMrQty" id="canEditMrQty" type="hidden" value="${materialRequestData.canEditMrQty}">
<input name="useApprovalType" id="useApprovalType" type="hidden" value="${materialRequestData.useApprovalType}">
<input name="viewType" id="viewType" type="hidden" value="${materialRequestData.viewType}">
<input name="prStatus" id="prStatus" type="hidden" value="${materialRequestData.prStatus}">
<input name="canRequestCancellation" id="canRequestCancellation" type="hidden" value="${materialRequestData.canRequestCancellation}">
<input name="poNumber" id="poNumber" type="hidden" value="${materialRequestData.lineItemColl[0].poNumber}">
<input name="releaseNumber" id="releaseNumber" type="hidden" value="${materialRequestData.lineItemColl[0].releaseNumber}">
<input name="directedChargeForDirect" id="directedChargeForDirect" type="hidden" value="${materialRequestData.lineItemColl[0].directedChargeForDirect}">
<input name="directedChargeForIndirect" id="directedChargeForIndirect" type="hidden" value="${materialRequestData.lineItemColl[0].directedChargeForIndirect}">
<input name="chargeNumbersFromDirectedChargeI" id="chargeNumbersFromDirectedChargeI" type="hidden" value="${materialRequestData.lineItemColl[0].chargeNumbersFromDirectedChargeI}">
<input name="chargeNumbersFromDirectedChargeD" id="chargeNumbersFromDirectedChargeD" type="hidden" value="${materialRequestData.lineItemColl[0].chargeNumbersFromDirectedChargeD}">
<input name="shipToLocationId" id="shipToLocationId" type="hidden" value="${materialRequestData.lineItemColl[0].shipToLocationId}">
<input name="deliveryPoint" id="deliveryPoint" type="hidden" value="${materialRequestData.lineItemColl[0].deliveryPoint}">
<input name="canEditLineChargeData" id="canEditLineChargeData" type="hidden" value="${materialRequestData.lineItemColl[0].canEditLineChargeData}">    
<input name="chargeNumbers" id="chargeNumbers" type="hidden" value="">
</div>
<!-- Hidden elements end -->

</div> <!-- close of interface -->

</tcmis:form>

<!-- You can build your error messages below. Similar divs would have to be built to show any other messages in a inline pop-up.-->
<!-- Error Messages Begins -->
<div id="errorMessagesArea" class="errorMessages" style="display: none;overflow: auto;">

</div>

</body>
</html>