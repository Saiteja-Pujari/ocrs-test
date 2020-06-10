package test;

import main.D3EStudio;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = D3EStudio.class)
@WebAppConfiguration
@TestPropertySource(
    properties = {
      "spring.autoconfigure.exclude=com.oembedler.moon.graphql.boot.GraphQLWebAutoConfiguration",
      "spring.autoconfigure.exclude=com.oembedler.moon.graphql.boot.GraphQLWebsocketAutoConfiguration",
      "spring.datasource.url=jdbc:h2:~/test;AUTO_SERVER=TRUE",
      "spring.jpa.hibernate.ddl-auto=create"
    })
public class D3EStudioTest {
  @Autowired ApplicationContext context;

  @Test
  public void contextLoads() {
    Assert.assertTrue("Startup failed", context != null);
  }
}
