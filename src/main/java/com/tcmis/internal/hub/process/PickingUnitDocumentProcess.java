package com.tcmis.internal.hub.process;

import java.util.Collection;
import java.util.Locale;

import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.GenericProcess;
import com.tcmis.internal.hub.beans.PickingUnitDocument;
import com.tcmis.internal.hub.beans.PickingUnitDocumentInputBean;

public class PickingUnitDocumentProcess extends GenericProcess {
	
	public PickingUnitDocumentProcess(String client) {
		super(client);
	}

	public PickingUnitDocumentProcess(String client, Locale locale) {
		super(client, locale);
	}
	
	public Collection<PickingUnitDocument> search(PickingUnitDocumentInputBean input) throws BaseException {
		StringBuilder select = new StringBuilder("select distinct pud.picking_unit_document_id, ");
			select.append("pud.picking_unit_id, pud.document_path, ");
			select.append("v.jsp_label document_type, pud.last_updated, pud.last_updated_by, ");
			select.append("fx_personnel_id_to_name(pud.last_updated_by) last_updated_by_name");
			select.append(" from picking_unit_document pud, vv_picking_document_type v");
		if (input.hasPickingUnitId()) {
			select.append(" where pud.picking_unit_id = ").append(input.getPickingUnitId());
		}
		else if (input.getIssueId() != null || input.getShipmentId() != null || input.getReceiptId() != null) {
			select.append(", issue i, picking_unit_issue pui");
			select.append(" where pud.picking_unit_id = pui.picking_unit_id and pui.issue_id = i.issue_id");
			if (input.hasIssueId()) {
				select.append(" and i.issue_id = ").append(input.getIssueId());
			}
			if (input.hasShipmentId()) {
				select.append(" and i.shipment_id = ").append(input.getShipmentId());
			}
			if (input.hasReceiptId()) {
				select.append(" and i.receipt_id = ").append(input.getReceiptId());
			}
		}
		select.append(" and v.document_type = pud.document_type");
		
		return factory.setBean(new PickingUnitDocument()).selectQuery(select.toString());
	}
}
