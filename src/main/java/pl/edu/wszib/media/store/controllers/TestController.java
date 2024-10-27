package pl.edu.wszib.media.store.controllers;

import lombok.RequiredArgsConstructor;
import pl.edu.wszib.media.store.dao.ITripDAO;
import pl.edu.wszib.media.store.dao.impl.spring.data.TripDAO;
import pl.edu.wszib.media.store.model.Trip;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class TestController {

        private final TripDAO tripDAO;

        @GetMapping("/test1")
        public void test() {
                System.out.println("dziala !!!");

        }
}
