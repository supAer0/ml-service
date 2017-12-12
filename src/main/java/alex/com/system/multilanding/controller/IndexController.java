package alex.com.system.multilanding.controller;

import alex.com.system.multilanding.model.InstanceSite;
import alex.com.system.multilanding.model.Site;
import alex.com.system.multilanding.repository.InstanceSiteRepository;
import alex.com.system.multilanding.repository.SiteRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.view.RedirectView;

import java.util.List;

@Controller
public class IndexController {

    private final SiteRepository siteRepository;
    private final InstanceSiteRepository instanceSiteRepository;

    public IndexController(SiteRepository siteRepository, InstanceSiteRepository instanceSiteRepository){
        this.siteRepository = siteRepository;
        this.instanceSiteRepository = instanceSiteRepository;
    }

    @GetMapping("/")
    public String index() {
        return "index";
    }
    @GetMapping("/sites/{id}")
    public String site(@PathVariable("id") Long id){
        try{
            Site s = siteRepository.findOne(id);
            return "site";
        }
        catch (Exception e){
            return "index";
        }
    }
    @GetMapping("/sites/{id_site}/instances/{id_instance}")
    public String elements(@PathVariable("id_site") Long idSite, @PathVariable("id_instance") Long idInstance){
        try {
            Site s = siteRepository.findOne(idSite);
            InstanceSite is =instanceSiteRepository.findOne(idInstance);
            return "elements";
        }

        catch (Exception e){
            return "index";
        }
    }
    @GetMapping("/login")
    public String getLogin(@RequestParam(value = "error", required = false) String error, @RequestParam(value = "logout", required = false) String logout, Model model){
        model.addAttribute("error", error != null);
        model.addAttribute("logout", logout != null);
        return "login";
    }
}
