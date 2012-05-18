package com.info08.billing.callcenterbk.client.ui.menu;

import com.info08.billing.callcenterbk.client.CallCenterBK;
import com.info08.billing.callcenterbk.client.content.survey.TabSurvey;
import com.info08.billing.callcenterbk.client.content.survey.TabSurveyCallCenterNews;
import com.info08.billing.callcenterbk.client.content.survey.TabSurveyHist;
import com.info08.billing.callcenterbk.client.content.survey.TabSurveyReplyType;
import com.info08.billing.callcenterbk.client.content.survey.TabSurveyKinds;
import com.info08.billing.callcenterbk.client.singletons.CommonSingleton;
import com.info08.billing.callcenterbk.client.ui.layout.Body;
import com.smartgwt.client.types.TreeModelType;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.events.DoubleClickEvent;
import com.smartgwt.client.widgets.events.DoubleClickHandler;
import com.smartgwt.client.widgets.grid.CellFormatter;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.layout.SectionStackSection;
import com.smartgwt.client.widgets.tree.Tree;
import com.smartgwt.client.widgets.tree.TreeGrid;
import com.smartgwt.client.widgets.tree.TreeGridField;
import com.smartgwt.client.widgets.tree.TreeNode;

public class SurveyStackSelection extends SectionStackSection {

	private Body body;
	public static final TreeNode[] menuData = new TreeNode[] {
			new MenuNode("100", "1",
					CallCenterBK.constants.menuSurveyStatuses(), true,
					"survey.png"),
			new MenuNode("101", "2", CallCenterBK.constants.menuSurveyKinds(),
					true, "survey.png"),
			new MenuNode("102", "3", CallCenterBK.constants.menuSurvey(), true,
					"survey.png"),
			new MenuNode("103", "4", CallCenterBK.constants.menuSurveyHist(),
					true, "survey.png"),
			new MenuNode("104", "5",
					CallCenterBK.constants.menuCallCenterNews(), true,
					"survey.png") };

	private TreeGrid employeeTreeGrid;

	public SurveyStackSelection(Body body) {
		this.body = body;
		setTitle(CallCenterBK.constants.surveyActions());
		setExpanded(false);
		setCanCollapse(true);

		Tree employeeTree = new Tree();
		employeeTree.setModelType(TreeModelType.PARENT);
		employeeTree.setRootValue(1);
		employeeTree.setNameProperty(CallCenterBK.constants.actionsList());
		employeeTree.setIdField("Id");
		employeeTree.setParentIdField("ReportsTo");
		employeeTree.setOpenProperty("isOpen");
		employeeTree.setData(menuData);

		TreeGridField formattedField = new TreeGridField(
				CallCenterBK.constants.actionsList());
		formattedField.setCellFormatter(new CellFormatter() {
			public String format(Object value, ListGridRecord record,
					int rowNum, int colNum) {
				return record.getAttributeAsString("Name");
			}
		});

		employeeTreeGrid = new TreeGrid();

		employeeTreeGrid.setWidth100();
		employeeTreeGrid.setHeight100();
		employeeTreeGrid.setCanReorderRecords(false);
		employeeTreeGrid.setCanAcceptDroppedRecords(false);
		employeeTreeGrid.setShowOpenIcons(false);
		employeeTreeGrid.setDropIconSuffix("into");
		employeeTreeGrid.setClosedIconSuffix("");
		employeeTreeGrid.setData(employeeTree);
		employeeTreeGrid.setFields(formattedField);
		employeeTreeGrid.setCanAutoFitFields(false);
		employeeTreeGrid.setCanSort(false);
		employeeTreeGrid.setCanAcceptDrop(false);
		employeeTreeGrid.setCanAcceptDroppedRecords(false);
		employeeTreeGrid.setCanDrag(false);
		employeeTreeGrid.setCanDrop(false);
		employeeTreeGrid.setCanSelectAll(false);
		employeeTreeGrid.setCanEdit(false);

		employeeTreeGrid.addDoubleClickHandler(new DoubleClickHandler() {
			@Override
			public void onDoubleClick(DoubleClickEvent event) {
				openTab(employeeTreeGrid.getSelectedRecord()
						.getAttributeAsString("Id"));
			}
		});

		addItem(employeeTreeGrid);
	}

	public void setMenuPersmission() {
		try {
			boolean hasDiscStatPerm = CommonSingleton.getInstance()
					.hasPermission("401000");
			menuData[0].setAttribute("enabled", hasDiscStatPerm);

			boolean hasDiscTypesPerm = CommonSingleton.getInstance()
					.hasPermission("402000");
			menuData[1].setAttribute("enabled", hasDiscTypesPerm);

			boolean hasDiscrPerm = CommonSingleton.getInstance().hasPermission(
					"403000");
			menuData[2].setAttribute("enabled", hasDiscrPerm);

			boolean hasDiscHistPerm = CommonSingleton.getInstance()
					.hasPermission("403000");
			menuData[3].setAttribute("enabled", hasDiscHistPerm);

			boolean hasCallCenterNewsPerm = CommonSingleton.getInstance()
					.hasPermission("404000");

			menuData[4].setAttribute("enabled", hasCallCenterNewsPerm);

		} catch (Exception e) {
			e.printStackTrace();
			SC.say(e.toString());
		}
	}

	private void openTab(String menuId) {
		if (menuId == null || menuId.trim().equals("")) {
			return;
		}
		if (menuId.equals("100")) {
			TabSurveyReplyType tabSurveyReplyType = new TabSurveyReplyType();
			body.addTab(tabSurveyReplyType);
		} else if (menuId.equals("101")) {
			TabSurveyKinds tabSurveyKinds = new TabSurveyKinds();
			body.addTab(tabSurveyKinds);
		} else if (menuId.equals("102")) {
			TabSurvey tabSurvey = new TabSurvey();
			body.addTab(tabSurvey);
		} else if (menuId.equals("103")) {
			TabSurveyHist tabSurveyHist = new TabSurveyHist();
			body.addTab(tabSurveyHist);
		} else if (menuId.equals("104")) {
			TabSurveyCallCenterNews tabSurveyCallCenterNews = new TabSurveyCallCenterNews();
			body.addTab(tabSurveyCallCenterNews);
		}
	}

	public static class MenuNode extends TreeNode {
		public MenuNode(String id, String reportsTo, String name,
				boolean isOpen, String icon) {
			setAttribute("Id", id);
			setAttribute("ReportsTo", reportsTo);
			setAttribute("Name", name);
			setAttribute("isOpen", isOpen);
			setIcon(icon);
		}
	}
}
