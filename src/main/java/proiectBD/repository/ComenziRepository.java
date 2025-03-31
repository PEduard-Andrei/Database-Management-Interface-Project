/** Clasa care reprezinta interfata pentru operatii cu comenzi,
 * @author Paunita Eduard-Andrei 332AA
 * @version 6 Ianuare 2025
 */
package proiectBD.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import proiectBD.tables.Comenzi;

import java.util.List;

@Repository
public interface ComenziRepository extends JpaRepository<Comenzi, Integer> {
    @Query("SELECT c FROM Comenzi c ORDER BY c.idComanda ASC")
    List<Comenzi> findAll();
}