public class HotelRateRule {
    private Hotel hotel;
    private CustomerType cType;
    private DayType dType;
    private int rate;

    public HotelRateRule(Hotel hotel, CustomerType cType, DayType dType, int rate) {
        this.hotel = hotel;
        this.cType = cType;
        this.dType = dType;
        this.rate = rate;
    }

    public Hotel getHotel() {
        return hotel;
    }

    public void sethName(String hName) {
        this.hotel = hotel;
    }

    public CustomerType getcType() {
        return cType;
    }

    public void setcType(CustomerType cType) {
        this.cType = cType;
    }

    public DayType getdType() {
        return dType;
    }

    public void setdType(DayType dType) {
        this.dType = dType;
    }

    public int getRate() {
        return rate;
    }

    public void setRate(int rate) {
        this.rate = rate;
    }

    @Override
    public String toString() {
        return "HotelPrice{" +
                "Hotel=" + hotel +
                ", cType=" + cType +
                ", dType=" + dType +
                ", rate=" + rate +
                '}';
    }
}

enum CustomerType {
    REGULAR, REWARD
}
enum DayType {
    WEEKDAY, WEEKEND
}
