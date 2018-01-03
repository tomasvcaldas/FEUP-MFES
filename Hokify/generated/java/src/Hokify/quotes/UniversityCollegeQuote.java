package Hokify.quotes;

import org.overture.codegen.runtime.*;

@SuppressWarnings("all")
public class UniversityCollegeQuote {
  private static int hc = 0;
  private static UniversityCollegeQuote instance = null;

  public UniversityCollegeQuote() {

    if (Utils.equals(hc, 0)) {
      hc = super.hashCode();
    }
  }

  public static UniversityCollegeQuote getInstance() {

    if (Utils.equals(instance, null)) {
      instance = new UniversityCollegeQuote();
    }

    return instance;
  }

  public int hashCode() {

    return hc;
  }

  public boolean equals(final Object obj) {

    return obj instanceof UniversityCollegeQuote;
  }

  public String toString() {

    return "<UniversityCollege>";
  }
}
