package com.example.fitrack.service;

import com.example.fitrack.customexceptions.ResourceNotFoundException;
import com.example.fitrack.dto.EventDTO;
import com.example.fitrack.models.Category;
import com.example.fitrack.models.Event;
import com.example.fitrack.models.User;
import com.example.fitrack.models.Venue;
import com.example.fitrack.repository.IEventRepository;
import com.example.fitrack.repository.IUserRepository;
import com.example.fitrack.repository.IVenueRepository;
import com.example.fitrack.services.EventService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class EventServiceTest {

    @Mock
    private IEventRepository eventRepository;

    @Mock
    private IUserRepository userRepository;

    @Mock
    private IVenueRepository venueRepository;

    @InjectMocks
    private EventService eventService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreateEvent() {
        EventDTO eventDTO = new EventDTO();
        eventDTO.setName("Event Name");
        eventDTO.setTeamSize(5);
        eventDTO.setCreatedById(1L);
        eventDTO.setVenueId(1L);
        eventDTO.setCategory(Category.TEAM);

        User user = new User();
        user.setId(1L);

        Venue venue = new Venue();
        venue.setId(1L);

        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));
        when(venueRepository.findById(anyLong())).thenReturn(Optional.of(venue));
        when(eventRepository.save(any(Event.class))).thenReturn(new Event());

        Event event = eventService.createEvent(eventDTO);

        assertNotNull(event);
        verify(userRepository, times(1)).findById(anyLong());
        verify(venueRepository, times(1)).findById(anyLong());
        verify(eventRepository, times(1)).save(any(Event.class));
    }

    @Test
    public void testGetEventById() {
        Event event = new Event();
        event.setId(1L);

        when(eventRepository.findById(anyLong())).thenReturn(Optional.of(event));

        Event foundEvent = eventService.getEventById(1L);

        assertNotNull(foundEvent);
        assertEquals(1L, foundEvent.getId());
        verify(eventRepository, times(1)).findById(anyLong());
    }

    @Test
    public void testUpdateEvent() {
        EventDTO eventDTO = new EventDTO();
        eventDTO.setName("Updated Event Name");
        eventDTO.setTeamSize(10);
        eventDTO.setVenueId(1L);

        Event event = new Event();
        event.setId(1L);

        Venue venue = new Venue();
        venue.setId(1L);

        when(eventRepository.findById(anyLong())).thenReturn(Optional.of(event));
        when(venueRepository.findById(anyLong())).thenReturn(Optional.of(venue));
        when(eventRepository.save(any(Event.class))).thenReturn(event);

        Event updatedEvent = eventService.updateEvent(1L, eventDTO);

        assertNotNull(updatedEvent);
        assertEquals("Updated Event Name", updatedEvent.getName());
        assertEquals(10, updatedEvent.getTeamSize());
        verify(eventRepository, times(1)).findById(anyLong());
        verify(venueRepository, times(1)).findById(anyLong());
        verify(eventRepository, times(1)).save(any(Event.class));
    }

    @Test
    public void testDeleteEvent() {
        Event event = new Event();
        event.setId(1L);

        when(eventRepository.findById(anyLong())).thenReturn(Optional.of(event));
        doNothing().when(eventRepository).delete(any(Event.class));

        eventService.deleteEvent(1L);

        verify(eventRepository, times(1)).findById(anyLong());
        verify(eventRepository, times(1)).delete(any(Event.class));
    }

    @Test
    public void testGetAllEvents() {
        when(eventRepository.findAll()).thenReturn(List.of(new Event(), new Event()));

        List<Event> events = eventService.getAllEvents();

        assertNotNull(events);
        assertEquals(2, events.size());
        verify(eventRepository, times(1)).findAll();
    }
}
