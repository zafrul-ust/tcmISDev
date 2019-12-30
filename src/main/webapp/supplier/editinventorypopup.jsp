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
	
	<!-- For Calendar support -->
	<script type="text/javascript" src="/js/calendar/newcalendar.js"></script>
	<script type="text/javascript" src="/js/calendar/AnchorPosition.js"></script>
	<script type="text/javascript" src="/js/calendar/PopupWindow.js"></script>
	
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
		var domRequired = false;
		//Initialize page layout based on input from parent page
		//returned obj of opener.getInitialEditInventoryPopupData:
		//		Key								Value
		//	-	popupTitle						String - title of the popup
		//	-	useAutoComplete					Boolean - whether autocomplete is set or not
		//	-	domRequired						Boolean - whether the given part is initially assumed to require DOM or not
		//	-	id of elements					Object - indicate how element is displayed differently from the default
		//objects of elements:
		//		Key								Value
		//	-	fixedElement					Boolean - whether element is showned as read-only or not, default is false
		//	-	data							single value or Object - data to be displayed; Object, whose keys are name and id, is used if displayed data is different from pass-back data
		//	-	checked							Boolean - whether element (if it is a checkbox) is checked or not
		function initializeData() {
			try {
				var selectedLocationId;
				var obj = opener.getInitialEditInventoryPopupData(window.name, $v("callFromResultFrame"));
				for (var elementId in obj) {
					//change default title to what is given 
					if (elementId === "popupTitle")
						document.title = obj[elementId];
					else if (elementId === "useAutoComplete" && obj[elementId])
							setAutoComplete();
					else if (elementId === "domRequired")
						domRequired = obj[elementId];
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
							} else
								$(elementId).value = dataArr[0].id;
						} else if (typeof curElement.checked === "boolean") {
							if ($(elementId).tagName.toLowerCase() === "input" && $(elementId).type === 'checkbox')
								$(elementId).checked = curElement.checked ? "checked" : "";
							if (curElement.fixedElement)
								$(elementId).disabled = "disabled";
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
				
				//if catPartNo is prepopulated with data or uneditable, assume the data is correct. If it is not prepopulated, reset the domRequired flag to false.
				if (!isWhitespace($v("catPartNo")) || $("catPartNo").type != "text") {
					isValidPart = true;
					isValidDOM = true;
				} else if (isWhitespace($v("catPartNo")))
					domRequired = false;

				//if bin is prepopulated with data or uneditable, assume the data is correct
				if (!isWhitespace($v("bin")) || $("bin").type != "text")
					isValidBin = true;
				
				if (typeof $("dateOfManufacture").onchange != "undefined") {
					$("dateOfManufacture").onchange = validateDOM;
					validateDOM();
				}
			} catch (e) {
				alert(messagesData.genericError);
			}
		}
		
		function setAutoComplete() {
			var fieldSeparator = "!@#$%";
			j$("").ready(function() {
				function log(event, data, formatted) {
					validatePart(formatted.split(fieldSeparator)[0], formatted.split(fieldSeparator)[4]);
				}
				
				j$("#catPartNo").autocomplete("/tcmIS/supplier/inventorymanagementmain.do",{
					extraParams : {
						uAction : function() {
							return "partAjaxSearch";
						},
						isInventoryLevelIdRequired : function() {
							return true;
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
							validatePart(catPartNo, values[4]);
						
						return  catPartNo;
					},
					formatResult: function(data) {
						return data[0].split(fieldSeparator)[0];
					}
				});
				
				j$('#catPartNo').keyup(function () {
					if ($v("catPartNo").length == 0)
						validatePart();
				});
		
				j$("#catPartNo").result(log).next().click(function() {
					j$(this).prev().search();
				});
				
				function binLog(event, data, formatted) {
					validateBin(formatted.split(fieldSeparator)[0]);
				}
				
				j$("#bin").autocomplete("/tcmIS/supplier/inventorymanagementmain.do",{
					extraParams : {
						uAction : function() {
							return "binAjaxSearch";
						},
						activeOnly : function() {
							return "Y";
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
							validateBin();
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
						var bin = value.split(fieldSeparator)[0];
						
						//since the backend algorithm will check input string and put it at the top of the list if it is available,
						//we only need to compare input string with first value of the suggestion
						if (i == 1)
							validateBin(bin);
						
						return  bin;
					},
					formatResult: function(data) {
						return data[0].split(fieldSeparator)[0];
					}
				});
				
				j$('#bin').keyup(function () {
					//when user delete the whole input string in one go, we want to return the text box to normal state
					if ($v("bin").length == 0)
						validateBin("");
				});
		
				j$("#bin").result(binLog).next().click(function() {
					j$(this).prev().search();
				});
			});
		}
		
		function validateBin(firstOption) {
			if (firstOption != $v('bin')) {
				$("bin").className = "inputBox red";
				isValidBin = false;
			} else {
				$("bin").className = "inputBox";
				isValidBin = true;
			}
		}
		
		function validatePart(firstOption, curPartDOMRequired) {
			//if a Part doesn't exist, we can assume domRequired is N
			if (firstOption != $v('catPartNo')) {
				$("catPartNo").className = "inputBox red";
				isValidPart = false;
				domRequired = false;
			} else {
				$("catPartNo").className = "inputBox";
				isValidPart = true;
				domRequired = curPartDOMRequired == 'Y' ? true : false;
			}
			validateDOM();
		}
		
		function validateDOM() {
			if (domRequired && isWhitespace($v("dateOfManufacture"))) {
				$("dateOfManufacture").className = "inputBox red";
				isValidDOM = false;
			} else {
				$("dateOfManufacture").className = "inputBox";
				isValidDOM = true;
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
			opener.submitEditInventoryPopup(window, isWhitespace($v("inventoryId")) ? null : $v("inventoryId"), $v("vendorPO"), $v("supplierId"), $v("locationId"), $v("catPartNo"), $v("quantity"), $v("lot"), $v("expirationDate"), $v("dateOfManufacture"), $v("bolTrackingNum"), $v("bin"), $("isVMI").checked ? "Y" : "N", $v("notes"));
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
		
		function openEditBinPopup() {
			openWinGeneric("/tcmIS/supplier/editbinpopup.do", "updateInsertBin", "370", "240", "yes", "80", "80", "yes");
		}
		
		function getInitialEditBinPopupData() {
			var popupData = opener.getInitialEditBinPopupData();
			popupData.supplierId = {
				fixedElement : true,
				data : [
					{
						id : $v("supplierId"),
						name : $("supplierId").type === "hidden" ? $("supplierIdDisplay").innerHTML : $("supplierId").options[$("supplierId").selectedIndex].text
					}
				]
			};
			popupData.locationId = {
				fixedElement : true,
				data : [
					{
						id : $v("locationId"),
						name : $("locationId").type === "hidden" ? $("locationIdDisplay").innerHTML : $("locationId").options[$("locationId").selectedIndex].text
					}
				]
			};
			
			return popupData;
		}
		
		function submitEditBinPopup(popupObj, popupSupplierId, popupLocationId, popupBin, popupIsActive) {
			opener.submitEditBinPopup(popupObj, popupSupplierId, popupLocationId, popupBin, popupIsActive);
		}
		
		function doAfterBinSubmit(popupSupplierId, popupLocationId, popupBin, popupIsActive) {
			if (popupBin === $v("bin")) {
				if (popupIsActive === "N")
					validateBin($v("bin") + "new");
				else
					validateBin($v("bin"));
			}
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
										<div class="boxhead">
											<div id="mainUpdateLinks">
												<span id="manageBinLink">
													<a href="#" onclick="openEditBinPopup();"><fmt:message key="label.managestoragelocations"/></a>
												</span>
											</div> <%-- mainUpdateLinks Ends --%>
										</div>
										<!-- Insert all the search option within this div -->
										<!-- Search Option Table Start -->
										<table id="searchTable" width="100%" border="0" cellpadding="0"	cellspacing="0" class="tableSearch">
											<tr>
												<td class="optionTitleBoldRight"><fmt:message key="label.supplier" />:</td>
												<td class="optionTitleLeft">
													<select class="selectBox" name="supplierId" id="supplierId"  onchange="supplierChanged();"></select>
												</td>
											</tr>
											<tr>
												<td class="optionTitleBoldRight"><fmt:message key="label.location" />:</td>
												<td class="optionTitleLeft">
													<select class="selectBox" name="locationId" id="locationId"  onchange="locationChanged();"></select>
												</td>
											</tr>
											<tr>
												<td class="optionTitleBoldRight"><fmt:message key="label.partno" />:*</td>
												<td class="optionTitleLeft">
													<input class="inputBox" type="text" name="catPartNo" id="catPartNo" value="" size="15" maxlength="30"/>
												</td>
											</tr>
											<tr>
												<td class="optionTitleBoldRight" nowrap="nowrap"><fmt:message key="label.ponumber" />:</td>
												<td class="optionTitleLeft">
													<input class="inputBox" type="text" name="vendorPO" id="vendorPO" value="" size="15" maxlength="30"/>
												</td>
											</tr>
											<tr>
												<td class="optionTitleBoldRight"><fmt:message key="label.quantity" />:*</td>
												<td class="optionTitleLeft">
													<input class="inputBox" type="text" name="quantity" id="quantity" value="" size="15"/>
												</td>
											</tr>
											<tr>
												<td class="optionTitleBoldRight"><fmt:message key="label.lot" />:*</td>
												<td class="optionTitleLeft">
													<input class="inputBox" type="text" name="lot" id="lot" value="" size="15" maxlength="36"/>
												</td>
											</tr>
											<tr>
												<td class="optionTitleBoldRight" nowrap="nowrap"><fmt:message key="label.storagelocation" />:</td>
												<td class="optionTitleLeft">
													<input class="inputBox" type="text" name="bin" id="bin" value="" size="15" maxlength="30"/>
												</td>
											</tr>
											<tr>
												<td class="optionTitleBoldRight" nowrap="nowrap"><fmt:message key="label.boltrackingnumber" />:</td>
												<td class="optionTitleLeft">
													<input class="inputBox" type="text" name="bolTrackingNum" id="bolTrackingNum" value="" size="15" maxlength="30"/>
												</td>
											</tr>
											<tr>
												<td class="optionTitleBoldRight" nowrap="nowrap"><fmt:message key="label.expirationdate" />:*</td>
												<td class="optionTitleLeft">
													<input type="text" readonly="readonly" name="expirationDate" id="expirationDate" onclick="return getCalendar(document.getElementById('expirationDate'),null,null,null,null,'y');" maxlength="10" size="15" class="inputBox pointer"/>
												</td>
											</tr>
											<tr>
												<td class="optionTitleBoldRight" nowrap="nowrap"><fmt:message key="label.dateofmanufacture" />:</td>
												<td class="optionTitleLeft">
													<input type="text" readonly="readonly" name="dateOfManufacture" id="dateOfManufacture" onclick="return getCalendar(document.getElementById('dateOfManufacture'),null,null,null,'<tcmis:getDateTag numberOfDaysFromToday="0" datePattern="${dateFormatPattern}"/>');" maxlength="10" size="15" class="inputBox pointer"/>
												</td>
											</tr>
											<tr>
												<td class="optionTitleBoldRight" nowrap="nowrap"><fmt:message key="label.notes" />:</td>
												<td class="optionTitleLeft">
													<textarea name="notes" id="notes" cols="30" rows="3" class="inputBox"></textarea>
												</td>
											</tr>
											<tr>
												<td colspan="2">
													<input type="checkbox" name="isVMI" id="isVMI" /><span class="optionTitleBoldLeft"><fmt:message key="label.vmi" /></span>
													&nbsp;
												</td>
											</tr>
											<tr>
												<td>&nbsp;</td>
											</tr>
											<tr>
												<td class="optionTitleLeft" colspan="2" nowrap="nowrap">
													<input onclick="submitPopup();" type="button" class="inputBtns" onmouseover="this.className='inputBtns inputBtnsOver'" onmouseout="this.className='inputBtns'" value="<fmt:message key="label.submit"/>" name="submitInventoryBtn" id="submitInventoryBtn"/>
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
					<input type="hidden" name="inventoryId" id="inventoryId" value="" />
				</div>
				<!-- Hidden elements end -->
			</div>
		</div>
	</div>
</body>
</html:html>