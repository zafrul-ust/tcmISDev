<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
	"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html;charset=UTF-8" language="java"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="/WEB-INF/tcmis.tld" prefix="tcmis" %>

<html>
<head>
<meta http-equiv="content-type" content="text/html; charset=UTF-8">
<meta http-equiv="expires" content="-1">
<link rel="shortcut icon" href="/images/buttons/tcmIS.ico"></link>

<%@ include file="/common/locale.jsp" %>
<!--Use this tag to get the correct CSS class.
This looks at what the user's preffered font size and which application he is viewing to set the correct CSS. -->
<tcmis:gridFontSizeCss />
<%-- Add any other stylesheets you need for the page here --%>

<script type="text/javascript" src="/js/common/formchek.js"></script>
<script type="text/javascript" src="/js/common/commonutil.js"></script>
<!-- This handles all the resizing of the page and frames -->
<%--NEW--%>
<script type="text/javascript" src="/js/common/grid/nosearchresize.js"></script>
<!-- This handels which key press events are disabeled on this page -->
<script type="text/javascript" src="/js/common/disableKeys.js"></script>

<!-- This handels the menu style and what happens to the right click on the whole page -->
<script type="text/javascript" src="/js/menu/milonic_src.js"></script>
<script type="text/javascript" src="/js/menu/mmenudom.js"></script>
<script type="text/javascript" src="/js/menu/mainmenudata.js"></script>
<script type="text/javascript" src="/js/menu/contextmenu.js"></script>
<%@ include file="/common/rightclickmenudata.jsp" %>


<!-- Add any other javascript you need for the page here -->
<script type="text/javascript" src="/js/common/noSearchStandardGridmain.js"></script>
<script type="text/javascript" src="/js/distribution/inputaddress.js"></script>

<script type="text/javascript">
<!--

var shipToCountryAbbrev = new Array(
		<c:forEach var="vvCountryBean" items="${vvCountryBeanCollection}" varStatus="status">
		 <c:if test="${status.index > 0}">,</c:if>
			{text:'<tcmis:jsReplace value="${status.current.country}"/>',value:'${status.current.countryAbbrev}'}
		</c:forEach>
		);

var shipToStateAbbrev = new Array();

var altCountryId = new Array(
<c:forEach var="vvCountryBean" items="${vvCountryBeanCollection}" varStatus="status">
 <c:choose>
   <c:when test="${status.index > 0}">
    ,'${status.current.countryAbbrev}'
   </c:when>
   <c:otherwise>
    '${status.current.countryAbbrev}'
   </c:otherwise>
  </c:choose>
</c:forEach>
);

var altState = new Array();
var altStateName = new Array();
<c:forEach var="vvCountryBean" items="${vvCountryBeanCollection}" varStatus="status">
  <c:set var="currentCountry" value='${status.current.countryAbbrev}'/>
  <c:set var="stateCollection" value='${status.current.stateCollection}'/>

  altState["${currentCountry}"] = new Array(
  <c:forEach var="vvStateBean" items="${stateCollection}" varStatus="status1">
 <c:choose>
   <c:when test="${status1.index > 0}">
        ,'<tcmis:jsReplace value="${status1.current.stateAbbrev}"/>'
   </c:when>
   <c:otherwise>
	   '<tcmis:jsReplace value="${status1.current.stateAbbrev}"/>'
   </c:otherwise>
  </c:choose>
  </c:forEach>
  );

  altStateName["${currentCountry}"] = new Array(
  <c:forEach var="vvStateBean" items="${stateCollection}" varStatus="status1">
 <c:choose>
   <c:when test="${status1.index > 0}">
        ,'<tcmis:jsReplace value="${status1.current.state}"/>'
   </c:when>
   <c:otherwise>
	   '<tcmis:jsReplace value="${status1.current.state}"/>'
   </c:otherwise>
  </c:choose>
  </c:forEach>
  );
</c:forEach>


/*Check if there is any error messages to show and set the variable you use in javascript to true or false.*/
<c:choose>
   <c:when test="${ empty tcmISError }">
    showErrorMessage = false;
   </c:when>
   <c:otherwise>
    showErrorMessage = true;
   </c:otherwise>
</c:choose>
<c:choose>
   <c:when test="${done == 'Y'}">
    done = true;
   </c:when>
   <c:otherwise>
    done = false;
   </c:otherwise>
</c:choose>

//-->
</script>

<!-- These are for the Grid, uncomment if you are going to use the grid -->
<%--NEW - dhtmlX grid files--%>
<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxcommon.js"></script>
<script type="text/javascript" src="/dhtmlxWindows/codebase/dhtmlxwindows.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxgrid.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxgridcell.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_form.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_srnd.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_json.js"></script>
<%--Uncomment below if you are providing header menu to switch columns on/off--%>
<!--<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_hmenu.js"></script>-->
<%--Uncomment the below if your grid has rwospans >1--%>
<!--<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_rowspan.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/rowspan_cell_patch.js"></script>
-->
<%--This has the custom cells we built,
    hcal - the internationalized calendar which we use
    hlink - this is for any links you want tp provide in the grid, the URL/function to call
           can be attached based on a event (rowselect etc)
    hed -editable sinlge line text
    hcoro -select drop down
    hch -checkbox
--%>
<script type="text/javascript" src="/dhtmlxGrid/codebase/excells/dhtmlxgrid_excell_customized.js"></script>
<%--Custom sorting.
This custom sorting function implements case insensitive sorting.
--%>
<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxcommon_haas.js"></script>
    
<title>
<fmt:message key="creditreview.title"/>
</title>


<script language="JavaScript" type="text/javascript">
<!--
var messagesData = new Array();
messagesData={alert:"<fmt:message key="label.alert"/>",and:"<fmt:message key="label.and"/>",
submitOnlyOnce:"<fmt:message key="label.submitOnlyOnce"/>",
validvalues:"<fmt:message key="label.validvalues"/>",
countryRequired:"<fmt:message key="label.country"/>",
addressRequired:"<fmt:message key="label.address"/>",
cityRequired:"<fmt:message key="label.city"/>",
stateRequired:"<fmt:message key="label.state"/>",
stateProvinceRequired:"<fmt:message key="label.state"/>/<fmt:message key="label.region"/>",
zipRequired:"<fmt:message key="label.postalcode"/>",
all:"<fmt:message key="label.all"/>",
shipto:"<fmt:message key="label.shipto"/> ",
mustbeanumber:"<fmt:message key="label.mustbeanumberinthisfield"/>",
pleaseselect:"<fmt:message key="label.pleaseselect"/>",
addressline1:"<fmt:message key="label.addressline1"/>",
fulladdressnozip:"<fmt:message key="label.fulladdressnozip"/>",
fulladdressnocity:"<fmt:message key="label.fulladdressnocity"/>",
nodatafound:"<fmt:message key="main.nodatafound"/>"
};

function submitReview() {

  loc = "creditreviewdetails.do?customerId="+$v("customerId")+"&opsEntityId="+$v('opsEntityId');

  var winId= 'customerReview'+$v("customerId");
//  showTransitWin("");
//  children[children.length] = 
    openWinGeneric(loc,winId,"900","600","yes","50","50","20","20","no");
}

// -->
</script>
</head>

<body bgcolor="#ffffff"  onload="myOnload();" onunload="try{opener.closeTransitWin();}catch(ex){}" onresize="resizeFrames()">

<tcmis:form action="/creditreview.do" onsubmit="return submitFrameOnlyOnce();">

<div class="interface" id="mainPage" style="">

<!-- Error Messages Begins -->

<div id="errorMessagesAreaBody" style="display:none;">
${tcmISError}
</div>
<!-- Error Messages Ends -->

<script type="text/javascript">
<!--
/*Check if there is any error messages to show and set the variable you use in javascript to true or false.*/
<c:choose>
   <c:when test="${ empty tcmISError }">
    showErrorMessage = false;
   </c:when>
   <c:otherwise>
    showErrorMessage = true;
   </c:otherwise>
</c:choose>
//-->
</script>

<!-- Result Frame Begins -->
<div id="resultFrameDiv" style="margin: 4px 4px 0px 4px;">

<%--NEw -Transit Page Starts --%>
<div id="transitPage" style="display: none;" class="optionTitleBoldCenter">
  <br><br><br><fmt:message key="label.pleasewait"/>
  <br><br><br><img src="/images/rel_interstitial_loading.gif" align="middle">
</div>
<!-- Transit Page Ends -->

<div id="resultGridDiv" style="display:">
<!-- Search results start -->
<!-- If you want your results section to span only 50% set this to 50% and your main table will be 100% -->
<table id="resultsMaskTable" width="100%" border="0" cellpadding="0" cellspacing="0">
<tr><td>
<div class="roundcont contentContainer">
<div class="roundright">
  <div class="roundtop">
    <div class="roundtopright"> <img src="/images/rndBoxes/borderTL.gif" alt="" width="15" height="15" class="corner" style="display: none" /></div>
  </div>
  <div class="roundContent">
    <div class="boxhead"> <%-- boxhead Begins --%>
     
    </div> <%-- boxhead Ends --%>

<div class="dataContent">

 <table width="100%" border="0" cellpadding="0" cellspacing="7" class="tableSearch">
      <tr>
        <td width="33%" align="left" nowrap><fmt:message key="label.outstandingar"/>:</td>
        <td width="33%" class="optionTitleBoldRight">
          <fmt:formatNumber var="openArAmount" value="${customerCreditColl.openArAmount}"  pattern="${unitpricecurrencyformat}"></fmt:formatNumber>	
          <c:if test="${! empty customerCreditColl.openArAmount}">${customerCreditColl.currencyId} <tcmis:replace value="${openArAmount}" from=".0000" to=""/></c:if>
        </td>
        <td width="4%">&nbsp;</td>
<%--         <td width="34%"><fmt:message key="label.gbp"/></td> --%>
      </tr>
      <tr>
        <td width="33%" align="left" nowrap><fmt:message key="label.openorders"/>:</td>
        <td width="33%" class="optionTitleBoldRight">
          <fmt:formatNumber var="openOrderAmount" value="${customerCreditColl.openOrderAmount}"  pattern="${unitpricecurrencyformat}"></fmt:formatNumber>	
          <c:if test="${! empty customerCreditColl.openOrderAmount}">${customerCreditColl.currencyId} <tcmis:replace value="${openOrderAmount}" from=".0000" to=""/></c:if>
        </td>
        <td width="4%">&nbsp;</td>
      </tr>
      <tr>
        <td width="33%" align="left" nowrap><fmt:message key="label.creditlimit"/>:</td>
        <td width="33%" class="optionTitleBoldRight">
          <fmt:formatNumber var="creditLimit" value="${customerCreditColl.creditLimit}"  pattern="${unitpricecurrencyformat}"></fmt:formatNumber>	
          <c:if test="${! empty customerCreditColl.creditLimit}">${customerCreditColl.currencyId} <tcmis:replace value="${creditLimit}" from=".0000" to=""/></c:if>
        </td>
        <td width="4%">&nbsp;</td>
      </tr>
      <tr>
        <td width="33%" align="left" nowrap><fmt:message key="label.availablecredit"/>:</td>
        <td width="33%" class="optionTitleBoldRight" nowrap>
          <fmt:formatNumber var="availableCredit" value="${customerCreditColl.availableCredit}"  pattern="${unitpricecurrencyformat}"></fmt:formatNumber>	
          <c:if test="${! empty customerCreditColl.availableCredit}">${customerCreditColl.currencyId} <tcmis:replace value="${availableCredit}" from=".0000" to=""/></c:if>
        </td>
        <td width="4%">&nbsp;</td>
      </tr>
      <tr>
        <td width="33%" align="left" nowrap><fmt:message key="label.graceperiod"/>:</td>
        <td width="33%" class="optionTitleBoldLeft">
          ${customerCreditColl.gracePeriodDays} <fmt:message key="label.days"/>
        </td>
        <td width="4%">&nbsp;</td>
      </tr>
      <tr>
        <td width="33%" align="left" nowrap><fmt:message key="label.greatestoverduedays"/>:</td>
        <td width="33%" class="optionTitleBoldLeft">
          ${customerCreditColl.greatestOverdueDays}
        </td>
        <td width="4%">&nbsp;</td>
      </tr>
      <tr>
        <td width="33%" align="left" nowrap><fmt:message key="label.amtpastdue0-30"/>:</td>
        <td width="33%" class="optionTitleBoldRight">
          <fmt:formatNumber var="pastDueAmount030Days" value="${customerCreditColl.pastDueAmount030Days}"  pattern="${unitpricecurrencyformat}"></fmt:formatNumber>	
          <c:if test="${! empty customerCreditColl.pastDueAmount030Days}">${customerCreditColl.currencyId} <tcmis:replace value="${pastDueAmount030Days}" from=".0000" to=""/></c:if>
        </td>
        <td width="4%">&nbsp;</td>
      </tr>
      <tr>
        <td width="33%" align="left" nowrap><fmt:message key="label.amtpastdue31-60"/>:</td>
        <td width="33%" class="optionTitleBoldRight">
          <fmt:formatNumber var="pastDueAmount3160Days" value="${customerCreditColl.pastDueAmount3160Days}"  pattern="${unitpricecurrencyformat}"></fmt:formatNumber>	
          <c:if test="${! empty customerCreditColl.pastDueAmount3160Days}">${customerCreditColl.currencyId} <tcmis:replace value="${pastDueAmount3160Days}" from=".0000" to=""/></c:if>
        </td>
        <td width="4%">&nbsp;</td>
      </tr>
      <tr>
        <td width="33%" align="left" nowrap><fmt:message key="label.amtpastdue61-90"/>:</td>
        <td width="33%" class="optionTitleBoldRight">
          <fmt:formatNumber var="pastDueAmount6190Days" value="${customerCreditColl.pastDueAmount6190Days}"  pattern="${unitpricecurrencyformat}"></fmt:formatNumber>	
          <c:if test="${! empty customerCreditColl.pastDueAmount6190Days}">${customerCreditColl.currencyId} <tcmis:replace value="${pastDueAmount6190Days}" from=".0000" to=""/></c:if>
        </td>
        <td width="4%">&nbsp;</td>
      </tr>
      <tr>
        <td width="33%" align="left" nowrap><fmt:message key="label.amtpastdue>90"/>:</td>
        <td width="33%" class="optionTitleBoldRight">
          <fmt:formatNumber var="pastDueAmount90Days" value="${customerCreditColl.pastDueAmount90Days}"  pattern="${unitpricecurrencyformat}"></fmt:formatNumber>	
          <c:if test="${! empty customerCreditColl.pastDueAmount90Days}">${customerCreditColl.currencyId} <tcmis:replace value="${pastDueAmount90Days}" from=".0000" to=""/></c:if>
        </td>
        <td width="4%">&nbsp;</td>
      </tr>
      <tr>
      	<td colspan="2">
      		<input name="review" id="review" type="submit" value="<fmt:message key="label.review"/>" class="inputBtns" 
				onmouseover="this.className='inputBtns inputBtnsOver'" onmouseout="this.className='inputBtns'" 
				onclick="submitReview();">
		</td>
	  </tr>
    </table>



</div>


  </div>
  <div class="roundbottom">
    <div class="roundbottomright"> <img src="/images/rndBoxes/borderBL.gif" alt="" width="15" height="15" class="corner" style="display: none" /> </div>
  </div>
</div>
</div>
</td></tr>
</table>
</div>

</div><!-- Result Frame Ends -->

<!-- Hidden element start -->
<div id="hiddenElements" style="display: none;">
<input type="hidden" name="uAction" id="uAction" value="${param.uAction}">
<input type="hidden" name="customerId" id="customerId" value="${param.customerId}">
<input type="hidden" name="opsEntityId" id="opsEntityId" value="${param.opsEntityId}">
</div>
<!-- Hidden elements end -->

</div> <!-- close of interface -->

</tcmis:form>

<!-- You can build your error messages below. Similar divs would have to be built to show any other messages in a inline pop-up.-->
<!-- Error Messages Begins -->
<div id="errorMessagesArea" class="errorMessages" style="display: none;overflow: auto;">

</div>

<c:if test="${applicationPermissionAll == 'Y'}">
 <script type="text/javascript">
 <!--
  showUpdateLinks = true;
 //-->
 </script>
</c:if>

</body>
</html>