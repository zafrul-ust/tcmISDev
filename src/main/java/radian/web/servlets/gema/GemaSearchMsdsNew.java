package radian.web.servlets.gema;

import radian.tcmis.server7.share.helpers.ServerResourceBundle;

public class GemaSearchMsdsNew  extends radian.web.servlets.share.SearchMsdsNew
{
   public String getClient()
   {
      //System.out.println("\n\n============ Gemasearchmsds check it is here....");
      return "GEMA";
   }
   protected ServerResourceBundle getBundle()
   {
      return (ServerResourceBundle) new GemaServerResourceBundle();
   }
}