//Title:
//Version:
//Copyright:
//Author:
//Company:
//Description:


package radian.tcmis.client.share.helpers;

import java.util.*;
import radian.tcmis.client.share.gui.*;
import radian.tcmis.client.share.httpCgi.*;

import java.awt.*;
import javax.swing.*;

import radian.tcmis.both1.helpers.resource.ResourceLoader;

public class UpdateVersion {
    int bytesMoving = 0;
    long objectSize = 0;
    String objectMoving = new String(); // give message to uploading window
    String objectVersion = new String();
    int numObjects = 0;
    UpdateVersion_DownloadWinThread lt = new UpdateVersion_DownloadWinThread(this);
    CmisApp parent = null;

    int numPack=0;

    Vector tmpFiles = new Vector();

     public UpdateVersion(CmisApp p){
         parent = p;
    }


    public int updateVersionRemote(String nowVersion,CmisApp parent){
    int flag = 0;
/*
       boolean mandatory = true;

       TcmisHttpConnection cgi = new TcmisHttpConnection(TcmisHttpConnection.CHECK_VERSION,parent);
       Hashtable query = new Hashtable();
       query.put("VERSION",parent.getResourceBundle().getString("VERSION")); //String, String
       query.put("ACTION","OLD"); //String, String
       Hashtable result = cgi.getResultHash(query);
       Vector versions = (Vector) result.get("VERSIONS"); // comes version, restart flag
       nowVersion = (String)((Vector) result.get("LAST_VERSION")).elementAt(0);
       if (((String)((Vector) result.get("RESTART")).elementAt(0)).equals("M")) mandatory=true;
       String no = new String("Alert!");

       UpdateDlg dlg = new UpdateDlg(parent,"Alert!",nowVersion,mandatory);
       dlg.showMe();
       if (mandatory && !dlg.update) System.exit(0); // cancel

       if (dlg.update){
          dlg = null;
          Vector classes = (Vector) result.get("OBJECTS");
       System.out.println("------------ what is this: " + classes);
          Vector clAction = (Vector) result.get("OBJECTS_ACTION");
          //parent.lDlg.setVisible(false);
          numObjects = classes.size();
          System.out.println("Objects Num : "+ numObjects);

          lt.start();

          numPack=0;
          for (int j=0;j<classes.size();j++){ //classes and versions should be the same size
            try {
              System.out.println("Vectors : "+ classes + versions + clAction );
              // ignore if classes is for removall only
              if (clAction.elementAt(j).toString().equalsIgnoreCase("A") ||
                  clAction.elementAt(j).toString().equalsIgnoreCase("D") ){
                System.out.println("ObjectMoving : "+ objectMoving);
                objectMoving = (String)classes.elementAt(j);
                objectVersion = (String) versions.elementAt(j);
                numPack=j;

                String objectAction =  clAction.elementAt(j).toString();
                flag = this.updatePackage(objectMoving,objectVersion,objectAction);
                if (flag != 1)  break;
              }
            } catch (Exception e) {
              flag=0;
              e.printStackTrace();
              break;
            }
          }

          // Got here with 1 got OK, build file
          String wDir = new String(System.getProperty("user.dir"));
          String file = wDir +  System.getProperty("file.separator") + "update.txt";

          try {
            File test = new File(file);
            test.delete();
            BufferedWriter bw = new BufferedWriter(new FileWriter(file));
            if (bw != null){
              for (int j=0;j<classes.size();j++){
                String line = classes.elementAt(j).toString()+" "+clAction.elementAt(j).toString();
                bw.write(line,0,line.length());
                bw.newLine();
              }
            }
            bw.close();
          } catch (Exception e1) {
            e1.printStackTrace();
          }

          lt.closeWin();
          lt.killT();

          return flag;
       }
 */
       return flag;
   }

  private int updatePackage(String pacName,String nowVersion,String oAction) throws Exception
  {
      /* DataInputStream in = null;
       FileOutputStream out = null;
       String wDir = new String(System.getProperty("user.dir"));
       wDir += System.getProperty("file.separator");
       String dDir =parent.getResourceBundle().getString("DOWNLOAD_DIR")+ "/" + nowVersion + "/";
       boolean cont = false;

       System.out.println("WDir:"+wDir);

       String newFileName = new String(wDir+pacName);

       System.out.println("File:"+newFileName);

       File test = new File(newFileName);

       if (oAction.equalsIgnoreCase("D") && test.exists()){
           return 1;
       } else if (oAction.equalsIgnoreCase("D")) {  // make directory
           if (test.mkdirs()) return 1; // try 3 time....
           if (test.mkdirs()) return 1;
           if (test.mkdirs()) return 1;
       }

       if (test.exists()) {
          if (!test.delete()) {
             return 0;
          }
       }

       long bSize = 0;
       if (pacName != null){
         URL url = new URL(dDir+pacName);
         System.out.println("Retriving : "+ dDir+pacName);
         TcmisHttpConnection cgi = new TcmisHttpConnection(0,parent);

         lt.setConnection(cgi);

         if (!cgi.buildConnection(url)) {
            System.out.println("** Error on building connection ***");
            return 0;
         }


         ByteArrayInputStream inS = new ByteArrayInputStream(cgi.getServerData());
         //set package to move size
         bSize = Integer.parseInt(cgi.getHttpsURLConnection().getHeaderField("Content-Length"));
         this.objectSize = bSize;
         System.out.println("file size:"+bSize);
         in = new DataInputStream(inS);
         out = new FileOutputStream(newFileName);

       }

       if (in != null && out != null){
          int pacSize = 50;
          byte buffer[] = new byte[pacSize];
          int n;
          this.bytesMoving = 0;
          while ((n = in.read(buffer)) > -1){
            out.write(buffer,0,n);
            this.bytesMoving += n;
          }
          out.close();
          in.close();
          test =  new File(newFileName);
          System.out.println("file size downloaded:"+test.length());
          if (test.length() == bSize){
             return 1;
          } else {
             test.delete();
             return 0;
          }
       }
     */
       return 0;

    }


   public String getObjectMoving(){
      return this.objectMoving;
   }

   public int getObjectMovingInt(){
      return this.numPack;
   }

   public String getObjectVersion(){
      return this.objectVersion;
   }

   public long getObjectSize(){
      return this.objectSize;
   }

   public int getBytesMoving(){
      return this.bytesMoving;
   }

   public int getNumObjects(){
      return this.numObjects;
   }

}

class UpdateVersion_DownloadWinThread extends StatusDlg
{
       TcmisHttpConnection con = null;
       int max= 0;
       JFrame jf = new JFrame("Download");
       boolean kill = false;

       static UpdateVersion parent = null;
       LoadingPanel lPanel = new LoadingPanel();

       public UpdateVersion_DownloadWinThread(UpdateVersion parent)
       {
         super("UpdateThread");
         this.parent = parent;
         jf.getContentPane().setLayout(new BorderLayout());
         jf.getContentPane().add(lPanel,BorderLayout.CENTER);
         jf.pack();

         /*String wDir = new String(System.getProperty("user.dir"));
         String imagesDir = wDir + System.getProperty("file.separator") + (new ClientResourceBundle()).getString("LOCAL_IMAGES_DIR") ;
         */
         jf.setIconImage(ResourceLoader.getImageIcon("images/gif/tcmicon.gif").getImage());
         CenterComponent.centerCompOnScreen(jf);

       }

       public void run()
       {
         // fake way just to move the status bar
         /*
         int i=0;
         boolean done=true;
         lPanel.setMessage("","","Connecting server...");
         lPanel.updateProcessBar(0,"0 %","");
         jf.setVisible(true);
         CursorChange.setCursor(jf,Cursor.WAIT_CURSOR);
         while (true){
           if (kill) break;
           try {
            Thread.sleep(500);
           } catch (Exception e){
            e.printStackTrace();
           }
         }
         */
       }

       void setConnection(TcmisHttpConnection c){
            this.con = c;
            synchronized (c){
              c.registerObject(this);
            }
       }

       public void closeWin(){
         jf.setVisible(false);
       }

       public void killT(){
         kill =true;
       }

       public void setCount(int b)
       {
            /*lPanel.setBarMax(max);
            lPanel.setBarMin(0);
            int n = parent.getObjectMovingInt();
            String ver = "Version: "+parent.getObjectVersion();
            String pac = "Package: "+n+" of "+ parent.getNumObjects() ;
            int size = b;
            int cent = (100 * size) / max;
            String centS = cent+"%";
            lPanel.setMessage(ver,pac,"Downloading... "+size/1000+" of "+max/1000+" Kbytes");
            lPanel.updateProcessBar(b,centS,parent.getObjectMoving().trim());
            */
       }

       public void setLen(int m){
         max=m;
       }

}
























