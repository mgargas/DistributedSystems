/**
 * Autogenerated by Thrift Compiler (0.9.1)
 *
 * DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
 *  @generated
 */
package com.mgargas.bank;


import java.util.Map;
import java.util.HashMap;
import org.apache.thrift.TEnum;

public enum Currency implements org.apache.thrift.TEnum {
  PLN(0),
  GBP(1),
  USD(2),
  CHF(3),
  EUR(4);

  private final int value;

  private Currency(int value) {
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
  public static Currency findByValue(int value) { 
    switch (value) {
      case 0:
        return PLN;
      case 1:
        return GBP;
      case 2:
        return USD;
      case 3:
        return CHF;
      case 4:
        return EUR;
      default:
        return null;
    }
  }
}
