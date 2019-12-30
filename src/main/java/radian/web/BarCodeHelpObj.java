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
 * 07-30-04 Changing the templates to be based on inventory group instead of hub. Using one method to do the loading templates job
 */
package radian.web;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Vector;

public class BarCodeHelpObj
{

  public BarCodeHelpObj()
  {

  }

  private String BarTextOut;
  private String BarTextIn;
  private String BarTextInA;
  private String BarTextInB;
  private String TempString;

  private int Sum;
  private Integer II;
//private String ThisChar;
  private int ThisChar;
  private int CharValue;
  private int CheckSumValue;
  private char CheckSum;
  private int Subset;
  private String StartChar;
  private int Weighting;
  private Integer UCC;

  String VB_Name="EFBAR128";

  /*' Copyright 2000 by Elfring Fonts Inc. All rights reserved. This code
   ' may not be modified or altered in any way.
   'Functions in this file:
   ' Bar128A(Text)     -> convert text to bar code 128 subset A
   ' Bar128Aucc(Text)  -> convert text to bar code 128 subset A, UCC/EAN
   ' Bar128B(Text)     -> convert text to bar code 128 subset B
   ' Bar128Bucc(Text)  -> convert text to bar code 128 subset B, UCC/EAN
   ' Bar128C(Text)     -> convert text to bar code 128 subset C
   ' Bar128Cucc(Text)  -> convert text to bar code 128 subset C, UCC/EAN
   ' This function converts a string into a format compatible with Elfring
   ' Fonts Inc bar codes. This conversion is for subset A only. It adds the
   ' start character, scans and converts data, adds a checksum and a stop
   ' character. Note that lower case letters are interpreted as control
   ' characters in subset A!
   '------------------------------------------------------
   */
  public String Bar128A( String BarTextInA ) throws Exception
  {
	String Barcode128A="";
try
	{
	  Barcode128A=Bar128AB( BarTextInA,0 );
	}
	catch ( Exception ex )
	{
	  throw ex;
	}

	return Barcode128A;
  }

  /*
   ' This function converts a string into a format compatible with Elfring
   ' Fonts Inc bar codes. This conversion is for subset C only. It adds the
   ' start character, throws away any non-numeric data, adds a leading zero if
   ' there aren't an even number of digits, scans and converts data into data
   ' pairs, adds a checksum and a stop character.
   '-----------------------------------------------------*/
  public String Bar128C( String BarTextIn ) throws Exception
  {
	String Barcode128c="";

try
	{
	  Barcode128c=Bar128SubsetC( BarTextIn,0 );
	}
	catch ( Exception ee )
	{
	  throw ee;
	}

	//System.out.println(Barcode128c);

	return Barcode128c;
  }


  /*
   ' This function converts a string into a format compatible with Elfring
   ' Fonts Inc bar codes. This conversion is for UCC/EAN subset C only. It adds
   ' the two start characters, throws away any non-numeric data, adds a leading
   ' zero if there aren't an even number of digits, scans and converts data into
   ' data pairs, adds a checksum and a stop character.
   '--------------------------------------------------------
   */

  public String Bar128Cucc( String BarTextIn ) throws Exception
  {
	String Bar128Cucc="";

try
	{
	  Bar128Cucc=Bar128SubsetC( BarTextIn,1 );

	}
	catch ( Exception ex )
	{
	  throw ex;
	}
	return Bar128Cucc;
  }

  /*'-----------------------------------------------------------------------------
	' Convert input string to bar code 128 A or B format, Pass Subset 0 = A, 1 = B
	'-----------------------------------------------------------------------------
   */

  private String Bar128AB( String BarTextIn,int Subset ) throws Exception
  {
	//' Initialize input and output strings
	String BarCodeOut="";
	BarTextIn=BarTextIn.trim();
	BarTextOut="";
// ' Set up for the subset we are in
	if ( Subset == 0 )
	{
	  Sum=103;
	  StartChar="{";
	}
	else
	{
	  Sum=104;
	  StartChar="|";
	}

// ' Calculate the checksum, mod 103 and build output string
	for ( int II=0; II < BarTextIn.length(); II++ )
	{
//  'Find the ASCII value of the current character
//   ThisChar= ( Asc( Mid( BarTextIn,II,1 ) ) );
	  char tmpchar=BarTextIn.charAt( II );
	  ThisChar= ( int ) tmpchar;
	  //System.out.println("Character "+tmpchar+"  ASCII value "+ThisChar);
//   'Calculate the bar code 128 value

//   'Calculate the bar code 128 value
	  if ( ThisChar < 123 )
	  {
		CharValue=ThisChar - 32;
	  }
	  else
	  {
		CharValue=ThisChar - 70;
	  }
//   'add this value to sum for checksum work
	  Sum=Sum + ( CharValue * ( II + 1 ) );

//   'Now work on output string, no spaces in TrueType fonts
//   if ( Mid(BarTextIn,II,1) = " ")
	  //if ( BarTextIn.substring(II,(II+1)) == " ")
	  if ( ThisChar == 32 )
	  {
		BarTextOut=BarTextOut + ( char ) ( 174 );
		//System.out.println("Found a Blank");
	  }
	  else
	  {
		BarTextOut=BarTextOut + tmpchar;
	  }
	}
// ' Find the remainder when Sum is divided by 103
	CheckSumValue= ( Sum % 103 );
// ' Translate that value to an ASCII character
	if ( CheckSumValue > 90 )
	{
	  CheckSum= ( char ) ( CheckSumValue + 70 );
	}
	else if ( CheckSumValue > 0 )
	{
	  CheckSum= ( char ) ( CheckSumValue + 32 );
	}
	else
	{
	  CheckSum= ( char ) ( 174 );
	}

// 'Build ouput string, trailing space is for Windows rasterization bug
	BarCodeOut=StartChar + BarTextOut + CheckSum + "~ ";

// 'Return the string
	return BarCodeOut;
  }


  /*'---------------------------------------------------------------------------
   ' Convert input string to bar code 128 C format, Pass UCC 0 = no, 1 = yes
   '---------------------------------------------------------------------------
   */

  private String Bar128SubsetC( String BarTextIn,int UCC ) throws Exception
  {
//' Initialize input and output strings
	String BarCodeOut="";
	BarTextOut="";

//' Throw away non-numeric data
	TempString=BarTextIn;
	// If not an even number of digits, add a leading 0
	if ((TempString.length() % 2) == 1) {
	 TempString = "0" + TempString;
	}

//' If UCC = 0, then normal start, otherwise UCC/EAN start

	Sum=105;
	StartChar="}";
	Weighting=1;

	String CharValues="";
//' Calculate the checksum, mod 103 and build output string
	for ( int I=0; I < TempString.length(); I=I + 2 )
	{
	  //'Break string into pairs of digits and get value
	  CharValues=TempString.substring( I,I + 2 );
	  //System.out.println("String To Parse "+CharValues);
	  CharValue=Integer.parseInt( CharValues );
	  //'Multiply value times weighting and add to sum
	  Sum=Sum + ( CharValue * Weighting );

	  Weighting=Weighting + 1;

	  //'translate value to ASCII and save in BarTextOut
	  if ( CharValue < 90 )
		BarTextOut=BarTextOut + ( char ) ( CharValue + 33 );
	  else if ( CharValue < 171 )
		BarTextOut=BarTextOut + ( char ) ( CharValue + 71 );
	  else
		BarTextOut=BarTextOut + ( char ) ( CharValue + 76 );
	}

//' Find the remainder when Sum is divided by 103
	CheckSumValue= ( Sum % 103 );
//' Translate that value to an ASCII character
	if ( CheckSumValue < 90 )
	  CheckSum= ( char ) ( CheckSumValue + 33 );
	else if ( CheckSumValue < 100 )
	  CheckSum= ( char ) ( CheckSumValue + 71 );
	else
	  CheckSum= ( char ) ( CheckSumValue + 76 );


//'Build ouput string, trailing space for Windows rasterization bug
	BarCodeOut=StartChar + BarTextOut + CheckSum + "~ ";

//'Return the string
//System.out.println("The out String "+BarCodeOut);

	return BarCodeOut;

  }

  /**
   * This Method gives back the ZPL for all the lablel formats specified
   */
  public StringBuffer loadtemplates(Vector filepaths)
   {
	 StringBuffer itmlbltemp=new StringBuffer();
	 String linefeedd="";
	 linefeedd+= ( char ) ( 13 );
	 linefeedd+= ( char ) ( 10 );
	 String templatpath=radian.web.tcmisResourceLoader.getlabeltempplatepath();

	 try
	 {
	   for(int k = 0; k < filepaths.size(); k++)
	   {
		 String filepath = (String) filepaths.elementAt(k);

		 BufferedReader buf=new BufferedReader( new FileReader( "" + templatpath + filepath+"" ) );
		 int i=0;
     String line = null; //not declared within while loop
    while (( line = buf.readLine()) != null) {
     //String temp = buf.readLine();
     itmlbltemp.append(line + linefeedd);
		 }
     buf.close();
    }
	 }
	 catch ( Exception ex )
	 {
	   ex.printStackTrace();
	 }

	 return itmlbltemp;
   }


  /**
   * This Method gives back the ZPL for all the item lablels
   */
/*  public StringBuffer itemlabeltmplate(String printerpath)
  {
	StringBuffer itmlbltemp=new StringBuffer();
	String templatpath=radian.web.tcmisResourceLoader.getlabeltempplatepath();
	String linefeedd="";
	linefeedd+= ( char ) ( 13 );
	linefeedd+= ( char ) ( 10 );

	try
	{
	  BufferedReader buf=new BufferedReader( new FileReader( "" + templatpath + "itemlbl"+printerpath+".txt" ) );
	  int i=0;
	  while ( ( i=buf.read() ) != -1 )
	  {
		String temp=buf.readLine();

		itmlbltemp.append( "^" + temp + linefeedd);
	  }
	}
	catch ( Exception ex )
	{
	  ex.printStackTrace();
	}

	//Decatur Label
	/*itmlbltemp.append( "^XA" + linefeedd );
	itmlbltemp.append( "^DFDECATURITEM^FS" + linefeedd );
	itmlbltemp.append( "^LH0,0" + linefeedd );
	itmlbltemp.append( "^FO30,30^ARN,27^FN1^FS" + linefeedd );
	itmlbltemp.append( "^FO450,30^ARN,27^FDSTORAGE TEMP:^FS" + linefeedd );
	itmlbltemp.append( "^FO660,30^ARN,27^FN2^FS" + linefeedd );
	itmlbltemp.append( "^FO30,60^ARN,27^FDEXP DATE:^FS" + linefeedd );
	itmlbltemp.append( "^FO175,60^ARN,27^FN3^FS" + linefeedd );
	itmlbltemp.append( "^FO30,90^ARN,27^FDLOT#:^FS" + linefeedd );
	itmlbltemp.append( "^FO120,90^ARN,27^FN4^FS" + linefeedd );
	itmlbltemp.append( "^FO30,120^ARN,27^FDPART#:^FS" + linefeedd );
	itmlbltemp.append( "^FO140,120^ARN,27^FN5^FS" + linefeedd );
	itmlbltemp.append( "^FO450,120^AE^BCN,100^FN6^FS" + linefeedd );
	itmlbltemp.append( "^XZ" + linefeedd );

	//Decatur Kit Label
	itmlbltemp.append( "^XA" + linefeedd );
	itmlbltemp.append( "^DFDECATURKIT^FS" + linefeedd );
	itmlbltemp.append( "^LH0,0" + linefeedd );
	itmlbltemp.append( "^FO30,30^A0,27^FDDPM:^FS" + linefeedd );
	itmlbltemp.append( "^FO110,30^A0,27^FN1^FS" + linefeedd );
	itmlbltemp.append( "^FO450,30^A0,27^FDSTORAGE TEMP:^FS" + linefeedd );
	itmlbltemp.append( "^FO650,30^A0,27^FN2^FS" + linefeedd );
	itmlbltemp.append( "^FO30,60^A0,27^FDEXP DATE:^FS" + linefeedd );
	itmlbltemp.append( "^FO170,60^A0,27^FN3^FS" + linefeedd );
	itmlbltemp.append( "^FO30,90^A0,27^FDLOT#:^FS" + linefeedd );
	itmlbltemp.append( "^FO110,90^A0,27^FN4^FS" + linefeedd );
	itmlbltemp.append( "^FO30,120^A0,27^FDPART#:^FS" + linefeedd );
	itmlbltemp.append( "^FO120,120^A0,27^FN5^FS" + linefeedd );
	itmlbltemp.append( "^FO30,150^A0,27^FB700,4,0,R^FN6^FS" + linefeedd );
	itmlbltemp.append( "^XZ" + linefeedd );

	//Standard Item Label
	itmlbltemp.append( "^XA" + linefeedd );
	itmlbltemp.append( "^DFITEM^FS" + linefeedd );
	itmlbltemp.append( "^LH0,0" + linefeedd );
	itmlbltemp.append( "^FO30,30^AE^BCN,100^FN1^FS" + linefeedd );
	itmlbltemp.append( "^FO400,30^A0N,27^FDExp:^FS" + linefeedd );
	itmlbltemp.append( "^FO475,30^A0N,27^FN2^FS" + linefeedd );
	itmlbltemp.append( "^FO400,60^A0N,27^FDRecert #:^FS" + linefeedd );
	itmlbltemp.append( "^FO500,60^A0N,27^FN3^FS" + linefeedd );
	itmlbltemp.append( "^FO400,90^A0N,27^FDParts:^FS" + linefeedd );
	itmlbltemp.append( "^FO500,90^A0N,27^FN4^FS" + linefeedd );
	itmlbltemp.append( "^FO500,120^A0N,27^FN5^FS" + linefeedd );
	itmlbltemp.append( "^FO500,150^A0N,27^FN6^FS" + linefeedd );
	itmlbltemp.append( "^FO500,180^A0N,27^FN7^FS" + linefeedd );
	itmlbltemp.append( "^FO500,210^A0N,27^FN8^FS" + linefeedd );
	itmlbltemp.append( "^FO500,240^A0N,27^FN9^FS" + linefeedd );
	itmlbltemp.append( "^FO500,270^A0N,27^FN10^FS" + linefeedd );
	itmlbltemp.append( "^FO30,200^A0N,27^FDItem:^FS" + linefeedd );
	itmlbltemp.append( "^FO140,200^A0N,27^FN11^FS" + linefeedd );
	itmlbltemp.append( "^FO30,230^A0N,27^FDLot:^FS" + linefeedd );
	itmlbltemp.append( "^FO140,230^A0N,27^FN12^FS" + linefeedd );
	itmlbltemp.append( "^FO30,260^A0N,27^FDBin:^FS" + linefeedd );
	itmlbltemp.append( "^FO140,260^A0N,27^FN13^FS" + linefeedd );
	itmlbltemp.append( "^XZ" + linefeedd );

	//Seagate Item Label
	itmlbltemp.append( "^XA" + linefeedd );
	itmlbltemp.append( "^DFSEAGATE^FS" + linefeedd );
	itmlbltemp.append( "^LH0,0" + linefeedd );
	itmlbltemp.append( "^FO150,20^A0,100^FN1^FS" + linefeedd );
	itmlbltemp.append( "^FO150,120^A0,100^FN2^FS" + linefeedd );
	itmlbltemp.append( "^FO150,220^A0,100^FN3^FS" + linefeedd );
	itmlbltemp.append( "^FO150,320^A0,100^FN4^FS" + linefeedd );
	itmlbltemp.append( "^FO150,420^A0,100^FN5^FS" + linefeedd );
	itmlbltemp.append( "^FO150,520^A0,100^FN6^FS" + linefeedd );
	itmlbltemp.append( "^FO330,30^AE^BCN,100^FN7^FS" + linefeedd );
	itmlbltemp.append( "^FO750,30^A0,40^FDExp:^FS" + linefeedd );
	itmlbltemp.append( "^FO850,30^A0,40^FN8^FS" + linefeedd );
	itmlbltemp.append( "^FO750,70^A0,40^FDPart:^FS" + linefeedd );
	itmlbltemp.append( "^FO850,70^A0,40^FN9^FS" + linefeedd );
	itmlbltemp.append( "^FO330,200^A0,30^FB900,3^FN10^FS" + linefeedd );
	itmlbltemp.append( "^FO330,300^A0,40^FDSignal Word:^FS" + linefeedd );
	itmlbltemp.append( "^FO580,300^A0,40^FN11^FS" + linefeedd );
	itmlbltemp.append( "^FO330,350^A0,40^FDHazard:^FS" + linefeedd );
	itmlbltemp.append( "^FO500,350^A0,40^FB700,3^FN12^FS" + linefeedd );
	itmlbltemp.append( "^FO330,500^A0,40^FDLot:^FS" + linefeedd );
	itmlbltemp.append( "^FO430,500^A0,40^FN13^FS" + linefeedd );
	itmlbltemp.append( "^FO750,500^A0,40^FDItem:^FS" + linefeedd );
	itmlbltemp.append( "^FO850,500^A0,40^FN14^FS" + linefeedd );
	itmlbltemp.append( "^FO330,550^A0,40^FDBin:^FS" + linefeedd );
	itmlbltemp.append( "^FO430,550^A0,40^FN15^FS" + linefeedd );
	itmlbltemp.append( "^FO750,550^A0,40^FDDOR:^FS" + linefeedd );
	itmlbltemp.append( "^FO850,550^A0,40^FN16^FS" + linefeedd );
	itmlbltemp.append( "^XZ" + linefeedd );

	itmlbltemp.append( "^XA" + linefeedd );
	itmlbltemp.append( "^DFTAPE^FS" + linefeedd );
	itmlbltemp.append( "^LH0,0" + linefeedd );
	itmlbltemp.append( "^FO10,15^A0,60^FN1^FS" + linefeedd );
	itmlbltemp.append( "^FO275,15^A0,27^FDItem:^FS" + linefeedd );
	itmlbltemp.append( "^FO350,15^A0,27^FN2^FS" + linefeedd );
	itmlbltemp.append( "^FO450,15^A0,27^FDExp:^FS" + linefeedd );
	itmlbltemp.append( "^FO515,15^A0,27^FN3^FS" + linefeedd );
	itmlbltemp.append( "^FO275,45^A0,27^FDParts:^FS" + linefeedd );
	itmlbltemp.append( "^FO350,45^A0,27^FN4^FS" + linefeedd );
	itmlbltemp.append( "^FO10,75^A0,27^FDLot:^FS" + linefeedd );
	itmlbltemp.append( "^FO60,75^A0,27^FN5^FS" + linefeedd );
	itmlbltemp.append( "^XZ" + linefeedd );


	itmlbltemp.append( "^XA" + linefeedd );
	itmlbltemp.append( "^DFPMC^FS" + linefeedd );
	itmlbltemp.append( "^LH0,0" + linefeedd );
	itmlbltemp.append( "^FO400,60^A0R,400,250^FN1^FS" + linefeedd );
	itmlbltemp.append( "^FO25,60^A0R,400,250^FN2^FS" + linefeedd );
	itmlbltemp.append( "^XZ" + linefeedd );
*//*
	return itmlbltemp;
  }

  /**
   * This Method gives back the ZPL for all the Box lablels
   */
  /*public StringBuffer boxlabeltmplate(String printerpath)
  {
	StringBuffer itmlbltemp=new StringBuffer();
	String linefeedd="";
	linefeedd+= ( char ) ( 13 );
	linefeedd+= ( char ) ( 10 );
	String templatpath=radian.web.tcmisResourceLoader.getlabeltempplatepath();
	try
	{
	  BufferedReader buf=new BufferedReader( new FileReader( "" + templatpath + "boxlbl"+printerpath+".txt" ) );
	  int i=0;
	  while ( ( i=buf.read() ) != -1 )
	  {
		String temp=buf.readLine();
		itmlbltemp.append( "^" + temp + linefeedd);
	  }
	}
	catch ( Exception ex )
	{
	  ex.printStackTrace();
	}

	/*itmlbltemp.append( "^XA" + linefeedd );
	itmlbltemp.append( "^DFBOXLABEL^FS" + linefeedd );
	itmlbltemp.append( "^LH0,0" + linefeedd );
	itmlbltemp.append( "^FO750,30^A0R,54^FB300,1,0,R^FDRequestor:^FS" + linefeedd );
	itmlbltemp.append( "^FO750,350^A0R,54^FN1^FS" + linefeedd );
	itmlbltemp.append( "^FO650,30^A0R,54^FB300,1,0,R^FDEnd User:^FS" + linefeedd );
	itmlbltemp.append( "^FO650,350^A0R,54^FN2^FS" + linefeedd );
	itmlbltemp.append( "^FO550,30^A0R,54^FB300,1,0,R^FDFacility:^FS" + linefeedd );
	itmlbltemp.append( "^FO550,350^A0R,54^FN3^FS" + linefeedd );
	itmlbltemp.append( "^FO450,30^A0R,54^FB300,1,0,R^FDWork Area:^FS" + linefeedd );
	itmlbltemp.append( "^FO450,350^A0R,54^FN4^FS" + linefeedd );
	itmlbltemp.append( "^FO350,30^A0R,54^FB300,1,0,R^FDDeliver To:^FS" + linefeedd );
	itmlbltemp.append( "^FO350,350^A0R,54^FN5^FS" + linefeedd );
	itmlbltemp.append( "^FO250,30^A0R,54^FB300,1,0,R^FDMR-Line:^FS" + linefeedd );
	itmlbltemp.append( "^FO250,350^A0R,54^FN6^FS" + linefeedd );
	itmlbltemp.append( "^FO150,30^A0R,54^FB300,1,0,R^FDPart Number:^FS" + linefeedd );
	itmlbltemp.append( "^FO150,350^A0R,54^FN7^FS" + linefeedd );
	itmlbltemp.append( "^XZ" + linefeedd );
*//*
	return itmlbltemp;
  }

/**
 * This Method gives back the ZPL for all the cabinet lablels
 */
 /* public StringBuffer cabinetlabeltmplate(String printerpath)
  {
	StringBuffer itmlbltemp=new StringBuffer();
	String linefeedd="";
	linefeedd+= ( char ) ( 13 );
	linefeedd+= ( char ) ( 10 );
	String templatpath=radian.web.tcmisResourceLoader.getlabeltempplatepath();
	try
	{
	  BufferedReader buf=new BufferedReader( new FileReader( "" + templatpath + "cablbl"+printerpath+".txt" ) );
	  int i=0;
	  while ( ( i=buf.read() ) != -1 )
	  {
		String temp=buf.readLine();
		itmlbltemp.append( "^" + temp + linefeedd);
	  }
	}
	catch ( Exception ex )
	{
	  ex.printStackTrace();
	}

	/*itmlbltemp.append( "^XA^DFCABINET^FS" + linefeedd );
	itmlbltemp.append( "^LH0,0" + linefeedd );
	itmlbltemp.append( "^FO290,30^AE^BCN,100^FN1^FS" + linefeedd );
	itmlbltemp.append( "^FO0,225^A0N,54^FB900,1,,C^FN2^FS" + linefeedd );
	itmlbltemp.append( "^XZ" + linefeedd );

	itmlbltemp.append( "^XA" + linefeedd );
	itmlbltemp.append( "^DFDECATURBIN^FS" + linefeedd );
	itmlbltemp.append( "^LH0,0" + linefeedd );
	itmlbltemp.append( "^FO30,30^A0N,27^FDMFG:^FS" + linefeedd );
	itmlbltemp.append( "^FO100,30^A0N,27^FN1^FS" + linefeedd );
	itmlbltemp.append( "^FO30,60^A0N,27^FDPART#:^FS" + linefeedd );
	itmlbltemp.append( "^FO130,60^A0N,27^FN2^FS" + linefeedd );
	itmlbltemp.append( "^FO30,90^A0N,27^FDDESC:^FS" + linefeedd );
	itmlbltemp.append( "^FO130,90^A0N,27^FN3^FS" + linefeedd );
	itmlbltemp.append( "^FO30,120^A0N,27^FDMSDS:^FS" + linefeedd );
	itmlbltemp.append( "^FO130,120^A0N,27^FN4^FS" + linefeedd );
	itmlbltemp.append( "^FO300,120^A0N,27^FDPKG:^FS" + linefeedd );
	itmlbltemp.append( "^FO375,120^A0N,27^FN5^FS" + linefeedd );
	itmlbltemp.append( "^FO225,180^AE^BCN,75^FN6^FS" + linefeedd );
	itmlbltemp.append( "^XZ" + linefeedd );

	itmlbltemp.append( "^XA" + linefeedd );
	itmlbltemp.append( "^DFDECATURDESCRIPTION^FS" + linefeedd );
	itmlbltemp.append( "^LH0,0" + linefeedd );
	itmlbltemp.append( "^FO10,80^A0N,78^FN1^FS" + linefeedd );
	itmlbltemp.append( "^FO10,200^A0N,78^FN2^FS" + linefeedd );
	itmlbltemp.append( "^XZ" + linefeedd );

	itmlbltemp.append( "^XA" + linefeedd );
	itmlbltemp.append( "^DFBIN^FS" + linefeedd );
	itmlbltemp.append( "^LH0,0" + linefeedd );
	itmlbltemp.append( "^FO200,30^AE^BCN,75^FN1^FS" + linefeedd );
	itmlbltemp.append( "^FO30,180^A0N,27^FN2^FS" + linefeedd );
	itmlbltemp.append( "^FO400,180^A0N,27^FN3^FS" + linefeedd );
	itmlbltemp.append( "^FO30,230^A0N,27^FDDESC:^FS" + linefeedd );
	itmlbltemp.append( "^FO120,230^A0N,27^FN4^FS" + linefeedd );
	itmlbltemp.append( "^FO30,260^A0N,27^FN5^FS" + linefeedd );
	itmlbltemp.append( "^XZ" + linefeedd );
*/
	/*return itmlbltemp;
  }
*/
}