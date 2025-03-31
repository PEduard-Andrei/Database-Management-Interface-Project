/** Clasa care reprezinta o anumita sectiune din dashboard, cu titlu si link-uri pentru vizualizare și adaugare.
 * @author Paunita Eduard-Andrei 332AA
 * @version 21 Decembrie 2024
 */
package proiectBD.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DashboardSection {
    private String title;
    private String viewAllUrl;
    private String addNewUrl;
}