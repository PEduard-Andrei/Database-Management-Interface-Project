/** Clasa pentru controllerul tabelului categoriilor de produse (view/delete/add/edit)
 * @author Paunita Eduard-Andrei 332AA
 * @version 6 Ianuarie 2025
 */
package proiectBD.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import proiectBD.service.Categorii_ProduseService;
import proiectBD.tables.Categorii_Produse;

@Controller
@RequestMapping("/categorii_produse")
public class Categorii_ProduseController {
    @Autowired
    private Categorii_ProduseService categorii_produseService;

    @GetMapping("/view")
    public String viewCategorii_Produse(Model model) {
        model.addAttribute("categorii_produse", categorii_produseService.getAllCategorii_Produse());
        return "categorii_produse/view";
    }

    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("categorieProduse", new Categorii_Produse());
        return "categorii_produse/add";
    }

    @PostMapping("/add")
    public String addCategorii_Produse(@ModelAttribute("categorieProduse") Categorii_Produse categorii_produse,
                                       Model model) {
        try {
            categorii_produseService.saveCategorii_Produse(categorii_produse);
            return "redirect:/categorii_produse/view";
        } catch (RuntimeException e) {
            model.addAttribute("error", e.getMessage());
            return "categorii_produse/add";
        }
    }
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable int id, Model model) {
        model.addAttribute("categorii_produse", categorii_produseService.getCategorii_ProduseById(id));
        return "categorii_produse/edit";
    }

    @PostMapping("/edit/{id}")
    public String updateCategorii_Produse(@PathVariable int id,
                                          @ModelAttribute Categorii_Produse categorii_produse,
                                          Model model) {
        try {
            categorii_produseService.updateCategorii_Produse(id, categorii_produse);
            return "redirect:/categorii_produse/view";
        } catch (RuntimeException e) {
            model.addAttribute("error", e.getMessage());
            model.addAttribute("categorii_produse", categorii_produse);
            return "categorii_produse/edit";
        }
    }

    @GetMapping("/delete/{id}")
    @ResponseBody
    public ResponseEntity<Void> deleteCategorii_Produse(@PathVariable int id) {
        categorii_produseService.deleteCategorii_Produse(id);
        return ResponseEntity.ok().build();
    }
}