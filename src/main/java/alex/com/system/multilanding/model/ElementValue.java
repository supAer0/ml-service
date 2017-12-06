package alex.com.system.multilanding.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

@Entity
@Table(name = "elementvalue")
public class ElementValue {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "element_value_id")
    private Long id;
    @ManyToOne
//    @JoinTable(name = "instancesite", joinColumns = @JoinColumn(name="element_value_id"), inverseJoinColumns = @JoinColumn(name="instancesite_id"))
    @JsonIgnore
    @JoinColumn(name="instancesite_id")
    private InstanceSite instanceSite;
    @ManyToOne
////    @JoinTable(name = "element", joinColumns = @JoinColumn(name="element_id"), inverseJoinColumns = @JoinColumn(name="element_value_id"))
    @JoinColumn(name="element_id")
    @JsonBackReference
    private Element element;
    @Lob
    private String value;

    public ElementValue(
            InstanceSite instanceSite,
            Element element,
            String value) {
        this.instanceSite = instanceSite;
//        this.element = element;
        this.value = value;
    }
    public ElementValue() {

    }

    public InstanceSite getInstanceSite() {
        return instanceSite;
    }

    public void setInstanceSite(InstanceSite instanceSite) {
        this.instanceSite = instanceSite;
    }

    public Element getElement() {
        return element;
    }

    public void setElement(Element element) {
        this.element = element;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}