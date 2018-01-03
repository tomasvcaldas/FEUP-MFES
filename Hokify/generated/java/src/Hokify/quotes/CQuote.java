package Hokify.quotes;

import org.overture.codegen.runtime.*;

@SuppressWarnings("all")
public class CQuote {
  private static int hc = 0;
  private static CQuote instance = null;

  public CQuote() {

    if (Utils.equals(hc, 0)) {
      hc = super.hashCode();
    }
  }

  public static CQuote getInstance() {

    if (Utils.equals(instance, null)) {
      instance = new CQuote();
    }

    return instance;
  }

  public int hashCode() {

    return hc;
  }

  public boolean equals(final Object obj) {

    return obj instanceof CQuote;
  }

  public String toString() {

    return "<C>";
  }
}
