#!/usr/local/bin/perl

$x = `ls -1 jacket_2101_*01_*.html`;
chop($x);
@x = split(/\n/,$x);
foreach $f (@x) {
    `rm $f`;
}
$x = `ls -1 jacket_2102_*01_*.html`;
chop($x);
@x = split(/\n/,$x);
foreach $f (@x) {
    `rm $f`;
}
$x = `ls -1 jacket_2103_*01_*.html`;
chop($x);
@x = split(/\n/,$x);
foreach $f (@x) {
    `rm $f`;
}
