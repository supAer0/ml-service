package alex.com.system.multilanding.model;

import javax.persistence.Entity;
import javax.persistence.*;

@Entity
public class Nisha {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(nullable=false,unique = true)
    private String title;

    public Nisha(String title) {
        this.title = title;
    }
    public Nisha(){

    }

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
}
