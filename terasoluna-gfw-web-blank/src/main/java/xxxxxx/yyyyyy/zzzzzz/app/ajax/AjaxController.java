package xxxxxx.yyyyyy.zzzzzz.app.ajax;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Locale;

import javax.inject.Inject;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.sax.SAXSource;
import javax.xml.transform.stream.StreamSource;

import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingErrorProcessor;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.terasoluna.gfw.common.exception.BusinessException;
import org.terasoluna.gfw.common.message.ResultMessage;
import org.terasoluna.gfw.common.message.ResultMessages;

import xxxxxx.yyyyyy.zzzzzz.Hoge;

@SessionAttributes(types = { CalculationParameters.class })
@RequestMapping("ajax")
@Controller
public class AjaxController {

    @Inject
    MessageSource messageSource;

    @Inject
    Hoge hoge;
    
    @ModelAttribute("calculationParameters")
    public CalculationParameters setUpCalculationParameters() {
        return new CalculationParameters();
    }

    @RequestMapping(value = "xxe", method = RequestMethod.GET)
    public String xxeForm(Model model) {
        StringBuilder data = new StringBuilder();

        data.append("<!DOCTYPE foo [ ").append("\n");
        data.append("  <!ELEMENT foo ANY >").append("\n");
        data.append(
                "  <!ENTITY xxe SYSTEM \"file:///C:/Users/btshimizukza/_netrc\" >")
                .append("\n");
        data.append("]>").append("\n");
        data.append("<XXEEntity>").append("\n");
        data.append("    <stringField>&xxe;</stringField>").append("\n");
        data.append("</XXEEntity>").append("\n");

        model.addAttribute("defaultData", data);
        
        System.out.println("★★★★★★★★★★★★★★★★★★★★" + hoge.getHoge());

        return "ajax/xxe";
    }

    @RequestMapping(value = "search", method = RequestMethod.GET)
    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    public SearchResult search(@Validated SearchCriteria criteria) throws IOException {

        SearchResult searchResult = new SearchResult();

        // omitted
        searchResult.setList(new ArrayList<Object>());

        return searchResult;
    }

    @RequestMapping(value = "plusForJson", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<CalculationResult> plusForJson(
            @Validated @RequestBody CalculationParameters params,
            BindingResult bResult, SessionStatus sessionStatus) throws BindException {
        CalculationResult result = new CalculationResult();

        if (bResult.hasErrors()) {
            sessionStatus.setComplete();
            throw new BindException(bResult);

        }
        try {

            // call service method.
            // omitted

        } catch (BusinessException e) {

            // implement error handling.
            // omitted
            return new ResponseEntity<CalculationResult>(result, HttpStatus.CONFLICT);
        }
        sessionStatus.setComplete();
        int sum = params.getNumber1() + params.getNumber2();
        result.setResultNumber(sum);
        return new ResponseEntity<CalculationResult>(result, HttpStatus.OK);
    }

    @RequestMapping(value = "plusForForm", method = RequestMethod.POST)
    @ResponseBody
    public CalculationResult plusForForm(@Validated CalculationParameters params) {
        CalculationResult result = new CalculationResult();
        int sum = params.getNumber1() + params.getNumber2();
        result.setResultNumber(sum);
        return result;
    }

    @ExceptionHandler(BindException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ErrorResults handleBindException(BindException e, Locale locale) {
        ErrorResults errorResults = new ErrorResults();
        for (FieldError fe : e.getBindingResult().getFieldErrors()) {
            errorResults.add(fe.getCode(),
                    messageSource.getMessage(fe, locale), fe.getField());
        }
        for (ObjectError oe : e.getBindingResult().getGlobalErrors()) {
            errorResults.add(oe.getCode(),
                    messageSource.getMessage(oe, locale), oe.getObjectName());
        }
        return errorResults;
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ErrorResults handleMethodArgumentNotValidException(
            MethodArgumentNotValidException e, Locale locale) {
        ErrorResults errorResults = new ErrorResults();
        for (FieldError fe : e.getBindingResult().getFieldErrors()) {
            errorResults.add(fe.getCode(),
                    messageSource.getMessage(fe, locale), fe.getField());
        }
        for (ObjectError oe : e.getBindingResult().getGlobalErrors()) {
            errorResults.add(oe.getCode(),
                    messageSource.getMessage(oe, locale), oe.getObjectName());
        }
        return errorResults;
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ErrorResults handleHttpMessageNotReadableException(
            HttpMessageNotReadableException e, Locale locale) {
        ErrorResults errorResults = new ErrorResults();
        errorResults.add("e.xx.fw.6005", messageSource.getMessage(
                "e.xx.fw.6005", null, locale));
        return errorResults;
    }

    @ExceptionHandler(BusinessException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ErrorResults handleHttpBusinessException(BusinessException e,
            Locale locale) {
        ErrorResults errorResults = new ErrorResults();
        errorResults.add("e.xx.fw.6005", messageSource.getMessage(
                "e.xx.fw.6005", null, locale));
        return errorResults;
    }

    @RequestMapping(value = "xxe", method = RequestMethod.POST)
    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    public XXEEntity xxeJaxb(@RequestBody XXEEntity request) throws IOException {
        return request;
    }

    @RequestMapping(value = "xxe", method = RequestMethod.POST, params = "SAXSource")
    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    public XXEEntity xxe(@RequestBody SAXSource request) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(request
                .getInputSource().getByteStream()));
        String body = reader.readLine();
        XXEEntity entity = new XXEEntity();
        entity.setStringField(body);
        return entity;
    }

    @RequestMapping(value = "xxe", method = RequestMethod.POST, params = "DOMSource")
    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    public XXEEntity xxe(@RequestBody DOMSource request) throws IOException {
        String body = request.getNode().getTextContent();
        XXEEntity entity = new XXEEntity();
        entity.setStringField(body);
        return entity;
    }

    @RequestMapping(value = "xxe", method = RequestMethod.POST, params = "StreamSource")
    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    public XXEEntity xxe(@RequestBody StreamSource request) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(request
                .getInputStream()));
        String body = reader.readLine();
        XXEEntity entity = new XXEEntity();
        entity.setStringField(body);
        return entity;
    }

}
