package radian.web.servlets.share.uploadfile;

import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;
import com.oreilly.servlet.MultipartRequest;

public class UploadFile extends HttpServlet {

  public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
    res.setContentType("text/html");
    PrintWriter out = res.getWriter();
    try {
      String fileName = "";
      String tmpPath = radian.web.tcmisResourceLoader.getpossfilepath();
      MultipartRequest multi = new MultipartRequest(req,tmpPath);
      boolean done = false;
      out.println("<HTML><HEAD><TITLE>UpLoad File</TITLE>"+
                  "<META http-equiv=Content-Type content=\"text/html; charset=iso-8859-1\">"+
                  "<LINK href=\"http://www.tcmis.com/images/buttons/tcmIS.ico\" rel=\"SHORTCUT ICON\"></LINK>"+
                  "<LINK href=\"/stylesheets/global.css\" type=text/css rel=stylesheet></LINK>"+
                  "<META http-equiv=Pragma content=no-cache>"+
                  "<META http-equiv=Expires content=-1></HEAD>"+
                  "<BODY><TABLE class=moveupmore width=\"100%\" border=0>"+
                  "<TBODY><TR vAlign=top>"+
                  "<TD width=200><IMG src=\"/images/tcmtitlegif.gif\" align=left border=0> </TD>"+
                  "<TD align=right><IMG src=\"/images/tcmistcmis32.gif\" align=right border=0> </TD>"+
                  "</TR></TBODY></TABLE>"+
                  "<TABLE class=moveup cellSpacing=0 cellPadding=0 width=\"100%\" border=0>"+
                  "<TBODY><TR>"+
                  "<TD class=heading align=left width=\"70%\" height=22><B>Upload Poss File</B>"+
                  "</TD><TD class=headingr align=right width=\"30%\" height=22>&nbsp;</TD>"+
                  "</TR></TBODY></TABLE>");
      out.println("<CENTER><H3>Data Loaded</H3>");
      Enumeration enumCollec = multi.getFileNames();
      while (enumCollec.hasMoreElements()) {
        String key = (String)enumCollec.nextElement();
        String name = multi.getFilesystemName(key);
        String type = multi.getContentType(key);
        File f = multi.getFile(key);
        out.println("<B>File name:</B> "+name);
        out.println("<BR><BR><B>File Type:</B> "+type);
        if (f != null) {
          out.println("<BR><BR><B>File Length:</B> "+f.length());
          if (f.length() > 0 && "Radian.txt".equalsIgnoreCase(name)) {
            done = true;
            Calendar cal = Calendar.getInstance();
            fileName = tmpPath+cal.get(Calendar.YEAR)+cal.get(Calendar.MONTH)+cal.get(Calendar.DAY_OF_MONTH)+cal.get(Calendar.HOUR)+cal.get(Calendar.MINUTE)+cal.get(Calendar.SECOND)+name;
            f.renameTo(new File(fileName));
          }
        }
      }
      if (done) {
        POSSThread pt = new POSSThread(fileName);
        pt.start();

        out.println("<H4>Upload file completed.</H4>");
      }else {
        out.println("<H5>The file you entered does not contain any data or it is the wrong file.</H5>");
        out.println("<H5>Please check the file name and try again.</H5>");
        out.println("<H5>Click Back or click on link below:</H5>");
        out.println("<A HREF=\"https://www.tcmis.com/poss\">https://www.tcmis.com/poss</A>");
      }
    }catch (Exception e) {
      out.println("<PRE>");
      e.printStackTrace(out);
      out.println("</PRE>");
    }
    out.println("</CENTER></BODY></HTML>");
  }//end of method
}//end of class