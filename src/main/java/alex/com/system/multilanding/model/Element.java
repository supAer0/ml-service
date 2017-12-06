package alex.com.system.multilanding.model;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "element")
public class Element {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "element_id")
    private Long id;
    @Column(nullable=false)
    private String name;
    @Column(nullable=false, name = "keyName")
    private String key;
    @ManyToOne
    @JoinColumn (name="site_id")
    @JsonBackReference
    private Site site;

    @OneToMany(mappedBy = "element", cascade = CascadeType.ALL)
    private List<ElementValue> elementValues;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Element(String name, String key) {
        this.name = name;
        this.key = key;
        this.elementValues=new ArrayList<>();
    }
    public Element() {
    }

    public Site getSite() {
        return site;
    }

    public void setSite(Site site) {
        this.site = site;
    }

    public List<ElementValue> getElementValues() {
        return elementValues;
    }

    public void setElementValues(List<ElementValue> elementValues) {
        this.elementValues = elementValues;
    }
    public void addElementValue(ElementValue elementValue){
        this.elementValues.add(elementValue);
    }
}
