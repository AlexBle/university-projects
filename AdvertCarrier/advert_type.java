import java.rmi.server.RemoteRef;

class advert_type extends product{

    public advert_type(String code, String description, String AFM){
        super(code, description, AFM);
    }

    public String toString(){
        return "Advert type code: " + this.code + "\nDescription: " + this.description + "\nAdvert Type AFM: " + this.AFM;
    }

    public String get_description(){
        return this.description;
    }
}