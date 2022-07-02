package com.codecool.trainscheduleapi.unit.service;

import com.codecool.trainscheduleapi.DTO.ServiceDTO;
import com.codecool.trainscheduleapi.DTO.ServiceSelectionDTO;
import com.codecool.trainscheduleapi.controller.ServiceController;
import com.codecool.trainscheduleapi.entity.Service;
import com.codecool.trainscheduleapi.repository.ServiceRepository;
import com.codecool.trainscheduleapi.service.ServiceService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ServiceServiceTest {

    @InjectMocks
    private ServiceService serviceService;

    @Mock
    private ServiceRepository serviceRepository;

    @Test
    void testFindAll() {
        List<Service> expectedService = new ArrayList<>();
        Service service1 = new Service(1L, null, true, true, true,
                true, true, true, true,
                true, true);
        Service service2 = new Service(2L, null, true, true, true,
                true, true, true, true,
                true, true);

        expectedService.add(service1);
        expectedService.add(service2);

        when(serviceRepository.findAll()).thenReturn(expectedService);

        List<ServiceDTO> serviceDTOList = serviceService.findAll();

        assertEquals(2, serviceDTOList.size());
        assertEquals(1L, serviceDTOList.get(0).getId());
        assertEquals(2L, serviceDTOList.get(1).getId());

        verify(serviceRepository, times(1)).findAll();
    }

    @Test
    void testFindById() {
        Optional<Service> service = Optional.of(new Service(1L, null, true, true, true,
                true, true, true, true,
                true, true));

        when(serviceRepository.findById(1L)).thenReturn(service);

        Optional<Service> resultService = serviceService.findById(1L);

        assertTrue(resultService.isPresent());
        assertEquals(1L, resultService.get().getId());

        verify(serviceRepository, times(1)).findById(1L);
    }

    @Test
    void testSave() {
        ServiceSelectionDTO serviceSelection = new ServiceSelectionDTO(true, true, true,
                true, true, true,
                true, true, true);
        Service service = new Service(null, null, true, true, true,
                true, true, true, true,
                true, true);

        when(serviceRepository.save(service)).thenReturn(new Service(1L, null, true, true, true,
                true, true, true, true,
                true, true));

        ServiceDTO resultService = serviceService.save(serviceSelection);

        assertNotNull(resultService);
        assertEquals(1L, resultService.getId());

        verify(serviceRepository, times(1)).save(service);
    }

    @Test
    void testUpdate_withExistingId_expect_update() {
        Optional<Service> serviceOriginal = Optional.of(new Service(1L, null, true, true, true,
                true, true, true, true,
                true, true));

        when(serviceRepository.findById(1L)).thenReturn(serviceOriginal);

        ServiceSelectionDTO serviceSelection = new ServiceSelectionDTO(false, false, false,
                false, false, false, false,
                false, false);
        Service serviceUpdated = new Service(1L, null, false, false, false,
                false, false, false, false,
                false, false);
        ServiceDTO expectedService = new ServiceDTO(1L, null, false, false, false,
                false, false, false, false,
                false, false);

        when(serviceRepository.save(serviceOriginal.get())).thenReturn(serviceUpdated);

        ResponseEntity<?> responseEntity = serviceService.update(serviceSelection, 1L, LoggerFactory.getLogger(ServiceController.class));

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(expectedService, responseEntity.getBody());

        verify(serviceRepository, times(1)).findById(1L);
        verify(serviceRepository, times(1)).save(serviceUpdated);
    }

    @Test
    void testUpdate_withUnknownId_expect_error() {
        when(serviceRepository.findById(2L)).thenReturn(Optional.empty());

        ServiceSelectionDTO serviceSelection = new ServiceSelectionDTO(false, false, false,
                false, false, false, false,
                false, false);
        Service serviceUpdated = new Service(1L, null, false, false, false,
                false, false, false, false,
                false, false);

        ResponseEntity<?> responseEntity = serviceService.update(serviceSelection, 2L, LoggerFactory.getLogger(ServiceController.class));

        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());

        verify(serviceRepository, times(1)).findById(2L);
        verify(serviceRepository, times(0)).save(serviceUpdated);
    }

    @Test
    void testDeleteById_withExistingId_expect_deletion() {
        Optional<Service> service = Optional.of(new Service(1L, null, true, true, true,
                true, true, true, true,
                true, true));

        when(serviceRepository.findById(1L)).thenReturn(service);

        ResponseEntity<?> responseEntity = serviceService.deleteById(1L, LoggerFactory.getLogger(ServiceController.class));

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

        verify(serviceRepository, times(1)).findById(1L);
        verify(serviceRepository, times(1)).delete(service.get());
    }

    @Test
    void testDeleteById_withUnknownId_expect_error() {
        Optional<Service> service = Optional.of(new Service(1L, null, true, true, true,
                true, true, true, true,
                true, true));

        when(serviceRepository.findById(2L)).thenReturn(Optional.empty());

        ResponseEntity<?> responseEntity = serviceService.deleteById(2L, LoggerFactory.getLogger(ServiceController.class));

        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());

        verify(serviceRepository, times(1)).findById(2L);
        verify(serviceRepository, times(0)).delete(service.get());
    }
}
