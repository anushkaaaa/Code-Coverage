package edu.iupui.cs450;

import org.junit.Assert;
import org.junit.*;
import java.time.*;
import java.time.chrono.*;
import java.time.temporal.*;
import java.util.ArrayList;
import java.util.List;

public class PeriodTest {

  // Test for years
  @Test
  public void testOfYears() {
    Period p = Period.ofYears(1996);
    Assert.assertEquals(1996, p.getYears());
    Assert.assertEquals(0, p.getMonths());
    Assert.assertEquals(0, p.getDays());
  }

  // Test for months
  @Test
  public void testOfMonths() {
    Period p = Period.ofMonths(4);
    Assert.assertEquals(0, p.getYears());
    Assert.assertEquals(4, p.getMonths());
    Assert.assertEquals(0, p.getDays());
  }

  // Test for weeks
  @Test
  public void testOfWeeks() {
    Period p = Period.ofWeeks(3);
    Assert.assertEquals(0, p.getYears());
    Assert.assertEquals(0, p.getMonths());
    Assert.assertEquals(21, p.getDays());
  }

  // Test for days
  @Test
  public void testOfDays() {
    Period p = Period.ofDays(20);
    Assert.assertEquals(0, p.getYears());
    Assert.assertEquals(0, p.getMonths());
    Assert.assertEquals(20, p.getDays());
  }

  // Test for of
  @Test
  public void testOfOf() {
    Period p = Period.of(1996, 4, 20);
    Assert.assertEquals(1996, p.getYears());
    Assert.assertEquals(4, p.getMonths());
    Assert.assertEquals(20, p.getDays());
  }

  // Test Period from(TemporalAmount amount)
  @Test
  public void testOfAmountInstanceOfPeriod() {
      TemporalAmount a = Period.of(1996,4,20);
      Period b = Period.from(Period.of(1996, 4, 20));
      Assert.assertEquals(a,b);
  }

  @Test
  public void testOfAmountInstanceOfChronoPeriod(){
      TemporalAmount a = IsoChronology.INSTANCE.period(1996,4,20);
      ChronoPeriod b = Period.from(a);
      Assert.assertEquals(IsoChronology.INSTANCE,b.getChronology());
  }

  @Test(expected= DateTimeException.class)
  public void testOfAmountInstanceOfChronoPeriodE(){
      Period.from(ThaiBuddhistChronology.INSTANCE.period(1996, 4, 20));
  }

  @Test(expected = DateTimeException.class)
  public void testOfAmountInstanceOfChronoPeriodDE() {
     class TemporalStub implements TemporalAmount {
      @Override
      public long get(TemporalUnit unit) {
        if (unit == ChronoUnit.YEARS) { return 0; }
        else { return 1; }
      }
      @Override
      public List<TemporalUnit> getUnits() {
        List<TemporalUnit> a = new ArrayList<>();
        a.add(ChronoUnit.YEARS);
        a.add(ChronoUnit.HALF_DAYS);
        return a;
      }
      @Override
      public Temporal addTo(Temporal temporal) {
        throw new UnsupportedOperationException();
      }
      @Override
      public Temporal subtractFrom(Temporal temporal) {
        throw new UnsupportedOperationException();
      }};
    TemporalStub amt = new TemporalStub();
    Period.from(amt);
  }

  //Test for Parse
  @Test
  public void testOfParse() {
    Period p = Period.parse("P0Y0M0D");
    Assert.assertEquals(Period.ZERO, p);
  }

  @Test
  public void testOfParseNegate() {
    Period p = Period.parse("-P1996Y4M20D");
    Assert.assertEquals(Period.of(-1996,-4,-20), p);
  }

  @Test
  public void testOfParseYear() {
    Period p = Period.parse("P1Y");
    Assert.assertEquals(Period.ofYears(1), p);
  }

  @Test
  public void testOfParseMonth() {
    Period p = Period.parse("P1M");
    Assert.assertEquals(Period.ofMonths(1), p);
  }

  @Test
  public void testOfParseDay() {
    Period p = Period.parse("P1D");
    Assert.assertEquals(Period.ofDays(1), p);
  }

  @Test
  public void testOfParseWeek(){
    Period p = Period.parse("P1W");
    Assert.assertEquals(Period.ofWeeks(1), p);
  }

  @Test(expected = DateTimeException.class)
  public void testOfParseNullValueException() {
    Period.parse("P");
  }

  @Test(expected = DateTimeException.class)
  public void testOfParseNumberValueException() {
    Period.parse("-P-2147483648Y");
  }

  @Test(expected = DateTimeException.class)
  public void testOfParseNumberFormatException() {
    Period.parse("P99999999999999999Y");
  }

  @Test(expected = DateTimeException.class)
  public void testOfParseValueException() {
      Period.parse("P1K5T6B");
  }

  // Test for get(TemporalUnit unit)
  @Test
  public void testOfTemporalGet(){
    Period p = Period.of(1996,4,20);
    Assert.assertEquals(p.getYears(),p.get(ChronoUnit.YEARS));
    Assert.assertEquals(p.getMonths(),p.get(ChronoUnit.MONTHS));
    Assert.assertEquals(p.getDays(),p.get(ChronoUnit.DAYS));
  }

  // Test for get(TemporalUnit unit Exception
  @Test(expected = UnsupportedTemporalTypeException.class)
  public void testOfTemporalGetException(){
    Period p = Period.of(1996,4,20);
    Assert.assertEquals(p.getYears(),p.get(ChronoUnit.YEARS));
    Assert.assertEquals(p.getMonths(),p.get(ChronoUnit.MONTHS));
    Assert.assertEquals(p.getDays(),p.get(ChronoUnit.DAYS));
    Assert.assertEquals(p.getDays(),p.get(ChronoUnit.HALF_DAYS));
  }

  // Test for List<TemporalUnit> getUnits()
  @Test
  public void testOfTemporalGetUnits(){
    Period p = Period.of(1996,4,20);
    List<TemporalUnit> t = new ArrayList<>();
    t.add(ChronoUnit.YEARS);
    t.add(ChronoUnit.MONTHS);
    t.add(ChronoUnit.DAYS);
    Assert.assertEquals(t, p.getUnits());
  }

  //Test for isZero
  @Test
  public void testOfIsZero() {
     Assert.assertSame(Period.of(0, 0, 0).isZero(), true);
  }

  @Test
  public void testOfIsNotZero() {
    Assert.assertSame(Period.of(1996, 4, 20).isZero(), false);
  }

  //Test for isNegative
  @Test
  public void testOfIsNegative() {
    Assert.assertSame(Period.of(-96, -4, -20).isNegative(), true);
  }

  @Test
  public void testOfIsNegativeYears() {
    Assert.assertSame(Period.of(-96, 4, 20).isNegative(), true);
  }

  @Test
  public void testOfIsNegativeMonths() {
    Assert.assertSame(Period.of(96, -4, 20).isNegative(), true);
  }

  @Test
  public void testOfIsNegativeDays() {
    Assert.assertSame(Period.of(96, 4, -20).isNegative(), true);
  }

  @Test
  public void testOfIsNotNegative() {
    Assert.assertSame(Period.of(96, 4, 20).isNegative(), false);
  }

  //Test for withYears
  @Test
  public void testOfWithSameYears() {
    Period p = Period.of(1996, 4, 20);
    PAssert(p.withYears(1996),1996,4,20);
  }

  @Test
  public void testOfWithDiffYears() {
    Period p = Period.of(1996, 4, 20);
    PAssert(p.withYears(1995),1995,4,20);
  }

  //Test for withMonths
  @Test
  public void testOfWithSameMonths() {
    Period p = Period.of(1996, 4, 20);
    PAssert(p.withMonths(4), 1996, 4, 20);
  }

  @Test
  public void testOfWithDiffMonths() {
    Period p = Period.of(1996, 4, 20);
    PAssert(p.withMonths(3), 1996, 3, 20);
  }

  //Test for withDays
  @Test
  public void testOfWithSameDays() {
    Period p = Period.of(1996, 4, 20);
    PAssert(p.withDays(20), 1996, 4, 20);
  }

  @Test
  public void testOfWithDiffDays() {
    Period p = Period.of(1996, 4, 20);
    PAssert(p.withDays(21), 1996, 4, 21);
  }

  // Test for Temporal Plus
  @Test
  public void testOfPlus(){
    Period a = Period.of(10, 0, 0);
    Period b = Period.of(1996,4,20);
    Period c = Period.of(2006,4,20);
    Assert.assertEquals(c,b.plus(a));
  }

  // Test for PlusYears
  @Test
  public void testOfPlusYears() {
    Period p = Period.of(1996, 4, 20);
    PAssert(p.plusYears(10), 2006, 4, 20);
  }

  @Test
  public void testOfPlusYearsToAdd0() {
    Period p = Period.of(1996, 4, 20);
    PAssert(p.plusYears(0), 1996, 4, 20);
  }

  // Test for PlusMonths
  @Test
  public void testOfPlusMonths() {
    Period p = Period.of(1996, 2, 20);
    PAssert(p.plusMonths(10), 1996, 12, 20);
  }

  @Test
  public void testOfPlusMonthsToAdd0() {
    Period p = Period.of(1996, 2, 20);
    PAssert(p.plusMonths(0), 1996, 2, 20);
  }

  // Test for PlusDays
  @Test
  public void testOfPlusDays() {
    Period p = Period.of(1996, 4, 20);
    PAssert(p.plusDays(10), 1996, 4, 30);
  }

  @Test
  public void testOfPlusDaysToAdd0() {
    Period p = Period.of(1996, 4, 20);
    PAssert(p.plusDays(0), 1996, 4, 20);
  }

  // Test for Temporal Minus
  @Test
  public void testOfMinus(){
    Period a = Period.of(10, 4, 0);
    Period b = Period.of(1996,8,20);
    Period c = Period.of(1986,4,20);
    Assert.assertEquals(c,b.minus(a));
  }

  // Test for minusYears
  @Test
  public void testOfMinusYears() {
    Period p = Period.of(1996,4,20);
    Assert.assertEquals(p.plusYears(-2),p.minusYears(2));
  }

  // Test for minusMonths
  @Test
  public void testOfMinusMonths() {
    Period p = Period.of(1996,4,20);
    Assert.assertEquals(p.plusMonths(-2),p.minusMonths(2));
  }

  // Test for minusDays
  @Test
  public void testOfMinusDays() {
    Period p = Period.of(1996,4,20);
    Assert.assertEquals(p.plusDays(-2),p.minusDays(2));
  }

  // Test for multipliedBY
  @Test
  public void testOfMultipliedBy() {
    Period p = Period.of(1000, 4, 10);
    PAssert(p.multipliedBy(2), 2000, 8, 20);
  }

  @Test
  public void testOfMultipliedBy1() {
    Period p = Period.of(1000, 4, 10);
    Assert.assertEquals(p,p.multipliedBy(1));
  }

  @Test
  public void testOfMultipliedByForPeriodZero() {
    Period p = Period.ZERO;
    Assert.assertEquals(p,p.multipliedBy(5));
  }

  // Test for Negated
  @Test
  public void TestOfNegated()
  {
    Period p = Period.of(1996, 4, 20);
    Assert.assertEquals(p.multipliedBy(-1),p.negated());
  }

  // Test for normalized
  @Test
  public void TestOfNormalizedNoChange() {
    Period p = Period.of(12, 1, 0);
    Assert.assertEquals(p, p.normalized());
  }

  @Test
  public void TestOfNormalizedChange() {
    Period p = Period.of(1, 12, 0);
    Assert.assertEquals(Period.of(2,0,0),p.normalized());
  }

  // Test for toTotalMonths
  @Test
  public void TestOfToTotalMonths()
  {
    Period p = Period.of(1996, 4, 20);
    Assert.assertEquals(1996*12+4, p.toTotalMonths());
  }

  @Test
  public void TestOfToTotalMonthsZero()
  {
    Period p = Period.of(0, 0, 20);
    Assert.assertEquals(0, p.toTotalMonths());
  }

  // Test for Temporal AddTo
  @Test
  public void testOfAddToMonthZero(){

    Period a = Period.of(10, 0, 10);
    LocalDate b = LocalDate.of(1996,4,20);
    LocalDate c = LocalDate.of(2006,4,30);
    Assert.assertEquals(c,a.addTo(b));
  }

  @Test
  public void testOfAddToMonthNotZero(){

    Period a = Period.of(10, 4, 0);
    LocalDate b = LocalDate.of(1996,4,20);
    LocalDate c = LocalDate.of(2006,8,20);
    Assert.assertEquals(c,a.addTo(b));
  }

  @Test
  public void testOfAddTo(){

    Period a = Period.of(0, 0, 0);
    LocalDate b = LocalDate.of(1996,8,20);
    Assert.assertEquals(b,a.addTo(b));
  }

  @Test
  public void testOfAddToTotalMonthZero(){

    Period a = Period.of(-1, 12, 0);
    LocalDate b = LocalDate.of(-1,2,5);
    LocalDate c = LocalDate.of(-1,2,5);
    Assert.assertEquals(c,a.addTo(b));
  }

  // Test for Temporal SubtractFrom
  @Test
  public void testOfSubtractFromMonthZero(){

    Period a = Period.of(10, 0, 10);
    LocalDate b = LocalDate.of(1996,1,30);
    LocalDate c = LocalDate.of(1986,1,20);
    Assert.assertEquals(c,a.subtractFrom(b));
  }

  @Test
  public void testOfSubtractFromMonthNotZero(){

    Period a = Period.of(10, 4, 0);
    LocalDate b = LocalDate.of(1996,8,20);
    LocalDate c = LocalDate.of(1986,4,20);
    Assert.assertEquals(c,a.subtractFrom(b));
  }

  @Test
  public void testOfSubtractFrom(){

    Period a = Period.of(0, 0, 0);
    LocalDate b = LocalDate.of(1996,8,20);
    Assert.assertEquals(b,a.subtractFrom(b));
  }

  @Test
  public void testOfSubtractFromTotalMonthZero(){

    Period a = Period.of(-1, 12, 0);
    LocalDate b = LocalDate.of(-1,2,5);
    LocalDate c = LocalDate.of(-1,2,5);
    Assert.assertEquals(c,a.subtractFrom(b));
  }

  // Test for Validate Chrono
  @Test(expected = DateTimeException.class)
  public void testOfValidateDiffChrono(){
    class TemporalStub implements Temporal {
      @Override
      public boolean isSupported(TemporalUnit unit) {
        return false;
      }
      @Override
      public Temporal with(TemporalField field, long newValue) {
        return null;
      }
      @Override
      public Temporal plus(long amountToAdd, TemporalUnit unit) {
        return null;
      }
      @Override
      public long until(Temporal endExclusive, TemporalUnit unit) {
        return 0;
      }
     @Override
      public boolean isSupported(TemporalField field) {
        return false;
      }
      @Override
      public long getLong(TemporalField field) {
        return 0;
      }
      @Override
      public <R> R query(TemporalQuery<R> query) {
        if (query == TemporalQueries.zoneId() || query == TemporalQueries.chronology() || query == TemporalQueries.precision()) {
          return (R) ThaiBuddhistChronology.INSTANCE;
        }
        return query.queryFrom(this);
      }

    };
    TemporalStub temporal= new TemporalStub();
    Period.ZERO.subtractFrom(temporal);
  }

  @Test
  public void testOfValidateNullChrono(){
    class TemporalStub implements Temporal {
      @Override
      public boolean isSupported(TemporalUnit unit) {
        return false;
      }
      @Override
      public Temporal with(TemporalField field, long newValue) {
        return null;
      }
      @Override
      public Temporal plus(long amountToAdd, TemporalUnit unit) {
        return null;
      }
      @Override
      public long until(Temporal endExclusive, TemporalUnit unit) {
        return 0;
      }
      @Override
      public boolean isSupported(TemporalField field) {
        return false;
      }
      @Override
      public long getLong(TemporalField field) {
        return 0;
      }
      @Override
      public <R> R query(TemporalQuery<R> query) {
        if (query == TemporalQueries.zoneId() || query == TemporalQueries.chronology() || query == TemporalQueries.precision()) {
          return null;
        }
        return query.queryFrom(this);
      }
    };
    TemporalStub temporal= new TemporalStub();
    Period.ZERO.addTo(temporal);
  }

  // Test if this period is equal to another period
  @Test
  public void testOfObjectInstanceOfPeriodChangedYear() {
    Period a = Period.of(1996, 4, 20);
    Period b = Period.from(Period.of(1994, 4, 20));
    Assert.assertNotEquals(a,b);
  }

  @Test
  public void testOfObjectInstanceOfPeriodChangedMonth() {
    Period a = Period.of(1996, 4, 20);
    Period b = Period.from(Period.of(1996, 6, 20));
    Assert.assertNotEquals(a,b);
  }

  @Test
  public void testOfObjectInstanceOfPeriodChangedDay() {
    Period a = Period.of(1996, 4, 20);
    Period b = Period.from(Period.of(1996, 4, 10));
    Assert.assertNotEquals(a,b);
  }

  @Test
  public void testOfObjectNotInstanceOfPeriod() {
    Period p = Period.of(1, 3, 0);
    Assert.assertNotEquals(p,null);
  }

  // Test for toString
  @Test
  public void TestOfToStringZero() {
    Period p = Period.ZERO;
    Assert.assertEquals("P0D", p.toString());
  }

  @Test
  public void TestOfToStringYZero() {
    Period p = Period.of(0,4,20);
    Assert.assertEquals("P4M20D", p.toString());
  }

  @Test
  public void TestOfToStringMZero() {
    Period p = Period.of(1996,0,20);
    Assert.assertEquals("P1996Y20D", p.toString());
  }

  @Test
  public void TestOfToStringDZero() {
    Period p = Period.of(1996,4,0);
    Assert.assertEquals("P1996Y4M", p.toString());
  }

  private void PAssert(Period p, int y, int m, int d) {
    Assert.assertEquals(y, p.getYears());
    Assert.assertEquals(m, p.getMonths());
    Assert.assertEquals(d, p.getDays());
  }


}





