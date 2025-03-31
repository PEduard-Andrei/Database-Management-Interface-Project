/** Clasa pentru controllerul tabelului de evidenta facturi (view/delete/add/edit)
 * @author Paunita Eduard-Andrei 332AA
 * @version 29 Decembrie 2024
 */

package proiectBD.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import proiectBD.service.Evidenta_FacturilorService;
import proiectBD.tables.Evidenta_Facturilor;

@Controller
@RequestMapping("/evidenta-facturilor")
public class Evidenta_FacturilorController {
    @Autowired
    private Evidenta_FacturilorService evidenta_facturilorService;

    @GetMapping("/view")
    public String viewEvidenta_Facturilor(Model model) {
        model.addAttribute("evidentaFacturilor", evidenta_facturilorService.getAllEvidenta_Facturilor());
        return "evidenta_facturilor/view";
    }

    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("evidenta", new Evidenta_Facturilor());
        model.addAttribute("facturi", evidenta_facturilorService.getAllFacturi());
        return "evidenta_facturilor/add";
    }

    @PostMapping("/add")
    public String addEvidenta_Facturilor(@ModelAttribute Evidenta_Facturilor evidenta) {
        evidenta_facturilorService.saveEvidenta_Facturilor(evidenta);
        return "redirect:/evidenta-facturilor/view";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable int id, Model model) {
        model.addAttribute("evidenta", evidenta_facturilorService.getEvidenta_FacturilorById(id));
        model.addAttribute("facturi", evidenta_facturilorService.getAllFacturi());
        return "evidenta_facturilor/edit";
    }

    @PostMapping("/edit/{id}")
    public String updateEvidenta_Facturilor(@PathVariable int id, @ModelAttribute Evidenta_Facturilor evidenta) {
        evidenta.setIdEvidenta(id);
        evidenta_facturilorService.saveEvidenta_Facturilor(evidenta);
        return "redirect:/evidenta-facturilor/view";
    }

    @GetMapping("/delete/{id}")
    @ResponseBody
    public ResponseEntity<Void> deleteEvidenta_Facturilor(@PathVariable int id) {
        evidenta_facturilorService.deleteEvidenta_Facturilor(id);
        return ResponseEntity.ok().build();
    }
}