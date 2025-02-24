import java.util.ArrayList;
import java.util.HashMap;
import com.example.hotel_book.User.*;

public class WorkerRequest extends Request{
    ArrayList<Hotel>  hotelsResult;
    Integer lockIndex;
    HashMap<String,Integer>  bookingsByArea;

    public WorkerRequest(String type, String requestid){
        super(type, requestid);
    }

    public WorkerRequest(String type, String requestid, ArrayList<Hotel>  hotelsResult, Integer lockIndex){
        super(type, requestid);
        this.hotelsResult = hotelsResult;
        this.lockIndex = lockIndex;
    }

    public WorkerRequest(String type, String requestid, HashMap<String,Integer>  bookingsByArea, Integer lockIndex){
        super(type, requestid);
        this.bookingsByArea = bookingsByArea;
        this.lockIndex = lockIndex;
    }
    
    public ArrayList<Hotel> getHotelsResult() {
        return hotelsResult;
    }

    public Integer getLockIndex() {
        return lockIndex;
    }
    
    public HashMap<String, Integer> getBookingByArea() {
        return bookingsByArea;
    }
}
