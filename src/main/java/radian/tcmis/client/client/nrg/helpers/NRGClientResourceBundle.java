package radian.tcmis.client.client.nrg.helpers;

import radian.tcmis.client.share.helpers.ClientResourceBundle;

public class NRGClientResourceBundle  extends ClientResourceBundle {

     static final Object[][] cHash = {
             {"SERVLET_PACKAGE","radian.tcmis.server7.client.nrg.servlets."},
             {"APP_NAME","NRG Energy Inc."},
             {"SHOW_LOGON_FRAME","No"},        //10-29-01 whether to show the logon frame or not
             {"USE_GLOBAL_PASSWORD","No"},
             {"GLOBAL_PASSWORD","t"},
             {"LOG_DIR","/temp/logs/nrg"},
             {"VERSION","1.8.05"},
             {"DOWNLOAD_DIR",""},
             {"EXEC_FILE","tcmISRun.exe"},
             {"CONFIG_FILE","tcmis.cfg"},
             {"DEBUG_FILE","cmisapp.deb"},
             {"START_CLASS","radian.tcmis.client.client.nrg.gui.NRGCmisApp"},
             {"DEBUG","false"},
             {"CLIENT_INITIALS","NRG"},
             {"MSDS_URL","/tcmIS/nrg/ViewMsds?"},
             {"WASTE_URL","/tcmIS/nrg/wasteprofile?"},
             {"PRINT_SCREEN_URL","/tcmIS/nrg/printscreen?"},
             {"WORK_AREA_NAME","Work Area"},
             {"WORK_AREA_NAME_PLURAL","Work Areas"}
     };

     public NRGClientResourceBundle(){
            super();
            addHash(cHash);
     }

}