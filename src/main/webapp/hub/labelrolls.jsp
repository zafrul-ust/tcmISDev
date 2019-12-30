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
		<script type="text/javascript" src="/js/common/disableKeys.js"></script>		<%-- This disables various key press events --%>	
		

		<%-- Page specific javascript --%>
		
		
		<title><fmt:message key="label.printlabels"/></title>
		
		<script language="JavaScript" type="text/javascript">
			<!-- <%-- NOTE: The only javascript here rather than in a specific js file should be javascript that contains values from JSP --%>

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
				pleasemakeselection:"<fmt:message key="label.pleasemakeselection"/>",
				validquantity:"<fmt:message key="labels.label.validquantity"/>",
				labelprintlimit:"<fmt:message key="label.labelprintlimit"/>"
				};


			function validate()
			{
				var errMsg = '';
				if(!$('receipt').checked && !$('document').checked && !$('pdoc').checked)
					errMsg += messagesData.pleasemakeselection;
				if(!isPositiveInteger($v('labelQuantity')))
					errMsg += messagesData.validquantity;
				else if(parseInt($v('labelQuantity')) > 500)
					errMsg += messagesData.labelprintlimit;

				if(errMsg.length > 0)
				{
					alert(errMsg);
					return false;
				}
				else
					return true;
			}

		function openPrintWin()
		{
			if(validate())
			{
				$('userAction').value = 'print';
				openWinGenericViewFile('/tcmIS/common/loadingfile.jsp', '_printWin', '650', '600', 'yes');
				document.genericForm.target = '_printWin';
				var a = window.setTimeout("document.genericForm.submit();", 50);
			}
			else 
				return false;
		}
		
		function displayPaperSizeSelection(){
			var row = document.getElementById("paperSizeDisplay");
			row.style.display = "block";
		}
		
		function hidePaperSizeSelection(){
			var row = document.getElementById("paperSizeDisplay");
			row.style.display = "none";
			
			// reset label size to default
			var dropdown = document.getElementById("paperSize");
			dropdown.value="31";
		}
				
			// -->
		 </script>
	</head>

	<%--call the loadLayoutWin() to set the sizes of the search section and initiate the layout. 
	If you dont want to use the layout set javascript variable useLayout=false;--%>
	<body bgcolor="#ffffff" onload="loadLayoutWin('','labelRolls');" onresize="resizeFrames()">
		<div class="interface" id="mainPage" style="">
			<%-- Search Section --%>
			<div id="searchFrameDiv">
				<div class="contentArea">
					<tcmis:form action="/labelrolls.do" onsubmit="return submitSearchOnlyOnce();" target="_self">
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
															<input name="labelType" id="receipt" type="radio" class="radioBtns" value="receipt" checked onclick="displayPaperSizeSelection()"/>&nbsp;<fmt:message key="label.receiptidlabels"/>
														</td>
														<td width="40%" class="optionTitleBoldLeft" nowrap>
															<input name="labelType" id="document" type="radio" class="radioBtns" value="document" onclick="hidePaperSizeSelection()"/>&nbsp;<fmt:message key="label.documentidlabels"/>
														</td>
														<td width="40%" class="optionTitleBoldLeft" nowrap>
															<input name="labelType" id="pdoc" type="radio" class="radioBtns" value="pdoc" onclick="hidePaperSizeSelection()"/>&nbsp;<fmt:message key="label.pdoclabels"/>
														</td>
													</tr>
													<tr name="paperSizeDisplay" id="paperSizeDisplay" style="display: block">
														<td width="5%" class="optionTitleBoldRight">
															<fmt:message key="label.labelsize"/>:
														</td>	
														<td width="30%" class="optionTitleBoldLeft">
														 	<select name="paperSize" class="selectBox" id="paperSize">
														 		<option value="11">.75” x 1.42” </option>
															    <option value="31" selected="selected">4” x 1”</option>
															</select>
														</td>
													</tr>
													<tr>
														<td width="50%" class="optionTitleBoldLeft">
															<fmt:message key="label.labelquantity"/>&nbsp;:&nbsp;<input type="text" name="labelQuantity" id="labelQuantity" value="" size="15" maxlength="30" class="inputBox"/>
														</td>
													</tr>
													<tr>
														<td width="50%" class="optionTitleBoldLeft">
															<input name="print" id="print" type="button" value="<fmt:message key="label.print"/>" class="inputBtns" onmouseover="this.className='inputBtns inputBtnsOver'" onmouseout="this.className='inputBtns'" onclick="openPrintWin()"/>
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
							<input name="userAction" id="userAction" type="hidden" value=""> 
							<input name="startSearchTime" id="startSearchTime" type="hidden" value="">
							<%-- To get the correct height value to insert, insert showSearchHeight = true; anywhere in the JavaScript section in main.jsp.  --%> 
							<input name="searchHeight" id="searchHeight" type="hidden" value="214">
						</div>
					</tcmis:form>
				</div>
			</div>
		</div>
		<%-- Error Messages Div --%>
		<div id="errorMessagesArea" class="errorMessages" style="display: none; overflow: auto;"></div>
	
	</body>
</html:html>