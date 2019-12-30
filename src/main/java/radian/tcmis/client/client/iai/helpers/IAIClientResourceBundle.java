package radian.tcmis.client.client.iai.helpers;

import radian.tcmis.client.share.helpers.ClientResourceBundle;

public class IAIClientResourceBundle  extends ClientResourceBundle {

     static final Object[][] cHash = {
             {"SERVLET_PACKAGE","radian.tcmis.server7.client.iai.servlets."},
             {"APP_NAME","Israel Aircraft Industries LTD"},
             {"SHOW_LOGON_FRAME","No"},        //10-29-01 whether to show the logon frame or not
             {"USE_GLOBAL_PASSWORD","No"},
             {"GLOBAL_PASSWORD","t"},
             {"LOG_DIR","/temp/logs/iai"},
             {"VERSION","1.8.05"},
             {"DOWNLOAD_DIR",""},
             {"EXEC_FILE","tcmISRun.exe"},
             {"CONFIG_FILE","tcmis.cfg"},
             {"DEBUG_FILE","cmisapp.deb"},
             {"START_CLASS","radian.tcmis.client.client.iai.gui.IAICmisApp"},
             {"DEBUG","false"},
             {"CLIENT_INITIALS","IAI"},
             {"MSDS_URL","/tcmIS/iai/ViewMsds?"},
             {"WASTE_URL","/tcmIS/iai/wasteprofile?"},
             {"PRINT_SCREEN_URL","/tcmIS/iai/printscreen?"},
             {"WORK_AREA_NAME","Work Area"},
             {"WORK_AREA_NAME_PLURAL","Work Areas"}
     };

     public IAIClientResourceBundle(){
            super();
            addHash(cHash);
     }

}