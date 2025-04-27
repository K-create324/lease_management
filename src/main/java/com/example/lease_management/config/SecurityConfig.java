package com.example.lease_management.config;

import com.example.lease_management.logg.Employee;
import com.example.lease_management.logg.ExternalUser;
import com.example.lease_management.repository.EmployeeRepository;
import com.example.lease_management.repository.ExternalUserRepository;
import com.example.lease_management.service.CustomUserDetailsService;

import com.vaadin.flow.spring.security.VaadinWebSecurity;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.stereotype.Service;


@Configuration
@EnableWebSecurity
public class SecurityConfig   {

    private final CustomUserDetailsService customUserDetailsService;

    public SecurityConfig(CustomUserDetailsService customUserDetailsService) {
        this.customUserDetailsService = customUserDetailsService;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf
                        .ignoringRequestMatchers(
                                new AntPathRequestMatcher("/VAADIN/**"),
                                new AntPathRequestMatcher("/HEARTBEAT/**"),
                                new AntPathRequestMatcher("/UIDL/**"),
                                new AntPathRequestMatcher("/push/**"),
                                new AntPathRequestMatcher("/resources/**"),
                                new AntPathRequestMatcher("/manifest.json"),
                                new AntPathRequestMatcher("/service-worker.js"),
                                new AntPathRequestMatcher("/sw-runtime-resources-precache.js"),
                                request -> {  // <--- DODAJ TO
                                    String path = request.getRequestURI();
                                    String paramV = request.getParameter("v-r");
                                    return path.equals("/") && paramV != null;
                                }
                        )
                        .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
                )
                .formLogin(form -> form

                        .permitAll()
                        .defaultSuccessUrl("/dashboard", true)

                )
                .logout(logout -> logout
                        .logoutUrl("/logout")  // Ścieżka do wylogowania
                        .logoutSuccessUrl("/login")  // Strona, na którą zostanie przekierowany użytkownik po wylogowaniu
                        .invalidateHttpSession(true)  // Usuwa sesję po wylogowaniu
                        .clearAuthentication(true)  // Usuwa dane uwierzytelniające
                )
                .authorizeHttpRequests(authorizeRequests -> authorizeRequests  // Nowa metoda
                        .requestMatchers("/login", "/register").permitAll()
                        .requestMatchers("/main_view").hasRole("EMPLOYEE" )
                        .requestMatchers("/dashboard").hasAnyRole("EXTERNAL_USER","EMPLOYEE" )



                        .anyRequest().authenticated()  // Reszta wymagająca logowania
                );
        return http.build();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return customUserDetailsService;  // Twój serwis użytkowników
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();  // Szyfrowanie haseł
    }

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder authenticationManagerBuilder =
                http.getSharedObject(AuthenticationManagerBuilder.class);
        authenticationManagerBuilder.userDetailsService(customUserDetailsService)
                .passwordEncoder(passwordEncoder());

        return authenticationManagerBuilder.build();
    }
}
//    konfiguracja dodaje formularz logowania, sprawdzanie hasła i ról

//    private final CustomUserDetailsService customUserDetailsService;
//
//    public SecurityConfig(CustomUserDetailsService customUserDetailsService) {
//        this.customUserDetailsService = customUserDetailsService;
//    }
//
//
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        super.configure(http);
////        setLoginView(http, com.example.lease_management.view.LogView.class);
//        http
//                .formLogin(form -> form
//                        .loginPage("/logowanie")
//                        .defaultSuccessUrl("/dashboard", true)  // <--- to!
//                        .permitAll()
//                );
//        System.out.println("SecurityConfig: LoginView ustawiony, dashboard po zalogowaniu");
//
//
//    }
//
//    @Bean
//    public UserDetailsService userDetailsService() {
//        return customUserDetailsService;
//    }
//
//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder();  // Używamy BCrypt do szyfrowania haseł
//    }
//}
//
//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.userDetailsService(userDetailsService())  // Używamy własnego serwisu dla użytkowników
//                .passwordEncoder(passwordEncoder());
//    }
//}


//        http
//                .authorizeHttpRequests(authorizeRequests -> authorizeRequests
//                        .requestMatchers("/logowanie", "/login").permitAll()  // Strony logowania dostępne dla wszystkich
//                        .requestMatchers("/dashboard").authenticated()        // Strona dashboard tylko dla zalogowanych
//                        .anyRequest().permitAll()  // Pozostałe strony dostępne dla wszystkich
//                )
//                .formLogin(form -> form
//                        .loginPage("/logowanie")  // Strona logowania
//                        .permitAll()  // Pozwól na dostęp do logowania bez autoryzacji
//                )
//                .logout(logout -> logout
//                        .permitAll()  // Pozwól na wylogowanie bez autoryzacji
//                );
//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        http
//                .authorizeHttpRequests(auth -> auth
////                        .requestMatchers("/admin/**").hasRole("EMPLOYEE")
////                        .requestMatchers("/user/**").hasRole("EXTERNAL_USER")
//                                .requestMatchers("/dashboard").authenticated()
//                                .anyRequest().permitAll()
//                )
//                .formLogin(form -> form
////                        .loginPage("/login")
//                                .defaultSuccessUrl("/dashboard", true)
//                                .permitAll()
//
//                )
//                .logout(logout -> logout
//                        .logoutUrl("/logout")
//                        .logoutSuccessUrl("/login?logout")
//                )
//                .userDetailsService(customUserDetailsService);
//
//        return http.build();
//    }

//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder();
//    }

//}
//🔍 Szczegółowe omówienie
//@Bean i SecurityFilterChain:
//
//Adnotacja @Bean informuje Spring, że metoda securityFilterChain zwraca komponent (bean), który powinien być zarządzany przez kontener Springa.
//
//SecurityFilterChain to interfejs definiujący łańcuch filtrów zabezpieczających aplikację webową. Spring Security używa tego łańcucha do przetwarzania żądań HTTP i stosowania odpowiednich zabezpieczeń.
//
//HttpSecurity:
//
//Obiekt HttpSecurity pozwala na konfigurowanie zabezpieczeń HTTP, takich jak uwierzytelnianie, autoryzacja, logowanie i wylogowywanie.
//
//Metoda http.build() kończy konfigurację i tworzy instancję SecurityFilterChain.
//
//authorizeHttpRequests:
//
//Służy do definiowania reguł autoryzacji dla żądań HTTP.
//
//requestMatchers("/admin/**").hasRole("EMPLOYEE"): Wymaga, aby użytkownik miał rolę EMPLOYEE dla wszystkich ścieżek zaczynających się od /admin/.
//
//requestMatchers("/user/**").hasRole("EXTERNAL_USER"): Wymaga roli EXTERNAL_USER dla ścieżek zaczynających się od /user/.
//
//anyRequest().permitAll(): Pozwala na dostęp do wszystkich innych żądań bez uwierzytelnienia.
//
//formLogin:
//
//Konfiguruje logowanie za pomocą formularza.
//
//loginPage("/login"): Określa niestandardową stronę logowania.
//
//defaultSuccessUrl("/dashboard", true): Po pomyślnym logowaniu użytkownik zostaje przekierowany na /dashboard. Drugi argument true oznacza, że przekierowanie nastąpi zawsze, niezależnie od poprzednio żądanej strony.
//
//permitAll(): Pozwala wszystkim użytkownikom (nawet niezalogowanym) na dostęp do strony logowania.
//
//logout:
//
//Konfiguruje mechanizm wylogowywania.
//
//logoutUrl("/logout"): Określa URL, pod którym użytkownik może się wylogować.
//
//logoutSuccessUrl("/login?logout"): Po wylogowaniu użytkownik zostaje przekierowany na stronę logowania z parametrem logout, co może być wykorzystane do wyświetlenia komunikatu o pomyślnym wylogowaniu.
//
//userDetailsService(customUserDetailsService):
//
//Ustawia niestandardową implementację UserDetailsService, która jest odpowiedzialna za ładowanie danych użytkownika (np. z bazy danych) podczas procesu uwierzytelniania.
//
//📚 Dodatkowe informacje
//requestMatchers vs antMatchers: W nowszych wersjach Spring Security requestMatchers zastępuje przestarzałą metodę antMatchers. Pozwala na bardziej elastyczne definiowanie wzorców URL i metod HTTP.
//
//authorizeHttpRequests vs authorizeRequests: Podobnie, authorizeHttpRequests jest nową, zalecaną metodą do definiowania reguł autoryzacji, zastępującą przestarzałą authorizeRequests.
//
//formLogin i logout: Te metody umożliwiają konfigurację logowania i wylogowywania w aplikacji. Dzięki nim można dostosować strony logowania, przekierowania po logowaniu/wylogowaniu oraz inne aspekty związane z uwierzytelnianiem.
