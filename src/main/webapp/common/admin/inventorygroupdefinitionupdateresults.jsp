<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
	"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html;charset=UTF-8" 		language="java"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" 	prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" 	prefix="fmt"%>

<%@ taglib uri="/WEB-INF/struts-nested.tld" 		prefix="nested"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" 			prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" 			prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" 			prefix="logic"%>
<%@ taglib uri="/WEB-INF/tcmis.tld" 				prefix="tcmis"%>

<html:html lang="true">
	<head>
		<meta http-equiv="content-type" content="text/html; charset=UTF-8"/>
		<meta http-equiv="expires" content="-1"/>
		<link rel="shortcut icon" href="https://www.tcmis.com/images/buttons/tcmIS.ico"></link>
	
		<%@ include file="/common/locale.jsp"%> 
		<!-- Use this tag to get the correct CSS class.
		This looks at what the user's preffered font size and which application he is viewing to set the correct CSS. -->
		<tcmis:gridFontSizeCss /> 
		<%-- Add any other stylesheets you need for the page here --%>
	
		<script type="text/javascript" src="/js/common/formchek.js"></script> 
		<script type="text/javascript" src="/js/common/commonutil.js"></script> 
		<%--NEW - grid resize--%>
		<script type="text/javascript" src="/js/common/grid/resultiframegridresize.js"></script>
		<!-- This handles which key press events are disabled on this page -->
		<script type="text/javascript" src="/js/common/disableKeys.js"></script>
	
		<!-- This handles the menu style and what happens to the right click on the whole page -->
		<script type="text/javascript" src="/js/menu/milonic_src.js"></script> 
		<script type="text/javascript" src="/js/menu/mmenudom.js"></script> 
		<script type="text/javascript" src="/js/menu/mainmenudata.js"></script> 
		<script type="text/javascript" src="/js/menu/contextmenu.js"></script> 
		<%@ include file="/common/rightclickmenudata.jsp"%> 
		
		<!-- For Calendar support -->
		<script type="text/javascript" src="/js/calendar/newcalendar.js"></script>
		<script type="text/javascript" src="/js/calendar/AnchorPosition.js"></script>
		<script type="text/javascript" src="/js/calendar/PopupWindow.js"></script>
		
		<!-- Add any other javascript you need for the page here --> 
		<script type="text/javascript" src="/js/common/admin/inventorygroupdefinitionupdateresults.js"></script> 

		<!-- These are for the Grid-->
		<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxcommon.js"></script>
		<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxgrid.js"></script>
		<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxgridcell.js"></script>
		<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_form.js"></script>
		<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_srnd.js"></script>
		<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_json.js"></script>    
		<%--Uncomment below if you are providing header menu to switch columns on/off--%>
		<!--<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_hmenu.js"></script>-->
		<%--Uncomment the below if your grid has rwospans >1--%>
		<!--
		<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_rowspan.js"></script>
		<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/rowspan_cell_patch.js"></script>
		-->
		<%--This has the custom cells we built, hcal - the internationalized calendar which we use
		    hlink- this is for any links you want tp provide in the grid, the URL/function to call
		    can be attached based on a even (rowselect etc)
		--%>
		<script type="text/javascript" src="/dhtmlxGrid/codebase/excells/dhtmlxgrid_excell_customized.js"></script>
		<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxcommon_haas.js"></script>

		<title><fmt:message key="label.inventorygroupdefinition" /></title>
		
		<script language="JavaScript" type="text/javascript">
		<!--
		//add all the javascript messages here, this for internationalization of client side javascript messages
		var messagesData = new Array();
		messagesData = {alert:"<fmt:message key="label.alert"/>",
		and:"<fmt:message key="label.and"/>",
		recordFound:"<fmt:message key="label.recordFound"/>",
		searchDuration:"<fmt:message key="label.searchDuration"/>",
		minutes:"<fmt:message key="label.minutes"/>",
		seconds:"<fmt:message key="label.seconds"/>",
		validvalues:"<fmt:message key="label.validvalues"/>",
		submitOnlyOnce:"<fmt:message key="label.submitOnlyOnce"/>",
		defaultSalesMargin:"<fmt:message key="label.defaultsalesmargin"/>",
		minGrossMargin:"<fmt:message key="label.mingrossmargin"/>",
		maxGrossMargin:"<fmt:message key="label.maxgrossmargin"/>",
		updateOrderOnReceipt:"<fmt:message key="label.updateorderonreceipt"/>",
		noRowSelected:"<fmt:message key="error.norowselected"/>",
		searchPersonnel:"<fmt:message key="searchpersonnel.label.title"/>",
		itemInteger:"<fmt:message key="error.item.integer"/>"};
		
		var gridConfig = {
			divName:'inventoryGroupDefinitionLineItemBean',	// the div id to contain the grid.
			beanData:'jsonMainData',     					// the data variable name for jsonparse, as in grid.parse( beanData ,"json" )
			beanGrid:'beanGrid',     						// the grid's name, as in beanGrid.attachEvent...
			config:'config',	     						// the column config var name, as in var config = { [ columnId:..,columnName...
			rowSpan:false,			 							// this page has rowSpan > 1 or not.
			submitDefault:'true',    						// the fields in grid are defaulted to be submitted or not.
		    singleClickEdit:true,     						// this will open the txt cell type to enter notes by single click
		    onRowSelect:doOnRowSelected,   					// the onRowSelect event function to be attached, as in beanGrid.attachEvent("onRowSelect",selectRow)
			onRightClick:selectRightclick,   				// the onRightClick event function to be attached, as in beanGrid.attachEvent("onRightClick",selectRow)
		    onBeforeSorting:doOnBeforeSelect 				// the onBeforeSorting event function to be attached, as in beanGrid.attachEvent("onBeforeSorting",selectRow));
		};
	
		/*This array defines the grid and properties about the columns in the grid.
		* more explanation of the grid config can be found in /dhtmlxGrid/codebase/dhtmlxcommon_haas.js initGridWithConfig()
		* */
		
		<c:set var="inventorygroup"><fmt:message key="label.inventorygroup"/></c:set>
		var config = [
  		{
  			columnId:"permission"
  		},
		{ columnId:"inventoryGroup",
		  columnName:'<tcmis:jsReplace value="${inventorygroup}"/>',
		  tooltip:"Y",
		  width:15
		},
		  {
		  	columnId:"okDoUpdate",
		  	columnName:'<fmt:message key="label.ok"/>',
		  	type:'hchstatus',  // checkbox:The value is string "true" if checked
		    align:'center',
		    width:3
		  },
		{ columnId:"manageKitsAsSingleUnitBooleanStr",
		  columnName:'<fmt:message key="label.managekits"/>',
		  type: 'hchstatus',
    	  align:'center',
		  width:4
		},
		{ columnId:"defaultBuyerName",
		  columnName:'<fmt:message key="label.defaultbuyer"/>',
  		  attachHeader:'<fmt:message key="label.name"/>',
		  align:'center',
		  width:8
		},
		{ columnId:"buyerLookup",
		  columnName:'#cspan',
		  attachHeader:'<fmt:message key="label.lookup"/>',
		  width:6
		},
		{ 
			columnId:"defaultBuyerId"
		},
		{ columnId:"issueGeneration",
		  columnName:'<fmt:message key="label.issuing"/>',
		  type:'hcoro',
		  width:10
		},

		{ columnId:"csrName",
		  columnName:'<fmt:message key="label.csr"/>',
		  attachHeader:'<fmt:message key="label.name"/>',
		  align:'center',
		  width:8
		},
		{ 
			columnId:"csrLookup",
			columnName:'#cspan',
			attachHeader:'<fmt:message key="label.lookup"/>',
			width:6
		},
		{ 
			columnId:"csrPersonnelId"
		},
		{ columnId:"poCopyFromLastBuyBooleanStr",
		  columnName:'<fmt:message key="label.copylastpo"/>',
		  type: 'hchstatus',
		  align:'center',
		  width:5
		},
		{
		  columnId:"skipReceivingQcBooleanStr",
		  columnName:'<fmt:message key="label.skipreceivingqc"/>',
		  type: 'hchstatus',
		  align:'center',
		  width:5
		},
		{
		  columnId:"nonintegerReceiving",
		  columnName:'<fmt:message key="label.nonintegerreceiving"/>',
		  type: 'hchstatus',
		  align:'center',
		  width:5
		},
		{
		  columnId:"allowForceBuy",
		  columnName:'<fmt:message key="label.allowforcebuy"/>',
		  type: 'hchstatus',
		  align:'center',
		  width:5
		},
		{
		  columnId:"specCheckRequired",
		  columnName:'<fmt:message key="label.allocatebyspec"/>',
		  type: 'hchstatus',
		  align:'center',
		  width:5
		},
		{
		  columnId:"orderAllocByExpireDate",
		  columnName:'<fmt:message key="label.allocateexpiresfirst"/>',
		  type: 'hchstatus',
		  align:'center',
		  width:5
		},
		{
		  columnId:"showOptionalInvPick",
		  columnName:'<fmt:message key="label.showoptionsonpicklist"/>',
		  type: 'hchstatus',
		  align:'center',
		  width:5
		},
		{
		  columnId:"autoReceiveTransfer",
		  columnName:'<fmt:message key="label.autoreceivetransfer"/>',
		  type: 'hchstatus',
		  align:'center',
		  width:5
		},
		{ columnId:"packingListTemplate",
		  columnName:'<fmt:message key="label.packinglist"/>',
		  type:'hcoro',
		  align:'center',
		  width:16
		},		
		{
		  columnId:"orderQtyUpdateOnReceipt",
		  columnName:'<fmt:message key="label.updateorderonreceipt"/>',
		  type: 'hed',
		  onChange:onChangeOrderQtyUpdateOnReceipt, // rowid & columnid passed as parameters
		  align:'right',
		  width:7
		},
		{
		  columnId:"defaultSalesMargin",
		  columnName:'<fmt:message key="label.defaultsalesmargin"/>',
		  type: 'hed',
		  onChange:onChangeDefaultSalesMargin, // rowid & columnid passed as parameters 
		  align:'right',
		  width:7
		},
		{ columnId:"minimumGrossMargin",
		  columnName:'<fmt:message key="label.mingrossmargin"/>',
		  type: 'hed',
		  onChange:onChangeMinGrossMargin, // rowid & columnid passed as parameters 
		  align:'right',
		  width:7
		},
		{ columnId:"maximumGrossMargin",
		  columnName:'<fmt:message key="label.maxgrossmargin"/>',
		  type: 'hed',
		  onChange:onChangeMaxGrossMargin,  // rowid & columnid passed as parameters
		  align:'right',
		  width:7
		},
		{ columnId:"indefiniteExpirationString",
		  columnName:'<fmt:message key="label.indefinitealias"/>',
		  type:'hcoro',
		  align:'center',
		  width:10
		},		
  		{
  			columnId:"opsEntityId"		// Column used to check row status
  		}
		];
		
		var indefiniteExpirationString = new  Array(
		{text:'<fmt:message key="label.na"/>',value:'NA'},
        {text:'<fmt:message key="label.unlimited"/>',value:'UNLIMITED'});

		var issueGeneration = new  Array(
				{text:'<fmt:message key="label.itemcounting"/>',value:'Item Counting'},
		        {text:'<fmt:message key="label.itemusage"/>',value:'Item Usage'},
				{text:'<fmt:message key="picking"/>',value:'Picking'},
		        {text:'<fmt:message key="label.receiptusage"/>',value:'Receipt Usage'},
				{text:'',value:''}
		        );

		var packingListTemplate = new  Array(
				<c:forEach var="packingListTemplateBean" items="${vvPackingListTemplate}" varStatus="packingListTemplateBeanStatus">
					<c:if test="${packingListTemplateBeanStatus.index > 0}">,</c:if>
					{text:'${packingListTemplateBean.packingListTemplateDesc}',value:'${packingListTemplateBean.packingListTemplate}'}
				</c:forEach>);
		
		// -->
		</script>
	</head>
	
	<body bgcolor="#ffffff" onload="resultOnLoadWithGrid(gridConfig)">
		<tcmis:form action="/inventorygroupdefinitionupdateresults.do" onsubmit="return submitFrameOnlyOnce();">
			<div id="errorMessagesAreaBody" style="display:none;">
			    <html:errors/>
			    ${tcmISError}
			    <c:forEach items="${tcmISErrors}" varStatus="status">
			  	${status.current}<br/>
			    </c:forEach>        
			</div>
		
			<script type="text/javascript">
			   <c:choose>
			    <c:when test="${requestScope['org.apache.struts.action.ERROR'] == null && empty tcmISErrors }">
			     showErrorMessage = false;
			    </c:when>
			    <c:otherwise>
			     showErrorMessage = true;
			    </c:otherwise>
			   </c:choose>   
			    //-->       
			</script>
		
			<div class="interface" id="resultsPage">
				<div class="backGroundContent">
				    
				<!-- Give the div name that holds the grid the same name as your viewbean or dynabean you want for updates -->
				<div id="inventoryGroupDefinitionLineItemBean" style="width:100%;height:600px;" style="display: none;"></div>
				
				<!-- Search results start -->
				<c:if test="${inventoryGroupDefinitionLineItemBeanCollection != null}" >
					<c:if test="${!empty inventoryGroupDefinitionLineItemBeanCollection}" >
						<script type="text/javascript">
							<!--	
							/*This is to keep track of whether to show any update links.
							If the user has any update permisions for any row then we show update links.*/
							<c:set var="showUpdateLink" value='N'/>
							<c:set var="dataCount" value='${0}'/>
							/*Storing the data to be displayed in a JSON object array.*/
							var jsonMainData = new Array();
							var jsonMainData = {
							rows:[
							<c:forEach var="inventoryGroupDefinitionLineItemBean" items="${inventoryGroupDefinitionLineItemBeanCollection}" varStatus="status">
								<c:if test="${status.index > 0}">,</c:if>

								<c:set var="manageKitsAsSingleUnitBool" value="false"/>
								<c:set var="poCopyFromLastBuyBool" 		value="false"/>
								<c:set var="skipReceivingQcBool" 		value="false"/>
								<c:set var="nonIntegerReceiving" value="false"/>
								<c:set var="allowForceBuy" value="false"/>
								<c:set var="specCheckRequired" value="false"/>
								<c:set var="orderAllocByExpireDate" value="false"/>
								<c:set var="showOptionalInvPick" value="false"/>
								<c:set var="autoReceiveTransfer" value="false"/>	
								
								<tcmis:jsReplace var="inventoryGroup" value="${inventoryGroupDefinitionLineItemBean.inventoryGroup}"/>
								<c:if test="${inventoryGroupDefinitionLineItemBean.manageKitsAsSingleUnitBooleanStr == 'true'}"><c:set var="manageKitsAsSingleUnitBool" value="true"/></c:if>
								<c:if test="${inventoryGroupDefinitionLineItemBean.poCopyFromLastBuyBooleanStr == 'true'}"><c:set var="poCopyFromLastBuyBool" value="true"/></c:if>
								<c:if test="${inventoryGroupDefinitionLineItemBean.skipReceivingQcBooleanStr == 'true'}"><c:set var="skipReceivingQcBool" value="true"/></c:if>
								<tcmis:jsReplace var="defaultBuyerName" value="${inventoryGroupDefinitionLineItemBean.defaultBuyerName}"/>
								<tcmis:jsReplace var="csrName" value="${inventoryGroupDefinitionLineItemBean.csrName}"/>
								<c:if test="${inventoryGroupDefinitionLineItemBean.nonintegerReceiving == 'Y'}"><c:set var="nonIntegerReceiving" value="true"/></c:if>
								<c:if test="${inventoryGroupDefinitionLineItemBean.allowForceBuy == 'Y'}"><c:set var="allowForceBuy" value="true"/></c:if>
								<c:if test="${inventoryGroupDefinitionLineItemBean.specCheckRequired == 'Y'}"><c:set var="specCheckRequired" value="true"/></c:if>
								<c:if test="${inventoryGroupDefinitionLineItemBean.orderAllocByExpireDate == 'Y'}"><c:set var="orderAllocByExpireDate" value="true"/></c:if>
								<c:if test="${inventoryGroupDefinitionLineItemBean.showOptionalInvPick == 'Y'}"><c:set var="showOptionalInvPick" value="true"/></c:if>
								<c:if test="${inventoryGroupDefinitionLineItemBean.autoReceiveTransfer == 'Y'}"><c:set var="autoReceiveTransfer" value="true"/></c:if>

								/* set page permission here */
								<c:set var="readonly" value='N'/>

								<tcmis:inventoryGroupPermission indicator="true" userGroupId="inventoryGrpDefAdmin" inventoryGroup="${inventoryGroupDefinitionLineItemBean.inventoryGroup}">
								 	<c:set var="readonly" value='Y'/>
									<c:set var="showUpdateLink" value='Y'/>
								</tcmis:inventoryGroupPermission>

								<tcmis:opsEntityPermission indicator="true" opsEntityId="${param.opsEntityId}"  userGroupId="opsInventoryGrpDefAdmin">
									<c:set var="readonly" value='Y'/>
									<c:set var="showUpdateLink" value='Y'/>
						  		</tcmis:opsEntityPermission>					

								<c:choose>
									<c:when test="${readonly == 'N'}">
										<c:set var="buyerLookup">
											<c:set var="buyerLookup" value="" />
										</c:set>

										<c:set var="csrLookup">
											<c:set var="csrLookup" value="" />
										</c:set>								
									</c:when>
  									<c:otherwise>
  										<c:set var="buyerLookup">
    										<input type="button" class="lookupBtn" onmouseover="this.className=\'lookupBtn lookupBtnOver\'" onmouseout="this.className=\'lookupBtn\'" id="fLookupButton" value="" onclick="getUsersList(${status.index+1},\'buyer\')"/><input type="button" class="smallBtns" onmouseover="this.className=\'smallBtns smallBtnsOver\'" onMouseout="this.className=\'smallBtns\'" name="None" value="<fmt:message key="label.clear"/>" onclick="clearUserEntry(${status.index+1},\'buyer\')"/> 
  										</c:set>
  										<c:set var="csrLookup">
											<input type="button" class="lookupBtn" onmouseover="this.className=\'lookupBtn lookupBtnOver\'" onmouseout="this.className=\'lookupBtn\'" id="gLookupButton" value="" onclick="getUsersList(${status.index+1},\'csr\')"/><input type="button" class="smallBtns" onmouseover="this.className=\'smallBtns smallBtnsOver\'" onMouseout="this.className=\'smallBtns\'" name="None" value="<fmt:message key="label.clear"/>" onclick="clearUserEntry(${status.index+1},\'csr\')"/> 
										</c:set>
									</c:otherwise>
 								</c:choose>
								
								/*The row ID needs to start with 1 per their design.*/
								{ id:${status.index +1},
								 data:['${readonly}','${inventoryGroup}',false,${manageKitsAsSingleUnitBool},
								 '${defaultBuyerName}','${buyerLookup}','${inventoryGroupDefinitionLineItemBean.defaultBuyerId}','${inventoryGroupDefinitionLineItemBean.issueGeneration}','${csrName}','${csrLookup}','${inventoryGroupDefinitionLineItemBean.csrPersonnelId}',
								 ${poCopyFromLastBuyBool},${skipReceivingQcBool},${nonIntegerReceiving},${allowForceBuy},${specCheckRequired},${orderAllocByExpireDate},
								 ${showOptionalInvPick},${autoReceiveTransfer},'${inventoryGroupDefinitionLineItemBean.packingListTemplate}','${inventoryGroupDefinitionLineItemBean.orderQtyUpdateOnReceiptStr}','${inventoryGroupDefinitionLineItemBean.defaultSalesMargin}',
								 '${inventoryGroupDefinitionLineItemBean.minimumGrossMarginStr}','${inventoryGroupDefinitionLineItemBean.maximumGrossMarginStr}','${inventoryGroupDefinitionLineItemBean.indefiniteExpirationString}','${param.opsEntityId}']}
							  	<c:set var="dataCount" value='${dataCount+1}'/>
							</c:forEach>
							]};
						//-->
						</script>
					</c:if>
					<!-- If the collection is empty say no data found -->
					<c:if test="${empty inventoryGroupDefinitionLineItemBeanCollection}" >
						<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableNoData" id="resultsPageTable">
							<tr>
								<td width="100%">
									<fmt:message key="main.nodatafound"/>
								</td>
							</tr>
						</table>
					</c:if>
				</c:if>
				<!-- Search results end -->
				
				<!-- Hidden element start -->
				<div id="hiddenElements" style="display: none;">
					<tcmis:setHiddenFields beanName="inventoryGroupDefinitionInputBean"/>
								
					<%--This is the minimum height of the result section you want to display--%>
					<input name="minHeight" id="minHeight" type="hidden" value="0"/>
				 </div>
				<!-- Hidden elements end -->
				
				</div> <!-- close of backGroundContent -->
			</div> <!-- interface End -->
			
			<c:if test="${showUpdateLink == 'Y'}">
			    <script type="text/javascript">
			        <!--
			        showUpdateLinks = true;
			        //-->
			    </script>
			</c:if>
			
		</tcmis:form>
	</body>	
</html:html>
