/** Clasa pentru controllerul tabelului de detalii comenzi (view/delete/add/edit)
 * @author Paunita Eduard-Andrei 332AA
 * @version 6 Ianuarie 2025
 */
package proiectBD.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import proiectBD.service.Detalii_ComenziService;
import proiectBD.service.ComenziService;
import proiectBD.service.ProduseService;
import proiectBD.service.LivrariService;
import proiectBD.tables.Detalii_Comenzi.Detalii_Comenzi;
import proiectBD.tables.Detalii_Comenzi.Detalii_ComenziId;

import java.util.List;

@Controller
@RequestMapping("/detalii_comenzi")
public class Detalii_ComenziController {
    @Autowired
    private Detalii_ComenziService detalii_comenziService;

    @Autowired
    private ComenziService comenziService;

    @Autowired
    private ProduseService produseService;

    @Autowired
    private LivrariService livrariService;

    @GetMapping("/view")
    public String viewDetalii_Comenzi(Model model) {
        List<Detalii_Comenzi> detaliiComenzi = detalii_comenziService.getAllDetalii_Comenzi();
        model.addAttribute("detaliiComenzi", detaliiComenzi);
        model.addAttribute("livrariService", livrariService);
        return "detalii_comenzi/view";
    }

    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("detaliiComanda", new Detalii_Comenzi());
        model.addAttribute("comenzi", comenziService.getAllComenzi());
        model.addAttribute("produse", produseService.getAllProduse());
        return "detalii_comenzi/add";
    }

    @PostMapping("/add")
    public String addDetalii_Comenzi(@RequestParam("comanda") Integer idComanda,
                                     @RequestParam("produs") Integer idProdus,
                                     @RequestParam("cantitate") Integer cantitate) {
        if (livrariService.isLivrata(idComanda)) {
            return "redirect:/detalii_comenzi/view?error=delivered";
        }

        Detalii_Comenzi detaliiComenzi = new Detalii_Comenzi();
        Detalii_ComenziId id = new Detalii_ComenziId();
        id.setIdComanda(idComanda);
        id.setIdProdus(idProdus);
        detaliiComenzi.setId(id);
        detaliiComenzi.setComanda(comenziService.getComenziById(idComanda));
        detaliiComenzi.setProdus(produseService.getProduseById(idProdus));
        detaliiComenzi.setCantitate(cantitate);

        detalii_comenziService.saveDetalii_Comenzi(detaliiComenzi);
        return "redirect:/detalii_comenzi/view";
    }

    @GetMapping("/edit/{idComanda}/{idProdus}")
    public String showEditForm(@PathVariable Integer idComanda,
                               @PathVariable Integer idProdus,
                               Model model) {

        Detalii_ComenziId id = new Detalii_ComenziId(idComanda, idProdus);
        model.addAttribute("detaliiComanda", detalii_comenziService.getDetalii_ComenziById(id));
        model.addAttribute("comenzi", detalii_comenziService.getAllComenzi());
        model.addAttribute("produse", detalii_comenziService.getAllProduse());
        return "detalii_comenzi/edit";
    }

    @PostMapping("/edit/{idComanda}/{idProdus}")
    public String updateDetalii_Comenzi(@PathVariable Integer idComanda,
                                        @PathVariable Integer idProdus,
                                        @RequestParam Integer cantitate) {

        Detalii_ComenziId id = new Detalii_ComenziId(idComanda, idProdus);
        Detalii_Comenzi detaliiComenzi = detalii_comenziService.getDetalii_ComenziById(id);
        detaliiComenzi.setCantitate(cantitate);
        detalii_comenziService.saveDetalii_Comenzi(detaliiComenzi);
        return "redirect:/detalii_comenzi/view";
    }

    @GetMapping("/delete/{idComanda}/{idProdus}")
    @ResponseBody
    public ResponseEntity<Void> deleteDetalii_Comenzi(@PathVariable Integer idComanda,
                                                      @PathVariable Integer idProdus) {
        if (livrariService.isLivrata(idComanda)) {
            return ResponseEntity.badRequest().build();
        }

        Detalii_ComenziId id = new Detalii_ComenziId(idComanda, idProdus);
        detalii_comenziService.deleteDetalii_Comenzi(id);
        return ResponseEntity.ok().build();
    }
}