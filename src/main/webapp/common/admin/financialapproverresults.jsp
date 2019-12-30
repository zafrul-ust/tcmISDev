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
<script type="text/javascript" src="/js/common/resultiframeresize.js"></script>
<!-- This handels which key press events are disabeled on this page -->
<script src="/js/common/disableKeys.js"></script>

<!-- This handels the menu style and what happens to the right click on the whole page -->
<script type="text/javascript" src="/js/menu/milonic_src.js"></script>
<script type="text/javascript" src="/js/menu/mmenudom.js"></script>
<script type="text/javascript" src="/js/menu/mainmenudata.js"></script>
<script type="text/javascript" src="/js/menu/contextmenu.js"></script>
<%@ include file="/common/rightclickmenudata.jsp" %>

<!-- Add any other javascript you need for the page here -->
<script type="text/javascript" src="/js/common/admin/financialApprover.js"></script>

<title>
	<fmt:message key="label.facilityPermissionsTitle"/>  ()
</title>

<script language="JavaScript" type="text/javascript">
	//add all the javascript messages here, this for internationalization of client side javascript messages
	var messagesData = new Array();
	messagesData={
		alert:"<fmt:message key="label.alert"/>",
		and:"<fmt:message key="label.and"/>",
		validvalues:"<fmt:message key="label.validvalues"/>",
		submitOnlyOnce:"<fmt:message key="label.submitOnlyOnce"/>",
		pleaseselectapprover:"<fmt:message key="label.pleaseselectapprover"/>"
	};
	
	var treeClass = "mktree";  // instead of mktree
	var nodeClosedClass = "liClosed"; // instead of liClosed
	var nodeOpenClass = "liOpen";     // instead of liOpen
	var nodeBulletClass = "liBullet"; // instead of liBullet
	var nodeLinkClass = "bullet";     // instead of bullet
	
	//addEvent(window,"load",convertTrees);
	function addEvent(o,e,f) {
		if (o.addEventListener) {
			o.addEventListener(e,f,true);
			return true;
		} else if (o.attachEvent) {
			return o.attachEvent("on"+e,f);
		} else {
			return false;
		}
	}
	
	function setDefault(name,val) {
		if (typeof(window[name]) == "undefined" || window[name] == null) {
			window[name]=val;
		}
	}
	
	function expandTree(treeId) {
		var ul = document.getElementById(treeId);
		if (ul == null) {
			return false;
		}
		expandCollapseList(ul,nodeOpenClass);
	}
	
	function collapseTree(treeId) {
		var ul = document.getElementById(treeId);
		if (ul == null) {
			return false;
		}
		expandCollapseList(ul,nodeClosedClass);
	}
	
	function expandToItem(treeId,itemId) {
		var ul = document.getElementById(treeId);
		if (ul == null) {
			return false;
		}
		
		var ret = expandCollapseList(ul,nodeOpenClass,itemId);
		if (ret) {
			var o = document.getElementById(itemId);
			if (o.scrollIntoView) {
				o.scrollIntoView(false);
			}
		}
	}
	
	function expandCollapseList(ul,cName,itemId) {
		if (!ul.childNodes || ul.childNodes.length == 0 ) {
			return false;
		}
		
		for(var itemi = 0; itemi < ul.childNodes.length; itemi++) {
			var item = ul.childNodes[itemi];
			if (itemId != null && item.id == itemId) {
				return true;
			}
			if (item.nodeName == "li") {
				var subLists = false;
				for (var sitemi = 0; sitemi < item.childNodes.length; sitemi++) {
					var sitem = item.childNodes[sitemi];
					if (sitem.nodeName=="ul") {
						subLists = true;
						var ret = expandCollapseList(sitem,cName,itemId);
						if (itemId != null && ret) {
							item.className = cName;
							return true;
						}
					}
				}
				
				if (subLists && itemId == null) {
					item.className = cName;
				}
			}
		}
	}
	
	function convertTrees() {
		setDefault("treeClass","mktree");
		setDefault("nodeClosedClass","liClosed");
		setDefault("nodeOpenClass","liOpen");
		setDefault("nodeBulletClass","liBullet");
		setDefault("nodeLinkClass","bullet");
		setDefault("preProcessTrees",true);
		
		if(preProcessTrees) {
			if(!document.createElement) {
				return;
			}
			
			uls = document.getElementsByTagName("ul");
			for(var uli = 0; uli < uls.length; uli++) {
				var ul = uls[uli];
				if(ul.nodeName == "ul" && ul.className == treeClass) {
					processList(ul);
				}
			}
		} 
		
		alert('test');
	}
	
	function processList(ul) {
		if (!ul.childNodes || ul.childNodes.length == 0) {
			return;
		}
		for (var itemi = 0; itemi < ul.childNodes.length; itemi++) {
			var item = ul.childNodes[itemi];
			if(item.nodeName == "li") {
				var subLists = false;
				for (var sitemi = 0; sitemi < item.childNodes.length; sitemi++) {
					var sitem = item.childNodes[sitemi];
					if (sitem.nodeName == "ul") {
						subLists = true;
						processList(sitem);
					}
				}
				
				var s= document.createElement("SPAN");
				var t= '\u00A0';
				s.className = nodeLinkClass;
				if (subLists) {
					if(item.className == null || item.className=="") {
						item.className = nodeClosedClass;
					}
					if (item.firstChild.nodeName == "#text") {
						t = t+item.firstChild.nodeValue;
						item.removeChild(item.firstChild);
					}
					s.onclick = function(){this.parentNode.className =(this.parentNode.className==nodeOpenClass) ? nodeClosedClass : nodeOpenClass;return false;}
				} else {
					item.className = nodeBulletClass;
					s.onclick = function(){return false;}
				}
				s.appendChild(document.createTextNode(t));
				item.insertBefore(s,item.firstChild);
			}
		}
	}
	
	function changeNode() {
		alert('this');
	}
	
	function doNothing() {}
	
	var approverChild = new Array();
	var objs = new Array();
	var personIndex = new Array();
	var total = 0 ;
	var currentIndex = -1;
	var previousNode = null;
	var lastExpanded = new Array();
	function getCurrentObject() {
		if( currentIndex == -1 ) return null;
		return objs[currentIndex];
	}
	
	function demoOnLoad() {	
		var e = approverChild;
		var objArray = objs;
		var kids;
		var i = 0 ;
		personIndex[ '' ] = -1;
		
		<c:forEach var="pageNameViewBean" items="${ApplicationAdminBeanCollection}" varStatus="status">
			var obj = {
				index:i,
				personnelId:'${status.current.personnelId}',
				userName:'<tcmis:jsReplace value="${status.current.userName}"/>',
				label:'<tcmis:jsReplace value="${status.current.userName}"/>',
				approverId:'${status.current.approverId}',
				approverName:'<tcmis:jsReplace value="${status.current.approverName}"/>',
				costLimit:'${status.current.costLimit}',
				approvalLimit:'${status.current.approvalLimit}',
				currencyId:'${status.current.currencyId}',
				displayNode:null,
				inTree:false,
				href:'javascript:doNothing()',
				tree:objs
			};
			objArray[i] = obj;
			personIndex[ obj.personnelId ] = i;
			kids = e[obj.approverId];
			if( kids == null ) {	
				kids = new Array();
				e[obj.approverId] = kids; 
			}
			// build anonymous child object object.
			kids[kids.length] = { id:obj.personnelId,
								  ind:i
								 };
			i++;
		</c:forEach>
		setExpandedNode();
		total = i;
		
		if( i != 0 ) {
			var tree = new YAHOO.widget.TreeView("TreeRoot");
			var rootNode = tree.getRoot();
			BuildNode("",rootNode);
			tree.draw();
			
			tree.subscribe("labelClick", function(node) {
				if( currentIndex ==	node.data )
					currentIndex = -1;
				else
					currentIndex = node.data;
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
		setResultFrameSize();
		
		<c:if test="${FacilityAdmin}">
			showUpdateLinks = true;
		</c:if>
		
		if( showUpdateLinks == true ) {
			parent.document.getElementById('updateResultLink').style["display"]="";
		} else {
			parent.document.getElementById('updateResultLink').style["display"]="none";
		}
		
		var pprid = getSearchFrame().expandedNode;
		getSearchFrame().expandedNode = '';
		if( pprid != '' && personIndex[ pprid ] != null ) {
			var nnod = objs[ personIndex[ pprid ] ].displayNode;
			if( nnod != null ) {
				currentIndex = personIndex[ pprid ] ;
				var localid = nnod.labelElId;
				previousNode = localid;
				document.getElementById(localid).style["border"] = "solid 2px gray";
			}
		}
		var oripid = getSearchFrame().originalPersonnel;
		if( personIndex[oripid] == null && document.getElementById('notexist') != null)
			document.getElementById('notexist').style["display"]="";
	}
	
	// pass div id ( gaurantee to exist ) and approverId
	function getMaxLimit(pid) {
		var e = approverChild;
		var n = e[pid];
		var max = 0;
		for(var i = 0;i<n.length;i++) {
			var childInd = n[i].ind;
			var limit = parseInt(objs[childInd].approvalLimit);
			if( limit == -1 ) return -1;
			if( limit > max ) max = limit;
		}
		return max;
	}

	// pass div id ( gaurantee to exist ) and approverId
	function BuildNode(appId,rootNode) {
		var e = approverChild;
		var n = e[appId];
		
		var objArray = objs;
		var innerHTML = "";
		for(var i = 0;i<n.length;i++) {
			var childInd = n[i].ind;
			
            var tmpNode = null;
            if( lastExpanded[objs[childInd].personnelId] == true ) 
            	tmpNode = new YAHOO.widget.TextNode(objArray[childInd], rootNode, true );
            else
				tmpNode = new YAHOO.widget.TextNode(objArray[childInd], rootNode, false);   
            tmpNode.data = childInd;
            objs[childInd].displayNode = tmpNode;
            objs[childInd].inTree = true;
			if( e[n[i].id] != null ) {
				BuildNode(n[i].id,tmpNode);
		    }
		}
		return innerHTML;
	}
	
	function getTree() {
		return objs;
	}
	
	function getApprovalLimit(pid) {
		var tree = objs;
		var i = tree.length;
		for(; --i > 0; ) {
			if( tree[i].personnelId == pid ) {
				return parseInt(tree[i].approvalLimit);
			}
		}
		return -1;
	}
</script>
</head>

<body bgcolor="#ffffff" onload="demoOnLoad()">
	<tcmis:form action="/financialapproverresults.do" onsubmit="return submitOnlyOnce();">
		<!-- Check if the user has permissions and needs to see the update links,set the variable you use in javascript to true.
		The default value of showUpdateLinks is false.-->

		<!-- You can build your error messages below. But we want to trigger the YUI pop-up from the main page.
		So this is just used to feed the YUI pop-up in the main page.
		Similar divs would have to be built to show any other messages in a pop-up.-->

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
				<c:if test="${ApplicationAdminBeanCollection != null}" >
					<c:set var="colorClass" value=''/>
					<c:set var="dataCount" value='-1'/>
					<c:set var="detailCount" value='${0}'/>
					<c:set var="adminCount" value='${0}'/>
					<c:set var="firstLine" value='No'/>
					<c:set var="preBase" value="${0}"/>
					<c:set var="preCompany" value=""/>
					<c:set var="preFacility" value=""/>

					<c:if test="${!empty ApplicationAdminBeanCollection}" >
						<table width="100%" id="resultsPageTable" border="0" cellpadding="0" cellspacing="0" height="200" bgcolor="white">
							<tr>
								<td valign="top">
									${param.facilityName} <fmt:message key="label.financeapprover"/><span id="notexist" style="display:none">( User ${param.fullName} is not a financial approver )</span>
									<div id="TreeRoot" class="treeClass"></div>
								</td>
							</tr>
						</table>
					</c:if>

					<c:if test="${empty ApplicationAdminBeanCollection}" >
						<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableNoData" id="resultsPageTable">
							<tr>
								<td width="100%">
									<fmt:message key="main.nodatafound"/>
								</td>
							</tr>
						</table>
					</c:if>
				</c:if>

				<!-- Hidden element start -->
				<div id="hiddenElements" style="display: none;">
					<input name="uAction" id="uAction" value="update" type="hidden">
					<input name="personnelId" id="personnelId" value="${param.personnelId}" type="hidden">
					<input name="userId" id="userId" value="${userId}" type="hidden">
					<input type="hidden" name="companyId" id="companyId" value="${param.companyId}"/>
					<input type="hidden" name="facilityId" id="facilityId" value="${param.facilityId}"/>
					<input type="hidden" name="facilityName" id="facilityName" value="${param.facilityName}"/>
					<input name="totalLines" id="totalLines" value="${dataCount}" type="hidden">
					<input type="hidden" name="top_${preBase}" id="top_${preBase}" value="${dataCount}"/>
					<input type="hidden" name="approverId" id="approverId" value=""/>
					<input type="hidden" name="costLimit" id="costLimit" value=""/>
					<input type="hidden" name="approvalLimit" id="approvalLimit" value=""/>
					<input type="hidden" name="fullName" id="fullName" value="${param.fullName}" />
					<input type="hidden" name="FacilityAdmin" id="FacilityAdmin" value="${FacilityAdmin}" />
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