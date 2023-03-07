
package cn.ac.amss.semanticweb.lang;

public class Utils
{
  private Utils() {
  }

  public static final String escapeXml(String s) {
    return s.replaceAll("&", "&amp;")
            .replaceAll(">", "&gt;")
            .replaceAll("<", "&lt;")
            .replaceAll("\"", "&quot;")
            .replaceAll("'", "&apos;");
  }

  public static final String unescapeXml(String s) {
    return s.replaceAll("&amp;", "&")
            .replaceAll("&gt;", ">")
            .replaceAll("&lt", "<")
            .replaceAll("&quot;", "\"")
            .replaceAll("'", "&apos;");
  }
}
