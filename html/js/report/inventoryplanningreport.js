function $(a) {
	return document.getElementById(a);
}

function buildDropDown( arr, defaultObj, eleName ) {
    var obj = $(eleName);
    var selectedIndex = 0;
    var checkDefault = false;
    for (var i = obj.length; i >= 0;i--)
        obj[i] = null;

    if( (arr == null || arr.length == 0)) {
        if (defaultObj == "Select") {
            setOption(0,messagesData.pleaseselect,"", eleName);
        }else {
            setOption(0,messagesData.all,"", eleName);    
        }
    }else if( arr.length == 1 ) {
        setOption(0,arr[0].name,arr[0].id, eleName);
    }else {
        var start = 0;
        if(defaultObj == "Select") {
            setOption(0,messagesData.pleaseselect,"", eleName);
            start ++;
        }else {
            setOption(0,messagesData.all,"", eleName);
            start ++;
        }
        if ("facilityId" == eleName)
            checkDefault = true;
        for ( var i=0; i < arr.length; i++) {
            setOption(start++,arr[i].name,arr[i].id,eleName);
            if (checkDefault) {
                if (arr[i].id == $v("defaultFacilityId"))
                    selectedIndex = start-1;
            }
        }
    }
    obj.selectedIndex = selectedIndex;
}

function setFacility() {
    buildDropDown(facCountyAreaArr,"Select","facilityId");
    facilityChanged();
}

function facilityChanged() {
    var facility = $("facilityId");
    var arr = null;
    if( facility.value != '' ) {
        for(i=0;i< facCountyAreaArr.length;i++)
            if( facCountyAreaArr[i].id == facility.value ) {
                arr = facCountyAreaArr[i].coll;
            break;
        }
    }

    buildDropDown(arr,"Select","county");
    countyChanged();
}

function countyChanged() {
    var facility = $("facilityId");
    var countyAreaArr = null;
    if( facility.value != '' ) {
        for(i=0;i< facCountyAreaArr.length;i++) {
            if( facCountyAreaArr[i].id == facility.value ) {
                countyAreaArr = facCountyAreaArr[i].coll;
                break;
            }
        }
    }
    var county = $("county");
    var arr1 = null;
    if( county.value != '' ) {
        for(i=0;i< countyAreaArr.length;i++) {
            if( countyAreaArr[i].id == county.value ) {
                arr1 = countyAreaArr[i].coll;
                $("solidThreshold").innerHTML = countyAreaArr[i].solidThreshold;
                $("solidThresholdUnit").innerHTML = countyAreaArr[i].solidThresholdUnit;
                $("liquidThreshold").innerHTML = countyAreaArr[i].liquidThreshold;
                $("liquidThresholdUnit").innerHTML = countyAreaArr[i].liquidThresholdUnit;
                $("gasThreshold").innerHTML = countyAreaArr[i].gasThreshold;
                $("gasThresholdUnit").innerHTML = countyAreaArr[i].gasThresholdUnit;
                break;
            }
        }
    }else{
        $("solidThreshold").innerHTML = '';
        $("solidThresholdUnit").innerHTML = '';
        $("liquidThreshold").innerHTML = '';
        $("liquidThresholdUnit").innerHTML = '';
        $("gasThreshold").innerHTML = '';
        $("gasThresholdUnit").innerHTML = '';
    }

    buildDropDown(arr1,"All","areaListArray");

}

/*
function reportTypecheck(reportRadio) {
    if (reportRadio.value == 'C') {
        if(!document.getElementById("trialRun").checked) {
            document.getElementById("trialRun").checked = true;
            document.getElementById("trialRun").value = 'Y';
        }

    }else
        reportRadio.value='A';
}
*/

function trialcheck(element) {
    if (element.value == 'Y')
        element.value = 'N';
    else
        element.value='Y';
}

function reportGeneratechecked(generateRadio) {
    if(generateRadio.value == 'BATCH')
        document.getElementById("reportGenerationType").value = "batch";
    else
        document.getElementById("reportGenerationType").value = "INTERACTIVE"; 
}

function generateReport() {
    var reportType = document.getElementById("reportType");
    document.getElementById("action").value="createReport";
    if(document.getElementById("reportGenerationType").value != 'INTERACTIVE') {
        var reportName = document.getElementById("reportName");
        if(reportName == null || reportName.value == "") {
            alert(messagesData.pleaseenterreportname);
            return;
        }
    }
    if ($v("county") == null || $v("county").length == 0) {
        alert(messagesData.validvalues+"\n"+messagesData.county);
        return;
    }

    var a = window.setTimeout("document.inventoryPlanningReportForm.submit();",50);
    openWinGenericViewFile('/tcmIS/common/loadingfile.jsp','_ReportExcel','650','600','yes');
    document.inventoryPlanningReportForm.target='_ReportExcel';
}
