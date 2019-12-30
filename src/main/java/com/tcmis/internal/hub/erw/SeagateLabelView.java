package com.tcmis.internal.hub.erw;

import java.util.Collection;
import java.util.Iterator;
import java.util.Vector;

import com.tcmis.common.util.NumberHandler;
import com.tcmis.common.util.StringHandler;
import com.tcmis.internal.hub.beans.SeagateLabelViewBean;

public class SeagateLabelView {

 private String MFG_LOT = "";
 private String BIN = "";
 private String DATE_OF_RECEIPT = "";
 private String RECEIPT_ID = "";
 private String CATALOG_ID = "";
 private String CAT_PART_NO = "";
 private String HEALTH = "";
 private String FLAMABILITY = "";
 private String REACTIVITY = "";
 private String CHEMICAL_STORAGE = "";
 private String SIGNAL_WORD = "";
 private String PERSONAL_PROTECTIVE_EQUIPMENT = "";
 private String DISPOSAL_CODE = "";
 private String DISPOSAL_CONTAINER_CODE = "";
 private String HAZARD_CODE1 = "";
 private String HAZARD_CODE2 = "";
 private String PART_DESCRIPTION = "";
 private String ITEM_ID = "";
 private String HAZARD_CODE_DESC1 = "";
 private String HAZARD_CODE_DESC2 = "";
 private String HAZARD_CODE_CAT_DESC1 = "";
 private String HAZARD_CODE_CAT_DESC2 = "";
 private String EXPIRE_DATE = "";
 private String STORAGE_TEMP = "";
 private String BARCODEID = "";
 private String ITEM_DESC = "";

 public SeagateLabelView(SeagateLabelViewBean seagateLabelViewBean) {
	MFG_LOT = StringHandler.emptyIfNull(seagateLabelViewBean.getMfgLot());
	BIN = StringHandler.emptyIfNull(seagateLabelViewBean.getBin());
	DATE_OF_RECEIPT = StringHandler.emptyIfNull(seagateLabelViewBean.getDateOfReceipt());
	RECEIPT_ID = NumberHandler.emptyIfNull(seagateLabelViewBean.getReceiptId());
	CATALOG_ID = StringHandler.emptyIfNull(seagateLabelViewBean.getCatalogId());
	CAT_PART_NO = StringHandler.emptyIfNull(seagateLabelViewBean.getCatPartNo());
	HEALTH = StringHandler.emptyIfNull(seagateLabelViewBean.getHealth());
	FLAMABILITY = StringHandler.emptyIfNull(seagateLabelViewBean.getFlamability());
	REACTIVITY = StringHandler.emptyIfNull(seagateLabelViewBean.getReactivity());
	CHEMICAL_STORAGE = StringHandler.emptyIfNull(seagateLabelViewBean.getChemicalStorage());
	SIGNAL_WORD = StringHandler.emptyIfNull(seagateLabelViewBean.getSignalWord());
	PERSONAL_PROTECTIVE_EQUIPMENT = StringHandler.emptyIfNull(seagateLabelViewBean.getPersonalProtectiveEquipment());
	DISPOSAL_CODE = StringHandler.emptyIfNull(seagateLabelViewBean.getDisposalCode());
	DISPOSAL_CONTAINER_CODE = StringHandler.emptyIfNull(seagateLabelViewBean.getDisposalCode()) + StringHandler.emptyIfNull(seagateLabelViewBean.getDisposalContainerCode());
	HAZARD_CODE1 = StringHandler.emptyIfNull(seagateLabelViewBean.getHazardCode1());
	HAZARD_CODE2 = StringHandler.emptyIfNull(seagateLabelViewBean.getHazardCode2());
	PART_DESCRIPTION = StringHandler.emptyIfNull(seagateLabelViewBean.getPartDescription());
	ITEM_ID = NumberHandler.emptyIfNull(seagateLabelViewBean.getItemId());
	HAZARD_CODE_DESC1 = StringHandler.emptyIfNull(seagateLabelViewBean.getHazardCodeDesc1());
	HAZARD_CODE_DESC2 = StringHandler.emptyIfNull(seagateLabelViewBean.getHazardCodeDesc2());
	HAZARD_CODE_CAT_DESC1 = StringHandler.emptyIfNull(seagateLabelViewBean.getHazardCodeCatDesc1());
	HAZARD_CODE_CAT_DESC2 = StringHandler.emptyIfNull(seagateLabelViewBean.getHazardCodeCatDesc2());
	EXPIRE_DATE = StringHandler.emptyIfNull(seagateLabelViewBean.getExpireDate());
	STORAGE_TEMP = StringHandler.emptyIfNull(seagateLabelViewBean.getLabelStorageTemp());
	BARCODEID = StringHandler.emptyIfNull(seagateLabelViewBean.getBarcodeReceiptId());
	ITEM_DESC = StringHandler.emptyIfNull(seagateLabelViewBean.getItemDesc());
 }

 public String getmfg_lotDesc() {
	return MFG_LOT;
 }

 public String getbinDesc() {
	return BIN;
 }

 public String getdate_of_receiptDesc() {
	return DATE_OF_RECEIPT;
 }

 public String getreceipt_idDesc() {
	return RECEIPT_ID;
 }

 public String getcatalog_idDesc() {
	return CATALOG_ID;
 }

 public String getcat_part_noDesc() {
	return CAT_PART_NO;
 }

 public String gethealthDesc() {
	return HEALTH;
 }

 public String getflamabilityDesc() {
	return FLAMABILITY;
 }

 public String getreactivityDesc() {
	return REACTIVITY;
 }

 public String getchemical_storageDesc() {
	return CHEMICAL_STORAGE;
 }

 public String getsignal_wordDesc() {
	return SIGNAL_WORD;
 }

 public String getpersonal_protective_equipmentDesc() {
	return PERSONAL_PROTECTIVE_EQUIPMENT;
 }

 public String getdisposal_codeDesc() {
	return DISPOSAL_CODE;
 }

 public String getdisposal_container_codeDesc() {
	return DISPOSAL_CONTAINER_CODE;
 }

 public String gethazard_code1Desc() {
	return HAZARD_CODE1;
 }

 public String gethazard_code2Desc() {
	return HAZARD_CODE2;
 }

 public String getpart_descriptionDesc() {
	return PART_DESCRIPTION;
 }

 public String getitem_idDesc() {
	return ITEM_ID;
 }

 public String gethazard_code_desc1Desc() {
	return HAZARD_CODE_DESC1;
 }

 public String gethazard_code_desc2Desc() {
	return HAZARD_CODE_DESC2;
 }

 public String gethazard_code_cat_desc1Desc() {
	return HAZARD_CODE_CAT_DESC1;
 }

 public String gethazard_code_cat_desc2Desc() {
	return HAZARD_CODE_CAT_DESC2;
 }

 public String getexpire_dateDesc() {
	return EXPIRE_DATE;
 }

 public String getstorage_tempDesc() {
	return STORAGE_TEMP;
 }

 public String getbarcodeidDesc() {
	return BARCODEID;
 }

 public String getitemDesc() {
	return ITEM_DESC;
 }

 public static Vector getFieldVector() {
	Vector v = new Vector();
	v.addElement("MFG_LOT = getmfg_lotDesc");
	v.addElement("BIN = getbinDesc");
	v.addElement("DATE_OF_RECEIPT = getdate_of_receiptDesc");
	v.addElement("RECEIPT_ID = getreceipt_idDesc");
	v.addElement("CATALOG_ID = getcatalog_idDesc");
	v.addElement("CAT_PART_NO = getcat_part_noDesc");
	v.addElement("HEALTH = gethealthDesc");
	v.addElement("FLAMABILITY = getflamabilityDesc");
	v.addElement("REACTIVITY = getreactivityDesc");
	v.addElement("CHEMICAL_STORAGE = getchemical_storageDesc");
	v.addElement("SIGNAL_WORD = getsignal_wordDesc");
	v.addElement("PERSONAL_PROTECTIVE_EQUIPMENT = getpersonal_protective_equipmentDesc");
	v.addElement("DISPOSAL_CODE = getdisposal_codeDesc");
	v.addElement("DISPOSAL_CONTAINER_CODE = getdisposal_container_codeDesc");
	v.addElement("HAZARD_CODE1 = gethazard_code1Desc");
	v.addElement("HAZARD_CODE2 = gethazard_code2Desc");
	v.addElement("PART_DESCRIPTION = getpart_descriptionDesc");
	v.addElement("ITEM_ID = getitem_idDesc");
	v.addElement("HAZARD_CODE_DESC1 = gethazard_code_desc1Desc");
	v.addElement("HAZARD_CODE_DESC2 = gethazard_code_desc2Desc");
	v.addElement("HAZARD_CODE_CAT_DESC1 = gethazard_code_cat_desc1Desc");
	v.addElement("HAZARD_CODE_CAT_DESC2 = gethazard_code_cat_desc2Desc");
	v.addElement("EXPIRE_DATE = getexpire_dateDesc");
	v.addElement("STORAGE_TEMP = getstorage_tempDesc");
	v.addElement("BARCODEID = getbarcodeidDesc");
	v.addElement("ITEM_DESC = getitemDesc");

	return v;
 }

 public static Vector getVector(Collection labelData) {
	Vector out = new Vector();
	Iterator mainIterator = labelData.iterator();
	while (mainIterator.hasNext()) {
	 SeagateLabelViewBean seagateLabelViewBean = (
		SeagateLabelViewBean) mainIterator.next(); ;
	 int quantityReceived = seagateLabelViewBean.getQuantityReceived().intValue();
	 SeagateLabelView seagateLabelView = new SeagateLabelView(seagateLabelViewBean);
	 for (int k = 0; k < quantityReceived; k++)
	 {
		out.addElement(seagateLabelView);
	 }
	}
	return out;
 }
}