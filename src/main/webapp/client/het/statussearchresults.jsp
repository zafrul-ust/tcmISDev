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

	<title>
		<fmt:message key="label.synch"/>
	</title>

	<script language="JavaScript" type="text/javascript">
		<!--
		//add all the javascript messages here, this for internationalization of client side javascript messages
		var messagesData = {
			alert:"<fmt:message key="label.alert"/>",
			and:"<fmt:message key="label.and"/>",
			validvalues:"<fmt:message key="label.validvalues"/>",
			submitOnlyOnce:"<fmt:message key="label.submitOnlyOnce"/>",
			recordFound:"<fmt:message key="label.recordFound"/>",
			searchDuration:"<fmt:message key="label.searchDuration"/>",
			minutes:"<fmt:message key="label.minutes"/>",
			seconds:"<fmt:message key="label.seconds"/>",
			returnselected:"<fmt:message key="label.returnselected"/>",
			ok:"<fmt:message key="label.ok"/>"
			};             
		var config = [
			{ columnId:"captureTime", columnName:'<fmt:message key="label.synchtime"/>', width:12, tooltip: true },
			{ columnId:"typeOfChange", columnName:'<fmt:message key="label.synchtype"/>', width: 5, align: "middle"},
			{ columnId:"success", columnName:'<fmt:message key="label.success"/>', width: 5, align: "middle"},
			{ columnId:"message", columnName:'<fmt:message key="label.comment"/>', width:40 }
		];

		function resultOnLoad() {

			var totalLines = $v("totalLines");
			if (totalLines > 0) {
				// make result area visible if data exist
				$("SearchResults").style["display"] = "";
				// build the grid for display
				doInitGrid();
			}
			else {
				$("SearchResults").style["display"] = "none";
			}

			/* This displays our standard footer message */
			 displayNoSearchSecDuration();
			 setResultSize();
		}

		function doInitGrid() {
			mygrid = new dhtmlXGridObject('SearchResults');
			initGridWithConfig(mygrid, config, -1, true, true);
			if (typeof (jsonMainData) != 'undefined') {
				mygrid.parse(jsonMainData, "json");
			}
		}

		function doSynch() {
			$("uAction").value = "update";
			document.genericForm.submit();
		}

		// -->
	</script>
</head>


<body bgcolor="#ffffff" onload="resultOnLoad()" onresize="resizeFrames()" >
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
											<div id="mainUpdateLinks"></div>
											<div> 
												<c:if test="${!personnelBean.standalone}"><a href="#" onclick="doSynch();">Initiate Full Synch</a></c:if>
											</div>
										</div>
										<div class="dataContent">
											<tcmis:form action="/hetstatus.do">
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
														<div id="SearchResults" style="width:100%;height:400px;" style="display: none;"></div>

														<script type="text/javascript">
														<!--
														<c:if test="${!empty results}" >  
														var jsonMainData =  {
															rows:[<c:forEach var="bean" items="${results}" varStatus="status">
																{id:${status.count},
																data:[
																  '<fmt:formatDate value="${bean.captureTime}" pattern="${dateTimeFormatPattern}"/>',
																  '${bean.typeOfChange}',
																  '${bean.success}',
																  '<tcmis:jsReplace value="${bean.message}" processMultiLines="true"/>'
																  ]}<c:if test="${!status.last}">,</c:if>  
															 </c:forEach>
														]};
														</c:if>
														//-->  
														</script>

														<%-- If the collection is empty say no data found --%>
														<c:if test="${empty results}">
														  <table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableNoData" id="resultsPageTable">
															<tr>
															   <td width="100%">
																  <fmt:message key="main.nodatafound"/>
															   </td>
															</tr>
														  </table>
														</c:if>

														<%-- Hidden element start --%> 
														<div id="hiddenElements" style="display: none;">    		
															<input name="minHeight" id="minHeight" type="hidden" value="100">
															<input name="startSearchTime" id="startSearchTime" type="hidden" value="${startSearchTime}"/>
															<input name="endSearchTime" id="endSearchTime" type="hidden" value="${endSearchTime}"/>
															<tcmis:setHiddenFields beanName="input"/>
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