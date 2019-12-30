package radian.web.servlets.zf;

import radian.tcmis.server7.share.helpers.ServerResourceBundle;

public class ZFClientHome extends radian.web.servlets.share.ClientHome
{
   public String getClient()
   {
      return "ZF";
   }
   protected ServerResourceBundle getBundle()
   {
      return (ServerResourceBundle) new ZFServerResourceBundle();
   }
}