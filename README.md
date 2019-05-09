## **Spring Boot Application with GraphQL and proxy to ​CarQueryAPI​ **

#### Prerequisite:
1) **GraphQL**: _http://graphql.org/_

2)  **CarQueryAPI​**: _http://www.carqueryapi.com/documentation_

#### General Description:
This is a backend application in Spring Boot which use GraphQL with internal DB and collecting also data from CarQueryAPI​.
Combining results.

#### Detail description Description:
This application has only one POST endpoint (_http://127.0.0.1:10001/cars_) which has Body parameter - Query.
Allowed queries are:
 - cars: which retrieves cars from DB (hsqldb) which is inserted when application is started (see file: tco-data.csv and DataLoaderService.java),
 - carsByMakeFilter(cmd:String): which retrieves cars from CarQueryAPI​ by |CMD=getMakes| and combining result with database
 - carsByModelFilter(cmd:String, make:String): which retrieves cars from CarQueryAPI​ by |CMD=getModels and make=Acura | and combining result with database

Examples of allowed queries in body of a POST endpoint:

Query - cars:
```python
{
    cars {
        model
        tco
    }
}
```

Query - carsByMakeFilter: 
```python
    {
      carsByMakeFilter(cmd:"getMakes") {
          make
          model
          tco
      }
    }
```

Query - carsByModelFilter: 
```python
    {
      carsByModelFilter(cmd:"getModels", make:"Acura") {
          make
          model
          tco
      }
    }
```
#### Running application details:
This application is running on port: **_10001_**

#### Swagger-ui is on next address when your application is up and running: 
_http://127.0.0.1:10001/swagger-ui.html_

#### Endpoint of a POST method: 
_http://127.0.0.1:10001/cars_

#### Docker image is build when project is build: 
_mvn clean install_

#### Why GraphQL over RESTful-based or vice-versa:
Both, REST and GraphQL, are API design architectures which can be used to build web services for data-driven applications. 

The RESTful approach is always limited to deal with single resources. If you need data which is coming from two or more resources you need to do multi round trips to the server. Furthermore REST requests are always returning the full set of data which is available for a certain resource. There is no way to limit the request to only retrieve a subset of data fields.

The GraphQL approach is much more flexible and is able to overcome the major shortcomings of REST. By using the GraphQL query language you can exactly describe what the response should look like. You’re able to specify which fields should be included to limit the response to the data which is needed. Furthermore you can use the graph and combine connected entities within one GraphQL data query. No additional server round trips needed.

There are cases the one or the other approach would yield better results, but that greatly depends on your situation. :)

###### SOME OF MY CONCLUSIONS

Conclusion PROS of GraphQL:
1) A GraphQL service exposes only one endpoints through which the client can pass the necessary query to retrieve the data.
2) Client Is The Driver

Conclusion CONS of GraphQL:
1) GraphQL does't have support for browser and mobile caching unlike RESTful service which uses native HTTP caching mechanisms
2) RESTful services leverage the HTTP status codes for different errors that can be encountered. This makes the monitoring the APIs very easy and effortless for the developer. But with GraphQL services always return 200 OK response. A typical GraphQL error message looks like this (message: 'Some error occurred') with status code 200 OK.
3) Unlike RESTful services GraphQL services mandates that the client has to know about the data schema to query. 
