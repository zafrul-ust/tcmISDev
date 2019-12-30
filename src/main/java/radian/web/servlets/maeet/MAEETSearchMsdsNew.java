package radian.web.servlets.maeet;

import radian.tcmis.server7.share.helpers.ServerResourceBundle;

public class MAEETSearchMsdsNew  extends radian.web.servlets.share.SearchMsdsNew
{
   public String getClient()
   {
      //System.out.println("\n\n============ MAEETsearchmsds check it is here....");
      return "MAEET";
   }
   protected ServerResourceBundle getBundle()
   {
      return (ServerResourceBundle) new MAEETServerResourceBundle();
   }
}