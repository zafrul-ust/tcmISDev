package radian.tcmis.client.client.sgd.helpers;

import radian.tcmis.client.share.helpers.ClientResourceBundle;

public class SGDClientResourceBundle  extends ClientResourceBundle {

     static final Object[][] cHash = {
             {"SERVLET_PACKAGE","radian.tcmis.server7.client.sgd.servlets."},
             {"APP_NAME","S.G.D Engineering Ltd."},
             {"SHOW_LOGON_FRAME","No"},        //10-29-01 whether to show the logon frame or not
             {"USE_GLOBAL_PASSWORD","No"},
             {"GLOBAL_PASSWORD","t"},
             {"LOG_DIR","/temp/logs/sgd"},
             {"VERSION","1.8.05"},
             {"DOWNLOAD_DIR",""},
             {"EXEC_FILE","tcmISRun.exe"},
             {"CONFIG_FILE","tcmis.cfg"},
             {"DEBUG_FILE","cmisapp.deb"},
             {"START_CLASS","radian.tcmis.client.client.sgd.gui.SGDCmisApp"},
             {"DEBUG","false"},
             {"CLIENT_INITIALS","SGD"},
             {"MSDS_URL","/tcmIS/sgd/ViewMsds?"},
             {"WASTE_URL","/tcmIS/sgd/wasteprofile?"},
             {"PRINT_SCREEN_URL","/tcmIS/sgd/printscreen?"},
             {"WORK_AREA_NAME","Work Area"},
             {"WORK_AREA_NAME_PLURAL","Work Areas"}
     };

     public SGDClientResourceBundle(){
            super();
            addHash(cHash);
     }

}