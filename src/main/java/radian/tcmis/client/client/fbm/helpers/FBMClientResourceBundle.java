package radian.tcmis.client.client.fbm.helpers;

import radian.tcmis.client.share.helpers.ClientResourceBundle;

public class FBMClientResourceBundle  extends ClientResourceBundle {

     static final Object[][] cHash = {
             {"SERVLET_PACKAGE","radian.tcmis.server7.client.fbm.servlets."},
             {"APP_NAME","FBM Composite Materials Ltd."},
             {"SHOW_LOGON_FRAME","No"},        //10-29-01 whether to show the logon frame or not
             {"USE_GLOBAL_PASSWORD","No"},
             {"GLOBAL_PASSWORD","t"},
             {"LOG_DIR","/temp/logs/fbm"},
             {"VERSION","1.8.05"},
             {"DOWNLOAD_DIR",""},
             {"EXEC_FILE","tcmISRun.exe"},
             {"CONFIG_FILE","tcmis.cfg"},
             {"DEBUG_FILE","cmisapp.deb"},
             {"START_CLASS","radian.tcmis.client.client.fbm.gui.FBMCmisApp"},
             {"DEBUG","false"},
             {"CLIENT_INITIALS","FBM"},
             {"MSDS_URL","/tcmIS/fbm/ViewMsds?"},
             {"WASTE_URL","/tcmIS/fbm/wasteprofile?"},
             {"PRINT_SCREEN_URL","/tcmIS/fbm/printscreen?"},
             {"WORK_AREA_NAME","Work Area"},
             {"WORK_AREA_NAME_PLURAL","Work Areas"}
     };

     public FBMClientResourceBundle(){
            super();
            addHash(cHash);
     }

}