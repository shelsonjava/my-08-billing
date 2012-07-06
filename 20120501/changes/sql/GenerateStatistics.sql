truncate table call_session_expense;
   truncate table ERIC.CDR ;
truncate table call_sessions;

insert into call_session_expense
  (cse_id,                          year_month, session_id, service_id, charge, charge_date, call_session_id)
select seq_call_sess_exp_id.nextval,ym,          session_id, service_id, price, rec_date,1 from LOG_SESSION_CHARGES@ORCL_DEV t
where t.ym between 1202 and 1207 ;
commit;


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

  select seq_call_session_id.nextval,
         session_id,
         ym,
         user_name,
         start_date,
         end_date,
         phone,
         parent_id,
         call_type,
         duration,
         hungup,
         session_quality
    from log_sessions@ORCL_DEV t
   where t.ym between 1202 and 1207 --and trunc(t.start_date)=to_date('2JUNE2012')
   ;
   commit;
insert into ERIC.CDR 
select * from ERIC.CDR@ORCL_DEV t
where trunc(t.calldate) between to_date('01JAN2012') and to_date('30JUNE2013');
commit;

/*begin
  -- Call the procedure
  statisticsgenerator(to_date('28JUNE2012'));
end;

select * from statistic t
where t.ym=1206 and t.stat_date= to_date('28JUNE2012');
select * from STATISTIC_BY_BILLING_COMPANY t
inner join billing_companies bc on t.billing_company_id=bc.billing_company_id
where t.ym=1206 and t.stat_date= to_date('28JUNE2012')
order by bc.billing_company_name;

select * from STATISTIC@ORCL_DEV t
where t.ym=1206 and t.stat_date= to_date('28JUNE2012');
select * from statistic_by_telcomp@ORCL_DEV t
inner join tel_comps@ORCL_DEV bc on bc.tel_comp_id=t.tel_comp_id
where t.ym=1206 and t.stat_date= to_date('28JUNE2012')
order by bc.tel_comp_name_geo;



*/
