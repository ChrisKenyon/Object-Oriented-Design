public class PackedDurationTest extends AbstractDurationTest {
  @Override
  protected AbstractDuration zero() {
    return new PackedDuration(0);
  }
}