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
		<link rel="shortcut icon" href="/images/buttons/tcmIS.ico"></link>
	
		<%@ include file="/common/locale.jsp" %> 						<%-- Sets locale --%>
		
		<tcmis:gridFontSizeCss /> <%-- Sets CSS based on the user's preffered font size and which application he is viewing --%>
		
		<script type="text/javascript" src="/js/common/formchek.js"></script>			<%-- Validation support --%>
		<script type="text/javascript" src="/js/common/opshubig.js"></script>			<%-- sets up data for OpsEntity/hub/IG pulldowns --%>
		<script type="text/javascript" src="/js/common/commonutil.js"></script>
		<script type="text/javascript" src="/js/common/grid/iframegridresizemain.js"></script>	<%-- This handles all the resizing of the page and frames --%>
		<script type="text/javascript" src="/js/common/disableKeys.js"></script>		<%-- This disables various key press events --%>		
		<script type="text/javascript"src="/js/common/standardGridmain.js"></script> 
		<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxcommon.js"></script> 
		<script type="text/javascript" src="/dhtmlxLayout/codebase/dhtmlxlayout.js"></script> 
		<script type="text/javascript" src="/dhtmlxWindows/codebase/dhtmlxwindows.js"></script>
		

		<%-- Page specific javascript --%>
		<script type="text/javascript" src="/js/hub/CMSPricelistMain.js"></script> 

	<style>
		.inputfile {
		    width: 0.1px;
		    height: 0.1px;
		    opacity: 0;
		    overflow: hidden;
		    position: absolute;
		    z-index: -1;
		}
		
		.inputfile + label {
		    text-overflow: ellipsis;
		    cursor: pointer;
		    display: inline;
		    overflow: hidden;
			color: #0000EE;
		}

		.inputfile:hover + label {
		    text-decoration: underline;
		} 
		
	</style>		
		
		<title><fmt:message key="CMSPricelist"/></title>
		
		<script language="JavaScript" type="text/javascript">

			<%-- Standard var for Internationalized messages--%>
			var messagesData = new Array();
			messagesData={
				alert:"<fmt:message key="label.alert"/>",
				and:"<fmt:message key="label.and"/>",
				all:"<fmt:message key="label.all"/>",
				validvalues:"<fmt:message key="label.validvalues"/>",
				submitOnlyOnce:"<fmt:message key="label.submitOnlyOnce"/>", 
				itemInteger:"<fmt:message key="error.item.integer"/>",
				errors:"<fmt:message key="label.errors"/>",     
				searchInput:"<fmt:message key="error.searchInput.integer"/>"
				};
			<c:set var="curCompany" value="-1"/>
			var priceGroupCompanies = 
				[<c:forEach var="priceGroup" items="${priceGroups}" varStatus="status">
					<c:choose>
						<c:when test="${priceGroup.companyId != curCompany}">
							<c:if test='${curCompany != "-1"}'>				]
							},</c:if>
							<c:set var="curCompany" value="${priceGroup.companyId}"/>
							{
								id: '${priceGroup.companyId}',
								name: '<tcmis:jsReplace value="${priceGroup.companyName}"/>',
								priceGroups: [
										{
										id: '${priceGroup.priceGroupId}',
										name: '<tcmis:jsReplace value="${priceGroup.priceGroupName}"/>'
										}
						</c:when>
						<c:otherwise>
										,
										{
											id: '${priceGroup.priceGroupId}',
											name: '<tcmis:jsReplace value="${priceGroup.priceGroupName}"/>'
										}
						</c:otherwise>
					</c:choose><c:if test="${status.last}">
								]
							}
				</c:if>
				</c:forEach>]; 		
			
			
			// -->
		 </script>
	</head>

	<%--call the loadLayoutWin() to set the sizes of the search section and initiate the layout. 
	If you dont want to use the layout set javascript variable useLayout=false;--%>
	<body bgcolor="#ffffff" onload="loadLayoutWin('','CMSPricelist');setCompanies();" onresize="resizeFrames()">
		<div class="interface" id="mainPage" style="">
			<%-- Search Section --%>
			<div id="searchFrameDiv">
				<div class="contentArea">
					<tcmis:form action="/CMSPricelist.do" onsubmit="return submitSearchOnlyOnce();" target="resultFrame">
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
														<td nowrap width="20%" class="optionTitleBoldRight" nowrap>
															<fmt:message key="label.company" />:
														</td>
														<td width="30%" class="optionTitleBoldLeft">
															<select name="companyId" id="companyId" class="selectBox"></select>
														</td>
														<td width="20%" class="optionTitleBoldRight">
															<fmt:message key="label.search" />:
														</td>
														<td width="50%" class="optionTitleBoldLeft" nowrap>
															<select name="searchField" class="selectBox" id="searchField">
																<option value="itemId" selected><fmt:message key="label.itemid" /></option>
																<option value="catPartNo"><fmt:message key="label.part" /></option>
															</select>
															<select name="searchMode" class="selectBox" id="searchMode">
																<option value="is"><fmt:message key="label.is" /></option>
																<option value="contains"><fmt:message key="label.contains" /></option>
																<option value="startsWith"><fmt:message key="label.startswith" /></option>
																<option value="endsWith"><fmt:message key="label.endswith" /></option>
															</select>
															<input name="searchArgument" id="searchArgument" type="text" class="inputBox" size="15">
														</td>
													</tr>
													<tr>
														<td nowrap width="20%" class="optionTitleBoldRight">
															<fmt:message key="label.pricegroup" />:
														</td>
														<td width="50%" class="optionTitleBoldLeft">
															<select name="priceGroupId" id="priceGroupId" class="selectBox"></select>
														</td>
														<%--td width="20%" class="optionTitleBoldRight">
															<fmt:message key="label.showexpired" />:
														</td >
														<td width="50%" class="optionTitleBoldLeft" nowrap>
															<input name="showExpired" id="showExpired" type="checkbox" class="radioBtns" onclick="toggleExpireDates()"  value="Y" />
														</td--%>
													</tr>
													<tr>
														<td class="optionTitleBoldLeft" colspan="2">
															<input name="submitSearch" id="submitSearch" type="submit" value="<fmt:message key="label.search"/>" 
																class="inputBtns" onmouseover="this.className='inputBtns inputBtnsOver'"
																onmouseout="this.className='inputBtns'" onclick="return submitSearchForm();">
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
							<input name="uAction" id="uAction" type="hidden" value=""> 
							<input name="startSearchTime" id="startSearchTime" type="hidden" value="">
							<%-- To get the correct height value to insert, insert showSearchHeight = true; anywhere in the JavaScript section in main.jsp.  --%> 
							<input name="searchHeight" id="searchHeight" type="hidden" value="214">
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
													<span id="updateResultLink">
														<form id="csvForm" name="csvForm" enctype="multipart/form-data" method="post" action="/tcmIS/hub/CVStoJSON.do" target="cvsUploadFrame">
														<a href="#" onclick="resultFrame.updatePrices();"><fmt:message key="label.update" /></a> |
														<a href="#" onclick="resultFrame.addRow();"><fmt:message key="label.addpart" /></a> |
														<a href="#" onclick="resultFrame.downloadTemplate();"><fmt:message key="label.downloadtemplate" /></a> |  
														<input type="file" name="uploadedCSV" id="uploadedCSV" class="inputfile" accept='text/*.csv' onchange='document.getElementById("csvForm").submit();'/><label for="uploadedCSV"><fmt:message key="label.uploadtemplate" /></span></label>
														</form>
														 
													</span>
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
		<iframe id="cvsUploadFrame" name="cvsUploadFrame" style="width;0px;height:0px;display:none;"></iframe>
	</body>
</html:html>