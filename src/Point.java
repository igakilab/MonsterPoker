public class Point {
  double value;
  double amp;

  public Point() {
    reset();
  }

  public void reset() {
    value = 0;
    amp = 1;
  }

  public double addValue(double addition) {
    this.value += addition;
    return this.value;
  }

  public double getAmpValue() {
    return value * amp;
  }

  public double getValue() {
    return value;
  }

  public double getAmp() {
    return value;
  }

  public void setValue(double x) {
    value = x;
  }

  public void setAmp(double x) {
    amp = x;
  }
}
