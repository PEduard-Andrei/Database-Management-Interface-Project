/** Clasa pentru controllerul paginii de autentificare (autentificare, inregistrare direct la baza de date)
 * @author Paunita Eduard-Andrei 332AA
 * @version 26 Decembrie 2024
 */
package proiectBD.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.ui.Model;
import java.util.ArrayList;
import java.util.List;

// Controller pentru autentificare si inregistrare utilizatori
@Controller
public class AuthController {
    // Cheia admin folosita pentru validarea inregistrarii de noi utilizatori
    private static final String ADMIN_KEY = "kJ9#mP2$vL5nX8@qR4wH7*cF3bN6%yT1pA9&dM5gU2#sE7jW4$xK8nB3@vQ6";
    @Autowired
    private JdbcTemplate jdbcTemplate;

    // Redirectioneaza catre pagina principala
    @GetMapping("/")
    public String home() {
        return "redirect:/dashboard";
    }

    // Afiseaza pagina de autentificare
    @GetMapping("/login")
    public String login() {
        return "login";
    }

    // Afiseaza pagina de inregistrare
    @GetMapping("/register")
    public String showRegister() {
        return "register";
    }

    // Proceseaza datele pentru crearea unui cont nou
    @PostMapping("/register")
    public String register(@RequestParam String username,
                           @RequestParam String password,
                           @RequestParam String confirmPassword,
                           @RequestParam String adminKey,
                           Model model) {
        List<String> errors = new ArrayList<>();

        // Verifica cheia admin
        if (!ADMIN_KEY.equals(adminKey)) {
            errors.add("invalidkey");
        }

        // Verifica potrivirea parolelor
        if (!password.equals(confirmPassword)) {
            errors.add("passwordmismatch");
        }

        if (!errors.isEmpty()) {
            model.addAttribute("errors", errors);
            return "register";
        }

        try {
            // Previne SQL injection pentru username
            String escapedUsername = username.replace("'", "''");

            // Creeaza rol cu permisiuni de login
            String createUserSQL = String.format(
                    "CREATE ROLE %s WITH LOGIN PASSWORD '%s'",
                    escapedUsername,
                    password
            );

            jdbcTemplate.execute(createUserSQL);

            // Acorda permisiuni de conectare la baza de date
            String grantConnectSQL = String.format(
                    "GRANT CONNECT ON DATABASE \"proiectBD\" TO %s",
                    escapedUsername
            );

            jdbcTemplate.execute(grantConnectSQL);

            return "redirect:/login?registration=success";
        } catch (Exception e) {
            errors.add("failed");
            model.addAttribute("errors", errors);
            return "register";
        }
    }
}