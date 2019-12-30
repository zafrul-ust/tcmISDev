function eXcell_empty(cell){
	if (cell){
		this.cell=cell;
		this.grid=cell.parentNode.grid;
	} else { this.cell={} }
	this.isDisabled=function(){
		return true;
	}
	this.getValue=function(){
		return "";		
	}
	this.setValue=function(){
	}
}
eXcell_empty.prototype=new eXcell;

dhtmlXGridObject.prototype.cells3=function(row, col){
	var cell = null;
	if (row['_childIndexes'] == null) {
		cell = row.childNodes[col];
	}
	else {
		cell = row.childNodes[row._childIndexes[col]];
	}
	if (!cell || cell._cellIndex!=col){
		return this.cells3(row.previousSibling,col);
	}
	return this.cells4(cell);
}
dhtmlXGridObject.prototype.cells=function(row_id, col){
	var c = this.getRowById(row_id);
	return this.cells3(c,col);
}
