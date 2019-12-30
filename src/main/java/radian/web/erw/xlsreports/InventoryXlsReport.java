// Decompiled by Jad v1.5.7g. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3)
// Source File Name:   InventoryXlsReport.java

package radian.web.erw.xlsreports;

import java.sql.ResultSet;
import java.util.*;
import radian.tcmis.both1.helpers.BothHelpObjs;
import radian.tcmis.server7.client.ray.dbObjs.RayTcmISDBModel;
import radian.tcmis.server7.share.db.DBResultSet;
import radian.tcmis.server7.share.dbObjs.TcmISDBModel;

public class InventoryXlsReport
{

    public InventoryXlsReport()
    {
        db = null;
        dbrs = null;
        rs = null;
    }

    public StringBuffer createXls(String query, String client, String orderBy)
        throws Exception
    {
        db = new RayTcmISDBModel(client);
        StringBuffer Msgcx = new StringBuffer();
        int numRecs = 0;
        int numRecsTest = 0;
        Hashtable Result1 = new Hashtable();
        Hashtable Result2 = new Hashtable();
        Vector ResultV = new Vector();
        Vector ResultF = new Vector();
        String testfac = "";
        boolean resultFadded = false;
        String partdesc = "";
        String partc = "";
        String comp_pack = "";
        String testbin = "";
        String testlot = "";
        String testonhand = "";
        String testpart = "";
        String testmaterial = "";
        try
        {
            if("BIN".equalsIgnoreCase(orderBy))
                dbrs = db.doQuery(String.valueOf(String.valueOf(query)).concat(" order by ITEM_ID,BIN"));
            else
            if("LOT".equalsIgnoreCase(orderBy))
                dbrs = db.doQuery(String.valueOf(String.valueOf(query)).concat(" order by ITEM_ID,BIN,LOT_NUM"));
            else
                dbrs = db.doQuery(String.valueOf(String.valueOf(query)).concat(" order by ITEM_ID"));
            rs = dbrs.getResultSet();
            //System.out.print("Finished The Querry\n ");
            while(rs.next())
            {
                numRecs++;
                if(numRecsTest != 0)
                {
                    if(testfac.equalsIgnoreCase(BothHelpObjs.makeSpaceFromNull(rs.getString("ITEM_ID").toString())))
                    {
                        //System.out.print"SecondTime\n");
                        Result2 = new Hashtable();
                        if(!testpart.equalsIgnoreCase(BothHelpObjs.makeSpaceFromNull(rs.getString("CAT_PART_NO").toString())))
                        {
                            partdesc = partdesc+"/"+BothHelpObjs.makeSpaceFromNull(rs.getString("PART_DESCRIPTION"));
                            partc = partc+"/"+BothHelpObjs.makeSpaceFromNull(rs.getString("CAT_PART_NO"));
                            comp_pack = comp_pack+"/"+BothHelpObjs.makeSpaceFromNull(rs.getString("TRADE_NAME"))+"-->"+BothHelpObjs.makeSpaceFromNull(rs.getString("PACKAGING"));
                        } else
                        {
                            if(!testmaterial.equalsIgnoreCase(BothHelpObjs.makeSpaceFromNull(rs.getString("MATERIAL_ID").toString())))
                            {
                                partdesc = partdesc+"/"+BothHelpObjs.makeSpaceFromNull(rs.getString("PART_DESCRIPTION"));
                                partc = partc+"/"+BothHelpObjs.makeSpaceFromNull(rs.getString("CAT_PART_NO"));
                                comp_pack = comp_pack+"/"+BothHelpObjs.makeSpaceFromNull(rs.getString("TRADE_NAME"))+"-->"+BothHelpObjs.makeSpaceFromNull(rs.getString("PACKAGING"));
                                testmaterial = "";
                                testmaterial = BothHelpObjs.makeSpaceFromNull(rs.getString("MATERIAL_ID"));
                                if("BIN".equalsIgnoreCase(orderBy))
                                {
                                    //System.out.println("\n\n---------- not suppose to be here 111");
                                    if(testbin.equalsIgnoreCase(BothHelpObjs.makeSpaceFromNull(rs.getString("BIN").toString())) && testonhand.equalsIgnoreCase(BothHelpObjs.makeSpaceFromNull(rs.getString("QTY_AVAILABLE").toString())))
                                    {
                                        Result2.put("ON_HOLD", BothHelpObjs.makeSpaceFromNull(rs.getString("QTY_HELD")));
                                        Result2.put("BIN", BothHelpObjs.makeBlankFromNull(rs.getString("bin")));
                                        Result2.put("ON_HAND", BothHelpObjs.makeSpaceFromNull(rs.getString("QTY_AVAILABLE")));
                                        ResultV.add(Result2);
                                        //System.out.print"Added\n");
                                        testbin = "";
                                        testlot = "";
                                        testonhand = "";
                                        testbin = BothHelpObjs.makeSpaceFromNull(rs.getString("BIN"));
                                        testonhand = BothHelpObjs.makeSpaceFromNull(rs.getString("QTY_AVAILABLE"));
                                    }
                                } else
                                if("LOT".equalsIgnoreCase(orderBy))
                                {
                                    if(testbin.equalsIgnoreCase(BothHelpObjs.makeSpaceFromNull(rs.getString("BIN").toString())) && testlot.equalsIgnoreCase(BothHelpObjs.makeSpaceFromNull(rs.getString("LOT_NUM").toString())) && testonhand.equalsIgnoreCase(BothHelpObjs.makeSpaceFromNull(rs.getString("QTY_AVAILABLE").toString())))
                                    {
                                        Result2.put("ON_HOLD", BothHelpObjs.makeSpaceFromNull(rs.getString("QTY_HELD")));
                                        Result2.put("BIN", BothHelpObjs.makeBlankFromNull(rs.getString("bin")));
                                        Result2.put("LOT_NUM", BothHelpObjs.makeBlankFromNull(rs.getString("LOT_NUM")));
                                        Result2.put("ON_HAND", BothHelpObjs.makeSpaceFromNull(rs.getString("QTY_AVAILABLE")));
                                        ResultV.add(Result2);
                                        //System.out.print"Added\n");
                                        testbin = "";
                                        testlot = "";
                                        testonhand = "";
                                        testbin = BothHelpObjs.makeSpaceFromNull(rs.getString("BIN"));
                                        testlot = BothHelpObjs.makeSpaceFromNull(rs.getString("LOT_NUM"));
                                        testonhand = BothHelpObjs.makeSpaceFromNull(rs.getString("QTY_AVAILABLE"));
                                    }
                                } else
                                if(testonhand.equalsIgnoreCase(BothHelpObjs.makeSpaceFromNull(rs.getString("QTY_AVAILABLE").toString())))
                                {
                                    Result2.put("ON_HOLD", BothHelpObjs.makeSpaceFromNull(rs.getString("QTY_HELD")));
                                    Result2.put("ON_HAND", BothHelpObjs.makeSpaceFromNull(rs.getString("QTY_AVAILABLE")));
                                    ResultV.add(Result2);
                                    //System.out.print"Added\n");
                                    testbin = "";
                                    testlot = "";
                                    testonhand = "";
                                    testonhand = BothHelpObjs.makeSpaceFromNull(rs.getString("QTY_AVAILABLE"));
                                }
                            }
                            if("BIN".equalsIgnoreCase(orderBy))
                            {
                                if(!testbin.equalsIgnoreCase(BothHelpObjs.makeSpaceFromNull(rs.getString("BIN").toString())) || !testonhand.equalsIgnoreCase(BothHelpObjs.makeSpaceFromNull(rs.getString("QTY_AVAILABLE").toString())))
                                {
                                    Result2.put("ON_HOLD", BothHelpObjs.makeSpaceFromNull(rs.getString("QTY_HELD")));
                                    Result2.put("BIN", BothHelpObjs.makeBlankFromNull(rs.getString("bin")));
                                    Result2.put("ON_HAND", BothHelpObjs.makeSpaceFromNull(rs.getString("QTY_AVAILABLE")));
                                    ResultV.add(Result2);
                                    //System.out.print"Added\n");
                                    testbin = "";
                                    testlot = "";
                                    testonhand = "";
                                    testbin = BothHelpObjs.makeSpaceFromNull(rs.getString("BIN"));
                                    testonhand = BothHelpObjs.makeSpaceFromNull(rs.getString("QTY_AVAILABLE"));
                                }
                            } else
                            if("LOT".equalsIgnoreCase(orderBy))
                            {
                                if(testbin.equalsIgnoreCase(BothHelpObjs.makeSpaceFromNull(rs.getString("BIN").toString())) && testlot.equalsIgnoreCase(BothHelpObjs.makeSpaceFromNull(rs.getString("LOT_NUM").toString())) && testonhand.equalsIgnoreCase(BothHelpObjs.makeSpaceFromNull(rs.getString("QTY_AVAILABLE").toString())))
                                {
                                    //System.out.print"Did Not Match\n");
                                } else
                                {
                                    Result2.put("ON_HOLD", BothHelpObjs.makeSpaceFromNull(rs.getString("QTY_HELD")));
                                    Result2.put("BIN", BothHelpObjs.makeBlankFromNull(rs.getString("bin")));
                                    Result2.put("LOT_NUM", BothHelpObjs.makeBlankFromNull(rs.getString("LOT_NUM")));
                                    Result2.put("ON_HAND", BothHelpObjs.makeSpaceFromNull(rs.getString("QTY_AVAILABLE")));
                                    ResultV.add(Result2);
                                    //System.out.print"Added\n");
                                    testbin = "";
                                    testlot = "";
                                    testonhand = "";
                                    testbin = BothHelpObjs.makeSpaceFromNull(rs.getString("BIN"));
                                    testlot = BothHelpObjs.makeSpaceFromNull(rs.getString("LOT_NUM"));
                                    testonhand = BothHelpObjs.makeSpaceFromNull(rs.getString("QTY_AVAILABLE"));
                                }
                            } else
                            if(!testonhand.equalsIgnoreCase(BothHelpObjs.makeSpaceFromNull(rs.getString("QTY_AVAILABLE").toString())))
                            {
                                Result2.put("ON_HOLD", BothHelpObjs.makeSpaceFromNull(rs.getString("QTY_HELD")));
                                Result2.put("ON_HAND", BothHelpObjs.makeSpaceFromNull(rs.getString("QTY_AVAILABLE")));
                                ResultV.add(Result2);
                                //System.out.print"Added\n");
                                testbin = "";
                                testlot = "";
                                testonhand = "";
                                testonhand = BothHelpObjs.makeSpaceFromNull(rs.getString("QTY_AVAILABLE"));
                            }
                        }
                        resultFadded = false;
                        numRecsTest++;
                    } else
                    {
                        Result1.put("LOT_BINS", ResultV);
                        Result1.put("PART_DESCRIPTION", BothHelpObjs.makeSpaceFromNull(partdesc).substring(1, BothHelpObjs.makeSpaceFromNull(partdesc).length()));
                        Result1.put("CAT_PART_NO", BothHelpObjs.makeSpaceFromNull(partc).substring(1, BothHelpObjs.makeSpaceFromNull(partc).length()));
                        Result1.put("TRADE_NAME", BothHelpObjs.makeSpaceFromNull(comp_pack).substring(1, BothHelpObjs.makeSpaceFromNull(comp_pack).length()));
                        ResultF.add(Result1);
                        resultFadded = true;
                        Result1 = new Hashtable();
                        Result2 = new Hashtable();
                        ResultV = new Vector();
                        comp_pack = "";
                        partdesc = "";
                        partc = "";
                        testbin = "";
                        testlot = "";
                        testonhand = "";
                        testfac = BothHelpObjs.makeSpaceFromNull(rs.getString("ITEM_ID"));
                        testpart = BothHelpObjs.makeSpaceFromNull(rs.getString("CAT_PART_NO"));
                        if("BIN".equalsIgnoreCase(orderBy))
                        {
                            testbin = BothHelpObjs.makeSpaceFromNull(rs.getString("BIN"));
                            testonhand = BothHelpObjs.makeSpaceFromNull(rs.getString("QTY_AVAILABLE"));
                        } else
                        if("LOT".equalsIgnoreCase(orderBy))
                        {
                            testbin = BothHelpObjs.makeSpaceFromNull(rs.getString("BIN"));
                            testlot = BothHelpObjs.makeSpaceFromNull(rs.getString("LOT_NUM"));
                            testonhand = BothHelpObjs.makeSpaceFromNull(rs.getString("QTY_AVAILABLE"));
                        } else
                        {
                            testonhand = BothHelpObjs.makeSpaceFromNull(rs.getString("QTY_AVAILABLE"));
                        }
                        Result1.put("MFG_DESC", BothHelpObjs.makeSpaceFromNull(rs.getString("MFG_DESC")));
                        Result1.put("MFG_PART_NO", BothHelpObjs.makeSpaceFromNull(rs.getString("MFG_PART_NO")));
                        Result1.put("ITEM_ID", BothHelpObjs.makeSpaceFromNull(rs.getString("ITEM_ID")));
                        partdesc = partdesc+"/"+BothHelpObjs.makeBlankFromNull(rs.getString("PART_DESCRIPTION"));
                        partc = partc+"/"+BothHelpObjs.makeBlankFromNull(rs.getString("CAT_PART_NO"));
                        comp_pack = comp_pack+"/"+BothHelpObjs.makeBlankFromNull(rs.getString("TRADE_NAME"))+"-->"+BothHelpObjs.makeSpaceFromNull(rs.getString("PACKAGING"));
                        if("BIN".equalsIgnoreCase(orderBy))
                        {
                            Result2.put("ON_HOLD", BothHelpObjs.makeSpaceFromNull(rs.getString("QTY_HELD")));
                            Result2.put("BIN", BothHelpObjs.makeSpaceFromNull(rs.getString("bin")));
                            Result2.put("ON_HAND", BothHelpObjs.makeSpaceFromNull(rs.getString("QTY_AVAILABLE")));
                        } else
                        if("LOT".equalsIgnoreCase(orderBy))
                        {
                            Result2.put("ON_HOLD", BothHelpObjs.makeSpaceFromNull(rs.getString("QTY_HELD")));
                            Result2.put("BIN", BothHelpObjs.makeSpaceFromNull(rs.getString("bin")));
                            Result2.put("LOT_NUM", BothHelpObjs.makeSpaceFromNull(rs.getString("lot_num")));
                            Result2.put("ON_HAND", BothHelpObjs.makeSpaceFromNull(rs.getString("QTY_AVAILABLE")));
                        } else
                        {
                            Result2.put("ON_HOLD", BothHelpObjs.makeSpaceFromNull(rs.getString("QTY_HELD")));
                            Result2.put("ON_HAND", BothHelpObjs.makeSpaceFromNull(rs.getString("QTY_AVAILABLE")));
                        }
                        Result1.put("ON_ORDER", BothHelpObjs.makeSpaceFromNull(rs.getString("QTY_ON_ORDER")));
                        Result1.put("IN_PURCHASING", BothHelpObjs.makeSpaceFromNull(rs.getString("QTY_IN_PURCHASING")));
                        ResultV.add(Result2);
                        resultFadded = false;
                        numRecsTest++;
                    }
                } else
                {
                    testfac = BothHelpObjs.makeSpaceFromNull(rs.getString("ITEM_ID"));
                    testpart = BothHelpObjs.makeSpaceFromNull(rs.getString("CAT_PART_NO"));
                    testmaterial = BothHelpObjs.makeSpaceFromNull(rs.getString("MATERIAL_ID"));
                    if("BIN".equalsIgnoreCase(orderBy))
                    {
                        testbin = BothHelpObjs.makeSpaceFromNull(rs.getString("BIN"));
                        testonhand = BothHelpObjs.makeSpaceFromNull(rs.getString("QTY_AVAILABLE"));
                    } else
                    if("LOT".equalsIgnoreCase(orderBy))
                    {
                        testbin = BothHelpObjs.makeSpaceFromNull(rs.getString("BIN"));
                        testlot = BothHelpObjs.makeSpaceFromNull(rs.getString("LOT_NUM"));
                        testonhand = BothHelpObjs.makeSpaceFromNull(rs.getString("QTY_AVAILABLE"));
                    } else
                    {
                        testonhand = BothHelpObjs.makeSpaceFromNull(rs.getString("QTY_AVAILABLE"));
                    }
                    Result1.put("MFG_DESC", BothHelpObjs.makeSpaceFromNull(rs.getString("MFG_DESC")));
                    Result1.put("MFG_PART_NO", BothHelpObjs.makeSpaceFromNull(rs.getString("MFG_PART_NO")));
                    Result1.put("ITEM_ID", BothHelpObjs.makeSpaceFromNull(rs.getString("ITEM_ID")));
                    partdesc = partdesc+"/"+BothHelpObjs.makeBlankFromNull(rs.getString("PART_DESCRIPTION"));
                    partc = partc+"/"+BothHelpObjs.makeBlankFromNull(rs.getString("CAT_PART_NO"));
                    comp_pack = comp_pack+"/"+BothHelpObjs.makeBlankFromNull(rs.getString("TRADE_NAME"))+"-->"+BothHelpObjs.makeSpaceFromNull(rs.getString("PACKAGING"));
                    if("BIN".equalsIgnoreCase(orderBy))
                    {
                        Result2.put("ON_HOLD", BothHelpObjs.makeSpaceFromNull(rs.getString("QTY_HELD")));
                        Result2.put("BIN", BothHelpObjs.makeSpaceFromNull(rs.getString("bin")));
                        Result2.put("ON_HAND", BothHelpObjs.makeSpaceFromNull(rs.getString("QTY_AVAILABLE")));
                    } else
                    if("LOT".equalsIgnoreCase(orderBy))
                    {
                        Result2.put("ON_HOLD", BothHelpObjs.makeSpaceFromNull(rs.getString("QTY_HELD")));
                        Result2.put("BIN", BothHelpObjs.makeSpaceFromNull(rs.getString("bin")));
                        Result2.put("LOT_NUM", BothHelpObjs.makeSpaceFromNull(rs.getString("lot_num")));
                        Result2.put("ON_HAND", BothHelpObjs.makeSpaceFromNull(rs.getString("QTY_AVAILABLE")));
                    } else
                    {
                        Result2.put("ON_HOLD", BothHelpObjs.makeSpaceFromNull(rs.getString("QTY_HELD")));
                        Result2.put("ON_HAND", BothHelpObjs.makeSpaceFromNull(rs.getString("QTY_AVAILABLE")));
                    }
                    Result1.put("ON_ORDER", BothHelpObjs.makeSpaceFromNull(rs.getString("QTY_ON_ORDER")));
                    Result1.put("IN_PURCHASING", BothHelpObjs.makeSpaceFromNull(rs.getString("QTY_IN_PURCHASING")));
                    ResultV.add(Result2);
                    resultFadded = false;
                    numRecsTest++;
                }
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            dbrs.close();
            if(!resultFadded)
            {
                Result1.put("LOT_BINS", ResultV);
                Result1.put("PART_DESCRIPTION", BothHelpObjs.makeSpaceFromNull(partdesc).substring(1, BothHelpObjs.makeSpaceFromNull(partdesc).length()));
                Result1.put("CAT_PART_NO", BothHelpObjs.makeSpaceFromNull(partc).substring(1, BothHelpObjs.makeSpaceFromNull(partc).length()));
                Result1.put("TRADE_NAME", BothHelpObjs.makeSpaceFromNull(comp_pack).substring(1, BothHelpObjs.makeSpaceFromNull(comp_pack).length()));
                ResultF.add(Result1);
            }
        }
        Hashtable Final = new Hashtable();
        Hashtable Final1 = new Hashtable();
        Vector ResultV1 = new Vector();
        if(numRecs != 0)
        {
            try
            {
                for(int j = 0; j < ResultF.size(); j++)
                {
                    String Color = " ";
                    ResultV1 = new Vector();
                    if(j % 2 == 0)
                        Color = "bgcolor=\"#dddddd\"";
                    else
                        Color = "bgcolor=\"#fcfcfc\"";
                    Final = (Hashtable)ResultF.elementAt(j);
                    int rowspan = 0;
                    ResultV1 = (Vector)Final.get("LOT_BINS");
                    rowspan = ResultV1.size();
                    Msgcx.append("<TR>\n");
                    Msgcx.append("<TD "+Color+" ALIGN=\"LEFT\" VALIGN=\"TOP\" ROWSPAN="+rowspan+">");
                    Msgcx.append(Final.get("CAT_PART_NO"));
                    Msgcx.append("</TD>\n");
                    Msgcx.append("<TD "+Color+" ALIGN=\"LEFT\" VALIGN=\"TOP\" ROWSPAN="+rowspan+">");
                    Msgcx.append(Final.get("PART_DESCRIPTION"));
                    Msgcx.append("</TD>\n");
                    Msgcx.append("<TD "+Color+" ALIGN=\"LEFT\" VALIGN=\"TOP\" ROWSPAN="+rowspan+">");
                    Msgcx.append(Final.get("TRADE_NAME"));
                    Msgcx.append("</TD>\n");
                    Msgcx.append("<TD "+Color+" ALIGN=\"LEFT\" VALIGN=\"TOP\" ROWSPAN="+rowspan+">");
                    Msgcx.append(Final.get("MFG_DESC"));
                    Msgcx.append("</TD>\n");
                    Msgcx.append("<TD "+Color+" ALIGN=\"LEFT\" VALIGN=\"TOP\" ROWSPAN="+rowspan+">");
                    Msgcx.append(Final.get("MFG_PART_NO"));
                    Msgcx.append("</TD>\n");
                    Msgcx.append("<TD "+Color+" ALIGN=\"LEFT\" VALIGN=\"TOP\" ROWSPAN="+rowspan+">");
                    Msgcx.append(Final.get("ITEM_ID"));
                    Msgcx.append("</TD>\n");
                    if(!"BIN".equalsIgnoreCase(orderBy))
                        if("LOT".equalsIgnoreCase(orderBy));
                    try
                    {
                        for(int i = 0; i < ResultV1.size(); i++)
                        {
                            Final1 = (Hashtable)ResultV1.elementAt(i);
                            if(i > 0)
                                Msgcx.append("<TR>\n");
                            if("BIN".equalsIgnoreCase(orderBy))
                            {
                                Msgcx.append("<TD "+Color+" ALIGN=\"LEFT\" VALIGN=\"TOP\">");
                                Msgcx.append(Final1.get("ON_HAND"));
                                Msgcx.append("</TD>\n");
                                Msgcx.append("<TD "+Color+" ALIGN=\"LEFT\" VALIGN=\"TOP\">");
                                Msgcx.append(Final1.get("ON_HOLD"));
                                Msgcx.append("</TD>\n");
                                Msgcx.append("<TD "+Color+" ALIGN=\"LEFT\" VALIGN=\"TOP\">");
                                Msgcx.append(Final1.get("BIN"));
                                Msgcx.append("</TD>\n");
                            } else
                            if("LOT".equalsIgnoreCase(orderBy))
                            {
                                Msgcx.append("<TD "+Color+" ALIGN=\"LEFT\" VALIGN=\"TOP\">");
                                Msgcx.append(Final1.get("ON_HAND"));
                                Msgcx.append("</TD>\n");
                                Msgcx.append("<TD "+Color+" ALIGN=\"LEFT\" VALIGN=\"TOP\">");
                                Msgcx.append(Final1.get("ON_HOLD"));
                                Msgcx.append("</TD>\n");
                                Msgcx.append("<TD "+Color+" ALIGN=\"LEFT\" VALIGN=\"TOP\">");
                                Msgcx.append(Final1.get("BIN"));
                                Msgcx.append("</TD>\n");
                                Msgcx.append("<TD "+Color+" ALIGN=\"LEFT\" VALIGN=\"TOP\">");
                                Msgcx.append(Final1.get("LOT_NUM"));
                                Msgcx.append("</TD>\n");
                            } else
                            {
                                Msgcx.append("<TD "+Color+" ALIGN=\"LEFT\" VALIGN=\"TOP\">");
                                Msgcx.append(Final1.get("ON_HAND"));
                                Msgcx.append("</TD>\n");
                                Msgcx.append("<TD "+Color+" ALIGN=\"LEFT\" VALIGN=\"TOP\">");
                                Msgcx.append(Final1.get("ON_HOLD"));
                                Msgcx.append("</TD>\n");
                                Msgcx.append("<TD "+Color+" ALIGN=\"LEFT\" VALIGN=\"TOP\">");
                                Msgcx.append(Final.get("ON_ORDER"));
                                Msgcx.append("</TD>\n");
                                Msgcx.append("<TD "+Color+" ALIGN=\"LEFT\" VALIGN=\"TOP\">");
                                Msgcx.append(Final.get("IN_PURCHASING"));
                                Msgcx.append("</TD>\n");
                            }
                            Msgcx.append("</TR>\n");
                        }

                    }
                    catch(Exception ee)
                    {
                        ee.printStackTrace();
                    }
                }

            }
            catch(Exception e1)
            {
                e1.printStackTrace();
                StringBuffer stringbuffer = Msgcx;
                return stringbuffer;
            }
        } else
        {
            Msgcx.append("<TR>\n");
            Msgcx.append("<TD> <B>No Records Found</B> </TD>\n");
            Msgcx.append("<TR>\n");
        }
        return Msgcx;
    }

    public StringBuffer createXlsWithSortedBin(String query, String client, String orderBy)
        throws Exception
    {
        db = new RayTcmISDBModel(client);
        StringBuffer Msgcx = new StringBuffer();
        try
        {
            dbrs = db.doQuery(String.valueOf(String.valueOf(query)).concat(" order by item_id"));
            rs = dbrs.getResultSet();
            //System.out.println("Done Doing Query");
            for(; rs.next(); Msgcx.append("</TR>\n"))
            {
                Msgcx.append("<TR>\n");
                Msgcx.append("<TD ALIGN=\"LEFT\">");
                Msgcx.append(BothHelpObjs.makeSpaceFromNull(rs.getString("CAT_PART_NO")));
                Msgcx.append("</TD>\n");
                Msgcx.append("<TD ALIGN=\"LEFT\">");
                Msgcx.append(BothHelpObjs.makeSpaceFromNull(rs.getString("PART_DESCRIPTION")));
                Msgcx.append("</TD>\n");
                Msgcx.append("<TD ALIGN=\"LEFT\">");
                Msgcx.append(BothHelpObjs.makeSpaceFromNull(rs.getString("TRADE_NAME")));
                Msgcx.append("</TD>\n");
                Msgcx.append("<TD ALIGN=\"LEFT\">");
                Msgcx.append(BothHelpObjs.makeSpaceFromNull(rs.getString("MFG_DESC")));
                Msgcx.append("</TD>\n");
                Msgcx.append("<TD ALIGN=\"LEFT\">");
                Msgcx.append(BothHelpObjs.makeSpaceFromNull(rs.getString("MFG_PART_NO")));
                Msgcx.append("</TD>\n");
                Msgcx.append("<TD ALIGN=\"LEFT\">");
                Msgcx.append(BothHelpObjs.makeSpaceFromNull(rs.getString("ITEM_ID")));
                Msgcx.append("</TD>\n");
                if("BIN".equalsIgnoreCase(orderBy))
                {
                    Msgcx.append("<TD ALIGN=\"LEFT\">");
                    Msgcx.append(BothHelpObjs.makeSpaceFromNull(rs.getString("bin")));
                    Msgcx.append("</TD>\n");
                    continue;
                }
                if("LOT".equalsIgnoreCase(orderBy))
                {
                    Msgcx.append("<TD ALIGN=\"LEFT\">");
                    Msgcx.append(BothHelpObjs.makeSpaceFromNull(rs.getString("QTY_AVAILABLE")));
                    Msgcx.append("</TD>\n");
                    Msgcx.append("<TD ALIGN=\"LEFT\">");
                    Msgcx.append(BothHelpObjs.makeSpaceFromNull(rs.getString("QTY_HELD")));
                    Msgcx.append("</TD>\n");
                    Msgcx.append("<TD ALIGN=\"LEFT\">");
                    Msgcx.append(BothHelpObjs.makeSpaceFromNull(rs.getString("bin")));
                    Msgcx.append("</TD>\n");
                    Msgcx.append("<TD ALIGN=\"LEFT\">");
                    Msgcx.append(BothHelpObjs.makeSpaceFromNull(rs.getString("lot_num")));
                    Msgcx.append("</TD>\n");
                    Msgcx.append("<TD ALIGN=\"LEFT\">");
                    Msgcx.append(BothHelpObjs.makeSpaceFromNull(rs.getString("receipt_id")));
                    Msgcx.append("</TD>\n");
                }
            }

        }
        catch(Exception e)
        {
            e.printStackTrace();
            throw e;
        }
        finally
        {
            dbrs.close();
            db.close();
        }
        return Msgcx;
    }

    public StringBuffer createXls(Vector tableData)
        throws Exception
    {
        StringBuffer Msgcx = new StringBuffer();
        try
        {
            for(int i = 0; i < tableData.size(); i++)
            {
                HashMap data = (HashMap)tableData.elementAt(i);
                Msgcx.append("<TR>\n");
                int j = 0;
                Msgcx.append("<TD ALIGN=\"LEFT\">");
                Msgcx.append((String)data.get("DETAIL_0"));
                Msgcx.append("</TD>\n");
                Msgcx.append("<TD ALIGN=\"LEFT\">");
                Msgcx.append((String)data.get("DETAIL_1"));
                Msgcx.append("</TD>\n");
                Msgcx.append("<TD ALIGN=\"LEFT\">");
                Msgcx.append((String)data.get("DETAIL_2"));
                Msgcx.append("</TD>\n");
                Msgcx.append("<TD ALIGN=\"LEFT\">");
                Msgcx.append((String)data.get("DETAIL_4"));
                Msgcx.append("</TD>\n");
                Msgcx.append("<TD ALIGN=\"LEFT\">");
                Msgcx.append((String)data.get("DETAIL_5"));
                Msgcx.append("</TD>\n");
                Msgcx.append("<TD ALIGN=\"LEFT\">");
                Msgcx.append((String)data.get("DETAIL_6"));
                Msgcx.append("</TD>\n");
                Msgcx.append("<TD ALIGN=\"LEFT\">");
                Msgcx.append((String)data.get("DETAIL_7"));
                Msgcx.append("</TD>\n");
                Msgcx.append("<TD ALIGN=\"LEFT\">");
                Msgcx.append((String)data.get("DETAIL_8"));
                Msgcx.append("</TD>\n");
                Msgcx.append("<TD ALIGN=\"LEFT\">");
                Msgcx.append((String)data.get("DETAIL_9"));
                Msgcx.append("</TD>\n");
                Msgcx.append("<TD ALIGN=\"LEFT\">");
                Msgcx.append((String)data.get("DETAIL_10"));
                Msgcx.append("</TD>\n");
                Msgcx.append("<TD ALIGN=\"LEFT\">");
                Msgcx.append((String)data.get("DETAIL_11"));
                Msgcx.append("</TD>\n");
                Msgcx.append("<TD ALIGN=\"LEFT\">");
                Msgcx.append((String)data.get("DETAIL_12"));
                Msgcx.append("</TD>\n");
                Msgcx.append("<TD ALIGN=\"LEFT\">");
                Msgcx.append((String)data.get("DETAIL_13"));
                Msgcx.append("</TD>\n");
                Msgcx.append("<TD ALIGN=\"LEFT\">");
                Msgcx.append((String)data.get("DETAIL_14"));
                Msgcx.append("</TD>\n");
                Msgcx.append("</TR>\n");
            }

        }
        catch(Exception e)
        {
            e.printStackTrace();
            throw e;
        }
        return Msgcx;
    }

    TcmISDBModel db;
    DBResultSet dbrs;
    ResultSet rs;
}
