/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements. See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership. The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package com.tcmis.ws.scanner.beans;

import java.math.BigDecimal;
import java.util.Vector;
import java.util.Collection;

public class CabinetListBean {
	String status;
	String reason;
	String logonId;
	BigDecimal personnelId;
	Collection receiptInfo = new Vector(0);
	Collection itemInfo = new Vector(0);
    Collection containerInfo = new Vector(0);
    Collection symbol2DDetailInfo = new Vector(0);
    
    public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
	public String getLogonId() {
		return logonId;
	}
	public void setLogonId(String logonId) {
		this.logonId = logonId;
	}
	public BigDecimal getPersonnelId() {
		return personnelId;
	}
	public void setPersonnelId(BigDecimal personnelId) {
		this.personnelId = personnelId;
	}

    public Collection getReceiptInfo() {
        return receiptInfo;
    }

    public void setReceiptInfo(Collection receiptInfo) {
        this.receiptInfo = receiptInfo;
    }

    public Collection getItemInfo() {
        return itemInfo;
    }

    public void setItemInfo(Collection itemInfo) {
        this.itemInfo = itemInfo;
    }

    public Collection getContainerInfo() {
        return containerInfo;
    }

    public void setContainerInfo(Collection containerInfo) {
        this.containerInfo = containerInfo;
    }

    public Collection getSymbol2DDetailInfo() {
        return symbol2DDetailInfo;
    }

    public void setSymbol2DDetailInfo(Collection symbol2DDetailInfo) {
        this.symbol2DDetailInfo = symbol2DDetailInfo;
    }
}