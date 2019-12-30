package radian.web.servlets.internal;

import radian.tcmis.server7.share.helpers.ServerResourceBundle;

public class InternalServerResourceBundle  extends ServerResourceBundle {

    static final Object[][] cHash = {
        {"LOG_DIR","/logsInternal"},
        {"DEBUG","true"},
        {"DB_CLIENT","Internal"},
        {"DB_CLIENT_NAME","Tcm_Ops"},
        {"ORACLE_DRIVER",""},
        {"ORACLE_URL",""},
        {"DB_USER",""},
        {"RELATIVE_HTML","/tcmIS/msds/internal"},
        {"DB_PASS",""},
        {"WEBS_HOME_WWWS",""},
        {"WEBS_HOME_WWW",""},
        {"HOME_PAGE","/tcmIS/internal/Home"},
        {"HOME_IMAGE","/images/buttons/internal/internal.gif"},
        {"HELP_HTML_PATH","/tcmIS/help/seagate/"},
        {"HELP_SERVLET_PATH","/tcmIS/servlet/radian.web.servlets.help.SeagatehelpMenu"},
        {"VIEW_MSDS","/tcmIS/all/ViewMsds?"},
        {"REGISTER_SERVLET","/tcmIS/internal/Register"},
        {"HOME_SERVLET","/tcmIS/internal/Home"},
        {"JNLP_MAINCLASS","RadianCCInternal.jar"},
        {"JNLP_DIRECTORY","/jnlp/internal"},
        {"TCMIS_START_CLASS","radian.tcmis.client.client.internal.gui.InternalCmisApp"},
        {"BATCH_REPORT_SERVLET","/tcmIS/internal/batchreport?"},
        {"BAR_BUILD_SERVLET","/tcmIS/Msds/All?limit=yes"},
        {"BUILD_SERVLET","/tcmIS/Msds/All?"},
        {"WEB_REGISTRATION","Yes"},
        {"HUBRECV_SERVLET_QC","/tcmIS/Hub/ReceivingQC?session=Active"},
        {"HUBRECV_SERVLET","/tcmIS/Hub/Receiving?session=Active"},
        {"LABEL_SERVLET","/tcmIS/Hub/Receiving?session=Active&generate_labels=yes&UserAction=GenerateLabels"},
        {"HUB_LABEL_SERVLET","/tcmIS/Hub/Logistics?session=Active"},
        {"RE_LABEL_SERVLET","/tcmIS/Hub/Logistics?session=Active&generate_labels=yes&UserAction=GenerateLabels"},
        {"HUB_INVEN_RECONCIL","/tcmIS/Hub/Reconciliation?session=Active"},
        {"HUB_INVEN_RECONCIL_NEWPICK","/tcmIS/Hub/NewPick?session=Active"},
        {"INVENTORY_LEVEL","/tcmIS/Hub/LevelMgmt"},
        {"INVENTORY_TRANSACTION","/tcmIS/Hub/Transactions"},
        {"CUSTOMER_RETURNS","/tcmIS/Hub/CustomerReturns"},
        {"SHIP_CONFIRM","/tcmIS/Hub/ShipConfirm"},
        {"OPERATION_ANALYSIS","/tcmIS/Hub/AllocationAnalysis"},
        {"SEARCH_PICKLIST","/tcmIS/Hub/searchpicklist"},
        {"GEN_PICKLIST","/tcmIS/Hub/genpicklist"},
        {"PICKINGQC","/tcmIS/Hub/PickingQC"},
        {"CHANGE_QTY","/tcmIS/Hub/changeshipqty?session=Active"},
        //Quality Control
        {"QUALITY_CONTROL","/tcmIS/Catalog/catqc?"},
        {"QUALITY_CONTROL_DETAILS","/tcmIS/Catalog/catqcdetails?"},
        {"QUALITY_CONTROL_ORIGINAL","/tcmIS/Catalog/catqcoriginal?"},
        {"QUALITY_CONTROL_MATERIAL","/tcmIS/Catalog/likematerial?"},
        {"QUALITY_CONTROL_MFG","/tcmIS/Catalog/likemfgid?"},
        {"QUALITY_CONTROL_MSDS","/tcmIS/Catalog/msdsqc?"},
        {"QUALITY_CONTROL_CASNUM","/tcmIS/Catalog/likecasnum?"},
	    {"QUALITY_CONTROL_REVDATE","/tcmIS/Catalog/likerevdate?"},
        {"QUALITY_CONTROL_ITEM","/tcmIS/Catalog/likeitem?"},
	    //Purchase Orders
	    {"BUY_ORDER","/tcmIS/supply/Buyorder?session=Active"},
	    {"PURCHASE_ORDER","/tcmIS/supply/purchorder?session=Active"},
	    {"PURCHASE_ORDER_SUPPLIER","/tcmIS/purchase/posupplier?session=Active"},
        {"PURCHASE_ORDER_SHIPTO","/tcmIS/purchase/poshipto?session=Active"},
        {"PURCHASE_ORDER_CARRIER","/tcmIS/purchase/pocarrier?session=Active"},
	    {"PURCHASE_ORDER_ITEM","/tcmIS/purchase/poitem?session=Active"},
	    {"PURCHASE_ORDER_ITEMNOTES","/tcmIS/purchase/itemnotes?session=Active"},
        {"PURCHASE_ORDER_PO","/tcmIS/purchase/popo?session=Active"},
        {"PURCHASE_ORDER_BPO","/tcmIS/purchase/pobpo?session=Active"},
        {"PURCHASE_ORDER_ORDERTAKER","/tcmIS/purchase/poordertaker?session=Active"},
        {"PURCHASE_ORDER_CURRENCY","/tcmIS/purchase/pocurrency?session=Active"},
	    {"PURCHASE_ORDER_SHOWMSDSREVDATE","/tcmIS/purchase/showmsdsrevdate?session=Active"},
	    {"PURCHASE_ORDER_SHOWSPECS","/tcmIS/purchase/showspecs?session=Active"},
	    {"PURCHASE_ORDER_SHOWCLIENTBUYS","/tcmIS/purchase/showclientbuys?session=Active"},
	    {"PURCHASE_ORDER_SHOWFLOWDOWNS","/tcmIS/purchase/showflowdowns?session=Active"},
	    {"PURCHASE_ORDER_SHOWTCMBUYS","/tcmIS/purchase/showtcmbuys?session=Active"},
	    {"PURCHASE_ORDER_PONOTES","/tcmIS/purchase/ponotes?session=Active"},
	    {"PURCHASE_ORDER_CANNEDCOMMENTS","/tcmIS/purchase/showcannedcomments?session=Active"},
	    {"PURCHASE_ORDER_REQUEST_TRANSFER","/tcmIS/purchase/showtcmbuys?"},
	    //Invoice Accounts Payable
	    {"INVOICE_PURCHASE_ORDER","/tcmIS/Invoice/AccountsPayable?session=Active"},
	    {"INVOICE_PURCHASE_ORDER_ITEM","/tcmIS/Invoice/invitem?session=Active"},
	    {"INVOICE_PURCHASE_ORDER_ORDERTAKER","/tcmIS/Invoice/newinvoice?session=Active"},
	    {"INVOICE_REPORTS","/tcmIS/Invoice/InvoiceReport?session=Active"},
			{"INVOICE_EDIT_PAYMENT_TERMS","/tcmIS/invoice/editInvoiceTerms?session=Active"},
        //{"WEB_HOME_PAGE","http://localhost/"},
        //{"HOME_DIRECTORY","C:\\WebPages\\"},
		{"GENERIC_SHIPTO","/tcmIS/hub/genericshipto?session=Active"},
        {"WEB_HOME_PAGE","/"},
        {"HOME_DIRECTORY","/webdata/html/"},
        {"WEBS_HOME_WWWS",""},
        {"DB_PASS",""}
    };

    public InternalServerResourceBundle()
    {
      super();
      addHash( cHash );
    }
}
