package radian.web.barcode;

import java.util.Hashtable;
import java.util.Vector;

import com.actuate.ereport.datasrcmgrs.appdatasrc.AppDataHandler;
import com.actuate.ereport.engine.ACJEngine;
import com.actuate.ereport.engine.TemplateManager;
import com.actuate.ereport.engine.lib.ACJConstants;
import com.actuate.ereport.output.ACJOutputProcessor;
import radian.tcmis.both1.reports.RegisterTable;
import radian.web.cabinets.cabinetData;

public class ACJBarCodeGenerator
{
    private static org.apache.log4j.Logger reoprtlog = org.apache.log4j.Logger.getLogger("tcmis.web");
    public ACJBarCodeGenerator() {
    }

	public void buildTest( Vector AllTheData,String NameofFile,String PaperSizeO,String hubNum,Vector compdata ) throws Exception
	{
	  ACJEngine en=new ACJEngine();
	  en.setDebugMode( true );
	  en.setX11GfxAvailibility( false );
	  en.setTargetOutputDevice( ACJConstants.PDF );

	  ACJOutputProcessor eClient=new ACJOutputProcessor();
	  String fontmappath=radian.web.tcmisResourceLoader.getfontpropertiespath();
	  String templatpath=radian.web.tcmisResourceLoader.getlabeltempplatepath();
	  String writefilepath=radian.web.tcmisResourceLoader.getsavelabelpath();
	  //en.setCacheOption( true,"" + writefilepath + "Barcode" + NameofFile + ".joi" );
	  eClient.setPathForFontMapFile( fontmappath );

	  compdata.trimToSize();
	  if ( compdata.size() == 0 )
	  {
		Hashtable compdataH=new Hashtable();
		compdataH.put( "DETAIL_0","" );
		compdataH.put( "DETAIL_1","" );
		compdataH.put( "DETAIL_2","" );
		compdataH.put( "DETAIL_3","" );
		compdataH.put( "DETAIL_4","" );

		compdata.addElement( compdataH );
	  }

	  try
	  {
		if ( "2118".equalsIgnoreCase( hubNum ) )
		{
		  en.readTemplate( "" + templatpath + "BARCODE2118.erw" );
		}
		else
		{
		  if ( "31".equalsIgnoreCase( PaperSizeO ) || "038".equalsIgnoreCase( PaperSizeO ) || "35".equalsIgnoreCase( PaperSizeO ) )
		  {
			hubNum="";
		  }
		  en.readTemplate( "" + templatpath + hubNum + "BARCODE" + PaperSizeO + ".erw" );
		}
	  }
	  catch ( Exception e )
	  {
		//System.out.println( "ERROR loading template" );
		//e.printStackTrace();
		throw e;
        }

	TemplateManager tm = en.getTemplateManager();
	//reoprtlog.info("buildTest    Start "+NameofFile.toString()+"    Size: "+AllTheData.size()+"    Comp Size:  "+compdata.size()+"");
	try
	{
	  AppDataHandler ds=new AppDataHandler();
	  //register table...
	  RegisterTable[] rt=getData( AllTheData );
	  for ( int i=0; i < rt.length; i++ )
	  {
		Vector v1=rt[i].getData();
		Vector v2=rt[i].getMethods();
		ds.RegisterTable( rt[i].getData(),rt[i].getName(),rt[i].getMethods(),rt[i].getWhere() );
	}

	RegisterTable[] rt1=getcompData( compdata );
	for ( int i=0; i < rt1.length; i++ )
	{
	  Vector v1=rt1[i].getData();
	  Vector v2=rt1[i].getMethods();
	  ds.RegisterTable( rt1[i].getData(),rt1[i].getName(),rt1[i].getMethods(),rt1[i].getWhere() );
	}

	en.setDataSource( ds );
  }
  catch ( Exception e )
  {
	e.printStackTrace();
	throw e;
  }

try
  {
	eClient.setReportData( en.generateReport() );
  }
  catch ( Exception ex )
  {
	//System.out.println( "\n\n---------ERROR: While generating report" );
	//ex.printStackTrace();
	throw ex;
  }

try
  {
	eClient.setPDFProperty( "FileName","" + writefilepath + "Barcode" + NameofFile + ".pdf" );
	eClient.generatePDF();
  }
  catch ( Exception e )
  {
	//System.out.println( "\n\n---------ERROR generating report" );
	//e.printStackTrace();
	throw e;
  }
  //reoprtlog.info( "buildTest    Done " + NameofFile.toString() + "" );
}

  public RegisterTable[] getData( Vector reportData1 ) throws Exception
  {
    RegisterTable[] rt=new RegisterTable[1];

    try
    {
      rt[0]=new RegisterTable( BarCodeData.getVector( reportData1,14 ),"BARCODE",BarCodeData.getFieldVector( 14 ),null );
    }
    catch ( Exception e1 )
    {
      e1.printStackTrace();
      throw e1;
    }
    return rt;
  }

  public RegisterTable[] getcompData( Vector reportData1 ) throws Exception
  {
	RegisterTable[] rt=new RegisterTable[1];

	try
	{
	  rt[0]=new RegisterTable( cabinetData.getVector( reportData1,14 ),"RECCOMP",cabinetData.getFieldVector( 14 ),null );
	}
	catch ( Exception e1 )
	{
	  e1.printStackTrace();
	  throw e1;
	}
	return rt;
  }
}


