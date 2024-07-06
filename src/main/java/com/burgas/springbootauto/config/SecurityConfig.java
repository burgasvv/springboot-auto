package com.burgas.springbootauto.config;

import com.burgas.springbootauto.handler.CustomAuthenticationSuccessHandler;
import com.burgas.springbootauto.service.person.CustomUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final CustomUserDetailsService userDetailsService;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(
                requests -> requests
                        .requestMatchers("/resource/**").permitAll()
                        .requestMatchers("/","/registration","/search","news/**","/images/**","/activateAccount/{token}",
                                "/forgotPassword","/forgotPassword/{status}","/restorePassword","/restorePassword/{token}",
                                "/brands","/brands/pages/{page}","/brands/{id}","/brands/{id}/cars",
                                "/brands/{id}/cars/pages/{page}","/brands/{id}/search-brand-cars",
                                "/brands/{id}/search-brand-cars/pages/{page}","/brands/search","/brands/search/pages/{page}",

                                "/cars","/cars/pages/{page}", "/cars/find-cars","/cars/find-cars/pages/{page}","/cars/{id}",
                                "/cars/{id}/images", "/cars/{od}/images/pages/{page}","/cars/search-by-tag",
                                "/cars/search-by-tag/pages/{page}", "/cars/search-by-tag/search","/cars/search-by-tag/search/pages/{page}",

                                "/drive-units","/drive-units/{id}","/drive-units/{id}/cars","/drive-units/{id}/cars/pages/{page}",
                                "/drive-units/{id}/cars/find-cars","/drive-units/{id}/cars/find-cars/pages/{page}",

                                "/categories","/categories/{id}","/categories/{id}/cars","/categories/{id}/cars/pages/{page}",
                                "/categories/{id}/find-category-cars","/categories/{id}/find-category-cars/pages/{page}",

                                "/classes","/classes/{id}","/classes/{id}/cars","/classes/{id}/cars/pages/{page}",
                                "/classes/{id}/find-class-cars","/classes/{id}/find-class-cars/pages/{page}")
                        .permitAll()
                        .requestMatchers("/brands/{id}/editions","/brands/{id}/gearboxes","/brands/{id}/turbo-types",

                                "/cars/secure/add","/cars/{id}/edit","/cars/{id}/add-images","/cars/{id}/add-preview-image",
                                "/cars/{id}/add-images-at-images-page","/cars/{id}/remove-preview-image",
                                "/cars/{id}/add-images-at-images-page","/cars/{id}/images/delete-image",
                                "/cars/{id}/set-preview/{imageId}","/cars/{id}/images/remove-preview-from-images",
                                "/cars/{id}/delete","/cars/{id}/handover","/cars/{id}/handover-done",
                                "/cars/{id}/attach-tag","/cars/{id}/add-tag","/cars/{id}/attach-equipment",
                                "/cars/{id}/remove-equipment-from-car","/cars/{id}/remove-equipment-from-car-in-form",

                                "/engines","/engines/{id}","/engines/find-engines",
                                "/editions","/editions/{id}","/gearboxes","/gearboxes/{id}",

                                "/equipments","/equipments/pages/{page}","/equipments/search","/equipments/search/pages/{page}",
                                "/equipments/{id}","/equipments/secure/add","/equipments/{id}/edit-equipment",
                                "/equipments/{id}/delete-equipment","/equipments/{id}/add-engine","/equipments/{id}/remove-engine",
                                "/equipments/{id}/add-transmission","/equipments/{id}/remove-transmission",
                                "/equipments/{id}/add-turbocharger","/equipments/{id}/remove-turbocharger",
                                "/equipments/{id}/share-equipment","/equipments/{id}/attach-to-car","/equipments/{id}/detach-from-car",

                                "/users","/users/pages/{page}","/users/search","/users/search/pages/{page}",
                                "/users/{name}","/users/{name}/edit","/users/secure/edit","/users/{name}/change-image",
                                "/users/{name}/add-image","/users/{name}/remove-image","/users/{name}/delete",
                                "/users/{name}/cars","/users/{name}/cars/pages/{page}","/users/{name}/cars/search",
                                "/users/{name}/cars/search/pages/{page}","/users/{name}/equipments",
                                "/users/{name}/equipments/pages/{page}","/users/{name}/equipments/search",
                                "/users/{name}/equipments/search/pages/{page}",

                                "/transmissions","/transmissions/{id}","/transmissions/find-transmissions",
                                "/turbochargers","/turbochargers/{id}","/turbochargers/find-turbochargers",
                                "/turbo-types","/turbo-types/{id}")
                        .hasAnyAuthority("USER", "ADMIN")
                        .requestMatchers("/brands/secure/add","/brands/{id}/edit","/brands/{id}/change-image",
                                "/brands/{id}/remove-image","/brands/{id}/delete","/brands/{id}/add-edition",
                                "/brands/{id}/add-gearbox","/brands/{id}/add-transmission","/brands/{id}/add-turbo-type",
                                "/brands/{id}/add-turbocharger",

                                "/categories/secure/add","/categories/{id}/edit","/categories/{id}/delete",
                                "/classes/secure/add","/classes/{id}/edit","/classes/{id}/delete",

                                "/engine-characteristics/{id}/edit",
                                "/engines/secure/add","/engines/{id}/edit", "/engines/{id}/delete",
                                "/editions/secure/add","/editions/{id}/edit","/editions/{id}/delete",
                                "/gearboxes/secure/add","/gearboxes/{id}/edit","/gearboxes/{id}/delete",

                                "/users/secure/make-admin","/users/{name}/ban","/users/{name}/unban",

                                "/transmissions/secure/add","/transmissions/{id}/edit","/transmissions/{id}/delete",
                                "/turbochargers/secure/add","/turbochargers/{id}/edit","/turbochargers/{id}/delete",
                                "/turbo-types/secure/add","/turbo-types/{id}e/edit","/turbo-types/{id}/delete")
                        .hasAuthority("ADMIN")
                        .anyRequest().authenticated());
        http
                .formLogin(
                        login -> login.loginPage("/login")
                                .successHandler(authenticationSuccessHandler())
                                .permitAll()
                )
                .logout(
                        logout -> logout.logoutUrl("/logout")
                                .logoutSuccessUrl("/login")
                                .permitAll());

        return http.build();
    }

    @Bean
    public AuthenticationSuccessHandler authenticationSuccessHandler() {
        return new CustomAuthenticationSuccessHandler();
    }

    @Bean
    public AuthenticationManager authenticationManager(){
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return new ProviderManager(authProvider);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
