package radian.web.servlets.tpl_ll;

import radian.tcmis.server7.share.helpers.ServerResourceBundle;

public class TplLLBuildSearchPageNew  extends radian.web.servlets.share.BuildSearchPageNew
{
   public String getClient()
   {
      return "TPL_LL";
   }
   protected ServerResourceBundle getBundle()
   {
      return (ServerResourceBundle) new TplLLServerResourceBundle();
   }
}