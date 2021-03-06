package database.entity;

public class Genre implements Identified {

    private Integer id;

    private String name;

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof Genre){
            if(this.getId().equals(((Genre) obj).getId())){
                return true;
            }
        }
        return false;
    }

    @Override
    public Integer getId() {
        return id;
    }

    @Override
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
