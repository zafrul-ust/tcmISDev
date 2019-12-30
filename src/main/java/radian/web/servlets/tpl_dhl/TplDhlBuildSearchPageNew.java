package radian.web.servlets.tpl_dhl;

import radian.tcmis.server7.share.helpers.ServerResourceBundle;

public class TplDhlBuildSearchPageNew  extends radian.web.servlets.share.BuildSearchPageNew
{
   public String getClient()
   {
      return "TPL_DHL";
   }
   protected ServerResourceBundle getBundle()
   {
      return (ServerResourceBundle) new TplDhlServerResourceBundle();
   }
}