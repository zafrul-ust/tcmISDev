package radian.tcmis.client.client.lmco.helpers;

import java.io.*;
import java.awt.*;
import javax.swing.*;
import com.borland.jbcl.layout.XYLayout;
import com.borland.jbcl.layout.XYConstraints;


public class Tcmis {

  protected static final int BACK = 0;
  protected static final int DELETE = 1;
  protected static final int BAD_DOWNLOAD = 2;
  protected static final int DUP_ORIGINAL = 3;
  protected static final int RENAME = 4;
  protected static final int BAD_UPDT_DEL = 5;

  protected PrintWriter debugWriter = null;
  protected static String classpath = "-cp jre"+System.getProperty("file.separator")+"1.1"+System.getProperty("file.separator")+"lib"+System.getProperty("file.separator")+"rt.jar;" +
             "classes"+System.getProperty("file.separator")+"jbcl2.0.jar;classes"+
             System.getProperty("file.separator")+"jgl3.1.0.jar;classes"+System.getProperty("file.separator")+
             "jbcl3.0.jar;classes"+System.getProperty("file.separator")+"jbclx.jar;classes"+
             System.getProperty("file.separator")+"swingall.jar;classes"+System.getProperty("file.separator")+
             "cryptx.jar;classes"+System.getProperty("file.separator")+"ERW.jar;classes"+
             System.getProperty("file.separator")+"ERWClient.jar;classes"+System.getProperty("file.separator")+
             "ERWCommon.jar;classes"+System.getProperty("file.separator")+"ERWDATViewer.jar;classes"+
             System.getProperty("file.separator")+"ERWMain.jar;classes"+System.getProperty("file.separator")+"RadianGui.jar;classes"+System.getProperty("file.separator")+"RadianLMCO.jar;classes"+          //the reason that it is lmk rather than lmco is because tcmis.exe was buld to look to RadianLMK
             System.getProperty("file.separator")+"RadianHelper.jar;classes"+System.getProperty("file.separator")+
             "RadianNChem.jar;classes"+System.getProperty("file.separator")+"RadianReport.jar;classes"+System.getProperty("file.separator")+
             "RadianUpdate.jar;classes"+System.getProperty("file.separator")+"SSLava.jar;classes"+System.getProperty("file.separator")+"." ;

  //protected static String execPW = new String("jre"+System.getProperty("file.separator")+"1.1"+System.getProperty("file.separator")+"bin"+System.getProperty("file.separator")+"jrew.exe "+classpath);
  //protected static String execP  = new String("jre"+System.getProperty("file.separator")+"1.1"+System.getProperty("file.separator")+"bin"+System.getProperty("file.separator")+"jre.exe " +classpath);
  //5-17-01 increase the heap
  protected static String execPW = new String("jre"+System.getProperty("file.separator")+"1.1"+System.getProperty("file.separator")+"bin"+System.getProperty("file.separator")+"jrew.exe -ms32m -mx128m "+classpath);
  protected static String execP  = new String("jre"+System.getProperty("file.separator")+"1.1"+System.getProperty("file.separator")+"bin"+System.getProperty("file.separator")+"jre.exe -ms32m -mx128m " +classpath);


  Frame f = new Frame("Error");

  public Tcmis(){

  }
  //Main method
  static public void main(String[] args) {
    Tcmis tcmis = new Tcmis();
    tcmis.doCheck(args);
  }

  void doCheck(String[] args){
    try{
       Tcmis_Waiting wait = new Tcmis_Waiting();
       wait.start();
       Thread.currentThread().sleep(5000);

       String wDir = new String(System.getProperty("user.dir"));

       execP  = wDir + System.getProperty("file.separator") + execP;
       execPW  = wDir + System.getProperty("file.separator") + execPW;


       String updt = wDir + System.getProperty("file.separator") + "update.txt";
       // Start debug file
       String deb = wDir +  System.getProperty("file.separator") + "tcmis.deb";
      try {
          FileOutputStream fOutStream = new FileOutputStream(deb);
          System.setOut(new PrintStream(fOutStream));
          System.setErr(new PrintStream(fOutStream));
          debugWriter = new PrintWriter(new BufferedWriter(new OutputStreamWriter(fOutStream)));
      } catch (Exception e){
          e.printStackTrace();
      }
      
      File test = new File(updt);
      String[] prog;
      String jre=null;
      System.out.println("args:"+args.length);
      if (test.exists()) {  // Run update
         prog = new String[2];
         prog[0] = execPW;
         prog[1] = "radian.tcmis.client.utils.update.Update";

      } else {               // Run tcmIS
         if (args.length > 0){
                int add = (args[0].equalsIgnoreCase("-W")?args.length-1:args.length);
                prog = new String[add+2];
                prog[0] = (args[0].equalsIgnoreCase("-W")?execP:execPW);
                prog[1] = "radian.tcmis.client.client.lmco.gui.LMCOCmisApp";
                for (int i=0;i<add;i++) {
                   if (i==0 && args[0].equalsIgnoreCase("-W")) continue;
                   prog[i+2] = "\""+args[i]+"\"";
                }
         } else {
                prog = new String[2];
                prog[0] =  execPW;
                prog[1] = "radian.tcmis.client.client.lmco.gui.LMCOCmisApp";
         }
      }

      // Launch tcmIS
      Runtime r = Runtime.getRuntime();
      String dump = new String("");
      for (int i=0;i<prog.length;i++) dump +=prog[i]+" ";
      System.out.println("Exec: "+dump);

      boolean worked =  wait.runProg(prog);

      if (!worked) {
         String m = "Error starting tcmIS.\nTry to restart your machine and try again.\nIf the problem persists, contact tcmIS@urscorp.com";
         JOptionPane pane = new JOptionPane(m,JOptionPane.ERROR_MESSAGE,JOptionPane.DEFAULT_OPTION);
         JDialog dialog = pane.createDialog(f, "Error");
         dialog.show();
      }

      Thread.currentThread().sleep(5000);

      System.exit(0);

    } catch (Exception e) {
      System.out.println(" -> Error starting process: \n-> "+e.getMessage()+"\n\n");
      e.printStackTrace();
    } // just to avoid dup if Start.java already defined
  }


}

class Tcmis_Dialog1 extends JDialog {
  JPanel panel1 = new JPanel();
  JProgressBar pb = new JProgressBar();
  XYLayout xYLayout1 = new XYLayout();
  JLabel jLabel1 = new JLabel();

  public Tcmis_Dialog1(Frame frame, String title, boolean modal) {
    super(frame, title, modal);
    try  {
      jbInit();
      pack();
      Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
      Dimension childSize = this.getSize();
      if (childSize.height > screenSize.height)
        childSize.height = screenSize.height;
      if (childSize.width > screenSize.width)
        childSize.width = screenSize.width;
      this.setLocation((screenSize.width - childSize.width) / 2, (screenSize.height - childSize.height) / 2);
    }
    catch(Exception ex) {
      ex.printStackTrace();
    }
  }

  public Tcmis_Dialog1() {
    this(null, "", false);
  }

  void jbInit() throws Exception {
    panel1.setLayout(xYLayout1);
    xYLayout1.setHeight(64);
    xYLayout1.setWidth(177);
    jLabel1.setText("Starting tcmIS ...");
    getContentPane().add(panel1);
    panel1.add(pb, new XYConstraints(17, 34, 145, -1));
    panel1.add(jLabel1, new XYConstraints(42, 10, 93, -1));

  }

  public void setValue(int n){
    pb.setValue(n);
    pb.revalidate();
    pb.repaint();
  }

  public void setPbMax(int n){
    pb.setMaximum(n);
  }
}

class Tcmis_Waiting extends Thread {
    public Tcmis_Waiting(){
        super("Waiting");
    }

    public void run(){
        JFrame f = new JFrame("Dummy");
        Tcmis_Dialog1 wait = new Tcmis_Dialog1(f,"Please wait",false);
        wait.setPbMax(100);
        wait.setValue(0);
        wait.setVisible(true);
        while (true){
           for (int i=0;i<100;i++){
                wait.setValue(i+1);
                try { this.sleep(500); } catch (Exception e) { e.printStackTrace(); };
           }
        }
    }

    public boolean runProg(String[] p){
       // Launch tcmIS
      try {
        Runtime r = Runtime.getRuntime();
        String dump = new String("");
        for (int i=0;i<p.length;i++) dump +=p[i]+" ";
        System.out.println("Exec: "+dump);
        r.exec(p);
        return true;
      } catch (Exception e) {
         e.printStackTrace();
         return false;
      }
    }
}





























