<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>

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
    <tcmis:fontSizeCss/>
    <!-- CSS for YUI -->
    <link rel="stylesheet" type="text/css" href="/yui/build/container/assets/container.css"/>
    <!-- Add any other stylesheets you need for the page here -->

        <%--
        <link rel="stylesheet" type="text/css" href="/css/dhtmlXGridHaas.css"></link>
        --%>

    <script type="text/javascript" src="/js/common/formchek.js"></script>
    <script type="text/javascript" src="/js/common/commonutil.js"></script>
    <script type="text/javascript" src="/js/common/resultiframeresize.js"></script>
    <!-- This handels which key press events are disabeled on this page -->
    <script src="/js/common/disableKeys.js"></script>

    <!-- This handels the menu style and what happens to the right click on the whole page -->
    <script type="text/javascript" src="/js/menu/milonic_src.js"></script>
    <script type="text/javascript" src="/js/menu/mmenudom.js"></script>
    <script type="text/javascript" src="/js/menu/mainmenudata.js"></script>
    <script type="text/javascript" src="/js/menu/contextmenu.js"></script>
    <%@ include file="/common/rightclickmenudata.jsp" %>


    <!-- Add any other javascript you need for the page here -->
    <script type="text/javascript" src="/js/hub/dodaacupdateresults.js"></script>


    <title>
        <fmt:message key="dodaacupdate.title"/>
    </title>

    <script language="JavaScript" type="text/javascript">
        <!--
        //add all the javascript messages here, this for internationalization of client side javascript messages
        var messagesData = new Array();
        messagesData = {alert:"<fmt:message key="label.alert"/>",and:"<fmt:message key="label.and"/>",
            validvalues:"<fmt:message key="label.validvalues"/>",
            submitOnlyOnce:"<fmt:message key="label.submitOnlyOnce"/>",
            recordFound:"<fmt:message key="label.recordFound"/>",
            searchDuration:"<fmt:message key="label.searchDuration"/>",
            minutes:"<fmt:message key="label.minutes"/>",
            seconds:"<fmt:message key="label.seconds"/>",
            itemInteger:"<fmt:message key="error.item.integer"/>",
            pleaseselect:'<fmt:message key="label.pleaseselect"/>'};

        //set this if you want to result table with FIXED width
        //parent.resultWidthResize=false;
        
        var altCountryId = new Array(
<c:forEach var="vvCountryBean" items="${vvCountryBeanCollection}" varStatus="status">
 <c:choose>
   <c:when test="${status.index > 0}">
    ,"<c:out value="${status.current.countryAbbrev}"/>"
   </c:when>
   <c:otherwise>
    "<c:out value="${status.current.countryAbbrev}"/>"
   </c:otherwise>
  </c:choose>
</c:forEach>
);

var altState = new Array();
var altStateName = new Array();
<c:forEach var="vvCountryBean" items="${vvCountryBeanCollection}" varStatus="status">
  <c:set var="currentCountry" value='${status.current.countryAbbrev}'/>
  <c:set var="stateCollection" value='${status.current.stateCollection}'/>

  altState["<c:out value="${currentCountry}"/>"] = new Array(
  <c:forEach var="vvStateBean" items="${stateCollection}" varStatus="status1">
  <c:set var="curState" value="${status1.current.stateAbbrev}"/>
  <c:if test="${curState eq 'None'}">
	<c:set var="curState" value=""/>
  </c:if>
 <c:choose>
   <c:when test="${status1.index > 0}">
        ,"${curState}"
   </c:when>
   <c:otherwise>
        "${curState}"
   </c:otherwise>
  </c:choose>
  </c:forEach>
  );

  altStateName["<c:out value="${currentCountry}"/>"] = new Array(
  <c:forEach var="vvStateBean" items="${stateCollection}" varStatus="status1">
 <c:choose>
   <c:when test="${status1.index > 0}">
        ,"${status1.current.state}"
   </c:when>
   <c:otherwise>
        "${status1.current.state}"
   </c:otherwise>
  </c:choose>
  </c:forEach>
  );
</c:forEach>
        
        
        // -->
    </script>
</head>

<body bgcolor="#ffffff" onload="myResultOnload()">

<tcmis:form action="/dodaacupdateresults.do" onsubmit="return submitFrameOnlyOnce();">

<div id="errorMessagesAreaBody" style="display:none;">
    <html:errors/>
    <logic:present name="errorBean" scope="request">
        <bean:write name="errorBean" property="cause"/>
    </logic:present>
</div>

<script type="text/javascript">

    <c:choose>
    <c:when test="${requestScope['org.apache.struts.action.ERROR'] == null and empty requestScope['errorBean']}">
    showErrorMessage = false;
    </c:when>
    <c:otherwise>
    showErrorMessage = true;
    </c:otherwise>
    </c:choose>
    //-->
</script>

<c:set var= "showOk" value=""/>
<!-- Error Messages Ends -->
<tcmis:facilityPermission indicator="true" userGroupId="DodaacAddressUpdate">
    <c:set var= "showOk" value="Y"/>
    <script type="text/javascript">
        <!--
        showUpdateLinks = true;
        //-->
    </script>
</tcmis:facilityPermission>

<%--TODO
         1. Use a different view to show the results here.
            That way we don't show invalid DODAAC addresses on the addres request processing page.
--%>

<div class="interface" id="resultsPage">
<div class="backGroundContent">
    <c:if test="${locationSearchViewBeanCollection != null}">
        <!-- Search results start -->
        <!-- If you want your results section to span only 50% set this to 50% and your main table will be 100% -->


        <c:if test="${!empty locationSearchViewBeanCollection}">
            <table width="100%" class="tableResults" id="resultsPageTable" border="0" cellpadding="0" cellspacing="0">
                <c:set var="colorClass" value=''/>
                <c:set var="dataCount" value='${0}'/>

                <c:forEach var="locationSearchViewBean" items="${locationSearchViewBeanCollection}"
                           varStatus="viewBean">


                    <c:if test="${viewBean.index % 10 == 0}">
                        <!-- Need to print the header every 10 rows-->
                        <tr>
                             <c:if test="${showOk=='Y'}">
                                <th width="2%"><fmt:message key="label.ok"/></th>
                            </c:if>
                            <th width="5%"><fmt:message key="label.dodaac"/></th>
                            <th width="5%"><fmt:message key="label.type"/></th>
                            <th width="5%"><fmt:message key="label.location"/></th>
                            <th width="5%"><fmt:message key="label.sent"/></th>
                            <th width="5%"><fmt:message key="label.address"/></th>
                            <th width="5%"><fmt:message key="label.city"/></th>
                            <th width="5%"><fmt:message key="label.country"/></th>
                            <th width="5%"><fmt:message key="label.state"/></th>
                        </tr>
                    </c:if>
                    <c:choose>
                        <c:when test="${viewBean.index % 2 == 0}">
                            <c:set var="colorClass" value=''/>
                        </c:when>
                        <c:otherwise>
                            <c:set var="colorClass" value='alt'/>
                        </c:otherwise>
                    </c:choose>
                    <c:if test="${ viewBean.current.sentToAirgas == 'NO' || viewBean.current.dodaacType == 'INVALID' }">
                        <c:set var="colorClass" value='black'/>
                    </c:if>

                    <tr onmouseup="highlightRow('${viewBean.index}')"class="<c:out value="${colorClass}"/>" id="rowId<c:out value="${viewBean.index}"   />">
                       <input name="colorClass<c:out value="${viewBean.index}"/>" id="colorClass<c:out value="${viewBean.index}"/>" value="<c:out value="${colorClass}"/>" type="hidden" >
                        <input name="locationSearchBean[<c:out value="${viewBean.index}"/>].locationId" id="locationId
                            <c:out value="${viewBean.index}"/>" type="hidden"
                                                        value="<c:out value="${viewBean.current.locationId}"/>">

                         <c:if test="${showOk=='Y'}">
                            <td width="2%"><input name="locationSearchBean[<c:out value="${viewBean.index}"/>].ok" id="ok
                                <c:out value="${viewBean.current.ok}"/>" type="checkbox" value="Y">
                            </td>
                         </c:if>

                        <td width="5%"><c:out value="${viewBean.current.dodaac}"/></td>
                        <td>
			  <c:if test="${ !empty showOk }">
                            <select name="locationSearchBean[<c:out value="${viewBean.index}"/>].dodaacType"
                                    class="selectBox"
                                    id="dodaacType<c:out value="${viewBean.index}"/>">
                                <c:forEach var="dodaacTypeBean" items="${vvDodaacTypeCollection}"
                                           varStatus="locationTypes">
                                    <option value="<c:out value="${locationTypes.current.dodaacType}" /> "
                                            <c:if test="${locationTypes.current.dodaacType == viewBean.current.dodaacType}">
                                                selected
                                            </c:if>>
                                        <c:out value="${locationTypes.current.dodaacTypeDesc}"/>

                                    </option>
                                </c:forEach>
                            </select>
			  </c:if>
		  <c:if test="${ empty showOk }">
		  ${viewBean.current.dodaacType}
		  </c:if>
                            </td>
                        <td width="5%"><c:out value="${viewBean.current.locationId}"/></td>
                        <td width="5%"><c:out value="${viewBean.current.sentToAirgas}"/></td>
                        <td width="25%">
                            <c:out value="${viewBean.current.addressLine1Display}"/><br>
                            <c:out value="${viewBean.current.addressLine2Display}"/><br>
                            <c:out value="${viewBean.current.addressLine3Display}"/><br>
                            <c:out value="${viewBean.current.addressLine4Display}"/>
                        </td>
                        <td width="5%"><c:out value="${viewBean.current.city}"/></td>
                        <td>
		  <c:if test="${ !empty showOk }">
          <c:set var="selectedCountry" value='${viewBean.current.countryAbbrev}'/>
          <c:set var="stateCollection" value=''/>

          <select name="locationSearchBean[${viewBean.index}].countryAbbrev" id="countryAbbrev_${viewBean.index}" class="selectBox" onchange="countryChanged(${viewBean.index});">
          <option value=""><fmt:message key="label.pleaseselect"/></option>
          <c:forEach var="vvCountyBean" items="${vvCountryBeanCollection}" varStatus="status">
            <c:set var="currentCountry" value='${status.current.countryAbbrev}'/>
            <c:choose>
              <c:when test="${empty selectedCountry}" >
                <c:set var="selectedCountry" value='${status.current.countryAbbrev}'/>
                <c:set var="stateCollection" value='${status.current.stateCollection}'/>
              </c:when>
              <c:when test="${currentCountry == selectedCountry}" >
                <c:set var="stateCollection" value='${status.current.stateCollection}'/>
              </c:when>
            </c:choose>
            <c:choose>

              <c:when test="${currentCountry == selectedCountry}">
                <option value="${currentCountry}" selected>${status.current.country}</option>
              </c:when>
              <c:otherwise>
                <option value="${currentCountry}">${status.current.country}</option>
              </c:otherwise>
            </c:choose>
          </c:forEach>
          </select>
		  </c:if>
		  <c:if test="${ empty showOk }">
		  ${viewBean.current.countryAbbrev}
		  </c:if>
                        </td>
                        <td>
		  <c:if test="${ !empty showOk }">
          <c:set var="selectedState" value='${viewBean.current.stateAbbrev}'/>
          <select name="locationSearchBean[${viewBean.index}].stateAbbrev" id="stateAbbrev_${viewBean.index}" class="selectBox">
          <option value=""><fmt:message key="label.pleaseselect"/></option>
			<c:if test="${!empty stateCollection}">
            <c:forEach var="vvStateBean" items="${stateCollection}" varStatus="status">
                  <c:set var="currentState" value='${status.current.stateAbbrev}'/>
                  <option value="${currentState}"<c:if test="${currentState == selectedState}"> selected</c:if>>${status.current.state}</option>
            </c:forEach>
            </c:if>
          </select>
		  </c:if>
		  <c:if test="${ empty showOk }">
		  ${viewBean.current.stateAbbrev}
		  </c:if>
                        </td>
                    </tr>
                    <c:set var="dataCount" value='${dataCount+1}'/>
                </c:forEach>
            </table>
        </c:if>
        <!-- If the collection is empty say no data found -->
        <c:if test="${empty locationSearchViewBeanCollection}">

            <table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableNoData" id="resultsPageTable">
                <tr>
                    <td width="100%">
                        <fmt:message key="main.nodatafound"/>
                    </td>
                </tr>
            </table>
        </c:if>


        <!-- Search results end -->
    </c:if>

    <!-- Hidden element start -->
    <div id="hiddenElements" style="display: none;">
        <input name="totalLines" id="totalLines" value="<c:out value="${dataCount}"/>" type="hidden">
        <input name="action" id="action" value="" type="hidden">
        
        <input name="searchText" id="searchText" type="hidden" value="${param.searchText}">
        <input name="dodaacType" id="dodaacType" type="hidden" value="${param.dodaacType} ">
        <input name="dodaac" id="dodaac" type="hidden" value="${param.dodaac}">        
        <!-- Store all search criteria in hidden elements, need this to requery the database after updates -->
    </div>
    <!-- Hidden elements end -->

</div>
<!-- close of backGroundContent -->
</div>
<!-- close of interface -->

</tcmis:form>
</body>
</html:html>
