package radian.tcmis.client.client.aerocz.helpers;

import radian.tcmis.client.share.helpers.ClientResourceBundle;

public class AeroczClientResourceBundle  extends ClientResourceBundle {

     static final Object[][] cHash = {
             {"SERVLET_PACKAGE","radian.tcmis.server7.client.aerocz.servlets."},
             {"APP_NAME","AERO Vodochody a.s."},
             {"SHOW_LOGON_FRAME","No"},        //10-29-01 whether to show the logon frame or not
             {"USE_GLOBAL_PASSWORD","No"},
             {"GLOBAL_PASSWORD","t"},
             {"LOG_DIR","/temp/logs/aerocz"},
             {"VERSION","1.8.05"},
             {"DOWNLOAD_DIR",""},
             {"EXEC_FILE","tcmISRun.exe"},
             {"CONFIG_FILE","tcmis.cfg"},
             {"DEBUG_FILE","cmisapp.deb"},
             {"START_CLASS","radian.tcmis.client.client.aerocz.gui.AeroczCmisApp"},
             {"DEBUG","false"},
             {"CLIENT_INITIALS","Aerocz"},
             {"MSDS_URL","/tcmIS/aerocz/ViewMsds?"},
             {"WASTE_URL","/tcmIS/aerocz/wasteprofile?"},
             {"PRINT_SCREEN_URL","/tcmIS/aerocz/printscreen?"},
             {"WORK_AREA_NAME","Work Area"},
             {"WORK_AREA_NAME_PLURAL","Work Areas"}
     };

     public AeroczClientResourceBundle(){
            super();
            addHash(cHash);
     }

}