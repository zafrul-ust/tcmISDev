var loaded = new Array();
var loadedIndex = new Array();
var url = null;
function loadBins(itemId,hub,index)
{  // this page will only be in	 hub
   if( loadedIndex[index] ) {
	   return;
   }
   if( loaded[itemId] ) {
	   setBins(index,loaded[itemId]);
	   return; 
   } 
   url = "/tcmIS/hub/logisticsresults.do?uAction=loaddata&index="+index+"&itemId="+itemId+"&hub="+hub+"&callback=processReqChangeJSON";
   callToServer(url);
}

function processReqChangeJSON(xmldoc) 
{   
    var bins = loaded[xmldoc.itemId] = xmldoc.bins;
    if( bins )
    	setBins(xmldoc.index,bins); 
}

function setBins(index,bins) {
	
	var binEle = $('selectElementBin'+index);
	if(binEle.tagName.toLowerCase() == 'select')
	{
		var opts = binEle.options;
		var val  = binEle.value;
		for(var i = 0 ; i < bins.length; i++ ) {
			if( val != bins[i] ) opts[opts.length] = new Option(bins[i],bins[i]);
		}
		loadedIndex[index] = true;
		try { $('moreBin'+index).style.display = "none"; } catch(err){}
			
		$('selectElementBin'+index).focus();
	}
}
