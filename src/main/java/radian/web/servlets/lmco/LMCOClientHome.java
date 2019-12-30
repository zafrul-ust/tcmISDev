package radian.web.servlets.lmco;

import radian.tcmis.server7.share.helpers.ServerResourceBundle;

public class LMCOClientHome extends radian.web.servlets.share.ClientHome
{
   public String getClient()
   {
      return "LMCO";
   }
   protected ServerResourceBundle getBundle()
   {
      return (ServerResourceBundle) new LMCOServerResourceBundle();
   }
}