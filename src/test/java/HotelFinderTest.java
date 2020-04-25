import org.junit.BeforeClass;
import static org.junit.Assert.*;

import org.junit.Test;

import java.util.List;

public class HotelFinderTest {

    static RateCalculator rc = new RateCalculator();

    @BeforeClass
    public static void populateData() {

        Hotel cv = new Hotel("CoconutValley", 3);
        Hotel al = new Hotel("AakulamLake", 4);
        Hotel vb = new Hotel("VeliBeach", 5);

        rc
            .registerPrice(new HotelRateRule(cv, CustomerType.REGULAR, DayType.WEEKDAY, 1100))
            .registerPrice(new HotelRateRule(cv, CustomerType.REWARD, DayType.WEEKDAY, 800))
            .registerPrice(new HotelRateRule(cv, CustomerType.REGULAR, DayType.WEEKEND, 900))
            .registerPrice(new HotelRateRule(cv, CustomerType.REWARD, DayType.WEEKEND, 800))

            .registerPrice(new HotelRateRule(al, CustomerType.REGULAR, DayType.WEEKDAY, 1600))
            .registerPrice(new HotelRateRule(al, CustomerType.REWARD, DayType.WEEKDAY, 1100))
            .registerPrice(new HotelRateRule(al, CustomerType.REGULAR, DayType.WEEKEND, 600))
            .registerPrice(new HotelRateRule(al, CustomerType.REWARD, DayType.WEEKEND, 500))

            .registerPrice(new HotelRateRule(vb, CustomerType.REGULAR, DayType.WEEKDAY, 2200))
            .registerPrice(new HotelRateRule(vb, CustomerType.REWARD, DayType.WEEKDAY, 1000))
            .registerPrice(new HotelRateRule(vb, CustomerType.REGULAR, DayType.WEEKEND, 1500))
            .registerPrice(new HotelRateRule(vb, CustomerType.REWARD, DayType.WEEKEND, 400));
    }

    @Test
    public void findHotel() throws Exception {
        String input1 = "Regular: 16Mar2009, 17Mar2009, 18Mar2009";
        String extected1 = "CoconutValley";

        String input2 = "Regular: 20Mar2009, 21Mar2009, 22Mar2009";
        String extected2 = "AakulamLake";

        String input3 = "Rewards: 26Mar2009, 27Mar2009, 28Mar2009";
        String extected3 = "VeliBeach";

        assertEquals(extected1, executeFind(input1));
        assertEquals(extected2, executeFind(input2));
        assertEquals(extected3, executeFind(input3));

    }

    @Test(expected = Exception.class)
    public void badInputExpectException() throws Exception {
        String input = "Bad Input";
        executeFind(input);
    }

    private String executeFind(String str) throws Exception {
        String[] inputArr = str.split(":");
            if(inputArr.length !=2){
            throw new Exception("Invalid input provided");
        }

        CustomerType custType = HotelFinder.getEnumCustType(inputArr[0].trim());
        List<DayType> bookingDays = HotelFinder.parseDayTypes(inputArr[1].trim());

        Hotel bestHotel = rc.searchBestHotel(custType, bookingDays);
        return bestHotel.getName();
    }
}
