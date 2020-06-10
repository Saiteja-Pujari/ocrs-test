package d3e.core;

import org.springframework.stereotype.Component;

import graphql.schema.Coercing;
import graphql.schema.CoercingParseLiteralException;
import graphql.schema.CoercingParseValueException;
import graphql.schema.CoercingSerializeException;
import graphql.schema.GraphQLScalarType;

@Component
public class BlockStringScalar extends GraphQLScalarType {

  @SuppressWarnings("deprecation")
  public BlockStringScalar() {
    super(
        "BlockString",
        "",
        new Coercing<BlockString, String>() {

          @Override
          public String serialize(Object dataFetcherResult) throws CoercingSerializeException {
            return ((BlockString) dataFetcherResult).getContent();
          }

          @Override
          public BlockString parseValue(Object input) throws CoercingParseValueException {
            return new BlockString((String) input);
          }

          @Override
          public BlockString parseLiteral(Object input) throws CoercingParseLiteralException {
            return new BlockString((String) input);
          }
        });
  }
}
