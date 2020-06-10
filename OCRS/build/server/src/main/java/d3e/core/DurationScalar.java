package d3e.core;

import java.time.Duration;
import org.springframework.stereotype.Component;
import graphql.schema.Coercing;
import graphql.schema.CoercingParseLiteralException;
import graphql.schema.CoercingParseValueException;
import graphql.schema.CoercingSerializeException;
import graphql.schema.GraphQLScalarType;

@Component
public class DurationScalar extends GraphQLScalarType {
  @SuppressWarnings("deprecation")
  public DurationScalar() {
    super(
        "Duration",
        "",
        new Coercing<Duration, String>() {
          @Override
          public String serialize(Object dataFetcherResult) throws CoercingSerializeException {
            return ((Duration) dataFetcherResult).toString();
          }

          @Override
          public Duration parseValue(Object input) throws CoercingParseValueException {
            return DurationExt.fromString((String) input);
          }

          @Override
          public Duration parseLiteral(Object input) throws CoercingParseLiteralException {
            return DurationExt.fromString((String) input);
          }
        });
  }
}
