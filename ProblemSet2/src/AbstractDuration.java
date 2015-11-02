/**
 * Abstract base class for durations. Provides functionality common to
 * both representational subclasses, including comparisons, hashing,
 * addition, subtraction, and the ability to construct new durations of
 * the same class as a given instance.
 */
abstract class AbstractDuration implements Duration {
  protected static final int SECS_IN_DAY = 24 * 60 * 60;

  /**
   * Constructs a new duration having the same class as {@code this}.
   * @param seconds length of the new duration in seconds (non-negative)
   * @return the new duration
   */
  protected abstract Duration fromSeconds(long seconds);

  /**
   * Constructs a new duration having the same class as {@code this}.
   * @param days the days component of the new duration (non-negative)
   * @param hours the hours component of the new duration (non-negative)
   * @param minutes the minutes component of the new duration (non-negative)
   * @param seconds the seconds component of the new duration (non-negative)
   * @return the new duration
   */
  protected abstract Duration fromDHMS(long days, int hours,
                                       int minutes, int seconds);

  /**
   * This helper method takes the character after the percent symbol in a string
   * It will compare the character against a switch case to determine
   * which component of the duration to replace the modifier with
   * @param nextChar the character after a percent symbol in the specifier
   * @return the duration component or by default the next character
   */
  private String matchSpecifier(char nextChar){
    switch(nextChar){
      case 'd':
        return String.valueOf(getDays());
      case 'D':
        String days = String.valueOf(getDays());
        if (days.length()==1){
          return "0"+days;
        } else{
          return days;
        }
      case 'h':
        return String.valueOf(getHours());
      case 'H':
        String hours = String.valueOf(getHours());
        if (hours.length()==1){
          return "0"+hours;
        } else{
          return hours;
        }
      case 'm':
        return String.valueOf(getMinutes());
      case 'M':
        String minutes = String.valueOf(getMinutes());
        if (minutes.length()==1){
          return "0"+minutes;
        } else{
          return minutes;
        }
      case 's':
        return String.valueOf(getSeconds());
      case 'S':
        String seconds = String.valueOf(getSeconds());
        if (seconds.length()==1){
          return "0"+seconds;
        } else{
          return seconds;
        }
      case '%':
        return "%";
      default:
        return String.valueOf(nextChar);
    }

  }

  @Override
  public String format(String template) {
    String formatted = "";
    for (int index = 0; index<template.length(); index++){
      if (template.charAt(index) == '%' && index != template.length()-1){
        index++;
        formatted += matchSpecifier(template.charAt(index));
      } else
        formatted += template.charAt(index);
    }
    return formatted;
  }

  /**
   * Adds two durations. The result will have the same (dynamic) class
   * as {@code this}.
   * @param other the duration to add to {@code this}
   * @return the sum of the durations
   */
  @Override
  public Duration plus(Duration other) {
    long result = inSeconds() + other.inSeconds();

    if (result < 0) {
      throw new RuntimeException("Duration overflow");
    }

    return fromSeconds(result);
  }

  /**
   * Subtracts two durations. Returns the zero duration rather than
   * negative. The result will have the same (dynamic) class as {@code
   * this}.
   * @param other the duration to subtract from {@code this}
   * @return the difference of the durations (or zero if {@code this}
   * is shorter than {@code other}).
   */
  @Override
  public Duration minus(Duration other) {
    long result = inSeconds() - other.inSeconds();
    return fromSeconds(result < 0 ? 0 : result);
  }

  @Override
  public int compareTo(Duration other) {
    if (inSeconds() < other.inSeconds()) {
      return -1;
    }

    if (inSeconds() > other.inSeconds()) {
      return 1;
    }

    return 0;
  }

  @Override
  public boolean equals(Object other) {
    if (!(other instanceof Duration)) return false;
    return inSeconds() == ((Duration)other).inSeconds();
  }

  @Override
  public int hashCode() {
    return (int) (inSeconds() ^ (inSeconds() >>> 32));
  }
}

