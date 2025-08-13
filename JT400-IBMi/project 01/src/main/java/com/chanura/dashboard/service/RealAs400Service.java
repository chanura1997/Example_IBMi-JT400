package com.chanura.dashboard.service;

import com.ibm.as400.access.AS400;
import com.ibm.as400.access.SystemValue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Properties;

@Service
public class RealAs400Service implements As400Service {

    @Value("${as400.hostname}")
    private String hostname;

    @Override
    public boolean validateCredentials(String username, String password) {
        try {
            AS400 as400 = new AS400(hostname, username, password);
            as400.connectService(AS400.COMMAND);
            as400.disconnectAllServices();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public Properties getSystemInfo(String username, String password) {
        Properties systemInfo = new Properties();
        
        try {
            AS400 as400 = new AS400(hostname, username, password);
            as400.connectService(AS400.COMMAND);
            
            try {
                // Get QDATE and QTIME using SystemValue class
                String qdate = getSystemValue(as400, "QDATE");
                String qtime = getSystemValue(as400, "QTIME");
                
                if (qdate != null && !qdate.isEmpty()) {
                    systemInfo.setProperty("QDATE", qdate);
                } else {
                    systemInfo.setProperty("QDATE", "Unable to retrieve QDATE from AS/400");
                }
                
                if (qtime != null && !qtime.isEmpty()) {
                    systemInfo.setProperty("QTIME", qtime);
                } else {
                    systemInfo.setProperty("QTIME", "Unable to retrieve QTIME from AS/400");
                }
                
            } catch (Exception e) {
                // Fallback: Use Java time but mark as not from AS/400
                java.time.LocalDate currentDate = java.time.LocalDate.now();
                java.time.LocalTime currentTime = java.time.LocalTime.now();
                
                systemInfo.setProperty("QDATE", currentDate.toString() + " (Fallback - AS/400 retrieval failed: " + e.getMessage() + ")");
                systemInfo.setProperty("QTIME", currentTime.toString() + " (Fallback - AS/400 retrieval failed: " + e.getMessage() + ")");
            }
            
            as400.disconnectAllServices();
            
        } catch (Exception e) {
            systemInfo.setProperty("QDATE", "Error connecting to AS/400: " + e.getMessage());
            systemInfo.setProperty("QTIME", "Error connecting to AS/400: " + e.getMessage());
        }
        
        return systemInfo;
    }
    
    private String getSystemValue(AS400 as400, String systemValueName) {
        try {
            // Create a SystemValue object for the specified system value
            SystemValue mySysVal = new SystemValue(as400, systemValueName);
            
            // Retrieve the value of the system value
            Object value = mySysVal.getValue();
            
            // Check if the value is an array
            if (value instanceof String[]) {
                String[] valueArray = (String[]) value;
                if (valueArray.length > 0) {
                    return valueArray[0]; // Return first element
                }
                
            } else if (value instanceof Integer) {
                // Handle integer values
                int intValue = (Integer) value;
                String valueStr;

                switch (intValue) {
                    case -1:
                        valueStr = "*DAILY";
                        break;
                    case -2:
                        valueStr = "*MAX";
                        break;
                    default:
                        valueStr = Integer.toString(intValue);
                        break;
                }

                return valueStr;
                
            } else if (value instanceof String) {
                // Handle string values (most common for QDATE/QTIME)
                return (String) value;
                
            } else {
                // Handle other types of values
                return value.toString();
            }
            
            return null;
            
        } catch (Exception e) {
            return null;
        }
    }
}

