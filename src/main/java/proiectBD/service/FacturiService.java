/** Clasa care se ocupa de serviciu pentru gestionarea facturilor,
 * incluzand operatii de adaugare, stergere si obtinere a acestora.
 * @author Paunita Eduard-Andrei 332AA
 * @version 6 Ianuarie, 2025
 */

package proiectBD.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import proiectBD.exception.DeleteConstraintException;
import proiectBD.repository.FacturiRepository;
import proiectBD.repository.ComenziRepository;
import proiectBD.repository.Detalii_ComenziRepository;
import proiectBD.tables.Facturi;
import proiectBD.tables.Comenzi;
import proiectBD.tables.Detalii_Comenzi.Detalii_Comenzi;

import java.math.BigDecimal;
import java.util.List;

@Service
public class FacturiService {
    @Autowired
    private FacturiRepository facturiRepository;

    @Autowired
    private ComenziRepository comenziRepository;

    @Autowired
    private Detalii_ComenziRepository detalii_comenziRepository;

    public List<Facturi> getAllFacturi() {
        return facturiRepository.findAll();
    }

    public Facturi getFacturiById(int id) {
        return facturiRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Factura nu a fost gasita cu id: " + id));
    }

    public Facturi saveFacturi(Facturi factura) {
        // Calculare pret
        BigDecimal total = calculateTotalForComanda(factura.getIdComanda().getIdComanda());
        factura.setSuma(total);
        return facturiRepository.save(factura);
    }

    public void deleteFacturi(int id) {
        try {
            facturiRepository.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            throw new DeleteConstraintException("Factura nu poate fi ștearsă deoarece este folosită în alte înregistrări (Evidenta Facturilor).");
        }
    }

    public List<Comenzi> getAllComenzi() {
        return comenziRepository.findAll();
    }

    // Calculare specifica pentru coamnda
    public BigDecimal calculateTotalForComanda(int comandaId) {
        List<Detalii_Comenzi> detalii = detalii_comenziRepository.findByComandaIdComanda(comandaId);
        return detalii.stream()
                .map(d -> d.getProdus().getPret().multiply(BigDecimal.valueOf(d.getCantitate())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public boolean existsFacturaForComanda(int comandaId) {
        return facturiRepository.findAll().stream()
                .anyMatch(f -> f.getIdComanda().getIdComanda() == comandaId);
    }

    public boolean isComandaSumZero(int comandaId) {
        BigDecimal total = calculateTotalForComanda(comandaId);
        return total.compareTo(BigDecimal.ZERO) == 0;
    }

    public boolean existsFacturaForComandaExcludingCurrent(int comandaId, int currentFacturaId) {
        return facturiRepository.findAll().stream()
                .anyMatch(f -> f.getIdComanda().getIdComanda() == comandaId
                        && f.getIdFactura() != currentFacturaId);
    }
}