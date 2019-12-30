package radian.tcmis.server7.share.helpers;

import radian.tcmis.both1.helpers.*;

public class ServerResourceBundle  extends BothResourceBundle {

     static Object[][] cHash = {
       {"DEBUG","false"},
       {"DB_GLOBAL","Global"},
       // For owl
       //{"BASE_DIR","/screech/urubu/java"},
       //{"BASE_DIR_TCM","/screech/urubu/java/radian"}
       // For condor
       {"BASE_DIR_TCM","/home/servlet/radian/logs/share"}
       // For Localhost
       //{"BASE_DIR","d:/Projects/java"},
       //{"BASE_DIR_TCM","d:/Projects/java/radian"}
       // for hawk
       //{"BASE_DIR","/red-tail/java/tcmIS/servlets"},
       //{"BASE_DIR_TCM","/red-tail/java/tcmIS/servlets/radian"}

     };

     public ServerResourceBundle(){
            super();
            addHash(cHash);
     }

}
