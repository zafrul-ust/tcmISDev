package radian.web.servlets.usgov;

import radian.tcmis.server7.share.helpers.ServerResourceBundle;

public class USGOVSearchMsdsNew  extends radian.web.servlets.share.SearchMsdsNew
{
   public String getClient()
   {
      //System.out.println("\n\n============ USGOVsearchmsds check it is here....");
      return "USGOV";
   }
   protected ServerResourceBundle getBundle()
   {
      return (ServerResourceBundle) new USGOVServerResourceBundle();
   }
}