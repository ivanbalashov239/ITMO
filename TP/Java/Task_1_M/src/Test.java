public class Test {
    public static void main(String[] args) {
        SimpleDate date1 = new NumbersDate(SimpleDate.Month.JANUARY, 21 ,2013);
        SimpleDate date2 = new NumbersDate(SimpleDate.Month.FEBRUARY, 23 ,2013);

        System.out.println(date2.from(date1));

        date1 = new NumbersDate(SimpleDate.Month.JANUARY, 1 ,2001);
        date2 = new NumbersDate(SimpleDate.Month.NOVEMBER, 19 ,2014);

        System.out.println(date2.from(date1));

        date1 = new CalendarDate(SimpleDate.Month.NOVEMBER, 1 ,2001);
        date2 = new CalendarDate(SimpleDate.Month.JANUARY, 19 ,2014);

        System.out.println(date2.from(date1));
    }
}
