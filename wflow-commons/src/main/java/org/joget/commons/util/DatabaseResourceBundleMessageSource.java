package org.joget.commons.util;

import org.joget.commons.spring.model.ResourceBundleMessage;
import org.joget.commons.spring.model.ResourceBundleMessageDao;
import java.util.Locale;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ResourceBundleMessageSource;

public class DatabaseResourceBundleMessageSource extends ResourceBundleMessageSource {

    @Autowired
    private ResourceBundleMessageDao resourceBundleMessageDao;
    
    @Autowired
    private SetupManager setupManager;

    @Override
    protected String resolveCodeWithoutArguments(String code, Locale locale) {
        String localeToUse = locale.toString();
        
        ResourceBundleMessage resourceBundleMessage = null;
        try {
            resourceBundleMessage = resourceBundleMessageDao.getMessage(code, localeToUse);
        } catch (Exception e) {
            LogUtil.error(getClass().getName(), null, "Error retrieving resource bundle message for " + code);
        }

        if (resourceBundleMessage != null) {
            return resourceBundleMessage.getMessage();
        } else {
            return super.resolveCodeWithoutArguments(code, locale);
        }
    }
}
