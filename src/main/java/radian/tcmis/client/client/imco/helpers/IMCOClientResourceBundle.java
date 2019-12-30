package radian.tcmis.client.client.imco.helpers;

import radian.tcmis.client.share.helpers.ClientResourceBundle;

public class IMCOClientResourceBundle  extends ClientResourceBundle {

     static final Object[][] cHash = {
             {"SERVLET_PACKAGE","radian.tcmis.server7.client.imco.servlets."},
             {"APP_NAME","IMCO INDUSTRIES LTD"},
             {"SHOW_LOGON_FRAME","No"},        //10-29-01 whether to show the logon frame or not
             {"USE_GLOBAL_PASSWORD","No"},
             {"GLOBAL_PASSWORD","t"},
             {"LOG_DIR","/temp/logs/imco"},
             {"VERSION","1.8.05"},
             {"DOWNLOAD_DIR",""},
             {"EXEC_FILE","tcmISRun.exe"},
             {"CONFIG_FILE","tcmis.cfg"},
             {"DEBUG_FILE","cmisapp.deb"},
             {"START_CLASS","radian.tcmis.client.client.imco.gui.IMCOCmisApp"},
             {"DEBUG","false"},
             {"CLIENT_INITIALS","IMCO"},
             {"MSDS_URL","/tcmIS/imco/ViewMsds?"},
             {"WASTE_URL","/tcmIS/imco/wasteprofile?"},
             {"PRINT_SCREEN_URL","/tcmIS/imco/printscreen?"},
             {"WORK_AREA_NAME","Work Area"},
             {"WORK_AREA_NAME_PLURAL","Work Areas"}
     };

     public IMCOClientResourceBundle(){
            super();
            addHash(cHash);
     }

}