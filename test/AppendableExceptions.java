import java.io.IOException;

/**
 * A class that implements appendable that throws input/output exception to test if controller
 * catches that and throws IllegalStateException.
 */
public class AppendableExceptions implements Appendable {

  @Override
  public Appendable append(CharSequence csq) throws IOException {
    throw new IOException();
  }

  @Override
  public Appendable append(CharSequence csq, int start, int end) throws IOException {
    throw new IOException();
  }

  @Override
  public Appendable append(char c) throws IOException {
    throw new IOException();
  }
}
