/** Clasa pentru controllerul tabelului de furnizori (view/delete/add/edit)
 * @author Paunita Eduard-Andrei 332AA
 * @version 6 Ianuarie 2025
 */
package proiectBD.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import proiectBD.service.FurnizoriService;
import proiectBD.tables.Furnizori;

@Controller
@RequestMapping("/furnizori")
public class FurnizoriController {
    @Autowired
    private FurnizoriService furnizoriService;

    @GetMapping("/view")
    public String viewFurnizori(Model model) {
        model.addAttribute("furnizori", furnizoriService.getAllFurnizori());
        return "furnizori/view";
    }

    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("furnizor", new Furnizori());
        return "furnizori/add";
    }

    private boolean isValidEmail(String email) {
        // Restrictii pentru introducerea unei adrese de e-mail valide
        String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";
        return email != null && email.matches(emailRegex) && !email.substring(email.indexOf('@')).contains("..");
    }

    @PostMapping("/add")
    public String addFurnizor(@ModelAttribute Furnizori furnizor, Model model) {
        try {
            // Validare numar telefon
            String phone = furnizor.getTelefon();
            if (!isValidPhoneNumber(phone)) {
                throw new RuntimeException("Număr de telefon invalid! Numărul trebuie să conțină doar cifre și eventual codul țării (ex: +40722123456 sau 0722123456)");
            }

            // Validare format email
            if (!isValidEmail(furnizor.getEmail())) {
                throw new RuntimeException("Adresa de email invalidă! Trebuie să fie de forma: nume@domeniu.extensie");
            }

            furnizoriService.saveFurnizor(furnizor);
            return "redirect:/furnizori/view";
        } catch (RuntimeException e) {
            model.addAttribute("error", e.getMessage());
            model.addAttribute("furnizor", furnizor);
            return "furnizori/add";
        }
    }

    private boolean isValidPhoneNumber(String phone) {
        // Formate numere telefon: +40123456789 sau 0123456789
        return phone != null &&
                (phone.matches("\\+40\\d{9}") || // Format +40123456789
                        phone.matches("0\\d{9}")); // Format 0123456789
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable int id, Model model) {
        model.addAttribute("furnizor", furnizoriService.getFurnizoriById(id));
        return "furnizori/edit";
    }

    @PostMapping("/edit/{id}")
    public String updateFurnizor(@PathVariable int id, @ModelAttribute Furnizori furnizor, Model model) {
        try {
            // Validare numa telefon
            String phone = furnizor.getTelefon();
            if (!isValidPhoneNumber(phone)) {
                throw new RuntimeException("Număr de telefon invalid! Numărul trebuie să conțină doar cifre și eventual codul țării (ex: +40722123456 sau 0722123456)");
            }

            // Validare format email
            if (!isValidEmail(furnizor.getEmail())) {
                throw new RuntimeException("Adresa de email invalidă! Trebuie să fie de forma: nume@domeniu.extensie");
            }

            furnizor.setIdFurnizor(id);
            furnizoriService.saveFurnizor(furnizor);
            return "redirect:/furnizori/view";
        } catch (RuntimeException e) {
            model.addAttribute("error", e.getMessage());
            model.addAttribute("furnizor", furnizor);
            return "furnizori/edit";
        }
    }

    @GetMapping("/delete/{id}")
    @ResponseBody
    public ResponseEntity<Void> deleteFurnizor(@PathVariable int id) {
        furnizoriService.deleteFurnizor(id);
        return ResponseEntity.ok().build();
    }
}