<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
	"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>

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

<!--Use this tag to get the correct CSS class.
This looks at what the user's preffered font size and which application he is viewing to set the correct CSS. -->
<tcmis:fontSizeCss />
<!-- CSS for YUI -->
<%--
<link rel="stylesheet" type="text/css" href="/yui/build/container/assets/container.css" />
--%>

<!-- Add any other stylesheets you need for the page here -->
<link rel="stylesheet" type="text/css" href="/css/tabs.css"></link>

<%--
<link rel="stylesheet" type="text/css" href="/css/dhtmlXGridHaas.css"></link>
--%>

<script type="text/javascript" src="/js/common/formchek.js"></script>
<script type="text/javascript" src="/js/common/commonutil.js"></script>
<!-- This handels which key press events are disabeled on this page -->
<script type="text/javascript" src="/js/common/disableKeys.js"></script>

<!-- This handels the menu style and what happens to the right click on the whole page -->
<script type="text/javascript" src="/js/menu/milonic_src.js"></script>
<script type="text/javascript" src="/js/menu/mmenudom.js"></script>
<script type="text/javascript" src="/js/menu/mainmenudata.js"></script>
<script type="text/javascript" src="/js/menu/contextmenu.js"></script>
<%@ include file="/common/rightclickmenudata.jsp" %>

<!-- For Calendar support -->
<%--
<script type="text/javascript" src="/js/calendar/newcalendar.js"></script>
<script type="text/javascript" src="/js/calendar/AnchorPosition.js"></script>
<script type="text/javascript" src="/js/calendar/PopupWindow.js"></script>
--%>

<!-- Add any other javascript you need for the page here -->
<script type="text/javascript" src="/js/hub/newitemform.js"></script>

<!-- These are for the Grid, uncomment if you are going to use the grid -->
<%--<script src="/js/dhtmlxGrid/dhtmlXCommon.js"></script>
<script src="/js/dhtmlxGrid/dhtmlXGridHaas.js"></script>
<script src="/js/dhtmlxGrid/dhtmlXGridCell.js"></script>
<script src="/js/dhtmlxGrid/dhtmlXGrid_excell_mro.js"></script>
--%>

<!-- This is for the YUI, uncomment if you will use this -->
<script type="text/javascript" src="/yui/build/yahoo/yahoo.js" ></script>
<script type="text/javascript" src="/yui/build/event/event.js" ></script>
<script type="text/javascript" src="/yui/build/dom/dom.js" ></script>
<script type="text/javascript" src="/yui/build/animation/animation.js" ></script>
<script type="text/javascript" src="/yui/build/container/container.js"></script>
<script type="text/javascript" src="/js/common/waitDialog.js"></script>
<script type="text/javascript" src="/yui/build/dragdrop/dragdrop.js" ></script>

<title>
	<fmt:message key="label.newitemform"/>
</title>

<script language="JavaScript" type="text/javascript">
<!--

var netWtRequired = new Array(
<c:forEach var="sizeUnitBean" items="${sizeUnitNetWtRequiredCollection}" varStatus="status">
     <c:choose>
       <c:when test="${status.index == 0}">
         "<c:out value="${status.current.sizeUnit}" escapeXml="false"/>"
       </c:when>
       <c:otherwise>
         ,"<c:out value="${status.current.sizeUnit}" escapeXml="false"/>"
       </c:otherwise>
     </c:choose>
</c:forEach>
);
// -->
</script>

<script language="JavaScript" type="text/javascript">
<!--
//add all the javascript messages here, this for internationalization of client side javascript messages
var messagesData = new Array();
messagesData={alert:"<fmt:message key="label.alert"/>",and:"<fmt:message key="label.and"/>",component:"<fmt:message key="label.component"/>",
validvalues:"<fmt:message key="label.validvalues"/>",submitOnlyOnce:"<fmt:message key="label.submitOnlyOnce"/>"
};
// -->
</script>
</head>

<c:choose>
 <c:when test="${submitted == 'Yes' || submitted == 'Message'}">
 <body bgcolor="#ffffff" onmouseup="toggleContextMenuToNormal()" onLoad="editOnLoad();submitMainPage()">
 </c:when>
 <c:otherwise>
 <body bgcolor="#ffffff" onmouseup="toggleContextMenuToNormal()" onLoad="editOnLoad()">
 </c:otherwise>
</c:choose>
<c:set var="changePackaging" value="${newChemHeaderViewBean.newChemPackagingChange}"  />
<c:set var="readonly" value='readonly'/>

<c:if test="${changePackaging == 'YES'}">
<script language="JavaScript" type="text/javascript">
<!--
with(milonic=new menuname("nchemcontextMenu")){
top="offset=2"
style = contextStyle;
margin=3
aI("text=<fmt:message key="label.addcomponent"/>;url=javascript:addNewTab();");
aI("text=<fmt:message key="label.removecomponent"/>;url=javascript:removeTab();");
}
// -->
</script>
<c:set var="readonly" value=''/>
</c:if>

<tcmis:form action="/newitemform.do" onsubmit="return submitOnlyOnce();">

<!-- Transit Page Begins -->
<div id="transitPage" class="optionTitleBoldCenter" style="display: none;">
 <br><br><br><fmt:message key="label.pleasewait"/>
 <br><br><br><img src="/images/rel_interstitial_loading.gif" align="middle">
</div>
<!-- Transit Page Ends -->

<div class="interface" id="mainPage"> <!-- start of interface-->

<div class="contentArea">  <!-- start of contentArea-->

<table id="searchMaskTable" width="750" border="0" cellpadding="0" cellspacing="0">
<tr><td>
<div class="roundcont filterContainer">
 <div class="roundright">
   <div class="roundtop">
     <div class="roundtopright"> <img src="/images/rndBoxes/borderTL_filter.gif" alt="" width="15" height="10" class="corner_filter" style="display: none" /></div>
   </div>
   <div class="roundContent">
    <!-- Insert all the search option within this div -->
    <!-- Search Option Table Start -->
    <table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableSearch">
	    <c:set var="submitted" value='${submitted}'/>
	    <c:if test="${submitted == 'Yes'}">
	      <tr>
		      <td width="100%" colspan="2" class="optionTitleBoldCenter"><fmt:message key="label.newitemsubmitted"/></td>
	      </tr>
	    </c:if>
	    <c:if test="${submitted == 'Error'}">
	      <tr>
		      <td width="100%" colspan="2" class="errorMessages"><fmt:message key="label.newitemsubmittederror"/></td>
	      </tr>
	    </c:if>
	    <c:if test="${submitted == 'Message'}">
	      	<tr>
    	    	<td width="100%" colspan="2" class="optionTitleBoldCenter"><fmt:message key="label.newitemsubmitted"/></td>
       		</tr>
       		<tr>
        		<td width="100%" colspan="2" class="optionTitleBoldCenter">${messageToUser}</td>
       		</tr>
     	</c:if>

      <tr>
        <td width="10%" class="optionTitleBoldRight"><fmt:message key="label.catalog"/>:</td>
        <td width="40%" class="optionTitleLeft">
	         <c:out value='${newChemHeaderViewBean.catalogDesc}'/>
        </td>
      </tr>

      <tr>
        <td width="10%" class="optionTitleBoldRight"><fmt:message key="label.partno"/>:</td>
        <td width="40%" class="optionTitleLeft">
					 <c:out value='${newChemHeaderViewBean.catPartNo}'/>
        </td>
     </tr>

      <tr>
        <td width="10%" class="optionTitleBoldRight"><fmt:message key="label.partdesc"/>:</td>
        <td width="90%" class="optionTitleLeft" colspan="3">
					<textarea cols="100" rows="3" class="inputBox" name="partDesc" id="partDesc">${newChemHeaderViewBean.partDescription}</textarea>
        </td>

      </tr>

      <tr>
        <td colspan="2" class="alignLeft">
        	<c:set var="submitted" value='${submitted}'/>
         	<c:choose>
          	<c:when test="${submitted == 'Error' || submitted == 'No'}">
           		<input name="submitData" id="submitData" type="submit" class="inputBtns" value="<fmt:message key="button.submit"/>" onmouseover="this.className='inputBtns inputBtnsOver'" onMouseout="this.className='inputBtns'" onclick="return submitFormData();">
            	<input name="Cancel" id="Cancel" type="button" value="<fmt:message key="label.cancel"/>" class="inputBtns" onmouseover="this.className='inputBtns inputBtnsOver'" onmouseout="this.className='inputBtns'" onclick="closeWindow();">
         	</c:when>
          	<c:otherwise>
           		<input name="Close" id="Close" type="button" value="<fmt:message key="label.close"/>" class="inputBtns" onmouseover="this.className='inputBtns inputBtnsOver'" onmouseout="this.className='inputBtns'" onclick="closeWindow();">
          	</c:otherwise>
         	</c:choose>
        </td>
      </tr>
      </table>
<!-- End search options -->
   </div>
   <div class="roundbottom">
     <div class="roundbottomright"> <img src="/images/rndBoxes/borderBL.gif" alt="" width="15" height="15" class="corner" style="display: none" /> </div>
   </div>
 </div>
</div>
</td></tr>
</table>
<!-- Search Option Ends -->


<!-- Initialize Menus -->
<script>
 drawMenus();
</script>

<!-- CSS Tabs -->
<div id="newChemTabs">
 <ul id="mainTabList">
  <%--<li id="homeli"><a href="#" id="homeLink" class="selectedTab" onclick="togglePage('home'); return false;"><span id="homeLinkSpan" class="selectedSpanTab"><img src="/images/home.gif" class="iconImage">Home<br class="brNoHeight"><img src="/images/minwidth.gif" width="8" height="0"></span></a></li>--%>
 </ul>
</div>
<!-- CSS Tabs End -->


<script language="JavaScript" type="text/javascript">
<!--
function startOnload()
{
<c:set var="dataCount" value='${0}'/>
<c:set var="selectedTabId" value=''/>

<c:forEach var="newChemDetailViewBean" items="${newChemDetailViewBeanCollection}" varStatus="status">
 <c:if test="${dataCount == 0}">
  <c:set var="selectedTabId" value="${dataCount}"/>
 </c:if>
 openTab('<c:out value="${dataCount}"/>','','<fmt:message key="label.component"/>','','');
 <c:set var="dataCount" value='${dataCount+1}'/>
</c:forEach>

<c:if test="${!empty selectedTabId}" >
 togglePage("<c:out value="${selectedTabId}"/>");
</c:if>
}
// -->
</script>

<br>
<br class="brNoHeight">
<%--<input type="button" class="smallBtns" onmouseover="this.className='smallBtns smallBtnsOver'" onmouseout="this.className='smallBtns'" name="dup_" value="dup" onclick="mydup()" />--%>
<%-- creating divs for tabs --%>

<!-- Search Option Begins -->
<table id="searchMaskTable" width="100%" border="0" cellpadding="0" cellspacing="0">
<tr><td>
<div class="roundcont filterContainer">
 <div class="roundright">
   <div class="roundtop">
     <div class="roundtopright"> <img src="/images/rndBoxes/borderTL_filter.gif" alt="" width="15" height="10" class="corner_filter" style="display: none" /></div>
   </div>
   <div class="roundContent">
      <div id="tabsdiv">
      <c:set var="partCount" value='0'/>
      <c:forEach var="newChemDetailViewBean" items="${newChemDetailViewBeanCollection}" varStatus="status">
        <div id="newItem<c:out value="${partCount}"/>" <%--style="background-color: #23233;"--%>>
          <table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableSearch">
            <tr>
              <td width="5%" class="optionTitleBoldRight"><fmt:message key="label.numberofcomponents"/></td>
              <td width="1%">&nbsp;</td>
              <td width="5%" class="optionTitleBoldLeft"><fmt:message key="label.size"/></td>
              <td width="5%" class="optionTitleBoldLeft"><fmt:message key="label.unit"/>:</td>
              <td width="10%" class="optionTitleBoldLeft"><fmt:message key="label.pkgstyle"/>:</td>
              <td width="10%" class="optionTitleBoldLeft" id="netWtVolLabel<c:out value="${partCount}"/>" name="netWtVolLabel<c:out value="${partCount}"/>"><fmt:message key="label.netwtvol"/>:</td>
              <td width="10%" class="optionTitleBoldLeft" id="netWtVolUnitLabel<c:out value="${partCount}"/>" name="netWtVolUnitLabel<c:out value="${partCount}"/>"><fmt:message key="label.unit"/>:</td>
              <td width="10%" class="optionTitleBoldLeft" id="dimensionLabel<c:out value="${partCount}"/>" name="dimensionLabel<c:out value="${partCount}"/>"><fmt:message key="label.dim"/>:</td>
            </tr>

            <input name="newItemInputBean[${partCount}].newComponent" id="newComponent<c:out value="${partCount}"/>" type="hidden" value="New">
            <input name="newItemInputBean[<c:out value="${partCount}"/>].netWtRequired" id="netWtRequired<c:out value="${partCount}"/>" type="hidden" value="">

            <tr>
              <td width="5%" class="optionTitleRight">
                <input type="text" class="inputBox" name="newItemInputBean[<c:out value="${partCount}"/>].component" id="component<c:out value="${partCount}"/>" value="<c:out value="${newChemDetailViewBean.componentsPerItem}"/>" ${readonly} size="2" maxLength="50"/>
              </td>
              <td width="1%">x</td>
              <td width="5%" class="optionTitleLeftt">
                <input type="text" class="inputBox" name="newItemInputBean[<c:out value="${partCount}"/>].size" id="size<c:out value="${partCount}"/>" value="<c:out value="${newChemDetailViewBean.partSize}"/>" ${readonly} size="6" maxLength="50"/>
              </td>
              <td width="5%" class="optionTitleLeft">
	              <c:set var="selectedSizeUnit" value='${newChemDetailViewBean.sizeUnit}'/>
                <c:choose>
                <c:when test="${changePackaging != 'YES'}">
                  ${selectedSizeUnit}
                  <input type="hidden" name="newItemInputBean[<c:out value="${partCount}"/>].unit" id="unit<c:out value="${partCount}"/>" value="${selectedSizeUnit}"/>
                </c:when>
                <c:otherwise>
                <select class="selectBox" name="newItemInputBean[<c:out value="${partCount}"/>].unit" id="unit<c:out value="${partCount}"/>" onchange="unitChanged(<c:out value="${partCount}"/>)">
									<c:set var="sizeUnitSizeColl" value='${vvSizeUnitBeanCollection}'/>
									<bean:size id="sizeUnitSize" name="sizeUnitSizeColl"/>
									<c:if test="${sizeUnitSize > 1}">
										<option value="Select One" selected><fmt:message key="label.selectOne"/></option>
									</c:if>
									<c:forEach var="vvSizeUnitBean" items="${vvSizeUnitBeanCollection}" varStatus="status2">
										<c:set var="currentSizeUnit" value='${status2.current.sizeUnit}'/>
										<c:choose>
											<c:when test="${currentSizeUnit == selectedSizeUnit}">
												<option value="<c:out value="${status2.current.sizeUnit}"/>" selected><c:out value="${status2.current.sizeUnit}" escapeXml="false"/></option>
											</c:when>
											<c:otherwise>
												<option value="<c:out value="${status2.current.sizeUnit}"/>"><c:out value="${status2.current.sizeUnit}" escapeXml="false"/></option>
											</c:otherwise>
										</c:choose>
									</c:forEach>
								</select>
              </c:otherwise>
              </c:choose>

              </td>
							<td width="10%" class="optionTitleLeft">
								<c:set var="selectedPkgStyle" value='${newChemDetailViewBean.pkgStyle}'/>
                <c:choose>
                <c:when test="${changePackaging != 'YES'}">
                  ${selectedPkgStyle}
                  <input type="hidden" name="newItemInputBean[<c:out value="${partCount}"/>].pkgStyle" id="pkgStyle<c:out value="${partCount}"/>" value="${selectedPkgStyle}"/>
                </c:when>
                <c:otherwise>
                <select class="selectBox" name="newItemInputBean[<c:out value="${partCount}"/>].pkgStyle" id="pkgStyle<c:out value="${partCount}"/>">
									<c:set var="pkgStyleSizeColl" value='${vvPkgStyleBeanCollection}'/>
									<bean:size id="pkgStyleSize" name="pkgStyleSizeColl"/>
									<c:if test="${pkgStyleSize > 1}">
										<option value="Select One" selected><fmt:message key="label.selectOne"/></option>
									</c:if>
									<c:forEach var="vvPkgStyleBean" items="${vvPkgStyleBeanCollection}" varStatus="status2">
										<c:set var="currentPkgStyle" value='${status2.current.pkgStyle}'/>
										<c:choose>
											<c:when test="${currentPkgStyle == selectedPkgStyle}">
												<option value="<c:out value="${status2.current.pkgStyle}"/>" selected><c:out value="${status2.current.pkgStyle}" escapeXml="false"/></option>
											</c:when>
											<c:otherwise>
												<option value="<c:out value="${status2.current.pkgStyle}"/>"><c:out value="${status2.current.pkgStyle}" escapeXml="false"/></option>
											</c:otherwise>
										</c:choose>
									</c:forEach>
								</select>
                </c:otherwise>
                </c:choose>
              </td>
							<td width="10%" class="optionTitleLeft">
								<input type="text" class="inputBox" name="newItemInputBean[<c:out value="${partCount}"/>].netWtVol" id="netWtVol<c:out value="${partCount}"/>" value="<c:out value="${newChemDetailViewBean.netWt}"/>" ${readonly} size="6" maxLength="50"/>
							</td>
							<td width="10%" class="optionTitleLeft">
								<c:set var="selectedFromUnit" value='${newChemDetailViewBean.netWtUnit}'/>
                <c:choose>
                <c:when test="${changePackaging != 'YES'}">
                  ${selectedFromUnit}
                  <input type="hidden" name="newItemInputBean[<c:out value="${partCount}"/>].netWtVolUnit" id="netWtVolUnit<c:out value="${partCount}"/>" value="${selectedFromUnit}"/>
                </c:when>
                <c:otherwise>
                <select class="selectBox" name="newItemInputBean[<c:out value="${partCount}"/>].netWtVolUnit" id="netWtVolUnit<c:out value="${partCount}"/>">
									<c:set var="sizeUnitConversionBeanColl" value='${sizeUnitConversionBeanCollection}'/>
									<bean:size id="netWtVolUnitSize" name="sizeUnitConversionBeanColl"/>
									<c:if test="${netWtVolUnitSize > 1}">
										<option value="Select One" selected><fmt:message key="label.selectOne"/></option>
									</c:if>
									<c:forEach var="sizeUnitConversionBean" items="${sizeUnitConversionBeanCollection}" varStatus="status2">
										<c:set var="currentFromUnit" value='${status2.current.fromUnit}'/>
										<c:choose>
											<c:when test="${currentFromUnit == selectedFromUnit}">
												<option value="<c:out value="${status2.current.fromUnit}"/>" selected><c:out value="${status2.current.fromUnit}" escapeXml="false"/></option>
											</c:when>
											<c:otherwise>
												<option value="<c:out value="${status2.current.fromUnit}"/>"><c:out value="${status2.current.fromUnit}" escapeXml="false"/></option>
											</c:otherwise>
										</c:choose>
									</c:forEach>
								</select>
                </c:otherwise>
                </c:choose>                   
              </td>
							<td width="10%" class="optionTitleLeft">
								<input type="text" class="inputBox" name="newItemInputBean[<c:out value="${partCount}"/>].dimension" id="dimension<c:out value="${partCount}"/>" value="<c:out value="${newChemDetailViewBean.dimension}"/>" ${readonly} size="6" maxLength="8"/>
							</td>
						</tr>
						<tr>
							<td width="5%" class="optionTitleBoldRight"><fmt:message key="label.materialdesc"/>:</td>
							<td class="optionTitleLeft" colspan="7">
							<input type="text" class="inputBox" name="newItemInputBean[<c:out value="${partCount}"/>].materialDesc" id="materialDesc<c:out value="${partCount}"/>" value="<c:out value="${status.current.materialDesc}"/>" size="60" maxLength="200"/>
						 </td>
            </tr>
            <tr>
							<td width="5%" class="optionTitleBoldRight"><fmt:message key="label.grade"/>:</td>
							<td class="optionTitleLeft" colspan="7">
							<input type="text" class="inputBox" name="newItemInputBean[<c:out value="${partCount}"/>].grade" id="grade<c:out value="${partCount}"/>" value="<c:out value="${status.current.grade}"/>" size="60" maxLength="200"/>
						 </td>
						</tr>
						<tr>
							<td  width="5%" class="optionTitleBoldRight"><fmt:message key="label.manufacturer"/>:</td>
							<td class="optionTitleLeft" colspan="7">
								<input type="text" class="inputBox" name="newItemInputBean[<c:out value="${partCount}"/>].manufacturer" id="manufacturer<c:out value="${partCount}"/>" value="<c:out value="${status.current.manufacturer}"/>" size="60" maxLength="60"/>
								<%--<input type="button" class="lookupBtn" onmouseover="this.className='lookupBtn lookupBtnOver'" onmouseout="this.className='lookupBtn'" name="searchManufacturer" value="..." align="right" onClick="searchManufacture(${partCount})"/>--%>
						 </td>
						</tr>
						<tr>
							<td  width="5%" class="optionTitleBoldRight"><fmt:message key="label.mfg.part.num"/>:</td>
							<td class="optionTitleLeft" colspan="7">
								<input type="text" class="inputBox" name="newItemInputBean[<c:out value="${partCount}"/>].mfgPartNo" id="mfgPartNo<c:out value="${partCount}"/>" value="<c:out value="${status.current.mfgPartNo}"/>" size="60" maxLength="200"/>
						 </td>
						</tr>
						<%--
						<tr>
							<td class="optionTitleBoldRight"><fmt:message key="label.msdsfile"/>:</td>
							<td class="optionTitleLeft" colspan="6">
							<input type="file" name="theFile" id="theFile"/>
						 </td>
						</tr>
						--%>
        </table>
      </div>
      <c:set var="partCount" value='${partCount+1}'/>
    </c:forEach>
    </div>

<%--
    --%>
<%-- buttons --%>
<%--
	 <table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableSearch">
     	<tr>
        <td width="100%" class="optionTitleBoldCenter">
        	<c:set var="submitted" value='${submitted}'/>
         	<c:choose>
          	<c:when test="${submitted == 'Error' || submitted == 'No'}">
           		<input name="submitData" id="submitData" type="submit" class="inputBtns" value="<fmt:message key="button.submit"/>" onmouseover="this.className='inputBtns inputBtnsOver'" onMouseout="this.className='inputBtns'" onclick="return submitFormData();">
            	<input name="Cancel" id="Cancel" type="button" value="<fmt:message key="label.cancel"/>" class="inputBtns" onmouseover="this.className='inputBtns inputBtnsOver'" onmouseout="this.className='inputBtns'" onclick="closeWindow();">
         	</c:when>
          	<c:otherwise>
           		<input name="Close" id="Close" type="button" value="<fmt:message key="label.close"/>" class="inputBtns" onmouseover="this.className='inputBtns inputBtnsOver'" onmouseout="this.className='inputBtns'" onclick="closeWindow();">
          	</c:otherwise>
         	</c:choose>
        </td>
      	</tr>
   	</table>
--%>

    <!-- Search Option Table end -->
   </div>
   <div class="roundbottom">
     <div class="roundbottomright"> <img src="/images/rndBoxes/borderBL.gif" alt="" width="15" height="15" class="corner" style="display: none" /> </div>
   </div>
 </div>
</div>
</td></tr>
</table>

<table id="resultsMaskTable" width="100%" border="0" cellpadding="0" cellspacing="0">
    <table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableResults">
   </table>
</table>

<!-- Hidden element start -->
 <div id="hiddenElements" style="display: none;"></div>
<input type="hidden" name="action" id="action" value=""/>
<input type="hidden" name="totalLines" id="totalLines" value="20" >
<input type="hidden" name="catalogCompanyId" id="catalogCompanyId" value="${catalogCompanyId}"/>
<input type="hidden" name="catalogId" id="catalogId" value="${catalogId}"/>
<input type="hidden" name="catPartNo" id="catPartNo" value="${catPartNo}"/>
<input type="hidden" name="inventoryGroup" id="inventoryGroup" value="${inventoryGroup}"/>
<input type="hidden" name="companyId" id="companyId" value="${newChemHeaderViewBean.companyId}"/>
<input type="hidden" name="facilityId" id="facilityId" value="${newChemHeaderViewBean.facilityId}"/>
<input type="hidden" name="catalogDesc" id="catalogDesc" value="${newChemHeaderViewBean.catalogDesc}"/>
<input type="hidden" name="newChemPackagingChange" id="newChemPackagingChange" value="${newChemHeaderViewBean.newChemPackagingChange}"/>
<input type="hidden" name="receiptId" id="receiptId" value="${receiptId}"/>
<input type="hidden" name="partCount" id="partCount" value='${partCount}'/>
</div> <!-- close of contentArea -->

<!-- Footer message start -->
 <div class="messageBar">&nbsp;</div>
<!-- Footer message end -->

</div> <!-- close of interface -->

</tcmis:form>
</body>
</html:html>