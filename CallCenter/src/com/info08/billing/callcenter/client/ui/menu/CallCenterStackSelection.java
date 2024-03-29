package com.info08.billing.callcenter.client.ui.menu;

import com.info08.billing.callcenter.client.CallCenter;
import com.info08.billing.callcenter.client.content.callcenter.TabFindAbonent;
import com.info08.billing.callcenter.client.content.callcenter.TabFindByNumber;
import com.info08.billing.callcenter.client.content.callcenter.TabFindCalendar;
import com.info08.billing.callcenter.client.content.callcenter.TabFindCurrencyRates;
import com.info08.billing.callcenter.client.content.callcenter.TabFindDistBetwCity;
import com.info08.billing.callcenter.client.content.callcenter.TabFindGeoInd;
import com.info08.billing.callcenter.client.content.callcenter.TabFindInCityTransport;
import com.info08.billing.callcenter.client.content.callcenter.TabFindIndexes;
import com.info08.billing.callcenter.client.content.callcenter.TabFindNews;
import com.info08.billing.callcenter.client.content.callcenter.TabFindNonStandartInfo;
import com.info08.billing.callcenter.client.content.callcenter.TabFindOrg;
import com.info08.billing.callcenter.client.content.callcenter.TabFindOrthCalendar;
import com.info08.billing.callcenter.client.content.callcenter.TabFindPoster;
import com.info08.billing.callcenter.client.content.callcenter.TabFindStreets;
import com.info08.billing.callcenter.client.content.callcenter.TabFindTransport;
import com.info08.billing.callcenter.client.content.callcenter.TabFindWebSites;
import com.info08.billing.callcenter.client.content.callcenter.TabTest;
import com.info08.billing.callcenter.client.dialogs.callcenter.DlgViewCurrentDateTime;
import com.info08.billing.callcenter.client.dialogs.callcenter.DlgViewSport;
import com.info08.billing.callcenter.client.dialogs.callcenter.DlgViewWeather;
import com.info08.billing.callcenter.client.singletons.CommonSingleton;
import com.info08.billing.callcenter.client.ui.layout.Body;
import com.info08.billing.callcenter.shared.common.ServerSession;
import com.info08.billing.callcenter.shared.entity.Person;
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

	public CallCenterStackSelection(final Body body) {
		setTitle(CallCenter.constants.callCenterActions());
		setExpanded(false);
		setCanCollapse(true);

		VLayout vLayout = new VLayout(5);
		vLayout.setWidth100();
		vLayout.setHeight100();
		vLayout.setPadding(10);

		IButton iButtonOrg = new IButton();
		iButtonOrg.setTitle(CallCenter.constants.organization());
		iButtonOrg.setIcon("organization.gif");
		iButtonOrg.setWidth100();
		iButtonOrg.setAlign(Alignment.LEFT);

		IButton iButtonAbFind = new IButton();
		iButtonAbFind.setTitle(CallCenter.constants.abonentFind());
		iButtonAbFind.setIcon("person.png");
		iButtonAbFind.setWidth100();
		iButtonAbFind.setAlign(Alignment.LEFT);

		IButton iButtonCodes = new IButton();
		iButtonCodes.setTitle(CallCenter.constants.codes());
		iButtonCodes.setIcon("index.jpg");
		iButtonCodes.setWidth100();
		iButtonCodes.setAlign(Alignment.LEFT);

		IButton iButtonTransport = new IButton();
		iButtonTransport.setTitle(CallCenter.constants.transport());
		iButtonTransport.setIcon("bus.gif");
		iButtonTransport.setWidth100();
		iButtonTransport.setAlign(Alignment.LEFT);

		IButton iButtonCityTransp = new IButton();
		iButtonCityTransp.setTitle(CallCenter.constants.tbilisiTransport());
		iButtonCityTransp.setIcon("bus.gif");
		iButtonCityTransp.setWidth100();
		iButtonCityTransp.setAlign(Alignment.LEFT);

		IButton iButtonPoster = new IButton();
		iButtonPoster.setTitle(CallCenter.constants.poster());
		iButtonPoster.setIcon("entertainment.png");
		iButtonPoster.setWidth100();
		iButtonPoster.setAlign(Alignment.LEFT);

		IButton iButtonValute = new IButton();
		iButtonValute.setTitle(CallCenter.constants.valute());
		iButtonValute.setIcon("currency.png");
		iButtonValute.setWidth100();
		iButtonValute.setAlign(Alignment.LEFT);

		IButton iButtonStreets = new IButton();
		iButtonStreets.setTitle(CallCenter.constants.streetsFind());
		iButtonStreets.setIcon("street.png");
		iButtonStreets.setWidth100();
		iButtonStreets.setAlign(Alignment.LEFT);

		IButton iButtonRegInd = new IButton();
		iButtonRegInd.setTitle(CallCenter.constants.regIndexes());
		iButtonRegInd.setIcon("index.jpg");
		iButtonRegInd.setWidth100();
		iButtonRegInd.setAlign(Alignment.LEFT);

		IButton iButtonFindByNum = new IButton();
		iButtonFindByNum.setTitle(CallCenter.constants.findByNumber());
		iButtonFindByNum.setIcon("phone.png");
		iButtonFindByNum.setWidth100();
		iButtonFindByNum.setAlign(Alignment.LEFT);

		IButton iButtonCityDist = new IButton();
		iButtonCityDist.setTitle(CallCenter.constants.distBetweenCities());
		iButtonCityDist.setIcon("measure_distance.gif");
		iButtonCityDist.setWidth100();
		iButtonCityDist.setAlign(Alignment.LEFT);

		IButton iButtonExactTime = new IButton();
		iButtonExactTime.setTitle(CallCenter.constants.exactTime());
		iButtonExactTime.setIcon("preferences_system_time.png");
		iButtonExactTime.setWidth100();
		iButtonExactTime.setAlign(Alignment.LEFT);

		IButton iButtonCalendar = new IButton();
		iButtonCalendar.setTitle(CallCenter.constants.calendar());
		iButtonCalendar.setIcon("calendar.png");
		iButtonCalendar.setWidth100();
		iButtonCalendar.setAlign(Alignment.LEFT);

		IButton iButtonOrthCalendar = new IButton();
		iButtonOrthCalendar.setTitle(CallCenter.constants.orthCalendar());
		iButtonOrthCalendar.setIcon("calendar.png");
		iButtonOrthCalendar.setWidth100();
		iButtonOrthCalendar.setAlign(Alignment.LEFT);

		IButton iButtonWeb = new IButton();
		iButtonWeb.setTitle(CallCenter.constants.sites());
		iButtonWeb.setIcon("web.png");
		iButtonWeb.setWidth100();
		iButtonWeb.setAlign(Alignment.LEFT);

		IButton iButtonSport = new IButton();
		iButtonSport.setTitle(CallCenter.constants.sport());
		iButtonSport.setIcon("soccer_ball.png");
		iButtonSport.setWidth100();
		iButtonSport.setAlign(Alignment.LEFT);

		IButton iButtonWiki = new IButton();
		iButtonWiki.setTitle(CallCenter.constants.wiki());
		iButtonWiki.setIcon("wikipedia_globe_icon.png");
		iButtonWiki.setWidth100();
		iButtonWiki.setAlign(Alignment.LEFT);

		IButton iButtonWeather = new IButton();
		iButtonWeather.setTitle(CallCenter.constants.weather());
		iButtonWeather.setIcon("weather.png");
		iButtonWeather.setWidth100();
		iButtonWeather.setAlign(Alignment.LEFT);

		vLayout.setMembers(iButtonOrg, iButtonAbFind, iButtonCodes,
				iButtonTransport, iButtonCityTransp, iButtonPoster,
				iButtonValute, iButtonStreets, iButtonRegInd, iButtonFindByNum,
				iButtonCityDist, iButtonExactTime, iButtonCalendar,
				iButtonOrthCalendar, iButtonWeb, iButtonSport, iButtonWiki,
				iButtonWeather);

		IButton iButtonNews = new IButton();
		iButtonNews.setTitle(CallCenter.constants.news());
		iButtonNews.setIcon("feed.png");
		iButtonNews.setWidth100();
		iButtonNews.setAlign(Alignment.LEFT);

		IButton iButtonSchedule = new IButton();
		iButtonSchedule.setTitle(CallCenter.constants.schedule());
		iButtonSchedule.setIcon("calendar.png");
		iButtonSchedule.setWidth100();
		iButtonSchedule.setAlign(Alignment.LEFT);

		LayoutSpacer layoutSpacer = new LayoutSpacer();
		layoutSpacer.setWidth100();
		layoutSpacer.setHeight(30);

		vLayout.addMember(layoutSpacer);
		vLayout.addMember(iButtonNews);
		vLayout.addMember(iButtonSchedule);

		IButton iButtonTest = new IButton();
		iButtonTest.setTitle("Test Menu");
		iButtonTest.setIcon("wikipedia_globe_icon.png");
		iButtonTest.setWidth100();
		iButtonTest.setAlign(Alignment.LEFT);
		//vLayout.addMember(iButtonTest);

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
				TabFindAbonent tabFindAbonent = new TabFindAbonent();
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
				TabFindPoster findPoster = new TabFindPoster();
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
				TabFindGeoInd tabFindGeoInd = new TabFindGeoInd();
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
				TabFindDistBetwCity findDistBetwCity = new TabFindDistBetwCity();
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
				TabFindCalendar findCalendar = new TabFindCalendar();
				body.addTab(findCalendar);
			}
		});
		iButtonOrthCalendar.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				TabFindOrthCalendar findOrthCalendar = new TabFindOrthCalendar();
				body.addTab(findOrthCalendar);
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
				TabFindNews tabFindNews = new TabFindNews();
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

	private void showRestShedule() {
		try {
			final ServerSession serverSession = CommonSingleton.getInstance()
					.getServerSession();
			if (serverSession == null || serverSession.isWebSession()) {
				SC.say(CallCenter.constants.notCallCenterUser());
				return;
			}
			Person person = serverSession.getUser();
			if (person == null) {
				SC.say(CallCenter.constants.notCallCenterUser());
				return;
			}
			Long persTypeId = person.getPersonelTypeId();
			if (persTypeId == null || !persTypeId.equals(9L)) {
				SC.say(CallCenter.constants.notCallCenterUser());
				return;
			}
			String userName = person.getUserName();
			DataSource dataSource = DataSource.get("PersDS");
			Criteria criteria = new Criteria();
			criteria.setAttribute("operator", userName);
			DSRequest dsRequest = new DSRequest();
			dsRequest.setOperationId("getOperatorSchedule");
			dataSource.fetchData(criteria, new DSCallback() {
				@Override
				public void execute(DSResponse response, Object rawData,
						DSRequest request) {
					String strTxt = CallCenter.constants.operScheduleNotFound();
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

	public void setMenuPersmission() {
		try {

		} catch (Exception e) {
			e.printStackTrace();
			SC.say(e.toString());
		}
	}
}
