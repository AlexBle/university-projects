import com.example.hotel_book.User.*;

public class MasterRequest extends Request{
    private static final long serialVersionUID = 123456789L;
    Hotel hotel;
    String hotelName = null;
    int[] dates = null;
    String managerid;
    Integer lockIndex;
    int review;
    Filter filter;

    public MasterRequest(String type, String requestid){
        super(type, requestid);
    }

    public MasterRequest(String type, String requestid, Hotel hotel){
        super(type,requestid);
        this.hotel = hotel;
    }

    public MasterRequest(String type, String requestid, String hotelName, int[] dates){
        super(type,requestid);
        this.hotelName = hotelName;
        this.dates = dates;
    }

    public MasterRequest(String type, String requestid, String managerid, Integer lockIndex){
        super(type,requestid);
        this.managerid = managerid;
        this.lockIndex = lockIndex;
    }

    public MasterRequest(String type, String requestid, int review, String hotelName){
        super(type, requestid);
        this.review = review;
        this.hotelName = hotelName;
    }

    public MasterRequest(String type, String requestid, Filter filter, Integer lockIndex){
        super(type, requestid);
        this.filter = filter;
        this.lockIndex = lockIndex;
    }

    public MasterRequest(String type, String requestid, int[] dates, Integer lockIndex){
        super(type, requestid);
        this.dates = dates;
        this.lockIndex = lockIndex;
    }



    public int[] getDates() {
        return dates;
    }

    public String getHotelName() {
        return hotelName;
    }
    
    public Hotel getHotel() {
        return hotel;
    }

    public String getManagerid() {
        return managerid;
    }

    public Integer getLockIndex() {
        return lockIndex;
    }

    public int getReview() {
        return review;
    }

    public Filter getFilter() {
        return filter;
    }

}
