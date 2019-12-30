package radian.web.servlets.gd;

import radian.tcmis.server7.share.helpers.ServerResourceBundle;

public class GDClientHome extends radian.web.servlets.share.ClientHome
{
   public String getClient()
   {
      return "GD";
   }
   protected ServerResourceBundle getBundle()
   {
      return (ServerResourceBundle) new GDServerResourceBundle();
   }
}