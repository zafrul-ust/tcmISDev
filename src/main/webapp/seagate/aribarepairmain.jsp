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
		<script type="text/javascript"src="/js/common/standardGridmain.js"></script> 
		<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxcommon.js"></script> 
		<script type="text/javascript" src="/dhtmlxLayout/codebase/dhtmlxlayout.js"></script> 
		<script type="text/javascript" src="/dhtmlxWindows/codebase/dhtmlxwindows.js"></script>
		
		<!-- For Calendar support for column type hcal-->
		<script type="text/javascript" src="/js/calendar/newcalendar.js"></script>
		<script type="text/javascript" src="/js/calendar/AnchorPosition.js"></script>
		<script type="text/javascript" src="/js/calendar/PopupWindow.js"></script>
		<script type="text/javascript" src="/js/calendar/calendarval.js"></script>
		

		<%-- Page specific javascript --%>
		<script type="text/javascript" src="/js/seagate/aribarepair.js"></script> 
		
		
		<title>		<fmt:message key="label.aribarepair"/> </title>
		
		<script language="JavaScript" type="text/javascript">
			<!-- <%-- NOTE: The only javascript here rather than in a specific js file should be javascript that contains values from JSP --%>
			<c:set var="facilityCount" value='${0}'/>
			var altFacilityId = new Array(
			<c:forEach var="myWorkareaFacilityViewBean" items="${myWorkareaFacilityViewBeanCollection}" varStatus="status">
			    <c:if test="${myWorkareaFacilityViewBean.active == 'y'}">
			        <c:if test="${facilityCount > 0}">
			        ,
			        </c:if>
			        '${status.current.facilityId}'
			        <c:set var="facilityCount" value='${facilityCount+1}'/>
			   </c:if>
			</c:forEach>
			);

			<c:set var="facilityCount" value='${0}'/>
			var altFacilityName = new Array(
			<c:forEach var="myWorkareaFacilityViewBean" items="${myWorkareaFacilityViewBeanCollection}" varStatus="status">
			   <c:if test="${myWorkareaFacilityViewBean.active == 'y'}">
			       <c:if test="${facilityCount > 0}">
			        ,
			       </c:if>
			       '<tcmis:jsReplace value="${status.current.facilityName}"/>'
			        <c:if test="${maxFacilityDescLength < fn:length(status.current.facilityName)}">
			            <c:set var="maxFacilityDescLength" value='${fn:length(status.current.facilityName)}'/>
			        </c:if>
			        <c:set var="facilityCount" value='${facilityCount+1}'/>
			    </c:if>
			</c:forEach>
			);

			<c:set var="facilityCount" value='${0}'/>
			var altFacilityEcommerce = new Array(
			<c:forEach var="myWorkareaFacilityViewBean" items="${myWorkareaFacilityViewBeanCollection}" varStatus="status">
			   <c:if test="${myWorkareaFacilityViewBean.active == 'y'}">
			       <c:if test="${facilityCount > 0}">
			        ,
			       </c:if>
			       '${status.current.ecommerce}'
			       <c:set var="facilityCount" value='${facilityCount+1}'/>
			   </c:if>
			</c:forEach>
			);

			<c:set var="module">
			 <tcmis:module/>
			</c:set>

			var altApplication = new Array();
			<c:forEach var="myWorkareaFacilityViewBean" items="${myWorkareaFacilityViewBeanCollection}" varStatus="status">
			    <c:if test="${myWorkareaFacilityViewBean.active == 'y'}">
			        <c:set var="applicationCount" value='${0}'/>
			        altApplication['<tcmis:jsReplace value="${status.current.facilityId}"/>'] = new Array(
			         <c:forEach items="${status.current.applicationBeanCollection}" varStatus="status1">
			             <c:choose>
			                <c:when test="${module == 'lmco'}">
			                    <c:if test="${status1.current.status == 'A'}">
			                        <c:if test="${applicationCount > 0}">
			                        ,
			                        </c:if>
			                        '<tcmis:jsReplace value="${status1.current.application}"/>'
			                        <c:set var="applicationCount" value='${applicationCount+1}'/>
			                     </c:if>
			                </c:when>
			                <c:otherwise>
			                    <c:if test="${status1.current.status == 'A' && status1.current.manualMrCreation == 'Y'}">
			                     <c:if test="${applicationCount > 0}">
			                        ,
			                        </c:if>
			                        '<tcmis:jsReplace value="${status1.current.application}"/>'
			                        <c:set var="applicationCount" value='${applicationCount+1}'/>
			                     </c:if>
			                </c:otherwise>
			             </c:choose>

			         </c:forEach>
			        );
			    </c:if>
			</c:forEach>

			var altApplicationDesc = new Array();
			<c:forEach var="myWorkareaFacilityViewBean" items="${myWorkareaFacilityViewBeanCollection}" varStatus="status">
			    <c:if test="${myWorkareaFacilityViewBean.active == 'y'}">
			        <c:set var="applicationCount" value='${0}'/>
			        altApplicationDesc['<tcmis:jsReplace value="${status.current.facilityId}"/>'] = new Array(
			         <c:forEach items="${status.current.applicationBeanCollection}" varStatus="status1">
			            <c:choose>
			                <c:when test="${module == 'lmco'}">
			                    <c:if test="${status1.current.status == 'A'}">
			                        <c:if test="${applicationCount > 0}">
			                        ,
			                        </c:if>
			                        '<tcmis:jsReplace value="${status1.current.applicationDesc}"/>'
			                        <c:set var="applicationCount" value='${applicationCount+1}'/>
			                        <c:if test="${maxApplicationDescLength < fn:length(status1.current.applicationDesc)}">
			                            <c:set var="maxApplicationDescLength" value='${fn:length(status1.current.applicationDesc)}'/>
			                        </c:if>
			                    </c:if>
			                 </c:when>
			                <c:otherwise>
			                    <c:if test="${status1.current.status == 'A' && status1.current.manualMrCreation == 'Y'}">
			                        <c:if test="${applicationCount > 0}">
			                        ,
			                        </c:if>
			                        '<tcmis:jsReplace value="${status1.current.applicationDesc}"/>'
			                        <c:set var="applicationCount" value='${applicationCount+1}'/>
			                        <c:if test="${maxApplicationDescLength < fn:length(status1.current.applicationDesc)}">
			                            <c:set var="maxApplicationDescLength" value='${fn:length(status1.current.applicationDesc)}'/>
			                        </c:if>
			                    </c:if>
			                </c:otherwise>
			             </c:choose>
			         </c:forEach>
			        );
			    </c:if>
			</c:forEach>
			
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
				searchInput:"<fmt:message key="error.searchInput.integer"/>",
				myworkareas:"<fmt:message key="label.selectOne"/>",
				myFacilities:"<fmt:message key="label.myfacilities"/>"
				};
		
			// -->
		 </script>

	</head>

	<%--call the loadLayoutWin() to set the sizes of the search section and initiate the layout. 
	If you dont want to use the layout set javascript variable useLayout=false;--%>
	<body bgcolor="#ffffff" onload="loadLayoutWin('','openPos');" onresize="resizeFrames()" onunload="try{opener.closeTransitWin();}catch(ex){}">
		<div class="interface" id="mainPage" style="">
			<%-- Search Section --%>
			<div id="searchFrameDiv">
				<div class="contentArea">
					<tcmis:form action="/aribarepairresults.do" onsubmit="return submitSearchOnlyOnce();" target="resultFrame">
						<table id="searchMaskTable" width="100%" border="0" cellpadding="0" cellspacing="0">
							<tr>
								<td>
								<div class="roundcont filterContainer">
									<div class="roundright">
										<div class="roundtop">
											<div class="roundtopright"><img src="/images/rndBoxes/borderTL_filter.gif" alt="" width="15" height="10" class="corner_filter" style="display: none" /></div>
											</div>
											<div class="roundContent"><%-- Insert all the search option within this div --%>
												<table width="250" border="0" cellpadding="0" cellspacing="0" class="tableSearch">
													<tr>
														<td nowrap width="20%" class="optionTitleBoldRight" nowrap>
															<fmt:message key="label.mrswithnodomrsumbited"/>:
														</td>
														<td width="50%" class="optionTitleBoldLeft" nowrap>
															<input class="inputBox pointer" readonly type="text" name="confirmFromDate" id="confirmFromDate" value="" onClick="return getCalendar(document.genericForm.confirmFromDate,null,document.genericForm.confirmToDate);" maxlength="10" size="9"/>				
															<fmt:message key="label.to"/>&nbsp;
															<input class="inputBox pointer" readonly type="text" name="confirmToDate" id="confirmToDate" value="" onClick="return getCalendar(document.genericForm.confirmToDate,document.genericForm.confirmFromDate);" maxlength="10" size="9"/>
															
														</td>
													</tr>
													<tr>
														<td width="30%" class="optionTitleBoldRight">
															<fmt:message key="label.facility"/>:
															</td>
															
															<td width="70%" class="optionTitleBoldLeft">
															<c:set var="selectedFacilityId" value="${personnelBean.myDefaultFacilityId}"/>
															<select name="facilityId" id="facilityId" class="selectBox" onchange="facilityChanged()">
															  <option value=""><fmt:message key="label.myfacilities"/></option>
															  <c:forEach var="myWorkareaFacilityViewBean" items="${myWorkareaFacilityViewBeanCollection}" varStatus="status">
															    <c:if test="${myWorkareaFacilityViewBean.active == 'y'}">
															        <c:set var="currentFacilityId" value='${status.current.facilityId}'/>
															          <c:if test="${empty selectedFacilityId}" >
															            <c:set var="selectedFacilityId" value='${currentFacilityId}' />
															          </c:if>
															          <c:if test="${currentFacilityId == selectedFacilityId}" >
															            <c:set var="applicationSelectBeanCollection" value='${status.current.applicationBeanCollection}'/>
															          </c:if>
															
															        <c:choose>
															          <c:when test="${currentFacilityId == selectedFacilityId}">
															            <option value="<c:out value="${currentFacilityId}"/>" selected><c:out value="${status.current.facilityName}"/></option>
															          </c:when>
															          <c:otherwise>
															            <option value="<c:out value="${currentFacilityId}"/>"><c:out value="${status.current.facilityName}"/></option>
															          </c:otherwise>
															        </c:choose>
															    </c:if>
															  </c:forEach>
															</select>
															</td>															
														</tr>
														<tr>
															<td width="30%" class="optionTitleBoldRight" nowrap="true">
															<fmt:message key="label.workarea"/>:
															</td>
															
															<td width="70%" class="optionTitleBoldLeft">
															<c:set var="selectedApplication" value='${param.application}'/>
															<c:if test="${empty selectedApplication}" >
															  <c:set var="selectedApplication" value="All"/>
															</c:if>
															
															<c:set var="applicationCount" value='${0}'/>
															<c:forEach var="facLocAppBean" items="${applicationSelectBeanCollection}" varStatus="status">
															    <c:choose>
															        <c:when test="${module == 'lmco'}">
															            <c:if test="${facLocAppBean.status == 'A'}">
															                <c:set var="applicationCount" value='${applicationCount+1}'/>
															            </c:if>
															        </c:when>
															        <c:otherwise>
															            <c:if test="${facLocAppBean.status == 'A' && facLocAppBean.manualMrCreation == 'Y'}">
															                <c:set var="applicationCount" value='${applicationCount+1}'/>
															            </c:if>
															        </c:otherwise>
															    </c:choose>
															</c:forEach>
															
															<select name="application" id="application" class="selectBox">
																<c:choose>
																  <c:when test="${applicationCount == 0}">
																	 <option value=""><fmt:message key="label.selectOne"/></option>
																  </c:when>
																  <c:otherwise>
																	 <c:if test="${applicationCount > 1}">
																		<option value="" selected><fmt:message key="label.myworkareas"/></option>
																	 </c:if>
																	  <c:set var="applicationCount" value='${0}'/>
																	  <c:forEach var="facLocAppBean" items="${applicationSelectBeanCollection}" varStatus="status">
																		 <c:set var="currentApplication" value='${status.current.application}'/>
																		 <c:set var="currentStatus"  value='${status.current.status}'/>
															             <c:choose>
															                <c:when test="${module == 'lmco'}">
															                   <c:if test="${currentStatus == 'A'}">
															                     <c:set var="applicationCount" value='${applicationCount+1}'/>
															                     <c:choose>
															                        <c:when test="${currentApplication == selectedApplication}">
															                          <option value="<c:out value="${currentApplication}"/>" selected><c:out value="${status.current.applicationDesc}"/></option>
															                        </c:when>
															                        <c:otherwise>
															                          <option value="<c:out value="${currentApplication}"/>"><c:out value="${status.current.applicationDesc}"/></option>
															                        </c:otherwise>
															                     </c:choose>
																		       </c:if>
															                </c:when>
															                <c:otherwise>
															                    <c:if test="${currentStatus == 'A' && status.current.manualMrCreation == 'Y'}">
															                     <c:set var="applicationCount" value='${applicationCount+1}'/>
															                     <c:choose>
															                        <c:when test="${currentApplication == selectedApplication}">
															                          <option value="<c:out value="${currentApplication}"/>" selected><c:out value="${status.current.applicationDesc}"/></option>
															                        </c:when>
															                        <c:otherwise>
															                          <option value="<c:out value="${currentApplication}"/>"><c:out value="${status.current.applicationDesc}"/></option>
															                        </c:otherwise>
															                     </c:choose>
																		       </c:if>
															                </c:otherwise>
															              </c:choose>
															          </c:forEach>
																	</c:otherwise>
																</c:choose>
															</select>
														</td>
													</tr>
													<tr>
														<td colspan="2" class="optionTitleBoldLeft">
															<input type="radio" class="radioBtns" name="cabinetReplenishment" value="Y" id="cabinetReplenishment" />
															<fmt:message key="label.cabinetreplenishment"/>
															<input type="radio" class="radioBtns" name="cabinetReplenishment" value="N" id="cabinetReplenishment" checked="checked" />
															<fmt:message key="label.manualorders"/>
														</td>
													</tr>
													<tr>
														<td class="optionTitleBoldLeft" colspan="2">
															<input name="submitSearch" id="submitSearch" type="submit" value="<fmt:message key="label.search"/>" 
																class="inputBtns" onmouseover="this.className='inputBtns inputBtnsOver'"
																onmouseout="this.className='inputBtns'" onclick="return submitSearchForm();">
															<input name="cancel" id="cancel" type="submit" value="<fmt:message key="label.close"/>" 
																class="inputBtns" onmouseover="this.className='inputBtns inputBtnsOver'"
																onmouseout="this.className='inputBtns'" onclick="try{opener.closeTransitWin();}catch(ex){} window.close();">
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
							<input name="companyId" id="companyId" type="hidden" value="${personnelBean.companyId}">
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
													<span id="updateResultLink" style="display: none">
														<a href="#" onclick="resultFrame.update();"><fmt:message key="label.update" /></a>
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
	
	</body>
</html:html>