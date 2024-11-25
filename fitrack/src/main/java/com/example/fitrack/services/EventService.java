package com.example.fitrack.services;

import com.example.fitrack.customexceptions.ResourceNotFoundException;
import com.example.fitrack.dto.EventDTO;
import com.example.fitrack.models.Event;
import com.example.fitrack.models.User;
import com.example.fitrack.models.Venue;
import com.example.fitrack.repository.IEventRepository;
import com.example.fitrack.repository.IUserRepository;
import com.example.fitrack.repository.IVenueRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.module.ResolutionException;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class EventService {

    private final IEventRepository eventRepository;
    private final IUserRepository userRepository;
    private final IVenueRepository venueRepository;

    public EventService(IEventRepository eventRepository, IUserRepository userRepository, IVenueRepository venueRepository) {
        this.eventRepository = eventRepository;
        this.userRepository = userRepository;
        this.venueRepository = venueRepository;
    }


    public Event createEvent(EventDTO eventDTO) {
        User user = userRepository.findById(eventDTO.getCreatedById())
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        Venue venue = venueRepository.findById(eventDTO.getVenueId())
                .orElseThrow(() -> new ResourceNotFoundException("Venue not found"));
        Event event = new Event();
        event.setName(eventDTO.getName());
        event.setTeamSize(eventDTO.getTeamSize());
        event.setCreatedBy(user);
        event.setVenue(venue);
        event.setCategory(eventDTO.getCategory());
        event.setCreatedAt(LocalDateTime.now());
        event.setUpdatedAt(LocalDateTime.now());
        return eventRepository.save(event);
    }

    public List<Event> getAllEvents() {
        return eventRepository.findAll();
    }

    public Event getEventById(Long id) {
        return eventRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Event not found"));
    }

    public Event updateEvent(Long id, EventDTO eventDTO) {
        Event event = eventRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Event not found"));

        Venue venue = venueRepository.findById(eventDTO.getVenueId())
                .orElseThrow(() -> new ResourceNotFoundException("Venue not found"));

        event.setName(eventDTO.getName());
        event.setTeamSize(eventDTO.getTeamSize());
        event.setVenue(venue);
        event.setUpdatedAt(LocalDateTime.now());

        return eventRepository.save(event);
    }

    public void deleteEvent(Long id) {
        Event event = eventRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Event not found"));
        eventRepository.delete(event);
    }

    public Event searchEvents(Long eventId) {
        return eventRepository.getReferenceById(eventId);
    }
}

