#!/usr/local/bin/perl

open (IN,"</tmp/BEA");
open (OUT,">/tmp/BEA2");
read (IN,$x,10000);
close(IN);
$x =~ s/\r/ /g;
print OUT $x;
close(OUT);
exit(0);
