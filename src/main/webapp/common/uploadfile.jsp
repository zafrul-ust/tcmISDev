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
	<%-- Add any other stylesheets you need for the page here --%>
	<style type="text/css">
		div.multiupload{
			border:1px outset lightblue;
			padding:3px;
			background:#eee;
		}
		div.multiupload div.list{
			border:1px inset lightblue;
			background: #fff;
		}
		div.multiupload div.list div.item{
			margin:1px;
			background: #eee;
		}
		div.multiupload div.list div.item:hover{
			background: #ccc;
		}
		div.multiupload div.list img{
			float:left;
			margin: 3px;
			cursor:pointer;
		}
	</style>	

	<script type="text/javascript" src="/js/common/formchek.js"></script>
	<script type="text/javascript" src="/js/common/commonutil.js"></script>
	<script type="text/javascript" src="/js/common/disableKeys.js"></script>
	
	<!-- This handels the menu style and what happens to the right click on the whole page -->
	<script type="text/javascript" src="/js/menu/milonic_src.js"></script>
	<script type="text/javascript" src="/js/menu/mmenudom.js"></script>
	<script type="text/javascript" src="/js/menu/mainmenudata.js"></script>
	<script type="text/javascript" src="/js/menu/contextmenu.js"></script>
	<%@ include file="/common/rightclickmenudata.jsp" %>
	
	
	<%-- Add any other javascript you need for the page here --%>
	<script type="text/javascript" src="/js/common/fileUpload/mootools.js"></script>
	<script type="text/javascript" src="/js/common/fileUpload/Stickman.MultiUpload.js"></script>
	<script type="text/javascript" src="/js/common/fileUpload/validatefileextension.js"></script>
	
	<title>
		<fmt:message key="label.uploadfile"/>
	</title>
	
	<script language="JavaScript" type="text/javascript">
	<!--
        windowCloseOnEsc = true;
        //add all the javascript messages here, this for internationalization of client side javascript messages
		var messagesData = new Array();
		messagesData={
			alert:"<fmt:message key="label.alert"/>",
			and:"<fmt:message key="label.and"/>",
			validvalues:"<fmt:message key="label.validvalues"/>",
			maxUpload:"<fmt:message key="label.maxupload"/>",
			verifyRemove:"<fmt:message key="label.verifyremovefromqueue"/>",
			filetypenotallowed:"<fmt:message key="label.filetypenotallowed"/>",
			submitOnlyOnce:"<fmt:message key="label.submitOnlyOnce"/>"
			};
	
		function closeThisWindow() {
			try {
		 		opener.closeTransitWin();
			} catch(ex) {}
		}
	
		function initializeThisWindow() {
			<c:if test="${'OK' == result}">
			try {
	  			opener.showTransitWin();
                if ('catalogAddSpec' == $v("modulePath"))
                    opener.specOnlineUpdate();
                else if('MSDS' == $v("modulePath")){
                	opener.updateContent('${files[0]}',$v("imageLocaleCode"), $v("localeDisplay"));
                }
			} catch(ex) {}
            </c:if>
			<c:choose>
				<c:when test="${not empty param.allowMultiple}">
					// unlimited files, name allFiles[0],  remove path from file name, remove extra empty element on upload
					new MultiUpload(document.uploadForm.allFiles, 0, "[{id}]", true, true );
					<fmt:message var="upload" key="label.uploadfiles"/>
					<fmt:message var="sendfile" key="label.sendfiles"/>
				</c:when>
				<c:otherwise>
					// 1 file, name allFiles[#], remove path from file name, remove extra empty element on upload
					new MultiUpload(document.uploadForm.allFiles, 1, "[{id}]", true, true );
					<fmt:message var="upload" key="label.uploadfile"/>
					<fmt:message var="sendfile" key="label.sendfile"/>
				</c:otherwise>
			</c:choose>
			<c:if test="${'MSDS' == param.modulePath}">
				$("language").style.display= "";
			</c:if>
		}
		
		function onSubmit() {
			var localeDropdown = $("locale");
			$("localeDisplay").value = localeDropdown.options[localeDropdown.selectedIndex].text;
			$("locale").disabled = false;
			return validateFileType();
		}
		
		function validateFileType() {
			var form = document.uploadForm;
			for(var i=0;i<form.elements.length;i++){
			    var e = form.elements[i];
			    
			    if("file" == e.type && e.value != null && e.value.trim().length > 0) {
			    	var extension = e.value.trim().split('.').pop().toLowerCase();
			    	if('MSDS' == $v("modulePath")) {
			    		if (extension != "pdf") {
				    		alert(messagesData.filetypenotallowed);
							return false;
			    		}
			    	}
			    	else if(validUploadFileExtension.indexOf(extension) == -1) {
				    	alert(messagesData.filetypenotallowed);
						return false;
					} 
			    }
			}

			return true;
		}
		
	// -->
	</script>
	
	<c:set var="defaultLocale" value="${param.locale}"/>
	<c:if test="${empty param.locale}">
	<c:set var="defaultLocale" value="en_US"/>
   		<c:forEach var="localeBean" items="${localeCodeColl}"  varStatus="status">
         		<c:if test="${status.current.localeCode == browserLocale }">
				<c:set var="defaultLocale" value="${browserLocale}"/>     
	     		</c:if>
   		</c:forEach>
   	</c:if>
</head>

<body bgcolor="#ffffff" onload="initializeThisWindow();" onunload="closeThisWindow();">
	<tcmis:form action="/uploadfile.do" onsubmit="return submitOnlyOnce();" enctype="multipart/form-data">

		<div id="transitPage" class="optionTitleBoldCenter" style="display: none;">
 			<br><br><br><fmt:message key="label.pleasewait"/>
 			<br><br><br><img src="/images/rel_interstitial_loading.gif" align="middle">
		</div>

		<div class="interface" id="mainPage">
			<div class="contentArea">
				<table id="searchMaskTable" width="100%" border="0" cellpadding="0" cellspacing="0">
					<tr>
						<td>
							<div class="roundcont filterContainer">
	 							<div class="roundright">
	   								<div class="roundtop">
	     									<div class="roundtopright">
	     										<img src="/images/rndBoxes/borderTL_filter.gif" alt="" width="15" height="10" class="corner_filter" style="display: none" />
	     									</div>
	   								</div>
	   								<div class="roundContent">
										<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableSearch">
      										<tr>
      											<td>
        											<center>${upload}</center>
      											</td>
      										</tr>
      										<tr valign="top" id="language" style="display: none;">
												<td class="optionTitleBoldLeft">
													<fmt:message key="label.language" />: &nbsp;&nbsp;
												 
													<select class="selectBox" name="locale" id="locale" ${param.localeLocked eq 'Y'?'disabled':''}>
														<c:forEach var="localeBean"
															items="${localeCodeColl}" varStatus="status">
															<option value="${status.current.localeCode}"
																<c:if test="${status.current.localeCode == defaultLocale}">selected="selected"</c:if>>${status.current.localeDisplay}</option>
														</c:forEach>
                                                    </select>
												</td>
											</tr>
											<tr>
												<td>
												 	<center><input type="file" id="allFiles" name="allFiles" name="allFiles" size="50" value="allFiles"></center>
												</td>
											</tr>
											<tr>
												<td>
													<center><input name="uploadFile" id="uploadFile" type="submit" value="${sendfile}" class="inputBtns" 
																	onclick="return onSubmit();" onmouseover="this.className='inputBtns inputBtnsOver'" onmouseout="this.className='inputBtns'"></center>
      											</td>
      										</tr>
      										
    									</table>
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

				<div class="spacerY">&nbsp;</div>

				<!-- Error Messages Begins -->
				<div id="errorMessagesArea" class="errorMessages">
					<html:errors/>
					<c:if test="${'File Type not allowed.' == tcmISError}">
						<fmt:message key="label.filetypenotallowed"/>
					</c:if>
					<c:if test="${'OK' == result}">
						<fmt:message key="label.successfulupload"/>
					</c:if>
				</div>

				 <div id="hiddenElements" style="display: none;">
					 <input type="hidden" name="fileName" id="fileName" value='${param.fileName}'/>
					 <input type="hidden" name="modulePath" id="modulePath" value='${param.modulePath}'/>
                     <input type="hidden" name="companyId" id="companyId" value='${param.companyId}'/>
                     <input type="hidden" name="requestId" id="requestId" value='${param.requestId}'/>
                     <input type="hidden" name="lineItem" id="lineItem" value='${param.lineItem}'/>
                     <input type="hidden" name="specId" id="specId" value='${param.specId}'/>
                     <input type="hidden" name="calledFrom" id="calledFrom" value='${param.calledFrom}'/>
                     <input type="hidden" name="imageLocaleCode" id="imageLocaleCode" value='${imageLocaleCode}'/>
                     <input type="hidden" name="localeDisplay" id="localeDisplay" value='${localeDisplay}'/>
                     <input type="hidden" name="localeLocked" id="localeLocked" value="<tcmis:jsReplace value="${param.localeLocked}"/>"/>
                 </div>
			</div>

			 <div class="messageBar">&nbsp;</div>
		</div>
	</tcmis:form>
</body>
</html:html>