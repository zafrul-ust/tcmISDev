/*
Copyright Scand LLC http://www.scbr.com
To use this component please contact info@scbr.com to obtain license

*/ 

 
 dhtmlXGridObject.prototype.enableDragAndDrop=function(mode){
 if(mode=="temporary_disabled"){
 this.dADTempOff=false;
 mode=true;}
 else
 this.dADTempOff=true;

 this.dragAndDropOff=convertStringToBoolean(mode);
};

 
dhtmlXGridObject.prototype.setDragBehavior=function(mode){
 switch(mode){
 case "child": this.dadmode=0;break;
 case "sibling": this.dadmode=1;break;
}};

 
dhtmlXGridObject.prototype._createDragNode=function(htmlObject){
 htmlObject.parentObject=new Object();
 htmlObject.parentObject.treeNod=this;
 htmlObject.parentObject.parentNode=htmlObject.parentNode;

 if(!this.dADTempOff)return null;
 var dragSpan=document.createElement('div');
 dragSpan.innerHTML=this.rowToDragElement(htmlObject.parentNode.idd);
 dragSpan.style.position="absolute";
 dragSpan.className="dragSpanDiv";
 return dragSpan;
}


Array.prototype.moveOrder = function(ind1,ind2,mode){
 var tmp = this[ind2];
 var tmp2= this[ind1];
 
 for(var i=0;i<this.length-1;i++)
 if((this[i]==null)||(this[i]==tmp))
{
 this[i]=this[i+1];
 this[i+1]=null;
}
 this[this.length-1]=null;

 
 mode=mode||false;
 for(var i=0;i<this.length;i++)
 if(!mode){
 if(this[i]==tmp2)mode=true;
}
 else{
 var tmp2=this[i];
 this[i]=tmp;
 tmp=tmp2;
}
}
dhtmlXGridObject.prototype._trsfDr=function(ind,row){
 if(ind)
 row=this.rowsCol[ind];
 else ind=this.rowsCol._dhx_find(row);
 if(row.parent_id!=0){

 var ind2=ind;

 if((this.rowsCol[ind2])&&(this.rowsCol[ind2].expand==""))ind2+=this.hasChildren(row.idd);
 var z=this.loadedKidsHash.get(row.parent_id);
 var kz=z._dhx_find(row)+1;

 return [ind2+1,kz];
}
 else return [ind+1,ind+1];
}

dhtmlXGridObject.prototype._drag=function(sourceHtmlObject,dhtmlObject,targetHtmlObject,innerFlag){
 if(!innerFlag)this.dadmodex=this.dadmode;

 if(this._autoOpenTimer)window.clearTimeout(this._autoOpenTimer);
 
 this._deSelectD();
 var r1=targetHtmlObject.parentNode;
 var rInd1 = this.rowsCol._dhx_find(r1)
 var r2=sourceHtmlObject.parentObject;

 if(r2 && r2.childNodes)
{
 if((!innerFlag)&&(this.dragFunc)&&(!this.dragFunc(r2.id,r1.idd,r2.treeNod,this)))return;

 var gridRowId =(new Date()).getMilliseconds();
 var text = new Array(this.getColumnCount());


 if(this.isTreeGrid()){
 if(this.dadmodex){
 var z=this._trsfDr(rInd1);
 this._dhkPos=z[1];
 var rnew = this.addRow(gridRowId,text,z[0],r1.parent_id);
 this._dhkPos=null;
 this.dadmodex=null;
}
 else
 var rnew = this.addRow(gridRowId,text,(rInd1+1),r1.idd);
}
 else
 var rnew = this.addRow(gridRowId,text,(rInd1+1));

 var flToDel = this.treeToGridElement(r2.treeNod,r2.id,gridRowId);

 for(var j=0;j<r2.childsCount;j++)
 this._drag(r2.childNodes[j].span.parentNode,0,rnew.childNodes[0],1)

 if(dhtmlObject && flToDel)
 r2.treeNod.deleteItem(r2.id,false);

 if((!innerFlag)&&(this.dropFunc))
 this.dropFunc(r2.id,r1.idd,gridRowId,r2.treeNod,this);

 return;
}
 var r2=sourceHtmlObject.parentNode;
 var rInd2 = this.rowsCol._dhx_find(r2)
 var sgrid=r2.grid;
 var tgrid=this;
 var id_list=sgrid.getSelectedId();
 var list=(id_list||"").split(sgrid.delim);
 if(list.length<2)id_list=r2.idd;

 if((!innerFlag)&&(tgrid.dragFunc)&&(!tgrid.dragFunc(id_list,r1.idd,sgrid,tgrid)))return;
 var id_list2="";

 if(list.length<2)
 id_list2=tgrid._dragBTWgrids(sgrid,r2,rInd2,tgrid,r1,rInd1,innerFlag);
 else
 for(var i=list.length-1;i>=0;i--)
{
 var r3=sgrid.getRowIndex(list[i]);
 if(r3!=-1)
 id_list2+=tgrid._dragBTWgrids(sgrid,sgrid.obj.rows[r3],r3,tgrid,r1,rInd1)+",";
}

 if(!innerFlag){
 if(sgrid.setSizes)sgrid.setSizes();
 if(tgrid.setSizes)tgrid.setSizes();
 
 if(this.dropFunc)
 tgrid.dropFunc(id_list,r1.idd,id_list2,sgrid,tgrid);
}
}

dhtmlXGridObject.prototype._dragBTWgrids=function(sgrid,rs,rsind,tgrid,rt,rtind,innerFlag){
 if(sgrid.dpcpy){
 if(sgrid.cellType._dhx_find("tree")!=-1)
 return(sgrid._recreateLevel(sgrid,rs,tgrid,rt,rtind+1))[1];
 else{
 var newId=rs.idd+"_"+(new Date()).valueOf();
 if(tgrid.cellType._dhx_find("tree")!=-1)
 if(tgrid.dadmodex){
 var z=tgrid._trsfDr(0,rt);
 tgrid._dhkPos=z[1];
 tgrid.addRow(newId,tgrid.gridToGrid(rs.idd,sgrid,tgrid),z[0],rt.parent_id);
 tgrid._dhkPos=null;
 tgrid.dadmodex=null;
}
 else
 tgrid.addRow(newId,tgrid.gridToGrid(rs.idd,sgrid,tgrid),"",rt.idd);
 else
 tgrid.addRow(newId,tgrid.gridToGrid(rs.idd,sgrid,tgrid),rtind+1);

 tgrid._copyUserData(rs.idd,newId,sgrid,tgrid);
 return newId;
}
}
 else{
 if(sgrid==tgrid)
{
 if(tgrid.isTreeGrid())
{
 sgrid.collapseKids(rs);
 sgrid.has_kids_dec(sgrid.getRowById(rs.parent_id));
 if(!rt.idd){
 tgrid._changeParent(rs,rt);
 tgrid.rowsCol._dhx_removeAt(rsind);
 tgrid.rowsCol._dhx_insertAt(0,rs);
 rs.parentNode.insertBefore(rs,rs.parentNode.childNodes[0])
 tgrid._fixLevel(rs);
 this.setSizes();
 return rs.idd;
}




 if(tgrid.dadmodex){
 if(rt.expand=="")
 tgrid.collapseKids(rt);

 var z=tgrid._trsfDr(0,rt);
 tgrid._dhkPos=z[1];
 tgrid._changeParent(rs,tgrid.rowsAr[rt.parent_id]);

 if(this.rowsCol[z[0]])
 rt.parentNode.insertBefore(rs,this.rowsCol[z[0]]);
 else
 rs.parentNode.appendChild(rs);

 this.rowsCol._dhx_removeAt(this.rowsCol._dhx_find(rs));
 this.rowsCol._dhx_insertAt(this.rowsCol._dhx_find(rt)+1,rs);

 tgrid._fixLevel(rs);

 tgrid._dhkPos=null;
 tgrid.dadmodex=null;
 return rs.idd;
}
 else
 tgrid.expandKids(rt);

 tgrid._changeParent(rs,rt);
 tgrid._fixLevel(rs);

}
 if(rt.tagName!="TR"){
 rs.parentNode.insertBefore(rs,rs.parentNode.childNodes[0]);
 tgrid.rowsCol.moveOrder(0,rsind,true);
 this.setSizes();
 return rs.idd;
}
 else
 if(rt.nextSibling)
 rt.parentNode.insertBefore(rs,rt.nextSibling);
 else
 _isKHTML?this.obj.appendChild(rs):tgrid.obj.firstChild.appendChild(rs);

 if(rtind==-1)rtind=0;
 tgrid.rowsCol.moveOrder(rtind,rsind);
}
 else
{
 
 tgrid._moveBTW(sgrid,rs,tgrid,rt);
}
 return rs.idd;
}
}

dhtmlXGridObject.prototype._recreateLevel=function(sgrid,rs,tgrid,rt,rtind){
 var newId=rs.idd+"_"+(new Date()).valueOf();
 if(rt.tagName!="TR"){
 var r=tgrid.addRow(newId,tgrid.gridToGrid(rs.idd,sgrid,tgrid),0,0,sgrid._getRowImage(rs));
 tgrid._copyUserData(rs.idd,newId,sgrid,tgrid);
}
 else
 if(tgrid.isTreeGrid()){
 if(tgrid.dadmodex){
 var z=tgrid._trsfDr(0,rt);
 tgrid._dhkPos=z[1];
 var r=tgrid.addRow(newId,tgrid.gridToGrid(rs.idd,sgrid,tgrid),z[0],rt.parent_id,sgrid._getRowImage(rs));
 tgrid._copyUserData(rs.idd,newId,sgrid,tgrid);
 tgrid._dhkPos=null;
 tgrid.dadmodex=null;
}
 else{
 var r=tgrid.addRow(newId,tgrid.gridToGrid(rs.idd,sgrid,tgrid),"",rt.idd,sgrid._getRowImage(rs));
 tgrid._copyUserData(rs.idd,newId,sgrid,tgrid);
}
}
 else{
 var r=tgrid.addRow(newId,tgrid.gridToGrid(rs.idd,sgrid,tgrid),rtind);
 tgrid._copyUserData(rs.idd,newId,sgrid,tgrid);
}
 rtind++;
 var z=sgrid.loadedKidsHash.get(rs.idd);
 if(z)
 for(var i=0;i<z.length;i++)
 rtind=(sgrid._recreateLevel(sgrid,z[i],tgrid,r,rtind))[0];

 return [rtind,newId];
}
dhtmlXGridObject.prototype._moveBTW=function(sgrid,rs,tgrid,rt,fl){
 if(tgrid.isTreeGrid())
 if(tgrid.dadmodex){
 var z=tgrid._trsfDr(0,rt);
 tgrid._dhkPos=z[1];
 var z=tgrid.addRow(rs.idd,tgrid.gridToGrid(rs.idd,sgrid,tgrid),z[0],rt.parent_id);
 tgrid._copyUserData(rs.idd,rs.idd,sgrid,tgrid);
 tgrid._dhkPos=null;
 tgrid.dadmodex=null;
}
 else{
 var z=tgrid.addRow(rs.idd,tgrid.gridToGrid(rs.idd,sgrid,tgrid),0,rt.idd);
 tgrid._copyUserData(rs.idd,rs.idd,sgrid,tgrid);
}
 else{
 var z=tgrid.addRow(rs.idd,tgrid.gridToGrid(rs.idd,sgrid,tgrid),this.getRowIndex(rt.idd)*1+1);
 tgrid._copyUserData(rs.idd,rs.idd,sgrid,tgrid);
}

 if(sgrid.isTreeGrid()){
 var a=sgrid.loadedKidsHash.get(rs.idd);
 if(a)
 for(var i=0;i<a.length;i++){
 this._moveBTW(sgrid,a[i],tgrid,z,1)
}

}

 if(!fl)sgrid.deleteRow(rs.idd);

}


 
dhtmlXGridObject.prototype.gridToGrid = function(rowId,sgrid,tgrid){
 var z=new Array();
 for(var i=0;i<sgrid.hdr.rows[0].cells.length;i++)
 z[i]=sgrid.cells(rowId,i).getValue();
 return z;
}


dhtmlXGridObject.prototype.checkParentLine=function(node,id){
 if((!id)||(!node))return false;
 if(node.idd==id)return true;
 else return this.checkParentLine(this.getRowById(node.parent_id),id);
}

dhtmlXGridObject.prototype._dragIn=function(htmlObject,shtmlObject,x,y){
 if(!this.dADTempOff)return 0;
 var tree=this.isTreeGrid();

 if(htmlObject.parentNode==shtmlObject.parentNode)
 return 0;

 if((!tree)&&((htmlObject.parentNode.nextSibling)&&(htmlObject.parentNode.nextSibling==shtmlObject.parentNode)))
 return 0;

 if((tree)&&((this.checkParentLine(htmlObject.parentNode,shtmlObject.parentNode.idd))))
 return 0;

 if((this.dragInFunc)&&(!this.dragInFunc(shtmlObject.parentNode.idd,htmlObject.parentNode.idd,shtmlObject.parentNode.grid,htmlObject.parentNode.grid)))
 return 0;

 this._selectD(htmlObject);

 if((tree)&&(htmlObject.parentNode.expand!="")){
 this._autoOpenTimer=window.setTimeout(new callerFunction(this._autoOpenItem,this),1000);
 this._autoOpenId=htmlObject.parentNode.idd;
}
 else
 if(this._autoOpenTimer)window.clearTimeout(this._autoOpenTimer);

 return htmlObject;
}

dhtmlXGridObject.prototype._autoOpenItem=function(e,gridObject){
 gridObject.openItem(gridObject._autoOpenId);
}

dhtmlXGridObject.prototype._dragOut=function(htmlObject){
 this._deSelectD(htmlObject);
 if(this._autoOpenTimer)window.clearTimeout(this._autoOpenTimer);
}
dhtmlXGridObject.prototype._selectD=function(htmlObject){
 this._llSelD=htmlObject;
 if(htmlObject.parentNode.tagName=="TR")
 for(var i=0;i<htmlObject.parentNode.childNodes.length;i++)
{
 var z= htmlObject.parentNode.childNodes[i];
 z._bgCol=z.style.backgroundColor;
 z.style.backgroundColor="#FFCCCC";
}

 var a1=getAbsoluteTop(htmlObject);
 var a2=getAbsoluteTop(this.objBox);

 
 if((a1-a2-parseInt(this.objBox.scrollTop))>(parseInt(this.objBox.offsetHeight)-50))
 this.objBox.scrollTop=parseInt(this.objBox.scrollTop)+20;
 
 if((a1-a2)<(parseInt(this.objBox.scrollTop)+30))
 this.objBox.scrollTop=parseInt(this.objBox.scrollTop)-20;
}

dhtmlXGridObject.prototype._deSelectD=function(){
 if((this._llSelD)&&(this._llSelD.parentNode.tagName=="TR"))
 for(var i=0;i<this._llSelD.parentNode.childNodes.length;i++)
 this._llSelD.parentNode.childNodes[i].style.backgroundColor=this._llSelD._bgCol;

 this._llSelD=null;
}


 
dhtmlXGridObject.prototype.rowToDragElement=function(gridRowId){
 var out=this.cells(gridRowId,0).getValue();
 return out;
}

dhtmlXGridObject.prototype._nonTrivialNode=function(tree,targetObject,beforeNode,itemObject)
{
 if(tree.dragFunc)
 if(!tree.dragFunc(itemObject.parentNode.idd,targetObject.id,(beforeNode?beforeNode.id:null),this,tree))return false;

 var treeNodeId =(new Date()).getMilliseconds();
 tree._attachChildNode(targetObject,treeNodeId,"","","","","","","",beforeNode);
 var gridRowId = itemObject.parentNode.idd;
 if(this.gridToTreeElement(tree,treeNodeId,gridRowId))
 this.deleteRow(gridRowId);

 if(tree.dropFunc)tree.dropFunc(treeNodeId,targetObject.id,(beforeNode?beforeNode.id:null),this,tree);
}

 
dhtmlXGridObject.prototype.gridToTreeElement = function(treeObj,treeNodeId,gridRowId){
 treeObj.setItemText(treeNodeId,this.cells(gridRowId,0).getValue());
 return true;
}


 
dhtmlXGridObject.prototype.treeToGridElement = function(treeObj,treeNodeId,gridRowId){
 for(var i=0;i<this.getColumnCount();i++){
 this.cells(gridRowId,i).setValue(treeObj.getUserData(treeNodeId,this.getColumnId(i)));
}
 return true;
}

dhtmlXGridObject.prototype._copyUserData = function(sId,tId,sG,tG){
 var z1 = sG.UserData[sId];
 var z2 = new Hashtable();
 if(z1){
 z2.keys = z2.keys.concat(z1.keys);
 z2.values = z2.values.concat(z1.values);
}

 tG.UserData[tId]=z2;
}



 
dhtmlXGridObject.prototype.moveRow=function(rowId,mode,targetId,targetGrid){
 switch(mode){
 case "row_sibling":
 var sNode=this.getRowById(rowId);
 if(!sNode)return(0);
 var a={parentNode:sNode};

 var tNode=(targetGrid||this).getRowById(targetId);
 if(!tNode)return(0);
 var b={parentNode:tNode};

(targetGrid||this)._drag(a,this,b);
 break;
 case "up":
 this.moveRowUp(rowId);
 break;
 case "down":
 this.moveRowDown(rowId);
 break;
}
}

 
dhtmlXGridObject.prototype.setDragHandler=function(func){if(typeof(func)=="function")this.dragFunc=func;else this.dragFunc=eval(func);};

 
dhtmlXGridObject.prototype.setDropHandler=function(func){if(typeof(func)=="function")this.dropFunc=func;else this.dropFunc=eval(func);};


 
dhtmlXGridObject.prototype.setDragInHandler=function(func){if(typeof(func)=="function")this.dragInFunc=func;else this.dragInFunc=eval(func);};



 
dhtmlXGridObject.prototype.enableMercyDrag=function(mode){this.dpcpy=convertStringToBoolean(mode);};

