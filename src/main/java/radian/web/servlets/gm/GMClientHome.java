package radian.web.servlets.gm;

import radian.tcmis.server7.share.helpers.ServerResourceBundle;

public class GMClientHome extends radian.web.servlets.share.ClientHome
{
   public String getClient()
   {
      return "GM";
   }
   protected ServerResourceBundle getBundle()
   {
      return (ServerResourceBundle) new GMServerResourceBundle();
   }
}