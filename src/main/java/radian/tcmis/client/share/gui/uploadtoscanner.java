package radian.tcmis.client.share.gui;

//Title:        Your Product Name
//Version:
//Copyright:    Copyright (c) 1998
//Author:       Your Name
//Company:      Your Company
//Description:  Your description

import java.awt.event.ActionEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import com.borland.jbcl.layout.XYLayout;
import radian.tcmis.both1.helpers.resource.ResourceLoader;
import radian.tcmis.client.share.helpers.ClientHelpObjs;
import radian.tcmis.client.share.helpers.TableSorter;
import java.awt.*;
import java.lang.Process;
import java.io.*;
import java.util.*;
import radian.tcmis.client.share.httpCgi.*;

//public class uploadtoscanner extends JDialog {
public class uploadtoscanner extends JPanel {

  TableSorter sorter=new TableSorter();

  JPanel panel1=new JPanel();

  JButton okB=new JButton();

  BorderLayout borderLayout3=new BorderLayout();

  String prNumber;

  String status;

  CmisApp cmis;

  RequestsWin requestWin;

  JPanel jPanel6=new JPanel();

  XYLayout xYLayout4=new XYLayout();

  BorderLayout borderLayout4=new BorderLayout();

  JPanel jPanel7=new JPanel();

  JPanel bottomP=new JPanel();

  GridBagLayout gridBagLayout1=new GridBagLayout();

  XYLayout xYLayout2=new XYLayout();

  XYLayout xYLayout3=new XYLayout();

  FlowLayout flowLayout1=new FlowLayout();

  BorderLayout borderLayout1=new BorderLayout();

  StaticJLabel commentL=new StaticJLabel();

  GridBagLayout gridBagLayout2=new GridBagLayout();

  StaticJLabel statusL=new StaticJLabel();
  StaticJLabel statusL2=new StaticJLabel();
  StaticJLabel statusL3=new StaticJLabel();
  StaticJLabel statusL4=new StaticJLabel();
  StaticJLabel statusL5=new StaticJLabel();
  StaticJLabel statusL6=new StaticJLabel();
  StaticJLabel statusL7=new StaticJLabel();

  JTextArea reasonT=new JTextArea();

  BorderLayout borderLayout2=new BorderLayout();
  boolean foundfile = false;
  /*
     public uploadtoscanner(JFrame fr) {
    super(fr,"Upload to Scanner", false);
    try  {
      jbInit();
      pack();
      this.setSize(new Dimension(450, 300));
      this.setResizable(false);
    }
    catch(Exception ex) {
      ex.printStackTrace();
    }
     }*/

  public uploadtoscanner( CmisApp cmis )
  {
    try
    {
      this.cmis=cmis;
      jbInit();
    }
    catch ( Exception ex )
    {
      ex.printStackTrace();
    }
  }


  public void setParent( CmisApp c )
  {
    cmis=c;
  }

  public void loadIt()
  {
    uploadtoscannerLoadThread x=new uploadtoscannerLoadThread( this );
    x.start();
  }

  void jbInit()
     throws Exception
  {
    /*String wDir = new String(System.getProperty("user.dir"));
         String imagesDir = wDir + System.getProperty("file.separator") + (new ClientResourceBundle()).getString("LOCAL_IMAGES_DIR") ;
     */
    ImageIcon ss=ResourceLoader.getImageIcon( "images/button/ok.gif" );
    okB.setIcon( ss );
    ss=ResourceLoader.getImageIcon( "images/button/cancel.gif" );
    panel1.setLayout( borderLayout4 );
    okB.setText( "OK" );
    okB.addActionListener( new java.awt.event.ActionListener()
    {
      public void actionPerformed( ActionEvent e )
      {
        okB_actionPerformed( e );
      }
    } );

    panel1.setMaximumSize( new Dimension( 520,380 ) );
    panel1.setMinimumSize( new Dimension( 520,380 ) );
    panel1.setPreferredSize( new Dimension( 520,380 ) );
    panel1.setBorder( ClientHelpObjs.groupBox( "" ) );

    jPanel6.setLayout( gridBagLayout1 );
    jPanel7.setLayout( gridBagLayout2 );
    jPanel7.setBorder( ClientHelpObjs.groupBox( "" ) );

    bottomP.setLayout( borderLayout1 );
    statusL.setFont( new java.awt.Font( "SansSerif",1,12 ) );
    statusL.setText( "1. Place scanner in cradle." );
    statusL2.setFont( new java.awt.Font( "SansSerif",1,12 ) );
    statusL2.setText( "2. Turn on scanner." );
    statusL3.setFont( new java.awt.Font( "SansSerif",1,12 ) );
    statusL3.setText( "3. Logon to the terminal if necessary." );
    statusL4.setFont( new java.awt.Font( "SansSerif",1,12 ) );
    statusL4.setText( "4. Press CLR until the MAIN MENU is displayed." );
    statusL5.setFont( new java.awt.Font( "SansSerif",1,12 ) );
    statusL5.setText( "5. Select File Xfer from the main menu." );
    statusL6.setFont( new java.awt.Font( "SansSerif",1,12 ) );
    statusL6.setText( "6. Press Enter on the scanner." );
    statusL7.setFont( new java.awt.Font( "SansSerif",1,12 ) );
    statusL7.setText( "7. Click the OK button when the scanner displays 'Waiting...'" );
    reasonT.setLineWrap( true );
    reasonT.setPreferredSize( new Dimension( 350,100 ) );
    reasonT.setWrapStyleWord( true );
    reasonT.setDoubleBuffered( true );
    reasonT.setMaximumSize( new Dimension( 400,100 ) );
    reasonT.setMinimumSize( new Dimension( 350,100 ) );
    reasonT.setEditable( false );
    //getContentPane().add(panel1);
    this.setLayout( borderLayout2 );
    this.add( panel1,BorderLayout.CENTER );
    panel1.add( jPanel6,BorderLayout.CENTER );
    jPanel7.add( statusL, new GridBagConstraints(0, 0, 1, 1, 1.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(5, 5, 6, 5), 0, 0) );
    jPanel7.add( statusL2,new GridBagConstraints( 0,1,1,1,1.0,0.0
           ,GridBagConstraints.WEST,GridBagConstraints.NONE,new Insets( 5,5,6,5 ),0,0 ) );
    jPanel7.add( statusL3,new GridBagConstraints( 0,2,1,1,1.0,0.0
           ,GridBagConstraints.WEST,GridBagConstraints.NONE,new Insets( 5,5,6,5 ),0,0 ) );
    jPanel7.add( statusL4,new GridBagConstraints( 0,3,1,1,1.0,0.0
           ,GridBagConstraints.WEST,GridBagConstraints.NONE,new Insets( 5,5,6,5 ),0,0 ) );
    jPanel7.add( statusL5,new GridBagConstraints( 0,4,1,1,1.0,0.0
           ,GridBagConstraints.WEST,GridBagConstraints.NONE,new Insets( 5,5,6,5 ),0,0 ) );
    jPanel7.add( statusL6,new GridBagConstraints( 0,5,1,1,1.0,0.0
           ,GridBagConstraints.WEST,GridBagConstraints.NONE,new Insets( 5,5,6,5 ),0,0 ) );
    jPanel7.add( statusL7,new GridBagConstraints( 0,6,1,1,1.0,0.0
           ,GridBagConstraints.WEST,GridBagConstraints.NONE,new Insets( 5,5,6,5 ),0,0 ) );
    jPanel6.add( bottomP,new GridBagConstraints( 1,1,GridBagConstraints.REMAINDER,GridBagConstraints.REMAINDER,0.0,0.0
                                                 ,GridBagConstraints.SOUTH,GridBagConstraints.NONE,new Insets( 5,5,5,5 ),0,0 ) );
    bottomP.add( okB,BorderLayout.WEST );
    jPanel6.add( commentL,new GridBagConstraints( 0,0,2,1,0.0,0.0
                                                  ,GridBagConstraints.CENTER,GridBagConstraints.BOTH,new Insets( 5,15,20,0 ),0,0 ) );
    jPanel6.add( jPanel7,new GridBagConstraints( 1,1,1,1,1.0,1.0
                                                 ,GridBagConstraints.NORTH,GridBagConstraints.BOTH,new Insets( 10,15,50,15 ),0,0 ) );
    this.validate();
  }

  void loadItAction()
  {
    System.out.println( "Here" );
  }

  void closeWindow()
  {
    requestWin.financeDlgOpen=false;
    this.setVisible( false );
  }


  void okB_actionPerformed( ActionEvent e )
  {
    ClientHelpObjs.playSound( ClientHelpObjs.SOUND_BUTTON_ACTION );
    runThread();
  }

  void runThread()
  {
    uploadtoscannerThread st=new uploadtoscannerThread( this );
    st.start();
  }

  void rerunuploadstuff()
  {
    douploadstuff();
  }

  //void okB_actionPerformed( ActionEvent e )
  public void douploadstuff()
  {
    CursorChange.setCursor( this,Cursor.WAIT_CURSOR );
    ClientHelpObjs.setEnabledContainer( this,false );
    //this.okB.setEnabled(false);
    boolean scrpitresult = true;

    //this.statusL.setText( "Downloading Data to Server Please Wait..." );

    StringBuffer uploadfile=new StringBuffer();
    String linefeedd="";
    linefeedd+= ( char ) ( 13 );
    linefeedd+= ( char ) ( 10 );

    uploadfile.append( "FD|LINKERR.TXT" + linefeedd );
    //uploadfile.append( "FD|C:\\SCANNERDOWNLOAD\\DONE.TXT" + linefeedd );
    uploadfile.append( "FD|DONE.TXT" + linefeedd );
    uploadfile.append( "NO|001|FR|C.DAT|countdata.csv" + linefeedd );
    uploadfile.append( "IF|&99|=|0|+1|ERROR3" + linefeedd );
    uploadfile.append( "NO|001|FR|D.DAT|errorcountdata.csv" + linefeedd );
    uploadfile.append( "IF|&99|=|0|+1|ERROR4" + linefeedd );
    uploadfile.append( "SK|QUIT" + linefeedd );
    uploadfile.append( "LB|ERROR3" + linefeedd );
    uploadfile.append( "FC|ERROR3.TXT|LINKERR.TXT" + linefeedd );
    uploadfile.append( "SK|QUIT" + linefeedd );
    uploadfile.append( "LB|ERROR4" + linefeedd );
    uploadfile.append( "FC|ERROR4.TXT|LINKERR.TXT" + linefeedd );
    uploadfile.append( "SK|QUIT" + linefeedd );
    uploadfile.append( "LB|ERROR6" + linefeedd );
    uploadfile.append( "FC|ERROR6.TXT|LINKERR.TXT" + linefeedd );
    uploadfile.append( "SK|QUITPCONLY" + linefeedd );
    uploadfile.append( "LB|QUIT" + linefeedd );
    uploadfile.append( "NO|001|QX|1" + linefeedd );
    uploadfile.append( "LB|QUITPCONLY" + linefeedd );
    //uploadfile.append( "FC|ERROR3.TXT|C:\\SCANNERDOWNLOAD\\DONE.TXT" + linefeedd );
    uploadfile.append( "FC|ERROR3.TXT|DONE.TXT" + linefeedd );
    uploadfile.append( "QX" + linefeedd );

    String file="";
    PrintStream ps=null;

    file="C:\\MCL\\Terminal\\mcllink\\mcllink.cmd";
    try
    {
      ps=new PrintStream( new FileOutputStream( file ),true );

      String contents="";
      contents+=uploadfile;
      byte outbuf[];
      outbuf=contents.getBytes();
      ps.write( outbuf );
      ps.close();
    }
    catch ( Exception writee )
    {
      writee.printStackTrace();
    }
    //call script to download file from server
    scrpitresult=calllinkexe();
    //wait if file done.txt is not found
    boolean doneFlag = false;
    Thread ct = Thread.currentThread();
    do {
      try {
        ct.sleep(1000);  //1 secs
        File f = new File("c:\\scannerdownload\\done.txt");
        doneFlag = f.exists();
      }catch (Exception ee) {
        doneFlag = false;
      }
    }while(!doneFlag);

    //continue with the rest of the process
    checkforerrfile();

    if (foundfile)
    {
      scrpitresult = false;
    }
    else
    {
      scrpitresult = true;
    }


    //String wDir=new String( System.getProperty( "user.dir" ) );
    //wDir=wDir + System.getProperty( "file.separator" );
    Hashtable DataH=new Hashtable();
    Vector uplodcounts=new Vector();
    boolean upresult=true;
    if ( scrpitresult )
    {
      try
      {
        // first change
        BufferedReader in=new BufferedReader( new FileReader( "c:\\scannerdownload\\countdata.csv" ) );
        String ln=new String();
        StringTokenizer st=null;
        while ( ( ln=in.readLine() ) != null )
        {
          //System.out.println("Line:"+ln);
          st=new StringTokenizer( ln,"," );
          int linecount=0;
          if ( st.countTokens() > 1 )
          {
            linecount++;
            int loopcount=0;
            DataH=new Hashtable();

            while ( st.hasMoreTokens() )
            {
              loopcount++;
              String tok=st.nextToken();
              if ( loopcount == 1 )
              {
                DataH.put( "BIN_ID",tok == null ? "" : tok.trim() );
              }
              else if ( loopcount == 2 )
              {
                DataH.put( "RECEIPT_ID",tok == null ? "" : tok.trim() );
              }
              else if ( loopcount == 3 )
              {
                DataH.put( "QTY",tok == null ? "" : tok.trim() );
              }
              else if ( loopcount == 4 )
              {
                DataH.put( "TIMESTAMP",tok == null ? "" : tok.trim() );
              }
              else if ( loopcount == 5 )
              {
                DataH.put( "PERSONNEL_ID",tok == null ? "" : tok.trim() );
              }
              else if ( loopcount == 6 )
              {
                DataH.put( "COMPANY_ID",tok == null ? "" : tok.trim() );
              }
            }
            uplodcounts.add( DataH );
          }
        }
        in.close();

        TcmisHttpConnection cgi=new TcmisHttpConnection( "ScannerUpload",cmis );
        Hashtable query=new Hashtable();
        Hashtable uploadresult=new Hashtable();

        query.put( "ACTION","DO_UPLOAD_UPDATE" );
        query.put( "USER_ID",cmis.getUserId() );
        query.put( "UPLOAD_DATA",uplodcounts );
        uploadresult=cgi.getResultHash( query );
        if ( uploadresult == null )
        {
          GenericDlg.showAccessDenied( cmis.getMain() );
          ClientHelpObjs.setEnabledContainer( this,true );
          return;
        }

        Hashtable uploadInfoH= ( Hashtable ) uploadresult.get( "UPLOAD_RESULT" );
        //System.out.println(uploadInfoH);
        upresult= ( ( Boolean ) uploadInfoH.get( "SUCCESS" ) ).booleanValue();

        if ( upresult )
        {
          GenericDlg g=new GenericDlg( cmis.getMain(),"Upload to Server",true );
          g.setMsg((String)uploadInfoH.get("MSG"));
          g.setVisible( true );
        }
        else
        {
          GenericDlg g=new GenericDlg( cmis.getMain(),"Upload to Server Error",true );
          g.setMsg( " Upload to Server Encountered some problems.\n Please contact the tech center at 512-519-3917 to get more details.\n Thanks." );
          g.setVisible( true );
        }

        //this.statusL.setText( "Please Set the Scanner on to the download option and press OK.." );
        CursorChange.setCursor( this,Cursor.DEFAULT_CURSOR );
        ClientHelpObjs.setEnabledContainer( this,true );
      }
      catch ( Exception efile )
      {
        efile.printStackTrace();
        GenericDlg.showAccessDenied( cmis.getMain() );
        ClientHelpObjs.setEnabledContainer( this,true );
        //this.statusL.setText( "Please Set the Scanner on to the download option and press OK.." );
        CursorChange.setCursor( this,Cursor.DEFAULT_CURSOR );
        return;
      }
    }
    else
    {
      GenericDlg g=new GenericDlg( cmis.getMain(),"Upload to Server Error",true );
      g.setMsg( " Upload to Server Encountered some problems.\n Please try again.\n Thanks." );
      g.setVisible( true );
      ClientHelpObjs.setEnabledContainer( this,true );
      //this.statusL.setText( "Please Set the Scanner on to the download option and press OK.." );
      CursorChange.setCursor( this,Cursor.DEFAULT_CURSOR );
    }
  }

  /*void testCu() {
    System.out.println("------------ got here");
    return test2();
  }
  boolean test2() {
    return false;
  }*/


  boolean calllinkexe()
  {
    boolean cllmcl=true;

  try
    {
      String osName=System.getProperty( "os.name" );
      String[] cmd=new String[3];
      System.out.println( osName );
      cmd[0]="cmd.exe";
      cmd[1]="/C";
      cmd[2]="C:\\MCL\\Terminal\\mcllink\\mcllink.exe";

      Runtime rt=Runtime.getRuntime();
      System.out.println( "Execing " + cmd[0] + " " + cmd[1]
                          + " " + cmd[2] );
      Process proc=rt.exec( cmd );
      // any error message?
      StreamGobbler errorGobbler=new StreamGobbler( proc.getErrorStream(),"ERROR" );

      // any output?
      StreamGobbler outputGobbler=new StreamGobbler( proc.getInputStream(),"OUTPUT" );

      // kick them off
      errorGobbler.start();
      outputGobbler.start();

      // any error???
      int exitVal=proc.waitFor();
      System.out.println( "ExitValue: " + exitVal );
      proc.destroy();
    }
    catch ( Throwable t )
    {
      t.printStackTrace();
      cllmcl=false;
    }
    return cllmcl;
  }

  void checkforerrfile()
  {
    boolean founderrfile=false;
    String erromsffromfile="";

    try
    {
      //Looking for the error file
      BufferedReader in=new BufferedReader( new FileReader( "c:\\scannerdownload\\LINKERR.TXT" ) );
      String ln=new String();
      StringTokenizer st=null;
      while ( ( ln=in.readLine() ) != null )
      {
        System.out.println( "Line:" + ln );
        erromsffromfile+=ln;
      }
      founderrfile=true;
      foundfile =true;
      in.close();
    }
    catch ( FileNotFoundException efile )
    {
      efile.printStackTrace();
    }
    catch ( IOException efile )
    {
      efile.printStackTrace();
    }

    if ( founderrfile )
    {
      TcmISMessageDlg cfd=new TcmISMessageDlg( cmis.getMain(),"TcmIS Messages",true );
      cfd.setMsg( erromsffromfile );
      cfd.noB.setText( "Cancel" );

      cfd.setSize( new Dimension( 450,200 ) );

      cfd.setVisible( true );
      if ( !cfd.getConfirmationFlag() )
      {
        ClientHelpObjs.setEnabledContainer( this,true );
        CursorChange.setCursor( this,Cursor.DEFAULT_CURSOR );
        //return founderrfile;
      }
      else
      {
        System.out.println("Calling Download Stuff again    ");
        calllinkexe();
        checkforerrfile();
        //return founderrfile;
      }
    }
    else
    {
      foundfile = false;
    }
    //return founderrfile;
  }

}


class uploadtoscannerThread extends Thread {
  uploadtoscanner parent;
  boolean runF = true;

  public uploadtoscannerThread (uploadtoscanner parent){
     super("uploadtoscannerThread");
     this.parent = parent;
  }

  public void run()
  {
    parent.douploadstuff();
  }
}

class StreamGobbler extends Thread
{
    InputStream is;
    String type;

    StreamGobbler(InputStream is, String type)
    {
        this.is = is;
        this.type = type;
    }

    public void run()
    {
        try
        {
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader br = new BufferedReader(isr);
            String line=null;
            while ( (line = br.readLine()) != null)
                System.out.println(type + ">" + line);
            } catch (IOException ioe)
              {
                ioe.printStackTrace();
              }
    }
}

class uploadtoscannerLoadThread extends Thread {
  uploadtoscanner parent;
  public uploadtoscannerLoadThread(uploadtoscanner parent){
     super("uploadtoscannerLoadThread");
     this.parent = parent;
  }
  public void run(){
     parent.loadItAction();
  }
}
