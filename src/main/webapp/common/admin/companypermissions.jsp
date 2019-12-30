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
<link rel="shortcut icon" href="https://www.tcmis.com/images/buttons/tcmIS.ico"></link>

<%@ include file="/common/locale.jsp" %>  
<tcmis:gridFontSizeCss />
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

<!-- Add any other javascript you need for the page here -->
<script type="text/javascript" src="/js/common/noSearchStandardGridmain.js"></script>
<!-- These are for the Grid-->
<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxcommon.js"></script>
<script type="text/javascript" src="/dhtmlxWindows/codebase/dhtmlxwindows.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxgrid.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxgridcell.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_form.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_srnd.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_json.js"></script>

<!-- Add any other javascript you need for the page here -->
<script type="text/javascript" src="/js/common/admin/companyPermissions.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/excells/dhtmlxgrid_excell_customized.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxcommon_haas.js"></script>

<title>
  <fmt:message key="label.companyPermissionsTitle"/> (${fullName})
</title>


<script language="JavaScript" type="text/javascript">
<!--
//add all the javascript messages here, this for internationalization of client side javascript messages
var messagesData = new Array();
messagesData={alert:"<fmt:message key="label.alert"/>",and:"<fmt:message key="label.and"/>",
recordFound:"<fmt:message key="label.recordFound"/>",
norowselected:"<fmt:message key="error.norowselected"/>",
searchDuration:"<fmt:message key="label.searchDuration"/>",minutes:"<fmt:message key="label.minutes"/>",seconds:"<fmt:message key="label.seconds"/>",
submitOnlyOnce:"<fmt:message key="label.submitOnlyOnce"/>"};


var config = [
{
  columnId:"permission"
},
{
  columnId:"companyAccessPermission"
},
{
  columnId:"adminRolePermission"
},
{
  columnId:"lockedPermission"
},
{ columnId:"companyName",
  columnName:'<fmt:message key="label.company"/>',
  width:15,
  submit:true,
  tooltip:"Y"
},
{ columnId:"companyAccess",
  columnName:'<fmt:message key="label.access"/><br><input type="checkbox" value="" onClick="return checkAll(\'companyAccess\');" name="checkAllAccess" id="checkAllAccess">',
  width:8,
  align:'center',
  type:'hchstatus'
},
{ columnId:"adminRole",
  columnName:'<fmt:message key="label.admin"/>',
  width:8,
  align:'center',
  type:'hchstatus'
},
{ columnId:"locked",
  columnName:'<fmt:message key="label.locked"/>',
  width:8,
  align:'center',
  type:'hchstatus'
},
{
  columnId:"companyId",
  submit:true
},
{
  columnId:"personnelId",
  submit:true
},
{
  columnId:"userAccess"
},
{
  columnId:"oldCompanyAccess"
},
{
  columnId:"oldAdminRole"
},
{
  columnId:"oldLocked"
},
{
  columnId:"updated"
}
    ,
    {
      columnId:"itarRolePermission"
    },
    {
      columnId:"itarRole"<c:if test="${showItarControl == 'true'}">,
      columnName:'<fmt:message key="label.itarrole"/>',
      type:'hcoro',
      onChange:itarRoleChanged,
      width:12,
      permission:true
      </c:if>
    }
]

// -->
</script>
</head>

<body bgcolor="#ffffff" onload="resultOnLoad();" onresize="resizeFrames()">
<tcmis:form action="/showcompanypermissions.do" onsubmit="return submitFrameOnlyOnce();">

<div class="interface" id="resultsPage">

<!-- You can build your error messages below. But we want to trigger the pop-up from the main page.
So this is just used to feed the pop-up in the main page.
Similar divs would have to be built to show any other messages in a pop-up.-->
<!-- Error Messages Begins -->
<div id="errorMessagesAreaBody" style="display:none;">
  ${tcmISError}<br/>
  <c:forEach items="${tcmISErrors}" varStatus="status">
  	${status.current}<br/>
  </c:forEach>
</div>

<script type="text/javascript">
<!--
/*Check if there is any error messages to show and set the variable you use in javascript to true or false.*/
<c:choose>
   <c:when test="${empty tcmISErrors and empty tcmISError}">
    showErrorMessage = false;
   </c:when>
   <c:otherwise>
    showErrorMessage = true;
   </c:otherwise>
</c:choose>
//-->
</script>
<!-- Error Messages Ends -->

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
    <div class="boxhead"> 
       <div id="mainUpdateLinks" style="display:none"> <%-- mainUpdateLinks Begins --%>
      <span id="updateResultLink" style="display:none">
         <a href="#" onclick="companyPermissionsUpdate(); return false;"><fmt:message key="label.update"/></a> |
      </span>
      <a href="#" onclick="createXSL();"><fmt:message key="label.createExcel"/></a> |
      <a href="#" onclick="window.close()"><fmt:message key="label.cancel"/></a>
      &nbsp;
     </div> <%-- mainUpdateLinks Ends --%>
    </div> <%-- boxhead Ends --%>
   <div class="dataContent">
     <!--Give the div name that holds the grid the same name as your viewbean or dynabean you want for updates-->
<div id="UserCompanyAdminViewBean" style="width:100%;height:600px;" style="display: none;"></div>
<!-- Search results start -->
<c:if test="${UserCompanyAdminViewBeanCollection != null}" >
<script type="text/javascript">
 <!--
    var itarRole = new Array();
    <c:set var="dataCount" value='${0}'/>
    <c:if test="${!empty UserCompanyAdminViewBeanCollection}" >
        /*Storing the data to be displayed in a JSON object array.*/
        var jsonMainData = new Array();
        var jsonMainData = {
        rows:[
        <c:forEach var="bean" items="${UserCompanyAdminViewBeanCollection}" varStatus="status">
        <c:if test="${status.index > 0}">,</c:if>

        <c:set var="readonly" value="N"/>
        <c:set var="adminreadonly" value="N"/>
        <c:set var="lockreadonly" value="N"/>
        <c:if test="${status.current.userAccess == 'Admin'}">
            <c:set var="readonly" value="Y"/>
            <c:set var="lockreadonly" value="Y"/>
            <c:set var="adminCount" value='${1}'/>
        </c:if>

        <c:if test="${status.current.userAccess == 'Grant Admin'}">
            <c:set var="adminreadonly" value="Y"/>
            <c:set var="readonly" value="Y"/>
            <c:set var="lockreadonly" value="Y"/>
            <c:set var="adminCount" value='${1}'/>
        </c:if>

        <c:if test="${status.current.userAccess == 'Unlock' && status.current.locked == 'Y'}">
            <c:set var="lockreadonly" value="Y"/>
            <c:set var="adminCount" value='${1}'/>
        </c:if>

        <c:set var="accessChecked" value="false"/>
        <c:if test="${status.current.companyAccess eq 'Y'}">
            <c:set var="accessChecked" value="true"/>
        </c:if>

        <c:set var="adminChecked" value="false"/>
        <c:if test="${status.current.adminRole == 'Admin' || status.current.adminRole == 'Grant Admin'}">
            <c:set var="adminChecked" value="true"/>
        </c:if>

        <c:set var="lockChecked" value="false"/>
        <c:if test="${status.current.locked == 'Y'}">
            <c:set var="lockChecked" value="true"/>
        </c:if>

        <c:set var="itarPermission" value="N"/>
        <c:if test="${status.current.userItarRole == 'Admin' || status.current.userItarRole == 'Grant Admin'}">
            <c:set var="itarPermission" value="Y"/>
            <c:set var="adminCount" value='${1}'/>    
        </c:if>


        /*The row ID needs to start with 1 per their design.*/
        { id:${status.count},
         data:['Y',
               '${readonly}',
               '${adminreadonly}',
               '${lockreadonly}',
             	'<tcmis:jsReplace value="${status.current.companyName}" />',
             	${accessChecked},
             	${adminChecked},
             	${lockChecked},
             	'${status.current.companyId}',
             	'${status.current.personnelId}',
             	'${status.current.userAccess}',
             	'${status.current.companyAccess}',
             	'${status.current.adminRole}',
             	'${status.current.locked}',
             	'',
             	'${itarPermission}',
                '${status.current.userItarRole}'
              ]}

          <c:set var="dataCount" value='${dataCount+1}'/>
        </c:forEach>
        ]};

        <%-- start of Itar Control --%>
        <c:if test="${showItarControl == 'true'}">
            <c:forEach var="bean" items="${UserCompanyAdminViewBeanCollection}" varStatus="status">
                itarRole['${status.count}']=[
                        {
                            text:'<fmt:message key="label.noaccess"/>',
                            value:''
                        },
                        {
                            text:'<fmt:message key="label.access"/>',
                            value:'User'
                        }
                    <c:if test="${status.current.userItarRole == 'Grant Admin' || status.current.itarRole == 'Admin'}">
                        ,
                        {
                            text:'<fmt:message key="label.admin"/>',
                            value:'Admin'
                        }
                    </c:if>
                    <c:if test="${status.current.userItarRole == 'Grant Admin'}">
                    ,
                    {
                        text:'<fmt:message key="label.grantadmin"/>',
                        value:'Grant Admin'
                    }
                </c:if>                
                ];
            </c:forEach>
        </c:if>
        <%-- end of Itar Control --%>
    </c:if>
// -->
</script>

<!-- If the collection is empty say no data found -->
<c:if test="${empty UserCompanyAdminViewBeanCollection}" >
<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableNoData" id="resultsPageTable">
 <tr>
  <td width="100%">
   <fmt:message key="main.nodatafound"/>
  </td>
 </tr>
</table>
</c:if>
</c:if>
<!-- Search results end -->
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

<!-- Hidden element start -->
<div id="hiddenElements" style="display: none;">
<input name="totalLines" id="totalLines" value="${dataCount}" type="hidden"/>
<%--Store the search parameters.--%>
<input name="uAction" id="uAction" value="${param.uAction}" type="hidden"/>
<input name="companyId" id="companyId" value="${param.companyId}" type="hidden"/>
<input name="personnelId" id="personnelId" value="${param.personnelId}" type="hidden"/>
<input name="startSearchTime" id="startSearchTime" value="${startSearchTime}" type="hidden" />
<input name="endSearchTime" id="endSearchTime" value="${endSearchTime}" type="hidden" />
<input name="personnelId" id="personnelId" value="${personnelBean.personnelId}" type="hidden" />
<input name="minHeight" id="minHeight" value="100" type="hidden" />
<input name="adminCount" id="adminCount" value="${adminCount}" type="hidden" />
<input name="fullName" id="fullName" value="${fullName}" type="hidden" />
</div>
<!-- Hidden elements end -->

</div> <!-- close of interface -->
<c:if test="${adminCount > 0}">
    <script type="text/javascript">
        <!--
        showUpdateLinks = true;
        //-->
    </script>
</c:if>


</tcmis:form>

<!-- You can build your error messages below.
     Similar divs would have to be built to show any other messages in a inline pop-up.-->
<!-- Error Messages Begins -->
<div id="errorMessagesArea" class="errorMessages" style="display: none;overflow: auto;">
</div>

</body>
</html:html>

