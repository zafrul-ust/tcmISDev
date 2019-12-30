package radian.web.servlets.sgd;

import radian.tcmis.server7.share.helpers.ServerResourceBundle;

public class SGDBuildSearchPageNew  extends radian.web.servlets.share.BuildSearchPageNew
{
   public String getClient()
   {
      return "SGD";
   }
   protected ServerResourceBundle getBundle()
   {
      return (ServerResourceBundle) new SGDServerResourceBundle();
   }
}