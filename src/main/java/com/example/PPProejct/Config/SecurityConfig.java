package com.example.PPProejct.Config;

import com.example.PPProejct.Service.JwtAuthenticationFilter;
import com.example.PPProejct.Service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;

import java.util.List;

import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;


@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {

//    ## Разбор кода
//
//    Этот код на Java представляет собой конфигурацию безопасности для веб-приложения, основанного на Spring Security.
//
//            **1. Метод securityFilterChain**
//
//            * `public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception` -  это метод, который принимает объект `HttpSecurity` и возвращает объект `SecurityFilterChain`.
//            * `HttpSecurity` - это объект, предоставляемый Spring Security для конфигурации безопасности.
//* `SecurityFilterChain` - это объект, представляющий цепочку фильтров безопасности.
//* `throws Exception` -  указывает, что метод может бросить исключение.
//
//**2.  Конфигурация безопасности**
//
//            * `httpSecurity.csrf(AbstractHttpConfigurer::disable)` - Отключает защиту от межсайтовой подделки запросов (CSRF).
//            * `httpSecurity.cors(cors -> cors.configurationSource(request -> {...}))` - Настраивает обработку CORS (Cross-Origin Resource Sharing), позволяя ресурсам с разных доменов обращаться к этому приложению.
//            * `cors.configurationSource(request -> {...})` -  создает объект `CorsConfigurationSource`, который определяет конфигурацию CORS для каждого запроса.
//    * `corsConfiguration.setAllowedOriginPatterns(List.of("*"))` -  разрешает доступ с любых доменов.
//    * `corsConfiguration.setAllowedMethods(List.of("GET","POST", "PUT", "DELETE", "OPTIONS"))` -  разрешает HTTP-методы: GET, POST, PUT, DELETE, OPTIONS.
//    * `corsConfiguration.setAllowedHeaders(List.of("*"))` -  разрешает любые заголовки запросов.
//            * `corsConfiguration.setAllowCredentials(true)` -  разрешает отправку куки с запросов.
//* `httpSecurity.authorizeHttpRequests(request -> {...})` -  Настраивает авторизацию для разных путей приложения:
//            * `requestMatchers("/auth/").permitAll()` -  Разрешает доступ к пути `/auth/` без авторизации.
//            * `requestMatchers("/swagger-ui/", "/swagger-resources/*", "/v3/api-docs/").permitAll()` -  Разрешает доступ к ресурсам Swagger без авторизации.
//    * `requestMatchers("/endpoint", "/admin/").hasRole("ADMIN")` -  Разрешает доступ к путям `/endpoint` и `/admin/` только для пользователей с ролью "ADMIN".
//            * `anyRequest().authenticated()` -  Требует авторизацию для всех остальных запросов.
//            * `httpSecurity.sessionManagement(manager -> manager.sessionCreationPolicy(STATELESS))` -  Настраивает управление сессиями как "без состояний" (STATELESS).
//            * `httpSecurity.authenticationProvider(authenticationProvider())` -  Устанавливает провайдер аутентификации.
//* `httpSecurity.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)` -  Добавляет фильтр `jwtAuthenticationFilter` перед стандартным фильтром `UsernamePasswordAuthenticationFilter` для проверки JWT-токенов.
//* `return httpSecurity.build();` -  Создает и возвращает объект `SecurityFilterChain` с заданной конфигурацией.
//
//**3. Дополнительные замечания**
//
//            * `authenticationProvider()` -  это метод, который должен быть определен в другом месте кода и возвращает объект `AuthenticationProvider`, ответственный за проверку учетных данных пользователя.
//            * `jwtAuthenticationFilter` -  это фильтр, который должен быть определен в другом месте кода и реализует проверку JWT-токенов для авторизации.
//
//**4. В итоге**
//
//    Данный код настраивает Spring Security для защиты веб-приложения. Он включает:
//
//            * Отключение CSRF
//* Настройку CORS
//* Авторизацию для разных путей
//* Управление сессиями без состояний
//* Использование провайдера аутентификации
//* Включение фильтра для проверки JWT-токенов.
//
//    Важно отметить, что код является лишь фрагментом более широкой конфигурации, и может быть дополнен или изменен в зависимости от потребностей конкретного приложения.
    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final UserService userService;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception{
        httpSecurity.csrf(AbstractHttpConfigurer::disable)
                .cors(cors -> cors.configurationSource(request ->  {
                    var corsConfiguration = new CorsConfiguration();
                    corsConfiguration.setAllowedOriginPatterns(List.of("*"));
                    corsConfiguration.setAllowedMethods(List.of("GET","POST", "PUT", "DELETE", "OPTIONS"));
                    corsConfiguration.setAllowedHeaders(List.of("*"));
                    corsConfiguration.setAllowCredentials(true);
                    return corsConfiguration;
                }))
                .authorizeHttpRequests(request -> request
                        .requestMatchers("/auth/**").permitAll()
                        .requestMatchers("/swagger-ui/**", "/swagger-resources/*", "/v3/api-docs/**").permitAll()
                        .requestMatchers("/no-auth/**").permitAll()
                        .requestMatchers("/admin-panel").hasRole("ADMIN")
                        .requestMatchers("/api/create-team").hasRole("TEAMLEAD")//доработать
                        .anyRequest().authenticated())
                .sessionManagement(manager -> manager.sessionCreationPolicy(STATELESS))
                .authenticationProvider(authenticationProvider())
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
        return httpSecurity.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider authenticationProvider =new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userService.userDetailsService());
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        return authenticationProvider;
    }


    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception{
        return configuration.getAuthenticationManager();
    }
}
