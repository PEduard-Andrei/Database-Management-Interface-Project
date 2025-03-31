/** Clasa care se ocupa de serviciu pentru gestionarea comenzilor,
 * incluzand operatii de adaugare, stergere si obtinere a acestora.
 * @author Paunita Eduard-Andrei 332AA
 * @version 31 Decembrie 2024
 */
package proiectBD.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import proiectBD.repository.ComenziRepository;
import proiectBD.tables.Comenzi;

import java.util.List;

@Service
public class ComenziService {
    @Autowired
    private ComenziRepository comenziRepository;

    public List<Comenzi> getAllComenzi() {
        return comenziRepository.findAll();
    }

    public Comenzi getComenziById(int id) {
        return comenziRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Comanda nu a fost gasita cu id: " + id));
    }

    public Comenzi saveComenzi(Comenzi comenzi) {
        return comenziRepository.save(comenzi);
    }

    public void deleteComenzi(int id) {
        comenziRepository.deleteById(id);
    }
}