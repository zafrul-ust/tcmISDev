package radian.web.servlets.rollsroyce;

import radian.tcmis.server7.share.helpers.ServerResourceBundle;

public class RollsRoyceSearchMsdsNew  extends radian.web.servlets.share.SearchMsdsNew
{
   public String getClient()
   {
      //System.out.println("\n\n============ RollsRoycesearchmsds check it is here....");
      return "ROLLS_ROYCE";
   }
   protected ServerResourceBundle getBundle()
   {
      return (ServerResourceBundle) new RollsRoyceServerResourceBundle();
   }
}