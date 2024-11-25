package com.example.fitrack.services;

import com.example.fitrack.dto.VenueDTO;
import com.example.fitrack.models.Venue;
import com.example.fitrack.repository.IVenueRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VenueService {

    private final IVenueRepository venueRepository;

    public VenueService(IVenueRepository venueRepository) {
        this.venueRepository = venueRepository;
    }

    public Venue createVenue(VenueDTO venueDTO) {
        Venue venue = new Venue();
        venue.setName(venueDTO.getName());
        venue.setCity(venueDTO.getCity());
        venue.setAddress(venueDTO.getAddress());
        venue.setCapacity(venue.getCapacity());
        return venueRepository.save(venue);
    }

    public List<Venue> getAllVenues() {
        return venueRepository.findAll();
    }

    public Venue getVenueById(Long id) {
        return venueRepository.findById(id).orElseThrow(() -> new RuntimeException("Venue not found"));
    }

    public Venue updateVenue(Long id, VenueDTO venueDTO) {
        Venue venue = venueRepository.findById(id).orElseThrow(() -> new RuntimeException("Venue not found"));
        venue.setName(venueDTO.getName());
        venue.setCity(venueDTO.getCity());
        venue.setAddress(venueDTO.getAddress());
        return venueRepository.save(venue);
    }

    public void deleteVenue(Long id) {
        Venue venue = venueRepository.findById(id).orElseThrow(() -> new RuntimeException("Venue not found"));
        venueRepository.delete(venue);
    }
}

