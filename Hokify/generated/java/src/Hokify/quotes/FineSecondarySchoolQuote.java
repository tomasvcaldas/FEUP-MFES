package Hokify.quotes;

import org.overture.codegen.runtime.*;

@SuppressWarnings("all")
public class FineSecondarySchoolQuote {
  private static int hc = 0;
  private static FineSecondarySchoolQuote instance = null;

  public FineSecondarySchoolQuote() {

    if (Utils.equals(hc, 0)) {
      hc = super.hashCode();
    }
  }

  public static FineSecondarySchoolQuote getInstance() {

    if (Utils.equals(instance, null)) {
      instance = new FineSecondarySchoolQuote();
    }

    return instance;
  }

  public int hashCode() {

    return hc;
  }

  public boolean equals(final Object obj) {

    return obj instanceof FineSecondarySchoolQuote;
  }

  public String toString() {

    return "<FineSecondarySchool>";
  }
}
