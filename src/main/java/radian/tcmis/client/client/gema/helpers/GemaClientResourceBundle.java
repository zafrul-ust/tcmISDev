package radian.tcmis.client.client.gema.helpers;

import radian.tcmis.client.share.helpers.ClientResourceBundle;

public class GemaClientResourceBundle  extends ClientResourceBundle {

     static final Object[][] cHash = {
             {"SERVLET_PACKAGE","radian.tcmis.server7.client.gema.servlets."},
             {"APP_NAME","GEMA"},
             {"SHOW_LOGON_FRAME","No"},        //10-29-01 whether to show the logon frame or not
             {"USE_GLOBAL_PASSWORD","No"},
             {"GLOBAL_PASSWORD","t"},
             {"LOG_DIR","/temp/logs/gema"},
             {"VERSION","1.8.05"},
             {"DOWNLOAD_DIR",""},
             {"EXEC_FILE","tcmISRun.exe"},
             {"CONFIG_FILE","tcmis.cfg"},
             {"DEBUG_FILE","cmisapp.deb"},
             {"START_CLASS","radian.tcmis.client.client.gema.gui.GemaCmisApp"},
             {"DEBUG","false"},
             {"CLIENT_INITIALS","Gema"},
             {"MSDS_URL","/tcmIS/gema/ViewMsds?"},
             {"WASTE_URL","/tcmIS/gema/wasteprofile?"},
             {"PRINT_SCREEN_URL","/tcmIS/gema/printscreen?"},
             {"WORK_AREA_NAME","Work Area"},
             {"WORK_AREA_NAME_PLURAL","Work Areas"}
     };

     public GemaClientResourceBundle(){
            super();
            addHash(cHash);
     }

}