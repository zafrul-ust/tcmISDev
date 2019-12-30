package radian.tcmis.client.client.bae.helpers;

import radian.tcmis.client.share.helpers.ClientResourceBundle;

public class BAEClientResourceBundle  extends ClientResourceBundle {

     static final Object[][] cHash = {
             {"SERVLET_PACKAGE","radian.tcmis.server7.client.bae.servlets."},
             {"APP_NAME","BAE"},
             {"SHOW_LOGON_FRAME","No"},        //10-29-01 whether to show the logon frame or not
             {"USE_GLOBAL_PASSWORD","No"},
             {"GLOBAL_PASSWORD","t"},
             {"LOG_DIR","/temp/logs/bae"},
             {"VERSION","1.8.05"},
             {"DOWNLOAD_DIR",""},
             {"EXEC_FILE","tcmISRun.exe"},
             {"CONFIG_FILE","tcmis.cfg"},
             {"DEBUG_FILE","cmisapp.deb"},
             {"START_CLASS","radian.tcmis.client.client.bae.gui.BAECmisApp"},
             {"DEBUG","false"},
             {"CLIENT_INITIALS","BAE"},
             {"MSDS_URL","/tcmIS/bae/ViewMsds?"},
             {"WASTE_URL","/tcmIS/bae/wasteprofile?"},
             {"PRINT_SCREEN_URL","/tcmIS/bae/printscreen?"},
             {"WORK_AREA_NAME","Work Area"},
             {"WORK_AREA_NAME_PLURAL","Work Areas"}
     };

     public BAEClientResourceBundle(){
            super();
            addHash(cHash);
     }

}