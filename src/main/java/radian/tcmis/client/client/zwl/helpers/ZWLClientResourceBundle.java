package radian.tcmis.client.client.zwl.helpers;

import radian.tcmis.client.share.helpers.ClientResourceBundle;

public class ZWLClientResourceBundle  extends ClientResourceBundle {

     static final Object[][] cHash = {
             {"SERVLET_PACKAGE","radian.tcmis.server7.client.zwl.servlets."},
             {"APP_NAME","Neue ZWL Zahradwerk Leipzip GmbH"},
             {"SHOW_LOGON_FRAME","No"},        //10-29-01 whether to show the logon frame or not
             {"USE_GLOBAL_PASSWORD","No"},
             {"GLOBAL_PASSWORD","t"},
             {"LOG_DIR","/temp/logs/zwl"},
             {"VERSION","1.8.05"},
             {"DOWNLOAD_DIR",""},
             {"EXEC_FILE","tcmISRun.exe"},
             {"CONFIG_FILE","tcmis.cfg"},
             {"DEBUG_FILE","cmisapp.deb"},
             {"START_CLASS","radian.tcmis.client.client.zwl.gui.ZWLCmisApp"},
             {"DEBUG","false"},
             {"CLIENT_INITIALS","ZWL"},
             {"MSDS_URL","/tcmIS/zwl/ViewMsds?"},
             {"WASTE_URL","/tcmIS/zwl/wasteprofile?"},
             {"PRINT_SCREEN_URL","/tcmIS/zwl/printscreen?"},
             {"WORK_AREA_NAME","Work Area"},
             {"WORK_AREA_NAME_PLURAL","Work Areas"}
     };

     public ZWLClientResourceBundle(){
            super();
            addHash(cHash);
     }

}