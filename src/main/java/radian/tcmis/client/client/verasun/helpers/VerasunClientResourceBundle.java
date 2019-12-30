package radian.tcmis.client.client.verasun.helpers;

import radian.tcmis.client.share.helpers.ClientResourceBundle;

public class VerasunClientResourceBundle  extends ClientResourceBundle {

     static final Object[][] cHash = {
             {"SERVLET_PACKAGE","radian.tcmis.server7.client.verasun.servlets."},
             {"APP_NAME","VeraSun Energy"},
             {"SHOW_LOGON_FRAME","No"},        //10-29-01 whether to show the logon frame or not
             {"USE_GLOBAL_PASSWORD","No"},
             {"GLOBAL_PASSWORD","t"},
             {"LOG_DIR","/temp/logs/verasun"},
             {"VERSION","1.8.05"},
             {"DOWNLOAD_DIR",""},
             {"EXEC_FILE","tcmISRun.exe"},
             {"CONFIG_FILE","tcmis.cfg"},
             {"DEBUG_FILE","cmisapp.deb"},
             {"START_CLASS","radian.tcmis.client.client.verasun.gui.VerasunCmisApp"},
             {"DEBUG","false"},
             {"CLIENT_INITIALS","Verasun"},
             {"MSDS_URL","/tcmIS/verasun/ViewMsds?"},
             {"WASTE_URL","/tcmIS/verasun/wasteprofile?"},
             {"PRINT_SCREEN_URL","/tcmIS/verasun/printscreen?"},
             {"WORK_AREA_NAME","Work Area"},
             {"WORK_AREA_NAME_PLURAL","Work Areas"}
     };

     public VerasunClientResourceBundle(){
            super();
            addHash(cHash);
     }

}