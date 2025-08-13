package com.chanura.dashboard.controller;

import com.chanura.dashboard.service.As400Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpSession;
import java.util.Properties;

@Controller
public class DashboardController {

    @Autowired
    private As400Service as400Service;

    @GetMapping("/")
    public String loginPage() {
        return "login";
    }

    @PostMapping("/login")
    public String login(@RequestParam String username, 
                       @RequestParam String password, 
                       HttpSession session, 
                       Model model) {
        
        if (as400Service.validateCredentials(username, password)) {
            session.setAttribute("username", username);
            session.setAttribute("password", password);
            return "redirect:/dashboard";
        } else {
            model.addAttribute("error", "Password is incorrect");
            return "login";
        }
    }

    @GetMapping("/dashboard")
    public String dashboard(HttpSession session, Model model) {
        String username = (String) session.getAttribute("username");
        String password = (String) session.getAttribute("password");
        
        if (username == null || password == null) {
            return "redirect:/";
        }
        
        Properties systemInfo = as400Service.getSystemInfo(username, password);
        model.addAttribute("qdate", systemInfo.getProperty("QDATE"));
        model.addAttribute("qtime", systemInfo.getProperty("QTIME"));
        model.addAttribute("username", username);
        
        return "dashboard";
    }

    @PostMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }
}
