#!/usr/local/bin/perl

open (IN,"<printed3");
open (OUT,">printed4");
while(<IN>) {
    $l = $_;
    if ($l =~ /jacket_(\d\d\d\d)_\d\d\d\d\d\d_(\d+)\.html:(\d+):/) {
	$file = $2;
	$line = $3;
	$nfil = $file;
	$nlin = $line;
	foreach $j (length $file .. 5) {
	    $nfil = "0" . $nfil;
	}
	foreach $j (length $line .. 5) {
	    $nlin = "0" . $nlin;
	}
	$fstr = $file . ".html";
	$nfstr = $nfil . ".html";
	$lstr = ":" . $line . ":";
	$nlstr = ":" . $nlin . ":";
	$l =~ s/$fstr/$nfstr/;
	$l =~ s/$lstr/$nlstr/;
	print OUT $l;
    }
}
close(IN);
close(OUT);
#jacket_2103_071201_0.html:18:      <h2>PO: 545959<br>
#jacket_2103_071201_0.html:190:      <h2>PO: 545959<br>
#jacket_2103_071201_0.html:193:         Item 213559 
