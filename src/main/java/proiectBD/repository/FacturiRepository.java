/** Clasa care reprezinta interfata pentru operatii cu facturi,
 * @author Paunita Eduard-Andrei 332AA
 * @version 6 Ianuarie 2025
 */
package proiectBD.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import proiectBD.tables.Facturi;

import java.util.List;

@Repository
public interface FacturiRepository extends JpaRepository<Facturi, Integer> {
    @Query("SELECT f FROM Facturi f ORDER BY f.idFactura ASC")
    List<Facturi> findAll();
}