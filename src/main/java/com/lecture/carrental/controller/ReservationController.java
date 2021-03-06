package com.lecture.carrental.controller;

import com.lecture.carrental.domain.Car;
import com.lecture.carrental.domain.Reservation;
import com.lecture.carrental.dto.ReservationDTO;
import com.lecture.carrental.service.ReservationService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@AllArgsConstructor
@RestController
@Produces(MediaType.APPLICATION_JSON)
@RequestMapping("/reservations")
public class ReservationController {

    public ReservationService reservationService;


    @GetMapping("/admin/all")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<ReservationDTO>> getAllReservation() {
        List<ReservationDTO> reservationDTOS = reservationService.fetchAllRezervations();
        return new ResponseEntity<>(reservationDTOS, HttpStatus.OK);
    }



    @GetMapping("/admin/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ReservationDTO> getReservationById(@PathVariable Long id) {
        ReservationDTO reservationDTOS = reservationService.findById(id);
        return new ResponseEntity<>(reservationDTOS, HttpStatus.OK);
    }


    @GetMapping("/admin/auth/all")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<ReservationDTO>> getAllUserReservations(@RequestParam(value = "userId") Long userId) {
        List<ReservationDTO> reservations = reservationService.findAllByUserId(userId);
        return new ResponseEntity<>(reservations, HttpStatus.OK);
    }
    @PostMapping("/add/auth")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Map<String, Boolean>> addReservation(@RequestParam (value = "userId") Long userId,
                                                               @RequestParam (value = "carId") Car carId,
                                                               @Valid @RequestBody Reservation reservation) {
        reservationService.addReservation(reservation, userId, carId);
        Map<String, Boolean> map = new HashMap<>();
        map.put("Reservation added successfully!", true);
        return new ResponseEntity<>(map, HttpStatus.CREATED);
    }


    @GetMapping("/{id}/auth")
    @PreAuthorize("hasRole('CUSTOMER') or hasRole('ADMIN')")
    public ResponseEntity<ReservationDTO> getUserReservationById(@PathVariable Long id, HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("id");

        ReservationDTO reservation = reservationService.findByIdAndUserId(id, userId);
        return new ResponseEntity<>(reservation, HttpStatus.OK);
    }

    @GetMapping("/auth/all")
    @PreAuthorize("hasRole('CUSTOMER') or hasRole('ADMIN')")
    public ResponseEntity<List<ReservationDTO>> getUserReservationsByUserId(HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("id");
        List<ReservationDTO> reservations = reservationService.findAllByUserId(userId);
        return new ResponseEntity<>(reservations, HttpStatus.OK);
    }

    @PostMapping("/add")
    @PreAuthorize("hasRole('CUSTOMER') or hasRole('ADMIN')")
    public ResponseEntity<Map<String, Boolean>> makeReservation(HttpServletRequest request,
                                                                @RequestParam(value = "carId") Car carId,
                                                                @Valid @RequestBody Reservation reservation) {

        Long userId = (Long) request.getAttribute("id");
        reservationService.addReservation(reservation, userId, carId);

        Map<String, Boolean> map = new HashMap<>();
        map.put("Reservation added successfully!", true);

        return new ResponseEntity<>(map, HttpStatus.CREATED);
    }
}