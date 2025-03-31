/** Clasa care se ocupa de serviciu pentru gestionarea produselor,
 * incluzand operatii de adaugare, stergere si obtinere a acestora.
 * @author Paunita Eduard-Andrei 332AA
 * @version 29 Decembrie 2024
 */

package proiectBD.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import proiectBD.repository.ProduseRepository;
import proiectBD.repository.Categorii_ProduseRepository;
import proiectBD.tables.Produse;
import proiectBD.tables.Categorii_Produse;

import java.util.List;

@Service
public class ProduseService {
    @Autowired
    private ProduseRepository produseRepository;

    @Autowired
    private Categorii_ProduseRepository categorii_produseRepository;

    public List<Produse> getAllProduse() {
        return produseRepository.findAll();
    }

    public Produse getProduseById(int id) {
        return produseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Produsul nu a fost gasit cu id: " + id));
    }

    public Produse saveProduse(Produse produse) {
        return produseRepository.save(produse);
    }

    public void deleteProduse(int id) {
        produseRepository.deleteById(id);
    }

    public List<Categorii_Produse> getAllCategories() {
        return categorii_produseRepository.findAll();
    }
}