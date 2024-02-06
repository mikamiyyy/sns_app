package jp.ac.morijyobi.sns_app.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        http
                .authorizeHttpRequests(requests -> requests
                        .requestMatchers("/").permitAll() // このURLはログイン無しでok
                        .requestMatchers("/common/**").permitAll() // common配下はログインなしでok
                        .requestMatchers("/sns/**").hasRole("GENERAL")
                        .requestMatchers("/management/**").hasRole("ADMIN")
                        .anyRequest().authenticated()       //それ以外のURLはログインが必要
                ).formLogin(login -> login
                        .loginProcessingUrl("/login")        // id,pwの送信先URL(POST)
                        .loginPage("/login")                 //ログイン画面のURL(GET)
                        .defaultSuccessUrl("/")         //ログイン成功時のリダイレクト先URL
                        .failureUrl("/login?error") //ログイン失敗時のリダイレクト
                        .permitAll()                        //ログイン画面のアクセス権限の設定(全て許可)
                ).logout(logout -> logout
                        .logoutUrl("/logout")  // ログアウトのURL
                        .logoutSuccessUrl("/login")  // ログアウト成功時のリダイレクト先URL
                        .invalidateHttpSession(true)  // セッションを無効にする
                        .deleteCookies("JSESSIONID")  // クッキーを削除する
                        .permitAll()
                );

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
