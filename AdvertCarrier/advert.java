public class advert{
    protected String type_code; //kwdikos typou diafhmisis
    protected String product_code; //kwdikos proiontos
    protected int day_duration; //xronikh diarkeia emfanishs ths diafhmishs se hmeres
    protected String details; //aitiologia
    protected String selected_type_of_advert; //epilegmenos typos diafimishs

    protected int word_count; //plhthos leksewn
    
    protected int runtime; //diarkeia se deyterolepta

    protected int automatic; //aytomath emfanish
    protected int pages; //plhtos selidwn

    public advert(String type_code, String product_code, int day_duration){ //Unspecified Advert
        
        this.type_code = type_code;
        this.product_code = product_code;
        this.day_duration = day_duration;
        this.selected_type_of_advert = null;
    }

    public advert(int word_count, String type_code , String product_code, int day_duration, String details){ //Print Advert
        
        this.type_code = type_code;
        this.product_code = product_code;
        this.day_duration = day_duration;
        this.details = details;
        this.word_count = word_count;
        this.selected_type_of_advert = "print";
    }

    public advert(int word_count, advert advert, String details){ //Print Advert from an Unspecified Advert
        this.type_code = advert.type_code;
        this.product_code =  advert.product_code;
        this.day_duration = advert. day_duration;
        this.details =  advert.details;
        this.word_count = word_count;
        this.details = details;
        this.selected_type_of_advert = "print";
    }

    public advert(String type_code, String product_code, int day_duration, String details, int runtime){ //TV Radio Advert
        
        this.type_code = type_code;
        this.product_code = product_code;
        this.day_duration = day_duration;
        this.details = details;
        this.runtime = runtime;
        this.selected_type_of_advert = "tv_radio";
    }

    public advert(advert advert, int runtime, String details){ //TV Radio Advert from an Unspecified Advert
        this.type_code = advert.type_code;
        this.product_code =  advert.product_code;
        this.day_duration = advert. day_duration;
        this.details =  advert.details;
        this.runtime = runtime;
        this.details = details;
        this.selected_type_of_advert = "tv_radio";
    }

    public advert(String type_code, String product_code, int day_duration, String details, int automatic, int pages){ //Internet Advert
        
        this.type_code = type_code;
        this.product_code = product_code;
        this.day_duration = day_duration;
        this.details = details;
        this.automatic = automatic;
        this.pages = pages;
        this.selected_type_of_advert = "internet";
    }

    public advert(advert advert, int automatic, int pages, String details){ //Internet Advert from an Unspecified Advert
        this.type_code = advert.type_code;
        this.product_code =  advert.product_code;
        this.day_duration = advert. day_duration;
        this.details =  advert.details;
        this.automatic = automatic;
        this.pages = pages;
        this.details = details;
        this.selected_type_of_advert = "internet";
    }


    public String get_selected(){
        return selected_type_of_advert;
    }

    public String toString(){
        String to_return = "Advert Type Code: " + this.type_code + "\nProduct Code: " + this.product_code + "\nDuration (days): " + Integer.toString(this.day_duration) + "\nDetails: " + this.details;
        //Select what more to print depending on what kind of advert it is
        if(this.selected_type_of_advert.equals("print")){
            to_return += "\nWord Count: " + Integer.toString(this.word_count);
        }
        else if(this.selected_type_of_advert.equals("tv_radio")){
            to_return += "\nRuntime: " + Integer.toString(this.runtime);
        }
        else if(this.selected_type_of_advert.equals("internet")){
            to_return += "\nAutomatic Appearance: " + Integer.toString(this.automatic) + "\nNumber of additional Pages: " + Integer.toString(this.pages);
        }
        return to_return;
    }
    
    public String get_type_code(){
        return this.type_code;
    }

    public int get_word_count(){
        return this.word_count;
    }

    public int get_day_duration(){
        return this.day_duration;
    }
    
    public String get_details(){
        return this.details;
    }
    
    public int get_runtime(){
        return this.runtime;
    }

    public int get_automatic(){
        return this.automatic;
    }

    public int get_pages(){
        return this.pages;
    }

    public String get_product_code(){
        return this.product_code;
    }
}