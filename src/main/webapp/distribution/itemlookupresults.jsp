<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
	"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>

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

<!-- For Calendar support -->
<script type="text/javascript" src="/js/calendar/newcalendar.js"></script>
<script type="text/javascript" src="/js/calendar/AnchorPosition.js"></script>
<script type="text/javascript" src="/js/calendar/PopupWindow.js"></script>

<!-- Add any other javascript you need for the page here -->
<script type="text/javascript" src="/js/distribution/itemlookupresults.js"></script>

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
    <fmt:message key="label.itemlookup"/>	
</title>

<script language="JavaScript" type="text/javascript">
<!--
//add all the javascript messages here, this for internationalization of client side javascript messages
var messagesData = new Array();
	       messagesData= {
			alert: "<fmt:message key="label.alert"/>",
			and: "<fmt:message key="label.and"/>", 
			catalogitemnotes:"<fmt:message key="label.catalogitemnotes"/>",
			componentdescription: "<fmt:message key="inventory.label.componentdescription"/>",
			country: "<fmt:message key="label.country"/>",
			grade: "<fmt:message key="label.grade"/>",
			image: "<fmt:message key="label.image"/>",
			item: "<fmt:message key="label.item"/>",
			itemInteger: "<fmt:message key="error.item.integer"/>",
			itemid: "<fmt:message key="label.itemid"/>",
			itemnotes:"<fmt:message key="itemnotes.title"/>",
			manufacturer: "<fmt:message key="label.manufacturer"/>",
			mfglit: "<fmt:message key="label.mfglit"/>",
			mfgpartno: "<fmt:message key="label.mfgpartno"/>",
			minutes: "<fmt:message key="label.minutes"/>",
			packaging: "<fmt:message key="inventory.label.packaging"/>",
			pleasewait: "<fmt:message key="label.pleasewaitmenu"/>",
			recordFound: "<fmt:message key="label.recordFound"/>",
			searchDuration: "<fmt:message key="label.searchDuration"/>",
			seconds: "<fmt:message key="label.seconds"/>",
			shelflife: "<fmt:message key="catalog.label.shelflife"/>",
			showcurrentsourcinginfo:"<fmt:message key="label.editsourcinginfo"/>",
			showevaluation: "<fmt:message key="label.showevaluation"/>",
			submitOnlyOnce: "<fmt:message key="label.submitOnlyOnce"/>",
			shipinfo: "<fmt:message key="label.shipinfo"/>",
			validvalues: "<fmt:message key="label.validvalues"/>"
};


		with(milonic=new menuname("itemActivitySubMenu")){
			top="offset=2";
			style=submenuStyle;
			margin=3;
			<c:if test="${not empty param.hub}">
				<c:if test="${not empty param.inventoryGroup}">
					aI("text=<fmt:message key="label.showitemactivityig"/>;url=javascript:showItemActivity('IG');");
				</c:if>
				aI("text=<fmt:message key="label.showitemactivityregion"/>;url=javascript:showItemActivity('REGION');");
			</c:if>
			aI("text=<fmt:message key="label.showitemactivityglobal"/>;url=javascript:showItemActivity('GLOBAL');");
		}

		with(milonic=new menuname("previousOrderSubMenu")){
			top="offset=2";
			style=submenuStyle;
			margin=3;
			<c:if test="${not empty param.hub}">
				<c:if test="${not empty param.inventoryGroup}">
					aI("text=<fmt:message key="label.showmrhistoryig"/>;url=javascript:showPreviousOrders('INVENTORY+GROUP');");
				</c:if>
				aI("text=<fmt:message key="label.showmrhistoryregion"/>;url=javascript:showPreviousOrders('REGION');");
			</c:if>
			aI("text=<fmt:message key="label.showmrhistoryglobal"/>;url=javascript:showPreviousOrders('GLOBAL');");
		}

		with(milonic=new menuname("quotationHistorySubMenu")){
			top="offset=2";
			style=submenuStyle;
			margin=3;
			<c:if test="${not empty param.hub}">
				<c:if test="${not empty param.inventoryGroup}">
					aI("text=<fmt:message key="label.showquotationhistoryig"/>;url=javascript:showQuotationHistory('INVENTORY+GROUP');");
				</c:if>
				aI("text=<fmt:message key="label.showquotationhistoryregion"/>;url=javascript:showQuotationHistory('REGION');");
			</c:if>
			aI("text=<fmt:message key="label.showquotationhistoryglobal"/>;url=javascript:showQuotationHistory('GLOBAL');");
		}

		with(milonic=new menuname("poHistorySubMenu")){
			top="offset=2";
			style=submenuStyle;
			margin=3;
			<c:if test="${not empty param.hub}">
				<c:if test="${not empty param.inventoryGroup}">
					aI("text=<fmt:message key="label.showpohistoryig"/>;url=javascript:showPoHistory('INVENTORY+GROUP');");
				</c:if>
				aI("text=<fmt:message key="label.showpohistoryregion"/>;url=javascript:showPoHistory('REGION');");
			</c:if>
			aI("text=<fmt:message key="label.showpohistoryglobal"/>;url=javascript:showPoHistory('GLOBAL');");
		}

		with(milonic=new menuname("notesSubMenu")){
			top="offset=2";
			style=submenuStyle;
			margin=3;
			aI("text=<fmt:message key="itemnotes.title"/>;url=javascript:showItemNotes();");
			aI("text=<fmt:message key="label.catalogitemnotes"/>;url=javascript:showCatalogItemNotes();");
			aI("text=<fmt:message key="supplieritemnotes.title"/>;url=javascript:showSupplierItemNotes();");
		}

		with(milonic=new menuname("rightClickMenu")){
			top="offset=2";
			style = contextWideStyle;
			margin=3;
			aI("showmenu=itemActivitySubMenu;text=<fmt:message key="label.showitemactivity"/>;image=");
			aI("showmenu=previousOrderSubMenu;text=<fmt:message key="label.showmrhistory"/>;image=");
			aI("showmenu=quotationHistorySubMenu;text=<fmt:message key="label.showquotationhistory"/>;image=");
			aI("showmenu=poHistorySubMenu;text=<fmt:message key="showpohistory.title"/>;image=");
			aI("showmenu=notesSubMenu;text=<fmt:message key="label.notes"/>;image=");
			aI("text=<fmt:message key="label.editsourcinginfo"/>;url=javascript:showSourcingInfo();");
			aI("text=<fmt:message key="label.show"/> <fmt:message key="minMaxLevels"/>;url=javascript:showMinMax();");
			aI("text=<fmt:message key="label.shipinfo"/>;url=javascript:showDOT();");
		}
	
	    drawMenus();

var config = [ 
	       		{columnId: "itemId", columnName:messagesData.item},
	       		{columnId: "materialDesc", columnName:messagesData.componentdescription, width:30},
			{columnId: "packaging", columnName:messagesData.packaging, width:20},
			{columnId: "grade", columnName:messagesData.grade},
			{columnId: "mfgDesc", columnName:messagesData.manufacturer, width:30},
			{columnId: "country", columnName:messagesData.country},
			{columnId: "mfgPartNo", columnName:messagesData.mfgpartno},
			{columnId: "shelfLifeDisplay", columnName:messagesData.shelflife, width:20},
			{columnId: "msdsOnLine"},
			{columnId: "materialId"},
			{columnId: "itemDesc"},
			{columnId: "engEval"}
	       ];
	       
	       var gridConfig = {
		       divName: 'itemCatalogScreenSearchBean', // the div id to contain the grid.
		       beanData: 'jsonMainData', // the data variable name for jsonparse, as in grid.parse( beanData ,"json" )
		       beanGrid: 'mygrid', // the grid's name, as in beanGrid.attachEvent...
		       config:config, // the column config var name, as in var config = { [ columnId:..,columnName...
		       rowSpan:true, // this page has rowSpan > 1 or not.
		       submitDefault:false, // the fields in grid are defaulted to be submitted or not.
		       //onRowSelect:selectRow, // the onRowSelect event function to be attached, as in beanGrid.attachEvent("onRowSelect",selectRow)
		       noSmartRender:false,
		       onRightClick:rightClickRow // the onRightClick event function to be attached, as in beanGrid.attachEvent("onRightClick",selectRow)
	       };


//-->
</script>

</head>

<body bgcolor="#ffffff" onload="resultOnLoadWithGrid(gridConfig);setCurrency()">
<tcmis:form action="/itemlookupresults.do" onsubmit="return submitFrameOnlyOnce();">
<div id="errorMessagesAreaBody" style="display:none;">
    <html:errors/>
    ${tcmISError}
    <c:forEach items="${tcmISErrors}" varStatus="status">
  	${status.current}<br/>
    </c:forEach>        
</div>

<script type="text/javascript">
   <c:choose>
    <c:when test="${requestScope['org.apache.struts.action.ERROR'] == null && empty tcmISError && empty tcmISErrors }">
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

<div id="itemCatalogScreenSearchBean" style="width:100%;height:400px;" style="display: none;"></div>

<c:if test="${itemCatalogBeanCollection != null}">
<script type="text/javascript">

<!--
<c:set var= "parCount" value= "0" />
<c:set var= "dataCount" value= '${0}' />
<c:set var= "prePar" value= "" />
<c:if test="${!empty itemCatalogBeanCollection}" >

var jsonMainData = {
				rows: [
					<c:forEach var= "p" items= "${itemCatalogBeanCollection}" varStatus= "status" >
						<c:if test= "${status.index != 0 }" >, </c:if>
						<c:set var= "curPar" value= "${status.current.itemId}" />
						<c:if test= "${!(curPar eq prePar)}" >
							<c:set var= "parCount" value= "${parCount+1}" />
							<c:if test= "${ parCount % 2 == 0 }" ><c:set var= "partClass" value= "odd_haas" /></c:if> 
							<c:if test= "${ parCount % 2 != 0 }" ><c:set var= "partClass" value= "ev_haas" /></c:if>
						</c:if> 
						{ id: ${status.index + 1},
						  "class": "${partClass}", 
						  data: ['${p.itemId}',
						  	 '<tcmis:jsReplace value='${p.materialDesc}' processMultiLines='true'/>',
						  	 '<tcmis:jsReplace value='${p.packaging}' processMultiLines='true'/>',
						  	 '<tcmis:jsReplace value='${p.grade}' processMultiLines='true'/>',
						  	 '<tcmis:jsReplace value='${p.mfgDesc}' processMultiLines='true'/>',
							 '<tcmis:jsReplace value='${p.countryName}' processMultiLines='true'/>',
							 '<tcmis:jsReplace value='${p.mfgPartNo}' processMultiLines='true'/>',
							 '<tcmis:jsReplace value='${p.shelfLife}' processMultiLines='true'/>', 
							 <%--hidden here.--%>
							'${p.msdsOnLine}', 
							'${p.materialId}', 
							'<tcmis:jsReplace value='${p.itemDesc}' processMultiLines='true'/>', 
							'${p.engEval}']
						}
						<c:set var= "numberOfKit" value= "${status.current.numberOfKitsPerItem}" />
						<c:if test= "${!(numberOfKit eq -1)}" >
							<c:set var= "dataCount" value= '${dataCount+1}' />
						</c:if>
						<c:set var= "prePar" value="${status.current.itemId}" />
					</c:forEach>]
				};
</c:if>

<c:set var="itemCount" value='0' />
		<c:forEach var="p" items="${itemCatalogBeanCollection}" varStatus="status">
			lineMap [${status.index}]= ${p.numberOfKitsPerItem};
			<c:if test="${p.numberOfKitsPerItem > 0}"><c:set var="itemCount" value='${itemCount + 1}' /></c:if>
			lineMap3 [${status.index}]= ${itemCount % 2};
		</c:forEach>
		var altAccountSysId = new Array(<c:forEach var= "prRulesViewBean" items= "${prRulesViewCollection}" varStatus= "status2" > <c:if test= "${status2.index > 0}" >, </c:if> {
			id: '<tcmis:jsReplace value="${status2.current.accountSysId}"/>', label: '<tcmis:jsReplace value="${status2.current.accountSysLabel}"/>'
		}
		</c:forEach>);

showUpdateLinks = false;
//-->   
</script>

<!-- If the collection is empty say no data found -->
<c:if test="${empty itemCatalogBeanCollection}">
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
	<input name="totalLines" id="totalLines" value="${dataCount}" type="hidden" />
	<input name="uAction" id="uAction" value="" type="hidden" />
	<input name="hub" id="hub" value="${param.hub}" type="hidden" />
	<input name="opsEntityId" id="opsEntityId" value="${param.opsEntityId}" type="hidden" />
	<input name="inventoryGroup" id="inventoryGroup" value="${param.inventoryGroup}" type="hidden" />
	<input name="minHeight" id="minHeight" type="hidden" value="100"/>	 
</div>
<!-- Hidden elements end -->

</div>
<!-- close of backGroundContent -->
</div>
<!-- close of interface -->

</tcmis:form>
</body>
</html:html>