/** Clasa care se ocupa de serviciu pentru gestionarea evidentei facturilor,
 * incluzand operatii de adaugare, stergere si obtinere a acestora.
 * @author Paunita Eduard-Andrei 332AA
 * @version 29 Decembrie 2024
 */
package proiectBD.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import proiectBD.repository.Evidenta_FacturilorRepository;
import proiectBD.repository.FacturiRepository;
import proiectBD.tables.Evidenta_Facturilor;
import proiectBD.tables.Facturi;

import java.util.List;

@Service
public class Evidenta_FacturilorService {
    @Autowired
    private Evidenta_FacturilorRepository evidenta_facturilorRepository;

    @Autowired
    private FacturiRepository facturiRepository;

    public List<Evidenta_Facturilor> getAllEvidenta_Facturilor() {
        return evidenta_facturilorRepository.findAll();
    }

    public Evidenta_Facturilor getEvidenta_FacturilorById(int id) {
        return evidenta_facturilorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Evidenta facturii nu a fost gasita cu id: " + id));
    }

    public Evidenta_Facturilor saveEvidenta_Facturilor(Evidenta_Facturilor evidenta) {
        return evidenta_facturilorRepository.save(evidenta);
    }

    public void deleteEvidenta_Facturilor(int id) {
        evidenta_facturilorRepository.deleteById(id);
    }

    public List<Facturi> getAllFacturi() {
        return facturiRepository.findAll();
    }
}