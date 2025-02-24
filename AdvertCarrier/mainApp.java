//Omada Xrhstwn 048
//AM 3210134 Alexandros Bletsis

import java.security.cert.CRLReason;
import java.util.*;
import java.io.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class mainApp{
    public static void menu(){//Sellection menu
        System.out.print("\n1. Enter new Advert Carrier\n2. Enter new Advert Type\n3. Enter new Advert\n4. Show all Adverts\n5. Show all Adverts of a single Carrier\n6. Calculate all costs of Adverts of a single Carrier\n7. Show sum of Adverts for every Product\n8. Calculate cost of Adverts of a single Product\n9. Show cost of Adverts for all Products\n10. Save Advert Types and Adverts\n0. Exit\nInput: ");
    }

    public static advert create_unspecified_advert(ArrayList <product> products,ArrayList <advert_type> advert_types, String type){//Creates a general advert without specifiyng its exact type (print, tv/radio, internet)
        String code;
        String product_code;
        int day_duration;
        Scanner input = new Scanner(System.in);
        boolean exists;
        int i;

        System.out.print("Give Advert Type Code: ");
        code = input.nextLine();
        exists = false;
        outerloop: //label for the while loop
        while(!exists){ // check if the Advert Type code belongs to a valid Advert Type
            for(i = 0;i<advert_types.size();i++){
                if(advert_types.get(i).get_code().equals(code) && advert_types.get(i).getClass().getSimpleName().equals(type)){
                    exists = true;
                    break outerloop;
                }
            }
            System.out.println("Given type code does not belong to an Advert Type or given type code does not match Advert Type!");
            System.out.print("Give Advert Type Code: ");
            code = input.nextLine();
        }

        System.out.print("Give Product Code: ");
        product_code = input.nextLine();
        exists = false;
        outerloop: //label for the while loop
        while(!exists){ // check if the product code belongs to a Product
            for(i = 0;i<products.size();i++){
                if(products.get(i).get_code().equals(product_code)){
                    exists = true;
                    break outerloop;
                }
            }
        System.out.println("Given product code does not belong to a Product!");
        System.out.print("Give Product Code: ");
        product_code = input.nextLine();
        }
        
        System.out.print("Give Duration of Advert (days): ");
        day_duration = input.nextInt();
        input.nextLine();
        while(day_duration<1){
            System.out.println("Day duration must be at least 1 day.");
            System.out.print("Give Duration of Advert (days): ");
            day_duration = input.nextInt();
            input.nextLine();
        }
        return new advert(code,product_code,day_duration);
    }

    public static int calculate_cost(advert ad, advert_type ad_type){ //Calculates the cost of a given Advert and its Advert Type
        int cost = 0;
        int page_price = 0;
        int per_sec_price = 0;
        if(ad.get_selected().equals("print")){ //if its a Print Advert
            //Check in the details, at which page the Advert will be in order to get the correct pricing
            if(ad.get_details().equals("First Page")){ 
                page_price = ((print_advert)ad_type).get_first_page_price(); //Type Cast because the Advert Types list has advert_type objects that arent specified what subclass they are
            }
            else if(ad.get_details().equals("Middle Page")){ 
                page_price = ((print_advert)ad_type).get_middle_page_price(); //Type Cast because the Advert Types list has advert_type objects that arent specified what subclass they are
            }
            else if(ad.get_details().equals("Last Page")){ 
                page_price = ((print_advert)ad_type).get_last_page_price(); //Type Cast because the Advert Types list has advert_type objects that arent specified what subclass they are
            }
            cost = ad.get_word_count() * page_price * ad.get_day_duration(); //Calculate and print cost
            System.out.println("\nAdvert with details: \n" + ad + "\nHas Cost: " + Integer.toString(cost) + " (Word Count * Price per word for page * Duration in days, " + Integer.toString(ad.get_word_count()) + " * " + Integer.toString(page_price) + " * " + Integer.toString(ad.get_day_duration()) + ")");
        }
        else if(ad.get_selected().equals("tv_radio")){ //if its a TV_Radio Advert
            //Check in the details, in which zone the Advert will air
            if(ad.get_details().equals("Morning Zone")){
                per_sec_price = ((tv_radio_advert)ad_type).get_morning_zone_price(); //Type Cast because the Advert Types list has advert_type objects that arent specified what subclass they are
            }
            else if(ad.get_details().equals("Noon Zone")){
                per_sec_price =  ((tv_radio_advert)ad_type).get_noon_zone_price(); //Type Cast because the Advert Types list has advert_type objects that arent specified what subclass they are
            }
            else if(ad.get_details().equals("Afternoon Zone")){
                per_sec_price =  ((tv_radio_advert)ad_type).get_afternoon_zone_price(); //Type Cast because the Advert Types list has advert_type objects that arent specified what subclass they are
            }
            else if(ad.get_details().equals("Evening Zone")){
                per_sec_price =  ((tv_radio_advert)ad_type).get_evening_zone_price(); //Type Cast because the Advert Types list has advert_type objects that arent specified what subclass they are
            }
            cost = ad.get_runtime() * per_sec_price * ad.get_day_duration(); //Calculate and print cost
            System.out.println("\nAdvert with details: \n" + ad + "\nHas Cost: " + Integer.toString(cost) + " (Runtime in seconds * Price per second * Duration in days, " + Integer.toString(ad.runtime) + " * " + Integer.toString(per_sec_price) + " * " + Integer.toString(ad.get_day_duration()) + ")");
        }
        else if(ad.get_selected().equals("internet")){ //if its an Internet Advert
            //Calculate and print cost
            cost = ((internet_advert)ad_type).get_day_price() * ad.get_day_duration() + ((internet_advert)ad_type).get_automatic_price() * ad.get_automatic() + ((internet_advert)ad_type).get_added_page_price() * (ad.get_pages()); //Type Cast because the Advert Types list has advert_type objects that arent specified what subclass they are
            System.out.println("\nAdvert with details: \n" + ad + "\nHas Cost: " + Integer.toString(cost) + " (Price per day * Duration in days + Cost of automatic appearance * Selection of automatic appearance (1 or 0) + Price per added page * added pages, " +Integer.toString(((internet_advert)ad_type).get_day_price())  + " * " + Integer.toString(ad.get_day_duration()) + " + " + Integer.toString(((internet_advert)ad_type).get_automatic_price()) + " * " + Integer.toString(ad.get_automatic()) + " + " + Integer.toString(((internet_advert)ad_type).get_added_page_price()) + " * " + Integer.toString((ad.get_pages())) + ")");
        }
        return cost; //returns the cost of the advert
    }

    public static advert_carrier read_carrier(String companyStr) {
        String namePattern = "NAME (.*?)$"; //the pattern inside the block for a company name
        String afmPattern = "AFM (.*?)$"; //the pattern inside the block for a company afm

        Pattern pattern = Pattern.compile(namePattern, Pattern.CASE_INSENSITIVE | Pattern.MULTILINE); //find the pattern that matches (name)
        Matcher matcher = pattern.matcher(companyStr);
        String name = "";
        if (matcher.find()) { //if you found it 
            name = matcher.group(1); //move it to the name
        }
        
        pattern = Pattern.compile(afmPattern, Pattern.CASE_INSENSITIVE | Pattern.MULTILINE); //find the pattern that matches (afm)
        matcher = pattern.matcher(companyStr);
        String afm = "";
        if (matcher.find()) {//if you found it 
            afm = matcher.group(1); //move it to the afm
        }

        if (!name.isEmpty() && !afm.isEmpty()) { //if you found both needed values in the block
            return new advert_carrier(name, afm); //return a new advert carrier
        } 
        else {
            System.out.println("Error reading file!");
            return null;
        }
    }

    public static product read_product(String itemStr) {//same as above
        String codePattern = "CODE (.*?)$";
        String afmPattern = "AFM (.*?)$";
        String descrPattern = "DESCR \"(.*?)\"";

        Pattern pattern = Pattern.compile(codePattern, Pattern.CASE_INSENSITIVE | Pattern.MULTILINE);
        Matcher matcher = pattern.matcher(itemStr);
        String code = "";
        if (matcher.find()) {
            code = matcher.group(1);
        }

        pattern = Pattern.compile(afmPattern, Pattern.CASE_INSENSITIVE | Pattern.MULTILINE);
        matcher = pattern.matcher(itemStr);
        String afm = "";
        if (matcher.find()) {
            afm = matcher.group(1);
        }

        pattern = Pattern.compile(descrPattern, Pattern.CASE_INSENSITIVE | Pattern.MULTILINE);
        matcher = pattern.matcher(itemStr);
        String descr = "";
        if (matcher.find()) {
            descr = matcher.group(1);
        }

        if (!code.isEmpty() && !afm.isEmpty() && !descr.isEmpty()) {
            return new product(code,descr,afm);
        } 
        else {
            System.out.println("Error reading file!");
            return null;
        }
    }

    public static advert_type read_advert_type(String typeStr) {//same as above
        String typePattern = "TYPE (.*?)$";
        String codePattern = "CODE (.*?)$";
        String descrPattern = "DESCR (.*?)$";
        String afmPattern = "AFM (.*?)$";
        
        Pattern pattern = Pattern.compile(typePattern, Pattern.CASE_INSENSITIVE | Pattern.MULTILINE);
        Matcher matcher = pattern.matcher(typeStr);
        String type = "";
        if (matcher.find()) {
            type = matcher.group(1);
        }

        pattern = Pattern.compile(codePattern, Pattern.CASE_INSENSITIVE | Pattern.MULTILINE);
        matcher = pattern.matcher(typeStr);
        String code = "";
        if (matcher.find()) {
            code = matcher.group(1);
        }

        pattern = Pattern.compile(descrPattern, Pattern.CASE_INSENSITIVE | Pattern.MULTILINE);
        matcher = pattern.matcher(typeStr);
        String descr = "";
        if (matcher.find()) {
            descr = matcher.group(1);
        }

        pattern = Pattern.compile(afmPattern, Pattern.CASE_INSENSITIVE | Pattern.MULTILINE);
        matcher = pattern.matcher(typeStr);
        String afm = "";
        if (matcher.find()) {
            afm = matcher.group(1);
        }

        //if you have the general values, continue to find the specific values
        if (!typePattern.isEmpty() && !code.isEmpty() && !afm.isEmpty() && !descr.isEmpty()) {
            if(type.trim().equals("Print")){ //if its a print advert type
                String firstPattern = "FIRST (.*?)$";
                String middlePattern = "MIDDLE (.*?)$";
                String lastPattern = "LAST (.*?)$";
                
                pattern = Pattern.compile(firstPattern, Pattern.CASE_INSENSITIVE | Pattern.MULTILINE);
                matcher = pattern.matcher(typeStr);
                String first = "";
                if (matcher.find()) {
                    first = matcher.group(1);
                }

                pattern = Pattern.compile(middlePattern, Pattern.CASE_INSENSITIVE | Pattern.MULTILINE);
                matcher = pattern.matcher(typeStr);
                String middle = "";
                if (matcher.find()) {
                    middle = matcher.group(1);
                }

                pattern = Pattern.compile(lastPattern, Pattern.CASE_INSENSITIVE | Pattern.MULTILINE);
                matcher = pattern.matcher(typeStr);
                String last = "";
                if (matcher.find()) {
                    last = matcher.group(1);
                }

                if (!first.isEmpty() && !middle.isEmpty() && !last.isEmpty()) {
                    return new print_advert(code,descr,afm,Integer.parseInt(first),Integer.parseInt(middle),Integer.parseInt(last));
                }
                else {
                    System.out.println("Error reading file!");
                    return null;
                }
            }
            else if(type.trim().equals("Media")){ //if its tv radio advert type
                String morningPattern = "MORNING (.*?)$";
                String noonPattern = "NOON (.*?)$";
                String afternoonPattern = "AFTERNOON (.*?)$";
                String eveningPattern = "EVENING (.*?)$";
               
                pattern = Pattern.compile(morningPattern, Pattern.CASE_INSENSITIVE | Pattern.MULTILINE);
                matcher = pattern.matcher(typeStr);
                String morning = "";
                if (matcher.find()) {
                    morning = matcher.group(1);
                }

                pattern = Pattern.compile(noonPattern, Pattern.CASE_INSENSITIVE | Pattern.MULTILINE);
                matcher = pattern.matcher(typeStr);
                String noon = "";
                if (matcher.find()) {
                    noon = matcher.group(1);
                }

                pattern = Pattern.compile(afternoonPattern, Pattern.CASE_INSENSITIVE | Pattern.MULTILINE);
                matcher = pattern.matcher(typeStr);
                String afternoon = "";
                if (matcher.find()) {
                    afternoon = matcher.group(1);
                }
                pattern = Pattern.compile(eveningPattern, Pattern.CASE_INSENSITIVE | Pattern.MULTILINE);
                matcher = pattern.matcher(typeStr);
                String evening = "";
                if (matcher.find()) {
                    evening = matcher.group(1);
                }


                if (!morning.isEmpty() && !noon.isEmpty() && !afternoon.isEmpty() && !evening.isEmpty()) {
                    return new tv_radio_advert(code,descr,afm,Integer.parseInt(morning),Integer.parseInt(noon),Integer.parseInt(afternoon),Integer.parseInt(evening));
                }
                else {
                    System.out.println("Error reading file!");
                    return null;
                }
            }
            else if(type.trim().equals("Web")){ //if its internet advert type
                String dayPattern = "DAY (.*?)$";
                String autoPattern = "AUTOMATIC (.*?)$";
                String addPattern = "ADDITIONAL (.*?)$";
                
                pattern = Pattern.compile(dayPattern, Pattern.CASE_INSENSITIVE | Pattern.MULTILINE);
                matcher = pattern.matcher(typeStr);
                String day = "";
                if (matcher.find()) {
                    day = matcher.group(1);
                }

                pattern = Pattern.compile(autoPattern, Pattern.CASE_INSENSITIVE | Pattern.MULTILINE);
                matcher = pattern.matcher(typeStr);
                String auto = "";
                if (matcher.find()) {
                    auto = matcher.group(1);
                }

                pattern = Pattern.compile(addPattern, Pattern.CASE_INSENSITIVE | Pattern.MULTILINE);
                matcher = pattern.matcher(typeStr);
                String add = "";
                if (matcher.find()) {
                    add = matcher.group(1);
                }
                if (!day.isEmpty() && !auto.isEmpty() && !add.isEmpty()) {
                    return new internet_advert(code,descr,afm,Integer.parseInt(day),Integer.parseInt(auto),Integer.parseInt(add));
                }
                else {
                    System.out.println("Error reading file!");
                    return null;
                }
            }
        } 
        else {
            System.out.println("Error reading file!");
            return null;
        }
        System.out.println("Error reading file!");
        return null;
    }
    
    public static advert read_advert(String advertStr) {//same as above
        String typePattern = "TYPE (.*?)$";
        String typeCodePattern = "ADVTYPE_CODE (.*?)$";
        String itemCodePattern = "ITEM_CODE (.*?)$";
        String durationPattern = "DURATION (.*?)$";
        String descrPattern = "DESCR (.*?)$";
        
        Pattern pattern = Pattern.compile(typePattern, Pattern.CASE_INSENSITIVE | Pattern.MULTILINE);
        Matcher matcher = pattern.matcher(advertStr);
        String type = "";
        if (matcher.find()) {
            type = matcher.group(1);
        }

        pattern = Pattern.compile(typeCodePattern, Pattern.CASE_INSENSITIVE | Pattern.MULTILINE);
        matcher = pattern.matcher(advertStr);
        
        String typeCode = "";
        if (matcher.find()) {
            typeCode = matcher.group(1);
        }

        pattern = Pattern.compile(descrPattern, Pattern.CASE_INSENSITIVE | Pattern.MULTILINE);
        matcher = pattern.matcher(advertStr);
        String descr = "";
        if (matcher.find()) {
            descr = matcher.group(1);
        }

        pattern = Pattern.compile(itemCodePattern, Pattern.CASE_INSENSITIVE | Pattern.MULTILINE);
        matcher = pattern.matcher(advertStr);
        String itemCode = "";
        if (matcher.find()) {
            itemCode = matcher.group(1);
        }

        pattern = Pattern.compile(durationPattern, Pattern.CASE_INSENSITIVE | Pattern.MULTILINE);
        matcher = pattern.matcher(advertStr);
        String duration = "";
        if (matcher.find()) {
            duration = matcher.group(1);
        }

        if (!type.isEmpty() && !typeCode.isEmpty() && !itemCode.isEmpty() && !duration.isEmpty()) {
            if(type.trim().equals("Print")){
                String wordPattern = "WORDS (.*?)$";
                
                pattern = Pattern.compile(wordPattern, Pattern.CASE_INSENSITIVE | Pattern.MULTILINE);
                matcher = pattern.matcher(advertStr);
                String words = "10";
                if (matcher.find()) {
                    words = matcher.group(1);
                }


                if (!words.isEmpty()) {
                    return new advert(Integer.parseInt(words),typeCode,itemCode,Integer.parseInt(duration),descr);
                }
                else {
                    System.out.println("Error reading file!");
                    return null;
                }
            }
            else if(type.trim().equals("Media")){
                String runtimePattern = "RUNTIME (.*?)$";
               
                pattern = Pattern.compile(runtimePattern, Pattern.CASE_INSENSITIVE | Pattern.MULTILINE);
                matcher = pattern.matcher(advertStr);
                String runtime = "10";
                if (matcher.find()) {
                    runtime = matcher.group(1);
                }

                if (!runtime.isEmpty()) {
                    return new advert(typeCode,itemCode,Integer.parseInt(duration),descr,Integer.parseInt(runtime));
                }
                else {
                    System.out.println("Error reading file!");
                    return null;
                }
            }
            else if(type.trim().equals("Web")){
                String autoPattern = "AUTOMATIC (.*?)$";
                String pagesPattern = "PAGES (.*?)$";
                

                pattern = Pattern.compile(autoPattern, Pattern.CASE_INSENSITIVE | Pattern.MULTILINE);
                matcher = pattern.matcher(advertStr);
                String auto = "0";
                if (matcher.find()) {
                    auto = matcher.group(1);
                }

                pattern = Pattern.compile(pagesPattern, Pattern.CASE_INSENSITIVE | Pattern.MULTILINE);
                matcher = pattern.matcher(advertStr);
                String pages = "1";
                if (matcher.find()) {
                    pages = matcher.group(1);
                }
                if (!auto.isEmpty() && !pages.isEmpty() && (auto.trim().equals("1")|| auto.trim().equals("0"))) {
                    return new advert(typeCode,itemCode,Integer.parseInt(duration),descr,Integer.parseInt(auto), Integer.parseInt(pages));
                }
                else {
                    System.out.println("Error reading file!");
                    return null;
                }
            }
        } 
        else {
            System.out.println("Error reading file!");
            return null;
        }
        System.out.println("Error reading file!");
        return null;
    }

    public static void write_files(ArrayList <advert_type> advert_types, ArrayList <advert> adverts){
        System.out.println("Updating advert_types.txt and adverts.txt.");
        FileWriter writer = null;
        try{
            writer = new FileWriter(new File("data\\advert_types.txt"));
            writer.write("ADVTYPE_LIST\n{\n");
            String type;
            String special_info;
            for(advert_type ad_type: advert_types){
                if(ad_type.getClass().toString().equals("class print_advert")){
                    type = "Print";
                    special_info = "\n\t\tFIRST " + ((print_advert)ad_type).get_first_page_price() 
                    +"\n\t\tMIDDLE " + ((print_advert)ad_type).get_middle_page_price()
                    +"\n\t\tLAST " + ((print_advert)ad_type).get_last_page_price();
                }
                else if(ad_type.getClass().toString().equals("class tv_radio_advert")){
                    type = "Media";
                    special_info = "\n\t\tMORNING " + ((tv_radio_advert)ad_type).get_morning_zone_price()
                    +"\n\t\tNOON " + ((tv_radio_advert)ad_type).get_noon_zone_price()
                    +"\n\t\tAFTERNOON " + ((tv_radio_advert)ad_type).get_afternoon_zone_price()
                    +"\n\t\tEVENING " + ((tv_radio_advert)ad_type).get_evening_zone_price();
                }
                else if(ad_type.getClass().toString().equals("class internet_advert")){
                    type = "Web";
                    special_info = "\n\t\tDAY " + ((internet_advert)ad_type).get_day_price()
                    + "\n\t\tAUTOMATIC " + ((internet_advert)ad_type).get_automatic_price()
                    + "\n\t\tADDITIONAL " + ((internet_advert)ad_type).get_added_page_price();
                }
                else{
                    writer.close();
                    throw new IOException();
                }
                writer.write("\n\tADVTYPE"
                + "\n\t{"
                + "\n\t\tTYPE " + type
                + "\n\t\tCODE " + ad_type.get_code()
                + "\n\t\tDESCR " + ad_type.get_description()
                + "\n\t\tAFM " + ad_type.get_AFM()
                + special_info
                + "\n\t}");
            }
            writer.write("\n}");
            writer.close();
        }   
        catch (IOException e) {
            System.err.println("Error writing advert_types.txtS!");
        }

        try{
            writer = new FileWriter(new File("data\\adverts.txt"));
            writer.write("ADV_LIST\n{\n");
            String type;
            String special_info;
            for(advert ad: adverts){
                if(ad.get_selected().equals("print")){
                    type = "Print";
                    special_info = "\n\t\tWORDS " + ad.get_word_count();
                }
                else if(ad.get_selected().equals("tv_radio")){
                    type = "Media";
                    special_info = "\n\t\tRUNTIME " + ad.get_runtime();
                }
                else if(ad.get_selected().equals("internet")){
                    type = "Web";
                    special_info = "\n\t\tAUTOMATIC " + ad.get_automatic()
                    + "\n\t\tPAGES " + ad.get_pages();
                }
                else{
                    writer.close();
                    throw new IOException();
                }
                writer.write("\n\tADV"
                + "\n\t{"
                + "\n\t\tTYPE " + type
                + "\n\t\tADVTYPE_CODE " + ad.get_type_code()
                + "\n\t\tITEM_CODE " + ad.get_product_code()
                + "\n\t\tDURATION " + ad.get_day_duration()
                + "\n\t\tDESCR " + ad.get_details()
                + special_info
                + "\n\t}");
            }
            writer.write("\n}");
            writer.close();
        }   
        catch (IOException e) {
            System.err.println("Error writing advert_types.txtS!");
        } 
    }

    public static void main(String[] args) {
        int i;
        int j;
        int k;
        advert temporary_ad;
        boolean exists;
        
        ArrayList <advert_carrier> carriers = new ArrayList<advert_carrier>();
        ArrayList <product> products = new ArrayList<product>();
        ArrayList <advert_type> advert_types = new ArrayList<advert_type>();
        ArrayList <advert> adverts = new ArrayList<advert>();

        Scanner input = new Scanner(System.in);

        String name;
        String AFM;
        String code;
        String description;
        String details;
        int word_count;
        int runtime;
        int automatic;
        int pages;
        int first_page_price; 
        int middle_page_price; 
        int last_page_price;
        int morning_zone_price; 
        int noon_zone_price;
        int afternoon_zone_price; 
        int evening_zone_price;
        int day_price; 
        int automatic_price; 
        int added_page_price;

        String selected_carrier_afm;
        String found_carriers_code;

        advert ad;
        advert_type ad_type;
        int total_cost;

        int sum_of_adverts[][];

        String selected_product_code;

        int total_costs[][];
        
        BufferedReader reader = null;
        String line;

        
        try{ //read carriers file
			reader = new BufferedReader(new FileReader(new File("data\\carriers.txt")));
            StringBuilder sb = new StringBuilder();
            boolean isBlock = false;

            while ((line = reader.readLine()) != null) { //read until the file ends
                if (line.trim().equals("COMPANY")) { //if you find a line with the tag COMPANY you are in a block with info for the company
                    isBlock = true;
                } else if (line.contains("{")) { //this will happen if theres a mistake
                    if (!isBlock) { 
                        sb = new StringBuilder(); //start again
                    }
                } else if (line.contains("}")) { //if you are at the end of the block
                    if (isBlock) {
                        isBlock = false;
                        String companyStr = sb.toString(); //move the info of the string builder
                        advert_carrier carrier = read_carrier(companyStr); //get the carrier from the info
                        if (carrier != null) { //if all is good add it to the list
                            carriers.add(carrier);
                        }
                        sb = new StringBuilder(); //start again
                    }
                } else if (isBlock) { //if you are in a block
                    sb.append(line.trim()); //add the line to the block
                    sb.append("\n");
                }
            }
            System.out.println("carriers.txt read successfully.");
        }
        catch (IOException e) {
            System.out.println("Error reading carriers file!");
        }

        try{
			reader = new BufferedReader(new FileReader(new File("data\\products.txt")));
            StringBuilder sb = new StringBuilder();
            boolean isBlock = false;

            while ((line = reader.readLine()) != null) {
                if (line.trim().equals("ITEM")) {
                    isBlock = true;
                } else if (line.contains("{")) {
                    if (!isBlock) {
                        sb = new StringBuilder();
                    }
                } else if (line.contains("}")) {
                    if (isBlock) {
                        isBlock = false;
                        String itemStr = sb.toString();
                        product product = read_product(itemStr);
                        if (product != null) {
                            products.add(product);
                        }
                        sb = new StringBuilder();
                    }
                } else if (isBlock) {
                    sb.append(line.trim());
                    sb.append("\n");
                }
            }
            System.out.println("products.txt read successfully.");
        }
        catch (IOException e) {
            System.out.println("Error reading products file!");
        }

        try{
			reader = new BufferedReader(new FileReader(new File("data\\advert_types.txt")));
            StringBuilder sb = new StringBuilder();
            boolean isBlock = false;
            
            while ((line = reader.readLine()) != null) {
                
                if (line.trim().equals("ADVTYPE")) {
                    isBlock = true;
                } else if (line.contains("{")) {
                    if (!isBlock) {
                        sb = new StringBuilder();
                    }
                } else if (line.contains("}")) {
                    if (isBlock) {
                        isBlock = false;
                        String typeStr = sb.toString();
                        advert_type advert_type = read_advert_type(typeStr);
                        if (advert_type != null) {
                            advert_types.add(advert_type);
                        }
                        sb = new StringBuilder();
                    }
                } else if (isBlock) {
                    sb.append(line.trim());
                    sb.append("\n");
                }
            }
            System.out.println("advert_types.txt read successfully.");
        }
        catch (IOException e) {
            System.out.println("Error reading advert types file!");
        }

        try{
			reader = new BufferedReader(new FileReader(new File("data\\adverts.txt")));
            StringBuilder sb = new StringBuilder();
            boolean isBlock = false;
            
            while ((line = reader.readLine()) != null) {
                if (line.trim().equals("ADV")) {
                    isBlock = true;
                } else if (line.contains("{")) {
                    if (!isBlock) {
                        sb = new StringBuilder();
                    }
                } else if (line.contains("}")) {
                    if (isBlock) {
                        isBlock = false;
                        String advertStr = sb.toString();
                        advert adv = read_advert(advertStr);
                        if (adv != null) {
                            adverts.add(adv);
                        }
                        sb = new StringBuilder();
                    }
                } else if (isBlock) {
                    sb.append(line.trim());
                    sb.append("\n");
                }
            }
            System.out.println("adverts.txt read successfully.");

        }
        catch (IOException e) {
            System.out.println("Error reading advert types file!");
        }
        
        /* 
        //Initilization of Lists
        carriers.add(new advert_carrier("Foreas A", "AFM Forea A"));
        carriers.add(new advert_carrier("Foreas B", "AFM Forea B"));
        carriers.add(new advert_carrier("Foreas C", "AFM Forea C"));
        carriers.add(new advert_carrier("Foreas D", "AFM Forea D"));

        products.add(new product("Product Code 1","Proion 1","AFM Promitheyth 1"));
        products.add(new product("Product Code 2","Proion 2","AFM Promitheyth 2"));
        products.add(new product("Product Code 3","Proion 3","AFM Promitheyth 3"));
        products.add(new product("Product Code 4","Proion 4","AFM Promitheyth 4"));
        products.add(new product("Product Code 5","Proion 5","AFM Promitheyth 5"));

        advert_types.add(new print_advert("Type 1","Efimerida 1", "AFM Forea A", 10, 4, 6));
        advert_types.add(new print_advert("Type 2","Periodiko 1", "AFM Forea B", 5, 3, 3));
        advert_types.add(new print_advert("Type 3","Periodiko 2", "AFM Forea C", 6, 2, 5));

        advert_types.add(new tv_radio_advert("Type 4","Radio 1", "AFM Forea D", 50, 40, 40, 70));
        advert_types.add(new tv_radio_advert("Type 5","Radio 2", "AFM Forea A", 60, 50, 40, 80));
        advert_types.add(new tv_radio_advert("Type 6","TV 1", "AFM Forea B", 150, 100, 120, 200));

        advert_types.add(new internet_advert("Type 7","Istoselida 1", "AFM Forea C", 100, 400, 50));
        advert_types.add(new internet_advert("Type 8","Istoselida 2", "AFM Forea D", 50, 300, 25));
        advert_types.add(new internet_advert("Type 9","Istoselida 3", "AFM Forea A", 70, 300, 40));

        adverts.add(new advert(300,"Type 1", "Product Code 1", 20, "First Page"));
        adverts.add(new advert(400,"Type 2", "Product Code 2", 1, "Last Page"));
        adverts.add(new advert(500,"Type 3", "Product Code 3", 365, "Middle Page"));
        adverts.add(new advert(600,"Type 1", "Product Code 4", 92, "First Page"));

        adverts.add(new advert("Type 4", "Product Code 5", 7, "Morning Zone", 45));
        adverts.add(new advert("Type 5", "Product Code 1", 15, "Noon Zone", 15));
        adverts.add(new advert("Type 6", "Product Code 2", 30, "Afternoon Zone", 60));
        adverts.add(new advert("Type 6", "Product Code 3", 1, "Evening  Zone", 90));

        adverts.add(new advert("Type 7", "Product Code 4", 10, "Details 1", 0, 1));
        adverts.add(new advert("Type 8", "Product Code 5", 700, "Details 2", 0, 7));
        adverts.add(new advert("Type 9", "Product Code 1", 50, "Details 3", 1, 1));
        adverts.add(new advert("Type 8", "Product Code 2", 180, "Details 4", 1, 50));
        */

        menu();
        int answer = input.nextInt();
        input.nextLine();

        while(answer!=0){
            if(answer == 1){ //Enter new Advert Carrier
                System.out.print("Give name of Advert Carrier: ");
                name = input.nextLine();
                System.out.print("Give AFM of Advert Carrier: ");
                AFM = input.nextLine();
                exists = true;
                while(exists){ //check if the given AFM already exists
                    exists = false;
                    for(i = 0;i<carriers.size();i++){
                        if(carriers.get(i).get_AFM().equals(AFM)){
                            System.out.println("AFM already exists!");
                            System.out.print("Give AFM of Advert Carrier: ");
                            AFM = input.nextLine();
                            exists = true;
                            break;
                        }
                    }
                }
                carriers.add(new advert_carrier(name,AFM));
            }
            else if(answer == 2){ //Enter new Advert Type
                System.out.print("Give Advert Type code: ");
                code = input.nextLine();
                exists = true;
                while(exists){ //check if the given code already exists
                    exists = false;
                    for(i = 0;i<advert_types.size();i++){
                        if(advert_types.get(i).get_code().equals(code)){
                            System.out.println("Type code already exists!");
                            System.out.print("Give Advert Type code: ");
                            code = input.nextLine();
                            exists = true;
                            break;
                        }
                    }
                }
                System.out.print("Give Advert Type's Carrier AFM: ");
                AFM = input.nextLine();
                exists = false;
                outerloop: //label for the while loop
                while(!exists){ // check if the AFM belongs to a Carrier
                    for(i = 0;i<carriers.size();i++){
                        if(carriers.get(i).get_AFM().equals(AFM)){
                            exists = true;
                            break outerloop;
                        }
                    }
                    System.out.println("Given AFM does not belong to a Carrier!");
                    System.out.print("Give Advert Type's Carrier AFM: ");
                    AFM = input.nextLine();
                }
                System.out.print("Select Advert Type: Print(1) TV/Radio(2) Internet(3) ");
                answer = input.nextInt();
                input.nextLine();
                while(answer>3 || answer<1){
                    System.out.println("OUT OF BOUNDS ANSWER!");
                    System.out.print("Select Advert Type: Print(1) TV/Radio(2) Internet(3) ");
                    answer = input.nextInt();
                    input.nextLine();
                }
                if(answer==1){
                    System.out.print("Give Advert Type description: ");
                    description = input.nextLine();
                    System.out.print("Give First Page Price per word: ");
                    first_page_price = input.nextInt();
                    input.nextLine();
                    while(first_page_price<1){
                        System.out.println("Price must be at least 1.");
                        System.out.print("Give First Page Price per word: ");
                        first_page_price = input.nextInt();
                        input.nextLine();
                    }
                    System.out.print("Give Middle Page Price per word: ");
                    middle_page_price = input.nextInt();
                    input.nextLine();
                    while(middle_page_price<1){
                        System.out.println("Price must be at least 1.");
                        System.out.print("Give Middle Page Price per word: ");
                        middle_page_price = input.nextInt();
                        input.nextLine();
                    }
                    System.out.print("Give Last Page Price per word: ");
                    last_page_price = input.nextInt();
                    input.nextLine();
                    while(last_page_price<1){
                        System.out.println("Price must be at least 1.");
                        System.out.print("Give Last Page Price per word: ");
                        last_page_price = input.nextInt();
                        input.nextLine();
                    }
                    advert_types.add(new print_advert(code,description,AFM,first_page_price,middle_page_price,last_page_price));
                }
                else if(answer == 2){
                    System.out.print("Give Advert Type description: ");
                    description = input.nextLine();
                    System.out.print("Give Morning Zone price per second: ");
                    morning_zone_price = input.nextInt();
                    input.nextLine();
                    while(morning_zone_price<1){
                        System.out.println("Price must be at least 1.");
                        System.out.print("Give Morning Zone price per second: ");
                        morning_zone_price = input.nextInt();
                        input.nextLine();
                    }
                    System.out.print("Give Noon Zone price per second: ");
                    noon_zone_price = input.nextInt();
                    input.nextLine();
                    while(noon_zone_price<1){
                        System.out.println("Price must be at least 1.");
                        System.out.print("Give Noon Zone price per second: ");
                        noon_zone_price = input.nextInt();
                        input.nextLine();
                    }
                    System.out.print("Give Afternoon Zone price per second: ");
                    afternoon_zone_price = input.nextInt();
                    input.nextLine();
                    while(afternoon_zone_price<1){
                        System.out.println("Price must be at least 1.");
                        System.out.print("Give Afternoon Zone price per second: ");
                        afternoon_zone_price = input.nextInt();
                        input.nextLine();
                    }
                    System.out.print("Give Evening Zone price per second: ");
                    evening_zone_price = input.nextInt();
                    input.nextLine();
                    while(evening_zone_price<1){
                        System.out.println("Price must be at least 1.");
                        System.out.print("Give Evening Zone price per second: ");
                        evening_zone_price = input.nextInt();
                        input.nextLine();
                    }
                    advert_types.add(new tv_radio_advert(code,description,AFM,morning_zone_price,noon_zone_price,afternoon_zone_price,evening_zone_price));
                }
                else if(answer == 3){
                    System.out.print("Give Advert Type description: ");
                    description = input.nextLine();
                    System.out.print("Give Price per day of appearance: ");
                    day_price = input.nextInt();
                    input.nextLine();
                    while(day_price<1){
                        System.out.println("Price must be at least 1.");
                        System.out.print("Give Price per day of appearance: ");
                        day_price = input.nextInt();
                        input.nextLine();
                    }
                    System.out.print("Give Automatic appearance cost: ");
                    automatic_price = input.nextInt();
                    input.nextLine();
                    while(automatic_price<1){
                        System.out.println("Cost must be at least 1.");
                        System.out.print("Give Automatic appearance cost: ");
                        automatic_price = input.nextInt();
                        input.nextLine();
                    }
                    System.out.print("Give Price per added page: ");
                    added_page_price = input.nextInt();
                    input.nextLine();
                    while(added_page_price<1){
                        System.out.println("Price must be at least 1.");
                        System.out.print("Give Price per added page: ");
                        added_page_price = input.nextInt();
                        input.nextLine();
                    }
                    advert_types.add(new internet_advert(code,description,AFM,day_price,automatic_price,added_page_price));
                }
            }
            else if(answer == 3){ //Enter new Advert 
                System.out.print("Select Advert Type: Print(1) TV/Radio(2) Internet(3) ");
                answer = input.nextInt();
                input.nextLine();
                while(answer>3 || answer<1){
                    System.out.println("OUT OF BOUNDS ANSWER!");
                    System.out.print("Select Advert Type: Print(1) TV/Radio(2) Internet(3) ");
                    answer = input.nextInt();
                    input.nextLine();
                }
                if(answer == 1){ //new Print Advert
                    temporary_ad = create_unspecified_advert(products,advert_types,"print_advert"); //Create a general advert
                    System.out.print("Give Advert details: First Page(1) Middle Page(2) Last Page (3) ");
                    answer = input.nextInt();
                    input.nextLine();
                    details = "";
                    while(answer>3 || answer<1){
                        System.out.println("OUT OF BOUNDS ANSWER!");
                        System.out.print("Give Advert details: First Page(1) Middle Page(2) Last Page (3) ");
                        answer = input.nextInt();
                        input.nextLine();
                    }
                    if(answer==1){
                        details = "First Page";
                    }
                    else if(answer==2){
                        details = "Middle Page";
                    }
                    else if(answer==3){
                        details = "Last Page";
                    }
                    System.out.print("Give Word Count: ");
                    word_count = input.nextInt();
                    input.nextLine();
                    while(word_count<1){
                        System.out.println("Word Count must be at least 1 word.");
                        System.out.print("Give Word Count: ");
                        word_count = input.nextInt();
                        input.nextLine();
                    }
                    adverts.add(new advert(word_count, temporary_ad,details)); //Create a Print advert using the general advert
                }
                else if(answer == 2){ //new TV/Radio Advert
                    temporary_ad = create_unspecified_advert(products,advert_types,"tv_radio_advert"); //Create a general advert
                    System.out.print("Give Advert details: Morning Zone(1) Noon Zone(2) Afternoon Zone(3) Evening Zone(4) ");
                    answer = input.nextInt();
                    input.nextLine();
                    details = "";
                    while(answer>4 || answer<1){
                        System.out.println("OUT OF BOUNDS ANSWER!");
                        System.out.print("Give Advert details: Morning Zone(1) Noon Zone(2) Afternoon Zone(3) Evening Zone(4) ");
                        answer = input.nextInt();
                        input.nextLine();
                    }
                    if(answer==1){
                        details = "Morning Zone";
                    }
                    else if(answer==2){
                        details = "Noon Zone";
                    }
                    else if(answer==3){
                        details = "Afternoon Zone";
                    }
                    else if(answer==4){
                        details = "Evening Zone";
                    }
                    System.out.print("Give Runtime: ");
                    runtime = input.nextInt();
                    input.nextLine();
                    while(runtime<1){
                        System.out.println("Runtime must be at least 1 second.");
                        System.out.print("Give Runtime (seconds): ");
                        runtime = input.nextInt();
                        input.nextLine();
                    }
                    adverts.add(new advert(temporary_ad,runtime,details)); //Create a TV/Radio advert using the general advert
                }
                else if(answer == 3){ //new Internet Advert
                    temporary_ad = create_unspecified_advert(products,advert_types,"internet_advert"); //Create a general advert
                    System.out.print("Give Advert details: ");
                    details = input.nextLine();
                    System.out.print("Give Automatic Appearance(1/0): ");
                    automatic = input.nextInt();
                    input.nextLine();
                    while(automatic!=1 && automatic !=0){
                        System.out.println("Automatic Appearance must be at either 1 or 0.");
                        System.out.print("Give Automatic Appearance(1/0): ");
                        automatic = input.nextInt();
                        input.nextLine();
                    }
                    System.out.print("Give number of additional Pages: ");
                    pages = input.nextInt();
                    input.nextLine();
                    while(pages<0){
                        System.out.println("Additional Pages can not be less than 0.");
                        System.out.print("Give number of additional Pages: ");
                        pages = input.nextInt();
                        input.nextLine();
                    }
                    adverts.add(new advert(temporary_ad,automatic,pages,details)); //Create a Internet advert using the general advert
                }
            }
            else if(answer == 4){ //Show all Adverts
                for(i = 0;i<adverts.size();i++){
                    System.out.println(adverts.get(i) + "\n");
                }
            }
            else if(answer == 5){ //Show all Adverts of a selected Carrier
                for(i = 0;i<carriers.size();i++){
                    System.out.println(Integer.toString(i+1) + ". " + carriers.get(i) + "\n"); //Show all carriers
                }
                System.out.print("Select one of the Carriers by their code (1 to " + Integer.toString(i) + "): ");
                answer = input.nextInt() - 1; //Selected carrier's index (-1 because list is 0 based)
                input.nextLine();
                while(answer+1>carriers.size() || answer < 0){
                    System.out.println("OUT OF BOUNDS ANSWER!");
                    System.out.print("Select one of the Carriers by their code (1 to " + Integer.toString(i) + "): ");
                    answer = input.nextInt() - 1; //Selected carrier's index (-1 because list is 0 based)
                    input.nextLine();
                }
                selected_carrier_afm = carriers.get(answer).get_AFM(); //AFM of selected carrier
                for(i = 0;i<advert_types.size();i++){ //For all of the Advert Types
                    if(advert_types.get(i).get_AFM().equals(selected_carrier_afm)){ //Check if the AFM of the Carrier of the Advert Type matches the selected AFM
                        found_carriers_code = advert_types.get(i).get_code(); //If it matches, you have a code of an Advert Type that belongs to the selected Carrier
                        for(j = 0;j<adverts.size();j++){ //For all the Adverts
                            if(adverts.get(j).get_type_code().equals(found_carriers_code)){ //If an Advert's Advert Type code matches the found code
                                System.out.println(adverts.get(j) + "\n"); //print the Advert
                            }
                        }
                    }
                }
            }
            else if(answer == 6){ //Calculate cost of all Adverts of a selected Carrier
                for(i = 0;i<carriers.size();i++){
                    System.out.println(Integer.toString(i+1) + ". " + carriers.get(i) + "\n");
                }
                System.out.print("Select one of the Carriers by their code (1 to " + Integer.toString(i) + "): ");
                answer = input.nextInt() - 1; //Selected carrier's index (-1 because list is 0 based)
                input.nextLine();
                while(answer+1>carriers.size() || answer < 0){
                    System.out.println("OUT OF BOUNDS ANSWER!");
                    System.out.print("Select one of the Carriers by their code (1 to " + Integer.toString(i) + "): ");
                    answer = input.nextInt() - 1; //Selected carrier's index (-1 because list is 0 based)
                    input.nextLine();
                }
                selected_carrier_afm = carriers.get(answer).get_AFM(); //AFM of selected carrier
                total_cost = 0;
                for(i = 0;i<advert_types.size();i++){ //For all of the Advert Types
                    if(advert_types.get(i).get_AFM().equals(selected_carrier_afm)){ //Check if the AFM of the Carrier of the Advert Type matches the selected AFM
                        found_carriers_code = advert_types.get(i).get_code(); //If it matches, you have a code of an Advert Type that belongs to the selected Carrier
                        for(j = 0;j<adverts.size();j++){ //For all the Adverts
                            if(adverts.get(j).get_type_code().equals(found_carriers_code)){ //If an Advert's Advert Type code matches the found code
                                //At this point you have an Advert that belongs to the selected Carrier and its type is that of the current Advert Type, time to calculate the cost of the Advert depending on its characteristics
                                ad = adverts.get(j);
                                ad_type = advert_types.get(i);
                                total_cost += calculate_cost(ad, ad_type);
                            }
                        }
                    }
                }
                System.out.println("\nTotal Cost of all Adverts: " + Integer.toString(total_cost) + "\n");
            }
            else if(answer == 7){ //Show sum of Adverts for every Product (Descending order)
                sum_of_adverts = new int[products.size()][2]; //[i][0] is the sum of Adverts and [i][1] is the index of the Product in the products list
                for(i = 0; i<products.size();i++){ //for every Product
                    sum_of_adverts[i][0] = 0; //sum = 0;
                    sum_of_adverts[i][1] = i; //store the index of the product
                    for(j = 0; j<adverts.size();j++){ // for every Advert
                        if(adverts.get(j).get_product_code().equals(products.get(i).get_code())){ //Check if the Advert is for the current Product
                            sum_of_adverts[i][0]++; //if it is then add an Advert to the sum
                        }
                    }
                }
                java.util.Arrays.sort(sum_of_adverts,  new java.util.Comparator<int[]>(){ //!Code found on stack overflow!
                    public int compare(int[] a, int[] b) {return Integer.compare(a[0], b[0]);} //sorts in ascending order
                    });
                
                for(i = products.size()-1; i>=0;i--){ //print in descending order
                    System.out.println("Product with details: \n" + products.get(sum_of_adverts[i][1]) + "\nHas a total of " + sum_of_adverts[i][0] + " Adverts made for it.\n");
                }
            }
            else if(answer == 8){ //Calculate cost of all Adverts of a selected Product
                for(i = 0;i<products.size();i++){
                    System.out.println(Integer.toString(i+1) + ". " + products.get(i) + "\n"); //Show all products
                }
                System.out.print("Select one of the Products by their code (1 to " + Integer.toString(i) + "): ");
                answer = input.nextInt() - 1; //Selected product's index (-1 because list is 0 based)
                input.nextLine();
                while(answer+1>products.size() || answer < 0){
                    System.out.println("OUT OF BOUNDS ANSWER!");
                    System.out.print("Select one of the Products by their code (1 to " + Integer.toString(i) + "): ");
                    answer = input.nextInt() - 1; //Selected product's index (-1 because list is 0 based)
                    input.nextLine();
                }
                selected_product_code = products.get(answer).get_code(); 
                total_cost = 0;
                for(j = 0; j<adverts.size();j++){ //for all Adverts
                    if(adverts.get(j).get_product_code().equals(selected_product_code)){ //if an Advert's code matches the selected code
                        for(i = 0; i<advert_types.size();i++){ //find the Advert's Type
                            if(advert_types.get(i).get_code().equals(adverts.get(j).get_type_code())){ //if it matches 
                                //At this point you have and Advert for the selected Product and its type is that of the current Advert Type, time to calculate the cost of the Advert depending on its characteristics
                                ad = adverts.get(j);
                                ad_type = advert_types.get(i);
                                total_cost += calculate_cost(ad, ad_type); //calculate cost of Advert
                            }
                        }
                    }
                }
                System.out.println("\nTotal Cost of all Adverts: " + Integer.toString(total_cost) + "\n");
            }
            else if(answer == 9){ //Calculate cost of all Adverts of all the Products
                total_costs = new int[products.size()][2];//[i][0] is the total cost of the Adverts for the Product and [i][1] is the index of the Product in the products list
                for(i = 0;i<products.size();i++){ //for all products
                    total_costs[i][0] = 0; //total_cost = 0;
                    total_costs[i][1] = i; //store the index of the product
                    for(j = 0; j<adverts.size();j++){ // for every Advert
                        if(adverts.get(j).get_product_code().equals(products.get(i).get_code())){ //Check if the Advert is for the current Product
                            for(k = 0; k<advert_types.size();k++){ //for all Advert Types
                                if(adverts.get(j).get_type_code().equals(advert_types.get(k).get_code())){ //if the the Advert's type matches the current Advert Type
                                    total_costs[i][0]+=calculate_cost(adverts.get(j), advert_types.get(k)); //calculate the cost of the Advert and add it to the total cost
                                }
                            }
                            
                        }
                    }
                }
                java.util.Arrays.sort(total_costs,  new java.util.Comparator<int[]>(){ //!Code found on stack overflow!
                    public int compare(int[] a, int[] b) {return Integer.compare(a[0], b[0]);} //sorts in ascending order
                    });
                
                for(i = products.size()-1; i>=0;i--){ //print in descending order
                    System.out.println("***\nProduct with details: \n" + products.get(total_costs[i][1]) + "\nHas a total cost of " + total_costs[i][0] + " for its Adverts.\n***\n");
                }
            }
            else if(answer == 10){
                write_files(advert_types,adverts);
            }
            else{
                System.out.println("OUT OF BOUNDS ANSWER!");
            }
            menu();
            answer = input.nextInt();
            input.nextLine();
        }
        input.close();
        write_files(advert_types,adverts);
    }
}