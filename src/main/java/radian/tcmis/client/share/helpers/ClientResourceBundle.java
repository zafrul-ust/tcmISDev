package radian.tcmis.client.share.helpers;

import radian.tcmis.both1.helpers.*;

public class ClientResourceBundle  extends BothResourceBundle {

      protected static final int LOCAL = 0;
      protected static final int WS0 = 1;
      protected static final int WS1 = 2;
      protected static final int CSS = 3;
      protected static final int TATOOINE = 4;
      protected static final int TEST = 5;
      protected static final int QA = 6;
      protected static final int GENTOO = 7;
      protected static final int WS2 = 8;

      //for muting sound
      protected static boolean soundMuted = false;

      // remove the comments from the appropriate line
      // to change which server your client is pointing
       protected static final int SPAM = LOCAL;
      //protected static final int SPAM = WS0;
      //protected static final int SPAM = WS1;
      //protected static final int SPAM = CSS;
      //protected static final int SPAM = TATOOINE;
      //protected static final int SPAM = TEST;
      //protected static final int SPAM = QA;
      //protected static final int SPAM = GENTOO;
      //protected static final int SPAM = WS2;

      protected static final String[] tempHostMachine =
        {"localhost",
         "ws0.tcmis.com",
         "ws1.tcmis.com",
         "www.tcmis.com",
         "tatooine.tcmis.com",
         "test.tcmis.com",
         "qa.tcmis.com",
         "gentoo.tcmis.com",
         "ws2.tcmis.com"};

      protected static final String[] tempServletPort =
        {"80","80","80","443","80","80","80","80","80"};

      protected static final String[] tempBaseDir =
        {"C:/Apache Group/Tomcat 5.5/webapps/tcmIS/WEB-INF/classes",
         "/home/tomcat5/webapps/tcmIS/WEB-INF/classes",
         "/home/tomcat5/webapps/tcmIS/WEB-INF/classes",
         "/home/tomcat5/webapps/tcmIS/WEB-INF/classes",
         "/home/tomcat5/webapps/tcmIS/WEB-INF/classes",
         "/home/tomcat5/webapps/tcmIS/WEB-INF/classes",
         "/home/tomcat5/apache-tomcat-5.5.28/webapps/tcmIS/WEB-INF/classes",
         "/home/tomcat5/webapps/tcmIS/WEB-INF/classes",
         "/home/tomcat5/webapps/tcmIS/WEB-INF/classes"};

      protected static final String[] tempBaseDirTcm =
        {"C:/Apache Group/Tomcat 5.5/webapps/tcmIS/WEB-INF/classes/radian",
         "/home/tomcat5/webapps/tcmIS/WEB-INF/classes/radian",
         "/home/tomcat5/webapps/tcmIS/WEB-INF/classes/radian",
         "/home/tomcat5/webapps/tcmIS/WEB-INF/classes/radian",
         "/home/tomcat5/webapps/tcmIS/WEB-INF/classes/radian",
         "/home/tomcat5/webapps/tcmIS/WEB-INF/classes/radian",
         "/home/tomcat5/apache-tomcat-5.5.28/webapps/tcmIS/WEB-INF/classes/radian",
         "/home/tomcat5/webapps/tcmIS/WEB-INF/classes/radian",
         "/home/tomcat5/webapps/tcmIS/WEB-INF/classes/radian"};

      static final String HOST_MACHINE = new String(tempHostMachine[SPAM]);

      static Object[][] cHash = {
             {"DEBUG","true"},
             {"BASE_DIR",tempBaseDir[SPAM]},
             {"BASE_DIR_TCM",tempBaseDirTcm[SPAM]},
             {"HOST_NAME",HOST_MACHINE},
             //{"HTTP_AND_HOST","http://"+HOST_MACHINE+PORT},
             //{"PROXY_NAME",HOST_MACHINE},
             {"WEBS_HOME","http://localhost"}, // localhost
             //{"WEBS_HOME","http://www.tcmis.com"},      //JRE 1.3
             //{"WEBS_HOME","https://www.tcmis.com"},   //JRE 1.4
             //{"WEBS_HOME","http://ws0.tcmis.com"}, // WS0
              // {"WEBS_HOME","http://ws1.tcmis.com"},
             //{"WEBS_HOME","http://ws2.tcmis.com"}, // ws2
             ////{"WEBS_HOME","http://tatooine.tcmis.com"}, // tatooine
             //{"WEBS_HOME","http://qa.tcmis.com"}, // test
             //{"WEBS_HOME","http://gentoo.tcmis.com"}, // gentoo
             {"SERVLET_PORT",tempServletPort[SPAM]},
             {"SERVLET_DIR","/tcmIS/servlet/"},
             {"PROXY_PORT","80"},
             {"LOCAL_IMAGES_DIR","images"},
             {"LOCAL_SOUND_DIR","sound"},
             {"LOCAL_REPORT_TEMPLATE_DIR","templates"},
             {"JDE_HOST","https://bluejay.tcmis.com"},
             {"JDE_DIR","/jde/jde_5250.html"},
             {"BROWSER4","\"C:\\Program Files\\Netscape\\Communicator\\Program\\netscape.exe\" "},
             {"BROWSER2","\"\\Netscape\\Communicator\\Program\\netscape.exe\" "},
             {"BROWSER3","\"\\Program Files\\Netscape\\Navigator\\Program\\netscape.exe\" "},
             {"BROWSER1","\"C:\\Program Files\\Internet Explorer\\iexplore.exe\" "},
             {"BROWSER5","\"\\Program Files\\Plus!\\Microsoft Internet\\iexplore.exe\" "},
             {"HTTP_PORT","80"},
             {"LOCAL_CLASSPATH_DIR","classes"},
             {"TEMP_EXT","tmp"},
             {"TRASH_EXT","trash"},
             {"HOLD_EXT","hold"},
             {"ORGANIZATION","www.tcmis.com"},
             {"EXEC_CLEAN_FILE","clean.bat"},
             {"EXEC_DUMMY_FILE","dummy.bat"},
             {"BAT_FILE","tcmIS.bat"},
             {"DONE_AU","https://www.tcmis.com/sounds/connect.au"},
             {"WORK_AREA_NAME","Work Area"},
             {"WORK_AREA_NAME_PLURAL","Work Areas"}
     };

     public ClientResourceBundle(){
             super();
             addHash(cHash);
     }

     public boolean getSoundMuted() {
       return soundMuted;
     }
     public void setSoundMuted(boolean m) {
       soundMuted = m;
     }

}




