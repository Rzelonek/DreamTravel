package pl.edu.wszib.media.store.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import pl.edu.wszib.media.store.exceptions.EmptyCartException;
import pl.edu.wszib.media.store.exceptions.IncorrectCartPositionsException;
import pl.edu.wszib.media.store.exceptions.InsufficientBalanceException;
import pl.edu.wszib.media.store.exceptions.UserNotLoggedException;
import pl.edu.wszib.media.store.services.IOrderService;

@Controller
public class OrderController {

    private final IOrderService orderService;

    public OrderController(IOrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("/order/add")
    public String order() {
        try {
            this.orderService.confirmOrder();
        } catch (EmptyCartException | IncorrectCartPositionsException | InsufficientBalanceException e) {
            // You can redirect to the cart page or display a message to the user
            return "redirect:/cart?error=" + e.getMessage();
        }
        return "redirect:/";
    }

    @GetMapping("/order")
    public String orders(Model model) {
        try {
            model.addAttribute("orders", this.orderService.getOrdersForCurrentUser());
        } catch (UserNotLoggedException e) {
            return "redirect:/";
        }
        return "orders";
    }
}
