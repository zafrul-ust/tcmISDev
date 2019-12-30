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
<tcmis:gridFontSizeCss />
<%-- Add any other stylesheets you need for the page here --%>

<script type="text/javascript" src="/js/common/formchek.js"></script>
<script type="text/javascript" src="/js/common/commonutil.js"></script>
<!-- This handles all the resizing of the page and frames -->
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
<!-- For Calendar support -->
<script type="text/javascript" src="/js/calendar/newcalendar.js"></script>
<script type="text/javascript" src="/js/calendar/AnchorPosition.js"></script>
<script type="text/javascript" src="/js/calendar/PopupWindow.js"></script>

<!-- Add any other javascript you need for the page here -->
<script type="text/javascript" src="/js/hub/addnewprinterloc.js"></script>
<script type="text/javascript" src="/js/common/noSearchStandardGridmain.js"></script>    

<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxcommon.js"></script>
<script type="text/javascript" src="/dhtmlxWindows/codebase/dhtmlxwindows.js"></script>    
<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxgrid.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxgridcell.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_form.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_srnd.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_json.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/excells/dhtmlxgrid_excell_customized.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxcommon_haas.js"></script>

<title>
<fmt:message key="label.addnewpl"/>
</title>


<script language="JavaScript" type="text/javascript">
<!--

var messagesData = new Array();
messagesData={alert:"<fmt:message key="label.alert"/>",and:"<fmt:message key="label.and"/>",
errors:"<fmt:message key="label.errors"/>",
validvalues:"<fmt:message key="label.validvalues"/>",
submitOnlyOnce:"<fmt:message key="label.submitOnlyOnce"/>",
name:"<fmt:message key="label.name"/>",
company:"<fmt:message key="label.company"/>",
printerpath:"<fmt:message key="label.printerpath"/>",
pleaseselect:"<fmt:message key="label.pleaseselect"/>" 
};

var config = [
{ 
  columnId:"labelStockPermission"  
},
{
    columnId:"labelStock",
    columnName:'<fmt:message key="label.size"/>',
    type:'hcoro',
    submit:true,
    width:10
},
{ 
  columnId:"printerResolutionDpiPermission"  
},
{ 
   columnId:"printerResolutionDpi",
   columnName:'<fmt:message key="label.resolution"/>',
   type:'hcoro',
   submit:true,
    width:10
},
{ 
  columnId:"printerPathPermission"  
},
{ 
   columnId:"printerPath",
   columnName:'<fmt:message key="label.printerpath"/>',
   submit:true,
   type:'hed',
   width:25,
   size:35,
   maxlength:45
}
];

// -->


var showErrorMessage = false;


</script>
</head>

<body bgcolor="#ffffff"  onload="setOps();resultOnLoad();" onresize="resizeFrames()" >

<tcmis:form action="/addnewprinterloc.do" onsubmit="return submitFrameOnlyOnce();">

<div class="interface" id="mainPage" style="">

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

<div id="resultGridDiv" style="display: none;">
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
     <fieldset>
	<legend>&nbsp;<fmt:message key="label.addnewpl"/>&nbsp;</legend>
	<table id="searchTable" width="100%" border="0" cellpadding="0" cellspacing="0" class="tableSearch">

   <tr>
		<td  class="optionTitleBoldRight" nowrap="nowrap">
		<span style='font-size:12.0pt;color:red'>*</span><fmt:message key="label.name" />:</td>
		<td>
		<input name="printerLocation" id="printerLocation" type="text" size="30" class="inputBox" />                                                         
		</td>
	 </tr>
   <tr>
      <td class="optionTitleBoldRight" nowrap>
      <span style='font-size:12.0pt;color:red'>*</span><fmt:message key="label.company"/>:</td>
      <td>
        <select name="companyId" id="companyId" class="selectBox">
			<bean:size collection="${UserPageSelectViewCollection}" id="resultSize"/>
			<c:if test="${resultSize != 1 }">
				<option value=""><fmt:message key="label.mycompanies"/></option>
			</c:if>			
             <c:forEach var="companyName" items="${UserPageSelectViewCollection}" varStatus="status">
            	<option value="${status.current.companyId}">${status.current.companyName}</option>
            </c:forEach>
          </select>
      </td>
    </tr>
    <tr>
      <td class="optionTitleBoldRight"><fmt:message key="label.supplier"/>:</td>
      <td  colspan ="2">
        <input name="supplierName" id="supplierName" type="text"  size="20" class="invGreyInputText" readonly="readonly" value="${param.supplierName}"  />                                                           
        <input name="supplier" id="supplier" type="hidden" value="${param.supplier}"/>
        <input class="lookupBtn" onmouseover="this.className='lookupBtn lookupBtnOver'" onmouseout="this.className='lookupBtn'" name="searchsupplierlike" value="..." onclick="getSupplier()" type="button"/>
        <input class="smallBtns" onmouseover="this.className='smallBtns smallBtnsOver'" onmouseout="this.className='smallBtns'" name="clearsupplierlike" value="<fmt:message key="label.clear"/>" onclick="clearSupplier()" type="button"/>
      </td>
    </tr>
    <tr>
      <td  class="optionTitleBoldRight" nowrap><fmt:message key="label.facility"/>:</td>
      <td  colspan ="2">
	      <input name="facilityName" id="facilityName" type="text"  size="20" class="invGreyInputText" readonly="readonly" value="${param.facilityName}"  />                                                           
	      <input name="facilityId" id="facilityId" type="hidden" value="${param.facilityId}"/>
	      <input class="lookupBtn" onmouseover="this.className='lookupBtn lookupBtnOver'" onmouseout="this.className='lookupBtn'" name="searchfacilitylike" value="..." onclick="getFacility()" type="button"/>
	      <input class="smallBtns" onmouseover="this.className='smallBtns smallBtnsOver'" onmouseout="this.className='smallBtns'" name="clearfacilitylike" value="<fmt:message key="label.clear"/>" onclick="clearFacility()" type="button"/>
      </td>
    </tr>
	<tr>
	<td  class="optionTitleBoldRight" nowrap="nowrap">
		<fmt:message key="label.operatingentity" />:
	</td>
	<td class="optionTitleBoldLeft" nowrap="nowrap">
       <select name="opsEntityId" id="opsEntityId" class="selectBox">
         </select>
	</td>
   </tr>
  <tr>
	<td class="optionTitleBoldRight" nowrap="nowrap">
		<fmt:message key="label.hub" />:
	</td>
	<td  class="optionTitleBoldLeft" nowrap="nowrap">
       <select name="hub" id="hub" class="selectBox">
         </select>
	</td>
   </tr>
   </table></fieldset>  
       <%-- mainUpdateLinks Begins --%>
       <tcmis:opsEntityPermission indicator="true" userGroupId="addprinterloc">
      <fmt:message key="label.printerpath"/> :&nbsp;
       <a href="#" onclick="addPrinterPath()"><fmt:message key="label.add"/></a> |&nbsp;
      <a href="#" onclick="removePrinterPath();"><fmt:message key="label.remove"/></a>  
      </tcmis:opsEntityPermission>       
      <%-- mainUpdateLinks Ends --%>
    </div> <%-- boxhead Ends --%>

<div class="dataContent">
 <!--  result page section start -->
<div id="locationlabelPrinterBean" style="height:200px;" style="display: none;"></div>
<c:if test="${locationLabelPrinterBeanCollection != null}">
<script type="text/javascript">
<!--
<c:set var="dataCount" value='${0}'/>

var labelStock = new Array(
		  {value:"",text:'<fmt:message key="label.pleaseselect"/>'},
		  {value:"038",text:'038'},{value:"038A",text:'038A'},{value:"31",text:'31'},{value:"31A",text:'31A'},
		  {value:"33",text:'33'},{value:"35",text:'35'},{value:"35A",text:'35A'},{value:"425",text:'425'},
		  {value:"46",text:'46'},{value:"53",text:'53'},{value:"54",text:'54'},{value:"64",text:'64'},
		  {value:"DD1348",text:'DD1348'},{value:"HAZDEC",text:'HAZDEC'},{value:"UPS",text:'UPS'}
		);
		
var printerResolutionDpi = new Array(
		  {value:"",text:'<fmt:message key="label.pleaseselect"/>'},
		  {value:"150",text:'150'},{value:"200",text:'200'},{value:"300",text:'300'},{value:"471",text:'471'}
		);
		

var jsonMainData = new Array();
var jsonMainData = { 
rows:[
 /*The row ID needs to start with 1 per their design.*/
{ id:1,
 data:[ 'Y', 
 '','Y','','Y',
 '']}
 
]};

//-->
</script>

</c:if>

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
<tr><td>&nbsp;</td></tr>
<tr align="center">
	<td  colspan="2">
	 <tcmis:opsEntityPermission indicator="true" userGroupId="addprinterloc">
		<input name="okBtn" type="button" class="inputBtns" value="<fmt:message key="label.ok(done)"/>" id="okBtn" onmouseover="this.className='inputBtns inputBtnsOver'" onMouseout="this.className='inputBtns'"
          		 onclick= "newPrinterLocation();"/>
      </tcmis:opsEntityPermission>       		 
        <input name="cancelBtn" type="button" class="inputBtns" value="<fmt:message key="label.cancel"/>" id="cancelBtn" onmouseover="this.className='inputBtns inputBtnsOver'" onMouseout="this.className='inputBtns'"
          		 onclick= "window.close();"/>
	</td></tr>
</table>
</div>
</div><!-- Result Frame Ends -->

<!-- Hidden element start -->
<div id="hiddenElements" style="display: none;">
<input name="totalLines" id="totalLines" value="${dataCount}" type="hidden"/>
<input name="uAction" id="uAction" value="${param.uAction}" type="hidden"/>
<input type="hidden" name="done" id="done" value="${done}"/>
<input name="startSearchTime" id="startSearchTime" type="hidden" value="${startSearchTime}"/>
<input name="endSearchTime" id="endSearchTime" type="hidden" value="${endSearchTime}"/>
<input name="minHeight" id="minHeight" type="hidden" value="100"/>

</div>
<!-- Hidden elements end -->

</div> 
</tcmis:form>

<!-- You can build your error messages below. Similar divs would have to be built to show any other messages in a inline pop-up.-->
<!-- Error Messages Begins -->
<div id="errorMessagesArea" class="errorMessages" style="display: none;overflow: auto;">

</div>

</body>
</html:html>