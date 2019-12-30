<?xml version="1.0" encoding="UTF-8" ?>
 <xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:fo="http://www.w3.org/1999/XSL/Format">
<xsl:output method = "xml"/>


<xsl:template match="/">
  <xsl:apply-templates select="invoices"/>
</xsl:template>


<xsl:template match="invoices">

<?mso-application progid="Word.Document"?>
<w:wordDocument xmlns:w="http://schemas.microsoft.com/office/word/2003/wordml" xmlns:v="urn:schemas-microsoft-com:vml" xmlns:w10="urn:schemas-microsoft-com:office:word" xmlns:sl="http://schemas.microsoft.com/schemaLibrary/2003/core" xmlns:aml="http://schemas.microsoft.com/aml/2001/core" xmlns:wx="http://schemas.microsoft.com/office/word/2003/auxHint" xmlns:o="urn:schemas-microsoft-com:office:office" xmlns:dt="uuid:C2F41010-65B3-11d1-A29F-00AA00C14882" xmlns:wsp="http://schemas.microsoft.com/office/word/2003/wordml/sp2" xmlns:st1="urn:schemas-microsoft-com:office:smarttags" w:macrosPresent="no" w:embeddedObjPresent="no" w:ocxPresent="no" xml:space="preserve">
<w:ignoreElements w:val="http://schemas.microsoft.com/office/word/2003/wordml/sp2"/>
<o:SmartTagType o:namespaceuri="urn:schemas-microsoft-com:office:smarttags" o:name="country-region"/>
<o:SmartTagType o:namespaceuri="urn:schemas-microsoft-com:office:smarttags" o:name="PostalCode"/><o:SmartTagType o:namespaceuri="urn:schemas-microsoft-com:office:smarttags" o:name="State"/><o:SmartTagType o:namespaceuri="urn:schemas-microsoft-com:office:smarttags" o:name="City"/><o:SmartTagType o:namespaceuri="urn:schemas-microsoft-com:office:smarttags" o:name="place"/><o:DocumentProperties><o:Title> </o:Title><o:Author>Mike Auty</o:Author><o:LastAuthor>HaasTCM</o:LastAuthor><o:Revision>2</o:Revision><o:TotalTime>2</o:TotalTime><o:LastPrinted>2010-02-10T20:39:00Z</o:LastPrinted><o:Created>2010-03-10T21:39:00Z</o:Created><o:LastSaved>2010-03-10T21:39:00Z</o:LastSaved><o:Pages>1</o:Pages><o:Words>2</o:Words><o:Characters>12</o:Characters><o:Company>Haas TCM</o:Company><o:Lines>1</o:Lines><o:Paragraphs>1</o:Paragraphs><o:CharactersWithSpaces>13</o:CharactersWithSpaces><o:Version>11.0000</o:Version></o:DocumentProperties><w:fonts><w:defaultFonts w:ascii="Times" w:fareast="Times" w:h-ansi="Times" w:cs="Times New Roman"/><w:font w:name="Helvetica"><w:panose-1 w:val="020B0604020202020204"/><w:charset w:val="00"/><w:family w:val="Swiss"/><w:pitch w:val="variable"/><w:sig w:usb-0="20007A87" w:usb-1="80000000" w:usb-2="00000008" w:usb-3="00000000" w:csb-0="000001FF" w:csb-1="00000000"/></w:font><w:font w:name="Times"><w:panose-1 w:val="02020603050405020304"/><w:charset w:val="00"/><w:family w:val="Roman"/><w:pitch w:val="variable"/><w:sig w:usb-0="20007A87" w:usb-1="80000000" w:usb-2="00000008" w:usb-3="00000000" w:csb-0="000001FF" w:csb-1="00000000"/></w:font></w:fonts><w:styles><w:versionOfBuiltInStylenames w:val="4"/><w:latentStyles w:defLockedState="off" w:latentStyleCount="156"/><w:style w:type="paragraph" w:default="on" w:styleId="Normal"><w:name w:val="Normal"/><w:rsid w:val="00596B75"/><w:rPr><wx:font wx:val="Times"/><w:sz w:val="24"/><w:lang w:val="EN-US" w:fareast="EN-US" w:bidi="AR-SA"/></w:rPr></w:style><w:style w:type="character" w:default="on" w:styleId="DefaultParagraphFont"><w:name w:val="Default Paragraph Font"/><w:semiHidden/></w:style><w:style w:type="table" w:default="on" w:styleId="TableNormal"><w:name w:val="Normal Table"/><wx:uiName wx:val="Table Normal"/><w:semiHidden/><w:rPr><wx:font wx:val="Times"/></w:rPr><w:tblPr><w:tblInd w:w="0" w:type="dxa"/><w:tblCellMar><w:top w:w="0" w:type="dxa"/><w:left w:w="108" w:type="dxa"/><w:bottom w:w="0" w:type="dxa"/><w:right w:w="108" w:type="dxa"/></w:tblCellMar></w:tblPr></w:style><w:style w:type="list" w:default="on" w:styleId="NoList"><w:name w:val="No List"/><w:semiHidden/></w:style><w:style w:type="paragraph" w:styleId="Header"><w:name w:val="header"/><wx:uiName wx:val="Header"/><w:basedOn w:val="Normal"/><w:rsid w:val="00596B75"/><w:pPr><w:pStyle w:val="Header"/><w:tabs><w:tab w:val="center" w:pos="4320"/><w:tab w:val="right" w:pos="8640"/></w:tabs></w:pPr><w:rPr><wx:font wx:val="Times"/></w:rPr></w:style><w:style w:type="paragraph" w:styleId="Footer"><w:name w:val="footer"/><wx:uiName wx:val="Footer"/><w:basedOn w:val="Normal"/><w:rsid w:val="00596B75"/><w:pPr><w:pStyle w:val="Footer"/><w:tabs><w:tab w:val="center" w:pos="4320"/><w:tab w:val="right" w:pos="8640"/></w:tabs></w:pPr><w:rPr><wx:font wx:val="Times"/></w:rPr></w:style></w:styles><w:shapeDefaults><o:shapedefaults v:ext="edit" spidmax="3074"><o:colormenu v:ext="edit" strokecolor="none"/></o:shapedefaults><o:shapelayout v:ext="edit"><o:idmap v:ext="edit" data="1"/></o:shapelayout></w:shapeDefaults><w:docPr><w:view w:val="print"/><w:zoom w:percent="100"/><w:doNotEmbedSystemFonts/><w:attachedTemplate w:val=""/><w:defaultTabStop w:val="720"/><w:displayHorizontalDrawingGridEvery w:val="0"/><w:displayVerticalDrawingGridEvery w:val="0"/><w:useMarginsForDrawingGridOrigin/><w:characterSpacingControl w:val="DontCompress"/><w:optimizeForBrowser/><w:validateAgainstSchema/><w:saveInvalidXML w:val="off"/><w:ignoreMixedContent w:val="off"/><w:alwaysShowPlaceholderText w:val="off"/><w:footnotePr><w:footnote w:type="separator"><w:p wsp:rsidR="00FC27E1" wsp:rsidRDefault="00FC27E1"><w:r><w:separator/></w:r></w:p></w:footnote><w:footnote w:type="continuation-separator"><w:p wsp:rsidR="00FC27E1" wsp:rsidRDefault="00FC27E1"><w:r><w:continuationSeparator/></w:r></w:p></w:footnote></w:footnotePr><w:endnotePr><w:endnote w:type="separator"><w:p wsp:rsidR="00FC27E1" wsp:rsidRDefault="00FC27E1"><w:r><w:separator/></w:r></w:p></w:endnote><w:endnote w:type="continuation-separator"><w:p wsp:rsidR="00FC27E1" wsp:rsidRDefault="00FC27E1"><w:r><w:continuationSeparator/></w:r></w:p></w:endnote></w:endnotePr><w:compat><w:spaceForUL/><w:balanceSingleByteDoubleByteWidth/><w:doNotLeaveBackslashAlone/><w:ulTrailSpace/><w:doNotExpandShiftReturn/><w:adjustLineHeightInTable/><w:dontAllowFieldEndSelect/><w:useWord2002TableStyleRules/></w:compat><wsp:rsids><wsp:rsidRoot wsp:val="00B66622"/><wsp:rsid wsp:val="00596B75"/><wsp:rsid wsp:val="006811D9"/><wsp:rsid wsp:val="00776224"/><wsp:rsid wsp:val="00812229"/><wsp:rsid wsp:val="008B4F38"/><wsp:rsid wsp:val="00A3270A"/><wsp:rsid wsp:val="00A43D86"/><wsp:rsid wsp:val="00B66622"/><wsp:rsid wsp:val="00DA572D"/><wsp:rsid wsp:val="00E54142"/><wsp:rsid wsp:val="00FC27E1"/></wsp:rsids></w:docPr>
<w:body>
<w:hdr w:type="odd"><w:p wsp:rsidR="00E54142" wsp:rsidRDefault="00596B75"><w:pPr><w:pStyle w:val="Header"/></w:pPr><w:r><w:rPr><w:noProof/></w:rPr><w:pict><v:shapetype id="_x0000_t202" coordsize="21600,21600" o:spt="202" path="m,l,21600r21600,l21600,xe"><v:stroke joinstyle="miter"/><v:path gradientshapeok="t" o:connecttype="rect"/></v:shapetype><v:shape id="_x0000_s1047" type="#_x0000_t202" style="position:absolute;margin-left:4.05pt;margin-top:108.2pt;width:522pt;height:603pt;z-index:12" stroked="f">
<v:textbox><w:txbxContent><w:p wsp:rsidR="00E54142" wsp:rsidRDefault="00E54142"/></w:txbxContent></v:textbox></v:shape></w:pict></w:r><w:r><w:rPr><w:noProof/></w:rPr><w:pict><v:rect id="_x0000_s1027" style="position:absolute;margin-left:117.05pt;margin-top:22.6pt;width:355pt;height:1in;z-index:1" stroked="f"><v:textbox style="mso-next-textbox:#_x0000_s1027">
<w:txbxContent><w:p wsp:rsidR="00E54142" wsp:rsidRDefault="00A3270A">
<w:pPr><w:outlineLvl w:val="0"/>
<w:rPr><w:rFonts w:ascii="Helvetica" w:h-ansi="Helvetica"/><wx:font wx:val="Helvetica"/><w:sz w:val="18"/></w:rPr></w:pPr>
<w:r>
<w:rPr><w:rFonts w:ascii="Helvetica" w:h-ansi="Helvetica"/><wx:font wx:val="Helvetica"/><w:sz w:val="18"/></w:rPr>
<w:t>Haas Group International Inc.</w:t></w:r></w:p><w:p wsp:rsidR="00A3270A" wsp:rsidRDefault="00A3270A">
<w:pPr><w:outlineLvl w:val="0"/><w:rPr><w:rFonts w:ascii="Helvetica" w:h-ansi="Helvetica"/><wx:font wx:val="Helvetica"/><w:sz w:val="18"/></w:rPr></w:pPr><w:r>
<w:rPr><w:rFonts w:ascii="Helvetica" w:h-ansi="Helvetica"/><wx:font wx:val="Helvetica"/><w:sz w:val="18"/></w:rPr>
<w:t>1475 Phoenixville Pike, Suite 101</w:t></w:r><w:r wsp:rsidR="00A43D86"><w:rPr><w:rFonts w:ascii="Helvetica" w:h-ansi="Helvetica"/><wx:font wx:val="Helvetica"/><w:sz w:val="18"/></w:rPr><w:t>, </w:t></w:r><st1:place w:st="on"><st1:City w:st="on"><w:r wsp:rsidR="00A43D86"><w:rPr><w:rFonts w:ascii="Helvetica" w:h-ansi="Helvetica"/><wx:font wx:val="Helvetica"/><w:sz w:val="18"/></w:rPr><w:t>West Chester</w:t></w:r></st1:City><w:r wsp:rsidR="00A43D86"><w:rPr><w:rFonts w:ascii="Helvetica" w:h-ansi="Helvetica"/><wx:font wx:val="Helvetica"/><w:sz w:val="18"/></w:rPr><w:t>,</w:t></w:r><w:r><w:rPr><w:rFonts w:ascii="Helvetica" w:h-ansi="Helvetica"/><wx:font wx:val="Helvetica"/><w:sz w:val="18"/></w:rPr><w:t> </w:t></w:r><st1:State w:st="on"><w:r><w:rPr><w:rFonts w:ascii="Helvetica" w:h-ansi="Helvetica"/><wx:font wx:val="Helvetica"/><w:sz w:val="18"/></w:rPr><w:t>Pennsylvania</w:t></w:r></st1:State><w:r wsp:rsidR="00A43D86"><w:rPr><w:rFonts w:ascii="Helvetica" w:h-ansi="Helvetica"/><wx:font wx:val="Helvetica"/><w:sz w:val="18"/></w:rPr><w:t>, </w:t></w:r><st1:PostalCode w:st="on"><w:r wsp:rsidR="00A43D86"><w:rPr><w:rFonts w:ascii="Helvetica" w:h-ansi="Helvetica"/><wx:font wx:val="Helvetica"/><w:sz w:val="18"/></w:rPr><w:t>19380</w:t></w:r></st1:PostalCode><w:r wsp:rsidR="00A43D86"><w:rPr><w:rFonts w:ascii="Helvetica" w:h-ansi="Helvetica"/><wx:font wx:val="Helvetica"/><w:sz w:val="18"/></w:rPr><w:t> </w:t></w:r><st1:country-region w:st="on"><w:r><w:rPr><w:rFonts w:ascii="Helvetica" w:h-ansi="Helvetica"/><wx:font wx:val="Helvetica"/><w:sz w:val="18"/></w:rPr><w:t>USA</w:t></w:r></st1:country-region></st1:place></w:p><w:p wsp:rsidR="00E54142" wsp:rsidRDefault="00E54142"><w:pPr><w:outlineLvl w:val="0"/></w:pPr><w:r><w:rPr><w:rFonts w:ascii="Helvetica" w:h-ansi="Helvetica"/><wx:font wx:val="Helvetica"/><w:sz w:val="18"/></w:rPr><w:t>Tel: </w:t></w:r><w:r wsp:rsidR="00A3270A"><w:rPr><w:rFonts w:ascii="Helvetica" w:h-ansi="Helvetica"/><wx:font wx:val="Helvetica"/><w:sz w:val="18"/></w:rPr><w:t>484.564.4500</w:t></w:r><w:r><w:rPr><w:rFonts w:ascii="Helvetica" w:h-ansi="Helvetica"/><wx:font wx:val="Helvetica"/><w:sz w:val="18"/></w:rPr><w:t> </w:t></w:r><w:r wsp:rsidR="00A43D86"><w:rPr><w:rFonts w:ascii="Helvetica" w:h-ansi="Helvetica"/><wx:font wx:val="Helvetica"/><w:sz w:val="18"/></w:rPr><w:t> </w:t></w:r><w:r><w:rPr><w:rFonts w:ascii="Helvetica" w:h-ansi="Helvetica"/><wx:font wx:val="Helvetica"/><w:sz w:val="18"/></w:rPr><w:t>Fax: </w:t></w:r><w:r wsp:rsidR="00A43D86"><w:rPr><w:rFonts w:ascii="Helvetica" w:h-ansi="Helvetica"/><wx:font wx:val="Helvetica"/><w:sz w:val="18"/></w:rPr><w:t>610.436.9076 </w:t></w:r><w:r wsp:rsidR="00A3270A"><w:rPr><w:rFonts w:ascii="Helvetica" w:h-ansi="Helvetica"/><wx:font wx:val="Helvetica"/><w:sz w:val="18"/></w:rPr><w:t> www.haasgroupintl.com</w:t></w:r></w:p>
</w:txbxContent>
</v:textbox></v:rect></w:pict></w:r><w:r><w:rPr><w:noProof/></w:rPr><w:pict><v:line id="_x0000_s1046" style="position:absolute;z-index:11" from="215.65pt,18.2pt" to="224.65pt,18.2pt" strokecolor="red" strokeweight=".5pt"/></w:pict></w:r><w:r><w:rPr><w:noProof/></w:rPr><w:pict><v:line id="_x0000_s1045" style="position:absolute;z-index:10" from="199.45pt,18.2pt" to="208.45pt,18.2pt" strokecolor="red" strokeweight=".5pt"/></w:pict></w:r><w:r><w:rPr><w:noProof/></w:rPr><w:pict><v:line id="_x0000_s1044" style="position:absolute;z-index:9" from="182.65pt,18.2pt" to="191.65pt,18.2pt" strokecolor="red" strokeweight=".5pt"/></w:pict></w:r><w:r><w:rPr><w:noProof/></w:rPr><w:pict><v:line id="_x0000_s1043" style="position:absolute;z-index:8" from="165.45pt,18.2pt" to="174.45pt,18.2pt" strokecolor="red" strokeweight=".5pt"/></w:pict></w:r><w:r><w:rPr><w:noProof/></w:rPr><w:pict><v:line id="_x0000_s1041" style="position:absolute;z-index:7" from="148.45pt,18.2pt" to="157.45pt,18.2pt" strokecolor="red" strokeweight=".5pt"/></w:pict></w:r><w:r><w:rPr><w:noProof/></w:rPr><w:pict><v:line id="_x0000_s1032" style="position:absolute;z-index:2" from="111.25pt,9.2pt" to="111.25pt,63.2pt" strokecolor="red" strokeweight=".5pt"/></w:pict></w:r><w:r><w:rPr><w:noProof/></w:rPr><w:pict><v:line id="_x0000_s1040" style="position:absolute;z-index:6" from="131.65pt,18.2pt" to="140.65pt,18.2pt" strokecolor="red" strokeweight=".5pt"/></w:pict></w:r><w:r><w:rPr><w:noProof/></w:rPr><w:pict><v:line id="_x0000_s1038" style="position:absolute;z-index:4" from="98.45pt,18.2pt" to="107.45pt,18.2pt" strokecolor="red" strokeweight=".5pt"/></w:pict></w:r><w:r><w:rPr><w:noProof/></w:rPr><w:pict><v:line id="_x0000_s1039" style="position:absolute;z-index:5" from="115.05pt,18.2pt" to="124.05pt,18.2pt" strokecolor="red" strokeweight=".5pt"/></w:pict></w:r><w:r><w:rPr><w:noProof/></w:rPr><w:pict><v:line id="_x0000_s1036" style="position:absolute;z-index:3" from="81.85pt,18.2pt" to="90.85pt,18.2pt" strokecolor="red" strokeweight=".5pt"/></w:pict></w:r><w:r><w:pict><v:shapetype id="_x0000_t75" coordsize="21600,21600" o:spt="75" o:preferrelative="t" path="m@4@5l@4@11@9@11@9@5xe" filled="f" stroked="f"><v:stroke joinstyle="miter"/><v:formulas><v:f eqn="if lineDrawn pixelLineWidth 0"/><v:f eqn="sum @0 1 0"/><v:f eqn="sum 0 0 @1"/><v:f eqn="prod @2 1 2"/><v:f eqn="prod @3 21600 pixelWidth"/><v:f eqn="prod @3 21600 pixelHeight"/><v:f eqn="sum @0 0 1"/><v:f eqn="prod @6 1 2"/><v:f eqn="prod @7 21600 pixelWidth"/><v:f eqn="sum @8 21600 0"/><v:f eqn="prod @7 21600 pixelHeight"/><v:f eqn="sum @10 21600 0"/></v:formulas><v:path o:extrusionok="f" gradientshapeok="t" o:connecttype="rect"/><o:lock v:ext="edit" aspectratio="t"/></v:shapetype><w:binData w:name="wordml://03000001.png">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
</w:binData><v:shape id="_x0000_i1025" type="#_x0000_t75" style="width:75.75pt;height:75.75pt"><v:imagedata src="wordml://03000001.png" o:title="HAAS GROUP INTERNAT#5DE279F"/></v:shape></w:pict></w:r></w:p><w:sectPr wsp:rsidR="00E54142" wsp:rsidSect="00596B75"><w:pgSz w:w="12240" w:h="15840"/><w:pgMar w:top="720" w:right="360" w:bottom="288" w:left="720" w:header="720" w:footer="720" w:gutter="0"/><w:cols w:space="720"/></w:sectPr>
<w:p wsp:rsidR="00395F31" wsp:rsidRDefault="00395F31"><w:pPr><w:pStyle w:val="Header"/></w:pPr></w:p>
<w:p wsp:rsidR="00395F31" wsp:rsidRDefault="00395F31"><w:pPr><w:pStyle w:val="Header"/></w:pPr></w:p>
</w:hdr>
<w:tbl>
<w:tblPr><w:tblW w:w="10162" w:type="dxa"/><w:tblInd w:w="-792" w:type="dxa"/><w:tblLayout w:type="Fixed"/><w:tblCellMar><w:left w:w="10" w:type="dxa"/><w:right w:w="10" w:type="dxa"/></w:tblCellMar><w:tblLook w:val="01E0"/></w:tblPr>
<w:tblGrid>
<w:gridCol w:w="5081"/>
<w:gridCol w:w="41"/>
<w:gridCol w:w="2499"/>
<w:gridCol w:w="21"/>
<w:gridCol w:w="2520"/>
</w:tblGrid>
<w:tr><w:tblPrEx><w:tblCellMar><w:top w:w="0" w:type="dxa"/><w:bottom w:w="0" w:type="dxa"/></w:tblCellMar></w:tblPrEx><w:trPr><w:trHeight w:val="232"/></w:trPr>
<w:tc><w:tcPr><w:tcW w:w="5122" w:type="dxa"/><w:gridSpan w:val="2"/></w:tcPr><w:p><w:pPr><w:pageBreakBefore/><w:rPr><w:rFonts w:ascii="Arial" w:h-ansi="Arial" w:cs="Arial"/><wx:font wx:val="Arial"/><w:sz w:val="20"/><w:sz-cs w:val="20"/></w:rPr></w:pPr><w:r><w:rPr><w:rFonts w:ascii="Arial" w:h-ansi="Arial" w:cs="Arial"/><wx:font wx:val="Arial"/><w:sz w:val="20"/><w:sz-cs w:val="20"/></w:rPr>
<w:t>Dana Pottstown</w:t>
</w:r></w:p></w:tc>
<w:tc><w:tcPr><w:tcW w:w="2520" w:type="dxa"/><w:gridSpan w:val="2"/><w:tcMar><w:left w:w="72" w:type="dxa"/></w:tcMar></w:tcPr><w:p><w:pPr><w:jc w:val="right"/><w:rPr><w:rFonts w:ascii="Arial" w:h-ansi="Arial" w:cs="Arial"/><wx:font wx:val="Arial"/><w:sz w:val="20"/><w:sz-cs w:val="20"/></w:rPr></w:pPr><w:r><w:rPr><w:rFonts w:ascii="Arial" w:h-ansi="Arial" w:cs="Arial"/><wx:font wx:val="Arial"/><w:sz w:val="20"/><w:sz-cs w:val="20"/></w:rPr>
<w:t>Invoice Number:</w:t>
</w:r></w:p></w:tc>
<w:tc><w:tcPr><w:tcW w:w="2520" w:type="dxa"/><w:tcMar><w:left w:w="72" w:type="dxa"/></w:tcMar></w:tcPr><w:p><w:pPr><w:rPr><w:rFonts w:ascii="Arial" w:h-ansi="Arial" w:cs="Arial"/><wx:font wx:val="Arial"/><w:sz w:val="20"/><w:sz-cs w:val="20"/></w:rPr></w:pPr><w:r><w:rPr><w:rFonts w:ascii="Arial" w:h-ansi="Arial" w:cs="Arial"/><wx:font wx:val="Arial"/><w:sz w:val="20"/><w:sz-cs w:val="20"/></w:rPr>
<w:t><xsl:value-of select="InvoiceViewDanaBean/invoice"/></w:t>
</w:r></w:p></w:tc></w:tr>

<w:tr><w:tblPrEx><w:tblCellMar><w:top w:w="0" w:type="dxa"/><w:bottom w:w="0" w:type="dxa"/></w:tblCellMar></w:tblPrEx><w:trPr><w:trHeight w:val="232"/></w:trPr>
<w:tc><w:tcPr><w:tcW w:w="5122" w:type="dxa"/><w:gridSpan w:val="2"/></w:tcPr><w:p><w:pPr><w:rPr><w:rFonts w:ascii="Arial" w:h-ansi="Arial" w:cs="Arial"/><wx:font wx:val="Arial"/><w:sz w:val="20"/><w:sz-cs w:val="20"/></w:rPr></w:pPr><w:r><w:rPr><w:rFonts w:ascii="Arial" w:h-ansi="Arial" w:cs="Arial"/><wx:font wx:val="Arial"/><w:sz w:val="20"/><w:sz-cs w:val="20"/></w:rPr>
<w:t>1040 Center Ave.</w:t>
</w:r></w:p></w:tc>
<w:tc><w:tcPr><w:tcW w:w="2520" w:type="dxa"/><w:gridSpan w:val="2"/></w:tcPr><w:p><w:pPr><w:jc w:val="right"/><w:rPr><w:rFonts w:ascii="Arial" w:h-ansi="Arial" w:cs="Arial"/><wx:font wx:val="Arial"/><w:sz w:val="20"/><w:sz-cs w:val="20"/></w:rPr></w:pPr><w:r><w:rPr><w:rFonts w:ascii="Arial" w:h-ansi="Arial" w:cs="Arial"/><wx:font wx:val="Arial"/><w:sz w:val="20"/><w:sz-cs w:val="20"/></w:rPr>
<w:t>Invoice Date:</w:t>
</w:r></w:p></w:tc>
<w:tc><w:tcPr><w:tcW w:w="2520" w:type="dxa"/><w:tcMar><w:left w:w="72" w:type="dxa"/></w:tcMar></w:tcPr><w:p><w:pPr><w:rPr><w:rFonts w:ascii="Arial" w:h-ansi="Arial" w:cs="Arial"/><wx:font wx:val="Arial"/><w:sz w:val="20"/><w:sz-cs w:val="20"/></w:rPr></w:pPr><w:r><w:rPr><w:rFonts w:ascii="Arial" w:h-ansi="Arial" w:cs="Arial"/><wx:font wx:val="Arial"/><w:sz w:val="20"/><w:sz-cs w:val="20"/></w:rPr>
<xsl:variable name="invoiceDate" select="InvoiceViewDanaBean/invoiceDate"/>
<w:t>
<xsl:call-template name="format-date">
 <xsl:with-param name="date" select="$invoiceDate" />
 <xsl:with-param name="format" select="1" />
</xsl:call-template>
</w:t>
</w:r></w:p></w:tc></w:tr>

<w:tr><w:tblPrEx><w:tblCellMar><w:top w:w="0" w:type="dxa"/><w:bottom w:w="0" w:type="dxa"/></w:tblCellMar></w:tblPrEx><w:trPr><w:trHeight w:val="232"/></w:trPr>
<w:tc><w:tcPr><w:tcW w:w="5122" w:type="dxa"/><w:gridSpan w:val="2"/></w:tcPr><w:p><w:pPr><w:rPr><w:rFonts w:ascii="Arial" w:h-ansi="Arial" w:cs="Arial"/><wx:font wx:val="Arial"/><w:sz w:val="20"/><w:sz-cs w:val="20"/></w:rPr></w:pPr><w:r><w:rPr><w:rFonts w:ascii="Arial" w:h-ansi="Arial" w:cs="Arial"/><wx:font wx:val="Arial"/><w:sz w:val="20"/><w:sz-cs w:val="20"/></w:rPr>
<w:t>Pottstown, PA 19464</w:t>
</w:r></w:p></w:tc>
<w:tc><w:tcPr><w:tcW w:w="2520" w:type="dxa"/><w:gridSpan w:val="2"/></w:tcPr><w:p><w:pPr><w:jc w:val="right"/><w:rPr><w:rFonts w:ascii="Arial" w:h-ansi="Arial" w:cs="Arial"/><wx:font wx:val="Arial"/><w:sz w:val="20"/><w:sz-cs w:val="20"/></w:rPr></w:pPr><w:r><w:rPr><w:rFonts w:ascii="Arial" w:h-ansi="Arial" w:cs="Arial"/><wx:font wx:val="Arial"/><w:sz w:val="20"/><w:sz-cs w:val="20"/></w:rPr>
<w:t>Invoice Amount:</w:t>
</w:r></w:p></w:tc>
<w:tc><w:tcPr><w:tcW w:w="2520" w:type="dxa"/><w:tcMar><w:left w:w="72" w:type="dxa"/></w:tcMar></w:tcPr><w:p><w:pPr><w:rPr><w:rFonts w:ascii="Arial" w:h-ansi="Arial" w:cs="Arial"/><wx:font wx:val="Arial"/><w:sz w:val="20"/><w:sz-cs w:val="20"/></w:rPr></w:pPr><w:r><w:rPr><w:rFonts w:ascii="Arial" w:h-ansi="Arial" w:cs="Arial"/><wx:font wx:val="Arial"/><w:sz w:val="20"/><w:sz-cs w:val="20"/></w:rPr>
<xsl:variable name="invoiceAmount" select="InvoiceViewDanaBean/invoiceAmount"/>
<w:t><xsl:value-of select='format-number($invoiceAmount, "$0.00;-$0.00")'/></w:t>
</w:r></w:p></w:tc></w:tr>

<w:tr><w:tblPrEx><w:tblCellMar><w:top w:w="0" w:type="dxa"/><w:bottom w:w="0" w:type="dxa"/></w:tblCellMar></w:tblPrEx><w:trPr><w:trHeight w:val="232"/></w:trPr>
<w:tc><w:tcPr><w:tcW w:w="5122" w:type="dxa"/><w:gridSpan w:val="2"/></w:tcPr><w:p><w:pPr><w:rPr><w:rFonts w:ascii="Arial" w:h-ansi="Arial" w:cs="Arial"/><wx:font wx:val="Arial"/><w:sz w:val="20"/><w:sz-cs w:val="20"/></w:rPr></w:pPr></w:p></w:tc>
<w:tc><w:tcPr><w:tcW w:w="2520" w:type="dxa"/><w:gridSpan w:val="2"/></w:tcPr><w:p><w:pPr><w:jc w:val="right"/><w:rPr><w:rFonts w:ascii="Arial" w:h-ansi="Arial" w:cs="Arial"/><wx:font wx:val="Arial"/><w:sz w:val="20"/><w:sz-cs w:val="20"/></w:rPr></w:pPr><w:r><w:rPr><w:rFonts w:ascii="Arial" w:h-ansi="Arial" w:cs="Arial"/><wx:font wx:val="Arial"/><w:sz w:val="20"/><w:sz-cs w:val="20"/></w:rPr>
<w:t>Payment Terms:</w:t>
</w:r></w:p></w:tc>
<w:tc><w:tcPr><w:tcW w:w="2520" w:type="dxa"/><w:tcMar><w:left w:w="72" w:type="dxa"/></w:tcMar></w:tcPr><w:p><w:pPr><w:rPr><w:rFonts w:ascii="Arial" w:h-ansi="Arial" w:cs="Arial"/><wx:font wx:val="Arial"/><w:sz w:val="20"/><w:sz-cs w:val="20"/></w:rPr></w:pPr><w:r><w:rPr><w:rFonts w:ascii="Arial" w:h-ansi="Arial" w:cs="Arial"/><wx:font wx:val="Arial"/><w:sz w:val="20"/><w:sz-cs w:val="20"/></w:rPr>
<w:t>Net 45</w:t>
</w:r></w:p></w:tc></w:tr>

<w:tr><w:tblPrEx><w:tblCellMar><w:top w:w="0" w:type="dxa"/><w:bottom w:w="0" w:type="dxa"/></w:tblCellMar></w:tblPrEx><w:trPr><w:trHeight w:val="232"/></w:trPr>
<w:tc><w:tcPr><w:tcW w:w="5122" w:type="dxa"/><w:gridSpan w:val="2"/></w:tcPr><w:p><w:pPr><w:rPr><w:rFonts w:ascii="Arial" w:h-ansi="Arial" w:cs="Arial"/><wx:font wx:val="Arial"/><w:sz w:val="20"/><w:sz-cs w:val="20"/></w:rPr></w:pPr><w:r><w:rPr><w:rFonts w:ascii="Arial" w:h-ansi="Arial" w:cs="Arial"/><wx:font wx:val="Arial"/><w:sz w:val="20"/><w:sz-cs w:val="20"/></w:rPr>
<w:t>ATTN: Kay Sisko</w:t>
</w:r></w:p></w:tc>
<w:tc><w:tcPr><w:tcW w:w="2520" w:type="dxa"/><w:gridSpan w:val="2"/></w:tcPr><w:p><w:pPr><w:jc w:val="right"/><w:rPr><w:rFonts w:ascii="Arial" w:h-ansi="Arial" w:cs="Arial"/><wx:font wx:val="Arial"/><w:sz w:val="20"/><w:sz-cs w:val="20"/></w:rPr></w:pPr><w:r><w:rPr><w:rFonts w:ascii="Arial" w:h-ansi="Arial" w:cs="Arial"/><wx:font wx:val="Arial"/><w:sz w:val="20"/><w:sz-cs w:val="20"/></w:rPr>
<w:t>Dana PO:</w:t>
</w:r></w:p></w:tc>
<w:tc><w:tcPr><w:tcW w:w="2520" w:type="dxa"/><w:tcMar><w:left w:w="72" w:type="dxa"/></w:tcMar></w:tcPr><w:p><w:pPr><w:rPr><w:rFonts w:ascii="Arial" w:h-ansi="Arial" w:cs="Arial"/><wx:font wx:val="Arial"/><w:sz w:val="20"/><w:sz-cs w:val="20"/></w:rPr></w:pPr><w:r><w:rPr><w:rFonts w:ascii="Arial" w:h-ansi="Arial" w:cs="Arial"/><wx:font wx:val="Arial"/><w:sz w:val="20"/><w:sz-cs w:val="20"/></w:rPr>
<w:t><xsl:value-of select="InvoiceViewDanaBean/poNumber"/></w:t>
</w:r></w:p></w:tc></w:tr>

<w:tr><w:tblPrEx><w:tblCellMar> <w:top w:w="0" w:type="dxa"/><w:bottom w:w="0"
w:type="dxa"/></w:tblCellMar></w:tblPrEx>
<w:trPr><w:trHeight w:val="180"/></w:trPr>
<w:tc><w:tcPr><w:tcW w:w="10162" w:type="dxa"/><w:gridSpan w:val="5"/></w:tcPr><w:p><w:pPr><w:rPr>
<w:rFonts w:ascii="Arial" w:h-ansi="Arial" w:cs="Arial"/><wx:font wx:val="Arial"/><w:sz w:val="20"/><w:sz-cs
w:val="20"/>
</w:rPr></w:pPr><w:r><w:rPr><w:rFonts w:ascii="Arial" w:h-ansi="Arial" w:cs="Arial"/><wx:font wx:val="Arial"/>
<w:sz w:val="20"/><w:sz-cs w:val="20"/></w:rPr>
<w:t></w:t>
</w:r></w:p></w:tc></w:tr>

<w:tr><w:tblPrEx><w:tblCellMar><w:top w:w="0" w:type="dxa"/><w:bottom w:w="0" w:type="dxa"/></w:tblCellMar></w:tblPrEx><w:trPr><w:trHeight w:val="180"/></w:trPr>
<w:tc><w:tcPr><w:tcW w:w="10162" w:type="dxa"/><w:gridSpan w:val="5"/></w:tcPr><w:p><w:pPr><w:rPr><w:rFonts w:ascii="Arial" w:h-ansi="Arial" w:cs="Arial"/><wx:font wx:val="Arial"/><w:sz w:val="20"/><w:sz-cs w:val="20"/></w:rPr></w:pPr><w:r><w:rPr><w:rFonts w:ascii="Arial" w:h-ansi="Arial" w:cs="Arial"/><wx:font wx:val="Arial"/><w:sz w:val="20"/><w:sz-cs w:val="20"/></w:rPr>
<w:t>Invoice Inquiries: Steve Buffum</w:t>
</w:r></w:p></w:tc></w:tr>

<w:tr><w:tblPrEx><w:tblCellMar><w:top w:w="0" w:type="dxa"/><w:bottom w:w="0" w:type="dxa"/></w:tblCellMar></w:tblPrEx><w:trPr><w:trHeight w:val="225"/></w:trPr>
<w:tc><w:tcPr><w:tcW w:w="10162" w:type="dxa"/><w:gridSpan w:val="5"/></w:tcPr><w:p><w:pPr><w:rPr><w:rFonts w:ascii="Arial" w:h-ansi="Arial" w:cs="Arial"/><wx:font wx:val="Arial"/><w:sz w:val="20"/><w:sz-cs w:val="20"/></w:rPr></w:pPr><w:r><w:rPr><w:rFonts w:ascii="Arial" w:h-ansi="Arial" w:cs="Arial"/><wx:font wx:val="Arial"/><w:sz w:val="20"/><w:sz-cs w:val="20"/></w:rPr>
<w:t>Contact Phone: 512 519 3916</w:t>
</w:r></w:p></w:tc></w:tr>

<w:tr><w:tblPrEx><w:tblCellMar><w:top w:w="0" w:type="dxa"/><w:bottom w:w="0" w:type="dxa"/></w:tblCellMar></w:tblPrEx><w:trPr><w:trHeight w:val="1450"/></w:trPr>
<w:tc><w:tcPr><w:tcW w:w="10162" w:type="dxa"/><w:gridSpan w:val="5"/></w:tcPr><w:p><w:pPr><w:rPr><w:rFonts w:ascii="Arial" w:h-ansi="Arial" w:cs="Arial"/><wx:font wx:val="Arial"/><w:sz w:val="20"/><w:sz-cs w:val="20"/></w:rPr></w:pPr><w:r><w:rPr><w:rFonts w:ascii="Arial" w:h-ansi="Arial" w:cs="Arial"/><wx:font wx:val="Arial"/><w:sz w:val="20"/><w:sz-cs w:val="20"/></w:rPr>
<w:t>Contact Fax: 512 519 3901</w:t>
</w:r></w:p></w:tc></w:tr>

<xsl:for-each select="InvoiceViewDanaBean">
<w:tr><w:tblPrEx><w:tblCellMar><w:top w:w="0" w:type="dxa"/><w:bottom w:w="0" w:type="dxa"/></w:tblCellMar></w:tblPrEx>
<w:trPr><w:trHeight w:val="660"/></w:trPr>
<w:tc><w:tcPr><w:tcW w:w="5081" w:type="dxa"/><w:vAlign w:val="center"/></w:tcPr><w:p><w:pPr><w:rPr>
<w:rFonts w:ascii="Arial" w:h-ansi="Arial" w:cs="Arial"/><wx:font wx:val="Arial"/><w:sz w:val="20"/><w:sz-cs w:val="20"/></w:rPr></w:pPr>
<w:r><w:rPr><w:rFonts w:ascii="Arial" w:h-ansi="Arial" w:cs="Arial"/><wx:font wx:val="Arial"/><w:sz w:val="20"/><w:sz-cs w:val="20"/></w:rPr>
<w:t><xsl:value-of select="lineDescription"/>:</w:t>
</w:r></w:p></w:tc>
<w:tc><w:tcPr><w:tcW w:w="2540" w:type="dxa"/><w:gridSpan w:val="2"/><w:vAlign w:val="center"/></w:tcPr><w:p><w:pPr><w:jc w:val="right"/><w:rPr>
<w:rFonts w:ascii="Arial" w:h-ansi="Arial" w:cs="Arial"/><wx:font wx:val="Arial"/><w:sz w:val="20"/><w:sz-cs w:val="20"/></w:rPr></w:pPr>
<w:r><w:rPr><w:rFonts w:ascii="Arial" w:h-ansi="Arial" w:cs="Arial"/><wx:font wx:val="Arial"/><w:sz w:val="20"/><w:sz-cs w:val="20"/></w:rPr>
<xsl:variable name="lineAmount" select="lineAmount"/>
<w:t><xsl:value-of select='format-number($lineAmount, "$0.00;-$0.00")'/></w:t>
</w:r></w:p></w:tc>
<w:tc><w:tcPr><w:tcW w:w="2541" w:type="dxa"/><w:gridSpan w:val="2"/><w:vAlign w:val="center"/></w:tcPr><w:p><w:pPr><w:jc w:val="center"/><w:rPr>
<w:rFonts w:ascii="Arial" w:h-ansi="Arial" w:cs="Arial"/><wx:font wx:val="Arial"/><w:sz w:val="20"/><w:sz-cs w:val="20"/></w:rPr></w:pPr></w:p></w:tc></w:tr>
</xsl:for-each>

<w:tr><w:tblPrEx><w:tblCellMar><w:top w:w="0" w:type="dxa"/><w:bottom w:w="0" w:type="dxa"/></w:tblCellMar></w:tblPrEx><w:trPr><w:trHeight w:val="680"/></w:trPr>
<w:tc><w:tcPr><w:tcW w:w="5081" w:type="dxa"/><w:vAlign w:val="center"/></w:tcPr><w:p><w:pPr><w:rPr><w:rFonts w:ascii="Arial" w:h-ansi="Arial" w:cs="Arial"/><wx:font wx:val="Arial"/><w:sz w:val="20"/><w:sz-cs w:val="20"/></w:rPr></w:pPr></w:p><w:p><w:pPr><w:rPr><w:rFonts w:ascii="Arial" w:h-ansi="Arial" w:cs="Arial"/><wx:font wx:val="Arial"/><w:sz w:val="20"/><w:sz-cs w:val="20"/></w:rPr></w:pPr><w:r><w:rPr><w:rFonts w:ascii="Arial" w:h-ansi="Arial" w:cs="Arial"/><wx:font wx:val="Arial"/><w:sz w:val="20"/><w:sz-cs w:val="20"/></w:rPr>
<w:t>Total Invoice Amount for PO <xsl:value-of select="InvoiceViewDanaBean/poNumber"/>:</w:t>
</w:r></w:p></w:tc>
<w:tc><w:tcPr><w:tcW w:w="2540" w:type="dxa"/><w:gridSpan w:val="2"/><w:vAlign w:val="center"/></w:tcPr><w:p><w:pPr><w:jc w:val="right"/><w:rPr><w:rFonts w:ascii="Arial" w:h-ansi="Arial" w:cs="Arial"/><wx:font wx:val="Arial"/><w:sz w:val="20"/><w:sz-cs w:val="20"/></w:rPr></w:pPr><w:r><w:rPr><w:rFonts w:ascii="Arial" w:h-ansi="Arial" w:cs="Arial"/><wx:font wx:val="Arial"/><w:sz w:val="20"/><w:sz-cs w:val="20"/></w:rPr>
<w:t>------------------------</w:t>
</w:r></w:p><w:p><w:pPr><w:jc w:val="right"/><w:rPr><w:rFonts w:ascii="Arial" w:h-ansi="Arial" w:cs="Arial"/><wx:font wx:val="Arial"/><w:sz w:val="20"/><w:sz-cs w:val="20"/></w:rPr></w:pPr><w:r><w:rPr><w:rFonts w:ascii="Arial" w:h-ansi="Arial" w:cs="Arial"/><wx:font wx:val="Arial"/><w:sz w:val="20"/><w:sz-cs w:val="20"/></w:rPr>
<xsl:variable name="invoiceAmount" select="InvoiceViewDanaBean/invoiceAmount"/>
<w:t><xsl:value-of select='format-number($invoiceAmount, "$0.00;-$0.00")'/></w:t>
</w:r></w:p></w:tc>
<w:tc><w:tcPr><w:tcW w:w="2541" w:type="dxa"/><w:gridSpan w:val="2"/><w:vAlign w:val="center"/></w:tcPr><w:p><w:pPr><w:jc w:val="center"/><w:rPr><w:rFonts w:ascii="Arial" w:h-ansi="Arial" w:cs="Arial"/><wx:font wx:val="Arial"/><w:sz w:val="20"/><w:sz-cs w:val="20"/></w:rPr></w:pPr></w:p></w:tc></w:tr>

<w:tr><w:tblPrEx><w:tblCellMar><w:top w:w="0" w:type="dxa"/><w:bottom w:w="0" w:type="dxa"/></w:tblCellMar></w:tblPrEx><w:trPr><w:trHeight w:val="1632"/></w:trPr>
<w:tc><w:tcPr><w:tcW w:w="10162" w:type="dxa"/><w:vAlign w:val="bottom"/><w:gridSpan w:val="5"/></w:tcPr><w:p><w:pPr><w:jc w:val="center"/><w:rPr><w:rFonts w:ascii="Arial" w:h-ansi="Arial" w:cs="Arial"/><wx:font wx:val="Arial"/><w:sz w:val="20"/><w:sz-cs w:val="20"/></w:rPr></w:pPr><w:r><w:rPr><w:rFonts w:ascii="Arial" w:h-ansi="Arial" w:cs="Arial"/><wx:font wx:val="Arial"/><w:sz w:val="20"/><w:sz-cs w:val="20"/></w:rPr>
<w:t>Wire transfers to:</w:t>
</w:r></w:p></w:tc></w:tr>

<w:tr><w:tblPrEx><w:tblCellMar><w:top w:w="0" w:type="dxa"/><w:bottom w:w="0" w:type="dxa"/></w:tblCellMar></w:tblPrEx><w:trPr><w:trHeight w:val="232"/></w:trPr>
<w:tc><w:tcPr><w:tcW w:w="10162" w:type="dxa"/><w:gridSpan w:val="5"/></w:tcPr><w:p><w:pPr><w:jc w:val="center"/><w:rPr><w:rFonts w:ascii="Arial" w:h-ansi="Arial" w:cs="Arial"/><wx:font wx:val="Arial"/><w:sz w:val="20"/><w:sz-cs w:val="20"/></w:rPr></w:pPr><w:r><w:rPr><w:rFonts w:ascii="Arial" w:h-ansi="Arial" w:cs="Arial"/><wx:font wx:val="Arial"/><w:sz w:val="20"/><w:sz-cs w:val="20"/></w:rPr>
<w:t>Haas Group Intl./PNC Bank</w:t>
</w:r></w:p></w:tc></w:tr>

<w:tr><w:tblPrEx><w:tblCellMar><w:top w:w="0" w:type="dxa"/><w:bottom w:w="0" w:type="dxa"/></w:tblCellMar></w:tblPrEx><w:trPr><w:trHeight w:val="963"/></w:trPr>
<w:tc><w:tcPr><w:tcW w:w="10162" w:type="dxa"/><w:gridSpan w:val="5"/></w:tcPr><w:p><w:pPr><w:autoSpaceDE w:val="off"/><w:autoSpaceDN w:val="off"/><w:adjustRightInd w:val="off"/><w:jc w:val="center"/><w:rPr><w:rFonts w:ascii="Arial" w:h-ansi="Arial" w:cs="Arial"/><wx:font wx:val="Arial"/><w:sz w:val="20"/><w:sz-cs w:val="20"/></w:rPr></w:pPr><w:r><w:rPr><w:rFonts w:ascii="Arial" w:h-ansi="Arial" w:cs="Arial"/><wx:font wx:val="Arial"/><w:sz w:val="20"/><w:sz-cs w:val="20"/></w:rPr>
<w:t>ABA NUMBER 031000053 Acct 8606079557</w:t>
</w:r></w:p></w:tc></w:tr>

<w:tr><w:tblPrEx><w:tblCellMar><w:top w:w="0" w:type="dxa"/><w:bottom w:w="0" w:type="dxa"/></w:tblCellMar></w:tblPrEx><w:trPr><w:trHeight w:val="316"/></w:trPr>
<w:tc><w:tcPr><w:tcW w:w="10162" w:type="dxa"/><w:gridSpan w:val="5"/></w:tcPr><w:p><w:pPr><w:jc w:val="center"/><w:rPr><w:rFonts w:ascii="Arial" w:h-ansi="Arial" w:cs="Arial"/><wx:font wx:val="Arial"/><w:sz w:val="20"/><w:sz-cs w:val="20"/></w:rPr></w:pPr><w:r><w:rPr><w:rFonts w:ascii="Arial" w:h-ansi="Arial" w:cs="Arial"/><wx:font wx:val="Arial"/><w:sz w:val="20"/><w:sz-cs w:val="20"/></w:rPr>
<w:t>Mail checks to:</w:t>
</w:r></w:p></w:tc></w:tr>

<w:tr><w:tblPrEx><w:tblCellMar><w:top w:w="0" w:type="dxa"/><w:bottom w:w="0" w:type="dxa"/></w:tblCellMar></w:tblPrEx><w:trPr><w:trHeight w:val="225"/></w:trPr>
<w:tc><w:tcPr><w:tcW w:w="10162" w:type="dxa"/><w:gridSpan w:val="5"/></w:tcPr><w:p><w:pPr><w:autoSpaceDE w:val="off"/><w:autoSpaceDN w:val="off"/><w:adjustRightInd w:val="off"/><w:jc w:val="center"/><w:rPr><w:rFonts w:ascii="Arial" w:h-ansi="Arial" w:cs="Arial"/><wx:font wx:val="Arial"/><w:sz w:val="20"/><w:sz-cs w:val="20"/></w:rPr></w:pPr><w:r><w:rPr><w:rFonts w:ascii="Arial" w:h-ansi="Arial" w:cs="Arial"/><wx:font wx:val="Arial"/><w:sz w:val="20"/><w:sz-cs w:val="20"/></w:rPr>
<w:t>Haas Group Intl.</w:t>
</w:r></w:p></w:tc></w:tr>

<w:tr><w:tblPrEx><w:tblCellMar><w:top w:w="0" w:type="dxa"/><w:bottom w:w="0" w:type="dxa"/></w:tblCellMar></w:tblPrEx><w:trPr><w:trHeight w:val="252"/></w:trPr>
<w:tc><w:tcPr><w:tcW w:w="10162" w:type="dxa"/><w:gridSpan w:val="5"/></w:tcPr><w:p><w:pPr><w:autoSpaceDE w:val="off"/><w:autoSpaceDN w:val="off"/><w:adjustRightInd w:val="off"/><w:jc w:val="center"/><w:rPr><w:rFonts w:ascii="Arial" w:h-ansi="Arial" w:cs="Arial"/><wx:font wx:val="Arial"/><w:sz w:val="20"/><w:sz-cs w:val="20"/></w:rPr></w:pPr><w:r><w:rPr><w:rFonts w:ascii="Arial" w:h-ansi="Arial" w:cs="Arial"/><wx:font wx:val="Arial"/><w:sz w:val="20"/><w:sz-cs w:val="20"/></w:rPr>
<w:t>1475 Phoenixville Pike, Suite 101</w:t>
</w:r></w:p></w:tc></w:tr>

<w:tr><w:tblPrEx><w:tblCellMar><w:top w:w="0" w:type="dxa"/><w:bottom w:w="0" w:type="dxa"/></w:tblCellMar></w:tblPrEx><w:trPr><w:trHeight w:val="336"/></w:trPr>
<w:tc><w:tcPr><w:tcW w:w="10162" w:type="dxa"/><w:gridSpan w:val="5"/></w:tcPr><w:p><w:pPr><w:autoSpaceDE w:val="off"/><w:autoSpaceDN w:val="off"/><w:adjustRightInd w:val="off"/><w:jc w:val="center"/><w:rPr><w:rFonts w:ascii="Arial" w:h-ansi="Arial" w:cs="Arial"/><wx:font wx:val="Arial"/><w:sz w:val="20"/><w:sz-cs w:val="20"/></w:rPr></w:pPr><w:r><w:rPr><w:rFonts w:ascii="Arial" w:h-ansi="Arial" w:cs="Arial"/><wx:font wx:val="Arial"/><w:sz w:val="20"/><w:sz-cs w:val="20"/></w:rPr>
<w:t>West Chester, PA 19380</w:t>
</w:r></w:p></w:tc></w:tr>

</w:tbl>

</w:body>
</w:wordDocument>
</xsl:template>

<xsl:template name="format-date">
  <xsl:param name="date" />
  <xsl:param name="format"/>

  <xsl:variable name="monthName" select="substring-before(substring-after($date, ' '), ' ')" />
  <xsl:variable name="previousMonthName" select="substring-before(substring-after($date, ' '), ' ')" />
  <xsl:variable name="day" select="substring-before(substring-after(substring-after($date, ' '), ' '), ' ')" />
  <xsl:variable name="year" select="substring-after(substring-after(substring-after(substring-after(substring-after($date, ' '), ' '), ' '), ' '), ' ')" />

  <xsl:variable name="month" select="substring(substring-after('Jan01Feb02Mar03Apr04May05Jun06Jul07Aug08Sep09Oct10Nov11Dec12', $monthName), 1, 2)" />
  <xsl:variable name="day2" select="concat(translate(substring($day,1,1), '0', ''), substring($day,2,1))" />
  <xsl:variable name="month2" select="concat(translate(substring($month,1,1), '0', ''), substring($month,2,1))" />

  <xsl:choose>
    <xsl:when test="$format = 1">
     <xsl:value-of select="concat($month, '/', $day, '/', substring($year, 3,2))" />
    </xsl:when>

    <xsl:when test="$format = 2">
     <xsl:value-of select="concat($monthName, ' ', $year)" />
    </xsl:when>

    <xsl:when test="$format = 3">
     <xsl:value-of select="concat($previousMonthName, ' ', $day2, ' ', $year)" />
    </xsl:when>

    <xsl:otherwise>
     <xsl:value-of select="$date" />
    </xsl:otherwise>
  </xsl:choose>
</xsl:template>
</xsl:stylesheet>
