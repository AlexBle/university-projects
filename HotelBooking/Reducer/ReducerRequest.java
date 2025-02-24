import java.util.ArrayList;
import java.util.HashMap;
import com.example.hotel_book.User.*;
public class ReducerRequest extends Request {
    ArrayList<Hotel> hotelsResult;
    Integer lockIndex;
    HashMap<String,Integer> areaBookingsResults;

    public ReducerRequest(String type, String requestid){
        super(type, requestid);
    }

    public ReducerRequest(String type, String requestid, ArrayList<Hotel>  hotelsResult, Integer lockIndex){
        super(type, requestid);
        this.hotelsResult = hotelsResult;
        this.lockIndex = lockIndex;
    }

    public ReducerRequest(String type, String requestid, HashMap<String,Integer> areaBookingsResults, Integer lockIndex){
        super(type, requestid);
        this.areaBookingsResults = areaBookingsResults;
        this.lockIndex = lockIndex;
    }

    public ArrayList<Hotel> getHotelsResult() {
        return hotelsResult;
    }

    public Integer getLockIndex() {
        return lockIndex;
    }

    public HashMap<String, Integer> getAreaBookingsResults() {
        return areaBookingsResults;
    }
}
