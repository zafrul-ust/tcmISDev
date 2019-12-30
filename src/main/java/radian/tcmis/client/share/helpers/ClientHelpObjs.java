//Title:
//Version:
//Copyright:
//Author:
//Company:
//Description:
package radian.tcmis.client.share.helpers;

import radian.tcmis.both1.helpers.*;
import radian.tcmis.client.share.gui.URLGrab;
import radian.tcmis.client.share.gui.*;
import radian.tcmis.client.share.httpCgi.*;

import java.util.*;
import java.text.*;
import java.io.*;
import java.awt.*;
import java.awt.Component;
import java.net.*;
//Nawaz 10-11-01
//import sun.audio.*;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.text.*;

import radian.tcmis.both1.helpers.resource.ResourceLoader;

public class ClientHelpObjs {
  public static final int WRAPLENGTH = 45;
  public static final int BACKGROUND = 1;
  public static final int FOREGROUND = 2;

  public static final int BORDER_RAISED = BevelBorder.RAISED;
  public static final int BORDER_LOWERED = BevelBorder.LOWERED;
  public static final int BORDER_STANDARD = 0;
  public static final int NO_BORDER = -1;

  public static final int SOUND_DONE = 1;
  public static final int SOUND_BUTTON_ACTION = 2;

  public static final int FILE_NOT_FOUND = 1;
  public static final int CAN_NOT_READ_FILE = 2;
  public static final int FILE_EMPTY = 3;
  public static final int POSS_SERVER_ERROR = 4;
  public static final int FILE_SENT_OK = 0;

  public static final int AUDIT_TEXT_NON_BLANK = 1;
  public static final int AUDIT_NUMERIC_NON_NEG = 2;
  public static final int AUDIT_NUMERIC_NON_ZERO = 3;
  public static final int AUDIT_NUMERIC = 4;

  public static String transferJar() {
    String jhome = System.getProperty("java.home");
    System.out.println("-------- java home: " + jhome);
    File libdir = new File(jhome + "/lib");
    if (!libdir.exists()) {
      return "Cannot find JVM lib directory.\nPlease contact your CSR for more info.";
    } else {
      try {
        // just to be safe
        File extdir = new File(libdir, "ext");
        if (!extdir.exists()) {
          extdir.mkdir();
        }
        if (!extdir.exists()) {
          return "Cannot find JVM extension directory.\nPlease contact your CSR for more info.";
        }
        String[] jarFilesName = {
            "jsse.jar", "jcert.jar", "jnet.jar"};
        //don't go download if already exist
        for (int i = 0; i < jarFilesName.length; i++) {
          // Get the jsse.jar and put it in the extension directory
          URL url = new URL("http://www.tcmis.com/extensionJars/" + jarFilesName[i]);
          URLConnection urlconnection = url.openConnection();
          BufferedInputStream bis = new BufferedInputStream(urlconnection.getInputStream());
          File jarFile = new File(extdir, jarFilesName[i]);
          if (!jarFile.exists()) {
            System.out.println("--------- downloading: " + jarFilesName[i]);
            jarFile.createNewFile();
            FileOutputStream fos = new FileOutputStream(jarFile);
            byte[] buffer = new byte[4096];
            int available = 0;
            while ( (available = bis.available()) > 0) {
              do {
                int rlen = Math.min(buffer.length, available);
                bis.read(buffer, 0, rlen);
                fos.write(buffer, 0, rlen);
                available -= rlen;
              } while (available > 0);
            }
            bis.close();
            fos.flush();
            fos.close();
          } else {
            System.out.println("---------- jar exist");
          }
        } //end of for
      } catch (Exception e) {
        e.printStackTrace();
        return "Cannot download files.\nPlease contact your CSR for more info.";
      }
    } //end of else
    return "Done";
  } //enf of method

  public static AttributiveCellRenderer createRenderer(Color color, Insets insets, int align) {
    AttributiveCellRenderer renderer = new AttributiveCellRenderer(align);
    renderer.setColumnBorder(new LinesBorder(color, insets));
    return renderer;
  }

  public static AttributiveCellRendererLine createRendererLine(Color color, Insets insets, int align) {
    AttributiveCellRendererLine renderer = new AttributiveCellRendererLine(align);
    renderer.setColumnBorder(new LinesBorder(color, insets));
    return renderer;
  }

  //this method write to a file and return a URL string
  public Hashtable punchOutSendPostWriteToFile(String url, String data, String mimetype, String fileName, SearchPT searchPT, boolean firstTime) throws Exception {
    Hashtable result = new Hashtable();
    URL tmp = null;
    try {
      if (firstTime) {
        URL.setURLStreamHandlerFactory(new URLStreamHandlerFactory() {
          public URLStreamHandler createURLStreamHandler(final String protocol) {
            if ("https".equals(protocol)) {
//                  return new com.sun.net.ssl.internal.www.protocol.https.Handler();
            }
            ;
            return null;
          }
        });
      }
      URL target = new URL(url);
      HttpURLConnection uc = (HttpURLConnection) target.openConnection();
      uc.setDoOutput(true);
      uc.setUseCaches(false);
      uc.setRequestMethod("POST");
      uc.setRequestProperty("Content-Type", mimetype);
      if (data != null) {
        OutputStream os = uc.getOutputStream();
        PrintWriter writer = new PrintWriter(os);
        writer.write(data);
        writer.close();
      }

      //2-06-02
      InputStream in = uc.getInputStream();
      int rspcode = uc.getResponseCode();
      System.out.println("--------- return code: " + rspcode + " from URL: " + url);
      searchPT.sendAribaResponseCodeToServer(rspcode);

      String wDir = new String(System.getProperty("user.dir"));
      String file = wDir + System.getProperty("file.separator") + fileName;
      //System.out.println("------- java web start dir: " + file);
      File test = new File(file);
      test.delete();
      BufferedWriter bw = new BufferedWriter(new FileWriter(file));
      //2-06-02 move higher up InputStream in = uc.getInputStream();
      BufferedReader br = new BufferedReader(new InputStreamReader(in));
      StringBuffer sb = new StringBuffer();
      do {
        String line;
        if ( (line = br.readLine()) == null) {
          break;
        }
        if (bw != null) {
          bw.write(line);
        }
      } while (true);
      bw.close();
      br.close();
      //2-06-02 move higher up int rspcode = uc.getResponseCode();
      String location = uc.getHeaderField("location");
      uc.disconnect();
      int count = 0;
      if (rspcode == 301 || rspcode == 302) {
        String tempLoc = url.substring(0, url.indexOf(".com/") + 4);
        result.put("URL", tempLoc + location);
      }
      result.put("RESPONSE_CODE", new Integer(rspcode));
      //2-06-02 move higher up searchPT.sendAribaResponseCodeToServer(rspcode);
      tmp = new URL("file:///" + file);
    } catch (Exception e) {
      throw e;
    }
    result.put("FILE", tmp.toExternalForm());
    return result;
  }

  public static void monitor(int mode, String msg) {
    if (mode == 0) {
      // System.out.println(msg);
      return;
    }
    Date D = new Date();
    SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
    String filename = (new ClientResourceBundle()).getString("LOG_DIR") + formatter.format(D) + ".log";
    FileOutputStream outStream;
    FileInputStream inStream;
    int inBytes = 0;
    byte inBuf[] = new byte[1];
    try {
      try {
        inStream = new FileInputStream(filename);
        inBytes = inStream.available();
        inBuf = new byte[inBytes];
        int bytesRead = inStream.read(inBuf, 0, inBytes);
        inStream.close();
      } catch (Exception e) {}

      outStream = new FileOutputStream(filename);
      if (inBytes > 0) {
        outStream.write(inBuf, 0, inBytes);
      }
      for (int i = 0; i < msg.length(); i++) {
        outStream.write(msg.charAt(i));
      }
      outStream.write('\n');
      outStream.close();
    } catch (Exception e) {
    }

    return;
  }

  public static Choice choiceSort(Choice in) {
    int x = in.getItemCount();
    String[] mud = new String[x];
    String[] sorted;
    for (int z = 0; z < x; z++) {
      mud[z] = new String(in.getItem(z));
    }
    in.removeAll();
    sorted = BothHelpObjs.sort(mud);
    for (int z = 0; z < x; z++) {
      in.addItem(sorted[z]);
    }
    return in;
  }

  public static com.borland.jbcl.control.ChoiceControl choiceSort(com.borland.jbcl.control.ChoiceControl in) {
    int x = in.getItemCount();
    String[] mud = new String[x];
    String[] sorted;
    for (int z = 0; z < x; z++) {
      mud[z] = new String(in.getItem(z));
    }
    in.removeAll();
    sorted = BothHelpObjs.sort(mud);
    for (int z = 0; z < x; z++) {
      in.addItem(sorted[z]);
    }
    return in;
  }

  public static JComboBox choiceSort(JComboBox in) {
    int x = in.getItemCount();
    if (x < 2) {
      return in;
    }
    String[] mud = new String[x];
    String[] sorted;
    for (int z = 0; z < x; z++) {
      mud[z] = new String(in.getItemAt(z).toString());
    }
    in.removeAllItems();
    sorted = BothHelpObjs.sort(mud);
    for (int z = 0; z < x; z++) {
      in.addItem(sorted[z]);
    }
// CBK - begin
    // the JComboBox doesn't work well if the first item is a blank string
    // the next bit of code moves a blank string to the last item where
    // JComboBox can deal with it.
    if (x > 0 && BothHelpObjs.isBlankString(in.getItemAt(0).toString())) {
      String blank = in.getItemAt(0).toString();
      in.removeItemAt(0);
      in.addItem(blank);
    }
// CBK - end
    return in;
  }

  public static Object[][] getArrayFromTable(JTable t) {
    Object[][] oa = new Object[t.getRowCount()][t.getColumnCount()];
    for (int i = 0; i < t.getRowCount(); i++) {
      for (int j = 0; j < t.getColumnCount(); j++) {
        oa[i][j] = t.getModel().getValueAt(i, j);
      }
    }
    return oa;
  }

  public static void playSound(int i) {
    if (new ClientResourceBundle().getSoundMuted()) {
      return;
    }
    String filename = "";
    switch (i) {
      case SOUND_DONE:
        filename = "done.au";
        break;
      case SOUND_BUTTON_ACTION:
        filename = "click.au";
        break;
      default:
        filename = "";
    }
    if (BothHelpObjs.isBlankString(filename)) {
      return;
    }
    playSound(filename);

  }

  public static void playSound(String s) {
    if (new ClientResourceBundle().getSoundMuted()) {
      return;
    }
    if (BothHelpObjs.isBlankString(s)) {
      return;
    }
    //System.out.println("The audio file is gone");
    if (ResourceLoader.playAudioFile("Sound/" + s + "")) {

    } else {
      System.out.println("The audio file did not play");
    }

    /*String wDir = new String(System.getProperty("user.dir"));
           String file = wDir + System.getProperty("file.separator") + (new ClientResourceBundle()).getString("LOCAL_SOUND_DIR")+ System.getProperty("file.separator") + s ;
           AudioStream as;
           try{
      as = new AudioStream(new FileInputStream(file));
      AudioPlayer.player.start(as);
           } catch (IOException e) {
      e.printStackTrace();
      System.err.println("Can't open audio stream");
           }*/

  }

  public static void playUrl(String url) {
    /* if(new ClientResourceBundle().getSoundMuted()) {
       return;
     }
     // fix fix to play from local file
     if(true){
        playSound(SOUND_DONE);
        return;
     }
     URL url1,url2;
     SequenceInputStream sis;
     AudioStream as;
     try{
       url1 = new URL(url);
       URLConnection uC = url1.openConnection();
       as = new AudioStream(uC.getInputStream());
       AudioPlayer.player.start(as);
     } catch (IOException e) {
       System.err.println("Can't open audio stream");
     }*/
  }

  public static Choice loadChoiceFromVector(Choice in, Vector v) {
    if (v == null || v.isEmpty()) {
      return in;
    }
    for (int x = 0; x < v.size(); x++) {
      in.add(v.elementAt(x).toString());
    }
    return in;
  }

  public static JComboBox loadChoiceFromVector(JComboBox in, Vector v) {
    if (v == null || v.isEmpty()) {
      return in;
    }
    for (int x = 0; x < v.size(); x++) {
      in.addItem(v.elementAt(x));
    }
    return in;
  }

  public static JComboBox loadChoiceFromVector(JComboBox in, Vector v, boolean separateThread) {
    if (separateThread) {
      try {
        ClientHelpObjs_ChoiceThread t = new ClientHelpObjs_ChoiceThread(in, v);
        t.start();
      } catch (Exception e) {
        e.printStackTrace();
        return in;
      }
    } else {
      return loadChoiceFromVector(in, v);
    }
    return in;

  }

  public static void showCursor(Container c) {
    Component[] comps = c.getComponents();
    Cursor cu = c.getCursor();
    int r = cu.getType();
    // System.out.println("cursor:" + r + "--" + cu.toString() + "::" + c.toString());
    for (int i = 0; i < comps.length; i++) {
      if (comps[i] instanceof Container) {
        ClientHelpObjs.showCursor( (Container) comps[i]);
      } else {
        Cursor cx = comps[i].getCursor();
        int type = cx.getType();
        // System.out.println("cursor:" + type + "--" + cx.toString() + "::" + comps[i].toString());
      }
    }
    return;
  }

  public static String wrapString(String S) {
    return ClientHelpObjs.wrapString(S, WRAPLENGTH);
  }

  public static String wrapString(String S, int interval) {
    String[] chop = {
        " ", ",", ".", "?", "-", "&", "!", "%"};
    String line = "\n";
    boolean flag = true;
    String out = "";
    int k = 0;

    while (S.length() >= interval) {
      String now = S.substring(0, interval);
      String later = S.substring(interval);
      if (now.lastIndexOf(line) >= 0) {
        int q = now.lastIndexOf(line);
        out = out + now.substring(0, q + 1);
        S = new String(now.substring(q + 1) + later);
        continue;
      }
      later.trim();
      if (later.equals(".")) {
        later = now.substring(now.length() - 1) + later;
        now = now.substring(0, now.length() - 1);
      }
      int here = 0;
      for (int r = 0; r < chop.length - 1; r++) {
        int q = now.lastIndexOf(chop[r]);
        if (q > here) {
          here = q;
        }
      }
      if (here == 0) {
        here = interval;
      }
      out = out + now.substring(0, here) + "\n";
      S = new String(now.substring(here) + later).trim();
    }
    out = out + S;
    return out;
  }

  public static void setComboBoxUpdateUi(Container C) {
    //for JRE 1.4 and comment out if JRE 1.3
    Component[] comps = C.getComponents();
    if (comps != null) {
      for (int i = 0; i < comps.length; i++) {
        if (comps[i] == null) {
          continue;
        }
        if (comps[i] instanceof JComboBox) {
          ( (JComboBox) comps[i]).updateUI();
        }
        if (comps[i] instanceof Container) {
          ClientHelpObjs.setComboBoxUpdateUi( (Container) comps[i]);
        }
      } //end of for loop
    } //end of if comps is not null
    // end of concern

  } //end of method

  public static void setEnabledContainer(Container C, boolean flag) {
    Component[] comps = C.getComponents();
    C.setEnabled(flag);
    for (int i = 0; i < comps.length; i++) {
      comps[i].setEnabled(flag);

      if (comps[i] instanceof Container) {
        ClientHelpObjs.setEnabledContainer( (Container) comps[i], flag);
      }
    }
    return;
  }

  public static void setEnabledChildOnly(Component C, boolean flag) {
    if (C instanceof Container) {
      Component[] comps = ( (Container) C).getComponents();
      for (int i = 0; i < comps.length; i++) {
        if (comps[i] instanceof Button ||
            comps[i] instanceof JButton ||
            comps[i] instanceof JComboBox ||
            comps[i] instanceof Choice ||
            comps[i] instanceof Checkbox ||
            comps[i] instanceof java.awt.List ||
            comps[i] instanceof TextComponent ||
            comps[i] instanceof JTextComponent ||
            comps[i] instanceof JList) {

          comps[i].setEnabled(flag);
        } else if (comps[i] instanceof JRadioButton ||
                   comps[i] instanceof JCheckBox) {
          comps[i].setEnabled(flag);
          comps[i].repaint();
          comps[i].setForeground(Color.darkGray);
          comps[i].repaint();
        } else if (comps[i] instanceof Container) {
          ClientHelpObjs.setEnabledChildOnly(comps[i], flag);
        }
      }
    }
    return;
  }

  public static Component[] getAllComponents(Container cont) {
    Vector v = new Vector();
    Component[] comps = cont.getComponents();
    Component[] comp2 = null;
    for (int i = 0; i < comps.length; i++) {
      if (comps[i] instanceof Container) {
        comp2 = ClientHelpObjs.getAllComponents( (Container) comps[i]);
        for (int j = 0; j < comp2.length; j++) {
          v.addElement(comp2[j]);
        }
      }
      if (comps[i] instanceof Component) {
        v.addElement(comps[i]);
      }
    }
    Component[] result = new Component[v.size()];
    for (int i = 0; i < v.size(); i++) {
      result[i] = (Component) v.elementAt(i);
    }

    return result;

  }

  public static void setContainerColor(Container C, int what, Color color, Vector avoid, Vector only) {
    Component[] comps = null;
    //if (C instanceof JDialog) ((JDialog) C).getContentPane().getComponents();
    //else if (C instanceof JFrame) ((JFrame) C).getContentPane().getComponents();
    ///else if (C instanceof JInternalFrame) ((JInternalFrame) C).getContentPane().getComponents();
    //else
    comps = C.getComponents();

    if (what == ClientHelpObjs.BACKGROUND) {
      if (doContainerChange(avoid, only, ClientHelpObjs.getClassNameWithoutPack(C))) {
        C.setBackground(color);
      }
      for (int i = 0; i < comps.length; i++) {
        if (doContainerChange(avoid, only, ClientHelpObjs.getClassNameWithoutPack(comps[i]))) {
          comps[i].setBackground(color);
        }
        if (comps[i] instanceof Container) {
          ClientHelpObjs.setContainerColor( (Container) comps[i], what, color, avoid, only);
        }
      }
      return;
    } else if (what == ClientHelpObjs.FOREGROUND) {
      if (doContainerChange(avoid, only, ClientHelpObjs.getClassNameWithoutPack(C))) {
        C.setForeground(color);
      }
      for (int i = 0; i < comps.length; i++) {
        if (doContainerChange(avoid, only, ClientHelpObjs.getClassNameWithoutPack(comps[i]))) {
          comps[i].setForeground(color);
        }
        if (comps[i] instanceof Container) {
          if (comps[i] instanceof JPanel) {
            JPanel j = (JPanel) comps[i];
            Object b = j.getBorder();
            if (b instanceof TitledBorder) {
              TitledBorder bo = (TitledBorder) b;
              if (doContainerChange(avoid, only, "TitledBorder")) {
                bo.setTitleColor(color);
              }
            }
          }
          if (comps[i] instanceof JScrollPane) {
            JScrollPane j = (JScrollPane) comps[i];
            Object b = j.getBorder();
            if (b instanceof TitledBorder) {
              TitledBorder bo = (TitledBorder) b;
              if (doContainerChange(avoid, only, "TitledBorder")) {
                bo.setTitleColor(color);
              }
            }
          }
          ClientHelpObjs.setContainerColor( (Container) comps[i], what, color, avoid, only);
        }
      }
      return;
    }
    return;
  }

  public static void setContainerFont(Container C, Font font, Vector avoid, Vector only) {
    Component[] comps = null;
    //if (C instanceof JDialog) ((JDialog) C).getContentPane().getComponents();
    //else if (C instanceof JFrame) ((JFrame) C).getContentPane().getComponents();
    ///else if (C instanceof JInternalFrame) ((JInternalFrame) C).getContentPane().getComponents();
    //else
    comps = C.getComponents();

    if (doContainerChange(avoid, only, ClientHelpObjs.getClassNameWithoutPack(C))) {
      C.setFont(font);
    }
    for (int i = 0; i < comps.length; i++) {
      if (doContainerChange(avoid, only, ClientHelpObjs.getClassNameWithoutPack(comps[i]))) {
        comps[i].setFont(font);
      }
      if (comps[i] instanceof Container) {
        if (comps[i] instanceof JPanel) {
          JPanel j = (JPanel) comps[i];
          Object b = j.getBorder();
          if (b instanceof TitledBorder) {
            TitledBorder bo = (TitledBorder) b;
            if (doContainerChange(avoid, only, "TitledBorder")) {
              bo.setTitleFont(font);
            }
          }
        }

        // if you want to change all buttons border thru the system
        /*
                           if (comps[i] instanceof JButton){
            JButton b = new JButton();
            b = (JButton) comps[i];
            b.setBorder(new BevelBorder(BevelBorder.RAISED));
                           }
         */

        ClientHelpObjs.setContainerFont( (Container) comps[i], font, avoid, only);
      }
    }
    return;
  }

  public static void setContainerMinimunSize(JComponent C, Dimension size) {
    Component[] comps = null;
    //if (C instanceof JDialog) ((JDialog) C).getContentPane().getComponents();
    //else if (C instanceof JFrame) ((JFrame) C).getContentPane().getComponents();
    ///else if (C instanceof JInternalFrame) ((JInternalFrame) C).getContentPane().getComponents();
    //else
    comps = C.getComponents();

    C.setMinimumSize(size);
    for (int i = 0; i < comps.length; i++) {
      if (comps[i] instanceof JComponent) {
        JComponent jComp = (JComponent) comps[i];
        jComp.setMinimumSize(size);
        ClientHelpObjs.setContainerMinimunSize(jComp, size);
      }
    }
    return;
  }

  protected static String getClassNameWithoutPack(Object o) {
    String classN = o.getClass().getName();
    classN = classN.substring(classN.lastIndexOf(".") + 1);
    return new String(classN);
  }

  protected static boolean doContainerChange(Vector avoid, Vector only, String name) {
    if (only == null && avoid == null) {
      return true;
    }
    if (only != null && only.contains(name)) {
      return true;
    }
    if (only == null && (avoid != null && !avoid.contains(name))) {
      return true;
    }
    return false;
  }

  public static Object[][] castTableArray(Object[][] oa, String cols) {
    int[] z = new int[cols.length()];
    for (int q = 0; q < cols.length(); q++) {
      try {
        z[q] = Integer.parseInt(cols.substring(q, q + 1));
      } catch (Exception e) {
        z[q] = -1;
      }
    }
    for (int r = 0; r < oa.length; r++) {
      for (int c = 0; c < cols.length(); c++) {
        switch (z[c]) {
          case 1:
            oa[r][c] = (String) oa[r][c];
            break;
          case 2:
            try {
              String sTmp = oa[r][c].toString();
              if (sTmp.indexOf(".") > 0) {
                Double dTmp = new Double(sTmp);
                oa[r][c] = dTmp;
                System.out.println("As Double:" + oa[r][c]);
              } else {
                Integer iTmp = new Integer(sTmp);
                oa[r][c] = iTmp;
              }
            } catch (Exception e) {
              oa[r][c] = (Object) oa[r][c];
            }
            break;
          default:
        }

      }
    }
    return oa;
  }

  public static Border groupBox(String text) {
    TitledBorder b = BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), text, TitledBorder.LEFT, TitledBorder.CENTER, new Font("Dialog", 0, 10));
    return b;
  }

  //trong 3/13/00
  public static Border groupBoxBlue(String text) {
    TitledBorder b = BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(Color.blue, Color.gray), text, TitledBorder.LEFT, TitledBorder.CENTER, new Font("Dialog", 0, 11));
    return b;
  }

  public static JButton getStandardJButton(String text, String gif, String tooltip, int border, int w, int h) {
    /*String wDir = new String(System.getProperty("user.dir"));
           String imagesDir = wDir + System.getProperty("file.separator") + (new ClientResourceBundle()).getString("LOCAL_IMAGES_DIR");
     */
    JButton b = new JButton();
    b.setText(text == null ? "" : text);
    if (gif != null) {
      ImageIcon ss = ResourceLoader.getImageIcon("images/button/" + gif, "Text");
      b.setIcon(ss);
    }
    if (tooltip != null) {
      b.setToolTipText(tooltip);

    }
    if (border < 0) {
      b.setBorderPainted(false);
    } else if (border > 0) {
      b.setBorder(new BevelBorder(border));
    }
    if (w > 0 && h > 0) {
      b.setPreferredSize(new Dimension(w, h));
    }
    return b;
  }

  public static void makeDefaultColors(Container comp) {
    JLabel l = new JLabel();
    Color myBlue = new Color(90, 80, 130);

    // static
    Vector v = new Vector();
    v.addElement("JCheckBox");
    v.addElement("JRadioButton");
    v.addElement("JButton");
    v.addElement("JToggleButton");
    v.addElement("TitledBorder");
    ClientHelpObjs.setContainerColor(comp, ClientHelpObjs.FOREGROUND, Color.black, null, v);
    ClientHelpObjs.setContainerFont(comp, new Font("Dialog", 0, 10), null, v);

    // data
    v = new Vector();
    v.addElement("JTextField");
    v.addElement("JTextArea");
    v.addElement("JTextPane");
    v.addElement("NextNameTextField");
    v.addElement("NextManufacturer");
    v.addElement("JComboBox");
    v.addElement("FacilityCombo");
    v.addElement("MonthCombo");
    v.addElement("YearCombo");
    v.addElement("ChemListsCombo");
    v.addElement("WorkAreaCombo");
    v.addElement("CatalogCombo");
    v.addElement("JPasswordField");
    ClientHelpObjs.setContainerColor(comp, ClientHelpObjs.FOREGROUND, myBlue, null, v);
    ClientHelpObjs.setContainerFont(comp, new Font("Dialog", 1, 11), null, v);

    //groupBox
    v = new Vector();
    v.addElement("JPanel");
    v.addElement("JScrollPane");
    ClientHelpObjs.setContainerColor(comp, ClientHelpObjs.FOREGROUND, Color.darkGray, null, v);
  }

  public static void viewMSDS() {

  }

//=======================Modified======================//
  public static void goURL(CmisApp cmis, String matID, String specID, String client, String facID, int itemID, int kind) {
    String q = new String();
    URLGrab U;

    // material ID
    q = new String("id=" + matID);

    // spec ID
    if (specID == null || specID.length() == 0) {
      q = q + "&spec=";
    } else {
      q = q + "&spec=" + specID;
    }

    // client
    q = q + "&cl=" + client;

    // facility
    q = q + "&facility=" + facID;

    // item
    q = q + "&itemid=" + itemID;

    q = q.trim().replace(' ', '+');
    U = new URLGrab(kind, q, cmis);
  }

//=======================End Modified======================//
  //6-14-01
  public static void goURL(CmisApp cmis, String matID, String specID, String client, String facID, String itemID, int kind, boolean hasSpec) {
    String q = new String();
    URLGrab U;

    // material ID
    q = new String("id=" + matID);

    // spec ID
    if (!hasSpec) {
      q += "&showspec=N";
    }
    if (specID == null || specID.length() == 0) {
      q = q + "&spec=";
    } else {
      q = q + "&spec=" + specID;
    }

    // client
    q = q + "&cl=" + client;

    // facility
    q = q + "&facility=" + facID;

    // item
    q = q + "&catpartno=" + itemID;

    q = q.trim().replace(' ', '+');
    U = new URLGrab(kind, q, cmis);
  }

  public static void goURL(CmisApp cmis, String matID, String specID, String client, String facID, int itemID, int kind, boolean hasSpec) {
    String q = new String();
    URLGrab U;

    // material ID
    q = new String("id=" + matID);

    // spec ID
    if (!hasSpec) {
      q += "&showspec=N";
    }
    if (specID == null || specID.length() == 0) {
      q = q + "&spec=";
    } else {
      q = q + "&spec=" + specID;
    }

    // client
    q = q + "&cl=" + client;

    // facility
    q = q + "&facility=" + facID;

    // item
    q = q + "&itemid=" + itemID;

    q = q.trim().replace(' ', '+');
    U = new URLGrab(kind, q, cmis);
  }

  public static void goWasteURL(CmisApp cmis, String client, int itemID, int kind) {
    String q = new String();
    URLGrab U;

    // material ID
    q = new String("id=" + itemID);

    q = q.trim().replace(' ', '+');
    U = new URLGrab(kind, q, cmis);
  }

  public static void goLabelURL(CmisApp cmis, String client, Vector containerId, int kind, String labelType) {
    String q = new String();
    URLGrab U;

    Integer number = new Integer(containerId.size());
    String url = "num=" + number.toString()+"&labelType="+labelType;
    for (int i = 0; i < containerId.size(); i++) {
      url = url + "&id" + i + "=" + containerId.elementAt(i).toString();
    }

    q = new String(url);
    U = new URLGrab(kind, q, cmis);
  }

  public static void goReportURL(CmisApp cmis, String myClient, Hashtable screenInfo, int kind) {
    String q = new String();
    String url = new String("");
    URLGrab U;

    String currentScreen = (String) screenInfo.get("CURRENT_SCREEN");
    if (currentScreen.equalsIgnoreCase("Catalog")) {
      url = getCatalogUrl(myClient, screenInfo);
    } else if (currentScreen.equalsIgnoreCase("OT")) { //Order Track
      url = getOrderTrackUrl(myClient, screenInfo);
    } else if (currentScreen.equalsIgnoreCase("NCT")) { //New Chem Track
      url = getNewChemTrackUrl(myClient, screenInfo);
    } else if (currentScreen.equalsIgnoreCase("WC")) { //Waste Catalog
      url = getWasteCatalogUrl(myClient, screenInfo);
    } else if (currentScreen.equalsIgnoreCase("WT")) { //Waste Track Win
      url = getWasteTrackWinUrl(myClient, screenInfo);
    } else if (currentScreen.equalsIgnoreCase("WM")) { //Waste Management
      url = getWasteManagementUrl(myClient, screenInfo);
    } else if (currentScreen.equalsIgnoreCase("WOT")) { //Waste Order Track
      url = getWasteOrderTrackUrl(myClient, screenInfo);
    } else if (currentScreen.equalsIgnoreCase("IF")) { //Inventory Float (Catalog R.M.C)
      url = getInventoryFloatUrl(myClient, screenInfo);
    } else if (currentScreen.equalsIgnoreCase("UP")) { //User Profile
      url = getUserProfileUrl(myClient, screenInfo);
    } else if (currentScreen.equalsIgnoreCase("Inventory")) { //Inventory
      url = getInventoryUrl(myClient, screenInfo);
    } else if (currentScreen.equalsIgnoreCase("MR")) { //Material Request
      url = getMaterialRequestUrl(myClient, screenInfo);
    } else if (currentScreen.equalsIgnoreCase("Work_Area")) { //Admin Work Area
      url = getWorkAreaUrl(myClient, screenInfo);
    } else if (currentScreen.equalsIgnoreCase("MSDS")) { //Usage report: MSDS revision report
      url = getMSDSUrl(myClient, screenInfo);
    } else if (currentScreen.equalsIgnoreCase("Approvers")) { // Admin screeen Approvers
      url = getApproverUrl(myClient, screenInfo);
    } else if (currentScreen.equalsIgnoreCase("Use_App_Group")) { // Approver groups
      url = getUseAppURL(myClient, screenInfo);
    } else if (currentScreen.equalsIgnoreCase("New_Chem_App")) { // New Chemical Approvers
      url = getNewChemURL(myClient, screenInfo);
    } else if (currentScreen.equalsIgnoreCase("Admin_Group")) { // Admin groups
      url = getAdminGrpURL(myClient, screenInfo);
    } else if (currentScreen.equalsIgnoreCase("LIST") ||
               currentScreen.equalsIgnoreCase("COST") ||
               currentScreen.equalsIgnoreCase("INVOICE")) { // Waste order: list && cost && invoice
      url = getListCostURL(myClient, screenInfo);
    }

    //System.out.println("=================  url: "+url);
    q = new String(url);
    U = new URLGrab(kind, q, cmis);
  }

  //trong 12/6/00 waste order: list && cost && invoice
  public static String getListCostURL(String myClient, Hashtable screenInfo) {
    String url = "";
    url += "client=" + URLEncoder.encode(myClient.toString());
    url += "&which_screen=" + URLEncoder.encode( (String) screenInfo.get("CURRENT_SCREEN"));
    url += "&vendor_name=" + URLEncoder.encode( (String) screenInfo.get("VENDOR_NAME"));
    url += "&waste_order=" + URLEncoder.encode( (String) screenInfo.get("WASTE_ORDER"));
    url += "&requestor_name=" + URLEncoder.encode( (String) screenInfo.get("REQUESTOR_NAME"));
    url += "&storage_area=" + URLEncoder.encode( (String) screenInfo.get("STORAGE_AREA"));
    url += "&submit_date=" + URLEncoder.encode( (String) screenInfo.get("SUBMIT_DATE"));
    url += "&resubmit_date=" + URLEncoder.encode( (String) screenInfo.get("RESUBMIT_DATE"));
    url += "&file_type=" + URLEncoder.encode( (String) screenInfo.get("FILE_TYPE"));
    url += "&method=" + URLEncoder.encode( (String) screenInfo.get("METHOD"));
    url += "&user_id=" + URLEncoder.encode( (String) screenInfo.get("USER_ID"));

    return url;
  }

  public static String getApproverUrl(String myClient, Hashtable screenInfo) {
    String url = "";
    url += "client=" + URLEncoder.encode(myClient.toString());
    url += "&which_screen=" + URLEncoder.encode( (String) screenInfo.get("CURRENT_SCREEN"));
    url += "&facility_id=" + URLEncoder.encode( (String) screenInfo.get("FACILITY_ID"));
    url += "&file_type=" + URLEncoder.encode( (String) screenInfo.get("FILE_TYPE"));
    url += "&method=" + URLEncoder.encode( (String) screenInfo.get("METHOD"));
    url += "&user_id=" + URLEncoder.encode( (String) screenInfo.get("USER_ID"));
    return url;
  }

  public static String getUseAppURL(String myClient, Hashtable screenInfo) {
    String url = "";
    url += "client=" + URLEncoder.encode(myClient.toString());
    url += "&which_screen=" + URLEncoder.encode( (String) screenInfo.get("CURRENT_SCREEN"));
    url += "&facility_id=" + URLEncoder.encode( (String) screenInfo.get("FACILITY_ID"));
    url += "&group_desc=" + URLEncoder.encode( (String) screenInfo.get("GROUP_DESC"));
    url += "&file_type=" + URLEncoder.encode( (String) screenInfo.get("FILE_TYPE"));
    url += "&method=" + URLEncoder.encode( (String) screenInfo.get("METHOD"));
    url += "&user_id=" + URLEncoder.encode( (String) screenInfo.get("USER_ID"));
    return url;
  }

  public static String getAdminGrpURL(String myClient, Hashtable screenInfo) {
    String url = "";
    url += "client=" + URLEncoder.encode(myClient.toString());
    url += "&which_screen=" + URLEncoder.encode( (String) screenInfo.get("CURRENT_SCREEN"));
    url += "&facility_id=" + URLEncoder.encode( (String) screenInfo.get("FACILITY_ID"));
    url += "&group_desc=" + URLEncoder.encode( (String) screenInfo.get("GROUP_DESC"));
    url += "&file_type=" + URLEncoder.encode( (String) screenInfo.get("FILE_TYPE"));
    url += "&method=" + URLEncoder.encode( (String) screenInfo.get("METHOD"));
    url += "&user_id=" + URLEncoder.encode( (String) screenInfo.get("USER_ID"));
    return url;
  }

  public static String getMSDSUrl(String myClient, Hashtable screenInfo) {
    String url = "";
    url += "client=" + URLEncoder.encode(myClient.toString());
    url += "&which_screen=" + URLEncoder.encode( (String) screenInfo.get("CURRENT_SCREEN"));
    url += "&facility_id=" + URLEncoder.encode( (String) screenInfo.get("FACILITY_ID"));
    url += "&file_type=" + URLEncoder.encode( (String) screenInfo.get("FILE_TYPE"));
    url += "&method=" + URLEncoder.encode( (String) screenInfo.get("METHOD"));
    url += "&user_id=" + URLEncoder.encode( (String) screenInfo.get("USER_ID"));
    return url;
  }

  public static String getNewChemURL(String myClient, Hashtable screenInfo) {
    String url = "";
    url += "client=" + URLEncoder.encode(myClient.toString());
    url += "&which_screen=" + URLEncoder.encode( (String) screenInfo.get("CURRENT_SCREEN"));
    url += "&facility_id=" + URLEncoder.encode( (String) screenInfo.get("FACILITY_ID"));
    url += "&file_type=" + URLEncoder.encode( (String) screenInfo.get("FILE_TYPE"));
    url += "&method=" + URLEncoder.encode( (String) screenInfo.get("METHOD"));
    url += "&user_id=" + URLEncoder.encode( (String) screenInfo.get("USER_ID"));
    return url;
  }

  //Admin: Work Area
  public static String getWorkAreaUrl(String myClient, Hashtable screenInfo) {
    String url = "";
    url += "client=" + URLEncoder.encode(myClient.toString());
    url += "&which_screen=" + URLEncoder.encode( (String) screenInfo.get("CURRENT_SCREEN"));
    url += "&facility_id=" + URLEncoder.encode( (String) screenInfo.get("FACILITY_ID"));
    url += "&file_type=" + URLEncoder.encode( (String) screenInfo.get("FILE_TYPE"));
    url += "&method=" + URLEncoder.encode( (String) screenInfo.get("METHOD"));
    url += "&user_id=" + URLEncoder.encode( (String) screenInfo.get("USER_ID"));
    return url;
  }

  // changes by dmitriy
  public static String getNewChemTrackUrl(String myClient, Hashtable screenInfo) {
    String url = "";
    url += "client=" + URLEncoder.encode(myClient.toString());
    url += "&which_screen=" + URLEncoder.encode( (String) screenInfo.get("CURRENT_SCREEN"));
    url += "&requestor=" + URLEncoder.encode( (String) screenInfo.get("REQUESTOR"));
    url += "&approver=" + URLEncoder.encode( (String) screenInfo.get("APPROVER"));
    url += "&searchText=" + URLEncoder.encode( (String) screenInfo.get("SEARCH_TEXT"));
    url += "&facId=" + URLEncoder.encode( (String) screenInfo.get("FACILITY_ID"));
    url += "&workArea=" + URLEncoder.encode( (String) screenInfo.get("WORK_AREA"));
    url += "&requestId=" + URLEncoder.encode( (String) screenInfo.get("REQUEST_ID"));
    url += "&showAllReq=" + URLEncoder.encode( (String) screenInfo.get("SHOW_ALL_REQUESTS"));

    url += "&draft=" + URLEncoder.encode( (String) screenInfo.get("DRAFT"));
    url += "&neParNum=" + URLEncoder.encode( (String) screenInfo.get("NEW_PART_NUMBER"));
    url += "&newGroup=" + URLEncoder.encode( (String) screenInfo.get("NEW_GROUP"));
    url += "&penAppro=" + URLEncoder.encode( (String) screenInfo.get("PENDING_APPROVAL"));
    url += "&reject=" + URLEncoder.encode( (String) screenInfo.get("REJECT"));
    url += "&newMat=" + URLEncoder.encode( (String) screenInfo.get("NEW_MATERIAL"));
    url += "&newSize=" + URLEncoder.encode( (String) screenInfo.get("NEW_SIZE"));
    url += "&newApp=" + URLEncoder.encode( (String) screenInfo.get("NEW_APPROVAL"));
    url += "&approvedC=" + URLEncoder.encode( (String) screenInfo.get("APPROVED_C"));
    url += "&banned=" + URLEncoder.encode( (String) screenInfo.get("BANNED"));

    url += "&reqName=" + URLEncoder.encode( (String) screenInfo.get("REQUESTOR_NAME"));
    url += "&approverName=" + URLEncoder.encode( (String) screenInfo.get("APPROVER_NAME"));

    url += "&file_type=" + URLEncoder.encode( (String) screenInfo.get("FILE_TYPE"));
    url += "&method=" + URLEncoder.encode( (String) screenInfo.get("METHOD"));
    url += "&user_id=" + URLEncoder.encode( (String) screenInfo.get("USER_ID"));
    //Nawaz 01-17-02
    url += "&fac_name=" + URLEncoder.encode( (String) screenInfo.get("FACILITY_NAME"));
    url += "&worka_name=" + URLEncoder.encode( (String) screenInfo.get("WORK_AREA_NAME"));
    url += "&status_sel=" + URLEncoder.encode( (String) screenInfo.get("STATUS_SELECTION"));
    url += "&need_approval=" + URLEncoder.encode( (String) screenInfo.get("NEED_APPROVAL"));

    return url;
  }

  public static String getOrderTrackUrl(String myClient, Hashtable screenInfo) {
    String url = "";
    url += "client=" + URLEncoder.encode(myClient.toString());
    url += "&which_screen=" + URLEncoder.encode( (String) screenInfo.get("CURRENT_SCREEN"));
    url += "&requestor=" + URLEncoder.encode( (String) screenInfo.get("REQUESTOR"));
    url += "&requestorName=" + URLEncoder.encode( (String) screenInfo.get("REQUESTOR_NAME"));
    url += "&needApproval=" + URLEncoder.encode( (String) screenInfo.get("NEED_MY_APPROVAL"));
    url += "&facId=" + URLEncoder.encode( (String) screenInfo.get("FACILITY"));
    url += "&workArea=" + URLEncoder.encode( (String) screenInfo.get("WORK_AREA"));
    url += "&searchText=" + URLEncoder.encode( (String) screenInfo.get("SEARCH_TEXT"));
    url += "&searchType=" + URLEncoder.encode( (String) screenInfo.get("SEARCH_TYPE"));
    url += "&searchContent=" + URLEncoder.encode( (String) screenInfo.get("SEARCH_CONTENT"));
    url += "&any=" + URLEncoder.encode( (String) screenInfo.get("STATUS_ANY"));
    url += "&draft=" + URLEncoder.encode( (String) screenInfo.get("STATUS_DRAFT"));
    url += "&open=" + URLEncoder.encode( (String) screenInfo.get("STATUS_OPEN"));
    url += "&archived=" + URLEncoder.encode( (String) screenInfo.get("STATUS_ARCHIVED"));
    if ("T".equalsIgnoreCase( (String) screenInfo.get("STATUS_ARCHIVED"))) {
      url += "&days=" + URLEncoder.encode( (String) screenInfo.get("DAYS"));
    }
    url += "&cancel=" + URLEncoder.encode( (String) screenInfo.get("STATUS_CANCELLED"));
    url += "&critical=" + URLEncoder.encode( (String) screenInfo.get("STATUS_CRIT_ONLY"));
    url += "&file_type=" + URLEncoder.encode( (String) screenInfo.get("FILE_TYPE"));
    url += "&method=" + URLEncoder.encode( (String) screenInfo.get("METHOD"));
    url += "&user_id=" + URLEncoder.encode( (String) screenInfo.get("USER_ID"));
    url += "&facility_name=" + URLEncoder.encode( (String) screenInfo.get("FACILITY_NAME"));
    url += "&workarea_name=" + URLEncoder.encode( (String) screenInfo.get("WORK_AREA_NAME"));

    return url;
  }

  public static String getMaterialRequestUrl(String myClient, Hashtable screenInfo) {
    String url = "";
    url += "client=" + URLEncoder.encode(myClient.toString());
    url += "&which_screen=" + URLEncoder.encode( (String) screenInfo.get("CURRENT_SCREEN"));
    url += "&accnt_sys_id=" + URLEncoder.encode( (String) screenInfo.get("ACCOUNT_SYS_ID"));
    url += "&request_id=" + URLEncoder.encode( (String) screenInfo.get("REQUEST_ID"));
    url += "&file_type=" + URLEncoder.encode( (String) screenInfo.get("FILE_TYPE"));
    url += "&method=" + URLEncoder.encode( (String) screenInfo.get("METHOD"));
    url += "&user_id=" + URLEncoder.encode( (String) screenInfo.get("USER_ID"));

    return url;
  }

  //Nawaz 01-14-02
  public static String getInventoryUrl(String myClient, Hashtable screenInfo) {
    String url = "";
    url += "client=" + URLEncoder.encode(myClient.toString());
    url += "&which_screen=" + URLEncoder.encode( (String) screenInfo.get("CURRENT_SCREEN"));
    url += "&facility_id=" + URLEncoder.encode( (String) screenInfo.get("FACILITY_ID"));
    url += "&warehouse=" + URLEncoder.encode( (String) screenInfo.get("WAREHOUSE"));
    url += "&search_text=" + URLEncoder.encode( (String) screenInfo.get("SEARCH_TEXT"));
    url += "&on_hand=" + URLEncoder.encode( (String) screenInfo.get("ON_HAND"));
    url += "&on_order=" + URLEncoder.encode( (String) screenInfo.get("ON_ORDER"));
    url += "&exp_date=" + URLEncoder.encode( (String) screenInfo.get("EXP_VAL"));
    url += "&exp_2_date=" + URLEncoder.encode( (String) screenInfo.get("EXP2_VAL"));
    url += "&by_date=" + URLEncoder.encode( (String) screenInfo.get("PROM_VAL"));
    url += "&all_parts=" + URLEncoder.encode( (String) screenInfo.get("ALL_PARTS"));
    url += "&file_type=" + URLEncoder.encode( (String) screenInfo.get("FILE_TYPE"));
    url += "&method=" + URLEncoder.encode( (String) screenInfo.get("METHOD"));
    url += "&user_id=" + URLEncoder.encode( (String) screenInfo.get("USER_ID"));

    url += "&fac_bol=" + URLEncoder.encode( (String) screenInfo.get("FAC_BOL"));
    url += "&ware_bol=" + URLEncoder.encode( (String) screenInfo.get("WARE_BOL"));
    url += "&exp_bol=" + URLEncoder.encode( (String) screenInfo.get("EXP_BOL"));
    url += "&prom_bol=" + URLEncoder.encode( (String) screenInfo.get("PROM_BOL"));
    url += "&on_hold_bol=" + URLEncoder.encode( (String) screenInfo.get("ON_HOLD_BOL"));
    url += "&on_order_bol=" + URLEncoder.encode( (String) screenInfo.get("ON_ORDER_BOL"));
    url += "&user_fac=" + URLEncoder.encode( (String) screenInfo.get("USER_FAC"));
    url += "&item=" + URLEncoder.encode( (String) screenInfo.get("ITEM"));
    url += "&facility_name=" + URLEncoder.encode( (String) screenInfo.get("FACILITY_NAME"));
    url += "&workarea_name=" + URLEncoder.encode( (String) screenInfo.get("WORK_AREA_NAME"));
    //Nawaz 01-30-02
    url += "&order_byinven=" + URLEncoder.encode( (String) screenInfo.get("ORDER_BY"));

    return url;
  }

  public static String getUserProfileUrl(String myClient, Hashtable screenInfo) {
    String url = "";
    url += "client=" + URLEncoder.encode(myClient.toString());
    url += "&which_screen=" + URLEncoder.encode( (String) screenInfo.get("CURRENT_SCREEN"));
    url += "&user_id_profile=" + URLEncoder.encode( (String) screenInfo.get("USER_ID_PROFILE"));
    url += "&browser=" + URLEncoder.encode( (String) screenInfo.get("BROWSER"));
    url += "&file_type=" + URLEncoder.encode( (String) screenInfo.get("FILE_TYPE"));
    url += "&method=" + URLEncoder.encode( (String) screenInfo.get("METHOD"));
    url += "&user_id=" + URLEncoder.encode( (String) screenInfo.get("USER_ID"));

    return url;
  }

  public static String getInventoryFloatUrl(String myClient, Hashtable screenInfo) {
    String url = "";
    url += "client=" + URLEncoder.encode(myClient.toString());
    url += "&which_screen=" + URLEncoder.encode( (String) screenInfo.get("CURRENT_SCREEN"));
    url += "&facility_id=" + URLEncoder.encode( (String) screenInfo.get("FACILITY_ID"));
    url += "&hub=" + URLEncoder.encode( (String) screenInfo.get("HUB"));
    url += "&item_id=" + URLEncoder.encode( (String) screenInfo.get("ITEM_ID"));
    url += "&file_type=" + URLEncoder.encode( (String) screenInfo.get("FILE_TYPE"));
    url += "&method=" + URLEncoder.encode( (String) screenInfo.get("METHOD"));
    url += "&user_id=" + URLEncoder.encode( (String) screenInfo.get("USER_ID"));

    return url;
  }

  public static String getWasteOrderTrackUrl(String myClient, Hashtable screenInfo) {
    String url = "";
    url += "client=" + URLEncoder.encode(myClient.toString());
    url += "&which_screen=" + URLEncoder.encode( (String) screenInfo.get("CURRENT_SCREEN"));
    url += "&facility_id=" + URLEncoder.encode( (String) screenInfo.get("FACILITY_ID"));
    url += "&storage_area=" + URLEncoder.encode( (String) screenInfo.get("STORAGE_AREA"));
    url += "&company_name=" + URLEncoder.encode( (String) screenInfo.get("VENDOR_NAME"));
    //url += "&vendor_id="+URLEncoder.encode((String)screenInfo.get("VENDOR_ID"));
    url += "&after=" + URLEncoder.encode( (String) screenInfo.get("AFTER"));
    url += "&before=" + URLEncoder.encode( (String) screenInfo.get("BEFORE"));
    url += "&order=" + URLEncoder.encode( (String) screenInfo.get("WASTE_ORDER"));
    url += "&copy_1=" + URLEncoder.encode( (String) screenInfo.get("COPY_1"));
    url += "&cod=" + URLEncoder.encode( (String) screenInfo.get("COD"));
    url += "&generate=" + URLEncoder.encode( (String) screenInfo.get("GENERATED"));
    url += "&submit=" + URLEncoder.encode( (String) screenInfo.get("SUBMITTED"));
    url += "&cancel=" + URLEncoder.encode( (String) screenInfo.get("CANCELLED"));
    url += "&pick_up=" + URLEncoder.encode( (String) screenInfo.get("PICKUP"));
    url += "&invoice=" + URLEncoder.encode( (String) screenInfo.get("INVOICED"));
    url += "&file_type=" + URLEncoder.encode( (String) screenInfo.get("FILE_TYPE"));
    url += "&method=" + URLEncoder.encode( (String) screenInfo.get("METHOD"));
    url += "&user_id=" + URLEncoder.encode( (String) screenInfo.get("USER_ID"));

    return url;
  }

  public static String getWasteManagementUrl(String myClient, Hashtable screenInfo) {
    String url = "";
    url += "client=" + URLEncoder.encode(myClient.toString());
    url += "&which_screen=" + URLEncoder.encode( (String) screenInfo.get("CURRENT_SCREEN"));
    url += "&facility_id=" + URLEncoder.encode( (String) screenInfo.get("FACILITY_ID"));
    url += "&storage_area=" + URLEncoder.encode( (String) screenInfo.get("STORAGE_AREA"));
    url += "&vendor_id=" + URLEncoder.encode( (String) screenInfo.get("VENDOR_ID"));
    url += "&vendor_name=" + URLEncoder.encode( (String) screenInfo.get("VENDOR_NAME"));
    url += "&requestor_name=" + URLEncoder.encode( (String) screenInfo.get("REQUESTOR_NAME"));
    url += "&file_type=" + URLEncoder.encode( (String) screenInfo.get("FILE_TYPE"));
    url += "&method=" + URLEncoder.encode( (String) screenInfo.get("METHOD"));
    url += "&user_id=" + URLEncoder.encode( (String) screenInfo.get("USER_ID"));

    return url;
  }

  public static String getWasteTrackWinUrl(String myClient, Hashtable screenInfo) {
    String url = "";
    url += "client=" + URLEncoder.encode(myClient.toString());
    url += "&which_screen=" + URLEncoder.encode( (String) screenInfo.get("CURRENT_SCREEN"));
    url += "&facility_id=" + URLEncoder.encode( (String) screenInfo.get("FACILITY_ID"));
    url += "&requestor_name=" + URLEncoder.encode( (String) screenInfo.get("REQUESTOR_NAME"));
    url += "&transferred=" + URLEncoder.encode( (String) screenInfo.get("TRANSFERRED"));
    url += "&days=" + URLEncoder.encode( (String) screenInfo.get("DAYS_SINCE_PICKUP"));
    url += "&file_type=" + URLEncoder.encode( (String) screenInfo.get("FILE_TYPE"));
    url += "&method=" + URLEncoder.encode( (String) screenInfo.get("METHOD"));
    url += "&user_id=" + URLEncoder.encode( (String) screenInfo.get("USER_ID"));

    return url;
  }

  public static String getWasteCatalogUrl(String myClient, Hashtable screenInfo) {
    String url = "";
    url += "client=" + URLEncoder.encode(myClient.toString());
    url += "&which_screen=" + URLEncoder.encode( (String) screenInfo.get("CURRENT_SCREEN"));
    url += "&facility_id=" + URLEncoder.encode( (String) screenInfo.get("FACILITY_ID"));
    url += "&requestor_name=" + URLEncoder.encode( (String) screenInfo.get("REQUESTOR_NAME"));
    url += "&generation_pt=" + URLEncoder.encode( (String) screenInfo.get("GENERATION_PT"));
    url += "&pre_transfer=" + URLEncoder.encode( (String) screenInfo.get("PREVIOUS_TRANSFER"));
    url += "&search_text=" + URLEncoder.encode( (String) screenInfo.get("SEARCH_TEXT"));
    url += "&file_type=" + URLEncoder.encode( (String) screenInfo.get("FILE_TYPE"));
    url += "&method=" + URLEncoder.encode( (String) screenInfo.get("METHOD"));
    url += "&user_id=" + URLEncoder.encode( (String) screenInfo.get("USER_ID"));

    return url;
  }

  //Nawaz 01-14-02
  public static String getCatalogUrl(String myClient, Hashtable screenInfo) {
    String url = "";
    url += "client=" + URLEncoder.encode(myClient.toString());
    url += "&which_screen=" + URLEncoder.encode( (String) screenInfo.get("CURRENT_SCREEN"));
    url += "&facility_id=" + URLEncoder.encode( (String) screenInfo.get("FACILITY_ID"));
    url += "&work_area=" + URLEncoder.encode( (String) screenInfo.get("WORK_AREA"));
    url += "&search_text=" + URLEncoder.encode( (String) screenInfo.get("SEARCH_TEXT"));
    url += "&file_type=" + URLEncoder.encode( (String) screenInfo.get("FILE_TYPE"));
    url += "&method=" + URLEncoder.encode( (String) screenInfo.get("METHOD"));
    url += "&user_id=" + URLEncoder.encode( (String) screenInfo.get("USER_ID"));
    url += "&all_fac=" + URLEncoder.encode( (String) screenInfo.get("ALL_FAC"));
    url += "&eng_eval_cat=" + URLEncoder.encode( (String) screenInfo.get("ENG_EVAL_CAT"));
    url += "&active=" + URLEncoder.encode( (String) screenInfo.get("ACTIVE"));
    url += "&approved=" + URLEncoder.encode( (String) screenInfo.get("APPROVED"));
    url += "&catalogType=" + URLEncoder.encode( (String) screenInfo.get("CATALOG_TYPE"));
    url += "&approved=" + URLEncoder.encode( (String) screenInfo.get("APPROVED"));
    url += "&facility_name=" + URLEncoder.encode( (String) screenInfo.get("FACILITY_NAME"));
    url += "&workarea_name=" + URLEncoder.encode( (String) screenInfo.get("WORK_AREA_NAME"));

    return url;
  }

  /*    public static void goURL(CmisApp cmis,String matID,String specID,String client,int itemID,int kind){
       String q = new String();
       URLGrab U;
       // material ID
       q = new String("id=m" + matID);
       // spec ID
       if (specID ==null || specID.length() == 0){
         q = q + "&spec=";
       } else {
         q = q + "&spec=" + specID;
       }
       // client
       q = q + "&cl=" + client;
       // item
       q = q + "&i=" + itemID;
       q = q.trim().replace(' ','+');
       U = new URLGrab(kind,q,cmis);
     }
   */
  public static void setTabbedPaneForegroundColor(JTabbedPane jtp) {
    for (int i = 0; i < jtp.getTabCount(); i++) {
      if (i == jtp.getSelectedIndex()) {
        jtp.setForegroundAt(i, Color.black);
      } else {
        jtp.setForegroundAt(i, Color.lightGray);
      }
    }
  }

  public static void goHelp(CmisApp p) {
    try {
      // From the stand alone application
      String client = p.getResourceBundle().getString("CLIENT_INITIALS").toString();
      if ("Boeing".equalsIgnoreCase(client)) {
       client = "ula";
      }
      String url = new String("/tcmIS/help/" + client.toLowerCase());
      url += "/";
      new URLCall(url, p);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public static void print(CmisApp p, String m) {
    try {
      if (p.getDebugWriter() != null) {
        p.getDebugWriter().println(m);
        p.getDebugWriter().flush();
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public static int sendPossData(JFrame fakeFrame, String path, String servlet, CmisApp p) {
    // Try to see if they have classes directory
    String msg = null;
    int intR = 0;
    try {
      File test = new File(path);
      if (!test.exists()) {
        return ClientHelpObjs.FILE_NOT_FOUND;
      }

      if (!test.isFile()) {
        return ClientHelpObjs.CAN_NOT_READ_FILE;
      }

      if (test.length() <= 0) {
        return ClientHelpObjs.FILE_EMPTY;
      }

      BufferedReader in = new BufferedReader(new FileReader(path));

      Vector sendV = new Vector();
      String line = null;
      while ( (line = in.readLine()) != null) {
        sendV.addElement(new String(line));
      }

      in.close();

      TcmisHttpConnection cgi = new TcmisHttpConnection(servlet, p);
      Hashtable query = new Hashtable();
      query.put("FILE", sendV); //String, vector
      // Time-out
      ClientHelpObjs_TimeThread tt = new ClientHelpObjs_TimeThread(fakeFrame, "Connection time-out, please try again");
      tt.start();
      Hashtable result = cgi.getResultHash(query);
      tt.stop();
      if (result.containsKey("RESULTS")) {
        msg = (String) result.get("RESULTS");
        intR = ClientHelpObjs.FILE_SENT_OK;
        // delete file
        if (msg.indexOf("successfull") > -1) {
          File dd = new File(path);
          if (!dd.delete()) {
            GenericDlg err = new GenericDlg(fakeFrame, "tcmIS/POSS File Error", true);
            err.setMsg("tcmIS could not delete the input file, please do it manually.\nInput File:\n     " + path);
            err.setVisible(true);
          }
        }
      } else {
        intR = ClientHelpObjs.POSS_SERVER_ERROR;
        msg = "Connection error, please try again.";
      }
    } catch (Exception e) {
      e.printStackTrace();
      msg = e.getMessage();
      intR = ClientHelpObjs.POSS_SERVER_ERROR;
    }

    GenericDlg err = new GenericDlg(fakeFrame, "tcmIS/POSS insert Result", true);
    err.setMsg(msg);
    err.setVisible(true);
    return intR;
  }

  public static String audit(Component[] comp, String[] value) {
    for (int i = 0; i < comp.length; i++) {
      String res = ClientHelpObjs.audit(comp[i], value[i]);
      if (res != null) {
        return res;
      }
    }
    return null;
  }

  public static String audit(Hashtable hash) { // key=comp value=value
    Enumeration E;
    for (E = hash.keys(); E.hasMoreElements(); ) {
      Object comp = E.nextElement();
      String res = ClientHelpObjs.audit( (Component) comp, (String) hash.get(comp));
      if (res != null) {
        return res;
      }
    }
    return null;
  }

  public static String audit(Component comp, String value) {
    String name = comp.getName();
    //System.out.println("Name:"+name);

    if (name == null) {
      return null;
    }
    StringTokenizer st = new StringTokenizer(name, "/");
    String n = (st.hasMoreTokens() ? st.nextToken() : null);
    //System.out.println("token:"+n);

    //trong 3/13/00
    //System.out.println("@@@@@@@@@ : " + n + " - " + n.equalsIgnoreCase("netwtT"));
    if (n.equalsIgnoreCase("netwtT")) {
      return null;
    }

    int type = (st.hasMoreTokens() ? Integer.parseInt(st.nextToken()) : 0);
    String msg = (st.hasMoreTokens() ? st.nextToken() : null);
    if (type == 0) {
      return null;
    }
    if (type == ClientHelpObjs.AUDIT_TEXT_NON_BLANK) {
      if (BothHelpObjs.makeBlankFromNull(value).trim().length() == 0) {
        return msg;
      }
    }
    if (type == ClientHelpObjs.AUDIT_NUMERIC) {
      if (!BothHelpObjs.isNumericString(value)) {
        return msg;
      }
    }
    if (type == ClientHelpObjs.AUDIT_NUMERIC_NON_NEG) {
      if (!BothHelpObjs.isNumericString(value) || BothHelpObjs.isNegative(value)) {
        return msg;
      }
    }
    if (type == ClientHelpObjs.AUDIT_NUMERIC_NON_ZERO) {
      if (!BothHelpObjs.isNumericString(value) || BothHelpObjs.isZero(value)) {
        return msg;
      }
    }

    return null;
  }

  public static Hashtable buildCompValueHash(Component[] comp) {
    Hashtable hash = new Hashtable();
    for (int i = 0; i < comp.length; i++) {
      String value = new String("");
      if (comp[i] instanceof JTextField) {
        JTextField tmp = (JTextField) comp[i];
        value = tmp.getText();
      } else if (comp[i] instanceof JTextArea) {
        JTextArea tmp = (JTextArea) comp[i];
        value = tmp.getText();
      } else if (comp[i] instanceof JComboBox) {
        JComboBox tmp = (JComboBox) comp[i];
        value = (String) tmp.getSelectedItem();
        //System.out.println("------- in clienthelpobj : " + tmp.getName() + " -- " + comp[i]);
      }
      //System.out.println("Object:"+comp[i].getName()+":Value:"+value);
      if (comp[i].getName() != null && value != null) {
        hash.put(comp[i], value);
      }
    }

    return hash;

  }

}

class ClientHelpObjs_ChoiceThread extends Thread {
  Component comp = null;
  Vector v = null;

  ClientHelpObjs_ChoiceThread(Component c, Vector vv) {
    super("ChoiceLoadThread");
    comp = c;
    v = vv;
  }

  public void run() {
    synchronized (comp) {
      if (comp instanceof JComboBox) {
        ClientHelpObjs.loadChoiceFromVector( (JComboBox) comp, v);
      } else {
        ClientHelpObjs.loadChoiceFromVector( (Choice) comp, v);
      }
    }
  }
}

class ClientHelpObjs_TimeThread extends Thread {

  String msg = null;
  JFrame fake = null;

  ClientHelpObjs_TimeThread(JFrame fakeFrame, String m) {
    super("TimeOutThread");
    msg = m;
    fake = fakeFrame;
  }

  public void run() {
    try {
      this.sleep(60000);
      // got here means kill!!
      GenericDlg err = new GenericDlg(fake, "tcmIS/POSS insert Result", true);
      err.setMsg(msg);
      err.setVisible(true);
      System.exit(0);
    } catch (Exception e) {
      System.out.println("Timeout thread could not sleep");
    }
    ;
  }
}
