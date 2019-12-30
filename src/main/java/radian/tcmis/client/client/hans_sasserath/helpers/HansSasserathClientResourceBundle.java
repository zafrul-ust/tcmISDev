package radian.tcmis.client.client.hans_sasserath.helpers;

import radian.tcmis.client.share.helpers.ClientResourceBundle;

public class HansSasserathClientResourceBundle  extends ClientResourceBundle {

     static final Object[][] cHash = {
             {"SERVLET_PACKAGE","radian.tcmis.server7.client.hans_sasserath.servlets."},
             {"APP_NAME","Hans Sasserath & Co KG"},
             {"SHOW_LOGON_FRAME","No"},        //10-29-01 whether to show the logon frame or not
             {"USE_GLOBAL_PASSWORD","No"},
             {"GLOBAL_PASSWORD","t"},
             {"LOG_DIR","/temp/logs/hans_sasserath"},
             {"VERSION","1.8.05"},
             {"DOWNLOAD_DIR",""},
             {"EXEC_FILE","tcmISRun.exe"},
             {"CONFIG_FILE","tcmis.cfg"},
             {"DEBUG_FILE","cmisapp.deb"},
             {"START_CLASS","radian.tcmis.client.client.hans_sasserath.gui.HansSasserathCmisApp"},
             {"DEBUG","false"},
             {"CLIENT_INITIALS","HansSasserath"},
             {"MSDS_URL","/tcmIS/hans_sasserath/ViewMsds?"},
             {"WASTE_URL","/tcmIS/hans_sasserath/wasteprofile?"},
             {"PRINT_SCREEN_URL","/tcmIS/hans_sasserath/printscreen?"},
             {"WORK_AREA_NAME","Work Area"},
             {"WORK_AREA_NAME_PLURAL","Work Areas"}
     };

     public HansSasserathClientResourceBundle(){
            super();
            addHash(cHash);
     }

}