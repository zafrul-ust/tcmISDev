function responseObj(data, ignoreSession) {
	$.extend(this, JSON.parse(data));
	if (!ignoreSession && this.isExpired()) {
		refreshSession();
	}
}

responseObj.prototype.isOK = function() {
	return "OK" == this.Status;
};

responseObj.prototype.isExpired = function() {
	return "SESSION EXPIRED" == this.Status;
};

responseObj.prototype.isError = function() {
	return "ERROR" == this.Status;
};

