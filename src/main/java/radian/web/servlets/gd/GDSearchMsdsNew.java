package radian.web.servlets.gd;

import radian.tcmis.server7.share.helpers.ServerResourceBundle;

public class GDSearchMsdsNew  extends radian.web.servlets.share.SearchMsdsNew
{
   public String getClient()
   {
      //System.out.println("\n\n============ GDsearchmsds check it is here....");
      return "GD";
   }
   protected ServerResourceBundle getBundle()
   {
      return (ServerResourceBundle) new GDServerResourceBundle();
   }
}