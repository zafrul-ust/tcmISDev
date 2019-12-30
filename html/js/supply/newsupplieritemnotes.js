windowCloseOnEsc = true;

function myOnload() {
	
	
		
	if ( done ) { 
 		
 		window.opener.refreshPage(); 
		window.close();
	}
}	

function validateInput() {
  var errorMsg = "";
  if(document.getElementById("itemId").value == '')
  {
   errorMsg += "\n"+messagesData.itemId;
  }
  if(isEmptyV("supplier"))
  {
   errorMsg += "\n"+messagesData.supplier;
  }
   if(isEmptyV("comments"))
	   {
	    errorMsg += "\n"+messagesData.comments;
	   }
   if(!isEmptyV("comments") && document.getElementById("comments").value.trim().length >= 1000)
	   {
	   errorMsg += "\n"+messagesData.maximumallowedtext;
	   }

  if( errorMsg != '' ) {
   	alert(messagesData.validvalues+errorMsg);
   	return false;
  }
  return true;
}


function getSuppliers()
{
   loc = "/tcmIS/supply/posuppliermain.do?displayElementId=supplierName&valueElementId=supplier&statusFlag=true";
   openWinGeneric(loc,"supplierssearch","800","500","yes","50","50")
}

function clearSuppliers() {
	$('supplier').value = '';
	$('supplierName').value = '';
}

function getItem() {
	 
   var loc = "/tcmIS/distribution/searchglobalitem.do?sourcePage=addItem&uAction=new&distribution=Y";
	
	var winId= 'searchglobalitem';
	openWinGeneric(loc,winId.replace('.','a'),"900","590","yes","50","50","20","20","no");
}

function itemChanged(itemId,itemDesc) {
	$('itemId').value = itemId;
	$('hiddenDesc').innerHTML=itemDesc;
	$('descSpan').innerHTML=itemDesc.substring(0,120);
	$('descLength').value=itemDesc.length;
}

function clearItem() {
	$('itemId').value = "";
	$('hiddenDesc').innerHTML="";
	$('descSpan').innerHTML="";
	$('descLength').value=0;

}

function newComment() {	
	if(validateInput())
	{		
	   showPleaseWait();
	   document.getElementById("uAction").value = "submit";
 	   window.setTimeout("document.genericForm.submit();",500);
 	  
	}
}

function showTooltip(tooltipId, parentId, posX, posY)
{
    it = document.getElementById(tooltipId);
// alert("top"+it.style.top+"     left:"+it.style.left);
//    if ((it.style.top == '' || it.style.top == '0pt' || it.style.top == '0px') 
//        && (it.style.left == '' || it.style.left == '0pt' || it.style.left == '0px'))
//    {
        // need to fixate default size (MSIE problem)
        it.style.width = it.offsetWidth + 'px';
        it.style.height = it.offsetHeight + 'px';
//  alert("width"+it.style.width+"     height:"+it.style.height);      
        img = document.getElementById(parentId); 
    
        // if tooltip is too wide, shift left to be within parent 
        if (posX + it.offsetWidth > img.offsetWidth) posX = img.offsetWidth - it.offsetWidth;
        if (posX < 0 ) posX = 0; 
        
        x = xstooltip_findPosX(img) + posX;
        y = xstooltip_findPosY(img)*1 + posY;
        
        it.style.top = y + 'px';
        it.style.left = x + 'px';//alert("top"+it.style.top+"     left:"+it.style.left);
//    }
    
    it.style.visibility = 'visible';
    it.style.width = '300px';
    var descHeight = (($v("descLength") / 45)+1)*12;
    it.style.height = descHeight + 'px'; 
}

function hideTooltip(id)
{
    it = document.getElementById(id); 
    it.style.visibility = 'hidden'; 
}


function xstooltip_findPosX(obj) 
{
  var curleft = 0;
  if (obj.offsetParent) 
  {
    while (obj.offsetParent) 
        {
            curleft += obj.offsetLeft
            obj = obj.offsetParent;
        }
    }
    else if (obj.x)
        curleft += obj.x;
    return curleft;
}

function xstooltip_findPosY(obj) 
{
    var curtop = 0;
    if (obj.offsetParent) 
    {
        while (obj.offsetParent) 
        {
            curtop += obj.offsetTop
            obj = obj.offsetParent;
        }
    }
    else if (obj.y)
        curtop += obj.y;
    return curtop;
}


