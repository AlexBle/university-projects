class product{
    protected String code; //kwdikos proiontos
    protected String description; //perigrafh
    protected String AFM; //afm promitheyth
    
    public product(String code, String description, String AFM){
        this.code = code;
        this.description = description;
        this.AFM = AFM;
    }

    public String toString(){
        return "Product code: " + this.code + "\nDescription: " + this.description + "\nProduct AFM: " + this.AFM;
    }

    public String get_AFM(){
        return this.AFM;
    }

    public String get_code(){
        return this.code;
    }
}