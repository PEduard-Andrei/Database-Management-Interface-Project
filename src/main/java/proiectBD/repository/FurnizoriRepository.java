/** Clasa care reprezinta interfata pentru operatii cu furnizori,
 * @author Paunita Eduard-Andrei 332AA
 * @version 6 Ianuarie 2025
 */

package proiectBD.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import proiectBD.tables.Furnizori;

@Repository
public interface FurnizoriRepository extends JpaRepository<Furnizori, Integer> {
    @Query("SELECT CASE WHEN COUNT(f) > 0 THEN true ELSE false END FROM Furnizori f WHERE LOWER(f.numeFurnizor) = LOWER(:numeFurnizor)")
    boolean existsByNumeFurnizorIgnoreCase(@Param("numeFurnizor") String numeFurnizor);

    @Query("SELECT CASE WHEN COUNT(f) > 0 THEN true ELSE false END FROM Furnizori f WHERE LOWER(f.numeFurnizor) = LOWER(:numeFurnizor) AND f.idFurnizor != :id")
    boolean existsByNumeFurnizorIgnoreCaseAndIdNot(@Param("numeFurnizor") String numeFurnizor, @Param("id") int id);
}