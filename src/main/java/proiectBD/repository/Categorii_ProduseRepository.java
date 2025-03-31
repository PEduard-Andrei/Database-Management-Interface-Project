/** Clasa care reprezinta interfata pentru operatii cu categoriile de produse,
 * @author Paunita Eduard-Andrei 332AA
 * @version 6 Ianuarie 2024
 */
package proiectBD.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import proiectBD.tables.Categorii_Produse;

@Repository
public interface Categorii_ProduseRepository extends JpaRepository<Categorii_Produse, Integer> {

    @Query("SELECT CASE WHEN COUNT(c) > 0 THEN true ELSE false END FROM Categorii_Produse c WHERE LOWER(c.denumire) = LOWER(:denumire)")
    boolean existsByDenumireIgnoreCase(@Param("denumire") String denumire);

    @Query("SELECT CASE WHEN COUNT(c) > 0 THEN true ELSE false END FROM Categorii_Produse c WHERE LOWER(c.denumire) = LOWER(:denumire) AND c.id_categorie != :id")
    boolean existsByDenumireIgnoreCaseAndIdNot(@Param("denumire") String denumire, @Param("id") int id);
}