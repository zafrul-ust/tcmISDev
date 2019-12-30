package radian.tcmis.client.client.kanfit.helpers;

import radian.tcmis.client.share.helpers.ClientResourceBundle;

public class KanfitClientResourceBundle  extends ClientResourceBundle {

     static final Object[][] cHash = {
             {"SERVLET_PACKAGE","radian.tcmis.server7.client.kanfit.servlets."},
             {"APP_NAME","Kanfit LTD"},
             {"SHOW_LOGON_FRAME","No"},        //10-29-01 whether to show the logon frame or not
             {"USE_GLOBAL_PASSWORD","No"},
             {"GLOBAL_PASSWORD","t"},
             {"LOG_DIR","/temp/logs/kanfit"},
             {"VERSION","1.8.05"},
             {"DOWNLOAD_DIR",""},
             {"EXEC_FILE","tcmISRun.exe"},
             {"CONFIG_FILE","tcmis.cfg"},
             {"DEBUG_FILE","cmisapp.deb"},
             {"START_CLASS","radian.tcmis.client.client.kanfit.gui.KanfitCmisApp"},
             {"DEBUG","false"},
             {"CLIENT_INITIALS","Kanfit"},
             {"MSDS_URL","/tcmIS/kanfit/ViewMsds?"},
             {"WASTE_URL","/tcmIS/kanfit/wasteprofile?"},
             {"PRINT_SCREEN_URL","/tcmIS/kanfit/printscreen?"},
             {"WORK_AREA_NAME","Work Area"},
             {"WORK_AREA_NAME_PLURAL","Work Areas"}
     };

     public KanfitClientResourceBundle(){
            super();
            addHash(cHash);
     }

}