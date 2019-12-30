package radian.web.servlets.bae;

import radian.tcmis.server7.share.helpers.ServerResourceBundle;

public class BAESearchMsdsNew  extends radian.web.servlets.share.SearchMsdsNew
{
   public String getClient()
   {
      //System.out.println("\n\n============ BAEsearchmsds check it is here....");
      return "BAE";
   }
   protected ServerResourceBundle getBundle()
   {
      return (ServerResourceBundle) new BAEServerResourceBundle();
   }
}