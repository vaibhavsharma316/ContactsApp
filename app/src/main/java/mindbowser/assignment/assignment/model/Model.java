package mindbowser.assignment.assignment.model;

/**
 * Created by vaibhav on 3/29/2016.
 */
public class Model {

    String id="";
    String name="";
    String uri="";
    String number="";
    String delete_status="";
    String favorite_status="";
    public Model()
    {



    }


    public String getDelete_status() {
        return delete_status;
    }

    public void setDelete_status(String delete_status) {
        this.delete_status = delete_status;
    }

    public String getFavorite_status() {
        return favorite_status;
    }

    public void setFavorite_status(String favorite_status) {
        this.favorite_status = favorite_status;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }
}
