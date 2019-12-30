// Google Analytics function is modify in AdminWorkbench
// Use the constant value to track information
/*var _gaq = _gaq || [];
_gaq.push(['_setAccount', googleAnltUA]);
_gaq.push(['_trackPageview']);

(function () {
    var ga = document.createElement('script'); ga.type = 'text/javascript'; ga.async = true;
    ga.src = ('https:' == document.location.protocol ? 'https://ssl' : 'http://www') + '.google-analytics.com/ga.js';
    var s = document.getElementsByTagName('script')[0]; s.parentNode.insertBefore(ga, s);
})();*/

//71284187   41922591

(function (i, s, o, g, r, a, m) {
    i['GoogleAnalyticsObject'] = r; i[r] = i[r] || function () {
        (i[r].q = i[r].q || []).push(arguments)
    }, i[r].l = 1 * new Date(); a = s.createElement(o),
    m = s.getElementsByTagName(o)[0]; a.async = 1; a.src = g; m.parentNode.insertBefore(a, m)
})(window, document, 'script', '//www.google-analytics.com/analytics.js', 'ga');

function gaLoadTab(tabID) {
	//alert('PageId: ' +tabID+ ', Personnel: ' +personnelId + ', Company: '+ companyName+ ', Module: '+module+ ', Date: '+ new Date());
	//newCustomerRequestDetail
	//showcustomerreturnrequest
	//dotShippingInfo
	//newSupplierRequestDetail
	
	if (tabID.substring(0,13) == 'purchaseOrder'  ||
		tabID.substring(0,10) == 'scratchPad'  ||
		tabID.substring(0,15) == 'materialrequest'	 ||
		tabID.substring(0,12) == 'showRadianPo'	 ||
		tabID.substring(0,9) == 'cataddreq'	 ||
		tabID.substring(0,9) == 'ChemReqQC'	 ||
		tabID.substring(0,18) == 'pendingTranslation'	 ||
		tabID.substring(0,7) == 'ChemReq'	
		)
	{
		//alert("Skip "+tabID); 
	}
	else
	{		
    try
	    {
	        ga('send', {
	            hitType: 'event',
	            eventCategory: 'Open PageId: ' +tabID+ ' Module: '+module + '',
	            eventAction: ' Personnel: ' +personnelId + ', Company: '+ companyName+ ', Date: '+ new Date()               
	        });
	    } 
    catch (ex) { }
	}

	return true;
}
