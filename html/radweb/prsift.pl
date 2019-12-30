#!/usr/local/bin/perl

open (IN,"<printed5");
read (IN,$x,100000);
close(IN);
chop($x);
@x = split(/\n/,$x);
foreach $i (0 .. $#x) {
    if ($x[$i] =~ /^jacket_(\d\d\d\d)_\d\d\d\d\d\d_(\d+)\.html:(\d+):(\s+)<h2>PO: (\d+)\D/) {
	$bp[$i] = $1;
	$file[$i] = $2;
	$line[$i] = $3;
	$po[$i] = $5;
    } elsif ($x[$i] =~ /^jacket_(\d\d\d\d)_\d\d\d\d\d\d_(\d+)\.html:(\d+):(\s+)Item (\d+)\D/) {
	$bp[$i] = $1;
	$file[$i] = $2;
	$line[$i] = $3;
	$item[$i] = $5;
    } else {
	print "Line mismatch $i\n";
    }
}
foreach $j (0 .. $#x/2) {
    $k = $j*2;
    $k2 = $j*2 + 1;
    print "PO $po[$k] Item $item[$k2]\n";
}
