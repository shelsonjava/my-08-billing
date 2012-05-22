select t.subscriber_id,
       addr_id,
       lnm.lastname family_name,
       fn.firstname name,
       (select wm_concat(decode(sp.hidden_by_request, 1, 'დ*', '') ||
                         pn.phone)
          from subscriber_to_phones sp
         inner join phone_numbers pn
            on pn.phone_number_id = sp.phone_number_id
         where sp.subscriber_id = t.subscriber_id) phones,
       tow.town_name city,
       str.street_name street,
       str.street_location street_location_geo,
       t.name_id,
       t.family_name_id,
       str.town_id city_id,
       ma.street_id,
       city_region_id,
       ma.hidden_by_request address_hide,
       ma.full_address address_suffix_geo,
       ma.anumber addr_number,
       ma.block addr_block,
       ma.appt addr_appt,
       ma.descr addr_descr,
       sd.street_district_id --,    
--ma.address  
  from subscribers              t,
       addresses                ma,
       street_to_town_districts sd,
       streets                  str,
       towns                    tow,
       firstnames               fn,
       lastnames                lnm
    where 1=1
    and ma.owner_id=t.subscriber_id and ma.owner_type=0
    and ma.street_id=str.street_id
    and sd.street_id(+)=str.street_id
    and ma.town_id=tow.town_id
    and fn.firstname_id = t.name_id
    and lnm.lastname_id=t.family_name_id
    and t.subscriber_id in (70508,384419);
    

