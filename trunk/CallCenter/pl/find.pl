#!/usr/bin/perl

use strict;
use CGI;

my $basedir = '/usr/local/apache2/htdocs';
my $location = 'recordings';

my $q = new CGI;

my $sid = $q->param('sid');
my $date = $q->param('date');

if ($sid !~ /^A[0-9]+$/) {
    error("SID = \"$sid\"");
}
if ($date !~ /^([0-9]{4})-([0-9]{2})-([0-9]{2})$/) {
    error("DATE = \"$date\"");
}
my %date = ( 'yyyy' => $1, 'mm' => $2, 'dd' => $3);
my @now = localtime(time);
my $where;
if (sprintf("%02d", $now[5] + 1900) eq $date{'yyyy'} && sprintf("%02d", $now[4] + 1) eq $date{'mm'} && sprintf("%02d", $now[3]) eq $date{'dd'}) {
    $where = $location;
} else {
    $where = $location . '/' . $date{'yyyy'} . '/' . $date{'mm'} . '/' . $date{'dd'};
}
my $path = $basedir . '/' . $where;
opendir(DIR, $path) or error("opendir() failed: PATH = \"$path\", Error = \"$!\"");
my @match = grep(/$sid/, readdir(DIR));
closedir(DIR);

foreach my $entry (@match) {
    $entry = 'http://' . $ENV{'SERVER_NAME'} . ':' . $ENV{'SERVER_PORT'} . '/'. $where . '/' . $entry;
}

print "Content-Type: text/html\n\n" . join("\n", @match);

sub error {
    my ($what) = @_;

    print "Content-Type: text/html\n";
    print "Status: 400 Bad Request\n\n";
    print "Invalid Params: $what";
    exit(1);
}
