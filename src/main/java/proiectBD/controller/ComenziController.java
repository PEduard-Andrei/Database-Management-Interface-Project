/** Clasa pentru controllerul tabelului de comenzi (view/delete/add/edit)
 * @author Paunita Eduard-Andrei 332AA
 * @version 31 Decembrie 2024
 */

package proiectBD.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import proiectBD.service.*;
import proiectBD.tables.Comenzi;

import java.util.List;

@Controller
@RequestMapping("/comenzi")
public class ComenziController {
    @Autowired
    private ComenziService comenziService;

    @Autowired
    private FurnizoriService furnizoriService;

    @Autowired
    private CurierService curierService;

    @Autowired
    private AngajatiService angajatiService;

    @Autowired
    private LivrariService livrariService;

    @GetMapping("/view")
    public String viewComenzi(Model model) {
        List<Comenzi> comenzi = comenziService.getAllComenzi();
        model.addAttribute("comenzi", comenzi);
        return "comenzi/view";
    }

    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("comanda", new Comenzi());
        model.addAttribute("furnizori", furnizoriService.getAllFurnizori());
        model.addAttribute("curieri", curierService.getAllCurier());
        model.addAttribute("angajati", angajatiService.getAllAngajati());
        return "comenzi/add";
    }

    @PostMapping("/add")
    public String addComenzi(@ModelAttribute Comenzi comenzi) {
        comenziService.saveComenzi(comenzi);
        return "redirect:/comenzi/view";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable int id, Model model) {
        if (livrariService.isLivrata(id)) {
            return "redirect:/comenzi/view?error=delivered";
        }

        Comenzi comanda = comenziService.getComenziById(id);
        model.addAttribute("comanda", comanda);
        model.addAttribute("furnizori", furnizoriService.getAllFurnizori());
        model.addAttribute("curieri", curierService.getAllCurier());
        model.addAttribute("angajati", angajatiService.getAllAngajati());
        return "comenzi/edit";
    }

    @PostMapping("/edit/{id}")
    public String updateComenzi(@PathVariable int id, @ModelAttribute Comenzi comenzi) {
        if (livrariService.isLivrata(id)) {
            return "redirect:/comenzi/view?error=delivered";
        }

        comenzi.setIdComanda(id);
        comenziService.saveComenzi(comenzi);
        return "redirect:/comenzi/view";
    }

    @GetMapping("/delete/{id}")
    @ResponseBody
    public ResponseEntity<Void> deleteComenzi(@PathVariable int id) {
        if (livrariService.isLivrata(id)) {
            return ResponseEntity.badRequest().build();
        }

        comenziService.deleteComenzi(id);
        return ResponseEntity.ok().build();
    }
}