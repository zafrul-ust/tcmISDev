var da = (document.all) ? 1 : 0;
var pr = (window.print) ? 1 : 0;
var mac = (navigator.userAgent.indexOf("Mac") != -1);
var winact="msds"
var clientname =""
var specrevdate = ""
var specid = "";

img1on=new Image();
img1on.src="/images/buttons/comp/2comp.gif";
img1off=new Image();
img1off.src="/images/buttons/comp/1comp.gif";

img2on=new Image();
img2on.src="/images/buttons/prop/2prop.gif";
img2off=new Image();
img2off.src="/images/buttons/prop/1prop.gif";

img3on=new Image();
img3on.src="/images/buttons/lists/2lists.gif";
img3off=new Image();
img3off.src="/images/buttons/lists/1lists.gif";

img5on=new Image();
img5on.src="/images/buttons/2msds.gif";
img5off=new Image();
img5off.src="/images/buttons/1msds.gif";

imghelpon=new Image();
imghelpon.src="/images/help_down.gif";
imghelpoff=new Image();
imghelpoff.src="/images/help_up.gif";


function myResize() {
	setWindowSizes();
	
//	var iFrameWidth = myWidth - $("searchFrameDiv").offsetWidth - 25;
	var iFrameHeight = myHeight - $("searchFrameDiv").offsetHeight - 34;
//	$("resultFrame").width = iFrameWidth + "px";
	$("resultFrame").height = iFrameHeight + "px";
}

function insertprintLink()
{
	if (isIE)
	{
		var target1 = document.getElementById("printlinkie");
		target1.style.display = "none";
	
		document.write("<BR><INPUT TYPE=\"BUTTON\" ID=\"printLink\" ONCLICK=\"return printPage(parent.resultFrame, this)\" value=\"Print MSDS\">\n");
	}
	else
	{
		document.write("<BR><A HREF=\"#\" onclick=\"openMSDS();\" STYLE=\"color:#0000ff;cursor:hand;\"><U>Open MSDS</U></A>\n");
	}
}

function printPage(frame, arg) {
  if (frame == window) {
    printThis();
  } else {
    link = arg; // a global variable
    printFrame(frame);
  }
  return false; 
}

function printThis() {
 
  if (pr) { // NS4, IE5
    window.print();
  } else if (da && !mac) { // IE4 (Windows)
    vbPrintPage();
  } else { // other browsers
    alert("Sorry, your browser doesn't support this feature.");
  }
}

function printFrame(frame) {
  if (pr && da) { // IE5
    frame.focus();
    window.print();
    link.focus();
  } else if (pr) { // NS4
    frame.print();
  } else if (da && !mac) { // IE4 (Windows)
    frame.focus();
    setTimeout("vbPrintPage(); link.focus();", 100);
  } else { // other browsers
    alert("Sorry, your browser doesn't support this feature.");
  }
}

String.prototype.endsWith = function(str)
{return (this.match(str+"$")==str)}

function myonload() {
	// will call changeView, but will set materialId first if there is none
	if ($v("materialId") === "") {
		changeMsds();
	}else {
		changeView();
	}
	
	try {
		if($v("msdsUrl").endsWith(".txt") || $v("msdsUrl").endsWith(".html"))
			$("openMSDSSpan").style.display="";
		else
			$("openMSDSSpan").style.display="none"
	} catch(ex) {}

    displayNotice();
    displayCompanyStandard();

}

function contactUs() {
	imgOff( 'msdsTd'); imgOff( 'compositionTd'); imgOff( 'propertiesTd'); imgOff( 'listsTd'); imgOff( 'shippingTd');
	
	$("currentView").value = "contactUs";
	
	var loc = '/tcmIS/erroremail/msdsspec?spec_hold=&act=msds&material_id='+$v("materialId")+'&revision_date='+$v("revisionDate")+'&localeCode='+$v("localeCode")+'&facility='+encodeURIComponent($v("facility"))+'&cl='+$v("module")+'&catpartno='+$v("catpartno")+'&url='+$v("msdsUrl");
	
	window.frames["resultFrame"].location = loc;
}

function showMsdsDocuments() {
	openWinGeneric('showmsdsdocuments.do?showDocuments=Yes&materialId='+ $v("materialId")+'&facilityId='+encodeURIComponent($v('facility'))+'&companyId='+encodeURIComponent($v('companyId'))+'&documentTypeSource=Catalog'
			,"showMsdsDocuments","800","350",'yes' );
}

function compareRevision() {
	openWinGeneric('revisioncomparision.do?materialId='+ $v("materialId") 
			,"compareRevision","800","350",'yes' );
}


function addEditSynonym() {
	openWinGeneric('addeditsynonym.do?materialId='+ $v("materialId") +
			'&facilityId=' + encodeURIComponent($v('facility'))
			,"addEditSynonym","450","300",'no' );
}

function uploadRevision() {
	openWinGeneric('uploadrevision.do?uAction=uploadRevision&materialId='+ $v("materialId") +
			'&facilityId=' + encodeURIComponent($v('facility'))
			,"uploadRevision","620","560",'yes' );
}

function viewContactLogs() {
	openWinGeneric('viewcontactlogs.do?currentMaterialId='+ $v("materialId") +
			'&revisionDateString=' + $v('revisionDateTimeStamp')+'&calledFrom=viewMsds'
			,"viewContactLogs","750","360",'yes' );
}


function openMSDS(){
	loc = $v("msdsUrl");
	
	window.open(loc, "showMSDS",'height=800,width=800,resizable=yes,menubar=yes,scrollbars=yes,toolbar=0');
}

function changeView() {
	if($v("currentView") == "msds") {
		$("uAction").value =  "viewMsds";
		document.genericForm.target = 'resultFrame';
		document.genericForm.submit(); 
	} else if($v("currentView") == "composition") {
		$("uAction").value =  "viewComposition";
		document.genericForm.target = 'resultFrame';
		document.genericForm.submit(); 
	} else if($v("currentView") == "properties") {
		$("uAction").value =  "viewProperties";
		document.genericForm.target = 'resultFrame';
		document.genericForm.submit(); 
	} else if($v("currentView") == "lists") {
		showTransitWin();
		$("uAction").value =  "viewLists";
		document.genericForm.target = 'resultFrame';
		document.genericForm.submit(); 
	} else if($v("currentView") == "shipping") {
		$("uAction").value =  "viewShipping";
		document.genericForm.target = 'resultFrame';
		document.genericForm.submit(); 
	} else {
		contactUs();
	}
	
}

function showTransitWin() {
	$("transitPage").style.display = "";
	$("resultFrame").style.display = "none";
}

function hideTransitWin() {
	$("transitPage").style.display = "none";
	$("resultFrame").style.display = "";
}

function revisionDateChanged()
{
	var mySplitResult = $v("revisionDateAndLocale").split("|");
	$("revisionDate").value = mySplitResult[0];
	$("localeCode").value = mySplitResult[1];
	$("revisionDateTimeStamp").value = mySplitResult[2];
	changeView();
	
	callToServer("viewmsds.do?uAction=getMSDSInfo&facility="+$v("facility")+"&materialId="+$v("materialId")+"&revisionDate="+mySplitResult[0]+"&localeCode="+mySplitResult[1]+"&revisionDateTimeStamp="+mySplitResult[2]);
}

function changeMsds()
{
	var loc = "viewmsds.do?act=msds&spec=&facility="+$v("facility")+"&catpartno=&materialId="+$v("chosenMsds")+"&itemId="+$v("itemId");
	window.location = loc;
}

function displayNotice() {
    var tempVal = $v("notice");
    if (tempVal == 'Y') {
        $("caProp65NoticeDisplayDiv").style.display = '';
    }else {
        $("caProp65NoticeDisplayDiv").style.display = 'none';
    }
}

function displayCompanyStandard() {
    var actionText = '';
    if ($v("facility") != '')
        actionText = '<a href="#" onclick="showMsdsDocuments();"><u>'+messagesData.documents+'</u></a>';

    var tempVal = $v("revisionMeetsCompanyStandard");
    if (tempVal == 'N') {
        $("companyStandardDisplayDiv").style.display = '';
        $("upperSideViewSpan").style.display = 'none';
        $("lowerSideViewSpan").style.display = 'none';
        if ($v('currentView') != 'msds') {
            $('currentView').value='msds';
            doTitle('msdsTd');
            changeView();
        }
        if ($v("facility") != '') {
            if (actionText != '')
                actionText += ' | ';
            actionText += '<a href="#" onclick="addEditSynonym();"><u>'+messagesData.synonym+'</u></a>';
        }
    }else {
        $("companyStandardDisplayDiv").style.display = 'none';
        $("upperSideViewSpan").style.display = '';
        $("lowerSideViewSpan").style.display = '';
        //setting action options
        if ($v("hideMsdsViewerActionOptions") != 'Y') {
            if (actionText != '')
                actionText += ' | ';
            actionText += '<a href="#" onclick="compareRevision();"><u>'+messagesData.msdsRevisionComparision+'</u></a>';
            if ($v("facility") != '')
                actionText += ' | <a href="#" onclick="addEditSynonym();"><u>'+messagesData.synonym+'</u></a>';
            if ($v("showMsdsProperties") == 'true')
                actionText += ' | <a href="#" onclick="showPropertiesLog();"><u>'+messagesData.showPropertiesLog+'</u></a>';
            if ($v("editMsdsProperties") == 'Y')
                actionText += ' | <a href="#" onclick="editProperties();"><u>'+messagesData.editProperties+'</u></a>';
        }
    }

    if ($v("hideMsdsViewerActionOptions") != 'Y') {
        if (actionText != '')
            actionText += ' | ';
        actionText += '<a href="#" onclick="uploadRevision();"><u>'+messagesData.submitNewRevision+'</u></a>';
        actionText += ' | <a href="#" onclick="viewContactLogs();"><u>'+messagesData.viewContactLogs+'</u></a>';
    }
    $("actionSpan").innerHTML = actionText;
}  //end of method

function changeTitleInfo(tradeName, revisionDate, mfgDesc, msdsUrl, notice, revisionMeetsCompanyStandard) {
	$("tradeNameSpan").innerHTML = tradeName;
	$("revisionDateSpan").innerHTML = revisionDate;
	$("mfgDescSpan").innerHTML = mfgDesc;
	$("msdsUrl").value = msdsUrl;
    $("notice").value = notice;
    displayNotice();
    $("revisionMeetsCompanyStandard").value = revisionMeetsCompanyStandard;
    displayCompanyStandard();
} //end of method

function openwin( str )
{
   MSDS_Help=window.open( str,"MSDS_Help",
                          "toolbar=no,location=no,directories=no,status=yes" +
                          ",menubar=no,scrollbars=yes,resizable=yes," +
                          "top=100,left=2,width=800,height=600" );
}

function openwin2( str )
{
	MSDS_Problem=window.open( str,"MSDS_Problem",
                             "toolbar=no,location=no,directories=no,status=no" +
                             ",menubar=no,scrollbars=no,resizable=no," +
                             ",width=200,height=150" );
}

function doTitle( imgName )
{
	if ( winact == "spec" )
	{
	  parent.frames.title.location="/tcmIS/servlet/radian.web.servlets."+clientname.toLowerCase()+"."+clientname+"msdsSideView?SESSION=0&date="+specrevdate+"&id="+specid;
	}
	if ( imgName == "compositionTd" )
	{
	  imgOff( 'listsTd'); imgOff( 'msdsTd'); imgOff( 'propertiesTd'); imgOff( 'shippingTd');
	}
	if ( imgName == "listsTd" )
	{
	  imgOff( 'compositionTd'); imgOff( 'msdsTd'); imgOff( 'propertiesTd'); imgOff( 'shippingTd');
	}
	if ( imgName == "msdsTd" )
	{
	  imgOff( 'listsTd'); imgOff( 'compositionTd'); imgOff( 'propertiesTd'); imgOff( 'shippingTd');
	}
	if ( imgName == "propertiesTd" )
	{
	  imgOff( 'listsTd'); imgOff( 'compositionTd'); imgOff( 'msdsTd'); imgOff( 'shippingTd');
	}
	if ( imgName == "shippingTd" )
	{
	  imgOff( 'listsTd'); imgOff( 'compositionTd'); imgOff( 'propertiesTd'); imgOff( 'msdsTd');
	}
	imgOn( imgName );

}

function imgOn( tdId )
{
	if(tdId == 'imghelp')
		document[tdId].src=eval( tdId + "on.src" );
	else
		$(tdId).className = "clickblue";
}

function imgOff( tdId )
{
	if (tdId == 'imghelp')
		document[tdId].src=eval( tdId + "off.src" );
	else
		$(tdId).className = "unclickblue";
}