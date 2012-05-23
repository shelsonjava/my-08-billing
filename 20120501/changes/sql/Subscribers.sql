select t.subscriber_id,
       addr_id,
       lnm.lastname,
       fn.firstname name,
       (select wm_concat(decode(sp.hidden_by_request, 1, 'დ*', '') ||
                         pn.phone)
          from subscriber_to_phones sp
         inner join phone_numbers pn
            on pn.phone_number_id = sp.phone_number_id
         where sp.subscriber_id = t.subscriber_id) phones,
       tow.town_name,
       str.street_name,
       str.street_location,
       t.name_id,
       t.family_name_id,
       str.town_id city_id,
       ma.street_id,
       sd.town_district_id,
       ma.hidden_by_request,
       ma.full_address,
       ma.anumber,
       ma.block,
       ma.appt,
       ma.descr,
       sd.street_to_town_district_id,
       str.street_location || ' ' ||
       (decode(ma.anumber, null, '', ma.anumber) ||
       decode(ma.block, null, '', 'კ.' || ma.block) ||
       decode(ma.appt, null, '', ' ბ.' || ma.appt) || ma.descr) as address
  from subscribers              t,
       addresses                ma,
       street_to_town_districts sd,
       streets                  str,
       towns                    tow,
       firstnames               fn,
       lastnames                lnm
 where 1 = 1
   and ma.owner_id = t.subscriber_id
   and ma.owner_type = 0
   and ma.street_id = str.street_id
   and sd.street_id(+) = str.street_id
   and ma.town_id = tow.town_id
   and fn.firstname_id = t.name_id
   and lnm.lastname_id = t.family_name_id
   and t.subscriber_id in (70508, 384419);
