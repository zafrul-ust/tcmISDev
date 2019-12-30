/**
 * Title:        Logical Search Help
 * Copyright:    Copyright (c) 2001
 * Company:      Radian
 * @author Nawaz Shaik
 * @version
 *
 * I am putting a bunch of get resource functions in this so that I dont have to be dependent on the
 * file path and the machine I am working on
 *
 */
package radian.web;

import java.util.Hashtable;
import java.util.Vector;

public abstract class logicalSearchhelp
{
  public static String doLogicM( String search,String snapName )
      {
        Vector result=new Vector();

        Hashtable r=getLogicHash( search );
        Vector opers= ( Vector ) r.get( "OPERATORS" );
        Vector likes= ( Vector ) r.get( "LIKES" );

        String aWhere="";

        if ( opers.size() < 1 )
        {
          aWhere=" ( lower("+snapName+") like lower('%" + search + "%')) ";
          return aWhere;
        }

        aWhere=aWhere + " ( lower("+snapName+") like lower('%" + likes.elementAt( 0 ).toString().trim() + "%')";
        for ( int i=0; i < opers.size(); i++ )
        {
          String op=opers.elementAt( i ).toString();
          String lk="like";
          if ( op.equalsIgnoreCase( "but not" ) )
          {
            op="and";
            lk="not like";
          }

          aWhere=aWhere + " " + op + " lower("+snapName+") " + lk + " lower('%" + likes.elementAt( i + 1 ).toString().trim() + "%')" + ( i == 0 ? "" : "" );
        }
        aWhere=aWhere + " ) ";

        return aWhere;
      }

     public static Hashtable getLogicHash( String search )
     {
       Hashtable result=new Hashtable();
       String tmp=search.toLowerCase();
       Vector strV=new Vector();
       Vector operV=new Vector();

       int i=0;
       while ( true )
       {
         int a= ( tmp.indexOf( " and " ) < 0 ? 5000 : ( tmp.indexOf( " and " ) + 1 ) );
         int o= ( tmp.indexOf( " or " ) < 0 ? 5000 : ( tmp.indexOf( " or " ) + 1 ) );
         int n= ( tmp.indexOf( " but not " ) < 0 ? 5000 : ( tmp.indexOf( " but not " ) + 1 ) );
         String sTmp="";
         if ( a < o && a < n )
         {
           sTmp=tmp.substring( 0,a );
           sTmp.trim();
           strV.addElement( sTmp );
           operV.addElement( "and" );
           if ( tmp.length() > ( a + 3 ) )
           {
             tmp=tmp.substring( a + 3 );
             tmp.trim();
             continue;
           }
         }
         else if ( o < a && o < n )
         {
           sTmp=tmp.substring( 0,o );
           sTmp.trim();
           strV.addElement( sTmp );
           operV.addElement( "or" );
           if ( tmp.length() > ( o + 3 ) )
           {
             tmp=tmp.substring( o + 3 );
             tmp.trim();
             continue;
           }
         }
         else if ( n < a && n < o )
         {
           sTmp=tmp.substring( 0,n );
           sTmp.trim();
           strV.addElement( sTmp );
           operV.addElement( "but not" );
           if ( tmp.length() > ( n + 7 ) )
           {
             tmp=tmp.substring( n + 7 );
             tmp.trim();
             continue;
           }
         }
         else
         {
           strV.addElement( tmp );
           break;
         }
       }

       result.put( "OPERATORS",operV );
       result.put( "LIKES",strV );

       return result;
    }


}