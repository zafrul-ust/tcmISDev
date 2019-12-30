package radian.tcmis.client.client.fedco.helpers;

import radian.tcmis.client.share.helpers.ClientResourceBundle;

public class FedcoClientResourceBundle  extends ClientResourceBundle {

     static final Object[][] cHash = {
             {"SERVLET_PACKAGE","radian.tcmis.server7.client.fedco.servlets."},
             {"APP_NAME","Fedco Metals Industries LTD"},
             {"SHOW_LOGON_FRAME","No"},        //10-29-01 whether to show the logon frame or not
             {"USE_GLOBAL_PASSWORD","No"},
             {"GLOBAL_PASSWORD","t"},
             {"LOG_DIR","/temp/logs/fedco"},
             {"VERSION","1.8.05"},
             {"DOWNLOAD_DIR",""},
             {"EXEC_FILE","tcmISRun.exe"},
             {"CONFIG_FILE","tcmis.cfg"},
             {"DEBUG_FILE","cmisapp.deb"},
             {"START_CLASS","radian.tcmis.client.client.fedco.gui.FedcoCmisApp"},
             {"DEBUG","false"},
             {"CLIENT_INITIALS","Fedco"},
             {"MSDS_URL","/tcmIS/fedco/ViewMsds?"},
             {"WASTE_URL","/tcmIS/fedco/wasteprofile?"},
             {"PRINT_SCREEN_URL","/tcmIS/fedco/printscreen?"},
             {"WORK_AREA_NAME","Work Area"},
             {"WORK_AREA_NAME_PLURAL","Work Areas"}
     };

     public FedcoClientResourceBundle(){
            super();
            addHash(cHash);
     }

}