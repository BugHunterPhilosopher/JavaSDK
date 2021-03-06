/*
 * Copyright (C) 2016 - present by OpenGamma Inc. and the OpenGamma group of companies
 *
 * Please see distribution for license.
 */
package com.opengamma.sdk.margin;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

import org.joda.beans.Bean;
import org.joda.beans.BeanBuilder;
import org.joda.beans.ImmutableBean;
import org.joda.beans.JodaBeanUtils;
import org.joda.beans.MetaBean;
import org.joda.beans.MetaProperty;
import org.joda.beans.gen.BeanDefinition;
import org.joda.beans.gen.PropertyDefinition;
import org.joda.beans.impl.direct.DirectMetaBean;
import org.joda.beans.impl.direct.DirectMetaProperty;
import org.joda.beans.impl.direct.DirectMetaPropertyMap;
import org.joda.beans.impl.direct.DirectPrivateBeanBuilder;

/**
 * CCP information from the service.
 */
@BeanDefinition(builderScope = "private", metaScope = "private", factoryName = "of")
public final class CcpsResult implements ImmutableBean {

  /**
   * The list of available CCPs, may be empty.
   * <p>
   * This is returned in string form in case the server returns a CCP that is not known by the client.
   */
  @PropertyDefinition(validate = "notNull", alias = "ccps")
  private final List<String> ccpNames;

  //-------------------------------------------------------------------------
  /**
   * Gets the list of available CCPs, may be empty.
   * 
   * @return the list of available CCPs
   */
  public List<Ccp> getCcps() {
    List<Ccp> ccps = new ArrayList<>();
    for (String name : ccpNames) {
      try {
        ccps.add(Ccp.of(name));
      } catch (RuntimeException ex) {
        // ignore, server may be newer than client
      }
    }
    return ccps;
  }

  /**
   * Checks if the CCP is available.
   * 
   * @param ccpName  the CCP to check
   * @return true if available to uses
   */
  public boolean isCcpAvailable(String ccpName) {
    return ccpNames.stream().anyMatch(ccpName::equalsIgnoreCase);
  }

  /**
   * Checks if the CCP is available.
   * 
   * @param ccp  the CCP to check
   * @return true if available to uses
   */
  public boolean isCcpAvailable(Ccp ccp) {
    return ccpNames.stream().anyMatch(x -> ccp.name().equalsIgnoreCase(x));
  }

  //------------------------- AUTOGENERATED START -------------------------
  /**
   * The meta-bean for {@code CcpsResult}.
   * @return the meta-bean, not null
   */
  public static MetaBean meta() {
    return CcpsResult.Meta.INSTANCE;
  }

  static {
    MetaBean.register(CcpsResult.Meta.INSTANCE);
  }

  /**
   * Obtains an instance.
   * @param ccpNames  the value of the property, not null
   * @return the instance
   */
  public static CcpsResult of(
      List<String> ccpNames) {
    return new CcpsResult(
      ccpNames);
  }

  private CcpsResult(
      List<String> ccpNames) {
    JodaBeanUtils.notNull(ccpNames, "ccpNames");
    this.ccpNames = Collections.unmodifiableList(new ArrayList<>(ccpNames));
  }

  @Override
  public MetaBean metaBean() {
    return CcpsResult.Meta.INSTANCE;
  }

  //-----------------------------------------------------------------------
  /**
   * Gets the list of available CCPs, may be empty.
   * <p>
   * This is returned in string form in case the server returns a CCP that is not known by the client.
   * @return the value of the property, not null
   */
  public List<String> getCcpNames() {
    return ccpNames;
  }

  //-----------------------------------------------------------------------
  @Override
  public boolean equals(Object obj) {
    if (obj == this) {
      return true;
    }
    if (obj != null && obj.getClass() == this.getClass()) {
      CcpsResult other = (CcpsResult) obj;
      return JodaBeanUtils.equal(ccpNames, other.ccpNames);
    }
    return false;
  }

  @Override
  public int hashCode() {
    int hash = getClass().hashCode();
    hash = hash * 31 + JodaBeanUtils.hashCode(ccpNames);
    return hash;
  }

  @Override
  public String toString() {
    StringBuilder buf = new StringBuilder(64);
    buf.append("CcpsResult{");
    buf.append("ccpNames").append('=').append(JodaBeanUtils.toString(ccpNames));
    buf.append('}');
    return buf.toString();
  }

  //-----------------------------------------------------------------------
  /**
   * The meta-bean for {@code CcpsResult}.
   */
  private static final class Meta extends DirectMetaBean {
    /**
     * The singleton instance of the meta-bean.
     */
    static final Meta INSTANCE = new Meta();

    /**
     * The meta-property for the {@code ccpNames} property.
     */
    @SuppressWarnings({"unchecked", "rawtypes" })
    private final MetaProperty<List<String>> ccpNames = DirectMetaProperty.ofImmutable(
        this, "ccpNames", CcpsResult.class, (Class) List.class);
    /**
     * The meta-properties.
     */
    private final Map<String, MetaProperty<?>> metaPropertyMap$ = new DirectMetaPropertyMap(
        this, null,
        "ccpNames");

    /**
     * Restricted constructor.
     */
    private Meta() {
    }

    @Override
    protected MetaProperty<?> metaPropertyGet(String propertyName) {
      switch (propertyName.hashCode()) {
        case 1689579800:  // ccpNames
        case 3048035:  // ccps (alias)
          return ccpNames;
      }
      return super.metaPropertyGet(propertyName);
    }

    @Override
    public BeanBuilder<? extends CcpsResult> builder() {
      return new CcpsResult.Builder();
    }

    @Override
    public Class<? extends CcpsResult> beanType() {
      return CcpsResult.class;
    }

    @Override
    public Map<String, MetaProperty<?>> metaPropertyMap() {
      return metaPropertyMap$;
    }

    //-----------------------------------------------------------------------
    @Override
    protected Object propertyGet(Bean bean, String propertyName, boolean quiet) {
      switch (propertyName.hashCode()) {
        case 1689579800:  // ccpNames
        case 3048035:  // ccps (alias)
          return ((CcpsResult) bean).getCcpNames();
      }
      return super.propertyGet(bean, propertyName, quiet);
    }

    @Override
    protected void propertySet(Bean bean, String propertyName, Object newValue, boolean quiet) {
      metaProperty(propertyName);
      if (quiet) {
        return;
      }
      throw new UnsupportedOperationException("Property cannot be written: " + propertyName);
    }

  }

  //-----------------------------------------------------------------------
  /**
   * The bean-builder for {@code CcpsResult}.
   */
  private static final class Builder extends DirectPrivateBeanBuilder<CcpsResult> {

    private List<String> ccpNames = Collections.emptyList();

    /**
     * Restricted constructor.
     */
    private Builder() {
    }

    //-----------------------------------------------------------------------
    @Override
    public Object get(String propertyName) {
      switch (propertyName.hashCode()) {
        case 1689579800:  // ccpNames
        case 3048035:  // ccps (alias)
          return ccpNames;
        default:
          throw new NoSuchElementException("Unknown property: " + propertyName);
      }
    }

    @SuppressWarnings("unchecked")
    @Override
    public Builder set(String propertyName, Object newValue) {
      switch (propertyName.hashCode()) {
        case 1689579800:  // ccpNames
        case 3048035:  // ccps (alias)
          this.ccpNames = (List<String>) newValue;
          break;
        default:
          throw new NoSuchElementException("Unknown property: " + propertyName);
      }
      return this;
    }

    @Override
    public CcpsResult build() {
      return new CcpsResult(
          ccpNames);
    }

    //-----------------------------------------------------------------------
    @Override
    public String toString() {
      StringBuilder buf = new StringBuilder(64);
      buf.append("CcpsResult.Builder{");
      buf.append("ccpNames").append('=').append(JodaBeanUtils.toString(ccpNames));
      buf.append('}');
      return buf.toString();
    }

  }

  //-------------------------- AUTOGENERATED END --------------------------
}
