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
var ${categoryId}MaterialGridConfig = {
		divName:'${categoryId}MaterialGrid',
		beanData:'${categoryId}MaterialGridData',
		beanGrid:'${categoryId}MaterialBean',
		config:'${categoryId}MaterialConfig',
		<c:if test="${category.itemData}">
		onRowSelect: mfrNotification.openMaterial, 
		</c:if>
		rowSpan:true,
		noSmartRender:false,
		submitDefault:false,
		selectChild: 1
};

<c:set var="snapshot" value="${categoryId ne mfroutofbusiness}"/>
<c:set var="readonly" value="${categoryId eq mfracquisition || categoryId eq mfroutofbusiness || categoryId eq rawmaterialchange || qcComplete eq 'Y'}"/>
<c:set var="showGrab" value="${categoryId ne mfroutofbusiness}"/>
<c:set var="showProductCode" value="${categoryId eq mfrproductcodechange}"/>
<c:set var="showLocaleData" value="${categoryId eq materialdiscontinuation || categoryId eq rebrandedproduct || categoryId eq mfrproductcodechange || categoryId eq specchange || categoryId eq formulationchange}"/>

<fmt:message var="newMsg" key="label.new"/>
<fmt:message var="extMsg" key="label.extension"/>
<c:set var="updateColumnName" value="${categoryId eq rebrandedproduct|| categoryId eq mfrproductcodechange || categoryId eq specchange?newMsg:extMsg}"/>

var ${categoryId}MaterialConfig = [
    {columnId:"permission", submit:false},
    {columnId:"grab"<c:if test="${showGrab}">, columnName:"<fmt:message key="label.grab"/>", type:"hchstatus", width:4, align:"center", snap:"snap", submit:true</c:if>},
    {columnId:"materialId", columnName:"<fmt:message key="label.materialid"/>", submit: true, width: 8<c:if test="${snapshot}">, snap:"snap"</c:if>},
    {columnId:"mfgId", columnName:'<fmt:message key="label.manufacturer"/>', width:8},
    {columnId:"materialDesc"<c:if test="${not readonly}">, columnName:'<fmt:message key="label.materialdesc"/>', attachHeader:"${updateColumnName}", width:16, tooltip:"Y", type:"hed", snap:"snap", size: 30, submit: true</c:if>},
    {columnId:"currentMaterialDesc", <c:if test="${not readonly}">columnName:'#cspan', attachHeader:"<fmt:message key="label.current"/>",</c:if><c:if test="${readonly}">columnName:'<fmt:message key="label.materialdesc"/>',</c:if> width:16, tooltip:"Y"},
    {columnId:"tradeName"<c:if test="${not readonly}">, columnName:'<fmt:message key="label.tradename"/>', attachHeader:"${updateColumnName}", width:16, tooltip:"Y", type:"hed", snap:"snap", size: 30, submit: true</c:if>},
    {columnId:"currentTradeName", <c:if test="${not readonly}">columnName:'#cspan', attachHeader:"<fmt:message key="label.current"/>",</c:if><c:if test="${readonly}">columnName:'<fmt:message key="label.tradename"/>',</c:if> width:16, tooltip:"Y"},
    {columnId:"productCode"<c:if test="${showProductCode && not readonly}">, columnName:'<fmt:message key="label.productcodes"/>', attachHeader:"<fmt:message key="label.new"/>", width:16, tooltip:"Y", type:"hed", snap:"snap", size: 30, submit: true</c:if>},
    {columnId:"currentProductCode"<c:if test="${showProductCode}"><c:if test="${not readonly}">, columnName:'#cspan', attachHeader:"<fmt:message key="label.current"/>"</c:if><c:if test="${readonly}">, columnName:'<fmt:message key="label.productcodes"/>'</c:if>, width:8, tooltip:"Y"</c:if>},
    {columnId:"localeCode"<c:if test="${showLocaleData}">, submit: true, width: 8</c:if>},
    {columnId:"locale", columnName:"<fmt:message key="label.locale"/>", width: 8},
    {columnId:"localeMaterialDescPermission", submit:false},
    {columnId:"localeMaterialDesc"<c:if test="${showLocaleData && not readonly}">, columnName:'<fmt:message key="label.materialdesc"/>', attachHeader:"${updateColumnName}", permission:true, width:16, tooltip:"Y", type:"hed", snap:"snap", size: 30, submit: true</c:if>},
    {columnId:"currentLocaleMaterialDesc"<c:if test="${showLocaleData}"><c:if test="${not readonly}">, columnName:'#cspan', attachHeader:"<fmt:message key="label.current"/>"</c:if><c:if test="${readonly}">, columnName:'<fmt:message key="label.materialdesc"/>'</c:if>, width:16, tooltip:"Y"</c:if>},
    {columnId:"localeTradeNamePermission", submit:false},
    {columnId:"localeTradeName"<c:if test="${showLocaleData && qcComplete eq 'N'}">, columnName:'<fmt:message key="label.tradename"/>', attachHeader:"${updateColumnName}", permission:true, width:16, tooltip:"Y", type:"hed", snap:"snap", size: 30, submit: true</c:if>},
    {columnId:"currentLocaleTradeName"<c:if test="${showLocaleData}"><c:if test="${not readonly}">, columnName:'#cspan', attachHeader:"<fmt:message key="label.current"/>"</c:if><c:if test="${readonly}">, columnName:'<fmt:message key="label.tradename"/>'</c:if>, width:16, tooltip:"Y"</c:if>},
    {columnId:"pageUploadCode"<c:if test="${snapshot}">, snap:"snap", submit: true</c:if>}
];
//-->
</script>

<!-- Result Frame Begins -->
<div id="${categoryId}MaterialResultDiv" style="display:none">
<!-- open contentArea -->
<div class="contentArea">
<table id="${categoryId}MaterialTable" width="100%" border="0" cellpadding="0" cellspacing="0">
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
						<c:if test="${not category.manufacturerData && qcComplete eq 'N'}">
						<span id="${categoryId}.deleteMaterials"><a href="#" onclick="mfrNotification.deleteMaterials();"><fmt:message key="label.removeselected"/></a></span>
						</c:if>
					  </div>
				      <div id="${categoryId}MaterialGrid" style="width:99%;height:300px" snap="snap"></div>
				      <c:if test="${not category.manufacturerData}">
				      	<%@ include file="/catalog/materialnotificationtable.jsp" %>
				      </c:if>
				      <table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableNoData" style="display:none" id="${categoryId}MaterialsNotFound">
						<tr>
							<td width="50%">
								<fmt:message key="label.nomaterialdatafound"/>
							</td>
						</tr>
					  </table>
					  <%-- result count and time --%>
					  <div id="${categoryId}MaterialFooter" class="messageBar"></div>
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

<c:if test="${category.itemData}">
<%@ include file="/catalog/itemaffectednotification.jsp" %>
</c:if>
