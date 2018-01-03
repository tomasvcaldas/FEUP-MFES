package Hokify.quotes;

import org.overture.codegen.runtime.*;

@SuppressWarnings("all")
public class TeachingQuote {
  private static int hc = 0;
  private static TeachingQuote instance = null;

  public TeachingQuote() {

    if (Utils.equals(hc, 0)) {
      hc = super.hashCode();
    }
  }

  public static TeachingQuote getInstance() {

    if (Utils.equals(instance, null)) {
      instance = new TeachingQuote();
    }

    return instance;
  }

  public int hashCode() {

    return hc;
  }

  public boolean equals(final Object obj) {

    return obj instanceof TeachingQuote;
  }

  public String toString() {

    return "<Teaching>";
  }
}
