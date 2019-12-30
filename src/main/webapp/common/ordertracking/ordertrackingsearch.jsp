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

<!--Use this tag to get the correct CSS class.
This looks at what the user's preffered font size and which application he is viewing to set the correct CSS. -->
<tcmis:fontSizeCss />
<!-- CSS for YUI -->

<!-- Add any other stylesheets you need for the page here -->

<script src="/js/common/formchek.js" language="JavaScript"></script>
<script src="/js/common/commonutil.js" language="JavaScript"></script>
<script src="/js/common/searchiframeresize.js" language="JavaScript"></script>
<!-- This handels which key press events are disabeled on this page -->
<script src="/js/common/disableKeys.js" language="JavaScript"></script>
<!-- This handels the menu style and what happens to the right click on the whole page -->
<script type="text/javascript" src="/js/menu/milonic_src.js"></script>
<script type="text/javascript" src="/js/menu/mmenudom.js"></script>
<script type="text/javascript" src="/js/menu/mainmenudata.js"></script>
<script type="text/javascript" src="/js/menu/contextmenu.js"></script>
<%@ include file="/common/rightclickmenudata.jsp" %>

<script src="/js/common/ordertracking/ordertracking.js" language="JavaScript"></script>

<title>
<fmt:message key="ordertracking.label.title"/>
</title>

<script language="JavaScript" type="text/javascript">
<!--

<c:set var="workareaFacilityColl" value='${personnelBean.myWorkareaFacilityOvBeanCollection}'/>
<bean:size id="companySize" name="workareaFacilityColl"/>
var altCompanyId = new Array(
<c:forEach var="myWorkareaFacilityViewBean" items="${personnelBean.myWorkareaFacilityOvBeanCollection}" varStatus="status">
 <c:choose>
   <c:when test="${status.index == 0 && companySize > 1}">
     "My Companies","<c:out value="${status.current.companyId}" escapeXml="false"/>"
   </c:when>
   <c:otherwise>
     <c:choose>
       <c:when test="${status.index == 0}">
         "<c:out value="${status.current.companyId}" escapeXml="false"/>"
       </c:when>
       <c:otherwise>
         ,"<c:out value="${status.current.companyId}" escapeXml="false"/>"
       </c:otherwise>
     </c:choose>
   </c:otherwise>
  </c:choose>
</c:forEach>
);

var altCompanyName = new Array(
<c:forEach var="myWorkareaFacilityViewBean" items="${personnelBean.myWorkareaFacilityOvBeanCollection}" varStatus="status">
 <c:choose>
   <c:when test="${status.index == 0 && companySize > 1}">
     "<fmt:message key="label.mycompanies"/>","<c:out value="${status.current.companyName}" escapeXml="false"/>"
   </c:when>
   <c:otherwise>
     <c:choose>
       <c:when test="${status.index == 0}">
         "<c:out value="${status.current.companyName}" escapeXml="false"/>"
       </c:when>
       <c:otherwise>
         ,"<c:out value="${status.current.companyName}" escapeXml="false"/>"
       </c:otherwise>
     </c:choose>
   </c:otherwise>
  </c:choose>
</c:forEach>
);

var altFacilityId = new Array();
var altFacilityName = new Array();
  <c:if test="${companySize > 1}">
    altFacilityId["My Companies"] = new Array("My Facilities");
    altFacilityName["My Companies"] = new Array("My Facilities");
  </c:if>

  <c:forEach var="myWorkareaFacilityViewBean" items="${personnelBean.myWorkareaFacilityOvBeanCollection}" varStatus="status">
    <c:set var="currentCompanyId" value='${status.current.companyId}'/>
    <c:set var="facilityListCollection" value='${status.current.facilityList}'/>

    <bean:size id="facilitySize" name="facilityListCollection"/>

    altFacilityId["<c:out value="${currentCompanyId}" escapeXml="false"/>"] = new Array(
    <c:forEach var="facilityObjBean" items="${facilityListCollection}" varStatus="status1">
        <c:choose>
          <c:when test="${status1.index == 0 && facilitySize > 1}">
            "My Facilities","<c:out value="${status1.current.refColumn}" escapeXml="false"/>"
          </c:when>
          <c:otherwise>
            <c:choose>
              <c:when test="${status1.index == 0}">
                "<c:out value="${status1.current.refColumn}" escapeXml="false"/>"
              </c:when>
              <c:otherwise>
                ,"<c:out value="${status1.current.refColumn}" escapeXml="false"/>"
              </c:otherwise>
            </c:choose>
          </c:otherwise>
        </c:choose>
    </c:forEach>
    );

    altFacilityName["<c:out value="${currentCompanyId}" escapeXml="false"/>"] = new Array(
    <c:forEach var="facilityObjBean" items="${facilityListCollection}" varStatus="status1">
        <c:choose>
          <c:when test="${status1.index == 0 && facilitySize > 1}">
            "<fmt:message key="label.myfacilities"/>","<c:out value="${status1.current.facilityGroupWithFacility}" escapeXml="false"/>"
          </c:when>
          <c:otherwise>
            <c:choose>
              <c:when test="${status1.index == 0}">
                "<c:out value="${status1.current.facilityGroupWithFacility}" escapeXml="false"/>"
              </c:when>
              <c:otherwise>
                ,"<c:out value="${status1.current.facilityGroupWithFacility}" escapeXml="false"/>"
              </c:otherwise>
            </c:choose>
          </c:otherwise>
        </c:choose>
    </c:forEach>
    );
  </c:forEach>

var altApplication = new Array();
var altApplicationDesc = new Array();
  <c:forEach var="myWorkareaFacilityViewBean" items="${personnelBean.myWorkareaFacilityOvBeanCollection}" varStatus="status">
    <c:set var="currentCompanyId" value='${status.current.companyId}'/>
    <c:set var="facilityListCollection" value='${status.current.facilityList}'/>

    <bean:size id="facilitySize" name="facilityListCollection"/>
    <c:if test="${facilitySize > 1}">
      altApplication["My Facilities"] = new Array("My Work Areas");
      altApplicationDesc["My Facilities"] = new Array("My Work Areas");
    </c:if>

    <c:forEach var="facilityObjBean" items="${facilityListCollection}" varStatus="status1">
      <c:set var="currentFacilityId" value='${status1.current.refColumn}'/>
      <c:set var="applicationListCollection" value='${status1.current.applicationList}'/>

      <bean:size id="applicationSize" name="applicationListCollection"/>

      altApplication["<c:out value="${currentFacilityId}" escapeXml="false"/>"] = new Array(
      <c:forEach var="applicationObjBean" items="${applicationListCollection}" varStatus="status2">
        <c:choose>
          <c:when test="${status2.index == 0 && applicationSize > 1}">
            "My Work Areas","<c:out value="${status2.current.application}" escapeXml="false"/>"
          </c:when>
          <c:otherwise>
            <c:choose>
              <c:when test="${status2.index == 0}">
                "<c:out value="${status2.current.application}" escapeXml="false"/>"
              </c:when>
              <c:otherwise>
                ,"<c:out value="${status2.current.application}" escapeXml="false"/>"
              </c:otherwise>
            </c:choose>
          </c:otherwise>
        </c:choose>
      </c:forEach>
      );

      altApplicationDesc["<c:out value="${currentFacilityId}" escapeXml="false"/>"] = new Array(
      <c:forEach var="applicationObjBean" items="${applicationListCollection}" varStatus="status2">
        <c:choose>
          <c:when test="${status2.index == 0 && applicationSize > 1}">
            "<fmt:message key="label.myworkareas"/>","<c:out value="${status2.current.applicationDesc}" escapeXml="true"/>"
          </c:when>
          <c:otherwise>
            <c:choose>
              <c:when test="${status2.index == 0}">
                "<c:out value="${status2.current.applicationDesc}" escapeXml="true"/>"
              </c:when>
              <c:otherwise>
                ,"<c:out value="${status2.current.applicationDesc}" escapeXml="true"/>"
              </c:otherwise>
            </c:choose>
          </c:otherwise>
        </c:choose>
      </c:forEach>
      );
    </c:forEach>
  </c:forEach>

var menuskin = "skin1"; // skin0, or skin1
var display_url = 0; // Show URLs in status bar?
// -->
</script>

<script language="JavaScript" type="text/javascript">
<!--
//add all the javascript messages here, this for internationalization of client side javascript messages
var messagesData = new Array();
messagesData={alert:"<fmt:message key="label.alert"/>",and:"<fmt:message key="label.and"/>",
anySearchError:"<fmt:message key="label.anySearchError"/>",
withinDayError:"<fmt:message key="label.withinDayError"/>",
recordFound:"<fmt:message key="label.recordFound"/>",
searchDuration:"<fmt:message key="label.searchDuration"/>",
minutes:"<fmt:message key="label.minutes"/>",
seconds:"<fmt:message key="label.seconds"/>",
submitOnlyOnce:"<fmt:message key="label.submitOnlyOnce"/>"};
// -->
</script>
</head>

<body bgcolor="#ffffff" onload="setSearchFrameSize();">

<tcmis:form action="/ordertrackingresults.do" onsubmit="return submitFrameOnlyOnce();" target="resultFrame">

<div class="interface" id="searchMainPage">

<div class="contentArea">

<!-- Search Option Begins -->
<table id="searchMaskTable" width="1024" border="0" cellpadding="0" cellspacing="0">
<tr><td>
<div class="roundcont filterContainer">
 <div class="roundright">
   <div class="roundtop">
     <div class="roundtopright"> <img src="/images/rndBoxes/borderTL_filter.gif" alt="" width="15" height="10" class="corner_filter" style="display: none" /></div>
   </div>
   <div class="roundContent">
    <%-- Insert all the search option within this div --%>
    <table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableSearch">

    <%-- row 1 --%>
    <tr>
      <td width="10%" class="optionTitleBoldRight"><fmt:message key="label.requestor"/>:</td>
      <td width="30%">
         <input class="inputBox" type="text" name="requestorName" id="requestorName" value="<c:out value='${requestorName}'/>" onChange="invalidateRequestor()" size="20">
         <input type="button" class="lookupBtn" onmouseover="this.className='lookupBtn lookupBtnOver'" onmouseout="this.className='lookupBtn'" name="lookupButton" id="lookupButton" value="" onclick="getPersonnel()">
      </td>

      <td width="5%" class="optionTitleBoldRight"><fmt:message key="label.company"/>:</td>
      <td width="30%">
				 <c:set var="workareaFacilityColl" value='${personnelBean.myWorkareaFacilityOvBeanCollection}'/>
				 <bean:size id="companySize" name="workareaFacilityColl"/>
				 <c:set var="selectedCompanyId" value='${param.companyId}'/>
         <select name="companyId" id="companyId" onchange="companyChanged()" class="selectBox">
         <c:choose>
           <c:when test="${companySize == 0}">
             <option value="My Companies"><fmt:message key="label.mycompanies"/></option>
           </c:when>
           <c:otherwise>
             <c:if test="${companySize > 1}">
               <option value="My Companies" selected><fmt:message key="label.mycompanies"/></option>
               <c:set var="selectedCompanyId" value="mycompanies"/>  <%-- set it to something so I can test it selectedCompanyId is empty below --%>
             </c:if>

             <c:forEach var="myWorkareaFacilityViewBean" items="${personnelBean.myWorkareaFacilityOvBeanCollection}" varStatus="status">
               <c:set var="currentCompanyId" value='${status.current.companyId}'/>
                 <c:if test="${empty selectedCompanyId}" >
                   <c:set var="selectedCompanyId" value='${status.current.companyId}'/>
                   <c:set var="facilityCollection" value='${status.current.facilityList}'/>
                 </c:if>
                 <option value="<c:out value="${currentCompanyId}"/>"><c:out value="${status.current.companyName}" escapeXml="false"/></option>
             </c:forEach>
           </c:otherwise>
         </c:choose>
         </select>
      </td>

      <td width="25%" class="optionTitleBoldLeft">
        <input name="status" id="statusAny" type="radio" class="radioBtns" value="ANY">
        <fmt:message key="label.any"/>
        <input name="status" id="statusDraft" type="radio" class="radioBtns" value="DRAFT">
        <fmt:message key="label.draft"/>
        <input name="status" id="statusPending" type="radio" class="radioBtns" value="PENDING">
        <fmt:message key="label.pending"/>
        <input name="status" id="statusOpen" type="radio" class="radioBtns" value="OPEN" checked>
        <fmt:message key="label.open"/>
      </td>
    </tr>

    <%-- row 2 --%>
    <tr>
       <td width="10%" class="optionTitleBoldRight">
         <input name="needMyApproval" id="needMyApproval" onclick="checkDisabled()" type="checkbox" class="radioBtns" value="Y">
       </td>
       <td width="25%" class="optionTitleBoldLeft">
          <fmt:message key="label.needmyapproval"/>
       </td>

      <td width="5%" class="optionTitleBoldRight"><fmt:message key="label.facility"/>:</td>
      <td width="30%">
         <c:choose>
           <c:when test="${facilityCollection == null}">
             <c:set var="facilitySize" value="0"/>
           </c:when>
           <c:otherwise>
             <bean:size id="facilitySize" name="facilityCollection"/>
           </c:otherwise>
         </c:choose>
         <c:set var="selectedFacilityId" value='${param.facilityId}'/>
         <select name="facilityId" id="facilityId" onchange="facilityChanged()" class="selectBox">
         <c:choose>
           <c:when test="${facilitySize == 0}">
             <option value="My Facilities"><fmt:message key="label.myfacilities"/></option>
           </c:when>
           <c:otherwise>
             <c:if test="${facilitySize > 1}">
               <option value="My Facilities" selected><fmt:message key="label.myfacilities"/></option>
               <c:set var="selectedFacilityId" value="myfacilities"/>  <%-- set it to something so I can test it selectedFacilityId is empty below --%>
             </c:if>
             <c:forEach var="facilityOvBean" items="${facilityCollection}" varStatus="status">
               <c:set var="currentFacilityId" value='${status.current.refColumn}'/>
                 <c:if test="${empty selectedFacilityId}" >
                  <c:set var="selectedFacilityId" value='${status.current.facilityId}'/>
                  <c:set var="applicationSelectBeanCollection" value='${status.current.applicationList}'/>
                 </c:if>
                 <option value="<c:out value="${currentFacilityId}"/>"><c:out value="${status.current.facilityGroupWithFacility}" escapeXml="false"/></option>
             </c:forEach>
           </c:otherwise>
         </c:choose>
         </select>
      </td>


       <td width="25%" class="optionTitleBoldLeft">
         <input name="status" id="statusReleased" type="radio" class="radioBtns" value="RELEASED">
         <fmt:message key="label.released"/> <fmt:message key="label.within"/>
         <input class="inputBox" type="text" name="releasedSinceDays" id="releasedSinceDays" value="<c:out value='${releasedSinceDays}'/>" size="5">
         <fmt:message key="label.days"/>
       </td>
    </tr>

    <%-- row 3 --%>
    <tr>
       <td width="10%" class="optionTitleBoldRight">
         <fmt:message key="label.searchby"/>:&nbsp;
       </td>
       <td width="30%" class="optionTitleBoldLeft">
 			 <html:select property="searchWhat" styleId="searchWhat" styleClass="selectBox">
   				<html:option value="PO_NUMBER" key="label.userpo"/>
   				<html:option value="PO_NUMBER_LINE" key="label.podaskline"/>
   				<html:option value="CAT_PART_NO" key="label.partNno"/>
				  <html:option value="PART_DESC" key="label.partdesc"/>
				  <html:option value="PR_NUMBER" key="label.requestid"/>
		   </html:select>

 			 <html:select property="searchType" styleId="searchType" styleClass="selectBox">
   				<html:option value="LIKE" key="label.contains"/>
   				<html:option value="IS" key="label.is"/>
  		 </html:select>

        <input class="inputBox" type="text" name="searchText" id="searchText" value="<c:out value='${param.searchText}'/>" size="10">
       </td>

       <td width="5%" class="optionTitleBoldRight"><fmt:message key="label.workarea"/>:</td>
       <td width="30%">
          <c:choose>
           <c:when test="${applicationSelectBeanCollection == null}">
             <c:set var="applicationSize" value="0"/>
           </c:when>
           <c:otherwise>
             <bean:size id="applicationSize" name="applicationSelectBeanCollection"/>
           </c:otherwise>
          </c:choose>
          <c:set var="selectedApplicationId" value='${param.applicationId}'/>
          <select name="applicationId" id="applicationId" class="selectBox">
            <c:choose>
              <c:when test="${applicationSize == 0}">
                <option value="My Work Areas"><fmt:message key="label.myworkareas"/></option>
              </c:when>
              <c:otherwise>
                <c:if test="${applicationSize > 1}">
                  <option value="My Work Areas" selected><fmt:message key="label.myworkareas"/></option>
                </c:if>
                <c:forEach var="facLocAppBean" items="${applicationSelectBeanCollection}" varStatus="status">
                  <c:set var="currentApplicationId" value='${status.current.application}'/>
                  <option value="<c:out value="${currentApplicationId}"/>"><c:out value="${status.current.applicationDesc}" escapeXml="false"/></option>
                </c:forEach>
              </c:otherwise>
            </c:choose>
          </select>
       </td>

       <td width="25%" class="optionTitleBoldLeft">
         <input name="status" id="statusDelivered" type="radio" class="radioBtns" value="DELIVERED">
         <fmt:message key="label.fullydelivered"/> <fmt:message key="label.within"/>
         <input class="inputBox" type="text" name="deliveredSinceDays" id="deliveredSinceDays" value="<c:out value='${deliveredSinceDays}'/>" size="5">
         <fmt:message key="label.days"/>
       </td>
    </tr>

    <%-- row 4 --%>
    <tr>
      <td width="5%" colspan="2" class="optionTitleBoldLeft">
        <input name="submitSearch" id="submitSearch" type="submit" class="inputBtns" value="<fmt:message key="button.search"/>" onmouseover="this.className='inputBtns inputBtnsOver'" onMouseout="this.className='inputBtns'" onclick="return submitSearchForm();">
        <input name="buttonCreateExcel" id="buttonCreateExcel" type="submit" class="inputBtns" value="<fmt:message key="label.createexcelfile"/>" onmouseover="this.className='inputBtns inputBtnsOver'" onmouseout="this.className='inputBtns'" onclick="generateExcel(); return false;">
      </td>
      <td width="5%">&nbsp;</td>
      <td width="30%" class="optionTitleBoldLeft">
         <input name="critical" id="critical" type="checkbox" class="radioBtns" value="Y">
         <fmt:message key="label.criticalonly"/>
      </td>
       <td width="25%" class="optionTitleBoldLeft">
         <input name="status" id="statusCanceled" type="radio" class="radioBtns" value="CANCELED">
         <fmt:message key="label.cancelled"/> <fmt:message key="label.within"/>
         <input class="inputBox" type="text" name="cancelledSinceDays" id="cancelledSinceDays" value="<c:out value='${cancelledSinceDays}'/>" size="5">
         <fmt:message key="label.days"/>
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

<!-- Error Messages Begins -->
<!-- Build this section only if there is an error message to display -->
<c:if test="${requestScope['org.apache.struts.action.ERROR'] != null}">
<div class="spacerY">&nbsp;
<div id="errorMessagesArea" class="errorMessages">
<html:errors/>
</div>
</div>
</c:if>
<!-- Error Messages Ends -->

</div> <!-- close of contentArea -->

<!-- Hidden element start -->
<div id="hiddenElements" style="display: none;">
<input name="action" id="action" type="hidden" value="">
<input name="requestorId" id="requestorId" type="hidden" value="<c:out value="${requestorId}"/>">
<input name="companyName" id="companyName" type="hidden" value="">
<input name="facilityName" id="facilityName" type="hidden" value="">
<input name="applicationDesc" id="applicationDesc" type="hidden" value="">
<input name="searchTypeDesc" id="searchTypeDesc" type="hidden" value="">
<input name="searchWhatDesc" id="searchWhatDesc" type="hidden" value="">
<input name="startSearchTime" id="startSearchTime" type="hidden"value="">
</div>
<!-- Hidden elements end -->

</div> <!-- close of interface -->

</tcmis:form>
</body>
</html:html>