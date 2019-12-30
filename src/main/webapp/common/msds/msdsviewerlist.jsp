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
	<meta http-equiv="content-type" content="text/html; charset=UTF-8"/>
	<meta http-equiv="expires" content="-1"/>
	<link rel="shortcut icon" href="https://www.tcmis.com/images/buttons/tcmIS.ico"></link>
	
	<%@ include file="/common/locale.jsp" %>
	
	<!--Use this tag to get the correct CSS class.
	This looks at what the user's preffered font size and which application he is viewing to set the correct CSS. -->
	<tcmis:gridFontSizeCss />
	<!-- Add any other stylesheets you need for the page here -->
	<link rel="stylesheet" type="text/css" href="/css/msds.css"/>
	
	<script type="text/javascript" src="/js/common/formchek.js"></script>
	<script type="text/javascript" src="/js/common/commonutil.js"></script>
	<%--NEW - grid resize--%>
	<script type="text/javascript" src="/js/common/grid/iframegridresizemain.js"></script>
	<!-- This handels which key press events are disabeled on this page -->
	<script src="/js/common/disableKeys.js"></script>
	
	<!-- These are for the Grid, uncomment if you are going to use the grid -->
	<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxcommon.js"></script>
	<script type="text/javascript" src="/dhtmlxLayout/codebase/dhtmlxlayout.js"></script>
	<script type="text/javascript" src="/dhtmlxWindows/codebase/dhtmlxwindows.js"></script>
	
	<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxgrid.js"></script>
	<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxgridcell.js"></script>
	<script type="text/javascript" src="/dhtmlxGrid/codebase/excells/dhtmlxgrid_excell_customized.js"></script>
	
	<!-- CSS for YUI -->
	<link rel="stylesheet" type="text/css" href="/yui/build/container/assets/container.css" />
	<link rel="stylesheet" type="text/css" href="/yui/build/fonts/fonts-min.css" />
	<link type="text/css" rel="stylesheet" href="/yui/build/treeview/assets/skins/sam/treeview.css"/>
	<script type="text/javascript" src="/yui/build/yahoo/yahoo-min.js" ></script>
	<script type="text/javascript" src="/yui/build/event/event-min.js" ></script>
	<script type="text/javascript" src="/yui/build/treeview/treeview-min.js" ></script>
	
	<script type="text/javascript" src="/js/jquery/jquery-1.6.4.js"></script>
	
<c:if test="${materialListCollection != null}" >
 <script type="text/javascript">
 <!--
 var dhxLayout = null;
 var messagesData = new Array();

    var previousNode = null;
    var selectedNode = null;
    function demoOnLoad() {
    	parent.hideTransitWin();
    	myResultFrameId = "reportListFrame";
	    var tree = new YAHOO.widget.TreeView("TreeRoot");
	    var rootNode = tree.getRoot();
        var curListNode = null;
        var curSublistNode = null;

        <c:set var="currentLine" value=''/>
        <c:set var="currentApprovalRole" value=''/>
        	curListNode = new YAHOO.widget.HTMLNode(
        			"<div class='listsodd'>"+
					"<span class='listscol listsnamecol listscolbold listscolcenter'>Lists</span>"+
					"<span class='listscol listscolbold listscolcenter'>On List</span>"+
					"<span class='listscol listscolbold listscolcenter'>Percent%</span></div>", rootNode, false,true);
	    <c:forEach var="listBean" items="${materialListCollection}" varStatus="status">
			<fmt:formatDate var="revDate" value="${listBean.revisionDate}" pattern="${dateFormatPattern}"/>
			var pct = ('${listBean.percent}' == "")?" ":'${listBean.percent}'+"%";
			curListNode = new YAHOO.widget.HTMLNode(
					"<div class='${status.index%2==0?'listseven':'listsodd'}' onclick='treeClickEvent(this, \"${listBean.lookupListId}\", \"${listBean.listName}\",\"\",\"${listBean.materialId}\",\"${revDate}\",\"${listBean.onList}\");'>"+
					"<span class='listscol listsnamecol'>"+
					"${listBean.listName}</span>"+
					"<span class='listscol listscolbold listscolcenter'>"+
					"${listBean.onList}</span>"+
					"<span class='listscol listscolcenter'>"+pct+"</span></div>", rootNode, false,true);
            
            <c:forEach var="sublistBean" items="${listBean.sublistColl}" varStatus="subStatus">
            	<c:if test="${listBean.listId eq sublistBean.listId}">
            		<fmt:formatDate var="revDateSub" value="${sublistBean.revisionDate}" pattern="${dateFormatPattern}"/>
            		var pct = ('${sublistBean.percent}' == "")?"":'${sublistBean.percent}'+"%";
            		curSublistNode = new YAHOO.widget.HTMLNode(
            				"<div class='${subStatus.index%2==0?'listseven':'listsodd'}' onclick='treeClickEvent(this, \"${sublistBean.lookupListId}\", \"${listBean.listName}\",\"${sublistBean.listName}\",\"${listBean.materialId}\",\"${revDate}\",\"${sublistBean.onList}\")'>"+
            				"<span class='listscol listsnamecol' style='margin-right:-18px;'>"+
                			"${sublistBean.listName}</span>"+
                			"<span class='listscol listscolbold listscolcenter'>"+
                			"${sublistBean.onList}</span>"+
                			"<span class='listscol listscolcenter'>"+pct+"</span></div>", curListNode, false,true);
                </c:if>
	        </c:forEach>
	    </c:forEach>

        tree.draw();
        
        tree.subscribe("expand",function(node){
        	// function executes after expand finishes, so we must put it in setTimeout,
        	// or it won't happen until the second expansion on every node
        	setTimeout('j$(".listsnamecol").css("width",(window.frameElement.clientWidth-265)+"px")',10);
        });

    }   //end of method

    function treeClickEvent(node, listId, listName, sublistName, materialId, revDate, onList) {
    	if (previousNode != node) {
		    showPleaseWait();
	    	var classlist = node.className.split(" ");
	    	classlist.push("listsselected")
	    	node.className = classlist.join(" ");
		    
		    var now = new Date();
			var startSearchTime =document.getElementById("startSearchTime");
		    startSearchTime.value = now.getTime();
		    
		    if( previousNode != null ) {
				classlist = previousNode.className.split(" ");
				for (var cls in classlist) {
					if (classlist[cls] === "listsselected") {
						classlist.splice(cls,1);
					}
				}
			    previousNode.className = classlist.join(" ");
		    }
		    previousNode = node;
	    	$("reportListFrame").src="msdsviewerlistresults.do?uAction=viewReportList&lookupListId="+listId+"&listName="+listName+"&sublistName="+sublistName+"&materialId="+materialId+"&revisionDate="+revDate+"&onlist="+onList;
    	}
    }
    
    function resizeTree() {
	    var height = window.frameElement.clientHeight*0.4;
	    var width = window.frameElement.clientWidth-30;
	    
	    document.getElementById("TreeRoot").style.height = height+"px";
	    j$(".listsnamecol").css("width",width-235+"px");
	    $("TreeRoot").style.width = width+"px";
	    if ( ! $("reportListFrame").src.endsWith("blank.html")) {
	    	setTimeout('$("reportListFrame").contentWindow.reSizeResultGrid();', 5);
	    }
    }
    
    function fillInReportLabels(listName, sublistName) {
    	document.getElementById("reportListName").innerHTML=listName;
    	document.getElementById("reportSublistName").innerHTML=( ! sublistName)?"N/A":sublistName;
    }

// -->
</script>
</c:if>
</head>
<body bgcolor="#ffffff" onload="demoOnLoad();resizeTree();loadLayoutWin('','msdsViewerList');" onresize="resizeTree();">

<div class="interface" id="mainPage">
<div id="searchFrameDiv">
	<!-- open content area -->
	<div class="contentArea">
		<table id="searchMaskTable" border="0" cellpadding="0" cellspacing="0">
			<tr>
				<td>
<div class="roundcont contentContainer">
<div class="roundright">
  <div class="roundtop">
    <div class="roundtopright"> <img src="/images/rndBoxes/borderTL.gif" alt="" width="15" height="15" class="corner" style="display: none" /></div>
  </div>
  <div class="roundContent">    

<c:set var="dataCount" value='${0}'/>
<c:if test="${!empty materialListCollection}" >
	<%--<div id="listDiv"> --%>
		<b><fmt:message key="label.lists"/></b>
		<div id="TreeRoot" class="treeClass" style="height:100%;overflow:auto;background-color:white;"></div>
	<%--</div> --%>
</c:if>

<c:if test="${empty materialListCollection}" >
<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableNoData" id="resultsPageTable">
 <tr>
  <td width="100%">
   <fmt:message key="main.nodatafound"/>
  </td>
 </tr>
</table>
</c:if>

  </div>
  <div class="roundbottom">
    <div class="roundbottomright"> <img src="/images/rndBoxes/borderBL.gif" alt="" width="15" height="15" class="corner" style="display: none" /> </div>
  </div>
</div>
</div>
</td>
</tr>
</table>
<div id="hiddenElements" style="display: none;">
	<input name="startSearchTime" id="startSearchTime" type="hidden" value="" /> 
	<input name="endSearchTime" id="endSearchTime" type="hidden" value=""/> 
	<input name="maxData" id="maxData" type="hidden" value="1000"/>
</div>
</div>

</div>
     
<!-- Result Frame Begins -->
<div id="resultFrameDiv" style="margin: 4px 4px 0px 4px;">
	<%-- Transit Page Starts --%>
	<div id="transitPage" style="display: none;" class="optionTitleBoldCenter">
		<br>
		<br>
		<br>
		<fmt:message key="label.pleasewait" />
		<br>
		<br>
		<br>
		<img src="/images/rel_interstitial_loading.gif" align="middle">
	</div>

	<div id="resultGridDiv" style="display: none;">
		<%-- Search results start --%> 
		<%-- If you want your results section to span only 50% set this to 50% or 800 pixels. Make sure to set resizeGridWithWindow to false. --%>
		<table id="resultsMaskTable" width="100%" border="0" cellpadding="0" cellspacing="0">
			<tr>
				<td valign="top">
					<div class="roundcont contentContainer">
						<div class="roundright">
							<div class="roundtop">
								<div class="roundtopright">
									<img src="/images/rndBoxes/borderTL.gif" alt="" width="15" height="15" class="corner" style="display: none" />
								</div>
							</div>
							<div class="roundContent">
								<div class="boxhead">
									<%-- Place all update links in divs that can be turned on or off depending on the search criteria that is selected
									     Do not remove mainUpdateLinks div, even if you don't have any links. You can remove content inside the div but not the div itself.
									--%>
									<div id="mainUpdateLinks"> <%-- mainUpdateLinks Begins --%>
								    	List: <span id="reportListName"></span><br />
								    	Sub List: <span id="reportSublistName"></span>
								    </div> <%-- mainUpdateLinks Ends --%>
								</div>
								<div class="dataContent">
									<iframe scrolling="no" id="reportListFrame" name="reportListFrame" width="100%" height="" frameborder="0" marginwidth="0" src="/blank.html"></iframe>
								</div>
						
								<%-- result count and time --%>
								<div id="footer" class="messageBar"></div>
						
							</div>
							<div class="roundbottom">
								<div class="roundbottomright">
									<img src="/images/rndBoxes/borderBL.gif" alt="" width="15" height="15" class="corner" style="display: none" />
								</div>
							</div>
						</div>
					</div>
				</td>
			</tr>
		</table>
	</div>
</div>
	<!-- Result Frame Ends -->




</div> <!-- close of interface -->

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

showUpdateLinks = true;
//-->
</script>

</body>
</html:html>


