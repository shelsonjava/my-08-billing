package com.info08.billing.callcenterbk.client.utils;

import java.util.ArrayList;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import com.google.gwt.user.client.ui.HTMLPanel;
import com.smartgwt.client.data.Criteria;
import com.smartgwt.client.data.DataSource;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.ComboBoxItem;
import com.smartgwt.client.widgets.form.fields.FormItem;
import com.smartgwt.client.widgets.form.fields.SelectItem;
import com.smartgwt.client.widgets.form.fields.events.ChangedEvent;
import com.smartgwt.client.widgets.form.fields.events.ChangedHandler;
import com.smartgwt.client.widgets.form.fields.events.KeyDownEvent;
import com.smartgwt.client.widgets.form.fields.events.KeyDownHandler;
import com.smartgwt.client.widgets.form.fields.events.KeyPressEvent;
import com.smartgwt.client.widgets.form.fields.events.KeyPressHandler;
import com.smartgwt.client.widgets.grid.ListGridRecord;

public class ClientUtils {

	public static void makeDependancy(final FormItem formItemParent,
			String publicParentId, final FormItem... formItemChilds) {
		makeDependancy(formItemParent, publicParentId, null, formItemChilds);
	}

	public static void makeDependancy(final FormItem formItemParent,
			String publicParentId, Map<?, ?> publicAditionalCriteria,
			final FormItem... formItemChilds) {
		makeDependancy(formItemParent, null, publicParentId, null, false,
				formItemChilds);
	}

	public static void makeDependancy(final FormItem formItemParent,
			String valueSet, String publicParentId,
			Map<?, ?> publicAditionalCriteria, boolean set,
			final FormItem... formItemChilds) {
		ArrayList<FormItemDescr> newformItemChilds = new ArrayList<FormItemDescr>();
		for (FormItem formItem : formItemChilds) {
			newformItemChilds.add(new FormItemDescr(formItem, publicParentId,
					publicAditionalCriteria, valueSet));
		}
		makeDependancy(formItemParent, set,
				newformItemChilds.toArray(new FormItemDescr[] {}));
	}

	public static void makeDependancy(final FormItem formItemParent,
			final boolean set, final FormItemDescr... formItemChilds) {
		formItemParent.addChangedHandler(new ChangedHandler() {

			@Override
			public void onChanged(ChangedEvent event) {
				Object value = formItemParent.getValue();
				for (FormItemDescr formItem : formItemChilds) {
					formItem.formItem.clearValue();
					if ((formItem.formItem instanceof ComboBoxItem)
							|| (formItem.formItem instanceof SelectItem)) {
						Criteria cr = formItem.formItem.getOptionCriteria();
						if (cr == null) {
							cr = new Criteria();
							formItem.formItem.setOptionCriteria(cr);
						}
						cr.setAttribute(formItem.parentName, value);
						addEditionalCriteria(formItem.aditionalCriteria, cr);
						formItem.formItem.setOptionCriteria(cr);
					}

					if (set && formItem.valueSet != null) {
						ListGridRecord record = formItemParent
								.getSelectedRecord();
						String val = null;
						if (record != null) {
							val = record.getAttribute(formItem.valueSet);
						}
						formItem.formItem.setValue(val);
					}

				}

			}
		});
	}

	public static void fillDescriptionCombo(final FormItem formItem, int type) {
		fillDescriptionCombo(formItem, type, false);
	}

	public static void fillDescriptionCombo(final FormItem formItem, int type,
			boolean sortByName) {
		fillDescriptionCombo(formItem, type, sortByName, null);
	}

	public static void fillDescriptionCombo(final FormItem formItem, int type,
			boolean sortByName, Map<String, Object> aditionalCriteria) {
		if (aditionalCriteria == null)
			aditionalCriteria = new TreeMap<String, Object>();
		aditionalCriteria.put("description_type_id", type);

		if (sortByName) {
			aditionalCriteria.put("by_name", 1);
		}
		fillCombo(formItem, "DescriptionsDS", "searchDescriptions",
				"description_id", "description", aditionalCriteria);
	}

	public static void fillCombo(final FormItem formItem, String sDataSource,
			String sFetchOperation, String valueField, String nameField) {
		fillCombo(formItem, sDataSource, sFetchOperation, valueField,
				nameField, null);
	}

	public static void fillCombo(final FormItem formItem, String sDataSource,
			String sFetchOperation, final String valueField, String nameField,
			Map<?, ?> aditionalCriteria) {

		try {
			if (!(formItem instanceof ComboBoxItem)
					&& !(formItem instanceof SelectItem)) {
				return;
			}

			formItem.setFetchMissingValues(true);
			formItem.setFilterLocally(false);
			if (formItem instanceof ComboBoxItem) {
				ComboBoxItem cItem = (ComboBoxItem) formItem;
				cItem.setAddUnknownValues(false);
				cItem.setAutoFetchData(false);
				// cItem.setTextMatchStyle(TextMatchStyle.SUBSTRING);

			} else {
				SelectItem sItem = (SelectItem) formItem;
				sItem.setAddUnknownValues(false);
				sItem.setAutoFetchData(false);

			}

			formItem.setFilterLocally(false);
			formItem.setFetchMissingValues(true);

			DataSource comboDS = DataSource.get(sDataSource);
			formItem.setOptionOperationId(sFetchOperation);
			formItem.setOptionDataSource(comboDS);
			formItem.setValueField(valueField);
			formItem.setDisplayField(nameField);
			Criteria criteria = new Criteria();
			addEditionalCriteria(aditionalCriteria, criteria);

			formItem.setOptionCriteria(criteria);
			if (formItem instanceof SelectItem)
				formItem.addKeyDownHandler(new KeyDownHandler() {

					@Override
					public void onKeyDown(KeyDownEvent event) {
						String key = event.getKeyName();
						if ((key.equals("Escape") || key.equals("Delete"))) {
							formItem.clearValue();
						}

					}
				});
			formItem.addKeyPressHandler(new KeyPressHandler() {
				@Override
				public void onKeyPress(KeyPressEvent event) {

					Criteria criteria = formItem.getOptionCriteria();
					if (criteria != null) {
						Object oldAttr = criteria.getAttribute(valueField);
						if (oldAttr != null) {
							criteria.setAttribute(valueField, (String) null);
						}
					}
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
			SC.say(e.toString());
		}
	}

	public static void setDefauldCriterias(FormItem formItem,
			Map<?, ?> aditionalCriteria) {
		Criteria criteria = new Criteria();
		addEditionalCriteria(aditionalCriteria, criteria);
		formItem.setOptionCriteria(criteria);
	}

	private static void addEditionalCriteria(Map<?, ?> aditionalCriteria,
			Criteria criteria) {
		if (aditionalCriteria != null) {
			Set<?> keys = aditionalCriteria.keySet();
			for (Object key : keys) {
				if (key != null) {
					Object value = aditionalCriteria.get(key);
					if (key != null)
						criteria.setAttribute(key.toString(), value);
				}
			}
		}

		criteria.setAttribute("_UUUUUUUIDUUU", HTMLPanel.createUniqueId());
	}

	public static Map<String, Object> fillMapFromForm(Map<String, Object> mp,
			DynamicForm... dms) {
		if (mp == null)
			mp = new TreeMap<String, Object>();
		for (DynamicForm dm : dms) {
			Map<?, ?> vals = dm.getValues();
			Set<?> keys = vals.keySet();
			for (Object key : keys) {
				String sKey = key.toString();
				if (dm.getField(sKey) != null) {
					mp.put(sKey, vals.get(key));
				}
			}
		}
		mp.remove("_ref");
		return mp;
	}

}
