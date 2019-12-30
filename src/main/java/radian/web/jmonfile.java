package radian.web;

//
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;

public class jmonfile
{

  public jmonfile()
  {

  }

  protected static long startTime;
  private static org.apache.log4j.Logger reoprtlog = org.apache.log4j.Logger.getLogger("tcmis.web");

  public void setstartTime()
  {
	this.startTime = System.currentTimeMillis();
  }

  public long getstartTime()  throws Exception
  {
   return this.startTime;
  }

  public void buildjmonxls( String jmonhtml ) throws Exception
  {
	this.setstartTime();
    //long currentTime=System.currentTimeMillis();
	//long lastaccssedtime = this.getstartTime();
	//if ((currentTime - lastaccssedtime) > 300000)
	{
	  String file="jmonhtml.xls";
	  PrintStream ps=null;
	  String writefilepath=radian.web.tcmisResourceLoader.getsavelreportpath();
	  file=writefilepath + "/jmonhtml.xls";

	  try
	  {
		ps=new PrintStream( new FileOutputStream( file,true ),true );

		String contents="";
		contents+=jmonhtml;
		byte outbuf[];
		outbuf=contents.getBytes();
		ps.write( outbuf );
		ps.close();
	  }
	  catch ( IOException writee )
	  {
		writee.printStackTrace();
	  }
	  reoprtlog.info("Written the JMON File");
	}
  }
}