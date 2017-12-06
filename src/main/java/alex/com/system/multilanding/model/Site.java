package alex.com.system.multilanding.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "site")
public class Site {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "site_id")
    private Long id;
    @Column(nullable = false)
    private String title;
    @Column(nullable = false)
    private String url;
    private String token;
    @OneToMany(mappedBy = "site", cascade = CascadeType.ALL)
    private List<InstanceSite> instanceSiteList;
    @OneToMany(mappedBy = "site", cascade = CascadeType.ALL)
    private List<Element> elementList;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Site() {

    }

    public Site(String title, String url, String token) {
        this.title = title;
        this.url = url;
        this.token = token;
        this.instanceSiteList = new ArrayList<>();
        this.elementList = new ArrayList<>();
    }

    public List<InstanceSite> getInstanceSiteList() {
        return instanceSiteList;
    }

    public void setInstanceSiteList(List<InstanceSite> instanceSiteList) {
        this.instanceSiteList = instanceSiteList;
    }

    public void addSiteToList(InstanceSite instanceSite) {
        this.instanceSiteList.add(instanceSite);
    }

    public void addElementToList(Element element) {
        this.elementList.add(element);
    }
    public void deleteElement(Element element){this.elementList.remove(element);}

    public List<Element> getElementList() {
        return elementList;
    }

    public void setElementList(List<Element> elementList) {
        this.elementList = elementList;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}


