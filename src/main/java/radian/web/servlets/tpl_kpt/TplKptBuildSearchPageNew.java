package radian.web.servlets.tpl_kpt;

import radian.tcmis.server7.share.helpers.ServerResourceBundle;

public class TplKptBuildSearchPageNew  extends radian.web.servlets.share.BuildSearchPageNew
{
   public String getClient()
   {
      return "TPL_KPT";
   }
   protected ServerResourceBundle getBundle()
   {
      return (ServerResourceBundle) new TplKptServerResourceBundle();
   }
}