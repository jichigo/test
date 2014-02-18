package xxxxxx.yyyyyy.zzzzzz.app.welcome;

import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * Handles requests for the application home page.
 */
@Controller
public class HomeController {

    private static final Logger logger = LoggerFactory
            .getLogger(HomeController.class);

    /**
     * Simply selects the home view to render by returning its name.
     */
    @RequestMapping(value = "/", method = { RequestMethod.GET })
    public String home(Locale locale, @ModelAttribute HelloWorldForm from,
            Model model) {
        logger.info("Welcome home! The client locale is {}.", locale);

        Date date = new Date();

        from.setDate1(date);
        from.setDate2(date);
        from.setDate3(date);
        from.setDate4(date);
        from.setDate5(date);

        from.setNumber1(1000L);
        from.setNumber2(1000L);
        from.setNumber3(1000L);

        model.addAttribute("serverTime1", DateFormat.getDateTimeInstance(
                DateFormat.SHORT, DateFormat.SHORT, locale).format(date));
        model.addAttribute("serverTime2", DateFormat.getDateTimeInstance(
                DateFormat.MEDIUM, DateFormat.MEDIUM, locale).format(date));
        model.addAttribute("serverTime3", DateFormat.getDateTimeInstance(
                DateFormat.LONG, DateFormat.LONG, locale).format(date));
        model.addAttribute("serverTime4", DateFormat.getDateTimeInstance(
                DateFormat.FULL, DateFormat.FULL, locale).format(date));

        return "welcome/home";
    }

    @RequestMapping(value = "/", method = { RequestMethod.POST })
    public String accept(Locale locale, @ModelAttribute HelloWorldForm from,
            Model model) {

        model.addAttribute("serverTime1", DateFormat.getDateTimeInstance(
                DateFormat.SHORT, DateFormat.SHORT, locale).format(
                from.getDate1()));
        model.addAttribute("serverTime2", DateFormat.getDateTimeInstance(
                DateFormat.MEDIUM, DateFormat.MEDIUM, locale).format(
                from.getDate2()));
        model.addAttribute("serverTime3", DateFormat.getDateTimeInstance(
                DateFormat.LONG, DateFormat.LONG, locale).format(
                from.getDate3()));
        model.addAttribute("serverTime4", DateFormat.getDateTimeInstance(
                DateFormat.FULL, DateFormat.FULL, locale).format(
                from.getDate4()));

        return "welcome/home";
    }

}
