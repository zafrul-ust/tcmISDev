package radian.web.servlets.ray;

import radian.tcmis.server7.share.helpers.ServerResourceBundle ;

public class RayServerResourceBundle  extends ServerResourceBundle {

    static final Object[][] cHash = {
        {"LOG_DIR","/logsRay"},
        {"DEBUG","true"},
        {"DB_CLIENT","Raytheon"},
        {"ORACLE_DRIVER",""},
        {"ORACLE_URL",""},
        {"DB_USER",""},
        {"RELATIVE_HTML","/tcmIS/msds/ray"},
        {"DB_PASS",""},
        {"DB_CLIENT_NAME","Ray"},

        {"WEBS_HOME_WWWS",""},
        {"WEBS_HOME_WWW",""},
        {"HOME_PAGE","/tcmIS/ray/Home"},
        {"HOME_IMAGE","/images/buttons/ray/ray.gif"},
        {"HELP_HTML_PATH","/tcmIS/help/new/"},
        {"VIEW_MSDS","/tcmIS/ray/ViewMsds?"},
        {"VIEW_NON_MAT_MSDS","/tcmIS/ray/nonmatmsdsview?"},
        {"REGISTER_SERVLET","/tcmIS/ray/Register"},
        {"HOME_SERVLET","/tcmIS/ray/Home"},
        {"JNLP_MAINCLASS","RadianCCRay.jar"},
        {"JNLP_DIRECTORY","/jnlp/ray"},
        {"TEST_JNLP_DIRECTORY","/prereleasejnlp/ray"},
        {"TCMIS_START_CLASS","radian.tcmis.client.client.ray.gui.RayCmisApp"},
        {"BATCH_REPORT_SERVLET","/tcmIS/ray/batchreport?"},

        {"ADDUSER_SERVLET","/tcmIS/ray/Register?useraction=AddUser"},
        {"CHGUSER_SERVLET","/tcmIS/ray/Register?useraction=ChgUser"},
        {"ADD_SERVLET","/tcmIS/ray/Register?useraction=Add"},
        {"LOGINREG_SERVLET","/tcmIS/ray/Register?useraction=Login"},
        {"CHANGE_SERVLET","/tcmIS/ray/Register?useraction=Change"},
        {"REGISTER_HOME_SERVLET","/tcmIS/ray/Register?"},
        {"TEST_INSTALL_SERVLET","/tcmIS/ray/Register?useraction=startingtcmis"},
        {"BAR_BUILD_SERVLET","/tcmIS/ray/Msds?limit=yes"},
        {"BUILD_SERVLET","/tcmIS/ray/Msds?"},
        {"SERVLET_DIR_WWW",""},
        {"ERW_TEMPLATE_DIR","/webdata/html/template/"},
        {"BASE_DIR_TCM_WWW","/webdata/html/"},
        {"ERW_REPORT_DIR_ACTIVE","/webdata/html/reports/temp/"},
        {"ERW_REPORT_DIR_BATCH","/webdata/html/reports/"},
        {"WEBS_HOME_WWW_ERW_ACTIVE","/reports/temp/"},
        {"WEBS_HOME_WWW_ERW_BATCH","/reports/"},
        {"CATALOGING_SERVLET","/tcmIS/ray/cataloging?"},
        {"PRINT_SCREEN","/tcmIS/ray/printscreen"},
        {"HELP_SERVLET_PATH","/tcmIS/client/help"},

        {"DROPRECEIVING","Yes"},
        {"RELABELING","No"},
        {"DROPRECEIVING_SERVLET","/tcmIS/ray/dropshipreceivingmain.do"},
        {"DROPRECEIVING_SERVLET_HEADER","/tcmIS/ray/dropshipreceivingmain.do"},
        {"RELABELING_SERVLET","/tcmIS/ray/Relabeling?session=Active"},

        {"COST_REPORT","/tcmIS/ray/betacostreport?"},
        {"SCHEDULDE_DELIVERY","/tcmIS/ray/schdelivery?"},
        {"CANCEL_MR","/tcmIS/ray/cancel?"},
        {"WASTE_ORDER","/tcmIS/ray/wasteorder?"},
        {"WASTE_ADHOC_REPORT","/tcmIS/ray/wastereport?"},
        {"USAGE_ADHOC_REPORT","/tcmIS/ray/usagereport?"},
        {"MATERIAL_MATRIX_REPORT","/tcmIS/ray/matrixreport?"},

        {"SHIPPINGINFO","No"},
        {"SHIPPINGINFO_SERVLET","/tcmIS/ray/shipinfo?session=Active"},
        {"SHIPPINGINFO_SERVLET_HEADER","/tcmIS/ray/shipinfo"},

        {"WORKAREA_MANAGEMENT","Yes"},
        {"WORKAREA_MANAGEMENT_SERVLET","/tcmIS/ray/showuseapprovalstatus.do"},

        {"MATERIALS_USED","Yes"},
        {"MATERIALS_USED_SERVLET","/tcmIS/ray/workareausagemain.do"},

        {"WEB_CATALOG","Yes"},
        {"WEB_CATALOG_SERVLET","/tcmIS/ray/catalog.do"},

        {"TSDF","Yes"},
        {"TSDF_RECEIVING_SERVLET","/tcmIS/ray/tsdfwastereceivingmain.do"},
        {"TSDF_CONTAINER_REPORT_SERVLET","/tcmIS/ray/tsdfcontainerreportmain.do"},
        {"WASTE_MANIFEST_REPORT_SERVLET","/tcmIS/ray/manifestreportmain.do"},
        {"WEB_ORDER_TRACKING","Yes"},
        {"WEB_ORDER_TRACKING_SERVLET","/tcmIS/ray/ordertrackingmain.do"},
        {"WEB_REGISTRATION","Yes"}
    };

    public RayServerResourceBundle(){
        super();
        addHash(cHash);
    }

}