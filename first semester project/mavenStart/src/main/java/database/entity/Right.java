package database.entity;

public class Right implements Identified {

    private Integer id;

    private String name;

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof Right){
            if(this.getId().equals(((Right) obj).getId())){
                return true;
            }
        }
        return false;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
