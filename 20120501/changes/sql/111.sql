select phns.name, pr, phns.phone, cp_id, organization_id, org_department_id
  from (select LPAD(' ', (LEVEL - 1) * 4, '.') || name name,
               t.pr,
               decode(t.pr, 3, t.name, null) phone,
               organization_id,
               org_department_id
          from (
                
                select *
                  from (select o.organization_id rid,
                                o.parrent_organization_id rpid,
                                'a' || o.organization_id id,
                                decode(o.parrent_organization_id,
                                       null,
                                       null,
                                       'a' || o.parrent_organization_id) pid,
                                o.organization_name name,
                                rownum rn,
                                1 pr,
                                level lev,
                                o.organization_id,
                                null org_department_id
                           from organizations o
                          start with o.organization_id = 80353
                         connect by prior o.organization_id =
                                     o.parrent_organization_id
                          order siblings by o.priority, o.super_priority, o.important_remark)
                union all
                select *
                  from (select /*+ index(d,IX_ORG_DEP_ORG_ID)*/
                         d.org_department_id cid,
                         d.parrent_department_id rpid,
                         'b' || d.org_department_id id,
                         decode(d.parrent_department_id,
                                null,
                                'a' || d.organization_id,
                                'b' || d.parrent_department_id) parent_id,
                         d.department,
                         rownum rn,
                         2 tp,
                         level lv,
                         d.organization_id,
                         d.org_department_id
                          from organization_department d
                         inner join ccare.organization_depart_to_phones odp
                            on odp.org_department_id = d.org_department_id
                         inner join (select o.organization_id
                                      from organizations o
                                     start with o.organization_id = 80353
                                    connect by prior o.organization_id =
                                                o.parrent_organization_id) k
                            on k.organization_id = d.organization_id
                         start with parrent_department_id is null
                        connect by prior
                                    d.org_department_id = parrent_department_id
                        
                        )
                union all
                select *
                  from (select pn.phone_number_id,
                               odp.org_department_id pid,
                               'c' || pn.phone_number_id,
                               'b' || odp.org_department_id,
                               cast(pn.phone as nvarchar2(4000)),
                               rownum,
                               3,
                               1,
                               organization_id,
                               odp.org_department_id
                          from organization_depart_to_phones odp
                         inner join phone_numbers pn
                            on pn.phone_number_id = odp.phone_number_id
                         inner join (select d.org_department_id,
                                           d.organization_id
                                      from organization_department d
                                     inner join (select o.organization_id
                                                  from organizations o
                                                 start with o.organization_id =
                                                            80353
                                                connect by prior
                                                            o.organization_id =
                                                            o.parrent_organization_id) k
                                        on k.organization_id =
                                           d.organization_id
                                     start with parrent_department_id is null
                                    connect by prior org_department_id =
                                                parrent_department_id) c
                            on c.org_department_id = odp.org_department_id
                         order by odp.org_department_id, pn.phone)) t
         start with pr = 1
        connect by prior id = pid
         order siblings by t.pr, t.lev, t.rn) phns
  left join ccare.contractor_phones t
    on t.contract_id = 5
   and phns.pr = 3
   and phns.phone = t.phone
