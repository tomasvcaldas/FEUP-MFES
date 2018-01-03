package Hokify.quotes;

import org.overture.codegen.runtime.*;

@SuppressWarnings("all")
public class BQuote {
  private static int hc = 0;
  private static BQuote instance = null;

  public BQuote() {

    if (Utils.equals(hc, 0)) {
      hc = super.hashCode();
    }
  }

  public static BQuote getInstance() {

    if (Utils.equals(instance, null)) {
      instance = new BQuote();
    }

    return instance;
  }

  public int hashCode() {

    return hc;
  }

  public boolean equals(final Object obj) {

    return obj instanceof BQuote;
  }

  public String toString() {

    return "<B>";
  }
}
