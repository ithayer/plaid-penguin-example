/**
 * Autogenerated by Thrift
 *
 * DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
 */
package example.simple;


import java.util.Map;
import java.util.HashMap;
import org.apache.thrift.TEnum;

public enum OpType implements org.apache.thrift.TEnum {
  SLEEP(1);

  private final int value;

  private OpType(int value) {
    this.value = value;
  }

  /**
   * Get the integer value of this enum value, as defined in the Thrift IDL.
   */
  public int getValue() {
    return value;
  }

  /**
   * Find a the enum type by its integer value, as defined in the Thrift IDL.
   * @return null if the value is not found.
   */
  public static OpType findByValue(int value) { 
    switch (value) {
      case 1:
        return SLEEP;
      default:
        return null;
    }
  }
}
