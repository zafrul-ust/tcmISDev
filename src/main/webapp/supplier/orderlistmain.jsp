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
<link rel="shortcut icon" href="https://www.tcmis.com/images/buttons/tcmIS.ico"> </link>

<%@ include file="/common/locale.jsp" %>
<!--Use this tag to get the correct CSS class.
This looks at what the user's preffered font size and which application he is viewing to set the correct CSS. -->
<tcmis:gridFontSizeCss />
<%-- Add any other stylesheets you need for the page here --%>

<script type="text/javascript" src="/js/common/formchek.js"></script>
<script type="text/javascript" src="/js/common/commonutil.js"></script>
<!-- This handles all the resizing of the page and frames -->
<script type="text/javascript" src="/js/common/grid/iframegridresizemain.js"></script>
<!-- This handels which key press events are disabeled on this page -->
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
<script type="text/javascript" src="/js/common/standardGridmain.js"></script>
<script type="text/javascript" src="/js/supplier/orderlistmain.js"></script>


<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxcommon.js"></script>
<script type="text/javascript" src="/dhtmlxLayout/codebase/dhtmlxlayout.js"></script>
<script type="text/javascript" src="/dhtmlxWindows/codebase/dhtmlxwindows.js"></script>

<title>
<fmt:message key="label.purchase.orders"/>
</title>

<script language="JavaScript" type="text/javascript">
<!--
//add all the javascript messages here, this for internationalization of client side javascript messages
var messagesData = new Array();
messagesData={alert:"<fmt:message key="label.alert"/>",and:"<fmt:message key="label.and"/>",
checkatleastone:"<fmt:message key="label.checkatleastone"/>",
errors:"<fmt:message key="label.errors"/>",      
validvalues:"<fmt:message key="label.validvalues"/>",
poNumber:"<fmt:message key="error.po.integer"/>",
submitOnlyOnce:"<fmt:message key="label.submitOnlyOnce"/>"};
// -->
</script>
</head>

<!--call the loadLayoutWin() to set the sizes of the search section and initiate the layout.
If you dont want to use the layout set javascript variable useLayout=false;
It is important to send the pageId if this page is going to open in a tab in the application.
You can also call any functions you need to do your search initialization for drop downs.
-->
<body bgcolor="#ffffff" onload="loadLayoutWin('','wBuy');"  onresize="resizeFrames()">

<div class="interface" id="mainPage" style="">

<!-- Search div Begins -->
<div id="searchFrameDiv">
  <!-- Header Begins -->
 <%--  
<div class="header">
  <div class="alignLeft"><%@ include file="/common/application/clientlogo.jsp" %></div>
  <div class="alignRight">
   <c:set var="firstname" value='${sessionScope.personnelBean.firstName}'/>
   <c:set var="lastname" value='${sessionScope.personnelBean.lastName}'/>

   <fmt:message key="label.loggedinas"/>: <span class="optionTitleBold"><c:out value="${firstname}" />&nbsp;<c:out value="${lastname}" /></span>
   <fmt:message key="label.forcompany"/>
   <span class="optionTitleBold">
    <c:out value="${companyName}" />
   </span>
   &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href="#" onclick="logout(); return false;"><fmt:message key="label.logout"/></a> | <a href="javascript:opentcmISHelp('${module}');"><fmt:message key="label.usualhelp"/></a>
  </div>
</div>
<!-- Header Ends -->

<!-- Top Navigation- we will not display this section after sometime, when we move to the new menu structure -->
<div class="topNavigation" id="topNavigation">
<!--<table width="100%" border="0" cellpadding="0" cellspacing="0">
<tr>
<td width="200">
<img src="/images/tcmtitlegif.gif" align="left">
</td>
<td>
<img src="/images/tcmistcmis32.gif" align="right">
</td>
</tr>
</table>-->

<table width="100%" border="0" cellpadding="0" cellspacing="0">
<tr><td width="70%" class="headingl">
<fmt:message key="label.purchase.orders"/>
</td>
<td width="30%" class="headingr">
<html:link style="color:#FFFFFF" forward="home">
 <fmt:message key="label.home"/>
</html:link>
</td>
</tr>
</table>
</div>
<!-- Top Navigation Ends -->
--%>
<!-- start of contentArea -->
<div class="contentArea">
<tcmis:form action="/orderlistresults.do" onsubmit="return submitSearchOnlyOnce();" target="resultFrame">

<!-- Search Option Begins -->
<%--Change the width to what you want your page to span.--%>
<table id="searchMaskTable" width="100%" border="0" cellpadding="0" cellspacing="0">
<tr><td>
<div class="roundcont filterContainer">
 <div class="roundright">
   <div class="roundtop">
     <div class="roundtopright"> <img src="/images/rndBoxes/borderTL_filter.gif" alt="" width="15" height="10" class="corner_filter" style="display: none" /></div>
   </div>
   <div class="roundContent">
    <!-- Insert all the search option within this div -->
    <table width="600" border="0" cellpadding="0" cellspacing="0" class="tableSearch">
	  <%-- row 1 --%>    
      <tr>
        <td  width="2%" class="optionTitleBoldLeft"><fmt:message key="label.supplier"/>:</td>
        <td  width="3%" class="optionTitleBoldLeft">
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
        <td  class="optionTitleBoldLeft"  width="2%" nowrap>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
          <input type="radio" class="radioBtns" name="showPOs" id="showPOs" value="bystatus" checked="checked"  onclick="checkPoChkboxs();"><fmt:message key="label.status" />:
        </td>             
        <td width="2%"  class="optionTitleBoldLeft" nowrap><input type="checkbox" class="radioBtns" name="poStatusChkbxArray" id="poStatusChkbxArray" value="NEW" checked="checked"><fmt:message key="label.new"/>
        </td>
       <td  width="2%"  class="optionTitleBoldLeft" nowrap>
       		<input type="checkbox" class="radioBtns" name="poStatusChkbxArray" id="poStatusChkbxArray" value="PROBLEM" checked="checked"><fmt:message key="label.problem"/> 
       		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
       </td>                  
       
       <%-- row 2 --%>
        </tr>
        <tr>
       
         <td  class="optionTitleBoldLeft" colspan="2">&nbsp;
          <%--
         <input type="checkbox" class="radioBtns" name="OpenPos" id="OpenPos" value="openPos"><fmt:message key="label.openposonly"/>
         --%> 
         </td>
                             
       <td>&nbsp;</td>
       
        <td  class="optionTitleBoldLeft" nowrap>
        <input type="checkbox" class="radioBtns" name="poStatusChkbxArray" id="poStatusChkbxArray" value="ACKNOWLEDGED" checked="checked"><fmt:message key="label.acknoledged"/></td>
		<td  class="optionTitleBoldLeft" nowrap>
		<input type="checkbox" class="radioBtns" name="poStatusChkbxArray" id="poStatusChkbxArray" value="RESOLVED" checked="checked"><fmt:message key="label.resolved"/></td>
        
        </tr>
        
        <%-- row 3 --%>
        <tr>       
       <td colspan="3">&nbsp;</td>       
        <td  class="optionTitleBoldLeft" nowrap><input type="checkbox" class="radioBtns" name="poStatusChkbxArray" id="poStatusChkbxArray" value="CONFIRMED" onclick="showHideDateConfirmed()"><fmt:message key="label.confirmed"/></td>
		<td  class="optionTitleBoldLeft" nowrap><input type="checkbox" class="radioBtns" name="poStatusChkbxArray" id="poStatusChkbxArray" value="REJECTED"><fmt:message key="label.rejected"/></td>        
         
        </tr>
        
        <%-- row 4 --%>   
        <tr id="dateConfirmedrow" style="display: none">       
       <td   class="optionTitleBoldLeft">&nbsp;   </td>
       
         <td   width="20%" class="optionTitleBoldRight" nowrap>&nbsp;</td>
         <td  colspan="3" class="optionTitleBoldLeft" nowrap>
           <fmt:message key="label.dateconfirmed"/>:                              
           <fmt:message key="label.from"/>
           <input class="inputBox pointer" readonly type="text" name="confirmedFromDate" id="confirmedFromDate" value="" onClick="return getCalendar(document.supplierForm.confirmedFromDate,null,document.supplierForm.confirmedToDate);"
                                                maxlength="10" size="8"/>
          <fmt:message key="label.to"/>&nbsp;

           <input class="inputBox pointer" readonly type="text" name="confirmedToDate" id="confirmedToDate" value="" onClick="return getCalendar(document.supplierForm.confirmedToDate,document.supplierForm.confirmedFromDate);"
                                                maxlength="10" size="8"/>                                                        
                                        
           </td>   
        </tr>  
        
        <%-- row 4 --%>
        <tr id="dateConfirmedrowHide" style="display:">       
       <td   class="optionTitleBoldLeft">&nbsp;   </td>
       
         <td   width="20%" class="optionTitleBoldRight">&nbsp;</td>
         <td  colspan="3" class="optionTitleBoldLeft">&nbsp;                             
           &nbsp;
          &nbsp;
		 &nbsp;                                                    
                                        
           </td>   
        </tr>   
      <%-- row 5 --%>                     
      <tr>          
       <td>&nbsp;</td>
        <td  width="20%">&nbsp;</td>
         <td class="optionTitleBoldRight" width="2%" nowrap>    <input type="radio" class="radioBtns" name="showPOs" id="showPOs" value="bypo" onclick="unCheckPoChkboxs();"><fmt:message key="label.po" />:</td>    
        <td class="optionTitleBoldLeft"><input type="text" class="inputBox" name="poNumber" id="poNumber" size="10"></td> 
        <td>&nbsp;</td>
      </tr>
     
      <%-- row 6 --%>     
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
<div id="searchErrorMessagesArea" class="errorMessages">
<html:errors/>
</div>
</div>
</c:if>
<!-- Error Messages Ends -->

<!-- Hidden element start -->
<div id="hiddenElements" style="display: none;">
<input name="action" id="action" type="hidden" value="">
<input name="startSearchTime" id="startSearchTime" type="hidden"value="">
<input name="searchHeight" id="searchHeight" type="hidden" value="183">
</div>
<!-- Hidden elements end -->
</tcmis:form>
</div>
<!-- close of contentArea -->
</div>
<!-- Search div Ends -->

<!-- Result Frame Begins -->
<div id="resultFrameDiv" style="margin: 4px 4px 0px 4px;">
<!-- Transit Page Begins -->
<div id="transitPage" style="display: none;" class="optionTitleBoldCenter">
  <br><br><br><fmt:message key="label.pleasewait"/>
  <br><br><br><img src="/images/rel_interstitial_loading.gif" align="middle">
</div>
<!-- Transit Page Ends -->
<div id="resultGridDiv" style="display: none;"> 
<!-- results start -->
<!-- If you want your results section to span only 50% set this to 50% or 800 pixels. Make sure to set resizeGridWithWindow to false. -->
<table id="resultsMaskTable" width="100%" border="0" cellpadding="0" cellspacing="0">
<tr>
    <td>
<div class="roundcont contentContainer">
<div class="roundright">
  <div class="roundtop">
    <div class="roundtopright"> <img src="/images/rndBoxes/borderTL.gif" alt="" width="15" height="15" class="corner" style="display: none" /></div>
  </div>
  <div class="roundContent">
    <div class="boxhead"> <%-- boxhead Begins --%>
     <%-- Place all update links in divs that can be turned on or off depending on the search criteria that is selected
          Do not remove mainUpdateLinks div, even if you don't have any links. You can remove content inside the div but not the div itself.
          Please keep the <span></span> on the same line this will avoid extra virtical space.
      --%>     
       <div id="mainUpdateLinks" style="display: none"> <%-- mainUpdateLinks Begins --%>
      <span id="updateResultLink" style="display: none"><a href="#" onclick="update;"><fmt:message key="label.update"/></a></span>         
     </div> <%-- mainUpdateLinks Ends --%>
    </div> <%-- boxhead Ends --%>
    <div class="dataContent">
     <iframe id="resultFrame" scrolling="no" name="resultFrame" width="100%" height="" frameborder="0" marginwidth="0" src="/blank.html"></iframe>
   </div>
   <%-- result count and time --%>
   <div id="footer" class="messageBar"></div>
  </div>

  <div class="roundbottom">
    <div class="roundbottomright"> <img src="/images/rndBoxes/borderBL.gif" alt="" width="15" height="15" class="corner" style="display: none" /> </div>
  </div>
</div>
</div>
</td>
</tr>
</table>
<!-- results end -->
</div>  

</div>
<!-- Result Frame Ends -->

</div> <!-- close of interface -->

<!-- You can build your error messages below.
     Similar divs would have to be built to show any other messages in a inline pop-up.-->
<!-- Error Messages Begins -->
<div id="errorMessagesArea" class="errorMessages" style="display: none;overflow: auto;">

</div>


</body>
</html:html>