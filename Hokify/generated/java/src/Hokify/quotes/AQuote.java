package Hokify.quotes;

import org.overture.codegen.runtime.*;

@SuppressWarnings("all")
public class AQuote {
  private static int hc = 0;
  private static AQuote instance = null;

  public AQuote() {

    if (Utils.equals(hc, 0)) {
      hc = super.hashCode();
    }
  }

  public static AQuote getInstance() {

    if (Utils.equals(instance, null)) {
      instance = new AQuote();
    }

    return instance;
  }

  public int hashCode() {

    return hc;
  }

  public boolean equals(final Object obj) {

    return obj instanceof AQuote;
  }

  public String toString() {

    return "<A>";
  }
}
