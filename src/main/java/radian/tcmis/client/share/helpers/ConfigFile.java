
package radian.tcmis.client.share.helpers ;

import radian.tcmis.client.share.gui.* ;
import radian.tcmis.both1.helpers.* ;


public class ConfigFile {

  String proxy = null;
  String port = null;
  String browser = null;
  String pBasic = null;
  String wBasic = null;
  String debug = null;
  String wwwport = null;
  boolean isUsed = false;
  CmisApp parent = null;

  //Construct
  public ConfigFile(CmisApp p) {
      parent = p;
      readConfigFile();
  }


  public String getProxy(){
     return proxy==null?"":proxy;
  }

  public void setProxy(String p){
     proxy = p;
  }

  public String getProxyPort(){
    return port==null?"":port;
  }

  public void setProxyPort(String p){
     port = p;
  }

  public String getBrowser(){
    return browser==null?"":browser;

  }

  public void setBrowser(String b){
     browser = b;
  }

  public boolean isProxyUsed()
  {
     if (proxy == null){
     return false;
  }

     return proxy.length() > 0;
  }

  public void setProxyUsed(boolean flag){
     if (!flag)  proxy = null;
  }

  protected void readConfigFile(){
    /*String wDir = new String(System.getProperty("user.dir"));
    String file = wDir +  System.getProperty("file.separator") + parent.getResourceBundle().getString("CONFIG_FILE");
    */

    int pNum  = "Proxy: ".length();
    int ppNum = "Proxy Port: ".length();
    int bNum  = "Browser: ".length();
    int puNum = "Proxy Basic: ".length();
    int wNum  = "WWW Basic: ".length();
    int dNum  = "Debug: ".length();
    int wpNum  = "WWW Port: ".length();

    //Nawaz 10-11-01
    /*try {
        BufferedReader config = new BufferedReader(new FileReader(file));
        if (config != null)
        {
          String line;
          while ((line = config.readLine()) != null){
            String data = null;
            if (line.startsWith("Proxy:") && line.length() > pNum){
              proxy = new String(line.substring(pNum,line.length()));
            } else if (line.startsWith("Browser:") && line.length() > bNum){
              browser = new String(line.substring(bNum,line.length()));
            } else if (line.startsWith("Proxy Port:") && line.length() > ppNum){
              port = new String(line.substring(ppNum,line.length()));
            } else if (line.startsWith("Proxy Basic:") && line.length() > puNum){
              pBasic = new String(line.substring(puNum,line.length()));
            } else if (line.startsWith("WWW Basic:") && line.length() > wNum){
              wBasic = new String(line.substring(wNum,line.length()));
            } else if (line.startsWith("Debug:") && line.length() > dNum){
              debug = new String(line.substring(dNum,line.length()));
            } else if (line.startsWith("WWW Port:") && line.length() > wpNum){
              wwwport = new String(line.substring(wpNum,line.length()));
            }
          }
        }
        config.close();
      }
      catch (Exception e1)
      {
         e1.printStackTrace();
     }*/

     proxy = "";
     browser = "";
     port = "";
     pBasic = "";
     wBasic = "";
     debug = "";
     wwwport = "";
  }

  public void update()
  {
    /*String wDir = new String(System.getProperty("user.dir"));
    String file = wDir +  System.getProperty("file.separator") + parent.getResourceBundle().getString("CONFIG_FILE");

    try {
        boolean started = false;
        boolean pBasicDone = false;
        boolean wBasicDone = false;
        boolean debugDone = false;
        boolean wportDone = false;
        BufferedReader oldConfig = new BufferedReader(new FileReader(file));
        BufferedWriter newConfig = new BufferedWriter(new FileWriter(file+"."+(new ClientResourceBundle()).getString("TEMP_EXT")));
        if (oldConfig != null && newConfig != null){
          String line;
          String data = null;
          while ((line = oldConfig.readLine()) != null){
            data = null;
            if (line.startsWith("Proxy:")){
              data = new String("Proxy: "+(proxy==null?"":proxy));
            } else if (line.startsWith("Browser:")){
              data = new String("Browser: "+(browser==null?"":browser));
              started = true;
            } else if (line.startsWith("Proxy Port:")){
              data = new String("Proxy Port: "+(port==null?"":port));
            } else if (line.startsWith("Proxy Basic:")){
              data = new String("Proxy Basic: "+(pBasic==null?"":pBasic));
              pBasicDone = true;
            } else if (line.startsWith("WWW Basic:")){
              data = new String("WWW Basic: "+(wBasic==null?"":wBasic));
              wBasicDone = true;
            } else if (line.startsWith("Debug:")){
              data = new String("Debug: "+(debug==null?"TRUE":debug));
              debugDone = true;
            } else if (line.startsWith("WWW Port:")){
              data = new String("WWW Port: "+(wwwport==null?"":wwwport));
              wportDone = true;
            } else {
              data = null;
            }
            if (data!=null){
              newConfig.write(data,0,data.length());
              newConfig.newLine();
            }
          }
          //System.out.println("Upated");
          if (!pBasicDone && started) { // didn't have this line
              data = new String("Proxy Basic: ");
              pBasicDone=true;
              newConfig.write(data,0,data.length());
              newConfig.newLine();
              //System.out.println("wrote");
          }
          if (!wBasicDone && started) { // didn't have this line
              data = new String("WWW Basic: ");
              wBasicDone=true;
              newConfig.write(data,0,data.length());
              newConfig.newLine();
          }
          if (!debugDone && started) { // didn't have this line
              data = new String("Debug: TRUE");
              debugDone=true;
              newConfig.write(data,0,data.length());
              newConfig.newLine();
          }
          if (!wportDone && started) { // didn't have this line
              data = new String("WWW Port: ");
              wportDone=true;
              newConfig.write(data,0,data.length());
              newConfig.newLine();
          }
        }
        oldConfig.close();
        newConfig.close();
        File temp1 = new File(file);
        File temp2 = new File(file+"."+(new ClientResourceBundle()).getString("TEMP_EXT"));
        temp1.delete();
        temp2.renameTo(temp1);
      } catch (Exception e1) {
         e1.printStackTrace();
    }
*/
  }


  public String getProxyBasic()
  {
     if (pBasic==null||pBasic.length()==0) return "";
     //System.out.println("pBasic before decrypt int:"+pBasic);
     /*
     StringTokenizer st = new StringTokenizer(pBasic,"_");

     Vector v = new Vector();
     while (st.hasMoreTokens()) {
             v.addElement(new Character((char)Integer.parseInt(st.nextToken())));
     }

     char[] chars1 = new char[v.size()];
     for (int i=0;i<v.size();i++){
        chars1[i]=((Character)v.elementAt(i)).charValue();
     }

     String msg = new String(chars1);
     //System.out.println("pBasic before decrypt char:"+msg);
     */
     //char[] chars = BothHelpObjs.decryptDes(null,pBasic);
     String result = BothHelpObjs.decryptDes(null,pBasic);
     //System.out.println("pBasic after decrypt token:"+result);
     return result;
  }

  public void setProxyBasic(String p){
     //System.out.println("Setting string before encrypt:"+p);
     //char[] chars = BothHelpObjs.encryptDes(null,p.trim());
     //System.out.println("Setting string after encrypt string:"+new String(chars));
     /*
     pBasic = new String("");
     if (chars!=null&&chars.length>0){
       for (int i=0;i<chars.length;i++){
        pBasic = pBasic + (int) chars[i] + "_";
       }
       pBasic = pBasic.substring(0,pBasic.length()-1); // crop
     }
     */
     pBasic = BothHelpObjs.encryptDes(null,p.trim());
     //System.out.println("Setting string after encrypt:"+pBasic);
  }

  public String getWWWBasic(){
     return null;
  }

  public void setWWWBasic(String p){
     wBasic = null;
  }

  public String getDebug(){
     //Nawaz 10-11-01
     //return debug==null?"":debug;
     return "false";
  }

  public void setDebug(String p){
     debug = p;
  }

  public String getWWWPort(){
     return wwwport==null?"":wwwport;
  }

  public void setWWWPort(String p){
     wwwport = p;
  }

}
































