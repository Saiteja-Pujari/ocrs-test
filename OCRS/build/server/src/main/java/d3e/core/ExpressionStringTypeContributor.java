package d3e.core;

import org.hibernate.boot.model.TypeContributions;
import org.hibernate.boot.model.TypeContributor;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.type.AbstractSingleColumnStandardBasicType;
import org.hibernate.type.descriptor.WrapperOptions;
import org.hibernate.type.descriptor.java.AbstractTypeDescriptor;
import org.hibernate.type.descriptor.sql.VarcharTypeDescriptor;

public class ExpressionStringTypeContributor implements TypeContributor {

  public ExpressionStringTypeContributor() {}

  @Override
  public void contribute(TypeContributions typeContributions, ServiceRegistry serviceRegistry) {
    typeContributions.contributeType(ExpressionStringType.INSTANCE);
  }

  public static class ExpressionStringType
      extends AbstractSingleColumnStandardBasicType<ExpressionString> {

    private static final long serialVersionUID = 1L;
    public static final ExpressionStringType INSTANCE = new ExpressionStringType();

    public ExpressionStringType() {
      super(VarcharTypeDescriptor.INSTANCE, ExpressionStringTypeDescriptor.INSTANCE);
    }

    @Override
    public String getName() {
      return "d3e.core.ExpressionString";
    }
  }

  public static class ExpressionStringTypeDescriptor
      extends AbstractTypeDescriptor<ExpressionString> {

    private static final long serialVersionUID = 1L;
    public static final ExpressionStringTypeDescriptor INSTANCE =
        new ExpressionStringTypeDescriptor();

    public ExpressionStringTypeDescriptor() {
      super(ExpressionString.class);
    }

    @Override
    public ExpressionString fromString(String string) {
      return new ExpressionString(string);
    }

    @Override
    public <X> X unwrap(ExpressionString value, Class<X> type, WrapperOptions options) {
      if (value == null) {
        return null;
      }
      if (String.class.isAssignableFrom(type)) {
        return (X) value.getContent();
      }
      throw unknownUnwrap(type);
    }

    @Override
    public <X> ExpressionString wrap(X value, WrapperOptions options) {
      if (value == null) {
        return null;
      }
      if (String.class.isInstance(value)) {
        return new ExpressionString((String) value);
      }
      throw unknownWrap(value.getClass());
    }
  }
}
