package radian.web.servlets.internal;

import radian.tcmis.server7.share.helpers.ServerResourceBundle;

public class internalBuildSearchPageNew  extends radian.web.servlets.share.BuildSearchPageNew
{
   public String getClient()
   {
      return "Tcm_Ops";
   }
   protected ServerResourceBundle getBundle()
   {
      return (ServerResourceBundle) new InternalServerResourceBundle();
   }
}