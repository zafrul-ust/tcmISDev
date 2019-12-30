/*
Copyright Scand LLC http://www.scbr.com
This version of Software is free for using in non-commercial applications.
For commercial use please contact info@scbr.com to obtain license
*/



function dataProcessor(serverProcessor){
    this.serverProcessor= serverProcessor;
	this.obj = null;
	this.mandatoryFields = new Array(0);//array of fields which should be varified (indexes of columns as indexes) with corresponding verificators (as values)
	this.updatedRows = new Array(0);//array of rows which are(were) updated
	this.autoUpdate = true;//if rows should be send to server automaticaly (based on cell edit finished)
	this.updateMode = "cell";
    return this;
    }

	/**
	* 	@desc: set if rows should be send to server automaticaly
	*	@param: mode - "row" - based on row selection changed, "cell" - based on cell editing finished, "off" - manual data sending
	*	@type: public;
	*/
	dataProcessor.prototype.setUpdateMode = function(mode){
		if(mode=="cell")
			this.autoUpdate = true;
		else{
			this.autoUpdate = false;
		}
		this.updateMode = mode;
	}


	dataProcessor.prototype.findRow = function(pattern){
    	for(var i=0;i<this.updatedRows.length;i++){
		    if(pattern==this.updatedRows[i])
	    			return i;
    	}
	    return -1;
    }

	/**
	* 	@desc: mark row as updated/normal. check mandatory fields,initiate autoupdate (if turned on)
	*	@param: rowId - id of row to set update-status for
	*	@param: state - true for "updated", false for "not updated"
	*	@type: public;
	*/
	dataProcessor.prototype.setUpdated = function(rowId,state){
		var rowInArray = this.findRow(rowId)
		if(rowInArray==-1)
			rowInArray = this.updatedRows.length;
		if(state){
			this.updatedRows[rowInArray] = rowId;
			this.setRowTextBold(rowId);
			this.checkBeforeUpdate(rowId,this.autoUpdate);
		}else{
			this.updatedRows[rowInArray] = null;
			this.setRowTextNormal(rowId);
			//this.grid.setUserData(rowId,"!nativeeditor_status","")
			//var nstatus = this.grid.getUserData(rowId,"!nativeeditor_status")
			//if(nstatus=="deleted")
			//	this.grid.deleteRow(rowId)
		}

	}

    dataProcessor.prototype.setRowTextBold=function(rowId){
        if (this.obj.mytype=="tree")
            this.obj.setItemStyle(rowId,"font-weight:bold;");
        else
            this.obj.setRowTextBold(rowId);
    };
    dataProcessor.prototype.setRowTextNormal=function(rowId){
        if (this.obj.mytype=="tree")
            this.obj.setItemStyle(rowId,"font-weight:normal;");
        else
            this.obj.setRowTextNormal(rowId);
    };
	/**
	* 	@desc: check mandatory fields and varify values of cells, initiate update (if specified)
	*	@param: rowId - id of row to set update-status for
	*	@param: updateFl - true to start update process
	*	@type: public;
	*/
	dataProcessor.prototype.checkBeforeUpdate = function(rowId,updateFl){
		var fl = true;
		var mandExists = false;
		for(var i=0;i<this.mandatoryFields.length;i++){
			if(this.mandatoryFields[i]){
				mandExists = true;
				var val = this.obj.cells(rowId,i).getValue()
				var colName = this.obj.getHeaderCol(i)
				if((typeof(this.mandatoryFields[i])=="function" && this.mandatoryFields[i](val,colName)) || (typeof(this.mandatoryFields[i])!="function" && val.toString()._dhx_trim()!="")){
					this.obj.cells(rowId,i).cell.style.borderColor = "";
				}else{
					fl = false;
					this.obj.cells(rowId,i).cell.style.borderColor = "red";
				}
			}
		}
		if((fl || !mandExists) && updateFl)//if all mandatory fields are ok or there were no mandatory fields
		{
			this.sendData(rowId);
		}
	}
	/**
	* 	@desc: send row(s) values to server
	*	@param: rowId - id of row which data to send. If not specified, then all "updated" rows will be send
	*	@type: public;
	*/
	dataProcessor.prototype.sendData = function(rowId){
		if(rowId){//send some row, not all
			//[send data to server]
               (new dtmlXMLLoaderObject(this.afterUpdate,this,true)).loadXML(this.serverProcessor+((this.serverProcessor.indexOf("?")!=-1)?"&":"?")+this._getRowData(rowId));
             // window.open(this.serverProcessor+((this.serverProcessor.indexOf("?")!=-1)?"&":"?")+this._getRowData(rowId));
			//this.setUpdated(rowId,false)
		}else{//send all rows one by one
			for(var i=0;i<this.updatedRows.length;i++){
				if(this.updatedRows[i]){
					this.checkBeforeUpdate(this.updatedRows[i],true)
				}
			}
		}
	}

	/**
	* 	@desc: response from server
	*	@param: xml - XMLLoader object with response XML
	*	@type: private;
	*/
	dataProcessor.prototype.afterUpdate = function(that,b,c,d,xml){
		var atag=xml.getXMLTopNode("data").childNodes[0];
           var action = atag.getAttribute("type");
           var sid = atag.getAttribute("sid");
           var tid = atag.getAttribute("tid");
           that.setUpdated(sid,false);

               switch (action){
                   case "insert":
                       if (that.obj.mytype=="tree")
                        that.obj.changeItemId(sid,tid);
                       else
                        that.obj.changeRowId(sid,tid);
                       sid=tid;
                       break;
                   case "delete":
                       if (that.obj.mytype=="tree")
                        that.obj.deleteItem(sid);
                       else
                        that.obj.deleteRow(sid);
                       break;

               }
           that.obj.setUserData(sid,"!nativeeditor_status",'');
	}

	/**
	* 	@desc: get all row related data
	*	@param: rowId - id of row in question
	*	@type: private;
	*/
	dataProcessor.prototype._getRowData = function(rowId){
        if (this.obj.mytype=="tree"){
           var z=tree._globalIdStorageFind(rowId);
           var z2=z.parentObject;

           var i=0;
           for (i=0; i<z2.childsCount; i++)
               if (z2.childNodes[i]==z) break;

           var str="tr_id="+z.id;
           str+="&tr_pid="+z2.id;
           str+="&tr_order="+i;
           str+="&tr_text="+escape(z.span.innerHTML);

            z2=(z._userdatalist||"").split(",");
            for (i=0; i<z2.length; i++)
                str+="&"+escape(z2[i])+"="+escape(z.userData["t_"+z2[i]]);

        }
        else
        {
           var str="gr_id="+rowId;
           var r=this.obj.getRowById(rowId);

           for (var i=0; i<r.childNodes.length; i++)
               {
               var c=this.obj.cells(r.idd,i);
               str+="&c"+i+"="+escape(c.getValue());
               }
           var data=this.obj.UserData[rowId];
           if (data){
               for (var j=0; j<data.keys.length; j++)
                   str+="&"+data.keys[j]+"="+escape(data.values[j]);
           }
        }
           return str;

	}

	/**
	* 	@desc: specify column which value should be varified before sending to server
	*	@param: ind - column index (0 based)
	*	@param: verifFunction - function (object) which should verify cell value (if not specified, then value will be compared to empty string). Two arguments will be passed into it: value and column name
	*	@type: public;
	*/
	dataProcessor.prototype.setVerificator = function(ind,verifFunction){
		if(verifFunction){
			this.mandatoryFields[ind] = verifFunction;
		}else
			this.mandatoryFields[ind] = true;
	}
	/**
	* 	@desc: remove column from list of those which should be verified
	*	@param: ind - column Index (0 based)
	*	@type: public;
	*/
	dataProcessor.prototype.clearVerificator = function(ind){
		this.mandatoryFields[ind] = false;
	}
	
	/**
	* 	@desc: initializes data-processor
	*	@param: anObj - dhtmlxGrid object to attach this data-processor to
	*	@type: public;
	*/
	dataProcessor.prototype.init = function(anObj){
		this.obj = anObj;
		var self = this;
        if (this.obj.mytype=="tree"){
            if (this.obj.setOnEditHandler)
    		this.obj.setOnEditHandler(function(state,id){
                if (state==3)
                    self.setUpdated(id,true)
                return true;
                });

            this.obj.setDropHandler(function(id){
                self.setUpdated(id,true);
            });
    		this.obj._onrdlh=function(rowId){
    			if(self.obj.getUserData(rowId,"!nativeeditor_status"))
    				return true;
    			self.obj.setUserData(rowId,"!nativeeditor_status","deleted");
    			self.setUpdated(rowId,true)
    			self.obj.setItemStyle(rowId,"text-decoration : line-through;");
    			return false;
    		};
    		this.obj._onradh=function(rowId){
    			self.obj.setUserData(rowId,"!nativeeditor_status","inserted");
    			self.setUpdated(rowId,true)
    		};
        }
        else{
      		this.obj.setOnEditCellHandler(function(state,id,index){
      			var cell = self.obj.cells(id,index)
      			if(state==0){

      			}else if(state==1){
					if(cell.isCheckbox()){
      					self.setUpdated(id,true)
      				}
      			}else if(state==2){
      				if(cell.wasChanged()){
						self.setUpdated(id,true)
      				}
      			}
      		})
      		this.obj.setOnRowSelectHandler(function(rowId){
      			if(self.updateMode=="row")
      				self.sendData();
      		});
      		this.obj.setOnEnterPressedHandler(function(rowId,celInd){
      			if(self.updateMode=="row")
      				self.sendData();
      		});
      		this.obj.setOnBeforeRowDeletedHandler(function(rowId){
      			if(self.obj.getUserData(rowId,"!nativeeditor_status"))
      				return true;
      			self.obj.setUserData(rowId,"!nativeeditor_status","deleted");
      			self.obj.setRowTextStyle(rowId,"text-decoration : line-through;");
      			self.setUpdated(rowId,true)
      			return false;
      		});
      		this.obj.setOnRowAddedHandler(function(rowId){
      			self.obj.setUserData(rowId,"!nativeeditor_status","inserted");
      			self.setUpdated(rowId,true)
      		});

        }
	}


