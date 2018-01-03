package Hokify.quotes;

import org.overture.codegen.runtime.*;

@SuppressWarnings("all")
public class HighSchoolQuote {
  private static int hc = 0;
  private static HighSchoolQuote instance = null;

  public HighSchoolQuote() {

    if (Utils.equals(hc, 0)) {
      hc = super.hashCode();
    }
  }

  public static HighSchoolQuote getInstance() {

    if (Utils.equals(instance, null)) {
      instance = new HighSchoolQuote();
    }

    return instance;
  }

  public int hashCode() {

    return hc;
  }

  public boolean equals(final Object obj) {

    return obj instanceof HighSchoolQuote;
  }

  public String toString() {

    return "<HighSchool>";
  }
}
