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
        alert("This form has already been submitted.\n Please Wait for Results.\n Thanks!");
        return false;
    }
}

function clearSelection() {
   var profile = document.getElementById("selectedValue");
   profile.value = "";
}

function selectProfile(selectedProfile, selectedProfileDesc) {
   var profile = document.getElementById("selectedValue");
   var profileDesc = document.getElementById("selectedValueDesc");
   profile.value = selectedProfile;
   profileDesc.value = selectedProfileDesc;
}

function auditSelection() {
    var profileId = document.getElementById("selectedValue");
    var profileDesc = document.getElementById("selectedValueDesc");
    if (profileId.value.trim().length > 0) {
       var parentProfileId = opener.document.getElementById("profileId");
       parentProfileId.value = profileId.value;
       var parentProfileDesc = opener.document.getElementById("profileDesc");
       parentProfileDesc.value = profileDesc.value;
       closeWindow();
    }else {
       alert("Please select a profile.");
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
