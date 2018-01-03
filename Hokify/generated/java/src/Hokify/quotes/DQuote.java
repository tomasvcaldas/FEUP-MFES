package Hokify.quotes;

import org.overture.codegen.runtime.*;

@SuppressWarnings("all")
public class DQuote {
  private static int hc = 0;
  private static DQuote instance = null;

  public DQuote() {

    if (Utils.equals(hc, 0)) {
      hc = super.hashCode();
    }
  }

  public static DQuote getInstance() {

    if (Utils.equals(instance, null)) {
      instance = new DQuote();
    }

    return instance;
  }

  public int hashCode() {

    return hc;
  }

  public boolean equals(final Object obj) {

    return obj instanceof DQuote;
  }

  public String toString() {

    return "<D>";
  }
}
