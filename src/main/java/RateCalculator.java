import java.util.*;
import java.util.stream.Collectors;

public class RateCalculator {
    private List<HotelRateRule> prices = new ArrayList<>();

    public RateCalculator registerPrice(HotelRateRule hotelRate) {
        prices.add(hotelRate);
        return this;
    }

    public Hotel searchBestHotel(CustomerType ct, List<DayType> days) {
        Map<Hotel, List<HotelRateRule>> hotelsWithRatesByCT = prices.stream()
                .filter(hp -> hp.getcType().equals(ct))
                .collect(Collectors.groupingBy(HotelRateRule::getHotel));

        Map<Hotel, Integer> hotelCostSForDays = getRatesForDaysByHotel(hotelsWithRatesByCT, days);
//        printHotelCosts(hotelCostSForDays);
        Hotel bestHotel = getBestRateRating(hotelCostSForDays);
        return bestHotel;
    }

    public Map<Hotel, Integer> getRatesForDaysByHotel(Map<Hotel, List<HotelRateRule>> rateHotel, List<DayType> days) {
        Map<Hotel, Integer> hotelCost = new HashMap<>();
        rateHotel.entrySet().forEach((entry) -> {
            int rateSum = 0;
            for (DayType day : days) {
                int dayRate = entry.getValue().stream()
                        .filter(hp -> hp.getdType().equals(day))
                        .map(HotelRateRule::getRate)
                        .findFirst().orElse(0);
                rateSum += dayRate;
            }
            hotelCost.put(entry.getKey(), rateSum);

        });
        return hotelCost;
    }

    private Hotel getBestRateRating(Map<Hotel, Integer> hotelCost) {

        Map.Entry<Hotel, Integer> minHotelRate = Collections.min(hotelCost.entrySet(),
                Comparator.comparing(Map.Entry::getValue));

        int lowestRate = minHotelRate.getValue();
        List<Hotel> lowestHotels = new ArrayList<>();

        for (Hotel hotel : hotelCost.keySet()) {
            if (hotelCost.get(hotel) == lowestRate) {
                lowestHotels.add(hotel);
            }
        }

        if(lowestHotels.size() ==1) {
            return lowestHotels.get(0);
        } else {
            Hotel h = lowestHotels.stream()
                    .max(Comparator.comparing(Hotel::getRating))
                    .get();
            return h;
        }
    }


    private void printHotelCosts(Map<Hotel, Integer> hotelCostSForDays) {
        hotelCostSForDays.entrySet().stream()
                .forEach((entry) -> {
                    System.out.println(entry.getKey() + " -Price- " + entry.getValue());
                });
    }
}
