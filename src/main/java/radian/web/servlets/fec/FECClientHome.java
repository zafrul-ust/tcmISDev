package radian.web.servlets.fec;

import radian.tcmis.server7.share.helpers.ServerResourceBundle;

public class FECClientHome extends radian.web.servlets.share.ClientHome
{
   public String getClient()
   {
      return "FEC";
   }
   protected ServerResourceBundle getBundle()
   {
      return (ServerResourceBundle) new FECServerResourceBundle();
   }
}