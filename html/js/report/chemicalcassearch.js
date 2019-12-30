var submitcount=0;
var updatecount=0;
function SubmitOnlyOnce() {
    if (submitcount == 0)
    {
        submitcount++;
        try
        {
         var target = document.all.item("TRANSITPAGE");
         target.style["display"] = "";
         var target1 = document.all.item("MAINPAGE");
         target1.style["display"] = "none";
        }
        catch (ex)
        {
        }

        return true;
    }
    else
    {
        alert(messagesData.submitOnlyOnce);
        return false;
    }
}

function clearSelection() {
   var casNumber = document.getElementById("casNumber");
   casNumber.value = "";
}

function selectCasNumber(selectedCasNumber) {
   var casNumber = document.getElementById("casNumber");
   casNumber.value = selectedCasNumber;
}

function auditSelection() {
    var casNumber = document.getElementById("casNumber");
    if (casNumber.value.trim().length > 0) {
       var parentCasNumber = opener.document.getElementById("casNumber");
       parentCasNumber.value = casNumber.value;
       closeWindow();
    }else {
       alert(messagesData.pleaseselectcasnumber);
    }
}

String.prototype.trim = function() {
   // skip leading and trailing whitespace
   // and return everything in between
   return this.replace(/^\s*(\b.*\b|)\s*$/, "$1");
}

function closeWindow() {
    window.close();
}
