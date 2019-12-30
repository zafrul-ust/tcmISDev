dhtmlXGridObject.prototype._init_point_custom=dhtmlXGridObject.prototype._init_point;
dhtmlXGridObject.prototype._init_point = function(){
	this._fixAlterCss=function(ind){
//#alter_css:06042008{		
	
		if (this._cssSP&&this._h2)
			return this._fixAlterCssTGR(ind);
		if (!this._cssEven && !this._cssUnEven) return;
		ind=ind||0;
		var j = ind;
		var wait=-1;
		var shift=0;

		for (var i = ind; i < this.rowsCol.length; i++){
			if (!this.rowsCol[i])
				continue;

			if (this.rowsCol[i].style.display != "none"){
				if (this.rowsCol[i].className.indexOf("rowselected") != -1){
					if (j%2 == 1)
						this.rowsCol[i].className=this._cssUnEven+" rowselected "+(this.rowsCol[i]._css||"");
					else
						this.rowsCol[i].className=this._cssEven+" rowselected "+(this.rowsCol[i]._css||"");
				} else {
					if (j%2 == 1)
						this.rowsCol[i].className=this._cssUnEven+" "+(this.rowsCol[i]._css||"");
					else
						this.rowsCol[i].className=this._cssEven+" "+(this.rowsCol[i]._css||"");
				}
				if (this.rowsCol[i]._rowSpan){
					wait=i+this.rowsCol[i]._rowSpan[0].length;
					shift=j;
				}
				if (i<wait)
					j=shift;
				else
					j++;
			}
		}
//#}		
	}
	
	this.attachEvent("onXLE",function(){
		var self=this;
		window.setTimeout(function(){
			self._fixAlterCss();
		},1)	
	})
	this._init_point=this._init_point_custom;
	if (this._init_point) this._init_point();
}