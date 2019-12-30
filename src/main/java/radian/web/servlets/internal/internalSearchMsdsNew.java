package radian.web.servlets.internal;

import radian.tcmis.server7.share.helpers.ServerResourceBundle;

public class internalSearchMsdsNew  extends radian.web.servlets.share.SearchMsdsNew
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