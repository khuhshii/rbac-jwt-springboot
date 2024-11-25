package com.example.fitrack.controllers;
import com.example.fitrack.customexceptions.UnauthorizedAccessException;

import com.example.fitrack.dto.EventDTO;
import com.example.fitrack.dto.request.SearchEventsRequest;
import com.example.fitrack.models.Event;
import com.example.fitrack.models.Role;
import com.example.fitrack.models.User;
import com.example.fitrack.services.EventService;
import com.example.fitrack.services.UserService;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@RestController
@RequestMapping("/api/events")
@CrossOrigin(origins="http://localhost:4200")
public class EventController {

    @Autowired
    private final EventService eventService;

    @Autowired
    private UserService userService;

    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    @PostMapping
    public ResponseEntity<?> createEvent(@RequestBody EventDTO eventDTO) {
        User user = userService.getByUserId(eventDTO.getCreatedById());
        if(user.getRole() != Role.ADMIN) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User is not authorized to create events.");
        }
        Event createdEvent = eventService.createEvent(eventDTO);
        Map<String, String> response = new HashMap<>();
        response.put("status", String.valueOf(HttpStatus.CREATED.value()));
        response.put("message", "Event Created Successfully");
        response.put("data", createdEvent.toString());
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<?> getAllEvents() {
        List<Event> events = eventService.getAllEvents();
        if (events.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(Map.of(
                    "status", HttpStatus.NO_CONTENT.value(),
                    "message", "No events available.",
                    "data", null
            ));
        }
        Map<String, String> response = new HashMap<>();
        response.put("status", String.valueOf(HttpStatus.CREATED.value()));
        response.put("message", "Event Created Successfully");
        response.put("data", events.toString());
        return ResponseEntity.status(HttpStatus.CREATED).body(events);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getEventById(@PathVariable Long id) {
        Event event = eventService.getEventById(id);
        return ResponseEntity.ok(Map.of(
                "status", HttpStatus.OK.value(),
                "message", "Event fetched successfully.",
                "data", event
        ));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateEvent(@PathVariable Long id, @RequestBody EventDTO eventDTO) {
        User user = userService.getByUserId(eventDTO.getCreatedById());
        if (user.getRole() != Role.ADMIN || !Objects.equals(user.getId(), eventDTO.getCreatedById())) {
            throw new UnauthorizedAccessException("User is not authorized to update event.");
        }
        Event updatedEvent = eventService.updateEvent(id, eventDTO);
        return ResponseEntity.ok(Map.of(
                "status", HttpStatus.OK.value(),
                "message", "Event Updated Successfully",
                "data", updatedEvent
        ));
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteEvent(@PathVariable Long id) {
        eventService.deleteEvent(id);
        return ResponseEntity.ok(Map.of(
                "status", HttpStatus.OK.value(),
                "message", "Event Deleted Successfully",
                "data", null
        ));
    }
    

    @GetMapping("/search/{eventId}")
    public ResponseEntity<?> searchEvents(@PathVariable Long eventId) {
        Event event = eventService.searchEvents(eventId);
        return ResponseEntity.ok(Map.of(
                "status", HttpStatus.OK.value(),
                "message", "Event found successfully.",
                "data", event
        ));
    }
}