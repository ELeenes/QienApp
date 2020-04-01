package QienApp.qien.rest;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import QienApp.qien.controller.MedewerkerRepository;
import QienApp.qien.controller.urenform.GewerkteDagService;
import QienApp.qien.controller.urenform.UrenDeclaratieRepository;
import QienApp.qien.controller.urenform.UrenDeclaratieService;
import QienApp.qien.domein.Medewerker;
import QienApp.qien.domein.urenform.GewerkteDag;
import QienApp.qien.domein.urenform.Urendeclaratie;


@RestController
@RequestMapping("/api/urendeclaraties")
public class UrendeclaratieEndpoints {

	//ik ben vrienden met deze lui:
	@Autowired
	UrenDeclaratieService urenDeclaratieService;
	@Autowired
	GewerkteDagService dagService;
	@Autowired
	MedewerkerRepository medewerkerRepository;
	@Autowired
	UrenDeclaratieRepository urenDeclaratieRepository;
	
	//TODO
	@PostMapping("/test/{urendeclaratieid}")
	public void doeHet(@PathVariable(value = "urendeclaratieid") long uId, @RequestBody Urendeclaratie u) {	
	//	urenDeclaratieService.updateUrendeclaratie(u);
	}
	
	/**
	 * UPDATE EXISTING or CREATE NEW URENDECLARATIE
	 * 1.als er een ID meekomt, 2. als er geen ID meekomt
	 * @param Urendeclaratie object
	 * @return
	 */
	@PutMapping("/")
	public Urendeclaratie updateUren(@RequestBody Urendeclaratie u) {
		return urenDeclaratieService.laszloMethode(u);
	}

	/**
	 * ENDPOINT 1:	maak leeg urendeclaratieformulier
	 * 
	 * TODO iets met ENUM maandnaam en maandnummers
	 * @return		leeg urenform
	 */
	@PostMapping("/{maandnaam}/{maandnr}")
	public Urendeclaratie maakLegeUrendeclaratie1(@PathVariable(value = "maandnaam") String maandNaam, 
			@PathVariable(value = "maandnr") int maandNr) 
	{
		return urenDeclaratieService.maakUrendeclaratieForm(maandNaam, maandNr);
	}
	
	/**
	 * ENDPOINT 2:	koppel een leeg form aan alle medewerkers met form ID
	 * 
	 * @param formId
	 * @return statusbericht: gekoppeld
	 */
	@PostMapping("/koppel/{formId}")
	public String koppelZeAllemaal(@PathVariable(value = "formId") long formId)	
	{
	return urenDeclaratieService.koppelAanAllen(urenDeclaratieService.getUrendeclaraties(formId));
	}
	
	/**
	 * ENDPOINT 2.5:	koppel een leeg form aan alle medewerkers met een FORM object
	 * 
	 * @param urendeclaratie object
	 * @return statusbericht: gekoppeld
	 */
	@PostMapping("/koppelallen/{formId}")
	public String koppelZeAllemaal2(@RequestBody Urendeclaratie u)	
	{
	return urenDeclaratieService.koppelAanAllen(u);
	}
	
	/**
	 * ENDPOINT 2.8:	koppel een nieuw gegenereerd leeg form aan alle medewerkers
	 * 
	 * @param urendeclaratie object
	 * @return statusbericht: gekoppeld
	 */
	@PostMapping("/doealles/{maandnaam}/{maandnr}")
	public String maakLegeUrendeclaratieEnKoppelAanAllen(@PathVariable(value = "maandnaam") String maandNaam, 
			@PathVariable(value = "maandnr") int maandNr) 
	{
	return urenDeclaratieService.koppelAanAllen(urenDeclaratieService.maakUrendeclaratieForm(maandNaam, maandNr));
	}

	
	/**
	 * ENDPOINT 3:	koppel een form aan een specifieke medewerker
	 * 
	 * @param formId
	 * @param medewerkerId
	 * @return gekoppeld formulier
	 */
	@PostMapping("/{formId}/{medewerkerId}")
	public Urendeclaratie koppelZe(@PathVariable(value = "formId") long formId, 
			@PathVariable(value = "medewerkerId") long medewerkerId)
	{
	return urenDeclaratieService.koppelFormAanMedewerker(formId, medewerkerId);
	}
	
	//urenDeclaratieService.updateUrendeclaratie(udId, urendDeclaratieDetails);
	
	
	/**
	 * ENDPOINT 4: update urendeclaratie van een persoon
	 */
//TODO




	@GetMapping("/{id}")	
	public Urendeclaratie getUrendeclaratie(@PathVariable(value = "id") String idUrendeclaratie) {
		System.out.println("getUrendeclaratie");
		return urenDeclaratieService.getUrendeclaraties(Long.parseLong(idUrendeclaratie));
	}
/**
 * LEGE URENFORM, ZONDER ID
 * 
 * @param maandNaam
 * @param maandNr
 */
	@PostMapping("/urendeclaratie/{maandnaam}/{maandnr}")
	public void maakLegeUrendeclaratie(@PathVariable(value = "maandnaam") String maandNaam, 
			@PathVariable(value = "maandnr") int maandNr) {

		urenDeclaratieService.maakUrendeclaratieForm(maandNaam, maandNr);
	}

	/**
	 * GET ALL URENDECLARATIES
	 * @return iterable met alle urendeclaraties
	 */
	@GetMapping("/urendeclaraties")
	public Iterable<Urendeclaratie> getUrendeclaraties() {
		return urenDeclaratieService.getAllUrendeclaraties();
	}

	/**
	 * GewerkteDag ENDPOINTS
	 * UPDATE
	 * 
	 */
	@PutMapping("/gewerktedag/{dagId}")
	public GewerkteDag updatePersoonDrDag(@PathVariable(value = "dagId") String dagId,
			@RequestBody GewerkteDag dagDetails) 
	{
		return dagService.updateDag(Long.parseLong(dagId), dagDetails);
	}
}