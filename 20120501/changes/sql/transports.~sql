select tt.name_descr,
       oc.town_name as otown_name,
       ic.town_name as itown_name,
       oc.town_name || ' ' || o.name_descr as transport_place_geo_out,
       ic.town_name || ' ' || i.name_descr as transport_place_geo_in,
       t.transp_model_descr as trip_criteria,
       tc.name_descr as transport_company_geo,
       tp.name_descr as transport_plane_geo,
       o.name_descr as ostation,
       i.name_descr as istation

  from ccare.transp_schedules t
 inner join ccare.transp_types tt
    on t.transp_type_id = tt.transp_type_id
  left join ccare.transp_companies tc
    on t.transp_comp_id = tc.transp_comp_id
  left join ccare.transp_resource tp
    on t.transp_res_id = tp.transp_res_id
 inner join ccare.transp_stations o
    on o.transp_stat_id = t.depart_transp_stat_id
 inner join ccare.transp_stations i
    on i.transp_stat_id = t.arrival_transp_stat_id
  left join ccare.towns oc
    on oc.town_id = o.town_id
  left join ccare.towns ic
    on ic.town_id = i.town_id
