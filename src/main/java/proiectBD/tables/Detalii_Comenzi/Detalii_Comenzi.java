/** Clasa care contine detaliile tabelului de detalii comenzi din baza de date
 * @author Paunita Eduard-Andrei 332AA
 * @version 21 Decembrie 2024
 */
package proiectBD.tables.Detalii_Comenzi;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import proiectBD.tables.Comenzi;
import proiectBD.tables.Produse;

@Entity
@Table(name = "Detalii_Comenzi")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Detalii_Comenzi {
    @EmbeddedId
    private Detalii_ComenziId id;

    @MapsId("idComanda")
    @ManyToOne
    @JoinColumn(name = "ID_Comanda")
    private Comenzi comanda;

    @MapsId("idProdus")
    @ManyToOne
    @JoinColumn(name = "ID_Produs")
    private Produse produs;

    @Column(name = "Cantitate")
    private Integer cantitate;
}