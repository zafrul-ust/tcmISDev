

package radian.tcmis.client.share.helpers ;

/*import com.borland.jbcl.layout.XYLayout;
import com.borland.jbcl.layout.XYConstraints;
import com.borland.jbcl.layout.GridBagConstraints2;*/
import java.io.*;
import java.util.*;
import radian.tcmis.client.share.gui.* ;


public class CleanUpFiles {

  String proxy = null;
  String port = null;
  String browser = null;
  boolean isUsed = false;
  boolean proxyChanged = false;
  boolean browserChanged = false;
  boolean portChanged = false;
  CmisApp parent = null;
  boolean cHold = false;

  //Construct
  public CleanUpFiles(CmisApp p) {
      parent = p;
  }

  public CleanUpFiles() {

  }


  public void fixFileNames(){
  /*
    String wDir = new String(System.getProperty("user.dir"));
    ///wDir = "D:/Projects/java/radian.tcmis.ray.client.client.gui";
    // // System.out.println("Dir:"+wDir);
    File root = new File(wDir);
    Vector files = this.loadFiles(new Vector(), root);
    for (int i=0;i<files.size();i++){
       // // System.out.println("File:"+files.elementAt(i).toString());
       doAction(new File(files.elementAt(i).toString()));
    }
  */
  }

  public  Vector loadFiles(Vector result, File root)
  {
  /*
    if (!root.isDirectory()) return result;

    String [] files = root.list();
    for (int i=0;i<files.length;i++){
        File file = new File(root,files[i]);
        if (file.isFile()){
          result.addElement(file.getAbsolutePath());
        } else {
          CleanUpFiles cl = new CleanUpFiles();
          Vector newResult = cl.loadFiles(result,file);
          result = newResult;
        }
    }
  */
    return result;
  }

  protected void doAction(File target)
  {
  /*
    if (target.isDirectory()) return;
    if (target.getName().indexOf("."+(new ClientResourceBundle()).getString("TEMP_EXT")) > 0 ||
        target.getName().indexOf("."+(new ClientResourceBundle()).getString("TRASH_EXT")) > 0){
        cHold = true;
    }
*/
  }

    /* this was for replacing deal, now we check for tmp and trash only...
    if (target.getName().indexOf("."+(new ClientResourceBundle()).getString("TRASH_EXT")) > 0){
        target.delete();
    } else if (target.getName().indexOf("."+(new ClientResourceBundle()).getString("HOLD_EXT")) > 0) {
        int loc = target.getAbsolutePath().indexOf("."+(new ClientResourceBundle()).getString("HOLD_EXT"));
        File newF = new File(target.getAbsolutePath().substring(0,loc));
        // // System.out.println("Inside hold");
        try {
          Thread.sleep(3000);
        } catch (Exception e){ e.printStackTrace();}
        if (newF.exists()) {
           if (newF.delete()){
              // // System.out.println("Could delete");
           } else {
              // // System.out.println("Could not delete");
           }
        }
        if (target.renameTo(newF)) {
           cHold = true;
           // // System.out.println("Could rename");
        } else {
          // // System.out.println("Could not rename");
        }

    }
  }

  */


  public  boolean changedJRE(){
    return cHold;
  }

}





























