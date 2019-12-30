function submitReceive() {
    /*Set any variables you want to send to the server*/
    var action = window.frames["resultFrame"].document.getElementById("action");
    action.value = 'receive';
    showPleaseWait();
    /*Submit the form in the result frame*/
    window.frames["resultFrame"].document.genericForm.submit();
}