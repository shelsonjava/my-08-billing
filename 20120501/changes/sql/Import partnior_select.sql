select t.phone_number,
       t.subscriber_name,
       sh.family_name || ' ' || sh.name mybase_subscriber_name,
       o.organization_name || '(' || od.department || ')' mybase_org_name,
       t.address || ' ' || t.additional_address partnior_address,
       sh.town_name || ' ' ||
       decode(sh.town_district_name,
              null,
              '',
              '(' || sh.town_district_name || ') ') || sh.street_name || ' ' ||
       sh.calc_short_address mybase_subscriber_address,
       oda.town_name || ' ' ||
       decode(oda.town_district_name,
              null,
              '',
              '(' || oda.town_district_name || ') ') || oda.street_name || ' ' ||
       oda.calc_short_address mybase_org_address
  from PARTNIOR_TABLE t
 inner join v_short_subscriber sh
    on t.subscriber_id = sh.subscriber_id
 inner join organization_department od
    on od.org_department_id = t.org_department_ids
 inner join organizations o
    on o.organization_id = od.organization_id
 inner join v_full_address oda
    on oda.addr_id = o.physical_address_id
 where t.org_department_count = 1
 order by t.priority, t.phone_number, t.org_department_count desc;
