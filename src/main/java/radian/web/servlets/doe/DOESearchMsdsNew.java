package radian.web.servlets.doe;

import radian.tcmis.server7.share.helpers.ServerResourceBundle;

public class DOESearchMsdsNew  extends radian.web.servlets.share.SearchMsdsNew
{
   public String getClient()
   {
      //System.out.println("\n\n============ DOEsearchmsds check it is here....");
      return "DOE";
   }
   protected ServerResourceBundle getBundle()
   {
      return (ServerResourceBundle) new DOEServerResourceBundle();
   }
}