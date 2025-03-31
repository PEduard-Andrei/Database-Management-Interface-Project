/** Clasa care reprezinta interfata pentru operatii cu detalii comenzi,
 * @author Paunita Eduard-Andrei 332AA
 * @version 6 Ianuarie 2025
 */
package proiectBD.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import proiectBD.tables.Detalii_Comenzi.Detalii_Comenzi;
import proiectBD.tables.Detalii_Comenzi.Detalii_ComenziId;

import java.util.List;

@Repository
public interface Detalii_ComenziRepository extends JpaRepository<Detalii_Comenzi, Detalii_ComenziId> {
    @Query("SELECT dc FROM Detalii_Comenzi dc ORDER BY dc.comanda.idComanda ASC")
    List<Detalii_Comenzi> findAll();

    @Query("SELECT dc FROM Detalii_Comenzi dc WHERE dc.comanda.idComanda = :comandaId ORDER BY dc.id.idProdus ASC")
    List<Detalii_Comenzi> findByComandaIdComanda(@Param("comandaId") int comandaId);
}