/** Clasa care reprezinta interfata pentru operatii cu curier,
 * @author Paunita Eduard-Andrei 332AA
 * @version 22 Decembrie 2024
 */
package proiectBD.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import proiectBD.tables.Curier;

@Repository
public interface CurierRepository extends JpaRepository<Curier, Integer> {
}