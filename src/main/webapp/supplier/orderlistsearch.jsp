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
<script type="text/javascript" src="/js/supplier/orderlistsearch.js"></script>

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
<fmt:message key="label.purchase.orders"/>
</title>

<script language="JavaScript" type="text/javascript">
<!--
//add all the javascript messages here, this for internationalization of client side javascript messages
var messagesData = new Array();
messagesData={alert:"<fmt:message key="label.alert"/>",
and:"<fmt:message key="label.and"/>",
validvalues:"<fmt:message key="label.validvalues"/>",
submitOnlyOnce:"<fmt:message key="label.submitOnlyOnce"/>",
poNumber:"<fmt:message key="error.po.integer"/>"};
// -->
</script>
</head>

<body bgcolor="#ffffff" onload="mySearchOnload();">

<tcmis:form action="/orderlistresults.do" onsubmit="return submitFrameOnlyOnce();" target="resultFrame">

<div class="interface" id="searchMainPage">

<div class="contentArea">

<!-- Search Option Begins -->
<table id="searchMaskTable" width="600" border="0" cellpadding="0" cellspacing="0">
<tr><td>
<div class="roundcont filterContainer">
 <div class="roundright">
   <div class="roundtop">
     <div class="roundtopright"> <img src="/images/rndBoxes/borderTL_filter.gif" alt="" width="15" height="10" class="corner_filter" style="display: none" /></div>
   </div>
   <div class="roundContent">
    <!-- Insert all the search option within this div -->
   
    <table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableSearch">
      <tr>
        <td rowspan="3" class="optionTitleBoldLeft"><fmt:message key="label.supplier"/>:</td>
        <td rowspan="3" width="10%" class="optionTitleBoldLeft">
        <select name="supplierIdArray" id="supplierIdArray" class="selectBox">                   
            <c:forEach var="SupplierLocationOvBean" items="${supplierLocationOvBean}" varStatus="status">
              <c:choose>
              <c:when test="${status.index  == 0}">
                <option value="<c:out value="${status.current.supplier}"/>" selected><c:out value="${status.current.supplierName}"/></option>
              </c:when>
              <c:otherwise>
                <option value="<c:out value="${status.current.supplier}"/>"><c:out value="${status.current.supplierName}"/></option>
              </c:otherwise>
            </c:choose>
            </c:forEach>
          </select>        
 		</td>   
        <td  class="optionTitleBoldLeft"  width="2%">
          <input type="radio" class="radioBtns" name="showPOs" id="showPOs" value="bystatus" checked="checked"  onclick="checkPoChkboxs();"><fmt:message key="label.status" />:
        </td>             
        <td width="2%"  class="optionTitleBoldLeft"><input type="checkbox" class="radioBtns" name="poStatusChkbxArray" id="poStatusChkbxArray" value="NEW" checked="checked"><fmt:message key="label.new"/>
        </td>
       <td  width="2%"  class="optionTitleBoldLeft"><input type="checkbox" class="radioBtns" name="poStatusChkbxArray" id="poStatusChkbxArray" value="PROBLEM" checked="checked"><fmt:message key="label.problem"/> </td>                  
        <td  width="8%" >&nbsp;</td>
        </tr>
        <tr>                     
       <td  width="8%" >&nbsp;</td>
        <td  class="optionTitleBoldLeft">
        <input type="checkbox" class="radioBtns" name="poStatusChkbxArray" id="poStatusChkbxArray" value="ACKNOWLEDGED" checked="checked"><fmt:message key="label.acknoledged"/></td>
		<td  class="optionTitleBoldLeft">
		<input type="checkbox" class="radioBtns" name="poStatusChkbxArray" id="poStatusChkbxArray" value="RESOLVED" checked="checked"><fmt:message key="label.resolved"/></td>
        <td  width="8%" >&nbsp;</td>
        </tr>
        <tr>                     
       <td  width="8%" >&nbsp;</td>
        <td  class="optionTitleBoldLeft"><input type="checkbox" class="radioBtns" name="poStatusChkbxArray" id="poStatusChkbxArray" value="CONFIRMED"><fmt:message key="label.confirmed"/></td>
		<td  class="optionTitleBoldLeft"><input type="checkbox" class="radioBtns" name="poStatusChkbxArray" id="poStatusChkbxArray" value="REJECTED"><fmt:message key="label.rejected"/></td>        
         <td  width="8%" >&nbsp;</td>
        </tr>                    
      <tr>            
       <td class="optionTitleBoldLeft" width="2%">&nbsp;</td>    
        <td class="optionTitleBoldLeft">&nbsp;</td> 
         <td class="optionTitleBoldLeft" width="2%">    <input type="radio" class="radioBtns" name="showPOs" id="showPOs" value="bypo" onclick="unCheckPoChkboxs();"><fmt:message key="label.po" />:</td>    
        <td class="optionTitleBoldLeft"><input type="text" class="inputBox" name="poNumber" id="poNumber" size="10"></td> 
      </tr>
      <tr>
        <td class="optionTitleBoldLeft" colspan="6">
            <input name="Search" id="submitSearch" type="submit"
                   value="<fmt:message key="label.search"/>" class="inputBtns"
                   onmouseover="this.className='inputBtns inputBtnsOver'"
                   onmouseout="this.className='inputBtns'"
                   onclick="return submitSearchForm();">

            <input name="buttonCreateExcel" id="buttonCreateExcel" type="submit"
                   class="inputBtns" value="<fmt:message key="label.createexcelfile"/>"
                   onmouseover="this.className='inputBtns inputBtnsOver'"
                   onmouseout="this.className='inputBtns'"
                   onclick="generateExcel(); return false;">
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
<input name="action" id="action" type="hidden" value="">
<input name="startSearchTime" id="startSearchTime" type="hidden"/>
</div>
<!-- Hidden elements end -->

</div> <!-- close of interface -->

</tcmis:form>
</body>
</html:html>