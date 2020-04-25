import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class HotelFinder {

    private static String inputStr = "Rewards: 26Mar2009, 27Mar2009, 28Mar2009";

    public static void main(String[] args) throws Exception {
        RateCalculator rc = new RateCalculator();
        populateRateRules(rc);

        String[] inputArr = inputStr.split(":");
        if (inputArr.length != 2) {
            throw new Exception("Invalid input provided");
        }

        CustomerType custType = getEnumCustType(inputArr[0].trim());
        List<DayType> bookingDays = parseDayTypes(inputArr[1].trim());

        Hotel bestHotel = rc.searchBestHotel(custType, bookingDays);
        System.out.println("INPUT : " + inputStr);
        System.out.println("OUTPUT : " + bestHotel.getName());

    }

    public static CustomerType getEnumCustType(String ctype) {
        CustomerType custType = (ctype.equalsIgnoreCase(CustomerType.REGULAR.toString()) ?
                CustomerType.REGULAR : CustomerType.REWARD);
        return custType;
    }

    public static List<DayType> parseDayTypes(String dayTypes) {
        String[] days = dayTypes.split(",");
        return Arrays.stream(days)
                .map(HotelFinder::getDayType)
                .collect(Collectors.toList());
    }

    public static DayType getDayType(String dateString) {
        DateTimeFormatter strFormat = DateTimeFormatter.ofPattern("ddMMMyyyy");
        LocalDate ld = null;
        try {
            ld = LocalDate.parse(dateString.trim(), strFormat);
            switch (ld.getDayOfWeek()) {
                case SATURDAY:
                case SUNDAY:
                    return DayType.WEEKEND;
                default:
                    return DayType.WEEKDAY;
            }
        } catch (DateTimeParseException e) {
            System.out.println("Invalid date format: " + e);
            return null;
        }
    }

    public static void populateRateRules(RateCalculator rc) {
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
}
