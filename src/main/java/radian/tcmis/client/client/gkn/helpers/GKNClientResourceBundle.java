package radian.tcmis.client.client.gkn.helpers;

import radian.tcmis.client.share.helpers.ClientResourceBundle;

public class GKNClientResourceBundle  extends ClientResourceBundle {

     static final Object[][] cHash = {
             {"SERVLET_PACKAGE","radian.tcmis.server7.client.gkn.servlets."},
             {"APP_NAME","GKN AEROSPACE"},
             {"SHOW_LOGON_FRAME","No"},        //10-29-01 whether to show the logon frame or not
             {"USE_GLOBAL_PASSWORD","No"},
             {"GLOBAL_PASSWORD","t"},
             {"LOG_DIR","/temp/logs/gkn"},
             {"VERSION","1.8.05"},
             {"DOWNLOAD_DIR",""},
             {"EXEC_FILE","tcmISRun.exe"},
             {"CONFIG_FILE","tcmis.cfg"},
             {"DEBUG_FILE","cmisapp.deb"},
             {"START_CLASS","radian.tcmis.client.client.gkn.gui.GKNCmisApp"},
             {"DEBUG","false"},
             {"CLIENT_INITIALS","GKN"},
             {"MSDS_URL","/tcmIS/gkn/ViewMsds?"},
             {"WASTE_URL","/tcmIS/gkn/wasteprofile?"},
             {"PRINT_SCREEN_URL","/tcmIS/gkn/printscreen?"},
             {"WORK_AREA_NAME","Work Area"},
             {"WORK_AREA_NAME_PLURAL","Work Areas"}
     };

     public GKNClientResourceBundle(){
            super();
            addHash(cHash);
     }

}