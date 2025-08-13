package com.chanura.dashboard.service;

import java.util.Properties;

public interface As400Service {

    boolean validateCredentials(String username, String password);
    
    Properties getSystemInfo(String username, String password);
}
