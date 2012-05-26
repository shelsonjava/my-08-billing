select pn.phone,
       pr.owner_type,
       pr.owner_id,
       decode(pr.owner_type,
              0,
              map_char_codes_geo('abon.'),
              map_char_codes_geo('org.')) owner_type_descr,
       decode(pr.owner_type,
              0,
              sub.name || ' ' || sub.family_name,
              o.organization_name || ',' || od.department) full_name,
       decode(pr.owner_type, 0, sub.addr_id, oda.addr_id) addr_id,
       decode(pr.owner_type, 0, 0, o.super_priority) super_priority,
       decode(pr.owner_type, 0, 0, o.important_remark) important_remark,
       decode(pr.owner_type, 0, 0, o.status) status,
       decode(pr.owner_type, 0, sub.town_name, oda.town_name) town_name,
       nvl(decode(pr.owner_type,
                  0,
                  sub.town_district_name,
                  oda.town_district_name) || ' - ',
           '') ||
       decode(pr.owner_type, 0, sub.street_name, oda.street_name) address,
       decode(pr.owner_type,
              0,
              sub.hidden_by_request,
              oda.hidden_by_request) address_hidden_by_request,
       map_char_codes_geo('mis.:') ||
       decode(decode(pr.owner_type,
                     0,
                     sub.hidden_by_request,
                     oda.hidden_by_request),
              0,
              map_char_codes_geo('Ria'),
              1,
              map_char_codes_geo('dafaruli'),
              map_char_codes_geo('Ria')) address_hidden_by_request_des,
       pr.hidden_by_request phone_hidden_by_request,
       map_char_codes_geo('ტელ.:') ||
       decode(pr.hidden_by_request,
              0,
              map_char_codes_geo('Ria'),
              1,
              map_char_codes_geo('dafaruli'),
              map_char_codes_geo('Ria')) phone_hidden_by_request_desc,
       pr.phone_contract_type,
       getdescription(pr.phone_contract_type) phone_contract_type_desc,
       pn.phone_state_id,
       getdescription(pn.phone_state_id) phone_state_id_desc,
       pn.phone_type_id,
       getdescription(pn.phone_type_id) phone_type_id_desc,
       pn.is_parallel,
       decode(pn.is_parallel,
              0,
              map_char_codes_geo('pirdapiri'),
              1,
              map_char_codes_geo('paraleluri'),
              map_char_codes_geo('pirdapiri')) is_parallel_descr,
       pr.for_contact,
       decode(pr.for_contact,
              0,
              map_char_codes_geo('arasakontaqto'),
              1,
              map_char_codes_geo('sakontaqto'),
              map_char_codes_geo('arasakontaqto')) for_contact_desr

  from phone_numbers pn
 inner join (select /*+ index(sp,IDX_SUBSCRIBER_TO_PHONES_ID)*/
              0                      owner_type,
              sp.subscriber_id       owner_id,
              sp.phone_number_id,
              sp.hidden_by_request,
              sp.phone_contract_type,
              1                      for_contact
               from SUBSCRIBER_TO_PHONES sp
             union all
             select /*+ index(odp,IDX_ORG_DEP_ID)*/
              1,
              odp.org_department_id,
              odp.phone_number_id,
              odp.hidden_by_request,
              odp.phone_contract_type,
              odp.for_contact
               from organization_depart_to_phones odp) pr
    on pr.phone_number_id = pn.phone_number_id
  left join v_full_subscriber sub
    on pr.owner_type = 0
   and sub.subscriber_id = pr.owner_id
  left join organization_department od
    on pr.owner_type = 1
   and od.org_department_id = pr.owner_id
  left join organizations o
    on o.Organization_Id = od.organization_id
  left join v_full_address oda
    on oda.addr_id = o.physical_address_id
 where pn.phone = '2272727'
