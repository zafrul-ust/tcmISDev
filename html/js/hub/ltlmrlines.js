(function($G, $) {
	'use strict';
	//mymodule
	$G.ltlmrlines = $G.ltlmrlines || {};
	
	$(function() {
		if ($("#totalLines").val() > 0) {
		  	$("#MrLineBean").show();
			$G.initGridWithGlobal($G.ltlmrlines.gridConfig);
		}
		else{
			$("#MrLineBean").hide();   
		}
		$G.setResultFrameSize();
	});
	
	// add objects to the module here
})(this, jQuery);