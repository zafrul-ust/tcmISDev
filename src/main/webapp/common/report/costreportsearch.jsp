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
<tcmis:fontSizeCss />
<!-- CSS for YUI -->
<%--
<link rel="stylesheet" type="text/css" href="/yui/build/container/assets/container.css" />
--%>
<!-- Add any other stylesheets you need for the page here -->

<%--
<link rel="stylesheet" type="text/css" href="/css/dhtmlXGridHaas.css"></link>
--%>

<script type="text/javascript" src="/js/common/formchek.js"></script>
<script type="text/javascript" src="/js/common/commonutil.js"></script>
<script type="text/javascript" src="/js/common/searchiframeresize.js"></script>
<!-- This handles which key press events are disabeled on this page -->
<script type="text/javascript" src="/js/common/disableKeys.js"></script>

<!-- This handels the menu style and what happens to the right click on the whole page -->
<script type="text/javascript" src="/js/menu/milonic_src.js"></script>
<script type="text/javascript" src="/js/menu/mmenudom.js"></script>
<script type="text/javascript" src="/js/menu/mainmenudata.js"></script>
<script type="text/javascript" src="/js/menu/contextmenu.js"></script>
<%@ include file="/common/rightclickmenudata.jsp" %>

<!-- For Calendar support -->
<script type="text/javascript" src="/js/calendar/newcalendar.js"></script>
<script type="text/javascript" src="/js/calendar/AnchorPosition.js"></script>
<script type="text/javascript" src="/js/calendar/PopupWindow.js"></script>


<!-- Add any other javascript you need for the page here -->
<script type="text/javascript" src="/js/common/report/costreportsearch.js"></script>

<!-- These are for the Grid, uncomment if you are going to use the grid -->
<%--<script src="/js/dhtmlxGrid/dhtmlXCommon.js"></script>
<script type="text/javascript" src="/js/dhtmlxGrid/dhtmlXGridHaas.js"></script>
<script type="text/javascript" src="/js/dhtmlxGrid/dhtmlXGridCell.js"></script>
<script type="text/javascript" src="/js/dhtmlxGrid/dhtmlXGrid_excell_mro.js"></script>
--%>

<!-- This is for the YUI, uncomment if you will use this -->
<%--<script type="text/javascript" src="/yui/build/yahoo/yahoo.js" ></script>
<script type="text/javascript" src="/yui/build/event/event.js" ></script>
<script type="text/javascript" src="/yui/build/dom/dom.js" ></script>
<script type="text/javascript" src="/yui/build/animation/animation.js" ></script>
<script type="text/javascript" src="/yui/build/container/container.js"></script>
<script type="text/javascript" src="/js/common/waitDialog.js"></script>
<script type="text/javascript" src="/yui/build/dragdrop/dragdrop.js" ></script>--%>

<title>
<fmt:message key="label.costreport"/>
</title>

<script language="JavaScript" type="text/javascript">
<!--

<c:set var="companyGroupColl" value='${companiesFacilitiesDropdown}'/>
<bean:size id="companySize" name="companyGroupColl"/>
var altCompanyId = new Array(
<c:forEach var="myWorkareaFacilityViewBean" items="${companiesFacilitiesDropdown}" varStatus="status">
     <c:choose>
       <c:when test="${status.index == 0}">
         "<c:out value="${status.current.companyId}" escapeXml="false"/>"
       </c:when>
       <c:otherwise>
         ,"<c:out value="${status.current.companyId}" escapeXml="false"/>"
       </c:otherwise>
     </c:choose>
</c:forEach>
);

var altCompanyName = new Array(
<c:forEach var="myWorkareaFacilityViewBean" items="${companiesFacilitiesDropdown}" varStatus="status">
     <c:choose>
       <c:when test="${status.index == 0}">
         "<c:out value="${status.current.companyName}" escapeXml="false"/>"
       </c:when>
       <c:otherwise>
         ,"<c:out value="${status.current.companyName}" escapeXml="false"/>"
       </c:otherwise>
     </c:choose>
</c:forEach>
);

var altReportGroup = new Array();
  <c:if test="${companySize > 1}">
    altReportGroup["My Companies"] = new Array("My Reporting Groups");
  </c:if>

  <c:forEach var="myWorkareaFacilityViewBean" items="${companiesFacilitiesDropdown}" varStatus="status">
    <c:set var="currentCompanyId" value='${status.current.companyId}'/>
    <c:set var="reportGroupListCollection" value='${status.current.costReportGroupList}'/>

    <bean:size id="reportGroupSize" name="reportGroupListCollection"/>

    altReportGroup["<c:out value="${currentCompanyId}" escapeXml="false"/>"] = new Array(
    <c:forEach var="costReportGroupBean" items="${reportGroupListCollection}" varStatus="status1">
        <c:choose>
          <c:when test="${status1.index == 0 && reportGroupSize > 1}">
            "My Reporting Groups"
		        ,"<c:out value="${status1.current.costReportGroup}" escapeXml="false"/>"
          </c:when>
          <c:otherwise>
            <c:choose>
              <c:when test="${status1.index == 0}">
                "<c:out value="${status1.current.costReportGroup}" escapeXml="false"/>"
              </c:when>
              <c:otherwise>
                ,"<c:out value="${status1.current.costReportGroup}" escapeXml="false"/>"
              </c:otherwise>
            </c:choose>
          </c:otherwise>
        </c:choose>
    </c:forEach>
    );
  </c:forEach>

var altFacilityId = new Array();
var altFacilityName = new Array();
altFacilityId["My Reporting Groups"] = new Array("My Facilities");
altFacilityName["My Reporting Groups"] = new Array("My Facilities");
  <c:forEach var="myWorkareaFacilityViewBean" items="${companiesFacilitiesDropdown}" varStatus="status">
    <c:set var="currentCompanyId" value='${status.current.companyId}'/>
		<c:set var="reportGroupCollection" value='${status.current.costReportGroupList}'/>

		<c:forEach var="costReportGroupBean" items="${reportGroupCollection}" varStatus="status1">
			<c:set var="currentReportGroup" value='${status1.current.costReportGroup}'/>
			<c:set var="facilityListCollection" value='${status1.current.facilityList}'/>

      <bean:size id="facilitySize" name="facilityListCollection"/>
      altFacilityId["<c:out value="${currentCompanyId}${currentReportGroup}" escapeXml="false"/>"] = new Array(
      <c:forEach var="facilityObjBean" items="${facilityListCollection}" varStatus="status2">
        <c:choose>
          <c:when test="${status2.index == 0 && facilitySize > 1}">
            "<c:out value="My Facilities" escapeXml="false"/>"
		        ,"<c:out value="${status2.current.facilityId}" escapeXml="false"/>"
          </c:when>
          <c:otherwise>
            <c:choose>
              <c:when test="${status2.index == 0}">
                "<c:out value="${status2.current.facilityId}" escapeXml="false"/>"
              </c:when>
              <c:otherwise>
                ,"<c:out value="${status2.current.facilityId}" escapeXml="false"/>"
              </c:otherwise>
            </c:choose>
          </c:otherwise>
        </c:choose>
      </c:forEach>
      );

      altFacilityName["<c:out value="${currentCompanyId}${currentReportGroup}" escapeXml="false"/>"] = new Array(
      <c:forEach var="facilityObjBean" items="${facilityListCollection}" varStatus="status2">
        <c:choose>
          <c:when test="${status2.index == 0 && facilitySize > 1}">
            "<fmt:message key="label.myfacilities"/>"
		        ,"<c:out value="${status2.current.facilityName}" escapeXml="false"/>"
          </c:when>
          <c:otherwise>
            <c:choose>
              <c:when test="${status2.index == 0}">
                "<c:out value="${status2.current.facilityName}" escapeXml="false"/>"
              </c:when>
              <c:otherwise>
                ,"<c:out value="${status2.current.facilityName}" escapeXml="false"/>"
              </c:otherwise>
            </c:choose>
          </c:otherwise>
        </c:choose>
      </c:forEach>
      );

		</c:forEach>
  </c:forEach>

var altApplication = new Array();
var altApplicationDesc = new Array();
altApplication["My Facilities"] = new Array("My Work Areas");
altApplicationDesc["My Facilities"] = new Array("My Work Areas");
  <c:forEach var="myWorkareaFacilityViewBean" items="${companiesFacilitiesDropdown}" varStatus="status">
    <c:set var="currentCompanyId" value='${status.current.companyId}'/>
		<c:set var="reportGroupCollection" value='${status.current.costReportGroupList}'/>

		<c:forEach var="costReportGroupBean" items="${reportGroupCollection}" varStatus="status1">
			<c:set var="currentReportGroup" value='${status1.current.costReportGroup}'/>
			<c:set var="facilityListCollection" value='${status1.current.facilityList}'/>

      <c:forEach var="facilityObjBean" items="${facilityListCollection}" varStatus="status2">
        <c:set var="currentFacilityId" value='${status2.current.facilityId}'/>
        <c:set var="applicationListCollection" value='${status2.current.applicationList}'/>

        <bean:size id="applicationSize" name="applicationListCollection"/>

        altApplication["<c:out value="${currentCompanyId}${currentReportGroup}${currentFacilityId}" escapeXml="false"/>"] = new Array(
        <c:forEach var="applicationObjBean" items="${applicationListCollection}" varStatus="status3">
          <c:choose>
            <c:when test="${status3.index == 0 && applicationSize > 1}">
              "My Work Areas"
		          ,"<c:out value="${status3.current.application}" escapeXml="true"/>"
            </c:when>
            <c:otherwise>
              <c:choose>
                <c:when test="${status3.index == 0}">
                  "<c:out value="${status3.current.application}" escapeXml="true"/>"
                </c:when>
                <c:otherwise>
                  ,"<c:out value="${status3.current.application}" escapeXml="true"/>"
                </c:otherwise>
              </c:choose>
            </c:otherwise>
          </c:choose>
        </c:forEach>
        );

        altApplicationDesc["<c:out value="${currentCompanyId}${currentReportGroup}${currentFacilityId}" escapeXml="false"/>"] = new Array(
        <c:forEach var="applicationObjBean" items="${applicationListCollection}" varStatus="status3">
          <c:choose>
            <c:when test="${status3.index == 0 && applicationSize > 1}">
              "<fmt:message key="label.myworkareas"/>"
		          ,"<c:out value="${status3.current.applicationDesc}" escapeXml="true"/>"
            </c:when>
            <c:otherwise>
              <c:choose>
                <c:when test="${status3.index == 0}">
                  "<c:out value="${status3.current.applicationDesc}" escapeXml="true"/>"
                </c:when>
                <c:otherwise>
                  ,"<c:out value="${status3.current.applicationDesc}" escapeXml="true"/>"
                </c:otherwise>
              </c:choose>
            </c:otherwise>
          </c:choose>
        </c:forEach>
        );

			</c:forEach>
    </c:forEach>
  </c:forEach>

var altAccountSysId = new Array();
var altAccountSysDesc = new Array();
altAccountSysId["My Facilities"] = new Array("My Accounting Systems");
altAccountSysDesc["My Facilities"] = new Array("My Accounting Systems");
  <c:forEach var="myWorkareaFacilityViewBean" items="${companiesFacilitiesDropdown}" varStatus="status">
    <c:set var="currentCompanyId" value='${status.current.companyId}'/>
		<c:set var="reportGroupCollection" value='${status.current.costReportGroupList}'/>

		<c:forEach var="costReportGroupBean" items="${reportGroupCollection}" varStatus="status1">
			<c:set var="currentReportGroup" value='${status1.current.costReportGroup}'/>
			<c:set var="facilityListCollection" value='${status1.current.facilityList}'/>



      <c:forEach var="facilityObjBean" items="${facilityListCollection}" varStatus="status2">
        <c:set var="currentFacilityId" value='${status2.current.facilityId}'/>
        <c:set var="accountSysListCollection" value='${status2.current.accountSysList}'/>

        <bean:size id="accountSysSize" name="accountSysListCollection"/>

        altAccountSysId["<c:out value="${currentCompanyId}${currentReportGroup}${currentFacilityId}" escapeXml="false"/>"] = new Array(
        <c:forEach var="accountSysObjBean" items="${accountSysListCollection}" varStatus="status3">
          <c:choose>
            <c:when test="${status3.index == 0 && accountSysSize > 1}">
              "My Accounting Systems"
		          ,"<c:out value="${status3.current.accountSysId}" escapeXml="false"/>"
            </c:when>
            <c:otherwise>
              <c:choose>
                <c:when test="${status3.index == 0}">
                  "<c:out value="${status3.current.accountSysId}" escapeXml="false"/>"
                </c:when>
                <c:otherwise>
                  ,"<c:out value="${status3.current.accountSysId}" escapeXml="false"/>"
                </c:otherwise>
              </c:choose>
            </c:otherwise>
          </c:choose>
        </c:forEach>
        );

        altAccountSysDesc["<c:out value="${currentCompanyId}${currentReportGroup}${currentFacilityId}" escapeXml="false"/>"] = new Array(
        <c:forEach var="accountSysObjBean" items="${accountSysListCollection}" varStatus="status3">
          <c:choose>
            <c:when test="${status3.index == 0 && accountSysSize > 1}">
              "<fmt:message key="label.myaccountsytems"/>"
		          ,"<c:out value="${status3.current.accountSysId}" escapeXml="false"/>"
            </c:when>
            <c:otherwise>
              <c:choose>
                <c:when test="${status3.index == 0}">
                  "<c:out value="${status3.current.accountSysId}" escapeXml="false"/>"
                </c:when>
                <c:otherwise>
                  ,"<c:out value="${status3.current.accountSysId}" escapeXml="false"/>"
                </c:otherwise>
              </c:choose>
            </c:otherwise>
          </c:choose>
        </c:forEach>
        );

			</c:forEach>
    </c:forEach>
  </c:forEach>

var altChargeType = new Array();
var altPoRequired = new Array();
var altPrAccountRequired = new Array();
var altChargeLabel1 = new Array();
var altChargeLabel2 = new Array();
var altChargeLabel3 = new Array();
var altChargeLabel4 = new Array();
  <c:forEach var="myWorkareaFacilityViewBean" items="${companiesFacilitiesDropdown}" varStatus="status">
    <c:set var="currentCompanyId" value='${status.current.companyId}'/>
		<c:set var="reportGroupCollection" value='${status.current.costReportGroupList}'/>

		<c:forEach var="costReportGroupBean" items="${reportGroupCollection}" varStatus="status1">
			<c:set var="currentReportGroup" value='${status1.current.costReportGroup}'/>
			<c:set var="facilityListCollection" value='${status1.current.facilityList}'/>

      <c:forEach var="facilityObjBean" items="${facilityListCollection}" varStatus="status2">
        <c:set var="currentFacilityId" value='${status2.current.facilityId}'/>
        <c:set var="accountSysListCollection" value='${status2.current.accountSysList}'/>

        <bean:size id="accountSysSize" name="accountSysListCollection"/>
        <c:if test="${accountSysSize > 1}">
          altChargeType["My Accounting Systems"] = new Array("My Charge Types");
        </c:if>

				<c:forEach var="accountSysObjBean" items="${accountSysListCollection}" varStatus="status3">
					<c:set var="currentAccountSys" value='${status3.current.accountSysId}'/>
					<c:set var="chargeTypeListCollection" value='${status3.current.chargeTypeList}'/>
		
          altChargeType["<c:out value="${currentCompanyId}${currentReportGroup}${currentFacilityId}${currentAccountSys}" escapeXml="false"/>"] = new Array(
            <c:forEach var="chargeTypeObjBean" items="${chargeTypeListCollection}" varStatus="status4">
              <c:choose>
                <c:when test="${status4.index == 0}">
                  "<c:out value="${status4.current.chargeType}" escapeXml="false"/>"
                </c:when>
                <c:otherwise>
                  ,"<c:out value="${status4.current.chargeType}" escapeXml="false"/>"
                </c:otherwise>
              </c:choose>
            </c:forEach>
          );

			 altPoRequired["<c:out value="${currentCompanyId}${currentReportGroup}${currentFacilityId}${currentAccountSys}" escapeXml="false"/>"] = new Array(
            <c:forEach var="chargeTypeObjBean" items="${chargeTypeListCollection}" varStatus="status4">
              <c:choose>
                <c:when test="${status4.index == 0}">
                  "<c:out value="${status4.current.poRequired}" escapeXml="false"/>"
                </c:when>
                <c:otherwise>
                  ,"<c:out value="${status4.current.poRequired}" escapeXml="false"/>"
                </c:otherwise>
              </c:choose>
            </c:forEach>
          );
			 altPrAccountRequired["<c:out value="${currentCompanyId}${currentReportGroup}${currentFacilityId}${currentAccountSys}" escapeXml="false"/>"] = new Array(
            <c:forEach var="chargeTypeObjBean" items="${chargeTypeListCollection}" varStatus="status4">
              <c:choose>
                <c:when test="${status4.index == 0}">
                  "<c:out value="${status4.current.prAccountRequired}" escapeXml="false"/>"
                </c:when>
                <c:otherwise>
                  ,"<c:out value="${status4.current.prAccountRequired}" escapeXml="false"/>"
                </c:otherwise>
              </c:choose>
            </c:forEach>
          );
			 altChargeLabel1["<c:out value="${currentCompanyId}${currentReportGroup}${currentFacilityId}${currentAccountSys}" escapeXml="false"/>"] = new Array(
            <c:forEach var="chargeTypeObjBean" items="${chargeTypeListCollection}" varStatus="status4">
              <c:choose>
                <c:when test="${status4.index == 0}">
                  "<c:out value="${status4.current.chargeLabel1}" escapeXml="false"/>"
                </c:when>
                <c:otherwise>
                  ,"<c:out value="${status4.current.chargeLabel1}" escapeXml="false"/>"
                </c:otherwise>
              </c:choose>
            </c:forEach>
          );
          altChargeLabel2["<c:out value="${currentCompanyId}${currentReportGroup}${currentFacilityId}${currentAccountSys}" escapeXml="false"/>"] = new Array(
            <c:forEach var="chargeTypeObjBean" items="${chargeTypeListCollection}" varStatus="status4">
              <c:choose>
                <c:when test="${status4.index == 0}">
                  "<c:out value="${status4.current.chargeLabel2}" escapeXml="false"/>"
                </c:when>
                <c:otherwise>
                  ,"<c:out value="${status4.current.chargeLabel2}" escapeXml="false"/>"
                </c:otherwise>
              </c:choose>
            </c:forEach>
          );
			 altChargeLabel3["<c:out value="${currentCompanyId}${currentReportGroup}${currentFacilityId}${currentAccountSys}" escapeXml="false"/>"] = new Array(
            <c:forEach var="chargeTypeObjBean" items="${chargeTypeListCollection}" varStatus="status4">
              <c:choose>
                <c:when test="${status4.index == 0}">
                  "<c:out value="${status4.current.chargeLabel3}" escapeXml="false"/>"
                </c:when>
                <c:otherwise>
                  ,"<c:out value="${status4.current.chargeLabel3}" escapeXml="false"/>"
                </c:otherwise>
              </c:choose>
            </c:forEach>
          );
          altChargeLabel4["<c:out value="${currentCompanyId}${currentReportGroup}${currentFacilityId}${currentAccountSys}" escapeXml="false"/>"] = new Array(
            <c:forEach var="chargeTypeObjBean" items="${chargeTypeListCollection}" varStatus="status4">
              <c:choose>
                <c:when test="${status4.index == 0}">
                  "<c:out value="${status4.current.chargeLabel4}" escapeXml="false"/>"
                </c:when>
                <c:otherwise>
                  ,"<c:out value="${status4.current.chargeLabel4}" escapeXml="false"/>"
                </c:otherwise>
              </c:choose>
            </c:forEach>
          );

				</c:forEach>
			</c:forEach>
    </c:forEach>
  </c:forEach>

var altCompanyAccountSysId = new Array();
var altCompanyAccountSysDesc = new Array();
altCompanyAccountSysId["My Companies"] = new Array("My Accounting Systems");
altCompanyAccountSysDesc["My Companies"] = new Array("My Accounting Systems");

  <c:forEach var="myWorkareaFacilityViewBean" items="${companiesFacilitiesDropdown}" varStatus="status">
    <c:set var="currentCompanyId" value='${status.current.companyId}'/>
    <c:set var="companyAccountSysCollection" value='${status.current.companyAccountSysList}'/>

    <bean:size id="companyAccountSysSize" name="companyAccountSysCollection"/>

    altCompanyAccountSysId["<c:out value="${currentCompanyId}" escapeXml="false"/>"] = new Array(
    <c:forEach var="accountSysBean" items="${companyAccountSysCollection}" varStatus="status1">
        <c:choose>
          <c:when test="${status1.index == 0 && companyAccountSysSize > 1}">
            "My Accounting Systems"
		        ,"<c:out value="${status1.current.accountSysId}" escapeXml="false"/>"
          </c:when>
          <c:otherwise>
            <c:choose>
              <c:when test="${status1.index == 0}">
                "<c:out value="${status1.current.accountSysId}" escapeXml="false"/>"
              </c:when>
              <c:otherwise>
                ,"<c:out value="${status1.current.accountSysId}" escapeXml="false"/>"
              </c:otherwise>
            </c:choose>
          </c:otherwise>
        </c:choose>
    </c:forEach>
    );

	altCompanyAccountSysDesc["<c:out value="${currentCompanyId}" escapeXml="false"/>"] = new Array(
        <c:forEach var="accountSysObjBean" items="${companyAccountSysCollection}" varStatus="status1">
          <c:choose>
            <c:when test="${status1.index == 0 && companyAccountSysSize > 1}">
              "<fmt:message key="label.myaccountsytems"/>"
		          ,"<c:out value="${status1.current.accountSysId}" escapeXml="false"/>"
            </c:when>
            <c:otherwise>
              <c:choose>
                <c:when test="${status1.index == 0}">
                  "<c:out value="${status1.current.accountSysId}" escapeXml="false"/>"
                </c:when>
                <c:otherwise>
                  ,"<c:out value="${status1.current.accountSysId}" escapeXml="false"/>"
                </c:otherwise>
              </c:choose>
            </c:otherwise>
          </c:choose>
        </c:forEach>
        );
  </c:forEach>

var altCompanyChargeType = new Array();
var altCompanyPoRequired = new Array();
var altCompanyPrAccountRequired = new Array();
var altCompanyChargeLabel1 = new Array();
var altCompanyChargeLabel2 = new Array();
var altCompanyChargeLabel3 = new Array();
var altCompanyChargeLabel4 = new Array();
  <c:forEach var="myWorkareaFacilityViewBean" items="${companiesFacilitiesDropdown}" varStatus="status">
    <c:set var="currentCompanyId" value='${status.current.companyId}'/>
        <c:set var="accountSysListCollection" value='${status.current.companyAccountSysList}'/>

        <bean:size id="accountSysSize" name="accountSysListCollection"/>
        <c:if test="${accountSysSize > 1}">
          altChargeType["My Accounting Systems"] = new Array("My Charge Types");
        </c:if>

				<c:forEach var="accountSysObjBean" items="${accountSysListCollection}" varStatus="status3">
					<c:set var="currentAccountSys" value='${status3.current.accountSysId}'/>
					<c:set var="chargeTypeListCollection" value='${status3.current.chargeTypeList}'/>

          altCompanyChargeType["<c:out value="${currentCompanyId}${currentAccountSys}" escapeXml="false"/>"] = new Array(
            <c:forEach var="chargeTypeObjBean" items="${chargeTypeListCollection}" varStatus="status4">
              <c:choose>
                <c:when test="${status4.index == 0}">
                  "<c:out value="${status4.current.chargeType}" escapeXml="false"/>"
                </c:when>
                <c:otherwise>
                  ,"<c:out value="${status4.current.chargeType}" escapeXml="false"/>"
                </c:otherwise>
              </c:choose>
            </c:forEach>
          );

			 altCompanyPoRequired["<c:out value="${currentCompanyId}${currentAccountSys}" escapeXml="false"/>"] = new Array(
            <c:forEach var="chargeTypeObjBean" items="${chargeTypeListCollection}" varStatus="status4">
              <c:choose>
                <c:when test="${status4.index == 0}">
                  "<c:out value="${status4.current.poRequired}" escapeXml="false"/>"
                </c:when>
                <c:otherwise>
                  ,"<c:out value="${status4.current.poRequired}" escapeXml="false"/>"
                </c:otherwise>
              </c:choose>
            </c:forEach>
          );
			 altCompanyPrAccountRequired["<c:out value="${currentCompanyId}${currentAccountSys}" escapeXml="false"/>"] = new Array(
            <c:forEach var="chargeTypeObjBean" items="${chargeTypeListCollection}" varStatus="status4">
              <c:choose>
                <c:when test="${status4.index == 0}">
                  "<c:out value="${status4.current.prAccountRequired}" escapeXml="false"/>"
                </c:when>
                <c:otherwise>
                  ,"<c:out value="${status4.current.prAccountRequired}" escapeXml="false"/>"
                </c:otherwise>
              </c:choose>
            </c:forEach>
          );
			 altCompanyChargeLabel1["<c:out value="${currentCompanyId}${currentAccountSys}" escapeXml="false"/>"] = new Array(
            <c:forEach var="chargeTypeObjBean" items="${chargeTypeListCollection}" varStatus="status4">
              <c:choose>
                <c:when test="${status4.index == 0}">
                  "<c:out value="${status4.current.chargeLabel1}" escapeXml="false"/>"
                </c:when>
                <c:otherwise>
                  ,"<c:out value="${status4.current.chargeLabel1}" escapeXml="false"/>"
                </c:otherwise>
              </c:choose>
            </c:forEach>
          );
          altCompanyChargeLabel2["<c:out value="${currentCompanyId}${currentAccountSys}" escapeXml="false"/>"] = new Array(
            <c:forEach var="chargeTypeObjBean" items="${chargeTypeListCollection}" varStatus="status4">
              <c:choose>
                <c:when test="${status4.index == 0}">
                  "<c:out value="${status4.current.chargeLabel2}" escapeXml="false"/>"
                </c:when>
                <c:otherwise>
                  ,"<c:out value="${status4.current.chargeLabel2}" escapeXml="false"/>"
                </c:otherwise>
              </c:choose>
            </c:forEach>
          );
			 altCompanyChargeLabel3["<c:out value="${currentCompanyId}${currentAccountSys}" escapeXml="false"/>"] = new Array(
            <c:forEach var="chargeTypeObjBean" items="${chargeTypeListCollection}" varStatus="status4">
              <c:choose>
                <c:when test="${status4.index == 0}">
                  "<c:out value="${status4.current.chargeLabel3}" escapeXml="false"/>"
                </c:when>
                <c:otherwise>
                  ,"<c:out value="${status4.current.chargeLabel3}" escapeXml="false"/>"
                </c:otherwise>
              </c:choose>
            </c:forEach>
          );
          altCompanyChargeLabel4["<c:out value="${currentCompanyId}${currentAccountSys}" escapeXml="false"/>"] = new Array(
            <c:forEach var="chargeTypeObjBean" items="${chargeTypeListCollection}" varStatus="status4">
              <c:choose>
                <c:when test="${status4.index == 0}">
                  "<c:out value="${status4.current.chargeLabel4}" escapeXml="false"/>"
                </c:when>
                <c:otherwise>
                  ,"<c:out value="${status4.current.chargeLabel4}" escapeXml="false"/>"
                </c:otherwise>
              </c:choose>
            </c:forEach>
          );

				</c:forEach>
  </c:forEach>

var altGroupAccountSysId = new Array();
var altGroupAccountSysDesc = new Array();
altGroupAccountSysId["My Reporting Groups"] = new Array("My Accounting Systems");
altGroupAccountSysDesc["My Reporting Groups"] = new Array("My Accounting Systems");
  <c:forEach var="myWorkareaFacilityViewBean" items="${companiesFacilitiesDropdown}" varStatus="status">
    <c:set var="currentCompanyId" value='${status.current.companyId}'/>
		<c:set var="reportGroupCollection" value='${status.current.costReportGroupList}'/>

		<c:forEach var="costReportGroupBean" items="${reportGroupCollection}" varStatus="status1">
			<c:set var="currentReportGroup" value='${status1.current.costReportGroup}'/>
			<c:set var="groupAccountSysListCollection" value='${status1.current.accountSysList}'/>

      <bean:size id="accountSysSize" name="groupAccountSysListCollection"/>
      altGroupAccountSysId["<c:out value="${currentCompanyId}${currentReportGroup}" escapeXml="false"/>"] = new Array(
      <c:forEach var="accountSysObjBean" items="${groupAccountSysListCollection}" varStatus="status2">
        <c:choose>
          <c:when test="${status2.index == 0 && accountSysSize > 1}">
            "<c:out value="My Accounting Systems" escapeXml="false"/>"
		        ,"<c:out value="${status2.current.accountSysId}" escapeXml="false"/>"
          </c:when>
          <c:otherwise>
            <c:choose>
              <c:when test="${status2.index == 0}">
                "<c:out value="${status2.current.accountSysId}" escapeXml="false"/>"
              </c:when>
              <c:otherwise>
                ,"<c:out value="${status2.current.accountSysId}" escapeXml="false"/>"
              </c:otherwise>
            </c:choose>
          </c:otherwise>
        </c:choose>
      </c:forEach>
      );

      altGroupAccountSysDesc["<c:out value="${currentCompanyId}${currentReportGroup}" escapeXml="false"/>"] = new Array(
      <c:forEach var="accountSysObjBean" items="${groupAccountSysListCollection}" varStatus="status2">
        <c:choose>
          <c:when test="${status2.index == 0 && accountSysSize > 1}">
            "<fmt:message key="label.myaccountsytems"/>"
		        ,"<c:out value="${status2.current.accountSysId}" escapeXml="false"/>"
          </c:when>
          <c:otherwise>
            <c:choose>
              <c:when test="${status2.index == 0}">
                "<c:out value="${status2.current.accountSysId}" escapeXml="false"/>"
              </c:when>
              <c:otherwise>
                ,"<c:out value="${status2.current.accountSysId}" escapeXml="false"/>"
              </c:otherwise>
            </c:choose>
          </c:otherwise>
        </c:choose>
      </c:forEach>
      );

		</c:forEach>
  </c:forEach>

var altGroupChargeType = new Array();
var altGroupPoRequired = new Array();
var altGroupPrAccountRequired = new Array();
var altGroupChargeLabel1 = new Array();
var altGroupChargeLabel2 = new Array();
var altGroupChargeLabel3 = new Array();
var altGroupChargeLabel4 = new Array();
  <c:forEach var="myWorkareaFacilityViewBean" items="${companiesFacilitiesDropdown}" varStatus="status">
    <c:set var="currentCompanyId" value='${status.current.companyId}'/>
		<c:set var="reportGroupCollection" value='${status.current.costReportGroupList}'/>

		<c:forEach var="costReportGroupBean" items="${reportGroupCollection}" varStatus="status1">
			<c:set var="currentReportGroup" value='${status1.current.costReportGroup}'/>
			<c:set var="groupAccountSysListCollection" value='${status1.current.accountSysList}'/>

        <bean:size id="accountSysSize" name="groupAccountSysListCollection"/>
        <c:if test="${accountSysSize > 1}">
          altChargeType["My Accounting Systems"] = new Array("My Charge Types");
        </c:if>

		<c:forEach var="accountSysObjBean" items="${groupAccountSysListCollection}" varStatus="status3">
			<c:set var="currentAccountSys" value='${status3.current.accountSysId}'/>
			<c:set var="chargeTypeListCollection" value='${status3.current.chargeTypeList}'/>

          altGroupChargeType["<c:out value="${currentCompanyId}${currentReportGroup}${currentAccountSys}" escapeXml="false"/>"] = new Array(
            <c:forEach var="chargeTypeObjBean" items="${chargeTypeListCollection}" varStatus="status4">
              <c:choose>
                <c:when test="${status4.index == 0}">
                  "<c:out value="${status4.current.chargeType}" escapeXml="false"/>"
                </c:when>
                <c:otherwise>
                  ,"<c:out value="${status4.current.chargeType}" escapeXml="false"/>"
                </c:otherwise>
              </c:choose>
            </c:forEach>
          );

			 altGroupPoRequired["<c:out value="${currentCompanyId}${currentReportGroup}${currentAccountSys}" escapeXml="false"/>"] = new Array(
            <c:forEach var="chargeTypeObjBean" items="${chargeTypeListCollection}" varStatus="status4">
              <c:choose>
                <c:when test="${status4.index == 0}">
                  "<c:out value="${status4.current.poRequired}" escapeXml="false"/>"
                </c:when>
                <c:otherwise>
                  ,"<c:out value="${status4.current.poRequired}" escapeXml="false"/>"
                </c:otherwise>
              </c:choose>
            </c:forEach>
          );
			 altGroupPrAccountRequired["<c:out value="${currentCompanyId}${currentReportGroup}${currentAccountSys}" escapeXml="false"/>"] = new Array(
            <c:forEach var="chargeTypeObjBean" items="${chargeTypeListCollection}" varStatus="status4">
              <c:choose>
                <c:when test="${status4.index == 0}">
                  "<c:out value="${status4.current.prAccountRequired}" escapeXml="false"/>"
                </c:when>
                <c:otherwise>
                  ,"<c:out value="${status4.current.prAccountRequired}" escapeXml="false"/>"
                </c:otherwise>
              </c:choose>
            </c:forEach>
          );
			 altGroupChargeLabel1["<c:out value="${currentCompanyId}${currentReportGroup}${currentAccountSys}" escapeXml="false"/>"] = new Array(
            <c:forEach var="chargeTypeObjBean" items="${chargeTypeListCollection}" varStatus="status4">
              <c:choose>
                <c:when test="${status4.index == 0}">
                  "<c:out value="${status4.current.chargeLabel1}" escapeXml="false"/>"
                </c:when>
                <c:otherwise>
                  ,"<c:out value="${status4.current.chargeLabel1}" escapeXml="false"/>"
                </c:otherwise>
              </c:choose>
            </c:forEach>
          );
          altGroupChargeLabel2["<c:out value="${currentCompanyId}${currentReportGroup}${currentAccountSys}" escapeXml="false"/>"] = new Array(
            <c:forEach var="chargeTypeObjBean" items="${chargeTypeListCollection}" varStatus="status4">
              <c:choose>
                <c:when test="${status4.index == 0}">
                  "<c:out value="${status4.current.chargeLabel2}" escapeXml="false"/>"
                </c:when>
                <c:otherwise>
                  ,"<c:out value="${status4.current.chargeLabel2}" escapeXml="false"/>"
                </c:otherwise>
              </c:choose>
            </c:forEach>
          );
          altGroupChargeLabel3["<c:out value="${currentCompanyId}${currentReportGroup}${currentAccountSys}" escapeXml="false"/>"] = new Array(
            <c:forEach var="chargeTypeObjBean" items="${chargeTypeListCollection}" varStatus="status4">
              <c:choose>
                <c:when test="${status4.index == 0}">
                  "<c:out value="${status4.current.chargeLabel3}" escapeXml="false"/>"
                </c:when>
                <c:otherwise>
                  ,"<c:out value="${status4.current.chargeLabel3}" escapeXml="false"/>"
                </c:otherwise>
              </c:choose>
            </c:forEach>
          );
          altGroupChargeLabel4["<c:out value="${currentCompanyId}${currentReportGroup}${currentAccountSys}" escapeXml="false"/>"] = new Array(
            <c:forEach var="chargeTypeObjBean" items="${chargeTypeListCollection}" varStatus="status4">
              <c:choose>
                <c:when test="${status4.index == 0}">
                  "<c:out value="${status4.current.chargeLabel4}" escapeXml="false"/>"
                </c:when>
                <c:otherwise>
                  ,"<c:out value="${status4.current.chargeLabel4}" escapeXml="false"/>"
                </c:otherwise>
              </c:choose>
            </c:forEach>
          );

				</c:forEach>
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
validvalues:"<fmt:message key="label.validvalues"/>",
poLabel:"<fmt:message key="label.po"/>",
invoicePeriod:"<fmt:message key="label.invalidinvoiceperiod"/>",
invoice:"<fmt:message key="label.invalidinvoice"/>",
getTemplateName:"<fmt:message key="label.gettemplatename"/>",
selectreportfield:"<fmt:message key="error.selectreportfield"/>",
submitOnlyOnce:"<fmt:message key="label.submitOnlyOnce"/>"};
// -->
</script>

</head>

<body bgcolor="#ffffff" onload="mySearchOnload();">

<tcmis:form action="/costreportsearch.do" onsubmit="return submitFrameOnlyOnce();">
<!-- Transit Page Begins -->
<div id="transitPage" class="optionTitleBoldCenter" >
  <br><br><br><fmt:message key="label.pleasewait"/>
  <br><br><br><img src="/images/rel_interstitial_loading.gif" align="middle">
</div>
<!-- Transit Page Ends -->

<div class="interface" id="searchMainPage" > <!-- Start of interface-->

<div class="contentArea"> <!-- Start of contentArea-->

<!-- Search Option Begins -->
<table id="searchMaskTable" width="100%" border="0" cellpadding="0" cellspacing="0">
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
		<%-- template name --%>
		<c:set var="displayVal" value='style="display: none"'/>
		<c:if test="${costReportInputBean.templateName != null}">
			<c:set var="displayVal" value='style="display: "'/>
		</c:if>
		<tr>
 		<td width="100%" colspan="2" class="optionTitleBoldCenter" id="templateLabel" ${displayVal} >
			 <c:set var="globalLabelLetter"><fmt:message key="${costReportInputBean.globalizationLabelLetter}"/></c:set>
			 <fmt:message key="label.templatename"/>:&nbsp;
			 <span id="templateLabelSpan">${globalLabelLetter}${costReportInputBean.templateId}-${costReportInputBean.templateName}</span>
		</td>
		</tr>

		<tr >
      <td width="40%">
	      <table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableSearch">

			<tr >
 		    <td width="15%" class="optionTitleBoldRight">
  			  <fmt:message key="label.company"/>:&nbsp;
 		    </td>
				<td width="25%" class="optionTitleLeft">
				<select name="companyId" id="companyId" onchange="companyChanged()" class="selectBox">

				</select>
 		    </td>
			</tr>

		  <tr >
 		    <td width="15%" class="optionTitleBoldRight">
  			  <fmt:message key="label.reportinggroup"/>:&nbsp;
 		    </td>
			 <td width="25%" class="optionTitleLeft">
         	<select name="reportingGroup" id="reportingGroup" onchange="costReportGroupChanged()" class="selectBox">
         	</select>
 		    </td>
		</tr>

 	  <tr >
 		<td width="15%" class="optionTitleBoldRight">
  			<fmt:message key="label.facility"/>:&nbsp;
 		</td>
		<td width="25%" class="optionTitleLeft">
         <select name="facilityId" id="facilityId" onchange="facilityChanged()" class="selectBox">
         </select>
 		</td>
		  </tr>

		<tr >
 		<td width="15%" class="optionTitleBoldRight">
  			<fmt:message key="label.workarea"/>:&nbsp;
 		</td>
		<td width="25%" class="optionTitleLeft">
          <select name="application" id="application" class="selectBox">
          </select>
 		</td>
		</tr>

 	  <tr >
 		<td width="15%" class="optionTitleBoldRight">
  			<fmt:message key="label.accountingsystem"/>:&nbsp;
 		</td>
		<td width="25%" class="optionTitleLeft">
          <select name="accountSysId" id="accountSysId" onchange="accountSysChanged()" class="selectBox">
          </select>
		 </td>
		      </tr>

		<%-- charge type --%>
		<c:set var="checkedValueDirect" value=''/>
		<c:set var="checkedValueIndirect" value=''/>
		<c:if test="${costReportInputBean.chargeType == null}">
			<c:set var="checkedValueDirect" value='checked=true'/>
		</c:if>
		<c:if test="${costReportInputBean.chargeType == 'd'}">
			<c:set var="checkedValueDirect" value='checked=true'/>
		</c:if>
		<c:if test="${costReportInputBean.chargeType == 'i'}">
			<c:set var="checkedValueIndirect" value='checked=true'/>
		</c:if>

		<tr >
		 <td width="15%" id="chargeTypeTdBlank" style="display: none">&nbsp;</td>
		 <td width="25%" class="optionTitleBoldLeft" id="chargeTypeTd" style="display: none" >
  			<input name="chargeType" id="chargeTypeDirect" type="radio" value="d" class="radioBtns" onclick="chargeTypeChanged('d')" ${checkedValueDirect}>
			<fmt:message key="label.direct"/>
         <input name="chargeType" id="chargeTypeIndirect" type="radio" value="i" class="radioBtns" onclick="chargeTypeChanged('i')" ${checkedValueIndirect}>
			<fmt:message key="label.indirect"/>			
		 </td>
		 </tr>
		<%-- po number --%>
		<tr >
			 <td width="15%" class="optionTitleBoldRight" id="poLabelTd" style="display: none">
			  <span name="poLabel" id="poLabel"></span>:&nbsp;
			 </td>
			 <td width="25%" class="optionTitleLeft" id="poTd" style="display: none">
			  <input class="inputBox" type="text" name="po" id="po" value="${costReportInputBean.po}" size="20" maxlength="30">
			 </td>
		</tr>
		<%-- charge number 1 --%>
		<tr >
			 <td width="15%" class="optionTitleBoldRight" id="chargeLabelTd" style="display: none">
			  <span name="label1" id="label1"></span>:&nbsp;
			 </td>
			 <td width="25%" class="optionTitleLeft" id="chargeTd" style="display: none">
			  <input class="inputBox" type="text" name="accountNumber" id="accountNumber" value="${costReportInputBean.accountNumber}" size="20" maxlength="30">
			 </td>
		</tr>
		<%-- charge number 2--%>
		<tr >
			 <td width="15%" class="optionTitleBoldRight" id="chargeLabel2Td" style="display: none">
				<span name="label2" id="label2"></span>:&nbsp;
		 	 </td>
			 <td width="25%" class="optionTitleLeft" id="charge2Td" style="display: none">
			  <input class="inputBox" type="text" name="accountNumber2" id="accountNumber2" value="${costReportInputBean.accountNumber2}" size="20" maxlength="30">
		 	</td>
		</tr>
		<%-- charge number 3--%>
		<tr >
			 <td width="15%" class="optionTitleBoldRight" id="chargeLabel3Td" style="display: none">
				<span name="label3" id="label3"></span>:&nbsp;
		 	 </td>
			 <td width="25%" class="optionTitleLeft" id="charge3Td" style="display: none">
			  <input class="inputBox" type="text" name="accountNumber3" id="accountNumber3" value="${costReportInputBean.accountNumber3}" size="20" maxlength="30">
		 	</td>
		</tr>
		<%-- charge number 4--%>
		<tr >
			 <td width="15%" class="optionTitleBoldRight" id="chargeLabel4Td" style="display: none">
				<span name="label4" id="label4"></span>:&nbsp;
		 	 </td>
			 <td width="25%" class="optionTitleLeft" id="charge4Td" style="display: none">
			  <input class="inputBox" type="text" name="accountNumber4" id="accountNumber4" value="${costReportInputBean.accountNumber4}" size="20" maxlength="30">
		 	</td>
		</tr>

		<%-- blank row --%>
		<tr id="blank1">
			<td>&nbsp;</td>
			<td>&nbsp;</td>
		</tr>
		<tr id="blank2">
			<td>&nbsp;</td>
			<td>&nbsp;</td>
		</tr>
		<tr id="blank3">
			<td>&nbsp;</td>
			<td>&nbsp;</td>
		</tr>
		<tr id="blank4">
			<td>&nbsp;</td>
			<td>&nbsp;</td>
		</tr>
		<tr id="blank5">
			<td>&nbsp;</td>
			<td>&nbsp;</td>
		</tr>

		</table>
      </td>
      <%-- end of left table --%>

		  <%-- begin of right tbale --%>
      <td width="60%">
		    <table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableSearch">

			 <tr >
 		        <td width="20%" class="optionTitleBoldRight">
  			      <fmt:message key="label.requestor"/>:&nbsp;
 		        </td>
 		        <td width="80%" colspan="3" class="optionTitleLeft">
					<input class="inputBox" type="text" name="requestorName" id="requestorName" value="${costReportInputBean.requestorName}" onChange="invalidateRequestor()" size="20">
				  	<input type="button" class="lookupBtn" onmouseover="this.className='lookupBtn lookupBtnOver'" onmouseout="this.className='lookupBtn'" name="lookupButton" id="lookupButton" value="" onclick="getPersonnel()">
 		        </td>
		      </tr>
				<tr>
					<td width="20%" class="optionTitleBoldRight">
  			      	<fmt:message key="label.unitofmeasure"/>:&nbsp;
 		         </td>
					<td width="80%" colspan="3" class="optionTitleLeft">
						<select name="uom" id="uom" class="selectBox">
							<c:set var="selectedVal" value=''/>
							<c:if test="${costReportInputBean.uom != null}">
								<c:if test="${costReportInputBean.uom == 'Invoice'}">
									<c:set var="selectedVal" value='selected'/>
								</c:if>
							</c:if>
							<option  ${selectedVal} value="Invoice"><fmt:message key="label.invoiceuom"/></option>
							<c:set var="selectedVal" value=''/>
							<c:if test="${costReportInputBean.uom != null}">
								<c:if test="${costReportInputBean.uom == 'Item'}">
									<c:set var="selectedVal" value='selected'/>
								</c:if>
							</c:if>
							<option  ${selectedVal} value="Item"><fmt:message key="label.itemuom"/></option>
						</select>
					</td>
				</tr>
				<tr >
		        <td width="25%" class="optionTitleBoldRight">
  			      <fmt:message key="label.searchby"/>:&nbsp;
 		        </td>
					<td width="75%" colspan="3" class="optionTitleLeft">
						<select name="searchBy" id="searchBy" class="selectBox">
							<c:set var="selectedVal" value=''/>
							<c:if test="${costReportInputBean.searchBy != null}">
								<c:if test="${costReportInputBean.searchBy == 'mfg_desc'}">
									<c:set var="selectedVal" value='selected'/>
								</c:if>
							</c:if>
							<option  value="mfg_desc"><fmt:message key="label.mfg"/></option>
							<c:set var="selectedVal" value=''/>
							<c:if test="${costReportInputBean.searchBy != null}">
								<c:if test="${costReportInputBean.searchBy == 'cat_part_no'}">
									<c:set var="selectedVal" value='selected'/>
								</c:if>
							</c:if>
							<option ${selectedVal} value="cat_part_no"><fmt:message key="label.partnumber"/></option>
							<c:set var="selectedVal" value=''/>
							<c:if test="${costReportInputBean.searchBy != null}">
								<c:if test="${costReportInputBean.searchBy == 'part_description'}">
									<c:set var="selectedVal" value='selected'/>
								</c:if>
							</c:if>
							<option ${selectedVal} value="part_description"><fmt:message key="label.partdescription"/></option>
							<c:set var="selectedVal" value=''/>
							<c:if test="${costReportInputBean.searchBy != null}">
								<c:if test="${costReportInputBean.searchBy == 'item_id'}">
									<c:set var="selectedVal" value='selected'/>
								</c:if>
							</c:if>
							<option ${selectedVal} value="item_id"><fmt:message key="label.item"/></option>
							<c:set var="selectedVal" value=''/>
							<c:if test="${costReportInputBean.searchBy != null}">
								<c:if test="${costReportInputBean.searchBy == 'item_desc'}">
									<c:set var="selectedVal" value='selected'/>
								</c:if>
							</c:if>
							<option ${selectedVal} value="item_desc"><fmt:message key="label.itemdescription"/></option>
							<c:set var="selectedVal" value=''/>
							<c:if test="${costReportInputBean.searchBy != null}">
								<c:if test="${costReportInputBean.searchBy == 'item_type'}">
									<c:set var="selectedVal" value='selected'/>
								</c:if>
							</c:if>
							<option ${selectedVal} value="item_type"><fmt:message key="label.itemtype"/></option>
							<c:set var="selectedVal" value=''/>
							<c:if test="${costReportInputBean.searchBy != null}">
								<c:if test="${costReportInputBean.searchBy == 'reference_number'}">
									<c:set var="selectedVal" value='selected'/>
								</c:if>
							</c:if>
							<option ${selectedVal} value="reference_number"><fmt:message key="label.reference"/></option>
							<c:set var="selectedVal" value=''/>
							<c:if test="${costReportInputBean.searchBy != null}">
								<c:if test="${costReportInputBean.searchBy == 'mr_line'}">
									<c:set var="selectedVal" value='selected'/>
								</c:if>
							</c:if>
							<option ${selectedVal} value="mr_line"><fmt:message key="label.mrline"/></option>
							<c:set var="selectedVal" value=''/>
							<c:if test="${costReportInputBean.searchBy != null}">
								<c:if test="${costReportInputBean.searchBy == 'radian_po'}">
									<c:set var="selectedVal" value='selected'/>
								</c:if>
							</c:if>
							<option ${selectedVal} value="radian_po"><fmt:message key="label.haaspo"/></option>
						</select>

						<select name="searchType" id="searchType" class="selectBox">
							<c:set var="selectedVal" value=''/>
							<c:if test="${costReportInputBean.searchType != null}">
								<c:if test="${costReportInputBean.searchType == 'is'}">
									<c:set var="selectedVal" value='selected'/>
								</c:if>
							</c:if>
							<option  ${selectedVal} value="is"><fmt:message key="label.is"/></option>
							<c:set var="selectedVal" value=''/>
							<c:if test="${costReportInputBean.searchType != null}">
								<c:if test="${costReportInputBean.searchType == 'contains'}">
									<c:set var="selectedVal" value='selected'/>
								</c:if>
							</c:if>
							<option  ${selectedVal} value="contains"><fmt:message key="label.contains"/></option>
						</select>

						<input class="inputBox" type="text" name="searchText" id="searchText" value="${costReportInputBean.searchText}" size="15">
 		        	</td>
			 	</tr>

			   <tr>
 					<td width="20%" class="optionTitleBoldRight">
  						<fmt:message key="label.invoicenr"/>:&nbsp;
 					</td>
					<td width="30%" class="optionTitleLeft" >
   					<input class="inputBox" type="text" name="searchByInvoiceNumber" id="searchByInvoiceNumber" value="${costReportInputBean.searchByInvoiceNumber}" size="15">
 					</td>
					<td width="20%" class="optionTitleBoldRight">
						<fmt:message key="label.invoiceperiod"/>:&nbsp;
					</td>
					<td width="30%" class="optionTitleLeft">
						<input class="inputBox" type="text" name="searchByInvoicePeriod" id="searchByInvoicePeriod" value="${costReportInputBean.searchByInvoicePeriod}" size="15">
						<%--
						<select name="searchByInvoicePeriod" id="searchByInvoicePeriod" class="selectBox" >
							<option  value="All">All Invoice Periods</option>
							<option  value="0">0 : 12/01/2000 - 03/31/2003</option>
							<option  value="1">1 : 05/01/1999 - 05/14/1999</option>
						</select>
						--%>
					</td>
				</tr>

				<tr>
					<td width="20%" class="optionTitleBoldRight">
  						<fmt:message key="label.invoicedbetween"/>:&nbsp;
 					</td>
					 <td width="80%" colspan="3" class="optionTitleBoldLeft">
						<c:set var="fmtDate" value=''/>
						<c:if test="${costReportInputBean.invoiceDateBegin != null}">
							<fmt:formatDate var="fmtDate" value="${costReportInputBean.invoiceDateBegin}" pattern="${dateFormatPattern}"/>
						</c:if>
						<input class="inputBox pointer" name="invoiceDateBegin" id="invoiceDateBegin" type="text" value="${fmtDate}" size="10" readonly="true" onClick="return getCalendar(document.getElementById('invoiceDateBegin'),null,null,null,document.getElementById('invoiceDateEnd'));">
						<fmt:message key="label.and"/>
						<c:set var="fmtDate" value=''/>
						<c:if test="${costReportInputBean.invoiceDateEnd != null}">
							<fmt:formatDate var="fmtDate" value="${costReportInputBean.invoiceDateEnd}" pattern="${dateFormatPattern}"/>
						</c:if>
						<input class="inputBox pointer" name="invoiceDateEnd" id="invoiceDateEnd" type="text" value="${fmtDate}" size="10" readonly="true" onClick="return getCalendar(document.getElementById('invoiceDateEnd'),null,null,document.getElementById('invoiceDateBegin'));">
 					</td>
 	  			</tr> 

			   <tr>
 					<td width="20%" class="optionTitleBoldRight">
  						<fmt:message key="label.deliveredbetween"/>:&nbsp;
					</td>
					<td width="80%" colspan="3" class="optionTitleBoldLeft">
						<c:set var="fmtDate" value=''/>
						<c:if test="${costReportInputBean.dateDeliveredBegin != null}">
							<fmt:formatDate var="fmtDate" value="${costReportInputBean.dateDeliveredBegin}" pattern="${dateFormatPattern}"/>
						</c:if>
						<input class="inputBox pointer" name="dateDeliveredBegin" id="dateDeliveredBegin" type="text" value="${fmtDate}" size="10" readonly="true" onClick="return getCalendar(document.getElementById('dateDeliveredBegin'),null,null,null,document.getElementById('dateDeliveredEnd'));">
						<fmt:message key="label.and"/>
						<c:set var="fmtDate" value=''/>
						<c:if test="${costReportInputBean.dateDeliveredEnd != null}">
							<fmt:formatDate var="fmtDate" value="${costReportInputBean.dateDeliveredEnd}" pattern="${dateFormatPattern}"/>
						</c:if>
						<input class="inputBox pointer" name="dateDeliveredEnd" id="dateDeliveredEnd" type="text" value="${fmtDate}" size="10" readonly="true" onClick="return getCalendar(document.getElementById('dateDeliveredEnd'),null,null,document.getElementById('dateDeliveredBegin'));">
					</td>
				</tr>

			   <tr >
 					<td width="20%" class="optionTitleBoldRight">
						 <fmt:message key="label.invoicetype"/>:&nbsp;
					</td>
					<td width="30%" class="optionTitleLeft">
						<select name="searchByInvoiceType" id="searchByInvoiceType" class="selectBox" size="4" multiple>
							<c:choose>
								<c:when test="${costReportInputBean.invoiceTypeCollection == null}">
									<option selected value="All Types"><fmt:message key="label.alltypes"/></option>
								</c:when>
								<c:otherwise>
									<c:set var="tmpColl" value='${costReportInputBean.invoiceTypeCollection}'/>
									<bean:size id="tmpSize" name="tmpColl"/>
									<c:choose>
										<c:when test="${tmpSize == 0}">
											<option selected value="All Types"><fmt:message key="label.alltypes"/></option>
										</c:when>
										<c:otherwise>
											<c:set var="selectedVal" value=''/>
											<c:set var="currentInvoiceType" value='All Types'/>
											<c:forEach var="bean" items="${costReportInputBean.invoiceTypeCollection}" varStatus="status2">
												<c:if test="${currentInvoiceType == status2.current}">
													<c:set var="selectedVal" value='selected'/>
												</c:if>
											</c:forEach>
											<option ${selectedVal} value="All Types"><fmt:message key="label.alltypes"/></option>
										</c:otherwise>
									</c:choose>
								</c:otherwise>
							</c:choose>

							<c:forEach var="vvItemTypeBean" items="${invoiceTypeCollection}" varStatus="status">
								<c:set var="selectedVal" value=''/>
								<c:set var="currentInvoiceType" value="${status.current.commodity}"/>
								<c:forEach var="bean" items="${costReportInputBean.invoiceTypeCollection}" varStatus="status2">
									<c:if test="${currentInvoiceType == status2.current}">
										<c:set var="selectedVal" value='selected'/>
									</c:if>
								</c:forEach>
								<option ${selectedVal} value="<c:out value="${status.current.commodity}"/>"><c:out value="${status.current.commodity}" escapeXml="false"/></option>
						 	</c:forEach>
						 </select>
					</td>
					<td width="15%" class="optionTitleBoldRight">
						<fmt:message key="label.itemtype"/>:&nbsp;
					</td>
					<td width="30%" class="optionTitleLeft">
						<select name="searchByItemType" id="searchByItemType" class="selectBox" size="4" multiple>
							<c:choose>
								<c:when test="${costReportInputBean.itemTypeCollection == null}">
									<option selected value="All Types"><fmt:message key="label.alltypes"/></option>
								</c:when>
								<c:otherwise>
									<c:set var="tmpColl" value='${costReportInputBean.itemTypeCollection}'/>
									<bean:size id="tmpSize" name="tmpColl"/>
									<c:choose>
										<c:when test="${tmpSize == 0}">
											<option selected value="All Types"><fmt:message key="label.alltypes"/></option>
										</c:when>
										<c:otherwise>
											<c:set var="selectedVal" value=''/>
											<c:set var="currentItemType" value='All Types'/>
											<c:forEach var="bean" items="${costReportInputBean.itemTypeCollection}" varStatus="status2">
												<c:if test="${currentItemType == status2.current}">
													<c:set var="selectedVal" value='selected'/>
												</c:if>
											</c:forEach>
											<option ${selectedVal} value="All Types"><fmt:message key="label.alltypes"/></option>
										</c:otherwise>
									</c:choose>
								</c:otherwise>
							</c:choose>

							<c:forEach var="vvItemTypeBean" items="${itemTypeCollection}" varStatus="status">
								<c:set var="selectedVal" value=''/>
								<c:set var="currentItemType" value="${status.current.itemType}"/>
								<c:forEach var="bean" items="${costReportInputBean.itemTypeCollection}" varStatus="status2">
									<c:if test="${currentItemType == status2.current}">
										<c:set var="selectedVal" value='selected'/>
									</c:if>
								</c:forEach>
								<option ${selectedVal} value="<c:out value="${status.current.itemType}"/>"><c:out value="${status.current.typeDesc}" escapeXml="false"/></option>
						 	</c:forEach>
						</select>
					</td>
				 </tr>

			   <%-- blank row--%>
			   <tr>
 					<td width="20%">
  						&nbsp;
					</td>
					<td width="80%" colspan="3" >
						&nbsp;
					</td>
				</tr>
			 
				</table>
      </td>
		  <%-- end of right table --%>
	  </tr>
	</table>
  <%-- end of top table --%>


    <table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableSearch">
 	  <tr >
 		<td class="optionTitleBoldCenter">
  			<fmt:message key="label.reportfields"/>
		 </td>
	   </tr>
	  </table>

  <%-- start of bottom table --%>
    <table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableSearch">
 	  <tr >
		 <%--- left table --%>
	   <td width="20%">
		 <table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableSearch">
<%--
		<c:set var="checkedValue" value=''/>
		<c:if test="${costReportInputBean.invoice != null}">
			<c:set var="checkedValue" value='checked=true'/>
		</c:if> 
		<tr >
        <td width="15%" class="optionTitleBoldRight">
			<input name="invoice" id="invoice" type="checkbox" value="Yes" class="radioBtns" ${checkedValue}>
 		</td>
 		<td width="85%" class="optionTitleBoldLeft">
  			<fmt:message key="label.invoice"/>
 		</td>
			 </tr>
 --%>		
		<c:set var="checkedValue" value=''/>
		<c:if test="${costReportInputBean.customerInvoiceNo != null}">
			<c:set var="checkedValue" value='checked=true'/>
		</c:if> 	 
		<tr >
	        <td width="15%" class="optionTitleBoldRight">
				<input name="customerInvoiceNo" id="customerInvoiceNo" type="checkbox" value="Yes" class="radioBtns" ${checkedValue}>
	 		</td>
	 		<td width="85%" class="optionTitleBoldLeft">
	  			<fmt:message key="label.invoice"/>
	 		</td>
		</tr>
		

		<c:set var="checkedValue" value=''/>
		<c:if test="${costReportInputBean.invoiceType != null}">
			<c:set var="checkedValue" value='checked=true'/>
		</c:if>
					 <tr >
        <td width="15%" class="optionTitleBoldRight">
			<input name="invoiceType" id="invoiceType" type="checkbox" value="Yes" class="radioBtns" ${checkedValue}>
 		</td>
 		<td width="85%" class="optionTitleBoldLeft">
  			<fmt:message key="label.invoicetype"/>
 		</td>
			 </tr>

		<c:set var="checkedValue" value=''/>
		<c:if test="${costReportInputBean.invoiceDate != null}">
			<c:set var="checkedValue" value='checked=true'/>
		</c:if>
			 <tr>
				         <td width="15%" class="optionTitleBoldRight">
			<input name="invoiceDate" id="invoiceDate" type="checkbox" value="Yes" class="radioBtns" ${checkedValue}>
 		</td>
 		<td width="85%" class="optionTitleBoldLeft">
  			<fmt:message key="label.invoicedate"/>
 		</td>
			 </tr>

		<c:set var="checkedValue" value=''/>
		<c:if test="${costReportInputBean.invoicePeriod != null}">
			<c:set var="checkedValue" value='checked=true'/>
		</c:if>
			 <tr>
				  <td width="15%" class="optionTitleBoldRight">
			<input name="invoicePeriod" id="invoicePeriod" type="checkbox" value="Yes" class="radioBtns" ${checkedValue}>
 		</td>
 		<td width="85%" class="optionTitleBoldLeft">
  			<fmt:message key="label.invoiceperiod"/>
 		</td>
			 </tr>

		<c:set var="checkedValue" value=''/>
		<c:if test="${costReportInputBean.invoiceLine != null}">
			<c:set var="checkedValue" value='checked=true'/>
		</c:if>
			 <tr>
				         <td width="15%" class="optionTitleBoldRight">
			<input name="invoiceLine" id="invoiceLine" type="checkbox" value="Yes" class="radioBtns" ${checkedValue}>
 		</td>
 		<td width="85%" class="optionTitleBoldLeft">
  			<fmt:message key="label.invoiceline"/>
 		</td>
			 </tr>

		<c:set var="checkedValue" value=''/>
		<c:if test="${costReportInputBean.accountingSystem != null}">
			<c:set var="checkedValue" value='checked=true'/>
		</c:if>
			 <tr>
				  <td width="15%" class="optionTitleBoldRight">
			<input name="accountingSystem" id="accountingSystem" type="checkbox" value="Yes" class="radioBtns" ${checkedValue}>
 		</td>
 		<td width="85%" class="optionTitleBoldLeft">
  			<fmt:message key="label.accountingsystem"/>
 		</td>
			 </tr>

		<c:set var="checkedValue" value=''/>
		<c:if test="${costReportInputBean.chargeNumber1 != null}">
			<c:set var="checkedValue" value='checked=true'/>
		</c:if>
		<tr>
			<td width="15%" class="optionTitleBoldRight">
			<input name="chargeNumber1" id="chargeNumber1" type="checkbox" value="Yes" class="radioBtns" ${checkedValue}>
 		</td>
 		<td width="85%" class="optionTitleBoldLeft">
  			<fmt:message key="label.chargenumber1"/>
 		</td>
		</tr>

		<c:set var="checkedValue" value=''/>
		<c:if test="${costReportInputBean.chargeNumber2 != null}">
			<c:set var="checkedValue" value='checked=true'/>
		</c:if>
		<tr>
			<td width="15%" class="optionTitleBoldRight">
			<input name="chargeNumber2" id="chargeNumber2" type="checkbox" value="Yes" class="radioBtns" ${checkedValue}>
 		</td>
 		<td width="85%" class="optionTitleBoldLeft">
  			<fmt:message key="label.chargenumber2"/>
 		</td>
			 </tr>

		 <c:set var="checkedValue" value=''/>
		<c:if test="${costReportInputBean.chargeNumber3 != null}">
			<c:set var="checkedValue" value='checked=true'/>
		</c:if>
		<tr>
			<td width="15%" class="optionTitleBoldRight">
			<input name="chargeNumber3" id="chargeNumber3" type="checkbox" value="Yes" class="radioBtns" ${checkedValue}>
 		</td>
 		<td width="85%" class="optionTitleBoldLeft">
  			<fmt:message key="label.chargenumber3"/>
 		</td>
		</tr>

		 </table>
		 </td>

		<%--- left 1 table --%>
	   <td width="18%">
		 <table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableSearch">
		 <c:set var="checkedValue" value=''/>
		<c:if test="${costReportInputBean.chargeNumber4 != null}">
			<c:set var="checkedValue" value='checked=true'/>
		</c:if>
		<tr>
			<td width="15%" class="optionTitleBoldRight">
			<input name="chargeNumber4" id="chargeNumber4" type="checkbox" value="Yes" class="radioBtns" ${checkedValue}>
 		</td>
 		<td width="85%" class="optionTitleBoldLeft">
  			<fmt:message key="label.chargenumber4"/>
 		</td>
		</tr>

		<c:set var="checkedValue" value=''/>
		<c:if test="${costReportInputBean.userPo != null}">
			<c:set var="checkedValue" value='checked=true'/>
		</c:if>
		<tr>
			<td width="15%" class="optionTitleBoldRight">
			<input name="userPo" id="userPo" type="checkbox" value="Yes" class="radioBtns" ${checkedValue}>
 		</td>
 		<td width="85%" class="optionTitleBoldLeft">
  			<fmt:message key="label.userpo"/>
 		</td>
		</tr>

		<c:set var="checkedValue" value=''/>
		<c:if test="${costReportInputBean.splitCharge != null}">
			<c:set var="checkedValue" value='checked=true'/>
		</c:if>
		<tr>
	        <td width="15%" class="optionTitleBoldRight">
			<input name="splitCharge" id="splitCharge" type="checkbox" value="Yes" class="radioBtns" ${checkedValue}>
 		</td>
 		<td width="85%" class="optionTitleBoldLeft">
  			<fmt:message key="label.splitcharge"/>
 		</td>
	</tr>

		<c:set var="checkedValue" value=''/>
		<c:if test="${costReportInputBean.company != null}">
			<c:set var="checkedValue" value='checked=true'/>
		</c:if>
			 <tr >
        <td width="15%" class="optionTitleBoldRight">
			<input name="company" id="company" type="checkbox" value="Yes" class="radioBtns" ${checkedValue}>
 		</td>
 		<td width="85%" class="optionTitleBoldLeft">
  			<fmt:message key="label.company"/>
 		</td>
			 </tr>

		<c:set var="checkedValue" value=''/>
		<c:if test="${costReportInputBean.facility != null}">
			<c:set var="checkedValue" value='checked=true'/>
		</c:if>
			 <tr>
				         <td width="15%" class="optionTitleBoldRight">
			<input name="facility" id="facility" type="checkbox" value="Yes" class="radioBtns" ${checkedValue}>
 		</td>
 		<td width="85%" class="optionTitleBoldLeft">
  			<fmt:message key="label.facility"/>
 		</td>
			 </tr>

		<c:set var="checkedValue" value=''/>
		<c:if test="${costReportInputBean.workArea != null}">
			<c:set var="checkedValue" value='checked=true'/>
		</c:if>
				 <tr >
        <td width="15%" class="optionTitleBoldRight">
			<input name="workArea" id="workArea" type="checkbox" value="Yes" class="radioBtns" ${checkedValue}>
 		</td>
 		<td width="85%" class="optionTitleBoldLeft">
  			<fmt:message key="label.workarea"/>
 		</td>
 	  </tr>

		<c:set var="checkedValue" value=''/>
		<c:if test="${costReportInputBean.requestor != null}">
			<c:set var="checkedValue" value='checked=true'/>
		</c:if>
				<tr >
        <td width="5%" class="optionTitleBoldRight">
			<input name="requestor" id="requestor" type="checkbox" value="Yes" class="radioBtns" ${checkedValue}>
 		</td>
 		<td width="5%" class="optionTitleBoldLeft">
  			<fmt:message key="label.requestor"/>
 		</td>
 	  </tr>

		 <c:set var="checkedValue" value=''/>
		<c:if test="${costReportInputBean.financeApprover != null}">
			<c:set var="checkedValue" value='checked=true'/>
		</c:if>
	<tr>
		        <td width="5%" class="optionTitleBoldRight">
			<input name="financeApprover" id="financeApprover" type="checkbox" value="Yes" class="radioBtns" ${checkedValue}>
 		</td>
 		<td width="85%" class="optionTitleBoldLeft">
  			<fmt:message key="label.financeapprover"/>
 		</td>
	</tr>

		 <c:set var="checkedValue" value=''/>
		<c:if test="${costReportInputBean.mrLine != null}">
			<c:set var="checkedValue" value='checked=true'/>
		</c:if>
			  <tr>
        <td width="5%" class="optionTitleBoldRight">
			<input name="mrLine" id="mrLine" type="checkbox" value="Yes" class="radioBtns" ${checkedValue}>
 		</td>
 		<td width="85%" class="optionTitleBoldLeft">
  			<fmt:message key="label.mrline"/>
 		</td>
			 </tr>

		 </table>
		</td>

		 <%--- left 2 table --%>
	   <td width="18%">
		 <table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableSearch">

		 <c:set var="checkedValue" value=''/>
		<c:if test="${costReportInputBean.wasteOrder != null}">
			<c:set var="checkedValue" value='checked=true'/>
		</c:if>
			 <tr>
				         <td width="5%" class="optionTitleBoldRight">
			<input name="wasteOrder" id="wasteOrder" type="checkbox" value="Yes" class="radioBtns" ${checkedValue}>
 		</td>
 		<td width="85%" class="optionTitleBoldLeft">
  			<fmt:message key="label.wasteorder"/>
 		</td>
			 </tr>

		 <c:set var="checkedValue" value=''/>
		<c:if test="${costReportInputBean.wasteManifest != null}">
			<c:set var="checkedValue" value='checked=true'/>
		</c:if>
			 <tr>
				 <td width="5%" class="optionTitleBoldRight">
			<input name="wasteManifest" id="wasteManifest" type="checkbox" value="Yes" class="radioBtns" ${checkedValue}>
 		</td>
 		<td width="85%" class="optionTitleBoldLeft">
  			<fmt:message key="label.wastemanifest"/>
 		</td>
			 </tr>

		 <c:set var="checkedValue" value=''/>
		<c:if test="${costReportInputBean.partNumber != null}">
			<c:set var="checkedValue" value='checked=true'/>
		</c:if>
			 <tr>
				         <td width="5%" class="optionTitleBoldRight">
			<input name="partNumber" id="partNumber" type="checkbox" value="Yes" class="radioBtns" ${checkedValue}>
 		</td>
 		<td width="85%" class="optionTitleBoldLeft">
  			<fmt:message key="label.partnumber"/>
 		</td>
			 </tr>

		 <c:set var="checkedValue" value=''/>
		<c:if test="${costReportInputBean.type != null}">
			<c:set var="checkedValue" value='checked=true'/>
		</c:if>
				<tr>
				        <td width="5%" class="optionTitleBoldRight">
			<input name="type" id="type" type="checkbox" value="Yes" class="radioBtns" ${checkedValue}>
 		</td>
 		<td width="85%" class="optionTitleBoldLeft">
  			<fmt:message key="label.type"/>
 		</td>
			</tr>

		 <c:set var="checkedValue" value=''/>
		<c:if test="${costReportInputBean.partDescription != null}">
			<c:set var="checkedValue" value='checked=true'/>
		</c:if>
			 <tr>
				         <td width="5%" class="optionTitleBoldRight">
			<input name="partDescription" id="partDescription" type="checkbox" value="Yes" class="radioBtns" ${checkedValue}>
 		</td>
 		<td width="85%" class="optionTitleBoldLeft">
  			<fmt:message key="label.partdescription"/>
 		</td>
			 </tr>

		 <c:set var="checkedValue" value=''/>
		<c:if test="${costReportInputBean.packaging != null}">
			<c:set var="checkedValue" value='checked=true'/>
		</c:if>
			 <tr>
			         <td width="5%" class="optionTitleBoldRight">
			<input name="packaging" id="packaging" type="checkbox" value="Yes" class="radioBtns" ${checkedValue}>
 		</td>
 		<td width="85%" class="optionTitleBoldLeft">
  			<fmt:message key="label.packaging"/>
 		</td>
			 </tr>

        <c:if test='${!empty catPartAttributeHeader}'>
            <c:set var="checkedValue" value=''/>
        	<c:if test="${costReportInputBean.catPartAttribute != null}">
        		<c:set var="checkedValue" value='checked=true'/>
        	</c:if>
            <tr>
                <td width="5%" class="optionTitleBoldRight">
                    <input name="catPartAttribute" id="catPartAttribute" type="checkbox" value="Yes" class="radioBtns" ${checkedValue}>
                </td>
                <td width="85%" class="optionTitleBoldLeft">
                    ${catPartAttributeHeader}
                </td>
            </tr>
            <input type="hidden" name="catPartAttributeHeader" id="catPartAttributeHeader" value="${catPartAttributeHeader}"/>
        </c:if>

        <c:if test='${!empty qualityIdLabel}'>
            <c:set var="checkedValue" value=''/>
            <c:if test="${costReportInputBean.qualityId != null}">
                <c:set var="checkedValue" value='checked=true'/>
            </c:if>
            <tr>
                <td width="5%" class="optionTitleBoldRight">
                    <input name="qualityId" id="qualityId" type="checkbox" value="Yes" class="radioBtns" ${checkedValue}>
                </td>
                <td width="85%" class="optionTitleBoldLeft">
                    ${qualityIdLabel}
                </td>
                <input type="hidden" name="qualityIdLabel" id="qualityIdLabel" value="${qualityIdLabel}"/>
            </tr>
        </c:if>

        <c:set var="checkedValue" value=''/>
		<c:if test="${costReportInputBean.item != null}">
			<c:set var="checkedValue" value='checked=true'/>
		</c:if>
			 <tr>
				         <td width="5%" class="optionTitleBoldRight">
			<input name="item" id="item" type="checkbox" value="Yes" class="radioBtns" ${checkedValue}>
 		</td>
 		<td width="85%" class="optionTitleBoldLeft">
  			<fmt:message key="label.item"/>
 		</td>
			 </tr>

		 <c:set var="checkedValue" value=''/>
		<c:if test="${costReportInputBean.itemDescription != null}">
			<c:set var="checkedValue" value='checked=true'/>
		</c:if>
			 <tr>
			   <td width="5%" class="optionTitleBoldRight">
			<input name="itemDescription" id="itemDescription" type="checkbox" value="Yes" class="radioBtns" ${checkedValue}>
 		</td>
 		<td width="85%" class="optionTitleBoldLeft">
  			<fmt:message key="label.itemdescription"/>
 		</td>
			 </tr>

		 <c:set var="checkedValue" value=''/>
		<c:if test="${costReportInputBean.manufacturer != null}">
			<c:set var="checkedValue" value='checked=true'/>
		</c:if>
		<tr>
			<td width="5%" class="optionTitleBoldRight">
			<input name="manufacturer" id="manufacturer" type="checkbox" value="Yes" class="radioBtns" ${checkedValue}>
 		</td>
 		<td width="85%" class="optionTitleBoldLeft">
  			<fmt:message key="label.manufacturer"/>
 		</td>
		</tr>

		 </table>
		 </td>

		<%--- left 3 table --%>
	   <td width="24%">
		 <table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableSearch">
		 <c:set var="checkedValue" value=''/>
		<c:if test="${costReportInputBean.haasPo != null}">
			<c:set var="checkedValue" value='checked=true'/>
		</c:if>
			 <tr>
				         <td width="5%" class="optionTitleBoldRight">
			<input name="haasPo" id="haasPo" type="checkbox" value="Yes" class="radioBtns" ${checkedValue}>
 		</td>
 		<td width="85%" class="optionTitleBoldLeft">
  			<fmt:message key="label.haaspo"/>
 		</td>
			 </tr>

		 <c:set var="checkedValue" value=''/>
		<c:if test="${costReportInputBean.reference != null}">
			<c:set var="checkedValue" value='checked=true'/>
		</c:if>
				  <tr>
			 <td width="5%" class="optionTitleBoldRight">
			<input name="reference" id="reference" type="checkbox" value="Yes" class="radioBtns" ${checkedValue}>
 		</td>
 		<td width="85%" class="optionTitleBoldLeft">
  			<fmt:message key="label.reference"/>
 		</td>
 	  </tr>

		 <c:set var="checkedValue" value=''/>
		<c:if test="${costReportInputBean.receiptId != null}">
			<c:set var="checkedValue" value='checked=true'/>
		</c:if>
			 <tr>
			         <td width="5%" class="optionTitleBoldRight">
			<input name="receiptId" id="receiptId" type="checkbox" value="Yes" class="radioBtns" ${checkedValue}>
 		</td>
 		<td width="85%" class="optionTitleBoldLeft">
  			<fmt:message key="label.receiptid"/>
 		</td>
 	  </tr>

		 <c:set var="checkedValue" value=''/>
		<c:if test="${costReportInputBean.mfgLot != null}">
			<c:set var="checkedValue" value='checked=true'/>
		</c:if>
					 <tr >
        <td width="5%" class="optionTitleBoldRight">
			<input name="mfgLot" id="mfgLot" type="checkbox" value="Yes" class="radioBtns" ${checkedValue}>
 		</td>
 		<td width="85%" class="optionTitleBoldLeft">
  			<fmt:message key="label.mfglot"/>
 		</td>
 	  </tr>
 	  
 	  <c:set var="checkedValue" value=''/>
		<c:if test="${costReportInputBean.customerPartNo != null}">
			<c:set var="checkedValue" value='checked=true'/>
		</c:if>
		<tr>
			<td width="5%" class="optionTitleBoldRight">
				<input name="customerPartNo" id="customerPartNo" type="checkbox" value="Yes" class="radioBtns" ${checkedValue} >
			</td>
			<td width="5%" class="optionTitleBoldLeft">
				<fmt:message key="label.customerpartnumber"/>
			</td>
		</tr>

		<c:set var="checkedValue" value=''/>
		<c:if test="${costReportInputBean.shippingReference != null}">
			<c:set var="checkedValue" value='checked=true'/>
		</c:if>
		<tr>
			<td width="5%" class="optionTitleBoldRight">
				<input name="shippingReference" id="shippingReference" type="checkbox" value="Yes" class="radioBtns" ${checkedValue} >
			</td>
			<td width="5%" class="optionTitleBoldLeft">
				<fmt:message key="label.shippingreferenece"/>
			</td>
		</tr>

		<c:set var="checkedValue" value=''/>
		<c:if test="${costReportInputBean.dateDelivered != null}">
			<c:set var="checkedValue" value='checked=true'/>
		</c:if>
	  <tr >
        <td width="5%" class="optionTitleBoldRight">
			<input name="dateDelivered" id="dateDelivered" type="checkbox" value="Yes" class="radioBtns" ${checkedValue}>
 		</td>
 		<td width="85%" class="optionTitleBoldLeft">
  			<fmt:message key="label.datedelivered"/> (<fmt:message key="label.groupby"/>:
 		</td>
 	  </tr>

		<c:set var="checkedValueDay" value=''/>
		<c:set var="checkedValueMonth" value=''/>
		<c:set var="checkedValueYear" value=''/>
		<c:if test="${costReportInputBean.dateDeliveredGroupBy == null}">
			<c:set var="checkedValueDay" value='checked=true'/>
		</c:if>
		<c:if test="${costReportInputBean.dateDeliveredGroupBy == 'Day'}">
			<c:set var="checkedValueDay" value='checked=true'/>
		</c:if>
		<c:if test="${costReportInputBean.dateDeliveredGroupBy == 'Month'}">
			<c:set var="checkedValueMonth" value='checked=true'/>
		</c:if>
		<c:if test="${costReportInputBean.dateDeliveredGroupBy == 'Year'}">
			<c:set var="checkedValueYear" value='checked=true'/>
		</c:if>
					 <tr >
        <td width="5%" >
 		</td>
        <td width="30%" class="optionTitleBoldLeft" nowrap>
			<input name="dateDeliveredGroupBy" id="byDay" type="radio" value="Day" class="radioBtns" ${checkedValueDay}>
  			<fmt:message key="label.day"/>
			<input name="dateDeliveredGroupBy" id="byMonth" type="radio" value="Month" class="radioBtns" ${checkedValueMonth}>
  			<fmt:message key="label.month"/>
			<input name="dateDeliveredGroupBy" id="byYear" type="radio" value="Year" class="radioBtns" ${checkedValueYear}>
  			<fmt:message key="label.year"/>)
 		</td>
 	  </tr>
 	  <c:set var="checkedValue" value=''/>
		<c:if test="${costReportInputBean.quantity != null}">
			<c:set var="checkedValue" value='checked=true'/>
		</c:if>
	  <tr >
        <td width="5%" class="optionTitleBoldRight">
			<input name="quantity" id="quantity" type="checkbox" value="Yes" class="radioBtns" ${checkedValue}>
 		</td>
 		<td width="85%" class="optionTitleBoldLeft">
  			<fmt:message key="label.quantity"/>
 		</td>
 	  </tr>


		 </table>
		</td>

		 <%--- right table --%>
	   <td width="20%">
		 <table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableSearch">
		

		<c:set var="checkedValue" value=''/>
		<c:if test="${costReportInputBean.invoiceUnitPrice != null}">
			<c:set var="checkedValue" value='checked=true'/>
		</c:if>
		<tr >
        <td width="5%" class="optionTitleBoldRight">
			<input name="invoiceUnitPrice" id="invoiceUnitPrice" type="checkbox" value="Yes" class="radioBtns" ${checkedValue}>
 		</td>
 		<td width="85%" class="optionTitleBoldLeft">
  			<fmt:message key="label.unitprice"/>
 		</td>
 	  </tr>

		<c:set var="checkedValue" value=''/>
		<c:if test="${costReportInputBean.unitRebate != null}">
			<c:set var="checkedValue" value='checked=true'/>
		</c:if>
		<tr >
        <td width="5%" class="optionTitleBoldRight">
			<input name="unitRebate" id="unitRebate" type="checkbox" value="Yes" class="radioBtns" ${checkedValue}>
 		</td>
 		<td width="85%" class="optionTitleBoldLeft">
  			<fmt:message key="label.unitrebate"/>
 		</td>
 	  </tr>
		
		
		<c:set var="checkedValue" value=''/>
		<c:if test="${costReportInputBean.totalFreightCharge != null}">
			<c:set var="checkedValue" value='checked=true'/>
		</c:if>
		<tr >
        <td width="5%" class="optionTitleBoldRight">
			<input name="totalFreightCharge" id="totalFreightCharge" type="checkbox" value="Yes" class="radioBtns" ${checkedValue}>
 		</td>
 		<td width="85%" class="optionTitleBoldLeft">
  			<fmt:message key="label.totalfreightcharge"/>
 		</td>
 	   </tr>

		 <c:set var="checkedValue" value=''/>
		<c:if test="${costReportInputBean.totalAdditionalCharge != null}">
			<c:set var="checkedValue" value='checked=true'/>
		</c:if>
				<tr >
        <td width="5%" class="optionTitleBoldRight">
			<input name="totalAdditionalCharge" id="totalAdditionalCharge" type="checkbox" value="Yes" class="radioBtns" ${checkedValue}>
 		</td>
 		<td width="85%" class="optionTitleBoldLeft">
  			<fmt:message key="label.totaladditionalcharge"/>
 		</td>
 	  </tr>

		 <c:set var="checkedValue" value=''/>
		<c:if test="${costReportInputBean.totalSalesTax != null}">
			<c:set var="checkedValue" value='checked=true'/>
		</c:if>
				<tr >
        <td width="5%" class="optionTitleBoldRight">
			<input name="totalSalesTax" id="totalSalesTax" type="checkbox" value="Yes" class="radioBtns" ${checkedValue}>
 		</td>
 		<td width="85%" class="optionTitleBoldLeft">
  			<fmt:message key="label.totalsalestax"/>
 		</td>
 	  </tr>

		<c:set var="checkedValue" value=''/>
		<c:if test="${costReportInputBean.serviceFee != null}">
			<c:set var="checkedValue" value='checked=true'/>
		</c:if>
		<tr >
        <td width="5%" class="optionTitleBoldRight">
			<input name="serviceFee" id="serviceFee" type="checkbox" value="Yes" class="radioBtns" ${checkedValue}>
 		</td>
 		<td width="85%" class="optionTitleBoldLeft">
  			<fmt:message key="label.servicefee"/>
 		</td>
 	  </tr>

		<c:set var="checkedValue" value=''/>
		<c:if test="${costReportInputBean.peiAmount != null}">
			<c:set var="checkedValue" value='checked=true'/>
		</c:if>
		<tr >
        <td width="5%" class="optionTitleBoldRight">
			<input name="peiAmount" id="peiAmount" type="checkbox" value="Yes" class="radioBtns" ${checkedValue}>
 		</td>
 		<td width="85%" class="optionTitleBoldLeft">
  			<fmt:message key="label.peiamount"/>
 		</td>
 	  </tr>

		<c:set var="checkedValue" value=''/>
		<c:if test="${costReportInputBean.netAmount != null}">
			<c:set var="checkedValue" value='checked=true'/>
		</c:if>
		<tr>
			<td width="5%" class="optionTitleBoldRight">
			<input name="netAmount" id="netAmount" type="checkbox" value="Yes" class="radioBtns" ${checkedValue} >
 		</td>
 		<td width="85%" class="optionTitleBoldLeft">
  			<fmt:message key="label.netamount"/>
 		</td>
		 </tr>

		<c:set var="checkedValue" value=''/>
		<c:if test="${costReportInputBean.materialSavings != null}">
			<c:set var="checkedValue" value='checked=true'/>
		</c:if>
		<tr> 
		<td width="5%" class="optionTitleBoldRight">
			<input name="materialSavings" id="materialSavings" type="checkbox" value="Yes" class="radioBtns" ${checkedValue} >
 		</td>
 		<td width="5%" class="optionTitleBoldLeft">
  			<fmt:message key="label.materialsavings"/>
 		</td>
		 </tr>

		 </table>
		 </td>
	 </tr>
	</table>
  <%-- end of bottom table --%>


    <table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableSearch">
		<tr>
			<td colspan="2">&nbsp;</td>
		</tr>
		<tr >
        <td width="70%" class="optionTitleRight">
			<input name="clearTemplate" id="clearTemplate" type="button" value="<fmt:message key="label.cleartemplate"/>" class="inputBtns" onmouseover="this.className='inputBtns inputBtnsOver'" onmouseout="this.className='inputBtns'" onclick="callClearTemplate()">
			<%--
			<input name="openTemplate" id="openTemplate" type="button" value="<fmt:message key="label.opentemplate"/>" class="inputBtns" onmouseover="this.className='inputBtns inputBtnsOver'" onmouseout="this.className='inputBtns'" onclick="getTemplate()">
			<input name="saveTemplate" id="saveTemplate" type="button" value="<fmt:message key="label.savetemplate"/>" class="inputBtns" onmouseover="this.className='inputBtns inputBtnsOver'" onmouseout="this.className='inputBtns'" onclick="callSaveTemplate()">
			--%>
			<input name="openTemplate" id="openTemplate" type="button" value="<fmt:message key="label.opentemplate"/>" class="inputBtns" onmouseover="this.className='inputBtns inputBtnsOver'" onmouseout="this.className='inputBtns'" onclick="openTemplateAudit()">
			<input name="saveTemplate" id="saveTemplate" type="button" value="<fmt:message key="label.savetemplate"/>" class="inputBtns" onmouseover="this.className='inputBtns inputBtnsOver'" onmouseout="this.className='inputBtns'" onclick="saveTemplateAudit()">
			<c:if test="${hasPublishPermission == 'Y'}">
				<input name="publishTemplate" id="publishTemplate" type="button" value="<fmt:message key="label.publishtemplate"/>" class="inputBtns" onmouseover="this.className='inputBtns inputBtnsOver'" onmouseout="this.className='inputBtns'" onclick="publishTemplateAudit()">
			</c:if>
			<c:if test="${hasUnpublishPermission == 'Y'}">
				<input name="unpublishTemplate" id="unpublishTemplate" type="button" value="<fmt:message key="label.unpublishtemplate"/>" class="inputBtns" onmouseover="this.className='inputBtns inputBtnsOver'" onmouseout="this.className='inputBtns'" onclick="unpublishTemplateAudit()">
			</c:if>
			<input name="generateReportB" id="generateReportB" type="button" value="<fmt:message key="label.generatereport"/>" class="inputBtns" onmouseover="this.className='inputBtns inputBtnsOver'" onmouseout="this.className='inputBtns'" onClick="generateReport()">

			</td>
			<td width="30%" class="optionTitleBoldLeft">
				<fmt:message key="label.reportformat"/>:&nbsp;
				<c:set var="checkedValueHtml" value=''/>
				<c:set var="checkedValueExcel" value=''/>
				<c:if test="${costReportInputBean.reportType == null}">
					<c:set var="checkedValueHtml" value='checked=true'/>
				</c:if>
				<c:if test="${costReportInputBean.reportType == 'html'}">
					<c:set var="checkedValueHtml" value='checked=true'/>
				</c:if>
				<c:if test="${costReportInputBean.reportType == 'excel'}">
					<c:set var="checkedValueExcel" value='checked=true'/>
				</c:if>
				<input name="reportType" id="byHtml" type="radio" value="html" class="radioBtns" ${checkedValueHtml}>
				<fmt:message key="label.html"/>
				<input name="reportType" id="byExcel" type="radio" value="excel" class="radioBtns" ${checkedValueExcel}>
				<fmt:message key="label.excel"/>
			</td>
		</tr>

		 <%--
	  <tr>
			<c:set var="checkedValueHtml" value=''/>
			<c:set var="checkedValueExcel" value=''/>
			<c:if test="${costReportInputBean.reportType == null}">
				<c:set var="checkedValueHtml" value='checked=true'/>
			</c:if>
			<c:if test="${costReportInputBean.reportType == 'html'}">
				<c:set var="checkedValueHtml" value='checked=true'/>
			</c:if>
			<c:if test="${costReportInputBean.reportType == 'excel'}">
				<c:set var="checkedValueExcel" value='checked=true'/>
			</c:if>
		  <td width="30%" class="optionTitleBoldLeft">
			<input name="reportType" id="byHtml" type="radio" value="html" class="radioBtns" ${checkedValueHtml}>
  			<fmt:message key="label.html"/>
			<input name="reportType" id="byExcel" type="radio" value="excel" class="radioBtns" ${checkedValueExcel}>
  			<fmt:message key="label.excel"/>
		  </td>
	  </tr> --%>

	 </table>
    
    <!-- Search Option Table end -->
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
<!-- Build this section only if there is an error message to display.
     For the search section, we show the error messages within its frame
-->
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
<input type="hidden" name="action" id="action" value=""/>
<input type="hidden" name="requestorId" id="requestorId" value="${costReportInputBean.requestorId}"/>
<input type="hidden" name="companyName" id="companyName" value=""/>
<input type="hidden" name="selectedCompanyId" id="selectedCompanyId" value="${costReportInputBean.companyId}"/>
<input type="hidden" name="selectedReportingGroup" id="selectedReportingGroup" value="${costReportInputBean.reportingGroup}"/>	
<input type="hidden" name="facilityName" id="facilityName" value=""/>
<input type="hidden" name="selectedFacilityId" id="selectedFacilityId" value="${costReportInputBean.facilityId}"/>
<input type="hidden" name="applicationName" id="applicationName" value=""/>
<input type="hidden" name="selectedApplication" id="selectedApplication" value="${costReportInputBean.application}"/>
<input type="hidden" name="accountSysName" id="accountSysName" value=""/>
<input type="hidden" name="selectedAccountSysId" id="selectedAccountSysId" value="${costReportInputBean.accountSysId}"/>	
<input type="hidden" name="searchByName" id="searchByName" value=""/>
<input type="hidden" name="itemTypeName" id="itemTypeName" value=""/>
<input type="hidden" name="invoiceTypeName" id="invoiceTypeName" value=""/>
<input type="hidden" name="templateName" id="templateName" value="${costReportInputBean.templateName}"/>
<input type="hidden" name="whereClauseForLink" id="whereClauseForLink" value=""/>
<input type="hidden" name="uomName" id="uomName" value=""/>
<input type="hidden" name="templateDescription" id="templateDescription" value=""/>
<input type="hidden" name="templateId" id="templateId" value="${costReportInputBean.templateId}"/>
<input type="hidden" name="globalizationLabelLetter" id="globalizationLabelLetter" value="${globalLabelLetter}"/>
</div>
<!-- Hidden elements end -->

</div> <!-- close of interface -->

</tcmis:form>
</body>
</html:html>