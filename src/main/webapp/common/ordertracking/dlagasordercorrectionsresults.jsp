<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>

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
		<link rel="shortcut icon" href="https://www.tcmis.com/images/buttons/tcmIS.ico"></link>
		
		<%@ include file="/common/locale.jsp" %>
		
		<tcmis:gridFontSizeCss /> <%-- Sets CSS based on the user's preffered font size and which application he is viewing --%>
	
		<script type="text/javascript" src="/js/common/formchek.js"></script>			<%-- Validation support --%>
		<script type="text/javascript" src="/js/common/commonutil.js"></script>
	
		<script type="text/javascript" src="/js/common/grid/resultiframegridresize.js"></script> <%-- This handles all the resizing of the page and frames --%>
		<script type="text/javascript" src="/js/common/disableKeys.js"></script>		<%-- This disables various key press events --%>

		<%-- Right Mouse Click (RMC) Menu support, keep in all pages  --%>
		<script type="text/javascript" src="/js/menu/milonic_src.js"></script>
		<script type="text/javascript" src="/js/menu/mmenudom.js"></script>
		<script type="text/javascript" src="/js/menu/mainmenudata.js"></script>
		<script type="text/javascript" src="/js/menu/contextmenu.js"></script>
		<script type="text/javascript" src="/js/menu/mm_menueditapi.js"></script>
		<%@ include file="/common/rightclickmenudata.jsp" %>
	
		<%-- Form popup Calendar support --%>
		<script type="text/javascript" src="/js/calendar/newcalendar.js"></script>
		<script type="text/javascript" src="/js/calendar/AnchorPosition.js"></script>
		<script type="text/javascript" src="/js/calendar/PopupWindow.js"></script>
	
		<%-- Grid support --%>
		<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxcommon.js"></script>
		<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxgrid.js"></script>
		<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxgridcell.js"></script>
		<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_form.js"></script>
		<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_srnd.js"></script>
		<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_json.js"></script>    
		<%--Uncomment below if you are providing header menu to switch columns on/off--%>
		<%--<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_hmenu.js"></script>--%>
		<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_rowspan.js"></script>
		<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/rowspan_cell_patch.js"></script>
		<script type="text/javascript" src="/dhtmlxGrid/codebase/excells/dhtmlxgrid_excell_customized.js"></script>
		<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxcommon_haas.js"></script>
		<script type="text/javascript" src="/dhtmlxLayout/codebase/dhtmlxlayout.js"></script>
		<script type="text/javascript" src="/dhtmlxWindows/codebase/dhtmlxwindows.js"></script>

		<%-- Page specific javascript --%>
		<script type="text/javascript" src="/js/common/ordertracking/dlagasordercorrections.js"></script>

		<title>
		    <fmt:message key="dlaGasOrderCorrections"/>
		</title>

		<script language="JavaScript" type="text/javascript">
			<%-- Define the right click menus --%>
			with(milonic=new menuname("rightClickMenu")){
				style=submenuStyle;
				itemheight=17;
				aI("showmenu=resendInvoiceMenu;text=<fmt:message key="label.resubmit"/> <fmt:message key="label.invoice"/>;");
			}
			
			with(milonic=new menuname("resendInvoiceMenu")){
			    top="offset=2"
				style = contextStyle;
				margin=3
				aI("text=temp;url=javascript:doNothing();image=");
			}
									
			var invoicesMenuData = {
				<c:forEach var="row" items="${searchResults}" varStatus="status">
					<c:set var="key" value="${row.prNumber},${row.shipmentId}"/>
					<c:if test="${!status.first}">,</c:if>
					'${key}':'<tcmis:jsReplace value="${invoicesData[key]}"/>'
				</c:forEach>
			};
			
			<%-- Initialize the RCMenus --%>
			drawMenus();
		
			<%-- Check for errors to display --%>
			<c:choose>
				<c:when test="${requestScope['org.apache.struts.action.ERROR'] == null && empty tcmISError && empty tcmISErrors }">
			     		showErrorMessage = false;
				</c:when>
				<c:otherwise>
					showErrorMessage = true;
				</c:otherwise>
			</c:choose>

			var messagesData = new Array();
			messagesData = {alert:"<fmt:message key="label.alert"/>",
					and:"<fmt:message key="label.and"/>",
					validvalues:"<fmt:message key="label.validvalues"/>",
					submitOnlyOnce:"<fmt:message key="label.submitOnlyOnce"/>",
					recordFound:"<fmt:message key="label.recordFound"/>",
					searchDuration:"<fmt:message key="label.searchDuration"/>",
					minutes:"<fmt:message key="label.minutes"/>",
					norowselected:"<fmt:message key="error.norowselected"/>",
					seconds:"<fmt:message key="label.seconds"/>"};
						
			var sendOptions = new  Array(
				{text:'<fmt:message key="label.asori"/>',value:'ASORI'},
			    {text:'<fmt:message key="label.asnonori"/>',value:'ASNONORI'},
			    {text:'<fmt:message key="label.dontsend"/>',value:'DONTSEND'},
		        {text:'',value:''}
			);
		</script>

	</head>

	<body bgcolor="#ffffff" onload="resultOnLoadWithGrid(gridConfig);">
		<tcmis:form action="/dlagasordercorrectionsresults.do" onsubmit="return submitFrameOnlyOnce();">
			
		
			<div class="interface" id="resultsPage">
				<div class="backGroundContent">
					<%-- This is where the grid will be inserted.  The div name is ALSO the name of the form values that will be sent to the server on update --%>
					<div id="Edi856CorrectionsBean" style="width:100%;height:400px;" style="display: none;"></div>	
					<c:choose>
						<%-- If the collection is empty say no data found --%>
						<c:when test="${empty searchResults}">
							<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableNoData" id="resultsPageTable">
								<tr>
						       			<td width="100%">
						          			<fmt:message key="main.nodatafound"/>
						       			</td>
						    		</tr>
						  	</table>
						</c:when>
						<c:otherwise>
							<script type="text/javascript">
								<!--

								var jsonMainData = {
									rows:[
									<c:forEach var="row" items="${searchResults}" varStatus="status">
										{ id:${status.count},
										  data:['Y',
										  <c:choose>
										  	<c:when test="${row.isHeaderRecord != true}">
										  	'N','N','N','N','Y','Y','Y','Y','Y','Y','Y','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N',
										  	</c:when>
										  	<c:otherwise>
										  	'Y','Y','Y','Y','N','N','N','N','N','N','N','N','Y','Y','Y','Y','Y','Y','Y','Y','Y','Y','Y','Y','Y','Y','Y','Y','Y','Y','Y','Y','Y','Y','Y','Y','Y','Y','Y','Y','Y','Y','Y','Y','Y','Y','Y','Y','Y','Y','Y','Y','Y','Y','Y','Y','Y','Y','Y','Y','Y','Y','Y','Y','Y',
										  	</c:otherwise>
										  </c:choose>     
										  		false,
										  	  	<c:choose>
											  		<c:when test="${row.isHeaderRecord != true}">
											  		'',
											  		</c:when>
												  	<c:otherwise>
												  	'ASORI',
												 	</c:otherwise>
											  	</c:choose>
												'${row.releaseNum}',
												'${row.prNumber}',
												'${row.status}',
												'${row.status2}',
												'<fmt:formatDate value="${row.dateSent}" pattern="${dateFormatPattern}"/>',
												'<fmt:formatDate value="${row.dateSent2}" pattern="${dateFormatPattern}"/>',
												'<fmt:formatDate value="${row.estimateDeliveryDate}" pattern="${dateFormatPattern}"/>',
												'<fmt:formatDate value="${row.shipDate}" pattern="${dateFormatPattern}"/>',
												'${row.usgovShipmentId}',
												'${row.boxId}',
												'${row.boxRfid}',
												'${row.caseRfid}',
												'${row.palletId}',
												'${row.palletRfid}',
												'${row.unitPrice}',
												'${row.polineQtyShippedInBox}',
												'${row.uom}',
												'${row.customerPoNo}',
												'${row.customerPoLine}',
												'<fmt:formatDate value="${row.poDate}" pattern="${dateFormatPattern}"/>',
											 	'<tcmis:jsReplace value="${row.catPartNo}" processMultiLines="false" />',
												'${row.polineQtyShippedInShipment}',
												'<tcmis:jsReplace value="${row.itemDescription}" processMultiLines="true" />',
												'${row.shipFromCageCode}',
												'${row.shipFromLocationId}',
												'${row.shiptoDodaac}',
												'<tcmis:jsReplace value="${row.shiptoPartyName}" processMultiLines="true" />',
												'${row.shiptoPartyId}',
												'<tcmis:jsReplace value="${row.shiptoAddress1}" processMultiLines="false" />',
												'<tcmis:jsReplace value="${row.shiptoAddress2}" processMultiLines="false" />',
												'<tcmis:jsReplace value="${row.shiptoAddress3}" processMultiLines="false" />',
												'${row.shiptoCity}',
												'${row.shiptoState}',
												'${row.shiptoZip}',
												'${row.shiptoCountry}',		
												'${row.transactionRefNum}',
												'${row.transportationControlNum}',
												'${row.deliveryTicket}',
												'${row.billOfLading}',
												'${row.shipmentId}',
												'<tcmis:jsReplace value="${row.carrierName}" processMultiLines="false" />',
												'${row.trackingNumber}',
												'${row.inspRequired}',									
												'${row.inspDodaac}',
												'${row.inspName}',
												'${row.inspEmail}',
												'${row.inspPhone}',
												'${row.inspPhoneExtension}',
												'${row.inspFaxNumber}',
												'<tcmis:jsReplace value="${row.inspAddressLine1}" processMultiLines="false" />',	
												'<tcmis:jsReplace value="${row.inspAddressLine2}" processMultiLines="false" />',
												'<tcmis:jsReplace value="${row.inspAddressLine3}" processMultiLines="false" />',
												'${row.inspCity}',
												'${row.inspState}',
												'${row.inspZip}',	
												'<tcmis:jsReplace value="${row.contractOffice}" processMultiLines="false" />',
												'<tcmis:jsReplace value="${row.contractOfficeAlternateName}" processMultiLines="false" />',
												'${row.contractOfficerName}',
												'${row.contractOfficeCode}',
												'<tcmis:jsReplace value="${row.contractOfficeAddress1}" processMultiLines="false" />',
												'<tcmis:jsReplace value="${row.contractOfficeAddress2}" processMultiLines="false" />',
												'${row.contractOfficeCity}',
												'${row.contractOfficeState}',
												'${row.contractOfficeZip}',
												'${row.contractOfficeCountry}',
												'${row.payofficeId}',
												'<tcmis:jsReplace value="${row.payofficeName}" processMultiLines="false" />',
												'<tcmis:jsReplace value="${row.payofficeAdditionalName}" processMultiLines="false" />',
												'<tcmis:jsReplace value="${row.payofficeAddress1}" processMultiLines="false" />',
												'<tcmis:jsReplace value="${row.payofficeAddress2}" processMultiLines="false" />',
												'${row.payofficeCity}',
												'${row.payofficeState}',
												'${row.payofficeZip}',
												'${row.payofficeCountry}',								
												'${row.poSuffix}',
												'${row.customerHaasContractId}',
												'${row.asnId}', 
												'${row.releaseNum}',
												'${row.boxId}',
												'${row.boxRfid}',
												'${row.statusDetail}',
												'${row.statusDetail2}',
												'${row.usgovShipmentId}',
												${row.isHeaderRecord}
												

										  ]}<c:if test="${!status.last}">,</c:if>
										  <c:set var="dataCount" value='${dataCount+1}'/> 
	 								</c:forEach>
									]};
								//-->  
							</script>
						</c:otherwise>
					</c:choose>
 
					<div id="hiddenElements" style="display: none;">    			
						<%-- Retrieve all the Search Criteria here for re-search after update--%>
    					<input name="minHeight" id="minHeight" type="hidden" value="100"/>
    					
    					<!-- Popup Calendar input options for revisedPromisedDate -->
    					<input name="totalLines" id="totalLines" value="<c:out value="${fn:length(searchResults)}"/>" type="hidden"/>
						<input name="uAction" id="uAction" value="" type="hidden">
						<input name="releaseNum" id="releaseNum" value="${param.releaseNum}" type="hidden"/>
						<input name="contractId" id="contractId" value="${param.contractId}" type="hidden"/>
						<input name="invoiceToResubmitId" id="invoiceToResubmitId" value="" type="hidden"/>
						<input type="hidden" name="blockBeforeExclude_estimateDeliveryDate" id="blockBeforeExclude_estimateDeliveryDate" value=""/>
						<input type="hidden" name="blockAfterExclude_estimateDeliveryDate" id="blockAfterExclude_estimateDeliveryDate" value=""/>
						<input type="hidden" name="blockBeforeExclude_estimateDeliveryDate" id="blockBeforeExclude_estimateDeliveryDate" value=""/>
						<input type="hidden" name="blockAfterExclude_estimateDeliveryDate" id="blockAfterExclude_estimateDeliveryDate" value=""/>
						<input type="hidden" name="inDefinite_shipDate" id="inDefinite_estimateDeliveryDate" value=""/>
						<input type="hidden" name="blockBeforeExclude_shipDate" id="blockBeforeExclude_shipDate" value=""/>
						<input type="hidden" name="blockAfterExclude_shipDate" id="blockAfterExclude_shipDate" value=""/>
						<input type="hidden" name="blockBeforeExclude_shipDate" id="blockBeforeExclude_shipDate" value=""/>
						<input type="hidden" name="blockAfterExclude_shipDate" id="blockAfterExclude_shipDate" value=""/>
						<input type="hidden" name="inDefinite_shipDate" id="inDefinite_shipDate" value=""/>
						
					</div>
				</div>
			</div>
		<script type="text/javascript">
			<%-- Define the columns for the result grid --%>
			var columnConfig = [ 
					{columnId:"permission" },	<%-- Update Permission for entire line --%>
					{columnId:"sendOptionsPermission" },		
					{columnId:"estimateDeliveryDatePermission"}, 
					{columnId:"shipDatePermission"}, 
					{columnId:"usgovShipmentIdPermission"}, 
					{columnId:"boxIdPermission"},
					{columnId:"boxRfidPermission"}, 
					{columnId:"caseRfidPermission"}, 
					{columnId:"palletIdPermission"}, 
					{columnId:"palletRfidPermission"},
					{columnId:"unitPricePermission"},
					{columnId:"polineQtyShippedInBoxPermission"}, 
					{columnId:"uomPermission"},
					{columnId:"polineQtyShippedInShipmentPermission"}, 
					{columnId:"itemDescriptionPermission"}, 
					{columnId:"shipFromCageCodePermission"},
					{columnId:"shipFromLocationIdPermission"},
					{columnId:"shiptoDodaacPermission"}, 
					{columnId:"shiptoPartyNamePermission"}, 
					{columnId:"shiptoPartyIdPermission"}, 
					{columnId:"shiptoAddress1Permission"},
					{columnId:"shiptoAddress2Permission"},
					{columnId:"shiptoAddress3Permission"}, 
					{columnId:"shiptoCityPermission"}, 
					{columnId:"shiptoStatePermission"}, 
					{columnId:"shiptoZipPermission"},
					{columnId:"shiptoCountryPermission"},
					{columnId:"transactionRefNumPermission"}, 
					{columnId:"transportationControlNumPermission"}, 
					{columnId:"deliveryTicketPermission"}, 
					{columnId:"billOfLadingPermission"},
					{columnId:"shipmentIdPermission"}, 
					{columnId:"carrierNamePermission"}, 
					{columnId:"trackingNumberPermission"}, 
					{columnId:"inspRequiredPermission"},
					{columnId:"inspDodaacPermission"}, 
					{columnId:"inspNamePermission"}, 
					{columnId:"inspEmailPermission"}, 
					{columnId:"inspPhonePermission"},
					{columnId:"inspPhoneExtensionPermission"},
					{columnId:"inspFaxNumberPermission"},
					{columnId:"inspAddressLine1Permission"}, 
					{columnId:"inspAddressLine2Permission"}, 
					{columnId:"inspAddressLine3Permission"}, 
					{columnId:"inspCityPermission"}, 
					{columnId:"inspStatePermission"}, 
					{columnId:"inspZipPermission"}, 
					{columnId:"contractOfficePermission"}, 
					{columnId:"contractOfficeAlternateNamePermission"}, 
					{columnId:"contractOfficerNamePermission"}, 
					{columnId:"contractOfficeCodePermission"}, 
					{columnId:"contractOfficeAddress1Permission"}, 
					{columnId:"contractOfficeAddress2Permission"}, 
					{columnId:"contractOfficeCityPermission"},
					{columnId:"contractOfficeStatePermission"}, 
					{columnId:"contractOfficeZipPermission"},
					{columnId:"contractOfficeCountryPermission"}, 
					{columnId:"payofficeIdPermission"}, 
					{columnId:"payofficeNamePermission"}, 
					{columnId:"payofficeAdditionalNamePermission"}, 
					{columnId:"payofficeAddress1Permission"},
					{columnId:"payofficeAddress2Permission"},
					{columnId:"payofficeCityPermission"}, 
					{columnId:"payofficeStatePermission"}, 
					{columnId:"payofficeZipPermission"},
					{columnId:"payofficeCountryPermission"},
					{
					  	columnId:"okDoUpdate",
					  	columnName:'<fmt:message key="label.ok"/>',
					  	type:'hchstatus',  // checkbox:The value is string "true" if checked
					    align:'center',
					    width:3
					},
					{
					  	columnId:"sendOptions",
					  	columnName:'<fmt:message key="label.sendoptions"/>',
					  	type:'hcoro', align:'center', width:5,size:4,
					    permission: true 
					},
					{columnId:"releaseNum", columnName:'<fmt:message key="label.releasenumber"/>', width:3},
					{columnId:"prNumber", columnName:'<fmt:message key="label.mrnumber"/>', width:5},
					{columnId:"status", columnName:'<fmt:message key="label.status"/>', width:3},
					{columnId:"status2", columnName:'<fmt:message key="label.status"/>2', width:4},
					{columnId:"dateSent", columnName:'<fmt:message key="label.datesent"/>', width:5},
					{columnId:"dateSent2", columnName:'<fmt:message key="label.datesent"/>2', width:5},
					{columnId:"estimateDeliveryDate", type:'hcal', columnName:'<fmt:message key="label.estimatedeliverydate"/>', align:'center', width:8, permission: true },
					{columnId:"shipDate", type:'hcal', columnName:'<fmt:message key="label.shipdate"/>', align:'center', width:8, permission: true },
					{columnId:"usgovShipmentId", columnName:'<fmt:message key="label.usgovshipmentid"/>', type:"hed", width:6, permission: true },
					{columnId:"boxId", columnName:'<fmt:message key="label.boxid"/>', type:"hed", width:10, permission: true  }, 				
					{columnId:"boxRfid", columnName:'<fmt:message key="label.boxrfid"/>', type:"hed", width:10, permission: true },
					{columnId:"caseRfid", columnName:'<fmt:message key="label.caserfid"/>', width:10, permission: true },
					{columnId:"palletId", columnName:'<fmt:message key="label.pallet"/>', type:"hed", width:10 , permission: true }, 
					{columnId:"palletRfid", columnName:'<fmt:message key="label.palletrfid"/>', type:"hed", width:10 , permission: true },		
					{columnId:"unitPrice", columnName:'<fmt:message key="label.unitprice"/>', type:"hed", width:5 , permission: true },
					{columnId:"polineQtyShippedInBox", columnName:'<fmt:message key="label.partsperbox"/>', align:'center', type:"hed", width:4, permission: true },
					{columnId:"uom", columnName:'<fmt:message key="label.uom"/>', type:"hed", width:3 , permission: true }, 
					{columnId:"customerPoNo", columnName:'<fmt:message key="label.customerpo"/>', width:6},
					{columnId:"customerPoLineNo", columnName:'<fmt:message key="label.customerpoline"/>', width:3},
					{columnId:"poDate", columnName:'<fmt:message key="label.podate"/>', align:'center', width:5},
					{columnId:"catPartNo", columnName:'<fmt:message key="label.catpartno"/>', width:10},	
					{columnId:"polineQtyShippedInShipment", columnName:'<fmt:message key="label.quantityshipped"/>', align:'center', type:"hed", width:4, permission: true },
					{columnId:"itemDescription", columnName:'<fmt:message key="label.description"/>', type:"hed", width:20,size:30,maxwidth:30, permission: true },
					{columnId:"shipFromCageCode", columnName:'<fmt:message key="label.shipfromcagecode"/>', type:"hed", width:4, permission: true },
					{columnId:"shipFromLocationId", columnName:'<fmt:message key="label.shipfromlocationid"/>', type:"hed", width:6, permission: true },
					{columnId:"shiptoDodaac", columnName:'<fmt:message key="label.shipto"/>', attachHeader:'<fmt:message key="label.dodaac"/>', type:"hed", width:8, permission: true },
					{columnId:"shiptoPartyName", columnName:'#cspan', attachHeader:'<fmt:message key="label.partyname"/>', type:"hed", width:8, permission: true },
					{columnId:"shiptoPartyId",  columnName:'#cspan', attachHeader:'<fmt:message key="label.partyid"/>', type:"hed", width:6, permission: true },
					{columnId:"shiptoAddress1",  columnName:'#cspan', attachHeader:'<fmt:message key="label.address1"/>', type:"hed", width:10, permission: true },
					{columnId:"shiptoAddress2",  columnName:'#cspan', attachHeader:'<fmt:message key="label.address2"/>', type:"hed", width:10, permission: true },
					{columnId:"shiptoAddress3",  columnName:'#cspan', attachHeader:'<fmt:message key="label.address3"/>', type:"hed", width:10, permission: true },
					{columnId:"shiptoCity",  columnName:'#cspan', attachHeader:'<fmt:message key="label.city"/>', type:"hed", width:6, permission: true },
					{columnId:"shiptoState",  columnName:'#cspan', attachHeader:'<fmt:message key="label.state"/>', type:"hed", width:4, permission: true },
					{columnId:"shiptoZip",  columnName:'#cspan', attachHeader:'<fmt:message key="label.zip"/>', type:"hed", width:5, permission: true },
					{columnId:"shiptoCountry",  columnName:'#cspan', attachHeader:'<fmt:message key="label.country"/>', type:"hed", width:4, permission: true },		
					{columnId:"transactionRefNum", columnName:'<fmt:message key="label.transactionreferencenumber"/>', type:"hed", width:8, permission: true },
					{columnId:"transportationControlNum", columnName:'<fmt:message key="label.transportationcontrolnumber"/>', type:"hed", width:6, permission: true },
					{columnId:"deliveryTicket", columnName:'<fmt:message key="label.deliveryticket"/>', type:"hed", width:8, permission: true },
					{columnId:"billOfLading", columnName:'<fmt:message key="label.genericbol"/>', type:"hed", width:10, permission: true },
					{columnId:"shipmentId", columnName:'<fmt:message key="label.shipmentid"/>', type:"hed", width:6, permission: true },
					{columnId:"carrierName", columnName:'<fmt:message key="label.carriername"/>', type:"hed", width:8, permission: true },
					{columnId:"trackingNumber", columnName:'<fmt:message key="label.trackingnumber"/>', type:"hed", width:10, permission: true },
					{columnId:"inspRequired", columnName:'<fmt:message key="label.insp"/>', attachHeader:'<fmt:message key="label.origininspectionrequired"/>', type:"hed", width:3, permission: true },	
					{columnId:"inspDodaac", columnName:'#cspan', attachHeader:'<fmt:message key="label.dodaac"/>', type:"hed", width:6, permission: true },	
					{columnId:"inspName", columnName:'#cspan',attachHeader:'<fmt:message key="label.name"/>', type:"hed", width:6, permission: true },
					{columnId:"inspEmail", columnName:'#cspan',attachHeader:'<fmt:message key="label.email"/>', type:"hed", width:6, permission: true },
					{columnId:"inspPhone", columnName:'#cspan',attachHeader:'<fmt:message key="label.phone"/>', type:"hed", width:6, permission: true },
					{columnId:"inspPhoneExtension", columnName:'#cspan',attachHeader:'<fmt:message key="label.extension"/>', type:"hed", width:6, permission: true },
					{columnId:"inspFaxNumber", columnName:'#cspan',attachHeader:'<fmt:message key="label.fax"/>', type:"hed", width:6, permission: true },	
					{columnId:"inspAddressLine1", columnName:'#cspan',attachHeader:'<fmt:message key="label.addressline1"/>', type:"hed", width:8, permission: true },	
					{columnId:"inspAddressLine2", columnName:'#cspan',attachHeader:'<fmt:message key="label.addressline2"/>', type:"hed", width:8, permission: true },
					{columnId:"inspAddressLine3", columnName:'#cspan',attachHeader:'<fmt:message key="label.addressline3"/>', type:"hed", width:8, permission: true },
					{columnId:"inspCity", columnName:'#cspan',attachHeader:'<fmt:message key="label.city"/>', type:"hed", width:6, permission: true },
					{columnId:"inspState", columnName:'#cspan',attachHeader:'<fmt:message key="label.state"/>', type:"hed", width:6, permission: true },
					{columnId:"inspZip", columnName:'#cspan',attachHeader:'<fmt:message key="label.zip"/>', type:"hed", width:6, permission: true },
					{columnId:"contractOffice", columnName:'<fmt:message key="label.contract"/>', attachHeader:'<fmt:message key="label.office"/>', type:"hed", width:6, permission: true },
					{columnId:"contractOfficeAlternateName", columnName:'#cspan', attachHeader:'<fmt:message key="label.officealternatename"/>', type:"hed", width:6, permission: true },
					{columnId:"contractOfficerName", columnName:'#cspan', attachHeader:'<fmt:message key="label.officername"/>', type:"hed", width:6, permission: true },
					{columnId:"contractOfficeCode", columnName:'#cspan', attachHeader:'<fmt:message key="label.officecode"/>', type:"hed", width:6, permission: true },
					{columnId:"contractOfficeAddress1", columnName:'#cspan', attachHeader:'<fmt:message key="label.address1"/>', type:"hed", width:10, permission: true },
					{columnId:"contractOfficeAddress2", columnName:'#cspan', attachHeader:'<fmt:message key="label.address2"/>', type:"hed", width:10, permission: true },
					{columnId:"contractOfficeCity", columnName:'#cspan', attachHeader:'<fmt:message key="label.city"/>', type:"hed", width:6, permission: true },
					{columnId:"contractOfficeState", columnName:'#cspan', attachHeader:'<fmt:message key="label.state"/>', type:"hed", width:4, permission: true },
					{columnId:"contractOfficeZip", columnName:'#cspan', attachHeader:'<fmt:message key="label.zip"/>', type:"hed", width:5, permission: true },
					{columnId:"contractOfficeCountry", columnName:'#cspan', attachHeader:'<fmt:message key="label.country"/>', type:"hed", width:6, permission: true },					
					{columnId:"payofficeId", columnName:'<fmt:message key="label.payoffice"/>', attachHeader:'<fmt:message key="label.id"/>', type:"hed", width:6, permission: true },
					{columnId:"payofficeName", columnName:'#cspan', attachHeader:'<fmt:message key="label.name"/>', type:"hed", width:8, permission: true },
					{columnId:"payofficeAdditionalName", columnName:'#cspan', attachHeader:'<fmt:message key="label.additionalname"/>', type:"hed", width:8, permission: true },
					{columnId:"payofficeAddress1", columnName:'#cspan', attachHeader:'<fmt:message key="label.address1"/>', type:"hed", width:10, permission: true },
					{columnId:"payofficeAddress2", columnName:'#cspan', attachHeader:'<fmt:message key="label.address2"/>', type:"hed", width:10, permission: true },
					{columnId:"payofficeCity", columnName:'#cspan', attachHeader:'<fmt:message key="label.city"/>', type:"hed", width:6, permission: true },
					{columnId:"payofficeState", columnName:'#cspan', attachHeader:'<fmt:message key="label.state"/>', type:"hed", width:6, permission: true },
					{columnId:"payofficeZip", columnName:'#cspan', attachHeader:'<fmt:message key="label.zip"/>', type:"hed", width:6, permission: true },
					{columnId:"payofficeCountry", columnName:'#cspan', attachHeader:'<fmt:message key="label.country"/>', type:"hed", width:4, permission: true },				
					{columnId:"poSuffix", columnName:'<fmt:message key="label.suffix"/>', width:3},
					{columnId:"customerHaasContractId", columnName:'<fmt:message key="label.contract"/>', width:6},
					{columnId:"asnId", columnName:'<fmt:message key="label.asnid"/>', width:6}, 
					{columnId:"releaseNum"},
					{columnId:"oldBoxId"},
					{columnId:"oldBoxRfid"},
					{columnId:"statusDetail", columnName:'<fmt:message key="labtestlabel.details"/>', width:6},
					{columnId:"statusDetail2"},
					{columnId:"oldUsgovShipmentId"},
					{columnId:"isHeaderRecord"}					
									
			]; 
			<%-- Define the grid options--%>
			var gridConfig = {
				divName: 'Edi856CorrectionsBean',	<%--  the div id for the grid. This is the also the variable name used to pass data back in updates --%>
				beanData: 'jsonMainData',	<%--  the data variable name for jsonparse, as in grid.parse( beanData ,"json" ) --%>
				beanGrid: 'mygrid',		<%--  variable to put the grid object in for later use --%>
				config: columnConfig,		<%--  the column config var name, as in var columnConfig = { [ columnId:..,columnName... --%>
				singleClickEdit: true,		<%--  Make TXT type field open edit on one click rahter than double click --%>
				rowSpan: true, 		<%--  true: this page has rowSpan > 1 or not, disables smartrendering & sorting --%>
				submitDefault: true,
		        onRightClick:selectRightClick         <%--  a javascript function to be called on right click with rowId & cellId as args --%>
			}; 

			showUpdateLinks = true;

		</script>

		</tcmis:form><div id="errorMessagesAreaBody" style="display:none;">
    				<html:errors />
				${tcmISError}
				<c:forEach var="error" items="${tcmISErrors}" varStatus="status">
					${error}<br />
				</c:forEach>        
			</div>
	</body>
</html:html>