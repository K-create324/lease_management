package com.example.lease_management;

import com.example.lease_management.logg.Employee;
import com.example.lease_management.logg.ExternalUser;
import com.example.lease_management.repository.EmployeeRepository;
import com.example.lease_management.repository.ExternalUserRepository;
import com.example.lease_management.service.CustomUserDetailsService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class CustomUserDetailsServiceTest {

    @Mock
    ExternalUserRepository externalUserRepository;

    @Mock
    EmployeeRepository employeeRepository;

    @InjectMocks
    CustomUserDetailsService customUserDetailsService;

    @Test
    public void logExternalUser(){

        ExternalUser externalUser= new ExternalUser();
        externalUser.setEmail("a@wp.pl");
        Mockito.when(externalUserRepository.findByEmail("a@wp.pl")).thenReturn(externalUser);
        UserDetails userDetails = customUserDetailsService.loadUserByUsername("a@wp.pl");
        verify(externalUserRepository).findByEmail("a@wp.pl");
        verify(employeeRepository, never()).findByEmail(anyString());
        assertNotNull(userDetails, "Użytkownik znaleziony");
        Assertions.assertEquals("a@wp.pl", userDetails.getUsername());

    }

    @Test
    public void logEmloyee(){

        Employee employee= new Employee();
        employee.setEmail("k@wp.pl");
        Mockito.when(externalUserRepository.findByEmail("k@wp.pl" )).thenReturn(null);
        Mockito.when(employeeRepository.findByEmail("k@wp.pl")).thenReturn(employee);
        UserDetails userDetails = customUserDetailsService.loadUserByUsername("k@wp.pl");

        verify(employeeRepository).findByEmail("k@wp.pl");


        assertNotNull(userDetails, "Użytkowniek znaleziony");
        assertEquals("k@wp.pl", userDetails.getUsername());
    }

    @Test
    public void logNotFound(){
        Mockito.when(externalUserRepository.findByEmail("b@wp.pl" )).thenReturn(null);
        Mockito.when(employeeRepository.findByEmail("b@wp.pl" )).thenReturn(null);

        UsernameNotFoundException usernameNotFoundException = assertThrows(UsernameNotFoundException.class, () -> customUserDetailsService.loadUserByUsername("b@wp.pl"));

        assertEquals("nie znaleziono użytkownika w bazie", usernameNotFoundException.getMessage());
        verify(externalUserRepository).findByEmail("b@wp.pl");
        verify(employeeRepository).findByEmail("b@wp.pl");


// throw new UsernameNotFoundException("nie znaleziono użytkownika w bazie");

    }
}
