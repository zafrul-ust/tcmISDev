<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
	"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html;charset=UTF-8" language="java"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix='fn' uri='http://java.sun.com/jsp/jstl/functions' %>

<%@ taglib uri="/WEB-INF/struts-nested.tld" prefix="nested"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/tcmis.tld" prefix="tcmis"%>

<html:html lang="true">
<head>
<meta http-equiv="content-type" content="text/html; charset=UTF-8">
<meta http-equiv="expires" content="-1">
<link rel="shortcut icon" href="https://www.tcmis.com/images/buttons/tcmIS.ico"></link>


<%@ include file="/common/locale.jsp"%> <!--Use this tag to get the correct CSS class.
	This looks at what the user's preffered font size and which application he is viewing to set the correct CSS. -->
<tcmis:gridFontSizeCss overflow="notHidden"/> <%-- Add any other stylesheets you need for the page here --%>

<link rel="stylesheet" type="text/css" href="/css/tabs.css"/>    

<script type="text/javascript" src="/js/common/formchek.js"></script> 
<script type="text/javascript" src="/js/common/commonutil.js"></script> <!-- This handles all the resizing of the page and frames -->
<script type="text/javascript" src="/js/common/grid/iframegridresizemain.js"></script> <!-- This handles which key press events are disabled on this page -->
<script type="text/javascript" src="/js/common/disableKeys.js"></script>

<!-- This handels the menu style and what happens to the right click on the whole page -->
<script type="text/javascript" src="/js/menu/milonic_src.js"></script>
<script type="text/javascript" src="/js/menu/mmenudom.js"></script>
<script type="text/javascript" src="/js/menu/mainmenudata.js"></script>
<script type="text/javascript" src="/js/menu/contextmenu.js"></script>
<script type="text/javascript" src="/js/menu/mm_menueditapi.js"></script>
<%@ include file="/common/rightclickmenudata.jsp" %>

<%-- For Calendar support --%>
<script type="text/javascript" src="/js/calendar/newcalendar.js"></script>
<script type="text/javascript" src="/js/calendar/AnchorPosition.js"></script>
<script type="text/javascript" src="/js/calendar/PopupWindow.js"></script>
<script type="text/javascript" src="/js/calendar/calendarval.js"></script>

	
<!-- Add any other javascript you need for the page here -->
<script type="text/javascript" src="/js/common/noSearchStandardGridmain.js"></script>
<script type="text/javascript" src="/js/common/grid/nosearchresize.js"></script>
<script type="text/javascript" src="/js/hub/testrequest.js"></script>

<!-- These are for the Grid, uncomment if you are going to use the grid -->
<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxcommon.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxgrid.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxgridcell.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxcommon_haas.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_form.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_srndRowSpan.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_json.js"></script>    
<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_rowspan.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/rowspan_cell_patch.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/excells/dhtmlxgrid_excell_customized.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxcommon_haas.js"></script>

<title><fmt:message key="title.testrequest" /></title>
<script language="JavaScript" type="text/javascript">
	//add all the javascript messages here, this for internationalization of client side javascript messages
	var messagesData = {
		alert:"<fmt:message key="label.alert"/>",
		and:"<fmt:message key="label.and"/>",
		all:"<fmt:message key="label.all"/>",
		errors:"<fmt:message key="label.errors"/>",
        sampleSizeLabel:"<fmt:message key="label.samplesize"/>",
        materialqualified:"<fmt:message key="label.materialqualified"/>",
        expirationdate:"<fmt:message key="label.expirationdate"/>",  
        validvalues:"<fmt:message key="label.validvalues"/>",
		pleasewait:"<fmt:message key="label.pleasewait"/>",	
		submitOnlyOnce:"<fmt:message key="label.submitOnlyOnce"/>",
		indefinite:"<fmt:message key="label.indefinite"/>",
		testresults:"<fmt:message key="title.testresults"/>",
        receipt:"<fmt:message key="label.receipt"/>",
        errorEmptyValue:"<fmt:message key="error.ValueCannotBeEmpty"/>"
	};

    with(milonic=new menuname("receiptMenu")){
     top="offset=2"
     style = contextStyle;
     margin=3
     aI("text=<fmt:message key="pickingqc.viewaddreceipts"/>;url=javascript:showReceiptDocuments();");
    }

    drawMenus();

</script>
</head>

<body bgcolor="#ffffff" onload="testRequestEntryOnLoad();">
	<div id="transitPage" style="display: none;" class="optionTitleBoldCenter">
		 <br/><br/><br/><fmt:message key="label.pleasewait" /> <br/><br/><br/>
		<img src="/images/rel_interstitial_loading.gif" align="middle" />
	</div>
<div id="resultGridDiv" style="display:"> 
<div class="interface" id="resultsPage" style="">

	<!-- There exists only one form on the page, but the form has several sections. -->
	<tcmis:form action="/testrequestform.do" onsubmit="return submitSearchOnlyOnce();">
		<div class="backgroundContent">
			<!-- Data Entry sections for submitting a new test request. -->
			<table id="testRequestTable" width="100%" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td>
						<!--  This div, roundContent, contains the table defining the layout of the form fields. -->
						<c:if test="${empty testrequestbean}" >			
							<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableNoData" id="resultsPageTable">
								<tr>
									<td width="100%">
									    <c:if test="${!empty createError}" >
										    ${createError}
										</c:if>
										<c:if test="${empty createError}" >
                                            <fmt:message key="main.nodatafound"/>
                                        </c:if>
									</td>
								</tr>
							</table>
						</c:if>
						<c:if test="${!empty testrequestbean}">
						<c:set var="haslabPersonnelPermission" value='N'/>
						<tcmis:inventoryGroupPermission indicator="true" userGroupId="labPersonnel" inventoryGroup="${testrequestbean.inventoryGroup}">
		   	                <c:set var="haslabPersonnelPermission" value='Y'/>
		                 </tcmis:inventoryGroupPermission>
		                 <c:set var="hasreceivingQcPermission" value='N'/>
						<tcmis:inventoryGroupPermission indicator="true" userGroupId="ReceivingQC" inventoryGroup="${testrequestbean.inventoryGroup}">
		   	                <c:set var="hasreceivingQcPermission" value='Y'/>
		                 </tcmis:inventoryGroupPermission>
		                 <c:set var="hascatalogPermission" value='N'/>
						<tcmis:catalogPermission indicator="true" userGroupId="labPersonnel" catalogId="${testrequestbean.catalogId}">
		   	                <c:set var="hascatalogPermission" value='Y'/>
		                 </tcmis:catalogPermission>
							<div id="generalSectionDiv">
								  <div class="roundcont contentContainer">
									<div class="roundright">
										<div class="roundtop">
											<div class="roundtopright"><img src="/images/rndBoxes/borderTL.gif" alt="" width="15" height="15" class="corner" style="display: none" /></div>
										</div>
										<div class="roundContent">
										<div class="boxhead"> 
											<%-- 
												Place all update links in divs that can be turned on or off depending on the search criteria that is selected
											    Do not remove mainUpdateLinks div, even if you don't have any links. You can remove content inside the div but not the div itself.
											--%>
											          <div id="mainUpdateLinks">	
											            	   <c:if test="${(testrequestbean.requestStatus != 'Complete') && (testrequestbean.requestStatus != 'Cancelled')}">
																  <c:if test="${((testrequestbean.requestStatus == 'New') && (hasreceivingQcPermission == 'Y')) ||
															                ((testrequestbean.requestStatus == 'To Lab') && (haslabPersonnelPermission == 'Y' || hascatalogPermission == 'Y')) ||
															                ((testrequestbean.requestStatus == 'In Test') && (haslabPersonnelPermission == 'Y'|| hascatalogPermission == 'Y')) ||
															                ((testrequestbean.requestStatus == 'QC') && (hasreceivingQcPermission == 'Y'))}">
															     <span id="updateResultLink"> <a href="#" onclick="submitUpdateTestRequest();"><fmt:message key="label.update" /></a></span>
															    <span id="sign">| <a href="#" onclick="signTestRequest();"><fmt:message key="label.sign" /></a></span>
															  </c:if>
															  <c:if test="${(testrequestbean.requestStatus != 'Complete') && (hasreceivingQcPermission == 'Y') && (testrequestbean.requestStatus != 'Cancelled')}">
															  <span id="cancel">| <a href="#" onclick="cancelTestRequest();"><fmt:message key="label.cancel" /></a></span>
															  </c:if>
														 </c:if>	
																							  												  							
												</div>
												
											</div>
										<table id="general" width="100%" border="0" cellpadding="0" cellspacing="0" class="tableSearch">
												<tr>
													<td colspan="4">
														<div class="boxhead"> 
															<fmt:message key="title.generalinformation"/><BR>
														</div>
													</td>
												</tr>				
												<tr>
													<td width="15%" class="optionTitleBoldRight"><fmt:message key="label.requestno" />&nbsp;:&nbsp;</td>
													<td width="5%" class="optionTitleLeft">${testrequestbean.testRequestId}</td>
													<td width="5%" class="optionTitleBoldRight"><fmt:message key="label.status"/>&nbsp;:&nbsp;</td>
													<td width="5%" class="optionTitleLeft">${testrequestbean.requestStatus}</td>
                                                    <td width="5%" class="optionTitleBoldRight"><fmt:message key="label.requestor"/>&nbsp;:&nbsp;</td>
													<td width="20%" class="optionTitleLeft">${testrequestbean.createSignature}</td>
                                                    <td width="5%" class="optionTitleBoldRight"><fmt:message key="label.frequency"/>&nbsp;:&nbsp;</td>
													<td class="optionTitleLeft">${testrequestbean.frequency}</td>
                                                </tr>
												<tr>
													<td nowrap class="optionTitleBoldRight"><fmt:message key="label.facility" />&nbsp;:&nbsp;</td>
													<td class="optionTitleLeft" colspan="3">${testrequestbean.facility}</td>					
													<td class="optionTitleBoldRight"><fmt:message key="label.partno."/>&nbsp;:&nbsp;</td>
													<td class="optionTitleLeft" colspan="3">${testrequestbean.catPartNo}</td>
												</tr>
												<tr>
													<td nowrap class="optionTitleBoldRight"><fmt:message key="label.partdescription" />&nbsp;:&nbsp;</td>
													<td class="optionTitleLeft" colspan="7">${testrequestbean.partDescription}</td>
												</tr>
												<tr>
													<td nowrap class="optionTitleBoldRight"><fmt:message key="label.specification" />&nbsp;:&nbsp;</td>
													<td class="optionTitleLeft" colspan="7">${testrequestbean.specList}</td>
												</tr>
												<tr>
													<td class="optionTitleBoldRight"><fmt:message key="report.label.itemDescription"/>&nbsp;:&nbsp;</td>
													<td class="optionTitleLeft" colspan="7">${testrequestbean.itemId} - ${testrequestbean.itemDescription}</td>
												</tr>
                                                <tr>
													<td class="optionTitleBoldRight"><fmt:message key="label.samplesize"/>&nbsp;:&nbsp;</td>
													<c:choose>
														  <c:when test="${(hasreceivingQcPermission == 'N') || (testrequestbean.requestStatus == 'In Test') || (testrequestbean.requestStatus == 'QC') || (testrequestbean.requestStatus == 'Complete')}" >
														   <td class="optionTitleLeft" colspan="3">${testrequestbean.sampleSize}</td>
														  </c:when>
														  <c:otherwise>
														    <td class="optionTitleLeft" colspan="3"><input name="sampleSize" id="sampleSize" type="text" class="inputBox"
															value="<tcmis:inputTextReplace value="${testrequestbean.sampleSize}"/>"
															size="50" maxlength="50"/></td>
														</c:otherwise>
														</c:choose>
												</tr>

                                            </table>
										</div>
									<div id="footer" class="messageBar"></div>
								  </div>
									<div class="roundbottom">
										<div class="roundbottomright"><img src="/images/rndBoxes/borderBL.gif" alt="" width="15" height="15" class="corner" style="display: none" /></div>
									</div>
								</div>	
															
							    </div>

                            <table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableSearch">
								<tr id="showCocDivRow" style="display: none;">	
									<td class="optionTitleBoldLeft"><fmt:message key="title.chainofcustody" /></td>
									<td colspan="6">&nbsp;</td>	
									<td class="optionTitleBoldRight" onclick="toggleVisibility({on: ['chainOfCustodyDiv', 'hideCocDivRow'], off: ['showCocDivRow']});">
										<img src="/dhtmlxLayout/codebase/imgs/dhxlayout_dhx_blue/dhxlayout_ver1b.gif" />
									</td>	
								</tr>	
								<tr id="hideCocDivRow">
									<td colspan="7">&nbsp;</td>	
									<td class="optionTitleBoldRight" onclick="toggleVisibility({on: ['showCocDivRow'], off: ['chainOfCustodyDiv', 'hideCocDivRow']});">
										<img src="/dhtmlxLayout/codebase/imgs/dhxlayout_dhx_blue/dhxlayout_ver1t.gif" />
									</td>	
								</tr>	
							</table>								
							<div id="chainOfCustodyDiv">
								<div class="roundcont contentContainer">
									<div class="roundright">
									<div class="roundtop"><div class="roundtopright"><img src="/images/rndBoxes/borderTL.gif" alt="" width="15" height="15" class="corner" style="display: none" /></div></div>
										<div class="roundContent">
                                            <table id="chainOfCustodyOutter" width="100%" border="0" cellpadding="0" cellspacing="0" class="tableSearch">
                                                <tr>
													<td width="60%">
                                                        <table id="chainOfCustody" width="100%" border="0" cellpadding="0" cellspacing="0" class="tableSearch">
                                                                <tr>
                                                                    <td colspan="4">
                                                                        <div class="boxhead">
                                                                            <fmt:message key="title.chainofcustody"/><BR>
                                                                        </div>
                                                                    </td>
                                                                </tr>
                                                                <tr>
                                                                    <td width="15%" class="optionTitleBoldRight"><fmt:message key="label.datesenttolab"/>&nbsp;:&nbsp;</td>
                                                                    <c:choose>
                                                                          <c:when test="${(hasreceivingQcPermission == 'N') || (testrequestbean.requestStatus == 'In Test') || (testrequestbean.requestStatus == 'QC') || (testrequestbean.requestStatus == 'Complete')}" >
                                                                          <input type="hidden" name="dateToLab" id="dateToLab" value="<fmt:formatDate value="${testrequestbean.dateToLab}" pattern="${dateFormatPattern}"/>"/>
                                                                           <td  width="5%" class="optionTitleLeft"><fmt:formatDate value="${testrequestbean.dateToLab}" pattern="${dateFormatPattern}"/></td>
                                                                          </c:when>
                                                                          <c:otherwise>
                                                                            <td  width="5%" class="optionTitleLeft"><input type="text" name="dateToLab" id="dateToLab" readonly
                                                                            class="inputBox pointer"  style="cursor:pointer;" value="<fmt:formatDate value="${testrequestbean.dateToLab}" pattern="${dateFormatPattern}"/>"
                                                                            onclick="return getCalendar(document.getElementById('dateToLab'),document.genericForm.date30,null,null,document.genericForm.today);"
                                                                            size="10"></td>
                                                                          </c:otherwise>
                                                                        </c:choose>

                                                                    <td  width="10%" class="optionTitleBoldRight"><fmt:message key="label.signed"/>&nbsp;:&nbsp;</td>
                                                                    <td  width="10%" class="optionTitleLeft">${testrequestbean.toLabSignature}</td>
                                                                </tr>
                                                                <tr>
                                                                    <td class="optionTitleBoldRight"><fmt:message key="label.datesamplesreceived"/>&nbsp;:&nbsp;</td>
                                                                    <c:choose>
                                                                      <c:when test="${((haslabPersonnelPermission == 'N') && (hascatalogPermission == 'N')) || (testrequestbean.requestStatus == 'In Test') || (testrequestbean.requestStatus == 'QC') || (testrequestbean.requestStatus == 'Complete')}" >
                                                                          <input type="hidden" name="dateReceivedByLab" id="dateReceivedByLab" value="<fmt:formatDate value="${testrequestbean.dateReceivedByLab}" pattern="${dateFormatPattern}"/>"/>
                                                                           <td class="optionTitleLeft"><fmt:formatDate value="${testrequestbean.dateReceivedByLab}" pattern="${dateFormatPattern}"/></td>
                                                                        </c:when>
                                                                     <c:otherwise>
                                                                        <td class="optionTitleLeft">
                                                                            <input type="text" name="dateReceivedByLab" id="dateReceivedByLab" readonly
                                                                            class="inputBox pointer"  style="cursor:pointer;" value="<fmt:formatDate value="${testrequestbean.dateReceivedByLab}" pattern="${dateFormatPattern}"/>"
                                                                            onclick="return getCalendar(document.getElementById('dateReceivedByLab'),null,null,document.getElementById('dateToLab'),document.genericForm.today);"
                                                                            size="10"
                                                                            ></input>
                                                                        </td>
                                                                     </c:otherwise>
                                                                    </c:choose>
                                                                    <td class="optionTitleBoldRight"><fmt:message key="label.signed"/>&nbsp;:&nbsp;</td>
                                                                    <td class="optionTitleLeft">${testrequestbean.receivedSignature}</td>
                                                                </tr>
                                                                <tr>
                                                                    <td class="optionTitleBoldRight"><fmt:message key="label.datetestscompleted"/>&nbsp;:&nbsp;</td>
                                                                        <c:choose>
                                                                          <c:when test="${((haslabPersonnelPermission == 'N') && (hascatalogPermission == 'N')) || (testrequestbean.requestStatus == 'To Lab') || (testrequestbean.requestStatus == 'QC') || (testrequestbean.requestStatus == 'Complete')}" >
                                                                          <input type="hidden" name="dateTestComplete" id="dateTestComplete" value="<fmt:formatDate value="${testrequestbean.dateTestComplete}" pattern="${dateFormatPattern}"/>"/>
                                                                           <td class="optionTitleLeft"><fmt:formatDate value="${testrequestbean.dateTestComplete}" pattern="${dateFormatPattern}"/></td>
                                                                          </c:when>
                                                                          <c:otherwise>
                                                                             <td class="optionTitleLeft"><input type="text" name="dateTestComplete" id="dateTestComplete" readonly
                                                                             class="inputBox pointer"  style="cursor:pointer;" value="<fmt:formatDate value="${testrequestbean.dateTestComplete}" pattern="${dateFormatPattern}"/>"
                                                                             onclick="return getCalendar(document.getElementById('dateTestComplete'),null,null,document.getElementById('dateToLab'),document.genericForm.today);"
                                                                             size="10">
                                                                            </td>
                                                                           </c:otherwise>
                                                                        </c:choose>

                                                                    <td class="optionTitleBoldRight"><fmt:message key="label.signed"/>&nbsp;:&nbsp;</td>
                                                                    <td class="optionTitleLeft">${testrequestbean.testCompletedSignature}</td>
                                                                </tr>
                                                                <tr>
                                                                    <td class="optionTitleBoldRight"><fmt:message key="label.materialqualified"/>&nbsp;:&nbsp;</td>
                                                                    <c:choose>
                                                                          <c:when test="${((haslabPersonnelPermission == 'N') && (hascatalogPermission == 'N')) || (testrequestbean.requestStatus == 'To Lab') || (testrequestbean.requestStatus == 'QC') || (testrequestbean.requestStatus == 'Complete')}" >
                                                                           <td colspan="3" class="optionTitleLeft">${testrequestbean.materialQualified}
                                                                          </c:when>
                                                                          <c:otherwise>
                                                                           <td colspan="3" class="optionTitleLeft">
                                                                             <select name="materialQualified" class="selectBox" id="materialQualified">
                                                                               <option value="" <c:if test="${testrequestbean.materialQualified == ''}"> selected </c:if>><fmt:message key="label.pleaseselect"/> </option>
                                                                               <option value="Y" <c:if test="${testrequestbean.materialQualified == 'Y'}"> selected </c:if>> <fmt:message key="label.yes"/> </option>
                                                                               <option value="N" <c:if test="${testrequestbean.materialQualified == 'N'}"> selected </c:if>> <fmt:message key="label.no"/> </option>
                                                                            </select>
                                                                          </c:otherwise>
                                                                        </c:choose>
                                                                    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<b><fmt:message key="label.datewithexpiry"/></b>&nbsp;:&nbsp;
                                                                    <fmt:formatDate var="fmtExpireDate" value="${testrequestbean.expirationDate}" pattern="MM/dd/yyyy"/>
                                                                            <c:choose>
                                                                              <c:when test="${fmtExpireDate == '01/01/3000'}" >
                                                                                 <c:set var="formattedExpirationDate">
                                                                                        <fmt:message key="label.indefinite"/>
                                                                                </c:set>
                                                                              </c:when>
                                                                              <c:otherwise>
                                                                                 <c:set var="formattedExpirationDate">
                                                                                   <fmt:formatDate value="${testrequestbean.expirationDate}" pattern="${dateFormatPattern}"/>
                                                                                 </c:set>
                                                                              </c:otherwise>
                                                                            </c:choose>
                                                                      <c:choose>
                                                                          <c:when test="${((haslabPersonnelPermission == 'N') && (hascatalogPermission == 'N')) || (testrequestbean.requestStatus == 'To Lab') || (testrequestbean.requestStatus == 'QC') || (testrequestbean.requestStatus == 'Complete')}" >
                                                                               ${formattedExpirationDate}
                                                                          </c:when>
                                                                          <c:otherwise>
                                                                                <input type="hidden" name="expirationDate" id="expirationDate" value="<fmt:formatDate value="${testrequestbean.expirationDate}" pattern="${dateFormatPattern}"/>"/>
                                                                                <input type="text" name="expireDate" id="expireDate" readonly
                                                                                class="inputBox pointer"  style="cursor:pointer;" value="${formattedExpirationDate}"
                                                                                onclick="return getCalendar(document.getElementById('expireDate'),null,null,document.genericForm.todayoneyear,null,'Y');"
                                                                                onchange="expiredDateChanged();"
                                                                                size="10">
                                                                          </c:otherwise>
                                                                        </c:choose>
                                                                    </td>
                                                                </tr>
                                                                <tr>
                                                                    <td class="optionTitleBoldRight"><fmt:message key="label.qualitynote"/>&nbsp;:&nbsp;</td>
                                                                    <c:choose>
                                                                          <c:when test="${((haslabPersonnelPermission == 'N') && (hascatalogPermission == 'N')) || (testrequestbean.requestStatus == 'To Lab') || (testrequestbean.requestStatus == 'QC') || (testrequestbean.requestStatus == 'Complete')}" >
                                                                           <td colspan="3" class="optionTitleLeft">${testrequestbean.qualityTrackingNumber}</td>
                                                                          </c:when>
                                                                          <c:otherwise>
                                                                            <td colspan="3" class="optionTitleLeft"><input name="qualityTrackingNumber" id="qualityTrackingNumber" type="text" class="inputBox"
                                                                            value="<tcmis:inputTextReplace value="${testrequestbean.qualityTrackingNumber}"/>"
                                                                            size="35" maxlength="50"/></td>
                                                                        </c:otherwise>
                                                                        </c:choose>
                                                                </tr>
                                                                <tr>
                                                                    <td class="optionTitleBoldRight"><fmt:message key="label.closedate"/>&nbsp;:&nbsp;</td>
                                                                     <c:choose>
                                                                          <c:when test="${((hasreceivingQcPermission == 'Y') && (testrequestbean.requestStatus == 'QC'))}" >
                                                                          <td class="optionTitleLeft"><input type="text" name="closedDate" id="closedDate" readonly
                                                                             class="inputBox pointer"  style="cursor:pointer;" value="<fmt:formatDate value="${testrequestbean.closedDate}" pattern="${dateFormatPattern}"/>"
                                                                             onclick="return getCalendar(document.getElementById('closedDate'),null,null,document.getElementById('dateTestComplete'),document.genericForm.today);"
                                                                             size="10">
                                                                            </td>
                                                                           </c:when>
                                                                          <c:otherwise>
                                                                            <input type="hidden" name="closedDate" id="closedDate" value="<fmt:formatDate value="${testrequestbean.closedDate}" pattern="${dateFormatPattern}"/>"/>
                                                                           <td class="optionTitleLeft"><fmt:formatDate value="${testrequestbean.closedDate}" pattern="${dateFormatPattern}"/></td>
                                                                           </c:otherwise>
                                                                        </c:choose>
                                                                    <td class="optionTitleBoldRight"><fmt:message key="label.signed"/>&nbsp;:&nbsp;</td>
                                                                    <td class="optionTitleLeft" >${testrequestbean.closedSignature}</td>
                                                                </tr>

                                                        </table>
                                                    </td>
                                                    <td width="40%">
                                                        <table id="chainOfCustodyCancel" width="100%" border="0" cellpadding="0" cellspacing="0" class="tableSearch">
                                                            <c:if test="${((hasreceivingQcPermission == 'Y') && (testrequestbean.requestStatus != 'Complete'))}" >
                                                                <tr>
                                                                    <td class="optionTitleBoldRight"><fmt:message key="label.datecancelled"/>&nbsp;:&nbsp;</td>
                                                                     <c:choose>
                                                                          <c:when test="${((hasreceivingQcPermission == 'Y') && (testrequestbean.requestStatus != 'Complete'))}" >
                                                                          <td class="optionTitleLeft"><input type="text" name="dateCancelled" id="dateCancelled" readonly
                                                                             class="inputBox pointer"  style="cursor:pointer;" value="<fmt:formatDate value="${testrequestbean.dateCancelled}" pattern="${dateFormatPattern}"/>"
                                                                             onclick="return getCalendar(document.getElementById('dateCancelled'),null,null,null,document.genericForm.today);"
                                                                             size="10">
                                                                            </td>
                                                                           </c:when>
                                                                          <c:otherwise>
                                                                            <input type="hidden" name="dateCancelled" id="dateCancelled" value="<fmt:formatDate value="${testrequestbean.dateCancelled}" pattern="${dateFormatPattern}"/>"/>
                                                                           <td class="optionTitleLeft"><fmt:formatDate value="${testrequestbean.dateCancelled}" pattern="${dateFormatPattern}"/></td>
                                                                           </c:otherwise>
                                                                        </c:choose>
                                                                </tr>
                                                                <tr>
                                                                    <td class="optionTitleBoldRight"><fmt:message key="label.cancelledby"/>&nbsp;:&nbsp;</td>
                                                                    <td class="optionTitleLeft" >${testrequestbean.cancelledByName}</td>
                                                                </tr>
                                                                <tr>
                                                                    <td class="optionTitleBoldRight"><fmt:message key="label.reasonforcancel"/>&nbsp;:&nbsp;</td>
                                                                    <td class="optionTitleLeft" colspan="3"><textarea class="inputBox" name="reasonForCancellation" id="reasonForCancellation" rows="4" cols="50"><c:out value="${testrequestbean.reasonForCancellation}"/></textarea></td>
                                                                </tr>
                                                            </c:if>
                                                        </table>
                                                    </td>
                                                </tr>
                                            </table>
                                        </div>
									<div id="footer" class="messageBar"></div>
								</div>
									<div class="roundbottom"><div class="roundbottomright"><img src="/images/rndBoxes/borderBL.gif" alt="" width="15" height="15" class="corner" style="display: none" /></div></div>
								</div>
							</div>

                        <!-- CSS Tabs -->
                        <div id="marsTabs" class="haasTabs">
                         <ul id="mainTabList">
                         </ul>
                        </div>
                        <!-- CSS Tabs End -->
                        <div id="section1" class="roundcont filterContainer">
                             <div class="roundright">
                               <div class="roundtop">
                                 <div class="roundtopright"> <img src="/images/rndBoxes/borderTL_filter.gif" alt="" width="15" height="10" class="corner_filter" style="display: none" /></div>
                               </div>
                               <div class="roundContent">
                                <table id="TestResults" width="100%" border="0" cellpadding="0" cellspacing="0" class="tableSearch">
									<tr>
										<td>
                                            <div id="tabsdiv">
				                                <c:set var="tabCount" value='0'/>
                                                <div id="newTab${tabCount}">
                                                    <div class="dataContent" id="testResultsDisplay" style="width:1000px;height:300px" style="display: none;"></div>
                                                    <c:if test="${testrequestbean.testResults != null}" >
                                                        <script type="text/javascript">
                                                        <!--
                                                            <%@ include file="/hub/labtestresults.jsp" %>
                                                        // -->
                                                        </script>
                                                    </c:if>
                                                </div>

                                                <c:set var="tabCount" value='${tabCount+1}'/>
                                                <div id="newTab${tabCount}">
                                                    <div class="dataContent" id="receiptDataDiv" style="width:1000px;height:300px;" style="display: none;"></div>
                                                    <c:if test="${testrequestbean.labTestReceiptColl != null}" >
                                                        <script type="text/javascript">
                                                        <!--
                                                            <%@ include file="/hub/labtestreceiptdata.jsp" %>
                                                        // -->
                                                        </script>
                                                    </c:if>
                                                </div>
                                        </div>
                                      </td>
									</tr>
								</table>
							   </div>
                               <div class="roundbottom">
                                 <div class="roundbottomright"> <img src="/images/rndBoxes/borderBL.gif" alt="" width="15" height="15" class="corner" style="display: none" /> </div>
                               </div>
                             </div>
                            </div>
						
						</c:if>
					</td>
				</tr>
			</table>
        </div>

    <!-- Hidden elements are used to persist form state values. -->
    <div id="hiddenElements" style="display: none;">
        <input type="hidden" name="uAction" id="uAction" value="search" />
        <input name="startSearchTime" id="startSearchTime" type="hidden" value="" />
        <input name="searchHeight" id="searchHeight" type="hidden" value="1000" />
        <input name="endSearchTime" id="endSearchTime" type="hidden" value="" />
        <input name="totalLines" id="totalLines" type="hidden" value="${fn:length(testrequestbean.testResults)}" />
        <input name="catalogId" id="catalogId" type="hidden" value="${testrequestbean.catalogId}" />
        <input name="companyId" id="companyId" type="hidden" value="${testrequestbean.companyId}" />
        <input name="inventoryGroup" id="inventoryGroup" type="hidden" value="${testrequestbean.inventoryGroup}" />
        <input name="facilityId" id="facilityId" type="hidden" value="${testrequestbean.facilityId}" />
        <input name="receiptId" id="receiptId" type="hidden" value="${testrequestbean.receiptId}" />
        <input name="testRequestId" id="testRequestId" type="hidden" value="${testrequestbean.testRequestId}" />
        <input name="createSignature" id="createSignature" type="hidden" value="${testrequestbean.createSignature}" />
        <input name="requestStatus" id="requestStatus" type="hidden" value="${testrequestbean.requestStatus}" />
        <input name='date30' id='date30' type="hidden" value='<tcmis:getDateTag numberOfDaysFromToday="-30" datePattern="${dateFormatPattern}"/>'  />
        <input name='today' id='today' type="hidden" value='<tcmis:getDateTag numberOfDaysFromToday="0" datePattern="${dateFormatPattern}"/>'  />
        <input name='todayoneyear' id='todayoneyear' type="hidden" value='<tcmis:getDateTag numberOfDaysFromToday="-365" datePattern="${dateFormatPattern}"/>'  />
        <input name='indefiniteDate' id='indefiniteDate' type="hidden" value=''  />
        <input name="errorMsg" id="errorMsg" type="hidden" value="${errorMsg}" />
    </div>
</div>
</tcmis:form>



<!-- close of interface -->
</div>

	 <%-- message --%>
<script type="text/javascript">
<!--
/*Check if there is any error messages to show and set the variable you use in javascript to true or false.*/
<c:choose>
   <c:when test="${empty errorMsg}">
    showErrorMessage = false;
   </c:when>
   <c:otherwise>
    showErrorMessage = true;
   </c:otherwise>
</c:choose>
//-->
</script>
<!-- Error Messages Ends -->


<!-- You can build your error messages below. Similar divs would have to be built to show any other messages in a inline pop-up.-->
<!-- Error Messages Begins -->
<div id="errorMessagesArea" class="errorMessages"
	style="display: none; overflow: auto;"></div>
</body>
</html:html>