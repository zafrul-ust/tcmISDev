<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
	"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html;charset=UTF-8" language="java"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<%@ taglib uri="/WEB-INF/struts-nested.tld" prefix="nested"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/tcmis.tld" prefix="tcmis"%>

<html:html lang="true">
<head>
	<meta http-equiv="content-type" content="text/html; charset=UTF-8"/>
	<meta http-equiv="expires" content="-1"/>
	<link rel="shortcut icon" href="https://www.tcmis.com/images/buttons/tcmIS.ico"></link>
	<%@ include file="/common/locale.jsp"%>
	<!--Use this tag to get the correct CSS class.
    This looks at what the user's preffered font size and which application he is viewing to set the correct CSS. -->
	<tcmis:gridFontSizeCss />
	<%-- Add any other stylesheets you need for the page here --%>

	<script type="text/javascript" src="/js/common/formchek.js"></script>
	<script type="text/javascript" src="/js/common/commonutil.js"></script>
	<!-- This handels which key press events are disabeled on this page -->
	<script type="text/javascript" src="/js/common/disableKeys.js"></script>
	
	<!-- Add any other javascript you need for the page here -->
	<script src="/js/jquery/jquery-1.6.4.js"></script>
	<script type="text/javascript" src="/js/jquery/autocomplete.js"></script>
	<link rel="stylesheet" type="text/css" href="/css/autocomplete.css" />

	<title><fmt:message key="label.popupwindow"/></title>
	
	<script language="JavaScript" type="text/javascript">
		//add all the javascript messages here, this for internationalization of client side javascript messages
		var messagesData = {
			genericError : "<fmt:message key="generic.error"/>"
		};
		
		var locationArr = new Array();
		//Initialize page layout based on input from parent page
		//returned obj of opener.getInitialEditInventoryPopupData:
		//		Key								Value
		//	-	popupTitle						String - title of the popup
		//	-	useAutoComplete					Boolean - whether autocomplete is set or not
		//	-	id of elements					Object - indicate how element is displayed differently from the default
		//objects of elements:
		//		Key								Value
		//	-	fixedElement					Boolean - whether element is showned as read-only or not, default is false
		//	-	data							single value or Object - data to be displayed; Object, whose keys are name and id, is used if displayed data is different from pass-back data
		function initializeData() {
			try {
				var selectedLocationId;
				var obj = opener.getInitialEditMinMaxPopupData(window.name, $v("callFromResultFrame"));
				for (var elementId in obj) {
					//change default title to what is given 
					if (elementId === "popupTitle")
						document.title = obj[elementId];
					else if (elementId === "useAutoComplete" && obj[elementId])
						setAutoComplete();
					else if ($(elementId)) {
						var curElement = obj[elementId];
						if (typeof curElement.data != "undefined") {
							var dataArr;
							if (isArray(curElement.data))
								dataArr = curElement.data;
							else {
								dataArr = new Array(
									{
										name: curElement.data,
										id: curElement.data
									}
								);
							}
							
							if (curElement.fixedElement)
								changeElementToSpan(elementId, dataArr[0].name, dataArr[0].id);
							else if ($(elementId).tagName.toLowerCase() === "select") {
								//locationId is set aside because it is dependant on supplierId
								if (elementId === "locationId") {
									locationArr = dataArr;
									selectedLocationId = curElement.selectedId;
								} else
									buildDropDown(dataArr, elementId, curElement.selectedId);
							} else if ($(elementId).tagName.toLowerCase() === "input" && $(elementId).type == "hidden" && $(elementId + "Span")) {
								$(elementId).value = dataArr[0].id;
								$(elementId + "Span").innerHTML = dataArr[0].name;
							} else
								$(elementId).value = dataArr[0].id;
						}
					}
				}
				
				//wait till the end so every data is ready to be used
				if (locationArr && locationArr.length > 0) {
					supplierChanged();
					
					//since locationId dropdown is dependent on supplierId dropdown, wait till the dropdowns are ready to set selected locationId
					if (selectedLocationId)
						$("locationId").value = selectedLocationId;
				}
				
				//if catPartNo is prepopulated with data or uneditable, assume the data is correct
				if (!isWhitespace($v("catPartNo")) || $("catPartNo").type != "text")
					isValidPart = true;
			} catch (e) {
				alert(messagesData.genericError);
			}
		}
		
		function setAutoComplete() {
			var fieldSeparator = "!@#$%";
			j$("").ready(function() {
				function log(event, data, formatted) {
					var values = formatted.split(fieldSeparator);
					validatePart(values[0], values[1], values[2], values[3], values[5]);
				}
				
				j$("#catPartNo").autocomplete("/tcmIS/supplier/inventorymanagementmain.do",{
					extraParams : {
						uAction : function() {
							return "partAjaxSearch";
						},
						isInventoryLevelIdRequired : function() {
							return false;
						},
						supplier : function() {
							return j$("#supplierId").val();
						},
						locationId : function() {
							return j$("#locationId").val();
						},
						fieldSeparator: function() {
							return fieldSeparator;
						}
					},
					width: 200,
					cacheLength:0, //if cache is allow, when user manually enters one of the previous suggestions, the suggestion list will show the entered suggestion 
					scrollHeight: 150,
					matchCase: true,
					parse: function (data) {
						var parsed = [];
						if (data.length == 0)
							validatePart();
						else {
							var rows = data.split("\n");
							for (var i=0; i < rows.length; i++) {
								var row = rows[i].trim();
								if (row) {
									row = row.split("|");
									parsed[parsed.length] = {
										data: row,
										value: row[0],
										result: row[0].split(fieldSeparator)[0]
									};
								}
							}
						}
						
						return parsed;
					},
					formatItem: function(data, i, n, value) {
						var values = value.split(fieldSeparator);
						var catPartNo = values[0];
						
						//since the backend algorithm will check input string and put it at the top of the list if it is available,
						//we only need to compare input string with first value of the suggestion
						if (i == 1)
							validatePart(catPartNo, values[1], values[2], values[3], values[5]);
						
						return  catPartNo;
					},
					formatResult: function(data) {
						return data[0].split(fieldSeparator)[0];
					}
				});
				
				j$('#catPartNo').keyup(function () {
					//when user delete the whole input string in one go, we want to return the text box to normal state
					if ($v("catPartNo").length == 0)
						validatePart();
				});
		
				j$("#catPartNo").result(log).next().click(function() {
					j$(this).prev().search();
				});
			});
		}
		
		function validatePart(firstOption, inventoryLevelId, reorderPoint, stockingLevel, isGFP) {
			if (firstOption != $v('catPartNo')) {
				$("catPartNo").className = "inputBox red";
				isValidPart = false;
			} else {
				$("catPartNo").className = "inputBox";
				isValidPart = true;

				$("inventoryLevelId").value = inventoryLevelId;
				$("reorderPoint").value = reorderPoint;
				$("stockingLevel").value = stockingLevel;
				$("isGFP").value = isGFP;
				$("isGFPSpan").innerHTML = isGFP === "Y" ? "Y" : "N";
			}
		}

		function supplierChanged() {
			buildDropDown(locationArr[$v("supplierId")], "locationId");
			locationChanged();
		}
		
		function locationChanged() {
			if (j$("#catPartNo").data("ui-autocomplete"))
				j$("#catPartNo").search();
		}

		function submitPopup() {
			opener.submitEditMinMaxPopup(window, isWhitespace($v("inventoryLevelId")) ? null : $v("inventoryLevelId"), $v("supplierId"), $v("locationId"), $v("catPartNo"), $v("reorderPoint"), $v("stockingLevel"));
		}
		
		function buildDropDown(arr, eleName, selectedId) {
			var obj = $(eleName);
			for (var i = obj.length; i > 0; i--)
				obj[i] = null;
			if (arr == null || arr.length == 0) {
				
			} else {
				var selectedIndex = 0;
				var start = 0;
				for (var i = 0; i < arr.length; i++) {
					if (selectedId && selectedId == arr[i].id)
						selectedIndex = i;
					setOption(start++, arr[i].name, arr[i].id, eleName);
				}
			}
			obj.selectedIndex = selectedIndex;
		}
	</script>
</head>

<body bgcolor="#ffffff"	onload="initializeData();" onunload="opener.closeTransitWin(window.name);">
	<div class="interface" id="mainPage" style="">
		<!-- Search Frame Begins -->
		<div id="searchFrameDiv" class="contentArea">
			<div class="contentArea">
				<table id="searchMaskTable" border="0" cellpadding="0"	cellspacing="0">
					<tr>
						<td>
							<div class="roundcont filterContainer">
								<div class="roundright">
									<div class="roundtop">
										<div class="roundtopright"><img src="/images/rndBoxes/borderTL_filter.gif" alt="" width="15" height="10" class="corner_filter"	style="display: none" /></div>
									</div>
									<div class="roundContent" id="searchTableDiv">
										<!-- Insert all the search option within this div -->
										<!-- Search Option Table Start -->
										<table id="searchTable" width="100%" border="0" cellpadding="0"	cellspacing="0" class="tableSearch">
											<tr>
												<td class="optionTitleBoldRight"><fmt:message key="label.supplier" />:</td>
												<td class="optionTitleLeft">
													<select class="selectBox" name="supplierId" id="supplierId" onchange="supplierChanged();"></select>
												</td>
											</tr>
											<tr>
												<td class="optionTitleBoldRight"><fmt:message key="label.location" />:</td>
												<td class="optionTitleLeft">
													<select class="selectBox" name="locationId" id="locationId" onchange="locationChanged();"></select>
												</td>
											</tr>
											<tr>
												<td class="optionTitleBoldRight"><fmt:message key="label.partno" />:*</td>
												<td class="optionTitleLeft">
													<input class="inputBox" type="text" name="catPartNo" id="catPartNo" value="" size="20"/>
												</td>
											</tr>
											<tr>
												<td class="optionTitleBoldRight"><fmt:message key="label.gfp" />:</td>
												<td class="optionTitleLeft">
													<span id="isGFPSpan"></span>
												</td>
											</tr>
											<tr>
												<td class="optionTitleBoldRight" nowrap="nowrap"><fmt:message key="label.reorderpoint" />:*</td>
												<td class="optionTitleLeft">
													<input class="inputBox" type="text" name="reorderPoint" id="reorderPoint" value="" size="20"/>
												</td>
											</tr>
											<tr>
												<td class="optionTitleBoldRight" nowrap="nowrap"><fmt:message key="label.stockinglevel" />:*</td>
												<td class="optionTitleLeft">
													<input class="inputBox" type="text" name="stockingLevel" id="stockingLevel" value="" size="20"/>
												</td>
											</tr>
											<tr>
												<td>&nbsp;</td>
											</tr>
											<tr>
												<td class="optionTitleLeft" colspan="2" nowrap="nowrap">
													<input onclick="submitPopup();" type="button" class="inputBtns" onmouseover="this.className='inputBtns inputBtnsOver'" onmouseout="this.className='inputBtns'" value="<fmt:message key="label.submit"/>" name="submitUpdateInsertPartBtn" id="submitUpdateInsertPartBtn"/>
													&nbsp;
													<input onclick="window.close();" type="button" class="inputBtns" onmouseover="this.className='inputBtns inputBtnsOver'" onmouseout="this.className='inputBtns'" value="<fmt:message key="label.cancel"/>" name="cancelBtn" id="cancelBtn"/>
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
				<!-- Search Option Ends -->
				
				<!-- Hidden element start -->
				<div id="hiddenElements" style="display: none;">
					<input type="hidden" name="callFromResultFrame" id="callFromResultFrame" value="<tcmis:jsReplace value="${param.callFromResultFrame}"/>" />
					<input type="hidden" name="inventoryLevelId" id="inventoryLevelId" value="" />
					<input type="hidden" name="isGFP" id="isGFP" value="" />
				</div>
				<!-- Hidden elements end -->
			</div>
		</div>
	</div>
</body>
</html:html>