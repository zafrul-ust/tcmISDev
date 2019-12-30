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
   var myVar = document.getElementById("selectedValue");
   myVar.value = "";
}

function selectValue(selectedValue, selectedValueDesc) {
   var myVar = document.getElementById("selectedValue");
   var myVarDesc = document.getElementById("selectedValueDesc");
   myVar.value = selectedValue;
   myVarDesc.value = selectedValueDesc;
}

function auditSelection() {
    var option = document.getElementById("selectedValue");
    var optionDesc = document.getElementById("selectedValueDesc");
    if (option.value.trim().length > 0) {
       var parentValue = opener.document.getElementById("managementOption");
       parentValue.value = option.value;
       var parentValueDesc = opener.document.getElementById("managementOptionDesc");
       parentValueDesc.value = option.value + " - " + optionDesc.value;
       closeWindow();
    }else {
       alert(messagesData.selectvalue);
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
