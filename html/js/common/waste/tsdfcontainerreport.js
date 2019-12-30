function init() { /*This is to initialize the YUI*/
 this.cfg = new YAHOO.util.Config(this);
 if (this.isSecure)
 {
  this.imageRoot = YAHOO.widget.Module.IMG_ROOT_SSL;
 }
}

/*The reson this is here because you might want a different width/proprties for the pop-up div depending on the page*/
function showErrorMessages()
{
 if (showErrorMessage)
 {
  showErrorMessagesWin = new YAHOO.widget.Panel("errorMessagesArea", { width:"400px", fixedcenter: true, constraintoviewport: true, underlay:"none", close:true, visible:true, draggable:true, modal:false } );
  showErrorMessagesWin.render();
  showErrorMessagesWin.show();

  var errorMessagesArea = document.getElementById("errorMessagesArea");
  errorMessagesArea.style.display="";
 }
}

function myOnload()
{
   setResultFrameSize();
}

function submitSearchForm()
{
 document.genericForm.target='resultFrame';
 var action = document.getElementById("action");
 action.value = 'search';
 var flag = validateForm();
  if(flag) {
   parent.showPleaseWait();
   return true;
  }
  else
  {
    return false;
  }
}

function cancel()
{
    window.close();
}

function getTsdfCalendar(dataFieldName)
{
    var notShip = document.getElementById("notShip");
    if (!notShip.checked) {
       getCalendar(dataFieldName);
    }
    return false;
}

function updateWasteTagNumber(wasteRequestLine)
{
   var tagNumber = prompt("Enter new Waste Tag # for Waste Req Line "+wasteRequestLine+":","");
   if (tagNumber != null) {
      if (tagNumber.length > 0) {
         document.genericForm.target='resultFrame';
         var wasteRequestIdLine = document.getElementById("wasteRequestIdLine");
         wasteRequestIdLine.value = wasteRequestLine;
         var action = document.getElementById("action");
         action.value = 'updateWasteTagNumber';
         var newWasteTagNumber = document.getElementById("newWasteTagNumber");
         newWasteTagNumber.value = tagNumber;
         submitOnlyOnce();
         document.genericForm.submit();
      }
   }
}

function notManifest()
{
   var noManifest = document.getElementById("noManifest");
   var genManifestLabel = document.getElementById("genManifestLabel");
   var genManifest = document.getElementById("genManifest");
   if (noManifest.checked) {
      genManifestLabel.disabled = true;
      genManifest.disabled = true;
   }else {
      genManifestLabel.disabled = false;
      genManifest.disabled = false;
   }
}

function notShipClicked()
{
   var notShip = document.getElementById("notShip");
   var tsdfShipDateLabel = document.getElementById("tsdfShipDateLabel");
   var tsdfStartShipDate = document.getElementById("tsdfStartShipDate");
   var linktsdfStartShipDate = document.getElementById("linktsdfStartShipDate");
   var tsdfShipDateAndLabel = document.getElementById("tsdfShipDateAndLabel");
   var tsdfEndShipDate = document.getElementById("tsdfEndShipDate");
   var linktsdfEndShipDate = document.getElementById("linktsdfEndShipDate");
   if (notShip.checked) {
      tsdfShipDateLabel.disabled = true;
      tsdfStartShipDate.disabled = true;
      linktsdfStartShipDate.disabled = true;
      tsdfShipDateAndLabel.disabled = true;
      tsdfEndShipDate.disabled = true;
      linktsdfEndShipDate.disabled = true;
   }else {
      tsdfShipDateLabel.disabled = false;
      tsdfStartShipDate.disabled = false;
      linktsdfStartShipDate.disabled = false;
      tsdfShipDateAndLabel.disabled = false;
      tsdfEndShipDate.disabled = false;
      linktsdfEndShipDate.disabled = false;
   }
}

function generateExcel() {
  var flag = validateForm();
  if(flag) {
    var action = document.getElementById("action");
    action.value = 'createExcel';
    openWinGenericViewFile('/tcmIS/common/loadingfile.jsp','_TsdfReportGenerateExcel','650','600','yes');
    document.genericForm.target='_TsdfReportGenerateExcel';
    var a = window.setTimeout("document.genericForm.submit();",500);
  }
}

function validateForm() {
  //make sure container id is an integer
  var tsdfContainerId = document.getElementById("tsdfContainerId");
  if (tsdfContainerId.value.trim().length > 0) {
     if ( !isInteger(tsdfContainerId.value.trim())) {
        alert(messagesData.tsdfContainerIdIsNotInteger);
        return false;
     }
  }
  var genContainerId = document.getElementById("genContainerId");
  if (genContainerId.value.trim().length > 0) {
     if ( !isInteger(genContainerId.value.trim())) {
        alert(messagesData.genContainerIdIsNotInteger);
        return false;
     }
  }

  //make sure ship date has the right format
  //tsdf ship date
  var shipDate = document.getElementById("tsdfStartShipDate");
  if (shipDate.value.trim().length == 10) {
     if (!checkdate(shipDate)) {
        alert(messagesData.tsdfShipDateFormat);
        return false;
     }
  }else if (shipDate.value.trim().length > 0) {
     alert(messagesData.tsdfShipDateFormat);
     return false;
  }
  shipDate = document.getElementById("tsdfEndShipDate");
  if (shipDate.value.trim().length == 10) {
     if (!checkdate(shipDate)) {
        alert(messagesData.tsdfShipDateFormat);
        return false;
     }
  }else if (shipDate.value.trim().length > 0) {
     alert(messagesData.tsdfShipDateFormat);
     return false;
  }
  //gen ship date
  shipDate = document.getElementById("genStartShipDate");
  if (shipDate.value.trim().length == 10) {
     if (!checkdate(shipDate)) {
        alert(messagesData.genShipDateFormat);
        return false;
     }
  }else if (shipDate.value.trim().length > 0) {
     alert(messagesData.genShipDateFormat);
     return false;
  }
  shipDate = document.getElementById("genEndShipDate");
  if (shipDate.value.trim().length == 10) {
     if (!checkdate(shipDate)) {
        alert(messagesData.genShipDateFormat);
        return false;
     }
  }else if (shipDate.value.trim().length > 0) {
     alert(messagesData.genShipDateFormat);
     return false;
  }
  return true;
} //end of validateForm

function tsdfChanged() {
  var tsdf0 = document.getElementById("tsdf");
  var genCompany0 = document.getElementById("genCompany");
  var tsdfVendor0 = document.getElementById("tsdfVendor");
  var selectedTsdf = tsdf0.value;
  for (var i = genCompany0.length; i > 0; i--) {
    genCompany0[i] = null;
  }
  for (var i = tsdfVendor0.length; i > 0; i--) {
    tsdfVendor0[i] = null;
  }
  showTsdfVendor(selectedTsdf);
  showGenCompany(selectedTsdf);
  genCompany0.selectedIndex = 0;
  tsdfVendor0.selectedIndex = 0;
  genCompanyChanged();
}

function showTsdfVendor(selectedTsdf) {
  var tsdfVendorIdArray = new Array();
  tsdfVendorIdArray = altVendorId[selectedTsdf];
  var tsdfVendorNameArray = new Array();
  tsdfVendorNameArray = altVendorName[selectedTsdf];;

  if(tsdfVendorIdArray.length == null || tsdfVendorIdArray.length == 0) {
    setOption(0,"All","", "tsdfVendor")
  }else {
    for (var i=0; i < tsdfVendorIdArray.length; i++) {
      setOption(i,tsdfVendorNameArray[i],tsdfVendorIdArray[i], "tsdfVendor")
    }
  }
}

function showGenCompany(selectedTsdf) {
  var genCompanyIdArray = new Array();
  genCompanyIdArray = altGenCompanyId[selectedTsdf];
  var genCompanyNameArray = new Array();
  genCompanyNameArray = altGenCompanyName[selectedTsdf];

  if(genCompanyIdArray.length == null ||genCompanyIdArray.length == 0) {
    setOption(0,"All","", "genCompany")
  }else {
    for (var i=0; i < genCompanyIdArray.length; i++) {
      setOption(i,genCompanyNameArray[i],genCompanyIdArray[i], "genCompany")
    }
  }
}

function genCompanyChanged() {
  var genCompany0 = document.getElementById("genCompany");
  var genFacilityId0 = document.getElementById("genFacilityId");
  var selectedGenCompany = genCompany0.value;

  for (var i = genFacilityId0.length; i > 0; i--) {
    genFacilityId0[i] = null;
  }

  showGenFacility(selectedGenCompany);
  genFacilityId0.selectedIndex = 0;
  genFacilityChanged();
}

function showGenFacility(selectedGenCompany) {
  var genFacilityIdArray = new Array();
  genFacilityIdArray = altGenFacilityId[selectedGenCompany];
  var genFacilityNameArray = new Array();
  genFacilityNameArray = altFacilityName[selectedGenCompany];

  if(genFacilityIdArray == null || genFacilityIdArray.length == 0) {
    setOption(0,"All","", "genFacilityId")
  }else {
    for (var i=0; i < genFacilityIdArray.length; i++) {
      setOption(i,genFacilityNameArray[i],genFacilityIdArray[i], "genFacilityId")
    }
  }
}

function genFacilityChanged() {
  var genFacilityId0 = document.getElementById("genFacilityId");
  var generationPoint0 = document.getElementById("generationPoint");
  var selectedGenFacility = genFacilityId0.value;

  for (var i = generationPoint0.length; i > 0; i--) {
    generationPoint0[i] = null;
  }

  showGenerationPoint(selectedGenFacility);
  generationPoint0.selectedIndex = 0;
}

function showGenerationPoint(selectedGenFacility) {
  var generationPointIdArray = new Array();
  generationPointIdArray = altGenerationPointId[selectedGenFacility];
  var generationPointNameArray = new Array();
  generationPointNameArray = altGenerationPointName[selectedGenFacility];

  if(generationPointIdArray == null || generationPointIdArray.length == 0) {
    setOption(0,"All","", "generationPoint")
  }else {
    for (var i=0; i < generationPointIdArray.length; i++) {
      generationPointName = generationPointNameArray[i]
      generationPointName = generationPointName.replace(/&nbsp;/g, " ");
      setOption(i,generationPointName,generationPointIdArray[i], "generationPoint")
    }
  }
}
