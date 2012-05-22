select * from tmp_subscribers_phones_migr;
select * from tmp_subscribers_migr tt where tt.deleted = 0
select * from tmp_subscriber_phone t;
select * from tmp_subscriber_address_migr t;


select address_id, abonent_id, city_id, street_id, address_suffix_geo, address_hide, city_region_id, addr_block, addr_appt, addr_descr, addr_number from paata.tmp_subscriber_address_migr
select phone_id, phone, phone_state_id, phone_type_id from paata.tmp_subscriber_phone
select abonent_id, phone_status_id, contact_phones, firstname_id, lastname_id, new_id, deleted from paata.tmp_subscribers_migr 
select id, abonent_id, phone_id, is_hide, is_parallel, phone_status_id from paata.tmp_subscribers_phones_migr ;



