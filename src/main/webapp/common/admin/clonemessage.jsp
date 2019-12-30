<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

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
	<script type="text/javascript" src="/js/common/grid/nosearchresize.js"></script>
	<script type="text/javascript" src="/js/common/disableKeys.js"></script>
	
	<script type="text/javascript" src="/js/common/noSearchStandardGridmain.js"></script>

	<script type="text/javascript" src="/js/menu/milonic_src.js"></script>
	<script type="text/javascript" src="/js/menu/mmenudom.js"></script>
	<script type="text/javascript" src="/js/menu/mainmenudata.js"></script>
	<script type="text/javascript" src="/js/menu/contextmenu.js"></script>
	<%@ include file="/common/rightclickmenudata.jsp" %>

	<!-- Add any other javascript you need for the page here -->

	<!-- These are for the Grid-->
	<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxcommon.js"></script>
	<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxgrid.js"></script>
	<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxgridcell.js"></script>
	<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_form.js"></script>
	<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_srnd.js"></script>
	<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_json.js"></script>    
	<script type="text/javascript" src="/dhtmlxGrid/codebase/excells/dhtmlxgrid_excell_customized.js"></script>
	<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxcommon_haas.js"></script>
	
	<script type="text/javascript" src="/js/common/admin/clonemessagemain.js"></script>

	<title>
		<fmt:message key="label.clonemessage"/>
	</title>

	<script language="JavaScript" type="text/javascript">
		<!--
		//add all the javascript messages here, this for internationalization of client side javascript messages
		var messagesData = new Array();
			messagesData = {alert:"<fmt:message key="label.alert"/>",
			and:"<fmt:message key="label.and"/>",
			submitOnlyOnce:"<fmt:message key="label.submitOnlyOnce"/>",
			recordFound:"<fmt:message key="label.recordFound"/>",
			searchDuration:"<fmt:message key="label.searchDuration"/>",
			minutes:"<fmt:message key="label.minutes"/>",
			seconds:"<fmt:message key="label.seconds"/>",
			ok:"<fmt:message key="label.ok"/>"		
		};      
			
		var config = [ 
						{columnId:"permission" },
		              	{columnId:"companySel", type:'hchstatus', columnName:'<fmt:message key="label.select"/>', align:'center', width:4},
		              	{columnId:"companyId"},
		    			{columnId:"companyName", type:'txt', columnName:'<fmt:message key="label.connectionpool"/>', tooltip:"Y", width:25}
					 ]; 


		var selectedRowId = 1;
		var resizeGridWithWindow = true;
		var jsonMainData; 
		
		function resultOnLoad() {
			var title = parent.opener.getTitle();
			var date = parent.opener.getExpirationDate();
			var message = parent.opener.getMessage();

			document.getElementById('messageTitle').value = title;
			document.getElementById('messageTitleDisplay').innerHTML = title;
			document.getElementById('messageExpirationDate').value = date;
			document.getElementById('messageExpirationDateDisplay').innerHTML = date;
			document.getElementById('messageText').value = message;
			document.getElementById('messageTextDisplay').innerHTML = message;

			jsonMainData = parent.opener.getConnectionPool();
			var totalLines = jsonMainData.rows.length;
			$("totalLines").value = totalLines;
			
			if (totalLines > 0) {
				// make result building visible if data exist
				$("SearchResults").style["display"] = "";
				// build the grid for display
				doInitGrid();
			}
			else {
				$("SearchResults").style["display"] = "none";
			}
			 setResultSize(100);

			 if(totalLines > 0) {
				 mygrid.selectRow(0);
			 }
		}

		function doInitGrid() {
			
			mygrid = new dhtmlXGridObject('SearchResults');
	
			// initGridWithConfig(inputGrid,config,rowSpan,submitDefault,singleClickEdit)
			// if rowSpan == true, sorting and smart rendering won't work; if
			// rowSpan == false, sorting and smart rendering will work
			// rowSpan == -1 is recommended for the page with update function
			// -1 is for disable rowSpan and smart rendering, but sorting still
			// works; false will disable rowSpan and sorting but smart rendering is
			// enabled
			// set submitDefault to true: Send the data to the server
			// singleClickEdit: this is for type:"txt",
			initGridWithConfig(mygrid, config, -1, true, true);
			if (typeof (jsonMainData) != 'undefined') {
				mygrid.parse(jsonMainData, "json");
			}
		}
	
		function cloneMessage() {
			$('uAction').value = 'cloneMessageUpdate';
			
			if (mygrid != null) {
				// This function prepares the grid data for submitting top the server
				mygrid.parentFormOnSubmit();
			}

			document.genericForm.submit(); // Submit the form
		}
		
		function selectAll() {
			var rowsNum = mygrid.getRowsNum(); // Get the total rows of the grid
			var columnId = mygrid.getColIndexById('connectionPoolSel');
			for ( var rowId = 1; rowId <= rowsNum; rowId++) {
				var curCheckBox = 'companySel' + rowId;
				if ($(curCheckBox)) { // Make sure the row has been rendered and the element exists
					$(curCheckBox).checked = true;
					updateHchStatusA(curCheckBox);
				}
				else { // The HTML element hasn't been drawn yet, update the JSON data directly
					mygrid._haas_json_data.rows[mygrid.getRowIndex(rowId)].data[columnId] = true;
				}
			}
		}
		
		function clearAll() {
			var rowsNum = mygrid.getRowsNum(); // Get the total rows of the grid
			var columnId = mygrid.getColIndexById('connectionPoolSel');
			for ( var rowId = 1; rowId <= rowsNum; rowId++) {
				var curCheckBox = 'companySel' + rowId;
				if ($(curCheckBox)) { // Make sure the row has been rendered and the element exists
					$(curCheckBox).checked = false;
					updateHchStatusA(curCheckBox);
				}
				else { // The HTML element hasn't been drawn yet, update the JSON data directly
					mygrid._haas_json_data.rows[mygrid.getRowIndex(rowId)].data[columnId] = true;
				}
			}
		}
		// -->
	</script>
</head>


<body bgcolor="#ffffff" onload="resultOnLoad()" onunload="try{opener.stopShowingWait();}catch(ex){}">
	<div class="interface" id="mainPage">
		<div id="resultFrameDiv" style="margin: 4px 4px 0px 4px;">
			<%-- Transit Page Starts --%>
			<div id="transitPage" style="display: none;" class="optionTitleBoldCenter">
				<br>
				<br>
				<br>
				<fmt:message key="label.pleasewait" />
				<br>
				<br>
				<br>
				<img src="/images/rel_interstitial_loading.gif" align="middle">
			</div>
			<div id="searchFrameDiv">
				<div class="contentArea">
						<table id="searchMaskTable" width="600" border="0" cellpadding="0" cellspacing="0">
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
												<table width="100%" id="searchTable" border="0" cellpadding="0" cellspacing="0" class="tableSearch">
													<tr>
														<td width="20%" class="optionTitleBoldLeft">
															<fmt:message key="label.messagetitle" /></input>:
														</td>
														<td width="50%" class="optionTitleBoldLeft" nowrap>
															<span id="messageTitleDisplay"/>					
														</td>
													</tr>
													<tr>
														<td width="20%" class="optionTitleBoldLeft">
															<fmt:message key="label.messageexpirationdate" />:
														</td>
														<td width="50%" class="optionTitleBoldLeft" nowrap>
															<span id="messageExpirationDateDisplay"/>													
														</td>
													</tr>
													<tr>
														<td width="20%" class="optionTitleBoldLeft">
															<fmt:message key="label.messagetext" />:
														</td>
														<td width="50%" class="optionTitleBoldLeft" >
															<span id="messageTextDisplay"/>																
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
				</div> 
			</div>
			<div id="resultGridDiv" style="display: none;">
				<%-- Search results start --%> 
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
											<div id="mainUpdateLinks"> 
												<a href="#" onclick="cloneMessage();"><fmt:message key="label.clonemessage" /></a>  |  
												<a href="#" onclick="selectAll();"><fmt:message key="label.selectall"/></a>  |
												<a href="#" onclick="clearAll();"><fmt:message key="label.clearall"/></a>
											</div>
										</div>
										<div class="dataContent">
											<tcmis:form action="/clonemessage.do">
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
														<div id="SearchResults" style="width:100%;height:400px; display: none;"></div>


														<%-- Hidden element start --%> 
														<div id="hiddenElements" style="display: none;">    		
															<input name="messageTitle" id="messageTitle" type="hidden" />	
															<input name="messageExpirationDate" id="messageExpirationDate" type="hidden" />	
															<input name="messageText" id="messageText" type="hidden" />		
															<input name="minHeight" id="minHeight" type="hidden" value="100">
															<input name="uAction" id="uAction" value="" type="hidden"/>	
															<input name="totalLines" id="totalLines" value="0" type="hidden"/>
															<input name="startSearchTime" id="startSearchTime" type="hidden" value="${startSearchTime}"/>
															<input name="endSearchTime" id="endSearchTime" type="hidden" value="${endSearchTime}"/>	
														</div>
													</div>
												</div>
											</tcmis:form>
										</div>
								
										<%-- result count and time --%>
										<div id="footer" class="messageBar"></div>
								
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
	</div> <!-- close of interface -->
	<!-- Error Messages Begins -->
	<div id="errorMessagesArea" class="errorMessages" style="display: none;overflow: auto;">
	
	</div>

</body>
</html:html>