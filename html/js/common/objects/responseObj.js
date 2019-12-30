function responseObj(data) {
	$.extend(this, $.parseJSON(data));
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

