/** Clasa pentru controllerul tabelului angajati (view/delete/add/edit)
 * @author Paunita Eduard-Andrei 332AA
 * @version 6 Ianuarie 2025
 */
package proiectBD.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import proiectBD.service.AngajatiService;
import proiectBD.tables.Angajati;

@Controller
@RequestMapping("/angajati")
public class AngajatiController {
    @Autowired
    private AngajatiService angajatiService;

    @GetMapping("/view")
    public String viewAngajati(Model model) {
        model.addAttribute("angajati", angajatiService.getAllAngajati());
        return "angajati/view";
    }

    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("angajat", new Angajati());
        return "angajati/add";
    }

    @PostMapping("/add")
    public String addAngajat(@ModelAttribute Angajati angajat) {
        angajatiService.saveAngajat(angajat);
        return "redirect:/angajati/view";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable int id, Model model) {
        model.addAttribute("angajat", angajatiService.getAngajatById(id));
        return "angajati/edit";
    }

    @PostMapping("/edit/{id}")
    public String updateAngajat(@PathVariable int id, @ModelAttribute Angajati angajat) {
        angajat.setIdAngajat(id);
        angajatiService.saveAngajat(angajat);
        return "redirect:/angajati/view";
    }

    @GetMapping("/delete/{id}")
    @ResponseBody
    public ResponseEntity<Void> deleteAngajat(@PathVariable int id) {
        angajatiService.deleteAngajat(id);
        return ResponseEntity.ok().build();
    }
}