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
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

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
<script type="text/javascript" src="/js/supply/newsupplieritemnotes.js"></script>


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
<fmt:message key="label.addNewComment"/>
</title>


<script language="JavaScript" type="text/javascript">
<!--
var messagesData = new Array();
messagesData={alert:"<fmt:message key="label.alert"/>",and:"<fmt:message key="label.and"/>",
errors:"<fmt:message key="label.errors"/>",
validvalues:"<fmt:message key="label.validvalues"/>",
submitOnlyOnce:"<fmt:message key="label.submitOnlyOnce"/>",
comments:"<fmt:message key="label.comments"/>",
itemId:"<fmt:message key="label.item"/>",
supplier:"<fmt:message key="label.supplier"/>",
maximumallowedtext:"<fmt:message key="label.maximumallowedtext">
          	    <fmt:param>${1000}</fmt:param>
          	  </fmt:message>"
};
// -->
</script>
</head>

<body bgcolor="#ffffff" onload="myOnload()">

<tcmis:form action="/newsupplieritemnotes.do" onsubmit="return submitSearchOnlyOnce();">

<div id="transitPage" style="display: none;text-align:center;">
<p><br><br><br><fmt:message key="label.pleasewait"/></p>
</div>

<div class="interface" id="mainPage" style="">


<script type="text/javascript">
<!--
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

<div id="resultGridDiv">
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

<fieldset>
<legend><fmt:message key="label.addNewComment"/>:</legend>
<table id="searchTable" width="100%" border="0" cellpadding="0" cellspacing="0" class="tableSearch">

<tr>
	<td class="optionTitleBoldRight" nowrap="nowrap">
		<fmt:message key="label.supplier" />:
	</td>
	<td class="optionTitleBoldLeft" nowrap="nowrap">
                <input name="supplierName" id="supplierName" type="text" maxlength="18" size="20" class="invGreyInputText" readonly="readonly" value="${param.supplierName}" />                                                           
                <input name="supplier" id="supplier" type="hidden" value="${param.supplier}"/> 
                <c:if test="${empty param.supplier || param.updateSupplier == 'Y'}">                                   
                <input class="lookupBtn" onmouseover="this.className='lookupBtn lookupBtnOver'" onmouseout="this.className='lookupBtn'" name="searchsupplierlike" value="..." onclick="getSuppliers()" type="button"/>
                <input class="smallBtns" onmouseover="this.className='smallBtns smallBtnsOver'" onmouseout="this.className='smallBtns'" name="clearsupplierlike" value="<fmt:message key="label.clear"/>" onclick="clearSuppliers()" type="button"/> 
                </c:if>                               				
	</td>
</tr> 
<tr>
	<td class="optionTitleBoldRight" nowrap="nowrap">
		<fmt:message key="label.item" />:
	</td>
	<td class="optionTitleBoldLeft" nowrap="nowrap">
               <input name="itemId" id="itemId" type="text" maxlength="18" size="20" class="invGreyInputText" readonly="readonly" value="${param.itemId}"  />                                                           
               <c:if test="${empty param.itemId}">                                 
               <input class="lookupBtn" onmouseover="this.className='lookupBtn lookupBtnOver'" onmouseout="this.className='lookupBtn'" name="searchsupplierlike" value="..." onclick="getItem()" type="button"/>
               <input class="smallBtns" onmouseover="this.className='smallBtns smallBtnsOver'" onmouseout="this.className='smallBtns'" name="clearsupplierlike" value="<fmt:message key="label.clear"/>" onclick="clearItem()" type="button"/>
               </c:if>
	</td>
</tr>
<tr>
	<td class="optionTitleBoldRight" nowrap="nowrap">
		<fmt:message key="label.description" />:
	</td>
	<td class="optionTitleLeft">
               <span id="descSpan" onmouseover="showTooltip('hiddenDesc', 'descSpan', 0, 15);" onmouseout="hideTooltip('hiddenDesc');">
						${fn:substring(param.itemDesc, 0, 120)}
	   		   </span> 
	   		   <input type="hidden" id="descLength" value="${fn:length(param.itemDesc)}" />
    </td>
</tr>
<tr>
	<td width="5%" class="optionTitleBoldRight"><fmt:message key="label.comments"/>:</td>
	<td width="50%"><textarea rows="4" cols="40" class="inputBox" name="comments" id="comments"><c:out value="${comments}" /></textarea></td>
</tr>
<tr>
	<td width="15%" colspan="2">
		<input name="okBtn" type="button" class="inputBtns" value="<fmt:message key="label.ok(done)"/>" id="okBtn" onmouseover="this.className='inputBtns inputBtnsOver'" onMouseout="this.className='inputBtns'"
          		  onclick= "newComment()">
        <input name="cancelBtn" type="button" class="inputBtns" value="<fmt:message key="label.cancel"/>" id="cancelBtn" onmouseover="this.className='inputBtns inputBtnsOver'" onMouseout="this.className='inputBtns'"
          		 onclick= "window.close();"/>
	</td>

</tr>
</table>
<c:if test="${requestScope['org.apache.struts.action.ERROR'] != null}">
<div class="spacerY">&nbsp;
<div id="searchErrorMessagesArea" class="errorMessages">
<html:errors/>
${tcmISError}
</div>
</div>
</c:if>
</fieldset>

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

</div><!-- Result Frame Ends -->

<!-- Hidden element start -->
<div id="hiddenElements" style="display: none;">
<input type="hidden" name="uAction" id="uAction" value="${param.uAction}"/>
<input name="opsEntityId" id="opsEntityId" type="hidden" value="${param.opsEntityId}"/>
</div>
<!-- Hidden elements end -->

<div id="hiddenDesc" style="visibility: hidden;position: absolute;z-index: 2;font:8pt sans-serif;border: solid 1px;background-color:white;">
${param.itemDesc}
</div> 

</div> <!-- close of interface -->

</tcmis:form>
</body>
</html:html>