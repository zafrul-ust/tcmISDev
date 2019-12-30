<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
	"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html;charset=UTF-8" language="java"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<%@ taglib uri="/WEB-INF/struts-nested.tld" prefix="nested"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/tcmis.tld" prefix="tcmis"%>

<html:html lang="true">
<head>
<meta http-equiv="content-type" content="text/html; charset=UTF-8" />
<meta http-equiv="expires" content="-1" />
<link rel="shortcut icon" href="https://www.tcmis.com/images/buttons/tcmIS.ico"></link>

<%@ include file="/common/locale.jsp"%>
<tcmis:gridFontSizeCss />

<script type="text/javascript" src="/js/common/formchek.js"></script>
<script type="text/javascript" src="/js/common/commonutil.js"></script>
<%--NEW - grid resize--%>
<script type="text/javascript" src="/js/common/grid/resultiframegridresize.js"></script>
<script src="/js/common/disableKeys.js"></script>

<!-- This handels the menu style and what happens to the right click on the whole page -->
<script type="text/javascript" src="/js/menu/milonic_src.js"></script>
<script type="text/javascript" src="/js/menu/mmenudom.js"></script>
<script type="text/javascript" src="/js/menu/mainmenudata.js"></script>
<script type="text/javascript" src="/js/menu/contextmenu.js"></script>
<%@ include file="/common/rightclickmenudata.jsp"%>

</script>
<!-- Add any other javascript you need for the page here -->
<script type="text/javascript" src="/js/jquery/jquery-1.9.1.min.js"></script>
<script src="/js/common/objects/responseObj.js"></script>

<script type="text/javascript">
	$.ajaxSetup({
		cache : false
	});

	$(document).ready(function() {
		$.get("/tcmIS/haas/customerDetails.do", fillMenus);

		$("#submitSearch").on("click", function(e) {
			$("#resultFrame")[0].contentWindow.doSearch($v("companyId"), $v("facilityId"));
		});


		$("#printResults").on("click", function(e) {
			$("#resultFrame")[0].contentWindow.printPage();
		});

		resizeResults();
	});

	$(window).resize(function(){
		resizeResults();
	});
	
	var facilities = null;

	function resizeResults() {
	    var height = $(window).height();       
	    $('#resultFrame').height(height - 160);		
	}
	
	function fillMenus(data) {
		var response = new responseObj(data);
		if (response.isOK()) {
			facilities = response.pulldownValues.facilities;
			loadCompanies(response.pulldownValues.companies);
		}
	};

	function loadCompanies(companies) {
		var $pulldown = $('#companyId');
		$.each(companies, function(i, company) {
			$pulldown.append($('<option></option>').val(company.companyId)
					.html(company.companyName));
		});

		$pulldown.on("change", function(e) {
			loadFacility(this.value);
		});

		$pulldown.trigger('change');
	};

	function loadFacility(companyId) {
		var $pulldown = $('#facilityId');
		$pulldown.empty();
		var curFacilities = facilities[companyId];
		$.each(curFacilities, function(i, facility) {
			$pulldown.append($('<option></option>').val(facility.facilityId)
					.html(facility.facilityName));
		});

	}
</script>

<title><fmt:message key="customerdetails.title" /></title>
</head>

<body bgcolor="#ffffff" onload="" onresize="resizeFrames()">

	<div class="interface" id="mainPage" style="">
		<div id="searchFrameDiv">
			<div class="contentArea">
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
										<!-- Insert all the search option within this div -->
										<table id="searchTable" width="100%" border="0" cellpadding="0" cellspacing="0" class="tableSearch">
											<%--     Row 1    --%>
											<tr>
												<td width="5%" class="optionTitleBoldRight" nowrap><fmt:message key="label.company" />:</td>
												<td width="45%" class="optionTitleLeft" nowrap><select name="companyId" id="companyId" class="selectBox"></select></td>
											</tr>

											<%--     Row 2    --%>
											<tr>
												<td width="5%" class="optionTitleBoldRight" nowrap><fmt:message key="label.facility" />:</td>
												<td width="45%" class="optionTitleLeft" nowrap><select name="facilityId" id="facilityId" class="selectBox"><option value=""></option></select></td>
											</tr>

											<%--     Row 3    --%>
											<tr>
												<td colspan="8" class="optionTitleBoldLeft">
												<input name="submitSearch" id="submitSearch" type="submit" class="inputBtns" value="<fmt:message key="label.search"/>" onmouseover="this.className='inputBtns inputBtnsOver'"
													onMouseout="this.className='inputBtns'">
													<input name="printResults" id="printResults" type="submit" class="inputBtns" value="<fmt:message key="label.print"/>"  onmouseover="this.className='inputBtns inputBtnsOver'"
													onMouseout="this.className='inputBtns'"></td>
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
				<!-- Search Option Ends -->



			</div>
			<!-- close of contentArea -->

		</div>
		<!-- Search Frame Ends -->

		<!-- Result Frame Begins -->
		<div id="resultFrameDiv" style="margin: 4px 4px 0px 4px;">

			<%--NEw -Transit Page Starts --%>
			<div id="transitPage" style="display: none;" class="optionTitleBoldCenter">
				<br><br><br><fmt:message key="label.pleasewait" /> <br><br><br><img src="/images/rel_interstitial_loading.gif" align="middle">
			</div>
			<!-- Transit Page Ends -->
			<div id="resultGridDiv" name="resultGridDiv">

				<!-- Search results start -->
				<!-- If you want your results section to span only 50% set this to 50% and your main table will be 100% -->
				<table id="resultsMaskTable" width="100%" border="0" cellpadding="0" cellspacing="0">
					<tr id="displayRow">
						<td>
							<div class="roundcont contentContainer">
								<div class="roundright">
									<div class="roundtop">
										<div class="roundtopright">
											<img src="/images/rndBoxes/borderTL.gif" alt="" width="15" height="15" class="corner" style="display: none" />
										</div>
									</div>
									<div class="roundContent">
										<div class="dataContent">
											<iframe scrolling="yes" id="resultFrame" name="resultFrame" width="100%" height="100%" frameborder="0" marginwidth="0" src="customerdetailsresults.jsp"></iframe>
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
			<!-- Result Frame Ends -->
			<!-- close of interface -->

			<!-- You can build your error messages below. Similar divs would have to be built to show any other messages in a inline pop-up.-->
			<!-- Error Messages Begins -->
			<div id="errorMessagesArea" class="errorMessages" style="display: none; overflow: auto;"></div>
			<%-- show legend Begins --%>
			<div id="showLegendArea" style="display: none; overflow: auto;">
				<table width=100% class="tableResults" border="0" cellpadding="0" cellspacing="0">
					<tr>
						<td width="100px" class="black legendText">&nbsp;</td>
						<td class="legendText"><fmt:message key="label.creditstatusstop" /></td>
					</tr>
				</table>
			</div>
			<%-- show legend Ends --%>
</body>
</html:html>