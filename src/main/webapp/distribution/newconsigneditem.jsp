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

<!-- Add any other javascript you need for the page here -->
<script type="text/javascript" src="/js/common/noSearchStandardGridmain.js"></script>
<script type="text/javascript" src="/js/distribution/newconsigneditem.js"></script>

<!-- These are for the Grid, uncomment if you are going to use the grid -->
<%--NEW - dhtmlX grid files--%>
<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxcommon.js"></script>
<script type="text/javascript" src="/dhtmlxWindows/codebase/dhtmlxwindows.js"></script>
    
<title>
<fmt:message key="label.newconsigneditem"/>
</title>


<script language="JavaScript" type="text/javascript">
<!--
var messagesData = new Array();
messagesData={alert:"<fmt:message key="label.alert"/>",and:"<fmt:message key="label.and"/>",
errors:"<fmt:message key="label.errors"/>",
update:"<fmt:message key="label.update"/>",
validvalues:"<fmt:message key="label.validvalues"/>",
submitOnlyOnce:"<fmt:message key="label.submitOnlyOnce"/>",
pleaseselect:"<fmt:message key="label.pleaseselect"/>",
itemaddedsuccessfully:"<fmt:message key="label.itemaddedsuccessfully"/>",
all:"<fmt:message key="label.all"/>",
itemid:"<fmt:message key="label.itemid"/>",
specs:"<fmt:message key="label.specs"/>",
srcignotfound:"<fmt:message key="label.srcignotfound"/>",
unitprice:"<fmt:message key="label.unitprice"/>"
};
// -->
</script>
</head>

<body bgcolor="#ffffff"  onload="showError();" onunload="try{opener.closeTransitWin();}catch(ex){}" >

<tcmis:form action="/newconsigneditem.do" onsubmit="return submitFrameOnlyOnce();">

<div class="interface" id="mainPage" style="">

<!-- Error Messages Begins -->

<div id="errorMessagesAreaBody" style="display:none;">
${tcmISError}
${rowCount}
</div>
<!-- Error Messages Ends -->

<script type="text/javascript">
<!--
/*Check if there is any error messages to show and set the variable you use in javascript to true or false.*/
<c:choose>
   <c:when test="${ empty tcmISError && done != 'Y'}">
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
<legend class="optionTitleBoldLeft">&nbsp;<fmt:message key="label.newconsigneditem"/>&nbsp;</legend>
<table id="searchTable" width="100%" border="0" cellpadding="0" cellspacing="0" class="tableSearch">
    <tr>
      <td width="10%" class="optionTitleBoldRight" nowrap><fmt:message key="label.inventorygroup"/>:&nbsp;</td>
      <td width="10%">
       ${param.destInventoryGroup}
       <input name="destInventoryGroup" id="destInventoryGroup" type="hidden" value="${param.destInventoryGroup}"/>                                                           
      </td>
    </tr>
	<tr>
			<td class="optionTitleBoldRight" nowrap="nowrap" width="10%">
					<fmt:message key="label.item" /><span style='font-size:12.0pt;color:red'>*</span>:
			</td>
			<td class="optionTitleBoldLeft" nowrap="nowrap" width="20%">
					<input name="itemId" id="itemId" type="text" maxlength="18" size="15" class="invGreyInputText" readonly="readonly"/>                                                           
                    <input class="lookupBtn" onmouseover="this.className='lookupBtn lookupBtnOver'" onmouseout="this.className='lookupBtn'" name="searchsupplierlike" value="..." onclick="getItem()" type="button"/>
                    <input class="smallBtns" onmouseover="this.className='smallBtns smallBtnsOver'" onmouseout="this.className='smallBtns'" name="clearsupplierlike" value="<fmt:message key="label.clear"/>" onclick="clearItem()" type="button"/>
			</td>
			<td class="optionTitleBoldRight" nowrap="nowrap" width="10%">
					<fmt:message key="label.specs" /><span style='font-size:12.0pt;color:red'>*</span>:
			</td>
			<td class="optionTitleBoldLeft" nowrap="nowrap" >
					<input name="specListDisplay" id="specListDisplay" type="text" maxlength="80" size="40" class="invGreyInputText" readonly="readonly" />                                                                                                                    
                    <input class="lookupBtn" onmouseover="this.className='lookupBtn lookupBtnOver'" onmouseout="this.className='lookupBtn'" name="searchsupplierlike" value="..." onclick="getSpecList()" type="button"/>
                    <input class="smallBtns" onmouseover="this.className='smallBtns smallBtnsOver'" onmouseout="this.className='smallBtns'" name="clearsupplierlike" value="<fmt:message key="label.clear"/>" onclick="clearSpecList()" type="button"/>
                    <input name="specListId" id="specListId" type="hidden" />
                    <input name="specDetail" id="specDetail" type="hidden" /> 
                    <input name="specCoc" id="specCoc" type="hidden" /> 
                    <input name="specCoa" id="specCoa" type="hidden" /> 
                    <input name="specLibrary" id="specLibrary" type="hidden" /> 
			</td>
	</tr>
	<tr>
			<td class="optionTitleBoldRight" nowrap="nowrap" width="10%">
					<fmt:message key="label.desc" />:
			</td>
			<td class="optionTitleLeft" colspan="3">
					<span id="itemDesc">&nbsp;</span>                                                          
			</td>
	</tr>

	<tr>
			<td class="optionTitleBoldRight" nowrap="nowrap" width="10%">
					<fmt:message key="label.unitprice" /><span style='font-size:12.0pt;color:red'>*</span>:
			</td>
			<td class="optionTitleBoldLeft" nowrap="nowrap" width="20%">
					<input name="unitPrice" id="unitPrice" type="text" value="" maxlength="18" size="15" class="inputBox" />                                                           
			</td>
			<td class="optionTitleBoldRight" nowrap="nowrap" width="10%">
					<fmt:message key="label.currency" /><span style='font-size:12.0pt;color:red'>*</span>:
			</td>
			<td class="optionTitleBoldLeft" nowrap="nowrap">
				<select name="currencyId" id="currencyId" class="selectBox" >
                    <c:forEach var="cbean" items="${vvCurrencyBeanCollection}" varStatus="status">
  						<option value="${status.current.currencyId}" >${status.current.currencyXref}</option>
    				</c:forEach>
    			</select>                                                          
			</td>
	</tr>
	<tr>
			<c:if test="${!empty sourceInventoryGroup}">
            <td class="optionTitleBoldRight" nowrap="nowrap" width="10%">
					<fmt:message key="label.supplyfrom" />:
			</td>
			<td class="optionTitleLeft" nowrap="nowrap" colspan="3">
				${sourceInventoryGroup}
                <c:set var="checked" value='${"checked"}' />                
                <input name="sourceInventoryGroup" id="sourceInventoryGroup" type="hidden" value="${sourceInventoryGroup}"/>
				<input name="supplyFrom" id="supplyFrom" type="radio" class="radioBtns" ${checked} value="Y"><fmt:message key="label.yes" />
    			<input name="supplyFrom" id="supplyFrom" type="radio" class="radioBtns" value="N"><fmt:message key="label.no" />  				
			</td>
            </c:if>
    </tr>
<tr>
	<td width="15%" colspan="4">
		<input name="okBtn" type="button" class="inputBtns" value="<fmt:message key="label.ok(done)"/>" id="okBtn" onmouseover="this.className='inputBtns inputBtnsOver'" onMouseout="this.className='inputBtns'"
          		 onclick= "addItem();"/>
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
</td></tr>
</table>
</div>

</div><!-- Result Frame Ends -->

<!-- Hidden element start -->
<div id="hiddenElements" style="display: none;">
<input type="hidden" name="uAction" id="uAction" value="${param.uAction}"/>
<input type="hidden" name="totalLines" id="totalLines" value="${dataCount}"/>
<input type="hidden" name="done" id="done" value="${done}"/>
<input name="startSearchTime" id="startSearchTime" type="hidden" value="${startSearchTime}"/>
<input name="endSearchTime" id="endSearchTime" type="hidden" value="${endSearchTime}"/>
<input type="hidden" name="consign" id="consign" value="${consign}"/>
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
</html:html>