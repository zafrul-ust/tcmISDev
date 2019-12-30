package radian.tcmis.client.client.nalco.helpers;

import radian.tcmis.client.share.helpers.ClientResourceBundle;

public class NalcoClientResourceBundle  extends ClientResourceBundle {

     static final Object[][] cHash = {
             {"SERVLET_PACKAGE","radian.tcmis.server7.client.nalco.servlets."},
             {"APP_NAME","NALCO"},
             {"SHOW_LOGON_FRAME","No"},        //10-29-01 whether to show the logon frame or not
             {"USE_GLOBAL_PASSWORD","No"},
             {"GLOBAL_PASSWORD","t"},
             {"LOG_DIR","/temp/logs/nalco"},
             {"VERSION","1.8.05"},
             {"DOWNLOAD_DIR",""},
             {"EXEC_FILE","tcmISRun.exe"},
             {"CONFIG_FILE","tcmis.cfg"},
             {"DEBUG_FILE","cmisapp.deb"},
             {"START_CLASS","radian.tcmis.client.client.nalco.gui.NalcoCmisApp"},
             {"DEBUG","false"},
             {"CLIENT_INITIALS","Nalco"},
             {"MSDS_URL","/tcmIS/nalco/ViewMsds?"},
             {"WASTE_URL","/tcmIS/nalco/wasteprofile?"},
             {"PRINT_SCREEN_URL","/tcmIS/nalco/printscreen?"},
             {"WORK_AREA_NAME","Work Area"},
             {"WORK_AREA_NAME_PLURAL","Work Areas"}
     };

     public NalcoClientResourceBundle(){
            super();
            addHash(cHash);
     }

}