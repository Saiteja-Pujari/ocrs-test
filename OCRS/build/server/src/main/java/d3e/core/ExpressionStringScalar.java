package d3e.core;

import org.springframework.stereotype.Component;

import graphql.schema.Coercing;
import graphql.schema.CoercingParseLiteralException;
import graphql.schema.CoercingParseValueException;
import graphql.schema.CoercingSerializeException;
import graphql.schema.GraphQLScalarType;

@Component
public class ExpressionStringScalar extends GraphQLScalarType {

  @SuppressWarnings("deprecation")
  public ExpressionStringScalar() {
    super(
        "ExpressionString",
        "",
        new Coercing<ExpressionString, String>() {

          @Override
          public String serialize(Object dataFetcherResult) throws CoercingSerializeException {
            return ((ExpressionString) dataFetcherResult).getContent();
          }

          @Override
          public ExpressionString parseValue(Object input) throws CoercingParseValueException {
            return new ExpressionString((String) input);
          }

          @Override
          public ExpressionString parseLiteral(Object input) throws CoercingParseLiteralException {
            return new ExpressionString((String) input);
          }
        });
  }
}
