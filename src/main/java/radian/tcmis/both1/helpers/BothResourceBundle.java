package radian.tcmis.both1.helpers;

import java.util.Hashtable;

public class BothResourceBundle extends Hashtable {

        static Object[][] data = {
              {"TOKEN_DEL",BothHelpObjs.TOKEN_DEL},
              {"NAME_VALUE_DEL",BothHelpObjs.NAME_VALUE_DEL},
              {"VALUE_VALUE_DEL",BothHelpObjs.VALUE_VALUE_DEL},
              {"NAME_NAME_DEL",BothHelpObjs.NAME_NAME_DEL},
              {"BYTES_DEL",BothHelpObjs.BYTES_DEL},
              {"SUPER_USER",BothHelpObjs.SUPER_USER},
              {"ADMIN_USER",BothHelpObjs.ADMIN_USER},
              {"CONN_USER",BothHelpObjs.CONN_USER},
              {"NEW_LINE_CHAR",BothHelpObjs.NEW_LINE_CHAR}
        };
        /*static Object[][] data = {
              {"TOKEN_DEL","/"},
              {"NAME_VALUE_DEL","~"},
              {"VALUE_VALUE_DEL","|"},
              {"NAME_NAME_DEL","`"},
              {"BYTES_DEL","^"},
              {"SUPER_USER","SuperUser"},
              {"ADMIN_USER","Administrator"},
              {"CONN_USER","Connection"},
              {"NEW_LINE_CHAR","`~^"}
        };*/

        public BothResourceBundle() {
             super();
             addHash(data);
        }

        public BothResourceBundle(Object[][] newHash) {
             this();
             addHash(newHash);
        }

        public String getString(String name){
             if (!containsKey(name)) {
               //System.out.println("trying: "+name+" but not found.\n"+this.toString());
               return null;
             }
             return get(name).toString();
        }

        public void addHash(Object[][] newHash) {
             for (int i=0;i<newHash.length;i++){
                String ktemp = (newHash[i][0]).toString();
                String temp = (newHash[i][1]).toString();;
                //if (hash.containsKey(ktemp)){
                //  hash.remove(ktemp);
                //}
                put(ktemp,temp);
             }
        }

}