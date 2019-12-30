package radian.tcmis.client.client.pge.helpers;

import radian.tcmis.client.share.helpers.ClientResourceBundle;

public class PGEClientResourceBundle  extends ClientResourceBundle {

     static final Object[][] cHash = {
             {"SERVLET_PACKAGE","radian.tcmis.server7.client.pge.servlets."},
             {"APP_NAME","PG&E Corporation"},
             {"SHOW_LOGON_FRAME","No"},        //10-29-01 whether to show the logon frame or not
             {"USE_GLOBAL_PASSWORD","No"},
             {"GLOBAL_PASSWORD","t"},
             {"LOG_DIR","/temp/logs/pge"},
             {"VERSION","1.8.05"},
             {"DOWNLOAD_DIR",""},
             {"EXEC_FILE","tcmISRun.exe"},
             {"CONFIG_FILE","tcmis.cfg"},
             {"DEBUG_FILE","cmisapp.deb"},
             {"START_CLASS","radian.tcmis.client.client.pge.gui.PGECmisApp"},
             {"DEBUG","false"},
             {"CLIENT_INITIALS","PGE"},
             {"MSDS_URL","/tcmIS/pge/ViewMsds?"},
             {"WASTE_URL","/tcmIS/pge/wasteprofile?"},
             {"PRINT_SCREEN_URL","/tcmIS/pge/printscreen?"},
             {"WORK_AREA_NAME","Work Area"},
             {"WORK_AREA_NAME_PLURAL","Work Areas"}
     };

     public PGEClientResourceBundle(){
            super();
            addHash(cHash);
     }

}