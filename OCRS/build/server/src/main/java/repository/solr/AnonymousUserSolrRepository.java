package repository.solr;

@org.springframework.stereotype.Repository
public interface AnonymousUserSolrRepository
    extends org.springframework.data.solr.repository.SolrCrudRepository<
        models.AnonymousUser, Long> {}
