package com.example.lease_management.service;

import com.example.lease_management.logg.AppUserDetails;
import com.example.lease_management.logg.Employee;
import com.example.lease_management.logg.ExternalUser;
import com.example.lease_management.repository.EmployeeRepository;
import com.example.lease_management.repository.ExternalUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
public class CustomUserDetailsService implements UserDetailsService {

    //UserDetailsService ze spring security, interfejs do sprawdzania


    private final ExternalUserRepository externalUserRepository;
    private final EmployeeRepository employeeRepository;

    @Autowired
    public CustomUserDetailsService(ExternalUserRepository externalUserRepository, EmployeeRepository employeeRepository) {
        this.externalUserRepository = externalUserRepository;
        this.employeeRepository = employeeRepository;
    }


    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        ExternalUser externalUser = externalUserRepository.findByEmail(email);
        System.out.println("Próba logowania: " + email);
        if(externalUser!=null){
            System.out.println("Znaleziono ExternalUser: " + externalUser.getEmail());
            return new AppUserDetails(externalUser);


        }
        Employee employee = employeeRepository.findByEmail(email);
        if(employee!=null){
            System.out.println("Znaleziono Employee: " + employee.getEmail());
            return new AppUserDetails(employee);
        }

        System.out.println("Nie znaleziono użytkownika: " + email);
       throw new UsernameNotFoundException("nie znaleziono użytkownika w bazie");
    }
}
