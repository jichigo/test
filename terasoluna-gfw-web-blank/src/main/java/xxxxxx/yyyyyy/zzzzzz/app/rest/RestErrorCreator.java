package xxxxxx.yyyyyy.zzzzzz.app.rest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import org.springframework.context.support.ApplicationObjectSupport;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.context.request.WebRequest;
import org.terasoluna.gfw.common.message.ResultMessage;
import org.terasoluna.gfw.common.message.ResultMessages;

/**
 * Creator of error object for RESTful Web Service.
 */
@Component
public class RestErrorCreator extends ApplicationObjectSupport {

    /**
     * Create error object for rest.
     * 
     * @param code
     *            error code
     * @param locale
     *            requested locale.
     * @param arguments
     *            arguments for to resolve the message.
     * @return error object
     */
    public RestError createRestError(String code, String defaultMessage,
            Locale locale, Object... arguments) {
        String localizedMessage = getMessageSourceAccessor().getMessage(code,
                arguments, defaultMessage, locale);
        return new RestError(code, localizedMessage);
    }

    /**
     * Create binding error object for rest.
     * 
     * @param bindingResult
     *            result of binding.
     * @param locale
     *            requested locale.
     * @return error object
     */
    public RestError createBindingResultRestError(String errorCode,
            BindingResult bindingResult, String defaultMessage, Locale locale) {
        RestError restError = createRestError(errorCode, defaultMessage, locale);
        for (FieldError fieldError : bindingResult.getFieldErrors()) {
            restError.addDetail(createRestError(fieldError, locale));
        }
        for (ObjectError objectError : bindingResult.getGlobalErrors()) {
            restError.addDetail(createRestError(objectError, locale));
        }
        return restError;
    }

    /**
     * Create error detail object for rest.
     * 
     * @param messageResolvable
     *            resolvable object for message.
     * @param locale
     *            requested locale.
     * @param additionalArguments
     *            additional arguments for to resolve the message.
     * @return error detail object
     */
    private RestError createRestError(
            DefaultMessageSourceResolvable messageResolvable, Locale locale) {

        String localizedMessage = getMessageSourceAccessor().getMessage(
                messageResolvable, locale);

        return new RestError(messageResolvable.getCode(), localizedMessage);
    }

    public RestError createResultMessagesRestError(String errorCode,
            ResultMessages resultMessages, String defaultMessage, Locale locale) {
        RestError restError;
        if (resultMessages.getList().size() == 1) {
            ResultMessage resultMessage = resultMessages.getList().get(0);
            String messageCode = resultMessage.getCode();
            if (messageCode == null) {
                messageCode = errorCode;
            }
            restError = createRestError(messageCode, resultMessage.getText(),
                    locale, resultMessage.getArgs());
        } else {
            restError = createRestError(errorCode, defaultMessage, locale);
            for (ResultMessage resultMessage : resultMessages.getList()) {
                restError.addDetail(createRestError(resultMessage.getCode(),
                        resultMessage.getText(), locale,
                        resultMessage.getArgs()));
            }
        }
        return restError;
    }

}
