/*
 * Copyright 2016 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */

package com.google.googlejavaformat.java;

import com.google.errorprone.annotations.Immutable;

/**
 * Options for a google-java-format invocation.
 *
 * <p>Like gofmt, the google-java-format CLI exposes <em>no</em> configuration options (aside from
 * {@code --aosp}).
 *
 * <p>The goal of google-java-format is to provide consistent formatting, and to free developers
 * from arguments over style choices. It is an explicit non-goal to support developers' individual
 * preferences, and in fact it would work directly against our primary goals.
 */
@Immutable
public class JavaFormatterOptions {

  public enum Style {
    /** The default Google Java Style configuration. */
    GOOGLE(1),

    /** The AOSP-compliant configuration. */
    AOSP(2);

    private final int indentationMultiplier;

    Style(int indentationMultiplier) {
      this.indentationMultiplier = indentationMultiplier;
    }

    int indentationMultiplier() {
      return indentationMultiplier;
    }
  }

  public enum SingleLineJavadocStyle {
    /***
     * Single line Javadoc looks like:
     *
     * <pre>
     * <code>
     *   /** A single line. **&#47;
     *   public void doSomething() {}
     * </code>
     * </pre>
     */
    SINGLE_LINE,

    /***
     * Single line Javadoc looks like:
     *
     * <pre>
     * <code>
     *   /**
     *    * A single line.
     *    **&#47;
     *   public void doSomething() {}
     * </code>
     * </pre>
     */
    MULTI_LINE;
  }

  private final Style style;
  private final boolean formatJavadoc;
  private final SingleLineJavadocStyle singleLineJavadocStyle;
  private final boolean spaceInsideEmptyBlock;

  private JavaFormatterOptions(Builder builder) {
    style = builder.style;
    formatJavadoc = builder.formatJavadoc;
    singleLineJavadocStyle = builder.singleLineJavadocStyle;
    spaceInsideEmptyBlock = builder.spaceInsideEmptyBlock;
  }

  /** Returns the multiplier for the unit of indent. */
  public int indentationMultiplier() {
    return style.indentationMultiplier();
  }

  boolean formatJavadoc() {
    return formatJavadoc;
  }

  /** Returns what style to use when formatting Javadoc comments that fit in a single line. */
  public SingleLineJavadocStyle singleLineJavadocStyle() {
    return singleLineJavadocStyle;
  }

  /**
   * Returns whether there should be a space inside of an empty block.
   *
   * <p>This is intended to work well with Checkstyle's WhiteSpaceAround check.
   *
   * @see <a
   *     href="https://checkstyle.sourceforge.io/apidocs/com/puppycrawl/tools/checkstyle/checks/whitespace/WhitespaceAroundCheck.html">WhitespaceAroundCheck</a>
   */
  public boolean spaceInsideEmptyBlock() {
    return spaceInsideEmptyBlock;
  }

  /** Returns the code style. */
  public Style style() {
    return style;
  }

  /** Returns the default formatting options. */
  public static JavaFormatterOptions defaultOptions() {
    return builder().build();
  }

  /** Returns a builder for {@link JavaFormatterOptions}. */
  public static Builder builder() {
    return new Builder();
  }

  /** A builder for {@link JavaFormatterOptions}. */
  public static class Builder {
    private Style style = Style.GOOGLE;
    private boolean formatJavadoc = true;
    private SingleLineJavadocStyle singleLineJavadocStyle = SingleLineJavadocStyle.SINGLE_LINE;
    private boolean spaceInsideEmptyBlock;

    private Builder() {}

    public Builder style(Style style) {
      this.style = style;
      return this;
    }

    Builder formatJavadoc(boolean formatJavadoc) {
      this.formatJavadoc = formatJavadoc;
      return this;
    }

    /** Sets what style to use when formatting Javadoc comments that fit in a single line. */
    public Builder singleLineJavadocStyle(SingleLineJavadocStyle singleLineJavadocStyle) {
      this.singleLineJavadocStyle = singleLineJavadocStyle;
      return this;
    }

    /** Returns whether there should be a space inside of an empty block. */
    public Builder spaceInsideEmptyBlock(boolean spaceInsideEmptyBlock) {
      this.spaceInsideEmptyBlock = spaceInsideEmptyBlock;
      return this;
    }

    public JavaFormatterOptions build() {
      return new JavaFormatterOptions(this);
    }
  }
}
