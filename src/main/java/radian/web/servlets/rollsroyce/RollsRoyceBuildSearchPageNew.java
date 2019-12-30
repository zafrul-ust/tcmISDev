package radian.web.servlets.rollsroyce;

import radian.tcmis.server7.share.helpers.ServerResourceBundle;

public class RollsRoyceBuildSearchPageNew  extends radian.web.servlets.share.BuildSearchPageNew
{
   public String getClient()
   {
      return "ROLLS_ROYCE";
   }
   protected ServerResourceBundle getBundle()
   {
      return (ServerResourceBundle) new RollsRoyceServerResourceBundle();
   }
}