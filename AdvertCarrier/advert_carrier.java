class advert_carrier{
    protected String name; //epwnymia
    protected String AFM; //AFM forea
    
    public advert_carrier(String name, String AFM){
        this.name = name;
        this.AFM = AFM;
    }

    public String toString(){
        return "Advert Carrier " + this.name +"\nAFM: " + this.AFM;
    }

    public String get_AFM(){
        return this.AFM;
    }
}