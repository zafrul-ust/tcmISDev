var selectedRowId=null;
var previousRowId=null;
var previousClass=null;

function newAddressLoad() {
  var country0 = document.getElementById("countryAbbrev");
  country0.selectedIndex = 225;
countryChanged();
}




function submitSearchForm(searchType, dodaacType) {
    var dodaacString="";
    var type = document.getElementById("type");
    if('nododaac1' == searchType) {
        var dodaac = document.getElementById("dodaac");
        /*if (type.value == "dpms")
        {
          dodaacString = "&dodaacType=POE&dodaac="+dodaac.value;
        }
        else if  (type.value == "nol")
        {
          dodaacString = "&dodaacType=NOL&dodaac="+dodaac.value;
        }
        else*/
        {
          dodaacString = "&dodaacType=&dodaac="+dodaac.value;
        }
    }
    else if('nododaac2' == searchType) {
        var dodaac = document.getElementById("mfDodaac");
        dodaacString = "&dodaacType=&dodaac="+dodaac.value;
    }
   
    var addressList = "/tcmIS/hub/dpmsaddresslistmain.do?&locationType="+searchType+dodaacString;    
    openWinGeneric(addressList,"dpms_address","700","400","yes")
return false;
}

function submitUpdateForm() {
    var flag = validateForm();
  if(flag) {
    this.close();
    return true;
  }
  else {
      return false;
  }
}

function submitNewForm(type) {
    var newAddress = "/tcmIS/hub/dpmsaddress.do?&submitNew=submit&type="+type;
    openWinGeneric(newAddress,"new_dpms_address","700","400","yes")
    return false;
}

function submitAddForm() {
    this.close();
}

function selectAddress(type) {
    if('nododaac2' == type) {
        var selectedDodaac = document.getElementById("selectedDodaac");
        var selectedDodaacType = document.getElementById("selectedDodaacType");
        var selectedLocationId = document.getElementById("selectedLocationId");
        var selectedAddress = document.getElementById("selectedAddress");
        var openerDodaac = opener.document.getElementById("mfDodaac");
        openerDodaac.value = selectedDodaac.value;
        var openerDodaacType = opener.document.getElementById("mfDodaacType");
        openerDodaacType.value = selectedDodaacType.value;
        var openerLocationId = opener.document.getElementById("mfLocationId");
        openerLocationId.value = selectedLocationId.value;
        var openerAddress = opener.document.getElementById("mfAddress");
        openerAddress.value = selectedAddress.value;
    }
    else {
        var selectedDodaac = document.getElementById("selectedDodaac");
        var selectedDodaacType = document.getElementById("selectedDodaacType");
        var selectedLocationId = document.getElementById("selectedLocationId");
        var selectedAddress = document.getElementById("selectedAddress");
        var openerDodaac = opener.document.getElementById("dodaac");
        openerDodaac.value = selectedDodaac.value;
        var openerDodaacType = opener.document.getElementById("dodaacType");
        openerDodaacType.value = selectedDodaacType.value;
        var openerLocationId = opener.document.getElementById("locationId");
        openerLocationId.value = selectedLocationId.value;
        var openerAddress = opener.document.getElementById("address");
        openerAddress.value = selectedAddress.value;
    }
    this.close();
}

function selectRow(rowId)
{
   //alert("rowId  "+rowId+"");
    var selectedRow = document.getElementById("rowId"+rowId+"");
    var selectedRowClass = document.getElementById("colorClass"+rowId+"");
	selectedRow.className = "selected"+selectedRowClass.value+"";

    if (previousRowId != null && rowId != previousRowId) {
      var previousRow = document.getElementById("rowId"+previousRowId+"");
	  var previousRowClass = document.getElementById("colorClass"+previousRowId+"");
      previousRow.className = previousRowClass.value;
     }

     selectedRowId =rowId;
    previousRowId = rowId;
    selectedRow = selectedRowClass;
    var selectedRowDodaac = document.getElementById("dodaac"+rowId+"");
    var selectedDodaac = document.getElementById("selectedDodaac");
    selectedDodaac.value = selectedRowDodaac.value;
    
    var selectedRowDodaacType = document.getElementById("dodaacType"+rowId+"");
    var selectedDodaacType = document.getElementById("selectedDodaacType");
    selectedDodaacType.value = selectedRowDodaacType.value;

    var selectedRowLocationId = document.getElementById("locationId"+rowId+"");
    var selectedLocationId = document.getElementById("selectedLocationId");
    selectedLocationId.value = selectedRowLocationId.value;

    var selectedRowAddress = document.getElementById("address"+rowId+"");
    var selectedAddress = document.getElementById("selectedAddress");
    selectedAddress.value = selectedRowAddress.value;
}

function countryChanged() {
  var country0 = document.getElementById("countryAbbrev");
  var state0 = document.getElementById("stateAbbrev");
  var selectedCountry = country0.value;
  for (var i = state0.length; i > 0; i--) {
    state0[i] = null;
  }

  showStateOptions(selectedCountry);
  state0.selectedIndex = 0;
}

function showStateOptions(selectedCountry) {
  var stateArray = new Array();
  stateArray = altState[selectedCountry];

  var stateNameArray = new Array();
  stateNameArray = altStateName[selectedCountry];

  if(stateArray.length == 0) {
    setOption(0,"All","", "stateAbbrev")
  }

  for (var i=0; i < stateArray.length; i++) {
    setOption(i,stateNameArray[i],stateArray[i], "stateAbbrev")
  }
}

function validateForm() {
  var flag = true;
  var milstripCode = document.getElementById("milstripCode");
  var oldMilstripCode = document.getElementById("oldMilstripCode");
  if(oldMilstripCode == null || oldMilstripCode.value.trim() == ''){
      if(milstripCode.value.length < 14) {
          alert(messagesData.invalidmilstripcode);
          flag = false;
      }
  }
  /*var replyDate = document.getElementById("dpmsReplyDate");
  if(replyDate != null && replyDate.value.trim() != ''){
      if(!isDate(replyDate.value,messagesData.datetimeformat)) {
          alert(messagesData.invaliddateformat);
          flag = false;
      }
      else {
          var today = new Date();
          var d1 = getDateFromFormat(replyDate.value,messagesData.datetimeformat);
          if(today < d1) {
              alert(messagesData.futuredatenotallowed);
              //alert('Future date is not allowed');
              flag = false;
          }
      }
  }*/

  /*var type = document.getElementById("type");
  if(type != null && type.value == 'dpms') {

    if(replyDate == null || replyDate.value.trim() == ''){
        alert(messagesData.daterequired);
        flag = false;
    }
  */

  return flag;
}