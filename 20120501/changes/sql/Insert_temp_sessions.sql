drop table temp_calls;
create table temp_calls as 
select cp.phone,sys_guid() session_id,to_char(sysdate,'YYMM') YM,sysdate+abs(dbms_random.value)/24 dt from CONTRACTOR_PHONES cp
inner join (
select  level
from    dual
connect by level <= trunc(abs(dbms_random.value)*10000)) on 1=1 ;
insert into call_sessions
  (call_session_id,
   session_id,
   year_month,
   uname,
   call_start_date,
   call_end_date,
   call_phone,
   switch_over_type,
   call_kind,
   call_duration,
   reject_type,
   call_quality)

  select seq_call_session_id.nextval,t.session_id,t.YM,'paata', dt,dt+1/24/60,t.phone,null,-3,1,null,10 from TEMP_CALLS t;


insert into call_session_expense
  (cse_id, year_month, session_id, service_id, charge, charge_date, call_session_id)


  select seq_call_sess_exp_id.nextval,t.YM,t.session_id,3,10,dt+1/24/60,t.phone from TEMP_CALLS t;
commit;
