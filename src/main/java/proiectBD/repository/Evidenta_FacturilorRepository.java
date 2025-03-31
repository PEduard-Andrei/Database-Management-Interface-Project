/** Clasa care reprezinta interfata pentru operatii cu evidenta facturilor,
 * @author Paunita Eduard-Andrei 332AA
 * @version 6 Ianuarie 2025
 */
package proiectBD.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import proiectBD.tables.Evidenta_Facturilor;

import java.util.List;

@Repository
public interface Evidenta_FacturilorRepository extends JpaRepository<Evidenta_Facturilor, Integer> {
    @Query("SELECT e FROM Evidenta_Facturilor e ORDER BY e.idFactura.idFactura ASC")
    List<Evidenta_Facturilor> findAll();
}