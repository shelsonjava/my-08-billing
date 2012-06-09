select 'insert into ' || HIST_TABLE ||
       '(hist_start_date,on_user,start_rcn,' || COLUMN_NAMES ||
       ') SELECT p_date,p_user, p_rcn,  d.* from (' || 'SELECT :new.' ||
       replace(COLUMN_NAMES,',',',:new.')  || ' FROM dual minus ' ||
       'SELECT :old.' || replace(COLUMN_NAMES,',',',:old.') ||
       ' FROM dual ) d;'
  from (select uc.TABLE_NAME,
               uc1.TABLE_NAME HIST_TABLE,
               wm_concat(uc.COLUMN_NAME) COLUMN_NAMES
          from user_tab_columns uc
         inner join user_tab_columns uc1
            on upper(uc1.TABLE_NAME) = upper('HIST_' || uc.TABLE_NAME)
           and uc.COLUMN_NAME = uc1.COLUMN_NAME
         where upper(uc.TABLE_NAME) = upper('town_district')
         group by uc1.TABLE_NAME, uc.TABLE_NAME) k;
