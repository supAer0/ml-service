package alex.com.system.multilanding.controller.RestController;

import alex.com.system.multilanding.model.*;
import alex.com.system.multilanding.repository.*;
import alex.com.system.multilanding.service.RandomString;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class ApiController {

    private final NishaRepository nishaRepository;
    private final SiteRepository siteRepository;
    private final InstanceSiteRepository instanceSiteRepository;
    private final ElementRepository elementRepository;
    private final ElementValueRepository elementValueRepository;

    public ApiController(NishaRepository nishaRepository, SiteRepository siteRepository, InstanceSiteRepository instanceSiteRepository, ElementRepository elementRepository, ElementValueRepository elementValueRepository) {
        this.nishaRepository = nishaRepository;
        this.siteRepository=siteRepository;
        this.instanceSiteRepository = instanceSiteRepository;
        this.elementRepository = elementRepository;
        this.elementValueRepository=elementValueRepository;
    }


    @PostMapping("/createNisha")
    public ResponseEntity<Nisha> createNisha(@RequestBody Nisha input){
        Nisha nisha = new Nisha(input.getTitle());
        nishaRepository.save(nisha);
        return new ResponseEntity<Nisha>(nisha, HttpStatus.OK);
    }

    // -------------------Retrieve All ---------------------------------------------

    @RequestMapping(value = "/niches", method = RequestMethod.GET)
    public ResponseEntity<List<Nisha>> listAllNiches() {
        List<Nisha> niches = (List<Nisha>) nishaRepository.findAll();
//        if (niches.isEmpty()) {
//            return new ResponseEntity(HttpStatus.NO_CONTENT);
//            // You many decide to return HttpStatus.NOT_FOUND
//        }
        return new ResponseEntity<List<Nisha>>(niches, HttpStatus.OK);
    }

    @RequestMapping(value = "/createSite", method = RequestMethod.POST)
    public ResponseEntity<Site> createSite(@RequestBody Site input){
        try{
        RandomString randomString = new RandomString();
        String token = randomString.nextString();
        Site site = new Site(input.getTitle(),input.getUrl(),token);
        siteRepository.save(site);
        return new ResponseEntity<>(site, HttpStatus.OK);}
        catch (Exception e ){
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/updateSite")
    public ResponseEntity updateSite(@RequestBody Site input){
        try {
            Site s = siteRepository.findOne(input.getId());
            s.setTitle(input.getTitle());
            s.setUrl(input.getUrl());
            siteRepository.save(s);
            return new ResponseEntity(HttpStatus.OK);
        }
        catch (Exception e ) {
            return ResponseEntity.notFound().build();
        }
    }

    // -------------------Retrieve All Sites---------------------------------------------

    @RequestMapping(value = "/sites", method = RequestMethod.GET)
    public ResponseEntity<List<Site>> listAllSites() {
        List<Site> sites = (List<Site>) siteRepository.findAll();
        return new ResponseEntity<List<Site>>(sites, HttpStatus.OK);
    }

    @RequestMapping(value = "/instances", method = RequestMethod.GET)
    public ResponseEntity<List<InstanceSite>> listAllInstanceForSite(@RequestParam("id") Long ident) {
        List<InstanceSite> res = new ArrayList<>();
        List<Site> sites = (List<Site>) siteRepository.findAll();
        for (Site s :sites) {
            if (s.getId() == ident)
                res = s.getInstanceSiteList();
        }
        return new ResponseEntity<List<InstanceSite>>(res, HttpStatus.OK);
    }


    @PostMapping("/createInstance")
    public ResponseEntity<InstanceSite> saveInstanceForSite(@RequestBody InstanceSite input, @RequestParam("id") Long ident){
        if (input.getId() != null){
            InstanceSite is = instanceSiteRepository.findOne(input.getId());
            is.setKeyName(input.getKeyName());
            is.setName(input.getName());
            is.setStatus(input.getStatus());
            is.setAbTest(input.getAbTest());
            instanceSiteRepository.save(is);
            return new ResponseEntity<>(is, HttpStatus.OK);
        }
        else{
        Date date = new Date();

        InstanceSite i = new InstanceSite(input.getName(),input.getKeyName(),input.getStatus(),date,input.getAbTest());
        try {
            Site s = siteRepository.findOne(ident);
            i.setSite(s);
        }
        catch (Exception e){
            i.setSite(null);
        }
            instanceSiteRepository.save(i);
            return new ResponseEntity<>(i, HttpStatus.OK);
        }
    }

    @RequestMapping(value = "/elements", method = RequestMethod.GET)
    public ResponseEntity<List<Element>> listAllElementsForSite(@RequestParam("id") Long ident) {
        Site s = siteRepository.findOne(ident);
        List<Element> res = s.getElementList();
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @PostMapping("/deleteElement")
    public ResponseEntity removeElementForSite(@RequestBody Element element, @RequestParam("id") Long ident) {
        Site s = siteRepository.findOne(ident);
        if(s == null) {
            return ResponseEntity.notFound().build();
        }
        //delete elements value
        try {
            Element element1 = elementRepository.findOne(element.getId());
            List<ElementValue> ev = element1.getElementValues();
            if (ev != null)
                elementValueRepository.delete(ev);
            elementRepository.delete(element1);
        }
        catch (Exception ignored){
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok().build();
    }

    @GetMapping("/deleteSite")
    public ResponseEntity removeSite(@RequestParam("id") Long idSite) {
        Site s = siteRepository.findOne(idSite);
        if(s == null) {
            return ResponseEntity.notFound().build();
        }
        try {
            siteRepository.delete(s);
        }
        catch (Exception ignored){
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok().build();
    }

    @PostMapping("/deleteInstance")
    public ResponseEntity removeInstance(@RequestBody InstanceSite instanceSite) {
        try {
            instanceSiteRepository.delete(instanceSite);
        }
        catch (Exception ignored){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().build();
    }

//    TODO: зачем-то этот метод возвращает значения
//    TODO: try catch for findOne()
    @PostMapping("/saveElements")
    public ResponseEntity<List<Element>> saveElementsForSite(@RequestBody List<Element> elements, @RequestParam("id") Long ident){
        List<Element> e = new ArrayList<>();
        Site s = siteRepository.findOne(ident);
        for (Element el : elements) {
            if(el.getId() != null){
                Element tmp = elementRepository.findOne(el.getId());
                tmp.setKey(el.getKey());
                tmp.setName(el.getName());
                elementRepository.save(tmp);
            } else{
                e.add(new Element(el.getName(),el.getKey()));
            }
        }

        if (s != null){
//            List<Element> all = s.getElementList();
//            List<Element> tmp = new ArrayList<>();
//            for (Element allEl:all) {
//                boolean delFlag = true;
//                for (Element el:elements) {
//                    if (allEl.getId() == el.getId()){
//                        delFlag = false;
//                        break;
//                    }
//                }
//                if(delFlag)
//                    tmp.add(allEl);
//            }
//
//            tmp.forEach(s::deleteElement);

            e.forEach(x ->
            {
                x.setSite(s);
                elementRepository.save(x);
            });
        }

        return new ResponseEntity<>(e, HttpStatus.OK);
    }


    @RequestMapping(value = "/elementsValue", method = RequestMethod.GET)
    public ResponseEntity<List<El>> elementsValues(@RequestParam("id_instance") Long ident) {
        InstanceSite is = instanceSiteRepository.findOne(ident);
        Site s = is.getSite();
        List<El> list = new ArrayList<>();
        List<Element> elements= s.getElementList();
        List<ElementValue> elementValues = is.getElementValues();
        for (Element e:elements) {
            El el = new El(e.getId(),e.getName(),e.getKey());
            for (ElementValue ev:elementValues) {
                if(e == ev.getElement()){
                    el.setValue(ev.getValue());
                    elementValues.remove(ev);
                    break;
                }
            }
            list.add(el);

        }
        return new ResponseEntity<>(list,HttpStatus.OK);

    }

    @PostMapping("/saveElementsWithValue")
    public ResponseEntity<List<ElementValue>> saveElementsWithValue(@RequestBody List<El> els, @RequestParam("id_instance") Long ident){
        InstanceSite is = instanceSiteRepository.findOne(ident);
        for (El e:els) {
            Element element = elementRepository.findOne(e.getId());
            ElementValue ev = new ElementValue();
            ev.setInstanceSite(is);
            ev.setElement(element);
            ev.setValue(e.getValue());
            elementValueRepository.save(ev);
        }


        return new ResponseEntity<>(elementValueRepository.findAll(), HttpStatus.OK);
    }

//    @GetMapping("getToken")
//    public ResponseEntity<String> getToken(@RequestParam("id_instance") Long ident){
//        try {
//            InstanceSite is = instanceSiteRepository.findOne(ident);
//            String token = is.getToken();
//            return new ResponseEntity<>(token, HttpStatus.OK);
//        }
//        catch (Exception e){
//            return ResponseEntity.notFound().build();
//        }
//    }

}
