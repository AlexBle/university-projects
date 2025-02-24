import com.example.hotel_book.User.*;
public class ManagerRequest extends Request{
    String managerid;
    Hotel hotel = null;
    String hotelName = null;
    int[] freeDates = null;
    public ManagerRequest(String managerid, String type, String requestid){
        super(type,requestid);
        this.managerid = managerid;
    }

    public ManagerRequest(String type, String requestid, Hotel hotel){
        super(type,requestid);
        this.hotel = hotel;
    }

    public ManagerRequest(String type, String requestid, String hotelName, int[] freeDates){
        super(type,requestid);
        this.hotelName = hotelName;
        this.freeDates = freeDates;
    }

    public ManagerRequest(String type, String requestid, int[] dates){
        super(type, requestid);
        this.freeDates = dates;
    }
    
    public String getManagerid() {
        return managerid;
    }

    public Hotel getHotel() {
        return hotel;
    }

    public int[] getFreeDates() {
        return freeDates;
    }

    public String getHotelName() {
        return hotelName;
    }

}
