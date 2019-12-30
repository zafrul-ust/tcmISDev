function companyChanged() {
  var companyIdO = document.getElementById("companyId");
  var facilityIdO = document.getElementById("facilityId");
  var selectedCompany = companyIdO.value;

  for (var i = facilityIdO.length; i > 0;i--) {
    facilityIdO[i] = null;
  }

  showFacility(selectedCompany);
  facilityIdO.selectedIndex = 0;
}

function showFacility(selectedCompany) {
  var facilityIdArray = new Array();
  facilityIdArray = altFacilityId[selectedCompany];
  var facilityNameArray = new Array();
  facilityNameArray = altFacilityName[selectedCompany];

  for (var i=0; i < facilityIdArray.length; i++) {
    setOption(i,facilityNameArray[i],facilityIdArray[i], "facilityId")
  }
}

function mySearchOnLoad()
{
  if ( $("fixedCompanyId") != null && $v("fixedCompanyId") =='Y' )
  {
  		$("companyId").value = 'Radian';
		document.genericForm.companyId.disabled = true;
  }
  
  var searchText = document.getElementById("searchText");
  if (searchText != null && searchText.value.length != 0) {
	  var now = new Date();
	  document.getElementById("startSearchTime").value = now.getTime();
	  var userAction = document.getElementById("action");
	  userAction.value = 'search';
	  document.genericForm.target='resultFrame';
	  showPleaseWait();
	  var a = window.setTimeout("document.genericForm.submit();",500);
  }
}

function submitSearchForm()
{
  // Clear selected user for new search
  document.getElementById("selectedUser").innerHTML = "";
  //document.getElementById("showlegendLink").style["display"] = "none";
  
 /*Make sure to not set the target of the form to anything other than resultFrame*/
  var isValidSearchForm = validateForm();
  var now = new Date();
  document.getElementById("startSearchTime").value = now.getTime();

  if(isValidSearchForm) {
   var userAction = document.getElementById("action");
   userAction.value = 'search';
   document.genericForm.target='resultFrame';
   showPleaseWait();
   return true;
  }
  else
  {
    return false;
  }
}

function validateForm() {
   return true;
} //end of validateForm

var returnSelectedUserName=null;
var returnSelectedUserId=null;
var valueElementId=null;
var displayElementId=null;

function selectedUser() {
	if ( $("sourcePage") != null && $v("sourcePage") =='inventoryGroupDefinition' ) {
		if(parent.returnSelectedUserId.length > 0)
		{	
			try {
				window.opener.setUser(parent.returnSelectedUserId,parent.returnSelectedUserName);
			}
			catch (ex) {}
		}
	}else if ( $("fixedCompanyId") != null && $v("fixedCompanyId") =='Y' && window.opener.setBuyer != null ) {
		if(parent.returnSelectedUserId.length > 0)
		{	
				window.opener.setBuyer($v("callbackparam"), parent.returnSelectedUserId, parent.returnSelectedUserName);
		}
	}else if ( $("callBackFunction") != null && $v("callBackFunction") == 'Yes' ) {
		window.opener.callBackFromSearchPersonnel(returnSelectedUserId,returnSelectedUserName);
	}else {
		try {
			  var openervalueElementId = opener.document.getElementById(valueElementId);
			  if (openervalueElementId)
				  openervalueElementId.value = returnSelectedUserId;
			} catch( ex ) {}
			try {
			  var openerdisplayElementId = opener.document.getElementById(displayElementId);
			  if (openerdisplayElementId) {
				  if(window.opener.dontChangElemCss)
				  {}
				  else
				  {	  
				  openerdisplayElementId.className = "inputBox";
				  }
				  openerdisplayElementId.value = returnSelectedUserName;
			  }
			} catch( ex ) {}
			try {
			  var openerdisplayArea = opener.document.getElementById($('displayArea').value); 
			  if (openerdisplayArea) {
				  //alert("returnSelectedUserName"+returnSelectedUserName);
				  openerdisplayArea.value = returnSelectedUserName;
			  }
			} catch( ex ) {}
		   
			try {
				if( window.opener.personnelChanged ) {
					window.opener.personnelChanged(returnSelectedUserId,returnSelectedUserName,callbackparam);
				}
			} catch(exx) {}
	}
	window.close();
	returnSelectedUserName = null;
	returnSelectedUserId=null;
	valueElementId=null;
	displayElementId=null;
}

function showLegend()
{
  var showLegendArea = document.getElementById("showLegendArea");
  showLegendArea.style.display="";

  var dhxWins = new dhtmlXWindows()
  dhxWins.setImagePath("/dhtmlxWindows/codebase/imgs/");
  if (!dhxWins.window(messagesData.showlegend)) {
  // create window first time
  var legendWin = dhxWins.createWindow(messagesData.showlegend, 0, 0, 400, 180);
  legendWin.setText(messagesData.showlegend);
  legendWin.clearIcon();  // hide icon
  legendWin.denyResize(); // deny resizing
  legendWin.denyPark();   // deny parking
  legendWin.attachObject("showLegendArea");
  legendWin.attachEvent("onClose", function(legendWin){legendWin.hide();});
  legendWin.center();
  }
  else
  {
    // just show
    dhxWins.window("legendwin").show();
  }
}
windowCloseOnEsc = true;
