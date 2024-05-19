package ru.sfu.zooshop.utility;

import static org.apache.commons.lang3.StringUtils.strip;
import static org.apache.commons.text.WordUtils.capitalizeFully;

public final class SystemUtility {
  public static String convert(String text) {
    if (text == null) {
      return null;
    }
    return capitalizeFully(strip(text));
  }
}
