<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
	"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html;charset=UTF-8" language="java"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

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

	<%-- Add any other stylesheets you need for the page here --%>
	
	<!-- This handles all the resizing of the page and frames -->
		<%--
		<link rel="stylesheet" type="text/css" href="/css/dhtmlXGridHaas.css"></link>
		<link rel="stylesheet" type="text/css" href="/yui/build/container/assets/container.css" />
		--%> 
		<!-- This handels the menu style and what happens to the right click on the whole page -->
		<script type="text/javascript" src="/js/menu/milonic_src.js"></script> 
		<script type="text/javascript" src="/js/menu/mmenudom.js"></script> 
		<script type="text/javascript" src="/js/menu/mainmenudata.js"></script> 
		<script type="text/javascript" src="/js/menu/contextmenu.js"></script> 
		<script type="text/javascript" src="/js/common/disableKeys.js" language="JavaScript"></script>
		<script type="text/javascript" src="/js/common/formchek.js" language="JavaScript"></script>
		<script type="text/javascript" src="/js/common/commonutil.js" language="JavaScript"></script>
			<!-- These are for the Grid, uncomment if you are going to use the grid -->
		<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxcommon.js"></script>
		<script type="text/javascript" src="/dhtmlxLayout/codebase/dhtmlxlayout.js"></script>
		<script type="text/javascript" src="/dhtmlxWindows/codebase/dhtmlxwindows.js"></script>
		<script type="text/javascript" src="/js/common/standardGridmain.js"></script>

		
		<%-- For Calendar support --%>
		
		<script src="/js/calendar/newcalendar.js" language="JavaScript"></script>
		<script src="/js/calendar/AnchorPosition.js" language="JavaScript"></script>
		<script src="/js/calendar/PopupWindow.js" language="JavaScript"></script>

	<%-- type ahead --%>
    <script type="text/javascript" src="/js/jquery/jquery-1.6.4.js"></script>
    <script type="text/javascript" src="/js/jquery/autocomplete.js"></script>
    <link rel="stylesheet" type="text/css" href="/css/autocomplete.css" />
		
<%-- Add any other javascript you need for the page here --%>
<script src="/js/supplier/cylindermanagementadd.js" language="JavaScript"></script>

<title><fmt:message key="label.addcylinder" /></title>

<script language="JavaScript" type="text/javascript">
<!--
	//add all the javascript messages here, this for internationalization of client side javascript messages
	var messagesData = new Array();
	messagesData={alert:"<fmt:message key="label.alert"/>",
        and:"<fmt:message key="label.and"/>",
        submitOnlyOnce:"<fmt:message key="label.submitOnlyOnce"/>",
        pleasewait:"<fmt:message key="label.pleasewait"/>",
        validValues:"<fmt:message key="label.validvalues"/>",
        supplier:"<fmt:message key="label.supplier"/>",
        location:"<fmt:message key="label.location"/>",
        serialNumber:"<fmt:message key="label.serialnumber"/>",
        manufacturer:"<fmt:message key="label.manufacturer"/>",
        nsn:"<fmt:message key="label.nsn"/>",
        nsn8120:"<fmt:message key="label.nsn8120"/>",
        nsn6830:"<fmt:message key="label.nsn6830"/>",
        cylinderStatus:"<fmt:message key="label.cylinderstatus"/>",
        conditionCode:"<fmt:message key="label.conditioncode"/>",
        waitingforinputfrom:"<fmt:message key="label.waitingforinputfrom"/>",
        all:"<fmt:message key="label.all"/>",
        cylinderExisted:"<fmt:message key="label.cylinderexisted"/>",
        outStatusMsg:"<fmt:message key="label.cylinderoutstatusmessage"/>",
        errors:"<fmt:message key="label.errors"/>"
    };

    //auto complete
    j$().ready(function() {
        //serial number
        function logSerialNumber(event, data, formatted) {
            j$('#serialNumber').val(formatted.split(":")[0]);
            $("serialNumber").className = "inputBox";
        }

        j$("#serialNumber").autocomplete("getautocompletedata.do",{
                extraParams: {userAction: function() { return 'serialNumber'; }
                             },
                width: 200,
                max: 100,  // default value is 10
                cacheLength:0, // disable cache here because we put "rownum < max" for better performance. Cache will make data off.
                scrollHeight: 200,
                formatItem: function(data, i, n, value) {
                    return  value.split(":")[1].substring(0,40);
                },
                formatResult: function(data, value) {
                    return value.split(":")[1];
                }
        });

        j$("#serialNumber").result(logSerialNumber).next().click(function() {
            j$(this).prev().search();
        });

        //manufacturer
        function logManufacturer(event, data, formatted) {
            j$('#manufacturerIdNo').val(formatted.split(":")[0]);
            $("manufacturerName").className = "inputBox";
        }

        j$("#manufacturerName").autocomplete("getautocompletedata.do",{
                extraParams: {userAction: function() { return 'manufacturer'; },
                              supplier: function() { return j$("#supplier").val(); }
                             },
                width: 200,
                max: 100,  // default value is 10
                cacheLength:0, // disable cache here because we put "rownum < max" for better performance. Cache will make data off.
                scrollHeight: 200,
                formatItem: function(data, i, n, value) {
                    return  value.split(":")[1].substring(0,40);
                },
                formatResult: function(data, value) {
                    return value.split(":")[1];
                }
        });

        j$('#manufacturerName').bind('keyup',(function(e) {
              var keyCode = (e.keyCode ? e.keyCode : e.which);
              if( keyCode != 13 && keyCode != 9) // 13 is for Enter; 9 is for Tab
                invalidateManufacturer();
        }));

        j$("#manufacturerName").result(logManufacturer).next().click(function() {
            j$(this).prev().search();
        });

        //vendor part no
        function logVendorPartNo(event, data, formatted) {
            var tmpData = formatted.split(":")[0];
            j$('#vendorPartNo').val(tmpData.split(";")[0]);
            $("vendorPartNoDisplay").className = "inputBox";
            $("correspondingNsnDisplay").removeAttribute("disabled");
        }

        j$("#vendorPartNoDisplay").autocomplete("getautocompletedata.do",{
                extraParams: {userAction: function() { return 'vendorPartNo'; },
                              supplier: function() { return j$("#supplier").val(); }
                             },
                width: 200,
                max: 100,  // default value is 10
                cacheLength:0, // disable cache here because we put "rownum < max" for better performance. Cache will make data off.
                scrollHeight: 200,
                formatItem: function(data, i, n, value) {
                    return  value.split(":")[1].substring(0,40);
                },
                formatResult: function(data, value) {
                    return value.split(":")[1];
                }
        });

        j$('#vendorPartNoDisplay').bind('keyup',(function(e) {
              var keyCode = (e.keyCode ? e.keyCode : e.which);

              if( keyCode != 13 && keyCode != 9) // 13 is for Enter; 9 is for Tab
                invalidateVendorPartNo();
        }));


        j$("#vendorPartNoDisplay").result(logVendorPartNo).next().click(function() {
            j$(this).prev().search();
        });

        //corresponding NSN
        function logCorrespondingNsn(event, data, formatted) {
            var tmpData = formatted.split(":")[0];
            j$('#correspondingNsn').val(tmpData.split(";")[0]);
            if (tmpData.split(";")[1] != 'X') {
                j$('#refurbCategoryDisplay').text(tmpData.split(";")[1]);
                $("refurbCategory").value = tmpData.split(";")[1];
            }else {
                j$('#refurbCategoryDisplay').text('');
                $("refurbCategory").value = '';
            }
            if (tmpData.split(";")[2] != 'X') {
                j$('#conversionGroupDisplay').text(tmpData.split(";")[2]);
                $("conversionGroup").value = tmpData.split(";")[2];
            }else {
                j$('#conversionGroupDisplay').text('');
                $("conversionGroup").value = '';
            }
            $("correspondingNsnDisplay").className = "inputBox";
        }

        j$("#correspondingNsnDisplay").autocomplete("getautocompletedata.do",{
                extraParams: {userAction: function() { return 'correspondingNsn'; },
                              supplier: function() { return j$("#supplier").val(); },
                              vendorPartNo: function() { return j$("#vendorPartNo").val(); }
                             },
                width: 200,
                max: 100,  // default value is 10
                cacheLength:0, // disable cache here because we put "rownum < max" for better performance. Cache will make data off.
                scrollHeight: 200,
                formatItem: function(data, i, n, value) {
                    return  value.split(":")[1].substring(0,40);
                },
                formatResult: function(data, value) {
                    return value.split(":")[1];
                }
        });

        j$('#correspondingNsnDisplay').bind('keyup',(function(e) {
              var keyCode = (e.keyCode ? e.keyCode : e.which);

              if( keyCode != 13 && keyCode != 9) // 13 is for Enter; 9 is for Tab
                invalidateCorrespondingNsn();
        }));


        j$("#correspondingNsnDisplay").result(logCorrespondingNsn).next().click(function() {
            j$(this).prev().search();
        });

    });

// -->
</script>
</head>
	
	<body bgcolor="#ffffff" onload="myOnLoad('${param.userAction}');" onunload="closeThisWindow();closeAllchildren();">
		<tcmis:form action="/cylindermanagementresults.do" onsubmit="return submitOnlyOnce();">
			<div id="transitPage" class="optionTitleBoldCenter" style="display: none;"><br/><br/><br/><fmt:message key="label.pleasewait" />
			</div>
			<div class="contentArea" id="mainPage">

			<script type="text/javascript">
				<!--
				/*Check if there is any error messages to show and set the variable you use in javascript to true or false.*/
				<c:choose>
					<c:when test="${empty tcmISError}">
					 showErrorMessage = false;
					</c:when>
					<c:otherwise>
					 showErrorMessage = true;
					</c:otherwise>
				</c:choose>
			//-->
			</script>
			<!-- Error Messages Ends -->
			
			<!-- Search Option Begins -->
			<table id="searchMaskTable" width="100%" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td>
					<div class="roundcont filterContainer">
					<div class="roundright">
					<div class="roundtop">
						<div class="roundtopright"><img src="/images/rndBoxes/borderTL_filter.gif" alt="" width="15" height="10" class="corner_filter" style="display: none" />
						</div>
					</div>
					<div class="roundContent">
					<!-- Insert all the search option within this div -->
					<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableSearch">
					    <tr>
                            <td class="optionTitleBoldRight"><fmt:message key="label.supplier"/>:&nbsp;&nbsp;</td>
                            <td>
                                <c:out value="${param.supplierName}" />
                            </td>
                        </tr>
						<tr>
							<td class="optionTitleBoldRight"><fmt:message key="label.location" />:<span style='font-size:12.0pt;color:red'>*</span></td>
							<td>
                        		  <select name="locationId" id="locationId" class="selectBox">
                        		  	<option value=""><fmt:message key='label.pleaseselect'/></option>
                                    <c:set var="selectedLocation" value='${param.locationId}'/>
                        		  	<c:forEach var="bean" items="${cylinderLocationColl}" varStatus="locationStatus">
                        		  	    <c:set var="tmpSelect" value=''/>
                                        <c:if test="${selectedLocation == bean.locationId}">
                                            <c:set var="tmpSelect" value='selected'/>
                                        </c:if>
                                        <option ${tmpSelect} value="<c:out value="${bean.locationId}"/>"><c:out value="${bean.locationDesc}"/></option>
                                    </c:forEach>
								</select>
							</td>
						</tr>
                        <tr>
                            <td class="optionTitleBoldRight"><fmt:message key="label.serialnumber" />:<span style='font-size:12.0pt;color:red'>*</span></td>
                            <td>
                                <input type="text" id="serialNumber" name="serialNumber" value="<c:out value='${param.serialNumber}'/>" class="inputBox" size="40" />
                            </td>
                        </tr>
						<tr>
							<td class="optionTitleBoldRight"><fmt:message key="label.manufacturer" />:<span style='font-size:12.0pt;color:red'>*</span></td>
							<td>
								<input name="manufacturerName" id="manufacturerName" type="text" class="inputBox" value="<c:out value="${param.manufacturerName}"/>" size="40">
								<input name="manufacturerIdNo" id="manufacturerIdNo" type="hidden" value="<c:out value="${param.manufacturerIdNo}"/>">
                            </td>
						</tr>
						<tr>
							<td class="optionTitleBoldRight"><fmt:message key="label.nsn8120" />:<span style='font-size:12.0pt;color:red'>*</span></td>
							<td>
							    <input type="text" id="vendorPartNoDisplay" value="<c:out value='${param.vendorPartNo}'/>" class="inputBox" size="40" />
								<input name="vendorPartNo" id="vendorPartNo" type="hidden" value="<c:out value="${param.vendorPartNo}"/>">
                            </td>
						</tr>
						<tr>
							<td class="optionTitleBoldRight"><fmt:message key="label.nsn6830" />:<span style='font-size:12.0pt;color:red'>*</span></td>
							<td>
							    <input type="text" id="correspondingNsnDisplay" value="<c:out value='${param.correspondingNsn}'/>" <c:if test="${empty param.vendorPartNo}">disabled="disabled"</c:if> class="inputBox" size="40" />
								<input name="correspondingNsn" id="correspondingNsn" type="hidden" value="<c:out value="${param.correspondingNsn}"/>">
                            </td>
						</tr>
					    <tr>
                            <td class="optionTitleBoldRight"><fmt:message key="label.category"/>:&nbsp;&nbsp;</td>
                            <td>
                                <div id="refurbCategoryDisplay" id="refurbCategoryDisplay">${param.refurbCategory}</div>
                                <input type="hidden" name="refurbCategory" id="refurbCategory" value="${param.refurbCategory}">
                            </td>
                        </tr>
					    <tr>
                            <td class="optionTitleBoldRight"><fmt:message key="label.conversiongroup"/>:&nbsp;&nbsp;</td>
                            <td>
                                <div id="conversionGroupDisplay" id="conversionGroupDisplay">${param.conversionGroup}</div>
                                <input type="hidden" name="conversionGroup" id="conversionGroup" value="${param.conversionGroup}">
                            </td>
                        </tr>
						<tr>
							<td class="optionTitleBoldRight"><fmt:message key="label.status" />:<span style='font-size:12.0pt;color:red'>*</span></td>
							<td>
                        		  <select name="status" id="status" class="selectBox">
                        		  	<c:set var="selectedStatus" value='${param.status}'/>
                                    <c:set var="tmpSelect" value=''/>
                                    <c:if test="${selectedStatus == 'A'}">
                                        <c:set var="tmpSelect" value='selected'/>
                                    </c:if>
                                    <option ${tmpSelect} value="A"><fmt:message key='label.active'/></option>
                                    <c:set var="tmpSelect" value=''/>
                                    <c:if test="${selectedStatus == 'I'}">
                                        <c:set var="tmpSelect" value='selected'/>
                                    </c:if>
                                    <option ${tmpSelect} value="I"><fmt:message key='label.inactive'/></option>
								</select>
							</td>
						</tr>
						<tr>
							<td class="optionTitleBoldRight"><fmt:message key="label.cylinderstatus" />:<span style='font-size:12.0pt;color:red'>*</span></td>
							<td>
                        		  <select name="cylinderStatus" id="cylinderStatus" class="selectBox" onchange="cylinderStatusChanged()">
                        		  	<option value=""><fmt:message key='label.pleaseselect'/></option>
                        		  	<c:set var="selectedCylinderStatus" value='${param.cylinderStatus}'/>
                        		  	<c:forEach var="bean" items="${cylinderStatusColl}" varStatus="cylinderStatus">
                        		  	    <c:set var="tmpSelect" value=''/>
                                        <c:if test="${bean.cylinderStatus == selectedCylinderStatus}">
                                            <c:set var="tmpSelect" value='selected'/>
                                        </c:if>
                                        <option ${tmpSelect} value="<c:out value="${bean.cylinderStatus}"/>"><c:out value="${bean.cylinderStatus}"/></option>
                                    </c:forEach>
								</select>
							</td>
						</tr>
						<tr>
							<td class="optionTitleBoldRight"><fmt:message key="label.conditioncode" />:<span style='font-size:12.0pt;color:red'>*</span></td>
							<td>
                        		  <select name="cylinderConditionCode" id="cylinderConditionCode" class="selectBox">
                        		  	<option value=""><fmt:message key='label.pleaseselect'/></option>
                        		  	<c:set var="selectedConditionCode" value='${param.cylinderConditionCode}'/>
                        		  	<c:forEach var="bean" items="${cylinderConditionCodeColl}" varStatus="conditionCodeStatus">
                        		  	    <c:set var="tmpSelect" value=''/>
                                        <c:if test="${bean.cylinderConditionCode == selectedConditionCode}">
                                            <c:set var="tmpSelect" value='selected'/>
                                        </c:if>
                                        <option ${tmpSelect} value="<c:out value="${bean.cylinderConditionCode}"/>"><c:out value="${bean.cylinderConditionCode}"/></option>
                                    </c:forEach>
								</select>
							</td>
						</tr>
					    <tr>
                            <td class="optionTitleBoldRight"><fmt:message key="label.returnfromlocation"/>:&nbsp;&nbsp;</td>
                            <td>
                                <input name="returnFromLocation" id="returnFromLocation" type="text" class="inputBox" value="<c:out value="${param.returnFromLocation}"/>" size="40">
                            </td>
                        </tr>
					    <tr>
                            <td class="optionTitleBoldRight"><fmt:message key="label.lastshippeddodaac"/>:&nbsp;&nbsp;</td>
                            <td>
                                <input name="lastShippedDodaac" id="lastShippedDodaac" type="text" class="inputBox" value="<c:out value="${param.lastShippedDodaac}"/>" size="40">
                            </td>
                        </tr>
					    <tr>
                            <td class="optionTitleBoldRight"><fmt:message key="label.latesthydrotestdate"/>:&nbsp;&nbsp;</td>
                            <td>
                                <input class="inputBox pointer" name="latestHydroTestDate" id="latestHydroTestDate" type="text" value="<c:out value="${param.latestHydroTestDate}"/>" size="20" readonly="true" onClick="return getCalendar(document.getElementById('latestHydroTestDate'));">
                            </td>
                        </tr>
                        <tr>
                            <td class="optionTitleBoldRight"><fmt:message key="label.documentnumber"/>:&nbsp;&nbsp;</td>
                            <td>
                                <input name="documentNumber" id="documentNumber" type="text" class="inputBox" value="<c:out value="${param.documentNumber}"/>" size="40">
                            </td>
                        </tr>
                        <tr>
                            <td class="optionTitleBoldRight"><fmt:message key="label.notes"/>:&nbsp;&nbsp;</td>
                            <td>
                                <textarea cols="50" rows="3" class="inputBox" name="notes" id="notes">${param.notes}</textarea>
                            </td>
                        </tr>
                        <tr>
							<td class="optionTitleBoldRight">&nbsp;</td>
							<td class="optionTitleBoldRight">&nbsp;</td>
						</tr>
						<tr>
							<td class="optionTitleBoldRight"><input name="updateButton" id="updateButton" type="button" class="inputBtns" value="<fmt:message key="label.submit"/>"
								onmouseover="this.className='inputBtns inputBtnsOver'"
								onmouseout="this.className='inputBtns'"
								onclick="submitUpdate();">
							</td>
							<td class="optionTitleBoldLeft"><input name="submitCancel" id="submitCancel" type="button" class="inputBtns" value="<fmt:message key="label.cancel"/>"
								onmouseover="this.className='inputBtns inputBtnsOver'"
								onmouseout="this.className='inputBtns'" onclick="closeWindow();">
							</td>
						</tr>
					</table>
					<!-- End search options -->
					</div>
					<div class="roundbottom">
						<div class="roundbottomright"><img src="/images/rndBoxes/borderBL.gif" alt="" width="15" height="15" class="corner" style="display: none" /></div>
					</div>
					</div>
					</div>
					</td>
				</tr>
			</table>
			<!-- Search Option Ends -->
		<div id="transitDialogWin" class="errorMessages" style="display:none;left:20%;top:20%;z-index:5;">
		</div>

		<div id="transitDialogWinBody" class="errorMessages" style="display: none;">
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

			<div class="spacerY">&nbsp;</div>

			<div id="hiddenElements" style="display: none;">
			    <input name="userAction" id="userAction" type="hidden">
				<input type="hidden" name="cylinderTrackingId" id="cylinderTrackingId" value='<tcmis:jsReplace value="${param.cylinderTrackingId}"/>'>
				<input type="hidden" name="supplier" id="supplier" value='<tcmis:jsReplace value="${param.supplier}"/>'>
				<input type="hidden" name="supplierName" id="supplierName" value='<tcmis:jsReplace value="${param.supplierName}"/>'>
				<input type="hidden" name="locationId" id="locationId" value='<tcmis:jsReplace value="${param.locationId}"/>'>
				<input type="hidden" name="calledFrom" id="calledFrom" value='<tcmis:jsReplace value="${param.calledFrom}"/>'>
				<input type="hidden" name="errorMsg" id="errorMsg" value="${tcmISError}">
            </div>
			<!-- Hidden elements end -->
			</div>
			<!-- close of contentArea -->
				
		</tcmis:form>

	</body>
</html:html>
