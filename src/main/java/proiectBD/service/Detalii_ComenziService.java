/** Clasa care se ocupa de serviciu pentru gestionarea detaliior comenzilor,
 * incluzand operatii de adaugare, stergere si obtinere a acestora.
 * @author Paunita Eduard-Andrei 332AA
 * @version 6 Ianuarie 2025
 */

package proiectBD.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import proiectBD.repository.Detalii_ComenziRepository;
import proiectBD.repository.ComenziRepository;
import proiectBD.repository.ProduseRepository;
import proiectBD.tables.Detalii_Comenzi.Detalii_Comenzi;
import proiectBD.tables.Detalii_Comenzi.Detalii_ComenziId;
import proiectBD.tables.Comenzi;
import proiectBD.tables.Produse;

import java.util.List;

@Service
public class Detalii_ComenziService {
    @Autowired
    private Detalii_ComenziRepository detalii_comenziRepository;

    @Autowired
    private ComenziRepository comenziRepository;

    @Autowired
    private ProduseRepository produseRepository;

    @Autowired
    private ProduseService produseService;

    public List<Detalii_Comenzi> getAllDetalii_Comenzi() {
        return detalii_comenziRepository.findAll();
    }

    public Detalii_Comenzi getDetalii_ComenziById(Detalii_ComenziId id) {
        return detalii_comenziRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Detaliile comenzii nu au fost gasite"));
    }

    public List<Detalii_Comenzi> getDetaliiByComandaId(int comandaId) {
        return detalii_comenziRepository.findByComandaIdComanda(comandaId);
    }

    @Transactional
    public Detalii_Comenzi saveDetalii_Comenzi(Detalii_Comenzi detalii) {
        return detalii_comenziRepository.save(detalii);
    }

    @Transactional
    public void deleteDetalii_Comenzi(Detalii_ComenziId id) {
        try {
            detalii_comenziRepository.deleteById(id);
        } catch (Exception e) {
            System.err.println("Eroare stergere Detalii_Comenzi: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public List<Comenzi> getAllComenzi() {
        return comenziRepository.findAll();
    }

    public List<Produse> getAllProduse() {
        return produseRepository.findAll();
    }
}