# postman2jmx-accso
Postman collection to Jmeter jmx file converter.
Adapted version from the original converter, tailored to Accso's DSO project.

### Note

- postman2jmx converter only converts the Postman V2+ exported files!

### Installation

postman2jmx requires Java8+ and Maven 3+.

- Clone or download the project.
```sh
$ git clone https://github.com/fredericjacob/postman2jmx-accso.git
```
- Build the project.
```sh
$ cd postman2jmx-accso
$ mvn package
```

### Usage

- NOTE: The JSON files containing global and environment variables must be located
in the folder "variables/" which must be present in the same location as the JAR.
- After build, go to the Postman2Jmx folder. It is located under the target folder.
Otherwise the converter will not work.
```sh
$ cd target/Postman2Jmx
```
- Then execute the following command to convert your postman json collection file to the jmx file.
```sh
$ java -jar my_postman_collection.json my_jmx_file.jmx MY_ENVIRONMENT
```

License
----

The MIT License (MIT)
