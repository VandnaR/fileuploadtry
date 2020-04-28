package com.wipro.fileupload.springConfig;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

/**
 * Created by DE393632 on 3/12/2019.
 */
@Configuration
public class ConfigUtility {

    @Autowired
    private Environment env;

    public String getProperty(String pPropertyKey) {
        return env.getProperty(pPropertyKey);
    }
}