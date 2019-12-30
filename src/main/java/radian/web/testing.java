/**
 * Title:        MSDS Viewer
 * Copyright:    Copyright (c) 2001
 * Company:      Radian
 * @author Nawaz Shaik
 * @version
 *
 * Use the methods in this class to get the header and footer information.
 * You can pass a title variable to add to the Title tag in the HEAD section
 * of the HTML
 *
 */
package radian.web;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.Runtime;

public class testing
{
  /** Creates a new instance of A */
  public testing()
  {


  }

  public static void main( String[] args )
  {
    try
    {
      //Runtime.getRuntime().exec("rundll32.exe user.exe,exitwindows");
      Process proc=Runtime.getRuntime().exec( "dir" );
      BufferedReader in=new BufferedReader( new InputStreamReader( proc.getInputStream() ) );
      String procOutput="";
      while ( ( procOutput=in.readLine() ) != null )
      {
        System.out.println( procOutput );
      }
    }
    catch ( java.io.IOException ioe )
    {
      System.out.println( "Exception caught" );
      ioe.printStackTrace();
    }
  }

}

/*String dbUrl = "jdbc:oracle:thin:@192.168.11.12:1523:hawkdev";
String user = "tcm_ops";
String password = "n0s3ha1r";

DriverManager.registerDriver(new oracle.jdbc.OracleDriver());
Connection connection = DriverManager.getConnection(dbUrl, user, password);

radian.tcmis.common.creator.CodeCreator creator = new radian.tcmis.common.creator.CodeCreator();
creator.createBean("radian.tcmis.internal.hub", "user_group_member_ig", "C:\\Temp\\", connection);
creator.createFactory("radian.tcmis.internal.hub", "user_group_member_ig", "C:\\Temp\\", connection);
connection.close();*/

/*for (Enumeration e = DataH.keys() ; e.hasMoreElements() ;)
             {
                 String branch_plant =  (String)(e.nextElement());
                 System.out.println("String "+branch_plant.toLowerCase()+" = DataH.get(\""+branch_plant+"\").toString().trim();");
             }*/

 /*ResultSetMetaData rsMeta1 = searchRs.getMetaData();
          System.out.println("Finished The Querry\n "+rsMeta1.getColumnCount());
          for(int i =1; i<=rsMeta1.getColumnCount(); i++)
          {
          System.out.println("String "+rsMeta1.getColumnName(i).toString().toLowerCase()+" = hD.get(\""+rsMeta1.getColumnName(i).toString()+"\")==null?\"&nbsp;\":hD.get(\""+rsMeta1.getColumnName(i).toString()+"\").toString();");

          //System.out.println("DataH.put(\""+rsMeta1.getColumnName(i).toString()+"\",searchRs.getString(\""+rsMeta1.getColumnName(i).toString()+"\")==null?\"\":searchRs.getString(\""+rsMeta1.getColumnName(i).toString()+"\"));");
          //System.out.println("//"+rsMeta1.getColumnName(i).toString()+"");
          //System.out.println("DataH.put(\""+rsMeta1.getColumnName(i).toString()+"\",searchRs.getString(\""+rsMeta1.getColumnName(i).toString()+"\")==null?\"\":searchRs.getString(\""+rsMeta1.getColumnName(i).toString()+"\"));");
          }*/

	  //to test the not null fields in javascript
	   /* if (rsMeta1.isNullable(i) == 0)
		{
		  System.out.println("var "+rsMeta1.getColumnName(i).toString().toLowerCase()+" =  document.getElementById(\""+rsMeta1.getColumnName(i).toString().toLowerCase()+"\");");
		  System.out.println("if ("+rsMeta1.getColumnName(i).toString().toLowerCase()+".value.trim().length == 0)");
		  System.out.println("{");
		  System.out.println("finalMsgt = finalMsgt + \" "+rsMeta1.getColumnName(i).toString().toLowerCase()+".xxxn\" ;");
		  System.out.println("allClear = 1;");
		  System.out.println("}");
		}*/

	  /*ResultSetMetaData rsMeta1 = rs.getMetaData();
			  System.out.println("Finished The Querry\n " + rsMeta1.getColumnCount());
			  for (int i = 1; i <= rsMeta1.getColumnCount(); i++)
			  {
				//System.out.println("Msgd.append(\"<TD WIDTH=\\\"10%\\\" \"+Color+\"l\\\">"+rsMeta1.getColumnName(i).toString().toLowerCase()+"</TD>xxn\");");
				//System.out.print("Msgd.append(\"<TD WIDTH=\\\"10%\\\" \"+Color+\"l\\\"><INPUT type=\\\"text\\\" CLASS=\\\"HEADER\\\" name=\\\""+rsMeta1.getColumnName(i).toString().toLowerCase()+"\"+TabNumber+\"\\\" value=\\\"\");");
				//System.out.println("Msgd.append(\"\\\" SIZE=\\\"5\\\"></TD>xxn\");");
				//For putting it into a table
				//For Symch Data
				//System.out.println("String "+rsMeta1.getColumnName(i).toString().toLowerCase()+"= request.getParameter((\""+rsMeta1.getColumnName(i).toString().toLowerCase()+"\"+loop)).toString();");
				//System.out.println("if ( "+rsMeta1.getColumnName(i).toString().toLowerCase()+" == null )");
				//System.out.println(""+rsMeta1.getColumnName(i).toString().toLowerCase()+"=\"\";");
				//System.out.println("materialData.remove(\""+rsMeta1.getColumnName(i).toString()+"\");");
				//System.out.println("materialData.put(\""+rsMeta1.getColumnName(i).toString()+"\", "+rsMeta1.getColumnName(i).toString().toLowerCase()+".trim() );");
				//System.out.println("Msgd.append("+rsMeta1.getColumnName(i).toString().toLowerCase()+");");
				//System.out.println("DataH.put(\""+rsMeta1.getColumnName(i).toString()+"\",searchRs.getString(\""+rsMeta1.getColumnName(i).toString()+"\")==null?\"\":searchRs.getString(\""+rsMeta1.getColumnName(i).toString()+"\"));");
				// To retrieve data from hash
				//System.out.println("String "+rsMeta1.getColumnName(i).toString().toLowerCase()+" = materialData.get(\""+rsMeta1.getColumnName(i).toString()+"\").toString();");
				//For Insert or update
				//System.out.print(""+rsMeta1.getColumnName(i).toString()+",");
				//System.out.print(""+rsMeta1.getColumnName(i).toString()+"='\"+"+rsMeta1.getColumnName(i).toString().toLowerCase()+"+\"',");
				//System.out.print("'\"+"+rsMeta1.getColumnName(i).toString().toLowerCase()+"+\"',");
				//System.out.print(""+rsMeta1.getColumnName(i).toString()+"='\"+"+rsMeta1.getColumnName(i).toString().toLowerCase()+"+\"',");
				// System.out.println("String "+rsMeta1.getColumnName(i).toString().toLowerCase()+" = hD.get(\""+rsMeta1.getColumnName(i).toString()+"\")==null?\"&nbsp;\":hD.get(\""+rsMeta1.getColumnName(i).toString()+"\").toString();");
				//System.out.println("MSDSData.put(\""+rsMeta1.getColumnName(i).toString()+"\",searchRs.getString(\""+rsMeta1.getColumnName(i).toString()+"\")==null?\"\":searchRs.getString(\""+rsMeta1.getColumnName(i).toString()+"\"));");
				//System.out.println("//"+rsMeta1.getColumnName(i).toString()+"");
				//System.out.println("newTaskH.put(\""+rsMeta1.getColumnName(i).toString()+"\",\"\");");
				//System.out.println("out.print(\"opener.document.ComponentForm\"+(compn+1)+\"."+rsMeta1.getColumnName(i).toString().toLowerCase()+"\"+compn+\".value = '\");out.print(rs.getString(\""+rsMeta1.getColumnName(i).toString()+"\")==null?\"\":rs.getString(\""+rsMeta1.getColumnName(i).toString()+"\")); out.print(\"';\n\");");

			  //System.out.println( "eval( compFormSam=\"window.document.ComponentForm\" + ( p + 1 ).toString() + \"."+rsMeta1.getColumnName(i).toString().toLowerCase()+"\" + p.toString() );" );
			  //System.out.println( "( curvaSam= ( eval( compFormSam.toString() ).value ) );" );
			  //System.out.println( "mainFormvalueSam=eval( \"window.document.MainForm."+rsMeta1.getColumnName(i).toString().toLowerCase()+"\" + p.toString() );" );
			  //System.out.println( "mainFormvalueSam.value=curvaSam;" );

			  //System.out.println( "out.println( \"<INPUT TYPE=\\\"hidden\\\" NAME=\\\""+rsMeta1.getColumnName(i).toString().toLowerCase()+"\" + TabNumber + \"\\\" VALUE=\\\"\" + msdsData.get( \""+rsMeta1.getColumnName(i).toString()+"\" ).toString() + \"\\\">\n\" );");
			  }*/

/*
 // Sort hashtable.
 Vector v=new Vector( hub_list.keySet() );
 Collections.sort( v );

 // Display (sorted) hashtable.
 for ( Enumeration e=v.elements(); e.hasMoreElements(); )
 {
   String key= ( String ) e.nextElement();
   String val= ( String ) hub_list.get( key );
   System.out.println( "Key: " + key + "     Val: " + val );
}

ArrayList as=new ArrayList( hub_list.entrySet() );
Collections.sort( as,new Comparator()
{
  public int compare( Object o1,Object o2 )
  {
	Map.Entry e1= ( Map.Entry ) o1;
	Map.Entry e2= ( Map.Entry ) o2;
	String first= ( String ) e1.getValue();
	String second= ( String ) e2.getValue();
	return first.compareTo( second );
  }
} );
Iterator i1=as.iterator();
 while ( i1.hasNext() )
 {
   System.out.println( ( Map.Entry ) i1.next() );

	 Map.Entry e1= ( Map.Entry ) i1.next();
	 String first= ( String ) e1.getValue();
	 System.out.println( "Try this   "+first );
 }


for (int loop  = 0 ; loop  < as.size()  ; loop++)
{
 System.out.println( as.get(loop) );
}
*/
