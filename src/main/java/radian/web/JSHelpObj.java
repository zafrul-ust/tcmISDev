/**
 * Title:        MSDS Viewer
 * Copyright:    Copyright (c) 2001
 * Company:      Radian
 * @author Nawaz Shaik
 * @version
 *
 * Use the methods in this class to get the header and footer information.
 * You can pass a title variable to add to the Title tag in the HEAD section
 * of the HTML
 *
 */
package radian.web;

import java.util.Hashtable;
import java.util.Vector;

public  abstract class JSHelpObj
{

public static StringBuffer checkInteger()
{
StringBuffer Msgt = new StringBuffer();
//Nawaz 05-14-02
Msgt.append("var defaultEmptyOK = false\n");

Msgt.append("function isEmpty(s)\n");
Msgt.append("{   return ((s == null) || (s.length == 0))\n");
Msgt.append("}\n");
Msgt.append("function isDigit (c)\n");
Msgt.append("{   return ((c >= \"0\") && (c <= \"9\"))\n");
Msgt.append("}\n");
Msgt.append("function isInteger (s)\n");

Msgt.append("{   var i;\n");

Msgt.append("    if (isEmpty(s))\n");
Msgt.append("       if (isInteger.arguments.length == 1) return defaultEmptyOK;\n");
Msgt.append("       else return (isInteger.arguments[1] == true);\n");

Msgt.append("    for (i = 0; i < s.length; i++)\n");
Msgt.append("    {\n");
Msgt.append("        // Check that current character is number.\n");
Msgt.append("        var c = s.charAt(i);\n");

Msgt.append("        if (!isDigit(c)) return false;\n");
Msgt.append("    }\n");
Msgt.append("    return true;\n");
Msgt.append("}\n");

return Msgt;
}
public static StringBuffer buildGenericPopUpWindow()
{
StringBuffer Msgt = new StringBuffer();
Msgt.append("function openWinGeneric(destination,WinName,WinWidth, WinHeight, Resizable )\n");
Msgt.append("        {\n");
Msgt.append("   windowprops = \"toolbar=no,location=no,directories=no,menubar=no,scrollbars=yes,status=no,top=200,left=200,width=\" + WinWidth + \",height=\" + WinHeight+\",resizable=\" + Resizable;\n");
Msgt.append("    preview = window.open(destination, WinName,windowprops);\n");
//Msgt.append("        \"toolbar=no,location=no,directories=no,status=no\" +\n");
//Msgt.append("        \",menubar=no,scrollbars=yes,resizable=yes,\" +\n");
//Msgt.append("        \",width=600,height=600,top=200,left=200\");\n");
Msgt.append("        }\n");

return Msgt;
}
public static StringBuffer buildPopUpWindow(String destination, String PaperSize)
{
StringBuffer Msgt = new StringBuffer();
Msgt.append("<!-- Original:  Ronnie T. Moore, Editor -->\n");
    Msgt.append("<!-- Web Site:  The JavaScript Source -->\n");

    Msgt.append("<!-- This script and many more are available free online at -->\n");
    Msgt.append("<!-- The JavaScript Source!! http://javascript.internet.com -->\n");

    Msgt.append("<!-- Begin\n");
    Msgt.append("closetime = 0; // Close window after __ number of seconds?\n");
    Msgt.append("// 0 = do not close, anything else = number of seconds\n");
    Msgt.append("testurl3 = \""+destination+"\";\n");
    Msgt.append("testurl3 = testurl3 + \"&paperSize=\" + "+PaperSize+" ;\n");
    Msgt.append("function Start(URL, WIDTH, HEIGHT) {\n");
    Msgt.append("windowprops = \"left=50,top=50,width=\" + WIDTH + \",height=\" + HEIGHT;\n");
    Msgt.append("preview = window.open(URL, \"preview\", windowprops);\n");
    Msgt.append("if (closetime) setTimeout(\"preview.close();\", closetime*1000);\n");
    Msgt.append("}\n");
return Msgt;
}
public static StringBuffer buildCSS()
{
StringBuffer Msgt = new StringBuffer();
//Nawaz 1-30-02 CSS for the glow link
    Msgt.append("<STYLE TYPE=\"text/css\">\n");
    Msgt.append("<!--\n");

    Msgt.append(".menuh {\n");
    Msgt.append("       BORDER-COLOR : #FFFF99 ;\n");
    Msgt.append("       cursor : hand ;\n");
    Msgt.append("       Border-Left : #FFFF99 ;\n");
    Msgt.append("       Border-Top : #FFFF99 ;\n");
    Msgt.append("       color: blue;\n");
    Msgt.append("       background: #b9bfee;\n");
    Msgt.append("       }\n");
    Msgt.append(".menu  {\n");
    Msgt.append("       }\n");
    Msgt.append(".home  {\n");
    Msgt.append("        cursor : hand ;\n");
    Msgt.append("       }\n");
    Msgt.append(".menulinks{\n");
    Msgt.append("text-decoration:none;\n");
    Msgt.append("}\n");
    Msgt.append("input.buttonBlue {\n");
    Msgt.append(" background-color:#E6E8FA;\n");
    Msgt.append(" color:blue;\n");
    Msgt.append(" cursor : hand ;\n");
    Msgt.append(" text-decoration:underline;\n");
    Msgt.append(" border: 1px \n}\n");

    Msgt.append("input.buttonWhite {\n");
    Msgt.append(" background-color:#FFFFFF;\n");
    Msgt.append(" color:blue;\n");
    Msgt.append(" cursor : hand ;\n");
    Msgt.append(" text-decoration:underline;\n");
    Msgt.append(" border: 1px \n}\n");
    Msgt.append("TD {font-size: 10pt}\n");

    Msgt.append("//-->\n");
    Msgt.append("</STYLE>\n");
return Msgt;

}

public static StringBuffer SubmitOnlyOnce()
{
StringBuffer Msgt = new StringBuffer();
Msgt.append("function SubmitOnlyOnce(){ \n");
    Msgt.append("     if (submitcount == 0) \n");
    Msgt.append("     { \n");
    Msgt.append("           submitcount++; \n");
    Msgt.append("           return true; \n");
    Msgt.append("      } \n");
    Msgt.append("      else \n");
    Msgt.append("      { \n");
    Msgt.append("        alert(\"This form has already been submitted.  Thanks!\");\n");
    Msgt.append("        return false;\n");
    Msgt.append("       }\n");
    Msgt.append("}\n");
return Msgt;
}
public static StringBuffer testFutureDateScript()
{
StringBuffer Msgt = new StringBuffer();
//Nawaz 12-17-01
     Msgt.append("function checkexpirydate(objName) {\n");
    Msgt.append("                var datefield1 = objName;\n");
    Msgt.append("                if (chkexpdate(objName) == false) {\n");
    Msgt.append("                    return false;\n");
    Msgt.append("                }\n");
    Msgt.append("                else {\n");
    Msgt.append("                    return true;\n");
    Msgt.append("                }\n");
    Msgt.append("}\n");
    //
    Msgt.append("function chkexpdate(objName) {\n");
    Msgt.append("                var strDatestyle1 = \"US\"; \n");  //United States date style\n");
    Msgt.append("                var strDate1;\n");
    Msgt.append("                var strDateArray1;\n");
    Msgt.append("                var strDay1;\n");
    Msgt.append("                var strMonth1;\n");
    Msgt.append("                var strYear1;\n");
    Msgt.append("                var intday1;\n");
    Msgt.append("                var intMonth1;\n");
    Msgt.append("                var intYear1;\n");
    Msgt.append("                var booFound1 = false;\n");
    Msgt.append("                var datefield1 = objName;\n");
    Msgt.append("                var strSeparatorArray1 = new Array(\"-\",\" \",\"/\",\".\");\n");
    Msgt.append("                var intElementNr1;\n");
    Msgt.append("                var err1 = 0;\n");
    Msgt.append("                var strMonthArray1 = new Array(12);\n");
    Msgt.append("                strMonthArray1[0] = \"Jan\";\n");
    Msgt.append("                strMonthArray1[1] = \"Feb\";\n");
    Msgt.append("                strMonthArray1[2] = \"Mar\";\n");
    Msgt.append("                strMonthArray1[3] = \"Apr\";\n");
    Msgt.append("                strMonthArray1[4] = \"May\";\n");
    Msgt.append("                strMonthArray1[5] = \"Jun\";\n");
    Msgt.append("                strMonthArray1[6] = \"Jul\";\n");
    Msgt.append("                strMonthArray1[7] = \"Aug\";\n");
    Msgt.append("                strMonthArray1[8] = \"Sep\";\n");
    Msgt.append("                strMonthArray1[9] = \"Oct\";\n");
    Msgt.append("                strMonthArray1[10] = \"Nov\";\n");
    Msgt.append("                strMonthArray1[11] = \"Dec\";\n");
    Msgt.append("                strDate1 = datefield1.value;\n");
    Msgt.append("                if (strDate1.length < 1) {\n");
    Msgt.append("                    return true;\n");
    Msgt.append("                }\n");
    Msgt.append("                for (intElementNr1 = 0; intElementNr1 < strSeparatorArray1.length; intElementNr1++) {\n");
    Msgt.append("                    if (strDate1.indexOf(strSeparatorArray1[intElementNr1]) != -1) {\n");
    Msgt.append("                        strDateArray1 = strDate1.split(strSeparatorArray1[intElementNr1]);\n");
    Msgt.append("                        if (strDateArray1.length != 3) {\n");
    Msgt.append("                            err1 = 1;\n");
    Msgt.append("                            return false;\n");
    Msgt.append("                        }\n");
    Msgt.append("                        else {\n");
    Msgt.append("                            strDay1 = strDateArray1[0];\n");
    Msgt.append("                            strMonth1 = strDateArray1[1];\n");
    Msgt.append("                            strYear1 = strDateArray1[2];\n");
    Msgt.append("                        }\n");
    Msgt.append("                        booFound1 = true;\n");
    Msgt.append("                    }\n");
    Msgt.append("                }\n");
    Msgt.append("                if (booFound1 == false) {\n");
    Msgt.append("                    if (strDate1.length>5) {\n");
    Msgt.append("                        strDay1 = strDate1.substr(0, 2);\n");
    Msgt.append("                        strMonth1 = strDate1.substr(2, 2);\n");
    Msgt.append("                        strYear1 = strDate1.substr(4);\n");
    Msgt.append("                    }\n");
    Msgt.append("                }\n");
    Msgt.append("                if (strYear1.length == 2) {\n");
    Msgt.append("                    strYear1 = '20' + strYear1;\n");
    Msgt.append("                }\n");
    // US style
    Msgt.append("                if (strDatestyle1 == \"US\") {\n");
    Msgt.append("                   strTemp1 = strDay1;\n");
    Msgt.append("                    strDay1 = strMonth1;\n");
    Msgt.append("                    strMonth1 = strTemp1;\n");
    Msgt.append("                }\n");
    Msgt.append("                intday1 = parseInt(strDay1, 10);\n");
    Msgt.append("                if (isNaN(intday1)) {\n");
    Msgt.append("                    err = 2;\n");
    Msgt.append("                    return false;\n");
    Msgt.append("                }\n");
    Msgt.append("                intMonth1 = parseInt(strMonth1, 10);\n");
    Msgt.append("                if (isNaN(intMonth1)) {\n");
    Msgt.append("                    for (i = 0;i<12;i++) {\n");
    Msgt.append("                        if (strMonth1.toUpperCase() == strMonthArray1[i].toUpperCase()) {\n");
    Msgt.append("                            intMonth1 = i+1;\n");
    Msgt.append("                            strMonth1 = strMonthArray1[i];\n");
    Msgt.append("                            i = 12;\n");
    Msgt.append("                        }\n");
    Msgt.append("                    }\n");
    Msgt.append("                    if (isNaN(intMonth1)) {\n");
    Msgt.append("                        err1 = 3;\n");
    Msgt.append("                        return false;\n");
    Msgt.append("                    }\n");
    Msgt.append("                }\n");
    Msgt.append("                intYear1 = parseInt(strYear1, 10);\n");
    Msgt.append("                if (isNaN(intYear1)) {\n");
    Msgt.append("                    err1 = 4;\n");
    Msgt.append("                    return false;\n");
    Msgt.append("                }\n");
    Msgt.append("                if (intMonth1>12 || intMonth1<1) {\n");
    Msgt.append("                    err1 = 5;\n");
    Msgt.append("                    return false;\n");
    Msgt.append("                }\n");
    Msgt.append("                if ((intMonth1 == 1\n");
    Msgt.append("                     || intMonth1 == 3\n");
    Msgt.append("                     || intMonth1 == 5\n");
    Msgt.append("                     || intMonth1 == 7\n");
    Msgt.append("                     || intMonth1 == 8\n");
    Msgt.append("                     || intMonth1 == 10\n");
    Msgt.append("                     || intMonth1 == 12) && (intday1 > 31 || intday1 < 1)) {\n");
    Msgt.append("                    err1 = 6;\n");
    Msgt.append("                    return false;\n");
    Msgt.append("                }\n");
    Msgt.append("                if ((intMonth1 == 4\n");
    Msgt.append("                     || intMonth1 == 6\n");
    Msgt.append("                     || intMonth1 == 9\n");
    Msgt.append("                     || intMonth1 == 11) && (intday1 > 30 || intday1 < 1)) {\n");
    Msgt.append("                    err1 = 7;\n");
    Msgt.append("                    return false;\n");
    Msgt.append("                }\n");
    Msgt.append("                if (intMonth1 == 2) {\n");
    Msgt.append("                    if (intday1 < 1) {\n");
    Msgt.append("                        err1 = 8;\n");
    Msgt.append("                        return false;\n");
    Msgt.append("                    }\n");
    Msgt.append("                    if (LeapYear(intYear1) == true) {\n");
    Msgt.append("                        if (intday1 > 29) {\n");
    Msgt.append("                            err1 = 9;\n");
    Msgt.append("                            return false;\n");
    Msgt.append("                        }\n");
    Msgt.append("                    }\n");
    Msgt.append("                    else {\n");
    Msgt.append("                        if (intday1 > 28) {\n");
    Msgt.append("                            err1 = 10;\n");
    Msgt.append("                            return false;\n");
    Msgt.append("                        }\n");
    Msgt.append("                    }\n");
    Msgt.append("                }\n");
    Msgt.append("}\n");
    //END 12-17-01
return Msgt;
}
public static StringBuffer testDateScript()
{
StringBuffer Msgt = new StringBuffer();
Msgt.append("function checkdate(objName) {\n");
    Msgt.append("                var datefield = objName;\n");
    Msgt.append("                if (chkdate(objName) == false) {\n");
    Msgt.append("                    return false;\n");
    Msgt.append("                }\n");
    Msgt.append("                else {\n");
    Msgt.append("                    return true;\n");
    Msgt.append("                }\n");
    Msgt.append("}\n");
    //
    Msgt.append("function chkdate(objName) {\n");
    Msgt.append("                var strDatestyle = \"US\"; \n");  //United States date style\n");
    //var strDatestyle = "EU";  //European date style
    Msgt.append("                var strDate;\n");
    Msgt.append("                var strDateArray;\n");
    Msgt.append("                var strDay;\n");
    Msgt.append("                var strMonth;\n");
    Msgt.append("                var strYear;\n");
    Msgt.append("                var intday;\n");
    Msgt.append("                var intMonth;\n");
    Msgt.append("                var intYear;\n");
    Msgt.append("                var booFound = false;\n");
    Msgt.append("                var datefield = objName;\n");
    Msgt.append("                var strSeparatorArray = new Array(\"-\",\" \",\"/\",\".\");\n");
    Msgt.append("                var intElementNr;\n");
    Msgt.append("                var err = 0;\n");
    Msgt.append("                var strMonthArray = new Array(12);\n");
    Msgt.append("                strMonthArray[0] = \"Jan\";\n");
    Msgt.append("                strMonthArray[1] = \"Feb\";\n");
    Msgt.append("                strMonthArray[2] = \"Mar\";\n");
    Msgt.append("                strMonthArray[3] = \"Apr\";\n");
    Msgt.append("                strMonthArray[4] = \"May\";\n");
    Msgt.append("                strMonthArray[5] = \"Jun\";\n");
    Msgt.append("                strMonthArray[6] = \"Jul\";\n");
    Msgt.append("                strMonthArray[7] = \"Aug\";\n");
    Msgt.append("                strMonthArray[8] = \"Sep\";\n");
    Msgt.append("                strMonthArray[9] = \"Oct\";\n");
    Msgt.append("                strMonthArray[10] = \"Nov\";\n");
    Msgt.append("                strMonthArray[11] = \"Dec\";\n");
    Msgt.append("                strDate = datefield.value;\n");
    Msgt.append("                if (strDate.length < 1) {\n");
    Msgt.append("                    return true;\n");
    Msgt.append("                }\n");
    Msgt.append("                for (intElementNr = 0; intElementNr < strSeparatorArray.length; intElementNr++) {\n");
    Msgt.append("                    if (strDate.indexOf(strSeparatorArray[intElementNr]) != -1) {\n");
    Msgt.append("                        strDateArray = strDate.split(strSeparatorArray[intElementNr]);\n");
    Msgt.append("                        if (strDateArray.length != 3) {\n");
    Msgt.append("                            err = 1;\n");
    Msgt.append("                            return false;\n");
    Msgt.append("                        }\n");
    Msgt.append("                        else {\n");
    Msgt.append("                            strDay = strDateArray[0];\n");
    Msgt.append("                            strMonth = strDateArray[1];\n");
    Msgt.append("                            strYear = strDateArray[2];\n");
    Msgt.append("                        }\n");
    Msgt.append("                        booFound = true;\n");
    Msgt.append("                    }\n");
    Msgt.append("                }\n");
    Msgt.append("                if (booFound == false) {\n");
    Msgt.append("                    if (strDate.length>5) {\n");
    Msgt.append("                        strDay = strDate.substr(0, 2);\n");
    Msgt.append("                        strMonth = strDate.substr(2, 2);\n");
    Msgt.append("                        strYear = strDate.substr(4);\n");
    Msgt.append("                    }\n");
    Msgt.append("                }\n");
    Msgt.append("                if (strYear.length == 2) {\n");
    Msgt.append("                    strYear = '20' + strYear;\n");
    Msgt.append("                }\n");
    // US style
    Msgt.append("                if (strDatestyle == \"US\") {\n");
    Msgt.append("                   strTemp = strDay;\n");
    Msgt.append("                    strDay = strMonth;\n");
    Msgt.append("                    strMonth = strTemp;\n");
    Msgt.append("                }\n");
    Msgt.append("                intday = parseInt(strDay, 10);\n");
    Msgt.append("                if (isNaN(intday)) {\n");
    Msgt.append("                    err = 2;\n");
    Msgt.append("                    return false;\n");
    Msgt.append("                }\n");
    Msgt.append("                intMonth = parseInt(strMonth, 10);\n");
    Msgt.append("                if (isNaN(intMonth)) {\n");
    Msgt.append("                    for (i = 0;i<12;i++) {\n");
    Msgt.append("                        if (strMonth.toUpperCase() == strMonthArray[i].toUpperCase()) {\n");
    Msgt.append("                            intMonth = i+1;\n");
    Msgt.append("                            strMonth = strMonthArray[i];\n");
    Msgt.append("                            i = 12;\n");
    Msgt.append("                        }\n");
    Msgt.append("                    }\n");
    Msgt.append("                    if (isNaN(intMonth)) {\n");
    Msgt.append("                        err = 3;\n");
    Msgt.append("                        return false;\n");
    Msgt.append("                    }\n");
    Msgt.append("                }\n");
    Msgt.append("                intYear = parseInt(strYear, 10);\n");
    Msgt.append("                if (isNaN(intYear)) {\n");
    Msgt.append("                    err = 4;\n");
    Msgt.append("                    return false;\n");
    Msgt.append("                }\n");
    Msgt.append("                if (intMonth>12 || intMonth<1) {\n");
    Msgt.append("                    err = 5;\n");
    Msgt.append("                    return false;\n");
    Msgt.append("                }\n");
    Msgt.append("                if ((intMonth == 1\n");
    Msgt.append("                     || intMonth == 3\n");
    Msgt.append("                     || intMonth == 5\n");
    Msgt.append("                     || intMonth == 7\n");
    Msgt.append("                     || intMonth == 8\n");
    Msgt.append("                     || intMonth == 10\n");
    Msgt.append("                     || intMonth == 12) && (intday > 31 || intday < 1)) {\n");
    Msgt.append("                    err = 6;\n");
    Msgt.append("                    return false;\n");
    Msgt.append("                }\n");
    Msgt.append("                if ((intMonth == 4\n");
    Msgt.append("                     || intMonth == 6\n");
    Msgt.append("                     || intMonth == 9\n");
    Msgt.append("                     || intMonth == 11) && (intday > 30 || intday < 1)) {\n");
    Msgt.append("                    err = 7;\n");
    Msgt.append("                    return false;\n");
    Msgt.append("                }\n");
    Msgt.append("                if (intMonth == 2) {\n");
    Msgt.append("                    if (intday < 1) {\n");
    Msgt.append("                        err = 8;\n");
    Msgt.append("                        return false;\n");
    Msgt.append("                    }\n");
    Msgt.append("                    if (LeapYear(intYear) == true) {\n");
    Msgt.append("                        if (intday > 29) {\n");
    Msgt.append("                            err = 9;\n");
    Msgt.append("                            return false;\n");
    Msgt.append("                        }\n");
    Msgt.append("                    }\n");
    Msgt.append("                    else {\n");
    Msgt.append("                        if (intday > 28) {\n");
    Msgt.append("                            err = 10;\n");
    Msgt.append("                            return false;\n");
    Msgt.append("                        }\n");
    Msgt.append("                    }\n");
    Msgt.append("                }\n");
    //
    //Msgt.append("                 alert ( \"entered day \" + strDay + \" month \" + strMonth + \" year \" + strYear);\n");
    Msgt.append("                 var now = new Date();\n");
    Msgt.append("                 var year  = now.getYear()\n");
    Msgt.append("                 if ( year < 2000) \n");
    Msgt.append("                 { year = year + 1900; } \n");
    //Msgt.append("                 alert ( \"TODAYS YEAR: \" + year); \n");
    Msgt.append("                 var day  = now.getDate();\n");
    Msgt.append("                 var month  = now.getMonth() + 1;\n");
//        Msgt.append("                 alert ( \"Todays day: \" + day  + \"year: \" + year + \"month: \" + month   );\n");
    Msgt.append("                 if (  strYear > year  )\n");
    Msgt.append("                 { \n");
    //Msgt.append("                   alert ( \" Entered Year is not valid \" ) ;\n");
    Msgt.append("                   return false;\n");
    Msgt.append("                 }\n");
    Msgt.append("                 if ( strYear == year )\n");
    Msgt.append("                 {\n");
    Msgt.append("                    if ( strMonth > month )\n");
    Msgt.append("                    {\n");
    //Msgt.append("                      alert ( \" Entered month is not valid \" );\n");
    Msgt.append("                      return false;\n");
    Msgt.append("                    }\n");
    Msgt.append("                    if ( strMonth == month )\n");
    Msgt.append("                    {\n");
    Msgt.append("                        if ( strDay > day )\n");
    Msgt.append("                        {\n");
    //Msgt.append("                          alert ( \" Entered day is not valid \" )\n");
    Msgt.append("                          return false;\n");
    Msgt.append("                        }\n");
    Msgt.append("                    }\n");
    Msgt.append("                 }\n");
    Msgt.append("                return true;\n");
    Msgt.append("}\n");

    Msgt.append("function LeapYear(intYear) {\n");
    Msgt.append("                if (intYear % 100 == 0) {\n");
    Msgt.append("                    if (intYear % 400 == 0) { return true; }\n");
    Msgt.append("                }\n");
    Msgt.append("                else {\n");
    Msgt.append("                    if ((intYear % 4) == 0) { return true; }\n");
    Msgt.append("                }\n");
    Msgt.append("                return false;\n");
    Msgt.append("}\n");
return Msgt;

 }

	 public static StringBuffer createCountryStateJs( Hashtable finalresultdata )
		 {
		 StringBuffer Msgjs=new StringBuffer();
		 Vector hubid= ( Vector ) finalresultdata.get( "COUNTRYV" );
		 String tmp="";
		 String althubid="var althubid = new Array(";
		 String altinvid="var altinvid = new Array();\n ";
		 String altinvName="var altinvName = new Array();\n ";

		 int i=0;

		 for ( int ii=0; ii < hubid.size(); ii++ )
		 {
			 String wacid= ( String ) hubid.elementAt( ii );
			 althubid+="\"" + wacid + "\"" + ",";

			 Hashtable fh= ( Hashtable ) finalresultdata.get( wacid );
			 Vector cabIDv= ( Vector ) fh.get( "STATE_ABBREV" );
			 Vector invName= ( Vector ) fh.get( "STATE" );

			 altinvid+="altinvid[\"" + wacid + "\"] = new Array(";
			 altinvName+="altinvName[\"" + wacid + "\"] = new Array(";
			 for ( i=0; i < cabIDv.size(); i++ )
			 {
			 String facID= ( String ) cabIDv.elementAt( i );
			 String invgrpname= ( String ) invName.elementAt( i );
			 tmp+="\"" + facID + "\"" + ",";
			 altinvid+="\"" + facID + "\"" + ",";
			 altinvName+="\"" + invgrpname + "\"" + ",";
			 }
			 //removing the last commas ','
			 altinvid=altinvid.substring( 0,altinvid.length() - 1 ) + ");\n";
			 altinvName=altinvName.substring( 0,altinvName.length() - 1 ) + ");\n";
		 }

		 if ( althubid.indexOf( "," ) > 1 )
		 {
			 althubid=althubid.substring( 0,althubid.length() - 1 ) + ");\n";
		 }

		 Msgjs.append( althubid + " " + altinvid + " " + altinvName );

		 return Msgjs;
		 }

}