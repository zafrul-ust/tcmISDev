package radian.tcmis.both1.helpers;

import java.util.*;

public class InitHashtable extends Hashtable {

    public InitHashtable(Object[][] data){
        super();
        for (int i=0;i<data.length;i++){
             this.put(data[i][0],data[i][1]);
        }
    }
}


