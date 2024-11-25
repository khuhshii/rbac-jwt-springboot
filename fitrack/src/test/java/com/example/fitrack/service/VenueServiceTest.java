package com.example.fitrack.service;

import com.example.fitrack.dto.VenueDTO;
import com.example.fitrack.models.Venue;
import com.example.fitrack.repository.IVenueRepository;
import com.example.fitrack.services.VenueService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class VenueServiceTest {

    @Mock
    private IVenueRepository venueRepository;

    @InjectMocks
    private VenueService venueService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreateVenue() {
        VenueDTO venueDTO = new VenueDTO();
        venueDTO.setName("Venue Name");
        venueDTO.setCity("City");
        venueDTO.setAddress("Address");
        venueDTO.setCapacity(100);

        Venue venue = new Venue();
        venue.setName(venueDTO.getName());
        venue.setCity(venueDTO.getCity());
        venue.setAddress(venueDTO.getAddress());
        venue.setCapacity(venueDTO.getCapacity());

        when(venueRepository.save(any(Venue.class))).thenReturn(venue);

        Venue createdVenue = venueService.createVenue(venueDTO);

        assertNotNull(createdVenue);
        assertEquals(venueDTO.getName(), createdVenue.getName());
        assertEquals(venueDTO.getCity(), createdVenue.getCity());
        assertEquals(venueDTO.getAddress(), createdVenue.getAddress());
        assertEquals(venueDTO.getCapacity(), createdVenue.getCapacity());
        verify(venueRepository, times(1)).save(any(Venue.class));
    }

    @Test
    public void testGetAllVenues() {
        when(venueRepository.findAll()).thenReturn(List.of(new Venue(), new Venue()));

        List<Venue> venues = venueService.getAllVenues();

        assertNotNull(venues);
        assertEquals(2, venues.size());
        verify(venueRepository, times(1)).findAll();
    }

    @Test
    public void testGetVenueById() {
        Venue venue = new Venue();
        venue.setId(1L);

        when(venueRepository.findById(1L)).thenReturn(Optional.of(venue));

        Venue foundVenue = venueService.getVenueById(1L);

        assertNotNull(foundVenue);
        assertEquals(1L, foundVenue.getId());
        verify(venueRepository, times(1)).findById(1L);
    }

    @Test
    public void testUpdateVenue() {
        VenueDTO venueDTO = new VenueDTO();
        venueDTO.setName("Updated Venue Name");
        venueDTO.setCity("Updated City");
        venueDTO.setAddress("Updated Address");
        venueDTO.setCapacity(200);

        Venue venue = new Venue();
        venue.setId(1L);

        when(venueRepository.findById(1L)).thenReturn(Optional.of(venue));
        when(venueRepository.save(any(Venue.class))).thenReturn(venue);

        Venue updatedVenue = venueService.updateVenue(1L, venueDTO);

        assertNotNull(updatedVenue);
        assertEquals(venueDTO.getName(), updatedVenue.getName());
        assertEquals(venueDTO.getCity(), updatedVenue.getCity());
        assertEquals(venueDTO.getAddress(), updatedVenue.getAddress());
        verify(venueRepository, times(1)).findById(1L);
        verify(venueRepository, times(1)).save(any(Venue.class));
    }

    @Test
    public void testDeleteVenue() {
        Venue venue = new Venue();
        venue.setId(1L);

        when(venueRepository.findById(1L)).thenReturn(Optional.of(venue));
        doNothing().when(venueRepository).delete(any(Venue.class));

        venueService.deleteVenue(1L);

        verify(venueRepository, times(1)).findById(1L);
        verify(venueRepository, times(1)).delete(any(Venue.class));
    }
}
