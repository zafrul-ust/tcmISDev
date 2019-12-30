package radian.web.servlets.l3;

import radian.tcmis.server7.share.helpers.ServerResourceBundle;

public class L3BuildSearchPageNew  extends radian.web.servlets.share.BuildSearchPageNew
{
   public String getClient()
   {
      return "L3";
   }
   protected ServerResourceBundle getBundle()
   {
      return (ServerResourceBundle) new L3ServerResourceBundle();
   }
}