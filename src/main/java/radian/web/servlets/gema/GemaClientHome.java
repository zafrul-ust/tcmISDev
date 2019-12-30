package radian.web.servlets.gema;

import radian.tcmis.server7.share.helpers.ServerResourceBundle;

public class GemaClientHome extends radian.web.servlets.share.ClientHome
{
   public String getClient()
   {
      return "GEMA";
   }
   protected ServerResourceBundle getBundle()
   {
      return (ServerResourceBundle) new GemaServerResourceBundle();
   }
}