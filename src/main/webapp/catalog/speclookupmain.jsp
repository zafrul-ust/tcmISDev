<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html;charset=UTF-8" language="java"%>

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
		
		<%@ include file="/common/locale.jsp" %> 						<%-- Sets locale --%>

		<tcmis:gridFontSizeCss /> <%-- Sets CSS based on the user's preffered font size and which application he is viewing --%>
		
		<script type="text/javascript" src="/js/common/formchek.js"></script>			<%-- Validation support --%>
		<script type="text/javascript" src="/js/common/commonutil.js"></script>
		<script type="text/javascript" src="/js/common/grid/iframegridresizemain.js"></script>	<%-- This handles all the resizing of the page and frames --%>
		<script type="text/javascript" src="/js/common/disableKeys.js"></script>		<%-- This disables various key press events --%>
		
		<script type="text/javascript" src="/dhtmlxLayout/codebase/dhtmlxlayout.js"></script>
		<script type="text/javascript" src="/dhtmlxWindows/codebase/dhtmlxwindows.js"></script>
		<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxcommon.js"></script>


        <%-- Page specific javascript --%>
		<script type="text/javascript" src="/js/catalog/speclookupmain.js"></script>
		
		<title>
		    <fmt:message key="label.speclookup"/>		<%-- Internationalized page title --%>
		</title>
		
		<script language="JavaScript" type="text/javascript">
		<%-- NOTE: The only javascript here rather than in a specific js file is javascript that contains values from JSP --%>
		<%-- Standard var for Internationalized messages--%>
		var messagesData = new Array();
		messagesData={
			alert:"<fmt:message key="label.alert"/>",
			and:"<fmt:message key="label.and"/>",
			all:"<fmt:message key="label.all"/>",
			showlegend:"<fmt:message key="label.showlegend"/>",
			errors:"<fmt:message key="label.errors"/>",  
			validvalues:"<fmt:message key="label.validvalues"/>",
            searchTextRequired:"<fmt:message key="label.inputSearchText"/>",
            pleasewait:"<fmt:message key="label.pleasewait"/>",
            submitOnlyOnce:"<fmt:message key="label.submitOnlyOnce"/>"};
		// -->
		</script>
	</head>

	<%-- Name passed in loadLayoutWin needs to match the pageData in application.jsp if the page will be on the pulldwon menu --%>
	<body bgcolor="#ffffff" onload="loadLayoutWin('','specLookup');" onresize="resizeFrames();" onunload="closeThisWindow();">
	
		<div class="interface" id="mainPage" style="">
			<div id="searchFrameDiv">
				<div class="contentArea">
					<tcmis:form action="/speclookupresults.do" onsubmit="return submitSearchOnlyOnce();" target="resultFrame">
						<table id="searchMaskTable" width="100%" border="0" cellpadding="0" cellspacing="0">
							<tr>
								<td>
									<div class="roundcont filterContainer">
	 									<div class="roundright">
	   										<div class="roundtop">
	     											<div class="roundtopright"> 
	     												<img src="/images/rndBoxes/borderTL_filter.gif" alt="" width="15" height="10" class="corner_filter" style="display: none" />
	     											</div>
											</div>
		   									<div class="roundContent">
												<table width="600" id="searchTable" border="0" cellpadding="0" cellspacing="0" class="tableSearch">
													<tr>    
														<td  class="optionTitleBoldLeft">
															<fmt:message key="label.search"/>:
                                                            <c:set var="tmpVal" value=''/>
                                                            <c:if test="${!empty param.modifySpecId}">
                                                                <c:set var="tmpVal" value='${param.modifySpecId}'/>
                                                            </c:if>
                                                            <input name="searchArgument" id="searchArgument" type="text" value="${tmpVal}" class="inputBox" size="40" onkeypress="return onKeyPress()"/>
													   	</td>        
													</tr>
													<tr>
													<td>&nbsp;</td>
													</tr>
													<tr>
														<td colspan="2"  class="optionTitleBoldLeft" nowrap>
															<input name="submitSearch" type="submit" class="inputBtns" value="<fmt:message key="label.search"/>" id="submitSearch" onmouseover="this.className='inputBtns inputBtnsOver'" onMouseout="this.className='inputBtns'" onclick= "return submitSearchForm()"/>
															<input name="xslButton" type="button" class="inputBtns" value="<fmt:message key="label.createExcel"/>" id="xslButton" onmouseover="this.className='inputBtns inputBtnsOver'" onMouseout="this.className='inputBtns'" onclick= "return createXSL()"/>
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
						<%-- Struts Error Messages --%>
						<%-- Build this section only if there is an error message to display.
						     For the search section, we show the error messages within its frame--%>
						<c:if test="${requestScope['org.apache.struts.action.ERROR'] != null}">
							<div class="spacerY">&nbsp;
								<div id="searchErrorMessagesArea" class="errorMessages">
									<html:errors/>
								</div>
							</div>
						</c:if>

						<%-- Hidden elements to use in javascript or send back to server --%>
						<div id="hiddenElements" style="display: none;">
							<input type="hidden" name="uAction" id="uAction" value=""/>
							<input name="startSearchTime" id="startSearchTime" type="hidden" value="" />
							<%-- needed if this page will show on application.do --$>
							<%-- To get the correct height value to insert, insert showSearchHeight = true; anywhere in the JavaScript array section in main.jsp.  --%>
							<input name="searchHeight" id="searchHeight" type="hidden" value="214"/>
                            <input name="calledFrom" id="calledFrom" type="hidden" value="${param.calledFrom}"/>
                            <input name="companyId" id="companyId" type="hidden" value="${param.companyId}"/>
                            <input name="requestId" id="requestId" type="hidden" value="${param.requestId}"/>
                            <input name="modifySpecId" id="modifySpecId" type="hidden" value="${param.modifySpecId}"/>
                            <input name="specId" id="specId" type="hidden" value=""/>
                            <input name="specName" id="specName" type="hidden" value="">
                            <input name="specTitle" id="specTitle" type="hidden" value="">
                            <input name="specVersion" id="specVersion" type="hidden" value="">
                            <input name="specAmendment" id="specAmendment" type="hidden" value="">
                            <input name="specLibrary" id="specLibrary" type="hidden" value="">
                            <input name="coc" id="coc" type="hidden" value="">
                            <input name="coa" id="coa" type="hidden" value="">
                        </div>

                        <%-- transition --%>
                        <div id="transitDailogWin" class="errorMessages" style="display: none;overflow: auto;">
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
                    </tcmis:form>
				</div>
			</div>
			<%-- Results Begin --%>
			<div id="resultFrameDiv" style="margin: 4px 4px 0px 4px;">
				<%-- div for inline popup "Please Wait" window --%>
				<div id="transitPage" style="display: none;" class="optionTitleBoldCenter">
					<br/>
					<br/>
					<br/>
					<fmt:message key="label.pleasewait"/>
				  	<br/>
				  	<br/>
				  	<br/>
				  	<img src="/images/rel_interstitial_loading.gif" align="middle"/>
				</div>

                <div id="resultGridDiv" style="display: none;">
					<%-- If you want your results section to span only 50% set this to 50% or 800 pixels. Make sure to set resizeGridWithWindow to false. --%>
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
												     Please keep the <span></span> on the same line this will avoid extra virtical space.
												 --%>     
												<div id="mainUpdateLinks" style="display: none">
					 							</div>
											</div>
											<div class="dataContent">
					  							<iframe scrolling="no" id="resultFrame" name="resultFrame" width="100%" height="300" frameborder="0" marginwidth="0" src="/blank.html"></iframe>
											</div>					
											<div id="footer" class="messageBar"></div> <%-- result count and time --%>
					
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

        <%-- Error message inline pop-up.--%>
		<div id="errorMessagesArea" class="errorMessages" style="display: none;overflow: auto;">
		</div>
	</body>
</html:html>