package pl.edu.wszib.media.store.controllers;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import pl.edu.wszib.media.store.dao.ITripDAO;
import pl.edu.wszib.media.store.services.ICartService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(path = "/cart")
@RequiredArgsConstructor
public class CartController {

    private final ICartService cartService;

    @Autowired
    HttpSession httpSession;

    @GetMapping(path = "/add/{id}")
    public String addToCart(@PathVariable final Long id) {
        this.cartService.addTripToCart(id);
        return "redirect:/";
    }

    @GetMapping
    public String cart(Model model) {
        model.addAttribute("cartSum", this.cartService.calculateCartSum());
        return "cart";
    }

    /*
     * @GetMapping(path = "/test")
     * public String test(Model model) {
     * return "test.css";
     * }
     */
}
