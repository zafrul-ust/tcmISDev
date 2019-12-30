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

function checkAll(rowCount) {
  var allCheck = document.getElementById("allCheck");
  var check;
  var value;
  if(!allCheck.checked) {
    check = false;
  }
  else {
    check = true;
  }
  for(var i=0; i<rowCount; i++) {
    var checkbox = document.getElementById("ok" + i);
    checkbox.checked = check;
    checkbox.value = value;
  }
}

function validateForm() {
  return true;
}

function tsdfChanged() {
  var tsdf0 = document.getElementById("tsdf");
  var generatorCompany0 = document.getElementById("generatorCompany");
  var tsdfFacilityIdForGenerator0 = document.getElementById("tsdfFacilityIdForGenerator");
  var selectedTsdf = tsdf0.value;
  for (var i = generatorCompany0.length; i > 0; i--) {
    generatorCompany0[i] = null;
  }

  for (var i = tsdfFacilityIdForGenerator0.length; i > 0; i--) {
    tsdfFacilityIdForGenerator0[i] = null;
  }
  showGeneratorCompany(selectedTsdf);
  generatorCompany0.selectedIndex = 0;
  generatorCompanyChanged(generatorCompany0.value);
}

function showGeneratorCompany(selectedTsdf) {
  var generatorCompanyArray = new Array();
  generatorCompanyArray = altGeneratorCompanyId[selectedTsdf];

  var generatorCompanyNameArray = new Array();
  generatorCompanyNameArray = altGeneratorCompanyName[selectedTsdf];

  if(generatorCompanyArray.length == 0) {
    setOption(0,"All","", "generatorCompany")
  }

  for (var i=0; i < inventoryGroupArray.length; i++) {
    setOption(i,generatorCompanyNameArray[i],generatorCompanyArray[i], "generatorCompany")
  }
}

function generatorCompanyChanged() {
  var generatorCompany0 = document.getElementById("generatorCompany");
  var tsdfFacilityIdForGenerator0 = document.getElementById("tsdfFacilityIdForGenerator");
  var selectedGeneratorCompany = generatorCompany0.value;
  if(tsdfFacilityIdForGenerator0 != null) {
    for (var i = tsdfFacilityIdForGenerator0.length; i > 0; i--) {
      tsdfFacilityIdForGenerator0[i] = null;
    }
  }
  showTsdfFacilityForGenerator(selectedGeneratorCompany);
  tsdfFacilityIdForGenerator0.selectedIndex = 0;
}


function showTsdfFacilityForGenerator(selectedGeneratorCompany) {
  var facilityIdArray = new Array();
  facilityIdArray = altFacilityId[selectedGeneratorCompany];
  var facilityNameArray = new Array();
  facilityNameArray = altFacilityName[selectedGeneratorCompany];

  if(facilityIdArray == null || facilityIdArray.length == 0) {
    setOption(0,"All","", "tsdfFacilityIdForGenerator")
  }
  else {
    for (var i=0; i < facilityIdArray.length; i++) {
      setOption(i,facilityNameArray[i],facilityIdArray[i], "tsdfFacilityIdForGenerator")
    }
  }
}