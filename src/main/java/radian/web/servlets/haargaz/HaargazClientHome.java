package radian.web.servlets.haargaz;

import radian.tcmis.server7.share.helpers.ServerResourceBundle;

public class HaargazClientHome extends radian.web.servlets.share.ClientHome
{
   public String getClient()
   {
      return "Haargaz";
   }
   protected ServerResourceBundle getBundle()
   {
      return (ServerResourceBundle) new HaargazServerResourceBundle();
   }
}