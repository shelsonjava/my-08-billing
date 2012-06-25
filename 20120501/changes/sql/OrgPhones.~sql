create table aaa as 
select h.*, p.phone
  from (select organization_id ,organization_name,
               level deplevel,
               orgLevel,
               department department,
               org_department_id,
               rownum deprowNum
          from (select org.*,
                       od.org_department_id,
                       od.department,
                       od.parrent_department_id,
                        rownum orgNum
                  from (select o.organization_id,
                               o.organization_name,
                               parrent_organization_id,
                               level orgLevel,
                               o.priority,
                               o.super_priority,
                               o.important_remark
                          from organizations o
                         start with o.organization_id = 110081
                        connect by prior o.organization_id =
                                    o.parrent_organization_id) org
                  left join organization_department od
                    on org.organization_id = od.organization_id) st
         start with st.parrent_department_id is null
        connect by prior org_department_id = parrent_department_id
         order siblings by orgNum) h
  left join organization_depart_to_phones op
    on op.org_department_id = h.org_department_id
  left join phone_numbers p
    on p.phone_number_id = op.phone_number_id
 order by deprowNum, p.phone
