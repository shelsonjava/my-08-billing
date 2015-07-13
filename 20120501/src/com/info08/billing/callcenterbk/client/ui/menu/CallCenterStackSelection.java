package com.info08.billing.callcenterbk.client.ui.menu;

import com.info08.billing.callcenterbk.client.CallCenterBK;
import com.info08.billing.callcenterbk.client.content.callcenter.TabFindSubscriber;
import com.info08.billing.callcenterbk.client.content.callcenter.TabFindByNumber;
import com.info08.billing.callcenterbk.client.content.callcenter.TabFindFacts;
import com.info08.billing.callcenterbk.client.content.callcenter.TabFindCurrencyRates;
import com.info08.billing.callcenterbk.client.content.callcenter.TabFindDistBetweenTowns;
import com.info08.billing.callcenterbk.client.content.callcenter.TabFindDistrictVillageIndexes;
import com.info08.billing.callcenterbk.client.content.callcenter.TabFindInCityTransport;
import com.info08.billing.callcenterbk.client.content.callcenter.TabFindIndexes;
import com.info08.billing.callcenterbk.client.content.callcenter.TabFindCallCenterNews;
import com.info08.billing.callcenterbk.client.content.callcenter.TabFindNonStandartInfo;
import com.info08.billing.callcenterbk.client.content.callcenter.TabFindOrg;
import com.info08.billing.callcenterbk.client.content.callcenter.TabFindEvent;
import com.info08.billing.callcenterbk.client.content.callcenter.TabFindStreets;
import com.info08.billing.callcenterbk.client.content.callcenter.TabFindTransport;
import com.info08.billing.callcenterbk.client.content.callcenter.TabFindWebSites;
import com.info08.billing.callcenterbk.client.content.callcenter.TabTest;
import com.info08.billing.callcenterbk.client.dialogs.callcenter.DlgViewCurrentDateTime;
import com.info08.billing.callcenterbk.client.dialogs.callcenter.DlgViewSport;
import com.info08.billing.callcenterbk.client.dialogs.callcenter.DlgViewWeather;
import com.info08.billing.callcenterbk.client.singletons.CommonSingleton;
import com.info08.billing.callcenterbk.client.ui.layout.Body;
import com.info08.billing.callcenterbk.shared.common.Constants;
import com.info08.billing.callcenterbk.shared.common.ServerSession;
import com.info08.billing.callcenterbk.shared.entity.Users;
import com.smartgwt.client.data.Criteria;
import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.DataSource;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.layout.LayoutSpacer;
import com.smartgwt.client.widgets.layout.SectionStackSection;
import com.smartgwt.client.widgets.layout.VLayout;

public class CallCenterStackSelection extends SectionStackSection {

	private IButton iButtonOrg = null;
	private IButton iButtonAbFind = null;
	private IButton iButtonCodes = null;
	private IButton iButtonTransport = null;
	private IButton iButtonCityTransp = null;
	private IButton iButtonPoster = null;
	private IButton iButtonValute = null;
	private IButton iButtonStreets = null;
	private IButton iButtonRegInd = null;
	private IButton iButtonFindByNum = null;
	private IButton iButtonCityDist = null;
	private IButton iButtonExactTime = null;
	private IButton iButtonCalendar = null;
	private IButton iButtonWeb = null;
	private IButton iButtonSport = null;
	private IButton iButtonWiki = null;
	private IButton iButtonWeather = null;
	private IButton iButtonNews = null;
	private IButton iButtonSchedule = null;
	private IButton iButtonTest = null;

	public CallCenterStackSelection(final Body body) {
		setTitle(CallCenterBK.constants.callCenterActions());
		setExpanded(false);
		setCanCollapse(true);

		VLayout vLayout = new VLayout(5);
		vLayout.setWidth100();
		vLayout.setHeight100();
		vLayout.setPadding(5);

		iButtonOrg = new IButton();
		iButtonOrg.setTitle(CallCenterBK.constants.organization());
		iButtonOrg.setIcon("organization.gif");
		iButtonOrg.setWidth100();
		iButtonOrg.setAlign(Alignment.LEFT);

		iButtonAbFind = new IButton();
		iButtonAbFind.setTitle(CallCenterBK.constants.abonentFind());
		iButtonAbFind.setIcon("person.png");
		iButtonAbFind.setWidth100();
		iButtonAbFind.setAlign(Alignment.LEFT);

		iButtonCodes = new IButton();
		iButtonCodes.setTitle(CallCenterBK.constants.codes());
		iButtonCodes.setIcon("index.jpg");
		iButtonCodes.setWidth100();
		iButtonCodes.setAlign(Alignment.LEFT);

		iButtonTransport = new IButton();
		iButtonTransport.setTitle(CallCenterBK.constants.transport());
		iButtonTransport.setIcon("bus.gif");
		iButtonTransport.setWidth100();
		iButtonTransport.setAlign(Alignment.LEFT);

		iButtonCityTransp = new IButton();
		iButtonCityTransp.setTitle(CallCenterBK.constants.tbilisiTransport());
		iButtonCityTransp.setIcon("bus.gif");
		iButtonCityTransp.setWidth100();
		iButtonCityTransp.setAlign(Alignment.LEFT);

		iButtonPoster = new IButton();
		iButtonPoster.setTitle(CallCenterBK.constants.poster());
		iButtonPoster.setIcon("entertainment.png");
		iButtonPoster.setWidth100();
		iButtonPoster.setAlign(Alignment.LEFT);

		iButtonValute = new IButton();
		iButtonValute.setTitle(CallCenterBK.constants.valute());
		iButtonValute.setIcon("currency.png");
		iButtonValute.setWidth100();
		iButtonValute.setAlign(Alignment.LEFT);

		iButtonStreets = new IButton();
		iButtonStreets.setTitle(CallCenterBK.constants.streetsFind());
		iButtonStreets.setIcon("street.png");
		iButtonStreets.setWidth100();
		iButtonStreets.setAlign(Alignment.LEFT);

		iButtonRegInd = new IButton();
		iButtonRegInd.setTitle(CallCenterBK.constants.regIndexes());
		iButtonRegInd.setIcon("index.jpg");
		iButtonRegInd.setWidth100();
		iButtonRegInd.setAlign(Alignment.LEFT);

		iButtonFindByNum = new IButton();
		iButtonFindByNum.setTitle(CallCenterBK.constants.findByNumber());
		iButtonFindByNum.setIcon("phone.png");
		iButtonFindByNum.setWidth100();
		iButtonFindByNum.setAlign(Alignment.LEFT);

		iButtonCityDist = new IButton();
		iButtonCityDist.setTitle(CallCenterBK.constants.distBetweenCities());
		iButtonCityDist.setIcon("measure_distance.gif");
		iButtonCityDist.setWidth100();
		iButtonCityDist.setAlign(Alignment.LEFT);
		

		iButtonExactTime = new IButton();
		iButtonExactTime.setTitle(CallCenterBK.constants.exactTime());
		iButtonExactTime.setIcon("preferences_system_time.png");
		iButtonExactTime.setWidth100();
		iButtonExactTime.setAlign(Alignment.LEFT);

		iButtonCalendar = new IButton();
		iButtonCalendar.setTitle(CallCenterBK.constants.calendar());
		iButtonCalendar.setIcon("calendar.png");
		iButtonCalendar.setWidth100();
		iButtonCalendar.setAlign(Alignment.LEFT);

		iButtonWeb = new IButton();
		iButtonWeb.setTitle(CallCenterBK.constants.sites());
		iButtonWeb.setIcon("web.png");
		iButtonWeb.setWidth100();
		iButtonWeb.setAlign(Alignment.LEFT);

		iButtonSport = new IButton();
		iButtonSport.setTitle(CallCenterBK.constants.sport());
		iButtonSport.setIcon("soccer_ball.png");
		iButtonSport.setWidth100();
		iButtonSport.setAlign(Alignment.LEFT);

		iButtonWiki = new IButton();
		iButtonWiki.setTitle(CallCenterBK.constants.wiki());
		iButtonWiki.setIcon("wikipedia_globe_icon.png");
		iButtonWiki.setWidth100();
		iButtonWiki.setAlign(Alignment.LEFT);

		iButtonWeather = new IButton();
		iButtonWeather.setTitle(CallCenterBK.constants.weather());
		iButtonWeather.setIcon("weather.png");
		iButtonWeather.setWidth100();
		iButtonWeather.setAlign(Alignment.LEFT);

		vLayout.setMembers(iButtonOrg, iButtonAbFind, iButtonCodes,
				iButtonTransport, iButtonCityTransp, iButtonPoster,
				iButtonValute, iButtonStreets, iButtonRegInd, iButtonFindByNum,
				iButtonCityDist, iButtonExactTime, iButtonCalendar, iButtonWeb,
				iButtonSport, iButtonWiki, iButtonWeather);

		iButtonNews = new IButton();
		iButtonNews.setTitle(CallCenterBK.constants.news());
		iButtonNews.setIcon("feed.png");
		iButtonNews.setWidth100();
		iButtonNews.setAlign(Alignment.LEFT);

		iButtonSchedule = new IButton();
		iButtonSchedule.setTitle(CallCenterBK.constants.schedule());
		iButtonSchedule.setIcon("calendar.png");
		iButtonSchedule.setWidth100();
		iButtonSchedule.setAlign(Alignment.LEFT);

		LayoutSpacer layoutSpacer = new LayoutSpacer();
		layoutSpacer.setWidth100();
		layoutSpacer.setHeight(30);

		vLayout.addMember(layoutSpacer);
		vLayout.addMember(iButtonNews);
		vLayout.addMember(iButtonSchedule);

		iButtonTest = new IButton();
		iButtonTest.setTitle("Test Menu");
		iButtonTest.setIcon("wikipedia_globe_icon.png");
		iButtonTest.setWidth100();
		iButtonTest.setAlign(Alignment.LEFT);
		// vLayout.addMember(iButtonTest);

		iButtonTest.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				TabTest findTest = new TabTest();
				body.addTab(findTest);
			}
		});

		iButtonOrg.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				TabFindOrg tabFindOrg = new TabFindOrg();
				body.addTab(tabFindOrg);
			}
		});
		iButtonAbFind.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				TabFindSubscriber tabFindAbonent = new TabFindSubscriber();
				body.addTab(tabFindAbonent);
			}
		});
		iButtonCodes.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				TabFindIndexes tabFindIndexes = new TabFindIndexes();
				body.addTab(tabFindIndexes);
			}
		});
		iButtonTransport.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				TabFindTransport tabFindTransport = new TabFindTransport();
				body.addTab(tabFindTransport);
			}
		});
		iButtonCityTransp.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				TabFindInCityTransport tabFindInCityTransport = new TabFindInCityTransport();
				body.addTab(tabFindInCityTransport);
			}
		});
		iButtonPoster.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				TabFindEvent findPoster = new TabFindEvent();
				body.addTab(findPoster);
			}
		});
		iButtonValute.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				TabFindCurrencyRates tabFindCurrencyRates = new TabFindCurrencyRates();
				body.addTab(tabFindCurrencyRates);
			}
		});
		iButtonStreets.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				TabFindStreets tabFindStreets = new TabFindStreets();
				body.addTab(tabFindStreets);
			}
		});
		iButtonRegInd.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				TabFindDistrictVillageIndexes tabFindGeoInd = new TabFindDistrictVillageIndexes();
				body.addTab(tabFindGeoInd);
			}
		});
		iButtonFindByNum.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				TabFindByNumber findByNumber = new TabFindByNumber();
				body.addTab(findByNumber);
			}
		});
		iButtonCityDist.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				TabFindDistBetweenTowns findDistBetwCity = new TabFindDistBetweenTowns();
				body.addTab(findDistBetwCity);
			}
		});
		iButtonExactTime.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				DlgViewCurrentDateTime dlgViewCurrentDateTime = new DlgViewCurrentDateTime();
				dlgViewCurrentDateTime.show();
			}
		});
		iButtonCalendar.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				TabFindFacts findCalendar = new TabFindFacts();
				body.addTab(findCalendar);
			}
		});
		iButtonWeb.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				TabFindWebSites tabFindWebSites = new TabFindWebSites();
				body.addTab(tabFindWebSites);
			}
		});
		iButtonSport.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				DlgViewSport dlgViewSport = new DlgViewSport();
				dlgViewSport.show();
			}
		});
		iButtonWiki.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				TabFindNonStandartInfo findNonStandartInfo = new TabFindNonStandartInfo();
				body.addTab(findNonStandartInfo);
			}
		});
		iButtonWeather.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				DlgViewWeather dlgViewWeather = new DlgViewWeather();
				dlgViewWeather.show();
			}
		});

		iButtonNews.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				TabFindCallCenterNews tabFindNews = new TabFindCallCenterNews(
						CallCenterStackSelection.this);
				body.addTab(tabFindNews);
			}
		});

		iButtonSchedule.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				showRestShedule();
			}
		});
		addItem(vLayout);
	}

	public void initStartupScript() {
		try {
			final ServerSession serverSession = CommonSingleton.getInstance()
					.getServerSession();
			if (serverSession == null || serverSession.isWebSession()) {
				return;
			}
			Users user = serverSession.getUser();
			if (user == null) {
				return;
			}
			Long persTypeId = user.getDepartment_id();
			if (persTypeId == null
					|| !persTypeId.equals(Constants.OperatorDepartmentID)) {
				return;
			}
			Long unreadNewsCnt = serverSession.getUnreadNewsCnt();
			String title = CallCenterBK.constants.newsUnread()
					+ unreadNewsCnt.toString();
			iButtonNews.setTitle(title);
			if (unreadNewsCnt.intValue() > 0) {
				iButtonNews.setTitleStyle("newsBtn");
			}
			iButtonNews.redraw();
		} catch (Exception e) {
			e.printStackTrace();
			SC.say(e.toString());
		}
	}

	public void redrawNewsTitle() {
		try {
			DataSource dataSource = DataSource.get("CallCenterNewsDS");
			Criteria criteria = new Criteria();
			criteria.setAttribute("user_id", CommonSingleton.getInstance()
					.getSessionPerson().getUser_id());
			DSRequest req = new DSRequest();
			req.setAttribute("operationId", "getUsrCCNewsCnt");
			dataSource.fetchData(criteria, new DSCallback() {
				@Override
				public void execute(DSResponse response, Object rawData,
						DSRequest request) {
					Record records[] = response.getData();
					if (records == null || records.length < 0) {
						return;
					}
					Record record = records[0];
					Integer unread_news_cnt = record
							.getAttributeAsInt("unread_news_cnt");
					if (unread_news_cnt == null) {
						return;
					}
					String title = CallCenterBK.constants.newsUnread()
							+ unread_news_cnt.toString();
					iButtonNews.setTitle(title);
					if (unread_news_cnt.intValue() > 0) {
						iButtonNews.setTitleStyle("newsBtn");
					} else {
						iButtonNews.setTitleStyle("newsBtnBlack");
					}
					iButtonNews.redraw();
				}
			}, req);
			com.smartgwt.client.rpc.RPCManager.sendQueue();
		} catch (Exception e) {
			e.printStackTrace();
			SC.say(e.toString());
		}
	}

	private void showRestShedule() {
		try {
			final ServerSession serverSession = CommonSingleton.getInstance()
					.getServerSession();
			if (serverSession == null || serverSession.isWebSession()) {
				SC.say(CallCenterBK.constants.notCallCenterUser());
				return;
			}
			Users user = serverSession.getUser();
			if (user == null) {
				SC.say(CallCenterBK.constants.notCallCenterUser());
				return;
			}
			Long persTypeId = user.getDepartment_id();
			if (persTypeId == null
					|| !persTypeId.equals(Constants.OperatorDepartmentID)) {
				SC.say(CallCenterBK.constants.notCallCenterUser());
				return;
			}
			Long userId = user.getUser_id();
			DataSource dataSource = DataSource.get("UsersDS");
			Criteria criteria = new Criteria();
			criteria.setAttribute("user_id", userId);
			DSRequest dsRequest = new DSRequest();
			dsRequest.setOperationId("getOperatorBreak");
			dataSource.fetchData(criteria, new DSCallback() {
				@Override
				public void execute(DSResponse response, Object rawData,
						DSRequest request) {
					String strTxt = CallCenterBK.constants
							.operScheduleNotFound();
					Record records[] = response.getData();
					if (records != null && records.length > 0) {
						Record record = records[0];
						String txt = record
								.getAttributeAsString("schedule_txt");
						if (txt != null && !txt.trim().equals("")) {
							strTxt = txt;
						}
					}
					SC.say(strTxt);
				}
			}, dsRequest);

		} catch (Exception e) {
			e.printStackTrace();
			SC.say(e.toString());
		}
	}

	public void setButtonsVisible() {
		try {
			// TODO - This Must Be Removed
			boolean flag = true;
			if (flag) {
				return;
			}
			// TODO - This Must Be Removed

			ServerSession serverSession = CommonSingleton.getInstance()
					.getServerSession();
			if (serverSession == null || serverSession.isWebSession()) {
				return;
			}
			String operatorSrc = serverSession.getOperatorSrc();
			if (operatorSrc == null || operatorSrc.trim().equals("")) {
				return;
			}
			if (operatorSrc.trim().equals("NONE")) {
				return;
			}
			if (iButtonOrg != null) {
				iButtonOrg.setDisabled(operatorSrc.trim().equals("16007"));
			}
			if (iButtonAbFind != null) {
				iButtonAbFind.setDisabled(operatorSrc.trim().equals("16007"));
			}
			if (iButtonFindByNum != null) {
				iButtonFindByNum
						.setDisabled(operatorSrc.trim().equals("16007"));
			}
			if (iButtonCodes != null) {
				iButtonCodes.setDisabled(operatorSrc.trim().equals("16007"));
			}
			if (iButtonStreets != null) {
				iButtonStreets.setDisabled(operatorSrc.trim().equals("16007"));
			}
			if (iButtonTransport != null) {
				iButtonTransport
						.setDisabled(operatorSrc.trim().equals("11808"));
			}
			if (iButtonCityTransp != null) {
				iButtonCityTransp.setDisabled(operatorSrc.trim()
						.equals("11808"));
			}
			if (iButtonPoster != null) {
				iButtonPoster.setDisabled(operatorSrc.trim().equals("11808"));
			}
			if (iButtonValute != null) {
				iButtonValute.setDisabled(operatorSrc.trim().equals("11808"));
			}
			if (iButtonRegInd != null) {
				iButtonRegInd.setDisabled(operatorSrc.trim().equals("11808"));
			}
			if (iButtonCityDist != null) {
				iButtonCityDist.setDisabled(operatorSrc.trim().equals("11808"));
			}
			if (iButtonExactTime != null) {
				iButtonExactTime
						.setDisabled(operatorSrc.trim().equals("11808"));
			}
			if (iButtonCalendar != null) {
				iButtonCalendar.setDisabled(operatorSrc.trim().equals("11808"));
			}
			if (iButtonWeb != null) {
				iButtonWeb.setDisabled(operatorSrc.trim().equals("11808"));
			}
			if (iButtonSport != null) {
				iButtonSport.setDisabled(operatorSrc.trim().equals("11808"));
			}
			if (iButtonWiki != null) {
				iButtonWiki.setDisabled(operatorSrc.trim().equals("11808"));
			}
			if (iButtonWeather != null) {
				iButtonWeather.setDisabled(operatorSrc.trim().equals("11808"));
			}
		} catch (Exception e) {
			e.printStackTrace();
			SC.say(e.toString());
		}
	}
}
