package com.inter.car.backendchallenge.service;

import com.inter.car.backendchallenge.service.datafetcher.AllCarsByMakeFilterDataFetcher;
import com.inter.car.backendchallenge.service.datafetcher.AllCarsByModelFilterDataFetcher;
import com.inter.car.backendchallenge.service.datafetcher.AllCarsFromDBDataFetcher;
import com.inter.car.backendchallenge.service.logging.annotation.Log;
import graphql.GraphQL;
import graphql.schema.GraphQLSchema;
import graphql.schema.idl.RuntimeWiring;
import graphql.schema.idl.SchemaGenerator;
import graphql.schema.idl.SchemaParser;
import graphql.schema.idl.TypeDefinitionRegistry;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;

@Service
public class GraphQLService {

    @Log
    private static Logger log;

    @Value("classpath:car.graphql")
    Resource graphQLResource;

    private GraphQL graphQL;

    @Autowired
    private AllCarsFromDBDataFetcher allCarsFromDBDataFetcher;

    @Autowired
    private AllCarsByMakeFilterDataFetcher allCarsByMakeFilterDataFetcher;

    @Autowired
    private AllCarsByModelFilterDataFetcher allCarsByModelFilterDataFetcher;

    @PostConstruct
    public void loadSchema() throws IOException {
        log.debug("Getting schema {}", graphQLResource.getFilename());
        File schemaFile = graphQLResource.getFile();

        TypeDefinitionRegistry typeDefinitionRegistry = new SchemaParser().parse(schemaFile);
        RuntimeWiring runtimeWiring = buildRuntimeWiring();
        GraphQLSchema schema = new SchemaGenerator().makeExecutableSchema(typeDefinitionRegistry, runtimeWiring);

        graphQL = GraphQL.newGraphQL(schema).build();
    }

    private RuntimeWiring buildRuntimeWiring() {
        return RuntimeWiring.newRuntimeWiring()
                .type("Query", typeWiring -> typeWiring
                        .dataFetcher("cars", allCarsFromDBDataFetcher)
                        .dataFetcher("carsByMakeFilter", allCarsByMakeFilterDataFetcher)
                        .dataFetcher("carsByModelFilter", allCarsByModelFilterDataFetcher))
                .build();
    }

    public GraphQL getGraphQL() {
        return graphQL;
    }

}
