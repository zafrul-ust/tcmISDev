package radian.tcmis.client.client.lmco.helpers;

import radian.tcmis.client.share.helpers.ClientResourceBundle;

public class LMCOClientResourceBundle  extends ClientResourceBundle {

     static final Object[][] cHash = {
             {"SERVLET_PACKAGE","radian.tcmis.server7.client.lmco.servlets."},
             {"APP_NAME","LOCKHEED"},
             {"LOG_DIR","/temp/logs/lmco"},
             {"USE_GLOBAL_PASSWORD","No"},
             {"GLOBAL_PASSWORD","t"},
             {"LOG_DIR","/temp/logs/ray"},
             {"VERSION","1.8.01"},
             {"DOWNLOAD_DIR",""},     //trong 7-28
             {"EXEC_FILE","tcmISRun.exe"},
             {"CONFIG_FILE","tcmis.cfg"},
             {"DEBUG_FILE","LMCOcmisapp.deb"},
             {"START_CLASS","radian.tcmis.client.client.lmco.gui.LMCOCmisApp"},
             {"DEBUG","false"},
             {"CLIENT_INITIALS","LMCO"},
             {"MSDS_URL","/tcmIS/lmco/ViewMsds?"},
             {"WASTE_URL","/tcmIS/lmco/wasteprofile?"},
             {"PRINT_SCREEN_URL","/tcmIS/lmco/printscreen?"},
             {"WORK_AREA_NAME","Work Area"},
             {"WORK_AREA_NAME_PLURAL","Work Areas"},
             {"PROCESS_DETAIL_URL","/tcmIS/lmco/processdetail?"}
       };

     public LMCOClientResourceBundle(){
            super();
            addHash(cHash);
     }

}