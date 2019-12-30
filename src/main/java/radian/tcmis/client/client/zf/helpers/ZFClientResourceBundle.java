package radian.tcmis.client.client.zf.helpers;

import radian.tcmis.client.share.helpers.ClientResourceBundle;

public class ZFClientResourceBundle  extends ClientResourceBundle {

     static final Object[][] cHash = {
             {"SERVLET_PACKAGE","radian.tcmis.server7.client.zf.servlets."},
             {"APP_NAME","ZF Sachs GmbH"},
             {"SHOW_LOGON_FRAME","No"},        //10-29-01 whether to show the logon frame or not
             {"USE_GLOBAL_PASSWORD","No"},
             {"GLOBAL_PASSWORD","t"},
             {"LOG_DIR","/temp/logs/zf"},
             {"VERSION","1.8.05"},
             {"DOWNLOAD_DIR",""},
             {"EXEC_FILE","tcmISRun.exe"},
             {"CONFIG_FILE","tcmis.cfg"},
             {"DEBUG_FILE","cmisapp.deb"},
             {"START_CLASS","radian.tcmis.client.client.zf.gui.ZFCmisApp"},
             {"DEBUG","false"},
             {"CLIENT_INITIALS","ZF"},
             {"MSDS_URL","/tcmIS/zf/ViewMsds?"},
             {"WASTE_URL","/tcmIS/zf/wasteprofile?"},
             {"PRINT_SCREEN_URL","/tcmIS/zf/printscreen?"},
             {"WORK_AREA_NAME","Work Area"},
             {"WORK_AREA_NAME_PLURAL","Work Areas"}
     };

     public ZFClientResourceBundle(){
            super();
            addHash(cHash);
     }

}