## Implement Dependency Injection

The starter repo for this project has not made use of Spring
Boot's DI framework. In `App.java` you can see an enormous list
of dependencies manually instantiated and wired up. You can also
see that every Service, Repository, and Controller are not marked
as Components and have no autowire field injections.

The first thing to do is to refactor this project as a Spring DI
project. To do this you'll first need to make sure every Spring
component is marked with the following annotations:
- `@RestController` for any controller who needs to be routed to
- `@Service` for any service a controller may need to call
- `@Repository` for any repositories
- `@Component` for generic components whose lifecycle needs to be
managed by the Application context.
    - *HINT:* These are `SeedData` and `Datastore`

Next we'll need to inject each dependency using the `@Autowired` 
field-injection annotation.

After completing these steps and removing the unnecessary code,
you should have no constructors in any Spring component class
and your `App.main` method should begin the Spring application.

You should also be able to access endpoints through Postman by 
sending a GET request to an endpoint such as 
`http://localhost:8080/checkables`

### Naming conventions

Check out the Naming Conventions section in the `README.md`.
The MT01 Tests are Reflection tests ensuring you've set up
each component properly. These tests need to know the name of
your fields and classes, so do not change any class names and 
use full camelCased names for each component:  
   - e.g. `patronService` not `patServ`
   
Reading any failing Reflection tests output will indicate if
it is failing due to a naming mismatch by returning a
`NoSuchFieldException` and indicating the name it was looking for.

### Completion

Run the gradle command:
`./gradlew -q clean :test --tests 'com.tct.MT01*'`
and make sure all tests pass.