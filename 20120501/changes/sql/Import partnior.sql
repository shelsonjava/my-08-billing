ALTER TRIGGER tr_partnior_table_hist DISABLE;

ALTER TABLE partnior_table DISABLE CONSTRAINT pk_partnior_table; 

insert into partnior_table
  (phone_number, subscriber_name, address, additional_address, subscriber_type)
select 
       substr(t."Field1", 2, length(t."Field1") - 2) phone_number,
       substr(trim(t."Field3"), 2, length(trim(t."Field3")) - 2) name,
       substr(trim(t."Field4"), 2, length(trim(t."Field4")) - 2) address,
       substr(trim(t."Field5"), 2, length(trim(t."Field5")) - 2) additional_value,
       substr(t."Field2", 2, length(t."Field2") - 2) substype
  from "For_08" t;

update PARTNIOR_TABLE t set t.phone_number_id =(select pn.phone_number_id from phone_numbers pn where pn.phone=to_char(t.phone_number));
update PARTNIOR_TABLE t set-- t.subscriber_id =(select sp.subscriber_id from subscriber_to_phones sp where sp.phone_number_id=t.phone_number_id),
(t.ORG_DEPARTMENT_IDS,t.Org_Department_Count)=(select wm_concat(dp.org_department_id),decode(count(1),0,null,count(1)) from organization_depart_to_phones dp where dp.phone_number_id=t.phone_number_id)
where t.phone_number_id is not null;
update PARTNIOR_TABLE t set t.phone_number_id=null where t.phone_number_id is not null and t.subscriber_id is null and t.org_department_ids is null;
--update PARTNIOR_TABLE t set t.priority=null;
update PARTNIOR_TABLE t set t.priority=0 where t.phone_number_id is null;
update PARTNIOR_TABLE t
   set t.priority = case
                      when t.org_department_count > 1 and
                           t.subscriber_id is null then
                       1
                      when t.org_department_count = 1 and
                           t.subscriber_id is null then
                       2
                      when t.org_department_count > 1 and
                           t.subscriber_id is not null then
                       3
                      when t.org_department_count = 1 and
                           t.subscriber_id is not null then
                       4
                      when t.org_department_ids is null and
                           t.subscriber_id is not null then
                       5
                    end
 where t.phone_number_id is not null;

update PARTNIOR_TABLE t
   set (t.subscriber_name_local, t.subscriber_address) =
       (select sh.family_name || ' ' || sh.name mybase_subscriber_name,
               sh.town_name || ' ' ||
               decode(sh.town_district_name,
                      null,
                      '',
                      '(' || sh.town_district_name || ') ') ||
               sh.street_name || ' ' || sh.calc_short_address mybase_subscriber_address
          from v_short_subscriber sh
         where t.subscriber_id = sh.subscriber_id)
 where t.subscriber_id is not null;
update PARTNIOR_TABLE t
   set (t.organization_name, t.organization_address) =
       (select o.organization_name || '(' || od.department || ')' mybase_org_name,
               oda.town_name || ' ' ||
               decode(oda.town_district_name,
                      null,
                      '',
                      '(' || oda.town_district_name || ') ') ||
               oda.street_name || ' ' || oda.calc_short_address mybase_org_address
          from organization_department od
         inner join organizations o
            on o.organization_id = od.organization_id
         inner join v_full_address oda
            on oda.addr_id = o.physical_address_id
         where t.org_department_ids = od.org_department_id)
 where t.org_department_ids is not null
   and t.org_department_count = 1;
ALTER TRIGGER tr_partnior_table_hist ENABLE;

ALTER TABLE partnior_table ENABLE CONSTRAINT pk_partnior_table; 
