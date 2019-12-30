(function($G, $) {
	'use strict';
	
	function Popup(location, args, popupId, width, height, resizeable, top, left, scrollable) {
		this.popupWin;
		this.url = location;
		this.params = args;
		this.windowName = popupId;
		this.windowWidth = (width && width.toString())||"300";
		this.windowHeight = (height && height.toString())||"300";
		this.variableSize = resizeable?"yes":"no";
		this.winTop = (top && top.toString())||"30";
		this.winLeft = (left && left.toString())||"30";
		this.scrollbars = scrollable?"yes":"no";
		this.closeFn = function() { return true; };
		
		$G.popupService.popups[popupId] = this;
	}
			
	Popup.prototype.show = function() {
		var fullUrl = "";
		for (var i in this.params) {
			if (fullUrl.length == 0) {
				fullUrl += "?";
			}
			else {
				fullUrl += "&";
			}
			fullUrl += i + "=" + this.params[i];
		}
		fullUrl = this.url + fullUrl;
		this.popupWin = $G.openWinGeneric(fullUrl, this.windowName, this.windowWidth, this.windowHeight, this.variableSize, this.winTop, this.winLeft, this.scrollbars);
	}
	
	Popup.prototype.hide = function() {
		this.popupWin.window.close();
	};
	
	Popup.prototype.finished = function(callbackFn) {
		this.closeFn = callbackFn;
	};
	
	$G.popupService = $G.popupService || {
		"Popup": Popup,
		"popups": {},
		"closePopup": function(data) {
			$G.parent.opener.popupService.finish($G.name, data);
			$G.window.close();
		},
		"finish": function(winName, data) {
			this.popups[winName].closeFn(data);
		}
	};
	
}(this, jQuery));