<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
	"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html;charset=UTF-8" language="java"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

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

<style type="text/css">
/*margin and padding on body element
  can introduce errors in determining
  element position and are not recommended;
  we turn them off as a foundation for YUI
  CSS treatments. */
body:action {
	color:green;
	margin:0;
	padding:0;
}
</style>

<tcmis:fontSizeCss />
<!-- CSS for YUI , I am not sure...
<link rel="stylesheet" type="text/css" href="/yui/build/container/assets/container.css" />
-->
<link rel="stylesheet" type="text/css" href="/yui/build/fonts/fonts-min.css" />
<link type="text/css" rel="stylesheet" href="/yui/build/treeview/assets/skins/sam/treeview.css">
<script src = "/yui/build/yahoo/yahoo-min.js" ></script>
<script src = "/yui/build/event/event-min.js" ></script>
<script src = "/yui/build/treeview/treeview-min.js" ></script>

<script type="text/javascript" src="/js/common/formchek.js"></script>
<script type="text/javascript" src="/js/common/commonutil.js"></script>
<script type="text/javascript" src="/js/common/grid/resultiframegridresize.js"></script>
<!-- This handels which key press events are disabeled on this page -->
<script src="/js/common/disableKeys.js"></script>

<!-- This handels the menu style and what happens to the right click on the whole page -->
<script type="text/javascript" src="/js/menu/milonic_src.js"></script>
<script type="text/javascript" src="/js/menu/mmenudom.js"></script>
<script type="text/javascript" src="/js/menu/mainmenudata.js"></script>
<script type="text/javascript" src="/js/menu/contextmenu.js"></script>
<%@ include file="/common/rightclickmenudata.jsp" %>

<!-- These are for the Grid-->
<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxcommon.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxgrid.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxgridcell.js"></script>
<%--This is for HTML form integration.--%>
<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_form.js"></script>
<%--This is for smart rendering.--%>
<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_srnd.js"></script>
<%--This is to suppoert loading by JSON.--%>
<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_json.js"></script>    
<%--Uncomment below if you are providing header menu to switch columns on/off--%>
<!--<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_hmenu.js"></script>-->
<%--Uncomment the below if your grid has rwospans >1--%>
<!--<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_rowspan.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/rowspan_cell_patch.js"></script>
-->
<%--This has the custom cells we built, hcal - the internationalized calendar which we use
    hlink- this is for any links you want tp provide in the grid, the URL/function to call
    can be attached based on a even (rowselect etc)
--%>
<script type="text/javascript" src="/dhtmlxGrid/codebase/excells/dhtmlxgrid_excell_customized.js"></script>
<%--This file has our custom sorting and other utility methos for the grid.--%>    
<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxcommon_haas.js"></script>

<!-- Add any other javascript you need for the page here -->

<title>
<fmt:message key="label.usergroupperms"/>  ()
</title>

<script language="JavaScript" type="text/javascript">
	var resizeGridWithWindow = false;
	//add all the javascript messages here, this for internationalization of client side javascript messages
	var messagesData = new Array();
	messagesData={
		alert:"<fmt:message key="label.alert"/>",
		and:"<fmt:message key="label.and"/>",
		validvalues:"<fmt:message key="label.validvalues"/>",
		submitOnlyOnce:"<fmt:message key="label.submitOnlyOnce"/>",
		pleaseselectapprover:"<fmt:message key="label.pleaseselectapprover"/>"
	};
	
	var altAllUserGroupUserIsAdmin = new Array(
		<c:forEach var="coDefUserGroupBean" items="${allUserGroupUserIsAdminCollection}" varStatus="status">
			<c:if test="${status.index > 0}">,</c:if>
			'${coDefUserGroupBean.userGroupId}'
		</c:forEach>
	);
</script>
</head>

<body bgcolor="#ffffff" onload="demoOnLoad()">
	<tcmis:form action="/companydefusergrouppermissionresults.do" onsubmit="return submitOnlyOnce();">
		<!-- You can build your error messages below. But we want to trigger the YUI pop-up from the main page.
		So this is just used to feed the YUI pop-up in the main page.Similar divs would have to be built to show any other messages in a pop-up.-->
		
		<!-- Error Messages Begins -->
		<div id="errorMessagesAreaBody" style="display:none;">
			<html:errors/>
			${tcmISError}
		</div>
		
		<script type="text/javascript">
			/*Check if there is any error messages to show and set the variable you use in javascript to true or false.*/
			<c:choose>
				<c:when test="${requestScope['org.apache.struts.action.ERROR'] == null and empty requestScope['tcmISError']}">
					showErrorMessage = false;
				</c:when>
				<c:otherwise>
					showErrorMessage = true;
				</c:otherwise>
			</c:choose>
		</script>
		<!-- Error Messages Ends -->
		
		<!-- start of interface -->
		<div class="interface" id="resultsPage">
			<!-- start of backGroundContent -->
			<div class="backGroundContent">
				<c:if test="${approvalUserGroupCollection != null}" >
					<script type="text/javascript">
						var childs = new Array();
						var objs = new Array();
						var memberOf = new Array();
						var ugIdArray = new Array();
						var total = 0 ;
						var currentIndex = -1;
						var currentXId = "";
						var previousNode = null;
						
						function doOnSelected(curUserGroupId) {
							var isAdmin = false;
							for (var i = 0; i < altAllUserGroupUserIsAdmin.length; i++)
								if (altAllUserGroupUserIsAdmin[i] == curUserGroupId) {
									isAdmin = true;
									break;
								}
							
							if (isAdmin)
								parent.document.getElementById('updateResultTreeLink').style["display"]="";
							else 
								parent.document.getElementById('updateResultTreeLink').style["display"]="none";
						}
						
						function demoOnLoad() {
							var e = childs;
							var objArray = objs;
							var kids;
							var i = 0 ;
							
							<c:forEach var="approvalUserGroupViewBean" items="${approvalUserGroupCollection}" varStatus="status">
								var obj = {
									index:${status.index},
									userGroupId:"${status.current.userGroupId}",
									userGroupDesc:"${status.current.userGroupDesc}",
									userGroupType:"${status.current.userGroupType}",
									label:"${status.current.userGroupDesc}",
									personnelId:'${status.current.personnelId}',
									userName:"${status.current.username}",
									role:"${status.current.role}",
									displayNode:null,
									inTree:false,
									href:'javascript:doOnSelected(${status.current.userGroupId})',
									tree:objs
								};
								
								objArray[i] = obj;
								kids = e[obj.userGroupId];
								if( kids == null ) {
									kids = new Array();
									e[obj.userGroupId] = kids;
									ugIdArray[ugIdArray.length] = obj.userGroupId;
								}
								// build child object object.
								kids[kids.length] = obj;
								//store data so I can expand node(s)
								if ('${param.personnelId}' == '${status.current.personnelId}') {
									memberOf[obj.userGroupId] = "Yes";
								}
								
								i++;
							</c:forEach>
							total = i;
							
							if( i != 0 ) {
								var tree = new YAHOO.widget.TreeView("TreeRoot");
								var rootNode = tree.getRoot();
								BuildNode(rootNode);
								tree.draw();
								
								tree.subscribe("labelClick", function(node) {
									currentIndex = node.data;
									currentXId = node.xID;
									var id = node.labelElId;
									document.getElementById(id).style["border"]  = "solid 2px gray";
									if( previousNode != null )
										document.getElementById(previousNode).style["border"] = "";
									if( previousNode == id )
										previousNode = null;
									else
										previousNode = id;
								});
							}
							
							parent.document.getElementById('transitPage').style["display"]="none";
							parent.document.getElementById('resultFrameDiv').style["display"]="";
							parent.document.getElementById('resultGridDiv').style["display"]="";
							setResultFrameSize();
							
							parent.document.getElementById('updateResultLink').style["display"]="none";
						}   //end of method
						
						// pass div id ( gaurantee to exist ) and approverId
						function BuildNode(rootNode) {
							var e = childs;
							var n = ugIdArray;
							
							var innerHTML = "";
							for(var i = 0;i<n.length;i++) {
								var ug = n[i];
								var kids = e[ug];
								var tmpNode = null;
								if (memberOf[kids[0].userGroupId] != null) {
									tmpNode = new YAHOO.widget.TextNode(kids[0], rootNode, true );
								} else {
									tmpNode = new YAHOO.widget.TextNode(kids[0], rootNode, false );
								}
								tmpNode.data = kids[0].index;
								tmpNode.xID="UG";
								
								for (var j = 0; j < kids.length; j++) {
									var tmpNode2 = null;
									if (kids[j].userGroupType == 'ReportPublish') {
										tmpNode2 = new YAHOO.widget.TextNode(kids[j].userName+" ("+kids[j].role+")", tmpNode, false );
									} else {
										tmpNode2 = new YAHOO.widget.TextNode(kids[j].userName, tmpNode, false );
									}
									tmpNode2.data = kids[j].index;
									tmpNode2.xID = "KID";
									kids[j].displayNode = tmpNode2;
									kids[j].inTree = true;
								}
							}
							
							return innerHTML;
						}
					</script>
					
					<c:choose>
						<c:when test="${!empty approvalUserGroupCollection}">
							<table width="100%" id="resultsPageTable" border="0" cellpadding="0" cellspacing="0" bgcolor="white">
								<tr>
									<td valign="top">
										<div id="TreeRoot" class="treeClass"></div>
									</td>
								</tr>
							</table>
						</c:when>
						<c:otherwise>
							<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableNoData" id="resultsPageTable">
								<tr>
									<td width="100%">
										<fmt:message key="main.nodatafound"/>
									</td>
								</tr>
							</table>
						</c:otherwise>
					</c:choose>
				</c:if>
				
				<!-- Hidden element start -->
				<div id="hiddenElements" style="display: none;">
					<input name="action" id="action" value="" type="hidden"/>
					<input name="personnelId" id="personnelId" value="${param.personnelId}" type="hidden"/>
					<input type="hidden" name="companyId" id="companyId" value="${param.companyId}"/>
					<input name="totalLines" id="totalLines" value="${fn:length(approvalUserGroupCollection)}" type="hidden"/>
					<input name="userGroupId" id="userGroupId" value="" type="hidden"/>
					<input name="userGroupAccess" id="userGroupAccess" value="" type="hidden"/>
					<input type="hidden" name="userGroupType" id="userGroupType" value="${param.userGroupType}" />
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