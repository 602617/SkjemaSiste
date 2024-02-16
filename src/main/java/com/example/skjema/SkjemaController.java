package com.example.skjema;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class SkjemaController {

    @Autowired
    private JavaMailSender mailSender;

    @GetMapping("/form")
    public String showSkjema(Model model){
        model.addAttribute("message", model.containsAttribute("message") ? model.getAttribute("message"): "");
        return "form";
    }

    @PostMapping("/submit")
    public String handleSubmit(@RequestParam String name, @RequestParam String astma,
                               @RequestParam  String daxxin, @RequestParam String skisenter,
                               @RequestParam String kaffe,
                               @RequestParam String tips,
                               @RequestParam String feedback,
                               @RequestParam String accessCode, RedirectAttributes redirectAttributes,Model model){
        final String validAccessCode = "1234";

        if (!accessCode.equals(validAccessCode)) {
            model.addAttribute("message", "Invalid access code.");
            return "form";
        }

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("mattalenes@gmail.com"); // Replace with your email
        message.setTo("mattalenes@gmail.com"); // Set the recipient
        message.setSubject("New Form Submission"); // Email subject
        message.setText("Name: " + name + "\nVentoline: " + astma + "\nDaxxin: " + daxxin +
                "\nSkisenter: " + skisenter + "\nKaffe og pølse: " + kaffe +
                "\nTips: " + tips + "\nFeedback: " + feedback); // Email body
        mailSender.send(message);

        model.addAttribute("message","Thank you for your submission " + name);
        return "form";
    }
}
