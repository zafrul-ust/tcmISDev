// function submitSearchForm() {} Overridden in iframegridresizemain.js
// function createXSL() {} Overridden in iframegridresizemain.js

var dhxWins = null;

// this function will intialize dhtmlXWindow if it's null
function initializeDhxWins() {
	if (dhxWins == null) {
		dhxWins = new dhtmlXWindows();
		dhxWins.setImagePath("/dhtmlxWindows/codebase/imgs/");
	}
}

function init() { /* This is to initialize the YUI */
	this.cfg = new YAHOO.util.Config(this);
	if (this.isSecure) {
		this.imageRoot = YAHOO.widget.Module.IMG_ROOT_SSL;
	}

	/*
	 * Yui pop-ups need to be initialized onLoad to make them work
	 * correctly. If they are not initialized onLoad they tend to act weird
	 */
	legendWin = new YAHOO.widget.Panel("showLegendArea", {
	        width : "300px",
	        fixedcenter : true,
	        constraintoviewport : true,
	        underlay : "none",
	        close : true,
	        visible : false,
	        draggable : true,
	        modal : false
	});
	legendWin.render();

	// showErrorMessagesWin = new YAHOO.widget.Panel("errorMessagesArea", {
	// width:"300px", height:"300px", fixedcenter: true,
	// constraintoviewport: true, underlay:"none", close:true,
	// visible:false,draggable:true, modal:false } );
	// showErrorMessagesWin.render();
}

var org_opsChanged = window.opsChanged;
window.opsChanged = function() {
	org_opsChanged();
};

var hubName;
window.hubChanged = function() {
	var hubO = $("hub");
	hubName = hubO.options[hubO.selectedIndex].text;
};
