class tv_radio_advert extends advert_type{
    protected int morning_zone_price; //timi ana deyterolepto sthn prwinh zonh
    protected int noon_zone_price; //timi ana deyterolepto sthn mesimerianh zonh
    protected int afternoon_zone_price; //timi ana deyterolepto sthn apogeymatinh zonh
    protected int evening_zone_price; //timi ana deyterolepto sthn vradinh zonh
    

    public tv_radio_advert(String code, String description, String AFM, int morning_zone_price,int noon_zone_price, int afternoon_zone_price, int evening_zone_price){
        super(code, description, AFM);
        this.morning_zone_price = morning_zone_price;
        this.noon_zone_price = noon_zone_price;
        this.afternoon_zone_price = afternoon_zone_price;
        this.evening_zone_price = evening_zone_price;
    }

    public String toString(){
        return super.toString() + "\nMoring Zone Price: " + Integer.toString(this.morning_zone_price) + "\nNoon Zone Price: " + Integer.toString(this.noon_zone_price) + "\nAfternoon Zone Price: "  + Integer.toString(this.afternoon_zone_price) + "\nEvening Zone Price: " + Integer.toString(this.evening_zone_price);
    }

    public int get_morning_zone_price(){
        return this.morning_zone_price;
    }

    public int get_noon_zone_price(){
        return this.noon_zone_price;
    }

    public int get_afternoon_zone_price(){
        return this.afternoon_zone_price;
    }

    public int get_evening_zone_price(){
        return this.evening_zone_price;
    }
}