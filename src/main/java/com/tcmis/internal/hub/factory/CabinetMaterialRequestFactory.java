package com.tcmis.internal.hub.factory;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.Vector;

import com.tcmis.common.db.DbManager;
import com.tcmis.common.framework.BaseBeanFactory;
import com.tcmis.common.util.StringHandler;
import com.tcmis.internal.hub.beans.CabinetMrCreateViewBean;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class CabinetMaterialRequestFactory extends BaseBeanFactory {
Log log = LogFactory.getLog(this.getClass());
	public CabinetMaterialRequestFactory(DbManager dbManager) {
		super(dbManager);
	}

    public Collection createMaterialRequest(CabinetMrCreateViewBean bean, BigDecimal personnelId, String comment) throws Exception {
        Connection connection = this.getDbManager().getConnection();
        Collection result = new Vector(0);
        try {
            result = createMaterialRequest(connection,bean,personnelId,comment);
        }catch(Exception e ) {
            e.printStackTrace();
        }finally {
            this.getDbManager().returnConnection(connection);
        }
        return result;
    }

    public Collection createMaterialRequest(Connection connection, CabinetMrCreateViewBean bean, BigDecimal personnelId, String comment) throws Exception {

		Collection cin = new Vector();

		java.sql.Date sysDate = new java.sql.Date(System.currentTimeMillis());

		cin.add(bean.getItemId() != null ? bean.getItemId() : ""); //1
		cin.add(bean.getOrderingApplication() != null ? bean.getOrderingApplication() : ""); //2
		cin.add(""); // a_ship_to_location_id request_line_item.ship_to_location_id%type, 3
		cin.add(""); // a_vendor_qualifier request_line_item.vendor_qualifier%type, 4
		cin.add(""); // a_proposed_vendor request_line_item.proposed_vendor%type, 5
		cin.add(bean.getMrQuantity() != null ? bean.getMrQuantity() : ""); // a_quantity request_line_item.quantity%type, 6
		cin.add(bean.getCatalogPrice() != null ? bean.getCatalogPrice() : ""); //a_quoted_price request_line_item.quoted_price%type, 7
		cin.add(comment); // a_remark request_line_item.remark%type,  8
		cin.add(""); // a_required_datetime request_line_item.required_datetime%type, 9
		cin.add(""); // a_po_number request_line_item.po_number%type, 10
		cin.add(""); // a_release_number request_line_item.release_number%type, 11
		cin.add(bean.getCatPartNo() != null ? bean.getCatPartNo() : ""); //a_fac_part_no request_line_item.fac_part_no%type, 12
		cin.add(new Timestamp(sysDate.getTime())); // a_release_date request_line_item.release_date%type, 13
		cin.add("n"); // a_critical request_line_item.critical%type, 14
		cin.add(""); // a_doc_num request_line_item.doc_num%type, 15
		cin.add(""); // a_doc_state request_line_item.doc_state%type, 16
		cin.add(""); // a_sales_order request_line_item.sales_order%type, 17
		cin.add(""); // a_notes request_line_item.notes%type, 18
		cin.add(""); // a_delivery_point request_line_item.delivery_point%type, 19
		cin.add("Deliver by"); // a_delivery_type request_line_item.delivery_type%type, 20
		cin.add(""); // a_charge_type request_line_item.charge_type%type, 21
        cin.add(bean.getRelaxShelfLife()); // a_relax_shelf_life request_line_item.relax_shelf_life%type, 22
        cin.add(bean.getStocked() != null ? bean.getStocked() : ""); // a_item_type request_line_item.item_type%type, 23
		cin.add(""); // a_so_creation_date request_line_item.so_creation_date%type, 24
		cin.add(bean.getCompanyId() != null ? bean.getCompanyId() : ""); //a_company_id request_line_item.company_id%type, 25
		cin.add(bean.getItemId() != null ? bean.getItemId() : ""); //a_example_item_id request_line_item.example_item_id%type, 26
		cin.add(bean.getCatalogId() != null ? bean.getCatalogId() : ""); //a_catalog_id request_line_item.catalog_id%type, 27
		cin.add("n"); // a_scrap request_line_item.scrap%type, 28
		cin.add(""); // a_catalog_name request_line_item.catalog_name%type, 29
		cin.add(comment); // a_example_packaging request_line_item.example_packaging%type, 30
		cin.add(""); // a_use_approval_status request_line_item.use_approval_status%type, 31
		cin.add(""); // a_use_approver request_line_item.use_approver%type, 32
		cin.add(""); // a_use_approval_date request_line_item.use_approval_date%type, 33
		cin.add(""); // a_use_approval_comment request_line_item.use_approval_comment%type, 34
		cin.add(""); // a_dpas_code request_line_item.dpas_code%type, 35
		cin.add(""); // a_do_number request_line_item.do_number%type, 36
		cin.add(""); // a_card_receipt request_line_item.card_receipt%type, 37
		cin.add(""); // a_payload_id request_line_item.payload_id%type, 38
		cin.add(bean.getPartGroupNo() != null ? bean.getPartGroupNo() : ""); // a_part_group_no request_line_item.part_group_no%type, 39
		cin.add(""); // a_extended_price request_line_item.extended_price%type, 40
		cin.add(""); // a_prepaid_amount request_line_item.prepaid_amount%type, 41
		cin.add(bean.getInventoryGroup() != null ? bean.getInventoryGroup() : ""); //a_inventory_group request_line_item.inventory_group%type, 42
		cin.add(""); // a_request_line_status request_line_item.request_line_status%type, 43
		cin.add(""); // a_last_shipped request_line_item.last_shipped%type, 44
		cin.add(""); // a_cat_part_no_revision request_line_item.cat_part_no_revision%type, 45
		cin.add(bean.getCatalogPrice() != null ? bean.getCatalogPrice() : (bean.getDistUnitPrice() != null ? bean.getDistUnitPrice() : "")); //a_catalog_price request_line_item.catalog_price%type, 46
        cin.add(bean.getUnitPrice() != null ? bean.getUnitPrice() : ""); //a_baseline_price request_line_item.baseline_price%type, 47
		cin.add(""); // a_category_status request_line_item.category_status%type, 48
		cin.add("Y"); // a_cabinet_replenishment request_line_item.cabinet_replenishment%type, 49
		cin.add(""); // a_requested_dispensed_size_unit request_line_item.requested_dispensed_size_unit%type, 50
		/*Nawaz 09-12-07 passing application for application_desc till ron changes the procedure*/
        /*Nawaz 10-10-11 Procedure seems to get applicationdesc from fla. passing null*/
        cin.add(""); // a_application_desc request_line_item.application_desc%type, 51

		Collection cout = new Vector();
		cout.add(new Integer(java.sql.Types.NUMERIC)); // a_pr_number in out request_line_item.pr_number%type, 52
		cout.add(new Integer(java.sql.Types.VARCHAR)); // a_line_item out request_line_item.line_item%type, 53

		Collection cOptionalIn = new Vector();
		cOptionalIn.add(personnelId); // a_requestor purchase_request.requestor%type, 54
		cOptionalIn.add(bean.getOrderingFacility() != null ? bean.getOrderingFacility() : ""); // a_facility_id purchase_request.facility_id%type, 55
		cOptionalIn.add(new Timestamp(sysDate.getTime())); // a_request_date purchase_request.request_date%type, 56
		cOptionalIn.add("posubmit"); // a_pr_status purchase_request.pr_status%type, 57
		cOptionalIn.add(""); // a_ship_to purchase_request.ship_to%type, 58
		cOptionalIn.add(""); // a_requested_finance_approver purchase_request.requested_finance_approver%type, 59
		cOptionalIn.add(""); // a_rejection_reason purchase_request.rejection_reason%type, 60
		cOptionalIn.add(""); // a_requested_releaser purchase_request.requested_releaser%type, 61
		cOptionalIn.add(""); // a_forward_to purchase_request.forward_to%type, 62
		cOptionalIn.add(""); // a_end_user purchase_request.end_user%type, 63
		cOptionalIn.add(""); // a_department purchase_request.department%type, 64
		cOptionalIn.add("N"); // a_engineering_evaluation purchase_request.engineering_evaluation%type, 65
		cOptionalIn.add(""); // a_request_id purchase_request.request_id%type, 66
		cOptionalIn.add(bean.getAccountSysId() != null ? bean.getAccountSysId() : ""); //a_account_sys_id purchase_request.account_sys_id%type, 67
		cOptionalIn.add(""); // a_credit_card_type purchase_request.credit_card_type%type, 68
		cOptionalIn.add(""); // a_credit_card_number purchase_request.credit_card_number%type, 69
		cOptionalIn.add(""); // a_credit_card_expiration_date purchase_request.credit_card_expiration_date%type, 70
		cOptionalIn.add(""); // a_credit_card_name purchase_request.credit_card_name%type, 71
		cOptionalIn.add(""); // a_contact_info purchase_request.contact_info%type, 72
		cOptionalIn.add(new Timestamp(sysDate.getTime())); // a_submitted_date purchase_request.submitted_date%type, 73
		cOptionalIn.add(""); // a_pos_user purchase_request.pos_user%type, 74
		cOptionalIn.add(new Timestamp(sysDate.getTime())); // a_mr_released_date purchase_request.mr_released_date%type, 75
		cOptionalIn.add(""); // a_finance_approved_date purchase_request.finance_approved_date%type, 76
		cOptionalIn.add(""); // a_account_number in varchar2, 77
		cOptionalIn.add(""); // a_account_number2 in varchar2, 78
		cOptionalIn.add(""); // a_percentage in varchar2,'|',  79
		cOptionalIn.add(""); //  a_seperator in varchar2, 80
		cOptionalIn.add(""); // 81 -- list of dates for scheduled deliveries
		cOptionalIn.add(""); // 82 -- format for parsing scheduled delivery dates
		cOptionalIn.add(""); // 83 -- list of quantities corresponding to schedule dates
		cOptionalIn.add(""); // 84 -- a_commit char
		cOptionalIn.add(""); // 85 --	a_force_pr_line char,

		Collection cOptionalOut = new Vector();
		cOptionalOut.add(new Integer(java.sql.Types.VARCHAR)); // a_return out varchar2 86

		Collection cOptionalIn2 = new Vector();
		cOptionalIn2.add(new BigDecimal("1")); // a_customer_po_price_factor request_line_item.customer_po_price_factor%type default 1, 87
		cOptionalIn2.add(""); //a_unit_of_sale fac_item.unit_of_sale%type default null, 88
		cOptionalIn2.add(""); //	a_unit_of_sale_quantity_per_ea fac_item.unit_of_sale_quantity_per_each%type default null, 89
		cOptionalIn2.add(""); //	a_unit_of_sale_price request_line_item.unit_of_sale_price%type default null, 90
		cOptionalIn2.add("Y"); //	a_skip_approval char default 'N', 91
		cOptionalIn2.add(bean.isAutoApproveCabScanFin() ? "N" : ""); //	a_over_price_limit char default null, 92
		cOptionalIn2.add(bean.isAutoApproveCabScanUse() ? "N" : ""); //	a_over_use_limit char default null, 93
		cOptionalIn2.add(""); //	a_count_id ig_count_definition.count_id%type default null, 94
		cOptionalIn2.add(""); // a_ship_via_location_id		request_line_item.ship_via_location_id%type default null,
		cOptionalIn2.add(""); // a_radian_po			request_line_item.radian_po%type default null,
		cOptionalIn2.add(""); // a_po_line			request_line_item.po_line%type default null,
		cOptionalIn2.add(bean.getCatalogCompanyId() != null ? bean.getCatalogCompanyId() : ""); // a_catalog_company_id request_line_item.catalog_company_id%type default null,
		cOptionalIn2.add(bean.hasCurrencyId() ? bean.getCurrencyId() : bean.getDefaultCurrencyId()); // a_currency_id			request_line_item.currency_id%type default null,
        cOptionalIn2.add(""); // a_billing_method		request_line_item.billing_method%type default null,
		cOptionalIn2.add("Y"); // a_allocate			char default 'Y',
		cOptionalIn2.add("Y"); // a_auto_allocate			request_line_item.auto_allocate%type default 'Y',
		cOptionalIn2.add("N"); // a_allocate_by_distance		request_line_item.allocate_by_distance%type default 'N',
		cOptionalIn2.add("N"); // a_all_or_none			request_line_item.all_or_none%type default 'N',
		cOptionalIn2.add(""); // a_cost_center			request_line_item.cost_center%type default null,
		cOptionalIn2.add(""); // a_cost_center_classification	request_line_item.cost_center_classification%type default null,
		cOptionalIn2.add(""); // a_process			request_line_item.process%type default null,
		cOptionalIn2.add(""); // a_order_type			request_line_item.order_type%type default null,
		cOptionalIn2.add(""); // a_chemical_category		request_line_item.chemical_category%type default null,
		cOptionalIn2.add(bean.getDropShipOverride()); // a_drop_ship_override		request_line_item.drop_ship_override%type default n
		cOptionalIn2.add(""); //		a_firm_price_required		request_line_item.firm_price_required%type default null,
		//if distributor Ops
        if (StringHandler.emptyIfNull(bean.getDistributorOps()).equalsIgnoreCase("Y")) {
			cOptionalIn2.add("Y"); //	a_spec_check_required		request_line_item.spec_check_required%type default 'N',
			cOptionalIn2.add("");//		a_ship_to_company_id		request_line_item.ship_to_company_id%type default null,
			cOptionalIn2.add(""); //	a_delivery_instruction		request_line_item.delivery_instruction%type default null,
			cOptionalIn2.add("Y"); //	a_planned			request_line_item.planned%type default 'Y',
			cOptionalIn2.add(bean.getShelfLifeRequired());//		a_remaining_shelf_life_percent	request_line_item.remaining_shelf_life_percent%type default null,
			cOptionalIn2.add(""); //	a_shelf_life_days		request_line_item.shelf_life_days%type default null,
			cOptionalIn2.add(""); //	a_shelf_life_basis		request_line_item.shelf_life_basis%type default null,
			cOptionalIn2.add("");//		a_promise_date			request_line_item.promise_date%type default null,
			cOptionalIn2.add(personnelId);//		a_submitted_by purchase_request.submitted_by%type default null,
			cOptionalIn2.add(bean.getCustomerId()); //		a_customer_id purchase_request.customer_id%type default null,
			cOptionalIn2.add(""); //	a_original_sales_quote_id purchase_request.original_sales_quote_id%type default null,
			cOptionalIn2.add(bean.getPriceGroupId()); //		a_price_group_id purchase_request.price_group_id%type default null,
			cOptionalIn2.add(""); //	a_requestor_name purchase_request.requestor_name%type default null,
			cOptionalIn2.add(""); //	a_requestor_phone purchase_request.requestor_phone%type default null,
			cOptionalIn2.add("");//		a_requestor_fax purchase_request.requestor_fax%type default null,
			cOptionalIn2.add("");//		a_requestor_email purchase_request.requestor_email%type default null,
			cOptionalIn2.add(bean.getPaymentTerms());//		a_payment_terms purchase_request.payment_terms%type default null,
			cOptionalIn2.add(bean.getOpsEntityId());//		a_ops_entity_id purchase_request.ops_entity_id%type default null,
			cOptionalIn2.add(bean.getOpsCompanyId());//		a_ops_company_id purchase_request.ops_company_id%type default null,
			cOptionalIn2.add(bean.getFieldSalesRepId());//	a_field_sales_rep_id purchase_request.field_sales_rep_id%type default null,
			cOptionalIn2.add("");//		a_sales_agent_id purchase_request.sales_agent_id%type default null,
			cOptionalIn2.add("");//		a_special_instructions purchase_request.special_instructions%type default null,
			cOptionalIn2.add("");//		a_carrier_account_id purchase_request.carrier_account_id%type default null,
			cOptionalIn2.add("");//		a_carrier_contact purchase_request.carrier_contact%type default null,
			cOptionalIn2.add("");//		a_carrier_service_type purchase_request.carrier_service_type%type default null,
			if(StringHandler.emptyIfNull(bean.getShipComplete()) == "ORDER" ) {
			 	cOptionalIn2.add("Y");//	a_ship_complete request_line_item.ship_complete%type default null,
			 	cOptionalIn2.add("Y");//	a_consolidate_shipment request_line_item.consolidate_shipment%type default null,$("shipComplete"+rowid).checked = true;
			}else if (StringHandler.emptyIfNull(bean.getShipComplete()) == "LINE" ) {
			 	cOptionalIn2.add("Y");//	a_ship_complete request_line_item.ship_complete%type default null,
			 	cOptionalIn2.add("N");//	a_consolidate_shipment request_line_item.consolidate_shipment%type default null,
			}else {
				cOptionalIn2.add("");//		a_ship_complete request_line_item.ship_complete%type default null,
			 	cOptionalIn2.add("");//		a_consolidate_shipment request_line_item.consolidate_shipment%type default null,
			}
			cOptionalIn2.add("");//		a_charge_recurrence request_line_item.charge_recurrence%type default null,
			cOptionalIn2.add("N");//	a_header_additional_charge char default 'N',
			cOptionalIn2.add("");//		a_customer_po_line request_line_item.customer_po_line%type default null,
			cOptionalIn2.add("N");//	a_tax_exempt request_line_item.tax_exempt%type default 'N',
			cOptionalIn2.add("");//		a_delivery_note request_line_item.delivery_note%type default null,
			cOptionalIn2.add(bean.getExpectedUnitCost());//		a_expected_unit_cost request_line_item.expected_unit_cost%type default null,
			cOptionalIn2.add("");//		a_add_charge_description request_line_item.add_charge_description%type default null,
			cOptionalIn2.add(bean.getBillToCompanyId());//		a_bill_to_company_id purchase_request.bill_to_company_id%type default null,
			cOptionalIn2.add(bean.getBillToLocationId());//		a_bill_to_location_id purchase_request.bill_to_location_id%type default null,
			cOptionalIn2.add(bean.getCustomerPartNo());//		a_customer_part_no request_line_item.customer_part_no%type default null,
			cOptionalIn2.add("N");//	a_ignore_pr_rules char default 'N',
			cOptionalIn2.add("");//		a_rli_inventory_group request_line_item.inventory_group%type default null,
			cOptionalIn2.add("Ex Works");//		a_inco_terms purchase_request.inco_terms%type default null,
			cOptionalIn2.add(bean.getDefCustomerServiceRepId());//		a_customer_service_rep_id purchase_request.customer_service_rep_id%type default null,
			cOptionalIn2.add("");//		a_release_status purchase_request.release_status%type default null,
			cOptionalIn2.add("");//		a_customer_po_date purchase_request.customer_po_date%type default null,
			cOptionalIn2.add("");//		a_customer_requisition_number request_line_item.customer_requisition_number%type default null,
			cOptionalIn2.add("");//		a_internal_note request_line_item.internal_note%type default null,
			cOptionalIn2.add("");//		a_purchasing_note request_line_item.purchasing_note%type default null,
			cOptionalIn2.add("N");//	a_allow_null_application char default 'N',
			cOptionalIn2.add("");//		a_original_sales_quote_line request_line_item.original_sales_quote_line%type default null,
			cOptionalIn2.add( "Cabinet Scan");//a_material_request_origin purchase_request.material_request_origin%type default null,
		//not distributor Ops
		}else {
			cOptionalIn2.add("N"); //	a_spec_check_required		request_line_item.spec_check_required%type default 'N',
			cOptionalIn2.add("");//		a_ship_to_company_id		request_line_item.ship_to_company_id%type default null,
			cOptionalIn2.add(""); //	a_delivery_instruction		request_line_item.delivery_instruction%type default null,
			cOptionalIn2.add("Y"); //	a_planned			request_line_item.planned%type default 'Y',
			cOptionalIn2.add("");//		a_remaining_shelf_life_percent	request_line_item.remaining_shelf_life_percent%type default null,
			cOptionalIn2.add(""); //	a_shelf_life_days		request_line_item.shelf_life_days%type default null,
			cOptionalIn2.add(""); //	a_shelf_life_basis		request_line_item.shelf_life_basis%type default null,
			cOptionalIn2.add("");//		a_promise_date			request_line_item.promise_date%type default null,
			cOptionalIn2.add(""); //	a_submitted_by purchase_request.submitted_by%type default null,
			cOptionalIn2.add(bean.getCustomerId()); //	a_customer_id purchase_request.customer_id%type default null,
			cOptionalIn2.add(""); //	a_original_sales_quote_id purchase_request.original_sales_quote_id%type default null,
			cOptionalIn2.add(""); //	a_price_group_id purchase_request.price_group_id%type default null,
			cOptionalIn2.add(""); //	a_requestor_name purchase_request.requestor_name%type default null,
			cOptionalIn2.add(""); //	a_requestor_phone purchase_request.requestor_phone%type default null,
			cOptionalIn2.add("");//		a_requestor_fax purchase_request.requestor_fax%type default null,
			cOptionalIn2.add("");//		a_requestor_email purchase_request.requestor_email%type default null,
			cOptionalIn2.add("");//		a_payment_terms purchase_request.payment_terms%type default null,
			cOptionalIn2.add(bean.getOpsEntityId());//		a_ops_entity_id purchase_request.ops_entity_id%type default null,
			cOptionalIn2.add(bean.getOpsCompanyId());//		a_ops_company_id purchase_request.ops_company_id%type default null,
			cOptionalIn2.add("");//		a_field_sales_rep_id purchase_request.field_sales_rep_id%type default null,
			cOptionalIn2.add("");//		a_sales_agent_id purchase_request.sales_agent_id%type default null,
			cOptionalIn2.add("");//		a_special_instructions purchase_request.special_instructions%type default null,
			cOptionalIn2.add("");//		a_carrier_account_id purchase_request.carrier_account_id%type default null,
			cOptionalIn2.add("");//		a_carrier_contact purchase_request.carrier_contact%type default null,
			cOptionalIn2.add("");//		a_carrier_service_type purchase_request.carrier_service_type%type default null,
			cOptionalIn2.add("");//		a_ship_complete request_line_item.ship_complete%type default null,
			cOptionalIn2.add("");//		a_consolidate_shipment request_line_item.consolidate_shipment%type default null,
			cOptionalIn2.add("");//		a_charge_recurrence request_line_item.charge_recurrence%type default null,
			cOptionalIn2.add("N");//		a_header_additional_charge char default 'N',
			cOptionalIn2.add("");//		a_customer_po_line request_line_item.customer_po_line%type default null,
			cOptionalIn2.add("N");//		a_tax_exempt request_line_item.tax_exempt%type default 'N',
			cOptionalIn2.add("");//		a_delivery_note request_line_item.delivery_note%type default null,
			cOptionalIn2.add("");//		a_expected_unit_cost request_line_item.expected_unit_cost%type default null,
			cOptionalIn2.add("");//		a_add_charge_description request_line_item.add_charge_description%type default null,
			cOptionalIn2.add("");//		a_bill_to_company_id purchase_request.bill_to_company_id%type default null,
			cOptionalIn2.add("");//		a_bill_to_location_id purchase_request.bill_to_location_id%type default null,
			cOptionalIn2.add("");//		a_customer_part_no request_line_item.customer_part_no%type default null,
			cOptionalIn2.add("N");//		a_ignore_pr_rules char default 'N',
			cOptionalIn2.add("");//		a_rli_inventory_group request_line_item.inventory_group%type default null,
			cOptionalIn2.add("");//		a_inco_terms purchase_request.inco_terms%type default null,
			cOptionalIn2.add("");//		a_customer_service_rep_id purchase_request.customer_service_rep_id%type default null,
			cOptionalIn2.add("");//		a_release_status purchase_request.release_status%type default null,
			cOptionalIn2.add("");//		a_customer_po_date purchase_request.customer_po_date%type default null,
			cOptionalIn2.add("");//		a_customer_requisition_number request_line_item.customer_requisition_number%type default null,
			cOptionalIn2.add("");//		a_internal_note request_line_item.internal_note%type default null,
			cOptionalIn2.add("");//		a_purchasing_note request_line_item.purchasing_note%type default null,
			cOptionalIn2.add("N");//		a_allow_null_application char default 'N',
			cOptionalIn2.add("");//		a_original_sales_quote_line request_line_item.original_sales_quote_line%type default null,
			cOptionalIn2.add( "");//	a_material_request_origin purchase_request.material_request_origin%type default null,
		}
		cOptionalIn2.add("");//		a_shipping_reference request_line_item.shipping_reference%type default null,
		cOptionalIn2.add("");//		a_pr_internal_note purchase_request.internal_note%type default null,
		cOptionalIn2.add("");//		a_use_application request_line_item.application%type default null) is
        Collection result = this.getDbManager().doProcedure(connection,"P_CREATE_MR", cin, cout, cOptionalIn, cOptionalOut, cOptionalIn2);
		return result;
	}

}
