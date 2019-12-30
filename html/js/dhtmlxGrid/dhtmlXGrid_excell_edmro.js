/*
Copyright Scand LLC http://www.scbr.com
To use this component please contact info@scbr.com to obtain license

*/

//Nawaz new editor cell based on mro
function eXcell_edmro(cell){
 //alert("Here creating eXcell_ed");
 try{
 this.cell = cell;
 this.grid = this.cell.parentNode.grid;
}catch(er){}
 this.edit = function(){
 this.val = this.getValue();
 this.obj = document.createElement(_isKHTML?"INPUT":"TEXTAREA");
 this.obj.style.width = "100%";
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
 this.obj.value = this.val
 this.cell.innerHTML = "";
 this.cell.appendChild(this.obj);
 this.obj.onselectstart=function(e){if(!e)e=event;e.cancelBubble=true;return true;};
 this.obj.focus()
 this.obj.focus()
}

eXcell_edmro.prototype.getValue = function(){
 //alert("Here eXcell_mro getValue");
 //alert(this.cell.innerHTML);
 return this.cell.childNodes[0].innerHTML._dhx_trim();
}
eXcell_edmro.prototype.setValue = function(val){
// if(!this.cell.childNodes.length){
 this.cell.style.whiteSpace='normal';
 this.cell.innerHTML="<div class='mroCell' style='height:100%;overflow:hidden;'></div>";
//}
 //alert(this.cell.id);
 if(!val || val.toString()._dhx_trim()=="")
 val="&nbsp;"
 this.cell.childNodes[0].innerHTML = val;
}

 this.detach = function(){
 this.setValue(this.obj.value);
 return this.val!=this.getValue();
}

}
eXcell_edmro.prototype = new eXcell;