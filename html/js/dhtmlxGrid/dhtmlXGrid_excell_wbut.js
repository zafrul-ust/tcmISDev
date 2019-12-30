/*
Copyright Scand LLC http://www.scbr.com
To use this component please contact info@scbr.com to obtain license

*/ 

 
function eXcell_wbut(cell){
 this.cell = cell;
 this.grid = this.cell.parentNode.grid;
 this.edit = function(){
 var val = this.getValue().toString();
 var valAr = val.toString().split("^");
 this.obj = document.createElement("INPUT");
 this.obj.style.width = "60px";
 this.obj.style.height =(this.cell.offsetHeight-(this.grid.multiLine?5:4))+"px";
 this.obj.style.border = "0px";
 this.obj.style.margin = "0px";
 this.obj.style.padding = "0px";
 this.obj.style.overflow = "hidden";
 this.obj.style.fontSize = _isKHTML?"10px":"12px";
 this.obj.style.fontFamily = "Arial";
 this.obj.wrap = "soft";
 this.obj.style.textAlign = this.cell.align;
 this.obj.onclick = function(e){(e||event).cancelBubble = true}
 this.cell.innerHTML = "";
 this.cell.appendChild(this.obj);
 this.obj.onselectstart=function(e){if(!e)e=event;e.cancelBubble=true;return true;};
 this.obj.style.textAlign = this.cell.align;
 this.obj.value=valAr[0]
 this.obj.focus()
 this.obj.focus()
 this.cell.appendChild(document.createTextNode(" "));
 var butElem = document.createElement('input');
 if(isIE()){
 butElem.style.height =(this.cell.offsetHeight-(this.grid.multiLine?5:4))+"px";
 butElem.style.lineHeight = "5px";
}else{
 butElem.style.fontSize = "8px";
 butElem.style.width = "10px";
 butElem.style.marginTop = "-5px"
}
 
 butElem.type='button'
 butElem.name='Lookup'
 butElem.value='...'
 var inObjValue = this.obj.value;
 var inCellIndex = this.cell.cellIndex
 var inRowId = this.cell.parentNode.idd
 var inGrid = this.grid
 var inCell = this;
 butElem.onclick=function(evt){if(valAr.length>1){eval(valAr[1]+"(inGrid,'"+inObjValue+"','"+inRowId+"',"+inCellIndex+")");}}
 this.cell.appendChild(butElem);
}
 this.getValue = function(){
 var outAr = new Array();
 outAr[0] = this.cell.innerHTML;
 if(this.cell.funcToCall){
 outAr[1] = this.cell.funcToCall
}
 return outAr.join("^")
}
 this.setValue = function(val){
 alert(this.obj)
 if(this.obj){
 alert(this.obj.value+"::"+val)
 this.obj.value = val;
}
 var valAr = val.split("^");
 this.cell.innerHTML = valAr[0];
 if(valAr.length>1){
 this.cell.funcToCall = valAr[1]
}
}
 this.detach = function(){
 alert(this.obj.value)
 this.setValue(this.obj.value);
 return this.val!=this.getValue();
}
}
eXcell_wbut.prototype = new eXcell;

