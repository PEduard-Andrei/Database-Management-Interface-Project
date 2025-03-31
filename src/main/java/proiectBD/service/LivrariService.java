/** Clasa care se ocupa de serviciu pentru gestionarea livrarilor,
 * incluzand operatii de adaugare, stergere si obtinere a acestora.
 * @author Paunita Eduard-Andrei 332AA
 * @version 6 Ianuarie 2025
 */

package proiectBD.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import proiectBD.repository.LivrariRepository;
import proiectBD.repository.ComenziRepository;
import proiectBD.tables.Livrari;
import proiectBD.tables.Comenzi;
import proiectBD.tables.Detalii_Comenzi.Detalii_Comenzi;
import proiectBD.tables.Produse;
import java.util.List;

@Service
public class LivrariService {
    @Autowired
    private LivrariRepository livrariRepository;

    @Autowired
    private ComenziRepository comenziRepository;

    @Autowired
    private Detalii_ComenziService detalii_comenziService;

    @Autowired
    private ProduseService produseService;

    public List<Livrari> getAllLivrari() {
        return livrariRepository.findAll();
    }

    public Livrari getLivrariById(int id) {
        return livrariRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Livrarea nu a fost gasita cu id: " + id));
    }

    public Livrari getLivrareByComandaId(int comandaId) {
        return livrariRepository.findByIdComandaIdComanda(comandaId);
    }

    @Transactional
    public ResponseEntity<?> saveLivrari(Livrari livrare) {
        try {
            // Verifica daca exista o livrare deja inregistrata
            if (livrare.getIdLivrare() == 0) {
                Livrari existingLivrare = getLivrareByComandaId(livrare.getIdComanda().getIdComanda());
                if (existingLivrare != null) {
                    return ResponseEntity.badRequest().body("O livrare este deja înregistrată pentru această comandă!");
                }
            }

            if (isStatusChangedToLivrata(livrare)) {
                processDeliveredOrder(livrare.getIdComanda().getIdComanda());
            }
            livrariRepository.save(livrare);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    public void deleteLivrari(int id) {
        livrariRepository.deleteById(id);
    }

    public List<Comenzi> getAllComenzi() {
        return comenziRepository.findAll();
    }

    private boolean isStatusChangedToLivrata(Livrari newLivrare) {
        Livrari existingLivrare = newLivrare.getIdLivrare() != 0 ?
                livrariRepository.findById(newLivrare.getIdLivrare()).orElse(null) : null;

        return existingLivrare != null &&
                !existingLivrare.getStatusLivrare().equals("Livrata") &&
                newLivrare.getStatusLivrare().equals("Livrata");
    }

    @Transactional
    private void processDeliveredOrder(int comandaId) {
        List<Detalii_Comenzi> detaliiList = detalii_comenziService.getDetaliiByComandaId(comandaId);
        for(Detalii_Comenzi detalii : detaliiList) {
            Produse produs = detalii.getProdus();
            int newStock = produs.getStoc() + detalii.getCantitate();
            produs.setStoc(newStock);
            produseService.saveProduse(produs);
        }
    }

    public boolean existsLivrareForComandaExcludingCurrent(int comandaId, int currentLivrareId) {
        Livrari existingLivrare = getLivrareByComandaId(comandaId);
        return existingLivrare != null && existingLivrare.getIdLivrare() != currentLivrareId;
    }

    public boolean isLivrata(int comandaId) {
        Livrari livrare = livrariRepository.findByIdComandaIdComanda(comandaId);
        return livrare != null && "Livrata".equals(livrare.getStatusLivrare());
    }
}