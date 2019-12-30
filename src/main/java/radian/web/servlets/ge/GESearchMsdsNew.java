package radian.web.servlets.ge;

import radian.tcmis.server7.share.helpers.ServerResourceBundle;

public class GESearchMsdsNew  extends radian.web.servlets.share.SearchMsdsNew
{
   public String getClient()
   {
      //System.out.println("\n\n============ GEsearchmsds check it is here....");
      return "GE";
   }
   protected ServerResourceBundle getBundle()
   {
      return (ServerResourceBundle) new GEServerResourceBundle();
   }
}