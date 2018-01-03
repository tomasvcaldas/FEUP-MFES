package Hokify;

import java.util.*;
import org.overture.codegen.runtime.*;

@SuppressWarnings("all")
public class Job {
  protected String company;
  protected String position;

  public void cg_init_Job_1(final String c, final String p) {

    company = c;
    position = p;
  }

  public Job(final String c, final String p) {

    cg_init_Job_1(c, p);
  }

  public String getCompany() {

    return company;
  }

  public String getPosition() {

    return position;
  }

  public Job() {}

  public String toString() {

    return "Job{"
        + "company := "
        + Utils.toString(company)
        + ", position := "
        + Utils.toString(position)
        + "}";
  }

  public static class Date implements Record {
    public Number year;
    public Number month;
    public Number day;

    public Date(final Number _year, final Number _month, final Number _day) {

      year = _year;
      month = _month;
      day = _day;
    }

    public boolean equals(final Object obj) {

      if (!(obj instanceof Date)) {
        return false;
      }

      Date other = ((Date) obj);

      return (Utils.equals(year, other.year))
          && (Utils.equals(month, other.month))
          && (Utils.equals(day, other.day));
    }

    public int hashCode() {

      return Utils.hashCode(year, month, day);
    }

    public Date copy() {

      return new Date(year, month, day);
    }

    public String toString() {

      return "mk_Job`Date" + Utils.formatFields(year, month, day);
    }
  }

  public static Boolean inv_Date(final Date d) {

    Boolean orResult_1 = false;

    if (Utils.equals(Utils.mod(d.year.longValue(), 400L), 0L)) {
      orResult_1 = true;
    } else {
      Boolean andResult_2 = false;

      if (!(Utils.equals(Utils.mod(d.year.longValue(), 100L), 0L))) {
        if (!(Utils.equals(Utils.mod(d.year.longValue(), 4L), 0L))) {
          andResult_2 = true;
        }
      }

      orResult_1 = andResult_2;
    }

    if (orResult_1) {
      Boolean andResult_3 = false;

      if (d.month.longValue() < 12L) {
        if (d.day.longValue()
            <= ((Number)
                    Utils.get(
                        SeqUtil.seq(31L, 29L, 31L, 30L, 31L, 30L, 31L, 31L, 30L, 31L, 30L, 31L),
                        d.month))
                .longValue()) {
          andResult_3 = true;
        }
      }

      return andResult_3;

    } else {
      Boolean andResult_4 = false;

      if (d.month.longValue() < 12L) {
        if (d.day.longValue()
            <= ((Number)
                    Utils.get(
                        SeqUtil.seq(31L, 28L, 31L, 30L, 31L, 30L, 31L, 31L, 30L, 31L, 30L, 31L),
                        d.month))
                .longValue()) {
          andResult_4 = true;
        }
      }

      return andResult_4;
    }
  }
}
