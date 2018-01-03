package Hokify;

import java.util.*;
import org.overture.codegen.runtime.*;

@SuppressWarnings("all")
public class PastJob extends Job {
  private Job.Date startDate;
  private Job.Date endDate;

  public void cg_init_PastJob_1(
      final String c, final String p, final Job.Date sDate, final Job.Date eDate) {

    startDate = Utils.copy(sDate);
    endDate = Utils.copy(eDate);
    cg_init_Job_1(c, p);
  }

  public PastJob(final String c, final String p, final Job.Date sDate, final Job.Date eDate) {

    cg_init_PastJob_1(c, p, Utils.copy(sDate), Utils.copy(eDate));
  }

  public Job.Date getStartDate() {

    return Utils.copy(startDate);
  }

  public Job.Date getEndDate() {

    return Utils.copy(endDate);
  }

  public PastJob() {}

  public static Boolean validDates(final Job.Date d1, final Job.Date d2) {

    Boolean orResult_4 = false;

    if (Utils.equals(d2, null)) {
      orResult_4 = true;
    } else {
      Boolean orResult_5 = false;

      if (d2.year.longValue() > d1.year.longValue()) {
        orResult_5 = true;
      } else {
        Boolean orResult_6 = false;

        Boolean andResult_13 = false;

        if (Utils.equals(d2.year, d1.year)) {
          if (d2.month.longValue() > d1.month.longValue()) {
            andResult_13 = true;
          }
        }

        if (andResult_13) {
          orResult_6 = true;
        } else {
          Boolean andResult_14 = false;

          if (Utils.equals(d2.year, d1.year)) {
            Boolean andResult_15 = false;

            if (Utils.equals(d2.month, d1.month)) {
              if (d2.day.longValue() > d1.day.longValue()) {
                andResult_15 = true;
              }
            }

            if (andResult_15) {
              andResult_14 = true;
            }
          }

          orResult_6 = andResult_14;
        }

        orResult_5 = orResult_6;
      }

      orResult_4 = orResult_5;
    }

    return orResult_4;
  }

  public static Boolean areEqual(final PastJob j1, final PastJob j2) {

    Boolean andResult_16 = false;

    if (Utils.equals(j1.startDate, j2.startDate)) {
      Boolean andResult_17 = false;

      if (Utils.equals(j1.endDate, j2.endDate)) {
        Boolean andResult_18 = false;

        if (Utils.equals(j1.company, j2.company)) {
          if (Utils.equals(j1.position, j2.position)) {
            andResult_18 = true;
          }
        }

        if (andResult_18) {
          andResult_17 = true;
        }
      }

      if (andResult_17) {
        andResult_16 = true;
      }
    }

    return andResult_16;
  }

  public String toString() {

    return "PastJob{"
        + "startDate := "
        + Utils.toString(startDate)
        + ", endDate := "
        + Utils.toString(endDate)
        + "}";
  }
}
