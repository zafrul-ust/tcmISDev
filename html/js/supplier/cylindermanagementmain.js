function buildDropDown( arr, eleName ) {
   var obj = $(eleName);
   for (var i = obj.length; i > 0;i--)
       obj[i] = null;
  // if size = 1 or 2 show last one, first one is all, second one is the only choice.
  if( arr == null || arr.length == 0 )  {
      if (eleName == 'supplier')
        setOption(0,messagesData.allSuppliers,'', eleName);
      if (eleName == 'locationId')
         setOption(0,messagesData.allLocations,'', eleName);
  }else if( arr.length == 1 )
      setOption(0,arr[0].name,arr[0].id, eleName);
  else {
      var start = 0;
      for ( var i=0; i < arr.length; i++) {
            setOption(start++,arr[i].name,arr[i].id,eleName);
      }
  }
  obj.selectedIndex = 0;
}

function setSupplier() {
 	buildDropDown(altSupplier,"supplier");
 	$('supplier').onchange = supplierChanged;
    supplierChanged();
}

function supplierChanged()
{
   var supplierA = $("supplier");
   buildDropDown(altLocation[supplierA.value],"locationId");
}


function validateSearchForm() {
    var result = true;
    return result;
}

function submitSearch()
{
    //set start search time
    var now = new Date();
    var startSearchTime = document.getElementById("startSearchTime");
    startSearchTime.value = now.getTime();
    if(validateSearchForm()) {
        showPleaseWait();
        document.genericForm.target='resultFrame';
        document.getElementById("userAction").value = 'search';
        document.genericForm.submit();
    }
}

function createExcel()
{
    if (validateSearchForm()) {
        var tmpSupplier = $('supplier');
        $('supplierName').value = tmpSupplier.options[tmpSupplier.selectedIndex].text;
        var tmpLocation = $('locationId');
        $('locationDesc').value = tmpLocation.options[tmpLocation.selectedIndex].text;
        var tmpSearchField = $('searchField');
        $('searchFieldName').value = tmpSearchField.options[tmpSearchField.selectedIndex].text;
        var tmpSearchMode = $('searchMode');
        $('searchModeName').value = tmpSearchMode.options[tmpSearchMode.selectedIndex].text;
        document.getElementById("userAction").value = "createExcel";
        document.genericForm.target='_CylinderManagentExcel';
        openWinGenericViewFile('/tcmIS/common/loadingfile.jsp','_CylinderManagementExcel','650','600','yes');
        setTimeout("document.genericForm.submit()",300);
    }
}

function submitAddCylinder() {
    var tmpSupplier = $('supplier');
    if (tmpSupplier.value == '')
        alert(messagesData.selectSupplier);
    else {
        openWinGeneric('cylindermanagementresults.do?userAction=showNewEditCylinder&calledFrom=main'+
                       '&supplier='+encodeURIComponent(tmpSupplier.value)+
                       '&supplierName='+encodeURIComponent(tmpSupplier.options[tmpSupplier.selectedIndex].text)+
                       '&cylinderStatus=In'+
                       '&locationId='+encodeURIComponent($v("locationId"))+
                       '&documentNumber='+
                       '&notes='
                       , '_newEditCylinder', '470','500', 'yes');
    }
}

