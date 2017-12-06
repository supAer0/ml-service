package alex.com.system.multilanding.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.sun.org.apache.xpath.internal.operations.Bool;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "instancesite")
public class InstanceSite {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "instancesite_id")
    private Long id;
    @Column(nullable=false)
    private String name;
    @Column(nullable=false)
    private String keyName;
    private Boolean status;
    private Boolean abTest;
    @JsonFormat(pattern = "YYYY-MM-dd")
    private Date dateCreate;
    @ManyToOne
    @JoinColumn (name="site_id")
    @JsonBackReference
    private Site site;
    @OneToMany(mappedBy = "instanceSite", cascade = CascadeType.ALL)
    private List<ElementValue> elementValues;

    public InstanceSite() {
    }

    public InstanceSite(String name, String keyName, Boolean status, Date dateCreate, Boolean abTest) {
        this.name = name;
        this.keyName = keyName;
        this.status = status;
        this.abTest = abTest;
        this.dateCreate = dateCreate;
        this.site = null;
        this.elementValues=new ArrayList<>();
    }

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

    public String getKeyName() {
        return keyName;
    }

    public void setKeyName(String keyName) {
        this.keyName = keyName;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public Date getDateCreate() {
        return dateCreate;
    }

    public void setDateCreate(Date dateCreate) {
        this.dateCreate = dateCreate;
    }

    public Site getSite() {
        return site;
    }

    public void setSite(Site site) {
        this.site = site;
    }

    public void addElementValues(ElementValue elementValue) {
        this.elementValues.add(elementValue);
    }

    public List<ElementValue> getElementValues() {
        return elementValues;
    }

    public void setElementValues(List<ElementValue> elementValues) {
        this.elementValues = elementValues;
    }

    public Boolean getAbTest() {
        return abTest;
    }

    public void setAbTest(Boolean ABTest) {
        this.abTest = ABTest;
    }
}
