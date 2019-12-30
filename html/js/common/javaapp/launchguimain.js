function launchClientGui(hostUrl) {
 var webApplicationPath = window.frames["searchFrame"].document.getElementById("companyId").value;
 if (webApplicationPath == '/tcmIS/cal')
    webApplicationPath = '/tcmIS/united';   
 var locationToRedirect =  webApplicationPath+"/launchguiresult.do?launchGui=y";

 var resultFrame =  document.getElementById("resultFrame");
 resultFrame.src = locationToRedirect;
}
