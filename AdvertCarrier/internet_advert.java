class internet_advert extends advert_type{
    protected int day_price; //timh ana hmera diafhmishs
    protected int automatic_price; //epipleon kostos gia thn aytomath emfanish ths diafhmishs kata to fortwma ths selidas
    protected int added_page_price; //timh gia kathe epipleon selida 
   

    public internet_advert(String code, String description, String AFM, int day_price, int automatic_price, int added_page_price){
        super(code, description, AFM);
        this.day_price = day_price;
        this.automatic_price = automatic_price;
        this.added_page_price = added_page_price;
    }

    public String toString(){
        return super.toString() + "\nPrice per Day: " + Integer.toString(this.day_price) + "\nAutomatic Price: " + Integer.toString(this.automatic_price) + "\nAdded Page Price: " + Integer.toString(this.added_page_price);
    }

    public int get_day_price(){
        return this.day_price;
    }

    public int get_automatic_price(){
        return this.automatic_price;
    }

    public int get_added_page_price(){
        return this.added_page_price;
    }
}