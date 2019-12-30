<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

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
	
		<%@ include file="/common/locale.jsp" %> 						<%-- Sets locale --%>
		
		<tcmis:gridFontSizeCss /> <%-- Sets CSS based on the user's preffered font size and which application he is viewing --%>
		
		<script type="text/javascript" src="/js/common/formchek.js"></script>			<%-- Validation support --%>
		<script type="text/javascript" src="/js/common/commonutil.js"></script>
		<script type="text/javascript" src="/js/common/grid/iframegridresizemain.js"></script>	<%-- This handles all the resizing of the page and frames --%>
		<script type="text/javascript" src="/js/common/disableKeys.js"></script>		<%-- This disables various key press events --%>
		
		<!-- For Calendar support -->
		<script type="text/javascript" src="/js/calendar/newcalendar.js"></script>
		<script type="text/javascript" src="/js/calendar/AnchorPosition.js"></script>
		<script type="text/javascript" src="/js/calendar/PopupWindow.js"></script>
		
		<script type="text/javascript"src="/js/common/standardGridmain.js"></script> 
		<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxcommon.js"></script> 
		<script type="text/javascript" src="/dhtmlxLayout/codebase/dhtmlxlayout.js"></script> 
		<script type="text/javascript" src="/dhtmlxWindows/codebase/dhtmlxwindows.js"></script>

		<%-- Page specific javascript --%>
		<script type="text/javascript" src="/js/lmco/vocetchemicalmain.js"></script>

        <script type="text/javascript" src="/js/jquery/jquery-1.6.4.js"></script>
        <script type="text/javascript" src="/js/jquery/autocomplete.js"></script>
        <link rel="stylesheet" type="text/css" href="/css/autocomplete.css" />


        <title><fmt:message key="label.vocetchemical"/></title>
		
		<script language="JavaScript" type="text/javascript">
			<!-- <%-- NOTE: The only javascript here rather than in a specific js file should be javascript that contains values from JSP --%>

			<%-- Standard var for Internationalized messages--%>
			var messagesData = new Array();
			messagesData={
				alert:"<fmt:message key="label.alert"/>",
				and:"<fmt:message key="label.and"/>",
				all:"<fmt:message key="label.all"/>",
				pleasewaitmenu:"<fmt:message key="label.pleasewaitmenu"/>",
				validvalues:"<fmt:message key="label.validvalues"/>",
				submitOnlyOnce:"<fmt:message key="label.submitOnlyOnce"/>", 
				itemInteger:"<fmt:message key="error.item.integer"/>",
				xxpositiveinteger:"<fmt:message key="label.xxpositiveinteger"/>",
				errors:"<fmt:message key="label.errors"/>",
                missingSearchText:"<fmt:message key="receiptdocumentviewer.searchmessage"/>",            
                searchInput:"<fmt:message key="error.searchInput.integer"/>"
				};
	
	var vocetSourceArr = new Array();
	<c:forEach var="facilityBean" items="${myWorkareaFacilityViewBeanCollection}" varStatus="status"> 
	  <c:if test="${facilityBean.active == 'y'}"> 
		  vocetSourceArr['${facilityBean.facilityId}'] = new Array(
		  	{ 
		  	  value: '', 
		  	  text: ''
			}
		    <c:forEach var="sourceBean" items="${vocetSourceColl}" varStatus="status1">
		    <c:if test="${facilityBean.facilityId == sourceBean.facilityId}"> 
		     ,{ 
		  	  value: '${sourceBean.vocetSourceId}', 
		  	  text: '<tcmis:jsReplace value="${sourceBean.vocetSourceDesc}"/>'
			}
			</c:if>
		    </c:forEach>
		);
	  </c:if>
	</c:forEach>
	
	var vocetCategoryArr = new Array();
	<c:forEach var="facilityBean" items="${myWorkareaFacilityViewBeanCollection}" varStatus="status"> 
	  <c:if test="${facilityBean.active == 'y'}"> 
		  vocetCategoryArr['${facilityBean.facilityId}'] = new Array(
		  	{ 
		  	  value: '', 
		  	  text: ''
			}
		    <c:forEach var="categoryBean" items="${vocetCategoryColl}" varStatus="status1"> 
		    <c:if test="${facilityBean.facilityId == categoryBean.facilityId}"> 
		    ,{ 
		  	  value: '${categoryBean.vocetCategoryId}', 
		  	  text: '<tcmis:jsReplace value="${categoryBean.vocetCategoryDesc}"/>'
			}
			</c:if>
		    </c:forEach>
		);
	  </c:if>
	</c:forEach>
	
	var defaults = {
	   value:'',text:'<fmt:message key="label.all"/>'
    } 
    
    defaults.nodefault = true;
	
    j$().ready(function() {
        function log(event, data, formatted) {
            j$('#updatedBy').val(formatted.split(":")[0]);
            $("updatedByName").className = "inputBox";
        }

        j$("#updatedByName").autocomplete("getpersonneldata.do",{
                extraParams: {activeOnly: function() { return j$('#activeOnly').is(':checked'); },
                              companyId: function() { return j$("#companyId").val(); } },
                width: 200,
                max: 10,  // default value is 10
                cacheLength:0, // disable cache here because we put "rownum < max" for better performance. Cache will make data off.
                scrollHeight: 200,
                formatItem: function(data, i, n, value) {
                    return  value.split(":")[1].substring(0,40);
                },
                formatResult: function(data, value) {
                    return value.split(":")[1];
                }
        });

        j$('#updatedByName').bind('keyup',(function(e) {
              var keyCode = (e.keyCode ? e.keyCode : e.which);

              if( keyCode != 13 && keyCode != 9) // 13 is for Enter; 9 is for Tab
                invalidateUpdatedBy();
        }));


        j$("#updatedByName").result(log).next().click(function() {
            j$(this).prev().search();
        });

    });
    
function uploadData() {
	openWinGeneric("uploadvocetimport.do?uAction=showUploadList&facilityId="+$v('facilityId'),"uploadList","450","170","yes","80","80");
	showTransitWin(messagesData.pleasewait);
}

var dhxFreezeWins = null;
	
function initializeDhxWins() {
	if (dhxFreezeWins == null) {
		dhxFreezeWins = new dhtmlXWindows();
		dhxFreezeWins.setImagePath("/dhtmlxWindows/codebase/imgs/");
	}
}

function showTransitWin(messageType)
{
	var waitingMsg = messagesData.pleasewaitmenu+"...";
	$("transitLabel").innerHTML = waitingMsg;

	var transitDailogWin = document.getElementById("transitDailogWin");
	transitDailogWin.innerHTML = document.getElementById("transitDailogWinBody").innerHTML;
	transitDailogWin.style.display="";
//	alert('here');
	initializeDhxWins();
	if (!dhxFreezeWins.window("transitDailogWin")) {
		// create window first time
		transitWin = dhxFreezeWins.createWindow("transitDailogWin",0,0, 400, 200);
		transitWin.setText("");
		transitWin.clearIcon();  // hide icon
		transitWin.denyResize(); // deny resizing
		transitWin.denyPark();   // deny parking

		transitWin.attachObject("transitDailogWin");
		//transitWin.attachEvent("onClose", function(inputWin){inputWin.hide();});
		transitWin.center();
		// setting window position as default x,y position doesn't seem to work, also hidding buttons.
		transitWin.setPosition(328, 131);
		transitWin.button("minmax1").hide();
		transitWin.button("park").hide();
		transitWin.button("close").hide();
		transitWin.setModal(true);
	}else{
		// just show
		transitWin.setModal(true);  // freeze the window here
		dhxFreezeWins.window("transitDailogWin").show();
	}
}

function stopShowingWait() {
	if (dhxFreezeWins != null) {
		if (dhxFreezeWins.window("transitDailogWin")) {
			dhxFreezeWins.window("transitDailogWin").setModal(false);
			dhxFreezeWins.window("transitDailogWin").hide();
		}
	}
	return true;
}

var permissionArr = new Array();
<c:forEach var="facilityBean" items="${myWorkareaFacilityViewBeanCollection}" varStatus="status">
    <tcmis:facilityPermission indicator="true" userGroupId="VocetProperties" facilityId="${status.current.facilityId}">
		permissionArr['<tcmis:jsReplace value="${status.current.facilityId}"/>'] = 'Y';
	</tcmis:facilityPermission> 
</c:forEach>

            // -->
		 </script>
	</head>

	<%--call the loadLayoutWin() to set the sizes of the search section and initiate the layout. 
	If you dont want to use the layout set javascript variable useLayout=false;--%>
	<body bgcolor="#ffffff" onload="loadLayoutWin('','vocetChemical');facilityChanged();" onresize="resizeFrames()">
		<div class="interface" id="mainPage" style="">
		
<div id="transitDailogWin" class="errorMessages" style="display:none;left:20%;top:20%;z-index:5;">
</div>


<div id="transitDailogWinBody" class="errorMessages" style="display: none;">
	<table width="100%" border="0" cellpadding="2" cellspacing="1">
		<tr><td>&nbsp;</td></tr>
		<tr><td>&nbsp;</td></tr>
		<tr><td>&nbsp;</td></tr>
		<tr>
			<td align="center" id="transitLabel">
			</td>
		</tr>
		<tr>
			<td align="center">
				<img src="/images/rel_interstitial_loading.gif" align="middle">
			</td>
		</tr>
	</table>
</div>
		
			<%-- Search Section --%>
			<div id="searchFrameDiv">
				<div class="contentArea">
					<tcmis:form action="/vocetchemicalresults.do" onsubmit="return submitSearchOnlyOnce();" target="resultFrame">
						<table id="searchMaskTable" width="100%" border="0" cellpadding="0" cellspacing="0">
							<tr>
								<td>
								<div class="roundcont filterContainer">
									<div class="roundright">
										<div class="roundtop">
											<div class="roundtopright"><img src="/images/rndBoxes/borderTL_filter.gif" alt="" width="15" height="10" class="corner_filter" style="display: none" /></div>
											</div>
											<div class="roundContent"><%-- Insert all the search option within this div --%>
												<table width="500" border="0" cellpadding="0" cellspacing="0" class="tableSearch">
													<tr>
														<td nowrap width="20%" class="optionTitleBoldRight">
															<fmt:message key="label.facility" />:
														</td>
														<td width="20%" class="optionTitleBoldLeft" >
															<c:set var="selectedFacilityId" value="${personnelBean.myDefaultFacilityId}"/>
                                                            <select name="facilityId" id="facilityId" class="selectBox" onchange="facilityChanged()">
                                                              <c:forEach var="facilityBean" items="${myWorkareaFacilityViewBeanCollection}" varStatus="status">
                                                                <c:if test="${facilityBean.active == 'y'}">
                                                                    <option value="${status.current.facilityId}" <c:if test="${status.current.facilityId == selectedFacilityId}">selected</c:if>><c:out value="${status.current.facilityName}"/></option>
                                                                </c:if>
                                                              </c:forEach>
                                                            </select>
														</td>
														<td nowrap width="20%" class="optionTitleBoldRight">
															<fmt:message key="label.source" />:
														</td>
														<td width="20%" class="optionTitleBoldLeft" >
														    <select name="vocetSourceId" id="vocetSourceId" class="selectBox" >
															</select>
														</td>
														<td nowrap width="20%" class="optionTitleBoldRight">
															<fmt:message key="label.category" />:
														</td>
														<td width="20%" class="optionTitleBoldLeft" >
														    <select name="vocetCategoryId" id="vocetCategoryId" class="selectBox" >
															</select>
														</td>
													</tr>
													
													<tr>
														<td nowrap width="20%" class="optionTitleBoldRight">
															<fmt:message key="label.entrytype" />:
														</td>
														<td width="20%" class="optionTitleBoldLeft" >
                                                            <select name="entryType" class="selectBox" id="entryType">
												                <option value=""><fmt:message key="label.all" /></option>
												                <option value="M"><fmt:message key="label.manual" /></option>
												                <option value="I"><fmt:message key="label.import" /></option>
												            </select>
														</td>
														<td nowrap width="20%" class="optionTitleBoldRight">
															<fmt:message key="cyclecountresults.label.uploadid" />:
														</td>
														<td width="20%" class="optionTitleBoldLeft" >
														    <input class="inputBox" type="text" name="uploadSeqId" id="uploadSeqId" value='' size="25" />
														</td>
														<td nowrap width="20%" class="optionTitleBoldRight">
															<fmt:message key="label.entrydate" />:
														</td>
														<td width="20%" class="optionTitleBoldLeft" nowrap>
														    <input type="text" readonly name="entryStartDate" id="entryStartDate" onclick="return getCalendar(document.genericForm.entryStartDate,null,document.genericForm.entryEndDate,null,null,'N');" class="inputBox pointer" value='' maxlength="10" size="8"/>
												            &nbsp;<fmt:message key="label.to"/>&nbsp;
												            <input type="text" readonly name="entryEndDate" id="entryEndDate" onclick="return getCalendar(document.genericForm.entryEndDate,document.genericForm.entryStartDate,null,null,null,'N');" class="inputBox pointer" value='' maxlength="10" size="8"/>
														</td>
													</tr>
                                                    <%--
                                                    <tr>
                                                      <td width="10%" class="optionTitleBoldRight" nowrap><fmt:message key="label.lastUpdatedBy"/>:</td>
                                                      <td width="50%" nowrap>
                                                         <input type="text" id="updatedByName" value="<c:out value=''/>" class="inputBox" size="20" />
                                                         <input name="updatedBy" id="updatedBy" type="hidden" value="">
                                                         <input name="activeOnly" id="activeOnly" onclick="" type="checkbox" checked class="radioBtns" value="Y">
                                                          <fmt:message key="label.activeOnly"/>
                                                      </td>
                                                    </tr>
                                                    --%>
                                                    
                                                    <tr>
                                                        <td width="10%" class="optionTitleBoldRight" nowrap>
                                                            <fmt:message key="label.search"/>:&nbsp;
                                                        </td>
                                                        <td width="50%" class="optionTitleBoldLeft" nowrap colspan="5">
                                                            <select name="searchField" id="searchField" class="selectBox" onchange="showOrHideMsdsNoSpan();">
                                                                 <option value="material_id"><fmt:message key="label.materialid"/></option>
                                                                 <option value="msds_number"><fmt:message key="label.msds"/></option>
                                                                 <option value="cas_number"><fmt:message key="label.casno"/></option>
                                                                 <option value="preferred_name"><fmt:message key="label.chemicalname"/></option>
                                                            </select>
                                                            &nbsp;
                                                            <select name="matchType" class="selectBox" id="matchType" onchange="openMsdsNoTextArea(value);">
                                                                   <option value="=" selected> <fmt:message key="label.is"/>  </option>
                                                                   <option value="like"> <fmt:message key="label.contains"/>  </option>
                                                                   <option value="starts with"><fmt:message key="label.startswith"/> </option>
                                                                   <option value="ends with"><fmt:message key="label.endswith" /></option>
                                                                   <option value="in csv list"><fmt:message key="label.incsvlist" /></option>
                                                                   <option value="create list"><fmt:message key="label.createlist" /></option>
                                                            </select>
                                                            &nbsp;
                                                            <input name="searchText" id="searchText" type="text" class="inputBox" onkeypress="onKeySearch1(event,submitSearchForm);" size="45" value="">&nbsp;
                                                        </td>
                                                    </tr>

                                                    <tr>
														<td class="optionTitleBoldLeft" colspan="8">
															<input name="submitSearch" id="submitSearch" type="button" value="<fmt:message key="label.search"/>" 
																class="inputBtns" onmouseover="this.className='inputBtns inputBtnsOver'"
																onmouseout="this.className='inputBtns'" onclick="return submitSearchForm();">
															<input name="xslButton" type="button" class="inputBtns" value="<fmt:message key="label.createExcel"/>" id="xslButton" onmouseover="this.className='inputBtns inputBtnsOver'" onMouseout="this.className='inputBtns'" 
																onclick= "return createXSL()"/>
															<input name="uploadB" id="uploadB" type="button"
							                                    class="inputBtns" value="<fmt:message key="label.uploadvocetdata"/>"
							                                    onmouseover="this.className='inputBtns inputBtnsOver'"
							                                    onmouseout="this.className='inputBtns'"
							                                    onclick="uploadData(); return false;">
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
						<c:if test="${requestScope['org.apache.struts.action.ERROR'] != null}">
							<div class="spacerY">&nbsp;
								<div id="searchErrorMessagesArea" class="errorMessages">
									<html:errors />
								</div>
							</div>
						</c:if>
						<div id="hiddenElements" style="display: none;">
							<input name="uAction" id="uAction" type="text" value=""> 
							<input name="startSearchTime" id="startSearchTime" type="hidden" value="">
							<%-- To get the correct height value to insert, insert showSearchHeight = true; anywhere in the JavaScript section in main.jsp.  --%> 
							<input name="searchHeight" id="searchHeight" type="hidden" value="214">
                            <input name="maxData" id="maxData" type="hidden" value="500"/>
                            <input name="companyId" id="companyId" type="hidden" value="${personnelBean.companyId}">
                        </div>
					</tcmis:form>
				</div>
			</div>
			<div id="resultFrameDiv" style="margin: 4px 4px 0px 4px;">
				<!-- Transit Page Begins -->
				<div id="transitPage" style="display: none;" class="optionTitleBoldCenter">
					<br><br><br><fmt:message key="label.pleasewait" /> <br><br><br><img src="/images/rel_interstitial_loading.gif" align="middle">
				</div>
				<!-- Transit Page Ends -->
				<div id="resultGridDiv" style="display: none;"><!-- Search results start --> <!-- If you want your results section to span only 50% set this to 50% or 800 pixels. Make sure to set resizeGridWithWindow to false. -->
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
											<div class="boxhead">
												<%-- Place all update links in divs that can be turned on or off depending on the search criteria that is selected
										          	Do not remove mainUpdateLinks div, even if you don't have any links. You can remove content inside the div but not the div itself.
										      		--%>
												<div id="mainUpdateLinks" style="display: none">
													<div id="updateResultLink" style="display: none">
														<a href="#" onclick="resultFrame.updateVocetChemical();"><fmt:message key="label.update" /></a>
													</div>
												</div> <%-- mainUpdateLinks Ends --%>
											</div> <%-- boxhead Ends --%>
									
											<div class="dataContent">
												<iframe scrolling="no" id="resultFrame" name="resultFrame" width="100%" height="" frameborder="0" marginwidth="0" src="/blank.html"></iframe>
											</div>
											<%-- result count and time --%>
											<div id="footer" class="messageBar">
											</div>
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
			</div>
		</div>

        <%-- Error Messages Div --%>
		<div id="errorMessagesArea" class="errorMessages" style="display: none; overflow: auto;"></div>

    </body>
</html:html>