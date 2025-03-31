/** Clasa pentru controllerul tabelului de produse (view/delete/add/edit)
 * @author Paunita Eduard-Andrei 332AA
 * @version 29 Decembrie 2024
 */
package proiectBD.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import proiectBD.service.ProduseService;
import proiectBD.tables.Produse;

@Controller
@RequestMapping("/produse")
public class ProduseController {
    @Autowired
    private ProduseService produseService;

    @GetMapping("/view")
    public String viewProduse(Model model) {
        model.addAttribute("produse", produseService.getAllProduse());
        model.addAttribute("categorii", produseService.getAllCategories());
        return "produse/view";
    }

    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("produs", new Produse());
        model.addAttribute("categorii", produseService.getAllCategories());
        return "produse/add";
    }

    @PostMapping("/add")
    public String addProdus(@ModelAttribute Produse produs) {
        produseService.saveProduse(produs);
        return "redirect:/produse/view";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable int id, Model model) {
        model.addAttribute("produs", produseService.getProduseById(id));
        model.addAttribute("categorii", produseService.getAllCategories());
        return "produse/edit";
    }

    @PostMapping("/edit/{id}")
    public String updateProdus(@PathVariable int id, @ModelAttribute Produse produs) {
        produs.setIdProdus(id);
        produseService.saveProduse(produs);
        return "redirect:/produse/view";
    }

    @GetMapping("/delete/{id}")
    @ResponseBody
    public ResponseEntity<Void> deleteProdus(@PathVariable int id) {
        produseService.deleteProduse(id);
        return ResponseEntity.ok().build();
    }
}