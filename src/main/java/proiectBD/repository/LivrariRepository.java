/** Clasa care reprezinta interfata pentru operatii cu livrari,
 * @author Paunita Eduard-Andrei 332AA
 * @version 6 Ianuarie 2025
 */
package proiectBD.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import proiectBD.tables.Livrari;

import java.util.List;

@Repository
public interface LivrariRepository extends JpaRepository<Livrari, Integer> {
    @Query("SELECT l FROM Livrari l ORDER BY l.idComanda.idComanda ASC")
    List<Livrari> findAll();

    @Query("SELECT l FROM Livrari l WHERE l.idComanda.idComanda = :comandaId")
    Livrari findByIdComandaIdComanda(@Param("comandaId") int comandaId);
}