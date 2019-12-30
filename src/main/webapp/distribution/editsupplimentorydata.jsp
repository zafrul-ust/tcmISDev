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
<%@ include file="/common/opshubig.jsp" %>

<!-- Add any other javascript you need for the page here -->
<script type="text/javascript" src="/js/common/standardGridmain.js"></script>
<script type="text/javascript" src="/js/common/opshubig.js"></script>
<script type="text/javascript" src="/js/distribution/editsupplimentarydata.js"></script>

<!-- For Calendar support -->
<script type="text/javascript" src="/js/calendar/newcalendar.js"></script>
<script type="text/javascript" src="/js/calendar/AnchorPosition.js"></script>
<script type="text/javascript" src="/js/calendar/PopupWindow.js"></script>

<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxcommon.js"></script>
<script type="text/javascript" src="/dhtmlxLayout/codebase/dhtmlxlayout.js"></script>
<script type="text/javascript" src="/dhtmlxWindows/codebase/dhtmlxwindows.js"></script>
<title>
<fmt:message key="label.editsupplimentdata"/>
</title>


<script language="JavaScript" type="text/javascript">
<!--
var messagesData = new Array();
messagesData={alert:"<fmt:message key="label.alert"/>",and:"<fmt:message key="label.and"/>",
errors:"<fmt:message key="label.errors"/>",
validvalues:"<fmt:message key="label.validvalues"/>",
submitOnlyOnce:"<fmt:message key="label.submitOnlyOnce"/>",
pleaseselect:"<fmt:message key="label.pleaseselect"/>", 
all:"<fmt:message key="label.all"/>",
transitTimeInDays:"<fmt:message key="label.transitTimeInDays"/>",
integer:"<fmt:message key="label.errorinteger"/>"
};




// -->
</script>
</head>

<body bgcolor="#ffffff" >

<tcmis:form action="/editsupplimentorydata.do" onsubmit="return submitFrameOnlyOnce();">

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
  <br/><br/><br/><fmt:message key="label.pleasewait"/>
  <br/><br/><br/><img src="/images/rel_interstitial_loading.gif" align="middle"/>
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
<fieldset>
<legend>&nbsp;<fmt:message key="label.editsupplimentdata"/>&nbsp;</legend>
<table id="searchTable" width="100%" border="0" cellpadding="0" cellspacing="0" class="tableSearch">

    <tr>
      <td width="10%" class="optionTitleBoldRight"><fmt:message key="label.sourcer"/>:</td>
      <td width="10%" class="optionTitleBoldLeft" nowrap="nowrap">
        <input  type="text" name="sourcerName" id="sourcerName" value="${editSupDataColl.sourcerName}" size="20" class="invGreyInputText" readonly="readonly" />
      	<input name="sourcer" id="sourcer" type="hidden" value="${editSupDataColl.sourcer}"/>
      	<input type="button" class="lookupBtn" onmouseover="this.className='lookupBtn lookupBtnOver'" onmouseout="this.className='lookupBtn'" name="selectedSourcer" value="..." align="right" onClick="searchSourcer()"/>
        <button class="smallBtns" onmouseover="this.className='smallBtns smallBtnsOver'" onMouseout="this.className='smallBtns'" name="None" value=""  OnClick="clearSourcer();return false;"><fmt:message key="label.clear"/> </button>
      </td>
    </tr>
<%-- 
    <tr>
      <td width="10%" class="optionTitleBoldRight"><fmt:message key="label.criticalordercarrier"/>:</td>
      <td width="10%" class="optionTitleBoldLeft" nowrap="nowrap">
        <input  type="text" name="criticalOrderCarrier" id="criticalOrderCarrier" value="${editSupDataColl.criticalOrderCarrier}" size="20" class="invGreyInputText" readonly="readonly" />
      	<input name="criticalOrderCarrierName" id="criticalOrderCarrierName" type="hidden" value="${editSupDataColl.criticalOrderCarrierName}"/>
      	<input type="button" class="lookupBtn" onmouseover="this.className='lookupBtn lookupBtnOver'" onmouseout="this.className='lookupBtn'" name="selectedCarrier" value="..." align="right" onClick="searchCarrier()"/>
        <button class="smallBtns" onmouseover="this.className='smallBtns smallBtnsOver'" onMouseout="this.className='smallBtns'" name="None" value=""  OnClick="clearCarrier();return false;"><fmt:message key="label.clear"/> </button>
      </td>
    </tr> 
    		 --%>
    <tr>
      <td width="10%" class="optionTitleBoldRight" nowrap><fmt:message key="label.refClientPartNo"/>:</td>
      <td width="10%">
       <input type="checkbox" name="refClientPartNo" id="refClientPartNo" value="${editSupDataColl.refClientPartNo}" <c:if test="${editSupDataColl.refClientPartNo == 'Y'}">checked</c:if> onclick="changeValue(this);" />
    </tr>
	<tr>
	 <td width="10%" class="optionTitleBoldRight" nowrap><fmt:message key="label.refclientPoNo"/>:</td>
      <td width="10%">
       <input type="checkbox" name="refClientPoNo" id="refClientPoNo" value="${editSupDataColl.refClientPoNo}" <c:if test="${editSupDataColl.refClientPoNo == 'Y'}">checked</c:if> onclick="changeValue(this);" />
      </td>
	</tr>
   <tr>
     <td width="10%" class="optionTitleBoldRight" nowrap><fmt:message key="label.refdeliverypoint"/>:</td>
      <td width="10%">
       <input type="checkbox" name="refDeliveryPoint" id="refDeliveryPoint" value="${editSupDataColl.refDeliveryPoint}" <c:if test="${editSupDataColl.refDeliveryPoint == 'Y'}">checked</c:if> onclick="changeValue(this);" />
      </td>
   </tr>
   <tr>
	<td width="10%" class="optionTitleBoldRight" nowrap><fmt:message key="label.refmr"/>:</td>
      <td width="10%">
       <input type="checkbox" name="refMaterialRequest" id="refMaterialRequest" value="${editSupDataColl.refMaterialRequest}" <c:if test="${editSupDataColl.refMaterialRequest == 'Y'}">checked</c:if> onclick="changeValue(this);" />
      </td>
    </tr>
    <tr>
	  <td width="10%" class="optionTitleBoldRight" nowrap><fmt:message key="label.refcustcontactinfo"/>:</td>
      <td width="10%">
       <input type="checkbox" name="refCustomerContactInfo" id="refCustomerContactInfo" value="${editSupDataColl.refCustomerContactInfo}" <c:if test="${editSupDataColl.refCustomerContactInfo == 'Y'}">checked</c:if> onclick="changeValue(this);" />
      </td>
	 </tr>
     <tr>
	  <td width="10%" class="optionTitleBoldRight" nowrap><fmt:message key="label.purchaserreview"/>:</td>
      <td width="10%">
       <input type="checkbox" name="purchaserReview"  id="purchaserReview" value="${editSupDataColl.purchaserReview}" <c:if test="${editSupDataColl.purchaserReview == 'Y'}">checked</c:if> onclick="changeValue(this);" />
      </td>
	 </tr>
	 <tr>
	  <td width="10%" class="optionTitleBoldRight" nowrap><fmt:message key="label.consignment"/>:</td>
      <td width="10%">
       <input type="checkbox" name="consignment"  id="consignment" value="${editSupDataColl.consignment}" <c:if test="${editSupDataColl.consignment == 'Y'}">checked</c:if> onclick="changeValue(this);" />
      </td>
	 </tr>
	 <tr>
	    <td width="10%" class="optionTitleBoldRight" nowrap="nowrap">
		<fmt:message key="label.defaultShipOriState" />:
		</td>
		<td width="10%" class="optionTitleBoldLeft" nowrap="nowrap">
		<c:if test="${! empty editSupDataColl.defaultShipmentOriginState}">
		     <c:set var="defaultShipmentOriginState" value="${editSupDataColl.defaultShipmentOriginState}"/>
		</c:if>
		<html:select property="defaultShipmentOriginState" styleClass="selectBox" styleId="defaultShipmentOriginState" value="${defaultShipmentOriginState}">
          <html:options collection="stateColl" property="stateAbbrev" labelProperty="state"/>
        </html:select>  
		</td>	
	 </tr>
	 <tr>
	    <td width="10%" class="optionTitleBoldRight" nowrap="nowrap">
		<fmt:message key="label.transitTimeInDays" />:
		</td>
		<td width="10%" class="optionTitleBoldLeft" nowrap="nowrap">
		<input name="transitTimeInDays" id="transitTimeInDays" type="text" class="inputBox" size="5" value="${editSupDataColl.transitTimeInDays}" />
		</td>	
	 </tr>
	 <tr>
	    <td width="10%" class="optionTitleBoldRight" nowrap="nowrap">
		<fmt:message key="label.leadtimeindays"/>:
		</td>
		<td width="10%" class="optionTitleBoldLeft" nowrap="nowrap">
		<input name="leadTimeDays" id="leadTimeDays" type="text" class="inputBox" size="5" value="${editSupDataColl.leadTimeDays}" />
		</td>	
	 </tr>
	 <tr>
	   
	</tr>
	<tr>
	    <td width="10%" class="optionTitleBoldRight" nowrap="nowrap">
		<fmt:message key="label.pricingType" />:
		</td>
		<td width="10%" class="optionTitleBoldLeft" nowrap="nowrap">
			<c:if test="${!empty editSupDataColl.pricingType}">
		       <c:set var="selectedPricingTy" value="${editSupDataColl.pricingType}" /> 
	        </c:if>
          <html:select property="pricingType" styleClass="selectBox" styleId="pricingType" value="${selectedPricingTy}">
             <html:option value="" key="label.pleaseselect"/>
             <html:option value="CONTRACT" key="label.contract"/>
             <html:option value="QUOTE" key="label.quote"/>
          </html:select>
		</td>	
	 </tr>
<%-- 
	 <tr>
	    <td width="10%" class="optionTitleBoldRight" nowrap="nowrap">
		<fmt:message key="label.incoterms" />:
		</td>
		<td width="10%" class="optionTitleBoldLeft" nowrap="nowrap">
			<select name="freightOnBoard" id="freightOnBoard" class="selectBox">
				<c:forEach var="freightBean" items="${vvFreightOnBoardBeanCollection}" varStatus="status">
					<c:if test="${status.current.status == 'ACTIVE'}">
						<option value="${status.current.freightOnBoard}" <c:if test="${status.current.freightOnBoard == editSupDataColl.freightOnBoard}">selected</c:if>><tcmis:jsReplace value="${status.current.description}"/></option>
					</c:if>
				</c:forEach>
			</select>
		</td>	
	 </tr>  --%>
	 <tr>
	    <td width="10%" class="optionTitleBoldRight">
		<fmt:message key="label.additionallinenotes" />:
		</td>
		<td width="10%" class="optionTitleLeft">
		<textarea cols="40" rows="3" class="inputBox" name="additionalLineNotes" id="additionalLineNotes">
		${editSupDataColl.additionalLineNotes}
		</textarea>                                                           
		</td>	
	 </tr>
	  <tr>
      <td width="10%" class="optionTitleBoldRight"><fmt:message key="label.defaultcsr"/>:</td>
      <td width="10%" class="optionTitleBoldLeft" nowrap="nowrap">
        <input  type="text" name="customerServiceRepName" id="customerServiceRepName" value="${editSupDataColl.customerServiceRepName}" size="20" class="invGreyInputText" readonly="readonly"/>
      	<input name="defCustomerServiceRepId" id="defCustomerServiceRepId" type="hidden" value="${editSupDataColl.defCustomerServiceRepId}"/>
      	<input type="button" class="lookupBtn" onmouseover="this.className='lookupBtn lookupBtnOver'" onmouseout="this.className='lookupBtn'" name="selectedSourcer" value="..." align="right" onClick="searchCsr()"/>
        <button class="smallBtns" onmouseover="this.className='smallBtns smallBtnsOver'" onMouseout="this.className='smallBtns'" name="None" value=""  OnClick="clearCsr();return false;"><fmt:message key="label.clear"/> </button>
      </td>
     </tr>
	 <tr><td></td></tr>
   <tr>
	<td colspan="2">
		<input name="okBtn" type="button" class="inputBtns" value="<fmt:message key="label.ok(done)"/>" id="okBtn" onmouseover="this.className='inputBtns inputBtnsOver'" onMouseout="this.className='inputBtns'"
          		 onclick= "editData();"/>
        <input name="cancelBtn" type="button" class="inputBtns" value="<fmt:message key="label.cancel"/>" id="cancelBtn" onmouseover="this.className='inputBtns inputBtnsOver'" onMouseout="this.className='inputBtns'"
          		 onclick= "window.close();"/>
	</td>

   </tr>
</table>
<c:if test="${requestScope['org.apache.struts.action.ERROR'] != null}">
<div class="spacerY">&nbsp;
<div id="searchErrorMessagesArea" class="errorMessages">
<html:errors/>
</div>
</div>
</c:if>
</fieldset>

<!--  result page section start -->
<div id="beanCollDiv" style="height:400px;display: none;" ></div>
</div>
 <%-- result count and time --%>
 <div id="footer" class="messageBar"></div>

  </div>
  <div class="roundbottom">
    <div class="roundbottomright"> <img src="/images/rndBoxes/borderBL.gif" alt="" width="15" height="15" class="corner" style="display: none" /> </div>
  </div>
</div>
</div>
</div>
</td></tr>
</table>
</div>

</div><!-- Result Frame Ends -->

<!-- Hidden element start -->
<div id="hiddenElements" style="display: none;">
<input type="hidden" name="uAction" id="uAction" value="${param.uAction}"/>
<input type="hidden" name="totalLines" id="totalLines" value="${dataCount}"/>
<input name="startSearchTime" id="startSearchTime" type="hidden" value="${startSearchTime}"/>
<input name="endSearchTime" id="endSearchTime" type="hidden" value="${endSearchTime}"/>
<input type="hidden" name="dbuyContractId" id="dbuyContractId" value="${param.dbuyContractId}"/>


</div>
<!-- Hidden elements end -->

</div> <!-- close of interface -->

</tcmis:form>

<!-- You can build your error messages below. Similar divs would have to be built to show any other messages in a inline pop-up.-->
<!-- Error Messages Begins -->
<div id="errorMessagesArea" class="errorMessages" style="display: none;overflow: auto;">

</div>
</body>
</html:html>