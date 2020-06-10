package d3e.core;

import org.hibernate.boot.model.TypeContributions;
import org.hibernate.boot.model.TypeContributor;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.type.AbstractSingleColumnStandardBasicType;
import org.hibernate.type.descriptor.WrapperOptions;
import org.hibernate.type.descriptor.java.AbstractTypeDescriptor;
import org.hibernate.type.descriptor.sql.VarcharTypeDescriptor;

public class BlockStringTypeContributor implements TypeContributor {

  public BlockStringTypeContributor() {}

  @Override
  public void contribute(TypeContributions typeContributions, ServiceRegistry serviceRegistry) {
    typeContributions.contributeType(BlockStringType.INSTANCE);
  }

  public static class BlockStringType extends AbstractSingleColumnStandardBasicType<BlockString> {

    private static final long serialVersionUID = 1L;
    public static final BlockStringType INSTANCE = new BlockStringType();

    public BlockStringType() {
      super(VarcharTypeDescriptor.INSTANCE, BlockStringTypeDescriptor.INSTANCE);
    }

    @Override
    public String getName() {
      return "d3e.core.BlockString";
    }
  }

  public static class BlockStringTypeDescriptor extends AbstractTypeDescriptor<BlockString> {

    private static final long serialVersionUID = 1L;
    public static final BlockStringTypeDescriptor INSTANCE = new BlockStringTypeDescriptor();

    public BlockStringTypeDescriptor() {
      super(BlockString.class);
    }

    @Override
    public BlockString fromString(String string) {
      return new BlockString(string);
    }

    @Override
    public <X> X unwrap(BlockString value, Class<X> type, WrapperOptions options) {
      if (value == null) {
        return null;
      }
      if (String.class.isAssignableFrom(type)) {
        return (X) value.getContent();
      }
      throw unknownUnwrap(type);
    }

    @Override
    public <X> BlockString wrap(X value, WrapperOptions options) {
      if (value == null) {
        return null;
      }
      if (String.class.isInstance(value)) {
        return new BlockString((String) value);
      }
      throw unknownWrap(value.getClass());
    }
  }
}
