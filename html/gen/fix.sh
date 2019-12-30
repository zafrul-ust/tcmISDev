#!/bin/sh
for i in *.html
do
	cat $i | sed -e 's|cgi-bin|cgi-bin|' -e 's|java/cgi-bin/secure|cgi-bin/secure/java|' > $i.new
	mv $i.new $i
done
