package com.info08.billing.callcenterbk.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.RootPanel;
import com.info08.billing.callcenterbk.client.content.info.TabInfoPortal;
import com.info08.billing.callcenterbk.client.dialogs.LoginDialog;
import com.info08.billing.callcenterbk.client.exception.CallCenterException;
import com.info08.billing.callcenterbk.client.service.CommonService;
import com.info08.billing.callcenterbk.client.service.CommonServiceAsync;
import com.info08.billing.callcenterbk.client.singletons.CommonSingleton;
import com.info08.billing.callcenterbk.client.ui.layout.MainLayout;
import com.info08.billing.callcenterbk.client.ui.layout.West;
import com.info08.billing.callcenterbk.shared.common.ServerSession;
import com.smartgwt.client.util.SC;

/**
 * <p>
 * ss
 * ეს არის ვებ აპლიკაციის ძირითადი ჩამთვრითავი კლასი კლიენტის მხარეს (ანუ
 * ბრაუზერში). ამ კლასის გამოძახება ხდება როგორც მისამართით (მაგ.: <a
 * href="http://192.168.1.1:8080/CallCenter"
 * >http://192.168.1.1:8080/CallCenter</a>) ასევე მისი გამოძახება ხდება სერვლეტ
 * კლასიდან {@link com.info08.billing.callcenterbk.server.servlets.InitAppServlet} , 
 * რომელსაც თავისთავად იძახებს ე.წ. SIP კლიენტი რომელშიც შემოდის აბონენტის ზარი.
 * ანუ პროცესი მიმდინარეობს შემდეგნაირად : ზარი შემოდის SIP კლიენტში, ის ხსნის
 * ბილინგისა და ქოლცენტის პროგრამას, ელოდება მის ჩატვირთვას და და შემდგომი ამისა
 * იღებს ყურმილს და იწყება საუბარი კლიენტსა და ოპერატორს შორის.რაც შემდეგნაირად
 * შეგვიძლია რომ წარმოვიდგინოთ :
 * <p>
 * <blockquote>
 * 
 * <pre>
 * 	SIP Client - InitAppServlet - Web Interface.
 * </pre>
 * 
 * </blockquote> რაც შეეხება ვებ ინტერფეისს , ეს კლასი არის მთლიანი აპლიკაციის
 * გარსის ჩამომყალიბვებელი. ვებ აპლიკაცის დაფუძნებულია ინტერფეისის წარმოდგენის
 * რამოდენიმე ტექნოლოგიაზე
 * <p>
 * 1. პირველ რიგში გამოიყენება ე.წ. Google Web Toolkit რომლიც უზრუნველყოფს ჯავა
 * პროფგრამისტის მიერ ჯავაზე დაწერილი ინტერფეისი დააკომპილიროს და გადათარგმნოს
 * დინამიურ ე.წ. HTML ენაში DOM ის საშუალებით. ინტერფეისის ნებისმიერი
 * კომპონენტის გენერაცია ხდება აპლიკაციის კომპილაციისას და წინასწარვე მზადდება
 * ინტერფეისი - რაც შემდგომში იძლევა მისი სწრაფად მუშაობის და გამოყენებაში
 * სიმარტივის შესაძლებლობას. ამ ტექნოლოგიაში კომუნიკაცია სერვერსა და კლიენტს
 * შორის ხდება ე.წ. RPC (Remote Procedure Call - მოშორებული პროცედურის
 * გამოძახება.) რომლის კონკრეტული იმპლემენტაციია GWT. პროტოკოლი უზრუნველყოფს
 * ჯავასკრიპტის ასინქრონულ კომუნიკაციის საშუალებას სერვერთან JSON ფორმატში, რაც
 * იძლევა იმის შეგრძნებას რომ თქვენ მუშაობთ არა ვებ აპლიკაციაში არამედ ე.წ.
 * Desktop Application ში - რაც ძალიან მოსახერხებელია.
 * <p>
 * 2. მეორე ბიბლიოთეკა (ტექნოლოგია) რომელიც გამოყენებულია პროგრამულ
 * უზრუნველყოფაში არის ე.წ. SmartGWT. ეს არის საკმაოდ მდიდარის კომპონენტების
 * კრებული რომელიც დაფუძნებულია GWT ტექნოლოგიაზე და მასთან ერთად ქმნის საკმაოდ
 * მოხერხებული და კარგი ინტერფეისის წერის შესაძლებლობას. SmartGWT ტექნოლოგია
 * მუშაობს და გამოიყენება მხოლოდ GWT ტექნოლოგიასთან ერთად. მის გარეშე მისი
 * გამოყენება შეუძლებელია.
 * <p>
 * ქვემოთ მოყვანილია ყველა ის ბმული ჩამოთვლილი ტექნოლოგიებისა რომელსაც
 * გამოიყენებს პროგრამული უზრუნველყოფის კლიენტის ნაწილი.
 * <p>
 * <blockquote>
 * 
 * <pre>
 * <a href="http://code.google.com/webtoolkit">Google Web Toolkit</a>
 * <a href="http://www.smartclient.com/smartgwt/showcase">SmartGWT</a>
 * <a href="http://www.smartclient.com/">SmartGWT - ს მწარმოებელი კომპანიის საიტი. </a>
 * <a href="http://en.wikipedia.org/wiki/Remote_procedure_call">RPC პროტოკოლის დოკუმენტაცია ვიკიპედიაში.</a>
 * </pre>
 * 
 * </blockquote>
 * 
 * @author პაატა ლომინაძე
 * @version 1.0.0.1
 * @since 1.0.0.1
 * 
 */
public class CallCenterBK implements EntryPoint {

	/**
	 * სერვერის ფუნქციონების მქონე კლასის აღწერა რომლიც მეშვეობითაც ხდება მაგ.:
	 * კლიენტის იდენტიფკაცია პროგრამაში. თუმცა თუ კლიენტი არის ოპერატორი რომელიც
	 * მოლოდინშია აბონენტთან, მიდი იდენტიფიკაცია არ ხდება რადგან ის უკვე
	 * იდენტიფიცირებულია ე.წ. SIP ის პროგრამაში და ამ აპლიკაციას მომხმარებელი
	 * გადმოეცემა პარამეტრად იმისათვის რომ ოპერატორმა დრო არ დაკარგოს
	 * იდენტიფიკაციაში.
	 */
	public static final CommonServiceAsync commonService = GWT
			.create(CommonService.class);

	/**
	 * პროგრამში გამოყენებულია განლაგების მენეჯერი რომლის მეშვეობითაც ხდება თუ
	 * რომელი კომპონენტის სად უნდა განლაგდეს.
	 */
	private static MainLayout mainLayout;

	/**
	 * ხდება ასევე პროგრამაში გამოყენებული წარწერების ჩატვირთვა თავიდანვე რომ
	 * შემდგომში ამაზე დრო არ დაიხარჯოს.
	 */
	public static AppConstants constants = (AppConstants) GWT
			.create(AppConstants.class);

	/**
	 * ამ ცვლადში ფიქსირდება პროგრამაში შესულია ოპერატორი თუ სხვა მომხმარებელი.
	 */
	private static boolean isOperator = false;

	/**
	 * თუ იდენტიფიცირებულია ოპერატორი. ამ შემთხვევაში ხდება ზარის
	 * იდენტიფიკატორის ცვლადის იდენტიფიცირება.
	 */
	private static String sessionId;

	/**
	 * ფუნქცია არის GWT ტექნოლოგიის მეთოდი რომელიც აუცილებელია იმისათვის რომ
	 * მოხდეს კლიენტის პროგრამის ჩანტვირთვა.
	 */
	public void onModuleLoad() {
		try {

			sessionId = com.google.gwt.user.client.Window.Location
					.getParameter("sessionId");

			if (sessionId != null && !sessionId.trim().equalsIgnoreCase("")) {
				isOperator = true;
			}
			initUI();
		} catch (Exception e) {
			e.printStackTrace();
			SC.say(e.toString());
		}
	}

	/**
	 * მთავარი გრაფიკული ინტერფეისი დამხატავი მეთოდი.
	 * 
	 * @throws CallCenterException
	 *             გამოისვრის შეცდომას თუ ვერ მოხდა იდენტიფიცირება ან კიდევ
	 *             რაიმე სხვა სახის შეცდომა.
	 */
	public static void initUI() throws CallCenterException {
		if (mainLayout != null) {
			mainLayout.destroy();
		}

		if (!isOperator) {
			CommonSingleton.getInstance().reInitDS();
			mainLayout = new MainLayout();
			RootPanel.get().add(mainLayout);
			LoginDialog loginDialog = new LoginDialog(mainLayout);
			loginDialog.draw();
		} else {
			commonService.login("noUserName", "noPassword", sessionId,
					new AsyncCallback<ServerSession>() {
						@Override
						public void onSuccess(ServerSession serverSession) {
							try {
								CommonSingleton.getInstance().reInitDS();
								mainLayout = new MainLayout();
								RootPanel.get().add(mainLayout);
								CommonSingleton.getInstance().setSessionPerson(
										serverSession.getUser());
								CommonSingleton.getInstance().setServerSession(
										serverSession);
								West.openByLoggedUser();
								mainLayout.getCenterPanel().getBodyPanel();
								TabInfoPortal.draw();
							} catch (Exception e) {
								SC.say(e.toString());
							}
						}

						@Override
						public void onFailure(Throwable caught) {
							SC.say(caught.toString());
						}
					});
		}
	}

	/**
	 * ფუნქცია უზრუნველყოფს იმას რომ ნებისმიერ კლასს კლიენტის მხარეს შეეძლოს
	 * მთავარი განლაგების მენეჯერის აღება რომ შემდგომში მოახდინოს რაიმე ცვლილება
	 * ინტერფეისში.
	 * 
	 * @return MainLayout აბრუნებს მთავარი განლაგების მენეჯერის კლასს.
	 */
	public MainLayout getMainLayout() {
		return mainLayout;
	}
}
