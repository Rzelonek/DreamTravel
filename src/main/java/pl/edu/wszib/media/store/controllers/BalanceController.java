package pl.edu.wszib.media.store.controllers;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import pl.edu.wszib.media.store.exceptions.BalanceValidationException;
import pl.edu.wszib.media.store.model.User;
import pl.edu.wszib.media.store.services.IBalanceService;
import pl.edu.wszib.media.store.validators.BalanceValidator;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.regex.Pattern;

@Controller
@RequestMapping(path = "/balance")
public class BalanceController {

    private final IBalanceService balanceService;

    public BalanceController(IBalanceService balanceService) {
        this.balanceService = balanceService;
    }

    @GetMapping
    public String showBalance(HttpSession session, Model model) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return "redirect:/login";
        }

        model.addAttribute("balance", balanceService.getBalance(user));
        return "balanceView";
    }

    @PostMapping(path = "/update")
    public String updateBalance(
            @RequestParam String name,
            @RequestParam String cardNumber,
            @RequestParam String expirationDate,
            @RequestParam String securityCode,
            @RequestParam Double amount,
            HttpSession session, Model model) {

        // Get the logged-in user from the session
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return "redirect:/login"; // Redirect to login if user is not logged in
        }

        // Validate security code and expiration date
        try {
            BalanceValidator.validateSecurityCode(securityCode);
            BalanceValidator.validateExpirationDate(expirationDate);
        } catch (BalanceValidationException e) {
            model.addAttribute("error", e.getMessage());
            return "redirect:/"; // Return to the balance view with an error message
        }

        // If all validations pass, update the user's balance
        balanceService.updateBalance(user, amount);

        // Update the session to reflect the new balance
        session.setAttribute("user", user);

        return "redirect:/"; // Redirect to the balance view
    }

}
