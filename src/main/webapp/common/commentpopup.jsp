<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="/WEB-INF/tcmis.tld" prefix="tcmis" %>
<html:html lang="true">
<head>
<meta http-equiv="content-type" content="text/html; charset=UTF-8"/>
<meta http-equiv="expires" content="-1"/>
<link rel="shortcut-icon" href="https://www.tcmis.com/images/buttons/tcmIS.ico"></link>
<%@ include file="/common/locale.jsp" %>
<tcmis:gridFontSizeCss />
<script type="text/javascript" src="/js/jquery/jquery-1.7.1.js"></script>
<script type="text/javascript" src="/js/common/grid/iframegridresizemain.js"></script>
<script type="text/javascript" src="/js/common/commonutil.js"></script>
<script type="text/javascript" src="/js/common/disableKeys.js"></script>
<script type="text/javascript" src="/js/menu/milonic_src.js"></script>
<script type="text/javascript" src="/js/menu/mmenudom.js"></script>
<script type="text/javascript" src="/js/menu/mainmenudata.js"></script>
<script type="text/javascript" src="/js/menu/contextmenu.js"></script>
<%@ include file="/common/rightclickmenudata.jsp" %>
<script type="text/javascript" src="/js/common/standardGridmain.js"></script>
<!-- These are for the Grid, uncomment if you are going to use the grid -->
<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxcommon.js"></script>
<script type="text/javascript" src="/dhtmlxLayout/codebase/dhtmlxlayout.js"></script>
<script type="text/javascript" src="/dhtmlxWindows/codebase/dhtmlxwindows.js"></script>
<script type="text/javascript" src="/js/common/popupservice.js"></script>
<script type="text/javascript" src="/js/common/commentpopup.js"></script>
<title><fmt:message key="label.comments"/></title>
</head>
<body>
	<div class="interface" id="mainPage" style=""><!-- Search Frame Begins -->
		<div id="searchFrameDiv"><%--NEW - removed the search frame and copied the search section here--%>
			<div class="contentArea">
				<!-- Search Option Begins -->
				<table id="searchMaskTable" width="100%" border="0" cellpadding="0" cellspacing="0">
				<tr>
				<td>
				<div class="roundcont filterContainer">
					<div class="roundright">
						<div class="roundtop">
							<div class="roundtopright"><img src="/images/rndBoxes/borderTL_filter.gif" alt="" width="15" height="10" class="corner_filter" style="display: none" /></div>
						</div>
						<div class="roundContent">
							<tcmis:jsReplace var="charLimit" value="${empty param.limit?'4000':param.limit}" processMultiLines="false"/>
							
							<label for="comment"><fmt:message key="label.pleaseentercomment"/></label><br />
							<textarea id="comment" rows="5" style="width:99%"></textarea><br />
							<label><fmt:message key="label.limit"/>&nbsp;<c:out value="${charLimit}"/></label><br />
							<input type="button" id="submit" class="inputBtns" value="<fmt:message key="label.submit"/>"/>
							<input type="button" id="cancel" class="inputBtns" value="<fmt:message key="label.cancel"/>"/>
							
							<input type="hidden" id="limit" value="${charLimit}"/>
						</div>
						<div class="roundbottom">
							<div class="roundbottomright"><img src="/images/rndBoxes/borderBL.gif" alt="" width="15" height="15" class="corner" style="display: none" /></div>
						</div>
					</div>
				</div>
				</td>
				</tr>
				</table>
			</div>
			<!-- close of contentArea -->
		</div>
	</div>
</body>
</html:html>