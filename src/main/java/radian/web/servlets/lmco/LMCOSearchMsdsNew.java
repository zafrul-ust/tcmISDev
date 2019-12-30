package radian.web.servlets.lmco;

import radian.tcmis.server7.share.helpers.ServerResourceBundle;

public class LMCOSearchMsdsNew  extends radian.web.servlets.share.SearchMsdsNew
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