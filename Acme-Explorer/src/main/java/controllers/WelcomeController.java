
package controllers;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import services.SystemConfigService;

@Controller
@RequestMapping("/welcome")
public class WelcomeController extends AbstractController {

	@Autowired
	SystemConfigService	systemConfigService;


	// Constructors -----------------------------------------------------------

	public WelcomeController() {
		super();
	}

	// Index ------------------------------------------------------------------		

	@RequestMapping(value = "/index")
	public ModelAndView index() {

		ModelAndView result;

		//		Locale locale = RequestContextUtils.getLocale(request);
		//		String country = locale.getCountry();
		//		String language = locale.getLanguage();

		final Locale locale = LocaleContextHolder.getLocale();
		final String language = locale.getLanguage();
		String phrase = "";
		final String banner = this.systemConfigService.findConfig().getBanner();

		if (language == "es")
			phrase = this.systemConfigService.findConfig().getWelcomeMessageEs();
		else if (language == "en")
			phrase = this.systemConfigService.findConfig().getWelcomeMessage();

		result = new ModelAndView("welcome/index");
		result.addObject("banner", banner);
		result.addObject("phrase", phrase);

		return result;
	}
}
