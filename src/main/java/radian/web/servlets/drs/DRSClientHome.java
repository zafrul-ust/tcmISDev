package radian.web.servlets.drs;

import radian.tcmis.server7.share.helpers.*;

public class DRSClientHome extends radian.web.servlets.share.ClientHome
{
   public String getClient()
   {
      return "DRS";
   }
   protected ServerResourceBundle getBundle()
   {
      return (ServerResourceBundle) new DRSServerResourceBundle();
   }
}