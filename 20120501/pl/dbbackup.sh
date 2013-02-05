#!/bin/bash                                                                                                                                            
                                                                                                                                                       
. $HOME/.bash_profile                                                                                                                                  
                                                                                                                                                       
BACKUP_HOME=/var/orabackup                                                                                                                             
BACKUP_DIR=$BACKUP_HOME/$(date +%Y.%m.%d)                                                                                                              
                                                                                                                                                       
[ ! -d $BACKUP_DIR ] && mkdir -p $BACKUP_DIR                                                                                                           
                                                                                                                                                       
cwd=$(pwd)                                                                                                                                             
cd $BACKUP_DIR                                                                                                                                         
exp ccare/ccare file=ccare.dmp log=ccare.log > $BACKUP_DIR/backup.log 2>&1                                                                             
cd $cwd                                                                                                                                                
                                                                                                                                                       
find $BACKUP_HOME -type d -mtime +7 | xargs rm -rf                                                                                                     