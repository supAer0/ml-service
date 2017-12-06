package alex.com.system.multilanding.controller.RestController;

import alex.com.system.multilanding.model.*;
import alex.com.system.multilanding.repository.*;
import com.sun.org.apache.regexp.internal.RE;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

@RestController
@RequestMapping("/openapi")
public class OpenApiController {

    private final NishaRepository nishaRepository;
    private final SiteRepository siteRepository;
    private final InstanceSiteRepository instanceSiteRepository;
    private final ElementRepository elementRepository;
    private final ElementValueRepository elementValueRepository;

    public OpenApiController(NishaRepository nishaRepository, SiteRepository siteRepository, InstanceSiteRepository instanceSiteRepository, ElementRepository elementRepository, ElementValueRepository elementValueRepository) {
        this.nishaRepository = nishaRepository;
        this.siteRepository=siteRepository;
        this.instanceSiteRepository = instanceSiteRepository;
        this.elementRepository = elementRepository;
        this.elementValueRepository=elementValueRepository;
    }

    @RequestMapping(value = "/getContent", method = RequestMethod.GET)
    public ResponseEntity<Map<String,String>> getContent(@RequestParam("token") String token, @RequestParam("ml_type") String mlType) {
        try {
            Site s = siteRepository.findByToken(token);
            List<InstanceSite> is = s.getInstanceSiteList();
            InstanceSite res = null;
            Map<String, String> results = new HashMap<>();
            for (InstanceSite e : is) {
                if (e.getKeyName().equals(mlType))
                    if (e.getStatus()){
                    res = e;
                    break;
                }
                else {
                        results.put("ml_type", "default");
                        return new ResponseEntity<>(results, HttpStatus.OK);
                    }
            }
            if (res == null)
                return ResponseEntity.notFound().build();
            Random random = new Random();
            boolean abFlag = random.nextBoolean();
            if(res.getAbTest() && abFlag){
                results.put("ml_type", "default");
                return new ResponseEntity<>(results, HttpStatus.OK);
            }
            else {
                List<ElementValue> elementValues = res.getElementValues();
                for (ElementValue ev : elementValues) {
                    Element e = ev.getElement();
                    results.put(e.getKey(), ev.getValue());
                }
                results.put("ml_type", res.getKeyName());
                return new ResponseEntity<>(results, HttpStatus.OK);
            }

        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

//    @RequestMapping(value = "/getContent", method = RequestMethod.GET)
//    public ResponseEntity<Map<String,String>> getContent(@RequestParam("site") String siteUrl, @RequestParam("utm") String utm) {
//
//            Site s = siteRepository.findByUrl(siteUrl);
//            if (s == null)
//                return ResponseEntity.notFound().build();
//            List<InstanceSite> is = s.getInstanceSiteList();
//            InstanceSite res = null;
//            for (InstanceSite e : is) {
//                if (e.getKeyName().equals(utm) && e.getStatus()){
//                    res = e;
//                    break;
//                }
//            }
//            if (res == null)
//                return ResponseEntity.notFound().build();
//            Map<String,String> results = new HashMap<>();
//            List<ElementValue> elementValues = res.getElementValues();
//            for (ElementValue ev:elementValues) {
//                Element e = ev.getElement();
//                results.put(e.getKey(),ev.getValue());
//            }
//            results.put("utm_source",res.getKeyName());
//            return new ResponseEntity<>(results,HttpStatus.OK);
//
//
//    }
}
