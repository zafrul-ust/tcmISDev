#!/bin/sh
for i in *.html
do
	cat $i | sed -e 's|cgi-bin|cgi-bin|' > $i.new
	mv $i.new $i
done
