package by.yatsukovich.security.config;

/*@Configuration
@RequiredArgsConstructor
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)*/
public class SecurityConfiguration {

    /*private final UserDetailsService userProvider;

    private final TokenUtils tokenUtils;
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration)
            throws Exception {

        return authenticationConfiguration.getAuthenticationManager();
    }
    @Bean
    public AuthenticationTokenFilter authenticationTokenFilterBean(AuthenticationManager authenticationManager) {
        AuthenticationTokenFilter authenticationTokenFilter = new AuthenticationTokenFilter(userProvider, tokenUtils);
        authenticationTokenFilter.setAuthenticationManager(authenticationManager);
        return authenticationTokenFilter;
    }


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .csrf()
                .disable()
                .exceptionHandling()
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers("auth/**").permitAll()
                .antMatchers("users/**").permitAll()
                .anyRequest()
                .authenticated();

        httpSecurity
                .addFilterBefore(authenticationTokenFilterBean(), UsernamePasswordAuthenticationFilter.class);
        return httpSecurity.build();
    }*/
}
