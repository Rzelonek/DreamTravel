package pl.edu.wszib.media.store.controllers;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.Part;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.support.ByteArrayMultipartFileEditor;

import pl.edu.wszib.media.store.exceptions.TripValidationException;
import pl.edu.wszib.media.store.model.Trip;
import pl.edu.wszib.media.store.services.ITripService;
import pl.edu.wszib.media.store.validators.TripValidator;
import org.springframework.http.HttpHeaders;
import java.util.Base64;
import java.io.IOException;
import java.util.Optional;

@Controller
@RequestMapping(path = "/trip")
public class TripController {

    private final ITripService tripService;

    public TripController(ITripService tripService) {
        this.tripService = tripService;
    }

    @RequestMapping(path = "/add", method = RequestMethod.GET)
    public String add(Model model) {
        model.addAttribute("tripModel", new Trip());
        return "addForm";
    }

    @RequestMapping(path = "/details/{id}", method = RequestMethod.GET)
    public String details(@PathVariable Long id, Model model) {
        Optional<Trip> tripBox = this.tripService.getById(id);
        if (tripBox.isEmpty()) {
            return "redirect:/";
        } else {
            model.addAttribute("tripModel", tripBox.get());

        }
        return "tripdetails";
    }

    @PostMapping(path = "/add")
    public String add(@ModelAttribute Trip trip) {
        try {

            TripValidator.validateGenderType(trip.getType());
        } catch (TripValidationException e) {
            e.printStackTrace();
            return "redirect:/trip/add";
        }

        tripService.save(trip);
        return "redirect:/";
    }

    @InitBinder
    protected void initBinder(HttpServletRequest request, WebDataBinder binder)
            throws ServletException {

        binder.registerCustomEditor(byte[].class, new ByteArrayMultipartFileEditor());
    }

    @RequestMapping(path = "/edit/{id}", method = RequestMethod.GET)
    public String edit(@PathVariable Long id, Model model) {
        Optional<Trip> tripBox = this.tripService.getById(id);
        if (tripBox.isEmpty()) {
            return "redirect:/";
        } else {
            model.addAttribute("tripModel", tripBox.get());

        }
        return "tripForm";
    }

    @PostMapping(path = "/edit/{id}")
    public String edit(@ModelAttribute Trip trip,
            @PathVariable Long id,
            @RequestParam(value = "photo", required = false) Part photoPart) {
        try {

            TripValidator.validateGenderType(trip.getType());
        } catch (TripValidationException e) {
            return "redirect:/trip/edit/" + id;
        }

        Optional<Trip> existingTripOptional = tripService.getById(id);
        if (existingTripOptional.isPresent()) {
            Trip existingTrip = existingTripOptional.get();

            if (photoPart == null || photoPart.getSize() == 0) {
                trip.setPhoto(existingTrip.getPhoto());
            } else {

                try {
                    byte[] photoBytes = photoPart.getInputStream().readAllBytes();
                    trip.setPhoto(photoBytes);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        tripService.update(trip, id);
        return "redirect:/";
    }

    @GetMapping("/photo/{id}")
    public ResponseEntity<byte[]> getTripPhoto(@PathVariable Long id) {
        Optional<Trip> tripOpt = tripService.getById(id);
        if (tripOpt.isEmpty() || tripOpt.map(Trip::getPhoto).orElse(null) == null) {
            return ResponseEntity.notFound().build();
        }

        byte[] photo = tripOpt.get().getPhoto();
        return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(photo);
    }

    @GetMapping("/photo/bytes/{id}")
    public ResponseEntity<String> getTripPhotoBytes(@PathVariable Long id) {
        Optional<Trip> tripOpt = tripService.getById(id);
        if (tripOpt.isEmpty() || tripOpt.map(Trip::getPhoto).orElse(null) == null) {
            return ResponseEntity.notFound().build();
        }

        byte[] photo = tripOpt.get().getPhoto();
        String base64Photo = Base64.getEncoder().encodeToString(photo);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "text/plain");
        return ResponseEntity.ok().headers(headers).body(base64Photo);
    }

}
