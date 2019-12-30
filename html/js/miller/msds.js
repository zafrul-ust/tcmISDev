function facilityChanged() {
  var facilityO = document.getElementById("facilityId");
  var departmentO = document.getElementById("department");
  var selectedFacility = facilityO.value;
  for (var i = departmentO.length; i > 0;i--) {
    departmentO[i] = null;
  }
  showDepartmentLinks(selectedFacility);
  departmentO.selectedIndex = 0;
  departmentChanged();
}

function showDepartmentLinks(selectedFacility) {
  var department = new Array();
  department = altDepartment[selectedFacility];
  var departmentVariable = new Array();
  departmentVariable = altDepartmentVariable[selectedFacility];

  if(departmentVariable.length == 0) {
    setCab(0,"All","")
  }

  for (var i=0; i < departmentVariable.length; i++) {
    setCab(i,department[i],departmentVariable[i])
  }
}

function setCab(href,text,id) {
  var optionName = new Option(text, id, false, false)
  var departmentO = document.getElementById("department");
  departmentO[href] = optionName;
}

function departmentChanged() {
  var facilityO = document.getElementById("facilityId");
  var departmentO = document.getElementById("department");
  var buildingO = document.getElementById("building");
  var selectedFacility = facilityO.value;
  var selectedDepartment = departmentO.value;
    for (var i = buildingO.length; i > 0;i--) {
      buildingO[i] = null;
    }
  if(selectedDepartment != null && selectedDepartment.length>0) {

    showBuildingLinks(selectedFacility+selectedDepartment);
  }
  else {
    showBuildingLinks("");
  }
  buildingO.selectedIndex = 0;
  buildingChanged();
}

function showBuildingLinks(selectedDepartment) {
  var building = new Array();
  building = altBuilding[selectedDepartment];
  var buildingVariable = new Array();
  buildingVariable = altBuildingVariable[selectedDepartment];
  if(buildingVariable != null && buildingVariable.length > 0) {
    for (var i=0; i < buildingVariable.length; i++) {
      setCab2(i,building[i],buildingVariable[i]);
    }
  }
  else {
    setCab2(0,"All","");
  }
}

function setCab2(href,text,id) {
  var optionName = new Option(text, id, false, false)
  var buildingO = document.getElementById("building");
  buildingO[href] = optionName;
}

function buildingChanged() {
  var facilityO = document.getElementById("facilityId");
  var departmentO = document.getElementById("department");
  var buildingO = document.getElementById("building");
  var floorO = document.getElementById("floor");
  var selectedFacility = facilityO.value;
  var selectedDepartment = departmentO.value;
  var selectedBuilding = buildingO.value;
    for (var i = floorO.length; i > 0;i--) {
      floorO[i] = null;
    }
  if(selectedBuilding != null && selectedBuilding.length>0) {
    showFloorLinks(selectedFacility+selectedDepartment+selectedBuilding);
  }
  else {
    showFloorLinks("");
  }
  floorO.selectedIndex = 0;
}

function showFloorLinks(selectedBuilding) {
  var floor = new Array();
  floor = altFloor[selectedBuilding];
  var floorVariable = new Array();
  floorVariable = altFloorVariable[selectedBuilding];

  if(floorVariable != null && floorVariable.length > 0) {
    for (var i=0; i < floorVariable.length; i++) {
      setCab3(i,floor[i],floorVariable[i])
    }
  }
  else {
    setCab3(0,"All","")
  }
}

function setCab3(href,text,id) {
  var optionName = new Option(text, id, false, false)
  var floorO = document.getElementById("floor");
  floorO[href] = optionName;
}
