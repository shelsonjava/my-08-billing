create table tmp_subscriber_address_migr as
select SEQ_ADDRESS_ID.nextval new_id,
 address_id,
 abonent_id,
 city_id,
 street_id,
 address_suffix_geo,
 address_hide,
 city_region_id,
 addr_block,
 addr_appt,
 addr_descr,
 addr_number,
 (select s.new_id
    from tmp_subscribers_migr s
   where s.abonent_id = t.abonent_id) new_subs_id
  from paata.tmp_subscriber_address_migr@orcl_dev t;
  

create table tmp_subscriber_phone as
select SEQ_PHONE_NUMBERS.NEXTVAL new_id,
       phone_id,
       phone,
       t.phone_state_id,
       t.phone_type_id,
       nvl(ps.nid,52100) as new_phone_state_id,
       nvl(pt.nid,54100) as new_phone_type_id,
       ps.nid as unknown_phone_state_id,
       pt.nid as unknown_phone_type_id
  from paata.tmp_subscriber_phone@orcl_dev t
  left join ccare.phone_states ps on ps.phone_state_id=t.phone_state_id
  left join ccare.phone_types pt on pt.phone_type_id=t.phone_type_id;
  
  
create table tmp_subscribers_migr as
select seq_subscriber_id.nextval new_id,
       abonent_id,
       phone_status_id,
       contact_phones,
       firstname_id,
       lastname_id,
       deleted
  from paata.tmp_subscribers_migr@orcl_dev;
  
  
  
create table tmp_subscribers_phones_migr as  
select seq_subscriber_to_phones_id.nextval as new_id,
       id,
       abonent_id,
       phone_id,
       is_hide,
       is_parallel,
       t.phone_status_id,
       (select p.new_id from tmp_subscriber_phone p where p.phone_id=t.phone_id) new_phone_id,
       (select s.new_id from tmp_subscribers_migr s where s.abonent_id=t.abonent_id) new_subscriber_id,
       nvl(ps.neid,53102) as new_phone_status_id,
       ps.neid unknown_phone_status_id
  from paata.tmp_subscribers_phones_migr@orcl_dev t
  left join ccare.phone_statuses ps on ps.phone_status_id=t.phone_status_id;
  
  
  
  insert into subscribers
  (subscriber_id, name_id, family_name_id, old_id, delll)
select new_id,
       firstname_id,
       lastname_id,
       abonent_id,
       deleted
  from tmp_subscribers_migr
;


insert into subscriber_to_phones
  (subscriber_to_phones_id, subscriber_id, phone_number_id, hidden_by_request, phone_contract_type, old_sub_id, old_phone, OLD_ID)

select new_id,
       new_subscriber_id,
       new_phone_id,
       is_hide,
       new_phone_status_id,
       abonent_id,
       phone_id,
       id
  from tmp_subscribers_phones_migr;
  
  insert into phone_numbers
  (phone_number_id, phone, phone_state_id, phone_type_id, is_parallel, old_id)
select t.new_id,
       t.phone,
       t.new_phone_state_id,
       t.new_phone_type_id,
       t1.is_parallel,
       t.phone_id
       
  from tmp_subscriber_phone t
  inner join tmp_subscribers_phones_migr t1 on t1.new_phone_id=t.new_id
;

create table tmp_delete_phones as
select t.phone, min(t.phone_number_id) stay, max(t.phone_number_id) del from PHONE_NUMBERS t
where  
t.phone in (select t1.phone from PHONE_NUMBERS t1 group by t1.phone having count(1)>1)
group by t.phone
order by 1;

delete from PHONE_NUMBERS t where t.phone_number_id in(select t1.del from tmp_delete_phones t1 );

update subscriber_to_phones sp set sp.phone_number_id =
(select t.stay from TMP_DELETE_PHONES t where t.del=sp.phone_number_id)
where sp.phone_number_id in
(select t.del from TMP_DELETE_PHONES t);
insert into addresses
  (addr_id,
   owner_id,
   town_id,
   street_id,
   full_address,
   hidden_by_request,
   town_district_id,
   block,
   appt,
   descr,
   anumber,
   owner_type,
   adress_type,
   OLD_ID,
   OLD_SUBS)
  select new_id,
         new_subs_id,
         city_id,
         street_id,
         address_suffix_geo,
         address_hide,
         city_region_id,
         addr_block,
         addr_appt,
         addr_descr,
         addr_number,
         0,
         0,
         address_id,
         abonent_id
    from tmp_subscriber_address_migr;



test

create table tmp as
select SDFSDF.nextval new_id, k.*
  from (select abonent_id,
               phone_status_id,
               contact_phones,
               firstname_id,
               lastname_id,
               deleted
          from paata.tmp_subscribers_migr@orcl_dev
         order by contact_phones,
                  lastname_id desc,
                  firstname_id,
                  phone_status_id) k;
