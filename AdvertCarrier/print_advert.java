import javax.sound.sampled.SourceDataLine;

class print_advert extends advert_type{
    protected int first_page_price; //timi ana leksi sthn 1h sellida
    protected int middle_page_price; ///timi ana leksi se endiamesh sellida
    protected int last_page_price; //timi ana leksi sthn teleutaia sellida

    public print_advert(String code, String description, String AFM, int first_page_price, int middle_page_price, int last_page_price){
        super(code, description, AFM);
        this.first_page_price = first_page_price;
        this.middle_page_price = middle_page_price;
        this.last_page_price = last_page_price;
    }

    public String toString(){
        return super.toString() + "\nFirst Page Price: " + Integer.toString(this.first_page_price) + "\nMiddle Page Price: " + Integer.toString(this.middle_page_price) + "\nLast Page Price: "  + Integer.toString(this.last_page_price);
    }

    public int get_first_page_price(){
        return this.first_page_price;
    }

    public int get_middle_page_price(){
        return this.middle_page_price;
    }

    public int get_last_page_price(){
        return this.last_page_price;
    }
}