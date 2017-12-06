package alex.com.system.multilanding.model;

public class El {
    private Long id;
    private String name;
    private String key;
    private String value;

    public El() {

    }


    public El(Long id, String name, String key) {
        this.id = id;
        this.name = name;
        this.key = key;
    }


    public El(Long id, String name, String key,String value) {
        this.id = id;
        this.name = name;
        this.key = key;
        this.value = value;
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

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
