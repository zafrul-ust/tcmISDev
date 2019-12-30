package radian.tcmis.client.client.qos.helpers;

import radian.tcmis.client.share.helpers.ClientResourceBundle;

public class QOSClientResourceBundle  extends ClientResourceBundle {

     static final Object[][] cHash = {
             {"SERVLET_PACKAGE","radian.tcmis.server7.client.qos.servlets."},
             {"APP_NAME","Quality on Site"},
             {"SHOW_LOGON_FRAME","No"},        //10-29-01 whether to show the logon frame or not
             {"USE_GLOBAL_PASSWORD","No"},
             {"GLOBAL_PASSWORD","t"},
             {"LOG_DIR","/temp/logs/qos"},
             {"VERSION","1.8.05"},
             {"DOWNLOAD_DIR",""},
             {"EXEC_FILE","tcmISRun.exe"},
             {"CONFIG_FILE","tcmis.cfg"},
             {"DEBUG_FILE","cmisapp.deb"},
             {"START_CLASS","radian.tcmis.client.client.qos.gui.QOSCmisApp"},
             {"DEBUG","false"},
             {"CLIENT_INITIALS","QOS"},
             {"MSDS_URL","/tcmIS/qos/ViewMsds?"},
             {"WASTE_URL","/tcmIS/qos/wasteprofile?"},
             {"PRINT_SCREEN_URL","/tcmIS/qos/printscreen?"},
             {"WORK_AREA_NAME","Work Area"},
             {"WORK_AREA_NAME_PLURAL","Work Areas"}
     };

     public QOSClientResourceBundle(){
            super();
            addHash(cHash);
     }

}