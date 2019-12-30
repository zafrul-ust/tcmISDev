package radian.tcmis.client.client.l3.helpers;

import radian.tcmis.client.share.helpers.ClientResourceBundle;

public class L3ClientResourceBundle  extends ClientResourceBundle {

     static final Object[][] cHash = {
             {"SERVLET_PACKAGE","radian.tcmis.server7.client.l3.servlets."},
             {"APP_NAME","L-3 Infrared Products"},
             {"SHOW_LOGON_FRAME","No"},        //10-29-01 whether to show the logon frame or not
             {"USE_GLOBAL_PASSWORD","No"},
             {"GLOBAL_PASSWORD","t"},
             {"LOG_DIR","/temp/logs/l3"},
             {"VERSION","1.8.05"},
             {"DOWNLOAD_DIR",""},
             {"EXEC_FILE","tcmISRun.exe"},
             {"CONFIG_FILE","tcmis.cfg"},
             {"DEBUG_FILE","cmisapp.deb"},
             {"START_CLASS","radian.tcmis.client.client.l3.gui.L3CmisApp"},
             {"DEBUG","false"},
             {"CLIENT_INITIALS","L3"},
             {"MSDS_URL","/tcmIS/l3/ViewMsds?"},
             {"WASTE_URL","/tcmIS/l3/wasteprofile?"},
             {"PRINT_SCREEN_URL","/tcmIS/l3/printscreen?"},
             {"WORK_AREA_NAME","Work Area"},
             {"WORK_AREA_NAME_PLURAL","Work Areas"}
     };

     public L3ClientResourceBundle(){
            super();
            addHash(cHash);
     }

}