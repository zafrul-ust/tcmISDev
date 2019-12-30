var returnselectedmanifest=null;

function selectedManifest()
{
    var manifest = opener.document.getElementById("manifest");
    manifest.value=returnselectedmanifest;
    //reset valiable
    returnselectedmanifest = null;
    window.close();
}