package Hokify.quotes;

import org.overture.codegen.runtime.*;

@SuppressWarnings("all")
public class MandatorySchoolQuote {
  private static int hc = 0;
  private static MandatorySchoolQuote instance = null;

  public MandatorySchoolQuote() {

    if (Utils.equals(hc, 0)) {
      hc = super.hashCode();
    }
  }

  public static MandatorySchoolQuote getInstance() {

    if (Utils.equals(instance, null)) {
      instance = new MandatorySchoolQuote();
    }

    return instance;
  }

  public int hashCode() {

    return hc;
  }

  public boolean equals(final Object obj) {

    return obj instanceof MandatorySchoolQuote;
  }

  public String toString() {

    return "<MandatorySchool>";
  }
}
