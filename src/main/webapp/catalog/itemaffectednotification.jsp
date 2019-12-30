<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<%@ taglib uri="/WEB-INF/struts-nested.tld" prefix="nested" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/tcmis.tld" prefix="tcmis" %>

<script type="text/javascript">
<!--
var ${categoryId}ItemGridConfig = {
		divName:'${categoryId}ItemGrid',
		beanData:'${categoryId}ItemGridData',
		beanGrid:'${categoryId}ItemBean',
		config:'${categoryId}ItemConfig',
		rowSpan:true,
		submitDefault:false,
		noSmartRender:false
};

<c:set var="snapshot" value="${categoryId ne mfroutofbusiness}"/>
<c:set var="readonly" value="${categoryId eq productfyinotice || categoryId eq mfroutofbusiness || qcComplete eq 'Y'}"/>
<c:set var="showGrab" value="${categoryId eq itemdiscontinuation || categoryId eq productfyinotice || categoryId eq shelflifestoragetempupdate}"/>
<c:set var="showPackaging" value="${false}"/>
<c:set var="showRevisionComments" value="${categoryId eq itemdiscontinuation || categoryId eq materialdiscontinuation || categoryId eq specchange || categoryId eq formulationchange || categoryId eq shelflifestoragetempupdate}"/>
<c:set var="showStockingType" value="${categoryId eq formulationchange || categoryId eq materialdiscontinuation || categoryId eq itemdiscontinuation}"/>
<c:set var="showItemType" value="${categoryId eq formulationchange || categoryId eq materialdiscontinuation || categoryId eq itemdiscontinuation}"/>
<c:set var="showPartData" value="${categoryId ne productfyinotice && categoryId ne mfroutofbusiness}"/>
<c:set var="showMfgPartNo" value="${showPartData && (categoryId eq itemdiscontinuation || categoryId eq materialdiscontinuation || categoryId eq mfrproductcodechange || categoryId eq specchange || categoryId eq formulationchange)}"/>
<c:set var="showGrade" value="${showPartData && categoryId eq specchange}"/>
<c:set var="showSlst" value="${showPartData && categoryId eq shelflifestoragetempupdate}"/>
<c:set var="showLocaleData" value="${showPartData}"/>
<c:set var="showLocaleMfgPartNo" value="${showLocaleData && showMfgPartNo}"/>
<c:set var="showLocaleGrade" value="${showLocaleData && showGrade}"/>
<c:set var="showLocaleSlst" value="${showLocaleData && showSlst}"/>

<fmt:message var="newMsg" key="label.new"/>
<fmt:message var="extMsg" key="label.extension"/>
<c:set var="updateColumnName" value="${categoryId eq rebrandedproduct || categoryId eq specchange || categoryId eq mfrproductcodechange?newMsg:extMsg}"/>

var tempUnits = [
    {value: '', text: ''},
	{value: 'C', text: 'C'},
	{value: 'F', text: 'F'}
];

var localeTempUnits = tempUnits;

var shelfLifeBasis = [
    {value: '', text:"N/A"}
<c:if test="${not empty shelfLifeBasisColl}">
	<c:forEach var="slBasis" items="${shelfLifeBasisColl}" varStatus="slBasisStatus">
	,{value: '${slBasis.shelfLifeBasis}', text: '<fmt:message key="${slBasis.jspLabel}"/>'}
	</c:forEach>
</c:if>
];

var ${categoryId}ItemConfig = [
    {columnId:"permission", submit:false},
    {columnId:"grab"<c:if test="${showGrab}">, columnName:"<fmt:message key="label.grab"/>", type:"hchstatus", width:4, align:"center", snap:"snap", submit: true</c:if>},
    {columnId:"itemId", columnName:"<fmt:message key="label.itemid"/>", submit: true, width: 8<c:if test="${snapshot}">, snap:"snap"</c:if>},
    {columnId:"itemDesc", columnName:'<fmt:message key="label.description"/>', width:16, tooltip:"Y"},
    {columnId:"packaging"<c:if test="${showPackaging}">, columnName:'<fmt:message key="label.packaging"/>', width:16, tooltip:"Y"</c:if>},
    {columnId:"revisionComments"<c:if test="${showRevisionComments && not readonly}">, columnName:'<fmt:message key="label.revisioncomments"/>', attachHeader:"${extMsg}", type:"hed", width:20, snap:"snap", size:50, submit: true</c:if>},
    {columnId:"currentRevisionComments"<c:if test="${showRevisionComments}"><c:if test="${not readonly}">, columnName:'#cspan', attachHeader:"<fmt:message key="label.current"/>"</c:if><c:if test="${readonly}">, columnName:'<fmt:message key="label.revisioncomments"/>'</c:if>, width:20, tooltip: 'Y'</c:if>},
    {columnId:"stockingType"<c:if test="${showStockingType && not readonly}">, columnName:'<fmt:message key="label.stockingtype"/>', attachHeader:"<fmt:message key="label.new"/>", width:10, submit:true, snap:"snap"</c:if>},
    {columnId:"currentStockingType"<c:if test="${showStockingType}"><c:if test="${not readonly}">, columnName:'#cspan', attachHeader:"<fmt:message key="label.current"/>"</c:if><c:if test="${readonly}">, columnName:'<fmt:message key="label.stockingtype"/>'</c:if>, width:10, tooltip: 'Y'</c:if>},
    {columnId:"itemType"<c:if test="${showItemType && not readonly}">, columnName:'<fmt:message key="label.itemtype"/>', attachHeader:"<fmt:message key="label.new"/>", width:10, submit:true, snap:"snap"</c:if>},
    {columnId:"currentItemType"<c:if test="${showItemType}"><c:if test="${not readonly}">, columnName:'#cspan', attachHeader:"<fmt:message key="label.current"/>"</c:if><c:if test="${readonly}">, columnName:'<fmt:message key="label.itemtype"/>'</c:if>, width:10, tooltip: 'Y'</c:if>},
    {columnId:"partId", snap:"snap", submit: true},
    {columnId:"materialId", columnName:'<fmt:message key="label.materialid"/>', width:8, snap:"snap", submit: true},
    {columnId:"mfgId", columnName:'<fmt:message key="label.manufacturer"/>', width:8},
    {columnId:"pkgStyle", columnName:'<fmt:message key="label.packaging"/>', width:16, tooltip:"Y"},
    {columnId:"mfgPartNo"<c:if test="${showMfgPartNo && not readonly}">, columnName:"<fmt:message key="label.mfgpartno"/>", attachHeader:"${updateColumnName}", width:16, tooltip:"Y", type:"hed", snap:"snap", size:20, submit: true</c:if>},
	{columnId:"currentMfgPartNo"<c:if test="${showMfgPartNo}"><c:if test="${not readonly}">, columnName:"#cspan", attachHeader:"<fmt:message key="label.current"/>"</c:if><c:if test="${readonly}">, columnName:"<fmt:message key="label.mfgpartno"/>"</c:if>, width:16, tooltip:"Y"</c:if>},
	{columnId:"grade"<c:if test="${showGrade && not readonly}">, columnName:"<fmt:message key="label.grade"/>", attachHeader:"<fmt:message key="label.new"/>", width:16, tooltip:"Y", type:"hed", snap:"snap", size: 30, submit: true</c:if>},
	{columnId:"currentGrade"<c:if test="${showGrade}"><c:if test="${not readonly}">, columnName:"#cspan", attachHeader:"<fmt:message key="label.current"/>"</c:if><c:if test="${readonly}">, columnName:"<fmt:message key="label.grade"/>"</c:if>, width:16, tooltip:"Y"</c:if>},
	{columnId:"minTemp"<c:if test="${showSlst && not readonly}">, columnName:"<fmt:message key="label.minstoragetemp"/>", attachHeader:"<fmt:message key="label.new"/>", width:6, tooltip:"Y", type:"hed", snap:"snap", submit: true</c:if>},
	{columnId:"currentMinTemp"<c:if test="${showSlst}"><c:if test="${not readonly}">, columnName:"#cspan", attachHeader:"<fmt:message key="label.current"/>"</c:if><c:if test="${readonly}">, columnName:"<fmt:message key="label.minstoragetemp"/>"</c:if>, width:6, tooltip:"Y"</c:if>},
	{columnId:"maxTemp"<c:if test="${showSlst && not readonly}">, columnName:"<fmt:message key="label.maxstoragetemp"/>", attachHeader:"<fmt:message key="label.new"/>", width:6, tooltip:"Y", type:"hed", snap:"snap", submit: true</c:if>},
	{columnId:"currentMaxTemp"<c:if test="${showSlst}"><c:if test="${not readonly}">, columnName:"#cspan", attachHeader:"<fmt:message key="label.current"/>"</c:if><c:if test="${readonly}">, columnName:"<fmt:message key="label.maxstoragetemp"/>"</c:if>, width:6, tooltip:"Y"</c:if>},
	{columnId:"tempUnits"<c:if test="${showSlst && not readonly}">, columnName:"<fmt:message key="label.units"/>", attachHeader:"<fmt:message key="label.new"/>", width:6, tooltip:"Y", type:"hcoro", snap:"snap", submit: true</c:if>},
	{columnId:"currentTempUnits"<c:if test="${showSlst}"><c:if test="${not readonly}">, columnName:"#cspan", attachHeader:"<fmt:message key="label.current"/>"</c:if><c:if test="${readonly}">, columnName:"<fmt:message key="label.units"/>"</c:if>, width:6, tooltip:"Y"</c:if>},
	{columnId:"shelfLifeDays"<c:if test="${showSlst && not readonly}">, columnName:"<fmt:message key="label.shelflife(days)"/>", attachHeader:"<fmt:message key="label.new"/>", width:6, tooltip:"Y", type:"hed", snap:"snap", submit: true</c:if>},
	{columnId:"currentShelfLifeDays"<c:if test="${showSlst}"><c:if test="${not readonly}">, columnName:"#cspan", attachHeader:"<fmt:message key="label.current"/>"</c:if><c:if test="${readonly}">, columnName:"<fmt:message key="label.shelflife(days)"/>"</c:if>, width:6, tooltip:"Y"</c:if>},
	{columnId:"shelfLifeBasis"<c:if test="${showSlst && not readonly}">, columnName:"<fmt:message key="label.basis"/>", attachHeader:"<fmt:message key="label.new"/>", width:16, tooltip:"Y", type:"hcoro", snap:"snap", submit: true</c:if>},
	{columnId:"currentShelfLifeBasis"<c:if test="${showSlst}"><c:if test="${not readonly}">, columnName:"#cspan", attachHeader:"<fmt:message key="label.current"/>"</c:if><c:if test="${readonly}">, columnName:"<fmt:message key="label.basis"/>"</c:if>, width:16, tooltip:"Y"</c:if>},
    {columnId:"localeCode", snap:"snap", submit: true, width: 8},
    {columnId:"locale", columnName:"<fmt:message key="label.locale"/>", width: 8},
    {columnId:"localeMfgPartNoPermission", submit:false},
   	{columnId:"localeMfgPartNo"<c:if test="${showLocaleMfgPartNo && not readonly}">, columnName:"<fmt:message key="label.mfgpartno"/>", attachHeader:"${updateColumnName}", permission:true, width:16, tooltip:"Y", type:"hed", snap:"snap", size: 20, submit: true</c:if>},
   	{columnId:"currentLocaleMfgPartNo"<c:if test="${showLocaleMfgPartNo}"><c:if test="${not readonly}">, columnName:"#cspan", attachHeader:"<fmt:message key="label.current"/>"</c:if><c:if test="${readonly}">, columnName:"<fmt:message key="label.mfgpartno"/>"</c:if>, width:16, tooltip:"Y"</c:if>},
   	{columnId:"localeGradePermission", submit:false},
   	{columnId:"localeGrade"<c:if test="${showLocaleGrade && not readonly}">, columnName:"<fmt:message key="label.grade"/>", attachHeader:"<fmt:message key="label.new"/>", permission:true, width:16, tooltip:"Y", type:"hed", snap:"snap", size: 30, submit: true</c:if>},
	{columnId:"currentLocaleGrade"<c:if test="${showLocaleGrade}"><c:if test="${not readonly}">, columnName:"#cspan", attachHeader:"<fmt:message key="label.current"/>"</c:if><c:if test="${readonly}">, columnName:"<fmt:message key="label.grade"/>"</c:if>, width:16, tooltip:"Y"</c:if>},
	{columnId:"localeMinTempPermission", submit:false},
   	{columnId:"localeMinTemp"<c:if test="${showLocaleSlst && not readonly}">, columnName:"<fmt:message key="label.minstoragetemp"/>", attachHeader:"<fmt:message key="label.new"/>", permission:true, width:6, tooltip:"Y", type:"hed", snap:"snap", submit: true</c:if>},
	{columnId:"currentLocaleMinTemp"<c:if test="${showLocaleSlst}"><c:if test="${not readonly}">, columnName:"#cspan", attachHeader:"<fmt:message key="label.current"/>"</c:if><c:if test="${readonly}">, columnName:"<fmt:message key="label.minstoragetemp"/>"</c:if>, width:6, tooltip:"Y"</c:if>},
	{columnId:"localeMaxTempPermission", submit:false},
	{columnId:"localeMaxTemp"<c:if test="${showLocaleSlst && not readonly}">, columnName:"<fmt:message key="label.maxstoragetemp"/>", attachHeader:"<fmt:message key="label.new"/>", permission:true, width:6, tooltip:"Y", type:"hed", snap:"snap", submit: true</c:if>},
	{columnId:"currentLocaleMaxTemp"<c:if test="${showLocaleSlst}"><c:if test="${not readonly}">, columnName:"#cspan", attachHeader:"<fmt:message key="label.current"/>"</c:if><c:if test="${readonly}">, columnName:"<fmt:message key="label.maxstoragetemp"/>"</c:if>, width:6, tooltip:"Y"</c:if>},
	{columnId:"localeTempUnitsPermission", submit:false},
	{columnId:"localeTempUnits"<c:if test="${showLocaleSlst && not readonly}">, columnName:"<fmt:message key="label.units"/>", attachHeader:"<fmt:message key="label.new"/>", permission:true, width:6, tooltip:"Y", type:"hcoro", snap:"snap", submit: true</c:if>},
	{columnId:"currentLocaleTempUnits"<c:if test="${showLocaleSlst}"><c:if test="${not readonly}">, columnName:"#cspan", attachHeader:"<fmt:message key="label.current"/>"</c:if><c:if test="${readonly}">, columnName:"<fmt:message key="label.units"/>"</c:if>, width:6, tooltip:"Y"</c:if>}
];

//-->
</script>

<!-- Result Frame Begins -->
<div id="${categoryId}ItemResultDiv" style="display:none">
<!-- open contentArea -->
<div class="contentArea">
<table id="${categoryId}ItemTable" width="100%" border="0" cellpadding="0" cellspacing="0">
	<tr>
		<td>
			<div class="roundcont contentContainer">
				<div class="roundright">
					<div class="roundtop">
						<div class="roundtopright">
							<img src="/images/rndBoxes/borderTL_filter.gif" alt="" width="15" height="10" class="corner_filter" style="display: none" />
						</div>
					</div>
					<div class="roundContent">
					  <%-- boxhead Begins --%>
					  <div class="boxhead">
						<%-- Place all update links in divs that can be turned on or off depending on the search criteria that is selected
							Do not remove mainUpdateLinks div, even if you don't have any links. You can remove content inside the div but not the div itself. --%>
						<c:if test="${not category.materialData && qcComplete eq 'N'}">
							<span id="${categoryId}.deleteItems"><a href="#" onclick="mfrNotification.deleteItems();"><fmt:message key="label.removeselected"/></a></span>
						</c:if>
					  </div>
					  <div id="${categoryId}ItemGrid" style="width:92%;height:300px" snap="snap"></div>
					  <%@ include file="/catalog/itemnotificationtable.jsp" %>
					  <table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableNoData" style="display:none" id="${categoryId}ItemsNotFound">
						<tr>
							<td width="50%">
								<fmt:message key="label.noitemdatafound"/>
							</td>
						</tr>
					  </table>
					  <%-- result count and time --%>
					  <div id="${categoryId}ItemFooter" class="messageBar"></div>
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
</div>
<!-- close of contentArea -->
</div>
<!-- Result Frame Ends -->
<div id="${categoryId}ItemTransitPage" style="display: none;" class="optionTitleBoldCenter">
	<br/><br/><br/>
	<fmt:message key="label.pleasewait" />
	<br/><br/><br/>
	<img src="/images/rel_interstitial_loading.gif" align="middle">
</div>
