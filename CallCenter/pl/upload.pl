#!/usr/bin/perl -w

use strict;
use CGI;

my $img_dir = "/usr/local/apache2/htdocs/hrimages";

my $q = new CGI;
my $op = $q->param('op') || 'NULL';
$op = 'upload' unless ($op =~ /^(upload|save)$/);
my $id = $q->param('id') || 'NULL';
error('INPUT: invalid id') unless ($id =~ /^[0-9]{11}$/);
my $usr_dir = $img_dir . '/' . $id;
my $tmp_dir = $usr_dir . '/tmp';
my $fname = $q->param('file_name');
my $ext;
if ($fname =~ /\.(jpe?g|png|gif)$/) {
    $ext = $1;
} else {
    error('INPUT: invalid filename');
}
if ($op eq 'upload') {
    foreach my $dir ($usr_dir, $tmp_dir) {
	unless (-d $dir) {
	    mkdir($dir, 0777) or error('SYSTEM: mkdir failed');
	}
    }
    my @files = ls($tmp_dir);
    my $fd = $q->upload('image');
    $fname = time . '.' . $ext;
    open(FD, '>' .  $tmp_dir . '/' . $fname) or error('SYSTEM: open failed');
    while (<$fd>) {
	print FD;
    }
    close(FD);
    foreach my $file (@files) {
	unlink($file);
    }
} elsif ($op eq 'save') {
    error('SYSTEM: tmpdir not found') unless (-d $tmp_dir);
    error('INPUT: invalid filename') unless ($fname =~ /^[0-9]+\.$ext$/);
    error('SYSTEM: file not found') unless (-f $tmp_dir . '/' . $fname);
    my @files = ls($usr_dir);
    rename($tmp_dir . '/' . $fname, $usr_dir . '/' . $fname) or error('SYSTEM: rename failed');
    foreach my $file (@files) {
	unlink($file);
    }
}
print "Content-Type: text/html\n\n$fname";

sub error {
    my ($what) = @_;

    print "Content-Type: text/html\n";
    print "Status: 400 Bad Request\n\n";
    print "Error: $what";
    exit(1);
}

sub ls {
    my ($dir) = @_;

    opendir(DIR, $dir) or error('SYSTEM: opendir failed');
    my @files = grep(!/^\./, readdir(DIR));
    closedir(DIR);
    return @files;
}
