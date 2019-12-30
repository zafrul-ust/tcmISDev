function submitSearchListForm()
{
    document.genericForm.target = "resultFrame";
    var action = document.getElementById("action");
    action.value = 'search';
     //set start search time
    var now = new Date();
    var startSearchTime = document.getElementById("startSearchTime");
    startSearchTime.value = now.getTime();
    var flag = validateForm();
    if (flag) {
        parent.showPleaseWait();
        return true;
    }
    else
    {
        return false;
    }
}
function generateExcel() {
    var flag = validateForm();
    if (flag) {
        var action = document.getElementById("action");
        action.value = 'createExcel';
        openWinGenericViewFile('/tcmIS/common/loadingfile.jsp', '_ReceivingReportGenerateExcel', '650', '600', 'yes');
        document.genericForm.target = '_ReceivingReportGenerateExcel';
        var a = window.setTimeout("document.genericForm.submit();", 500);
    }
}
function validateForm() {
    var flag = true;
    var searchText = document.getElementById("searchText");
    var dodaac = document.getElementById("dodaac") ;
    if ( searchText.value.length == 0 && dodaac.value.length == 0) {
        alert(messagesData.dodaacorsearchtextrequired);
        flag = false;
    }
    return flag;
}


function submitNewForm(type) {
    var newAddress = "/tcmIS/hub/dpmsaddress.do?&submitNew=submit&type="+type;
    openWinGeneric(newAddress,"new_dpms_address","700","400","yes")
    return false;
}