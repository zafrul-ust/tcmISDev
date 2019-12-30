#!/usr/local/bin/perl

&ReadParse;
open (D,">/tmp/MP");

$ENV{'ORACLE_BASE'} = "/export/home/oracle/OraHome1";
$ENV{'ORACLE_HOME'} = "/export/home/oracle/OraHome1";
;
$ENV{'ORACLE_TERM'} = "xsun5";

use ExtUtils::testlib;
use Oraperl;

$msg = "";
$login = &ora_login($db, 'tcm_ops/n0s3ha1r@owlprod', '') || die "$ora_errstr\n";
&ora_do($login,"set scan off");
foreach $key (keys %in) {
    ($po,$item) = split(/_/,$key);
    $stmt = "update rec_sheet_printed set printed = \'$in{$key}\' where po_number = $po and item_id = $item";
    $exist = &ora_do($login,"$stmt");
    if ($exist =~ /^0/) {
	$stmt = "insert into rec_sheet_printed values ($po,$item,\'$in{$key}\')";
	&ora_do($login,"$stmt");
    }
    $msg .= "Marked PO Number $po item $item with print status $in{$key}<br>\n";
}
&ora_logoff($login);

print "Content-type: text/html\n\n";
print "<html><title>Marked as Printed</title>\n";
print "$msg\n";
print "</html>\n";

exit(0);

sub ReadParse {

  if (@_) {
     local (*in) = @_;
  }

#  local ($i, $loc, $key, $val);

# Read in text
  if ($ENV{'REQUEST_METHOD'} eq "GET") {
    $in = $ENV{'QUERY_STRING'};
  } elsif ($ENV{'REQUEST_METHOD'} eq "POST") {
    for ($i = 0; $i < $ENV{'CONTENT_LENGTH'}; $i++) {
      $in .= getc;
    }
  }

  @in = split(/&/,$in);

  foreach $i (0 .. $#in) {
# Convert plus's to spaces
    $in[$i] =~ s/\+/ /g;

# Convert %XX from hex numbers to alphanumeric
    $in[$i] =~ s/%(..)/pack("c",hex($1))/ge;

# Split into key and value.
    $loc = index($in[$i],"=");
    $key = substr($in[$i],0,$loc);
    $val = substr($in[$i],$loc+1);
    $in{$key} .= " and " if (defined($in{$key})); # \0 is the multiple separator
    $in{$key} .= $val;
  }
  return 1; # just for fun
}
__END__

